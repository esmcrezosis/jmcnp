package com.esmc.mcnp.web.controller.base;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.esmc.mcnp.UserSecurity;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.util.ErrorDTO;

/**
 * @author Mawuli
 *
 */
public abstract class BaseController {

	/**
	 * Logger.
	 */
	private final Logger log = LogManager.getLogger(BaseController.class.getName());
	@Autowired
	private PlatformTransactionManager transactionManager;

	public Logger getLog() {
		return log;
	}

	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	protected TransactionStatus getTransactionStatus() {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		return getTransactionManager().getTransaction(transactionDefinition);
	}

	@InitBinder
	private void dateBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}

	public static EuUtilisateur getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserSecurity) {
			return ((UserSecurity) principal);
		}
		return null;
	}

	@ModelAttribute("codeMembre")
	public String getCodeMembreUser() {
		if (Objects.nonNull(getCurrentUser())) {
			return getCurrentUser().getCodeMembre();
		}
		return null;
	}

	public static boolean isLoggedIn() {
		return getCurrentUser() != null;
	}

	/**
	 * Handles exception if entity is not found in database.
	 *
	 * @param exception
	 * @param response
	 * @return
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorDTO handleJPAEntityNotFoundExceptions(EntityNotFoundException exception, HttpServletResponse response) {
		return new ErrorDTO("Entity not found");
	}

	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation") // 409
	@ExceptionHandler(DataIntegrityViolationException.class)
	public void conflict() {
		// Nothing to do
	}

	@ExceptionHandler({ SQLException.class, DataAccessException.class })
	public String databaseError(Exception ex) {
		ex.printStackTrace();
		return "error";
	}

	/**
	 * Handles error when save/update results in constraint violation in database.
	 *
	 * @param exception
	 * @param response
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	public ErrorDTO handleConstraintViolationExceptions(ConstraintViolationException exception,
			HttpServletResponse response) {
		ErrorDTO error = new ErrorDTO("Validation failed");
		if (exception.getConstraintViolations() != null) {
			exception.getConstraintViolations().stream().forEach((cv) -> {
				error.addDetail(cv.getPropertyPath() + " " + cv.getMessage() + " (" + cv.getInvalidValue() + ")");
			});
		}
		return error;
	}

	/**
	 * Handles all unknown unexpected exception that can happen during WS call.
	 *
	 * @param exception
	 * @param response
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Object handleExceptions(HttpServletRequest req, Exception exception, HttpServletResponse response) {
		log.error("Erreur Interne au serveur -> Request: " + req.getRequestURL() + " raised ", exception);
		if (com.esmc.mcnp.core.utils.ServletUtils.isAjaxRequest(req)) {
			return new ErrorDTO("Erreur Interne au serveur -> Request: " + req.getRequestURL() + " raised "
					+ exception.getMessage());
		} else {
			ModelAndView mav = new ModelAndView();
			mav.addObject("exception", exception);
			mav.addObject("url", req.getRequestURL());
			mav.setViewName("error");
			return mav;
		}
	}

}

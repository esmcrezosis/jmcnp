package com.esmc.mcnp.web.controller.base;

import java.sql.SQLException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.esmc.mcnp.UserSecurity;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.util.ErrorDTO;

/**
 * @author Mawuli
 *
 */
public abstract class BaseRestController {

	/**
	 * Logger.
	 */
	private final Logger log = LogManager.getLogger(BaseRestController.class.getName());

	public Logger getLog() {
		return log;
	}

	public static EuUtilisateur getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserSecurity) {
			return ((UserSecurity) principal);
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
	@ResponseBody
	public ErrorDTO databaseError() {
		ErrorDTO error = new ErrorDTO("Validation failed");
		return error;
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
	public ErrorDTO handleExceptions(HttpServletRequest req, Exception exception, HttpServletResponse response) {
		//log.error("Erreur Interne au serveur -> Request: " + req.getRequestURL() + " raised ", exception);
        exception.printStackTrace();
		ErrorDTO error = new ErrorDTO("Validation failed");
		error.addDetail(exception.getMessage());
		return error;
	}

}

package com.esmc.mcnp.web.mvc.utils;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.esmc.mcnp.domain.UserSecurity;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.web.mvc.dto.Result;

public abstract class BaseRestController {

	/** Logger. */
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
	public Result handleJPAEntityNotFoundExceptions(EntityNotFoundException exception, HttpServletResponse response) {
		log.error("Erreur d'execution ; ", exception);
		return new Result(0, "Entity not found");
	}

	/**
	 * Handles exception if entity is not found in database.
	 * 
	 * @param exception
	 * @param response
	 * @return
	 */
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public Result accessDenied(AccessDeniedException exception, HttpServletResponse response) {
		log.error("Erreur d'execution ; ", exception);
		return new Result(0, exception.getMessage());
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
	public Result handleConstraintViolationExceptions(ConstraintViolationException exception,
			HttpServletResponse response) {
		Result error = new Result(0,"Validation failed");
		if (exception.getConstraintViolations() != null) {
			log.error("Erreur d'execution ; ", exception);
			exception.getConstraintViolations().stream().forEach((cv) -> error
					.setMessage("Validation failed :" + cv.getPropertyPath() + " " + cv.getMessage() + " (" + cv.getInvalidValue() + ")"));
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
	public Result handleExceptions(Exception exception, HttpServletResponse response) {
		log.error("[RestController]", exception);
		return new Result(0, "Error occurred => " + "Type : " + exception.getClass().getSimpleName() + "; Message : "
				+ exception.getMessage());
	}
}

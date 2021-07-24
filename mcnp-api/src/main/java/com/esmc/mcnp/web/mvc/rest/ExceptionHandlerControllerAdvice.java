/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.web.mvc.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.esmc.mcnp.web.mvc.dto.Result;
import com.esmc.mcnp.web.mvc.exceptions.WrongAddressException;

/**
 *
 * @author HP
 */
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {
	
	private final Logger log = LogManager.getLogger(ExceptionHandlerControllerAdvice.class.getName());

	@ExceptionHandler(IOException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE) // (1)
	public ResponseEntity<Result> exceptionHandler(IOException e, HttpServletRequest request) {
		log.error("Erreur d'execution ; ", e);
		if (StringUtils.containsIgnoreCase(ExceptionUtils.getRootCauseMessage(e), "Broken pipe")) { // (2)
			return null; // (2) socket is closed, cannot return any response
		} else {
			Result message = new Result(0, e.getMessage() + ": " + e.getCause().getMessage());
			return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR); // (3)
		}
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Result> nullPointerExceptionHandler(HttpServletRequest request,
			NullPointerException exception) {
		log.error("Erreur d'execution ; ", exception);
		//exception.printStackTrace()
		Result message = new Result(0, exception.getMessage() + ": " + exception.getCause().getMessage());
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(WrongAddressException.class)
	public ResponseEntity<Result> wrongAddressExceptionHandler(HttpServletRequest request,
			WrongAddressException exception) {
		log.error("Erreur d'execution ; ", exception);
		//exception.printStackTrace();
		Result message = new Result(0, exception.getMessage() + ": " + exception.getCause().getMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Result> genericExceptionHandler(HttpServletRequest request, Exception exception) {
		log.error("Erreur d'execution ; ", exception);
		//exception.printStackTrace();
		Result message = new Result(0, exception.getMessage() + ": " + exception.getCause().getMessage());
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

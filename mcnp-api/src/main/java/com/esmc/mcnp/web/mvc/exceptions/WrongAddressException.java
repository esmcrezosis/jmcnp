package com.esmc.mcnp.web.mvc.exceptions;

/**
 * @author Gokan EKINCI
 */
public class WrongAddressException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrongAddressException(String message) {
		super(message);
	}
}

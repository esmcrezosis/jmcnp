/**
 * 
 */
package com.esmc.mcnp.commons.exception;

/**
 * @author Siva
 *
 */
public class McnpException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public McnpException() {
		super();
	}

	public McnpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public McnpException(String message, Throwable cause) {
		super(message, cause);
	}

	public McnpException(String message) {
		super(message);
	}

	public McnpException(Throwable cause) {
		super(cause);
	}

}

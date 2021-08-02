package com.esmc.mcnp.infrastructure.exception.business;

import org.springframework.http.HttpStatus;

/**
 * Exception of entity not found.
 *
 * @author johnniang
 */
public class NotFoundException extends AppException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}

package com.esmc.mcnp.infrastructure.exception.system;

import org.springframework.http.HttpStatus;

import com.esmc.mcnp.infrastructure.exception.business.AppException;

/**
 * Exception caused by accessing forbidden resources.
 *
 * @author johnniang
 */
public class ForbiddenException extends AppException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}

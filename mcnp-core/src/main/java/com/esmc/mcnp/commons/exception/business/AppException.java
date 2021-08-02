package com.esmc.mcnp.infrastructure.exception.business;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Base exception of the project.
 *
 * @author johnniang
 */
public abstract class AppException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Error errorData.
     */
    private Object errorData;

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    @NonNull
    public abstract HttpStatus getStatus();

    @Nullable
    public Object getErrorData() {
        return errorData;
    }

    /**
     * Sets error errorData.
     *
     * @param errorData error data
     * @return current exception.
     */
    @NonNull
    public AppException setErrorData(@Nullable Object errorData) {
        this.errorData = errorData;
        return this;
    }
}

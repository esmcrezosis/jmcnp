package com.esmc.mcnp.util;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO object for returning back error messages for REST WS.
 * 
 * @author Ignas
 *
 */
public class ErrorDTO {

    /** Description. */
    private String message;

    /** Details. */
    private List<String> details = new ArrayList<String>();

    /** Constructor. */
    public ErrorDTO(String message) {
        this.message = message;
    }

    
    public ErrorDTO(String message, List<String> details) {
		this.message = message;
		this.details = details;
	}


	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    /** Add single detail to details list. */
    public void addDetail(String detail) {
        this.details.add(detail);
    }
    
}

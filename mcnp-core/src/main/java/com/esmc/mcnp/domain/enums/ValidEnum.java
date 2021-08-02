package com.esmc.mcnp.domain.enums;

public enum ValidEnum {
    /**
     * valide
     */
    Y("YES"),
    /**
     * invalide
     */
    N("NO");

    private String message;

    ValidEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

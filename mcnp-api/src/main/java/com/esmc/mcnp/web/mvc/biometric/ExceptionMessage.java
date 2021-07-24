package com.esmc.mcnp.web.mvc.biometric;

public class ExceptionMessage {

	private Integer resultat;
    private String message;
    private String className;
    private String path;
    private String date;

    public ExceptionMessage() {
    }

    public ExceptionMessage(String date, String path, String className, String message) {
        this.message = message;
        this.className = className;
        this.path = path;
        this.date = date;
    }

    public Integer getResultat() {
		return resultat;
	}

	public void setResultat(Integer resultat) {
		this.resultat = resultat;
	}

	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

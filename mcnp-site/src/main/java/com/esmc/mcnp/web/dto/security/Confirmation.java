package com.esmc.mcnp.web.dto.security;

import java.io.Serializable;

public class Confirmation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codeMembre;
	private String codeOperateur;
	private String activite;
	private String message;

	public Confirmation() {
	}

	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	public String getCodeOperateur() {
		return codeOperateur;
	}

	public void setCodeOperateur(String codeOperateur) {
		this.codeOperateur = codeOperateur;
	}

	public String getActivite() {
		return activite;
	}

	public void setActivite(String activite) {
		this.activite = activite;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

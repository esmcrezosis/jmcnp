package com.esmc.mcnp.web.mvc.dto;

import java.io.Serializable;

public class Eli implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String numeroEli;
	private String typeEli;

	public Eli() {
	}

	public Eli(String numeroEli) {
		this.numeroEli = numeroEli;
	}

	public Eli(String numeroEli, String typeEli) {
		this.numeroEli = numeroEli;
	}

	public String getNumeroEli() {
		return numeroEli;
	}

	public void setNumeroEli(String numeroEli) {
		this.numeroEli = numeroEli;
	}

	public String getTypeEli() {
		return typeEli;
	}

	public void setTypeEli(String typeEli) {
		this.typeEli = typeEli;
	}

}

package com.esmc.mcnp.web.mvc.dto;

public class EliDto {
	private String numeroEli;
	private String codeTegc;

	public EliDto() {

	}

	public EliDto(String numeroEli, String codeTegc) {
		super();
		this.numeroEli = numeroEli;
		this.codeTegc = codeTegc;
	}

	public String getNumeroEli() {
		return numeroEli;
	}

	public void setNumeroEli(String numeroEli) {
		this.numeroEli = numeroEli;
	}

	public String getCodeTegc() {
		return codeTegc;
	}

	public void setCodeTegc(String codeTegc) {
		this.codeTegc = codeTegc;
	}

}

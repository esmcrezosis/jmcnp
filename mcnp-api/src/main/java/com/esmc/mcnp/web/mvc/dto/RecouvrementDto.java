package com.esmc.mcnp.web.mvc.dto;

public class RecouvrementDto {

	private String codeMembre;
	private String newCodeMembre;
	private String typeRessource;

	public RecouvrementDto() {
	}

	public RecouvrementDto(String codeMembre, String newCodeMembre, String typeRessource) {
		this.codeMembre = codeMembre;
		this.newCodeMembre = newCodeMembre;
		this.typeRessource = typeRessource;
	}

	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	public String getNewCodeMembre() {
		return newCodeMembre;
	}

	public void setNewCodeMembre(String newCodeMembre) {
		this.newCodeMembre = newCodeMembre;
	}

	public String getTypeRessource() {
		return typeRessource;
	}

	public void setTypeRessource(String typeRessource) {
		this.typeRessource = typeRessource;
	}

}

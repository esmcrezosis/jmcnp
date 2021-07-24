package com.esmc.mcnp.web.dto.cm;

import java.io.Serializable;

public class Membre implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codeMembre;
	private String nomMembre;
	private String prenomMembre;
	private String raisonSociale;
	private Integer codeStatus;

	public Membre(String codeMembre, String raisonSociale, Integer codeStatus) {
		this.codeMembre = codeMembre;
		this.raisonSociale = raisonSociale;
		this.codeStatus = codeStatus;
	}

	public Membre(String codeMembre, String nomMembre, String prenomMembre, Integer codeStatus) {
		this.codeMembre = codeMembre;
		this.nomMembre = nomMembre;
		this.prenomMembre = prenomMembre;
		this.codeStatus = codeStatus;
	}

	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	public String getNomMembre() {
		return nomMembre;
	}

	public void setNomMembre(String nomMembre) {
		this.nomMembre = nomMembre;
	}

	public String getPrenomMembre() {
		return prenomMembre;
	}

	public void setPrenomMembre(String prenomMembre) {
		this.prenomMembre = prenomMembre;
	}

	public String getRaisonSociale() {
		return raisonSociale;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}

	public Integer getCodeStatus() {
		return codeStatus;
	}

	public void setCodeStatus(Integer codeStatus) {
		this.codeStatus = codeStatus;
	}

}

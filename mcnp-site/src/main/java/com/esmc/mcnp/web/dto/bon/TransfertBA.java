package com.esmc.mcnp.web.dto.bon;

public class TransfertBA {

	private String codeMembre;
	private String typeBon;
	private String catBon;
	private Double montant;

	public TransfertBA() {
	}

	public TransfertBA(String codeMembre, String typeBon, Double montant) {
		this.codeMembre = codeMembre;
		this.typeBon = typeBon;
		this.montant = montant;
	}

	public TransfertBA(String codeMembre, String typeBon, String catBon, Double montant) {
		this.codeMembre = codeMembre;
		this.typeBon = typeBon;
		this.montant = montant;
		this.catBon = catBon;
	}

	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	public String getTypeBon() {
		return typeBon;
	}

	public void setTypeBon(String typeBon) {
		this.typeBon = typeBon;
	}

	public String getCatBon() {
		return catBon;
	}

	public void setCatBon(String catBon) {
		this.catBon = catBon;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

}

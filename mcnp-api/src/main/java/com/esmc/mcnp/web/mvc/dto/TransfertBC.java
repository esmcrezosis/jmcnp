package com.esmc.mcnp.web.mvc.dto;

public class TransfertBC {

	private String codeMembre;
	private String type;
	private String cat;
	private String typeCredit;
	private Double prk;
	private Double montant;

	public TransfertBC() {
	}

	public TransfertBC(String codeMembre, String type, String cat, String typeCredit, Double montant) {
		this.codeMembre = codeMembre;
		this.type = type;
		this.cat = cat;
		this.typeCredit = typeCredit;
		this.montant = montant;
	}

	public TransfertBC(String codeMembre, String type, String cat, String typeCredit, Double prk, Double montant) {
		this.codeMembre = codeMembre;
		this.type = type;
		this.cat = cat;
		this.typeCredit = typeCredit;
		this.prk = prk;
		this.montant = montant;
	}

	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public String getTypeCredit() {
		return typeCredit;
	}

	public void setTypeCredit(String typeCredit) {
		this.typeCredit = typeCredit;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public Double getPrk() {
		return prk;
	}

	public void setPrk(Double prk) {
		this.prk = prk;
	}

}

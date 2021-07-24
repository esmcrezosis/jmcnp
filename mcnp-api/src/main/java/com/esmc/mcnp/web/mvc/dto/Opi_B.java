package com.esmc.mcnp.web.mvc.dto;

public class Opi_B {
	private String codeMembre;
	private String modePaiement;
	private String referencePaiement;
	private Double montant;
	private String codeTegc;
	private String typeTe;

	public Opi_B() {
		// TODO Auto-generated constructor stub
	}

	public Opi_B(String codeMembre, String modePaiement, String referencePaiement, Double montant, String codeTegc,
			String typeTe) {
		this.codeMembre = codeMembre;
		this.modePaiement = modePaiement;
		this.referencePaiement = referencePaiement;
		this.montant = montant;
		this.codeTegc = codeTegc;
		this.typeTe = typeTe;
	}

	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	public String getModePaiement() {
		return modePaiement;
	}

	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}

	public String getReferencePaiement() {
		return referencePaiement;
	}

	public void setReferencePaiement(String referencePaiement) {
		this.referencePaiement = referencePaiement;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public String getCodeTegc() {
		return codeTegc;
	}

	public void setCodeTegc(String codeTegc) {
		this.codeTegc = codeTegc;
	}

	public String getTypeTe() {
		return typeTe;
	}

	public void setTypeTe(String typeTe) {
		this.typeTe = typeTe;
	}

}

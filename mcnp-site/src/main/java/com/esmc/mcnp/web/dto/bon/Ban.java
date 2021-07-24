package com.esmc.mcnp.web.dto.bon;

import java.io.Serializable;

public class Ban implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private String codeMembre;
	private String codeTegc;
	private double montant;
	private String modePaiement;

	public Ban() {
	}

	public Ban(String codeMembre, double montant, String modePaiement) {
		this.codeMembre = codeMembre;
		this.montant = montant;
		this.modePaiement = modePaiement;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	public String getCodeTegc() {
		return codeTegc;
	}

	public void setCodeTegc(String codeTegc) {
		this.codeTegc = codeTegc;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getModePaiement() {
		return modePaiement;
	}

	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}

}

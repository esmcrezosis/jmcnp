package com.esmc.mcnp.web.mvc.dto;

/**
 * Created by USER on 14/04/2017.
 */
public class ChargeDTO {
	private String codeCharge;
	private String typeCharge;
	private String libelleCharge;
	private String origineCharge;
	private String codeTegcCreancier;
	private double montantCharge;
	private String codeMembreDebiteur;
	private Creance[] creances;

	public ChargeDTO() {
	}

	public ChargeDTO(String codeCharge, String typeCharge, String libelleCharge, String origineCharge,
			double montantCharge, String codeMembreDebiteur, String codeTegcCreancier) {
		this.codeCharge = codeCharge;
		this.typeCharge = typeCharge;
		this.libelleCharge = libelleCharge;
		this.origineCharge = origineCharge;
		this.montantCharge = montantCharge;
		this.codeMembreDebiteur = codeMembreDebiteur;
		this.codeTegcCreancier = codeTegcCreancier;
	}

	public String getCodeCharge() {
		return codeCharge;
	}

	public void setCodeCharge(String codeCharge) {
		this.codeCharge = codeCharge;
	}

	public String getTypeCharge() {
		return typeCharge;
	}

	public void setTypeCharge(String typeCharge) {
		this.typeCharge = typeCharge;
	}

	public String getLibelleCharge() {
		return libelleCharge;
	}

	public void setLibelleCharge(String libelleCharge) {
		this.libelleCharge = libelleCharge;
	}

	public String getOrigineCharge() {
		return origineCharge;
	}

	public void setOrigineCharge(String origineCharge) {
		this.origineCharge = origineCharge;
	}

	public double getMontantCharge() {
		return montantCharge;
	}

	public void setMontantCharge(double montantCharge) {
		this.montantCharge = montantCharge;
	}

	public String getCodeMembreDebiteur() {
		return codeMembreDebiteur;
	}

	public void setCodeMembreDebiteur(String codeMembreDebiteur) {
		this.codeMembreDebiteur = codeMembreDebiteur;
	}
	
	public String getCodeTegcCreancier() {
		return codeTegcCreancier;
	}

	public void setCodeTegcCreancier(String codeTegcCreancier) {
		this.codeTegcCreancier = codeTegcCreancier;
	}

	public Creance[] getCreances() {
		return creances;
	}

	public void setCreances(Creance[] creances) {
		this.creances = creances;
	}
}

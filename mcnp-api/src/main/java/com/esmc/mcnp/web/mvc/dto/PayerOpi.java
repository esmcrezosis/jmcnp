package com.esmc.mcnp.web.mvc.dto;

public class PayerOpi {
	private String codeMembre;
	private String modeReg;
	private String typeEsc;
	private Integer nbre;
	private double montant;
	private String codeMembrePbf;
	private String numeroBon;

	public PayerOpi() {

	}

	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	public String getModeReg() {
		return modeReg;
	}

	public void setModeReg(String modeReg) {
		this.modeReg = modeReg;
	}

	public String getTypeEsc() {
		return typeEsc;
	}

	public void setTypeEsc(String typeEsc) {
		this.typeEsc = typeEsc;
	}

	public Integer getNbre() {
		return nbre;
	}

	public void setNbre(Integer nbre) {
		this.nbre = nbre;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getCodeMembrePbf() {
		return codeMembrePbf;
	}

	public void setCodeMembrePbf(String codeMembrePbf) {
		this.codeMembrePbf = codeMembrePbf;
	}

	public String getNumeroBon() {
		return numeroBon;
	}

	public void setNumeroBon(String numeroBon) {
		this.numeroBon = numeroBon;
	}

}

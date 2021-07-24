package com.esmc.mcnp.web.mvc.dto;

public class Creance {

	private String codeMembreCreancier;
	private double montantOp;
	private String numDoc;
	private String codeTegc;

	public Creance() {

	}

	public Creance(String codeMembreCreancier, String codeTegc, double montantOp, String numDoc) {
		this.codeMembreCreancier = codeMembreCreancier;
		this.montantOp = montantOp;
		this.numDoc = numDoc;
		this.codeTegc = codeTegc;
	}

	public String getCodeMembreCreancier() {
		return codeMembreCreancier;
	}

	public void setCodeMembreCreancier(String codeMembreCreancier) {
		this.codeMembreCreancier = codeMembreCreancier;
	}

	public double getMontantOp() {
		return montantOp;
	}

	public void setMontantOp(double montantOp) {
		this.montantOp = montantOp;
	}

	public String getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}

	public String getCodeTegc() {
		return codeTegc;
	}

	public void setCodeTegc(String codeTegc) {
		this.codeTegc = codeTegc;
	}

}

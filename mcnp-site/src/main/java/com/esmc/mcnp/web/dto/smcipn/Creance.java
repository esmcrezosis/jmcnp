package com.esmc.mcnp.web.dto.smcipn;

public class Creance {

	private String codeMembreDebiteur;
	private String codeMembreCreancier;
	private String nomCreancier;
	private String nomDebiteur;
	private String codeTegc;
	private String libelleCreance;
	private double montantOp;
	private String modePaiement;
	private String referencePaiement;
	private String numDoc;
	private Integer marge;
	private Integer nbreOpi;
	private String typeCreance;
	private String numeroAppel;

	public Creance() {

	}

	public Creance(String codeMembreCreancier, String codeTegc, double montantOp, String numDoc) {
		this.codeMembreCreancier = codeMembreCreancier;
		this.montantOp = montantOp;
		this.numDoc = numDoc;
		this.codeTegc = codeTegc;
	}
	
	public String getCodeMembreDebiteur() {
		return codeMembreDebiteur;
	}

	public void setCodeMembreDebiteur(String codeMembreDebiteur) {
		this.codeMembreDebiteur = codeMembreDebiteur;
	}

	public String getCodeMembreCreancier() {
		return codeMembreCreancier;
	}

	public void setCodeMembreCreancier(String codeMembreCreancier) {
		this.codeMembreCreancier = codeMembreCreancier;
	}
	
	public String getNomCreancier() {
		return nomCreancier;
	}

	public void setNomCreancier(String nomCreancier) {
		this.nomCreancier = nomCreancier;
	}

	public String getNomDebiteur() {
		return nomDebiteur;
	}

	public void setNomDebiteur(String nomDebiteur) {
		this.nomDebiteur = nomDebiteur;
	}

	public String getCodeTegc() {
		return codeTegc;
	}

	public void setCodeTegc(String codeTegc) {
		this.codeTegc = codeTegc;
	}
	
	public String getLibelleCreance() {
		return libelleCreance;
	}

	public void setLibelleCreance(String libelleCreance) {
		this.libelleCreance = libelleCreance;
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

	public Integer getMarge() {
		return marge;
	}

	public void setMarge(Integer marge) {
		this.marge = marge;
	}

	public Integer getNbreOpi() {
		return nbreOpi;
	}

	public void setNbreOpi(Integer nbreOpi) {
		this.nbreOpi = nbreOpi;
	}

	public String getTypeCreance() {
		return typeCreance;
	}

	public void setTypeCreance(String typeCreance) {
		this.typeCreance = typeCreance;
	}

	public String getNumeroAppel() {
		return numeroAppel;
	}

	public void setNumeroAppel(String numeroAppel) {
		this.numeroAppel = numeroAppel;
	}

}

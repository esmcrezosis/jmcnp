package com.esmc.mcnp.web.mvc.dto;

import java.io.Serializable;

public class NCreance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codeMembreDebiteur;
	private String codeMembreCreancier;
	private String codeTegc;
	private double montantOp;
	private String typeRessource;
	private String modePaiement;
	private String referencePaiement;
	private Integer marge;
	private Integer nbreOpi;

	public NCreance() {

	}

	public NCreance(String codeMembreCreancier, String codeTegc, double montantOp, String typeRessource) {
		this.codeMembreCreancier = codeMembreCreancier;
		this.montantOp = montantOp;
		this.typeRessource = typeRessource;
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

	public String getCodeTegc() {
		return codeTegc;
	}

	public void setCodeTegc(String codeTegc) {
		this.codeTegc = codeTegc;
	}

	public double getMontantOp() {
		return montantOp;
	}

	public void setMontantOp(double montantOp) {
		this.montantOp = montantOp;
	}

	public String getTypeRessource() {
		return typeRessource;
	}

	public void setTypeRessource(String typeRessource) {
		this.typeRessource = typeRessource;
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

}

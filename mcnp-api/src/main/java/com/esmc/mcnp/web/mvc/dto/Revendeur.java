package com.esmc.mcnp.web.mvc.dto;

public class Revendeur {
    private String codeMembreRevendeur;
	private String designationProduit;
	private String codeTegc;
	private String modePaiement;
	private String referencePayement;
	private String typeBon;
	private Double montantBon;
	private Integer reinjecter;
	private Integer nbreInjection;
	private String codeEnvoi;
	private String codeConfirmation;

	public Revendeur() {
	}

	
	public String getCodeMembreRevendeur() {
		return codeMembreRevendeur;
	}


	public void setCodeMembreRevendeur(String codeMembreRevendeur) {
		this.codeMembreRevendeur = codeMembreRevendeur;
	}


	public String getDesignationProduit() {
		return designationProduit;
	}

	public void setDesignationProduit(String designationProduit) {
		this.designationProduit = designationProduit;
	}

	public String getCodeTegc() {
		return codeTegc;
	}

	public void setCodeTegc(String codeTegc) {
		this.codeTegc = codeTegc;
	}

	public String getModePaiement() {
		return modePaiement;
	}

	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}

	public String getReferencePayement() {
		return referencePayement;
	}

	public void setReferencePayement(String referencePayement) {
		this.referencePayement = referencePayement;
	}
	public String getTypeBon() {
		return typeBon;
	}

	public void setTypeBon(String typeBon) {
		this.typeBon = typeBon;
	}
	public Double getMontantBon() {
		return montantBon;
	}

	public void setMontantBon(Double montantBon) {
		this.montantBon = montantBon;
	}

	public Integer getReinjecter() {
		return reinjecter;
	}

	public void setReinjecter(Integer reinjecter) {
		this.reinjecter = reinjecter;
	}

	public Integer getNbreInjection() {
		return nbreInjection;
	}

	public void setNbreInjection(Integer nbreInjection) {
		this.nbreInjection = nbreInjection;
	}
	
    public String getCodeEnvoi() {
		return codeEnvoi;
	}

	public void setCodeEnvoi(String codeEnvoi) {
		this.codeEnvoi = codeEnvoi;
	}
	public String getCodeConfirmation() {
		return codeConfirmation;
	}

	public void setCodeConfirmation(String codeConfirmation) {
		this.codeConfirmation = codeConfirmation;
	}
}

package com.esmc.mcnp.web.dto;

public class Revendeur {

	private String designationProduit;
	private String codeTegc;
	private String modePaiement;
	private String referencePayement;
	private Double montantBonNeutre;
	private Integer reinjecter;
	private Integer nbreInjection;

	public Revendeur() {
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

	public Double getMontantBonNeutre() {
		return montantBonNeutre;
	}

	public void setMontantBonNeutre(Double montantBonNeutre) {
		this.montantBonNeutre = montantBonNeutre;
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

}

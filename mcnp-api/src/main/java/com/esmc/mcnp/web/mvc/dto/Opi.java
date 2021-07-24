package com.esmc.mcnp.web.mvc.dto;

public class Opi {

	private String codeMembre;
	private String modePaiement;
	private String referencePaiement;
	private Double montant;
	private String codeTegc;
	private String typeOpi;
	private Long souscriptionId;
	private String typeBnp;

	public Opi() {
	}

	public Opi(String codeMembre, String codeTegc, Double montant) {
		this.codeMembre = codeMembre;
		this.montant = montant;
		this.codeTegc = codeTegc;
	}

	public Opi(String typeOpi, String codeMembre, String codeTegc, String modePaiement, String referencePaiement,
			Double montant, Long souscriptionId) {
		this.typeOpi = typeOpi;
		this.codeMembre = codeMembre;
		this.modePaiement = modePaiement;
		this.referencePaiement = referencePaiement;
		this.montant = montant;
		this.codeTegc = codeTegc;
		this.souscriptionId = souscriptionId;
	}

	/**
	 * @return the codeMembre
	 */
	public String getCodeMembre() {
		return codeMembre;
	}

	/**
	 * @param codeMembre
	 *            the codeMembre to set
	 */
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

	/**
	 * @return the montant
	 */
	public Double getMontant() {
		return montant;
	}

	/**
	 * @param montant
	 *            the montant to set
	 */
	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public String getCodeTegc() {
		return codeTegc;
	}

	public void setCodeTegc(String codeTegc) {
		this.codeTegc = codeTegc;
	}

	public String getTypeOpi() {
		return typeOpi;
	}

	public void setTypeOpi(String typeOpi) {
		this.typeOpi = typeOpi;
	}

	public Long getSouscriptionId() {
		return souscriptionId;
	}

	public void setSouscriptionId(Long souscriptionId) {
		this.souscriptionId = souscriptionId;
	}

	public String getTypeBnp() {
		return typeBnp;
	}

	public void setTypeBnp(String typeBnp) {
		this.typeBnp = typeBnp;
	}

}

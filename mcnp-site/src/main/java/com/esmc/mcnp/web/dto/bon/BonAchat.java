package com.esmc.mcnp.web.dto.bon;

public class BonAchat {
	private String type;
	private String codeMembre;
	private String codeBonNeutre;
	private Double montant;

	public BonAchat() {
	}

	public BonAchat(String codeMembre, Double montant) {
		this.codeMembre = codeMembre;
		this.montant = montant;
	}

	public BonAchat(String codeMembre, String codeBonNeutre, Double montant) {
		this.codeMembre = codeMembre;
		this.codeBonNeutre = codeBonNeutre;
		this.montant = montant;
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

	public String getCodeBonNeutre() {
		return codeBonNeutre;
	}

	public void setCodeBonNeutre(String codeBonNeutre) {
		this.codeBonNeutre = codeBonNeutre;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

}

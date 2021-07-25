package com.esmc.mcnp.dto.ksu;

public class Souscription {
	
	private String typeSouscription;
	private String codeMembreIntegrateur;
	private double montSouscription;
	private String typeActivite;
	private String codeActivite;
	private Integer typeIntegrateur;
	private String statutSouscription;

	public Souscription() {
	}

	public Souscription(String typeSouscription, String codeMembreIntegrateur, double montSouscription,
			String typeActivite, Integer typeIntegrateur, String statutSouscription) {
		this.typeSouscription = typeSouscription;
		this.codeMembreIntegrateur = codeMembreIntegrateur;
		this.montSouscription = montSouscription;
		this.typeActivite = typeActivite;
		this.typeIntegrateur = typeIntegrateur;
		this.statutSouscription = statutSouscription;
	}

	public String getTypeSouscription() {
		return typeSouscription;
	}

	public void setTypeSouscription(String typeSouscription) {
		this.typeSouscription = typeSouscription;
	}

	public String getCodeMembreIntegrateur() {
		return codeMembreIntegrateur;
	}

	public void setCodeMembreIntegrateur(String codeMembreIntegrateur) {
		this.codeMembreIntegrateur = codeMembreIntegrateur;
	}

	public double getMontSouscription() {
		return montSouscription;
	}

	public void setMontSouscription(double montSouscription) {
		this.montSouscription = montSouscription;
	}

	public String getTypeActivite() {
		return typeActivite;
	}

	public void setTypeActivite(String typeActivite) {
		this.typeActivite = typeActivite;
	}

	public String getCodeActivite() {
		return codeActivite;
	}

	public void setCodeActivite(String codeActivite) {
		this.codeActivite = codeActivite;
	}

	public Integer getTypeIntegrateur() {
		return typeIntegrateur;
	}

	public void setTypeIntegrateur(Integer typeIntegrateur) {
		this.typeIntegrateur = typeIntegrateur;
	}

	public String getStatutSouscription() {
		return statutSouscription;
	}

	public void setStatutSouscription(String statutSouscription) {
		this.statutSouscription = statutSouscription;
	}
	
	

}

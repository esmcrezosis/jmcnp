package com.esmc.mcnp.web.dto.bon;

public class BcFormation {

	private String codeMembre;
	private String cycle;
	private int duree;
	private double montFormation;
	private double montSouscription;
	private double montBonconso;
	private String typeRecurrent;
	private String typeProduit;
	private String catBonConso;
	private String numBon;

	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public double getMontFormation() {
		return montFormation;
	}

	public void setMontFormation(double montFormation) {
		this.montFormation = montFormation;
	}

	public double getMontSouscription() {
		return montSouscription;
	}

	public void setMontSouscription(double montSouscription) {
		this.montSouscription = montSouscription;
	}

	public double getMontBonconso() {
		return montBonconso;
	}

	public void setMontBonconso(double montBonconso) {
		this.montBonconso = montBonconso;
	}

	public String getTypeRecurrent() {
		return typeRecurrent;
	}

	public void setTypeRecurrent(String typeRecurrent) {
		this.typeRecurrent = typeRecurrent;
	}

	public String getTypeProduit() {
		return typeProduit;
	}

	public void setTypeProduit(String typeProduit) {
		this.typeProduit = typeProduit;
	}

	public String getCatBonConso() {
		return catBonConso;
	}

	public void setCatBonConso(String catBonConso) {
		this.catBonConso = catBonConso;
	}

	public String getNumBon() {
		return numBon;
	}

	public void setNumBon(String numBon) {
		this.numBon = numBon;
	}

}

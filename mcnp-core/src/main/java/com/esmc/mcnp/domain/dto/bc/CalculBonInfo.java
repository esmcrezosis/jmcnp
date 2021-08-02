package com.esmc.mcnp.domain.dto.bc;

public class CalculBonInfo {

	private String catBon;
	private String typeRecurrent;
	private String typeProduit;
	private String typeCredit;
	private double duree;
	private Integer frequenceCumul;
	private Integer bps;
	private double prk;

	public CalculBonInfo() {
	}

	public CalculBonInfo(double prk, double duree) {
		this.prk = prk;
		this.duree = duree;
	}

	public CalculBonInfo(String catBon, String typeRecurrent, String typeProduit, double duree, double prk) {
		this.catBon = catBon;
		this.typeRecurrent = typeRecurrent;
		this.typeProduit = typeProduit;
		this.duree = duree;
		this.prk = prk;
	}

	public CalculBonInfo(String catBon, String typeRecurrent, String typeProduit, String typeCredit, double duree,
			double prk) {
		this.catBon = catBon;
		this.typeRecurrent = typeRecurrent;
		this.typeProduit = typeProduit;
		this.typeCredit = typeCredit;
		this.duree = duree;
		this.prk = prk;
	}

	public CalculBonInfo(String catBon, String typeRecurrent, String typeProduit, double duree, double prk,
			Integer frequenceCumul, Integer bps) {
		this.catBon = catBon;
		this.typeRecurrent = typeRecurrent;
		this.typeProduit = typeProduit;
		this.duree = duree;
		this.prk = prk;
		this.frequenceCumul = frequenceCumul;
		this.bps = bps;
	}	

	public CalculBonInfo(String catBon, String typeRecurrent, String typeProduit, String typeCredit, double duree,
			Integer frequenceCumul, Integer bps, double prk) {
		this.catBon = catBon;
		this.typeRecurrent = typeRecurrent;
		this.typeProduit = typeProduit;
		this.typeCredit = typeCredit;
		this.duree = duree;
		this.frequenceCumul = frequenceCumul;
		this.bps = bps;
		this.prk = prk;
	}

	/**
	 * @return the typeBon
	 */
	public String getCatBon() {
		return catBon;
	}

	/**
	 * @param typeBon
	 *            the typeBon to set
	 */
	public void setCatBon(String catBon) {
		this.catBon = catBon;
	}

	/**
	 * @return the typeRecurrent
	 */
	public String getTypeRecurrent() {
		return typeRecurrent;
	}

	/**
	 * @param typeRecurrent
	 *            the typeRecurrent to set
	 */
	public void setTypeRecurrent(String typeRecurrent) {
		this.typeRecurrent = typeRecurrent;
	}

	/**
	 * @return the typeProduit
	 */
	public String getTypeProduit() {
		return typeProduit;
	}

	/**
	 * @param typeProduit
	 *            the typeProduit to set
	 */
	public void setTypeProduit(String typeProduit) {
		this.typeProduit = typeProduit;
	}

	public String getTypeCredit() {
		return typeCredit;
	}

	public void setTypeCredit(String typeCredit) {
		this.typeCredit = typeCredit;
	}

	/**
	 * @return the duree
	 */
	public double getDuree() {
		return duree;
	}

	/**
	 * @param duree
	 *            the duree to set
	 */
	public void setDuree(double duree) {
		this.duree = duree;
	}

	public Integer getFrequenceCumul() {
		return frequenceCumul;
	}

	public void setFrequenceCumul(Integer frequenceCumul) {
		this.frequenceCumul = frequenceCumul;
	}

	public Integer getBps() {
		return bps;
	}

	public void setBps(Integer bps) {
		this.bps = bps;
	}

	/**
	 * @return the prk
	 */
	public double getPrk() {
		return prk;
	}

	/**
	 * @param prk
	 *            the prk to set
	 */
	public void setPrk(double prk) {
		this.prk = prk;
	}

}

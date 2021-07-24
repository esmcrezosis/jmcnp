package com.esmc.mcnp.web.dto.opi;

import java.util.Date;

public class Opi {

	private String codeMembre;
	private String codeBanque;
	private String modePaiement;
	private String referencePaiement;
	private Double montant;
	private String numeroBon;
	private String typeFournisseur;
	private String codeTegc;
	private String typeOpi;
	private Integer nbre;
	private Integer serie;
	private double montTranche1;
	private int diferre;
	private Date dateDebut;
	private String typeGcp;
	private Integer marge;
	private Integer ttc;
	private float tauxTva;

	public Opi() {
	}

	public Opi(String codeMembre, String typeFournisseur, String codeTegc, String codeBanque, Double montant) {
		this.codeMembre = codeMembre;
		this.codeBanque = codeBanque;
		this.montant = montant;
		this.typeFournisseur = typeFournisseur;
		this.codeTegc = codeTegc;
	}

	public Opi(String typeGcp, String codeMembre, String typeFournisseur, String codeTegc, String codeBanque,
			Double montant) {
		this.typeGcp = typeGcp;
		this.codeMembre = codeMembre;
		this.codeBanque = codeBanque;
		this.montant = montant;
		this.typeFournisseur = typeFournisseur;
		this.codeTegc = codeTegc;
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

	/**
	 * @return the codeBanque
	 */
	public String getCodeBanque() {
		return codeBanque;
	}

	/**
	 * @param codeBanque
	 *            the codeBanque to set
	 */
	public void setCodeBanque(String codeBanque) {
		this.codeBanque = codeBanque;
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

	public String getNumeroBon() {
		return numeroBon;
	}

	public void setNumeroBon(String numeroBon) {
		this.numeroBon = numeroBon;
	}

	public String getTypeFournisseur() {
		return typeFournisseur;
	}

	public void setTypeFournisseur(String typeFournisseur) {
		this.typeFournisseur = typeFournisseur;
	}

	public String getCodeTegc() {
		return codeTegc;
	}

	public void setCodeTegc(String codeTegc) {
		this.codeTegc = codeTegc;
	}

	public Integer getNbre() {
		return nbre;
	}

	public void setNbre(Integer nbre) {
		this.nbre = nbre;
	}

	public Integer getSerie() {
		return serie;
	}

	public void setSerie(Integer serie) {
		this.serie = serie;
	}

	public String getTypeOpi() {
		return typeOpi;
	}

	public void setTypeOpi(String typeOpi) {
		this.typeOpi = typeOpi;
	}

	public double getMontTranche1() {
		return montTranche1;
	}

	public void setMontTranche1(double montTranche1) {
		this.montTranche1 = montTranche1;
	}

	public int getDiferre() {
		return diferre;
	}

	public void setDiferre(int diferre) {
		this.diferre = diferre;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public String getTypeGcp() {
		return typeGcp;
	}

	public void setTypeGcp(String typeGcp) {
		this.typeGcp = typeGcp;
	}

	public Integer getMarge() {
		return marge;
	}

	public void setMarge(Integer marge) {
		this.marge = marge;
	}

	public Integer getTtc() {
		return ttc;
	}

	public void setTtc(Integer ttc) {
		this.ttc = ttc;
	}

	public float getTauxTva() {
		return tauxTva;
	}

	public void setTauxTva(float tauxTva) {
		this.tauxTva = tauxTva;
	}

}

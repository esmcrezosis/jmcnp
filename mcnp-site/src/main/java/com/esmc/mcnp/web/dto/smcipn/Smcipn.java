package com.esmc.mcnp.web.dto.smcipn;

public class Smcipn {
	private String codeMembre;
	private String codeTe;
	private String codeMembreSmcipn;
	private String typeProduit;
	private String numAppelOffre;
	private String nomAppelOffre;
	private String description;
	private int duree;
	private String type;
	private String typePaiement;
	private String typeCommission;
	private boolean pbsDisponible;
	private double montant;
	private String typeActivation;
	private String numDoc;
	private Integer ttc;
	private Integer marge;
	private Float tauxTva;
	private String modePaiement;
	private String numeroCompte;
	private Integer nbreOpi;

	public Smcipn() {
	}

	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	public String getCodeTe() {
		return codeTe;
	}

	public void setCodeTe(String codeTe) {
		this.codeTe = codeTe;
	}

	public String getNumAppelOffre() {
		return numAppelOffre;
	}

	public void setNumAppelOffre(String numAppelOffre) {
		this.numAppelOffre = numAppelOffre;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getTypePaiement() {
		return typePaiement;
	}

	public void setTypePaiement(String typePaiement) {
		this.typePaiement = typePaiement;
	}

	public String getTypeCommission() {
		return typeCommission;
	}

	public void setTypeCommission(String typeCommission) {
		this.typeCommission = typeCommission;
	}

	public boolean isPbsDisponible() {
		return pbsDisponible;
	}

	public void setPbsDisponible(boolean pbsDisponible) {
		this.pbsDisponible = pbsDisponible;
	}

	public String getNomAppelOffre() {
		return nomAppelOffre;
	}

	public void setNomAppelOffre(String nomAppelOffre) {
		this.nomAppelOffre = nomAppelOffre;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public String getTypeActivation() {
		return typeActivation;
	}

	public void setTypeActivation(String typeActivation) {
		this.typeActivation = typeActivation;
	}

	public String getCodeMembreSmcipn() {
		return codeMembreSmcipn;
	}

	public void setCodeMembreSmcipn(String codeMembreSmcipn) {
		this.codeMembreSmcipn = codeMembreSmcipn;
	}

	public String getTypeProduit() {
		return typeProduit;
	}

	public void setTypeProduit(String typeProduit) {
		this.typeProduit = typeProduit;
	}

	public String getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}

	public Integer getTtc() {
		return ttc;
	}

	public void setTtc(Integer ttc) {
		this.ttc = ttc;
	}

	public Integer getMarge() {
		return marge;
	}

	public void setMarge(Integer marge) {
		this.marge = marge;
	}

	public Float getTauxTva() {
		return tauxTva;
	}

	public void setTauxTva(Float tauxTva) {
		this.tauxTva = tauxTva;
	}

	public String getModePaiement() {
		return modePaiement;
	}

	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}

	public String getNumeroCompte() {
		return numeroCompte;
	}

	public void setNumeroCompte(String numeroCompte) {
		this.numeroCompte = numeroCompte;
	}

	public Integer getNbreOpi() {
		return nbreOpi;
	}

	public void setNbreOpi(Integer nbreOpi) {
		this.nbreOpi = nbreOpi;
	}

}

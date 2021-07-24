package com.esmc.mcnp.web.dto.bon;

public class BonConso {

    private String codeMembre;
    private String typeBonConso;
    private String catBonConso;
    private String typeRecurrent;
    private String typeNonRecurrent;
    private String typeProduit;
    private double duree;
    private double prk;
    private double montant;
    private double montBonconso;
    private String codeBon;
    private String typeCredit;

    public BonConso() {
    }

    public BonConso(String codeMembre, String typeBonConso, String catBonConso, String codeBon, Double montant) {
        this.codeMembre = codeMembre;
        this.typeBonConso = typeBonConso;
        this.catBonConso = catBonConso;
        this.montant = montant;
        this.codeBon = codeBon;
    }

    public BonConso(String codeMembre, String typeBonConso, String catBonConso, String codeBon, Double prk,
            Double montant) {
        this.codeMembre = codeMembre;
        this.typeBonConso = typeBonConso;
        this.catBonConso = catBonConso;
        this.prk = prk;
        this.montant = montant;
        this.codeBon = codeBon;
    }

    public String getCodeMembre() {
        return codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    public String getTypeBonConso() {
        return typeBonConso;
    }

    public void setTypeBonConso(String typeBonConso) {
        this.typeBonConso = typeBonConso;
    }

    public String getCatBonConso() {
        return catBonConso;
    }

    public void setCatBonConso(String catBonConso) {
        this.catBonConso = catBonConso;
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

    public double getDuree() {
        return duree;
    }

    public void setDuree(double duree) {
        this.duree = duree;
    }

    public Double getPrk() {
        return prk;
    }

    public void setPrk(Double prk) {
        this.prk = prk;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Double getMontBonconso() {
        return montBonconso;
    }

    public void setMontBonconso(Double montBonconso) {
        this.montBonconso = montBonconso;
    }

    public String getCodeBon() {
        return codeBon;
    }

    public void setCodeBon(String codeBon) {
        this.codeBon = codeBon;
    }

    public String getTypeNonRecurrent() {
        return typeNonRecurrent;
    }

    public void setTypeNonRecurrent(String typeNonRecurrent) {
        this.typeNonRecurrent = typeNonRecurrent;
    }

	public String getTypeCredit() {
		return typeCredit;
	}

	public void setTypeCredit(String typeCredit) {
		this.typeCredit = typeCredit;
	}

}

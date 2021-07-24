package com.esmc.mcnp.web.mvc.dto;

public class PaiementDto {

    private String codeMembre;
    private String numeroBon;
    private String typeBa;
    private String codeBanque;
    private double montant;
    private Integer nbre;
    private Integer serie;

    public PaiementDto() {
    }

    public PaiementDto(String codeMembre, String numeroBon, String typeBa, String codeBanque, double montant) {
        this.codeMembre = codeMembre;
        this.numeroBon = numeroBon;
        this.typeBa = typeBa;
        this.codeBanque = codeBanque;
        this.montant = montant;
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

    public String getCodeMembre() {
        return codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    public String getNumeroBon() {
        return numeroBon;
    }

    public void setNumeroBon(String numeroBon) {
        this.numeroBon = numeroBon;
    }

    public String getTypeBa() {
        return typeBa;
    }

    public void setTypeBa(String typeBa) {
        this.typeBa = typeBa;
    }

    public String getCodeBanque() {
        return codeBanque;
    }

    public void setCodeBanque(String codeBanque) {
        this.codeBanque = codeBanque;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

}

package com.esmc.mcnp.dto.acteur;

import java.io.Serializable;
import java.math.BigDecimal;

public class Consultation implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5961865861583088354L;
    private String typeConsultation;
    private String methodeConsultation;
    private String codeMembre;
    private String codeCompte;
    private String numeroCarte;
    private String nomMembre;
    private String prenomMembre;
    private String raisonSociale;
    private String codeTypeCredit;
    private String codebar;
    private String immatbenef;
    private BigDecimal idCredit;
    
    public Consultation() {
        // TODO Auto-generated constructor stub
    }

    public String getCodeMembre() {
        return codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    public String getCodeCompte() {
        return codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }

    public String getTypeConsultation() {
        return typeConsultation;
    }

    public void setTypeConsultation(String typeConsultation) {
        this.typeConsultation = typeConsultation;
    }

    public String getNumeroCarte() {
        return numeroCarte;
    }

    public void setNumeroCarte(String numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    public String getNomMembre() {
        return nomMembre;
    }

    public void setNomMembre(String nomMembre) {
        this.nomMembre = nomMembre;
    }

    public String getPrenomMembre() {
        return prenomMembre;
    }

    public void setPrenomMembre(String prenomMembre) {
        this.prenomMembre = prenomMembre;
    }

    public String getMethodeConsultation() {
        return methodeConsultation;
    }

    public void setMethodeConsultation(String methodeConsultation) {
        this.methodeConsultation = methodeConsultation;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getCodeTypeCredit() {
        return codeTypeCredit;
    }

    public void setCodeTypeCredit(String codeTypeCredit) {
        this.codeTypeCredit = codeTypeCredit;
    }

    public String getCodebar() {
        return codebar;
    }

    public void setCodebar(String codebar) {
        this.codebar = codebar;
    }

    public String getImmatbenef() {
        return immatbenef;
    }

    public void setImmatbenef(String immatbenef) {
        this.immatbenef = immatbenef;
    }

    public BigDecimal getIdCredit() {
        return idCredit;
    }

    public void setIdCredit(BigDecimal idCredit) {
        this.idCredit = idCredit;
    }

}

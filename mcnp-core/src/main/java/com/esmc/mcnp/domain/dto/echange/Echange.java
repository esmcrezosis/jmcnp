/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dto.echange;

import java.io.Serializable;
import java.util.List;

import com.esmc.mcnp.dto.bc.Credit;

/**
 *
 * @author USER
 */
public class Echange implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private String typeEchange;
    private String creditEchanger;
    private String creditObtenir;
    private String codeCompte;
    private double montant;
    private String codeMembreRPG;
    private String codeProduit;
    private Long IdUtilisateur;
    private String numAppelOffre;
    private transient List<Credit> credits;

    public String getTypeEchange() {
        return typeEchange;
    }

    public String getCodeMembreRPG() {
        return codeMembreRPG;
    }

    public void setCodeMembreRPG(String codeMembreRPG) {
        this.codeMembreRPG = codeMembreRPG;
    }

    public void setTypeEchange(String typeEchange) {
        this.typeEchange = typeEchange;
    }

    public String getCreditEchanger() {
        return creditEchanger;
    }

    public void setCreditEchanger(String creditEchanger) {
        this.creditEchanger = creditEchanger;
    }

    public String getCreditObtenir() {
        return creditObtenir;
    }

    public void setCreditObtenir(String creditObtenir) {
        this.creditObtenir = creditObtenir;
    }

    public String getCodeCompte() {
        return codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

    public Long getIdUtilisateur() {
        return IdUtilisateur;
    }

    public void setIdUtilisateur(Long IdUtilisateur) {
        this.IdUtilisateur = IdUtilisateur;
    }

    public String getNumAppelOffre() {
        return numAppelOffre;
    }

    public void setNumAppelOffre(String numAppelOffre) {
        this.numAppelOffre = numAppelOffre;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }
}

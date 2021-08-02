/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.domain.dto.echange;

import java.io.Serializable;

/**
 *
 * @author USER
 */
@SuppressWarnings("serial")
public class OffreDemande implements Serializable{
    private String num_offre_demande;
    private String typeCreditOf;
    private String typeCreditDe;
    private double montant_echange;
    private String codeCompteOf;
    private String codeCompteDe;

    public OffreDemande() {
    }

    public OffreDemande(String num_offre_demande, String typeCreditOf, String typeCreditDe, double montant_echange, String codeCompteOf, String codeCompteDe) {
        this.num_offre_demande = num_offre_demande;
        this.typeCreditOf = typeCreditOf;
        this.typeCreditDe = typeCreditDe;
        this.montant_echange = montant_echange;
        this.codeCompteOf = codeCompteOf;
        this.codeCompteDe = codeCompteDe;
    }

    public String getNum_offre_demande() {
        return num_offre_demande;
    }

    public void setNum_offre_demande(String num_offre_demande) {
        this.num_offre_demande = num_offre_demande;
    }

    public String getTypeCreditOf() {
        return typeCreditOf;
    }

    public void setTypeCreditOf(String typeCreditOf) {
        this.typeCreditOf = typeCreditOf;
    }

    public String getTypeCreditDe() {
        return typeCreditDe;
    }

    public void setTypeCreditDe(String typeCreditDe) {
        this.typeCreditDe = typeCreditDe;
    }

    public double getMontant_echange() {
        return montant_echange;
    }

    public void setMontant_echange(double montant_echange) {
        this.montant_echange = montant_echange;
    }

    public String getCodeCompteOf() {
        return codeCompteOf;
    }

    public void setCodeCompteOf(String codeCompteOf) {
        this.codeCompteOf = codeCompteOf;
    }

    public String getCodeCompteDe() {
        return codeCompteDe;
    }

    public void setCodeCompteDe(String codeCompteDe) {
        this.codeCompteDe = codeCompteDe;
    }
    
    
}

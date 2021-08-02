/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.domain.dto.desendettement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.esmc.mcnp.domain.dto.echange.Escompte;

/**
 *
 * @author USER
 */
public class Compensation implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codeCompte;
    private String codeComptepbf;
    private double montant;
    private float tx_marge;
    private String typeCompensation;
    private List<Escompte> escomptes;
    private int typeOp;

    public Compensation() {
    }

    public String getCodeCompte() {
        return codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }

    public String getCodeComptepbf() {
        return codeComptepbf;
    }

    public void setCodeComptepbf(String codeComptepbf) {
        this.codeComptepbf = codeComptepbf;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public float getTx_marge() {
        return tx_marge;
    }

    public void setTx_marge(float tx_marge) {
        this.tx_marge = tx_marge;
    }

    public String getTypeCompensation() {
        return typeCompensation;
    }

    public void setTypeCompensation(String typeCompensation) {
        this.typeCompensation = typeCompensation;
    }

    public int getTypeOp() {
        return typeOp;
    }

    public void setTypeOp(int typeOp) {
        this.typeOp = typeOp;
    }

    public void setEscomptes(List<Escompte> escomptes) {
        this.escomptes = escomptes;
    }

    public List<Escompte> getEscomptes() {
        if (escomptes == null) {
            escomptes = new ArrayList<>();
        }
        return escomptes;
    }

}

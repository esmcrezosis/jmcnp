/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dto.smcipn;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class Transfert implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codeCompteTransfert;
    private String codeCompteDest;
    private double montTransfert;
    private String typeTransfert;
    private String numeroAppel;

    public String getCodeCompteTransfert() {
        return codeCompteTransfert;
    }

    public void setCodeCompteTransfert(String codeCompteTransfert) {
        this.codeCompteTransfert = codeCompteTransfert;
    }

    public String getCodeCompteDest() {
        return codeCompteDest;
    }

    public void setCodeCompteDest(String codeCompteDest) {
        this.codeCompteDest = codeCompteDest;
    }

    public double getMontTransfert() {
        return montTransfert;
    }

    public void setMontTransfert(double montTransfert) {
        this.montTransfert = montTransfert;
    }

    public String getTypeTransfert() {
        return typeTransfert;
    }

    public void setTypeTransfert(String typeTransfert) {
        this.typeTransfert = typeTransfert;
    }

    public String getNumeroAppel() {
        return numeroAppel;
    }

    public void setNumeroAppel(String numeroAppel) {
        this.numeroAppel = numeroAppel;
    }

}

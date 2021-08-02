/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.domain.dto.bn;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class Nn implements Serializable {

    private static final long serialVersionUID = 1L;
    private String typeNn;
    private double montant;

    public Nn() {
    }

    public Nn(String typeNn, double montant) {
        this.typeNn = typeNn;
        this.montant = montant;
    }

    public String getTypeNn() {
        return typeNn;
    }

    public void setTypeNn(String typeNn) {
        this.typeNn = typeNn;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

}

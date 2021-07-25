/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dto.other;

import java.io.Serializable;

/**
 *
 * @author Administrateur
 */
public class Cour implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codeCours;
    private double valDevFin;
    private double valDevInit;

    public Cour() {
    }

    public String getCodeCours() {
        return codeCours;
    }

    public void setCodeCours(String codeCours) {
        this.codeCours = codeCours;
    }

    public double getValDevFin() {
        return valDevFin;
    }

    public void setValDevFin(double valDevFin) {
        this.valDevFin = valDevFin;
    }

    public double getValDevInit() {
        return valDevInit;
    }

    public void setValDevInit(double valDevInit) {
        this.valDevInit = valDevInit;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dto.acteur;

import java.io.Serializable;

/**
 *
 * @author Administrateur
 */
public class Filiere implements Serializable {

    private static final long serialVersionUID = 1L;
    private long idFiliere;
    private String nomFiliere;

    public Filiere() {
    }

    public long getIdFiliere() {
        return idFiliere;
    }

    public void setIdFiliere(long idFiliere) {
        this.idFiliere = idFiliere;
    }

    public String getNomFiliere() {
        return nomFiliere;
    }

    public void setNomFiliere(String nomFiliere) {
        this.nomFiliere = nomFiliere;
    }

}

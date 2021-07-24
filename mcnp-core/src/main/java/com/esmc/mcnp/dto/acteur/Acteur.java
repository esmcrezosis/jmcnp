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
public class Acteur implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idActeur;
    private String typeActeur;
    private String codeActivite;
    

    public Acteur() {
    }

    public Long getIdActeur() {
        return idActeur;
    }

    public void setIdActeur(Long idActeur) {
        this.idActeur = idActeur;
    }

    
    public String getCodeActivite() {
        return codeActivite;
    }

    public void setCodeActivite(String codeActivite) {
        this.codeActivite = codeActivite;
    }

    public String getTypeActeur() {
        return typeActeur;
    }

    public void setTypeActeur(String typeActeur) {
        this.typeActeur = typeActeur;
    }

  


}

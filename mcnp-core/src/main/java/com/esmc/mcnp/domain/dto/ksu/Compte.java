/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dto.ksu;

import java.io.Serializable;

import com.esmc.mcnp.dto.acteur.Membre;
import com.esmc.mcnp.dto.acteur.MembreMorale;

/**
 *
 * @author Mawuli
 */
@SuppressWarnings("serial")
public class Compte implements Serializable {

    private String codeCompte;
    private Membre membre;
    private MembreMorale morale;
    private double solde;
    private String codeCategorie;

    public Compte() {
    }

    public Compte(String codeCompte, Membre membre, double solde) {
        this.codeCompte = codeCompte;
        this.membre = membre;
        this.solde = solde;
    }

    public Compte(String codeCompte, MembreMorale morale, double solde) {
        this.codeCompte = codeCompte;
        this.morale = morale;
        this.solde = solde;
    }

    public String getCodeCompte() {
        return codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public MembreMorale getMorale() {
        return morale;
    }

    public void setMorale(MembreMorale morale) {
        this.morale = morale;
    }

    public String getCodeCategorie() {
        return codeCategorie;
    }

    public void setCodeCategorie(String codeCategorie) {
        this.codeCategorie = codeCategorie;
    }

}

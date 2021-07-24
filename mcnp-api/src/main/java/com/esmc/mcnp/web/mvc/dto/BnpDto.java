package com.esmc.mcnp.web.mvc.dto;

/**
 * Created by HP on 30/06/2017.
 */
public class BnpDto {
    private String codeMembreApp;
    private String codeMembreBenef;
    private String typeBnp;
    private String typeRecurrent;
    private boolean formation;
    private int duree;
    private String cycle;
    private double montant;
    private double montBonconso;

    public BnpDto() {
    }

    public String getCodeMembreApp() {
        return codeMembreApp;
    }

    public void setCodeMembreApp(String codeMembreApp) {
        this.codeMembreApp = codeMembreApp;
    }

    public String getCodeMembreBenef() {
        return codeMembreBenef;
    }

    public void setCodeMembreBenef(String codeMembreBenef) {
        this.codeMembreBenef = codeMembreBenef;
    }

    public String getTypeBnp() {
        return typeBnp;
    }

    public void setTypeBnp(String typeBnp) {
        this.typeBnp = typeBnp;
    }

    public String getTypeRecurrent() {
        return typeRecurrent;
    }

    public void setTypeRecurrent(String typeRecurrent) {
        this.typeRecurrent = typeRecurrent;
    }

    public boolean isFormation() {
        return formation;
    }

    public void setFormation(boolean formation) {
        this.formation = formation;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public double getMontBonconso() {
        return montBonconso;
    }

    public void setMontBonconso(double montBonconso) {
        this.montBonconso = montBonconso;
    }
}

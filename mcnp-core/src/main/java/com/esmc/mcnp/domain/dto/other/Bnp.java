package com.esmc.mcnp.domain.dto.other;

import java.io.Serializable;

public class Bnp implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String typeBnp;
    private String natureBnp;
    private String codeMembreApp;
    private String codeMembreBenef;
    private String codeTeApp;
    private String modeApa;
    private double montBnp;
    private double montBonconso;
    private String codeBnp;
    private String typeProduit;
    private String typeRecurrent;
    private boolean formation;
    private String cycle;
    private Integer duree;

    public Bnp() {
    }

    public Bnp(String typeBnp, String natureBnp, String codeMembreApp, String codeMembreBenef, String modeApa,
            double montBnp) {
        this.typeBnp = typeBnp;
        this.natureBnp = natureBnp;
        this.codeMembreApp = codeMembreApp;
        this.codeMembreBenef = codeMembreBenef;
        this.modeApa = modeApa;
        this.montBnp = montBnp;
    }

    /**
     * @param typeBnp
     * @param natureBnp
     * @param codeMembreApp
     * @param codeMembreBenef
     * @param modeApa
     * @param montBnp
     * @param codeBnp
     */
    public Bnp(String typeBnp, String natureBnp, String codeMembreApp, String codeMembreBenef, String modeApa,
            double montBnp, String codeBnp) {
        this.typeBnp = typeBnp;
        this.natureBnp = natureBnp;
        this.codeMembreApp = codeMembreApp;
        this.codeMembreBenef = codeMembreBenef;
        this.modeApa = modeApa;
        this.montBnp = montBnp;
        this.codeBnp = codeBnp;
    }

    public String getTypeBnp() {
        return typeBnp;
    }

    public void setTypeBnp(String typeBnp) {
        this.typeBnp = typeBnp;
    }

    public String getNatureBnp() {
        return natureBnp;
    }

    public void setNatureBnp(String natureBnp) {
        this.natureBnp = natureBnp;
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

    public String getCodeTeApp() {
        return codeTeApp;
    }

    public void setCodeTeApp(String codeTeApp) {
        this.codeTeApp = codeTeApp;
    }

    public String getModeApa() {
        return modeApa;
    }

    public void setModeApa(String modeApa) {
        this.modeApa = modeApa;
    }

    public double getMontBnp() {
        return montBnp;
    }

    public void setMontBnp(double montBnp) {
        this.montBnp = montBnp;
    }

    public double getMontBonconso() {
        return montBonconso;
    }

    public void setMontBonconso(double montBonconso) {
        this.montBonconso = montBonconso;
    }

    public String getCodeBnp() {
        return codeBnp;
    }

    public void setCodeBnp(String codeBnp) {
        this.codeBnp = codeBnp;
    }

    public String getTypeProduit() {
        return typeProduit;
    }

    public void setTypeProduit(String typeProduit) {
        this.typeProduit = typeProduit;
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

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.domain.dto.obps;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author USER
 */
public class TpaGcpView implements Serializable {

    private static final long serialVersionUID = 1L;
    private long idTpagcp;
    private String codeCompte;
    private Double montGcp;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalDate dateDebTranche;
    private LocalDate dateFinTranche;
    private Double montTranche;
    private Integer resteNtf;
    private Double solde;

    public long getIdTpagcp() {
        return idTpagcp;
    }

    public void setIdTpagcp(long idTpagcp) {
        this.idTpagcp = idTpagcp;
    }

    public String getCodeCompte() {
        return codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }

    public Double getMontGcp() {
        return montGcp;
    }

    public void setMontGcp(Double montGcp) {
        this.montGcp = montGcp;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public LocalDate getDateDebTranche() {
        return dateDebTranche;
    }

    public void setDateDebTranche(LocalDate dateDebTranche) {
        this.dateDebTranche = dateDebTranche;
    }

    public LocalDate getDateFinTranche() {
        return dateFinTranche;
    }

    public void setDateFinTranche(LocalDate dateFinTranche) {
        this.dateFinTranche = dateFinTranche;
    }

    public Double getMontTranche() {
        return montTranche;
    }

    public void setMontTranche(Double montTranche) {
        this.montTranche = montTranche;
    }

    public Integer getResteNtf() {
        return resteNtf;
    }

    public void setResteNtf(Integer resteNtf) {
        this.resteNtf = resteNtf;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

}

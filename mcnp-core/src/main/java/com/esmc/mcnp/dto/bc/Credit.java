/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dto.bc;

import java.util.Date;

/**
 *
 * @author USER
 */
public class Credit {

    private Long idCredit;
    private Double montCredit;
    private Date dateFin;

    public Credit(Long idCredit, Double montCredit) {
        this.idCredit = idCredit;
        this.montCredit = montCredit;
    }

    public Credit(Long idCredit, Double montCredit, Date dateFin) {
        this.idCredit = idCredit;
        this.montCredit = montCredit;
        this.dateFin = dateFin;
    }

    public Long getIdCredit() {
        return idCredit;
    }

    public void setIdCredit(Long idCredit) {
        this.idCredit = idCredit;
    }

    public Double getMontCredit() {
        return montCredit;
    }

    public void setMontCredit(Double montCredit) {
        this.montCredit = montCredit;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

}

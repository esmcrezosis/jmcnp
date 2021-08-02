/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.domain.dto.echange;

import java.io.Serializable;

/**
 *
 * @author Administrateur
 */
public class DetailDomicilie implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer dureeRenouvellement;
    private Double montantCredit;
    private Long idCredit;

    public DetailDomicilie() {
    }

    public Integer getDureeRenouvellement() {
        return dureeRenouvellement;
    }

    public void setDureeRenouvellement(Integer dureeRenouvellement) {
        this.dureeRenouvellement = dureeRenouvellement;
    }

    public Double getMontantCredit() {
        return montantCredit;
    }

    public void setMontantCredit(Double montantCredit) {
        this.montantCredit = montantCredit;
    }

    public Long getIdCredit() {
        return idCredit;
    }

    public void setIdCredit(Long idCredit) {
        this.idCredit = idCredit;
    }
    

}

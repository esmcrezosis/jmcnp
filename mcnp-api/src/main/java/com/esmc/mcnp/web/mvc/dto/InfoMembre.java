/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.web.mvc.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import com.esmc.mcnp.domain.entity.cm.EuMembre;

/**
 *
 * @author HP
 */
public class InfoMembre implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String codeMembre;
    private String nomMembre;
    private String prenomMembre;
    private Date dateNaissance;
    private String lieuNaissance;
    private String telephone;
    private boolean autoriser;
    private String email;
    private String typeRep;
    private LocalDate dateDemande;
    private boolean doublon;

    public InfoMembre() {
    }

    public InfoMembre(String codeMembre, String nomMembre, String prenomMembre, Date dateNaissance,
            String lieuNaissance, String telephone, String email) {
        this.codeMembre = codeMembre;
        this.nomMembre = nomMembre;
        this.prenomMembre = prenomMembre;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.telephone = telephone;
        this.email = email;
    }

    public String getCodeMembre() {
        return codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    public String getNomMembre() {
        return nomMembre;
    }

    public void setNomMembre(String nomMembre) {
        this.nomMembre = nomMembre;
    }

    public String getPrenomMembre() {
        return prenomMembre;
    }

    public void setPrenomMembre(String prenomMembre) {
        this.prenomMembre = prenomMembre;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

	public boolean isAutoriser() {
        return autoriser;
    }

    public void setAutoriser(boolean autoriser) {
        this.autoriser = autoriser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTypeRep() {
		return typeRep;
	}

	public void setTypeRep(String typeRep) {
		this.typeRep = typeRep;
	}

    public LocalDate getDateDemande() {
		return dateDemande;
	}

	public void setDateDemande(LocalDate dateDemande) {
		this.dateDemande = dateDemande;
	}

	public boolean isDoublon() {
		return doublon;
	}

	public void setDoublon(boolean doublon) {
		this.doublon = doublon;
	}

	public static InfoMembre mapFromEuMembre(EuMembre membre) {
        if (Objects.nonNull(membre)) {
            return new InfoMembre(membre.getCodeMembre(), membre.getNomMembre(), membre.getPrenomMembre(),
                    membre.getDateNaisMembre(), membre.getLieuNaisMembre(), membre.getPortableMembre(),
                    membre.getEmailMembre());
        } else {
            return null;
        }
    }

}

package com.esmc.mcnp.model.pc;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the eu_releve database table.
 */
@Entity
@Table(name = "eu_releve")
@DynamicUpdate
@NamedQuery(name = "EuReleve.findAll", query = "SELECT e FROM EuReleve e")
public class EuReleve implements Serializable {

    private static final long serialVersionUID = 1L;
    private int releveId;
    private Integer publier;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date releveDate;
    private String releveFichier;
    private String releveMembre;
    private String releveType;
    private String newCodeMembre;
    private Long utilisateur;
    private Boolean traiter;

    public EuReleve() {
    }

    @Id
    @Column(name = "releve_id")
    public int getReleveId() {
        return this.releveId;
    }

    public void setReleveId(int releveId) {
        this.releveId = releveId;
    }

    public Integer getPublier() {
        return this.publier;
    }

    public void setPublier(Integer publier) {
        this.publier = publier;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "releve_date")
    public Date getReleveDate() {
        return this.releveDate;
    }

    public void setReleveDate(Date releveDate) {
        this.releveDate = releveDate;
    }

    @Column(name = "releve_fichier")
    public String getReleveFichier() {
        return this.releveFichier;
    }

    public void setReleveFichier(String releveFichier) {
        this.releveFichier = releveFichier;
    }

    @Column(name = "releve_membre")
    public String getReleveMembre() {
        return this.releveMembre;
    }

    public void setReleveMembre(String releveMembre) {
        this.releveMembre = releveMembre;
    }

    @Column(name = "releve_type")
    public String getReleveType() {
        return this.releveType;
    }

    public void setReleveType(String releveType) {
        this.releveType = releveType;
    }

    @Column(name = "new_code_membre")
    public String getNewCodeMembre() {
        return newCodeMembre;
    }

    public void setNewCodeMembre(String newCodeMembre) {
        this.newCodeMembre = newCodeMembre;
    }

    @Column(name = "utilisateur")
    public Long getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Long utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Column(name = "traiter")
    public Boolean getTraiter() {
        return traiter;
    }

    public void setTraiter(Boolean traiter) {
        this.traiter = traiter;
    }

}

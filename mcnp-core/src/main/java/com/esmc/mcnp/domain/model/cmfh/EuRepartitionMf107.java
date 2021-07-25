package com.esmc.mcnp.model.cmfh;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the eu_repartition_mf107 database table.
 *
 */
@Entity
@Table(name = "eu_repartition_mf107")
@NamedQuery(name = "EuRepartitionMf107.findAll", query = "SELECT e FROM EuRepartitionMf107 e")
public class EuRepartitionMf107 implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idRep;
    private String codeMembre;
    private Date dateRep;
    private Long idMf107;
    private Long idUtilisateur;
    private double montReglt;
    private double montRep;
    private Integer payer;
    private double soldeRep;

    public EuRepartitionMf107() {
    }

    @Id
    @Column(name = "id_rep", unique = true, nullable = false)
    public Long getIdRep() {
        return this.idRep;
    }

    public void setIdRep(Long idRep) {
        this.idRep = idRep;
    }

    @Column(name = "code_membre", length = 30)
    public String getCodeMembre() {
        return this.codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_rep")
    public Date getDateRep() {
        return this.dateRep;
    }

    public void setDateRep(Date dateRep) {
        this.dateRep = dateRep;
    }

    @Column(name = "id_mf107")
    public Long getIdMf107() {
        return this.idMf107;
    }

    public void setIdMf107(Long idMf107) {
        this.idMf107 = idMf107;
    }

    @Column(name = "id_utilisateur")
    public Long getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Column(name = "mont_reglt")
    public double getMontReglt() {
        return this.montReglt;
    }

    public void setMontReglt(double montReglt) {
        this.montReglt = montReglt;
    }

    @Column(name = "mont_rep")
    public double getMontRep() {
        return this.montRep;
    }

    public void setMontRep(double montRep) {
        this.montRep = montRep;
    }

    public Integer getPayer() {
        return this.payer;
    }

    public void setPayer(Integer payer) {
        this.payer = payer;
    }

    @Column(name = "solde_rep")
    public double getSoldeRep() {
        return this.soldeRep;
    }

    public void setSoldeRep(double soldeRep) {
        this.soldeRep = soldeRep;
    }

}

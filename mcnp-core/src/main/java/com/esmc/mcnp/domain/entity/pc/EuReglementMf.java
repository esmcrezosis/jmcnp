package com.esmc.mcnp.domain.entity.pc;

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
 * The persistent class for the eu_reglement_mf database table.
 *
 */
@Entity
@Table(name = "eu_reglement_mf")
@NamedQuery(name = "EuReglementMf.findAll", query = "SELECT e FROM EuReglementMf e")
public class EuReglementMf implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idRegltMf;
    private String codeMembre;
    private Date dateRegltMf;
    private Long idUtilisateur;
    private double montRegltMf;

    public EuReglementMf() {
    }

    @Id
    @Column(name = "id_reglt_mf", unique = true, nullable = false, length = 6)
    public Long getIdRegltMf() {
        return this.idRegltMf;
    }

    public void setIdRegltMf(Long idRegltMf) {
        this.idRegltMf = idRegltMf;
    }

    @Column(name = "code_membre", length = 100)
    public String getCodeMembre() {
        return this.codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_reglt_mf")
    public Date getDateRegltMf() {
        return this.dateRegltMf;
    }

    public void setDateRegltMf(Date dateRegltMf) {
        this.dateRegltMf = dateRegltMf;
    }

    @Column(name = "id_utilisateur", length = 12)
    public Long getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Column(name = "mont_reglt_mf")
    public double getMontRegltMf() {
        return this.montRegltMf;
    }

    public void setMontRegltMf(double montRegltMf) {
        this.montRegltMf = montRegltMf;
    }

}

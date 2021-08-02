package com.esmc.mcnp.domain.entity.op;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the eu_repartition_nn database table.
 *
 */
@Entity
@Table(name = "eu_repartition_nn")
@NamedQuery(name = "EuRepartitionNn.findAll", query = "SELECT e FROM EuRepartitionNn e")
public class EuRepartitionNn implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idRepNn;
    private Date dateRep;
    private Long idUtilisateur;
    private double montMarge;
    private double montRep;
    private EuDetailAppelNn euDetailAppelNn;

    public EuRepartitionNn() {
    }

    @Id
    @Column(name = "id_rep_nn", unique = true, nullable = false)
    public Long getIdRepNn() {
        return this.idRepNn;
    }

    public void setIdRepNn(Long idRepNn) {
        this.idRepNn = idRepNn;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_rep")
    public Date getDateRep() {
        return this.dateRep;
    }

    public void setDateRep(Date dateRep) {
        this.dateRep = dateRep;
    }

    @Column(name = "id_utilisateur")
    public Long getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Column(name = "mont_marge")
    public double getMontMarge() {
        return this.montMarge;
    }

    public void setMontMarge(double montMarge) {
        this.montMarge = montMarge;
    }

    @Column(name = "mont_rep")
    public double getMontRep() {
        return this.montRep;
    }

    public void setMontRep(double montRep) {
        this.montRep = montRep;
    }

    //bi-directional many-to-one association to EuDetailAppelNn
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_detail_appel_nn")
    public EuDetailAppelNn getEuDetailAppelNn() {
        return this.euDetailAppelNn;
    }

    public void setEuDetailAppelNn(EuDetailAppelNn euDetailAppelNn) {
        this.euDetailAppelNn = euDetailAppelNn;
    }

}

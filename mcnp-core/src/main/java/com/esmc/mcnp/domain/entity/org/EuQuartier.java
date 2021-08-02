package com.esmc.mcnp.domain.entity.org;

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

import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

/**
 * The persistent class for the eu_quartier database table.
 *
 */
@Entity
@Table(name = "eu_quartier")
@NamedQuery(name = "EuQuartier.findAll", query = "SELECT e FROM EuQuartier e")
public class EuQuartier implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idQuartier;
    private Date dateCreate;
    private String libQuartier;
    private EuVille euVille;
    private EuUtilisateur euUtilisateur;

    public EuQuartier() {
    }

    @Id
    @Column(name = "id_quartier", unique = true, nullable = false)
    public Integer getIdQuartier() {
        return this.idQuartier;
    }

    public void setIdQuartier(Integer idQuartier) {
        this.idQuartier = idQuartier;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_create")
    public Date getDateCreate() {
        return this.dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    @Column(name = "lib_quartier", length = 75)
    public String getLibQuartier() {
        return this.libQuartier;
    }

    public void setLibQuartier(String libQuartier) {
        this.libQuartier = libQuartier;
    }

    //bi-directional many-to-one association to EuVille
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ville")
    public EuVille getEuVille() {
        return this.euVille;
    }

    public void setEuVille(EuVille euVille) {
        this.euVille = euVille;
    }

    //bi-directional many-to-one association to EuUtilisateur
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur")
    public EuUtilisateur getEuUtilisateur() {
        return this.euUtilisateur;
    }

    public void setEuUtilisateur(EuUtilisateur euUtilisateur) {
        this.euUtilisateur = euUtilisateur;
    }

}

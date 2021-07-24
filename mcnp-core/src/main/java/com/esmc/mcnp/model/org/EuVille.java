package com.esmc.mcnp.model.org;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esmc.mcnp.model.security.EuUtilisateur;

/**
 * The persistent class for the eu_ville database table.
 *
 */
@Entity
@Table(name = "eu_ville")
@NamedQuery(name = "EuVille.findAll", query = "SELECT e FROM EuVille e")
public class EuVille implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idVille;
    private Date dateCreate;
    private String libVille;
    private List<EuQuartier> euQuartiers;
    private EuPays euPay;
    private EuUtilisateur euUtilisateur;

    public EuVille() {
    }

    @Id
    @Column(name = "id_ville", unique = true, nullable = false)
    public Long getIdVille() {
        return this.idVille;
    }

    public void setIdVille(Long idVille) {
        this.idVille = idVille;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_create")
    public Date getDateCreate() {
        return this.dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    @Column(name = "lib_ville", length = 75)
    public String getLibVille() {
        return this.libVille;
    }

    public void setLibVille(String libVille) {
        this.libVille = libVille;
    }

    //bi-directional many-to-one association to EuQuartier
    @OneToMany(mappedBy = "euVille")
    public List<EuQuartier> getEuQuartiers() {
        return this.euQuartiers;
    }

    public void setEuQuartiers(List<EuQuartier> euQuartiers) {
        this.euQuartiers = euQuartiers;
    }

    public EuQuartier addEuQuartier(EuQuartier euQuartier) {
        getEuQuartiers().add(euQuartier);
        euQuartier.setEuVille(this);

        return euQuartier;
    }

    public EuQuartier removeEuQuartier(EuQuartier euQuartier) {
        getEuQuartiers().remove(euQuartier);
        euQuartier.setEuVille(null);

        return euQuartier;
    }

    //bi-directional many-to-one association to EuPays
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pays")
    public EuPays getEuPay() {
        return this.euPay;
    }

    public void setEuPay(EuPays euPay) {
        this.euPay = euPay;
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

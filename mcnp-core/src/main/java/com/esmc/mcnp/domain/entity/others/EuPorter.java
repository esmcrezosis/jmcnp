package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_porter database table.
 *
 */
@Entity
@Table(name = "eu_porter")
@NamedQuery(name = "EuPorter.findAll", query = "SELECT e FROM EuPorter e")
public class EuPorter implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idPorter;
    private Integer disponible;
    private Integer mdv;
    private double puObjet;
    private Integer qteObjet;
    private double remise;
    private EuObjet euObjet;
    private EuProforma euProforma;

    public EuPorter() {
    }

    @Id
    @Column(name = "id_porter", unique = true, nullable = false)
    public Long getIdPorter() {
        return this.idPorter;
    }

    public void setIdPorter(Long idPorter) {
        this.idPorter = idPorter;
    }

    public Integer getDisponible() {
        return this.disponible;
    }

    public void setDisponible(Integer disponible) {
        this.disponible = disponible;
    }

    public Integer getMdv() {
        return this.mdv;
    }

    public void setMdv(Integer mdv) {
        this.mdv = mdv;
    }

    @Column(name = "pu_objet", nullable = false)
    public double getPuObjet() {
        return this.puObjet;
    }

    public void setPuObjet(double puObjet) {
        this.puObjet = puObjet;
    }

    @Column(name = "qte_objet", nullable = false)
    public Integer getQteObjet() {
        return this.qteObjet;
    }

    public void setQteObjet(Integer qteObjet) {
        this.qteObjet = qteObjet;
    }

    public double getRemise() {
        return this.remise;
    }

    public void setRemise(double remise) {
        this.remise = remise;
    }

    //bi-directional many-to-one association to EuObjet
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_objet", nullable = false)
    public EuObjet getEuObjet() {
        return this.euObjet;
    }

    public void setEuObjet(EuObjet euObjet) {
        this.euObjet = euObjet;
    }

    //bi-directional many-to-one association to EuProforma
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_proforma", nullable = false)
    public EuProforma getEuProforma() {
        return this.euProforma;
    }

    public void setEuProforma(EuProforma euProforma) {
        this.euProforma = euProforma;
    }

}

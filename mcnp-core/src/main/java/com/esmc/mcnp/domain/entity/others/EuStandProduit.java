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

import com.esmc.mcnp.domain.entity.acteur.EuFiliere;

/**
 * The persistent class for the eu_stand_produit database table.
 *
 */
@Entity
@Table(name = "eu_stand_produit")
@NamedQuery(name = "EuStandProduit.findAll", query = "SELECT e FROM EuStandProduit e")
public class EuStandProduit implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idProduit;
    private String designProduit;
    private EuStand euStand;
    private EuFiliere euFiliere;

    public EuStandProduit() {
    }

    @Id
    @Column(name = "id_produit", unique = true, nullable = false)
    public Long getIdProduit() {
        return this.idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    @Column(name = "design_produit", length = 150)
    public String getDesignProduit() {
        return this.designProduit;
    }

    public void setDesignProduit(String designProduit) {
        this.designProduit = designProduit;
    }

    //bi-directional many-to-one association to EuStand
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stand")
    public EuStand getEuStand() {
        return this.euStand;
    }

    public void setEuStand(EuStand euStand) {
        this.euStand = euStand;
    }

    //bi-directional many-to-one association to EuFiliere
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_filiere")
    public EuFiliere getEuFiliere() {
        return this.euFiliere;
    }

    public void setEuFiliere(EuFiliere euFiliere) {
        this.euFiliere = euFiliere;
    }

}

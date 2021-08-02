package com.esmc.mcnp.model.others;

import com.esmc.mcnp.model.cm.EuMembre;

import java.io.Serializable;
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

/**
 * The persistent class for the eu_stand database table.
 *
 */
@Entity
@Table(name = "eu_stand")
@NamedQuery(name = "EuStand.findAll", query = "SELECT e FROM EuStand e")
public class EuStand implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idStand;
    private String description;
    private String designStand;
    private EuMembre euMembre;
    private List<EuStandProduit> euStandProduits;

    public EuStand() {
    }

    @Id
    @Column(name = "id_stand", unique = true, nullable = false)
    public Integer getIdStand() {
        return this.idStand;
    }

    public void setIdStand(Integer idStand) {
        this.idStand = idStand;
    }

    @Column(length = 255)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "design_stand", length = 100)
    public String getDesignStand() {
        return this.designStand;
    }

    public void setDesignStand(String designStand) {
        this.designStand = designStand;
    }

    //bi-directional many-to-one association to EuMembre
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre")
    public EuMembre getEuMembre() {
        return this.euMembre;
    }

    public void setEuMembre(EuMembre euMembre) {
        this.euMembre = euMembre;
    }

    //bi-directional many-to-one association to EuStandProduit
    @OneToMany(mappedBy = "euStand")
    public List<EuStandProduit> getEuStandProduits() {
        return this.euStandProduits;
    }

    public void setEuStandProduits(List<EuStandProduit> euStandProduits) {
        this.euStandProduits = euStandProduits;
    }

    public EuStandProduit addEuStandProduit(EuStandProduit euStandProduit) {
        getEuStandProduits().add(euStandProduit);
        euStandProduit.setEuStand(this);

        return euStandProduit;
    }

    public EuStandProduit removeEuStandProduit(EuStandProduit euStandProduit) {
        getEuStandProduits().remove(euStandProduit);
        euStandProduit.setEuStand(null);

        return euStandProduit;
    }

}

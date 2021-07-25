package com.esmc.mcnp.model.obpsd;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

/**
 * The persistent class for the eu_fgfn database table.
 *
 */
@Entity
@Table(name = "eu_fgfn")
@NamedQuery(name = "EuFgfn.findAll", query = "SELECT e FROM EuFgfn e")
public class EuFgfn implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codeFgfn;
    private double montantFgfn;
    private double montantUtilise;
    private double soldeFgfn;
    private String typeFgfn;
    private String codeBanque;
    private List<EuDetailFgfn> euDetailFgfns;
    private EuMembreMorale euMembreMorale;

    public EuFgfn() {
    }

    @Id
    @JsonView
    @Column(name = "code_fgfn", unique = true, nullable = false, length = 120)
    public String getCodeFgfn() {
        return this.codeFgfn;
    }

    public void setCodeFgfn(String codeFgfn) {
        this.codeFgfn = codeFgfn;
    }

    @JsonView
    @Column(name = "montant_fgfn", nullable = false)
    public double getMontantFgfn() {
        return this.montantFgfn;
    }

    public void setMontantFgfn(double montantFgfn) {
        this.montantFgfn = montantFgfn;
    }

    @JsonView
    @Column(name = "montant_utilise", nullable = false)
    public double getMontantUtilise() {
        return this.montantUtilise;
    }

    public void setMontantUtilise(double montantUtilise) {
        this.montantUtilise = montantUtilise;
    }

    @JsonView
    @Column(name = "solde_fgfn")
    public double getSoldeFgfn() {
        return this.soldeFgfn;
    }

    public void setSoldeFgfn(double soldeFgfn) {
        this.soldeFgfn = soldeFgfn;
    }

    @JsonView
    @Column(name = "type_fgfn", length = 25)
    public String getTypeFgfn() {
        return this.typeFgfn;
    }

    public void setTypeFgfn(String typeFgfn) {
        this.typeFgfn = typeFgfn;
    }

    // bi-directional many-to-one association to EuDetailFgfn
    @JsonIgnore
    @OneToMany(mappedBy = "euFgfn")
    public List<EuDetailFgfn> getEuDetailFgfns() {
        return this.euDetailFgfns;
    }

    public void setEuDetailFgfns(List<EuDetailFgfn> euDetailFgfns) {
        this.euDetailFgfns = euDetailFgfns;
    }

    public EuDetailFgfn addEuDetailFgfn(EuDetailFgfn euDetailFgfn) {
        getEuDetailFgfns().add(euDetailFgfn);
        euDetailFgfn.setEuFgfn(this);

        return euDetailFgfn;
    }

    public EuDetailFgfn removeEuDetailFgfn(EuDetailFgfn euDetailFgfn) {
        getEuDetailFgfns().remove(euDetailFgfn);
        euDetailFgfn.setEuFgfn(null);

        return euDetailFgfn;
    }

    // bi-directional many-to-one association to EuMembreMorale
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre")
    public EuMembreMorale getEuMembreMorale() {
        return this.euMembreMorale;
    }

    public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
        this.euMembreMorale = euMembreMorale;
    }

    @JsonView
    @Column(name = "code_banque")
    public String getCodeBanque() {
        return codeBanque;
    }

    public void setCodeBanque(String codeBanque) {
        this.codeBanque = codeBanque;
    }

}

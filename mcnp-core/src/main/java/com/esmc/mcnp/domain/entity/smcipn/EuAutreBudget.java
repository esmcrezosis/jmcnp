package com.esmc.mcnp.domain.entity.smcipn;

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
 * The persistent class for the eu_autre_budget database table.
 *
 */
@Entity
@Table(name = "eu_autre_budget")
@NamedQuery(name = "EuAutreBudget.findAll", query = "SELECT e FROM EuAutreBudget e")
public class EuAutreBudget implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idBudget;
    private String libbesoin;
    private double montant;
    private String typeBudget;
    private EuInvestissement euInvestissement;

    public EuAutreBudget() {
    }

    @Id
    @Column(name = "id_budget", unique = true, nullable = false)
    public Long getIdBudget() {
        return this.idBudget;
    }

    public void setIdBudget(Long idBudget) {
        this.idBudget = idBudget;
    }

    @Column(length = 200)
    public String getLibbesoin() {
        return this.libbesoin;
    }

    public void setLibbesoin(String libbesoin) {
        this.libbesoin = libbesoin;
    }

    public double getMontant() {
        return this.montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    @Column(name = "type_budget", length = 80)
    public String getTypeBudget() {
        return this.typeBudget;
    }

    public void setTypeBudget(String typeBudget) {
        this.typeBudget = typeBudget;
    }

    //bi-directional many-to-one association to EuInvestissement
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_investissement", nullable = false)
    public EuInvestissement getEuInvestissement() {
        return this.euInvestissement;
    }

    public void setEuInvestissement(EuInvestissement euInvestissement) {
        this.euInvestissement = euInvestissement;
    }

}

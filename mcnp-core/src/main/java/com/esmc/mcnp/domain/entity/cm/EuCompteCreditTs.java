package com.esmc.mcnp.domain.entity.cm;

import org.hibernate.annotations.DynamicUpdate;

import com.esmc.mcnp.domain.entity.bc.EuBon;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_compte_credit_ts database table.
 *
 */
@Entity
@Table(name = "eu_compte_credit_ts")
@DynamicUpdate
@NamedQuery(name = "EuCompteCreditTs.findAll", query = "SELECT e FROM EuCompteCreditTs e")
public class EuCompteCreditTs implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idCreditTs;
    private Date DateCreditTs;
    private Double montant;
    private String compteSource;
    private EuCompteCredit euCompteCredit;
    private EuCompte euCompte;
    private EuBon euBon;

    public EuCompteCreditTs() {
    }

    @Id
    @Column(name = "id_credit_ts")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getIdCreditTs() {
        return idCreditTs;
    }

    public void setIdCreditTs(Long idCreditTs) {
        this.idCreditTs = idCreditTs;
    }

    @Column(name = "date_credit_ts")
    public Date getDateCreditTs() {
        return DateCreditTs;
    }

    public void setDateCreditTs(Date dateCreditTs) {
        DateCreditTs = dateCreditTs;
    }

    public Double getMontant() {
        return this.montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    @Column(name = "compte_source")
    public String getCompteSource() {
        return compteSource;
    }

    public void setCompteSource(String compteSource) {
        this.compteSource = compteSource;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_credit")
    public EuCompteCredit getEuCompteCredit() {
        return euCompteCredit;
    }

    public void setEuCompteCredit(EuCompteCredit euCompteCredit) {
        this.euCompteCredit = euCompteCredit;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_compte_ts")
    public EuCompte getEuCompte() {
        return euCompte;
    }

    public void setEuCompte(EuCompte euCompte) {
        this.euCompte = euCompte;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bon_id")
    public EuBon getEuBon() {
        return euBon;
    }

    public void setEuBon(EuBon euBon) {
        this.euBon = euBon;
    }

}

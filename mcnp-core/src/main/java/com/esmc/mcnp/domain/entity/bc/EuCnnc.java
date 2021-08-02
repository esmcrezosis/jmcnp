package com.esmc.mcnp.domain.entity.bc;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;

import java.util.Date;

/**
 * The persistent class for the eu_cnnc database table.
 *
 */
@Entity
@Table(name = "eu_cnnc")
@NamedQuery(name = "EuCnnc.findAll", query = "SELECT e FROM EuCnnc e")
public class EuCnnc implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idCnnc;
    private String codeMembre;
    private Date datefin;
    private String libelle;
    private double montCredit;
    private double montUtilise;
    private double solde;
    private String sourceCredit;
    private EuCompteCredit euCompteCredit;

    public EuCnnc() {
    }

    @Id
    @Column(name = "id_cnnc", unique = true, nullable = false)
    public Long getIdCnnc() {
        return this.idCnnc;
    }

    public void setIdCnnc(Long idCnnc) {
        this.idCnnc = idCnnc;
    }

    @Column(name = "code_membre", nullable = false, length = 100)
    public String getCodeMembre() {
        return this.codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getDatefin() {
        return this.datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    @Column(nullable = false, length = 80)
    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Column(name = "mont_credit", nullable = false)
    public double getMontCredit() {
        return this.montCredit;
    }

    public void setMontCredit(double montCredit) {
        this.montCredit = montCredit;
    }

    @Column(name = "mont_utilise")
    public double getMontUtilise() {
        return this.montUtilise;
    }

    public void setMontUtilise(double montUtilise) {
        this.montUtilise = montUtilise;
    }

    public double getSolde() {
        return this.solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    @Column(name = "source_credit", length = 200)
    public String getSourceCredit() {
        return this.sourceCredit;
    }

    public void setSourceCredit(String sourceCredit) {
        this.sourceCredit = sourceCredit;
    }

    //bi-directional many-to-one association to EuCompteCredit
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_credit", nullable = false)
    public EuCompteCredit getEuCompteCredit() {
        return this.euCompteCredit;
    }

    public void setEuCompteCredit(EuCompteCredit euCompteCredit) {
        this.euCompteCredit = euCompteCredit;
    }

}

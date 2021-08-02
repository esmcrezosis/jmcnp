package com.esmc.mcnp.domain.entity.smcipn;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;

/**
 * The persistent class for the eu_smc database table.
 *
 */
@Entity
@Table(name = "eu_smc")
@NamedQuery(name = "EuSmc.findAll", query = "SELECT e FROM EuSmc e")
public class EuSmc implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idSmc;
    private String codeCapa;
    private String codeDomicilier;
    private String codeSmcipn;
    private Date dateSmc;
    private double entree;
    private double montant;
    private double montantSolde;
    private Integer origineSmc;
    private double solde;
    private double sortie;
    private String sourceCredit;
    private String typeSmc;
    private EuCompteCredit euCompteCredit;
    private List<EuUtiliser> euUtilisers;

    public EuSmc() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_smc", unique = true, nullable = false)
    public Long getIdSmc() {
        return this.idSmc;
    }

    public void setIdSmc(Long idSmc) {
        this.idSmc = idSmc;
    }

    @Column(name = "code_capa", length = 180)
    public String getCodeCapa() {
        return this.codeCapa;
    }

    public void setCodeCapa(String codeCapa) {
        this.codeCapa = codeCapa;
    }

    @Column(name = "code_domicilier", length = 200)
    public String getCodeDomicilier() {
        return this.codeDomicilier;
    }

    public void setCodeDomicilier(String codeDomicilier) {
        this.codeDomicilier = codeDomicilier;
    }

    @Column(name = "code_smcipn", length = 200)
    public String getCodeSmcipn() {
        return this.codeSmcipn;
    }

    public void setCodeSmcipn(String codeSmcipn) {
        this.codeSmcipn = codeSmcipn;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_smc", nullable = false)
    public Date getDateSmc() {
        return this.dateSmc;
    }

    public void setDateSmc(Date dateSmc) {
        this.dateSmc = dateSmc;
    }

    @Column(nullable = false)
    public double getEntree() {
        return this.entree;
    }

    public void setEntree(double entree) {
        this.entree = entree;
    }

    @Column(nullable = false)
    public double getMontant() {
        return this.montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    @Column(name = "montant_solde")
    public double getMontantSolde() {
        return this.montantSolde;
    }

    public void setMontantSolde(double montantSolde) {
        this.montantSolde = montantSolde;
    }

    @Column(name = "origine_smc", nullable = false)
    public Integer getOrigineSmc() {
        return this.origineSmc;
    }

    public void setOrigineSmc(Integer origineSmc) {
        this.origineSmc = origineSmc;
    }

    @Column(nullable = false)
    public double getSolde() {
        return this.solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    @Column(nullable = false)
    public double getSortie() {
        return this.sortie;
    }

    public void setSortie(double sortie) {
        this.sortie = sortie;
    }

    @Column(name = "source_credit", length = 200)
    public String getSourceCredit() {
        return this.sourceCredit;
    }

    public void setSourceCredit(String sourceCredit) {
        this.sourceCredit = sourceCredit;
    }

    @Column(name = "type_smc", nullable = false, length = 180)
    public String getTypeSmc() {
        return this.typeSmc;
    }

    public void setTypeSmc(String typeSmc) {
        this.typeSmc = typeSmc;
    }

    //bi-directional many-to-one association to EuCompteCredit
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_credit")
    public EuCompteCredit getEuCompteCredit() {
        return this.euCompteCredit;
    }

    public void setEuCompteCredit(EuCompteCredit euCompteCredit) {
        this.euCompteCredit = euCompteCredit;
    }

    //bi-directional many-to-one association to EuUtiliser
    @OneToMany(mappedBy = "euSmc")
    public List<EuUtiliser> getEuUtilisers() {
        return this.euUtilisers;
    }

    public void setEuUtilisers(List<EuUtiliser> euUtilisers) {
        this.euUtilisers = euUtilisers;
    }

    public EuUtiliser addEuUtiliser(EuUtiliser euUtiliser) {
        getEuUtilisers().add(euUtiliser);
        euUtiliser.setEuSmc(this);

        return euUtiliser;
    }

    public EuUtiliser removeEuUtiliser(EuUtiliser euUtiliser) {
        getEuUtilisers().remove(euUtiliser);
        euUtiliser.setEuSmc(null);

        return euUtiliser;
    }

}

package com.esmc.mcnp.domain.entity.smcipn;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

import com.esmc.mcnp.domain.entity.ba.EuCapa;
import com.esmc.mcnp.domain.entity.smcipn.EuServir;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the eu_fn database table.
 *
 */
@Entity
@Table(name = "eu_fn")
@DynamicUpdate
@NamedQuery(name = "EuFn.findAll", query = "SELECT e FROM EuFn e")
public class EuFn implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idFn;
    private String codeDomicilier;
    private String codeSmcipn;
    private Date dateFn;
    private double entree;
    private double montant;
    private double mtSolde;
    private Integer origineFn;
    private double solde;
    private double sortie;
    private String typeFn;
    private EuCapa euCapa;
    private List<EuServir> euServirs;

    public EuFn() {
    }

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    @Column(name = "id_fn", unique = true, nullable = false)
    public Long getIdFn() {
        return this.idFn;
    }

    public void setIdFn(Long idFn) {
        this.idFn = idFn;
    }

    @Column(name = "code_domicilier", length = 200)
    public String getCodeDomicilier() {
        return this.codeDomicilier;
    }

    public void setCodeDomicilier(String codeDomicilier) {
        this.codeDomicilier = codeDomicilier;
    }

    @Column(name = "code_smcipn", length = 120)
    public String getCodeSmcipn() {
        return this.codeSmcipn;
    }

    public void setCodeSmcipn(String codeSmcipn) {
        this.codeSmcipn = codeSmcipn;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_fn")
    public Date getDateFn() {
        return this.dateFn;
    }

    public void setDateFn(Date dateFn) {
        this.dateFn = dateFn;
    }

    public double getEntree() {
        return this.entree;
    }

    public void setEntree(double entree) {
        this.entree = entree;
    }

    public double getMontant() {
        return this.montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    @Column(name = "mt_solde")
    public double getMtSolde() {
        return this.mtSolde;
    }

    public void setMtSolde(double mtSolde) {
        this.mtSolde = mtSolde;
    }

    @Column(name = "origine_fn", nullable = false)
    public Integer getOrigineFn() {
        return this.origineFn;
    }

    public void setOrigineFn(Integer origineFn) {
        this.origineFn = origineFn;
    }

    public double getSolde() {
        return this.solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public double getSortie() {
        return this.sortie;
    }

    public void setSortie(double sortie) {
        this.sortie = sortie;
    }

    @Column(name = "type_fn", length = 180)
    public String getTypeFn() {
        return this.typeFn;
    }

    public void setTypeFn(String typeFn) {
        this.typeFn = typeFn;
    }

    //bi-directional many-to-one association to EuCapa
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_capa")
    public EuCapa getEuCapa() {
        return this.euCapa;
    }

    public void setEuCapa(EuCapa euCapa) {
        this.euCapa = euCapa;
    }

    //bi-directional many-to-one association to EuServir
    @OneToMany(mappedBy = "euFn")
    public List<EuServir> getEuServirs() {
        return this.euServirs;
    }

    public void setEuServirs(List<EuServir> euServirs) {
        this.euServirs = euServirs;
    }

    public EuServir addEuServir(EuServir euServir) {
        getEuServirs().add(euServir);
        euServir.setEuFn(this);

        return euServir;
    }

    public EuServir removeEuServir(EuServir euServir) {
        getEuServirs().remove(euServir);
        euServir.setEuFn(null);

        return euServir;
    }

}

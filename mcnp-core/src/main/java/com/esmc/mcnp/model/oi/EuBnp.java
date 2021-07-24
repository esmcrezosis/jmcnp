package com.esmc.mcnp.model.oi;

import com.esmc.mcnp.model.others.EuOperation;

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
 * The persistent class for the eu_bnp database table.
 *
 */
@Entity
@Table(name = "eu_bnp")
@NamedQuery(name = "EuBnp.findAll", query = "SELECT e FROM EuBnp e")
public class EuBnp implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codeBnp;
    private String codeMembreApp;
    private String codeMembreBenef;
    private Double conus;
    private Double montConus;
    private Double montCredit;
    private Double montPanu;
    private Double montPar;
    private Double montantBnp;
    private Double panu;
    private String natureBnp;
    private Integer periode;
    private String reconstPanu;
    private String reconstPar;
    private String rembourser;
    private Integer idGcsc;
    private EuTypeBnp euTypeBnp;
    private EuOperation euOperation;
    private List<EuDetailBnp> euDetailBnps;

    public EuBnp() {
    }

    @Id
    @Column(name = "code_bnp", unique = true, nullable = false, length = 100)
    public String getCodeBnp() {
        return codeBnp;
    }

    public void setCodeBnp(String codeBnp) {
        this.codeBnp = codeBnp;
    }

    @Column(name = "code_membre_app", length = 100)
    public String getCodeMembreApp() {
        return codeMembreApp;
    }

    public void setCodeMembreApp(String codeMembreApp) {
        this.codeMembreApp = codeMembreApp;
    }

    @Column(name = "code_membre_benef", length = 100)
    public String getCodeMembreBenef() {
        return codeMembreBenef;
    }

    public void setCodeMembreBenef(String codeMembreBenef) {
        this.codeMembreBenef = codeMembreBenef;
    }

    public Double getConus() {
        return conus;
    }

    public void setConus(Double conus) {
        this.conus = conus;
    }

    @Column(name = "mont_conus")
    public Double getMontConus() {
        return montConus;
    }

    public void setMontConus(Double montConus) {
        this.montConus = montConus;
    }

    @Column(name = "mont_credit")
    public Double getMontCredit() {
        return montCredit;
    }

    public void setMontCredit(Double montCredit) {
        this.montCredit = montCredit;
    }

    @Column(name = "mont_panu")
    public Double getMontPanu() {
        return montPanu;
    }

    public void setMontPanu(Double montPanu) {
        this.montPanu = montPanu;
    }

    @Column(name = "mont_par")
    public Double getMontPar() {
        return montPar;
    }

    public void setMontPar(Double montPar) {
        this.montPar = montPar;
    }

    @Column(name = "montant_bnp")
    public Double getMontantBnp() {
        return montantBnp;
    }

    public void setMontantBnp(Double montantBnp) {
        this.montantBnp = montantBnp;
    }

    public Double getPanu() {
        return panu;
    }

    public void setPanu(Double panu) {
        this.panu = panu;
    }

    @Column(name = "nature_bnp")
    public String getNatureBnp() {
        return natureBnp;
    }

    public void setNatureBnp(String natureBnp) {
        this.natureBnp = natureBnp;
    }

    public Integer getPeriode() {
        return periode;
    }

    public void setPeriode(Integer periode) {
        this.periode = periode;
    }

    @Column(name = "reconst_panu", length = 4)
    public String getReconstPanu() {
        return reconstPanu;
    }

    public void setReconstPanu(String reconstPanu) {
        this.reconstPanu = reconstPanu;
    }

    @Column(name = "reconst_par", length = 4)
    public String getReconstPar() {
        return reconstPar;
    }

    public void setReconstPar(String reconstPar) {
        this.reconstPar = reconstPar;
    }

    @Column(length = 4)
    public String getRembourser() {
        return rembourser;
    }

    public void setRembourser(String rembourser) {
        this.rembourser = rembourser;
    }

    // bi-directional many-to-one association to EuTypeBnp
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_type_bnp")
    public EuTypeBnp getEuTypeBnp() {
        return euTypeBnp;
    }

    public void setEuTypeBnp(EuTypeBnp euTypeBnp) {
        this.euTypeBnp = euTypeBnp;
    }

    // bi-directional many-to-one association to EuOperation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_operation")
    public EuOperation getEuOperation() {
        return euOperation;
    }

    public void setEuOperation(EuOperation euOperation) {
        this.euOperation = euOperation;
    }

    @Column(name = "id_gcsc")
    public Integer getIdGcsc() {
        return idGcsc;
    }

    public void setIdGcsc(Integer idGcsc) {
        this.idGcsc = idGcsc;
    }

    // bi-directional many-to-one association to EuDetailBnp
    @OneToMany(mappedBy = "euBnp")
    public List<EuDetailBnp> getEuDetailBnps() {
        return euDetailBnps;
    }

    public void setEuDetailBnps(List<EuDetailBnp> euDetailBnps) {
        this.euDetailBnps = euDetailBnps;
    }

    public EuDetailBnp addEuDetailBnp(EuDetailBnp euDetailBnp) {
        getEuDetailBnps().add(euDetailBnp);
        euDetailBnp.setEuBnp(this);

        return euDetailBnp;
    }

    public EuDetailBnp removeEuDetailBnp(EuDetailBnp euDetailBnp) {
        getEuDetailBnps().remove(euDetailBnp);
        euDetailBnp.setEuBnp(null);

        return euDetailBnp;
    }

}

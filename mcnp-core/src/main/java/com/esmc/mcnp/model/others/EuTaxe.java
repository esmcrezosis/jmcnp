package com.esmc.mcnp.model.others;

import com.esmc.mcnp.model.org.EuPays;

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
 * The persistent class for the eu_taxe database table.
 *
 */
@Entity
@Table(name = "eu_taxe")
@NamedQuery(name = "EuTaxe.findAll", query = "SELECT e FROM EuTaxe e")
public class EuTaxe implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idTaxe;
    private String libelleTaxe;
    private double tauxTaxe;
    private List<EuFacture> euFactures;
    private List<EuProforma> euProformas;
    private EuPays euPay;

    public EuTaxe() {
    }

    @Id
    @Column(name = "id_taxe", unique = true, nullable = false)
    public Integer getIdTaxe() {
        return this.idTaxe;
    }

    public void setIdTaxe(Integer idTaxe) {
        this.idTaxe = idTaxe;
    }

    @Column(name = "libelle_taxe", nullable = false, length = 200)
    public String getLibelleTaxe() {
        return this.libelleTaxe;
    }

    public void setLibelleTaxe(String libelleTaxe) {
        this.libelleTaxe = libelleTaxe;
    }

    @Column(name = "taux_taxe", nullable = false)
    public double getTauxTaxe() {
        return this.tauxTaxe;
    }

    public void setTauxTaxe(double tauxTaxe) {
        this.tauxTaxe = tauxTaxe;
    }

    //bi-directional many-to-one association to EuFacture
    @OneToMany(mappedBy = "euTaxe")
    public List<EuFacture> getEuFactures() {
        return this.euFactures;
    }

    public void setEuFactures(List<EuFacture> euFactures) {
        this.euFactures = euFactures;
    }

    public EuFacture addEuFacture(EuFacture euFacture) {
        getEuFactures().add(euFacture);
        euFacture.setEuTaxe(this);

        return euFacture;
    }

    public EuFacture removeEuFacture(EuFacture euFacture) {
        getEuFactures().remove(euFacture);
        euFacture.setEuTaxe(null);

        return euFacture;
    }

    //bi-directional many-to-one association to EuProforma
    @OneToMany(mappedBy = "euTaxe")
    public List<EuProforma> getEuProformas() {
        return this.euProformas;
    }

    public void setEuProformas(List<EuProforma> euProformas) {
        this.euProformas = euProformas;
    }

    public EuProforma addEuProforma(EuProforma euProforma) {
        getEuProformas().add(euProforma);
        euProforma.setEuTaxe(this);

        return euProforma;
    }

    public EuProforma removeEuProforma(EuProforma euProforma) {
        getEuProformas().remove(euProforma);
        euProforma.setEuTaxe(null);

        return euProforma;
    }

    //bi-directional many-to-one association to EuPays
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pays", nullable = false)
    public EuPays getEuPay() {
        return this.euPay;
    }

    public void setEuPay(EuPays euPay) {
        this.euPay = euPay;
    }

}

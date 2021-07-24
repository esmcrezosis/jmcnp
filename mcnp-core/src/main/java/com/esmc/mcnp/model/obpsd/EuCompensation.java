package com.esmc.mcnp.model.obpsd;

import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.others.EuOperation;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the EU_COMPENSATION database table.
 *
 */
@Entity
@Table(name = "eu_compensation")
@NamedQuery(name = "EuCompensation.findAll", query = "SELECT e FROM EuCompensation e")
public class EuCompensation implements Serializable {

    private static final long serialVersionUID = 1L;
    private long idCompens;
    private String codeMembreBenef;
    private LocalDate dateCompens;
    private LocalDate dateDeb;
    private LocalDate dateDebTranche;
    private LocalDate dateFin;
    private LocalDate dateFinTranche;
    private LocalTime heureCompens;
    private double montCompens;
    private double montEchu;
    private double montTranche;
    private Integer ntf;
    private Integer periode;
    private Integer resteNtf;
    private double soldeCompensation;
    private EuCompte euCompte;
    private EuMembreMorale euMembreMorale;
    private EuOperation euOperation;

    public EuCompensation() {
    }

    @Id
    @Column(name = "id_compens", unique = true, nullable = false, precision = 19)
    public long getIdCompens() {
        return this.idCompens;
    }

    public void setIdCompens(long idCompens) {
        this.idCompens = idCompens;
    }

    @Column(name = "code_membre_benef", length = 25)
    public String getCodeMembreBenef() {
        return this.codeMembreBenef;
    }

    public void setCodeMembreBenef(String codeMembreBenef) {
        this.codeMembreBenef = codeMembreBenef;
    }

    @Column(name = "date_compens")
    public LocalDate getDateCompens() {
        return this.dateCompens;
    }

    public void setDateCompens(LocalDate dateCompens) {
        this.dateCompens = dateCompens;
    }

    @Column(name = "date_deb")
    public LocalDate getDateDeb() {
        return this.dateDeb;
    }

    public void setDateDeb(LocalDate dateDeb) {
        this.dateDeb = dateDeb;
    }

    @Column(name = "date_deb_tranche")
    public LocalDate getDateDebTranche() {
        return this.dateDebTranche;
    }

    public void setDateDebTranche(LocalDate dateDebTranche) {
        this.dateDebTranche = dateDebTranche;
    }

    @Column(name = "date_fin")
    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    @Column(name = "date_fin_tranche")
    public LocalDate getDateFinTranche() {
        return this.dateFinTranche;
    }

    public void setDateFinTranche(LocalDate dateFinTranche) {
        this.dateFinTranche = dateFinTranche;
    }

    @Column(name = "heure_compens")
    public LocalTime getHeureCompens() {
        return this.heureCompens;
    }

    public void setHeureCompens(LocalTime heureCompens) {
        this.heureCompens = heureCompens;
    }

    @Column(name = "mont_compens", precision = 126)
    public double getMontCompens() {
        return this.montCompens;
    }

    public void setMontCompens(double montCompens) {
        this.montCompens = montCompens;
    }

    @Column(name = "mont_echu", precision = 126)
    public double getMontEchu() {
        return this.montEchu;
    }

    public void setMontEchu(double montEchu) {
        this.montEchu = montEchu;
    }

    @Column(name = "mont_tranche", precision = 126)
    public double getMontTranche() {
        return this.montTranche;
    }

    public void setMontTranche(double montTranche) {
        this.montTranche = montTranche;
    }

    @Column(precision = 10)
    public Integer getNtf() {
        return this.ntf;
    }

    public void setNtf(Integer ntf) {
        this.ntf = ntf;
    }

    @Column(precision = 10)
    public Integer getPeriode() {
        return this.periode;
    }

    public void setPeriode(Integer periode) {
        this.periode = periode;
    }

    @Column(name = "reste_ntf", precision = 10)
    public Integer getResteNtf() {
        return this.resteNtf;
    }

    public void setResteNtf(Integer resteNtf) {
        this.resteNtf = resteNtf;
    }

    @Column(name = "solde_compensation", precision = 126)
    public double getSoldeCompensation() {
        return this.soldeCompensation;
    }

    public void setSoldeCompensation(double soldeCompensation) {
        this.soldeCompensation = soldeCompensation;
    }

    //bi-directional many-to-one association to EuCompte
    @ManyToOne
    @JoinColumn(name = "code_compte")
    public EuCompte getEuCompte() {
        return this.euCompte;
    }

    public void setEuCompte(EuCompte euCompte) {
        this.euCompte = euCompte;
    }

    //bi-directional many-to-one association to EuMembreMorale
    @ManyToOne
    @JoinColumn(name = "code_membre_benef", insertable = false, updatable = false)
    public EuMembreMorale getEuMembreMorale() {
        return this.euMembreMorale;
    }

    public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
        this.euMembreMorale = euMembreMorale;
    }

    //bi-directional many-to-one association to EuOperation
    @ManyToOne
    @JoinColumn(name = "id_operation")
    public EuOperation getEuOperation() {
        return this.euOperation;
    }

    public void setEuOperation(EuOperation euOperation) {
        this.euOperation = euOperation;
    }

}

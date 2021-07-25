package com.esmc.mcnp.model.others;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.smcipn.EuBudgetFacture;
import com.esmc.mcnp.model.smcipn.EuInvestissement;

/**
 * The persistent class for the eu_besoin database table.
 *
 */
@Entity
@Table(name = "eu_besoin")
@NamedQuery(name = "EuBesoin.findAll", query = "SELECT e FROM EuBesoin e")
public class EuBesoin implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idBesoin;
    private Date dateBesoin;
    private Date dateValide;
    private Integer disponible;
    private String objetBesoin;
    private EuMembreMorale euMembreMorale;
    private List<EuBudgetFacture> euBudgetFactures;
    private EuConcerner euConcerner;
    private List<EuInvestissement> euInvestissements;
    private List<EuProforma> euProformas;

    public EuBesoin() {
    }

    @Id
    @Column(name = "id_besoin", unique = true, nullable = false)
    public Long getIdBesoin() {
        return this.idBesoin;
    }

    public void setIdBesoin(Long idBesoin) {
        this.idBesoin = idBesoin;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_besoin")
    public Date getDateBesoin() {
        return this.dateBesoin;
    }

    public void setDateBesoin(Date dateBesoin) {
        this.dateBesoin = dateBesoin;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_valide")
    public Date getDateValide() {
        return this.dateValide;
    }

    public void setDateValide(Date dateValide) {
        this.dateValide = dateValide;
    }

    public Integer getDisponible() {
        return this.disponible;
    }

    public void setDisponible(Integer disponible) {
        this.disponible = disponible;
    }

    @Lob
    @Column(name = "objet_besoin")
    public String getObjetBesoin() {
        return this.objetBesoin;
    }

    public void setObjetBesoin(String objetBesoin) {
        this.objetBesoin = objetBesoin;
    }

    //bi-directional many-to-one association to EuMembreMorale
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre")
    public EuMembreMorale getEuMembreMorale() {
        return this.euMembreMorale;
    }

    public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
        this.euMembreMorale = euMembreMorale;
    }

    //bi-directional many-to-one association to EuBudgetFacture
    @OneToMany(mappedBy = "euBesoin")
    public List<EuBudgetFacture> getEuBudgetFactures() {
        return this.euBudgetFactures;
    }

    public void setEuBudgetFactures(List<EuBudgetFacture> euBudgetFactures) {
        this.euBudgetFactures = euBudgetFactures;
    }

    public EuBudgetFacture addEuBudgetFacture(EuBudgetFacture euBudgetFacture) {
        getEuBudgetFactures().add(euBudgetFacture);
        euBudgetFacture.setEuBesoin(this);

        return euBudgetFacture;
    }

    public EuBudgetFacture removeEuBudgetFacture(EuBudgetFacture euBudgetFacture) {
        getEuBudgetFactures().remove(euBudgetFacture);
        euBudgetFacture.setEuBesoin(null);

        return euBudgetFacture;
    }

    //bi-directional one-to-one association to EuConcerner
    @OneToOne(mappedBy = "euBesoin", fetch = FetchType.LAZY)
    public EuConcerner getEuConcerner() {
        return this.euConcerner;
    }

    public void setEuConcerner(EuConcerner euConcerner) {
        this.euConcerner = euConcerner;
    }

    //bi-directional many-to-one association to EuInvestissement
    @OneToMany(mappedBy = "euBesoin")
    public List<EuInvestissement> getEuInvestissements() {
        return this.euInvestissements;
    }

    public void setEuInvestissements(List<EuInvestissement> euInvestissements) {
        this.euInvestissements = euInvestissements;
    }

    public EuInvestissement addEuInvestissement(EuInvestissement euInvestissement) {
        getEuInvestissements().add(euInvestissement);
        euInvestissement.setEuBesoin(this);

        return euInvestissement;
    }

    public EuInvestissement removeEuInvestissement(EuInvestissement euInvestissement) {
        getEuInvestissements().remove(euInvestissement);
        euInvestissement.setEuBesoin(null);

        return euInvestissement;
    }

    //bi-directional many-to-one association to EuProforma
    @OneToMany(mappedBy = "euBesoin")
    public List<EuProforma> getEuProformas() {
        return this.euProformas;
    }

    public void setEuProformas(List<EuProforma> euProformas) {
        this.euProformas = euProformas;
    }

    public EuProforma addEuProforma(EuProforma euProforma) {
        getEuProformas().add(euProforma);
        euProforma.setEuBesoin(this);

        return euProforma;
    }

    public EuProforma removeEuProforma(EuProforma euProforma) {
        getEuProformas().remove(euProforma);
        euProforma.setEuBesoin(null);

        return euProforma;
    }

}

package com.esmc.mcnp.domain.entity.bc;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;

/**
 * The persistent class for the eu_detail_domicilie database table.
 *
 */
@Entity
@Table(name = "eu_detail_domicilie")
@NamedQuery(name = "EuDetailDomicilie.findAll", query = "SELECT e FROM EuDetailDomicilie e")
public class EuDetailDomicilie implements Serializable {

    private static final long serialVersionUID = 1L;
    private EuDetailDomiciliePK id;
    private Integer consommer;
    private Integer dureeRenouvellement;
    private double montantCredit;
    private Integer resteDuree;
    private Integer utiliser;
    private EuCompteCredit euCompteCredit;
    private EuMembre euMembre;
    private EuMembreMorale euMembreMorale;
    private EuDomiciliation euDomiciliation;

    public EuDetailDomicilie() {
    }

    @EmbeddedId
    public EuDetailDomiciliePK getId() {
        return this.id;
    }

    public void setId(EuDetailDomiciliePK id) {
        this.id = id;
    }

    public Integer getConsommer() {
        return this.consommer;
    }

    public void setConsommer(Integer consommer) {
        this.consommer = consommer;
    }

    @Column(name = "duree_renouvellement")
    public Integer getDureeRenouvellement() {
        return this.dureeRenouvellement;
    }

    public void setDureeRenouvellement(Integer dureeRenouvellement) {
        this.dureeRenouvellement = dureeRenouvellement;
    }

    @Column(name = "montant_credit")
    public double getMontantCredit() {
        return this.montantCredit;
    }

    public void setMontantCredit(double montantCredit) {
        this.montantCredit = montantCredit;
    }

    @Column(name = "reste_duree")
    public Integer getResteDuree() {
        return this.resteDuree;
    }

    public void setResteDuree(Integer resteDuree) {
        this.resteDuree = resteDuree;
    }

    public Integer getUtiliser() {
        return this.utiliser;
    }

    public void setUtiliser(Integer utiliser) {
        this.utiliser = utiliser;
    }

    //bi-directional many-to-one association to EuCompteCredit
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_credit", nullable = false, insertable = false, updatable = false)
    public EuCompteCredit getEuCompteCredit() {
        return this.euCompteCredit;
    }

    public void setEuCompteCredit(EuCompteCredit euCompteCredit) {
        this.euCompteCredit = euCompteCredit;
    }

    //bi-directional many-to-one association to EuMembre
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre")
    public EuMembre getEuMembre() {
        return this.euMembre;
    }

    public void setEuMembre(EuMembre euMembre) {
        this.euMembre = euMembre;
    }

    //bi-directional many-to-one association to EuMembreMorale
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre_morale")
    public EuMembreMorale getEuMembreMorale() {
        return this.euMembreMorale;
    }

    public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
        this.euMembreMorale = euMembreMorale;
    }

    //bi-directional many-to-one association to EuDomiciliation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_domicilier", nullable = false, insertable = false, updatable = false)
    public EuDomiciliation getEuDomiciliation() {
        return this.euDomiciliation;
    }

    public void setEuDomiciliation(EuDomiciliation euDomiciliation) {
        this.euDomiciliation = euDomiciliation;
    }

}

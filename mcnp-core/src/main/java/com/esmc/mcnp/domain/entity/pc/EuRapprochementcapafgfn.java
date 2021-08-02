package com.esmc.mcnp.domain.entity.pc;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;

/**
 * The persistent class for the eu_rapprochementcapafgfn database table.
 *
 */
@Entity
@Table(name = "eu_rapprochementcapafgfn")
@NamedQuery(name = "EuRapprochementcapafgfn.findAll", query = "SELECT e FROM EuRapprochementcapafgfn e")
public class EuRapprochementcapafgfn implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private double capa;
    private String compte;
    private String dateFgfn;
    private double montFgfn;
    private double montPreleve;
    private String raisonSociale;
    private double reste;
    private EuMembreMorale euMembreMorale1;
    private EuMembreMorale euMembreMorale2;

    public EuRapprochementcapafgfn() {
    }

    @Id
    @Column(unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCapa() {
        return this.capa;
    }

    public void setCapa(double capa) {
        this.capa = capa;
    }

    @Column(length = 200)
    public String getCompte() {
        return this.compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }

    @Column(name = "date_fgfn", length = 80)
    public String getDateFgfn() {
        return this.dateFgfn;
    }

    public void setDateFgfn(String dateFgfn) {
        this.dateFgfn = dateFgfn;
    }

    @Column(name = "mont_fgfn")
    public double getMontFgfn() {
        return this.montFgfn;
    }

    public void setMontFgfn(double montFgfn) {
        this.montFgfn = montFgfn;
    }

    @Column(name = "mont_preleve")
    public double getMontPreleve() {
        return this.montPreleve;
    }

    public void setMontPreleve(double montPreleve) {
        this.montPreleve = montPreleve;
    }

    @Column(name = "raison_sociale", length = 100)
    public String getRaisonSociale() {
        return this.raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public double getReste() {
        return this.reste;
    }

    public void setReste(double reste) {
        this.reste = reste;
    }

    //bi-directional many-to-one association to EuMembreMorale
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre")
    public EuMembreMorale getEuMembreMorale1() {
        return this.euMembreMorale1;
    }

    public void setEuMembreMorale1(EuMembreMorale euMembreMorale1) {
        this.euMembreMorale1 = euMembreMorale1;
    }

    //bi-directional many-to-one association to EuMembreMorale
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membrepbf")
    public EuMembreMorale getEuMembreMorale2() {
        return this.euMembreMorale2;
    }

    public void setEuMembreMorale2(EuMembreMorale euMembreMorale2) {
        this.euMembreMorale2 = euMembreMorale2;
    }

}

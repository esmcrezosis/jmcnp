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
 * The persistent class for the eu_rapprochementsms database table.
 *
 */
@Entity
@Table(name = "eu_rapprochementsms")
@NamedQuery(name = "EuRapprochementsms.findAll", query = "SELECT e FROM EuRapprochementsms e")
public class EuRapprochementsms implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private double creditamount;
    private String creditcode;
    private String dateFl;
    private String datetime;
    private double montFl;
    private String motif;
    private String raisonSociale;
    private EuMembreMorale euMembreMorale1;
    private EuMembreMorale euMembreMorale2;

    public EuRapprochementsms() {
    }

    @Id
    @Column(unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCreditamount() {
        return this.creditamount;
    }

    public void setCreditamount(double creditamount) {
        this.creditamount = creditamount;
    }

    @Column(length = 80)
    public String getCreditcode() {
        return this.creditcode;
    }

    public void setCreditcode(String creditcode) {
        this.creditcode = creditcode;
    }

    @Column(name = "date_fl", length = 80)
    public String getDateFl() {
        return this.dateFl;
    }

    public void setDateFl(String dateFl) {
        this.dateFl = dateFl;
    }

    @Column(length = 80)
    public String getDatetime() {
        return this.datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Column(name = "mont_fl")
    public double getMontFl() {
        return this.montFl;
    }

    public void setMontFl(double montFl) {
        this.montFl = montFl;
    }

    @Column(length = 200)
    public String getMotif() {
        return this.motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    @Column(name = "raison_sociale", length = 100)
    public String getRaisonSociale() {
        return this.raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    //bi-directional many-to-one association to EuMembreMorale
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membrepbf")
    public EuMembreMorale getEuMembreMorale1() {
        return this.euMembreMorale1;
    }

    public void setEuMembreMorale1(EuMembreMorale euMembreMorale1) {
        this.euMembreMorale1 = euMembreMorale1;
    }

    //bi-directional many-to-one association to EuMembreMorale
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre")
    public EuMembreMorale getEuMembreMorale2() {
        return this.euMembreMorale2;
    }

    public void setEuMembreMorale2(EuMembreMorale euMembreMorale2) {
        this.euMembreMorale2 = euMembreMorale2;
    }

}

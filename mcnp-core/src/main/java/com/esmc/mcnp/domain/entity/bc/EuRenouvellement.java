package com.esmc.mcnp.domain.entity.bc;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the eu_renouvellement database table.
 *
 */
@Entity
@Table(name = "eu_renouvellement")
@NamedQuery(name = "EuRenouvellement.findAll", query = "SELECT e FROM EuRenouvellement e")
public class EuRenouvellement implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idRenouvellement;
    private Date dateRenouvellement;
    private LocalTime heureDeb;
    private LocalTime heureFin;
    private double montCredit;
    private double montNconso;
    private Integer nbreCredit;
    private Integer nbreNconso;
    private String typeRenouvellement;

    public EuRenouvellement() {
    }

    @Id
    @Column(name = "id_renouvellement", unique = true, nullable = false)
    public Long getIdRenouvellement() {
        return this.idRenouvellement;
    }

    public void setIdRenouvellement(Long idRenouvellement) {
        this.idRenouvellement = idRenouvellement;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_renouvellement")
    public Date getDateRenouvellement() {
        return this.dateRenouvellement;
    }

    public void setDateRenouvellement(Date dateRenouvellement) {
        this.dateRenouvellement = dateRenouvellement;
    }

    @Column(name = "heure_deb")
    public LocalTime getHeureDeb() {
        return this.heureDeb;
    }

    public void setHeureDeb(LocalTime heureDeb) {
        this.heureDeb = heureDeb;
    }

    @Column(name = "heure_fin")
    public LocalTime getHeureFin() {
        return this.heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    @Column(name = "mont_credit")
    public double getMontCredit() {
        return this.montCredit;
    }

    public void setMontCredit(double montCredit) {
        this.montCredit = montCredit;
    }

    @Column(name = "mont_nconso")
    public double getMontNconso() {
        return this.montNconso;
    }

    public void setMontNconso(double montNconso) {
        this.montNconso = montNconso;
    }

    @Column(name = "nbre_credit")
    public Integer getNbreCredit() {
        return this.nbreCredit;
    }

    public void setNbreCredit(Integer nbreCredit) {
        this.nbreCredit = nbreCredit;
    }

    @Column(name = "nbre_nconso")
    public Integer getNbreNconso() {
        return this.nbreNconso;
    }

    public void setNbreNconso(Integer nbreNconso) {
        this.nbreNconso = nbreNconso;
    }

    @Column(name = "type_renouvellement")
	public String getTypeRenouvellement() {
		return typeRenouvellement;
	}

	public void setTypeRenouvellement(String typeRenouvellement) {
		this.typeRenouvellement = typeRenouvellement;
	}

}

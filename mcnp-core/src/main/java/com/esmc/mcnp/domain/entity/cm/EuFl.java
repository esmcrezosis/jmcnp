package com.esmc.mcnp.domain.entity.cm;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;

import java.util.Date;


/**
 * The persistent class for the eu_fl database table.
 * 
 */
@Entity
@Table(name="eu_fl")
@NamedQuery(name="EuFl.findAll", query="SELECT e FROM EuFl e")
public class EuFl implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeFl;
	private String creditcode;
	private Date dateFl;
	private Date heureFl;
	private double idUtilisateur;
	private double montFl;
	private EuMembreMorale euMembreMorale;
	private EuMembre euMembre;

	public EuFl() {
	}


	@Id
	@Column(name="code_fl", unique=true, nullable=false, length=100)
	public String getCodeFl() {
		return this.codeFl;
	}

	public void setCodeFl(String codeFl) {
		this.codeFl = codeFl;
	}


	@Column(length=200)
	public String getCreditcode() {
		return this.creditcode;
	}

	public void setCreditcode(String creditcode) {
		this.creditcode = creditcode;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_fl")
	public Date getDateFl() {
		return this.dateFl;
	}

	public void setDateFl(Date dateFl) {
		this.dateFl = dateFl;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="heure_fl")
	public Date getHeureFl() {
		return this.heureFl;
	}

	public void setHeureFl(Date heureFl) {
		this.heureFl = heureFl;
	}


	@Column(name="id_utilisateur")
	public double getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(double idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	@Column(name="mont_fl")
	public double getMontFl() {
		return this.montFl;
	}

	public void setMontFl(double montFl) {
		this.montFl = montFl;
	}


	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre_morale")
	public EuMembreMorale getEuMembreMorale() {
		return this.euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}


	//bi-directional many-to-one association to EuMembre
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre")
	public EuMembre getEuMembre() {
		return this.euMembre;
	}

	public void setEuMembre(EuMembre euMembre) {
		this.euMembre = euMembre;
	}

}
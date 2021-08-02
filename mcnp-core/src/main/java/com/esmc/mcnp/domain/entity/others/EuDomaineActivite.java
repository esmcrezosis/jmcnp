package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

import java.util.Date;


/**
 * The persistent class for the eu_domaine_activite database table.
 * 
 */
@Entity
@Table(name="eu_domaine_activite")
@NamedQuery(name="EuDomaineActivite.findAll", query="SELECT e FROM EuDomaineActivite e")
public class EuDomaineActivite implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idDomaine;
	private Date dateCreation;
	private String descDomaine;
	private String libDomaine;
	private EuUtilisateur euUtilisateur;

	public EuDomaineActivite() {
	}


	@Id
	@Column(name="id_domaine", unique=true, nullable=false)
	public double getIdDomaine() {
		return this.idDomaine;
	}

	public void setIdDomaine(double idDomaine) {
		this.idDomaine = idDomaine;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_creation")
	public Date getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	@Column(name="desc_domaine", length=200)
	public String getDescDomaine() {
		return this.descDomaine;
	}

	public void setDescDomaine(String descDomaine) {
		this.descDomaine = descDomaine;
	}


	@Column(name="lib_domaine", length=100)
	public String getLibDomaine() {
		return this.libDomaine;
	}

	public void setLibDomaine(String libDomaine) {
		this.libDomaine = libDomaine;
	}


	//bi-directional many-to-one association to EuUtilisateur
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_utilisateur")
	public EuUtilisateur getEuUtilisateur() {
		return this.euUtilisateur;
	}

	public void setEuUtilisateur(EuUtilisateur euUtilisateur) {
		this.euUtilisateur = euUtilisateur;
	}

}
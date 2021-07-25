package com.esmc.mcnp.model.acteur;

import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_gac_filiere database table.
 *
 */
@Entity
@Table(name="eu_gac_filiere")
@NamedQuery(name="EuGacFiliere.findAll", query="SELECT e FROM EuGacFiliere e")
public class EuGacFiliere implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeGacFiliere;
	private Date dateCreation;
	private String groupe;
	private double idUtilisateur;
	private String nomGacFiliere;
	private EuGac euGac;
	private EuMembreMorale euMembreMorale;
	private EuMembre euMembre;

	public EuGacFiliere() {
	}


	@Id
	@Column(name="code_gac_filiere", nullable=false, length=100)
	public String getCodeGacFiliere() {
		return this.codeGacFiliere;
	}

	public void setCodeGacFiliere(String codeGacFiliere) {
		this.codeGacFiliere = codeGacFiliere;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_creation")
	public Date getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	@Column(length=100)
	public String getGroupe() {
		return this.groupe;
	}

	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}


	@Column(name="id_utilisateur")
	public double getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(double idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	@Column(name="nom_gac_filiere", length=200)
	public String getNomGacFiliere() {
		return this.nomGacFiliere;
	}

	public void setNomGacFiliere(String nomGacFiliere) {
		this.nomGacFiliere = nomGacFiliere;
	}


	//bi-directional many-to-one association to EuGac
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_gac")
	public EuGac getEuGac() {
		return this.euGac;
	}

	public void setEuGac(EuGac euGac) {
		this.euGac = euGac;
	}


	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre")
	public EuMembreMorale getEuMembreMorale() {
		return this.euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}


	//bi-directional many-to-one association to EuMembre
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre_gestionnaire")
	public EuMembre getEuMembre() {
		return this.euMembre;
	}

	public void setEuMembre(EuMembre euMembre) {
		this.euMembre = euMembre;
	}

}
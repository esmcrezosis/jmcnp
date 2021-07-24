package com.esmc.mcnp.model.acteur;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esmc.mcnp.model.acteur.EuCreneaux;
import com.esmc.mcnp.model.acteur.EuFiliere;
import com.esmc.mcnp.model.acteur.EuTypeActeur;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.security.EuUtilisateur;

/**
 * The persistent class for the eu_acteurs_creneaux database table.
 *
 */
@Entity
@Table(name = "eu_acteurs_creneaux")
@NamedQuery(name = "EuActeursCreneaux.findAll", query = "SELECT e FROM EuActeursCreneaux e")
public class EuActeursCreneaux implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeActeur;
	private String codeGacFiliere;
	private Date dateCreation;
	private String groupe;
	private String nomActeur;
	private EuCreneaux euCreneaux;
	private EuFiliere euFiliere;
	private EuTypeActeur euTypeActeur;
	private EuUtilisateur euUtilisateur;
	private EuMembreMorale euMembreMorale;
	private EuMembre euMembre;

	public EuActeursCreneaux() {
	}

	@Id
	@Column(name = "code_acteur", unique = true, nullable = false, length = 100)
	public String getCodeActeur() {
		return codeActeur;
	}

	public void setCodeActeur(String codeActeur) {
		this.codeActeur = codeActeur;
	}

	@Column(name = "code_gac_filiere", length = 100)
	public String getCodeGacFiliere() {
		return codeGacFiliere;
	}

	public void setCodeGacFiliere(String codeGacFiliere) {
		this.codeGacFiliere = codeGacFiliere;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_creation")
	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Column(length = 100)
	public String getGroupe() {
		return groupe;
	}

	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}

	@Column(name = "nom_acteur", length = 100)
	public String getNomActeur() {
		return nomActeur;
	}

	public void setNomActeur(String nomActeur) {
		this.nomActeur = nomActeur;
	}

	// bi-directional many-to-one association to EuCreneaux
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_creneau")
	public EuCreneaux getEuCreneaux() {
		return euCreneaux;
	}

	public void setEuCreneaux(EuCreneaux euCreneaux) {
		this.euCreneaux = euCreneaux;
	}

	// bi-directional many-to-one association to EuFiliere
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_filiere")
	public EuFiliere getEuFiliere() {
		return euFiliere;
	}

	public void setEuFiliere(EuFiliere euFiliere) {
		this.euFiliere = euFiliere;
	}

	// bi-directional many-to-one association to EuTypeActeur
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_type_acteur")
	public EuTypeActeur getEuTypeActeur() {
		return euTypeActeur;
	}

	public void setEuTypeActeur(EuTypeActeur euTypeActeur) {
		this.euTypeActeur = euTypeActeur;
	}

	// bi-directional many-to-one association to EuUtilisateur
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_utilisateur")
	public EuUtilisateur getEuUtilisateur() {
		return euUtilisateur;
	}

	public void setEuUtilisateur(EuUtilisateur euUtilisateur) {
		this.euUtilisateur = euUtilisateur;
	}

	// bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_membre")
	public EuMembreMorale getEuMembreMorale() {
		return euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}

	// bi-directional many-to-one association to EuMembre
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_membre_gestionnaire")
	public EuMembre getEuMembre() {
		return euMembre;
	}

	public void setEuMembre(EuMembre euMembre) {
		this.euMembre = euMembre;
	}

}
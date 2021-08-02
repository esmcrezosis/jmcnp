package com.esmc.mcnp.domain.entity.obpsd;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_relevebancaire database table.
 * 
 */
@Entity
@Table(name="eu_relevebancaire")
@NamedQuery(name="EuRelevebancaire.findAll", query="SELECT e FROM EuRelevebancaire e")
public class EuRelevebancaire implements Serializable {
	private static final long serialVersionUID = 1L;
	private int relevebancaireId;
	private int publier;
	private String relevebancaireBanque;
	private Date relevebancaireDate;
	private String relevebancaireFichier;
	private String relevebancaireUtilisateur;

	public EuRelevebancaire() {
	}


	@Id
	@Column(name="relevebancaire_id")
	public int getRelevebancaireId() {
		return this.relevebancaireId;
	}

	public void setRelevebancaireId(int relevebancaireId) {
		this.relevebancaireId = relevebancaireId;
	}


	public int getPublier() {
		return this.publier;
	}

	public void setPublier(int publier) {
		this.publier = publier;
	}


	@Column(name="relevebancaire_banque")
	public String getRelevebancaireBanque() {
		return this.relevebancaireBanque;
	}

	public void setRelevebancaireBanque(String relevebancaireBanque) {
		this.relevebancaireBanque = relevebancaireBanque;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="relevebancaire_date")
	public Date getRelevebancaireDate() {
		return this.relevebancaireDate;
	}

	public void setRelevebancaireDate(Date relevebancaireDate) {
		this.relevebancaireDate = relevebancaireDate;
	}


	@Column(name="relevebancaire_fichier")
	public String getRelevebancaireFichier() {
		return this.relevebancaireFichier;
	}

	public void setRelevebancaireFichier(String relevebancaireFichier) {
		this.relevebancaireFichier = relevebancaireFichier;
	}


	@Column(name="relevebancaire_utilisateur")
	public String getRelevebancaireUtilisateur() {
		return this.relevebancaireUtilisateur;
	}

	public void setRelevebancaireUtilisateur(String relevebancaireUtilisateur) {
		this.relevebancaireUtilisateur = relevebancaireUtilisateur;
	}

}
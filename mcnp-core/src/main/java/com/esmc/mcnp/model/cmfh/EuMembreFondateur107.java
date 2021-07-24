package com.esmc.mcnp.model.cmfh;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.model.security.EuUtilisateur;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the eu_membre_fondateur107 database table.
 * 
 */
@Entity
@Table(name="eu_membre_fondateur107")
@NamedQuery(name="EuMembreFondateur107.findAll", query="SELECT e FROM EuMembreFondateur107 e")
public class EuMembreFondateur107 implements Serializable {
	private static final long serialVersionUID = 1L;
	private String numident;
	private String codeMembre;
	private String nom;
	private String prenom;
	private String cel;
	private String tel;
	private double nbRepartition;
	private double solde;
	private EuUtilisateur euUtilisateur;

	public EuMembreFondateur107() {
	}


	@Id
	@Column(unique=true, nullable=false, length=100)
	public String getNumident() {
		return this.numident;
	}

	public void setNumident(String numident) {
		this.numident = numident;
	}


	public String getCel() {
		return this.cel;
	}

	public void setCel(String cel) {
		this.cel = cel;
	}

	@Column(name="code_membre", length=100)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Column(name="nb_repartition")
	public double getNbRepartition() {
		return this.nbRepartition;
	}

	public void setNbRepartition(double nbRepartition) {
		this.nbRepartition = nbRepartition;
	}


	@Column(length=160)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}


	@Column(length=240)
	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public double getSolde() {
		return this.solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}


	@Column(length=60)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}


	//bi-directional many-to-one association to EuUtilisateur
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_utilisateur")
	public EuUtilisateur getEuUtilisateur() {
		return this.euUtilisateur;
	}

	public void setEuUtilisateur(EuUtilisateur euUtilisateur) {
		this.euUtilisateur = euUtilisateur;
	}

}
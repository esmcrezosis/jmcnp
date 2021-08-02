package com.esmc.mcnp.domain.entity.cmfh;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the eu_membre_fondateur11000 database table.
 * 
 */
@Entity
@Table(name="eu_membre_fondateur11000")
@NamedQuery(name="EuMembreFondateur11000.findAll", query="SELECT e FROM EuMembreFondateur11000 e")
public class EuMembreFondateur11000 implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long numBon;
	private String cel;
	private String codeMembre;
	private double nbRepartition;
	private String nom;
	private String prenom;
	private double solde;
	private String tel;
	private List<EuDetailMf11000> euDetailMf11000s;
	private EuUtilisateur euUtilisateur;

	public EuMembreFondateur11000() {
	}


	@Id
	@Column(name="num_bon", unique=true, nullable=false)
	public Long getNumBon() {
		return this.numBon;
	}

	public void setNumBon(Long numBon) {
		this.numBon = numBon;
	}


	@Column(length=60)
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


	@Column(length=240)
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


	//bi-directional many-to-one association to EuDetailMf11000
	@JsonIgnore
	@OneToMany(mappedBy="euMembreFondateur11000")
	public List<EuDetailMf11000> getEuDetailMf11000s() {
		return this.euDetailMf11000s;
	}

	public void setEuDetailMf11000s(List<EuDetailMf11000> euDetailMf11000s) {
		this.euDetailMf11000s = euDetailMf11000s;
	}

	public EuDetailMf11000 addEuDetailMf11000(EuDetailMf11000 euDetailMf11000) {
		getEuDetailMf11000s().add(euDetailMf11000);
		euDetailMf11000.setEuMembreFondateur11000(this);

		return euDetailMf11000;
	}

	public EuDetailMf11000 removeEuDetailMf11000(EuDetailMf11000 euDetailMf11000) {
		getEuDetailMf11000s().remove(euDetailMf11000);
		euDetailMf11000.setEuMembreFondateur11000(null);

		return euDetailMf11000;
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
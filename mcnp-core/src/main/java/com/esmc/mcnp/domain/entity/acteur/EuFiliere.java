package com.esmc.mcnp.model.acteur;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.model.others.EuStandProduit;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_filiere database table.
 * 
 */
@Entity
@Table(name="eu_filiere")
@NamedQuery(name="EuFiliere.findAll", query="SELECT e FROM EuFiliere e")
public class EuFiliere implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idFiliere;
	private String codeDivision;
	private Date dateCreation;
	private String descripFiliere;
	private String nomFiliere;
	private List<EuActeursCreneaux> euActeursCreneauxs;
	private EuUtilisateur euUtilisateur;
	private List<EuStandProduit> euStandProduits;

	public EuFiliere() {
	}


	@Id
	@Column(name="id_filiere", unique=true, nullable=false)
	public Long getIdFiliere() {
		return this.idFiliere;
	}

	public void setIdFiliere(Long idFiliere) {
		this.idFiliere = idFiliere;
	}


	@Column(name="code_division", length=100)
	public String getCodeDivision() {
		return this.codeDivision;
	}

	public void setCodeDivision(String codeDivision) {
		this.codeDivision = codeDivision;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_creation")
	public Date getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	@Column(name="descrip_filiere", length=200)
	public String getDescripFiliere() {
		return this.descripFiliere;
	}

	public void setDescripFiliere(String descripFiliere) {
		this.descripFiliere = descripFiliere;
	}


	@Column(name="nom_filiere", length=100)
	public String getNomFiliere() {
		return this.nomFiliere;
	}

	public void setNomFiliere(String nomFiliere) {
		this.nomFiliere = nomFiliere;
	}


	//bi-directional many-to-one association to EuActeursCreneaux
	@JsonIgnore
	@OneToMany(mappedBy="euFiliere", fetch = FetchType.LAZY)
	public List<EuActeursCreneaux> getEuActeursCreneauxs() {
		return this.euActeursCreneauxs;
	}

	public void setEuActeursCreneauxs(List<EuActeursCreneaux> euActeursCreneauxs) {
		this.euActeursCreneauxs = euActeursCreneauxs;
	}

	public EuActeursCreneaux addEuActeursCreneaux(EuActeursCreneaux euActeursCreneaux) {
		getEuActeursCreneauxs().add(euActeursCreneaux);
		euActeursCreneaux.setEuFiliere(this);

		return euActeursCreneaux;
	}

	public EuActeursCreneaux removeEuActeursCreneaux(EuActeursCreneaux euActeursCreneaux) {
		getEuActeursCreneauxs().remove(euActeursCreneaux);
		euActeursCreneaux.setEuFiliere(null);

		return euActeursCreneaux;
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


	//bi-directional many-to-one association to EuStandProduit
	@JsonIgnore
	@OneToMany(mappedBy="euFiliere", fetch = FetchType.LAZY)
	public List<EuStandProduit> getEuStandProduits() {
		return this.euStandProduits;
	}

	public void setEuStandProduits(List<EuStandProduit> euStandProduits) {
		this.euStandProduits = euStandProduits;
	}

	public EuStandProduit addEuStandProduit(EuStandProduit euStandProduit) {
		getEuStandProduits().add(euStandProduit);
		euStandProduit.setEuFiliere(this);

		return euStandProduit;
	}

	public EuStandProduit removeEuStandProduit(EuStandProduit euStandProduit) {
		getEuStandProduits().remove(euStandProduit);
		euStandProduit.setEuFiliere(null);

		return euStandProduit;
	}

}
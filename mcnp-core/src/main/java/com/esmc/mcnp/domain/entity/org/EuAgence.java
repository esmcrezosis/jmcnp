package com.esmc.mcnp.model.org;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esmc.mcnp.model.others.EuSubSecteur;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the eu_agence database table.
 *
 */
@Entity
@Table(name = "eu_agence")
@NamedQuery(name = "EuAgence.findAll", query = "SELECT e FROM EuAgence e")
public class EuAgence implements Serializable {

	private static final long serialVersionUID = 1L;
	private String codeAgence;
	private String codeMembre;
	private Date dateCreation;
	private String libelleAgence;
	private Integer partenaire;
	private String typeGac;
	private Integer idPays;
	private Integer idRegion;
	private Integer idPrefecture;
	private Integer idCanton;
	private List<EuMembre> euMembres;
	private List<EuMembreMorale> euMembreMorales;
	private List<EuSubSecteur> euSubSecteurs;
	private List<EuUtilisateur> euUtilisateurs;

	public EuAgence() {
	}

	@Id
	@Column(name = "code_agence", unique = true, nullable = false, length = 100)
	public String getCodeAgence() {
		return this.codeAgence;
	}

	public void setCodeAgence(String codeAgence) {
		this.codeAgence = codeAgence;
	}

	@Column(name = "code_membre", length = 100)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_creation")
	public Date getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Column(name = "libelle_agence", length = 250)
	public String getLibelleAgence() {
		return this.libelleAgence;
	}

	public void setLibelleAgence(String libelleAgence) {
		this.libelleAgence = libelleAgence;
	}

	public Integer getPartenaire() {
		return this.partenaire;
	}

	public void setPartenaire(Integer partenaire) {
		this.partenaire = partenaire;
	}

	@Column(name = "type_gac", length = 100)
	public String getTypeGac() {
		return this.typeGac;
	}

	public void setTypeGac(String typeGac) {
		this.typeGac = typeGac;
	}

	@Column(name = "id_pays")
	public Integer getIdPays() {
		return idPays;
	}

	public void setIdPays(Integer idPays) {
		this.idPays = idPays;
	}

	@Column(name = "id_region")
	public Integer getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(Integer idRegion) {
		this.idRegion = idRegion;
	}

	@Column(name = "id_prefecture")
	public Integer getIdPrefecture() {
		return idPrefecture;
	}

	public void setIdPrefecture(Integer idPrefecture) {
		this.idPrefecture = idPrefecture;
	}

	@Column(name = "id_canton")
	public Integer getIdCanton() {
		return idCanton;
	}

	public void setIdCanton(Integer idCanton) {
		this.idCanton = idCanton;
	}

	// bi-directional many-to-one association to EuMembre
	@JsonIgnore
	@OneToMany(mappedBy = "euAgence")
	public List<EuMembre> getEuMembres() {
		return this.euMembres;
	}

	public void setEuMembres(List<EuMembre> euMembres) {
		this.euMembres = euMembres;
	}

	public EuMembre addEuMembre(EuMembre euMembre) {
		getEuMembres().add(euMembre);
		euMembre.setEuAgence(this);

		return euMembre;
	}

	public EuMembre removeEuMembre(EuMembre euMembre) {
		getEuMembres().remove(euMembre);
		euMembre.setEuAgence(null);

		return euMembre;
	}

	// bi-directional many-to-one association to EuMembreMorale
	@JsonIgnore
	@OneToMany(mappedBy = "euAgence")
	public List<EuMembreMorale> getEuMembreMorales() {
		return this.euMembreMorales;
	}

	public void setEuMembreMorales(List<EuMembreMorale> euMembreMorales) {
		this.euMembreMorales = euMembreMorales;
	}

	public EuMembreMorale addEuMembreMorale(EuMembreMorale euMembreMorale) {
		getEuMembreMorales().add(euMembreMorale);
		euMembreMorale.setEuAgence(this);

		return euMembreMorale;
	}

	public EuMembreMorale removeEuMembreMorale(EuMembreMorale euMembreMorale) {
		getEuMembreMorales().remove(euMembreMorale);
		euMembreMorale.setEuAgence(null);

		return euMembreMorale;
	}

	// bi-directional many-to-one association to EuSubSecteur
	@JsonIgnore
	@OneToMany(mappedBy = "euAgence")
	public List<EuSubSecteur> getEuSubSecteurs() {
		return this.euSubSecteurs;
	}

	public void setEuSubSecteurs(List<EuSubSecteur> euSubSecteurs) {
		this.euSubSecteurs = euSubSecteurs;
	}

	public EuSubSecteur addEuSubSecteur(EuSubSecteur euSubSecteur) {
		getEuSubSecteurs().add(euSubSecteur);
		euSubSecteur.setEuAgence(this);

		return euSubSecteur;
	}

	public EuSubSecteur removeEuSubSecteur(EuSubSecteur euSubSecteur) {
		getEuSubSecteurs().remove(euSubSecteur);
		euSubSecteur.setEuAgence(null);

		return euSubSecteur;
	}

	// bi-directional many-to-one association to EuUtilisateur
	@JsonIgnore
	@OneToMany(mappedBy = "euAgence")
	public List<EuUtilisateur> getEuUtilisateurs() {
		return this.euUtilisateurs;
	}

	public void setEuUtilisateurs(List<EuUtilisateur> euUtilisateurs) {
		this.euUtilisateurs = euUtilisateurs;
	}

	public EuUtilisateur addEuUtilisateur(EuUtilisateur euUtilisateur) {
		getEuUtilisateurs().add(euUtilisateur);
		euUtilisateur.setEuAgence(this);

		return euUtilisateur;
	}

	public EuUtilisateur removeEuUtilisateur(EuUtilisateur euUtilisateur) {
		getEuUtilisateurs().remove(euUtilisateur);
		euUtilisateur.setEuAgence(null);

		return euUtilisateur;
	}

}

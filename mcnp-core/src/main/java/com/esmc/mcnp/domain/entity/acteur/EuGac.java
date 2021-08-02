package com.esmc.mcnp.domain.entity.acteur;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.domain.entity.smcipn.EuSmcipn;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the eu_gac database table.
 *
 */
@Entity
@Table(name = "eu_gac")
@NamedQuery(name = "EuGac.findAll", query = "SELECT e FROM EuGac e")
public class EuGac implements Serializable {

	private static final long serialVersionUID = 1L;
	private String codeGac;
	private String codeAgence;
	private String codeGacChaine;
	private String codeGacCreate;
	private String codeGacZone;
	private String codeSecteur;
	private String codeZone;
	private Date dateCreation;
	private String groupe;
	private Integer idPays;
	private Integer idRegion;
	private String nomGac;
	private String typeGac;
	private String zone;
	private List<EuActeur> euActeurs;
	private EuTypeGac euTypeGac;
	private EuMembreMorale euMembreMorale;
	private EuMembre euMembre;
	private EuUtilisateur euUtilisateur;
	private List<EuGacFiliere> euGacFilieres;
	private List<EuSmcipn> euSmcipns;

	public EuGac() {
	}

	@Id
	@Column(name = "code_gac", unique = true, nullable = false, length = 100)
	public String getCodeGac() {
		return this.codeGac;
	}

	public void setCodeGac(String codeGac) {
		this.codeGac = codeGac;
	}

	@Column(name = "code_agence", length = 100)
	public String getCodeAgence() {
		return this.codeAgence;
	}

	public void setCodeAgence(String codeAgence) {
		this.codeAgence = codeAgence;
	}

	@Column(name = "code_gac_chaine", length = 255)
	public String getCodeGacChaine() {
		return this.codeGacChaine;
	}

	public void setCodeGacChaine(String codeGacChaine) {
		this.codeGacChaine = codeGacChaine;
	}

	@Column(name = "code_gac_create", length = 255)
	public String getCodeGacCreate() {
		return this.codeGacCreate;
	}

	public void setCodeGacCreate(String codeGacCreate) {
		this.codeGacCreate = codeGacCreate;
	}

	@Column(name = "code_gac_zone", length = 100)
	public String getCodeGacZone() {
		return this.codeGacZone;
	}

	public void setCodeGacZone(String codeGacZone) {
		this.codeGacZone = codeGacZone;
	}

	@Column(name = "code_secteur", length = 100)
	public String getCodeSecteur() {
		return this.codeSecteur;
	}

	public void setCodeSecteur(String codeSecteur) {
		this.codeSecteur = codeSecteur;
	}

	@Column(name = "code_zone", length = 100)
	public String getCodeZone() {
		return this.codeZone;
	}

	public void setCodeZone(String codeZone) {
		this.codeZone = codeZone;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_creation")
	public Date getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Column(length = 100)
	public String getGroupe() {
		return this.groupe;
	}

	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}

	@Column(name = "id_pays")
	public Integer getIdPays() {
		return this.idPays;
	}

	public void setIdPays(Integer idPays) {
		this.idPays = idPays;
	}

	@Column(name = "id_region")
	public Integer getIdRegion() {
		return this.idRegion;
	}

	public void setIdRegion(Integer idRegion) {
		this.idRegion = idRegion;
	}

	@Column(name = "nom_gac", length = 200)
	public String getNomGac() {
		return this.nomGac;
	}

	public void setNomGac(String nomGac) {
		this.nomGac = nomGac;
	}

	@Column(name = "type_gac", length = 100)
	public String getTypeGac() {
		return this.typeGac;
	}

	public void setTypeGac(String typeGac) {
		this.typeGac = typeGac;
	}

	@Column(length = 100)
	public String getZone() {
		return this.zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	// bi-directional many-to-one association to EuActeur
	@OneToMany(mappedBy = "euGac")
	public List<EuActeur> getEuActeurs() {
		return this.euActeurs;
	}

	public void setEuActeurs(List<EuActeur> euActeurs) {
		this.euActeurs = euActeurs;
	}

	public EuActeur addEuActeur(EuActeur euActeur) {
		getEuActeurs().add(euActeur);
		euActeur.setEuGac(this);

		return euActeur;
	}

	public EuActeur removeEuActeur(EuActeur euActeur) {
		getEuActeurs().remove(euActeur);
		euActeur.setEuGac(null);

		return euActeur;
	}

	// bi-directional many-to-one association to EuTypeGac
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_type_gac")
	public EuTypeGac getEuTypeGac() {
		return this.euTypeGac;
	}

	public void setEuTypeGac(EuTypeGac euTypeGac) {
		this.euTypeGac = euTypeGac;
	}

	// bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_membre")
	public EuMembreMorale getEuMembreMorale() {
		return this.euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}

	// bi-directional many-to-one association to EuMembre
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_membre_gestionnaire")
	public EuMembre getEuMembre() {
		return this.euMembre;
	}

	public void setEuMembre(EuMembre euMembre) {
		this.euMembre = euMembre;
	}

	// bi-directional many-to-one association to EuUtilisateur
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_utilisateur")
	public EuUtilisateur getEuUtilisateur() {
		return this.euUtilisateur;
	}

	public void setEuUtilisateur(EuUtilisateur euUtilisateur) {
		this.euUtilisateur = euUtilisateur;
	}

	// bi-directional many-to-one association to EuGacFiliere
	@OneToMany(mappedBy = "euGac")
	public List<EuGacFiliere> getEuGacFilieres() {
		return this.euGacFilieres;
	}

	public void setEuGacFilieres(List<EuGacFiliere> euGacFilieres) {
		this.euGacFilieres = euGacFilieres;
	}

	public EuGacFiliere addEuGacFiliere(EuGacFiliere euGacFiliere) {
		getEuGacFilieres().add(euGacFiliere);
		euGacFiliere.setEuGac(this);

		return euGacFiliere;
	}

	public EuGacFiliere removeEuGacFiliere(EuGacFiliere euGacFiliere) {
		getEuGacFilieres().remove(euGacFiliere);
		euGacFiliere.setEuGac(null);

		return euGacFiliere;
	}

	// bi-directional many-to-one association to EuSmcipn
	@OneToMany(mappedBy = "gac")
	public List<EuSmcipn> getEuSmcipns() {
		return this.euSmcipns;
	}

	public void setEuSmcipns(List<EuSmcipn> euSmcipns) {
		this.euSmcipns = euSmcipns;
	}

	public EuSmcipn addEuSmcipn(EuSmcipn euSmcipn) {
		getEuSmcipns().add(euSmcipn);
		euSmcipn.setGac(this);

		return euSmcipn;
	}

	public EuSmcipn removeEuSmcipn(EuSmcipn euSmcipn) {
		getEuSmcipns().remove(euSmcipn);
		euSmcipn.setGac(null);

		return euSmcipn;
	}

}

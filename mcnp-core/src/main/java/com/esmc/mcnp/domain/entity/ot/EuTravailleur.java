package com.esmc.mcnp.domain.entity.ot;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuTravailleur
 *
 */
@Entity
@Table(name = "eu_travailleur")
public class EuTravailleur implements Serializable {

	private Integer id;
	private String codeMembre;
	private String libelle;
	private String experience;
	private String type;
	private Date date;
	private String adresse;
	private String niveau;
	private String formation;
	private String education;
	private String observation;
	private Integer publier;
	private Integer canton;
	private Long utilisateur;
	private static final long serialVersionUID = 1L;

	public EuTravailleur() {
		super();
	}

	@Id
	@Column(name = "travailleur_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "travailleur_code_membre")
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Column(name = "travailleur_libelle")
	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Column(name = "travailleur_experience")
	public String getExperience() {
		return this.experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	@Column(name = "travailleur_type")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "travailleur_date")
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "travailleur_adresse")
	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	@Column(name = "travailleur_niveau")
	public String getNiveau() {
		return this.niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	@Column(name = "travailleur_formation")
	public String getFormation() {
		return this.formation;
	}

	public void setFormation(String formation) {
		this.formation = formation;
	}

	@Column(name = "travailleur_education")
	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	@Column(name = "travailleur_observation")
	public String getObservation() {
		return this.observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Integer getPublier() {
		return this.publier;
	}

	public void setPublier(Integer publier) {
		this.publier = publier;
	}

	@Column(name = "id_canton")
	public Integer getCanton() {
		return this.canton;
	}

	public void setCanton(Integer canton) {
		this.canton = canton;
	}

	@Column(name = "travailleur_utilisateur")
	public Long getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(Long utilisateur) {
		this.utilisateur = utilisateur;
	}

}

package com.esmc.mcnp.web.dto.ot;

import java.io.Serializable;
import java.util.Date;

import com.esmc.mcnp.domain.entity.ot.EuTravailleur;

public class Travailleur implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String codeMembre;
	private String formation;
	private String niveau;
	private String education;
	private String experience;
	private String libelle;
	private String type;
	private Date date;
	private String canton;

	public Travailleur(Integer id, String codeMembre, String formation, String niveau, String education,
			String experience, String libelle, String type, Date date, String canton) {
		super();
		this.id = id;
		this.codeMembre = codeMembre;
		this.formation = formation;
		this.niveau = niveau;
		this.education = education;
		this.experience = experience;
		this.libelle = libelle;
		this.type = type;
		this.date = date;
		this.canton = canton;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	public String getFormation() {
		return formation;
	}

	public void setFormation(String formation) {
		this.formation = formation;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCanton() {
		return canton;
	}

	public void setCanton(String canton) {
		this.canton = canton;
	}

	Travailleur fromEuTravailleur(EuTravailleur travailleur) {
		this.id = travailleur.getId();
		this.codeMembre = travailleur.getCodeMembre();
		this.date = travailleur.getDate();
		this.education = travailleur.getEducation();
		this.experience = travailleur.getExperience();
		this.formation = travailleur.getFormation();
		this.libelle = travailleur.getLibelle();
		this.niveau = travailleur.getNiveau();
		this.type = travailleur.getType();
		return this;
	}

}

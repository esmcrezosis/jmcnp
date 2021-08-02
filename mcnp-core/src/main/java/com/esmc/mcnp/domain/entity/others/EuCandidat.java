package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_candidat database table.
 * 
 */
@Entity
@Table(name="eu_candidat")
@NamedQuery(name="EuCandidat.findAll", query="SELECT e FROM EuCandidat e")
public class EuCandidat implements Serializable {
	private static final long serialVersionUID = 1L;
	private double candidatId;
	private String candidatAffiliation;
	private double candidatAttestation;
	private String candidatCompetence;
	private Date candidatDate;
	private Date candidatDatenaiss;
	private double candidatDocument;
	private String candidatEducation;
	private String candidatExperience;
	private String candidatFormation;
	private String candidatLangue;
	private String candidatNationalite;
	private String candidatNom;
	private String candidatPays;
	private String candidatPoste;
	private String candidatTache;
	private double publier;

	public EuCandidat() {
	}


	@Id
	@Column(name="candidat_id", unique=true, nullable=false)
	public double getCandidatId() {
		return this.candidatId;
	}

	public void setCandidatId(double candidatId) {
		this.candidatId = candidatId;
	}


	@Column(name="candidat_affiliation", length=150)
	public String getCandidatAffiliation() {
		return this.candidatAffiliation;
	}

	public void setCandidatAffiliation(String candidatAffiliation) {
		this.candidatAffiliation = candidatAffiliation;
	}


	@Column(name="candidat_attestation")
	public double getCandidatAttestation() {
		return this.candidatAttestation;
	}

	public void setCandidatAttestation(double candidatAttestation) {
		this.candidatAttestation = candidatAttestation;
	}


	@Column(name="candidat_competence", length=255)
	public String getCandidatCompetence() {
		return this.candidatCompetence;
	}

	public void setCandidatCompetence(String candidatCompetence) {
		this.candidatCompetence = candidatCompetence;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="candidat_date")
	public Date getCandidatDate() {
		return this.candidatDate;
	}

	public void setCandidatDate(Date candidatDate) {
		this.candidatDate = candidatDate;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="candidat_datenaiss")
	public Date getCandidatDatenaiss() {
		return this.candidatDatenaiss;
	}

	public void setCandidatDatenaiss(Date candidatDatenaiss) {
		this.candidatDatenaiss = candidatDatenaiss;
	}


	@Column(name="candidat_document")
	public double getCandidatDocument() {
		return this.candidatDocument;
	}

	public void setCandidatDocument(double candidatDocument) {
		this.candidatDocument = candidatDocument;
	}


	@Column(name="candidat_education", length=100)
	public String getCandidatEducation() {
		return this.candidatEducation;
	}

	public void setCandidatEducation(String candidatEducation) {
		this.candidatEducation = candidatEducation;
	}


	@Column(name="candidat_experience", length=200)
	public String getCandidatExperience() {
		return this.candidatExperience;
	}

	public void setCandidatExperience(String candidatExperience) {
		this.candidatExperience = candidatExperience;
	}


	@Column(name="candidat_formation", length=255)
	public String getCandidatFormation() {
		return this.candidatFormation;
	}

	public void setCandidatFormation(String candidatFormation) {
		this.candidatFormation = candidatFormation;
	}


	@Column(name="candidat_langue", length=255)
	public String getCandidatLangue() {
		return this.candidatLangue;
	}

	public void setCandidatLangue(String candidatLangue) {
		this.candidatLangue = candidatLangue;
	}


	@Column(name="candidat_nationalite", length=255)
	public String getCandidatNationalite() {
		return this.candidatNationalite;
	}

	public void setCandidatNationalite(String candidatNationalite) {
		this.candidatNationalite = candidatNationalite;
	}


	@Column(name="candidat_nom", length=255)
	public String getCandidatNom() {
		return this.candidatNom;
	}

	public void setCandidatNom(String candidatNom) {
		this.candidatNom = candidatNom;
	}


	@Column(name="candidat_pays", length=255)
	public String getCandidatPays() {
		return this.candidatPays;
	}

	public void setCandidatPays(String candidatPays) {
		this.candidatPays = candidatPays;
	}


	@Column(name="candidat_poste", length=255)
	public String getCandidatPoste() {
		return this.candidatPoste;
	}

	public void setCandidatPoste(String candidatPoste) {
		this.candidatPoste = candidatPoste;
	}


	@Column(name="candidat_tache", length=500)
	public String getCandidatTache() {
		return this.candidatTache;
	}

	public void setCandidatTache(String candidatTache) {
		this.candidatTache = candidatTache;
	}


	public double getPublier() {
		return this.publier;
	}

	public void setPublier(double publier) {
		this.publier = publier;
	}

}
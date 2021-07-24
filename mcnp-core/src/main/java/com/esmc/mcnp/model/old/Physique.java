package com.esmc.mcnp.model.old;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the physique database table.
 * 
 */
@Entity
@Table(name="physique")
@NamedQuery(name="Physique.findAll", query="SELECT p FROM Physique p")
public class Physique implements Serializable {
	private static final long serialVersionUID = 1L;
	private String numidentp;
	private String agence;
	private String bp;
	private String codeMembre;
	private Date dateident;
	private String datenais;
	private String email;
	private String emprunt;
	private double etatContrat;
	private String formation;
	private Date heurid;
	private String lieunais;
	private String mere;
	private String nationalite;
	private double nbrenf;
	private String nom;
	private double numcompbq;
	private String pere;
	private String photo;
	private String portable;
	private String prenom;
	private String prof;
	private String qartresid;
	private String religion;
	private String sexe;
	private String sitmatr;
	private String tel;
	private String user;
	private String ville;

	public Physique() {
	}


	@Id
	@Column(unique=true, nullable=false, length=30)
	public String getNumidentp() {
		return this.numidentp;
	}

	public void setNumidentp(String numidentp) {
		this.numidentp = numidentp;
	}


	@Column(length=200)
	public String getAgence() {
		return this.agence;
	}

	public void setAgence(String agence) {
		this.agence = agence;
	}


	@Lob
	public String getBp() {
		return this.bp;
	}

	public void setBp(String bp) {
		this.bp = bp;
	}


	@Column(name="code_membre", length=120)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getDateident() {
		return this.dateident;
	}

	public void setDateident(Date dateident) {
		this.dateident = dateident;
	}


	@Column(length=255)
	public String getDatenais() {
		return this.datenais;
	}

	public void setDatenais(String datenais) {
		this.datenais = datenais;
	}


	@Column(length=200)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Column(length=60)
	public String getEmprunt() {
		return this.emprunt;
	}

	public void setEmprunt(String emprunt) {
		this.emprunt = emprunt;
	}


	@Column(name="etat_contrat")
	public double getEtatContrat() {
		return this.etatContrat;
	}

	public void setEtatContrat(double etatContrat) {
		this.etatContrat = etatContrat;
	}


	@Lob
	public String getFormation() {
		return this.formation;
	}

	public void setFormation(String formation) {
		this.formation = formation;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getHeurid() {
		return this.heurid;
	}

	public void setHeurid(Date heurid) {
		this.heurid = heurid;
	}


	@Column(length=80)
	public String getLieunais() {
		return this.lieunais;
	}

	public void setLieunais(String lieunais) {
		this.lieunais = lieunais;
	}


	@Column(length=200)
	public String getMere() {
		return this.mere;
	}

	public void setMere(String mere) {
		this.mere = mere;
	}


	@Column(length=80)
	public String getNationalite() {
		return this.nationalite;
	}

	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}


	public double getNbrenf() {
		return this.nbrenf;
	}

	public void setNbrenf(double nbrenf) {
		this.nbrenf = nbrenf;
	}


	@Column(length=200)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}


	public double getNumcompbq() {
		return this.numcompbq;
	}

	public void setNumcompbq(double numcompbq) {
		this.numcompbq = numcompbq;
	}


	@Column(length=200)
	public String getPere() {
		return this.pere;
	}

	public void setPere(String pere) {
		this.pere = pere;
	}


	@Lob
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}


	@Column(length=44)
	public String getPortable() {
		return this.portable;
	}

	public void setPortable(String portable) {
		this.portable = portable;
	}


	@Column(length=200)
	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	@Lob
	public String getProf() {
		return this.prof;
	}

	public void setProf(String prof) {
		this.prof = prof;
	}


	@Column(length=200)
	public String getQartresid() {
		return this.qartresid;
	}

	public void setQartresid(String qartresid) {
		this.qartresid = qartresid;
	}


	@Lob
	public String getReligion() {
		return this.religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}


	@Column(length=36)
	public String getSexe() {
		return this.sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}


	@Column(length=100)
	public String getSitmatr() {
		return this.sitmatr;
	}

	public void setSitmatr(String sitmatr) {
		this.sitmatr = sitmatr;
	}


	@Column(length=200)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}


	@Lob
	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}


	@Lob
	public String getVille() {
		return this.ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

}
package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_document database table.
 * 
 */
@Entity
@Table(name="eu_document")
@NamedQuery(name="EuDocument.findAll", query="SELECT e FROM EuDocument e")
public class EuDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idDocument;
	private double accord;
	private Date dateCreation;
	private Date dateDebut;
	private Date dateFin;
	private String descripDocument;
	private double idTypeDocument;
	private double idUtilisateur;
	private String nomDocument;
	private String numAppeloffres;
	private double publier;

	public EuDocument() {
	}


	@Id
	@Column(name="id_document", unique=true, nullable=false)
	public double getIdDocument() {
		return this.idDocument;
	}

	public void setIdDocument(double idDocument) {
		this.idDocument = idDocument;
	}


	public double getAccord() {
		return this.accord;
	}

	public void setAccord(double accord) {
		this.accord = accord;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_creation")
	public Date getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_debut")
	public Date getDateDebut() {
		return this.dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_fin")
	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}


	@Column(name="descrip_document", length=255)
	public String getDescripDocument() {
		return this.descripDocument;
	}

	public void setDescripDocument(String descripDocument) {
		this.descripDocument = descripDocument;
	}


	@Column(name="id_type_document")
	public double getIdTypeDocument() {
		return this.idTypeDocument;
	}

	public void setIdTypeDocument(double idTypeDocument) {
		this.idTypeDocument = idTypeDocument;
	}


	@Column(name="id_utilisateur")
	public double getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(double idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	@Column(name="nom_document", length=600)
	public String getNomDocument() {
		return this.nomDocument;
	}

	public void setNomDocument(String nomDocument) {
		this.nomDocument = nomDocument;
	}


	@Column(name="num_appeloffres", length=45)
	public String getNumAppeloffres() {
		return this.numAppeloffres;
	}

	public void setNumAppeloffres(String numAppeloffres) {
		this.numAppeloffres = numAppeloffres;
	}


	public double getPublier() {
		return this.publier;
	}

	public void setPublier(double publier) {
		this.publier = publier;
	}

}
package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_fichier database table.
 * 
 */
@Entity
@Table(name="eu_fichier")
@NamedQuery(name="EuFichier.findAll", query="SELECT e FROM EuFichier e")
public class EuFichier implements Serializable {
	private static final long serialVersionUID = 1L;
	private double fichierId;
	private double fichierCategorie;
	private String fichierLibelle;
	private String fichierType;
	private String fichierUrl;
	private double publier;

	public EuFichier() {
	}


	@Id
	@Column(name="fichier_id", unique=true, nullable=false)
	public double getFichierId() {
		return this.fichierId;
	}

	public void setFichierId(double fichierId) {
		this.fichierId = fichierId;
	}


	@Column(name="fichier_categorie")
	public double getFichierCategorie() {
		return this.fichierCategorie;
	}

	public void setFichierCategorie(double fichierCategorie) {
		this.fichierCategorie = fichierCategorie;
	}


	@Column(name="fichier_libelle", length=255)
	public String getFichierLibelle() {
		return this.fichierLibelle;
	}

	public void setFichierLibelle(String fichierLibelle) {
		this.fichierLibelle = fichierLibelle;
	}


	@Column(name="fichier_type", length=20)
	public String getFichierType() {
		return this.fichierType;
	}

	public void setFichierType(String fichierType) {
		this.fichierType = fichierType;
	}


	@Column(name="fichier_url", length=255)
	public String getFichierUrl() {
		return this.fichierUrl;
	}

	public void setFichierUrl(String fichierUrl) {
		this.fichierUrl = fichierUrl;
	}


	public double getPublier() {
		return this.publier;
	}

	public void setPublier(double publier) {
		this.publier = publier;
	}

}
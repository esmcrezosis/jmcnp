package com.esmc.mcnp.model.old;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the membres database table.
 * 
 */
@Entity
@Table(name="membres")
@NamedQuery(name="Membres.findAll", query="SELECT m FROM Membres m")
public class Membres implements Serializable {
	private static final long serialVersionUID = 1L;
	private double id;
	private double age;
	private String nom;
	private String prenom;
	private String traitement;

	public Membres() {
	}


	@Id
	@Column(unique=true, nullable=false)
	public double getId() {
		return this.id;
	}

	public void setId(double id) {
		this.id = id;
	}


	public double getAge() {
		return this.age;
	}

	public void setAge(double age) {
		this.age = age;
	}


	@Column(length=255)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}


	@Column(length=255)
	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	@Column(length=255)
	public String getTraitement() {
		return this.traitement;
	}

	public void setTraitement(String traitement) {
		this.traitement = traitement;
	}

}
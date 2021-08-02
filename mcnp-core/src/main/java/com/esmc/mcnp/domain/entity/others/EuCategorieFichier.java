package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_categorie_fichier database table.
 * 
 */
@Entity
@Table(name="eu_categorie_fichier")
@NamedQuery(name="EuCategorieFichier.findAll", query="SELECT e FROM EuCategorieFichier e")
public class EuCategorieFichier implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idCategorieFichier;
	private String libelleCategorieFichier;

	public EuCategorieFichier() {
	}


	@Id
	@Column(name="id_categorie_fichier", unique=true, nullable=false)
	public double getIdCategorieFichier() {
		return this.idCategorieFichier;
	}

	public void setIdCategorieFichier(double idCategorieFichier) {
		this.idCategorieFichier = idCategorieFichier;
	}


	@Column(name="libelle_categorie_fichier", length=100)
	public String getLibelleCategorieFichier() {
		return this.libelleCategorieFichier;
	}

	public void setLibelleCategorieFichier(String libelleCategorieFichier) {
		this.libelleCategorieFichier = libelleCategorieFichier;
	}

}
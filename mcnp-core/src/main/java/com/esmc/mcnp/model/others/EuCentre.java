package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_centre database table.
 * 
 */
@Entity
@Table(name="eu_centre")
@NamedQuery(name="EuCentre.findAll", query="SELECT e FROM EuCentre e")
public class EuCentre implements Serializable {
	private static final long serialVersionUID = 1L;
	private double centreId;
	private String centreDescription;
	private String centreLibelle;
	private String centreQuartier;
	private String centreVille;
	private double idPays;
	private double publier;

	public EuCentre() {
	}


	@Id
	@Column(name="centre_id", unique=true, nullable=false)
	public double getCentreId() {
		return this.centreId;
	}

	public void setCentreId(double centreId) {
		this.centreId = centreId;
	}


	@Column(name="centre_description", length=255)
	public String getCentreDescription() {
		return this.centreDescription;
	}

	public void setCentreDescription(String centreDescription) {
		this.centreDescription = centreDescription;
	}


	@Column(name="centre_libelle", length=100)
	public String getCentreLibelle() {
		return this.centreLibelle;
	}

	public void setCentreLibelle(String centreLibelle) {
		this.centreLibelle = centreLibelle;
	}


	@Column(name="centre_quartier", length=25)
	public String getCentreQuartier() {
		return this.centreQuartier;
	}

	public void setCentreQuartier(String centreQuartier) {
		this.centreQuartier = centreQuartier;
	}


	@Column(name="centre_ville", length=25)
	public String getCentreVille() {
		return this.centreVille;
	}

	public void setCentreVille(String centreVille) {
		this.centreVille = centreVille;
	}


	@Column(name="id_pays")
	public double getIdPays() {
		return this.idPays;
	}

	public void setIdPays(double idPays) {
		this.idPays = idPays;
	}


	public double getPublier() {
		return this.publier;
	}

	public void setPublier(double publier) {
		this.publier = publier;
	}

}
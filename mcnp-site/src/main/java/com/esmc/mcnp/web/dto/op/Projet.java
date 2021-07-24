package com.esmc.mcnp.web.dto.op;

import java.io.Serializable;
import java.util.Date;

public class Projet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String codeMembre;
	private String libelle;
	private String description;
	private String type;
	private String centrale;
	private Date date;
	private Double montant;
	private Double montantFinal;
	private String observation;
	private Integer publier;
	private Long utilisateur;
	private Integer canton;
	private String stockage;

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

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCentrale() {
		return centrale;
	}

	public void setCentrale(String centrale) {
		this.centrale = centrale;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public Double getMontantFinal() {
		return montantFinal;
	}

	public void setMontantFinal(Double montantFinal) {
		this.montantFinal = montantFinal;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Integer getPublier() {
		return publier;
	}

	public void setPublier(Integer publier) {
		this.publier = publier;
	}

	public Long getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Long utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Integer getCanton() {
		return canton;
	}

	public void setCanton(Integer canton) {
		this.canton = canton;
	}

	public String getStockage() {
		return stockage;
	}

	public void setStockage(String stockage) {
		this.stockage = stockage;
	}

}

package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_factures database table.
 * 
 */
@Entity
@Table(name="eu_factures")
@NamedQuery(name="EuFactures.findAll", query="SELECT e FROM EuFactures e")
public class EuFactures implements Serializable {
	private static final long serialVersionUID = 1L;
	private double factureId;
	private String factureCodeMembre;
	private Date factureDate;
	private double factureMontant;
	private String factureNumero;
	private double factureUtilisateur;
	private double publier;

	public EuFactures() {
	}


	@Id
	@Column(name="facture_id", unique=true, nullable=false)
	public double getFactureId() {
		return this.factureId;
	}

	public void setFactureId(double factureId) {
		this.factureId = factureId;
	}


	@Column(name="facture_code_membre", length=25)
	public String getFactureCodeMembre() {
		return this.factureCodeMembre;
	}

	public void setFactureCodeMembre(String factureCodeMembre) {
		this.factureCodeMembre = factureCodeMembre;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="facture_date")
	public Date getFactureDate() {
		return this.factureDate;
	}

	public void setFactureDate(Date factureDate) {
		this.factureDate = factureDate;
	}


	@Column(name="facture_montant")
	public double getFactureMontant() {
		return this.factureMontant;
	}

	public void setFactureMontant(double factureMontant) {
		this.factureMontant = factureMontant;
	}


	@Column(name="facture_numero", length=50)
	public String getFactureNumero() {
		return this.factureNumero;
	}

	public void setFactureNumero(String factureNumero) {
		this.factureNumero = factureNumero;
	}


	@Column(name="facture_utilisateur")
	public double getFactureUtilisateur() {
		return this.factureUtilisateur;
	}

	public void setFactureUtilisateur(double factureUtilisateur) {
		this.factureUtilisateur = factureUtilisateur;
	}


	public double getPublier() {
		return this.publier;
	}

	public void setPublier(double publier) {
		this.publier = publier;
	}

}
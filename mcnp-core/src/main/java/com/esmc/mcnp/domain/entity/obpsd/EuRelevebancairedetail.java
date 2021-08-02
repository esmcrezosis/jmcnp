package com.esmc.mcnp.model.obpsd;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_relevebancairedetail database table.
 * 
 */
@Entity
@Table(name="eu_relevebancairedetail")
@NamedQuery(name="EuRelevebancairedetail.findAll", query="SELECT e FROM EuRelevebancairedetail e")
public class EuRelevebancairedetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private int relevebancairedetailId;
	private int publier;
	private Date relevebancairedetailDate;
	private String relevebancairedetailDateValeur;
	private String relevebancairedetailLibelle;
	private String relevebancairedetailMontant;
	private String relevebancairedetailNumero;
	private int relevebancairedetailRelevebancaire;
	private Long idAchatFranchises;

	public EuRelevebancairedetail() {
	}


	@Id
	@Column(name="relevebancairedetail_id")
	public int getRelevebancairedetailId() {
		return this.relevebancairedetailId;
	}

	public void setRelevebancairedetailId(int relevebancairedetailId) {
		this.relevebancairedetailId = relevebancairedetailId;
	}


	public int getPublier() {
		return this.publier;
	}

	public void setPublier(int publier) {
		this.publier = publier;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="relevebancairedetail_date")
	public Date getRelevebancairedetailDate() {
		return this.relevebancairedetailDate;
	}

	public void setRelevebancairedetailDate(Date relevebancairedetailDate) {
		this.relevebancairedetailDate = relevebancairedetailDate;
	}


	@Column(name="relevebancairedetail_date_valeur")
	public String getRelevebancairedetailDateValeur() {
		return this.relevebancairedetailDateValeur;
	}

	public void setRelevebancairedetailDateValeur(String relevebancairedetailDateValeur) {
		this.relevebancairedetailDateValeur = relevebancairedetailDateValeur;
	}


	@Column(name="relevebancairedetail_libelle")
	public String getRelevebancairedetailLibelle() {
		return this.relevebancairedetailLibelle;
	}

	public void setRelevebancairedetailLibelle(String relevebancairedetailLibelle) {
		this.relevebancairedetailLibelle = relevebancairedetailLibelle;
	}


	@Column(name="relevebancairedetail_montant")
	public String getRelevebancairedetailMontant() {
		return this.relevebancairedetailMontant;
	}

	public void setRelevebancairedetailMontant(String relevebancairedetailMontant) {
		this.relevebancairedetailMontant = relevebancairedetailMontant;
	}


	@Column(name="relevebancairedetail_numero")
	public String getRelevebancairedetailNumero() {
		return this.relevebancairedetailNumero;
	}

	public void setRelevebancairedetailNumero(String relevebancairedetailNumero) {
		this.relevebancairedetailNumero = relevebancairedetailNumero;
	}


	@Column(name="relevebancairedetail_relevebancaire")
	public int getRelevebancairedetailRelevebancaire() {
		return this.relevebancairedetailRelevebancaire;
	}

	public void setRelevebancairedetailRelevebancaire(int relevebancairedetailRelevebancaire) {
		this.relevebancairedetailRelevebancaire = relevebancairedetailRelevebancaire;
	}


	@Column(name = "id_achat_franchises")
	public Long getIdAchatFranchises() {
		return idAchatFranchises;
	}


	public void setIdAchatFranchises(Long idAchatFranchises) {
		this.idAchatFranchises = idAchatFranchises;
	}

}
package com.esmc.mcnp.model.pc;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_releveechange database table.
 * 
 */
@Entity
@Table(name="eu_releveechange")
@NamedQuery(name="EuReleveechange.findAll", query="SELECT e FROM EuReleveechange e")
public class EuReleveechange implements Serializable {
	private static final long serialVersionUID = 1L;
	private int releveechangeId;
	private byte publier;
	private Date releveechangeDate;
	private int releveechangeEchange;
	private int releveechangeMontant;
	private String releveechangeProduit;
	private int releveechangeReleve;

	public EuReleveechange() {
	}


	@Id
	@Column(name="releveechange_id")
	public int getReleveechangeId() {
		return this.releveechangeId;
	}

	public void setReleveechangeId(int releveechangeId) {
		this.releveechangeId = releveechangeId;
	}


	public byte getPublier() {
		return this.publier;
	}

	public void setPublier(byte publier) {
		this.publier = publier;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="releveechange_date")
	public Date getReleveechangeDate() {
		return this.releveechangeDate;
	}

	public void setReleveechangeDate(Date releveechangeDate) {
		this.releveechangeDate = releveechangeDate;
	}


	@Column(name="releveechange_echange")
	public int getReleveechangeEchange() {
		return this.releveechangeEchange;
	}

	public void setReleveechangeEchange(int releveechangeEchange) {
		this.releveechangeEchange = releveechangeEchange;
	}


	@Column(name="releveechange_montant")
	public int getReleveechangeMontant() {
		return this.releveechangeMontant;
	}

	public void setReleveechangeMontant(int releveechangeMontant) {
		this.releveechangeMontant = releveechangeMontant;
	}


	@Column(name="releveechange_produit")
	public String getReleveechangeProduit() {
		return this.releveechangeProduit;
	}

	public void setReleveechangeProduit(String releveechangeProduit) {
		this.releveechangeProduit = releveechangeProduit;
	}


	@Column(name="releveechange_releve")
	public int getReleveechangeReleve() {
		return this.releveechangeReleve;
	}

	public void setReleveechangeReleve(int releveechangeReleve) {
		this.releveechangeReleve = releveechangeReleve;
	}

}
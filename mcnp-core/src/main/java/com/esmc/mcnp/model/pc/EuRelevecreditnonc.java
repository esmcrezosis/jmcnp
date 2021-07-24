package com.esmc.mcnp.model.pc;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_relevecreditnonc database table.
 * 
 */
@Entity
@Table(name="eu_relevecreditnonc")
@NamedQuery(name="EuRelevecreditnonc.findAll", query="SELECT e FROM EuRelevecreditnonc e")
public class EuRelevecreditnonc implements Serializable {
	private static final long serialVersionUID = 1L;
	private int relevecreditnoncId;
	private byte publier;
	private int relevecreditnoncCreditnonc;
	private Date relevecreditnoncDate;
	private int relevecreditnoncMontant;
	private String relevecreditnoncProduit;
	private int relevecreditnoncReleve;

	public EuRelevecreditnonc() {
	}


	@Id
	@Column(name="relevecreditnonc_id")
	public int getRelevecreditnoncId() {
		return this.relevecreditnoncId;
	}

	public void setRelevecreditnoncId(int relevecreditnoncId) {
		this.relevecreditnoncId = relevecreditnoncId;
	}


	public byte getPublier() {
		return this.publier;
	}

	public void setPublier(byte publier) {
		this.publier = publier;
	}


	@Column(name="relevecreditnonc_creditnonc")
	public int getRelevecreditnoncCreditnonc() {
		return this.relevecreditnoncCreditnonc;
	}

	public void setRelevecreditnoncCreditnonc(int relevecreditnoncCreditnonc) {
		this.relevecreditnoncCreditnonc = relevecreditnoncCreditnonc;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="relevecreditnonc_date")
	public Date getRelevecreditnoncDate() {
		return this.relevecreditnoncDate;
	}

	public void setRelevecreditnoncDate(Date relevecreditnoncDate) {
		this.relevecreditnoncDate = relevecreditnoncDate;
	}


	@Column(name="relevecreditnonc_montant")
	public int getRelevecreditnoncMontant() {
		return this.relevecreditnoncMontant;
	}

	public void setRelevecreditnoncMontant(int relevecreditnoncMontant) {
		this.relevecreditnoncMontant = relevecreditnoncMontant;
	}


	@Column(name="relevecreditnonc_produit")
	public String getRelevecreditnoncProduit() {
		return this.relevecreditnoncProduit;
	}

	public void setRelevecreditnoncProduit(String relevecreditnoncProduit) {
		this.relevecreditnoncProduit = relevecreditnoncProduit;
	}


	@Column(name="relevecreditnonc_releve")
	public int getRelevecreditnoncReleve() {
		return this.relevecreditnoncReleve;
	}

	public void setRelevecreditnoncReleve(int relevecreditnoncReleve) {
		this.relevecreditnoncReleve = relevecreditnoncReleve;
	}

}
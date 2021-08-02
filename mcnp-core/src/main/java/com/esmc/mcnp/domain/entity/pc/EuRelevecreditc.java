package com.esmc.mcnp.domain.entity.pc;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_relevecreditc database table.
 * 
 */
@Entity
@Table(name="eu_relevecreditc")
@NamedQuery(name="EuRelevecreditc.findAll", query="SELECT e FROM EuRelevecreditc e")
public class EuRelevecreditc implements Serializable {
	private static final long serialVersionUID = 1L;
	private int relevecreditcId;
	private byte publier;
	private int relevecreditcCreditc;
	private Date relevecreditcDate;
	private int relevecreditcMontant;
	private String relevecreditcProduit;
	private int relevecreditcReleve;

	public EuRelevecreditc() {
	}


	@Id
	@Column(name="relevecreditc_id")
	public int getRelevecreditcId() {
		return this.relevecreditcId;
	}

	public void setRelevecreditcId(int relevecreditcId) {
		this.relevecreditcId = relevecreditcId;
	}


	public byte getPublier() {
		return this.publier;
	}

	public void setPublier(byte publier) {
		this.publier = publier;
	}


	@Column(name="relevecreditc_creditc")
	public int getRelevecreditcCreditc() {
		return this.relevecreditcCreditc;
	}

	public void setRelevecreditcCreditc(int relevecreditcCreditc) {
		this.relevecreditcCreditc = relevecreditcCreditc;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="relevecreditc_date")
	public Date getRelevecreditcDate() {
		return this.relevecreditcDate;
	}

	public void setRelevecreditcDate(Date relevecreditcDate) {
		this.relevecreditcDate = relevecreditcDate;
	}


	@Column(name="relevecreditc_montant")
	public int getRelevecreditcMontant() {
		return this.relevecreditcMontant;
	}

	public void setRelevecreditcMontant(int relevecreditcMontant) {
		this.relevecreditcMontant = relevecreditcMontant;
	}


	@Column(name="relevecreditc_produit")
	public String getRelevecreditcProduit() {
		return this.relevecreditcProduit;
	}

	public void setRelevecreditcProduit(String relevecreditcProduit) {
		this.relevecreditcProduit = relevecreditcProduit;
	}


	@Column(name="relevecreditc_releve")
	public int getRelevecreditcReleve() {
		return this.relevecreditcReleve;
	}

	public void setRelevecreditcReleve(int relevecreditcReleve) {
		this.relevecreditcReleve = relevecreditcReleve;
	}

}
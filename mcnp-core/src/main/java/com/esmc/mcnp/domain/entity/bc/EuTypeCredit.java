package com.esmc.mcnp.domain.entity.bc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_type_credit database table.
 *
 */
@Entity
@Table(name = "eu_type_credit")
@NamedQuery(name = "EuTypeCredit.findAll", query = "SELECT e FROM EuTypeCredit e")
public class EuTypeCredit implements Serializable {

	private static final long serialVersionUID = 1L;
	private String codeTypeCredit;
	private String codeCatProduit;
	private String libTypeCredit;
	private Double quotanr;
	private Double quotar;
	private Double prk;
	private String typeProduit;

	public EuTypeCredit() {
	}

	@Id
	@Column(name = "code_type_credit", unique = true, nullable = false, length = 60)
	public String getCodeTypeCredit() {
		return this.codeTypeCredit;
	}

	public void setCodeTypeCredit(String codeTypeCredit) {
		this.codeTypeCredit = codeTypeCredit;
	}

	@Column(name = "code_cat_produit", nullable = false, length = 30)
	public String getCodeCatProduit() {
		return this.codeCatProduit;
	}

	public void setCodeCatProduit(String codeCatProduit) {
		this.codeCatProduit = codeCatProduit;
	}

	@Column(name = "lib_type_credit", length = 250)
	public String getLibTypeCredit() {
		return this.libTypeCredit;
	}

	public void setLibTypeCredit(String libTypeCredit) {
		this.libTypeCredit = libTypeCredit;
	}

	public Double getQuotanr() {
		return this.quotanr;
	}

	public void setQuotanr(Double quotanr) {
		this.quotanr = quotanr;
	}

	public Double getQuotar() {
		return this.quotar;
	}

	public void setQuotar(Double quotar) {
		this.quotar = quotar;
	}

	public Double getPrk() {
		return prk;
	}

	public void setPrk(Double prk) {
		this.prk = prk;
	}

	@Column(name = "type_produit")
	public String getTypeProduit() {
		return typeProduit;
	}

	public void setTypeProduit(String typeProduit) {
		this.typeProduit = typeProduit;
	}

}

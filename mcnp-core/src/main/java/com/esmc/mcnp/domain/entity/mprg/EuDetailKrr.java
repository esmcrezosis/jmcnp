package com.esmc.mcnp.domain.entity.mprg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_detail_krr database table.
 * 
 */
@Entity
@Table(name="eu_detail_krr")
@NamedQuery(name="EuDetailKrr.findAll", query="SELECT e FROM EuDetailKrr e")
public class EuDetailKrr implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idDetailKrr;
	private Long idCredit;
	private Long idKrr;
	private double montCredit;
	private String sourceCredit;
	private int annuler;

	public EuDetailKrr() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_detail_krr")
	public Long getIdDetailKrr() {
		return this.idDetailKrr;
	}

	public void setIdDetailKrr(Long idDetailKrr) {
		this.idDetailKrr = idDetailKrr;
	}


	@Column(name="id_credit")
	public Long getIdCredit() {
		return this.idCredit;
	}

	public void setIdCredit(Long idCredit) {
		this.idCredit = idCredit;
	}


	@Column(name="id_krr")
	public Long getIdKrr() {
		return this.idKrr;
	}

	public void setIdKrr(Long idKrr) {
		this.idKrr = idKrr;
	}


	@Column(name="mont_credit")
	public double getMontCredit() {
		return this.montCredit;
	}

	public void setMontCredit(double montCredit) {
		this.montCredit = montCredit;
	}


	@Column(name="source_credit")
	public String getSourceCredit() {
		return this.sourceCredit;
	}

	public void setSourceCredit(String sourceCredit) {
		this.sourceCredit = sourceCredit;
	}

	@Column(name = "annuler")
	public int getAnnuler() {
		return annuler;
	}

	public void setAnnuler(int annuler) {
		this.annuler = annuler;
	}

}
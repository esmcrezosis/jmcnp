package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_detail_facturation database table.
 * 
 */
@Entity
@Table(name="eu_detail_facturation")
@NamedQuery(name="EuDetailFacturation.findAll", query="SELECT e FROM EuDetailFacturation e")
public class EuDetailFacturation implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idDetailFacturation;
	private String codeCompte;
	private String codeMembre;
	private String creditcode;
	private Date dateFacturation;
	private double idCnp;
	private double idOperation;
	private double montFacturation;

	public EuDetailFacturation() {
	}


	@Id
	@Column(name="id_detail_facturation", unique=true, nullable=false)
	public double getIdDetailFacturation() {
		return this.idDetailFacturation;
	}

	public void setIdDetailFacturation(double idDetailFacturation) {
		this.idDetailFacturation = idDetailFacturation;
	}


	@Column(name="code_compte", length=180)
	public String getCodeCompte() {
		return this.codeCompte;
	}

	public void setCodeCompte(String codeCompte) {
		this.codeCompte = codeCompte;
	}


	@Column(name="code_membre", length=180)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Column(length=32)
	public String getCreditcode() {
		return this.creditcode;
	}

	public void setCreditcode(String creditcode) {
		this.creditcode = creditcode;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_facturation")
	public Date getDateFacturation() {
		return this.dateFacturation;
	}

	public void setDateFacturation(Date dateFacturation) {
		this.dateFacturation = dateFacturation;
	}


	@Column(name="id_cnp", nullable=false)
	public double getIdCnp() {
		return this.idCnp;
	}

	public void setIdCnp(double idCnp) {
		this.idCnp = idCnp;
	}


	@Column(name="id_operation")
	public double getIdOperation() {
		return this.idOperation;
	}

	public void setIdOperation(double idOperation) {
		this.idOperation = idOperation;
	}


	@Column(name="mont_facturation")
	public double getMontFacturation() {
		return this.montFacturation;
	}

	public void setMontFacturation(double montFacturation) {
		this.montFacturation = montFacturation;
	}

}
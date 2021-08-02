package com.esmc.mcnp.domain.entity.bc;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_credit_codebarre database table.
 * 
 */
@Entity
@Table(name="eu_credit_codebarre")
@NamedQuery(name="EuCreditCodebarre.findAll", query="SELECT e FROM EuCreditCodebarre e")
public class EuCreditCodebarre implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idCodebarre;
	private String codeTypeCredit;
	private String codebarre;
	private Date dategeneration;
	private Date dategenerer;
	private double idCredit;
	private String identBenificiaire;
	private String immatBenef;
	private double imprimer;
	private Date printdate;

	public EuCreditCodebarre() {
	}


	@Id
	@Column(name="id_codebarre", unique=true, nullable=false)
	public double getIdCodebarre() {
		return this.idCodebarre;
	}

	public void setIdCodebarre(double idCodebarre) {
		this.idCodebarre = idCodebarre;
	}


	@Column(name="code_type_credit", length=255)
	public String getCodeTypeCredit() {
		return this.codeTypeCredit;
	}

	public void setCodeTypeCredit(String codeTypeCredit) {
		this.codeTypeCredit = codeTypeCredit;
	}


	@Column(length=255)
	public String getCodebarre() {
		return this.codebarre;
	}

	public void setCodebarre(String codebarre) {
		this.codebarre = codebarre;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getDategeneration() {
		return this.dategeneration;
	}

	public void setDategeneration(Date dategeneration) {
		this.dategeneration = dategeneration;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getDategenerer() {
		return this.dategenerer;
	}

	public void setDategenerer(Date dategenerer) {
		this.dategenerer = dategenerer;
	}


	@Column(name="id_credit")
	public double getIdCredit() {
		return this.idCredit;
	}

	public void setIdCredit(double idCredit) {
		this.idCredit = idCredit;
	}


	@Column(name="ident_benificiaire", length=255)
	public String getIdentBenificiaire() {
		return this.identBenificiaire;
	}

	public void setIdentBenificiaire(String identBenificiaire) {
		this.identBenificiaire = identBenificiaire;
	}


	@Column(name="immat_benef", length=255)
	public String getImmatBenef() {
		return this.immatBenef;
	}

	public void setImmatBenef(String immatBenef) {
		this.immatBenef = immatBenef;
	}


	public double getImprimer() {
		return this.imprimer;
	}

	public void setImprimer(double imprimer) {
		this.imprimer = imprimer;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getPrintdate() {
		return this.printdate;
	}

	public void setPrintdate(Date printdate) {
		this.printdate = printdate;
	}

}
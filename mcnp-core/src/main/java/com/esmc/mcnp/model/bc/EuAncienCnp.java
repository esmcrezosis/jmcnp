package com.esmc.mcnp.model.bc;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_ancien_cnp database table.
 * 
 */
@Entity
@Table(name="eu_ancien_cnp")
@NamedQuery(name="EuAncienCnp.findAll", query="SELECT e FROM EuAncienCnp e")
public class EuAncienCnp implements Serializable {
	private static final long serialVersionUID = 1L;
	private String idCnp;
	private String codeCapa;
	private String codeDomicilier;
	private Date dateCnp;
	private java.math.BigInteger idCredit;
	private double montCredit;
	private double montDebit;
	private String origineCnp;
	private double soldeCnp;
	private String sourceCredit;
	private byte transfertGcp;
	private String typeCnp;

	public EuAncienCnp() {
	}


	@Id
	@Column(name="id_cnp")
	public String getIdCnp() {
		return this.idCnp;
	}

	public void setIdCnp(String idCnp) {
		this.idCnp = idCnp;
	}


	@Column(name="code_capa")
	public String getCodeCapa() {
		return this.codeCapa;
	}

	public void setCodeCapa(String codeCapa) {
		this.codeCapa = codeCapa;
	}


	@Column(name="code_domicilier")
	public String getCodeDomicilier() {
		return this.codeDomicilier;
	}

	public void setCodeDomicilier(String codeDomicilier) {
		this.codeDomicilier = codeDomicilier;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_cnp")
	public Date getDateCnp() {
		return this.dateCnp;
	}

	public void setDateCnp(Date dateCnp) {
		this.dateCnp = dateCnp;
	}


	@Column(name="id_credit")
	public java.math.BigInteger getIdCredit() {
		return this.idCredit;
	}

	public void setIdCredit(java.math.BigInteger idCredit) {
		this.idCredit = idCredit;
	}


	@Column(name="mont_credit")
	public double getMontCredit() {
		return this.montCredit;
	}

	public void setMontCredit(double montCredit) {
		this.montCredit = montCredit;
	}


	@Column(name="mont_debit")
	public double getMontDebit() {
		return this.montDebit;
	}

	public void setMontDebit(double montDebit) {
		this.montDebit = montDebit;
	}


	@Column(name="origine_cnp")
	public String getOrigineCnp() {
		return this.origineCnp;
	}

	public void setOrigineCnp(String origineCnp) {
		this.origineCnp = origineCnp;
	}


	@Column(name="solde_cnp")
	public double getSoldeCnp() {
		return this.soldeCnp;
	}

	public void setSoldeCnp(double soldeCnp) {
		this.soldeCnp = soldeCnp;
	}


	@Column(name="source_credit")
	public String getSourceCredit() {
		return this.sourceCredit;
	}

	public void setSourceCredit(String sourceCredit) {
		this.sourceCredit = sourceCredit;
	}


	@Column(name="transfert_gcp")
	public byte getTransfertGcp() {
		return this.transfertGcp;
	}

	public void setTransfertGcp(byte transfertGcp) {
		this.transfertGcp = transfertGcp;
	}


	@Column(name="type_cnp")
	public String getTypeCnp() {
		return this.typeCnp;
	}

	public void setTypeCnp(String typeCnp) {
		this.typeCnp = typeCnp;
	}

}
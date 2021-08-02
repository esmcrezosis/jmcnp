package com.esmc.mcnp.domain.entity.bc;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the eu_ancien_cnnc database table.
 * 
 */
@Entity
@Table(name="eu_ancien_cnnc")
@NamedQuery(name="EuAncienCnnc.findAll", query="SELECT e FROM EuAncienCnnc e")
public class EuAncienCnnc implements Serializable {
	private static final long serialVersionUID = 1L;
	private String idCnnc;
	private String codeMembre;
	private Date datefin;
	private BigInteger idCredit;
	private String libelle;
	private double montCredit;
	private String sourceCredit;

	public EuAncienCnnc() {
	}


	@Id
	@Column(name="id_cnnc")
	public String getIdCnnc() {
		return this.idCnnc;
	}

	public void setIdCnnc(String idCnnc) {
		this.idCnnc = idCnnc;
	}


	@Column(name="code_membre")
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Temporal(TemporalType.DATE)
	public Date getDatefin() {
		return this.datefin;
	}

	public void setDatefin(Date datefin) {
		this.datefin = datefin;
	}


	@Column(name="id_credit")
	public BigInteger getIdCredit() {
		return this.idCredit;
	}

	public void setIdCredit(BigInteger idCredit) {
		this.idCredit = idCredit;
	}


	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
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

}
package com.esmc.mcnp.model.ba;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the eu_ancien_capa database table.
 * 
 */
@Entity
@Table(name="eu_ancien_capa")
@NamedQuery(name="EuAncienCapa.findAll", query="SELECT e FROM EuAncienCapa e")
public class EuAncienCapa implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeCapa;
	private String codeCompte;
	private String codeMembre;
	private String codeProduit;
	private Date dateCapa;
	private String etatCapa;
	private Time heureCapa;
	private BigInteger idCredit;
	private BigInteger idOperation;
	private double montantCapa;
	private String origineCapa;
	private String typeCapa;

	public EuAncienCapa() {
	}


	@Id
	@Column(name="code_capa")
	public String getCodeCapa() {
		return this.codeCapa;
	}

	public void setCodeCapa(String codeCapa) {
		this.codeCapa = codeCapa;
	}


	@Column(name="code_compte")
	public String getCodeCompte() {
		return this.codeCompte;
	}

	public void setCodeCompte(String codeCompte) {
		this.codeCompte = codeCompte;
	}


	@Column(name="code_membre")
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Column(name="code_produit")
	public String getCodeProduit() {
		return this.codeProduit;
	}

	public void setCodeProduit(String codeProduit) {
		this.codeProduit = codeProduit;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_capa")
	public Date getDateCapa() {
		return this.dateCapa;
	}

	public void setDateCapa(Date dateCapa) {
		this.dateCapa = dateCapa;
	}


	@Column(name="etat_capa")
	public String getEtatCapa() {
		return this.etatCapa;
	}

	public void setEtatCapa(String etatCapa) {
		this.etatCapa = etatCapa;
	}


	@Column(name="heure_capa")
	public Time getHeureCapa() {
		return this.heureCapa;
	}

	public void setHeureCapa(Time heureCapa) {
		this.heureCapa = heureCapa;
	}


	@Column(name="id_credit")
	public BigInteger getIdCredit() {
		return this.idCredit;
	}

	public void setIdCredit(BigInteger idCredit) {
		this.idCredit = idCredit;
	}


	@Column(name="id_operation")
	public BigInteger getIdOperation() {
		return this.idOperation;
	}

	public void setIdOperation(BigInteger idOperation) {
		this.idOperation = idOperation;
	}


	@Column(name="montant_capa")
	public double getMontantCapa() {
		return this.montantCapa;
	}

	public void setMontantCapa(double montantCapa) {
		this.montantCapa = montantCapa;
	}


	@Column(name="origine_capa")
	public String getOrigineCapa() {
		return this.origineCapa;
	}

	public void setOrigineCapa(String origineCapa) {
		this.origineCapa = origineCapa;
	}


	@Column(name="type_capa")
	public String getTypeCapa() {
		return this.typeCapa;
	}

	public void setTypeCapa(String typeCapa) {
		this.typeCapa = typeCapa;
	}

}
package com.esmc.mcnp.domain.entity.smcipn;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_ancien_fn database table.
 * 
 */
@Entity
@Table(name="eu_ancien_fn")
@NamedQuery(name="EuAncienFn.findAll", query="SELECT e FROM EuAncienFn e")
public class EuAncienFn implements Serializable {
	private static final long serialVersionUID = 1L;
	private String idFn;
	private String codeCapa;
	private Date dateFn;
	private double entree;
	private double montant;
	private double mtSolde;
	private double solde;
	private double sortie;
	private String typeFn;

	public EuAncienFn() {
	}


	@Id
	@Column(name="id_fn")
	public String getIdFn() {
		return this.idFn;
	}

	public void setIdFn(String idFn) {
		this.idFn = idFn;
	}


	@Column(name="code_capa")
	public String getCodeCapa() {
		return this.codeCapa;
	}

	public void setCodeCapa(String codeCapa) {
		this.codeCapa = codeCapa;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_fn")
	public Date getDateFn() {
		return this.dateFn;
	}

	public void setDateFn(Date dateFn) {
		this.dateFn = dateFn;
	}


	public double getEntree() {
		return this.entree;
	}

	public void setEntree(double entree) {
		this.entree = entree;
	}


	public double getMontant() {
		return this.montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}


	@Column(name="mt_solde")
	public double getMtSolde() {
		return this.mtSolde;
	}

	public void setMtSolde(double mtSolde) {
		this.mtSolde = mtSolde;
	}


	public double getSolde() {
		return this.solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}


	public double getSortie() {
		return this.sortie;
	}

	public void setSortie(double sortie) {
		this.sortie = sortie;
	}


	@Column(name="type_fn")
	public String getTypeFn() {
		return this.typeFn;
	}

	public void setTypeFn(String typeFn) {
		this.typeFn = typeFn;
	}

}
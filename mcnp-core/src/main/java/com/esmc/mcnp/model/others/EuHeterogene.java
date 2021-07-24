package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_heterogene database table.
 * 
 */
@Entity
@Table(name="eu_heterogene")
@NamedQuery(name="EuHeterogene.findAll", query="SELECT e FROM EuHeterogene e")
public class EuHeterogene implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeHetero;
	private String designationHetero;
	private double montant;
	private double montantUtilise;
	private double solde;

	public EuHeterogene() {
	}


	@Id
	@Column(name="code_hetero", unique=true, nullable=false, length=25)
	public String getCodeHetero() {
		return this.codeHetero;
	}

	public void setCodeHetero(String codeHetero) {
		this.codeHetero = codeHetero;
	}


	@Column(name="designation_hetero", length=30)
	public String getDesignationHetero() {
		return this.designationHetero;
	}

	public void setDesignationHetero(String designationHetero) {
		this.designationHetero = designationHetero;
	}


	public double getMontant() {
		return this.montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}


	@Column(name="montant_utilise")
	public double getMontantUtilise() {
		return this.montantUtilise;
	}

	public void setMontantUtilise(double montantUtilise) {
		this.montantUtilise = montantUtilise;
	}


	public double getSolde() {
		return this.solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}

}
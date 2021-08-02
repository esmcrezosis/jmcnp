package com.esmc.mcnp.domain.entity.bc;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "eu_credit_utiliser")
public class EuCreditUtiliser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String codeMembre;
	private String codeCompte;
	private String libelleOperation;
	private double montant;
	private LocalDateTime dateOperation;
	private String codeMembreBenef;

	public EuCreditUtiliser() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "code_membre")
	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Column(name = "code_compte")
	public String getCodeCompte() {
		return codeCompte;
	}

	public void setCodeCompte(String codeCompte) {
		this.codeCompte = codeCompte;
	}

	@Column(name = "libelle_operation")
	public String getLibelleOperation() {
		return libelleOperation;
	}

	public void setLibelleOperation(String libelleOperation) {
		this.libelleOperation = libelleOperation;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	@Column(name = "date_operation")
	public LocalDateTime getDateOperation() {
		return dateOperation;
	}

	public void setDateOperation(LocalDateTime dateOperation) {
		this.dateOperation = dateOperation;
	}

	@Column(name = "code_membre_benef")
	public String getCodeMembreBenef() {
		return codeMembreBenef;
	}

	public void setCodeMembreBenef(String codeMembreBenef) {
		this.codeMembreBenef = codeMembreBenef;
	}

}

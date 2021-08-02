package com.esmc.mcnp.domain.dto.desendettement;

import java.time.LocalDateTime;

public class ReglementWari {

	private LocalDateTime dateTransaction;
	private String numeroTransaction;
	private String matricule;
	private String etablissement;
	private Double montant;

	public ReglementWari() {
	}

	public ReglementWari(LocalDateTime dateTransaction, String numeroTransaction, String matricule,
			String etablissement, Double montant) {
		this.dateTransaction = dateTransaction;
		this.numeroTransaction = numeroTransaction;
		this.matricule = matricule;
		this.etablissement = etablissement;
		this.montant = montant;
	}

	public LocalDateTime getDateTransaction() {
		return dateTransaction;
	}

	public void setDateTransaction(LocalDateTime dateTransaction) {
		this.dateTransaction = dateTransaction;
	}

	public String getNumeroTransaction() {
		return numeroTransaction;
	}

	public void setNumeroTransaction(String numeroTransaction) {
		this.numeroTransaction = numeroTransaction;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getEtablissement() {
		return etablissement;
	}

	public void setEtablissement(String etablissement) {
		this.etablissement = etablissement;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

}

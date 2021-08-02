package com.esmc.mcnp.domain.entity.obpsd;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Long;
import java.lang.String;
import java.time.LocalDateTime;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuReglementWari
 *
 */
@Entity
@Table(name="eu_reglement_wari")
public class EuReglementWari implements Serializable {

	
	private Long id;
	private LocalDateTime datePaiement;
	private String numeroTransaction;
	private String matricule;
	private String etablissement;
	private Double montant;
	private static final long serialVersionUID = 1L;

	public EuReglementWari() {
		super();
	}   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	
	@Column(name = "date_paiement")
	public LocalDateTime getDatePaiement() {
		return this.datePaiement;
	}

	public void setDatePaiement(LocalDateTime datePaiement) {
		this.datePaiement = datePaiement;
	}   
	
	@Column(name = "numero_transaction")
	public String getNumeroTransaction() {
		return this.numeroTransaction;
	}

	public void setNumeroTransaction(String numeroTransaction) {
		this.numeroTransaction = numeroTransaction;
	}
	
	@Column(name = "matricule")
	public String getMatricule() {
		return this.matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}   
	
	@Column(name = "etablissement")
	public String getEtablissement() {
		return this.etablissement;
	}

	public void setEtablissement(String etablissement) {
		this.etablissement = etablissement;
	}
	
	@Column(name = "montant")
	public Double getMontant() {
		return this.montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}
   
}

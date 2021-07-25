package com.esmc.mcnp.model.pc;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuPaiement
 *
 */
@Entity
@Table(name = "eu_paiement")
public class EuPaiement implements Serializable {

	private Integer idPaiement;
	private Double montantPaiement;
	private Date datePaiement;
	private EuDemandePaiement euDemandePaiement;
	private String codeMembreEmploye;
	private Integer payer;
	private List<EuDetailPaiement> euDetailPaiements;
	private static final long serialVersionUID = 1L;

	public EuPaiement() {
		super();
	}

	@Id
	@Column(name = "id_paiement")
	public Integer getIdPaiement() {
		return this.idPaiement;
	}

	public void setIdPaiement(Integer idPaiement) {
		this.idPaiement = idPaiement;
	}

	@Column(name = "montant_paiement")
	public Double getMontantPaiement() {
		return this.montantPaiement;
	}

	public void setMontantPaiement(Double montantPaiement) {
		this.montantPaiement = montantPaiement;
	}

	@Column(name = "date_paiement")
	public Date getDatePaiement() {
		return this.datePaiement;
	}

	public void setDatePaiement(Date datePaiement) {
		this.datePaiement = datePaiement;
	}

	@ManyToOne
	@JoinColumn(name = "id_demande_paiement")
	public EuDemandePaiement getEuDemandePaiement() {
		return this.euDemandePaiement;
	}

	public void setEuDemandePaiement(EuDemandePaiement euDemandePaiement) {
		this.euDemandePaiement = euDemandePaiement;
	}

	@Column(name = "code_membre_employe")
	public String getCodeMembreEmploye() {
		return this.codeMembreEmploye;
	}

	public void setCodeMembreEmploye(String codeMembreEmploye) {
		this.codeMembreEmploye = codeMembreEmploye;
	}

	@Column(name = "payer")
	public Integer getPayer() {
		return payer;
	}

	public void setPayer(Integer payer) {
		this.payer = payer;
	}

	@OneToMany(mappedBy = "euPaiement")
	public List<EuDetailPaiement> getEuDetailPaiements() {
		return euDetailPaiements;
	}

	public void setEuDetailPaiements(List<EuDetailPaiement> euDetailPaiements) {
		this.euDetailPaiements = euDetailPaiements;
	}
}

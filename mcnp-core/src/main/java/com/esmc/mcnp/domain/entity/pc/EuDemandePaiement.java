package com.esmc.mcnp.domain.entity.pc;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.pc.EuPaiement;

/**
 * Entity implementation class for Entity: EuDemandePaiement
 *
 */
@Entity
@Table(name = "eu_demande_paiement")
public class EuDemandePaiement implements Serializable {

	private Integer idDemandePaiement;
	private String numeroDemandePaiement;
	private Double montantDemandePaiement;
	private Date dateDemandePaiement;
	private String codeMembreEmployeur;
	private Integer payer;
	private Date dateDeb;
	private Date dateFin;
	private String typeDemande;
	private String libelleTypeDemande;
	private List<EuPaiement> euPaiements;
	private static final long serialVersionUID = 1L;

	public EuDemandePaiement() {
		super();
	}

	@Id
	@Column(name = "id_demande_paiement")
	public Integer getIdDemandePaiement() {
		return this.idDemandePaiement;
	}

	public void setIdDemandePaiement(Integer idDemandePaiement) {
		this.idDemandePaiement = idDemandePaiement;
	}

	@Column(name = "numero_demande_paiement")
	public String getNumeroDemandePaiement() {
		return numeroDemandePaiement;
	}

	public void setNumeroDemandePaiement(String numeroDemandePaiement) {
		this.numeroDemandePaiement = numeroDemandePaiement;
	}

	@Column(name = "montant_demande_paiement")
	public Double getMontantDemandePaiement() {
		return this.montantDemandePaiement;
	}

	public void setMontantDemandePaiement(Double montantDemandePaiement) {
		this.montantDemandePaiement = montantDemandePaiement;
	}

	@Column(name = "date_demande_paiement")
	public Date getDateDemandePaiement() {
		return this.dateDemandePaiement;
	}

	public void setDateDemandePaiement(Date dateDemandePaiement) {
		this.dateDemandePaiement = dateDemandePaiement;
	}

	@Column(name = "code_membre_employeur")
	public String getCodeMembreEmployeur() {
		return this.codeMembreEmployeur;
	}

	public void setCodeMembreEmployeur(String codeMembreEmployeur) {
		this.codeMembreEmployeur = codeMembreEmployeur;
	}

	@Column(name = "payer")
	public Integer getPayer() {
		return this.payer;
	}

	public void setPayer(Integer payer) {
		this.payer = payer;
	}

	@Column(name = "date_debut")
	public Date getDateDeb() {
		return this.dateDeb;
	}

	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}

	@Column(name = "date_fin")
	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	@Column(name = "type_demande")
	public String getTypeDemande() {
		return this.typeDemande;
	}

	public void setTypeDemande(String typeDemande) {
		this.typeDemande = typeDemande;
	}

	@Column(name = "libelle_type_demande")
	public String getLibelleTypeDemande() {
		return libelleTypeDemande;
	}

	public void setLibelleTypeDemande(String libelleTypeDemande) {
		this.libelleTypeDemande = libelleTypeDemande;
	}

	@OneToMany(mappedBy = "euDemandePaiement")
	public List<EuPaiement> getEuPaiements() {
		return euPaiements;
	}

	public void setEuPaiements(List<EuPaiement> euPaiements) {
		this.euPaiements = euPaiements;
	}
}

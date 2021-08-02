package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="eu_proforma_commande")
@NamedQuery(name="EuProformaCommande.findAll", query="SELECT e FROM EuProformaCommande e")
public class EuProformaCommande implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idProforma;
	private String codeMembre;
	private String codeProforma;
	private Double montant;
	private Date dateProforma;
	private int delai;
	private int statut;
	private String codeMembreFournisseur;
	private String modePayement;
	private EuDevis euDevis;
	
	@Id
	@Column(name="id_proforma", unique=true, nullable=false)
	public Long getIdProforma() {
		return idProforma;
	}
	public void setIdProforma(Long idProforma) {
		this.idProforma = idProforma;
	}
	
	@Column(name="code_membre")
	public String getCodeMembre() {
		return codeMembre;
	}
	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}
	
	@Column(name="code_proforma")
	public String getCodeProforma() {
		return codeProforma;
	}
	public void setCodeProforma(String codeProforma) {
		this.codeProforma = codeProforma;
	}
	
	@Column(name="montant")
	public Double getMontant() {
		return montant;
	}
	public void setMontant(Double montant) {
		this.montant = montant;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_proforma")
	public Date getDateProforma() {
		return dateProforma;
	}
	public void setDateProforma(Date dateProforma) {
		this.dateProforma = dateProforma;
	}
	
	@Column(name="delai")
	public int getDelai() {
		return delai;
	}
	public void setDelai(int delai) {
		this.delai = delai;
	}
	
	@Column(name="statut")
	public int getStatut() {
		return statut;
	}
	public void setStatut(int statut) {
		this.statut = statut;
	}
	
	@Column(name="code_membre_fournisseur")
	public String getCodeMembreFournisseur() {
		return codeMembreFournisseur;
	}
	public void setCodeMembreFournisseur(String codeMembreFournisseur) {
		this.codeMembreFournisseur = codeMembreFournisseur;
	}
	
	@Column(name="mode_payement")
	public String getModePayement() {
		return modePayement;
	}
	public void setModePayement(String modePayement) {
		this.modePayement = modePayement;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "id_devis")
	public EuDevis getEuDevis() {
		return euDevis;
	}
	public void setEuDevis(EuDevis euDevis) {
		this.euDevis = euDevis;
	}


}

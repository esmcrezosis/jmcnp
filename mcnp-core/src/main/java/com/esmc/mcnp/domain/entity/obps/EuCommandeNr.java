package com.esmc.mcnp.domain.entity.obps;

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

import com.esmc.mcnp.domain.entity.others.EuProformaCommande;

@Entity
@Table(name="eu_commande_nr")
@NamedQuery(name="EuCommandeNr.findAll", query="SELECT e FROM EuCommandeNr e")
public class EuCommandeNr implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idCommandeNr;
	private String codeMembre;
	private String codeCommande;
	private Double montant;
	private Date dateCommandeNr;
	private Integer delai;
	private Integer statut;
	private String codeLivraison;
	private Date dateLivraison;
	private String codeMembreFournisseur;
	private EuProformaCommande euProformaCommande;
		

	@Id
	@Column(name="id_commande_nr", unique=true, nullable=false)
	public Long getIdCommandeNr() {
		return idCommandeNr;
	}
	public void setIdCommandeNr(Long idCommandeNr) {
		this.idCommandeNr = idCommandeNr;
	}
	@Column(name="code_membre", length=25)
	public String getCodeMembre() {
		return codeMembre;
	}
	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}
	@Column(name="code_commande", length=50)
	public String getCodeCommande() {
		return codeCommande;
	}
	public void setCodeCommande(String codeCommande) {
		this.codeCommande = codeCommande;
	}
	
	@Column(name="montant")
	public Double getMontant() {
		return montant;
	}
	public void setMontant(Double montant) {
		this.montant = montant;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_commande_nr")
	public Date getDateCommandeNr() {
		return dateCommandeNr;
	}
	public void setDateCommandeNr(Date dateCommandeNr) {
		this.dateCommandeNr = dateCommandeNr;
	}
	
	@Column(name="delai")
	public Integer getDelai() {
		return delai;
	}
	public void setDelai(Integer delai) {
		this.delai = delai;
	}
	
	@Column(name="statut")
	public Integer getStatut() {
		return statut;
	}
	public void setStatut(Integer statut) {
		this.statut = statut;
	}
	
	@Column(name="code_livraison")
	public String getCodeLivraison() {
		return codeLivraison;
	}
	public void setCodeLivraison(String codeLivraison) {
		this.codeLivraison = codeLivraison;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_livraison")
	public Date getDateLivraison() {
		return dateLivraison;
	}
	public void setDateLivraison(Date dateLivraison) {
		this.dateLivraison = dateLivraison;
	}
	
	@Column(name="code_membre_fournisseur")
	public String getCodeMembreFournisseur() {
		return codeMembreFournisseur;
	}
	public void setCodeMembreFournisseur(String codeMembreFournisseur) {
		this.codeMembreFournisseur = codeMembreFournisseur;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_proforma")
	public EuProformaCommande getEuProformaCommande() {
		return euProformaCommande;
	}
	public void setEuProformaCommande(EuProformaCommande euProformaCommande) {
		this.euProformaCommande = euProformaCommande;
	}
	
	
}

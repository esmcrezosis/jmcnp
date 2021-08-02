package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="eu_devis")
@NamedQuery(name="EuDevis.findAll", query="SELECT e FROM EuDevis e")
public class EuDevis implements Serializable {
	private static final long serialVersionUID = 1L;
 	private Long idDevis;
	private String codeMembre;
	private String codeDevis;
	private Double montant;
	private Date dateDevis;
	private int statut;
	private String codeMembreFournisseur;
	private String modePayement;
	
	@Id
	@Column(name="id_devis", unique=true, nullable=false)
	public Long getIdDevis() {
		return idDevis;
	}
	public void setIdDevis(Long idDevis) {
		this.idDevis = idDevis;
	}
	
	@Column(name="code_membre")
	public String getCodeMembre() {
		return codeMembre;
	}
	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}
	
	@Column(name="code_devis")
	public String getCodeDevis() {
		return codeDevis;
	}
	public void setCodeDevis(String codeDevis) {
		this.codeDevis = codeDevis;
	}
	
	@Column(name="montant")
	public Double getMontant() {
		return montant;
	}
	public void setMontant(Double montant) {
		this.montant = montant;
	}
	
	@Column(name="date_devis")
	public Date getDateDevis() {
		return dateDevis;
	}
	public void setDateDevis(Date dateDevis) {
		this.dateDevis = dateDevis;
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
	

}

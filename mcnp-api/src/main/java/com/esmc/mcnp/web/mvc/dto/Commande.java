package com.esmc.mcnp.web.mvc.dto;

public class Commande {
	private Long codeCommande;
	private String codeConfirmation;
	private String codeTegcAcheteur;
	private String codeTegcVendeur;
	private String typeBon;
	private Double montantAchat;
	private Long idUtilisateur;
	
	
	public Long getCodeCommande() {
		return codeCommande;
	}
	public void setCodeCommande(Long codeCommande) {
		this.codeCommande = codeCommande;
	}
	public String getCodeConfirmation() {
		return codeConfirmation;
	}
	public void setCodeConfirmation(String codeConfirmation) {
		this.codeConfirmation = codeConfirmation;
	}
	
	public String getCodeTegcAcheteur() {
		return codeTegcAcheteur;
	}
	public void setCodeTegcAcheteur(String codeTegcAcheteur) {
		this.codeTegcAcheteur = codeTegcAcheteur;
	}
	public String getCodeTegcVendeur() {
		return codeTegcVendeur;
	}
	public void setCodeTegcVendeur(String codeTegcVendeur) {
		this.codeTegcVendeur = codeTegcVendeur;
	}
	public String getTypeBon() {
		return typeBon;
	}
	public void setTypeBon(String typeBon) {
		this.typeBon = typeBon;
	}
	public Double getMontantAchat() {
		return montantAchat;
	}
	public void setMontantAchat(Double montantAchat) {
		this.montantAchat = montantAchat;
	}
	public Long getIdUtilisateur() {
		return idUtilisateur;
	}
	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}
	
	
	
	

	


}

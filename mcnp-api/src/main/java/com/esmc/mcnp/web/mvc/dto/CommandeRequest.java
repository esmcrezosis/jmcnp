package com.esmc.mcnp.web.mvc.dto;

import java.io.Serializable;

public class CommandeRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long codeCommande;
	private String codeConfirmation;
	private String codeTegcLivreur;
	private Long idUtilisateur;

	public CommandeRequest() {
	}

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

	public String getCodeTegcLivreur() {
		return codeTegcLivreur;
	}

	public void setCodeTegcLivreur(String codeTegcLivreur) {
		this.codeTegcLivreur = codeTegcLivreur;
	}

	public Long getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

}

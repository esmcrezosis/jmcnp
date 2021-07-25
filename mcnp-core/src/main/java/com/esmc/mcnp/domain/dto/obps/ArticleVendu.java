/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.esmc.mcnp.dto.obps;

/**
 *
 * @author Administrateur
 */
public class ArticleVendu {

	private String codeTegc;
	private String codeBarre;
	private String codeMembreAcheteur;
	private String reference;
	private String designation;
	private int quantite;
	private Double prix;

	public ArticleVendu() {
	}

	public ArticleVendu(String codeBarre, String codeMembreAcheteur, String reference, String designation, int quantite,
			Double prix) {
		this.codeBarre = codeBarre;
		this.codeMembreAcheteur = codeMembreAcheteur;
		this.reference = reference;
		this.designation = designation;
		this.quantite = quantite;
		this.prix = prix;
	}

	public ArticleVendu(String codeTegc, String codeBarre, String codeMembreAcheteur, String reference,
			String designation, int quantite, Double prix) {
		this.codeTegc = codeTegc;
		this.codeBarre = codeBarre;
		this.codeMembreAcheteur = codeMembreAcheteur;
		this.reference = reference;
		this.designation = designation;
		this.quantite = quantite;
		this.prix = prix;
	}

	public String getCodeTegc() {
		return codeTegc;
	}

	public void setCodeTegc(String codeTegc) {
		this.codeTegc = codeTegc;
	}

	public String getCodeBarre() {
		return codeBarre;
	}

	public void setCodeBarre(String codeBarre) {
		this.codeBarre = codeBarre;
	}

	public String getCodeMembreAcheteur() {
		return codeMembreAcheteur;
	}

	public void setCodeMembreAcheteur(String codeMembreAcheteur) {
		this.codeMembreAcheteur = codeMembreAcheteur;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public Double getPrix() {
		return prix;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

}

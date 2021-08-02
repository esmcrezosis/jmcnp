/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.esmc.mcnp.domain.dto.obps;

import java.util.List;

/**
 *
 * @author Administrateur
 */
public class ArticleVenduParTegc {

	private String codeTegc;
	private Double montantAchat;
	List<ArticleVendu> ListArticlesVendus;
	

	public ArticleVenduParTegc() {
	}


	public ArticleVenduParTegc(String codeTegc, Double montantAchat, List<ArticleVendu> listArticlesVendus) {
		super();
		this.codeTegc = codeTegc;
		this.montantAchat = montantAchat;
		ListArticlesVendus = listArticlesVendus;
	}


	public String getCodeTegc() {
		return codeTegc;
	}


	public void setCodeTegc(String codeTegc) {
		this.codeTegc = codeTegc;
	}


	public Double getMontantAchat() {
		return montantAchat;
	}


	public void setMontantAchat(Double montantAchat) {
		this.montantAchat = montantAchat;
	}


	public List<ArticleVendu> getListArticlesVendus() {
		return ListArticlesVendus;
	}


	public void setListArticlesVendus(List<ArticleVendu> listArticlesVendus) {
		ListArticlesVendus = listArticlesVendus;
	}

	
}

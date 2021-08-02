package com.esmc.mcnp.domain.entity.obps;

import java.io.Serializable;

import javax.persistence.*;
//import java.util.Date;

/**
 * The persistent class for the eu_article_stockes_categorie database table.
 * 
 */
@Entity
@Table(name = "eu_article_stockes_categorie")
@NamedQuery(name = "EuArticleStockesCategorie.findAll", query = "SELECT e FROM EuArticleStockesCategorie e")
public class EuArticleStockesCategorie implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idArticleStockesCategorie;
	private String nomArticleStockesCategorie;
	private String codeMembreMorale;
	private Integer etat;
	private String codeTegc;

	public EuArticleStockesCategorie() {
	}

	@Id
	@Column(name = "id_article_stockes_categorie")
	public Long getIdArticleStockesCategorie() {
		return idArticleStockesCategorie;
	}

	public void setIdArticleStockesCategorie(Long idArticleStockesCategorie) {
		this.idArticleStockesCategorie = idArticleStockesCategorie;
	}

	@Column(name = "nom_article_stockes_categorie")
	public String getNomArticleStockesCategorie() {
		return nomArticleStockesCategorie;
	}

	public void setNomArticleStockesCategorie(String nomArticleStockesCategorie) {
		this.nomArticleStockesCategorie = nomArticleStockesCategorie;
	}

	@Column(name = "code_membre_morale")
	public String getCodeMembreMorale() {
		return codeMembreMorale;
	}

	public void setCodeMembreMorale(String codeMembreMorale) {
		this.codeMembreMorale = codeMembreMorale;
	}

	public Integer getEtat() {
		return etat;
	}

	public void setEtat(Integer etat) {
		this.etat = etat;
	}

	@Column(name = "code_tegc")
	public String getCodeTegc() {
		return codeTegc;
	}

	public void setCodeTegc(String codeTegc) {
		this.codeTegc = codeTegc;
	}

}
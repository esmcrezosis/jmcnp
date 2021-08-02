package com.esmc.mcnp.domain.entity.obps;

import java.io.Serializable;
import javax.persistence.*;
//import java.util.Date;


/**
 * The persistent class for the eu_article_stockes_additif database table.
 * 
 */
@Entity
@Table(name="eu_article_stockes_additif")
@NamedQuery(name="EuArticleStockesAdditif.findAll", query="SELECT e FROM EuArticleStockesAdditif e")
public class EuArticleStockesAdditif implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idArticleStockesAdditif;
	private String nomArticleStockesAdditif;
	private String reference;
	private String codeMembreMorale;
	private Integer etat;

	public EuArticleStockesAdditif() {
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Column(name = "id_article_stockes_additif")
	public Long getIdArticleStockesAdditif() {
		return idArticleStockesAdditif;
	}

	public void setIdArticleStockesAdditif(Long idArticleStockesAdditif) {
		this.idArticleStockesAdditif = idArticleStockesAdditif;
	}

	@Column(name = "nom_article_stockes_additif")
	public String getNomArticleStockesAdditif() {
		return nomArticleStockesAdditif;
	}

	public void setNomArticleStockesAdditif(String nomArticleStockesAdditif) {
		this.nomArticleStockesAdditif = nomArticleStockesAdditif;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
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

	
}
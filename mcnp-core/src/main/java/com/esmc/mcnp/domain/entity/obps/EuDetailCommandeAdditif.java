package com.esmc.mcnp.domain.entity.obps;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the eu_detail_commande_additif database table.
 * 
 */
@Entity
@Table(name = "eu_detail_commande_additif")
@NamedQuery(name = "EuDetailCommandeAdditif.findAll", query = "SELECT e FROM EuDetailCommandeAdditif e")
public class EuDetailCommandeAdditif implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idDetailCommandeAdditif;
	private String referenceAdditif;
	private EuDetailCommande euDetailCommande;
	private EuArticleStockesAdditif euArticleStockesAdditif;
	
	public EuDetailCommandeAdditif() {
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_detail_commande_additif", unique = true, nullable = false)
	public Long getIdDetailCommandeAdditif() {
		return idDetailCommandeAdditif;
	}

	public void setIdDetailCommandeAdditif(Long idDetailCommandeAdditif) {
		this.idDetailCommandeAdditif = idDetailCommandeAdditif;
	}

	@Column(name = "reference_additif")
	public String getReferenceAdditif() {
		return referenceAdditif;
	}

	public void setReferenceAdditif(String referenceAdditif) {
		this.referenceAdditif = referenceAdditif;
	}

	//bi-directional many-to-one association to EuDetailCommande
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_detail_commande")
	public EuDetailCommande getEuDetailCommande() {
		return euDetailCommande;
	}

	public void setEuDetailCommande(EuDetailCommande euDetailCommande) {
		this.euDetailCommande = euDetailCommande;
	}
	
	
	//bi-directional many-to-one association to EuArticleStockesAdditif
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_article_stockes_additif")
		
	public EuArticleStockesAdditif getEuArticleStockesAdditif() {
		return euArticleStockesAdditif;
	}

	public void setEuArticleStockesAdditif(EuArticleStockesAdditif euArticleStockesAdditif) {
		this.euArticleStockesAdditif = euArticleStockesAdditif;
	}

	

}
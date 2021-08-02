package com.esmc.mcnp.model.obps;

import com.esmc.mcnp.model.acteur.EuEli;
import com.esmc.mcnp.model.cm.EuMembreMorale;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
//import java.util.Date;


/**
 * The persistent class for the eu_article_stockes database table.
 * 
 */
@Entity
@Table(name="eu_article_stockes")
@NamedQuery(name="EuArticleStockes.findAll", query="SELECT e FROM EuArticleStockes e")
public class EuArticleStockes implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idArticleStocke;
	private String codeBarre;
	private String categorie;
	private Date dateEnregistrement;
	private String type;
	private String designation;
	private Double prix;
	private Integer qteStock;
	private int publier;
	private String reference;
	private Integer articleStockesCategorie;
	private EuMembreMorale euMembreMorale;
	private Integer qteVendu;
	private Integer qteSolde;
	private Double prixEli;
	private EuEli euEli;
	

	public EuArticleStockes() {
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Column(name = "id_article_stockes")
	public Long getIdArticleStocke() {
		return idArticleStocke;
	}

	public void setIdArticleStocke(Long idArticleStocke) {
		this.idArticleStocke = idArticleStocke;
	}
		
	@Column(name="code_barre", unique=true, nullable=false, length=255)
	public String getCodeBarre() {
		return this.codeBarre;
	}

	public void setCodeBarre(String codeBarre) {
		this.codeBarre = codeBarre;
	}

	@Column(length=255)
	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public Double getPrix() {
		return this.prix;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}


	public int getPublier() {
		return this.publier;
	}

	public void setPublier(int publier) {
		this.publier = publier;
	}


	@Column(length=255)
	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	@Column(name = "date_enregistrement")
	@Temporal(TemporalType.DATE)
	public Date getDateEnregistrement() {
		return dateEnregistrement;
	}

	public void setDateEnregistrement(Date dateEnregistrement) {
		this.dateEnregistrement = dateEnregistrement;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="qte_stock")
	public Integer getQteStock() {
		return qteStock;
	}

	public void setQteStock(Integer qteStock) {
		this.qteStock = qteStock;
	}

	@Column(name = "article_stockes_categorie")
	public Integer getArticleStockesCategorie() {
		return articleStockesCategorie;
	}

	public void setArticleStockesCategorie(Integer articleStockesCategorie) {
		this.articleStockesCategorie = articleStockesCategorie;
	}

	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre_morale")
	public EuMembreMorale getEuMembreMorale() {
		return this.euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}
	@Column(name="qte_vendu")
	public Integer getQteVendu() {
		return qteVendu;
	}

	public void setQteVendu(Integer qteVendu) {
		this.qteVendu = qteVendu;
	}
	@Column(name="qte_solde")
	public Integer getQteSolde() {
		return qteSolde;
	}

	public void setQteSolde(Integer qteSolde) {
		this.qteSolde = qteSolde;
	}
	
	@Column(name = "prix_eli")
	public Double getPrixEli() {
		return prixEli;
	}

	public void setPrixEli(Double prixEli) {
		this.prixEli = prixEli;
	}

	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_eli")
	public EuEli getEuEli() {
		return this.euEli;
	}

	public void setEuEli(EuEli euEli) {
		this.euEli = euEli;
	}

}
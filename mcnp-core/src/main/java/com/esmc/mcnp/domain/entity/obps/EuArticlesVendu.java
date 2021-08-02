package com.esmc.mcnp.domain.entity.obps;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.bc.EuBon;

import java.util.Date;

/**
 * The persistent class for the eu_articles_vendu database table.
 *
 */
@Entity
@Table(name = "eu_articles_vendu")
@NamedQuery(name = "EuArticlesVendu.findAll", query = "SELECT e FROM EuArticlesVendu e")
public class EuArticlesVendu implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idArticleVendu;
	private String codeBarre;
	private String codeMembreAcheteur;
	private Date dateVente;
	private String reference;
	private String designation;
	private int quantite;
	private double prixUnitaire;
	private String codeMembreVendeur;
	private EuBon euBon;

	public EuArticlesVendu() {
	}
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Column(name = "id_article_vendu")
	public Long getIdArticleVendu() {
		return idArticleVendu;
	}

	public void setIdArticleVendu(Long idArticleVendu) {
		this.idArticleVendu = idArticleVendu;
	}

	@Column(name = "code_barre", length = 255)
	public String getCodeBarre() {
		return this.codeBarre;
	}

	public void setCodeBarre(String codeBarre) {
		this.codeBarre = codeBarre;
	}

	@Column(name = "code_membre_acheteur", length = 255)
	public String getCodeMembreAcheteur() {
		return this.codeMembreAcheteur;
	}

	public void setCodeMembreAcheteur(String codeMembreAcheteur) {
		this.codeMembreAcheteur = codeMembreAcheteur;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_vente")
	public Date getDateVente() {
		return this.dateVente;
	}

	public void setDateVente(Date dateVente) {
		this.dateVente = dateVente;
	}

	@Column(name = "reference", length = 250)
	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Column(name = "designation", length = 255)
	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Column(name = "quantite")
	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	@Column(name = "prix_unitaire")
	public double getPrixUnitaire() {
		return prixUnitaire;
	}

	public void setPrixUnitaire(double prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}


	@Column(name = "code_membre_vendeur")
	public String getCodeMembreVendeur() {
		return codeMembreVendeur;
	}

	public void setCodeMembreVendeur(String codeMembreVendeur) {
		this.codeMembreVendeur = codeMembreVendeur;
	}

	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "bon_id")
	public EuBon getEuBon() {
		return euBon;
	}

	public void setEuBon(EuBon euBon) {
		this.euBon = euBon;
	}

}

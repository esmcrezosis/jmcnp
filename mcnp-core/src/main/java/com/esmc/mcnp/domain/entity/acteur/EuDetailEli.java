package com.esmc.mcnp.domain.entity.acteur;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_detail_eli database table.
 * 
 */
@Entity
@Table(name="eu_detail_eli")
@NamedQuery(name="EuDetailEli.findAll", query="SELECT e FROM EuDetailEli e")
public class EuDetailEli implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idDetailEli;
	private Integer idEli;
	private String libelleProduit;
	private Double montantProduit;
	private Double prixUnitaire;
	private Integer quantite;
	private Integer statut;
	private String typeBps;
	private Double qteVente;
	private Double prixVente;

	public EuDetailEli() {
	}


	@Id
	@Column(name="id_detail_eli")
	public Integer getIdDetailEli() {
		return this.idDetailEli;
	}

	public void setIdDetailEli(Integer idDetailEli) {
		this.idDetailEli = idDetailEli;
	}


	@Column(name="id_eli")
	public Integer getIdEli() {
		return this.idEli;
	}

	public void setIdEli(Integer idEli) {
		this.idEli = idEli;
	}


	@Column(name="libelle_produit")
	public String getLibelleProduit() {
		return this.libelleProduit;
	}

	public void setLibelleProduit(String libelleProduit) {
		this.libelleProduit = libelleProduit;
	}


	@Column(name="montant_produit")
	public Double getMontantProduit() {
		return this.montantProduit;
	}

	public void setMontantProduit(Double montantProduit) {
		this.montantProduit = montantProduit;
	}


	@Column(name="prix_unitaire")
	public Double getPrixUnitaire() {
		return this.prixUnitaire;
	}

	public void setPrixUnitaire(Double prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}


	public Integer getQuantite() {
		return this.quantite;
	}

	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}


	public Integer getStatut() {
		return this.statut;
	}

	public void setStatut(Integer statut) {
		this.statut = statut;
	}

    @Column(name = "type_bps")
	public String getTypeBps() {
		return typeBps;
	}


	public void setTypeBps(String typeBps) {
		this.typeBps = typeBps;
	}

    @Column(name = "qte_vente")
	public Double getQteVente() {
		return qteVente;
	}


	public void setQteVente(Double qteVente) {
		this.qteVente = qteVente;
	}

    @Column(name = "prix_vente")
	public Double getPrixVente() {
		return prixVente;
	}


	public void setPrixVente(Double prixVente) {
		this.prixVente = prixVente;
	}

}
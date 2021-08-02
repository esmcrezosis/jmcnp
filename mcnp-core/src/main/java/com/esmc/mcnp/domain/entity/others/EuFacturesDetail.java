package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_factures_detail database table.
 * 
 */
@Entity
@Table(name="eu_factures_detail")
@NamedQuery(name="EuFacturesDetail.findAll", query="SELECT e FROM EuFacturesDetail e")
public class EuFacturesDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private double factureDetailId;
	private String factureDetailLibelle;
	private double factureDetailPrixUnitaire;
	private double factureDetailQuantite;
	private String factureDetailReference;
	private double factureId;

	public EuFacturesDetail() {
	}


	@Id
	@Column(name="facture_detail_id", unique=true, nullable=false)
	public double getFactureDetailId() {
		return this.factureDetailId;
	}

	public void setFactureDetailId(double factureDetailId) {
		this.factureDetailId = factureDetailId;
	}


	@Column(name="facture_detail_libelle", length=100)
	public String getFactureDetailLibelle() {
		return this.factureDetailLibelle;
	}

	public void setFactureDetailLibelle(String factureDetailLibelle) {
		this.factureDetailLibelle = factureDetailLibelle;
	}


	@Column(name="facture_detail_prix_unitaire")
	public double getFactureDetailPrixUnitaire() {
		return this.factureDetailPrixUnitaire;
	}

	public void setFactureDetailPrixUnitaire(double factureDetailPrixUnitaire) {
		this.factureDetailPrixUnitaire = factureDetailPrixUnitaire;
	}


	@Column(name="facture_detail_quantite")
	public double getFactureDetailQuantite() {
		return this.factureDetailQuantite;
	}

	public void setFactureDetailQuantite(double factureDetailQuantite) {
		this.factureDetailQuantite = factureDetailQuantite;
	}


	@Column(name="facture_detail_reference", length=25)
	public String getFactureDetailReference() {
		return this.factureDetailReference;
	}

	public void setFactureDetailReference(String factureDetailReference) {
		this.factureDetailReference = factureDetailReference;
	}


	@Column(name="facture_id")
	public double getFactureId() {
		return this.factureId;
	}

	public void setFactureId(double factureId) {
		this.factureId = factureId;
	}

}
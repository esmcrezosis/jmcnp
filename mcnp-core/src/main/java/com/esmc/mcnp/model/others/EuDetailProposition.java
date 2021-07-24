package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_detail_proposition database table.
 * 
 */
@Entity
@Table(name="eu_detail_proposition")
@NamedQuery(name="EuDetailProposition.findAll", query="SELECT e FROM EuDetailProposition e")
public class EuDetailProposition implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idDetailProposition;
	private String appartenance;
	private String codeMembreMorale;
	private String libelleProduit;
	private double mdv;
	private double prixUnitaire;
	private double quantite;
	private String typeProduit;
	private String uniteMesure;
	private EuProposition euProposition;

	public EuDetailProposition() {
	}


	@Id
	@Column(name="id_detail_proposition", unique=true, nullable=false)
	public double getIdDetailProposition() {
		return this.idDetailProposition;
	}

	public void setIdDetailProposition(double idDetailProposition) {
		this.idDetailProposition = idDetailProposition;
	}


	@Column(length=50)
	public String getAppartenance() {
		return this.appartenance;
	}

	public void setAppartenance(String appartenance) {
		this.appartenance = appartenance;
	}


	@Column(name="code_membre_morale", length=50)
	public String getCodeMembreMorale() {
		return this.codeMembreMorale;
	}

	public void setCodeMembreMorale(String codeMembreMorale) {
		this.codeMembreMorale = codeMembreMorale;
	}


	@Column(name="libelle_produit", length=100)
	public String getLibelleProduit() {
		return this.libelleProduit;
	}

	public void setLibelleProduit(String libelleProduit) {
		this.libelleProduit = libelleProduit;
	}


	public double getMdv() {
		return this.mdv;
	}

	public void setMdv(double mdv) {
		this.mdv = mdv;
	}


	@Column(name="prix_unitaire")
	public double getPrixUnitaire() {
		return this.prixUnitaire;
	}

	public void setPrixUnitaire(double prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}


	public double getQuantite() {
		return this.quantite;
	}

	public void setQuantite(double quantite) {
		this.quantite = quantite;
	}


	@Column(name="type_produit", length=10)
	public String getTypeProduit() {
		return this.typeProduit;
	}

	public void setTypeProduit(String typeProduit) {
		this.typeProduit = typeProduit;
	}


	@Column(name="unite_mesure", length=50)
	public String getUniteMesure() {
		return this.uniteMesure;
	}

	public void setUniteMesure(String uniteMesure) {
		this.uniteMesure = uniteMesure;
	}


	//bi-directional many-to-one association to EuProposition
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_proposition")
	public EuProposition getEuProposition() {
		return this.euProposition;
	}

	public void setEuProposition(EuProposition euProposition) {
		this.euProposition = euProposition;
	}

}
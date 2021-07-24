package com.esmc.mcnp.model.acteur;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Integer;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuAvrDetailAchat
 *
 */
@Entity
@Table(name = "eu_avr_detail_achat")
public class EuAvrDetailAchat implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idDetailAchat;
	private String codeMembreFournisseur;
	private String designationProduit;
	private String referenceProduit;
	private String teFournisseur;
	private Integer qteAchat;
	private Integer qteDisponible;
	private Double prixTotalAchat;
	private EuAvrAchat euAvrAchat;

	public EuAvrDetailAchat() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_detail_achat")
	public Integer getIdDetailAchat() {
		return this.idDetailAchat;
	}

	public void setIdDetailAchat(Integer idDetailAchat) {
		this.idDetailAchat = idDetailAchat;
	}

	@Column(name = "code_membre_fournisseur")
	public String getCodeMembreFournisseur() {
		return this.codeMembreFournisseur;
	}

	public void setCodeMembreFournisseur(String codeMembreFournisseur) {
		this.codeMembreFournisseur = codeMembreFournisseur;
	}

	@Column(name = "designation_produit")
	public String getDesignationProduit() {
		return this.designationProduit;
	}

	public void setDesignationProduit(String designationProduit) {
		this.designationProduit = designationProduit;
	}

	@Column(name = "reference_produit")
	public String getReferenceProduit() {
		return this.referenceProduit;
	}

	public void setReferenceProduit(String referenceProduit) {
		this.referenceProduit = referenceProduit;
	}

	@Column(name = "te_fournisseur")
	public String getTeFournisseur() {
		return this.teFournisseur;
	}

	public void setTeFournisseur(String teFournisseur) {
		this.teFournisseur = teFournisseur;
	}

	@ManyToOne
	@JoinColumn(name = "id")
	public EuAvrAchat getEuAvrAchat() {
		return this.euAvrAchat;
	}

	public void setEuAvrAchat(EuAvrAchat euAvrAchat) {
		this.euAvrAchat = euAvrAchat;
	}

	@Column(name = "qte_achat")
	public Integer getQteAchat() {
		return this.qteAchat;
	}

	public void setQteAchat(Integer qteAchat) {
		this.qteAchat = qteAchat;
	}

	@Column(name = "qte_disponible")
	public Integer getQteDisponible() {
		return this.qteDisponible;
	}

	public void setQteDisponible(Integer qteDisponible) {
		this.qteDisponible = qteDisponible;
	}

	@Column(name = "prix_total_achat")
	public Double getPrixTotalAchat() {
		return this.prixTotalAchat;
	}

	public void setPrixTotalAchat(Double prixTotalAchat) {
		this.prixTotalAchat = prixTotalAchat;
	}

}

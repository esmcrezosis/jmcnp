package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_detail_demande_achat database table.
 * 
 */
@Entity
@Table(name="eu_detail_demande_achat")
@NamedQuery(name="EuDetailDemandeAchat.findAll", query="SELECT e FROM EuDetailDemandeAchat e")
public class EuDetailDemandeAchat implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idDetailDemandeAchat;
	private String designationArticle;
	private int prixUnitaire;
	private int quantite;
	private String referenceArticle;
	private int validation;
	private EuDemandeAchat euDemandeAchat;

	public EuDetailDemandeAchat() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_detail_demande_achat")
	public Long getIdDetailDemandeAchat() {
		return this.idDetailDemandeAchat;
	}

	public void setIdDetailDemandeAchat(Long idDetailDemandeAchat) {
		this.idDetailDemandeAchat = idDetailDemandeAchat;
	}


	@Column(name="designation_article")
	public String getDesignationArticle() {
		return this.designationArticle;
	}

	public void setDesignationArticle(String designationArticle) {
		this.designationArticle = designationArticle;
	}


	@Column(name="prix_unitaire")
	public int getPrixUnitaire() {
		return this.prixUnitaire;
	}

	public void setPrixUnitaire(int prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}


	public int getQuantite() {
		return this.quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}


	@Column(name="reference_article")
	public String getReferenceArticle() {
		return this.referenceArticle;
	}

	public void setReferenceArticle(String referenceArticle) {
		this.referenceArticle = referenceArticle;
	}


	public int getValidation() {
		return this.validation;
	}

	public void setValidation(int validation) {
		this.validation = validation;
	}


	//bi-directional many-to-one association to EuDemandeAchat
	@ManyToOne
	@JoinColumn(name="id_demande_achat")
	public EuDemandeAchat getEuDemandeAchat() {
		return this.euDemandeAchat;
	}

	public void setEuDemandeAchat(EuDemandeAchat euDemandeAchat) {
		this.euDemandeAchat = euDemandeAchat;
	}

}
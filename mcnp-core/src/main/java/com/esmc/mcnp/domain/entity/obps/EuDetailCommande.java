package com.esmc.mcnp.domain.entity.obps;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the eu_detail_commande database table.
 * 
 */
@Entity
@Table(name = "eu_detail_commande")
@NamedQuery(name = "EuDetailCommande.findAll", query = "SELECT e FROM EuDetailCommande e")
public class EuDetailCommande implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idDetailCommande;
	private String reference;
	private String designation;
	private Integer qte;
	private Double prixUnitaire;
	private Integer livrer;
	private Integer prepayer;
	private String codeBarre;
	private Double remise;
	private Integer commander;
	private EuCommande euCommande;
	
	public EuDetailCommande() {
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_detail_commande", unique = true, nullable = false)
	public Long getIdDetailCommande() {
		return idDetailCommande;
	}

	public void setIdDetailCommande(Long idDetailCommande) {
		this.idDetailCommande = idDetailCommande;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public Integer getQte() {
		return qte;
	}

	public void setQte(Integer qte) {
		this.qte = qte;
	}

	@Column(name = "prix_unitaire")
	public Double getPrixUnitaire() {
		return prixUnitaire;
	}

	public void setPrixUnitaire(Double prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}

	public Integer getLivrer() {
		return livrer;
	}

	public void setLivrer(Integer livrer) {
		this.livrer = livrer;
	}

	public Integer getPrepayer() {
		return prepayer;
	}

	public void setPrepayer(Integer prepayer) {
		this.prepayer = prepayer;
	}

	@Column(name = "code_barre")
	public String getCodeBarre() {
		return codeBarre;
	}

	public void setCodeBarre(String codeBarre) {
		this.codeBarre = codeBarre;
	}

	public Double getRemise() {
		return remise;
	}

	public void setRemise(Double remise) {
		this.remise = remise;
	}

	public Integer getCommander() {
		return commander;
	}

	public void setCommander(Integer commander) {
		this.commander = commander;
	}

	// bi-directional one-to-one association to EuCommande
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "code_commande")
	public EuCommande getEuCommande() {
		return this.euCommande;
	}

	public void setEuCommande(EuCommande euCommande) {
		this.euCommande = euCommande;
	}

}
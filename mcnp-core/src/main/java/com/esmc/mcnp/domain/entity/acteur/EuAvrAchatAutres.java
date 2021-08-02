package com.esmc.mcnp.domain.entity.acteur;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuAvrAchatAutres
 *
 */
@Entity
@Table(name = "eu_avr_achat_autre")
public class EuAvrAchatAutres implements Serializable {

	private Integer id;
	private String nomProduit;
	private String codeMembreAcheteur;
	private Date dateDemande;
	private String caracteristiqueProduit;
	private static final long serialVersionUID = 1L;

	public EuAvrAchatAutres() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "nom_produit")
	public String getNomProduit() {
		return this.nomProduit;
	}

	public void setNomProduit(String nomProduit) {
		this.nomProduit = nomProduit;
	}

	@Column(name = "code_membre_acheteur")
	public String getCodeMembreAcheteur() {
		return this.codeMembreAcheteur;
	}

	public void setCodeMembreAcheteur(String codeMembreAcheteur) {
		this.codeMembreAcheteur = codeMembreAcheteur;
	}

	@Column(name = "date_demande")
	public Date getDateDemande() {
		return this.dateDemande;
	}

	public void setDateDemande(Date dateDemande) {
		this.dateDemande = dateDemande;
	}

	@Column(name = "caracteristique_produit")
	public String getCaracteristiqueProduit() {
		return this.caracteristiqueProduit;
	}

	public void setCaracteristiqueProduit(String caracteristiqueProduit) {
		this.caracteristiqueProduit = caracteristiqueProduit;
	}

}

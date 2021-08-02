package com.esmc.mcnp.domain.entity.op;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuOffreurProjet
 *
 */
@Entity
@Table(name = "eu_offreur_projet")
public class EuOffreurProjet implements Serializable {

	private Integer id;
	private Long souscription;
	private String raisonSociale;
	private String adresse;
	private Integer type;
	private Date date;
	private String produit;
	private Integer publier;
	private Integer operationnel;
	private String capaciteProduction;
	private String stockDisponible;
	private String qteMax;
	private String qteMoyen;
	private String qteMin;
	private String descriptionProjet;
	private String codeMembre;
	private String canton;
	private String ville;
	private String fournisseur;
	private static final long serialVersionUID = 1L;

	public EuOffreurProjet() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "offreur_projet_id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "offreur_projet_souscription")
	public Long getSouscription() {
		return this.souscription;
	}

	public void setSouscription(Long souscription) {
		this.souscription = souscription;
	}

	@Column(name = "offreur_projet_raison_sociale")
	public String getRaisonSociale() {
		return this.raisonSociale;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}

	@Column(name = "offreur_projet_adresse")
	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	@Column(name = "offreur_projet_type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "offreur_projet_date")
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "offreur_projet_produit")
	public String getProduit() {
		return this.produit;
	}

	public void setProduit(String produit) {
		this.produit = produit;
	}

	@Column(name = "publier")
	public Integer getPublier() {
		return this.publier;
	}

	public void setPublier(Integer publier) {
		this.publier = publier;
	}

	@Column(name = "offreur_projet_operationnel")
	public Integer getOperationnel() {
		return this.operationnel;
	}

	public void setOperationnel(Integer operationnel) {
		this.operationnel = operationnel;
	}

	@Column(name = "offreur_projet_capacite_production")
	public String getCapaciteProduction() {
		return this.capaciteProduction;
	}

	public void setCapaciteProduction(String capaciteProduction) {
		this.capaciteProduction = capaciteProduction;
	}

	@Column(name = "offreur_projet_stock_disponible")
	public String getStockDisponible() {
		return this.stockDisponible;
	}

	public void setStockDisponible(String stockDisponible) {
		this.stockDisponible = stockDisponible;
	}

	@Column(name = "offreur_projet_qte_max")
	public String getQteMax() {
		return this.qteMax;
	}

	public void setQteMax(String qteMax) {
		this.qteMax = qteMax;
	}

	@Column(name = "offreur_projet_qte_moyen")
	public String getQteMoyen() {
		return this.qteMoyen;
	}

	public void setQteMoyen(String qteMoyen) {
		this.qteMoyen = qteMoyen;
	}

	@Column(name = "offreur_projet_qte_min")
	public String getQteMin() {
		return this.qteMin;
	}

	public void setQteMin(String qteMin) {
		this.qteMin = qteMin;
	}

	@Column(name = "offreur_projet_description_projet")
	public String getDescriptionProjet() {
		return this.descriptionProjet;
	}

	public void setDescriptionProjet(String descriptionProjet) {
		this.descriptionProjet = descriptionProjet;
	}

	@Column(name = "offreur_projet_code_membre")
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Column(name = "offreur_projet_canton")
	public String getCanton() {
		return this.canton;
	}

	public void setCanton(String canton) {
		this.canton = canton;
	}

	@Column(name = "offreur_projet_ville")
	public String getVille() {
		return this.ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	@Column(name = "offreur_projet_fournisseur")
	public String getFournisseur() {
		return this.fournisseur;
	}

	public void setFournisseur(String fournisseur) {
		this.fournisseur = fournisseur;
	}

}

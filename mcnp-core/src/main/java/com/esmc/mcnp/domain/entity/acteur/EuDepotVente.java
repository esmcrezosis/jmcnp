package com.esmc.mcnp.model.acteur;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.model.acteur.EuSouscription;
import com.esmc.mcnp.model.bc.EuProduit;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * The persistent class for the eu_depot_vente database table.
 * 
 */
@Entity
@Table(name = "eu_depot_vente")
@NamedQuery(name = "EuDepotVente.findAll", query = "SELECT e FROM EuDepotVente e")
public class EuDepotVente implements Serializable {
	private static final long serialVersionUID = 1L;
	private long idDepot;
	private String codeMembre;
	private Date dateDepot;
	private double montDepot;
	private double montVendu;
	private double soldeDepot;
	@JsonIgnore
	private EuUtilisateur euUtilisateur;
	@JsonIgnore
	private EuProduit euProduit;
	private String typeDepot;
	private Boolean payer;
	@JsonIgnore
	private EuSouscription euSouscription;

	public EuDepotVente() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_depot", unique = true, nullable = false)
	public long getIdDepot() {
		return this.idDepot;
	}

	public void setIdDepot(long idDepot) {
		this.idDepot = idDepot;
	}

	@Column(name = "code_membre", nullable = false, length = 180)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_depot", nullable = false)
	public Date getDateDepot() {
		return this.dateDepot;
	}

	public void setDateDepot(Date dateDepot) {
		this.dateDepot = dateDepot;
	}

	@Column(name = "mont_depot", nullable = false)
	public double getMontDepot() {
		return this.montDepot;
	}

	public void setMontDepot(double montDepot) {
		this.montDepot = montDepot;
	}

	@Column(name = "mont_vendu", nullable = false)
	public double getMontVendu() {
		return this.montVendu;
	}

	public void setMontVendu(double montVendu) {
		this.montVendu = montVendu;
	}

	@Column(name = "solde_depot", nullable = false)
	public double getSoldeDepot() {
		return this.soldeDepot;
	}

	public void setSoldeDepot(double soldeDepot) {
		this.soldeDepot = soldeDepot;
	}

	// bi-directional many-to-one association to EuUtilisateur
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_utilisateur", nullable = false)
	public EuUtilisateur getEuUtilisateur() {
		return this.euUtilisateur;
	}

	public void setEuUtilisateur(EuUtilisateur euUtilisateur) {
		this.euUtilisateur = euUtilisateur;
	}

	// bi-directional many-to-one association to EuProduit
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_produit", nullable = false)
	public EuProduit getEuProduit() {
		return this.euProduit;
	}

	public void setEuProduit(EuProduit euProduit) {
		this.euProduit = euProduit;
	}

	@Column(name = "type_depot")
	public String getTypeDepot() {
		return typeDepot;
	}

	public void setTypeDepot(String typeDepot) {
		this.typeDepot = typeDepot;
	}

	public Boolean getPayer() {
		return payer;
	}

	public void setPayer(Boolean payer) {
		this.payer = payer;
	}

	@OneToOne
	@JoinColumn(name = "souscription_id")
	public EuSouscription getEuSouscription() {
		return euSouscription;
	}

	public void setEuSouscription(EuSouscription euSouscription) {
		this.euSouscription = euSouscription;
	}

}
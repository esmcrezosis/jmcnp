package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.obps.EuCommande;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;


/**
 * The persistent class for the eu_facture database table.
 * 
 */
@Entity
@Table(name="eu_facture")
@NamedQuery(name="EuFacture.findAll", query="SELECT e FROM EuFacture e")
public class EuFacture implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeFacture;
	private Date dateFacture;
	private double etatFacture;
	private double montantHt;
	private EuMembreMorale euMembreMorale1;
	private EuMembreMorale euMembreMorale2;
	private EuTaxe euTaxe;
	private EuCommande euCommande;
	private EuUtilisateur euUtilisateur;

	public EuFacture() {
	}


	@Id
	@Column(name="code_facture", unique=true, nullable=false, length=100)
	public String getCodeFacture() {
		return this.codeFacture;
	}

	public void setCodeFacture(String codeFacture) {
		this.codeFacture = codeFacture;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_facture", nullable=false)
	public Date getDateFacture() {
		return this.dateFacture;
	}

	public void setDateFacture(Date dateFacture) {
		this.dateFacture = dateFacture;
	}


	@Column(name="etat_facture")
	public double getEtatFacture() {
		return this.etatFacture;
	}

	public void setEtatFacture(double etatFacture) {
		this.etatFacture = etatFacture;
	}


	@Column(name="montant_ht")
	public double getMontantHt() {
		return this.montantHt;
	}

	public void setMontantHt(double montantHt) {
		this.montantHt = montantHt;
	}


	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre_fournisseur")
	public EuMembreMorale getEuMembreMorale1() {
		return this.euMembreMorale1;
	}

	public void setEuMembreMorale1(EuMembreMorale euMembreMorale1) {
		this.euMembreMorale1 = euMembreMorale1;
	}


	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre_client", nullable=false)
	public EuMembreMorale getEuMembreMorale2() {
		return this.euMembreMorale2;
	}

	public void setEuMembreMorale2(EuMembreMorale euMembreMorale2) {
		this.euMembreMorale2 = euMembreMorale2;
	}


	//bi-directional many-to-one association to EuTaxe
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_taxe")
	public EuTaxe getEuTaxe() {
		return this.euTaxe;
	}

	public void setEuTaxe(EuTaxe euTaxe) {
		this.euTaxe = euTaxe;
	}


	//bi-directional many-to-one association to EuCommande
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_commande")
	public EuCommande getEuCommande() {
		return this.euCommande;
	}

	public void setEuCommande(EuCommande euCommande) {
		this.euCommande = euCommande;
	}


	//bi-directional many-to-one association to EuUtilisateur
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_utilisateur")
	public EuUtilisateur getEuUtilisateur() {
		return this.euUtilisateur;
	}

	public void setEuUtilisateur(EuUtilisateur euUtilisateur) {
		this.euUtilisateur = euUtilisateur;
	}

}
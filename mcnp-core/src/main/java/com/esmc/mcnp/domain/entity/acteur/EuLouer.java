package com.esmc.mcnp.model.acteur;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.bc.EuDomiciliation;
import com.esmc.mcnp.model.others.EuProprietaire;
import com.esmc.mcnp.model.security.EuUtilisateur;

import java.util.Date;


/**
 * The persistent class for the eu_louer database table.
 * 
 */
@Entity
@Table(name="eu_louer")
@NamedQuery(name="EuLouer.findAll", query="SELECT e FROM EuLouer e")
public class EuLouer implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idLouer;
	private Date dateLocation;
	private double dureeLocation;
	private double idMaison;
	private double montLoyer;
	private EuMembreMorale euMembreMorale;
	private EuUtilisateur euUtilisateur;
	private EuProprietaire euProprietaire;
	private EuDomiciliation euDomiciliation;
	private EuMembre euMembre;
	private EuAppartement euAppartement;

	public EuLouer() {
	}


	@Id
	@Column(name="id_louer", unique=true, nullable=false)
	public double getIdLouer() {
		return this.idLouer;
	}

	public void setIdLouer(double idLouer) {
		this.idLouer = idLouer;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_location")
	public Date getDateLocation() {
		return this.dateLocation;
	}

	public void setDateLocation(Date dateLocation) {
		this.dateLocation = dateLocation;
	}


	@Column(name="duree_location")
	public double getDureeLocation() {
		return this.dureeLocation;
	}

	public void setDureeLocation(double dureeLocation) {
		this.dureeLocation = dureeLocation;
	}


	@Column(name="id_maison")
	public double getIdMaison() {
		return this.idMaison;
	}

	public void setIdMaison(double idMaison) {
		this.idMaison = idMaison;
	}


	@Column(name="mont_loyer")
	public double getMontLoyer() {
		return this.montLoyer;
	}

	public void setMontLoyer(double montLoyer) {
		this.montLoyer = montLoyer;
	}


	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre_ag")
	public EuMembreMorale getEuMembreMorale() {
		return this.euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
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


	//bi-directional many-to-one association to EuProprietaire
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_proprietaire")
	public EuProprietaire getEuProprietaire() {
		return this.euProprietaire;
	}

	public void setEuProprietaire(EuProprietaire euProprietaire) {
		this.euProprietaire = euProprietaire;
	}


	//bi-directional many-to-one association to EuDomiciliation
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_domiciliation")
	public EuDomiciliation getEuDomiciliation() {
		return this.euDomiciliation;
	}

	public void setEuDomiciliation(EuDomiciliation euDomiciliation) {
		this.euDomiciliation = euDomiciliation;
	}


	//bi-directional many-to-one association to EuMembre
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre_loc")
	public EuMembre getEuMembre() {
		return this.euMembre;
	}

	public void setEuMembre(EuMembre euMembre) {
		this.euMembre = euMembre;
	}


	//bi-directional many-to-one association to EuAppartement
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_appartement")
	public EuAppartement getEuAppartement() {
		return this.euAppartement;
	}

	public void setEuAppartement(EuAppartement euAppartement) {
		this.euAppartement = euAppartement;
	}

}
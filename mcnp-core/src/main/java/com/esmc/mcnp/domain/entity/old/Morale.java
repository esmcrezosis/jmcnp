package com.esmc.mcnp.model.old;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the morale database table.
 * 
 */
@Entity
@Table(name="morale")
@NamedQuery(name="Morale.findAll", query="SELECT m FROM Morale m")
public class Morale implements Serializable {
	private static final long serialVersionUID = 1L;
	private String numidentm;
	private String agence;
	private String bp;
	private String codeMembre;
	private Date dateident;
	private String email;
	private double etatContrat;
	private Date heurid;
	private double montant;
	private String nomm;
	private double numcompbq;
	private String portable;
	private String qart;
	private String representant;
	private String rue;
	private String site;
	private String tel;
	private String user;
	private String ville;

	public Morale() {
	}


	@Id
	@Column(unique=true, nullable=false, length=45)
	public String getNumidentm() {
		return this.numidentm;
	}

	public void setNumidentm(String numidentm) {
		this.numidentm = numidentm;
	}


	@Column(nullable=false, length=30)
	public String getAgence() {
		return this.agence;
	}

	public void setAgence(String agence) {
		this.agence = agence;
	}


	@Column(nullable=false, length=100)
	public String getBp() {
		return this.bp;
	}

	public void setBp(String bp) {
		this.bp = bp;
	}


	@Column(name="code_membre", length=120)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getDateident() {
		return this.dateident;
	}

	public void setDateident(Date dateident) {
		this.dateident = dateident;
	}


	@Column(nullable=false, length=30)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Column(name="etat_contrat", nullable=false)
	public double getEtatContrat() {
		return this.etatContrat;
	}

	public void setEtatContrat(double etatContrat) {
		this.etatContrat = etatContrat;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getHeurid() {
		return this.heurid;
	}

	public void setHeurid(Date heurid) {
		this.heurid = heurid;
	}


	@Column(nullable=false)
	public double getMontant() {
		return this.montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}


	@Column(nullable=false, length=220)
	public String getNomm() {
		return this.nomm;
	}

	public void setNomm(String nomm) {
		this.nomm = nomm;
	}


	@Column(nullable=false)
	public double getNumcompbq() {
		return this.numcompbq;
	}

	public void setNumcompbq(double numcompbq) {
		this.numcompbq = numcompbq;
	}


	@Column(nullable=false, length=25)
	public String getPortable() {
		return this.portable;
	}

	public void setPortable(String portable) {
		this.portable = portable;
	}


	@Column(nullable=false, length=50)
	public String getQart() {
		return this.qart;
	}

	public void setQart(String qart) {
		this.qart = qart;
	}


	@Column(nullable=false, length=150)
	public String getRepresentant() {
		return this.representant;
	}

	public void setRepresentant(String representant) {
		this.representant = representant;
	}


	@Column(nullable=false, length=50)
	public String getRue() {
		return this.rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}


	@Column(nullable=false, length=80)
	public String getSite() {
		return this.site;
	}

	public void setSite(String site) {
		this.site = site;
	}


	@Column(nullable=false, length=50)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}


	@Column(nullable=false, length=45)
	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}


	@Column(nullable=false, length=50)
	public String getVille() {
		return this.ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

}
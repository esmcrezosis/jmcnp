package com.esmc.mcnp.model.cmfh;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.model.security.EuUtilisateur;

import java.util.Date;


/**
 * The persistent class for the eu_detail_compte_mf107 database table.
 * 
 */
@Entity
@Table(name="eu_detail_compte_mf107")
@NamedQuery(name="EuDetailCompteMf107.findAll", query="SELECT e FROM EuDetailCompteMf107 e")
public class EuDetailCompteMf107 implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idDetailCompteMf107;
	private String codeCompte;
	private String creditcode;
	private double cumul;
	private Date dateDetail;
	private double etatDetailCompte;
	private double montantRep;
	private String numident;
	private double pourcentage;
	private EuUtilisateur euUtilisateur;

	public EuDetailCompteMf107() {
	}


	@Id
	@Column(name="id_detail_compte_mf107", unique=true, nullable=false)
	public double getIdDetailCompteMf107() {
		return this.idDetailCompteMf107;
	}

	public void setIdDetailCompteMf107(double idDetailCompteMf107) {
		this.idDetailCompteMf107 = idDetailCompteMf107;
	}


	@Column(name="code_compte", length=45)
	public String getCodeCompte() {
		return this.codeCompte;
	}

	public void setCodeCompte(String codeCompte) {
		this.codeCompte = codeCompte;
	}


	@Column(length=20)
	public String getCreditcode() {
		return this.creditcode;
	}

	public void setCreditcode(String creditcode) {
		this.creditcode = creditcode;
	}


	public double getCumul() {
		return this.cumul;
	}

	public void setCumul(double cumul) {
		this.cumul = cumul;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_detail")
	public Date getDateDetail() {
		return this.dateDetail;
	}

	public void setDateDetail(Date dateDetail) {
		this.dateDetail = dateDetail;
	}


	@Column(name="etat_detail_compte")
	public double getEtatDetailCompte() {
		return this.etatDetailCompte;
	}

	public void setEtatDetailCompte(double etatDetailCompte) {
		this.etatDetailCompte = etatDetailCompte;
	}


	@Column(name="montant_rep")
	public double getMontantRep() {
		return this.montantRep;
	}

	public void setMontantRep(double montantRep) {
		this.montantRep = montantRep;
	}


	@Column(length=25)
	public String getNumident() {
		return this.numident;
	}

	public void setNumident(String numident) {
		this.numident = numident;
	}


	public double getPourcentage() {
		return this.pourcentage;
	}

	public void setPourcentage(double pourcentage) {
		this.pourcentage = pourcentage;
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
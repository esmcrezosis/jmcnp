package com.esmc.mcnp.domain.entity.cmfh;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_mf database table.
 * 
 */
@Entity
@Table(name="eu_mf")
@NamedQuery(name="EuMf.findAll", query="SELECT e FROM EuMf e")
public class EuMf implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idMf;
	private String codeCompte;
	private String codeMembre;
	private Date dateDebMf;
	private Date dateFinMf;
	private Date dateMf;
	private double domicilier;
	private double gainMf;
	private double idUtilisateur;
	private double montMf;
	private double nbGain;
	private EuTypeMf euTypeMf;

	public EuMf() {
	}


	@Id
	@Column(name="id_mf", unique=true, nullable=false)
	public double getIdMf() {
		return this.idMf;
	}

	public void setIdMf(double idMf) {
		this.idMf = idMf;
	}


	@Column(name="code_compte", length=45)
	public String getCodeCompte() {
		return this.codeCompte;
	}

	public void setCodeCompte(String codeCompte) {
		this.codeCompte = codeCompte;
	}


	@Column(name="code_membre", length=25)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_deb_mf")
	public Date getDateDebMf() {
		return this.dateDebMf;
	}

	public void setDateDebMf(Date dateDebMf) {
		this.dateDebMf = dateDebMf;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_fin_mf")
	public Date getDateFinMf() {
		return this.dateFinMf;
	}

	public void setDateFinMf(Date dateFinMf) {
		this.dateFinMf = dateFinMf;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_mf")
	public Date getDateMf() {
		return this.dateMf;
	}

	public void setDateMf(Date dateMf) {
		this.dateMf = dateMf;
	}


	public double getDomicilier() {
		return this.domicilier;
	}

	public void setDomicilier(double domicilier) {
		this.domicilier = domicilier;
	}


	@Column(name="gain_mf")
	public double getGainMf() {
		return this.gainMf;
	}

	public void setGainMf(double gainMf) {
		this.gainMf = gainMf;
	}


	@Column(name="id_utilisateur")
	public double getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(double idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	@Column(name="mont_mf")
	public double getMontMf() {
		return this.montMf;
	}

	public void setMontMf(double montMf) {
		this.montMf = montMf;
	}


	@Column(name="nb_gain")
	public double getNbGain() {
		return this.nbGain;
	}

	public void setNbGain(double nbGain) {
		this.nbGain = nbGain;
	}


	//bi-directional many-to-one association to EuTypeMf
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_type_mf")
	public EuTypeMf getEuTypeMf() {
		return this.euTypeMf;
	}

	public void setEuTypeMf(EuTypeMf euTypeMf) {
		this.euTypeMf = euTypeMf;
	}

}
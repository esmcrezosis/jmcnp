package com.esmc.mcnp.domain.entity.mprg;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esmc.mcnp.domain.entity.bc.EuProduit;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;


/**
 * The persistent class for the eu_krr database table.
 *
 */
@Entity
@Table(name="eu_krr")
@NamedQuery(name="EuKrr.findAll", query="SELECT e FROM EuKrr e")
public class EuKrr implements Serializable {
	private static final long serialVersionUID = 1L;
	private long idKrr;
	private long idCredit;
	private Date dateDemande;
	private Date dateEchue;
	private Date dateRenouveller;
	private double montCapa;
	private double montKrr;
	private double montReconst;
	private String payer;
	private String reconstituer;
	private String typeKrr;
	private EuMembre euMembre;
	private EuMembreMorale euMembreMorale;
	private EuProduit euProduit;

	public EuKrr() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_krr")
	public long getIdKrr() {
		return idKrr;
	}

	public void setIdKrr(long idKrr) {
		this.idKrr = idKrr;
	}

	@Column(name="id_credit", unique=true, nullable=false)
	public long getIdCredit() {
		return idCredit;
	}

	public void setIdCredit(long idCredit) {
		this.idCredit = idCredit;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_demande")
	public Date getDateDemande() {
		return dateDemande;
	}

	public void setDateDemande(Date dateDemande) {
		this.dateDemande = dateDemande;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_echue")
	public Date getDateEchue() {
		return dateEchue;
	}

	public void setDateEchue(Date dateEchue) {
		this.dateEchue = dateEchue;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_renouveller")
	public Date getDateRenouveller() {
		return dateRenouveller;
	}

	public void setDateRenouveller(Date dateRenouveller) {
		this.dateRenouveller = dateRenouveller;
	}


	@Column(name="mont_capa")
	public double getMontCapa() {
		return montCapa;
	}

	public void setMontCapa(double montCapa) {
		this.montCapa = montCapa;
	}


	@Column(name="mont_krr")
	public double getMontKrr() {
		return montKrr;
	}

	public void setMontKrr(double montKrr) {
		this.montKrr = montKrr;
	}


	@Column(name="mont_reconst")
	public double getMontReconst() {
		return montReconst;
	}

	public void setMontReconst(double montReconst) {
		this.montReconst = montReconst;
	}


	@Column(length=4)
	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}


	@Column(length=4)
	public String getReconstituer() {
		return reconstituer;
	}

	public void setReconstituer(String reconstituer) {
		this.reconstituer = reconstituer;
	}

	@Column(name="type_krr")
	public String getTypeKrr() {
		return typeKrr;
	}


	public void setTypeKrr(String typeKrr) {
		this.typeKrr = typeKrr;
	}


	//bi-directional many-to-one association to EuMembre
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre")
	public EuMembre getEuMembre() {
		return euMembre;
	}

	public void setEuMembre(EuMembre euMembre) {
		this.euMembre = euMembre;
	}


	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre_morale")
	public EuMembreMorale getEuMembreMorale() {
		return euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}


	//bi-directional many-to-one association to EuProduit
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_produit")
	public EuProduit getEuProduit() {
		return euProduit;
	}

	public void setEuProduit(EuProduit euProduit) {
		this.euProduit = euProduit;
	}

}
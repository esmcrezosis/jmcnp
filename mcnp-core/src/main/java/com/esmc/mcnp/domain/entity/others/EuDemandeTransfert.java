package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;

import java.util.Date;


/**
 * The persistent class for the eu_demande_transfert database table.
 * 
 */
@Entity
@Table(name="eu_demande_transfert")
@NamedQuery(name="EuDemandeTransfert.findAll", query="SELECT e FROM EuDemandeTransfert e")
public class EuDemandeTransfert implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idDemande;
	private String codeTypeNn;
	private Date dateDemande;
	private Date dateOctroi;
	private Date dateOkAcnev;
	private Date dateOkExec;
	private Date dateOkSurv;
	private String etatDemande;
	private double montantNnDem;
	private double montantPrecObt;
	private double motantRemb;
	private double okAcnev;
	private double okExec;
	private double okSurv;
	private double resteRemb;
	private String typeDemande;
	private EuMembreMorale euMembreMorale;

	public EuDemandeTransfert() {
	}


	@Id
	@Column(name="id_demande", unique=true, nullable=false)
	public double getIdDemande() {
		return this.idDemande;
	}

	public void setIdDemande(double idDemande) {
		this.idDemande = idDemande;
	}


	@Column(name="code_type_nn", nullable=false, length=10)
	public String getCodeTypeNn() {
		return this.codeTypeNn;
	}

	public void setCodeTypeNn(String codeTypeNn) {
		this.codeTypeNn = codeTypeNn;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_demande")
	public Date getDateDemande() {
		return this.dateDemande;
	}

	public void setDateDemande(Date dateDemande) {
		this.dateDemande = dateDemande;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_octroi")
	public Date getDateOctroi() {
		return this.dateOctroi;
	}

	public void setDateOctroi(Date dateOctroi) {
		this.dateOctroi = dateOctroi;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_ok_acnev")
	public Date getDateOkAcnev() {
		return this.dateOkAcnev;
	}

	public void setDateOkAcnev(Date dateOkAcnev) {
		this.dateOkAcnev = dateOkAcnev;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_ok_exec")
	public Date getDateOkExec() {
		return this.dateOkExec;
	}

	public void setDateOkExec(Date dateOkExec) {
		this.dateOkExec = dateOkExec;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_ok_surv")
	public Date getDateOkSurv() {
		return this.dateOkSurv;
	}

	public void setDateOkSurv(Date dateOkSurv) {
		this.dateOkSurv = dateOkSurv;
	}


	@Column(name="etat_demande", length=15)
	public String getEtatDemande() {
		return this.etatDemande;
	}

	public void setEtatDemande(String etatDemande) {
		this.etatDemande = etatDemande;
	}


	@Column(name="montant_nn_dem", nullable=false)
	public double getMontantNnDem() {
		return this.montantNnDem;
	}

	public void setMontantNnDem(double montantNnDem) {
		this.montantNnDem = montantNnDem;
	}


	@Column(name="montant_prec_obt")
	public double getMontantPrecObt() {
		return this.montantPrecObt;
	}

	public void setMontantPrecObt(double montantPrecObt) {
		this.montantPrecObt = montantPrecObt;
	}


	@Column(name="motant_remb")
	public double getMotantRemb() {
		return this.motantRemb;
	}

	public void setMotantRemb(double motantRemb) {
		this.motantRemb = motantRemb;
	}


	@Column(name="ok_acnev")
	public double getOkAcnev() {
		return this.okAcnev;
	}

	public void setOkAcnev(double okAcnev) {
		this.okAcnev = okAcnev;
	}


	@Column(name="ok_exec")
	public double getOkExec() {
		return this.okExec;
	}

	public void setOkExec(double okExec) {
		this.okExec = okExec;
	}


	@Column(name="ok_surv")
	public double getOkSurv() {
		return this.okSurv;
	}

	public void setOkSurv(double okSurv) {
		this.okSurv = okSurv;
	}


	@Column(name="reste_remb")
	public double getResteRemb() {
		return this.resteRemb;
	}

	public void setResteRemb(double resteRemb) {
		this.resteRemb = resteRemb;
	}


	@Column(name="type_demande", length=20)
	public String getTypeDemande() {
		return this.typeDemande;
	}

	public void setTypeDemande(String typeDemande) {
		this.typeDemande = typeDemande;
	}


	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre")
	public EuMembreMorale getEuMembreMorale() {
		return this.euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}

}
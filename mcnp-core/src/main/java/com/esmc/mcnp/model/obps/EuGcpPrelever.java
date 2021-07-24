package com.esmc.mcnp.model.obps;

import com.esmc.mcnp.model.bc.EuBon;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_gcp_prelever database table.
 *
 */
@Entity
@Table(name="eu_gcp_prelever")
@NamedQuery(name="EuGcpPrelever.findAll", query="SELECT e FROM EuGcpPrelever e")
public class EuGcpPrelever implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idPrelevement;
	private String codeMembre;
	private Date datePrelevement;
	private Date heurePrelevement;
	private Long idCredit;
	private Long idOperation;
	private Long idTpagcp;
	private double montPrelever;
	private double montRapprocher;
	private Integer rapprocher;
	private double soldePrelevement;
	private String sourceCredit;
	private EuGcp euGcp;
	private EuTegc euTegc;
	private EuBon euBon;

	public EuGcpPrelever() {
	}


	@Id
	@Column(name="id_prelevement", unique=true, nullable=false)
	public Long getIdPrelevement() {
		return this.idPrelevement;
	}

	public void setIdPrelevement(Long idPrelevement) {
		this.idPrelevement = idPrelevement;
	}


	@Column(name="code_membre", length=100)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_prelevement")
	public Date getDatePrelevement() {
		return this.datePrelevement;
	}

	public void setDatePrelevement(Date datePrelevement) {
		this.datePrelevement = datePrelevement;
	}


	@Temporal(TemporalType.TIME)
	@Column(name="heure_prelevement")
	public Date getHeurePrelevement() {
		return this.heurePrelevement;
	}

	public void setHeurePrelevement(Date heurePrelevement) {
		this.heurePrelevement = heurePrelevement;
	}


	@Column(name="id_credit")
	public Long getIdCredit() {
		return this.idCredit;
	}

	public void setIdCredit(Long idCredit) {
		this.idCredit = idCredit;
	}

	@Column(name="id_operation")
	public Long getIdOperation() {
		return this.idOperation;
	}

	public void setIdOperation(Long idOperation) {
		this.idOperation = idOperation;
	}


	@Column(name="id_tpagcp")
	public Long getIdTpagcp() {
		return this.idTpagcp;
	}

	public void setIdTpagcp(Long idTpagcp) {
		this.idTpagcp = idTpagcp;
	}


	@Column(name="mont_prelever", nullable=false)
	public double getMontPrelever() {
		return this.montPrelever;
	}

	public void setMontPrelever(double montPrelever) {
		this.montPrelever = montPrelever;
	}


	@Column(name="mont_rapprocher", nullable=false)
	public double getMontRapprocher() {
		return this.montRapprocher;
	}

	public void setMontRapprocher(double montRapprocher) {
		this.montRapprocher = montRapprocher;
	}


	public Integer getRapprocher() {
		return this.rapprocher;
	}

	public void setRapprocher(Integer rapprocher) {
		this.rapprocher = rapprocher;
	}


	@Column(name="solde_prelevement")
	public double getSoldePrelevement() {
		return this.soldePrelevement;
	}

	public void setSoldePrelevement(double soldePrelevement) {
		this.soldePrelevement = soldePrelevement;
	}


	@Column(name="source_credit", length=200)
	public String getSourceCredit() {
		return this.sourceCredit;
	}

	public void setSourceCredit(String sourceCredit) {
		this.sourceCredit = sourceCredit;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_gcp")
	public EuGcp getEuGcp() {
		return euGcp;
	}


	public void setEuGcp(EuGcp euGcp) {
		this.euGcp = euGcp;
	}


	//bi-directional many-to-one association to EuTegc
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_tegc")
	public EuTegc getEuTegc() {
		return this.euTegc;
	}

	public void setEuTegc(EuTegc euTegc) {
		this.euTegc = euTegc;
	}


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="bon_id")
	public EuBon getEuBon() {
		return euBon;
	}


	public void setEuBon(EuBon euBon) {
		this.euBon = euBon;
	}

}
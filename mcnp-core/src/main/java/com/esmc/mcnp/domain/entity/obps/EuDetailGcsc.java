package com.esmc.mcnp.model.obps;

import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.smcipn.EuGcsc;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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

@Entity
@Table(name="eu_detail_gcsc")
@NamedQuery(name="EuDetailGcsc.findAll", query="SELECT e FROM EuDetailGcsc e")
public class EuDetailGcsc implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idDetailGcsc;
	private String codeMembre;
	private Date dateConso;
	private double montGcsc;
	private String source;
	private EuGcsc euGcsc;
	private EuCompteCredit euCompteCredit;
	private EuBon euBon;

	public EuDetailGcsc() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_detail_gcsc", unique=true, nullable=false)
	public Long getIdDetailGcsc() {
		return idDetailGcsc;
	}

	public void setIdDetailGcsc(Long idDetailGcsc) {
		this.idDetailGcsc = idDetailGcsc;
	}

	@Column(name="code_membre", length=100)
	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_conso")
	public Date getDateConso() {
		return dateConso;
	}

	public void setDateConso(Date dateConso) {
		this.dateConso = dateConso;
	}

	@Column(name="mont_gcsc")
	public double getMontGcsc() {
		return montGcsc;
	}

	public void setMontGcsc(double montGcsc) {
		this.montGcsc = montGcsc;
	}

	@Column(name="source", length=200)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	//bi-directional many-to-one association to EuGcsc
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_gcsc")
	public EuGcsc getEuGcsc() {
		return euGcsc;
	}

	public void setEuGcsc(EuGcsc euGcsc) {
		this.euGcsc = euGcsc;
	}

	//bi-directional many-to-one association to EuCompteCredit
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_credit")
	public EuCompteCredit getEuCompteCredit() {
		return euCompteCredit;
	}

	public void setEuCompteCredit(EuCompteCredit euCompteCredit) {
		this.euCompteCredit = euCompteCredit;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade= CascadeType.PERSIST)
	@JoinColumn(name="bon_id")
	public EuBon getEuBon() {
		return euBon;
	}

	public void setEuBon(EuBon euBon) {
		this.euBon = euBon;
	}



}

package com.esmc.mcnp.domain.entity.ba;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_insertsmsmoney database table.
 * 
 */
@Entity
@Table(name="eu_insertsmsmoney")
@NamedQuery(name="EuInsertsmsmoney.findAll", query="SELECT e FROM EuInsertsmsmoney e")
public class EuInsertsmsmoney implements Serializable {
	private static final long serialVersionUID = 1L;
	private double neng;
	private double codeagence;
	private double creditamount;
	private String creditcode;
	private Date datetime;
	private Date datetimeconsumed;
	private String destaccountConsumed;
	private String fromaccount;
	private String motif;
	private String sentto;
	private String utilisateur;

	public EuInsertsmsmoney() {
	}


	@Id
	@Column(unique=true, nullable=false)
	public double getNeng() {
		return this.neng;
	}

	public void setNeng(double neng) {
		this.neng = neng;
	}


	public double getCodeagence() {
		return this.codeagence;
	}

	public void setCodeagence(double codeagence) {
		this.codeagence = codeagence;
	}


	public double getCreditamount() {
		return this.creditamount;
	}

	public void setCreditamount(double creditamount) {
		this.creditamount = creditamount;
	}


	@Column(length=80)
	public String getCreditcode() {
		return this.creditcode;
	}

	public void setCreditcode(String creditcode) {
		this.creditcode = creditcode;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getDatetime() {
		return this.datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getDatetimeconsumed() {
		return this.datetimeconsumed;
	}

	public void setDatetimeconsumed(Date datetimeconsumed) {
		this.datetimeconsumed = datetimeconsumed;
	}


	@Column(name="destaccount_consumed", length=200)
	public String getDestaccountConsumed() {
		return this.destaccountConsumed;
	}

	public void setDestaccountConsumed(String destaccountConsumed) {
		this.destaccountConsumed = destaccountConsumed;
	}


	@Column(length=200)
	public String getFromaccount() {
		return this.fromaccount;
	}

	public void setFromaccount(String fromaccount) {
		this.fromaccount = fromaccount;
	}


	@Column(length=60)
	public String getMotif() {
		return this.motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}


	@Column(length=80)
	public String getSentto() {
		return this.sentto;
	}

	public void setSentto(String sentto) {
		this.sentto = sentto;
	}


	@Column(length=255)
	public String getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(String utilisateur) {
		this.utilisateur = utilisateur;
	}

}
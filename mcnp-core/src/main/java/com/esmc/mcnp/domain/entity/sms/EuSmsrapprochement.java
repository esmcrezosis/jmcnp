package com.esmc.mcnp.domain.entity.sms;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_smsrapprochement database table.
 * 
 */
@Entity
@Table(name="eu_smsrapprochement")
@NamedQuery(name="EuSmsrapprochement.findAll", query="SELECT e FROM EuSmsrapprochement e")
public class EuSmsrapprochement implements Serializable {
	private static final long serialVersionUID = 1L;
	private double id;
	private String banque;
	private String creditcode;
	private String datetime;
	private String datetimeconsumed;
	private double montant;
	private String motif;

	public EuSmsrapprochement() {
	}


	@Id
	@Column(unique=true, nullable=false)
	public double getId() {
		return this.id;
	}

	public void setId(double id) {
		this.id = id;
	}


	@Column(length=100)
	public String getBanque() {
		return this.banque;
	}

	public void setBanque(String banque) {
		this.banque = banque;
	}


	@Column(length=80)
	public String getCreditcode() {
		return this.creditcode;
	}

	public void setCreditcode(String creditcode) {
		this.creditcode = creditcode;
	}


	@Column(length=80)
	public String getDatetime() {
		return this.datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}


	@Column(length=80)
	public String getDatetimeconsumed() {
		return this.datetimeconsumed;
	}

	public void setDatetimeconsumed(String datetimeconsumed) {
		this.datetimeconsumed = datetimeconsumed;
	}


	public double getMontant() {
		return this.montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}


	@Column(length=80)
	public String getMotif() {
		return this.motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

}
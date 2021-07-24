package com.esmc.mcnp.model.sms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "eu_sms_connexion")
@NamedQuery(name = "EuSmsConnexion.findAll", query = "SELECT e FROM EuSmsConnexion e")
public class EuSmsConnexion implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long smsConnexionId;
	private String smsConnexionCodeEnvoi;
	private String smsConnexionCodeRecu;
	private Date smsConnexionDate;
	private String smsConnexionCodeMembre;
	private Integer smsConnexionUtilise;

	public EuSmsConnexion() {

	}

	@Id
	@Column(name = "sms_connexion_id", unique = true, nullable = false)
	public Long getSmsConnexionId() {
		return smsConnexionId;
	}

	public void setSmsConnexionId(Long smsConnexionId) {
		this.smsConnexionId = smsConnexionId;
	}

	@Column(name = "sms_connexion_code_envoi")
	public String getSmsConnexionCodeEnvoi() {
		return smsConnexionCodeEnvoi;
	}

	public void setSmsConnexionCodeEnvoi(String smsConnexionCodeEnvoi) {
		this.smsConnexionCodeEnvoi = smsConnexionCodeEnvoi;
	}

	@Column(name = "sms_connexion_code_recu")
	public String getSmsConnexionCodeRecu() {
		return smsConnexionCodeRecu;
	}

	public void setSmsConnexionCodeRecu(String smsConnexionCodeRecu) {
		this.smsConnexionCodeRecu = smsConnexionCodeRecu;
	}

	@Column(name = "sms_connexion_date")
	public Date getSmsConnexionDate() {
		return smsConnexionDate;
	}

	public void setSmsConnexionDate(Date smsConnexionDate) {
		this.smsConnexionDate = smsConnexionDate;
	}

	@Column(name = "sms_connexion_code_membre")
	public String getSmsConnexionCodeMembre() {
		return smsConnexionCodeMembre;
	}

	public void setSmsConnexionCodeMembre(String smsConnexionCodeMembre) {
		this.smsConnexionCodeMembre = smsConnexionCodeMembre;
	}

	@Column(name = "sms_connexion_utilise")
	public Integer getSmsConnexionUtilise() {
		return smsConnexionUtilise;
	}

	public void setSmsConnexionUtilise(Integer smsConnexionUtilise) {
		this.smsConnexionUtilise = smsConnexionUtilise;
	}

}

package com.esmc.mcnp.model.sms;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_sms database table.
 *
 */
@Entity
@Table(name="eu_sms")
@NamedQuery(name="EuSms.findAll", query="SELECT e FROM EuSms e")
public class EuSms implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long neng;
	private String dateenvoi;
	private String datetime;
	private String decodestring;
	private String envoyele;
	private String envoyepar;
	private Integer etat;
	private String heureenvoi;
	private Integer iddateenvoi;
	private Integer iddatetime;
	private Integer idheureenvoi;
	private String nom;
	private String prenom;
	private String recipient;
	private double retries;
	private String smsbody;
	private String societe;
	private String typedestinataire;

	public EuSms() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	public Long getNeng() {
		return this.neng;
	}

	public void setNeng(Long neng) {
		this.neng = neng;
	}


	@Column(length=30)
	public String getDateenvoi() {
		return this.dateenvoi;
	}

	public void setDateenvoi(String dateenvoi) {
		this.dateenvoi = dateenvoi;
	}


	@Column(length=80)
	public String getDatetime() {
		return this.datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}


	@Column(length=255)
	public String getDecodestring() {
		return this.decodestring;
	}

	public void setDecodestring(String decodestring) {
		this.decodestring = decodestring;
	}


	@Column(length=80)
	public String getEnvoyele() {
		return this.envoyele;
	}

	public void setEnvoyele(String envoyele) {
		this.envoyele = envoyele;
	}


	@Column(length=200)
	public String getEnvoyepar() {
		return this.envoyepar;
	}

	public void setEnvoyepar(String envoyepar) {
		this.envoyepar = envoyepar;
	}


	public Integer getEtat() {
		return this.etat;
	}

	public void setEtat(Integer etat) {
		this.etat = etat;
	}


	@Column(length=60)
	public String getHeureenvoi() {
		return this.heureenvoi;
	}

	public void setHeureenvoi(String heureenvoi) {
		this.heureenvoi = heureenvoi;
	}


	public Integer getIddateenvoi() {
		return this.iddateenvoi;
	}

	public void setIddateenvoi(Integer iddateenvoi) {
		this.iddateenvoi = iddateenvoi;
	}


	public Integer getIddatetime() {
		return this.iddatetime;
	}

	public void setIddatetime(Integer iddatetime) {
		this.iddatetime = iddatetime;
	}


	public Integer getIdheureenvoi() {
		return this.idheureenvoi;
	}

	public void setIdheureenvoi(Integer idheureenvoi) {
		this.idheureenvoi = idheureenvoi;
	}


	@Column(length=200)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}


	@Column(length=200)
	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	@Column(length=60)
	public String getRecipient() {
		return this.recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}


	public double getRetries() {
		return this.retries;
	}

	public void setRetries(double retries) {
		this.retries = retries;
	}


	@Column(length=255)
	public String getSmsbody() {
		return this.smsbody;
	}

	public void setSmsbody(String smsbody) {
		this.smsbody = smsbody;
	}


	@Column(length=200)
	public String getSociete() {
		return this.societe;
	}

	public void setSociete(String societe) {
		this.societe = societe;
	}


	@Column(length=80)
	public String getTypedestinataire() {
		return this.typedestinataire;
	}

	public void setTypedestinataire(String typedestinataire) {
		this.typedestinataire = typedestinataire;
	}

}
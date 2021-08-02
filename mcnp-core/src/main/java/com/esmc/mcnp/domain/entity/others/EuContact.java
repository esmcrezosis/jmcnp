package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_contact database table.
 * 
 */
@Entity
@Table(name="eu_contact")
@NamedQuery(name="EuContact.findAll", query="SELECT e FROM EuContact e")
public class EuContact implements Serializable {
	private static final long serialVersionUID = 1L;
	private double contactId;
	private Date contactDate;
	private String contactEmail;
	private String contactMessage;
	private String contactNom;
	private String contactSujet;
	private String contactType;
	private double traiter;

	public EuContact() {
	}


	@Id
	@Column(name="contact_id", unique=true, nullable=false)
	public double getContactId() {
		return this.contactId;
	}

	public void setContactId(double contactId) {
		this.contactId = contactId;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="contact_date")
	public Date getContactDate() {
		return this.contactDate;
	}

	public void setContactDate(Date contactDate) {
		this.contactDate = contactDate;
	}


	@Column(name="contact_email", length=250)
	public String getContactEmail() {
		return this.contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}


	@Lob
	@Column(name="contact_message")
	public String getContactMessage() {
		return this.contactMessage;
	}

	public void setContactMessage(String contactMessage) {
		this.contactMessage = contactMessage;
	}


	@Column(name="contact_nom", length=250)
	public String getContactNom() {
		return this.contactNom;
	}

	public void setContactNom(String contactNom) {
		this.contactNom = contactNom;
	}


	@Column(name="contact_sujet", length=250)
	public String getContactSujet() {
		return this.contactSujet;
	}

	public void setContactSujet(String contactSujet) {
		this.contactSujet = contactSujet;
	}


	@Column(name="contact_type", length=20)
	public String getContactType() {
		return this.contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}


	public double getTraiter() {
		return this.traiter;
	}

	public void setTraiter(double traiter) {
		this.traiter = traiter;
	}

}
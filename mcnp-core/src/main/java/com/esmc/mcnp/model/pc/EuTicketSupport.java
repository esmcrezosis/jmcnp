package com.esmc.mcnp.model.pc;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_ticket_support database table.
 * 
 */
@Entity
@Table(name="eu_ticket_support")
@NamedQuery(name="EuTicketSupport.findAll", query="SELECT e FROM EuTicketSupport e")
public class EuTicketSupport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ticket")
	private int idTicket;

	@Column(name="addresse_integrateur")
	private String addresseIntegrateur;

	@Column(name="avis_one")
	private String avisOne;

	@Column(name="avis_two")
	private String avisTwo;

	@Column(name="code_membre_demandeur")
	private String codeMembreDemandeur;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_validation")
	private Date dateValidation;

	@Lob
	private String description;

	private String email;

	@Column(name="file_name")
	private String fileName;

	private byte invalid;

	private String lieu;

	@Column(name="numero_demandeur")
	private String numeroDemandeur;

	@Column(name="renseignement_id")
	private int renseignementId;

	private int telephone;

	private byte valid;

	@Column(name="visa_one")
	private String visaOne;

	@Column(name="visa_two")
	private String visaTwo;

	public EuTicketSupport() {
	}

	public int getIdTicket() {
		return this.idTicket;
	}

	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}

	public String getAddresseIntegrateur() {
		return this.addresseIntegrateur;
	}

	public void setAddresseIntegrateur(String addresseIntegrateur) {
		this.addresseIntegrateur = addresseIntegrateur;
	}

	public String getAvisOne() {
		return this.avisOne;
	}

	public void setAvisOne(String avisOne) {
		this.avisOne = avisOne;
	}

	public String getAvisTwo() {
		return this.avisTwo;
	}

	public void setAvisTwo(String avisTwo) {
		this.avisTwo = avisTwo;
	}

	public String getCodeMembreDemandeur() {
		return this.codeMembreDemandeur;
	}

	public void setCodeMembreDemandeur(String codeMembreDemandeur) {
		this.codeMembreDemandeur = codeMembreDemandeur;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getDateValidation() {
		return this.dateValidation;
	}

	public void setDateValidation(Date dateValidation) {
		this.dateValidation = dateValidation;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte getInvalid() {
		return this.invalid;
	}

	public void setInvalid(byte invalid) {
		this.invalid = invalid;
	}

	public String getLieu() {
		return this.lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	public String getNumeroDemandeur() {
		return this.numeroDemandeur;
	}

	public void setNumeroDemandeur(String numeroDemandeur) {
		this.numeroDemandeur = numeroDemandeur;
	}

	public int getRenseignementId() {
		return this.renseignementId;
	}

	public void setRenseignementId(int renseignementId) {
		this.renseignementId = renseignementId;
	}

	public int getTelephone() {
		return this.telephone;
	}

	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}

	public byte getValid() {
		return this.valid;
	}

	public void setValid(byte valid) {
		this.valid = valid;
	}

	public String getVisaOne() {
		return this.visaOne;
	}

	public void setVisaOne(String visaOne) {
		this.visaOne = visaOne;
	}

	public String getVisaTwo() {
		return this.visaTwo;
	}

	public void setVisaTwo(String visaTwo) {
		this.visaTwo = visaTwo;
	}

}
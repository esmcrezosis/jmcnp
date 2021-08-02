package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_offre_demande_message database table.
 *
 */
@Entity
@Table(name="eu_offre_demande_message")
@NamedQuery(name="EuOffreDemandeMessage.findAll", query="SELECT e FROM EuOffreDemandeMessage e")
public class EuOffreDemandeMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeCompte;
	private String codeMembre;
	private Date dateMessage;
	private String idCredit;
	private Long idDemande;
	private Long idMessage;
	private Long idOffre;
	private String message;
	private String typeMessage;

	public EuOffreDemandeMessage() {
	}


	@Column(name="code_compte", length=125)
	public String getCodeCompte() {
		return this.codeCompte;
	}

	public void setCodeCompte(String codeCompte) {
		this.codeCompte = codeCompte;
	}


	@Column(name="code_membre", length=125)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_message")
	public Date getDateMessage() {
		return this.dateMessage;
	}

	public void setDateMessage(Date dateMessage) {
		this.dateMessage = dateMessage;
	}


	@Column(name="id_credit", length=45)
	public String getIdCredit() {
		return this.idCredit;
	}

	public void setIdCredit(String idCredit) {
		this.idCredit = idCredit;
	}


	@Column(name="id_demande")
	public Long getIdDemande() {
		return this.idDemande;
	}

	public void setIdDemande(Long idDemande) {
		this.idDemande = idDemande;
	}

    @Id
	@Column(name="id_message", nullable=false)
	public Long getIdMessage() {
		return this.idMessage;
	}

	public void setIdMessage(Long idMessage) {
		this.idMessage = idMessage;
	}


	@Column(name="id_offre")
	public Long getIdOffre() {
		return this.idOffre;
	}

	public void setIdOffre(Long idOffre) {
		this.idOffre = idOffre;
	}


	@Lob
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	@Column(name="type_message", length=10)
	public String getTypeMessage() {
		return this.typeMessage;
	}

	public void setTypeMessage(String typeMessage) {
		this.typeMessage = typeMessage;
	}

}
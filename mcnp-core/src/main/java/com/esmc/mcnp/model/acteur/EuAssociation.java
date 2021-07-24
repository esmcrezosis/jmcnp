package com.esmc.mcnp.model.acteur;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "eu_association")
public class EuAssociation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long associationId;
	private String associationNom;
	private String associationNumero;
	private Integer associationMobile;
	private int publier;
	private Date associationDateAgrement;
	private String associationEmail;
	private String associationRecepisse;
	private String associationAdresse;
	private Timestamp associationDate;
	private Integer idFiliere;
	private String codeTypeActeur;
	private String codeStatut;
	private String codeAgence;
	private Integer guichet;
	private String codeMembre;

	@Id
	@Column(name = "association_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getAssociationId() {
		return associationId;
	}

	public void setAssociationId(Long associationId) {
		this.associationId = associationId;
	}

	@Basic
	@Column(name = "association_nom")
	public String getAssociationNom() {
		return associationNom;
	}

	public void setAssociationNom(String associationNom) {
		this.associationNom = associationNom;
	}

	@Basic
	@Column(name = "association_numero")
	public String getAssociationNumero() {
		return associationNumero;
	}

	public void setAssociationNumero(String associationNumero) {
		this.associationNumero = associationNumero;
	}

	@Basic
	@Column(name = "association_mobile")
	public Integer getAssociationMobile() {
		return associationMobile;
	}

	public void setAssociationMobile(Integer associationMobile) {
		this.associationMobile = associationMobile;
	}

	@Basic
	@Column(name = "publier")
	public Integer getPublier() {
		return publier;
	}

	public void setPublier(Integer publier) {
		this.publier = publier;
	}

	@Basic
	@Column(name = "association_date_agrement")
	public Date getAssociationDateAgrement() {
		return associationDateAgrement;
	}

	public void setAssociationDateAgrement(Date associationDateAgrement) {
		this.associationDateAgrement = associationDateAgrement;
	}

	@Basic
	@Column(name = "association_email")
	public String getAssociationEmail() {
		return associationEmail;
	}

	public void setAssociationEmail(String associationEmail) {
		this.associationEmail = associationEmail;
	}

	@Basic
	@Column(name = "association_recepisse")
	public String getAssociationRecepisse() {
		return associationRecepisse;
	}

	public void setAssociationRecepisse(String associationRecepisse) {
		this.associationRecepisse = associationRecepisse;
	}

	@Basic
	@Column(name = "association_adresse")
	public String getAssociationAdresse() {
		return associationAdresse;
	}

	public void setAssociationAdresse(String associationAdresse) {
		this.associationAdresse = associationAdresse;
	}

	@Basic
	@Column(name = "association_date")
	public Timestamp getAssociationDate() {
		return associationDate;
	}

	public void setAssociationDate(Timestamp associationDate) {
		this.associationDate = associationDate;
	}

	@Basic
	@Column(name = "id_filiere")
	public Integer getIdFiliere() {
		return idFiliere;
	}

	public void setIdFiliere(Integer idFiliere) {
		this.idFiliere = idFiliere;
	}

	@Basic
	@Column(name = "code_type_acteur")
	public String getCodeTypeActeur() {
		return codeTypeActeur;
	}

	public void setCodeTypeActeur(String codeTypeActeur) {
		this.codeTypeActeur = codeTypeActeur;
	}

	@Basic
	@Column(name = "code_statut")
	public String getCodeStatut() {
		return codeStatut;
	}

	public void setCodeStatut(String codeStatut) {
		this.codeStatut = codeStatut;
	}

	@Basic
	@Column(name = "code_agence")
	public String getCodeAgence() {
		return codeAgence;
	}

	public void setCodeAgence(String codeAgence) {
		this.codeAgence = codeAgence;
	}

	@Basic
	@Column(name = "guichet")
	public Integer getGuichet() {
		return guichet;
	}

	public void setGuichet(Integer guichet) {
		this.guichet = guichet;
	}

	@Basic
	@Column(name = "code_membre")
	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		EuAssociation that = (EuAssociation) o;
		return Double.compare(that.associationId, associationId) == 0
				&& Objects.equals(associationNom, that.associationNom)
				&& Objects.equals(associationNumero, that.associationNumero)
				&& Objects.equals(associationMobile, that.associationMobile) && Objects.equals(publier, that.publier)
				&& Objects.equals(associationDateAgrement, that.associationDateAgrement)
				&& Objects.equals(associationEmail, that.associationEmail)
				&& Objects.equals(associationRecepisse, that.associationRecepisse)
				&& Objects.equals(associationAdresse, that.associationAdresse)
				&& Objects.equals(associationDate, that.associationDate) && Objects.equals(idFiliere, that.idFiliere)
				&& Objects.equals(codeTypeActeur, that.codeTypeActeur) && Objects.equals(codeStatut, that.codeStatut)
				&& Objects.equals(codeAgence, that.codeAgence) && Objects.equals(guichet, that.guichet)
				&& Objects.equals(codeMembre, that.codeMembre);
	}

	@Override
	public int hashCode() {
		return Objects.hash(associationId, associationNom, associationNumero, associationMobile, publier,
				associationDateAgrement, associationEmail, associationRecepisse, associationAdresse, associationDate,
				idFiliere, codeTypeActeur, codeStatut, codeAgence, guichet, codeMembre);
	}
}

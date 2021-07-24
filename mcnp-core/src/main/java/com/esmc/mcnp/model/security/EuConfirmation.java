package com.esmc.mcnp.model.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_confirmation database table.
 *
 */
@Entity
@Table(name = "eu_confirmation")
@NamedQuery(name = "EuConfirmation.findAll", query = "SELECT e FROM EuConfirmation e")
public class EuConfirmation implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idConfirmation;
	private String codeMembre;
	private String codeOperateur;
	private String nomOperateur;
	private String dataText;
	private String activite;
	private int status;
	private String dateCreation;
	private String dateConfirmation;
	private String texteConfirmation;
	private String page;
	private int typeConfirmation;

	public EuConfirmation() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_confirmation")
	public Long getIdConfirmation() {
		return idConfirmation;
	}

	public void setIdConfirmation(Long idConfirmation) {
		this.idConfirmation = idConfirmation;
	}

	@Column(name = "code_membre")
	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Column(name = "code_operateur")
	public String getCodeOperateur() {
		return codeOperateur;
	}

	public void setCodeOperateur(String codeOperateur) {
		this.codeOperateur = codeOperateur;
	}

	@Column(name = "nom_operateur")
	public String getNomOperateur() {
		return nomOperateur;
	}

	public void setNomOperateur(String nomOperateur) {
		this.nomOperateur = nomOperateur;
	}

	@Column(name = "data_text")
	public String getDataText() {
		return dataText;
	}

	public void setDataText(String dataText) {
		this.dataText = dataText;
	}

	public String getActivite() {
		return activite;
	}

	public void setActivite(String activite) {
		this.activite = activite;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "date_creation")
	public String getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Column(name = "date_confirmation")
	public String getDateConfirmation() {
		return dateConfirmation;
	}

	public void setDateConfirmation(String dateConfirmation) {
		this.dateConfirmation = dateConfirmation;
	}

	@Column(name = "texte_confirmation")
	public String getTexteConfirmation() {
		return texteConfirmation;
	}

	public void setTexteConfirmation(String texteConfirmation) {
		this.texteConfirmation = texteConfirmation;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	@Column(name = "type_confirmation")
	public int getTypeConfirmation() {
		return typeConfirmation;
	}

	public void setTypeConfirmation(int typeConfirmation) {
		this.typeConfirmation = typeConfirmation;
	}

}

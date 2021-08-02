package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esmc.mcnp.domain.entity.pc.EuCreance;

/**
 * The persistent class for the eu_objet database table.
 *
 */
@Entity
@Table(name = "eu_objet")
@NamedQuery(name = "EuObjet.findAll", query = "SELECT e FROM EuObjet e")
public class EuObjet implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idObjet;
	private String codeMembre;
	private String designObjet;
	private String descriptionObjet;
	private Date dateEnregistrement;
	private Double montEstime;
	private Double montVente;
	private String etatObjet;
	private EuCreance dette;

	public EuObjet() {
	}

	@Id
	@Column(name = "id_objet", unique = true, nullable = false)
	public Integer getIdObjet() {
		return this.idObjet;
	}

	public void setIdObjet(Integer idObjet) {
		this.idObjet = idObjet;
	}

	@Column(name = "design_objet", length = 255)
	public String getDesignObjet() {
		return this.designObjet;
	}

	public void setDesignObjet(String designObjet) {
		this.designObjet = designObjet;
	}

	@Column(name = "code_membre")
	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_enregistrement")
	public Date getDateEnregistrement() {
		return dateEnregistrement;
	}

	public void setDateEnregistrement(Date dateEnregistrement) {
		this.dateEnregistrement = dateEnregistrement;
	}

	@Column(name = "mont_estime")
	public Double getMontEstime() {
		return montEstime;
	}

	public void setMontEstime(Double montEstime) {
		this.montEstime = montEstime;
	}

	@Column(name = "mont_vente")
	public Double getMontVente() {
		return montVente;
	}

	public void setMontVente(Double montVente) {
		this.montVente = montVente;
	}

	@Column(name = "etat_objet")
	public String getEtatObjet() {
		return etatObjet;
	}

	public void setEtatObjet(String etatObjet) {
		this.etatObjet = etatObjet;
	}

	@OneToOne
	@JoinColumn(name = "id_creance")
	public EuCreance getDette() {
		return dette;
	}

	public void setDette(EuCreance dette) {
		this.dette = dette;
	}

	@Column(name = "description_objet")
	public String getDescriptionObjet() {
		return descriptionObjet;
	}

	public void setDescriptionObjet(String descriptionObjet) {
		this.descriptionObjet = descriptionObjet;
	}

}

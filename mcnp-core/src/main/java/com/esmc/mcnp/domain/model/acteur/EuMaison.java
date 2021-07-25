package com.esmc.mcnp.model.acteur;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_maison database table.
 * 
 */
@Entity
@Table(name="eu_maison")
@NamedQuery(name="EuMaison.findAll", query="SELECT e FROM EuMaison e")
public class EuMaison implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idMaison;
	private String codeMembre;
	private Date dateEnregistrement;
	private String descMaison;
	private Integer idProprietaire;
	private Integer idUtilisateur;
	
	public EuMaison() {
	}


	@Id
	@Column(name="id_maison", unique=true, nullable=false)
	public Long getIdMaison() {
		return this.idMaison;
	}

	public void setIdMaison(Long idMaison) {
		this.idMaison = idMaison;
	}


	@Column(name="code_membre", length=100)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_enregistrement")
	public Date getDateEnregistrement() {
		return this.dateEnregistrement;
	}

	public void setDateEnregistrement(Date dateEnregistrement) {
		this.dateEnregistrement = dateEnregistrement;
	}


	@Column(name="desc_maison", length=255)
	public String getDescMaison() {
		return this.descMaison;
	}

	public void setDescMaison(String descMaison) {
		this.descMaison = descMaison;
	}


	@Column(name="id_proprietaire")
	public Integer getIdProprietaire() {
		return idProprietaire;
	}


	public void setIdProprietaire(Integer idProprietaire) {
		this.idProprietaire = idProprietaire;
	}

	@Column(name="id_utilisateur")
	public Integer getIdUtilisateur() {
		return idUtilisateur;
	}


	public void setIdUtilisateur(Integer idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}
}
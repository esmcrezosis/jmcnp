package com.esmc.mcnp.domain.entity.cmfh;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


/**
 * The persistent class for the eu_detail_mf107 database table.
 * 
 */
@Entity
@Table(name="eu_detail_mf107")
@NamedQuery(name="EuDetailMf107.findAll", query="SELECT e FROM EuDetailMf107 e")
public class EuDetailMf107 implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idMf107;
	private String codeMembre;
	private String creditcode;
	private Date dateMf107;
	private double montApport;
	private String numident;
	private double pourcentage;
	private Integer proprietaire;
	private EuUtilisateur euUtilisateur;

	public EuDetailMf107() {
	}


	@Id
	@Column(name="id_mf107", unique=true, nullable=false)
	public Long getIdMf107() {
		return this.idMf107;
	}

	public void setIdMf107(Long idMf107) {
		this.idMf107 = idMf107;
	}


	@Column(name="code_membre", length=100)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Column(length=80)
	public String getCreditcode() {
		return this.creditcode;
	}

	public void setCreditcode(String creditcode) {
		this.creditcode = creditcode;
	}

	@JsonFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_mf107")
	public Date getDateMf107() {
		return this.dateMf107;
	}

	public void setDateMf107(Date dateMf107) {
		this.dateMf107 = dateMf107;
	}


	@Column(name="mont_apport")
	public double getMontApport() {
		return this.montApport;
	}

	public void setMontApport(double montApport) {
		this.montApport = montApport;
	}


	@Column(length=120)
	public String getNumident() {
		return this.numident;
	}

	public void setNumident(String numident) {
		this.numident = numident;
	}


	public double getPourcentage() {
		return this.pourcentage;
	}

	public void setPourcentage(double pourcentage) {
		this.pourcentage = pourcentage;
	}


	public Integer getProprietaire() {
		return this.proprietaire;
	}

	public void setProprietaire(Integer proprietaire) {
		this.proprietaire = proprietaire;
	}


	//bi-directional many-to-one association to EuUtilisateur
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_utilisateur")
	public EuUtilisateur getEuUtilisateur() {
		return this.euUtilisateur;
	}

	public void setEuUtilisateur(EuUtilisateur euUtilisateur) {
		this.euUtilisateur = euUtilisateur;
	}

}
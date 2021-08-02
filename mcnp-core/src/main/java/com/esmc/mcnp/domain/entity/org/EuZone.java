package com.esmc.mcnp.domain.entity.org;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.others.EuDevise;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_zone database table.
 * 
 */
@Entity
@Table(name="eu_zone")
@NamedQuery(name="EuZone.findAll", query="SELECT e FROM EuZone e")
public class EuZone implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeZone;
	private Date dateCreation;
	private String nomZone;
	@JsonIgnore
	private List<EuPays> euPays;
	@JsonIgnore
	private EuUtilisateur euUtilisateur;
	@JsonIgnore
	private EuDevise euDevise;

	public EuZone() {
	}


	@Id
	@Column(name="code_zone", unique=true, nullable=false, length=100)
	public String getCodeZone() {
		return this.codeZone;
	}

	public void setCodeZone(String codeZone) {
		this.codeZone = codeZone;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_creation")
	public Date getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	@Column(name="nom_zone", nullable=false, length=100)
	public String getNomZone() {
		return this.nomZone;
	}

	public void setNomZone(String nomZone) {
		this.nomZone = nomZone;
	}


	//bi-directional many-to-one association to EuPays
	@OneToMany(mappedBy="euZone")
	public List<EuPays> getEuPays() {
		return this.euPays;
	}

	public void setEuPays(List<EuPays> euPays) {
		this.euPays = euPays;
	}

	public EuPays addEuPay(EuPays euPay) {
		getEuPays().add(euPay);
		euPay.setEuZone(this);

		return euPay;
	}

	public EuPays removeEuPay(EuPays euPay) {
		getEuPays().remove(euPay);
		euPay.setEuZone(null);

		return euPay;
	}


	//bi-directional many-to-one association to EuUtilisateur
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_utilisateur")
	public EuUtilisateur getEuUtilisateur() {
		return this.euUtilisateur;
	}

	public void setEuUtilisateur(EuUtilisateur euUtilisateur) {
		this.euUtilisateur = euUtilisateur;
	}


	//bi-directional many-to-one association to EuDevise
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_dev")
	public EuDevise getEuDevise() {
		return this.euDevise;
	}

	public void setEuDevise(EuDevise euDevise) {
		this.euDevise = euDevise;
	}

}
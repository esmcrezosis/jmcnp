package com.esmc.mcnp.domain.entity.acteur;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.acteur.EuTypeContrat;
import com.esmc.mcnp.domain.entity.org.EuPays;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

import java.util.Date;


/**
 * The persistent class for the eu_contrat database table.
 * 
 */
@Entity
@Table(name="eu_contrat")
@NamedQuery(name="EuContrat.findAll", query="SELECT e FROM EuContrat e")
public class EuContrat implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idContrat;
	private String codeMembre;
	private Date dateContrat;
	private String filiere;
	private double idTypeActeur;
	private double idTypeCreneau;
	private String natureContrat;
	private EuUtilisateur euUtilisateur;
	private EuPays euPay;
	private EuTypeContrat euTypeContrat;

	public EuContrat() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_contrat", unique=true, nullable=false)
	public Long getIdContrat() {
		return this.idContrat;
	}

	public void setIdContrat(Long idContrat) {
		this.idContrat = idContrat;
	}


	@Column(name="code_membre", length=100)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_contrat")
	public Date getDateContrat() {
		return this.dateContrat;
	}

	public void setDateContrat(Date dateContrat) {
		this.dateContrat = dateContrat;
	}


	@Column(length=100)
	public String getFiliere() {
		return this.filiere;
	}

	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}


	@Column(name="id_type_acteur")
	public double getIdTypeActeur() {
		return this.idTypeActeur;
	}

	public void setIdTypeActeur(double idTypeActeur) {
		this.idTypeActeur = idTypeActeur;
	}


	@Column(name="id_type_creneau")
	public double getIdTypeCreneau() {
		return this.idTypeCreneau;
	}

	public void setIdTypeCreneau(double idTypeCreneau) {
		this.idTypeCreneau = idTypeCreneau;
	}


	@Column(name="nature_contrat", length=80)
	public String getNatureContrat() {
		return this.natureContrat;
	}

	public void setNatureContrat(String natureContrat) {
		this.natureContrat = natureContrat;
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


	//bi-directional many-to-one association to EuPays
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_pays")
	public EuPays getEuPay() {
		return this.euPay;
	}

	public void setEuPay(EuPays euPay) {
		this.euPay = euPay;
	}


	//bi-directional many-to-one association to EuTypeContrat
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_type_contrat")
	public EuTypeContrat getEuTypeContrat() {
		return this.euTypeContrat;
	}

	public void setEuTypeContrat(EuTypeContrat euTypeContrat) {
		this.euTypeContrat = euTypeContrat;
	}

}
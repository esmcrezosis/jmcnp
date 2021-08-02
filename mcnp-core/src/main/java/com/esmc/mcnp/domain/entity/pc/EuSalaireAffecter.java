package com.esmc.mcnp.domain.entity.pc;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;


/**
 * The persistent class for the eu_salaire_affecter database table.
 * 
 */
@Entity
@Table(name="eu_salaire_affecter")
@NamedQuery(name="EuSalaireAffecter.findAll", query="SELECT e FROM EuSalaireAffecter e")
public class EuSalaireAffecter implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idAffectation;
	private Date dateAffectation;
	private Date dateDeb;
	private Date dateFin;
	private Date heureAffectation;
	private Long idCredit;
	private Long idCreditAffecter;
	private Long idOperation;
	private Long idSmc;
	private Long idUtilisateur;
	private double montAffecter;
	private String typeCncs;
	private EuMembre euMembre;
	private EuMembreMorale euMembreMorale;

	public EuSalaireAffecter() {
	}


	@Id
	@Column(name="id_affectation", unique=true, nullable=false)
	public Long getIdAffectation() {
		return this.idAffectation;
	}

	public void setIdAffectation(Long idAffectation) {
		this.idAffectation = idAffectation;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_affectation")
	public Date getDateAffectation() {
		return this.dateAffectation;
	}

	public void setDateAffectation(Date dateAffectation) {
		this.dateAffectation = dateAffectation;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_deb")
	public Date getDateDeb() {
		return this.dateDeb;
	}

	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_fin")
	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="heure_affectation")
	public Date getHeureAffectation() {
		return this.heureAffectation;
	}

	public void setHeureAffectation(Date heureAffectation) {
		this.heureAffectation = heureAffectation;
	}


	@Column(name="id_credit", nullable=false)
	public Long getIdCredit() {
		return this.idCredit;
	}

	public void setIdCredit(Long idCredit) {
		this.idCredit = idCredit;
	}


	@Column(name="id_credit_affecter", nullable=false)
	public Long getIdCreditAffecter() {
		return this.idCreditAffecter;
	}

	public void setIdCreditAffecter(Long idCreditAffecter) {
		this.idCreditAffecter = idCreditAffecter;
	}


	@Column(name="id_operation")
	public Long getIdOperation() {
		return this.idOperation;
	}

	public void setIdOperation(Long idOperation) {
		this.idOperation = idOperation;
	}


	@Column(name="id_smc", nullable=false)
	public Long getIdSmc() {
		return this.idSmc;
	}

	public void setIdSmc(Long idSmc) {
		this.idSmc = idSmc;
	}


	@Column(name="id_utilisateur")
	public Long getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	@Column(name="mont_affecter")
	public double getMontAffecter() {
		return this.montAffecter;
	}

	public void setMontAffecter(double montAffecter) {
		this.montAffecter = montAffecter;
	}


	@Column(name="type_cncs", length=28)
	public String getTypeCncs() {
		return this.typeCncs;
	}

	public void setTypeCncs(String typeCncs) {
		this.typeCncs = typeCncs;
	}


	//bi-directional many-to-one association to EuMembre
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre")
	public EuMembre getEuMembre() {
		return this.euMembre;
	}

	public void setEuMembre(EuMembre euMembre) {
		this.euMembre = euMembre;
	}


	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre_emp")
	public EuMembreMorale getEuMembreMorale() {
		return this.euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}

}
package com.esmc.mcnp.model.op;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_detail_appel_nn database table.
 *
 */
@Entity
@Table(name="eu_detail_appel_nn")
@NamedQuery(name="EuDetailAppelNn.findAll", query="SELECT e FROM EuDetailAppelNn e")
public class EuDetailAppelNn implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idDetailAppelNn;
	private String codeCompte;
	private String codeMembre;
	private String creditcode;
	private Date dateApport;
	private Date heureApport;
	private Long idUtilisateur;
	private double montantApport;
	private Integer payer;
	private EuAppelNn euAppelNn;
	private List<EuRepartitionNn> euRepartitionNns;

	public EuDetailAppelNn() {
	}


	@Id
	@Column(name="id_detail_appel_nn", unique=true, nullable=false)
	public Long getIdDetailAppelNn() {
		return this.idDetailAppelNn;
	}

	public void setIdDetailAppelNn(Long idDetailAppelNn) {
		this.idDetailAppelNn = idDetailAppelNn;
	}


	@Column(name="code_compte", length=45)
	public String getCodeCompte() {
		return this.codeCompte;
	}

	public void setCodeCompte(String codeCompte) {
		this.codeCompte = codeCompte;
	}


	@Column(name="code_membre", length=25)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Column(length=20)
	public String getCreditcode() {
		return this.creditcode;
	}

	public void setCreditcode(String creditcode) {
		this.creditcode = creditcode;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_apport")
	public Date getDateApport() {
		return this.dateApport;
	}

	public void setDateApport(Date dateApport) {
		this.dateApport = dateApport;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="heure_apport")
	public Date getHeureApport() {
		return this.heureApport;
	}

	public void setHeureApport(Date heureApport) {
		this.heureApport = heureApport;
	}


	@Column(name="id_utilisateur")
	public Long getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	@Column(name="montant_apport")
	public double getMontantApport() {
		return this.montantApport;
	}

	public void setMontantApport(double montantApport) {
		this.montantApport = montantApport;
	}


	public Integer getPayer() {
		return this.payer;
	}

	public void setPayer(Integer payer) {
		this.payer = payer;
	}


	//bi-directional many-to-one association to EuAppelNn
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_appel_nn")
	public EuAppelNn getEuAppelNn() {
		return this.euAppelNn;
	}

	public void setEuAppelNn(EuAppelNn euAppelNn) {
		this.euAppelNn = euAppelNn;
	}


	//bi-directional many-to-one association to EuRepartitionNn
	@OneToMany(mappedBy="euDetailAppelNn")
	public List<EuRepartitionNn> getEuRepartitionNns() {
		return this.euRepartitionNns;
	}

	public void setEuRepartitionNns(List<EuRepartitionNn> euRepartitionNns) {
		this.euRepartitionNns = euRepartitionNns;
	}

	public EuRepartitionNn addEuRepartitionNn(EuRepartitionNn euRepartitionNn) {
		getEuRepartitionNns().add(euRepartitionNn);
		euRepartitionNn.setEuDetailAppelNn(this);

		return euRepartitionNn;
	}

	public EuRepartitionNn removeEuRepartitionNn(EuRepartitionNn euRepartitionNn) {
		getEuRepartitionNns().remove(euRepartitionNn);
		euRepartitionNn.setEuDetailAppelNn(null);

		return euRepartitionNn;
	}

}
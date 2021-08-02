package com.esmc.mcnp.domain.entity.smcipn;

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

/**
 * The persistent class for the eu_servir database table.
 *
 */
@Entity
@Table(name = "eu_servir")
@NamedQuery(name = "EuServir.findAll", query = "SELECT e FROM EuServir e")
public class EuServir implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idServir;
	private Date dateCreation;
	private double montantAllouer;
	private EuSmcipnpwi euSmcipnpwi;
	private EuSmcipn smcipn;
	private EuFn euFn;

	public EuServir() {
	}

	@Id
	@Column(name = "id_servir", unique = true, nullable = false)
	public Long getIdServir() {
		return this.idServir;
	}

	public void setIdServir(Long idServir) {
		this.idServir = idServir;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_creation")
	public Date getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Column(name = "montant_allouer")
	public double getMontantAllouer() {
		return this.montantAllouer;
	}

	public void setMontantAllouer(double montantAllouer) {
		this.montantAllouer = montantAllouer;
	}

	// bi-directional many-to-one association to EuSmcipnpwi
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_smcipn")
	public EuSmcipnpwi getEuSmcipnpwi() {
		return this.euSmcipnpwi;
	}

	public void setEuSmcipnpwi(EuSmcipnpwi euSmcipnpwi) {
		this.euSmcipnpwi = euSmcipnpwi;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_smcipn")
	public EuSmcipn getSmcipn() {
		return smcipn;
	}

	public void setSmcipn(EuSmcipn smcipn) {
		this.smcipn = smcipn;
	}

	// bi-directional many-to-one association to EuFn
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_fn", nullable = false)
	public EuFn getEuFn() {
		return this.euFn;
	}

	public void setEuFn(EuFn euFn) {
		this.euFn = euFn;
	}

}

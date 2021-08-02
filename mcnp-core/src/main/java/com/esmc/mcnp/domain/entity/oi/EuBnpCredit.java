package com.esmc.mcnp.domain.entity.oi;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;

/**
 * The persistent class for the eu_bnp_credit database table.
 *
 */
@Entity
@Table(name = "eu_bnp_credit")
@NamedQuery(name = "EuBnpCredit.findAll", query = "SELECT e FROM EuBnpCredit e")
public class EuBnpCredit implements Serializable {

	private static final long serialVersionUID = 1L;
	private EuBnpCreditPK id;
	private double montConus;
	private double montCredit;
	private double montFs;
	private double montPanu;
	private double montPanuFs;
	private double montPar;
	private Integer periodeRemb;
	private EuBnp euBnp;
	private EuCompteCredit euCompteCredit;

	public EuBnpCredit() {
	}

	@EmbeddedId
	public EuBnpCreditPK getId() {
		return id;
	}

	public void setId(EuBnpCreditPK id) {
		this.id = id;
	}

	@Column(name = "mont_conus")
	public double getMontConus() {
		return montConus;
	}

	public void setMontConus(double montConus) {
		this.montConus = montConus;
	}

	@Column(name = "mont_credit")
	public double getMontCredit() {
		return montCredit;
	}

	public void setMontCredit(double montCredit) {
		this.montCredit = montCredit;
	}

	@Column(name = "mont_fs")
	public double getMontFs() {
		return montFs;
	}

	public void setMontFs(double montFs) {
		this.montFs = montFs;
	}

	@Column(name = "mont_panu")
	public double getMontPanu() {
		return montPanu;
	}

	public void setMontPanu(double montPanu) {
		this.montPanu = montPanu;
	}

	@Column(name = "mont_panu_fs")
	public double getMontPanuFs() {
		return montPanuFs;
	}

	public void setMontPanuFs(double montPanuFs) {
		this.montPanuFs = montPanuFs;
	}

	@Column(name = "mont_par")
	public double getMontPar() {
		return montPar;
	}

	public void setMontPar(double montPar) {
		this.montPar = montPar;
	}

	@Column(name = "periode_remb")
	public Integer getPeriodeRemb() {
		return periodeRemb;
	}

	public void setPeriodeRemb(Integer periodeRemb) {
		this.periodeRemb = periodeRemb;
	}

	// bi-directional many-to-one association to EuCompteCredit
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_credit", insertable = false, updatable = false, unique = true, nullable = false)
	public EuCompteCredit getEuCompteCredit() {
		return euCompteCredit;
	}

	public void setEuCompteCredit(EuCompteCredit euCompteCredit) {
		this.euCompteCredit = euCompteCredit;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_bnp", insertable = false, updatable = false, unique = true, nullable = false)
	public EuBnp getEuBnp() {
		return euBnp;
	}

	public void setEuBnp(EuBnp euBnp) {
		this.euBnp = euBnp;
	}

}

package com.esmc.mcnp.domain.entity.oi;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;
import com.esmc.mcnp.domain.entity.oi.EuBnp;

/**
 * The persistent class for the eu_detail_bnp database table.
 *
 */
@Entity
@Table(name = "eu_detail_bnp")
@NamedQuery(name = "EuDetailBnp.findAll", query = "SELECT e FROM EuDetailBnp e")
public class EuDetailBnp implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idDetail;
	private double montCapa;
	private double montConus;
	private double montFs;
	private double montPanu;
	private double montPanuFs;
	private double montPar;
	private double montantCredit;
	private Integer periode;
	private Integer renouvEffectue;
	private EuBnp euBnp;
	private EuCompteCredit euCompteCredit;

	public EuDetailBnp() {
	}

	@Id
	@Column(name = "id_detail", unique = true, nullable = false)
	public Long getIdDetail() {
		return this.idDetail;
	}

	public void setIdDetail(Long idDetail) {
		this.idDetail = idDetail;
	}

	@Column(name = "mont_capa")
	public double getMontCapa() {
		return this.montCapa;
	}

	public void setMontCapa(double montCapa) {
		this.montCapa = montCapa;
	}

	@Column(name = "mont_conus", nullable = false)
	public double getMontConus() {
		return this.montConus;
	}

	public void setMontConus(double montConus) {
		this.montConus = montConus;
	}

	@Column(name = "mont_fs", nullable = false)
	public double getMontFs() {
		return this.montFs;
	}

	public void setMontFs(double montFs) {
		this.montFs = montFs;
	}

	@Column(name = "mont_panu", nullable = false)
	public double getMontPanu() {
		return this.montPanu;
	}

	public void setMontPanu(double montPanu) {
		this.montPanu = montPanu;
	}

	@Column(name = "mont_panu_fs", nullable = false)
	public double getMontPanuFs() {
		return this.montPanuFs;
	}

	public void setMontPanuFs(double montPanuFs) {
		this.montPanuFs = montPanuFs;
	}

	@Column(name = "mont_par", nullable = false)
	public double getMontPar() {
		return this.montPar;
	}

	public void setMontPar(double montPar) {
		this.montPar = montPar;
	}

	@Column(name = "montant_credit", nullable = false)
	public double getMontantCredit() {
		return this.montantCredit;
	}

	public void setMontantCredit(double montantCredit) {
		this.montantCredit = montantCredit;
	}

	@Column(nullable = false)
	public Integer getPeriode() {
		return this.periode;
	}

	public void setPeriode(Integer periode) {
		this.periode = periode;
	}

	@Column(name = "renouv_effectue")
	public Integer getRenouvEffectue() {
		return this.renouvEffectue;
	}

	public void setRenouvEffectue(Integer renouvEffectue) {
		this.renouvEffectue = renouvEffectue;
	}

	// bi-directional many-to-one association to EuBnp
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_bnp", nullable = false)
	public EuBnp getEuBnp() {
		return this.euBnp;
	}

	public void setEuBnp(EuBnp euBnp) {
		this.euBnp = euBnp;
	}

	// bi-directional many-to-one association to EuCompteCredit
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_credit", nullable = false)
	public EuCompteCredit getEuCompteCredit() {
		return this.euCompteCredit;
	}

	public void setEuCompteCredit(EuCompteCredit euCompteCredit) {
		this.euCompteCredit = euCompteCredit;
	}

}
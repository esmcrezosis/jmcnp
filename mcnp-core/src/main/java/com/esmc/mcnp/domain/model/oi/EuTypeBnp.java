package com.esmc.mcnp.model.oi;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.model.oi.EuBnp;
import com.esmc.mcnp.model.oi.EuCaps;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the eu_type_bnp database table.
 * 
 */
@Entity
@Table(name="eu_type_bnp")
@NamedQuery(name="EuTypeBnp.findAll", query="SELECT e FROM EuTypeBnp e")
public class EuTypeBnp implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeTypeBnp;
	private String libelleBnp;
	private double txConus;
	private double txFs;
	private double txPanu;
	private double txPanuFs;
	private double txPar;
	private List<EuBnp> euBnps;
	private List<EuCaps> euCaps;

	public EuTypeBnp() {
	}


	@Id
	@Column(name="code_type_bnp", unique=true, nullable=false, length=100)
	public String getCodeTypeBnp() {
		return this.codeTypeBnp;
	}

	public void setCodeTypeBnp(String codeTypeBnp) {
		this.codeTypeBnp = codeTypeBnp;
	}


	@Column(name="libelle_bnp", length=255)
	public String getLibelleBnp() {
		return this.libelleBnp;
	}

	public void setLibelleBnp(String libelleBnp) {
		this.libelleBnp = libelleBnp;
	}


	@Column(name="tx_conus", nullable=false)
	public double getTxConus() {
		return this.txConus;
	}

	public void setTxConus(double txConus) {
		this.txConus = txConus;
	}


	@Column(name="tx_fs", nullable=false)
	public double getTxFs() {
		return this.txFs;
	}

	public void setTxFs(double txFs) {
		this.txFs = txFs;
	}


	@Column(name="tx_panu", nullable=false)
	public double getTxPanu() {
		return this.txPanu;
	}

	public void setTxPanu(double txPanu) {
		this.txPanu = txPanu;
	}


	@Column(name="tx_panu_fs", nullable=false)
	public double getTxPanuFs() {
		return this.txPanuFs;
	}

	public void setTxPanuFs(double txPanuFs) {
		this.txPanuFs = txPanuFs;
	}


	@Column(name="tx_par", nullable=false)
	public double getTxPar() {
		return this.txPar;
	}

	public void setTxPar(double txPar) {
		this.txPar = txPar;
	}


	//bi-directional many-to-one association to EuBnp
	@JsonIgnore
	@OneToMany(mappedBy="euTypeBnp")
	public List<EuBnp> getEuBnps() {
		return this.euBnps;
	}

	public void setEuBnps(List<EuBnp> euBnps) {
		this.euBnps = euBnps;
	}

	public EuBnp addEuBnp(EuBnp euBnp) {
		getEuBnps().add(euBnp);
		euBnp.setEuTypeBnp(this);

		return euBnp;
	}

	public EuBnp removeEuBnp(EuBnp euBnp) {
		getEuBnps().remove(euBnp);
		euBnp.setEuTypeBnp(null);

		return euBnp;
	}


	//bi-directional many-to-one association to EuCaps
	@JsonIgnore
	@OneToMany(mappedBy="euTypeBnp")
	public List<EuCaps> getEuCaps() {
		return this.euCaps;
	}

	public void setEuCaps(List<EuCaps> euCaps) {
		this.euCaps = euCaps;
	}

	public EuCaps addEuCap(EuCaps euCap) {
		getEuCaps().add(euCap);
		euCap.setEuTypeBnp(this);

		return euCap;
	}

	public EuCaps removeEuCap(EuCaps euCap) {
		getEuCaps().remove(euCap);
		euCap.setEuTypeBnp(null);

		return euCap;
	}

}
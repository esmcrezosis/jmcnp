package com.esmc.mcnp.model.obpsd;

import com.esmc.mcnp.model.cm.EuCompte;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the eu_gcp_pbf database table.
 * 
 */
@Entity
@Table(name="eu_gcp_pbf")
@NamedQuery(name="EuGcpPbf.findAll", query="SELECT e FROM EuGcpPbf e")
public class EuGcpPbf implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeGcpPbf;
	private double agioConsomme;
	private String codeMembre;
	private double gcpCompense;
	private double montAgio;
	private double montGcp;
	private double montGcpReel;
	private double soldeAgio;
	private double soldeGcp;
	private double soldeGcpReel;
	private String typeCapa;
	private List<EuDetailGcpPbf> euDetailGcpPbfs;
	private EuCompte euCompte;

	public EuGcpPbf() {
	}


	@Id
	@Column(name="code_gcp_pbf", unique=true, nullable=false, length=128)
	public String getCodeGcpPbf() {
		return this.codeGcpPbf;
	}

	public void setCodeGcpPbf(String codeGcpPbf) {
		this.codeGcpPbf = codeGcpPbf;
	}


	@Column(name="agio_consomme")
	public double getAgioConsomme() {
		return this.agioConsomme;
	}

	public void setAgioConsomme(double agioConsomme) {
		this.agioConsomme = agioConsomme;
	}


	@Column(name="code_membre", length=100)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Column(name="gcp_compense")
	public double getGcpCompense() {
		return this.gcpCompense;
	}

	public void setGcpCompense(double gcpCompense) {
		this.gcpCompense = gcpCompense;
	}


	@Column(name="mont_agio")
	public double getMontAgio() {
		return this.montAgio;
	}

	public void setMontAgio(double montAgio) {
		this.montAgio = montAgio;
	}


	@Column(name="mont_gcp")
	public double getMontGcp() {
		return this.montGcp;
	}

	public void setMontGcp(double montGcp) {
		this.montGcp = montGcp;
	}


	@Column(name="mont_gcp_reel")
	public double getMontGcpReel() {
		return this.montGcpReel;
	}

	public void setMontGcpReel(double montGcpReel) {
		this.montGcpReel = montGcpReel;
	}


	@Column(name="solde_agio")
	public double getSoldeAgio() {
		return this.soldeAgio;
	}

	public void setSoldeAgio(double soldeAgio) {
		this.soldeAgio = soldeAgio;
	}


	@Column(name="solde_gcp")
	public double getSoldeGcp() {
		return this.soldeGcp;
	}

	public void setSoldeGcp(double soldeGcp) {
		this.soldeGcp = soldeGcp;
	}


	@Column(name="solde_gcp_reel")
	public double getSoldeGcpReel() {
		return this.soldeGcpReel;
	}

	public void setSoldeGcpReel(double soldeGcpReel) {
		this.soldeGcpReel = soldeGcpReel;
	}


	@Column(name="type_capa", nullable=false, length=60)
	public String getTypeCapa() {
		return this.typeCapa;
	}

	public void setTypeCapa(String typeCapa) {
		this.typeCapa = typeCapa;
	}


	//bi-directional many-to-one association to EuDetailGcpPbf
	@OneToMany(mappedBy="euGcpPbf")
	public List<EuDetailGcpPbf> getEuDetailGcpPbfs() {
		return this.euDetailGcpPbfs;
	}

	public void setEuDetailGcpPbfs(List<EuDetailGcpPbf> euDetailGcpPbfs) {
		this.euDetailGcpPbfs = euDetailGcpPbfs;
	}

	public EuDetailGcpPbf addEuDetailGcpPbf(EuDetailGcpPbf euDetailGcpPbf) {
		getEuDetailGcpPbfs().add(euDetailGcpPbf);
		euDetailGcpPbf.setEuGcpPbf(this);

		return euDetailGcpPbf;
	}

	public EuDetailGcpPbf removeEuDetailGcpPbf(EuDetailGcpPbf euDetailGcpPbf) {
		getEuDetailGcpPbfs().remove(euDetailGcpPbf);
		euDetailGcpPbf.setEuGcpPbf(null);

		return euDetailGcpPbf;
	}


	//bi-directional many-to-one association to EuCompte
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_compte")
	public EuCompte getEuCompte() {
		return this.euCompte;
	}

	public void setEuCompte(EuCompte euCompte) {
		this.euCompte = euCompte;
	}

}
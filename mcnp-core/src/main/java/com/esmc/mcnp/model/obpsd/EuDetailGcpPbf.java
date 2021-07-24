package com.esmc.mcnp.model.obpsd;

import com.esmc.mcnp.model.obpsd.EuGcpPbf;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_detail_gcp_pbf database table.
 *
 */
@Entity
@Table(name="eu_detail_gcp_pbf")
@NamedQuery(name="EuDetailGcpPbf.findAll", query="SELECT e FROM EuDetailGcpPbf e")
public class EuDetailGcpPbf implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idGcpPbf;
	private double agio;
	private Integer compensable;
	private Long idCredit;
	private Long idEchange;
	private Long idEscompte;
	private double montGcpPbf;
	private double montPreleve;
	private double soldeGcpPbf;
	private String sourceCredit;
	private String typeCapa;
	private EuGcpPbf euGcpPbf;

	public EuDetailGcpPbf() {
	}


	@Id
	@Column(name="id_gcp_pbf", unique=true, nullable=false)
	public Long getIdGcpPbf() {
		return this.idGcpPbf;
	}

	public void setIdGcpPbf(Long idGcpPbf) {
		this.idGcpPbf = idGcpPbf;
	}


	public double getAgio() {
		return this.agio;
	}

	public void setAgio(double agio) {
		this.agio = agio;
	}


	public Integer getCompensable() {
		return this.compensable;
	}

	public void setCompensable(Integer compensable) {
		this.compensable = compensable;
	}


	@Column(name="id_credit")
	public Long getIdCredit() {
		return this.idCredit;
	}

	public void setIdCredit(Long idCredit) {
		this.idCredit = idCredit;
	}


	@Column(name="id_echange", nullable=false)
	public Long getIdEchange() {
		return this.idEchange;
	}

	public void setIdEchange(Long idEchange) {
		this.idEchange = idEchange;
	}


	@Column(name="id_escompte", nullable=false)
	public Long getIdEscompte() {
		return this.idEscompte;
	}

	public void setIdEscompte(Long idEscompte) {
		this.idEscompte = idEscompte;
	}


	@Column(name="mont_gcp_pbf")
	public double getMontGcpPbf() {
		return this.montGcpPbf;
	}

	public void setMontGcpPbf(double montGcpPbf) {
		this.montGcpPbf = montGcpPbf;
	}


	@Column(name="mont_preleve")
	public double getMontPreleve() {
		return this.montPreleve;
	}

	public void setMontPreleve(double montPreleve) {
		this.montPreleve = montPreleve;
	}


	@Column(name="solde_gcp_pbf")
	public double getSoldeGcpPbf() {
		return this.soldeGcpPbf;
	}

	public void setSoldeGcpPbf(double soldeGcpPbf) {
		this.soldeGcpPbf = soldeGcpPbf;
	}


	@Column(name="source_credit", length=100)
	public String getSourceCredit() {
		return this.sourceCredit;
	}

	public void setSourceCredit(String sourceCredit) {
		this.sourceCredit = sourceCredit;
	}


	@Column(name="type_capa", length=180)
	public String getTypeCapa() {
		return this.typeCapa;
	}

	public void setTypeCapa(String typeCapa) {
		this.typeCapa = typeCapa;
	}


	//bi-directional many-to-one association to EuGcpPbf
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_gcp_pbf")
	public EuGcpPbf getEuGcpPbf() {
		return this.euGcpPbf;
	}

	public void setEuGcpPbf(EuGcpPbf euGcpPbf) {
		this.euGcpPbf = euGcpPbf;
	}

}
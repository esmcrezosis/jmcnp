package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_detail_regsms database table.
 *
 */
@Entity
@Table(name="eu_detail_regsms")
@NamedQuery(name="EuDetailRegsms.findAll", query="SELECT e FROM EuDetailRegsms e")
public class EuDetailRegsms implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idRegsms;
	private String codeMembreDist;
	private String codeMembrePbf;
	private Date dateRegsms;
	private Long idDetailSmsmoney;
	private double montRegle;

	public EuDetailRegsms() {
	}


	@Id
	@Column(name="id_regsms", unique=true, nullable=false)
	public Long getIdRegsms() {
		return this.idRegsms;
	}

	public void setIdRegsms(Long idRegsms) {
		this.idRegsms = idRegsms;
	}


	@Column(name="code_membre_dist", length=180)
	public String getCodeMembreDist() {
		return this.codeMembreDist;
	}

	public void setCodeMembreDist(String codeMembreDist) {
		this.codeMembreDist = codeMembreDist;
	}


	@Column(name="code_membre_pbf", length=180)
	public String getCodeMembrePbf() {
		return this.codeMembrePbf;
	}

	public void setCodeMembrePbf(String codeMembrePbf) {
		this.codeMembrePbf = codeMembrePbf;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_regsms")
	public Date getDateRegsms() {
		return this.dateRegsms;
	}

	public void setDateRegsms(Date dateRegsms) {
		this.dateRegsms = dateRegsms;
	}


	@Column(name="id_detail_smsmoney")
	public Long getIdDetailSmsmoney() {
		return this.idDetailSmsmoney;
	}

	public void setIdDetailSmsmoney(Long idDetailSmsmoney) {
		this.idDetailSmsmoney = idDetailSmsmoney;
	}


	@Column(name="mont_regle", nullable=false)
	public double getMontRegle() {
		return this.montRegle;
	}

	public void setMontRegle(double montRegle) {
		this.montRegle = montRegle;
	}

}
package com.esmc.mcnp.domain.entity.bc;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the eu_credit_echange database table.
 *
 */
@Entity
@Table(name = "eu_credit_echange")
@NamedQuery(name = "EuCreditEchange.findAll", query = "SELECT e FROM EuCreditEchange e")
public class EuCreditEchange implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idCreditEchange;
	private double agio;
	private Long idCredit;
	private Long idEchange;
	private double montEchange;
	private String sourceCredit;

	public EuCreditEchange() {
	}

	@Id
	@Column(name = "id_credit_echange", unique = true, nullable = false)
	public Long getIdCreditEchange() {
		return this.idCreditEchange;
	}

	public void setIdCreditEchange(Long idCreditEchange) {
		this.idCreditEchange = idCreditEchange;
	}

	public double getAgio() {
		return this.agio;
	}

	public void setAgio(double agio) {
		this.agio = agio;
	}

	@Column(name = "id_credit", nullable = false)
	public Long getIdCredit() {
		return this.idCredit;
	}

	public void setIdCredit(Long idCredit) {
		this.idCredit = idCredit;
	}

	@Column(name = "id_echange", nullable = false)
	public Long getIdEchange() {
		return this.idEchange;
	}

	public void setIdEchange(Long idEchange) {
		this.idEchange = idEchange;
	}

	@Column(name = "mont_echange")
	public double getMontEchange() {
		return this.montEchange;
	}

	public void setMontEchange(double montEchange) {
		this.montEchange = montEchange;
	}

	@Column(name = "source_credit", length = 100)
	public String getSourceCredit() {
		return this.sourceCredit;
	}

	public void setSourceCredit(String sourceCredit) {
		this.sourceCredit = sourceCredit;
	}

}
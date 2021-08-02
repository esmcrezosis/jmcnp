package com.esmc.mcnp.domain.entity.bc;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the eu_detail_domicilie database table.
 * 
 */
@Embeddable
public class EuDetailDomiciliePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String codeDomicilier;
	private double idCredit;

	public EuDetailDomiciliePK() {
	}

	@Column(name="code_domicilier", insertable=false, updatable=false, unique=true, nullable=false, length=200)
	public String getCodeDomicilier() {
		return this.codeDomicilier;
	}
	public void setCodeDomicilier(String codeDomicilier) {
		this.codeDomicilier = codeDomicilier;
	}

	@Column(name="id_credit", insertable=false, updatable=false, unique=true, nullable=false)
	public double getIdCredit() {
		return this.idCredit;
	}
	public void setIdCredit(double idCredit) {
		this.idCredit = idCredit;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EuDetailDomiciliePK)) {
			return false;
		}
		EuDetailDomiciliePK castOther = (EuDetailDomiciliePK)other;
		return 
			this.codeDomicilier.equals(castOther.codeDomicilier)
			&& (this.idCredit == castOther.idCredit);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codeDomicilier.hashCode();
		hash = hash * prime + ((int) (java.lang.Double.doubleToLongBits(this.idCredit) ^ (java.lang.Double.doubleToLongBits(this.idCredit) >>> 32)));
		
		return hash;
	}
}
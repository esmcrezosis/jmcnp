package com.esmc.mcnp.domain.entity.cmfh;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the eu_detail_domicilie_mf database table.
 * 
 */
@Embeddable
public class EuDetailDomicilieMfPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private double idMf;
	private double idDomMf;

	public EuDetailDomicilieMfPK() {
	}

	@Column(name="id_mf", unique=true, nullable=false)
	public double getIdMf() {
		return this.idMf;
	}
	public void setIdMf(double idMf) {
		this.idMf = idMf;
	}

	@Column(name="id_dom_mf", unique=true, nullable=false)
	public double getIdDomMf() {
		return this.idDomMf;
	}
	public void setIdDomMf(double idDomMf) {
		this.idDomMf = idDomMf;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EuDetailDomicilieMfPK)) {
			return false;
		}
		EuDetailDomicilieMfPK castOther = (EuDetailDomicilieMfPK)other;
		return 
			(this.idMf == castOther.idMf)
			&& (this.idDomMf == castOther.idDomMf);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (java.lang.Double.doubleToLongBits(this.idMf) ^ (java.lang.Double.doubleToLongBits(this.idMf) >>> 32)));
		hash = hash * prime + ((int) (java.lang.Double.doubleToLongBits(this.idDomMf) ^ (java.lang.Double.doubleToLongBits(this.idDomMf) >>> 32)));
		
		return hash;
	}
}
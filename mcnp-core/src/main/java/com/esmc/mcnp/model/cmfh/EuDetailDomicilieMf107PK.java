package com.esmc.mcnp.model.cmfh;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the eu_detail_domicilie_mf107 database table.
 * 
 */
@Embeddable
public class EuDetailDomicilieMf107PK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private double idDom;
	private double idMf107;

	public EuDetailDomicilieMf107PK() {
	}

	@Column(name="id_dom", insertable=false, updatable=false, unique=true, nullable=false)
	public double getIdDom() {
		return this.idDom;
	}
	public void setIdDom(double idDom) {
		this.idDom = idDom;
	}

	@Column(name="id_mf107", unique=true, nullable=false)
	public double getIdMf107() {
		return this.idMf107;
	}
	public void setIdMf107(double idMf107) {
		this.idMf107 = idMf107;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EuDetailDomicilieMf107PK)) {
			return false;
		}
		EuDetailDomicilieMf107PK castOther = (EuDetailDomicilieMf107PK)other;
		return 
			(this.idDom == castOther.idDom)
			&& (this.idMf107 == castOther.idMf107);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (java.lang.Double.doubleToLongBits(this.idDom) ^ (java.lang.Double.doubleToLongBits(this.idDom) >>> 32)));
		hash = hash * prime + ((int) (java.lang.Double.doubleToLongBits(this.idMf107) ^ (java.lang.Double.doubleToLongBits(this.idMf107) >>> 32)));
		
		return hash;
	}
}
package com.esmc.mcnp.domain.entity.cmfh;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the eu_detail_domicilie_mf11000 database table.
 * 
 */
@Embeddable
public class EuDetailDomicilieMf11000PK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private double idDomi;
	private double idMf11000;

	public EuDetailDomicilieMf11000PK() {
	}

	@Column(name="id_domi", insertable=false, updatable=false, unique=true, nullable=false)
	public double getIdDomi() {
		return this.idDomi;
	}
	public void setIdDomi(double idDomi) {
		this.idDomi = idDomi;
	}

	@Column(name="id_mf11000", insertable=false, updatable=false, unique=true, nullable=false)
	public double getIdMf11000() {
		return this.idMf11000;
	}
	public void setIdMf11000(double idMf11000) {
		this.idMf11000 = idMf11000;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EuDetailDomicilieMf11000PK)) {
			return false;
		}
		EuDetailDomicilieMf11000PK castOther = (EuDetailDomicilieMf11000PK)other;
		return 
			(this.idDomi == castOther.idDomi)
			&& (this.idMf11000 == castOther.idMf11000);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (java.lang.Double.doubleToLongBits(this.idDomi) ^ (java.lang.Double.doubleToLongBits(this.idDomi) >>> 32)));
		hash = hash * prime + ((int) (java.lang.Double.doubleToLongBits(this.idMf11000) ^ (java.lang.Double.doubleToLongBits(this.idMf11000) >>> 32)));
		
		return hash;
	}
}
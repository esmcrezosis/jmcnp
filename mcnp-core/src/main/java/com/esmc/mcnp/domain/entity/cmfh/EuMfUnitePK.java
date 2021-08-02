package com.esmc.mcnp.domain.entity.cmfh;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the eu_mf_unite database table.
 * 
 */
@Embeddable
public class EuMfUnitePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private double idMf;
	private String codeUnite;

	public EuMfUnitePK() {
	}

	@Column(name="id_mf", unique=true, nullable=false)
	public double getIdMf() {
		return this.idMf;
	}
	public void setIdMf(double idMf) {
		this.idMf = idMf;
	}

	@Column(name="code_unite", insertable=false, updatable=false, unique=true, nullable=false, length=20)
	public String getCodeUnite() {
		return this.codeUnite;
	}
	public void setCodeUnite(String codeUnite) {
		this.codeUnite = codeUnite;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EuMfUnitePK)) {
			return false;
		}
		EuMfUnitePK castOther = (EuMfUnitePK)other;
		return 
			(this.idMf == castOther.idMf)
			&& this.codeUnite.equals(castOther.codeUnite);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (java.lang.Double.doubleToLongBits(this.idMf) ^ (java.lang.Double.doubleToLongBits(this.idMf) >>> 32)));
		hash = hash * prime + this.codeUnite.hashCode();
		
		return hash;
	}
}
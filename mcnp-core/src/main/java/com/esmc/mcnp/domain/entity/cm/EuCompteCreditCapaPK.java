package com.esmc.mcnp.domain.entity.cm;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the eu_compte_credit_capa database table.
 * 
 */
@Embeddable
public class EuCompteCreditCapaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
    private Long idCredit;
	private String codeCapa;

	public EuCompteCreditCapaPK() {
	}

	@Column(name="id_credit", nullable=false)
    public Long getIdCredit() {
		return this.idCredit;
	}
    public void setIdCredit(Long idCredit) {
		this.idCredit = idCredit;
	}

	@Column(name="code_capa", nullable=false, length=126)
	public String getCodeCapa() {
		return this.codeCapa;
	}
	public void setCodeCapa(String codeCapa) {
		this.codeCapa = codeCapa;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EuCompteCreditCapaPK)) {
			return false;
		}
		EuCompteCreditCapaPK castOther = (EuCompteCreditCapaPK)other;
		return 
			(this.idCredit .equals(castOther.idCredit))
			&& this.codeCapa.equals(castOther.codeCapa);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (java.lang.Double.doubleToLongBits(this.idCredit) ^ (java.lang.Double.doubleToLongBits(this.idCredit) >>> 32)));
		hash = hash * prime + this.codeCapa.hashCode();
		
		return hash;
	}
}
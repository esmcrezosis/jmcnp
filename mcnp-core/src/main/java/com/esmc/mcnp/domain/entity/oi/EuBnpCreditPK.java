package com.esmc.mcnp.domain.entity.oi;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EuBnpCreditPK implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String codeBnp;
	private long idCredit;

	public EuBnpCreditPK() {
	}

	public EuBnpCreditPK(String codeBnp, long idCredit) {
		super();
		this.codeBnp = codeBnp;
		this.idCredit = idCredit;
	}

	@Column(name = "code_bnp")
	public String getCodeBnp() {
		return codeBnp;
	}

	public void setCodeBnp(String codeBnp) {
		this.codeBnp = codeBnp;
	}

	@Column(name = "id_credit")
	public long getIdCredit() {
		return idCredit;
	}

	public void setIdCredit(long idCredit) {
		this.idCredit = idCredit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeBnp == null) ? 0 : codeBnp.hashCode());
		result = prime * result + (int) (idCredit ^ (idCredit >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		EuBnpCreditPK other = (EuBnpCreditPK) obj;
		if (codeBnp == null) {
			if (other.codeBnp != null) {
				return false;
			}
		} else if (!codeBnp.equals(other.codeBnp)) {
			return false;
		}
		if (idCredit != other.idCredit) {
			return false;
		}
		return true;
	}
}

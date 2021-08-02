package com.esmc.mcnp.domain.entity.obps;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the eu_detail_tpagcp database table.
 * 
 */
@Embeddable
public class EuDetailTpagcpPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long idTpagcp;
	private String codeTegc;

	public EuDetailTpagcpPK() {
	}

	public EuDetailTpagcpPK(Long idTpagcp, String codeTegc) {
		this.idTpagcp = idTpagcp;
		this.codeTegc = codeTegc;
	}

	@Column(name = "id_tpagcp")
	public Long getIdTpagcp() {
		return this.idTpagcp;
	}

	public void setIdTpagcp(Long idTpagcp) {
		this.idTpagcp = idTpagcp;
	}

	@Column(name = "code_tegc")
	public String getCodeTegc() {
		return this.codeTegc;
	}

	public void setCodeTegc(String codeTegc) {
		this.codeTegc = codeTegc;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EuDetailTpagcpPK)) {
			return false;
		}
		EuDetailTpagcpPK castOther = (EuDetailTpagcpPK) other;
		return this.idTpagcp.equals(castOther.idTpagcp) && this.codeTegc.equals(castOther.codeTegc);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idTpagcp.hashCode();
		hash = hash * prime + this.codeTegc.hashCode();

		return hash;
	}
}
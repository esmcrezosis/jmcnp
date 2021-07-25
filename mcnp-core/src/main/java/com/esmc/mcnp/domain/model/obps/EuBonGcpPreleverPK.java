package com.esmc.mcnp.model.obps;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.Long;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuBonGcpPreleverPK
 *
 */
@Embeddable
public class EuBonGcpPreleverPK implements Serializable {

	private Integer bonId;
	private Long idGcpPrelever;
	private static final long serialVersionUID = 1L;

	public EuBonGcpPreleverPK() {
	}

	public EuBonGcpPreleverPK(Integer bonId, Long idGcpPrelever) {
		this.bonId = bonId;
		this.idGcpPrelever = idGcpPrelever;
	}

	@Column(name = "bon_id")
	public Integer getBonId() {
		return this.bonId;
	}

	public void setBonId(Integer bonId) {
		this.bonId = bonId;
	}

	@Column(name = "id_gcp_prelever")
	public Long getIdGcpPrelever() {
		return this.idGcpPrelever;
	}

	public void setIdGcpPrelever(Long idGcpPrelever) {
		this.idGcpPrelever = idGcpPrelever;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bonId == null) ? 0 : bonId.hashCode());
		result = prime * result + ((idGcpPrelever == null) ? 0 : idGcpPrelever.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EuBonGcpPreleverPK other = (EuBonGcpPreleverPK) obj;
		if (bonId == null) {
			if (other.bonId != null)
				return false;
		} else if (!bonId.equals(other.bonId))
			return false;
		if (idGcpPrelever == null) {
			if (other.idGcpPrelever != null)
				return false;
		} else if (!idGcpPrelever.equals(other.idGcpPrelever))
			return false;
		return true;
	}

}

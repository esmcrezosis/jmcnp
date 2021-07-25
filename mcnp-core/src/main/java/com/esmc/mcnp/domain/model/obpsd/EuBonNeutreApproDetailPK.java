package com.esmc.mcnp.model.obpsd;

import java.io.Serializable;
import java.lang.Integer;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EubonNeutreApproDetailPK
 *
 */
@Embeddable
public class EuBonNeutreApproDetailPK implements Serializable {

	private Integer bonNeutreDetailId;
	private Integer bonNeutreApproId;
	private static final long serialVersionUID = 1L;

	public EuBonNeutreApproDetailPK() {
		super();
	}

	public EuBonNeutreApproDetailPK(Integer bonNeutreDetailId, Integer bonNeutreApproId) {
		super();
		this.bonNeutreDetailId = bonNeutreDetailId;
		this.bonNeutreApproId = bonNeutreApproId;
	}

	@Column(name = "bon_neutre_detail_id")
	public Integer getBonNeutreDetailId() {
		return this.bonNeutreDetailId;
	}

	public void setBonNeutreDetailId(Integer bonNeutreDetailId) {
		this.bonNeutreDetailId = bonNeutreDetailId;
	}

	@Column(name = "bon_neutre_appro_id")
	public Integer getBonNeutreApproId() {
		return this.bonNeutreApproId;
	}

	public void setBonNeutreApproId(Integer bonNeutreApproId) {
		this.bonNeutreApproId = bonNeutreApproId;
	}

	/*
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof EuBonNeutreApproDetailPK)) {
			return false;
		}
		EuBonNeutreApproDetailPK other = (EuBonNeutreApproDetailPK) o;
		return true
				&& (getBonNeutreDetailId() == null ? other.getBonNeutreDetailId() == null
						: getBonNeutreDetailId().equals(other.getBonNeutreDetailId()))
				&& (getBonNeutreApproId() == null ? other.getBonNeutreApproId() == null
						: getBonNeutreApproId().equals(other.getBonNeutreApproId()));
	}

	/*
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getBonNeutreDetailId() == null ? 0 : getBonNeutreDetailId().hashCode());
		result = prime * result + (getBonNeutreApproId() == null ? 0 : getBonNeutreApproId().hashCode());
		return result;
	}

}

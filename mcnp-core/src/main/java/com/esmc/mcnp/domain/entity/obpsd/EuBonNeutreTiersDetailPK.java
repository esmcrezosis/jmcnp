package com.esmc.mcnp.model.obpsd;

import java.io.Serializable;
import java.lang.Integer;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ID class for entity: EubonNeutreApproDetailPK
 *
 */
@Embeddable
public class EuBonNeutreTiersDetailPK implements Serializable {

	private Integer bonNeutreDetailId;
	private Integer bonNeutreTiersId;
	private static final long serialVersionUID = 1L;

	public EuBonNeutreTiersDetailPK() {
	}

	public EuBonNeutreTiersDetailPK(Integer bonNeutreDetailId, Integer bonNeutreTiersId) {
		super();
		this.bonNeutreDetailId = bonNeutreDetailId;
		this.bonNeutreTiersId = bonNeutreTiersId;
	}

	@Column(name = "bon_neutre_detail_id")
	public Integer getBonNeutreDetailId() {
		return this.bonNeutreDetailId;
	}

	public void setBonNeutreDetailId(Integer bonNeutreDetailId) {
		this.bonNeutreDetailId = bonNeutreDetailId;
	}

	@Column(name = "bon_neutre_tiers_id")
	public Integer getBonNeutreTiersId() {
		return this.bonNeutreTiersId;
	}

	public void setBonNeutreTiersId(Integer bonNeutreTiersId) {
		this.bonNeutreTiersId = bonNeutreTiersId;
	}

	/*
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof EuBonNeutreTiersDetailPK)) {
			return false;
		}
		EuBonNeutreTiersDetailPK other = (EuBonNeutreTiersDetailPK) o;
		return true
				&& (getBonNeutreDetailId() == null ? other.getBonNeutreDetailId() == null
						: getBonNeutreDetailId().equals(other.getBonNeutreDetailId()))
				&& (getBonNeutreTiersId() == null ? other.getBonNeutreTiersId() == null
						: getBonNeutreTiersId().equals(other.getBonNeutreTiersId()));
	}

	/*
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getBonNeutreDetailId() == null ? 0 : getBonNeutreDetailId().hashCode());
		result = prime * result + (getBonNeutreTiersId() == null ? 0 : getBonNeutreTiersId().hashCode());
		return result;
	}

}

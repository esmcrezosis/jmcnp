/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.domain.entity.obpsd;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author mawuli
 */
@Entity
@Table(name = "eu_bon_neutre_appro_detail")
public class EuBonNeutreApproDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	private EuBonNeutreApproDetailPK id;
	private Double bonNeutreApproDetailMontant;
	private Double bonNeutreApproDetailMontUtilise;
	private Double bonNeutreApproDetailSolde;
	private Date bonNeutreApproDetailDate;
	private String bonNeutreApproDetailBanque;

	@EmbeddedId
	public EuBonNeutreApproDetailPK getId() {
		return id;
	}

	public void setId(EuBonNeutreApproDetailPK id) {
		this.id = id;
	}

	@Column(name = "bon_neutre_appro_detail_montant")
	public Double getBonNeutreApproDetailMontant() {
		return bonNeutreApproDetailMontant;
	}

	public void setBonNeutreApproDetailMontant(Double bonNeutreApproDetailMontant) {
		this.bonNeutreApproDetailMontant = bonNeutreApproDetailMontant;
	}

	@Column(name = "bon_neutre_appro_detail_mont_utilise")
	public Double getBonNeutreApproDetailMontUtilise() {
		return bonNeutreApproDetailMontUtilise;
	}

	public void setBonNeutreApproDetailMontUtilise(Double bonNeutreApproDetailMontUtilise) {
		this.bonNeutreApproDetailMontUtilise = bonNeutreApproDetailMontUtilise;
	}

	@Column(name = "bon_neutre_appro_detail_solde")
	public Double getBonNeutreApproDetailSolde() {
		return bonNeutreApproDetailSolde;
	}

	public void setBonNeutreApproDetailSolde(Double bonNeutreApproDetailSolde) {
		this.bonNeutreApproDetailSolde = bonNeutreApproDetailSolde;
	}

	@Column(name = "bon_neutre_appro_detail_date")
	public Date getBonNeutreApproDetailDate() {
		return bonNeutreApproDetailDate;
	}

	public void setBonNeutreApproDetailDate(Date bonNeutreApproDetailDate) {
		this.bonNeutreApproDetailDate = bonNeutreApproDetailDate;
	}

	@Column(name = "bon_neutre_appro_detail_banque")
	public String getBonNeutreApproDetailBanque() {
		return bonNeutreApproDetailBanque;
	}

	public void setBonNeutreApproDetailBanque(String bonNeutreApproDetailBanque) {
		this.bonNeutreApproDetailBanque = bonNeutreApproDetailBanque;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof EuBonNeutreApproDetail)) {
			return false;
		}
		EuBonNeutreApproDetail other = (EuBonNeutreApproDetail) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.esmc.mcnp.model.pbf.EuBonNeutreApproDetail[ id=" + id + " ]";
	}

}

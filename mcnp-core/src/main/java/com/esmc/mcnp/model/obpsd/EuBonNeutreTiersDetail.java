package com.esmc.mcnp.model.obpsd;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: EuBonNeutreTiersDetail
 *
 */
@Entity
@Table(name = "eu_bon_neutre_tiers_detail")
public class EuBonNeutreTiersDetail implements Serializable {

	private EuBonNeutreTiersDetailPK id;
	private Double bonNeutreTiersDetailMontant;
	private Double bonNeutreTiersDetailMontUtilise;
	private Double bonNeutreTiersDetailSolde;
	private Date bonNeutreTiersDetailDate;
	private String bonNeutreTiersDetailBanque;
	private static final long serialVersionUID = 1L;

	public EuBonNeutreTiersDetail() {
		super();
	}

	@EmbeddedId
	public EuBonNeutreTiersDetailPK getId() {
		return this.id;
	}

	public void setId(EuBonNeutreTiersDetailPK id) {
		this.id = id;
	}

	@Column(name = "bon_neutre_tiers_detail_montant")
	public Double getBonNeutreTiersDetailMontant() {
		return this.bonNeutreTiersDetailMontant;
	}

	public void setBonNeutreTiersDetailMontant(Double bonNeutreTiersDetailMontant) {
		this.bonNeutreTiersDetailMontant = bonNeutreTiersDetailMontant;
	}

	@Column(name = "bon_neutre_tiers_detail_mont_utilise")
	public Double getBonNeutreTiersDetailMontUtilise() {
		return bonNeutreTiersDetailMontUtilise;
	}

	public void setBonNeutreTiersDetailMontUtilise(Double bonNeutreTiersDetailMontUtilise) {
		this.bonNeutreTiersDetailMontUtilise = bonNeutreTiersDetailMontUtilise;
	}

	@Column(name = "bon_neutre_tiers_detail_solde")
	public Double getBonNeutreTiersDetailSolde() {
		return bonNeutreTiersDetailSolde;
	}

	public void setBonNeutreTiersDetailSolde(Double bonNeutreTiersDetailSolde) {
		this.bonNeutreTiersDetailSolde = bonNeutreTiersDetailSolde;
	}

	@Column(name = "bon_neutre_tiers_detail_date")
	public Date getBonNeutreTiersDetailDate() {
		return this.bonNeutreTiersDetailDate;
	}

	public void setBonNeutreTiersDetailDate(Date bonNeutreTiersDetailDate) {
		this.bonNeutreTiersDetailDate = bonNeutreTiersDetailDate;
	}

	@Column(name = "bon_neutre_tiers_detail_banque")
	public String getBonNeutreTiersDetailBanque() {
		return this.bonNeutreTiersDetailBanque;
	}

	public void setBonNeutreTiersDetailBanque(String bonNeutreTiersDetailBanque) {
		this.bonNeutreTiersDetailBanque = bonNeutreTiersDetailBanque;
	}

}

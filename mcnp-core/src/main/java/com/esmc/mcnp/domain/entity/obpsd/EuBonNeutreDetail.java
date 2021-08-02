package com.esmc.mcnp.domain.entity.obpsd;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the eu_bon_neutre database table.
 * 
 */
@Entity
@Table(name = "eu_bon_neutre_detail")
@NamedQuery(name = "EuBonNeutreDetail.findAll", query = "SELECT e FROM EuBonNeutreDetail e")
public class EuBonNeutreDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private int bonNeutreDetailId;
	private String bonNeutreDetailBanque;
	private String bonNeutreDetailCode;
	private String bonNeutreDetailType;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date bonNeutreDetailDate;
	private String bonNeutreDetailNumero;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date bonNeutreDetailDateNumero;
	private double bonNeutreDetailMontant;
	private double bonNeutreDetailMontantSolde;
	private double bonNeutreDetailMontantUtilise;
	private String bonNeutreDetailVignette;
	private Integer bonNeutreTiersId;
	private Integer bonNeutreApproId;
	private EuBonNeutre euBonNeutre;
	private Integer idCanton;

	public EuBonNeutreDetail() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bon_neutre_detail_id", unique = true, nullable = false)
	public int getBonNeutreDetailId() {
		return this.bonNeutreDetailId;
	}

	public void setBonNeutreDetailId(int bonNeutreDetailId) {
		this.bonNeutreDetailId = bonNeutreDetailId;
	}

	@Column(name = "bon_neutre_detail_banque", length = 11)
	public String getBonNeutreDetailBanque() {
		return this.bonNeutreDetailBanque;
	}

	public void setBonNeutreDetailBanque(String bonNeutreDetailBanque) {
		this.bonNeutreDetailBanque = bonNeutreDetailBanque;
	}

	@Column(name = "bon_neutre_detail_code", length = 50)
	public String getBonNeutreDetailCode() {
		return this.bonNeutreDetailCode;
	}

	public void setBonNeutreDetailCode(String bonNeutreDetailCode) {
		this.bonNeutreDetailCode = bonNeutreDetailCode;
	}

	@Column(name = "bon_neutre_detail_type", length = 10)
	public String getBonNeutreDetailType() {
		return bonNeutreDetailType;
	}

	public void setBonNeutreDetailType(String bonNeutreDetailType) {
		this.bonNeutreDetailType = bonNeutreDetailType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "bon_neutre_detail_date")
	public Date getBonNeutreDetailDate() {
		return this.bonNeutreDetailDate;
	}

	public void setBonNeutreDetailDate(Date bonNeutreDetailDate) {
		this.bonNeutreDetailDate = bonNeutreDetailDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "bon_neutre_detail_date_numero")
	public Date getBonNeutreDetailDateNumero() {
		return this.bonNeutreDetailDateNumero;
	}

	public void setBonNeutreDetailDateNumero(Date bonNeutreDetailDateNumero) {
		this.bonNeutreDetailDateNumero = bonNeutreDetailDateNumero;
	}

	@Column(name = "bon_neutre_detail_montant")
	public double getBonNeutreDetailMontant() {
		return this.bonNeutreDetailMontant;
	}

	public void setBonNeutreDetailMontant(double bonNeutreDetailMontant) {
		this.bonNeutreDetailMontant = bonNeutreDetailMontant;
	}

	@Column(name = "bon_neutre_detail_montant_solde")
	public double getBonNeutreDetailMontantSolde() {
		return this.bonNeutreDetailMontantSolde;
	}

	public void setBonNeutreDetailMontantSolde(double bonNeutreDetailMontantSolde) {
		this.bonNeutreDetailMontantSolde = bonNeutreDetailMontantSolde;
	}

	@Column(name = "bon_neutre_detail_montant_utilise")
	public double getBonNeutreDetailMontantUtilise() {
		return this.bonNeutreDetailMontantUtilise;
	}

	public void setBonNeutreDetailMontantUtilise(double bonNeutreDetailMontantUtilise) {
		this.bonNeutreDetailMontantUtilise = bonNeutreDetailMontantUtilise;
	}

	@Column(name = "bon_neutre_detail_numero", length = 255)
	public String getBonNeutreDetailNumero() {
		return this.bonNeutreDetailNumero;
	}

	public void setBonNeutreDetailNumero(String bonNeutreDetailNumero) {
		this.bonNeutreDetailNumero = bonNeutreDetailNumero;
	}

	@Column(name = "bon_neutre_detail_vignette", length = 255)
	public String getBonNeutreDetailVignette() {
		return this.bonNeutreDetailVignette;
	}

	public void setBonNeutreDetailVignette(String bonNeutreDetailVignette) {
		this.bonNeutreDetailVignette = bonNeutreDetailVignette;
	}

	@Column(name = "bon_neutre_tiers_id")
	public Integer getBonNeutreTiersId() {
		return bonNeutreTiersId;
	}

	public void setBonNeutreTiersId(Integer bonNeutreTiersId) {
		this.bonNeutreTiersId = bonNeutreTiersId;
	}

	@Column(name = "bon_neutre_appro_id")
	public Integer getBonNeutreApproId() {
		return bonNeutreApproId;
	}

	public void setBonNeutreApproId(Integer bonNeutreApproId) {
		this.bonNeutreApproId = bonNeutreApproId;
	}

	@ManyToOne
	@JoinColumn(name = "bon_neutre_id")
	public EuBonNeutre getEuBonNeutre() {
		return euBonNeutre;
	}

	public void setEuBonNeutre(EuBonNeutre euBonNeutre) {
		this.euBonNeutre = euBonNeutre;
	}

	@Column(name = "id_canton")
	public Integer getIdCanton() {
		return idCanton;
	}

	public void setIdCanton(Integer idCanton) {
		this.idCanton = idCanton;
	}

}
package com.esmc.mcnp.model.obpsd;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuBonNeutreAppro
 *
 */
@Entity
@Table(name = "eu_bon_neutre_appro")
public class EuBonNeutreAppro implements Serializable {

	private Integer bonNeutreApproId;
	private String bonNeutreApproApporteur;
	private String bonNeutreApproBeneficiaire;
	private Date bonNeutreApproDate;
	private Double bonNeutreApproMontant;
	private static final long serialVersionUID = 1L;

	public EuBonNeutreAppro() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bon_neutre_appro_id")
	public Integer getBonNeutreApproId() {
		return this.bonNeutreApproId;
	}

	public void setBonNeutreApproId(Integer bonNeutreApproId) {
		this.bonNeutreApproId = bonNeutreApproId;
	}

	@Column(name = "bon_neutre_appro_apporteur")
	public String getBonNeutreApproApporteur() {
		return this.bonNeutreApproApporteur;
	}

	public void setBonNeutreApproApporteur(String bonNeutreApproApporteur) {
		this.bonNeutreApproApporteur = bonNeutreApproApporteur;
	}

	@Column(name = "bon_neutre_appro_beneficiaire")
	public String getBonNeutreApproBeneficiaire() {
		return this.bonNeutreApproBeneficiaire;
	}

	public void setBonNeutreApproBeneficiaire(String bonNeutreApproBeneficiaire) {
		this.bonNeutreApproBeneficiaire = bonNeutreApproBeneficiaire;
	}

	@Column(name = "bon_neutre_appro_date")
	public Date getBonNeutreApproDate() {
		return this.bonNeutreApproDate;
	}

	public void setBonNeutreApproDate(Date bonNeutreApproDate) {
		this.bonNeutreApproDate = bonNeutreApproDate;
	}

	@Column(name = "bon_neutre_appro_montant")
	public Double getBonNeutreApproMontant() {
		return this.bonNeutreApproMontant;
	}

	public void setBonNeutreApproMontant(Double bonNeutreApproMontant) {
		this.bonNeutreApproMontant = bonNeutreApproMontant;
	}

}

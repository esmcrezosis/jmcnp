package com.esmc.mcnp.domain.entity.obpsd;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuBonNeutreTiers
 *
 */
@Entity
@Table(name = "eu_bon_neutre_tiers")
public class EuBonNeutreTiers implements Serializable {

	private Integer bonNeutreTiersId;
	private String bonNeutreTiersApporteur;
	private String bonNeutreTiersBeneficiaire;
	private Date bonNeutreTiersDate;
	private Double bonNeutreTiersMontant;
	private static final long serialVersionUID = 1L;

	public EuBonNeutreTiers() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bon_neutre_tiers_id")
	public Integer getBonNeutreTiersId() {
		return this.bonNeutreTiersId;
	}

	public void setBonNeutreTiersId(Integer bonNeutreTiersId) {
		this.bonNeutreTiersId = bonNeutreTiersId;
	}

	@Column(name = "bon_neutre_tiers_apporteur")
	public String getBonNeutreTiersApporteur() {
		return this.bonNeutreTiersApporteur;
	}

	public void setBonNeutreTiersApporteur(String bonNeutreTiersApporteur) {
		this.bonNeutreTiersApporteur = bonNeutreTiersApporteur;
	}

	@Column(name = "bon_neutre_tiers_beneficiaire")
	public String getBonNeutreTiersBeneficiaire() {
		return this.bonNeutreTiersBeneficiaire;
	}

	public void setBonNeutreTiersBeneficiaire(String bonNeutreTiersBeneficiaire) {
		this.bonNeutreTiersBeneficiaire = bonNeutreTiersBeneficiaire;
	}

	@Column(name = "bon_neutre_tiers_date")
	public Date getBonNeutreTiersDate() {
		return this.bonNeutreTiersDate;
	}

	public void setBonNeutreTiersDate(Date bonNeutreTiersDate) {
		this.bonNeutreTiersDate = bonNeutreTiersDate;
	}

	@Column(name = "bon_neutre_tiers_montant")
	public Double getBonNeutreTiersMontant() {
		return this.bonNeutreTiersMontant;
	}

	public void setBonNeutreTiersMontant(Double bonNeutreTiersMontant) {
		this.bonNeutreTiersMontant = bonNeutreTiersMontant;
	}

}

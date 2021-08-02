package com.esmc.mcnp.domain.entity.obpsd;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the eu_bon_neutre database table.
 * 
 */
@Entity
@Table(name = "eu_bon_neutre")
@NamedQuery(name = "EuBonNeutre.findAll", query = "SELECT e FROM EuBonNeutre e")
public class EuBonNeutre implements Serializable {
	private static final long serialVersionUID = 1L;
	private int bonNeutreId;
	private String bonNeutreCode;
	private String bonNeutreCodeMembre;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date bonNeutreDate;
	private String bonNeutreEmail;
	private String bonNeutreMobile;
	private Double bonNeutreMontant;
	private Double bonNeutreMontantSolde;
	private Double bonNeutreMontantUtilise;
	private String bonNeutreNom;
	private String bonNeutrePrenom;
	private String bonNeutreRaison;
	private String bonNeutreType;
	private String bonNeutreCodebarre;

	public EuBonNeutre() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bon_neutre_id")
	public int getBonNeutreId() {
		return this.bonNeutreId;
	}

	public void setBonNeutreId(int bonNeutreId) {
		this.bonNeutreId = bonNeutreId;
	}

	@Column(name = "bon_neutre_code")
	public String getBonNeutreCode() {
		return this.bonNeutreCode;
	}

	public void setBonNeutreCode(String bonNeutreCode) {
		this.bonNeutreCode = bonNeutreCode;
	}

	@Column(name = "bon_neutre_code_membre")
	public String getBonNeutreCodeMembre() {
		return this.bonNeutreCodeMembre;
	}

	public void setBonNeutreCodeMembre(String bonNeutreCodeMembre) {
		this.bonNeutreCodeMembre = bonNeutreCodeMembre;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "bon_neutre_date")
	public Date getBonNeutreDate() {
		return this.bonNeutreDate;
	}

	public void setBonNeutreDate(Date bonNeutreDate) {
		this.bonNeutreDate = bonNeutreDate;
	}

	@Column(name = "bon_neutre_email")
	public String getBonNeutreEmail() {
		return this.bonNeutreEmail;
	}

	public void setBonNeutreEmail(String bonNeutreEmail) {
		this.bonNeutreEmail = bonNeutreEmail;
	}

	@Column(name = "bon_neutre_mobile")
	public String getBonNeutreMobile() {
		return this.bonNeutreMobile;
	}

	public void setBonNeutreMobile(String bonNeutreMobile) {
		this.bonNeutreMobile = bonNeutreMobile;
	}

	@Column(name = "bon_neutre_montant")
	public Double getBonNeutreMontant() {
		return this.bonNeutreMontant;
	}

	public void setBonNeutreMontant(Double bonNeutreMontant) {
		this.bonNeutreMontant = bonNeutreMontant;
	}

	@Column(name = "bon_neutre_montant_solde")
	public Double getBonNeutreMontantSolde() {
		return this.bonNeutreMontantSolde;
	}

	public void setBonNeutreMontantSolde(Double bonNeutreMontantSolde) {
		this.bonNeutreMontantSolde = bonNeutreMontantSolde;
	}

	@Column(name = "bon_neutre_montant_utilise")
	public double getBonNeutreMontantUtilise() {
		return this.bonNeutreMontantUtilise;
	}

	public void setBonNeutreMontantUtilise(double bonNeutreMontantUtilise) {
		this.bonNeutreMontantUtilise = bonNeutreMontantUtilise;
	}

	@Column(name = "bon_neutre_nom")
	public String getBonNeutreNom() {
		return this.bonNeutreNom;
	}

	public void setBonNeutreNom(String bonNeutreNom) {
		this.bonNeutreNom = bonNeutreNom;
	}

	@Column(name = "bon_neutre_prenom")
	public String getBonNeutrePrenom() {
		return this.bonNeutrePrenom;
	}

	public void setBonNeutrePrenom(String bonNeutrePrenom) {
		this.bonNeutrePrenom = bonNeutrePrenom;
	}

	@Column(name = "bon_neutre_raison")
	public String getBonNeutreRaison() {
		return this.bonNeutreRaison;
	}

	public void setBonNeutreRaison(String bonNeutreRaison) {
		this.bonNeutreRaison = bonNeutreRaison;
	}

	@Column(name = "bon_neutre_type")
	public String getBonNeutreType() {
		return this.bonNeutreType;
	}

	public void setBonNeutreType(String bonNeutreType) {
		this.bonNeutreType = bonNeutreType;
	}

	@Column(name = "bon_neutre_codebarre")
	public String getBonNeutreCodebarre() {
		return bonNeutreCodebarre;
	}

	public void setBonNeutreCodebarre(String bonNeutreCodebarre) {
		this.bonNeutreCodebarre = bonNeutreCodebarre;
	}

}
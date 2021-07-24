package com.esmc.mcnp.model.odd;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "eu_mstiers_listebc")
public class EuMstierListebc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idMstierListebc;
	private String codeMembreApporteur;
	private String codeMembreBenef;
	private String typeSouscription;
	private Date dateListebc;
	private Integer statut;
	private String typeListe;
	private Integer bonConso;
	private Integer fraisSolvabilite;
	private Integer peripherique;
	private Integer connectivite;
	private Integer assurance;
	private Integer deposit;
	private Integer compteBancaire;
	private String typeKit;
	private Integer idOdd;
	private String codeFicheOdd;
	private String codeBnp;
	private Long utilisateur;

	public EuMstierListebc() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_mstiers_listebc")
	public Integer getIdMstierListebc() {
		return idMstierListebc;
	}

	public void setIdMstierListebc(Integer idMstierListebc) {
		this.idMstierListebc = idMstierListebc;
	}

	@Column(name = "code_membre_apporteur")
	public String getCodeMembreApporteur() {
		return codeMembreApporteur;
	}

	public void setCodeMembreApporteur(String codeMembreApporteur) {
		this.codeMembreApporteur = codeMembreApporteur;
	}

	@Column(name = "code_membre_beneficiaire")
	public String getCodeMembreBenef() {
		return codeMembreBenef;
	}

	public void setCodeMembreBenef(String codeMembreBenef) {
		this.codeMembreBenef = codeMembreBenef;
	}

	@Column(name = "type_souscription")
	public String getTypeSouscription() {
		return typeSouscription;
	}

	public void setTypeSouscription(String typeSouscription) {
		this.typeSouscription = typeSouscription;
	}

	@Column(name = "code_bnp")
	public String getCodeBnp() {
		return codeBnp;
	}

	public void setCodeBnp(String codeBnp) {
		this.codeBnp = codeBnp;
	}

	@Column(name = "date_listebc")
	public Date getDateListebc() {
		return dateListebc;
	}

	public void setDateListebc(Date dateListebc) {
		this.dateListebc = dateListebc;
	}

	public Integer getStatut() {
		return statut;
	}

	public void setStatut(Integer statut) {
		this.statut = statut;
	}

	@Column(name = "type_liste")
	public String getTypeListe() {
		return typeListe;
	}

	public void setTypeListe(String typeListe) {
		this.typeListe = typeListe;
	}

	public Long getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Long utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Column(name = "bon_conso")
	public Integer getBonConso() {
		return bonConso;
	}

	public void setBonConso(Integer bonConso) {
		this.bonConso = bonConso;
	}

	@Column(name = "frais_solvabilite")
	public Integer getFraisSolvabilite() {
		return fraisSolvabilite;
	}

	public void setFraisSolvabilite(Integer fraisSolvabilite) {
		this.fraisSolvabilite = fraisSolvabilite;
	}

	public Integer getPeripherique() {
		return peripherique;
	}

	public void setPeripherique(Integer peripherique) {
		this.peripherique = peripherique;
	}

	public Integer getConnectivite() {
		return connectivite;
	}

	public void setConnectivite(Integer connectivite) {
		this.connectivite = connectivite;
	}

	public Integer getAssurance() {
		return assurance;
	}

	public void setAssurance(Integer assurance) {
		this.assurance = assurance;
	}

	public Integer getDeposit() {
		return deposit;
	}

	public void setDeposit(Integer deposit) {
		this.deposit = deposit;
	}

	@Column(name = "compte_bancaire")
	public Integer getCompteBancaire() {
		return compteBancaire;
	}

	public void setCompteBancaire(Integer compteBancaire) {
		this.compteBancaire = compteBancaire;
	}

	@Column(name = "type_kit")
	public String getTypeKit() {
		return typeKit;
	}

	public void setTypeKit(String typeKit) {
		this.typeKit = typeKit;
	}

	@Column(name = "id_odd")
	public Integer getIdOdd() {
		return idOdd;
	}

	public void setIdOdd(Integer idOdd) {
		this.idOdd = idOdd;
	}

	@Column(name = "code_fiche_odd", unique = true)
	public String getCodeFicheOdd() {
		return codeFicheOdd;
	}

	public void setCodeFicheOdd(String codeFicheOdd) {
		this.codeFicheOdd = codeFicheOdd;
	}

}

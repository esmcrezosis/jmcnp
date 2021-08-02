package com.esmc.mcnp.domain.entity.obps;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

import java.util.Date;

/**
 * The persistent class for the eu_commande database table.
 * 
 */
@Entity
@Table(name = "eu_commande")
@NamedQuery(name = "EuCommande.findAll", query = "SELECT e FROM EuCommande e")
public class EuCommande implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long codeCommande;
	private Date dateCommande;
	private Double montantCommande;
	private String codeMembreAcheteur;
	private String codeMembreVendeur;
	private String codeTegcVendeur;
	private String codeMembreLivreur;
	private String codeMembreTransitaire;
	private String codeMembreTransporteur;
	private String quartierAcheteur;
	private String villeAcheteur;
	private String telAcheteur;
	private String adresseLivraison;
	private String codeLivraison;
	private int executer;
	private int livrer;
	private Double fraisLivraison;
	private Double fraisTransit;
	private Double fraisTransport;
	private Date DateLivraison;
	private String codeZone;
	private int idPays;
	private int idRegion;
	private int idPrefecture;
	private int modeLivraison;
	private String typeRecurrent;
	private String periodeRecurrent;
	private String typeBon;
	private Double montantLivraison;
	private String codeConfirmation;
	private Integer bps;
	private Integer frequence;
	private EuUtilisateur euUtilisateur;

	public EuCommande() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "code_commande", unique = true, nullable = false)
	public Long getCodeCommande() {
		return codeCommande;
	}

	public void setCodeCommande(Long codeCommande) {
		this.codeCommande = codeCommande;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_commande")
	public Date getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}

	@Column(name = "montant_commande")
	public Double getMontantCommande() {
		return montantCommande;
	}

	public void setMontantCommande(Double montantCommande) {
		this.montantCommande = montantCommande;
	}

	@Column(name = "code_membre_acheteur")
	public String getCodeMembreAcheteur() {
		return codeMembreAcheteur;
	}

	public void setCodeMembreAcheteur(String codeMembreAcheteur) {
		this.codeMembreAcheteur = codeMembreAcheteur;
	}

	@Column(name = "code_membre_vendeur")
	public String getCodeMembreVendeur() {
		return codeMembreVendeur;
	}

	public void setCodeMembreVendeur(String codeMembreVendeur) {
		this.codeMembreVendeur = codeMembreVendeur;
	}

	@Column(name = "code_tegc_vendeur")
	public String getCodeTegcVendeur() {
		return codeTegcVendeur;
	}

	public void setCodeTegcVendeur(String codeTegcVendeur) {
		this.codeTegcVendeur = codeTegcVendeur;
	}

	@Column(name = "code_membre_livreur")
	public String getCodeMembreLivreur() {
		return codeMembreLivreur;
	}

	public void setCodeMembreLivreur(String codeMembreLivreur) {
		this.codeMembreLivreur = codeMembreLivreur;
	}

	@Column(name = "code_membre_transitaire")
	public String getCodeMembreTransitaire() {
		return codeMembreTransitaire;
	}

	public void setCodeMembreTransitaire(String codeMembreTransitaire) {
		this.codeMembreTransitaire = codeMembreTransitaire;
	}

	@Column(name = "code_membre_transporteur")
	public String getCodeMembreTransporteur() {
		return codeMembreTransporteur;
	}

	public void setCodeMembreTransporteur(String codeMembreTransporteur) {
		this.codeMembreTransporteur = codeMembreTransporteur;
	}

	@Column(name = "quartier_acheteur")
	public String getQuartierAcheteur() {
		return quartierAcheteur;
	}

	public void setQuartierAcheteur(String quartierAcheteur) {
		this.quartierAcheteur = quartierAcheteur;
	}

	@Column(name = "ville_Acheteur")
	public String getVilleAcheteur() {
		return villeAcheteur;
	}

	public void setVilleAcheteur(String villeAcheteur) {
		this.villeAcheteur = villeAcheteur;
	}

	@Column(name = "tel_acheteur")
	public String getTelAcheteur() {
		return telAcheteur;
	}

	public void setTelAcheteur(String telAcheteur) {
		this.telAcheteur = telAcheteur;
	}

	@Column(name = "adresse_livraison")
	public String getAdresseLivraison() {
		return adresseLivraison;
	}

	public void setAdresseLivraison(String adresseLivraison) {
		this.adresseLivraison = adresseLivraison;
	}

	@Column(name = "code_livraison")
	public String getCodeLivraison() {
		return codeLivraison;
	}

	public void setCodeLivraison(String codeLivraison) {
		this.codeLivraison = codeLivraison;
	}

	@Column(name = "executer")
	public int getExecuter() {
		return executer;
	}

	public void setExecuter(int executer) {
		this.executer = executer;
	}

	@Column(name = "livrer")
	public int getLivrer() {
		return livrer;
	}

	public void setLivrer(int livrer) {
		this.livrer = livrer;
	}

	@Column(name = "frais_livraison")
	public Double getFraisLivraison() {
		return fraisLivraison;
	}

	public void setFraisLivraison(Double fraisLivraison) {
		this.fraisLivraison = fraisLivraison;
	}

	@Column(name = "frais_transit")
	public Double getFraisTransit() {
		return fraisTransit;
	}

	public void setFraisTransit(Double fraisTransit) {
		this.fraisTransit = fraisTransit;
	}

	@Column(name = "frais_transport")
	public Double getFraisTransport() {
		return fraisTransport;
	}

	public void setFraisTransport(Double fraisTransport) {
		this.fraisTransport = fraisTransport;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_livraison")
	public Date getDateLivraison() {
		return DateLivraison;
	}

	public void setDateLivraison(Date dateLivraison) {
		DateLivraison = dateLivraison;
	}

	@Column(name = "code_zone")
	public String getCodeZone() {
		return codeZone;
	}

	public void setCodeZone(String codeZone) {
		this.codeZone = codeZone;
	}

	@Column(name = "id_pays")
	public int getIdPays() {
		return idPays;
	}

	public void setIdPays(int idPays) {
		this.idPays = idPays;
	}

	@Column(name = "id_region")
	public int getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(int idRegion) {
		this.idRegion = idRegion;
	}

	@Column(name = "id_prefecture")
	public int getIdPrefecture() {
		return idPrefecture;
	}

	public void setIdPrefecture(int idPrefecture) {
		this.idPrefecture = idPrefecture;
	}

	@Column(name = "mode_livraison")
	public int getModeLivraison() {
		return modeLivraison;
	}

	public void setModeLivraison(int modeLivraison) {
		this.modeLivraison = modeLivraison;
	}

	@Column(name = "type_recurrent")
	public String getTypeRecurrent() {
		return typeRecurrent;
	}

	public void setTypeRecurrent(String typeRecurrent) {
		this.typeRecurrent = typeRecurrent;
	}

	@Column(name = "periode_recurrent")
	public String getPeriodeRecurrent() {
		return periodeRecurrent;
	}

	public void setPeriodeRecurrent(String periodeRecurrent) {
		this.periodeRecurrent = periodeRecurrent;
	}

	@Column(name = "type_bon")
	public String getTypeBon() {
		return typeBon;
	}

	public void setTypeBon(String typeBon) {
		this.typeBon = typeBon;
	}

	@Column(name = "montant_livraison")
	public Double getMontantLivraison() {
		return montantLivraison;
	}

	public void setMontantLivraison(Double montantLivraison) {
		this.montantLivraison = montantLivraison;
	}

	@Column(name = "code_confirmation")
	public String getCodeConfirmation() {
		return codeConfirmation;
	}

	public void setCodeConfirmation(String codeConfirmation) {
		this.codeConfirmation = codeConfirmation;
	}

	@Column(name = "bps")
	public Integer getBps() {
		return bps;
	}

	public void setBps(Integer bps) {
		this.bps = bps;
	}

	@Column(name = "frequence")
	public Integer getFrequence() {
		return frequence;
	}

	public void setFrequence(Integer frequence) {
		this.frequence = frequence;
	}

	@ManyToOne
	@JoinColumn(name = "id_utilisateur")
	public EuUtilisateur getEuUtilisateur() {
		return euUtilisateur;
	}

	public void setEuUtilisateur(EuUtilisateur euUtilisateur) {
		this.euUtilisateur = euUtilisateur;
	}

}
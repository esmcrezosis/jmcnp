package com.esmc.mcnp.model.obpsd;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the eu_traite database table.
 *
 */
@Entity
@Table(name = "eu_traite")
@NamedQuery(name = "EuTraite.findAll", query = "SELECT e FROM EuTraite e")
public class EuTraite implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long traiteId;
	private String traiteCodeBanque;
	private Long traiteTegcp;
	private Integer traiter;
	private Date traiteDateDebut;
	private Date traiteDateFin;
	private Integer traiteDisponible;
	private Integer traiteImprimer;
	private String traiteNumero;
	private Integer traiteEscompteNature;
	private Integer traitePayer;
	private Integer traiteAvantVte;
	private Double traiteMontant;
	private String traiteStatut;
	private String modePaiement;
	private String referencePaiement;
	private Long idApprovisionnement;
	private String bonType;
	private EuTpagcp tpagcp;
	private EuBon euBon;
    private EuUtilisateur utilisateur;

	public EuTraite() {
	}

	@Id
	@Column(name = "traite_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getTraiteId() {
		return this.traiteId;
	}

	public void setTraiteId(Long traiteId) {
		this.traiteId = traiteId;
	}

	@Column(name = "traite_code_banque", length = 25)
	public String getTraiteCodeBanque() {
		return this.traiteCodeBanque;
	}

	public void setTraiteCodeBanque(String traiteCodeBanque) {
		this.traiteCodeBanque = traiteCodeBanque;
	}

	@Column(name = "traite_tegcp", insertable = false, updatable = false)
	public Long getTraiteTegcp() {
		return this.traiteTegcp;
	}

	public void setTraiteTegcp(Long traiteTegcp) {
		this.traiteTegcp = traiteTegcp;
	}

	public Integer getTraiter() {
		return this.traiter;
	}

	public void setTraiter(Integer traiter) {
		this.traiter = traiter;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "traite_date_debut")
	public Date getTraiteDateDebut() {
		return traiteDateDebut;
	}

	public void setTraiteDateDebut(Date traiteDateDebut) {
		this.traiteDateDebut = traiteDateDebut;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "traite_date_fin")
	public Date getTraiteDateFin() {
		return traiteDateFin;
	}

	public void setTraiteDateFin(Date traiteDateFin) {
		this.traiteDateFin = traiteDateFin;
	}

	@Column(name = "traite_disponible")
	public Integer getTraiteDisponible() {
		return traiteDisponible;
	}

	public void setTraiteDisponible(Integer traiteDisponible) {
		this.traiteDisponible = traiteDisponible;
	}

	@Column(name = "traite_imprimer")
	public Integer getTraiteImprimer() {
		return traiteImprimer;
	}

	public void setTraiteImprimer(Integer traiteImprimer) {
		this.traiteImprimer = traiteImprimer;
	}

	@Column(name = "traite_numero")
	public String getTraiteNumero() {
		return traiteNumero;
	}

	public void setTraiteNumero(String traiteNumero) {
		this.traiteNumero = traiteNumero;
	}

	@Column(name = "traite_escompte_nature")
	public Integer getTraiteEscompteNature() {
		return traiteEscompteNature;
	}

	public void setTraiteEscompteNature(Integer traiteEscompteNature) {
		this.traiteEscompteNature = traiteEscompteNature;
	}

	@Column(name = "traite_payer")
	public Integer getTraitePayer() {
		return traitePayer;
	}

	public void setTraitePayer(Integer traitePayer) {
		this.traitePayer = traitePayer;
	}

	@Column(name = "traite_avant_vte")
	public Integer getTraiteAvantVte() {
		return traiteAvantVte;
	}

	public void setTraiteAvantVte(Integer traiteAvantVte) {
		this.traiteAvantVte = traiteAvantVte;
	}

	@Column(name = "traite_montant")
	public Double getTraiteMontant() {
		return traiteMontant;
	}

	public void setTraiteMontant(Double traiteMontant) {
		this.traiteMontant = traiteMontant;
	}

	@Column(name = "traite_statut")
	public String getTraiteStatut() {
		return traiteStatut;
	}

	public void setTraiteStatut(String traiteStatut) {
		this.traiteStatut = traiteStatut;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_utilisateur")
	public EuUtilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(EuUtilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Column(name = "mode_paiement")
	public String getModePaiement() {
		return modePaiement;
	}

	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}

	@Column(name = "reference_paiement")
	public String getReferencePaiement() {
		return referencePaiement;
	}

	public void setReferencePaiement(String referencePaiement) {
		this.referencePaiement = referencePaiement;
	}

	/**
	 * @return the idApprovisionnement
	 */
	@Column(name = "id_approvisionnement")
	public Long getIdApprovisionnement() {
		return idApprovisionnement;
	}

	/**
	 * @param idApprovisionnement the idApprovisionnement to set
	 */
	public void setIdApprovisionnement(Long idApprovisionnement) {
		this.idApprovisionnement = idApprovisionnement;
	}
	
	@Column(name = "bon_type")
	public String getBonType() {
		return bonType;
	}

	public void setBonType(String bonType) {
		this.bonType = bonType;
	}
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bon_id")
	public EuBon getEuBon() {
		return euBon;
	}

	public void setEuBon(EuBon euBon) {
		this.euBon = euBon;
	}

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "traite_tegcp")
	public EuTpagcp getTpagcp() {
		return tpagcp;
	}

	public void setTpagcp(EuTpagcp tpagcp) {
		this.tpagcp = tpagcp;
	}
}

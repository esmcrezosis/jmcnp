package com.esmc.mcnp.model.acteur;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_souscription database table.
 * 
 */
@Entity
@Table(name="eu_souscription")
@NamedQuery(name="EuSouscription.findAll", query="SELECT e FROM EuSouscription e")
public class EuSouscription implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer souscriptionId;
	private String codeActivite;
	private String codeStatut;
	private String codeTypeActeur;
	private Integer erreur;
	private String erreurdescription;
	private Integer idCanton;
	private Integer idCompetence;
	private Integer idMetier;
	private Integer idPostulat;
	private Integer publier;
	private Integer quittanceInvalide;
	private String souscriptionAncienMembre;
	private Integer souscriptionAutonome;
	private String souscriptionBanque;
	private Date souscriptionDate;
	private Date souscriptionDateNumero;
	private String souscriptionEmail;
	private Integer souscriptionFiliere;
	private String souscriptionLogin;
	private Integer souscriptionMembreasso;
	private String souscriptionMobile;
	private Integer souscriptionMontant;
	private String souscriptionNom;
	private Integer souscriptionNombre;
	private String souscriptionNumero;
	private Integer souscriptionOrdre;
	private String souscriptionPasse;
	private String souscriptionPersonne;
	private String souscriptionPrenom;
	private String souscriptionProgramme;
	private String souscriptionQuartier;
	private String souscriptionRaison;
	private Integer souscriptionSouscription;
	private String souscriptionType;
	private Integer souscriptionTypeCandidat;
	private String souscriptionVignette;
	private String souscriptionVille;

	public EuSouscription() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="souscription_id")
	public Integer getSouscriptionId() {
		return this.souscriptionId;
	}

	public void setSouscriptionId(Integer souscriptionId) {
		this.souscriptionId = souscriptionId;
	}


	@Column(name="code_activite")
	public String getCodeActivite() {
		return this.codeActivite;
	}

	public void setCodeActivite(String codeActivite) {
		this.codeActivite = codeActivite;
	}


	@Column(name="code_statut")
	public String getCodeStatut() {
		return this.codeStatut;
	}

	public void setCodeStatut(String codeStatut) {
		this.codeStatut = codeStatut;
	}


	@Column(name="code_type_acteur")
	public String getCodeTypeActeur() {
		return this.codeTypeActeur;
	}

	public void setCodeTypeActeur(String codeTypeActeur) {
		this.codeTypeActeur = codeTypeActeur;
	}


	public Integer getErreur() {
		return this.erreur;
	}

	public void setErreur(Integer erreur) {
		this.erreur = erreur;
	}


	@Lob
	public String getErreurdescription() {
		return this.erreurdescription;
	}

	public void setErreurdescription(String erreurdescription) {
		this.erreurdescription = erreurdescription;
	}


	@Column(name="id_canton")
	public Integer getIdCanton() {
		return this.idCanton;
	}

	public void setIdCanton(Integer idCanton) {
		this.idCanton = idCanton;
	}


	@Column(name="id_competence")
	public Integer getIdCompetence() {
		return this.idCompetence;
	}

	public void setIdCompetence(Integer idCompetence) {
		this.idCompetence = idCompetence;
	}


	@Column(name="id_metier")
	public Integer getIdMetier() {
		return this.idMetier;
	}

	public void setIdMetier(Integer idMetier) {
		this.idMetier = idMetier;
	}


	@Column(name="id_postulat")
	public Integer getIdPostulat() {
		return this.idPostulat;
	}

	public void setIdPostulat(Integer idPostulat) {
		this.idPostulat = idPostulat;
	}


	public Integer getPublier() {
		return this.publier;
	}

	public void setPublier(Integer publier) {
		this.publier = publier;
	}


	@Column(name="quittance_invalide")
	public Integer getQuittanceInvalide() {
		return this.quittanceInvalide;
	}

	public void setQuittanceInvalide(Integer quittanceInvalide) {
		this.quittanceInvalide = quittanceInvalide;
	}


	@Column(name="souscription_ancien_membre")
	public String getSouscriptionAncienMembre() {
		return this.souscriptionAncienMembre;
	}

	public void setSouscriptionAncienMembre(String souscriptionAncienMembre) {
		this.souscriptionAncienMembre = souscriptionAncienMembre;
	}


	@Column(name="souscription_autonome")
	public Integer getSouscriptionAutonome() {
		return this.souscriptionAutonome;
	}

	public void setSouscriptionAutonome(Integer souscriptionAutonome) {
		this.souscriptionAutonome = souscriptionAutonome;
	}


	@Column(name="souscription_banque")
	public String getSouscriptionBanque() {
		return this.souscriptionBanque;
	}

	public void setSouscriptionBanque(String souscriptionBanque) {
		this.souscriptionBanque = souscriptionBanque;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="souscription_date")
	public Date getSouscriptionDate() {
		return this.souscriptionDate;
	}

	public void setSouscriptionDate(Date souscriptionDate) {
		this.souscriptionDate = souscriptionDate;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="souscription_date_numero")
	public Date getSouscriptionDateNumero() {
		return this.souscriptionDateNumero;
	}

	public void setSouscriptionDateNumero(Date souscriptionDateNumero) {
		this.souscriptionDateNumero = souscriptionDateNumero;
	}


	@Column(name="souscription_email")
	public String getSouscriptionEmail() {
		return this.souscriptionEmail;
	}

	public void setSouscriptionEmail(String souscriptionEmail) {
		this.souscriptionEmail = souscriptionEmail;
	}


	@Column(name="souscription_filiere")
	public Integer getSouscriptionFiliere() {
		return this.souscriptionFiliere;
	}

	public void setSouscriptionFiliere(Integer souscriptionFiliere) {
		this.souscriptionFiliere = souscriptionFiliere;
	}


	@Column(name="souscription_login")
	public String getSouscriptionLogin() {
		return this.souscriptionLogin;
	}

	public void setSouscriptionLogin(String souscriptionLogin) {
		this.souscriptionLogin = souscriptionLogin;
	}


	@Column(name="souscription_membreasso")
	public Integer getSouscriptionMembreasso() {
		return this.souscriptionMembreasso;
	}

	public void setSouscriptionMembreasso(Integer souscriptionMembreasso) {
		this.souscriptionMembreasso = souscriptionMembreasso;
	}


	@Column(name="souscription_mobile")
	public String getSouscriptionMobile() {
		return this.souscriptionMobile;
	}

	public void setSouscriptionMobile(String souscriptionMobile) {
		this.souscriptionMobile = souscriptionMobile;
	}


	@Column(name="souscription_montant")
	public Integer getSouscriptionMontant() {
		return this.souscriptionMontant;
	}

	public void setSouscriptionMontant(Integer souscriptionMontant) {
		this.souscriptionMontant = souscriptionMontant;
	}


	@Column(name="souscription_nom")
	public String getSouscriptionNom() {
		return this.souscriptionNom;
	}

	public void setSouscriptionNom(String souscriptionNom) {
		this.souscriptionNom = souscriptionNom;
	}


	@Column(name="souscription_nombre")
	public Integer getSouscriptionNombre() {
		return this.souscriptionNombre;
	}

	public void setSouscriptionNombre(Integer souscriptionNombre) {
		this.souscriptionNombre = souscriptionNombre;
	}


	@Column(name="souscription_numero")
	public String getSouscriptionNumero() {
		return this.souscriptionNumero;
	}

	public void setSouscriptionNumero(String souscriptionNumero) {
		this.souscriptionNumero = souscriptionNumero;
	}


	@Column(name="souscription_ordre")
	public Integer getSouscriptionOrdre() {
		return this.souscriptionOrdre;
	}

	public void setSouscriptionOrdre(Integer souscriptionOrdre) {
		this.souscriptionOrdre = souscriptionOrdre;
	}


	@Column(name="souscription_passe")
	public String getSouscriptionPasse() {
		return this.souscriptionPasse;
	}

	public void setSouscriptionPasse(String souscriptionPasse) {
		this.souscriptionPasse = souscriptionPasse;
	}


	@Column(name="souscription_personne")
	public String getSouscriptionPersonne() {
		return this.souscriptionPersonne;
	}

	public void setSouscriptionPersonne(String souscriptionPersonne) {
		this.souscriptionPersonne = souscriptionPersonne;
	}


	@Column(name="souscription_prenom")
	public String getSouscriptionPrenom() {
		return this.souscriptionPrenom;
	}

	public void setSouscriptionPrenom(String souscriptionPrenom) {
		this.souscriptionPrenom = souscriptionPrenom;
	}


	@Column(name="souscription_programme")
	public String getSouscriptionProgramme() {
		return this.souscriptionProgramme;
	}

	public void setSouscriptionProgramme(String souscriptionProgramme) {
		this.souscriptionProgramme = souscriptionProgramme;
	}


	@Column(name="souscription_quartier")
	public String getSouscriptionQuartier() {
		return this.souscriptionQuartier;
	}

	public void setSouscriptionQuartier(String souscriptionQuartier) {
		this.souscriptionQuartier = souscriptionQuartier;
	}


	@Column(name="souscription_raison")
	public String getSouscriptionRaison() {
		return this.souscriptionRaison;
	}

	public void setSouscriptionRaison(String souscriptionRaison) {
		this.souscriptionRaison = souscriptionRaison;
	}


	@Column(name="souscription_souscription")
	public Integer getSouscriptionSouscription() {
		return this.souscriptionSouscription;
	}

	public void setSouscriptionSouscription(Integer souscriptionSouscription) {
		this.souscriptionSouscription = souscriptionSouscription;
	}


	@Column(name="souscription_type")
	public String getSouscriptionType() {
		return this.souscriptionType;
	}

	public void setSouscriptionType(String souscriptionType) {
		this.souscriptionType = souscriptionType;
	}


	@Column(name="souscription_type_candidat")
	public Integer getSouscriptionTypeCandidat() {
		return this.souscriptionTypeCandidat;
	}

	public void setSouscriptionTypeCandidat(Integer souscriptionTypeCandidat) {
		this.souscriptionTypeCandidat = souscriptionTypeCandidat;
	}


	@Column(name="souscription_vignette")
	public String getSouscriptionVignette() {
		return this.souscriptionVignette;
	}

	public void setSouscriptionVignette(String souscriptionVignette) {
		this.souscriptionVignette = souscriptionVignette;
	}


	@Column(name="souscription_ville")
	public String getSouscriptionVille() {
		return this.souscriptionVille;
	}

	public void setSouscriptionVille(String souscriptionVille) {
		this.souscriptionVille = souscriptionVille;
	}

}
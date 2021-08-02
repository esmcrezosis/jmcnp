package com.esmc.mcnp.model.cm;

import com.esmc.mcnp.model.cm.EuReligion;
import com.esmc.mcnp.model.org.EuPays;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the eu_ancien_membre database table.
 *
 */
@Entity
@Table(name = "eu_ancien_membre")
@NamedQuery(name = "EuAncienMembre.findAll", query = "SELECT e FROM EuAncienMembre e")
public class EuAncienMembre implements Serializable {

    private static final long serialVersionUID = 1L;
    private String ancienCodeMembre;
    private String autoEnroler;
    private String bpMembre;
    private String codeAgence;
    private String codeGacFiliere;
    private String codeMembre;
    private String codeStatut;
    private String codeTypeActeur;
    private Date dateIdentification;
    private Date dateNaisMembre;
    private String domaineActivite;
    private String emailMembre;
    private String empreinteMembre;
    private Integer etatContrat;
    private String fingerprint1;
    private String fingerprint2;
    private String fingerprint3;
    private String fingerprint4;
    private String fingerprint5;
    private String formation;
    private Date heureIdentification;
    private Long idUtilisateur;
    private String lieuNaisMembre;
    private String mereMembre;
    private String mifarecard;
    private Integer nbrEnfMembre;
    private String nomMembre;
    private String numRegistreMembre;
    private String pereMembre;
    private String photoMembre;
    private String portableMembre;
    private String prenomMembre;
    private String professionMembre;
    private String quartierMembre;
    private String raisonSociale;
    private String sexeMembre;
    private String siteWeb;
    private String sitfamMembre;
    private String telMembre;
    private String typeMembre;
    private String villeMembre;
    private EuPays euPay;
    private EuReligion euReligion;

    public EuAncienMembre() {
    }

    @Id
    @Column(name = "ancien_code_membre", unique = true, nullable = false, length = 100)
    public String getAncienCodeMembre() {
        return this.ancienCodeMembre;
    }

    public void setAncienCodeMembre(String ancienCodeMembre) {
        this.ancienCodeMembre = ancienCodeMembre;
    }

    @Column(name = "auto_enroler", length = 4)
    public String getAutoEnroler() {
        return this.autoEnroler;
    }

    public void setAutoEnroler(String autoEnroler) {
        this.autoEnroler = autoEnroler;
    }

    @Column(name = "bp_membre", length = 100)
    public String getBpMembre() {
        return this.bpMembre;
    }

    public void setBpMembre(String bpMembre) {
        this.bpMembre = bpMembre;
    }

    @Column(name = "code_agence", length = 100)
    public String getCodeAgence() {
        return this.codeAgence;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    @Column(name = "code_gac_filiere", length = 100)
    public String getCodeGacFiliere() {
        return this.codeGacFiliere;
    }

    public void setCodeGacFiliere(String codeGacFiliere) {
        this.codeGacFiliere = codeGacFiliere;
    }

    @Column(name = "code_membre", length = 25)
    public String getCodeMembre() {
        return this.codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Column(name = "code_statut", length = 100)
    public String getCodeStatut() {
        return this.codeStatut;
    }

    public void setCodeStatut(String codeStatut) {
        this.codeStatut = codeStatut;
    }

    @Column(name = "code_type_acteur", length = 100)
    public String getCodeTypeActeur() {
        return this.codeTypeActeur;
    }

    public void setCodeTypeActeur(String codeTypeActeur) {
        this.codeTypeActeur = codeTypeActeur;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_identification")
    public Date getDateIdentification() {
        return this.dateIdentification;
    }

    public void setDateIdentification(Date dateIdentification) {
        this.dateIdentification = dateIdentification;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_nais_membre")
    public Date getDateNaisMembre() {
        return this.dateNaisMembre;
    }

    public void setDateNaisMembre(Date dateNaisMembre) {
        this.dateNaisMembre = dateNaisMembre;
    }

    @Column(name = "domaine_activite", length = 250)
    public String getDomaineActivite() {
        return this.domaineActivite;
    }

    public void setDomaineActivite(String domaineActivite) {
        this.domaineActivite = domaineActivite;
    }

    @Column(name = "email_membre", length = 150)
    public String getEmailMembre() {
        return this.emailMembre;
    }

    public void setEmailMembre(String emailMembre) {
        this.emailMembre = emailMembre;
    }

    @Column(name = "empreinte_membre", length = 200)
    public String getEmpreinteMembre() {
        return this.empreinteMembre;
    }

    public void setEmpreinteMembre(String empreinteMembre) {
        this.empreinteMembre = empreinteMembre;
    }

    @Column(name = "etat_contrat")
    public Integer getEtatContrat() {
        return this.etatContrat;
    }

    public void setEtatContrat(Integer etatContrat) {
        this.etatContrat = etatContrat;
    }

    @Column(length = 2000)
    public String getFingerprint1() {
        return this.fingerprint1;
    }

    public void setFingerprint1(String fingerprint1) {
        this.fingerprint1 = fingerprint1;
    }

    @Column(length = 2000)
    public String getFingerprint2() {
        return this.fingerprint2;
    }

    public void setFingerprint2(String fingerprint2) {
        this.fingerprint2 = fingerprint2;
    }

    @Column(length = 2000)
    public String getFingerprint3() {
        return this.fingerprint3;
    }

    public void setFingerprint3(String fingerprint3) {
        this.fingerprint3 = fingerprint3;
    }

    @Column(length = 2000)
    public String getFingerprint4() {
        return this.fingerprint4;
    }

    public void setFingerprint4(String fingerprint4) {
        this.fingerprint4 = fingerprint4;
    }

    @Column(length = 2000)
    public String getFingerprint5() {
        return this.fingerprint5;
    }

    public void setFingerprint5(String fingerprint5) {
        this.fingerprint5 = fingerprint5;
    }

    @Column(length = 200)
    public String getFormation() {
        return this.formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "heure_identification")
    public Date getHeureIdentification() {
        return this.heureIdentification;
    }

    public void setHeureIdentification(Date heureIdentification) {
        this.heureIdentification = heureIdentification;
    }

    @Column(name = "id_utilisateur")
    public Long getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Column(name = "lieu_nais_membre", length = 200)
    public String getLieuNaisMembre() {
        return this.lieuNaisMembre;
    }

    public void setLieuNaisMembre(String lieuNaisMembre) {
        this.lieuNaisMembre = lieuNaisMembre;
    }

    @Column(name = "mere_membre", length = 200)
    public String getMereMembre() {
        return this.mereMembre;
    }

    public void setMereMembre(String mereMembre) {
        this.mereMembre = mereMembre;
    }

    @Column(length = 2000)
    public String getMifarecard() {
        return this.mifarecard;
    }

    public void setMifarecard(String mifarecard) {
        this.mifarecard = mifarecard;
    }

    @Column(name = "nbr_enf_membre")
    public Integer getNbrEnfMembre() {
        return this.nbrEnfMembre;
    }

    public void setNbrEnfMembre(Integer nbrEnfMembre) {
        this.nbrEnfMembre = nbrEnfMembre;
    }

    @Column(name = "nom_membre", length = 200)
    public String getNomMembre() {
        return this.nomMembre;
    }

    public void setNomMembre(String nomMembre) {
        this.nomMembre = nomMembre;
    }

    @Column(name = "num_registre_membre", length = 200)
    public String getNumRegistreMembre() {
        return this.numRegistreMembre;
    }

    public void setNumRegistreMembre(String numRegistreMembre) {
        this.numRegistreMembre = numRegistreMembre;
    }

    @Column(name = "pere_membre", length = 200)
    public String getPereMembre() {
        return this.pereMembre;
    }

    public void setPereMembre(String pereMembre) {
        this.pereMembre = pereMembre;
    }

    @Column(name = "photo_membre", length = 200)
    public String getPhotoMembre() {
        return this.photoMembre;
    }

    public void setPhotoMembre(String photoMembre) {
        this.photoMembre = photoMembre;
    }

    @Column(name = "portable_membre", length = 100)
    public String getPortableMembre() {
        return this.portableMembre;
    }

    public void setPortableMembre(String portableMembre) {
        this.portableMembre = portableMembre;
    }

    @Column(name = "prenom_membre", length = 150)
    public String getPrenomMembre() {
        return this.prenomMembre;
    }

    public void setPrenomMembre(String prenomMembre) {
        this.prenomMembre = prenomMembre;
    }

    @Column(name = "profession_membre", length = 200)
    public String getProfessionMembre() {
        return this.professionMembre;
    }

    public void setProfessionMembre(String professionMembre) {
        this.professionMembre = professionMembre;
    }

    @Column(name = "quartier_membre", length = 200)
    public String getQuartierMembre() {
        return this.quartierMembre;
    }

    public void setQuartierMembre(String quartierMembre) {
        this.quartierMembre = quartierMembre;
    }

    @Column(name = "raison_sociale", length = 100)
    public String getRaisonSociale() {
        return this.raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    @Column(name = "sexe_membre", length = 36)
    public String getSexeMembre() {
        return this.sexeMembre;
    }

    public void setSexeMembre(String sexeMembre) {
        this.sexeMembre = sexeMembre;
    }

    @Column(name = "site_web", length = 200)
    public String getSiteWeb() {
        return this.siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    @Column(name = "sitfam_membre", length = 200)
    public String getSitfamMembre() {
        return this.sitfamMembre;
    }

    public void setSitfamMembre(String sitfamMembre) {
        this.sitfamMembre = sitfamMembre;
    }

    @Column(name = "tel_membre", length = 100)
    public String getTelMembre() {
        return this.telMembre;
    }

    public void setTelMembre(String telMembre) {
        this.telMembre = telMembre;
    }

    @Column(name = "type_membre", nullable = false, length = 4)
    public String getTypeMembre() {
        return this.typeMembre;
    }

    public void setTypeMembre(String typeMembre) {
        this.typeMembre = typeMembre;
    }

    @Column(name = "ville_membre", nullable = false, length = 200)
    public String getVilleMembre() {
        return this.villeMembre;
    }

    public void setVilleMembre(String villeMembre) {
        this.villeMembre = villeMembre;
    }

    //bi-directional many-to-one association to EuPays
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pays")
    public EuPays getEuPay() {
        return this.euPay;
    }

    public void setEuPay(EuPays euPay) {
        this.euPay = euPay;
    }

    //bi-directional many-to-one association to EuReligion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_religion_membre")
    public EuReligion getEuReligion() {
        return this.euReligion;
    }

    public void setEuReligion(EuReligion euReligion) {
        this.euReligion = euReligion;
    }

}

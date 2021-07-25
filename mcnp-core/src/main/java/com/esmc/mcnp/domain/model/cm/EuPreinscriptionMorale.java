package com.esmc.mcnp.model.cm;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the eu_preinscription_morale database table.
 *
 */
@Entity
@Table(name = "eu_preinscription_morale")
@NamedQuery(name = "EuPreinscriptionMorale.findAll", query = "SELECT e FROM EuPreinscriptionMorale e")
public class EuPreinscriptionMorale implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idPreinscriptionMorale;
    private String bpMembre;
    private String categorieMembre;
    private String codeFkps;
    private String codeFl;
    private String codeFs;
    private String codeMembreMorale;
    private String codeRep;
    private String codeStatut;
    private String codeTypeActeur;
    private Date dateInscription;
    private String domaineActivite;
    private String emailMembre;
    private Date heureInscription;
    private Integer idPays;
    private String numRegistreMembre;
    private String numeroAgrementAcnev;
    private String numeroAgrementFiliere;
    private String numeroAgrementTechnopole;
    private String numeroContrat;
    private String portableMembre;
    private String quartierMembre;
    private String raisonSociale;
    private String siteWeb;
    private String telMembre;
    private String villeMembre;

    public EuPreinscriptionMorale() {
    }

    @Id
    @Column(name = "id_preinscription_morale", unique = true, nullable = false)
    public Integer getIdPreinscriptionMorale() {
        return this.idPreinscriptionMorale;
    }

    public void setIdPreinscriptionMorale(Integer idPreinscriptionMorale) {
        this.idPreinscriptionMorale = idPreinscriptionMorale;
    }

    @Column(name = "bp_membre", length = 50)
    public String getBpMembre() {
        return this.bpMembre;
    }

    public void setBpMembre(String bpMembre) {
        this.bpMembre = bpMembre;
    }

    @Column(name = "categorie_membre", length = 100)
    public String getCategorieMembre() {
        return this.categorieMembre;
    }

    public void setCategorieMembre(String categorieMembre) {
        this.categorieMembre = categorieMembre;
    }

    @Column(name = "code_fkps", length = 25)
    public String getCodeFkps() {
        return this.codeFkps;
    }

    public void setCodeFkps(String codeFkps) {
        this.codeFkps = codeFkps;
    }

    @Column(name = "code_fl", length = 25)
    public String getCodeFl() {
        return this.codeFl;
    }

    public void setCodeFl(String codeFl) {
        this.codeFl = codeFl;
    }

    @Column(name = "code_fs", length = 25)
    public String getCodeFs() {
        return this.codeFs;
    }

    public void setCodeFs(String codeFs) {
        this.codeFs = codeFs;
    }

    @Column(name = "code_membre_morale", length = 25)
    public String getCodeMembreMorale() {
        return this.codeMembreMorale;
    }

    public void setCodeMembreMorale(String codeMembreMorale) {
        this.codeMembreMorale = codeMembreMorale;
    }

    @Column(name = "code_rep", length = 25)
    public String getCodeRep() {
        return this.codeRep;
    }

    public void setCodeRep(String codeRep) {
        this.codeRep = codeRep;
    }

    @Column(name = "code_statut", length = 25)
    public String getCodeStatut() {
        return this.codeStatut;
    }

    public void setCodeStatut(String codeStatut) {
        this.codeStatut = codeStatut;
    }

    @Column(name = "code_type_acteur", length = 25)
    public String getCodeTypeActeur() {
        return this.codeTypeActeur;
    }

    public void setCodeTypeActeur(String codeTypeActeur) {
        this.codeTypeActeur = codeTypeActeur;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_inscription")
    public Date getDateInscription() {
        return this.dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    @Column(name = "domaine_activite", length = 255)
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "heure_inscription")
    public Date getHeureInscription() {
        return this.heureInscription;
    }

    public void setHeureInscription(Date heureInscription) {
        this.heureInscription = heureInscription;
    }

    @Column(name = "id_pays")
    public Integer getIdPays() {
        return this.idPays;
    }

    public void setIdPays(Integer idPays) {
        this.idPays = idPays;
    }

    @Column(name = "num_registre_membre", length = 50)
    public String getNumRegistreMembre() {
        return this.numRegistreMembre;
    }

    public void setNumRegistreMembre(String numRegistreMembre) {
        this.numRegistreMembre = numRegistreMembre;
    }

    @Column(name = "numero_agrement_acnev", length = 100)
    public String getNumeroAgrementAcnev() {
        return this.numeroAgrementAcnev;
    }

    public void setNumeroAgrementAcnev(String numeroAgrementAcnev) {
        this.numeroAgrementAcnev = numeroAgrementAcnev;
    }

    @Column(name = "numero_agrement_filiere", length = 100)
    public String getNumeroAgrementFiliere() {
        return this.numeroAgrementFiliere;
    }

    public void setNumeroAgrementFiliere(String numeroAgrementFiliere) {
        this.numeroAgrementFiliere = numeroAgrementFiliere;
    }

    @Column(name = "numero_agrement_technopole", length = 100)
    public String getNumeroAgrementTechnopole() {
        return this.numeroAgrementTechnopole;
    }

    public void setNumeroAgrementTechnopole(String numeroAgrementTechnopole) {
        this.numeroAgrementTechnopole = numeroAgrementTechnopole;
    }

    @Column(name = "numero_contrat", length = 100)
    public String getNumeroContrat() {
        return this.numeroContrat;
    }

    public void setNumeroContrat(String numeroContrat) {
        this.numeroContrat = numeroContrat;
    }

    @Column(name = "portable_membre", length = 25)
    public String getPortableMembre() {
        return this.portableMembre;
    }

    public void setPortableMembre(String portableMembre) {
        this.portableMembre = portableMembre;
    }

    @Column(name = "quartier_membre", length = 200)
    public String getQuartierMembre() {
        return this.quartierMembre;
    }

    public void setQuartierMembre(String quartierMembre) {
        this.quartierMembre = quartierMembre;
    }

    @Column(name = "raison_sociale", length = 255)
    public String getRaisonSociale() {
        return this.raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    @Column(name = "site_web", length = 50)
    public String getSiteWeb() {
        return this.siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    @Column(name = "tel_membre", length = 25)
    public String getTelMembre() {
        return this.telMembre;
    }

    public void setTelMembre(String telMembre) {
        this.telMembre = telMembre;
    }

    @Column(name = "ville_membre", length = 25)
    public String getVilleMembre() {
        return this.villeMembre;
    }

    public void setVilleMembre(String villeMembre) {
        this.villeMembre = villeMembre;
    }

}

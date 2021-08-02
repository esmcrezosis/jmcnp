package com.esmc.mcnp.domain.entity.cm;

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
 * The persistent class for the eu_preinscription database table.
 *
 */
@Entity
@Table(name = "eu_preinscription")
@NamedQuery(name = "EuPreinscription.findAll", query = "SELECT e FROM EuPreinscription e")
public class EuPreinscription implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idPreinscription;
    private String bpMembre;
    private String codeFkps;
    private String codeFl;
    private String codeFs;
    private String codeMembre;
    private Date dateInscription;
    private Date dateNaisMembre;
    private String emailMembre;
    private String formation;
    private Date heureInscription;
    private Integer idPays;
    private Integer idReligionMembre;
    private String lieuNaisMembre;
    private String mereMembre;
    private Integer nbrEnfMembre;
    private String nomMembre;
    private String pereMembre;
    private String portableMembre;
    private String prenomMembre;
    private String professionMembre;
    private String quartierMembre;
    private String sexeMembre;
    private String sitfamMembre;
    private String telMembre;
    private String villeMembre;

    public EuPreinscription() {
    }

    @Id
    @Column(name = "id_preinscription", unique = true, nullable = false)
    public Integer getIdPreinscription() {
        return this.idPreinscription;
    }

    public void setIdPreinscription(Integer idPreinscription) {
        this.idPreinscription = idPreinscription;
    }

    @Column(name = "bp_membre", length = 25)
    public String getBpMembre() {
        return this.bpMembre;
    }

    public void setBpMembre(String bpMembre) {
        this.bpMembre = bpMembre;
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

    @Column(name = "code_membre", length = 25)
    public String getCodeMembre() {
        return this.codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_inscription")
    public Date getDateInscription() {
        return this.dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_nais_membre")
    public Date getDateNaisMembre() {
        return this.dateNaisMembre;
    }

    public void setDateNaisMembre(Date dateNaisMembre) {
        this.dateNaisMembre = dateNaisMembre;
    }

    @Column(name = "email_membre", length = 150)
    public String getEmailMembre() {
        return this.emailMembre;
    }

    public void setEmailMembre(String emailMembre) {
        this.emailMembre = emailMembre;
    }

    @Column(length = 50)
    public String getFormation() {
        return this.formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
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

    @Column(name = "id_religion_membre")
    public Integer getIdReligionMembre() {
        return this.idReligionMembre;
    }

    public void setIdReligionMembre(Integer idReligionMembre) {
        this.idReligionMembre = idReligionMembre;
    }

    @Column(name = "lieu_nais_membre", length = 50)
    public String getLieuNaisMembre() {
        return this.lieuNaisMembre;
    }

    public void setLieuNaisMembre(String lieuNaisMembre) {
        this.lieuNaisMembre = lieuNaisMembre;
    }

    @Column(name = "mere_membre", length = 50)
    public String getMereMembre() {
        return this.mereMembre;
    }

    public void setMereMembre(String mereMembre) {
        this.mereMembre = mereMembre;
    }

    @Column(name = "nbr_enf_membre")
    public Integer getNbrEnfMembre() {
        return this.nbrEnfMembre;
    }

    public void setNbrEnfMembre(Integer nbrEnfMembre) {
        this.nbrEnfMembre = nbrEnfMembre;
    }

    @Column(name = "nom_membre", length = 50)
    public String getNomMembre() {
        return this.nomMembre;
    }

    public void setNomMembre(String nomMembre) {
        this.nomMembre = nomMembre;
    }

    @Column(name = "pere_membre", length = 50)
    public String getPereMembre() {
        return this.pereMembre;
    }

    public void setPereMembre(String pereMembre) {
        this.pereMembre = pereMembre;
    }

    @Column(name = "portable_membre", length = 25)
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

    @Column(name = "profession_membre", length = 50)
    public String getProfessionMembre() {
        return this.professionMembre;
    }

    public void setProfessionMembre(String professionMembre) {
        this.professionMembre = professionMembre;
    }

    @Column(name = "quartier_membre", length = 50)
    public String getQuartierMembre() {
        return this.quartierMembre;
    }

    public void setQuartierMembre(String quartierMembre) {
        this.quartierMembre = quartierMembre;
    }

    @Column(name = "sexe_membre", length = 9)
    public String getSexeMembre() {
        return this.sexeMembre;
    }

    public void setSexeMembre(String sexeMembre) {
        this.sexeMembre = sexeMembre;
    }

    @Column(name = "sitfam_membre", length = 50)
    public String getSitfamMembre() {
        return this.sitfamMembre;
    }

    public void setSitfamMembre(String sitfamMembre) {
        this.sitfamMembre = sitfamMembre;
    }

    @Column(name = "tel_membre", length = 25)
    public String getTelMembre() {
        return this.telMembre;
    }

    public void setTelMembre(String telMembre) {
        this.telMembre = telMembre;
    }

    @Column(name = "ville_membre", length = 50)
    public String getVilleMembre() {
        return this.villeMembre;
    }

    public void setVilleMembre(String villeMembre) {
        this.villeMembre = villeMembre;
    }

}

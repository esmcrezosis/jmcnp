package com.esmc.mcnp.model.org;

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
 * The persistent class for the eu_tete_division database table.
 *
 */
@Entity
@Table(name = "eu_tete_division")
@NamedQuery(name = "EuTeteDivision.findAll", query = "SELECT e FROM EuTeteDivision e")
public class EuTeteDivision implements Serializable {

    private static final long serialVersionUID = 1L;
    private int idTeteDivision;
    private String codeActeur;
    private Integer codeAgenceCreate;
    private String codeMembre;
    private String codeMembreMorale;
    private String codeMondeCreate;
    private String codeSecteurCreate;
    private String codeSourceCreate;
    private String codeZoneCreate;
    private Date dateCreation;
    private Integer idFiliere;
    private Integer idPays;
    private Integer idRegion;
    private Long idUtilisateur;
    private String typeTeteDivision;

    public EuTeteDivision() {
    }

    @Id
    @Column(name = "id_tete_division", unique = true, nullable = false)
    public int getIdTeteDivision() {
        return this.idTeteDivision;
    }

    public void setIdTeteDivision(int idTeteDivision) {
        this.idTeteDivision = idTeteDivision;
    }

    @Column(name = "code_acteur", length = 20)
    public String getCodeActeur() {
        return this.codeActeur;
    }

    public void setCodeActeur(String codeActeur) {
        this.codeActeur = codeActeur;
    }

    @Column(name = "code_agence_create")
    public Integer getCodeAgenceCreate() {
        return this.codeAgenceCreate;
    }

    public void setCodeAgenceCreate(Integer codeAgenceCreate) {
        this.codeAgenceCreate = codeAgenceCreate;
    }

    @Column(name = "code_membre", length = 20)
    public String getCodeMembre() {
        return this.codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Column(name = "code_membre_morale", length = 20)
    public String getCodeMembreMorale() {
        return this.codeMembreMorale;
    }

    public void setCodeMembreMorale(String codeMembreMorale) {
        this.codeMembreMorale = codeMembreMorale;
    }

    @Column(name = "code_monde_create", length = 100)
    public String getCodeMondeCreate() {
        return this.codeMondeCreate;
    }

    public void setCodeMondeCreate(String codeMondeCreate) {
        this.codeMondeCreate = codeMondeCreate;
    }

    @Column(name = "code_secteur_create", length = 19)
    public String getCodeSecteurCreate() {
        return this.codeSecteurCreate;
    }

    public void setCodeSecteurCreate(String codeSecteurCreate) {
        this.codeSecteurCreate = codeSecteurCreate;
    }

    @Column(name = "code_source_create", length = 100)
    public String getCodeSourceCreate() {
        return this.codeSourceCreate;
    }

    public void setCodeSourceCreate(String codeSourceCreate) {
        this.codeSourceCreate = codeSourceCreate;
    }

    @Column(name = "code_zone_create", length = 100)
    public String getCodeZoneCreate() {
        return this.codeZoneCreate;
    }

    public void setCodeZoneCreate(String codeZoneCreate) {
        this.codeZoneCreate = codeZoneCreate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_creation")
    public Date getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Column(name = "id_filiere")
    public Integer getIdFiliere() {
        return this.idFiliere;
    }

    public void setIdFiliere(Integer idFiliere) {
        this.idFiliere = idFiliere;
    }

    @Column(name = "id_pays")
    public Integer getIdPays() {
        return this.idPays;
    }

    public void setIdPays(Integer idPays) {
        this.idPays = idPays;
    }

    @Column(name = "id_region")
    public Integer getIdRegion() {
        return this.idRegion;
    }

    public void setIdRegion(Integer idRegion) {
        this.idRegion = idRegion;
    }

    @Column(name = "id_utilisateur")
    public Long getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Column(name = "type_tete_division", length = 20)
    public String getTypeTeteDivision() {
        return this.typeTeteDivision;
    }

    public void setTypeTeteDivision(String typeTeteDivision) {
        this.typeTeteDivision = typeTeteDivision;
    }

}

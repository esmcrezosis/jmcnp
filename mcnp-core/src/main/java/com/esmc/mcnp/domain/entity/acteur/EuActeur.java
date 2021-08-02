package com.esmc.mcnp.domain.entity.acteur;

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

import com.esmc.mcnp.domain.entity.acteur.EuGac;

/**
 * The persistent class for the eu_acteur database table.
 *
 */
@Entity
@Table(name = "eu_acteur")
@NamedQuery(name = "EuActeur.findAll", query = "SELECT e FROM EuActeur e")
public class EuActeur implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idActeur;
    private String codeActeur;
    private String codeActivite;
    private String codeAgenceCreate;
    private String codeDivision;
    private String codeMembre;
    private String codeMondeCreate;
    private String codeSecteurCreate;
    private String codeSourceCreate;
    private String codeZoneCreate;
    private Date dateCreation;
    private Integer idPays;
    private Integer idRegion;
    private Long idPrefecture;
    private Long idCanton;
    private Long idUtilisateur;
    private String typeActeur;
    private EuGac euGac;

    public EuActeur() {
    }

    @Id
    @Column(name = "id_acteur", unique = true, nullable = false)
    public Long getIdActeur() {
        return this.idActeur;
    }

    public void setIdActeur(Long idActeur) {
        this.idActeur = idActeur;
    }

    @Column(name = "code_acteur", length = 180)
    public String getCodeActeur() {
        return this.codeActeur;
    }

    public void setCodeActeur(String codeActeur) {
        this.codeActeur = codeActeur;
    }

    @Column(name = "code_activite", length = 200)
    public String getCodeActivite() {
        return this.codeActivite;
    }

    public void setCodeActivite(String codeActivite) {
        this.codeActivite = codeActivite;
    }

    @Column(name = "code_agence_create", length = 100)
    public String getCodeAgenceCreate() {
        return this.codeAgenceCreate;
    }

    public void setCodeAgenceCreate(String codeAgenceCreate) {
        this.codeAgenceCreate = codeAgenceCreate;
    }

    @Column(name = "code_division", length = 100)
    public String getCodeDivision() {
        return this.codeDivision;
    }

    public void setCodeDivision(String codeDivision) {
        this.codeDivision = codeDivision;
    }

    @Column(name = "code_membre", length = 100)
    public String getCodeMembre() {
        return this.codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Column(name = "code_monde_create", length = 100)
    public String getCodeMondeCreate() {
        return this.codeMondeCreate;
    }

    public void setCodeMondeCreate(String codeMondeCreate) {
        this.codeMondeCreate = codeMondeCreate;
    }

    @Column(name = "code_secteur_create", length = 100)
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

    @Column(name = "id_prefecture")
    public Long getIdPrefecture() {
		return idPrefecture;
	}

	public void setIdPrefecture(Long idPrefecture) {
		this.idPrefecture = idPrefecture;
	}

	@Column(name = "id_canton")
	public Long getIdCanton() {
		return idCanton;
	}

	public void setIdCanton(Long idCanton) {
		this.idCanton = idCanton;
	}

	@Column(name = "id_utilisateur")
    public Long getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Column(name = "type_acteur", length = 180)
    public String getTypeActeur() {
        return this.typeActeur;
    }

    public void setTypeActeur(String typeActeur) {
        this.typeActeur = typeActeur;
    }

    //bi-directional many-to-one association to EuGac
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_gac_chaine")
    public EuGac getEuGac() {
        return this.euGac;
    }

    public void setEuGac(EuGac euGac) {
        this.euGac = euGac;
    }

}

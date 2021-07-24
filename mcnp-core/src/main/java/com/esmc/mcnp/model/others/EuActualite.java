package com.esmc.mcnp.model.others;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the eu_actualite database table.
 *
 */
@Entity
@Table(name = "eu_actualite")
@NamedQuery(name = "EuActualite.findAll", query = "SELECT e FROM EuActualite e")
public class EuActualite implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer actualiteId;
    private Date actualiteDate;
    private String actualiteDescription;
    private String actualiteLibelle;
    private String actualiteResume;
    private String actualiteType;
    private String actualiteVignette;
    private Integer publier;

    public EuActualite() {
    }

    @Id
    @Column(name = "actualite_id", unique = true, nullable = false)
    public Integer getActualiteId() {
        return this.actualiteId;
    }

    public void setActualiteId(Integer actualiteId) {
        this.actualiteId = actualiteId;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "actualite_date")
    public Date getActualiteDate() {
        return this.actualiteDate;
    }

    public void setActualiteDate(Date actualiteDate) {
        this.actualiteDate = actualiteDate;
    }

    @Lob
    @Column(name = "actualite_description")
    public String getActualiteDescription() {
        return this.actualiteDescription;
    }

    public void setActualiteDescription(String actualiteDescription) {
        this.actualiteDescription = actualiteDescription;
    }

    @Column(name = "actualite_libelle", length = 255)
    public String getActualiteLibelle() {
        return this.actualiteLibelle;
    }

    public void setActualiteLibelle(String actualiteLibelle) {
        this.actualiteLibelle = actualiteLibelle;
    }

    @Column(name = "actualite_resume", length = 255)
    public String getActualiteResume() {
        return this.actualiteResume;
    }

    public void setActualiteResume(String actualiteResume) {
        this.actualiteResume = actualiteResume;
    }

    @Column(name = "actualite_type", length = 20)
    public String getActualiteType() {
        return this.actualiteType;
    }

    public void setActualiteType(String actualiteType) {
        this.actualiteType = actualiteType;
    }

    @Column(name = "actualite_vignette", length = 255)
    public String getActualiteVignette() {
        return this.actualiteVignette;
    }

    public void setActualiteVignette(String actualiteVignette) {
        this.actualiteVignette = actualiteVignette;
    }

    public Integer getPublier() {
        return this.publier;
    }

    public void setPublier(Integer publier) {
        this.publier = publier;
    }

}

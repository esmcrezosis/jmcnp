package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_tache database table.
 *
 */
@Entity
@Table(name = "eu_tache")
@NamedQuery(name = "EuTache.findAll", query = "SELECT e FROM EuTache e")
public class EuTache implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer tacheId;
    private Integer publier;
    private String tacheCode;
    private String tacheDescription;
    private String tacheLibelle;
    private String tacheUrl;

    public EuTache() {
    }

    @Id
    @Column(name = "tache_id", unique = true, nullable = false)
    public Integer getTacheId() {
        return this.tacheId;
    }

    public void setTacheId(Integer tacheId) {
        this.tacheId = tacheId;
    }

    public Integer getPublier() {
        return this.publier;
    }

    public void setPublier(Integer publier) {
        this.publier = publier;
    }

    @Column(name = "tache_code", length = 255)
    public String getTacheCode() {
        return this.tacheCode;
    }

    public void setTacheCode(String tacheCode) {
        this.tacheCode = tacheCode;
    }

    @Lob
    @Column(name = "tache_description")
    public String getTacheDescription() {
        return this.tacheDescription;
    }

    public void setTacheDescription(String tacheDescription) {
        this.tacheDescription = tacheDescription;
    }

    @Column(name = "tache_libelle", length = 255)
    public String getTacheLibelle() {
        return this.tacheLibelle;
    }

    public void setTacheLibelle(String tacheLibelle) {
        this.tacheLibelle = tacheLibelle;
    }

    @Column(name = "tache_url", length = 255)
    public String getTacheUrl() {
        return this.tacheUrl;
    }

    public void setTacheUrl(String tacheUrl) {
        this.tacheUrl = tacheUrl;
    }

}

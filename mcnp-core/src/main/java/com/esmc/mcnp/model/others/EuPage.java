package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_page database table.
 *
 */
@Entity
@Table(name = "eu_page")
@NamedQuery(name = "EuPage.findAll", query = "SELECT e FROM EuPage e")
public class EuPage implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idPage;
    private Integer deroulant;
    private String description;
    private Integer menu;
    private Integer menusous;
    private Integer ordre;
    private Integer publier;
    private String resume;
    private Integer spotlight;
    private String titre;
    private String vignette;

    public EuPage() {
    }

    @Id
    @Column(name = "id_page", unique = true, nullable = false)
    public Integer getIdPage() {
        return this.idPage;
    }

    public void setIdPage(Integer idPage) {
        this.idPage = idPage;
    }

    public Integer getDeroulant() {
        return this.deroulant;
    }

    public void setDeroulant(Integer deroulant) {
        this.deroulant = deroulant;
    }

    @Lob
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMenu() {
        return this.menu;
    }

    public void setMenu(Integer menu) {
        this.menu = menu;
    }

    public Integer getMenusous() {
        return this.menusous;
    }

    public void setMenusous(Integer menusous) {
        this.menusous = menusous;
    }

    public Integer getOrdre() {
        return this.ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public Integer getPublier() {
        return this.publier;
    }

    public void setPublier(Integer publier) {
        this.publier = publier;
    }

    @Lob
    public String getResume() {
        return this.resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Integer getSpotlight() {
        return this.spotlight;
    }

    public void setSpotlight(Integer spotlight) {
        this.spotlight = spotlight;
    }

    @Column(length = 250)
    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Column(length = 250)
    public String getVignette() {
        return this.vignette;
    }

    public void setVignette(String vignette) {
        this.vignette = vignette;
    }

}

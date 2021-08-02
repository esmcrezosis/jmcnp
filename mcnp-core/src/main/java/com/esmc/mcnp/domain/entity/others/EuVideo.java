package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_video database table.
 *
 */
@Entity
@Table(name = "eu_video")
@NamedQuery(name = "EuVideo.findAll", query = "SELECT e FROM EuVideo e")
public class EuVideo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer videoId;
    private Integer publier;
    private Integer videoCategorie;
    private String videoDescription;
    private String videoLibelle;
    private String videoType;

    public EuVideo() {
    }

    @Id
    @Column(name = "video_id", unique = true, nullable = false)
    public Integer getVideoId() {
        return this.videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getPublier() {
        return this.publier;
    }

    public void setPublier(Integer publier) {
        this.publier = publier;
    }

    @Column(name = "video_categorie")
    public Integer getVideoCategorie() {
        return this.videoCategorie;
    }

    public void setVideoCategorie(Integer videoCategorie) {
        this.videoCategorie = videoCategorie;
    }

    @Lob
    @Column(name = "video_description")
    public String getVideoDescription() {
        return this.videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    @Column(name = "video_libelle", length = 100)
    public String getVideoLibelle() {
        return this.videoLibelle;
    }

    public void setVideoLibelle(String videoLibelle) {
        this.videoLibelle = videoLibelle;
    }

    @Column(name = "video_type", length = 10)
    public String getVideoType() {
        return this.videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

}

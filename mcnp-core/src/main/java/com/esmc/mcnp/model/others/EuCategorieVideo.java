package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_categorie_video database table.
 * 
 */
@Entity
@Table(name="eu_categorie_video")
@NamedQuery(name="EuCategorieVideo.findAll", query="SELECT e FROM EuCategorieVideo e")
public class EuCategorieVideo implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idCategorieVideo;
	private String libelleCategorieVideo;

	public EuCategorieVideo() {
	}


	@Id
	@Column(name="id_categorie_video", unique=true, nullable=false)
	public double getIdCategorieVideo() {
		return this.idCategorieVideo;
	}

	public void setIdCategorieVideo(double idCategorieVideo) {
		this.idCategorieVideo = idCategorieVideo;
	}


	@Column(name="libelle_categorie_video", length=100)
	public String getLibelleCategorieVideo() {
		return this.libelleCategorieVideo;
	}

	public void setLibelleCategorieVideo(String libelleCategorieVideo) {
		this.libelleCategorieVideo = libelleCategorieVideo;
	}

}
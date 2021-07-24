package com.esmc.mcnp.model.op;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_detail_projet database table.
 * 
 */
@Entity
@Table(name="eu_detail_projet")
@NamedQuery(name="EuDetailProjet.findAll", query="SELECT e FROM EuDetailProjet e")
public class EuDetailProjet implements Serializable {
	private static final long serialVersionUID = 1L;
	private String detailProjetId;
	private String detailProjetFichier;
	private String detailProjetLibelle;
	private Integer etat;
	private Integer projetId;

	public EuDetailProjet() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="detail_projet_id")
	public String getDetailProjetId() {
		return this.detailProjetId;
	}

	public void setDetailProjetId(String detailProjetId) {
		this.detailProjetId = detailProjetId;
	}


	@Column(name="detail_projet_fichier")
	public String getDetailProjetFichier() {
		return this.detailProjetFichier;
	}

	public void setDetailProjetFichier(String detailProjetFichier) {
		this.detailProjetFichier = detailProjetFichier;
	}


	@Column(name="detail_projet_libelle")
	public String getDetailProjetLibelle() {
		return this.detailProjetLibelle;
	}

	public void setDetailProjetLibelle(String detailProjetLibelle) {
		this.detailProjetLibelle = detailProjetLibelle;
	}


	public Integer getEtat() {
		return this.etat;
	}

	public void setEtat(Integer etat) {
		this.etat = etat;
	}


	@Column(name="projet_id")
	public Integer getProjetId() {
		return this.projetId;
	}

	public void setProjetId(Integer projetId) {
		this.projetId = projetId;
	}

}
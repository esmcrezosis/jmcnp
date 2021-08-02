package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_categorie_esmc database table.
 * 
 */
@Entity
@Table(name="eu_categorie_esmc")
@NamedQuery(name="EuCategorieEsmc.findAll", query="SELECT e FROM EuCategorieEsmc e")
public class EuCategorieEsmc implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idCategorie;
	private String codeCategorie;
	private String libCategorie;

	public EuCategorieEsmc() {
	}


	@Id
	@Column(name="id_categorie", unique=true, nullable=false)
	public double getIdCategorie() {
		return this.idCategorie;
	}

	public void setIdCategorie(double idCategorie) {
		this.idCategorie = idCategorie;
	}


	@Column(name="code_categorie", length=40)
	public String getCodeCategorie() {
		return this.codeCategorie;
	}

	public void setCodeCategorie(String codeCategorie) {
		this.codeCategorie = codeCategorie;
	}


	@Column(name="lib_categorie", length=200)
	public String getLibCategorie() {
		return this.libCategorie;
	}

	public void setLibCategorie(String libCategorie) {
		this.libCategorie = libCategorie;
	}

}
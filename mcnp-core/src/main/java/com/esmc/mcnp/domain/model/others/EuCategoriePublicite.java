package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_categorie_publicite database table.
 * 
 */
@Entity
@Table(name="eu_categorie_publicite")
@NamedQuery(name="EuCategoriePublicite.findAll", query="SELECT e FROM EuCategoriePublicite e")
public class EuCategoriePublicite implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idCategoriePublicite;
	private String libelleCategoriePublicite;

	public EuCategoriePublicite() {
	}


	@Id
	@Column(name="id_categorie_publicite", unique=true, nullable=false)
	public double getIdCategoriePublicite() {
		return this.idCategoriePublicite;
	}

	public void setIdCategoriePublicite(double idCategoriePublicite) {
		this.idCategoriePublicite = idCategoriePublicite;
	}


	@Column(name="libelle_categorie_publicite", length=100)
	public String getLibelleCategoriePublicite() {
		return this.libelleCategoriePublicite;
	}

	public void setLibelleCategoriePublicite(String libelleCategoriePublicite) {
		this.libelleCategoriePublicite = libelleCategoriePublicite;
	}

}
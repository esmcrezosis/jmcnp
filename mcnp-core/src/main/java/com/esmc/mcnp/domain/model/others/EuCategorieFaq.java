package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_categorie_faq database table.
 * 
 */
@Entity
@Table(name="eu_categorie_faq")
@NamedQuery(name="EuCategorieFaq.findAll", query="SELECT e FROM EuCategorieFaq e")
public class EuCategorieFaq implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idCategorieFaq;
	private String libelleCategorieFaq;

	public EuCategorieFaq() {
	}


	@Id
	@Column(name="id_categorie_faq", unique=true, nullable=false)
	public double getIdCategorieFaq() {
		return this.idCategorieFaq;
	}

	public void setIdCategorieFaq(double idCategorieFaq) {
		this.idCategorieFaq = idCategorieFaq;
	}


	@Column(name="libelle_categorie_faq", length=100)
	public String getLibelleCategorieFaq() {
		return this.libelleCategorieFaq;
	}

	public void setLibelleCategorieFaq(String libelleCategorieFaq) {
		this.libelleCategorieFaq = libelleCategorieFaq;
	}

}
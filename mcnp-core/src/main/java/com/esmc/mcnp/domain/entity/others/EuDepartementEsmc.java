package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_departement_esmc database table.
 * 
 */
@Entity
@Table(name="eu_departement_esmc")
@NamedQuery(name="EuDepartementEsmc.findAll", query="SELECT e FROM EuDepartementEsmc e")
public class EuDepartementEsmc implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idDepartement;
	private String codeDepartement;
	private String libDepartement;

	public EuDepartementEsmc() {
	}


	@Id
	@Column(name="id_departement", unique=true, nullable=false)
	public double getIdDepartement() {
		return this.idDepartement;
	}

	public void setIdDepartement(double idDepartement) {
		this.idDepartement = idDepartement;
	}


	@Column(name="code_departement", length=40)
	public String getCodeDepartement() {
		return this.codeDepartement;
	}

	public void setCodeDepartement(String codeDepartement) {
		this.codeDepartement = codeDepartement;
	}


	@Column(name="lib_departement", length=200)
	public String getLibDepartement() {
		return this.libDepartement;
	}

	public void setLibDepartement(String libDepartement) {
		this.libDepartement = libDepartement;
	}

}
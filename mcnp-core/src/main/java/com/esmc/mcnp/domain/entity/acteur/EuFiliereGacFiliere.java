package com.esmc.mcnp.domain.entity.acteur;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_filiere_gac_filiere database table.
 * 
 */
@Entity
@Table(name="eu_filiere_gac_filiere")
@NamedQuery(name="EuFiliereGacFiliere.findAll", query="SELECT e FROM EuFiliereGacFiliere e")
public class EuFiliereGacFiliere implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idFiliere;
	private String codeGacFiliere;

	public EuFiliereGacFiliere() {
	}


	@Id
	@Column(name="id_filiere", unique=true, nullable=false)
	public double getIdFiliere() {
		return this.idFiliere;
	}

	public void setIdFiliere(double idFiliere) {
		this.idFiliere = idFiliere;
	}


	@Column(name="code_gac_filiere", nullable=false, length=100)
	public String getCodeGacFiliere() {
		return this.codeGacFiliere;
	}

	public void setCodeGacFiliere(String codeGacFiliere) {
		this.codeGacFiliere = codeGacFiliere;
	}

}
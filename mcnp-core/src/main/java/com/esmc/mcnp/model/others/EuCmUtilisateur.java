package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_cm_utilisateur database table.
 * 
 */
@Entity
@Table(name="eu_cm_utilisateur")
@NamedQuery(name="EuCmUtilisateur.findAll", query="SELECT e FROM EuCmUtilisateur e")
public class EuCmUtilisateur implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idCmUtilisateur;
	private String libelleCmUtilisateur;

	public EuCmUtilisateur() {
	}


	@Id
	@Column(name="id_cm_utilisateur", unique=true, nullable=false)
	public double getIdCmUtilisateur() {
		return this.idCmUtilisateur;
	}

	public void setIdCmUtilisateur(double idCmUtilisateur) {
		this.idCmUtilisateur = idCmUtilisateur;
	}


	@Column(name="libelle_cm_utilisateur", length=200)
	public String getLibelleCmUtilisateur() {
		return this.libelleCmUtilisateur;
	}

	public void setLibelleCmUtilisateur(String libelleCmUtilisateur) {
		this.libelleCmUtilisateur = libelleCmUtilisateur;
	}

}
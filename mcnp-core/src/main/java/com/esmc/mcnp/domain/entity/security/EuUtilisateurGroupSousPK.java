package com.esmc.mcnp.domain.entity.security;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the eu_utilisateur_group_sous database table.
 * 
 */
@Embeddable
public class EuUtilisateurGroupSousPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private double idUtilisateur;
	private String codeGroupeSous;

	public EuUtilisateurGroupSousPK() {
	}

	@Column(name="id_utilisateur", unique=true, nullable=false)
	public double getIdUtilisateur() {
		return this.idUtilisateur;
	}
	public void setIdUtilisateur(double idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	@Column(name="code_groupe_sous", unique=true, nullable=false, length=25)
	public String getCodeGroupeSous() {
		return this.codeGroupeSous;
	}
	public void setCodeGroupeSous(String codeGroupeSous) {
		this.codeGroupeSous = codeGroupeSous;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EuUtilisateurGroupSousPK)) {
			return false;
		}
		EuUtilisateurGroupSousPK castOther = (EuUtilisateurGroupSousPK)other;
		return 
			(this.idUtilisateur == castOther.idUtilisateur)
			&& this.codeGroupeSous.equals(castOther.codeGroupeSous);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (java.lang.Double.doubleToLongBits(this.idUtilisateur) ^ (java.lang.Double.doubleToLongBits(this.idUtilisateur) >>> 32)));
		hash = hash * prime + this.codeGroupeSous.hashCode();
		
		return hash;
	}
}
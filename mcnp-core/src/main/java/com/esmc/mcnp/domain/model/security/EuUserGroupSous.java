package com.esmc.mcnp.model.security;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_user_group_sous database table.
 * 
 */
@Entity
@Table(name="eu_user_group_sous")
@NamedQuery(name="EuUserGroupSous.findAll", query="SELECT e FROM EuUserGroupSous e")
public class EuUserGroupSous implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeGroupeSous;
	private String codeGroupe;
	private String libelleGroupeSous;

	public EuUserGroupSous() {
	}


	@Id
	@Column(name="code_groupe_sous", unique=true, nullable=false, length=100)
	public String getCodeGroupeSous() {
		return this.codeGroupeSous;
	}

	public void setCodeGroupeSous(String codeGroupeSous) {
		this.codeGroupeSous = codeGroupeSous;
	}


	@Column(name="code_groupe", length=25)
	public String getCodeGroupe() {
		return this.codeGroupe;
	}

	public void setCodeGroupe(String codeGroupe) {
		this.codeGroupe = codeGroupe;
	}


	@Column(name="libelle_groupe_sous", nullable=false, length=100)
	public String getLibelleGroupeSous() {
		return this.libelleGroupeSous;
	}

	public void setLibelleGroupeSous(String libelleGroupeSous) {
		this.libelleGroupeSous = libelleGroupeSous;
	}

}
package com.esmc.mcnp.model.others;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Integer;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuPostePointage
 *
 */
@Entity
@Table(name = "eu_poste_pointage")

public class EuPostePointage implements Serializable {

	private Integer idPostePointage;
	private String codeMembreEmployeur;
	private Double salaireBase;
	private String libellePostePointage;
	private static final long serialVersionUID = 1L;

	public EuPostePointage() {
		super();
	}

	@Id
	@Column(name = "id_poste_pointage")
	public Integer getIdPostePointage() {
		return this.idPostePointage;
	}

	public void setIdPostePointage(Integer idPostePointage) {
		this.idPostePointage = idPostePointage;
	}

	@Column(name = "code_membre_employeur")
	public String getCodeMembreEmployeur() {
		return this.codeMembreEmployeur;
	}

	public void setCodeMembreEmployeur(String codeMembreEmployeur) {
		this.codeMembreEmployeur = codeMembreEmployeur;
	}

	@Column(name = "salaire_base")
	public Double getSalaireBase() {
		return this.salaireBase;
	}

	public void setSalaireBase(Double salaireBase) {
		this.salaireBase = salaireBase;
	}

	@Column(name = "libelle_poste_pointage")
	public String getLibellePostePointage() {
		return this.libellePostePointage;
	}

	public void setLibellePostePointage(String libellePostePointage) {
		this.libellePostePointage = libellePostePointage;
	}

}

package com.esmc.mcnp.model.obps;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_mdv database table.
 * 
 */
@Entity
@Table(name="eu_mdv")
@NamedQuery(name="EuMdv.findAll", query="SELECT e FROM EuMdv e")
public class EuMdv implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idMdv;
	private String codeGacFiliere;
	private String codeMembre;
	private double dureeVie;

	public EuMdv() {
	}


	@Id
	@Column(name="id_mdv", unique=true, nullable=false)
	public double getIdMdv() {
		return this.idMdv;
	}

	public void setIdMdv(double idMdv) {
		this.idMdv = idMdv;
	}


	@Column(name="code_gac_filiere", nullable=false, length=100)
	public String getCodeGacFiliere() {
		return this.codeGacFiliere;
	}

	public void setCodeGacFiliere(String codeGacFiliere) {
		this.codeGacFiliere = codeGacFiliere;
	}


	@Column(name="code_membre", length=100)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Column(name="duree_vie", nullable=false)
	public double getDureeVie() {
		return this.dureeVie;
	}

	public void setDureeVie(double dureeVie) {
		this.dureeVie = dureeVie;
	}

}
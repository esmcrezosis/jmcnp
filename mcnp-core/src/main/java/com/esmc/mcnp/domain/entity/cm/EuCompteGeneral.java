package com.esmc.mcnp.domain.entity.cm;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_compte_general database table.
 * 
 */
@Entity
@Table(name="eu_compte_general")
@NamedQuery(name="EuCompteGeneral.findAll", query="SELECT e FROM EuCompteGeneral e")
public class EuCompteGeneral implements Serializable {
	private static final long serialVersionUID = 1L;
	private EuCompteGeneralPK id;
	private String intitule;
	private double solde;
	private EuTypeCompte euTypeCompte;

	public EuCompteGeneral() {
	}


	@EmbeddedId
	public EuCompteGeneralPK getId() {
		return this.id;
	}

	public void setId(EuCompteGeneralPK id) {
		this.id = id;
	}


	@Column(nullable=false, length=100)
	public String getIntitule() {
		return this.intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}


	@Column(nullable=false)
	public double getSolde() {
		return this.solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}


	//bi-directional many-to-one association to EuTypeCompte
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_type_compte", nullable=false, insertable=false, updatable=false)
	public EuTypeCompte getEuTypeCompte() {
		return this.euTypeCompte;
	}

	public void setEuTypeCompte(EuTypeCompte euTypeCompte) {
		this.euTypeCompte = euTypeCompte;
	}

}
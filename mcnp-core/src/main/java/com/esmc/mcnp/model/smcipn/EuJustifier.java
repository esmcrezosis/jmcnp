package com.esmc.mcnp.model.smcipn;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.model.cm.EuMembre;


/**
 * The persistent class for the eu_justifier database table.
 * 
 */
@Entity
@Table(name="eu_justifier")
@NamedQuery(name="EuJustifier.findAll", query="SELECT e FROM EuJustifier e")
public class EuJustifier implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeMembre;
	private double affecter;
	private double salaire;
	private double solde;
	private EuMembre euMembre;
	private EuSmcipn euSmcipn;

	public EuJustifier() {
	}


	@Id
	@Column(name="code_membre", unique=true, nullable=false, length=100)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	public double getAffecter() {
		return this.affecter;
	}

	public void setAffecter(double affecter) {
		this.affecter = affecter;
	}


	public double getSalaire() {
		return this.salaire;
	}

	public void setSalaire(double salaire) {
		this.salaire = salaire;
	}


	public double getSolde() {
		return this.solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}


	//bi-directional one-to-one association to EuMembre
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre", nullable=false, insertable=false, updatable=false)
	public EuMembre getEuMembre() {
		return this.euMembre;
	}

	public void setEuMembre(EuMembre euMembre) {
		this.euMembre = euMembre;
	}


	//bi-directional many-to-one association to EuSmcipn
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_smcipn", nullable=false)
	public EuSmcipn getEuSmcipn() {
		return this.euSmcipn;
	}

	public void setEuSmcipn(EuSmcipn euSmcipn) {
		this.euSmcipn = euSmcipn;
	}

}
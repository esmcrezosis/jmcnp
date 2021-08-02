package com.esmc.mcnp.model.obpsd;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_escpte_echu_recap database table.
 * 
 */
@Entity
@Table(name="eu_escpte_echu_recap")
@NamedQuery(name="EuEscpteEchuRecap.findAll", query="SELECT e FROM EuEscpteEchuRecap e")
public class EuEscpteEchuRecap implements Serializable {
	private static final long serialVersionUID = 1L;
	private double id;
	private String codeMembreBenef;
	private String raisonSociale;
	private double totMontEchu;
	private double totMontEchuTransferer;
	private double totMontant;

	public EuEscpteEchuRecap() {
	}


	@Id
	@Column(unique=true, nullable=false)
	public double getId() {
		return this.id;
	}

	public void setId(double id) {
		this.id = id;
	}


	@Column(name="code_membre_benef", length=200)
	public String getCodeMembreBenef() {
		return this.codeMembreBenef;
	}

	public void setCodeMembreBenef(String codeMembreBenef) {
		this.codeMembreBenef = codeMembreBenef;
	}


	@Column(name="raison_sociale", length=200)
	public String getRaisonSociale() {
		return this.raisonSociale;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}


	@Column(name="tot_mont_echu")
	public double getTotMontEchu() {
		return this.totMontEchu;
	}

	public void setTotMontEchu(double totMontEchu) {
		this.totMontEchu = totMontEchu;
	}


	@Column(name="tot_mont_echu_transferer")
	public double getTotMontEchuTransferer() {
		return this.totMontEchuTransferer;
	}

	public void setTotMontEchuTransferer(double totMontEchuTransferer) {
		this.totMontEchuTransferer = totMontEchuTransferer;
	}


	@Column(name="tot_montant")
	public double getTotMontant() {
		return this.totMontant;
	}

	public void setTotMontant(double totMontant) {
		this.totMontant = totMontant;
	}

}
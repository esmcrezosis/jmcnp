package com.esmc.mcnp.model.obpsd;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_escpte_echu_detail database table.
 * 
 */
@Entity
@Table(name="eu_escpte_echu_detail")
@NamedQuery(name="EuEscpteEchuDetail.findAll", query="SELECT e FROM EuEscpteEchuDetail e")
public class EuEscpteEchuDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private double id;
	private String codeMembre;
	private String codeMembreBenef;
	private String dateDeb;
	private String dateEscompte;
	private String dateFin;
	private double idEscompte;
	private double montEchu;
	private double montEchuTransferer;
	private double montTranche;
	private double montant;
	private double ntf;
	private String raisonSociale;

	public EuEscpteEchuDetail() {
	}


	@Id
	@Column(unique=true, nullable=false)
	public double getId() {
		return this.id;
	}

	public void setId(double id) {
		this.id = id;
	}


	@Column(name="code_membre", length=200)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Column(name="code_membre_benef", nullable=false, length=200)
	public String getCodeMembreBenef() {
		return this.codeMembreBenef;
	}

	public void setCodeMembreBenef(String codeMembreBenef) {
		this.codeMembreBenef = codeMembreBenef;
	}


	@Column(name="date_deb", length=80)
	public String getDateDeb() {
		return this.dateDeb;
	}

	public void setDateDeb(String dateDeb) {
		this.dateDeb = dateDeb;
	}


	@Column(name="date_escompte", length=80)
	public String getDateEscompte() {
		return this.dateEscompte;
	}

	public void setDateEscompte(String dateEscompte) {
		this.dateEscompte = dateEscompte;
	}


	@Column(name="date_fin", length=80)
	public String getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}


	@Column(name="id_escompte", nullable=false)
	public double getIdEscompte() {
		return this.idEscompte;
	}

	public void setIdEscompte(double idEscompte) {
		this.idEscompte = idEscompte;
	}


	@Column(name="mont_echu")
	public double getMontEchu() {
		return this.montEchu;
	}

	public void setMontEchu(double montEchu) {
		this.montEchu = montEchu;
	}


	@Column(name="mont_echu_transferer")
	public double getMontEchuTransferer() {
		return this.montEchuTransferer;
	}

	public void setMontEchuTransferer(double montEchuTransferer) {
		this.montEchuTransferer = montEchuTransferer;
	}


	@Column(name="mont_tranche")
	public double getMontTranche() {
		return this.montTranche;
	}

	public void setMontTranche(double montTranche) {
		this.montTranche = montTranche;
	}


	public double getMontant() {
		return this.montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}


	public double getNtf() {
		return this.ntf;
	}

	public void setNtf(double ntf) {
		this.ntf = ntf;
	}


	@Column(name="raison_sociale", length=200)
	public String getRaisonSociale() {
		return this.raisonSociale;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}

}
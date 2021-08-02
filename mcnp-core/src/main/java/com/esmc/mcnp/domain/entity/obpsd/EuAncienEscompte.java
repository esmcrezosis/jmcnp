package com.esmc.mcnp.model.obpsd;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the eu_ancien_escompte database table.
 * 
 */
@Entity
@Table(name="eu_ancien_escompte")
@NamedQuery(name="EuAncienEscompte.findAll", query="SELECT e FROM EuAncienEscompte e")
public class EuAncienEscompte implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idEscompte;
	private String codeCompte;
	private String codeMembre;
	private String codeMembreBenef;
	private Date dateDeb;
	private Date dateDebTranche;
	private Date dateEscompte;
	private Date dateFin;
	private Date dateFinTranche;
	private BigInteger idEchange;
	private double montEchu;
	private double montEchuTransferer;
	private double montTranche;
	private double montant;
	private int ntf;
	private int periode;
	private int resteNtf;
	private double solde;

	public EuAncienEscompte() {
	}


	@Id
	@Column(name="id_escompte")
	public Long getIdEscompte() {
		return this.idEscompte;
	}

	public void setIdEscompte(Long idEscompte) {
		this.idEscompte = idEscompte;
	}


	@Column(name="code_compte")
	public String getCodeCompte() {
		return this.codeCompte;
	}

	public void setCodeCompte(String codeCompte) {
		this.codeCompte = codeCompte;
	}


	@Column(name="code_membre")
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Column(name="code_membre_benef")
	public String getCodeMembreBenef() {
		return this.codeMembreBenef;
	}

	public void setCodeMembreBenef(String codeMembreBenef) {
		this.codeMembreBenef = codeMembreBenef;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_deb")
	public Date getDateDeb() {
		return this.dateDeb;
	}

	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_deb_tranche")
	public Date getDateDebTranche() {
		return this.dateDebTranche;
	}

	public void setDateDebTranche(Date dateDebTranche) {
		this.dateDebTranche = dateDebTranche;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_escompte")
	public Date getDateEscompte() {
		return this.dateEscompte;
	}

	public void setDateEscompte(Date dateEscompte) {
		this.dateEscompte = dateEscompte;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_fin")
	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_fin_tranche")
	public Date getDateFinTranche() {
		return this.dateFinTranche;
	}

	public void setDateFinTranche(Date dateFinTranche) {
		this.dateFinTranche = dateFinTranche;
	}


	@Column(name="id_echange")
	public BigInteger getIdEchange() {
		return this.idEchange;
	}

	public void setIdEchange(BigInteger idEchange) {
		this.idEchange = idEchange;
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


	public int getNtf() {
		return this.ntf;
	}

	public void setNtf(int ntf) {
		this.ntf = ntf;
	}


	public int getPeriode() {
		return this.periode;
	}

	public void setPeriode(int periode) {
		this.periode = periode;
	}


	@Column(name="reste_ntf")
	public int getResteNtf() {
		return this.resteNtf;
	}

	public void setResteNtf(int resteNtf) {
		this.resteNtf = resteNtf;
	}


	public double getSolde() {
		return this.solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}

}
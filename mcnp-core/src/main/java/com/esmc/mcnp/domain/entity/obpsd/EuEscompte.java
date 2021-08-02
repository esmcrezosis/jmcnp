package com.esmc.mcnp.domain.entity.obpsd;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.esmc.mcnp.domain.entity.cm.EuCompte;


/**
 * The persistent class for the eu_escompte database table.
 *
 */
@Entity
@Table(name="eu_escompte")
@NamedQuery(name="EuEscompte.findAll", query="SELECT e FROM EuEscompte e")
public class EuEscompte implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idEscompte;
	private String codeMembre;
	private String codeMembreBenef;
	private LocalDate dateDeb;
	private LocalDate dateDebTranche;
	private LocalDate dateEscompte;
	private LocalDate dateFin;
	private LocalDate dateFinTranche;
	private Long idEchange;
	private double montEchu;
	private double montEchuTransferer;
	private double montTranche;
	private double montant;
	private Integer ntf;
	private Integer periode;
	private Integer resteNtf;
	private double solde;
	private double txAgioApplique;
	private EuCompte euCompte;

	public EuEscompte() {
	}


	@Id
	@Column(name="id_escompte", unique=true, nullable=false)
	public Long getIdEscompte() {
		return this.idEscompte;
	}

	public void setIdEscompte(Long idEscompte) {
		this.idEscompte = idEscompte;
	}


	@Column(name="code_membre", length=100)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Column(name="code_membre_benef", length=100)
	public String getCodeMembreBenef() {
		return this.codeMembreBenef;
	}

	public void setCodeMembreBenef(String codeMembreBenef) {
		this.codeMembreBenef = codeMembreBenef;
	}


	@Column(name="date_deb")
	public LocalDate getDateDeb() {
		return this.dateDeb;
	}

	public void setDateDeb(LocalDate dateDeb) {
		this.dateDeb = dateDeb;
	}


	@Column(name="date_deb_tranche")
	public LocalDate getDateDebTranche() {
		return this.dateDebTranche;
	}

	public void setDateDebTranche(LocalDate dateDebTranche) {
		this.dateDebTranche = dateDebTranche;
	}


	@Column(name="date_escompte")
	public LocalDate getDateEscompte() {
		return this.dateEscompte;
	}

	public void setDateEscompte(LocalDate dateEscompte) {
		this.dateEscompte = dateEscompte;
	}


	@Column(name="date_fin")
	public LocalDate getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}


	@Column(name="date_fin_tranche")
	public LocalDate getDateFinTranche() {
		return this.dateFinTranche;
	}

	public void setDateFinTranche(LocalDate dateFinTranche) {
		this.dateFinTranche = dateFinTranche;
	}


	@Column(name="id_echange")
	public Long getIdEchange() {
		return this.idEchange;
	}

	public void setIdEchange(Long idEchange) {
		this.idEchange = idEchange;
	}


	@Column(name="mont_echu", nullable=false)
	public double getMontEchu() {
		return this.montEchu;
	}

	public void setMontEchu(double montEchu) {
		this.montEchu = montEchu;
	}


	@Column(name="mont_echu_transferer", nullable=false)
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


	public Integer getNtf() {
		return this.ntf;
	}

	public void setNtf(Integer ntf) {
		this.ntf = ntf;
	}


	public Integer getPeriode() {
		return this.periode;
	}

	public void setPeriode(Integer periode) {
		this.periode = periode;
	}


	@Column(name="reste_ntf")
	public Integer getResteNtf() {
		return this.resteNtf;
	}

	public void setResteNtf(Integer resteNtf) {
		this.resteNtf = resteNtf;
	}


	public double getSolde() {
		return this.solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}


	@Column(name="tx_agio_applique", nullable=false)
	public double getTxAgioApplique() {
		return this.txAgioApplique;
	}

	public void setTxAgioApplique(double txAgioApplique) {
		this.txAgioApplique = txAgioApplique;
	}


	//bi-directional many-to-one association to EuCompte
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_compte")
	public EuCompte getEuCompte() {
		return this.euCompte;
	}

	public void setEuCompte(EuCompte euCompte) {
		this.euCompte = euCompte;
	}

}
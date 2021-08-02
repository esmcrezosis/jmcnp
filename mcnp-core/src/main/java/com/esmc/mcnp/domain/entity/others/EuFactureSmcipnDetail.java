package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_facture_smcipn_detail database table.
 *
 */
@Entity
@Table(name="eu_facture_smcipn_detail")
@NamedQuery(name="EuFactureSmcipnDetail.findAll", query="SELECT e FROM EuFactureSmcipnDetail e")
public class EuFactureSmcipnDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeFacture;
	private String codeMembreFournisseur;
	private String codeMembreSalarier;
	private double idFactureDetail;
	private double investisAlloue;
	private double montInvestis;
	private double montSalaire;
	private double salaireAlloue;
	private double soldeInvestis;
	private double soldeSalaire;

	public EuFactureSmcipnDetail() {
	}


	@Column(name="code_facture", length=25)
	public String getCodeFacture() {
		return this.codeFacture;
	}

	public void setCodeFacture(String codeFacture) {
		this.codeFacture = codeFacture;
	}


	@Column(name="code_membre_fournisseur", length=25)
	public String getCodeMembreFournisseur() {
		return this.codeMembreFournisseur;
	}

	public void setCodeMembreFournisseur(String codeMembreFournisseur) {
		this.codeMembreFournisseur = codeMembreFournisseur;
	}


	@Column(name="code_membre_salarier", length=25)
	public String getCodeMembreSalarier() {
		return this.codeMembreSalarier;
	}

	public void setCodeMembreSalarier(String codeMembreSalarier) {
		this.codeMembreSalarier = codeMembreSalarier;
	}


	@Id
	@Column(name="id_facture_detail", nullable=false)
	public double getIdFactureDetail() {
		return this.idFactureDetail;
	}

	public void setIdFactureDetail(double idFactureDetail) {
		this.idFactureDetail = idFactureDetail;
	}


	@Column(name="investis_alloue")
	public double getInvestisAlloue() {
		return this.investisAlloue;
	}

	public void setInvestisAlloue(double investisAlloue) {
		this.investisAlloue = investisAlloue;
	}


	@Column(name="mont_investis")
	public double getMontInvestis() {
		return this.montInvestis;
	}

	public void setMontInvestis(double montInvestis) {
		this.montInvestis = montInvestis;
	}


	@Column(name="mont_salaire")
	public double getMontSalaire() {
		return this.montSalaire;
	}

	public void setMontSalaire(double montSalaire) {
		this.montSalaire = montSalaire;
	}


	@Column(name="salaire_alloue")
	public double getSalaireAlloue() {
		return this.salaireAlloue;
	}

	public void setSalaireAlloue(double salaireAlloue) {
		this.salaireAlloue = salaireAlloue;
	}


	@Column(name="solde_investis")
	public double getSoldeInvestis() {
		return this.soldeInvestis;
	}

	public void setSoldeInvestis(double soldeInvestis) {
		this.soldeInvestis = soldeInvestis;
	}


	@Column(name="solde_salaire")
	public double getSoldeSalaire() {
		return this.soldeSalaire;
	}

	public void setSoldeSalaire(double soldeSalaire) {
		this.soldeSalaire = soldeSalaire;
	}

}
package com.esmc.mcnp.model.smcipn;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.model.others.EuBesoin;
import com.esmc.mcnp.model.others.EuObjet;
import com.esmc.mcnp.model.others.EuProforma;


/**
 * The persistent class for the eu_budget_facture database table.
 * 
 */
@Entity
@Table(name="eu_budget_facture")
@NamedQuery(name="EuBudgetFacture.findAll", query="SELECT e FROM EuBudgetFacture e")
public class EuBudgetFacture implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeProforma;
	private String categorieObjet;
	private double puObjet;
	private double qteObjet;
	private double remiseObjet;
	private EuProforma euProforma;
	private EuInvestissement euInvestissement;
	private EuBesoin euBesoin;
	private EuObjet euObjet;

	public EuBudgetFacture() {
	}


	@Id
	@Column(name="code_proforma", unique=true, nullable=false, length=100)
	public String getCodeProforma() {
		return this.codeProforma;
	}

	public void setCodeProforma(String codeProforma) {
		this.codeProforma = codeProforma;
	}


	@Column(name="categorie_objet", length=40)
	public String getCategorieObjet() {
		return this.categorieObjet;
	}

	public void setCategorieObjet(String categorieObjet) {
		this.categorieObjet = categorieObjet;
	}


	@Column(name="pu_objet")
	public double getPuObjet() {
		return this.puObjet;
	}

	public void setPuObjet(double puObjet) {
		this.puObjet = puObjet;
	}


	@Column(name="qte_objet")
	public double getQteObjet() {
		return this.qteObjet;
	}

	public void setQteObjet(double qteObjet) {
		this.qteObjet = qteObjet;
	}


	@Column(name="remise_objet")
	public double getRemiseObjet() {
		return this.remiseObjet;
	}

	public void setRemiseObjet(double remiseObjet) {
		this.remiseObjet = remiseObjet;
	}


	//bi-directional one-to-one association to EuProforma
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_proforma", nullable=false, insertable=false, updatable=false)
	public EuProforma getEuProforma() {
		return this.euProforma;
	}

	public void setEuProforma(EuProforma euProforma) {
		this.euProforma = euProforma;
	}


	//bi-directional many-to-one association to EuInvestissement
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_investissement")
	public EuInvestissement getEuInvestissement() {
		return this.euInvestissement;
	}

	public void setEuInvestissement(EuInvestissement euInvestissement) {
		this.euInvestissement = euInvestissement;
	}


	//bi-directional many-to-one association to EuBesoin
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_besoin")
	public EuBesoin getEuBesoin() {
		return this.euBesoin;
	}

	public void setEuBesoin(EuBesoin euBesoin) {
		this.euBesoin = euBesoin;
	}


	//bi-directional many-to-one association to EuObjet
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_objet", nullable=false)
	public EuObjet getEuObjet() {
		return this.euObjet;
	}

	public void setEuObjet(EuObjet euObjet) {
		this.euObjet = euObjet;
	}

}
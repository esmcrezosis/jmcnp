package com.esmc.mcnp.domain.entity.smcipn;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.others.EuBesoin;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_investissement database table.
 * 
 */
@Entity
@Table(name="eu_investissement")
@NamedQuery(name="EuInvestissement.findAll", query="SELECT e FROM EuInvestissement e")
public class EuInvestissement implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idInvestissement;
	private String catObjet;
	private Date dateInvestissement;
	private double montantBudget;
	private List<EuAutreBudget> euAutreBudgets;
	private List<EuBudgetFacture> euBudgetFactures;
	private EuSmcipn euSmcipn;
	private EuUtilisateur euUtilisateur;
	private EuBesoin euBesoin;

	public EuInvestissement() {
	}


	@Id
	@Column(name="id_investissement", unique=true, nullable=false)
	public double getIdInvestissement() {
		return this.idInvestissement;
	}

	public void setIdInvestissement(double idInvestissement) {
		this.idInvestissement = idInvestissement;
	}


	@Column(name="cat_objet", length=80)
	public String getCatObjet() {
		return this.catObjet;
	}

	public void setCatObjet(String catObjet) {
		this.catObjet = catObjet;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_investissement")
	public Date getDateInvestissement() {
		return this.dateInvestissement;
	}

	public void setDateInvestissement(Date dateInvestissement) {
		this.dateInvestissement = dateInvestissement;
	}


	@Column(name="montant_budget")
	public double getMontantBudget() {
		return this.montantBudget;
	}

	public void setMontantBudget(double montantBudget) {
		this.montantBudget = montantBudget;
	}


	//bi-directional many-to-one association to EuAutreBudget
	@OneToMany(mappedBy="euInvestissement")
	public List<EuAutreBudget> getEuAutreBudgets() {
		return this.euAutreBudgets;
	}

	public void setEuAutreBudgets(List<EuAutreBudget> euAutreBudgets) {
		this.euAutreBudgets = euAutreBudgets;
	}

	public EuAutreBudget addEuAutreBudget(EuAutreBudget euAutreBudget) {
		getEuAutreBudgets().add(euAutreBudget);
		euAutreBudget.setEuInvestissement(this);

		return euAutreBudget;
	}

	public EuAutreBudget removeEuAutreBudget(EuAutreBudget euAutreBudget) {
		getEuAutreBudgets().remove(euAutreBudget);
		euAutreBudget.setEuInvestissement(null);

		return euAutreBudget;
	}


	//bi-directional many-to-one association to EuBudgetFacture
	@OneToMany(mappedBy="euInvestissement")
	public List<EuBudgetFacture> getEuBudgetFactures() {
		return this.euBudgetFactures;
	}

	public void setEuBudgetFactures(List<EuBudgetFacture> euBudgetFactures) {
		this.euBudgetFactures = euBudgetFactures;
	}

	public EuBudgetFacture addEuBudgetFacture(EuBudgetFacture euBudgetFacture) {
		getEuBudgetFactures().add(euBudgetFacture);
		euBudgetFacture.setEuInvestissement(this);

		return euBudgetFacture;
	}

	public EuBudgetFacture removeEuBudgetFacture(EuBudgetFacture euBudgetFacture) {
		getEuBudgetFactures().remove(euBudgetFacture);
		euBudgetFacture.setEuInvestissement(null);

		return euBudgetFacture;
	}


	//bi-directional many-to-one association to EuSmcipn
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_smcipn")
	public EuSmcipn getEuSmcipn() {
		return this.euSmcipn;
	}

	public void setEuSmcipn(EuSmcipn euSmcipn) {
		this.euSmcipn = euSmcipn;
	}


	//bi-directional many-to-one association to EuUtilisateur
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_utilisateur")
	public EuUtilisateur getEuUtilisateur() {
		return this.euUtilisateur;
	}

	public void setEuUtilisateur(EuUtilisateur euUtilisateur) {
		this.euUtilisateur = euUtilisateur;
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

}
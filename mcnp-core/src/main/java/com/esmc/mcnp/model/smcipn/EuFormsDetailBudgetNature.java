package com.esmc.mcnp.model.smcipn;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_forms_detail_budget_nature database table.
 * 
 */
@Entity
@Table(name="eu_forms_detail_budget_nature")
@NamedQuery(name="EuFormsDetailBudgetNature.findAll", query="SELECT e FROM EuFormsDetailBudgetNature e")
public class EuFormsDetailBudgetNature implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idFormsDetailBudgetNature;
	private String bpsDemande;
	private Integer disponibleBudgetNature;
	private double prixUnitaireBudgetNature;
	private int qteBudgetNature;
	private double totalBudgetNature;
	private EuFormsBudgetNature euFormsBudgetNature;

	public EuFormsDetailBudgetNature() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_forms_detail_budget_nature")
	public Long getIdFormsDetailBudgetNature() {
		return this.idFormsDetailBudgetNature;
	}

	public void setIdFormsDetailBudgetNature(Long idFormsDetailBudgetNature) {
		this.idFormsDetailBudgetNature = idFormsDetailBudgetNature;
	}


	@Column(name="bps_demande")
	public String getBpsDemande() {
		return this.bpsDemande;
	}

	public void setBpsDemande(String bpsDemande) {
		this.bpsDemande = bpsDemande;
	}


	@Column(name="disponible_budget_nature")
	public Integer getDisponibleBudgetNature() {
		return this.disponibleBudgetNature;
	}

	public void setDisponibleBudgetNature(Integer disponibleBudgetNature) {
		this.disponibleBudgetNature = disponibleBudgetNature;
	}


	@Column(name="prix_unitaire_budget_nature")
	public double getPrixUnitaireBudgetNature() {
		return this.prixUnitaireBudgetNature;
	}

	public void setPrixUnitaireBudgetNature(double prixUnitaireBudgetNature) {
		this.prixUnitaireBudgetNature = prixUnitaireBudgetNature;
	}


	@Column(name="qte_budget_nature")
	public int getQteBudgetNature() {
		return this.qteBudgetNature;
	}

	public void setQteBudgetNature(int qteBudgetNature) {
		this.qteBudgetNature = qteBudgetNature;
	}


	@Column(name="total_budget_nature")
	public double getTotalBudgetNature() {
		return this.totalBudgetNature;
	}

	public void setTotalBudgetNature(double totalBudgetNature) {
		this.totalBudgetNature = totalBudgetNature;
	}


	//bi-directional many-to-one association to EuFormsBudgetNature
	@ManyToOne
	@JoinColumn(name="id_forms_budget_nature")
	public EuFormsBudgetNature getEuFormsBudgetNature() {
		return this.euFormsBudgetNature;
	}

	public void setEuFormsBudgetNature(EuFormsBudgetNature euFormsBudgetNature) {
		this.euFormsBudgetNature = euFormsBudgetNature;
	}

}
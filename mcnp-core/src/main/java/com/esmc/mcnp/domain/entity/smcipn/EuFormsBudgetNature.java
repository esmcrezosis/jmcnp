package com.esmc.mcnp.domain.entity.smcipn;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.acteur.EuBpsVenduAvr;

import java.util.List;

/**
 * The persistent class for the eu_forms_budget_nature database table.
 * 
 */
@Entity
@Table(name = "eu_forms_budget_nature")
@NamedQuery(name = "EuFormsBudgetNature.findAll", query = "SELECT e FROM EuFormsBudgetNature e")
public class EuFormsBudgetNature implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idFormsBudgetNature;
	private String codeMembreBudget;
	private Long referenceTypeBudget;
	private String typeBudget;
	private boolean validBudget;
	private boolean affecterBudget;
	private Double montantBudget;
	private EuBpsVenduAvr euBpsVenduAvr;
	private List<EuFormsDetailBudgetNature> euFormsDetailBudgetNatures;

	public EuFormsBudgetNature() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_forms_budget_nature")
	public int getIdFormsBudgetNature() {
		return this.idFormsBudgetNature;
	}

	public void setIdFormsBudgetNature(int idFormsBudgetNature) {
		this.idFormsBudgetNature = idFormsBudgetNature;
	}

	@Column(name = "code_membre_budget")
	public String getCodeMembreBudget() {
		return this.codeMembreBudget;
	}

	public void setCodeMembreBudget(String codeMembreBudget) {
		this.codeMembreBudget = codeMembreBudget;
	}

	@Column(name = "reference_type_budget")
	public Long getReferenceTypeBudget() {
		return this.referenceTypeBudget;
	}

	public void setReferenceTypeBudget(Long referenceTypeBudget) {
		this.referenceTypeBudget = referenceTypeBudget;
	}

	@Column(name = "type_budget")
	public String getTypeBudget() {
		return this.typeBudget;
	}

	public void setTypeBudget(String typeBudget) {
		this.typeBudget = typeBudget;
	}

	@Column(name = "valid_budget")
	public boolean isValidBudget() {
		return validBudget;
	}

	public void setValidBudget(boolean validBudget) {
		this.validBudget = validBudget;
	}

	@Column(name = "affecter_budget")
	public boolean isAffecterBudget() {
		return affecterBudget;
	}

	public void setAffecterBudget(boolean affecterBudget) {
		this.affecterBudget = affecterBudget;
	}

	@Column(name = "montant_budget")
	public Double getMontantBudget() {
		return montantBudget;
	}

	public void setMontantBudget(Double montantBudget) {
		this.montantBudget = montantBudget;
	}

	@ManyToOne
	@JoinColumn(name = "id_bps_vendu_achat_vente_reciproque")
	public EuBpsVenduAvr getEuBpsVenduAvr() {
		return euBpsVenduAvr;
	}

	public void setEuBpsVenduAvr(EuBpsVenduAvr euBpsVenduAvr) {
		this.euBpsVenduAvr = euBpsVenduAvr;
	}

	// bi-directional many-to-one association to EuFormsDetailBudgetNature
	@OneToMany(mappedBy = "euFormsBudgetNature")
	public List<EuFormsDetailBudgetNature> getEuFormsDetailBudgetNatures() {
		return this.euFormsDetailBudgetNatures;
	}

	public void setEuFormsDetailBudgetNatures(List<EuFormsDetailBudgetNature> euFormsDetailBudgetNatures) {
		this.euFormsDetailBudgetNatures = euFormsDetailBudgetNatures;
	}

	public EuFormsDetailBudgetNature addEuFormsDetailBudgetNature(EuFormsDetailBudgetNature euFormsDetailBudgetNature) {
		getEuFormsDetailBudgetNatures().add(euFormsDetailBudgetNature);
		euFormsDetailBudgetNature.setEuFormsBudgetNature(this);

		return euFormsDetailBudgetNature;
	}

	public EuFormsDetailBudgetNature removeEuFormsDetailBudgetNature(
			EuFormsDetailBudgetNature euFormsDetailBudgetNature) {
		getEuFormsDetailBudgetNatures().remove(euFormsDetailBudgetNature);
		euFormsDetailBudgetNature.setEuFormsBudgetNature(null);

		return euFormsDetailBudgetNature;
	}

}
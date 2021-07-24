package com.esmc.mcnp.web.dto.smcipn;

public class BudgetDTO {

	private String codeMembre;
	private String codeTegc;
	private String typeRessource;
	private String numeroBudget;
	private String typeBudget;
	private Integer typeBudgetId;
	private double montant;

	public BudgetDTO() {
	}

	public BudgetDTO(String codeMembre, String codeTegc, String typeRessource, String numeroBudget, String typeBudget,
			Integer typeBudgetId, double montant) {
		this.codeMembre = codeMembre;
		this.setCodeTegc(codeTegc);
		this.typeRessource = typeRessource;
		this.numeroBudget = numeroBudget;
		this.typeBudget = typeBudget;
		this.typeBudgetId = typeBudgetId;
		this.montant = montant;
	}

	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	public String getCodeTegc() {
		return codeTegc;
	}

	public void setCodeTegc(String codeTegc) {
		this.codeTegc = codeTegc;
	}

	public String getTypeBudget() {
		return typeBudget;
	}

	public void setTypeBudget(String typeBudget) {
		this.typeBudget = typeBudget;
	}

	public Integer getTypeBudgetId() {
		return typeBudgetId;
	}

	public void setTypeBudgetId(Integer typeBudgetId) {
		this.typeBudgetId = typeBudgetId;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getNumeroBudget() {
		return numeroBudget;
	}

	public void setNumeroBudget(String numeroBudget) {
		this.numeroBudget = numeroBudget;
	}

	public String getTypeRessource() {
		return typeRessource;
	}

	public void setTypeRessource(String typeRessource) {
		this.typeRessource = typeRessource;
	}

}

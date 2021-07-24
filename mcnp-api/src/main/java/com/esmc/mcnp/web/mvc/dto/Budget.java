package com.esmc.mcnp.web.mvc.dto;

public class Budget {
	
	private Long idBudget;
	private String typeBudget;
	private Long idTypebudget;

	public Budget() {
	}

	public Budget(Long idBudget, String typeBudget, Long idTypebudget) {
		this.idBudget = idBudget;
		this.typeBudget = typeBudget;
		this.idTypebudget = idTypebudget;
	}

	public Long getIdBudget() {
		return idBudget;
	}

	public void setIdBudget(Long idBudget) {
		this.idBudget = idBudget;
	}

	public String getTypeBudget() {
		return typeBudget;
	}

	public void setTypeBudget(String typeBudget) {
		this.typeBudget = typeBudget;
	}

	public Long getIdTypebudget() {
		return idTypebudget;
	}

	public void setIdTypebudget(Long idTypebudget) {
		this.idTypebudget = idTypebudget;
	}

}

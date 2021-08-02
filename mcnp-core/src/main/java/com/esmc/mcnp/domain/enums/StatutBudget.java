package com.esmc.mcnp.domain.enums;

public enum StatutBudget {
    ENCOURS("En Cours"),
    VALIDATION("En Validation"),
    VALIDE("Valide"),
    AFFECTE("Affecte"),
    DEFICITAIRE("Deficitaire"),
    EXCEDENTAIRE("Execedentaire"),
    CLOTURE("Cloture");

    String statutBudget;

    StatutBudget(String statutBudget) {
        this.statutBudget = statutBudget;
    }

    public String getStatutBudget(){
    	return this.statutBudget;
	}
}

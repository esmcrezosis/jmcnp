package com.esmc.mcnp.model.enums;

public enum NatureBudget {
	RECETTE("Recette"), DEPENSE("Depense");

	private final String nom;

	NatureBudget(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}
}

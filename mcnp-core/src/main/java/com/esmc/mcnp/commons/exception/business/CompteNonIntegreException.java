package com.esmc.mcnp.commons.exception.business;

public class CompteNonIntegreException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6219593894286791981L;

	public CompteNonIntegreException() {
		super("Ce compte est incohérent : La somme des détails de votre compte ne correspond pas au solde du compte");
	}

	public CompteNonIntegreException(String message) {
		super(message);
	}

}

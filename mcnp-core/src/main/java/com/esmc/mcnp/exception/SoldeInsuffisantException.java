package com.esmc.mcnp.exception;

public class SoldeInsuffisantException extends IllegalArgumentException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6171707239551643785L;

	public SoldeInsuffisantException() {
		super("Le solde de votre compte est insuffisant pour effectuer cette transaction");
	}

	public SoldeInsuffisantException(String message) {
		super(message);
	}

}

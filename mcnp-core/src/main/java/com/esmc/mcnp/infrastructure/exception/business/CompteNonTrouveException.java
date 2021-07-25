package com.esmc.mcnp.exception;

public class CompteNonTrouveException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7880639999320827890L;

	public CompteNonTrouveException() {
		super("Ce Compte n'est pas trouvé dans notre base de données");
	}

	public CompteNonTrouveException(String message) {
		super(message);
	}

}

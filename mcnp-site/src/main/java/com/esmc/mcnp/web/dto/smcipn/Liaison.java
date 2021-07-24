package com.esmc.mcnp.web.dto.smcipn;

import java.io.Serializable;

public class Liaison implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codeCompteadmin;
	private String codeCompteAnim;

	public Liaison() {
	}

	public Liaison(String codeCompteadmin, String codeCompteAnim) {
		this.codeCompteadmin = codeCompteadmin;
		this.codeCompteAnim = codeCompteAnim;
	}

	public String getCodeCompteadmin() {
		return codeCompteadmin;
	}

	public void setCodeCompteadmin(String codeCompteadmin) {
		this.codeCompteadmin = codeCompteadmin;
	}

	public String getCodeCompteAnim() {
		return codeCompteAnim;
	}

	public void setCodeCompteAnim(String codeCompteAnim) {
		this.codeCompteAnim = codeCompteAnim;
	}

}

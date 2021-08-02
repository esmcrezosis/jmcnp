package com.esmc.mcnp.domain.entity.config;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the eu_parametres database table.
 *
 */
@Embeddable
public class EuParametresPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String codeParam;
	private String libParam;

	public EuParametresPK() {
	}

	public EuParametresPK(String codeParam, String libParam) {
		this.codeParam = codeParam;
		this.libParam = libParam;
	}

	@Column(name = "code_param", unique = true, nullable = false, length = 200)
	public String getCodeParam() {
		return codeParam;
	}

	public void setCodeParam(String codeParam) {
		this.codeParam = codeParam;
	}

	@Column(name = "lib_param", unique = true, nullable = false, length = 200)
	public String getLibParam() {
		return libParam;
	}

	public void setLibParam(String libParam) {
		this.libParam = libParam;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EuParametresPK)) {
			return false;
		}
		EuParametresPK castOther = (EuParametresPK) other;
		return codeParam.equals(castOther.codeParam) && libParam.equals(castOther.libParam);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + codeParam.hashCode();
		hash = hash * prime + libParam.hashCode();

		return hash;
	}
}
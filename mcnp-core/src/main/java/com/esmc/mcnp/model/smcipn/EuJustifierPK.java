package com.esmc.mcnp.model.smcipn;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the EU_JUSTIFIER database table.
 * 
 */
@Embeddable
public class EuJustifierPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String codeMembre;
	private String codeSmcipn;

	public EuJustifierPK() {
	}

	@Column(name="code_membre", insertable=false, updatable=false, unique=true, nullable=false, length=25)
	public String getCodeMembre() {
		return this.codeMembre;
	}
	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Column(name="id_smcipn", insertable=false, updatable=false, unique=true, nullable=false, length=25)
	public String getCodeSmcipn() {
		return this.codeSmcipn;
	}
	public void setCodeSmcipn(String codeSmcipn) {
		this.codeSmcipn = codeSmcipn;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EuJustifierPK)) {
			return false;
		}
		EuJustifierPK castOther = (EuJustifierPK)other;
		return 
			this.codeMembre.equals(castOther.codeMembre)
			&& this.codeSmcipn.equals(castOther.codeSmcipn);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codeMembre.hashCode();
		hash = hash * prime + this.codeSmcipn.hashCode();
		
		return hash;
	}
}
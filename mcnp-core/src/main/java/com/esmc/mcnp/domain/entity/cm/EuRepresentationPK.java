package com.esmc.mcnp.domain.entity.cm;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the eu_representation database table.
 * 
 */
@Embeddable
public class EuRepresentationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String codeMembre;
	private String codeMembreMorale;

	public EuRepresentationPK() {
	}

	public EuRepresentationPK(String codeMembre, String codeMembreMorale) {
		this.codeMembre = codeMembre;
		this.codeMembreMorale = codeMembreMorale;
	}

	@Column(name="code_membre", length=100)
	public String getCodeMembre() {
		return this.codeMembre;
	}
	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Column(name="code_membre_morale", length=100)
	public String getCodeMembreMorale() {
		return this.codeMembreMorale;
	}
	public void setCodeMembreMorale(String codeMembreMorale) {
		this.codeMembreMorale = codeMembreMorale;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EuRepresentationPK)) {
			return false;
		}
		EuRepresentationPK castOther = (EuRepresentationPK)other;
		return 
			this.codeMembre.equals(castOther.codeMembre)
			&& this.codeMembreMorale.equals(castOther.codeMembreMorale);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codeMembre.hashCode();
		hash = hash * prime + this.codeMembreMorale.hashCode();
		
		return hash;
	}
}
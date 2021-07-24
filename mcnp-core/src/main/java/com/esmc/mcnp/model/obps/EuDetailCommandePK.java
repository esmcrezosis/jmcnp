package com.esmc.mcnp.model.obps;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the EU_DETAIL_COMMANDE database table.
 * 
 */
@Embeddable
public class EuDetailCommandePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String codeCommande;
	private long idObjet;

	public EuDetailCommandePK() {
	}

	@Column(name="CODE_COMMANDE", insertable=false, updatable=false, unique=true, nullable=false, length=25)
	public String getCodeCommande() {
		return this.codeCommande;
	}
	public void setCodeCommande(String codeCommande) {
		this.codeCommande = codeCommande;
	}

	@Column(name="ID_OBJET", insertable=false, updatable=false, unique=true, nullable=false, precision=19)
	public long getIdObjet() {
		return this.idObjet;
	}
	public void setIdObjet(long idObjet) {
		this.idObjet = idObjet;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EuDetailCommandePK)) {
			return false;
		}
		EuDetailCommandePK castOther = (EuDetailCommandePK)other;
		return 
			this.codeCommande.equals(castOther.codeCommande)
			&& (this.idObjet == castOther.idObjet);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codeCommande.hashCode();
		hash = hash * prime + ((int) (this.idObjet ^ (this.idObjet >>> 32)));
		
		return hash;
	}
}
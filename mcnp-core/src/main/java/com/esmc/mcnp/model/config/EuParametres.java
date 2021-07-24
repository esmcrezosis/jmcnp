package com.esmc.mcnp.model.config;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_parametres database table.
 * 
 */
@Entity
@Table(name="eu_parametres")
@NamedQuery(name="EuParametres.findAll", query="SELECT e FROM EuParametres e")
public class EuParametres implements Serializable {
	private static final long serialVersionUID = 1L;
	private EuParametresPK id;
	private double montant;

	public EuParametres() {
	}


	@EmbeddedId
	public EuParametresPK getId() {
		return this.id;
	}

	public void setId(EuParametresPK id) {
		this.id = id;
	}


	public double getMontant() {
		return this.montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

}
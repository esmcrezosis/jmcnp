package com.esmc.mcnp.model.cmfh;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_rep_reglement database table.
 * 
 */
@Entity
@Table(name="eu_rep_reglement")
@NamedQuery(name="EuRepReglement.findAll", query="SELECT e FROM EuRepReglement e")
public class EuRepReglement implements Serializable {
	private static final long serialVersionUID = 1L;
	private EuRepReglementPK id;
	private double montRepReglt;

	public EuRepReglement() {
	}


	@EmbeddedId
	public EuRepReglementPK getId() {
		return this.id;
	}

	public void setId(EuRepReglementPK id) {
		this.id = id;
	}


	@Column(name="mont_rep_reglt")
	public double getMontRepReglt() {
		return this.montRepReglt;
	}

	public void setMontRepReglt(double montRepReglt) {
		this.montRepReglt = montRepReglt;
	}

}
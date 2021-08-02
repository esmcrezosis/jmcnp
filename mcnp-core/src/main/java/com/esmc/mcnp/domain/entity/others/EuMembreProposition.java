package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_membre_proposition database table.
 * 
 */
@Entity
@Table(name="eu_membre_proposition")
@NamedQuery(name="EuMembreProposition.findAll", query="SELECT e FROM EuMembreProposition e")
public class EuMembreProposition implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idMembreProposition;
	private String codeMembre;
	private EuProposition euProposition;

	public EuMembreProposition() {
	}


	@Id
	@Column(name="id_membre_proposition", unique=true, nullable=false)
	public double getIdMembreProposition() {
		return this.idMembreProposition;
	}

	public void setIdMembreProposition(double idMembreProposition) {
		this.idMembreProposition = idMembreProposition;
	}


	@Column(name="code_membre", length=25)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	//bi-directional many-to-one association to EuProposition
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_proposition")
	public EuProposition getEuProposition() {
		return this.euProposition;
	}

	public void setEuProposition(EuProposition euProposition) {
		this.euProposition = euProposition;
	}

}
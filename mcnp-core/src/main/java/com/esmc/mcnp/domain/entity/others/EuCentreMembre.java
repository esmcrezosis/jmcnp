package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_centre_membre database table.
 * 
 */
@Entity
@Table(name="eu_centre_membre")
@NamedQuery(name="EuCentreMembre.findAll", query="SELECT e FROM EuCentreMembre e")
public class EuCentreMembre implements Serializable {
	private static final long serialVersionUID = 1L;
	private double centreMembreId;
	private double centreId;
	private String codeMembre;

	public EuCentreMembre() {
	}


	@Id
	@Column(name="centre_membre_id", unique=true, nullable=false)
	public double getCentreMembreId() {
		return this.centreMembreId;
	}

	public void setCentreMembreId(double centreMembreId) {
		this.centreMembreId = centreMembreId;
	}


	@Column(name="centre_id")
	public double getCentreId() {
		return this.centreId;
	}

	public void setCentreId(double centreId) {
		this.centreId = centreId;
	}


	@Column(name="code_membre", length=25)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

}
package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_emploi_esmc database table.
 * 
 */
@Entity
@Table(name="eu_emploi_esmc")
@NamedQuery(name="EuEmploiEsmc.findAll", query="SELECT e FROM EuEmploiEsmc e")
public class EuEmploiEsmc implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idEmploi;
	private String codeEmploi;
	private String libEmploi;

	public EuEmploiEsmc() {
	}


	@Id
	@Column(name="id_emploi", unique=true, nullable=false)
	public double getIdEmploi() {
		return this.idEmploi;
	}

	public void setIdEmploi(double idEmploi) {
		this.idEmploi = idEmploi;
	}


	@Column(name="code_emploi", length=40)
	public String getCodeEmploi() {
		return this.codeEmploi;
	}

	public void setCodeEmploi(String codeEmploi) {
		this.codeEmploi = codeEmploi;
	}


	@Column(name="lib_emploi", length=200)
	public String getLibEmploi() {
		return this.libEmploi;
	}

	public void setLibEmploi(String libEmploi) {
		this.libEmploi = libEmploi;
	}

}
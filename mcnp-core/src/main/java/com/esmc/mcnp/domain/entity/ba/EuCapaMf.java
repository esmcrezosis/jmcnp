package com.esmc.mcnp.domain.entity.ba;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_capa_mf database table.
 * 
 */
@Entity
@Table(name="eu_capa_mf")
@NamedQuery(name="EuCapaMf.findAll", query="SELECT e FROM EuCapaMf e")
public class EuCapaMf implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idCapaMf;
	private String codeCapa;
	private String codeMembreBenef;
	private String codeMembreMf;
	private String codeMembrePbf;
	private Date dateCapa;
	private double idOperation;
	private double idUtilisateur;
	private double montMf;
	private double numBon;
	private double rembourser;

	public EuCapaMf() {
	}


	@Id
	@Column(name="id_capa_mf", unique=true, nullable=false)
	public double getIdCapaMf() {
		return this.idCapaMf;
	}

	public void setIdCapaMf(double idCapaMf) {
		this.idCapaMf = idCapaMf;
	}


	@Column(name="code_capa", length=200)
	public String getCodeCapa() {
		return this.codeCapa;
	}

	public void setCodeCapa(String codeCapa) {
		this.codeCapa = codeCapa;
	}


	@Column(name="code_membre_benef", length=180)
	public String getCodeMembreBenef() {
		return this.codeMembreBenef;
	}

	public void setCodeMembreBenef(String codeMembreBenef) {
		this.codeMembreBenef = codeMembreBenef;
	}


	@Column(name="code_membre_mf", length=180)
	public String getCodeMembreMf() {
		return this.codeMembreMf;
	}

	public void setCodeMembreMf(String codeMembreMf) {
		this.codeMembreMf = codeMembreMf;
	}


	@Column(name="code_membre_pbf", length=180)
	public String getCodeMembrePbf() {
		return this.codeMembrePbf;
	}

	public void setCodeMembrePbf(String codeMembrePbf) {
		this.codeMembrePbf = codeMembrePbf;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_capa")
	public Date getDateCapa() {
		return this.dateCapa;
	}

	public void setDateCapa(Date dateCapa) {
		this.dateCapa = dateCapa;
	}


	@Column(name="id_operation")
	public double getIdOperation() {
		return this.idOperation;
	}

	public void setIdOperation(double idOperation) {
		this.idOperation = idOperation;
	}


	@Column(name="id_utilisateur")
	public double getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(double idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	@Column(name="mont_mf")
	public double getMontMf() {
		return this.montMf;
	}

	public void setMontMf(double montMf) {
		this.montMf = montMf;
	}


	@Column(name="num_bon")
	public double getNumBon() {
		return this.numBon;
	}

	public void setNumBon(double numBon) {
		this.numBon = numBon;
	}


	@Column(nullable=false)
	public double getRembourser() {
		return this.rembourser;
	}

	public void setRembourser(double rembourser) {
		this.rembourser = rembourser;
	}

}
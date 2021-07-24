package com.esmc.mcnp.model.cm;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the eu_ancien_fs database table.
 * 
 */
@Entity
@Table(name="eu_ancien_fs")
@NamedQuery(name="EuAncienF.findAll", query="SELECT e FROM EuAncienFs e")
public class EuAncienFs implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeFs;
	private String codeMembre;
	private String creditcode;
	private Date dateFs;
	private Time heureFs;
	private BigInteger idUtilisateur;
	private double montFs;

	public EuAncienFs() {
	}


	@Id
	@Column(name="code_fs")
	public String getCodeFs() {
		return this.codeFs;
	}

	public void setCodeFs(String codeFs) {
		this.codeFs = codeFs;
	}


	@Column(name="code_membre")
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	public String getCreditcode() {
		return this.creditcode;
	}

	public void setCreditcode(String creditcode) {
		this.creditcode = creditcode;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_fs")
	public Date getDateFs() {
		return this.dateFs;
	}

	public void setDateFs(Date dateFs) {
		this.dateFs = dateFs;
	}


	@Column(name="heure_fs")
	public Time getHeureFs() {
		return this.heureFs;
	}

	public void setHeureFs(Time heureFs) {
		this.heureFs = heureFs;
	}


	@Column(name="id_utilisateur")
	public BigInteger getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(BigInteger idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	@Column(name="mont_fs")
	public double getMontFs() {
		return this.montFs;
	}

	public void setMontFs(double montFs) {
		this.montFs = montFs;
	}

}
package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the eu_ancienne_operation database table.
 * 
 */
@Entity
@Table(name="eu_ancienne_operation")
@NamedQuery(name="EuAncienneOperation.findAll", query="SELECT e FROM EuAncienneOperation e")
public class EuAncienneOperation implements Serializable {
	private static final long serialVersionUID = 1L;
	private String idOperation;
	private String codeCat;
	private String codeMembre;
	private String codeProduit;
	private Date dateOp;
	private Time heureOp;
	private BigInteger idUtilisateur;
	private String libOp;
	private double montantOp;
	private String typeOp;

	public EuAncienneOperation() {
	}


	@Id
	@Column(name="id_operation")
	public String getIdOperation() {
		return this.idOperation;
	}

	public void setIdOperation(String idOperation) {
		this.idOperation = idOperation;
	}


	@Column(name="code_cat")
	public String getCodeCat() {
		return this.codeCat;
	}

	public void setCodeCat(String codeCat) {
		this.codeCat = codeCat;
	}


	@Column(name="code_membre")
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Column(name="code_produit")
	public String getCodeProduit() {
		return this.codeProduit;
	}

	public void setCodeProduit(String codeProduit) {
		this.codeProduit = codeProduit;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_op")
	public Date getDateOp() {
		return this.dateOp;
	}

	public void setDateOp(Date dateOp) {
		this.dateOp = dateOp;
	}


	@Column(name="heure_op")
	public Time getHeureOp() {
		return this.heureOp;
	}

	public void setHeureOp(Time heureOp) {
		this.heureOp = heureOp;
	}

	@Column(name="id_utilisateur")
	public BigInteger getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(BigInteger idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	@Column(name="lib_op")
	public String getLibOp() {
		return this.libOp;
	}

	public void setLibOp(String libOp) {
		this.libOp = libOp;
	}


	@Column(name="montant_op")
	public double getMontantOp() {
		return this.montantOp;
	}

	public void setMontantOp(double montantOp) {
		this.montantOp = montantOp;
	}


	@Column(name="type_op")
	public String getTypeOp() {
		return this.typeOp;
	}

	public void setTypeOp(String typeOp) {
		this.typeOp = typeOp;
	}

}
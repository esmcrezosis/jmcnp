package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_facture_smcipn database table.
 *
 */
@Entity
@Table(name="eu_facture_smcipn")
@NamedQuery(name="EuFactureSmcipn.findAll", query="SELECT e FROM EuFactureSmcipn e")
public class EuFactureSmcipn implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeFacture;
	private String codeMembreMorale;
	private String codeSmcipn;
	private Date dateFacture;
	private double etatFacture;
	private double idUtilisateur;
	private double montFacture;
	private String typeFacture;

	public EuFactureSmcipn() {
	}


	@Id
	@Column(name="code_facture", nullable=false, length=25)
	public String getCodeFacture() {
		return this.codeFacture;
	}

	public void setCodeFacture(String codeFacture) {
		this.codeFacture = codeFacture;
	}


	@Column(name="code_membre_morale", length=25)
	public String getCodeMembreMorale() {
		return this.codeMembreMorale;
	}

	public void setCodeMembreMorale(String codeMembreMorale) {
		this.codeMembreMorale = codeMembreMorale;
	}


	@Column(name="code_smcipn", length=30)
	public String getCodeSmcipn() {
		return this.codeSmcipn;
	}

	public void setCodeSmcipn(String codeSmcipn) {
		this.codeSmcipn = codeSmcipn;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_facture")
	public Date getDateFacture() {
		return this.dateFacture;
	}

	public void setDateFacture(Date dateFacture) {
		this.dateFacture = dateFacture;
	}


	@Column(name="etat_facture")
	public double getEtatFacture() {
		return this.etatFacture;
	}

	public void setEtatFacture(double etatFacture) {
		this.etatFacture = etatFacture;
	}


	@Column(name="id_utilisateur")
	public double getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(double idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	@Column(name="mont_facture")
	public double getMontFacture() {
		return this.montFacture;
	}

	public void setMontFacture(double montFacture) {
		this.montFacture = montFacture;
	}


	@Column(name="type_facture", length=30)
	public String getTypeFacture() {
		return this.typeFacture;
	}

	public void setTypeFacture(String typeFacture) {
		this.typeFacture = typeFacture;
	}

}
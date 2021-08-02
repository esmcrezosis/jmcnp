package com.esmc.mcnp.domain.entity.cmfh;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_domiciliation_mf database table.
 * 
 */
@Entity
@Table(name="eu_domiciliation_mf")
@NamedQuery(name="EuDomiciliationMf.findAll", query="SELECT e FROM EuDomiciliationMf e")
public class EuDomiciliationMf implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idDomMf;
	private String codeMembreMorale;
	private Date dateDomMf;
	private double etatDomMf;
	private double idUtilisateur;
	private double mtDomMf;
	private double mtDomObtenu;

	public EuDomiciliationMf() {
	}


	@Id
	@Column(name="id_dom_mf", unique=true, nullable=false)
	public double getIdDomMf() {
		return this.idDomMf;
	}

	public void setIdDomMf(double idDomMf) {
		this.idDomMf = idDomMf;
	}


	@Column(name="code_membre_morale", length=25)
	public String getCodeMembreMorale() {
		return this.codeMembreMorale;
	}

	public void setCodeMembreMorale(String codeMembreMorale) {
		this.codeMembreMorale = codeMembreMorale;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_dom_mf")
	public Date getDateDomMf() {
		return this.dateDomMf;
	}

	public void setDateDomMf(Date dateDomMf) {
		this.dateDomMf = dateDomMf;
	}


	@Column(name="etat_dom_mf")
	public double getEtatDomMf() {
		return this.etatDomMf;
	}

	public void setEtatDomMf(double etatDomMf) {
		this.etatDomMf = etatDomMf;
	}


	@Column(name="id_utilisateur")
	public double getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(double idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	@Column(name="mt_dom_mf")
	public double getMtDomMf() {
		return this.mtDomMf;
	}

	public void setMtDomMf(double mtDomMf) {
		this.mtDomMf = mtDomMf;
	}


	@Column(name="mt_dom_obtenu")
	public double getMtDomObtenu() {
		return this.mtDomObtenu;
	}

	public void setMtDomObtenu(double mtDomObtenu) {
		this.mtDomObtenu = mtDomObtenu;
	}

}
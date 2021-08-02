package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_licence database table.
 * 
 */
@Entity
@Table(name="eu_licence")
@NamedQuery(name="EuLicence.findAll", query="SELECT e FROM EuLicence e")
public class EuLicence implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idLicence;
	private String codeMembreMorale;
	private Date dateLicence;
	private String descLicence;
	private double idUtilisateur;
	private String libelleLicence;
	private String numLicence;

	public EuLicence() {
	}


	@Id
	@Column(name="id_licence", unique=true, nullable=false)
	public double getIdLicence() {
		return this.idLicence;
	}

	public void setIdLicence(double idLicence) {
		this.idLicence = idLicence;
	}


	@Column(name="code_membre_morale", length=20)
	public String getCodeMembreMorale() {
		return this.codeMembreMorale;
	}

	public void setCodeMembreMorale(String codeMembreMorale) {
		this.codeMembreMorale = codeMembreMorale;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_licence")
	public Date getDateLicence() {
		return this.dateLicence;
	}

	public void setDateLicence(Date dateLicence) {
		this.dateLicence = dateLicence;
	}


	@Column(name="desc_licence", length=450)
	public String getDescLicence() {
		return this.descLicence;
	}

	public void setDescLicence(String descLicence) {
		this.descLicence = descLicence;
	}


	@Column(name="id_utilisateur")
	public double getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(double idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	@Column(name="libelle_licence", length=45)
	public String getLibelleLicence() {
		return this.libelleLicence;
	}

	public void setLibelleLicence(String libelleLicence) {
		this.libelleLicence = libelleLicence;
	}


	@Column(name="num_licence", length=45)
	public String getNumLicence() {
		return this.numLicence;
	}

	public void setNumLicence(String numLicence) {
		this.numLicence = numLicence;
	}

}
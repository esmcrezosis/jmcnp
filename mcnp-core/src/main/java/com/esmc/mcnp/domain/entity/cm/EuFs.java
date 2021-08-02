package com.esmc.mcnp.domain.entity.cm;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;

import java.util.Date;


/**
 * The persistent class for the eu_fs database table.
 *
 */
@Entity
@Table(name="eu_fs")
@NamedQuery(name="EuFs.findAll", query="SELECT e FROM EuFs e")
public class EuFs implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeFs;
	private String codeMembre;
	private String creditcode;
	private Date dateFs;
	private Date heureFs;
	private Long idUtilisateur;
	private double montFs;
	private EuMembreMorale euMembreMorale;

	public EuFs() {
	}


	@Id
	@Column(name="code_fs", unique=true, nullable=false, length=200)
	public String getCodeFs() {
		return this.codeFs;
	}

	public void setCodeFs(String codeFs) {
		this.codeFs = codeFs;
	}


	@Column(name="code_membre", length=200)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Column(length=200)
	public String getCreditcode() {
		return this.creditcode;
	}

	public void setCreditcode(String creditcode) {
		this.creditcode = creditcode;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_fs")
	public Date getDateFs() {
		return this.dateFs;
	}

	public void setDateFs(Date dateFs) {
		this.dateFs = dateFs;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="heure_fs")
	public Date getHeureFs() {
		return this.heureFs;
	}

	public void setHeureFs(Date heureFs) {
		this.heureFs = heureFs;
	}


	@Column(name="id_utilisateur")
	public Long getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	@Column(name="mont_fs")
	public double getMontFs() {
		return this.montFs;
	}

	public void setMontFs(double montFs) {
		this.montFs = montFs;
	}


	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre_morale")
	public EuMembreMorale getEuMembreMorale() {
		return this.euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}

}
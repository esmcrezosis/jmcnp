package com.esmc.mcnp.model.pc;

import com.esmc.mcnp.model.others.EuPostePointage;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuPointage
 *
 */
@Entity
@Table(name = "eu_pointage")

public class EuPointage implements Serializable {

	private Integer idPointage;
	private String codeMembreEmploye;
	private Date dateHeureDebut;
	private Date dateHeureFin;
	private Date datePointage;
	private Boolean traiter;
	private EuPostePointage euPostePointage;
	private static final long serialVersionUID = 1L;

	public EuPointage() {
		super();
	}

	@Id
	@Column(name = "id_pointage")
	public Integer getIdPointage() {
		return this.idPointage;
	}

	public void setIdPointage(Integer idPointage) {
		this.idPointage = idPointage;
	}

	@Column(name = "code_membre_employe")
	public String getCodeMembreEmploye() {
		return this.codeMembreEmploye;
	}

	public void setCodeMembreEmploye(String codeMembreEmploye) {
		this.codeMembreEmploye = codeMembreEmploye;
	}

	@Column(name = "date_heure_debut")
	public Date getDateHeureDebut() {
		return this.dateHeureDebut;
	}

	public void setDateHeureDebut(Date dateHeureDebut) {
		this.dateHeureDebut = dateHeureDebut;
	}

	@Column(name = "date_heure_fin")
	public Date getDateHeureFin() {
		return this.dateHeureFin;
	}

	public void setDateHeureFin(Date dateHeureFin) {
		this.dateHeureFin = dateHeureFin;
	}

	@Column(name = "date_pointage")
	public Date getDatePointage() {
		return this.datePointage;
	}

	public void setDatePointage(Date datePointage) {
		this.datePointage = datePointage;
	}

	@Column(name = "traiter")
	public Boolean getTraiter() {
		return this.traiter;
	}

	public void setTraiter(Boolean traiter) {
		this.traiter = traiter;
	}

	@ManyToOne
	@JoinColumn(name = "id_poste_pointage")
	public EuPostePointage getEuPostePointage() {
		return this.euPostePointage;
	}

	public void setEuPostePointage(EuPostePointage idPostePointage) {
		this.euPostePointage = idPostePointage;
	}

}

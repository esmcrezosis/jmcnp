package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_frais database table.
 *
 */
@Entity
@Table(name="eu_frais")
@NamedQuery(name="EuFrais.findAll", query="SELECT e FROM EuFrais e")
public class EuFrais implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idFrais;
	private String codeGac;
	private Date dateFrais;
	private Integer disponible;
	private Long idProposition;
	private Long idUtilisateur;
	private double montProjet;
	private double montantFrais;
	private double montantProposition;
	private double montantSalaire;
	private double pourcentFrais;
	private Integer valider;

	public EuFrais() {
	}


	@Id
	@Column(name="id_frais", unique=true, nullable=false)
	public Long getIdFrais() {
		return this.idFrais;
	}

	public void setIdFrais(Long idFrais) {
		this.idFrais = idFrais;
	}


	@Column(name="code_gac", length=25)
	public String getCodeGac() {
		return this.codeGac;
	}

	public void setCodeGac(String codeGac) {
		this.codeGac = codeGac;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_frais")
	public Date getDateFrais() {
		return this.dateFrais;
	}

	public void setDateFrais(Date dateFrais) {
		this.dateFrais = dateFrais;
	}


	public Integer getDisponible() {
		return this.disponible;
	}

	public void setDisponible(Integer disponible) {
		this.disponible = disponible;
	}


	@Column(name="id_proposition")
	public Long getIdProposition() {
		return this.idProposition;
	}

	public void setIdProposition(Long idProposition) {
		this.idProposition = idProposition;
	}


	@Column(name="id_utilisateur")
	public Long getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	@Column(name="mont_projet")
	public double getMontProjet() {
		return this.montProjet;
	}

	public void setMontProjet(double montProjet) {
		this.montProjet = montProjet;
	}


	@Column(name="montant_frais")
	public double getMontantFrais() {
		return this.montantFrais;
	}

	public void setMontantFrais(double montantFrais) {
		this.montantFrais = montantFrais;
	}


	@Column(name="montant_proposition")
	public double getMontantProposition() {
		return this.montantProposition;
	}

	public void setMontantProposition(double montantProposition) {
		this.montantProposition = montantProposition;
	}


	@Column(name="montant_salaire")
	public double getMontantSalaire() {
		return this.montantSalaire;
	}

	public void setMontantSalaire(double montantSalaire) {
		this.montantSalaire = montantSalaire;
	}


	@Column(name="pourcent_frais")
	public double getPourcentFrais() {
		return this.pourcentFrais;
	}

	public void setPourcentFrais(double pourcentFrais) {
		this.pourcentFrais = pourcentFrais;
	}


	public Integer getValider() {
		return this.valider;
	}

	public void setValider(Integer valider) {
		this.valider = valider;
	}

}
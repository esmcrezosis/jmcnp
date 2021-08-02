package com.esmc.mcnp.domain.entity.bc;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the eu_bon database table.
 * 
 */
@Entity
@Table(name = "eu_bon")
@NamedQuery(name = "EuBon.findAll", query = "SELECT e FROM EuBon e")
public class EuBon implements Serializable {
	private static final long serialVersionUID = 1L;
	private int bonId;
	private String bonCodeBarre;
	private String bonCodeMembreDistributeur;
	private String bonCodeMembreEmetteur;
	private Date bonDate;
	private double bonMontant;
	private double bonMontantSalaire;
	private String bonNumero;
	private String bonProposition;
	private String bonType;
	private Integer bonExprimer;
	private Date bonDateExpression;

	public EuBon() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bon_id")
	public int getBonId() {
		return this.bonId;
	}

	public void setBonId(int bonId) {
		this.bonId = bonId;
	}

	@Column(name = "bon_code_barre")
	public String getBonCodeBarre() {
		return this.bonCodeBarre;
	}

	public void setBonCodeBarre(String bonCodeBarre) {
		this.bonCodeBarre = bonCodeBarre;
	}

	@Column(name = "bon_code_membre_distributeur")
	public String getBonCodeMembreDistributeur() {
		return this.bonCodeMembreDistributeur;
	}

	public void setBonCodeMembreDistributeur(String bonCodeMembreDistributeur) {
		this.bonCodeMembreDistributeur = bonCodeMembreDistributeur;
	}

	@Column(name = "bon_code_membre_emetteur")
	public String getBonCodeMembreEmetteur() {
		return this.bonCodeMembreEmetteur;
	}

	public void setBonCodeMembreEmetteur(String bonCodeMembreEmetteur) {
		this.bonCodeMembreEmetteur = bonCodeMembreEmetteur;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "bon_date")
	public Date getBonDate() {
		return this.bonDate;
	}

	public void setBonDate(Date bonDate) {
		this.bonDate = bonDate;
	}

	@Column(name = "bon_montant")
	public double getBonMontant() {
		return this.bonMontant;
	}

	public void setBonMontant(double bonMontant) {
		this.bonMontant = bonMontant;
	}

	@Column(name = "bon_montant_salaire")
	public double getBonMontantSalaire() {
		return this.bonMontantSalaire;
	}

	public void setBonMontantSalaire(double bonMontantSalaire) {
		this.bonMontantSalaire = bonMontantSalaire;
	}

	@Column(name = "bon_numero")
	public String getBonNumero() {
		return this.bonNumero;
	}

	public void setBonNumero(String bonNumero) {
		this.bonNumero = bonNumero;
	}

	@Column(name = "bon_proposition")
	public String getBonProposition() {
		return this.bonProposition;
	}

	public void setBonProposition(String bonProposition) {
		this.bonProposition = bonProposition;
	}

	@Column(name = "bon_type")
	public String getBonType() {
		return this.bonType;
	}

	public void setBonType(String bonType) {
		this.bonType = bonType;
	}

	@Column(name = "bon_exprimer")
	public Integer getBonExprimer() {
		return bonExprimer;
	}

	public void setBonExprimer(Integer bonExprimer) {
		this.bonExprimer = bonExprimer;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "bon_date_expression")
	public Date getBonDateExpression() {
		return bonDateExpression;
	}

	public void setBonDateExpression(Date bonDateExpression) {
		this.bonDateExpression = bonDateExpression;
	}

}
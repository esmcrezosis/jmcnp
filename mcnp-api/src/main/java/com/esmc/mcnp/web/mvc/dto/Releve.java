package com.esmc.mcnp.web.mvc.dto;

import java.util.Date;

public class Releve {
	
	private String codeBanque;
	private Date date;
	private String numero;
	private String libelle;
	private double montant;

	public Releve() {
		// TODO Auto-generated constructor stub
	}

	public Releve(String codeBanque, Date date, String numero, String libelle, double montant) {
		this.codeBanque = codeBanque;
		this.date = date;
		this.numero = numero;
		this.libelle = libelle;
		this.montant = montant;
	}

	public String getCodeBanque() {
		return codeBanque;
	}

	public void setCodeBanque(String codeBanque) {
		this.codeBanque = codeBanque;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

}

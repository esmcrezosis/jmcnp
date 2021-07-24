/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dto.bc;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class Apa implements Serializable {

	private static final long serialVersionUID = 1L;
	private String modeApa;
	private String codeMembre;
	private String typeBon;
	private String catBon;
	private String typeRecurrent;
	private String typeProduit;
	private String codeBa;
	private String typeNn;
	private String catNn;
	private double montApa;
	private double prk;
	private double montCredit;
	private double montInvest;
	private int dureeInvest;
	private String numeroAppelOffre;
	private Long idUtilisateur;

	public Apa() {
	}

	public String getModeApa() {
		return modeApa;
	}

	public void setModeApa(String modeApa) {
		this.modeApa = modeApa;
	}

	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	public String getTypeBon() {
		return typeBon;
	}

	public void setTypeBon(String typeBon) {
		this.typeBon = typeBon;
	}

	public String getCatBon() {
		return catBon;
	}

	public void setCatBon(String catBon) {
		this.catBon = catBon;
	}

	public String getTypeRecurrent() {
		return typeRecurrent;
	}

	public void setTypeRecurrent(String typeRecurrent) {
		this.typeRecurrent = typeRecurrent;
	}

	public String getTypeProduit() {
		return typeProduit;
	}

	public void setTypeProduit(String typeProduit) {
		this.typeProduit = typeProduit;
	}

	public String getCodeBa() {
		return codeBa;
	}

	public void setCodeBa(String codeBa) {
		this.codeBa = codeBa;
	}

	public String getTypeNn() {
		return typeNn;
	}

	public void setTypeNn(String typeNn) {
		this.typeNn = typeNn;
	}

	public String getCatNn() {
		return catNn;
	}

	public void setCatNn(String catNn) {
		this.catNn = catNn;
	}

	public double getPrk() {
		return prk;
	}

	public void setPrk(double prk) {
		this.prk = prk;
	}

	public double getMontApa() {
		return montApa;
	}

	public void setMontApa(double montApa) {
		this.montApa = montApa;
	}

	public double getMontCredit() {
		return montCredit;
	}

	public void setMontCredit(double montCredit) {
		this.montCredit = montCredit;
	}

	public Long getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public double getMontInvest() {
		return montInvest;
	}

	public void setMontInvest(double montInvest) {
		this.montInvest = montInvest;
	}

	public int getDureeInvest() {
		return dureeInvest;
	}

	public void setDureeInvest(int dureeInvest) {
		this.dureeInvest = dureeInvest;
	}

	public String getNumeroAppelOffre() {
		return numeroAppelOffre;
	}

	public void setNumeroAppelOffre(String numeroAppelOffre) {
		this.numeroAppelOffre = numeroAppelOffre;
	}

}

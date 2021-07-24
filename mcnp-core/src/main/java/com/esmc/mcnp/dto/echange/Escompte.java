/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dto.echange;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author USER
 */
public class Escompte implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idEscompte;
	private String codeMembre;
	private double montant;
	private int ntf;
	private double mont_tranche;
	private LocalDate datedeb;
	private LocalDate datefin;

	public Escompte() {
	}

	public Long getIdEscompte() {
		return idEscompte;
	}

	public void setIdEscompte(Long idEscompte) {
		this.idEscompte = idEscompte;
	}

	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public int getNtf() {
		return ntf;
	}

	public void setNtf(int ntf) {
		this.ntf = ntf;
	}

	public double getMont_tranche() {
		return mont_tranche;
	}

	public void setMont_tranche(double mont_tranche) {
		this.mont_tranche = mont_tranche;
	}

	public LocalDate getDatedeb() {
		return datedeb;
	}

	public void setDatedeb(LocalDate datedeb) {
		this.datedeb = datedeb;
	}

	public LocalDate getDatefin() {
		return datefin;
	}

	public void setDatefin(LocalDate datefin) {
		this.datefin = datefin;
	}

}

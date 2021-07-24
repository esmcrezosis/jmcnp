package com.esmc.mcnp.web.dto.ot;

import com.esmc.mcnp.web.dto.smcipn.Creance;

public class SalaireDTO {
	private String codeCharge;
	private String libelleCharge;
	private String numeroAppel;
	private double montantCharge;
	private Creance[] creances;

	public SalaireDTO() {
	}

	public SalaireDTO(String numeroAppel, double montantCharge) {
		this.setNumeroAppel(numeroAppel);
		this.montantCharge = montantCharge;
	}

	public SalaireDTO(String codeCharge, String libelleCharge, String numeroAppel, double montantCharge,
			Creance[] creances) {
		this.codeCharge = codeCharge;
		this.libelleCharge = libelleCharge;
		this.numeroAppel = numeroAppel;
		this.montantCharge = montantCharge;
		this.creances = creances;
	}

	public String getCodeCharge() {
		return codeCharge;
	}

	public void setCodeCharge(String codeCharge) {
		this.codeCharge = codeCharge;
	}

	public String getLibelleCharge() {
		return libelleCharge;
	}

	public void setLibelleCharge(String libelleCharge) {
		this.libelleCharge = libelleCharge;
	}

	public String getNumeroAppel() {
		return numeroAppel;
	}

	public void setNumeroAppel(String numeroAppel) {
		this.numeroAppel = numeroAppel;
	}

	public double getMontantCharge() {
		return montantCharge;
	}

	public void setMontantCharge(double montantCharge) {
		this.montantCharge = montantCharge;
	}

	public Creance[] getCreances() {
		return creances;
	}

	public void setCreances(Creance[] creances) {
		this.creances = creances;
	}
}

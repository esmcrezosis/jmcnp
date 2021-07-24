package com.esmc.mcnp.web.mvc.utils;

public class BnpForm {

	private String typeBnp;
	private boolean fs;
	private double montantBnp;

	public BnpForm() {
	}

	public BnpForm(String typeBnp, boolean fs, double montantBnp) {
		this.typeBnp = typeBnp;
		this.fs = fs;
		this.montantBnp = montantBnp;
	}

	public String getTypeBnp() {
		return typeBnp;
	}

	public void setTypeBnp(String typeBnp) {
		this.typeBnp = typeBnp;
	}

	public boolean isFs() {
		return fs;
	}

	public void setFs(boolean fs) {
		this.fs = fs;
	}

	public double getMontantBnp() {
		return montantBnp;
	}

	public void setMontantBnp(double montantBnp) {
		this.montantBnp = montantBnp;
	}

}
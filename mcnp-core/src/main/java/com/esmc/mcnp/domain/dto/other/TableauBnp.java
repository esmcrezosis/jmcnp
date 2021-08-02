package com.esmc.mcnp.domain.dto.other;

public class TableauBnp {

	private long idCredit;
	private String codeBnp;
	private double montBnp;
	private double montCredit;
	private double montConus;
	private double montPar;
	private double montPanu;
	private double montFs;
	private double totConus;
	private double totPar;
	private double totPanu;
	private double totFs;
	private int periode;

	public TableauBnp(long idCredit, String codeBnp, double montBnp, double montCredit, double montConus,
			double montPar, double montPanu, double montFs, int periode) {
		super();
		this.idCredit = idCredit;
		this.codeBnp = codeBnp;
		this.montBnp = montBnp;
		this.montCredit = montCredit;
		this.montConus = montConus;
		this.montPar = montPar;
		this.montPanu = montPanu;
		this.montFs = montFs;
		this.periode = periode;
	}

	public long getIdCredit() {
		return idCredit;
	}

	public void setIdCredit(long idCredit) {
		this.idCredit = idCredit;
	}

	public String getCodeBnp() {
		return codeBnp;
	}

	public void setCodeBnp(String codeBnp) {
		this.codeBnp = codeBnp;
	}

	public double getMontBnp() {
		return montBnp;
	}

	public void setMontBnp(double montBnp) {
		this.montBnp = montBnp;
	}

	public double getMontCredit() {
		return montCredit;
	}

	public void setMontCredit(double montCredit) {
		this.montCredit = montCredit;
	}

	public double getMontConus() {
		return montConus;
	}

	public void setMontConus(double montConus) {
		this.montConus = montConus;
	}

	public double getMontPar() {
		return montPar;
	}

	public void setMontPar(double montPar) {
		this.montPar = montPar;
	}

	public double getMontPanu() {
		return montPanu;
	}

	public void setMontPanu(double montPanu) {
		this.montPanu = montPanu;
	}

	public double getMontFs() {
		return montFs;
	}

	public void setMontFs(double montFs) {
		this.montFs = montFs;
	}

	public int getPeriode() {
		return periode;
	}

	public void setPeriode(int periode) {
		this.periode = periode;
	}

	public double getTotConus() {
		return totConus;
	}

	public void setTotConus(double totConus) {
		this.totConus = totConus;
	}

	public double getTotPar() {
		return totPar;
	}

	public void setTotPar(double totPar) {
		this.totPar = totPar;
	}

	public double getTotPanu() {
		return totPanu;
	}

	public void setTotPanu(double totPanu) {
		this.totPanu = totPanu;
	}

	public double getTotFs() {
		return totFs;
	}

	public void setTotFs(double totFs) {
		this.totFs = totFs;
	}

}

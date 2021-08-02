package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_cours database table.
 * 
 */
@Entity
@Table(name="eu_cours")
@NamedQuery(name="EuCours.findAll", query="SELECT e FROM EuCours e")
public class EuCours implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeCours;
	private String libDevise;
	private double valDevFin;
	private double valDevInit;
	private EuDevise euDevise1;
	private EuDevise euDevise2;

	public EuCours() {
	}


	@Id
	@Column(name="code_cours", unique=true, nullable=false, length=60)
	public String getCodeCours() {
		return this.codeCours;
	}

	public void setCodeCours(String codeCours) {
		this.codeCours = codeCours;
	}


	@Column(name="lib_devise", length=10)
	public String getLibDevise() {
		return this.libDevise;
	}

	public void setLibDevise(String libDevise) {
		this.libDevise = libDevise;
	}


	@Column(name="val_dev_fin")
	public double getValDevFin() {
		return this.valDevFin;
	}

	public void setValDevFin(double valDevFin) {
		this.valDevFin = valDevFin;
	}


	@Column(name="val_dev_init")
	public double getValDevInit() {
		return this.valDevInit;
	}

	public void setValDevInit(double valDevInit) {
		this.valDevInit = valDevInit;
	}


	//bi-directional many-to-one association to EuDevise
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_dev_fin", nullable=false)
	public EuDevise getEuDevise1() {
		return this.euDevise1;
	}

	public void setEuDevise1(EuDevise euDevise1) {
		this.euDevise1 = euDevise1;
	}


	//bi-directional many-to-one association to EuDevise
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_dev_init", nullable=false)
	public EuDevise getEuDevise2() {
		return this.euDevise2;
	}

	public void setEuDevise2(EuDevise euDevise2) {
		this.euDevise2 = euDevise2;
	}

}
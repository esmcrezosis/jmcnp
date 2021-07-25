package com.esmc.mcnp.model.cmfh;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_detail_domicilie_mf database table.
 * 
 */
@Entity
@Table(name="eu_detail_domicilie_mf")
@NamedQuery(name="EuDetailDomicilieMf.findAll", query="SELECT e FROM EuDetailDomicilieMf e")
public class EuDetailDomicilieMf implements Serializable {
	private static final long serialVersionUID = 1L;
	private EuDetailDomicilieMfPK id;
	private double mtDomicilie;
	private double nbGainDom;
	private double nbGainReste;

	public EuDetailDomicilieMf() {
	}


	@EmbeddedId
	public EuDetailDomicilieMfPK getId() {
		return this.id;
	}

	public void setId(EuDetailDomicilieMfPK id) {
		this.id = id;
	}


	@Column(name="mt_domicilie")
	public double getMtDomicilie() {
		return this.mtDomicilie;
	}

	public void setMtDomicilie(double mtDomicilie) {
		this.mtDomicilie = mtDomicilie;
	}


	@Column(name="nb_gain_dom")
	public double getNbGainDom() {
		return this.nbGainDom;
	}

	public void setNbGainDom(double nbGainDom) {
		this.nbGainDom = nbGainDom;
	}


	@Column(name="nb_gain_reste")
	public double getNbGainReste() {
		return this.nbGainReste;
	}

	public void setNbGainReste(double nbGainReste) {
		this.nbGainReste = nbGainReste;
	}

}
package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_concerner database table.
 * 
 */
@Entity
@Table(name="eu_concerner")
@NamedQuery(name="EuConcerner.findAll", query="SELECT e FROM EuConcerner e")
public class EuConcerner implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idBesoin;
	private double qteObjet;
	private String type;
	private EuObjet euObjet;
	private EuBesoin euBesoin;

	public EuConcerner() {
	}


	@Id
	@Column(name="id_besoin", unique=true, nullable=false)
	public double getIdBesoin() {
		return this.idBesoin;
	}

	public void setIdBesoin(double idBesoin) {
		this.idBesoin = idBesoin;
	}


	@Column(name="qte_objet", nullable=false)
	public double getQteObjet() {
		return this.qteObjet;
	}

	public void setQteObjet(double qteObjet) {
		this.qteObjet = qteObjet;
	}


	@Column(length=60)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}


	//bi-directional many-to-one association to EuObjet
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_objet", nullable=false)
	public EuObjet getEuObjet() {
		return this.euObjet;
	}

	public void setEuObjet(EuObjet euObjet) {
		this.euObjet = euObjet;
	}


	//bi-directional one-to-one association to EuBesoin
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_besoin", nullable=false, insertable=false, updatable=false)
	public EuBesoin getEuBesoin() {
		return this.euBesoin;
	}

	public void setEuBesoin(EuBesoin euBesoin) {
		this.euBesoin = euBesoin;
	}

}
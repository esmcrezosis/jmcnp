package com.esmc.mcnp.model.cmfh;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_detail_domicilie_mf11000 database table.
 * 
 */
@Entity
@Table(name="eu_detail_domicilie_mf11000")
@NamedQuery(name="EuDetailDomicilieMf11000.findAll", query="SELECT e FROM EuDetailDomicilieMf11000 e")
public class EuDetailDomicilieMf11000 implements Serializable {
	private static final long serialVersionUID = 1L;
	private EuDetailDomicilieMf11000PK id;
	private double mtDomiApport;
	private double nbRepartition;
	private double resteRepartition;
	private EuDetailMf11000 euDetailMf11000;
	private EuDomicilieMf11000 euDomicilieMf11000;

	public EuDetailDomicilieMf11000() {
	}


	@EmbeddedId
	public EuDetailDomicilieMf11000PK getId() {
		return this.id;
	}

	public void setId(EuDetailDomicilieMf11000PK id) {
		this.id = id;
	}


	@Column(name="mt_domi_apport")
	public double getMtDomiApport() {
		return this.mtDomiApport;
	}

	public void setMtDomiApport(double mtDomiApport) {
		this.mtDomiApport = mtDomiApport;
	}


	@Column(name="nb_repartition")
	public double getNbRepartition() {
		return this.nbRepartition;
	}

	public void setNbRepartition(double nbRepartition) {
		this.nbRepartition = nbRepartition;
	}


	@Column(name="reste_repartition")
	public double getResteRepartition() {
		return this.resteRepartition;
	}

	public void setResteRepartition(double resteRepartition) {
		this.resteRepartition = resteRepartition;
	}


	//bi-directional many-to-one association to EuDetailMf11000
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_mf11000", nullable=false, insertable=false, updatable=false)
	public EuDetailMf11000 getEuDetailMf11000() {
		return this.euDetailMf11000;
	}

	public void setEuDetailMf11000(EuDetailMf11000 euDetailMf11000) {
		this.euDetailMf11000 = euDetailMf11000;
	}


	//bi-directional many-to-one association to EuDomicilieMf11000
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_domi", nullable=false, insertable=false, updatable=false)
	public EuDomicilieMf11000 getEuDomicilieMf11000() {
		return this.euDomicilieMf11000;
	}

	public void setEuDomicilieMf11000(EuDomicilieMf11000 euDomicilieMf11000) {
		this.euDomicilieMf11000 = euDomicilieMf11000;
	}

}
package com.esmc.mcnp.domain.entity.cmfh;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_detail_domicilie_mf107 database table.
 * 
 */
@Entity
@Table(name="eu_detail_domicilie_mf107")
@NamedQuery(name="EuDetailDomicilieMf107.findAll", query="SELECT e FROM EuDetailDomicilieMf107 e")
public class EuDetailDomicilieMf107 implements Serializable {
	private static final long serialVersionUID = 1L;
	private EuDetailDomicilieMf107PK id;
	private double mtDomiApport;
	private double nbRep;
	private double nbReste;
	private EuDomicilieMf107 euDomicilieMf107;

	public EuDetailDomicilieMf107() {
	}


	@EmbeddedId
	public EuDetailDomicilieMf107PK getId() {
		return this.id;
	}

	public void setId(EuDetailDomicilieMf107PK id) {
		this.id = id;
	}


	@Column(name="mt_domi_apport")
	public double getMtDomiApport() {
		return this.mtDomiApport;
	}

	public void setMtDomiApport(double mtDomiApport) {
		this.mtDomiApport = mtDomiApport;
	}


	@Column(name="nb_rep")
	public double getNbRep() {
		return this.nbRep;
	}

	public void setNbRep(double nbRep) {
		this.nbRep = nbRep;
	}


	@Column(name="nb_reste")
	public double getNbReste() {
		return this.nbReste;
	}

	public void setNbReste(double nbReste) {
		this.nbReste = nbReste;
	}


	//bi-directional many-to-one association to EuDomicilieMf107
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_dom", nullable=false, insertable=false, updatable=false)
	public EuDomicilieMf107 getEuDomicilieMf107() {
		return this.euDomicilieMf107;
	}

	public void setEuDomicilieMf107(EuDomicilieMf107 euDomicilieMf107) {
		this.euDomicilieMf107 = euDomicilieMf107;
	}

}
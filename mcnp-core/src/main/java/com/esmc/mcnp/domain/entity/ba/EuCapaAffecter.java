package com.esmc.mcnp.domain.entity.ba;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.bc.EuDomiciliation;
import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;


/**
 * The persistent class for the eu_capa_affecter database table.
 * 
 */
@Entity
@Table(name="eu_capa_affecter")
@NamedQuery(name="EuCapaAffecter.findAll", query="SELECT e FROM EuCapaAffecter e")
public class EuCapaAffecter implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idAffecter;
	private String codeCapa;
	private double dureeRenouvellement;
	private double montInvest;
	private double montRenouv;
	private double resteDuree;
	private String typeCredit;
	private EuCompteCredit euCompteCredit;
	private EuDomiciliation euDomiciliation;

	public EuCapaAffecter() {
	}


	@Id
	@Column(name="id_affecter", unique=true, nullable=false)
	public double getIdAffecter() {
		return this.idAffecter;
	}

	public void setIdAffecter(double idAffecter) {
		this.idAffecter = idAffecter;
	}


	@Column(name="code_capa", length=200)
	public String getCodeCapa() {
		return this.codeCapa;
	}

	public void setCodeCapa(String codeCapa) {
		this.codeCapa = codeCapa;
	}


	@Column(name="duree_renouvellement")
	public double getDureeRenouvellement() {
		return this.dureeRenouvellement;
	}

	public void setDureeRenouvellement(double dureeRenouvellement) {
		this.dureeRenouvellement = dureeRenouvellement;
	}


	@Column(name="mont_invest")
	public double getMontInvest() {
		return this.montInvest;
	}

	public void setMontInvest(double montInvest) {
		this.montInvest = montInvest;
	}


	@Column(name="mont_renouv")
	public double getMontRenouv() {
		return this.montRenouv;
	}

	public void setMontRenouv(double montRenouv) {
		this.montRenouv = montRenouv;
	}


	@Column(name="reste_duree")
	public double getResteDuree() {
		return this.resteDuree;
	}

	public void setResteDuree(double resteDuree) {
		this.resteDuree = resteDuree;
	}


	@Column(name="type_credit", length=60)
	public String getTypeCredit() {
		return this.typeCredit;
	}

	public void setTypeCredit(String typeCredit) {
		this.typeCredit = typeCredit;
	}


	//bi-directional many-to-one association to EuCompteCredit
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_credit")
	public EuCompteCredit getEuCompteCredit() {
		return this.euCompteCredit;
	}

	public void setEuCompteCredit(EuCompteCredit euCompteCredit) {
		this.euCompteCredit = euCompteCredit;
	}


	//bi-directional many-to-one association to EuDomiciliation
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_domicilier")
	public EuDomiciliation getEuDomiciliation() {
		return this.euDomiciliation;
	}

	public void setEuDomiciliation(EuDomiciliation euDomiciliation) {
		this.euDomiciliation = euDomiciliation;
	}

}
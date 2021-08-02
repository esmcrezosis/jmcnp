package com.esmc.mcnp.domain.entity.bc;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.esmc.mcnp.domain.entity.obps.EuTegc;

/**
 * The persistent class for the eu_prk database table.
 *
 */
@Entity
@Table(name = "eu_prk")
@NamedQuery(name = "EuPrk.findAll", query = "SELECT e FROM EuPrk e")
public class EuPrk implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idPrk;
	private double valeur;
	private EuTegc euTegc;
	private EuTypeCredit euTypeCredit;
	private String typeProduit;

	public EuPrk() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_prk", unique = true, nullable = false)
	public Integer getIdPrk() {
		return this.idPrk;
	}

	public void setIdPrk(Integer idPrk) {
		this.idPrk = idPrk;
	}

	public double getValeur() {
		return this.valeur;
	}

	public void setValeur(double valeur) {
		this.valeur = valeur;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_tegc")
	public EuTegc getEuTegc() {
		return euTegc;
	}

	public void setEuTegc(EuTegc euTegc) {
		this.euTegc = euTegc;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_type_credit")
	public EuTypeCredit getEuTypeCredit() {
		return euTypeCredit;
	}

	public void setEuTypeCredit(EuTypeCredit euTypeCredit) {
		this.euTypeCredit = euTypeCredit;
	}
	@Column(name = "type_produit")
	public String getTypeProduit() {
		return typeProduit;
	}

	public void setTypeProduit(String typeProduit) {
		this.typeProduit = typeProduit;
	}

}

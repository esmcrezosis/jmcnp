package com.esmc.mcnp.domain.entity.oi;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.cm.EuCategorieCompte;
import com.esmc.mcnp.domain.entity.cm.EuMembre;


/**
 * The persistent class for the eu_bnp_sqmax database table.
 *
 */
@Entity
@Table(name="eu_bnp_sqmax")
@NamedQuery(name="EuBnpSqmax.findAll", query="SELECT e FROM EuBnpSqmax e")
public class EuBnpSqmax implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idSqmax;
	private Long idCredit;
	private double montant;
	private EuMembre euMembre;
	private EuCategorieCompte euCategorieCompte;

	public EuBnpSqmax() {
	}


	@Id
	@Column(name="id_sqmax", unique=true, nullable=false)
	public Long getIdSqmax() {
		return this.idSqmax;
	}

	public void setIdSqmax(Long idSqmax) {
		this.idSqmax = idSqmax;
	}


	@Column(name="id_credit")
	public Long getIdCredit() {
		return this.idCredit;
	}

	public void setIdCredit(Long idCredit) {
		this.idCredit = idCredit;
	}


	public double getMontant() {
		return this.montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}


	//bi-directional many-to-one association to EuMembre
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre")
	public EuMembre getEuMembre() {
		return this.euMembre;
	}

	public void setEuMembre(EuMembre euMembre) {
		this.euMembre = euMembre;
	}


	//bi-directional many-to-one association to EuCategorieCompte
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_cat")
	public EuCategorieCompte getEuCategorieCompte() {
		return this.euCategorieCompte;
	}

	public void setEuCategorieCompte(EuCategorieCompte euCategorieCompte) {
		this.euCategorieCompte = euCategorieCompte;
	}

}
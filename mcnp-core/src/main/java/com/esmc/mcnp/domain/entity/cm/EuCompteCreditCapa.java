package com.esmc.mcnp.domain.entity.cm;

import org.hibernate.annotations.DynamicUpdate;

import com.esmc.mcnp.domain.entity.ba.EuCapa;
import com.esmc.mcnp.domain.entity.bc.EuBon;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the eu_compte_credit_capa database table.
 * 
 */
@Entity
@Table(name = "eu_compte_credit_capa")
@DynamicUpdate
@NamedQuery(name = "EuCompteCreditCapa.findAll", query = "SELECT e FROM EuCompteCreditCapa e")
public class EuCompteCreditCapa implements Serializable {
	private static final long serialVersionUID = 1L;
	private EuCompteCreditCapaPK id;
	private String codeProduit;
	private double montant;
	private EuCapa euCapa;
	private EuCompteCredit euCompteCredit;
	private EuBon euBon;

	public EuCompteCreditCapa() {
	}

	@EmbeddedId
	public EuCompteCreditCapaPK getId() {
		return this.id;
	}

	public void setId(EuCompteCreditCapaPK id) {
		this.id = id;
	}

	@Column(name = "code_produit", length = 60)
	public String getCodeProduit() {
		return this.codeProduit;
	}

	public void setCodeProduit(String codeProduit) {
		this.codeProduit = codeProduit;
	}

	public double getMontant() {
		return this.montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	// bi-directional many-to-one association to EuCapa
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_capa", nullable = false, insertable = false, updatable = false)
	public EuCapa getEuCapa() {
		return this.euCapa;
	}

	public void setEuCapa(EuCapa euCapa) {
		this.euCapa = euCapa;
	}

	// bi-directional many-to-one association to EuCompteCredit
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_credit", nullable = false, insertable = false, updatable = false)
	public EuCompteCredit getEuCompteCredit() {
		return this.euCompteCredit;
	}

	public void setEuCompteCredit(EuCompteCredit euCompteCredit) {
		this.euCompteCredit = euCompteCredit;
	}

	// bi-directional many-to-one association to EuCompteCredit
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bon_id")
	public EuBon getEuBon() {
		return euBon;
	}

	public void setEuBon(EuBon euBon) {
		this.euBon = euBon;
	}

}
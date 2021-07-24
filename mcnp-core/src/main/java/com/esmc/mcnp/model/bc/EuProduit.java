package com.esmc.mcnp.model.bc;

import com.esmc.mcnp.model.cm.EuCategorieCompte;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.mprg.EuKrr;
import com.esmc.mcnp.model.obps.EuCreditConsommer;
import com.esmc.mcnp.model.acteur.EuDepotVente;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the eu_produit database table.
 * 
 */
@Entity
@Table(name="eu_produit")
@NamedQuery(name="EuProduit.findAll", query="SELECT e FROM EuProduit e")
public class EuProduit implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeProduit;
	private String descriptionProduit;
	private String libelleProduit;
	private String typeProduit;
	private List<EuCompteCredit> euCompteCredits;
	private List<EuCreditConsommer> euCreditConsommers;
	private List<EuDepotVente> euDepotVentes;
	private List<EuKrr> euKrrs;
	private EuCategorieCompte euCategorieCompte;

	public EuProduit() {
	}

	/**
	 * @param codeProduit
	 */
	public EuProduit(String codeProduit) {
		this.codeProduit = codeProduit;
	}

	@Id
	@Column(name="code_produit", unique=true, nullable=false, length=60)
	public String getCodeProduit() {
		return this.codeProduit;
	}

	public void setCodeProduit(String codeProduit) {
		this.codeProduit = codeProduit;
	}


	@Column(name="description_produit", length=255)
	public String getDescriptionProduit() {
		return this.descriptionProduit;
	}

	public void setDescriptionProduit(String descriptionProduit) {
		this.descriptionProduit = descriptionProduit;
	}


	@Column(name="libelle_produit", nullable=false, length=200)
	public String getLibelleProduit() {
		return this.libelleProduit;
	}

	public void setLibelleProduit(String libelleProduit) {
		this.libelleProduit = libelleProduit;
	}


	@Column(name="type_produit", length=20)
	public String getTypeProduit() {
		return this.typeProduit;
	}

	public void setTypeProduit(String typeProduit) {
		this.typeProduit = typeProduit;
	}

	//bi-directional many-to-one association to EuCategorieCompte
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_categorie", nullable=false)
	public EuCategorieCompte getEuCategorieCompte() {
		return this.euCategorieCompte;
	}

	public void setEuCategorieCompte(EuCategorieCompte euCategorieCompte) {
		this.euCategorieCompte = euCategorieCompte;
	}

	//bi-directional many-to-one association to EuCompteCredit
	@JsonIgnore
	@OneToMany(mappedBy="euProduit")
	public List<EuCompteCredit> getEuCompteCredits() {
		return this.euCompteCredits;
	}

	public void setEuCompteCredits(List<EuCompteCredit> euCompteCredits) {
		this.euCompteCredits = euCompteCredits;
	}

	public EuCompteCredit addEuCompteCredit(EuCompteCredit euCompteCredit) {
		getEuCompteCredits().add(euCompteCredit);
		euCompteCredit.setEuProduit(this);

		return euCompteCredit;
	}

	public EuCompteCredit removeEuCompteCredit(EuCompteCredit euCompteCredit) {
		getEuCompteCredits().remove(euCompteCredit);
		euCompteCredit.setEuProduit(null);

		return euCompteCredit;
	}

	//bi-directional many-to-one association to EuCreditConsommer
	@JsonIgnore
	@OneToMany(mappedBy="euProduit")
	public List<EuCreditConsommer> getEuCreditConsommers() {
		return this.euCreditConsommers;
	}

	public void setEuCreditConsommers(List<EuCreditConsommer> euCreditConsommers) {
		this.euCreditConsommers = euCreditConsommers;
	}

	public EuCreditConsommer addEuCreditConsommer(EuCreditConsommer euCreditConsommer) {
		getEuCreditConsommers().add(euCreditConsommer);
		euCreditConsommer.setEuProduit(this);

		return euCreditConsommer;
	}

	public EuCreditConsommer removeEuCreditConsommer(EuCreditConsommer euCreditConsommer) {
		getEuCreditConsommers().remove(euCreditConsommer);
		euCreditConsommer.setEuProduit(null);

		return euCreditConsommer;
	}


	//bi-directional many-to-one association to EuDepotVente
	@JsonIgnore
	@OneToMany(mappedBy="euProduit")
	public List<EuDepotVente> getEuDepotVentes() {
		return this.euDepotVentes;
	}

	public void setEuDepotVentes(List<EuDepotVente> euDepotVentes) {
		this.euDepotVentes = euDepotVentes;
	}

	public EuDepotVente addEuDepotVente(EuDepotVente euDepotVente) {
		getEuDepotVentes().add(euDepotVente);
		euDepotVente.setEuProduit(this);

		return euDepotVente;
	}

	public EuDepotVente removeEuDepotVente(EuDepotVente euDepotVente) {
		getEuDepotVentes().remove(euDepotVente);
		euDepotVente.setEuProduit(null);

		return euDepotVente;
	}


	//bi-directional many-to-one association to EuKrr
	@JsonIgnore
	@OneToMany(mappedBy="euProduit")
	public List<EuKrr> getEuKrrs() {
		return this.euKrrs;
	}

	public void setEuKrrs(List<EuKrr> euKrrs) {
		this.euKrrs = euKrrs;
	}

	public EuKrr addEuKrr(EuKrr euKrr) {
		getEuKrrs().add(euKrr);
		euKrr.setEuProduit(this);

		return euKrr;
	}

	public EuKrr removeEuKrr(EuKrr euKrr) {
		getEuKrrs().remove(euKrr);
		euKrr.setEuProduit(null);

		return euKrr;
	}

}
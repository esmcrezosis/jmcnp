package com.esmc.mcnp.model.cm;

import com.esmc.mcnp.model.oi.EuBnpSqmax;
import com.esmc.mcnp.model.others.EuOperation;
import com.esmc.mcnp.model.bc.EuProduit;
import com.esmc.mcnp.model.ksu.EuCartes;
import com.esmc.mcnp.model.obps.EuGcp;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the eu_categorie_compte database table.
 *
 */
@Entity
@Table(name = "eu_categorie_compte")
@NamedQuery(name = "EuCategorieCompte.findAll", query = "SELECT e FROM EuCategorieCompte e")
public class EuCategorieCompte implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeCat;
	private String codeTypeCompte;
	private String descCat;
	private String libCat;
	private String typeMembre;
	private List<EuBnpSqmax> euBnpSqmaxs;
	private List<EuCartes> euCartes;
	private List<EuCompte> euComptes;
	private List<EuGcp> euGcps;
	private List<EuOperation> euOperations;
	private List<EuProduit> euProduits;

	public EuCategorieCompte() {
	}

	public EuCategorieCompte(String codeCat) {
		this.codeCat = codeCat;
	}

	public EuCategorieCompte(String codeCat, String libCat, String codeTypeCompte) {
		this.codeCat = codeCat;
		this.codeTypeCompte = codeTypeCompte;
	}

	public EuCategorieCompte(String codeCat, String libCat) {
		this.codeCat = codeCat;
		this.libCat = libCat;
	}

	@Id
	@Column(name = "code_cat", unique = true, nullable = false, length = 40)
	public String getCodeCat() {
		return codeCat;
	}

	public void setCodeCat(String codeCat) {
		this.codeCat = codeCat;
	}

	@Column(name = "code_type_compte", length = 20)
	public String getCodeTypeCompte() {
		return codeTypeCompte;
	}

	public void setCodeTypeCompte(String codeTypeCompte) {
		this.codeTypeCompte = codeTypeCompte;
	}

	@Column(name = "desc_cat", length = 150)
	public String getDescCat() {
		return descCat;
	}

	public void setDescCat(String descCat) {
		this.descCat = descCat;
	}

	@Column(name = "lib_cat", length = 150)
	public String getLibCat() {
		return libCat;
	}

	public void setLibCat(String libCat) {
		this.libCat = libCat;
	}

	@Column(name = "type_membre", length = 20)
	public String getTypeMembre() {
		return typeMembre;
	}

	public void setTypeMembre(String typeMembre) {
		this.typeMembre = typeMembre;
	}

	// bi-directional many-to-one association to EuBnpSqmax
	@JsonIgnore
	@OneToMany(mappedBy = "euCategorieCompte")
	public List<EuBnpSqmax> getEuBnpSqmaxs() {
		return euBnpSqmaxs;
	}

	public void setEuBnpSqmaxs(List<EuBnpSqmax> euBnpSqmaxs) {
		this.euBnpSqmaxs = euBnpSqmaxs;
	}

	public EuBnpSqmax addEuBnpSqmax(EuBnpSqmax euBnpSqmax) {
		getEuBnpSqmaxs().add(euBnpSqmax);
		euBnpSqmax.setEuCategorieCompte(this);

		return euBnpSqmax;
	}

	public EuBnpSqmax removeEuBnpSqmax(EuBnpSqmax euBnpSqmax) {
		getEuBnpSqmaxs().remove(euBnpSqmax);
		euBnpSqmax.setEuCategorieCompte(null);

		return euBnpSqmax;
	}

	// bi-directional many-to-one association to EuCartes
	@JsonIgnore
	@OneToMany(mappedBy = "euCategorieCompte")
	public List<EuCartes> getEuCartes() {
		return euCartes;
	}

	public void setEuCartes(List<EuCartes> euCartes) {
		this.euCartes = euCartes;
	}

	public EuCartes addEuCarte(EuCartes euCarte) {
		getEuCartes().add(euCarte);
		euCarte.setEuCategorieCompte(this);

		return euCarte;
	}

	public EuCartes removeEuCarte(EuCartes euCarte) {
		getEuCartes().remove(euCarte);
		euCarte.setEuCategorieCompte(null);

		return euCarte;
	}

	// bi-directional many-to-one association to EuCompte
	@JsonIgnore
	@OneToMany(mappedBy = "euCategorieCompte")
	public List<EuCompte> getEuComptes() {
		return euComptes;
	}

	public void setEuComptes(List<EuCompte> euComptes) {
		this.euComptes = euComptes;
	}

	public EuCompte addEuCompte(EuCompte euCompte) {
		getEuComptes().add(euCompte);
		euCompte.setEuCategorieCompte(this);

		return euCompte;
	}

	public EuCompte removeEuCompte(EuCompte euCompte) {
		getEuComptes().remove(euCompte);
		euCompte.setEuCategorieCompte(null);

		return euCompte;
	}

	// bi-directional many-to-one association to EuGcp
	@JsonIgnore
	@OneToMany(mappedBy = "euCategorieCompte")
	public List<EuGcp> getEuGcps() {
		return euGcps;
	}

	public void setEuGcps(List<EuGcp> euGcps) {
		this.euGcps = euGcps;
	}

	public EuGcp addEuGcp(EuGcp euGcp) {
		getEuGcps().add(euGcp);
		euGcp.setEuCategorieCompte(this);

		return euGcp;
	}

	public EuGcp removeEuGcp(EuGcp euGcp) {
		getEuGcps().remove(euGcp);
		euGcp.setEuCategorieCompte(null);

		return euGcp;
	}

	// bi-directional many-to-one association to EuOperation
	@JsonIgnore
	@OneToMany(mappedBy = "euCategorieCompte")
	public List<EuOperation> getEuOperations() {
		return euOperations;
	}

	public void setEuOperations(List<EuOperation> euOperations) {
		this.euOperations = euOperations;
	}

	public EuOperation addEuOperation(EuOperation euOperation) {
		getEuOperations().add(euOperation);
		euOperation.setEuCategorieCompte(this);

		return euOperation;
	}

	public EuOperation removeEuOperation(EuOperation euOperation) {
		getEuOperations().remove(euOperation);
		euOperation.setEuCategorieCompte(null);

		return euOperation;
	}

	// bi-directional many-to-one association to EuProduit
	@JsonIgnore
	@OneToMany(mappedBy = "euCategorieCompte")
	public List<EuProduit> getEuProduits() {
		return euProduits;
	}

	public void setEuProduits(List<EuProduit> euProduits) {
		this.euProduits = euProduits;
	}

	public EuProduit addEuProduit(EuProduit euProduit) {
		getEuProduits().add(euProduit);
		euProduit.setEuCategorieCompte(this);

		return euProduit;
	}

	public EuProduit removeEuProduit(EuProduit euProduit) {
		getEuProduits().remove(euProduit);
		euProduit.setEuCategorieCompte(null);

		return euProduit;
	}

}
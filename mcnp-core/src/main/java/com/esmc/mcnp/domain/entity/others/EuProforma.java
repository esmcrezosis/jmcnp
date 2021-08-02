package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.domain.entity.smcipn.EuBudgetFacture;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_proforma database table.
 * 
 */
@Entity
@Table(name="eu_proforma")
@NamedQuery(name="EuProforma.findAll", query="SELECT e FROM EuProforma e")
public class EuProforma implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeProforma;
	private Date dateLivre;
	private Date datePaie;
	private Date dateProforma;
	private Date delaiValid;
	private String lieuLivre;
	private double montantHt;
	private String typeProforma;
	private EuBudgetFacture euBudgetFacture;
	private List<EuPorter> euPorters;
	private EuUtilisateur euUtilisateur;
	private EuMembreMorale euMembreMorale;
	private EuTaxe euTaxe;
	private EuBesoin euBesoin;

	public EuProforma() {
	}


	@Id
	@Column(name="code_proforma", unique=true, nullable=false, length=100)
	public String getCodeProforma() {
		return this.codeProforma;
	}

	public void setCodeProforma(String codeProforma) {
		this.codeProforma = codeProforma;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_livre")
	public Date getDateLivre() {
		return this.dateLivre;
	}

	public void setDateLivre(Date dateLivre) {
		this.dateLivre = dateLivre;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_paie")
	public Date getDatePaie() {
		return this.datePaie;
	}

	public void setDatePaie(Date datePaie) {
		this.datePaie = datePaie;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_proforma")
	public Date getDateProforma() {
		return this.dateProforma;
	}

	public void setDateProforma(Date dateProforma) {
		this.dateProforma = dateProforma;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="delai_valid")
	public Date getDelaiValid() {
		return this.delaiValid;
	}

	public void setDelaiValid(Date delaiValid) {
		this.delaiValid = delaiValid;
	}


	@Column(name="lieu_livre", length=100)
	public String getLieuLivre() {
		return this.lieuLivre;
	}

	public void setLieuLivre(String lieuLivre) {
		this.lieuLivre = lieuLivre;
	}


	@Column(name="montant_ht")
	public double getMontantHt() {
		return this.montantHt;
	}

	public void setMontantHt(double montantHt) {
		this.montantHt = montantHt;
	}


	@Column(name="type_proforma", length=100)
	public String getTypeProforma() {
		return this.typeProforma;
	}

	public void setTypeProforma(String typeProforma) {
		this.typeProforma = typeProforma;
	}


	//bi-directional one-to-one association to EuBudgetFacture
	@OneToOne(mappedBy="euProforma", fetch=FetchType.LAZY)
	public EuBudgetFacture getEuBudgetFacture() {
		return this.euBudgetFacture;
	}

	public void setEuBudgetFacture(EuBudgetFacture euBudgetFacture) {
		this.euBudgetFacture = euBudgetFacture;
	}


	//bi-directional many-to-one association to EuPorter
	@OneToMany(mappedBy="euProforma")
	public List<EuPorter> getEuPorters() {
		return this.euPorters;
	}

	public void setEuPorters(List<EuPorter> euPorters) {
		this.euPorters = euPorters;
	}

	public EuPorter addEuPorter(EuPorter euPorter) {
		getEuPorters().add(euPorter);
		euPorter.setEuProforma(this);

		return euPorter;
	}

	public EuPorter removeEuPorter(EuPorter euPorter) {
		getEuPorters().remove(euPorter);
		euPorter.setEuProforma(null);

		return euPorter;
	}


	//bi-directional many-to-one association to EuUtilisateur
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_utilisateur")
	public EuUtilisateur getEuUtilisateur() {
		return this.euUtilisateur;
	}

	public void setEuUtilisateur(EuUtilisateur euUtilisateur) {
		this.euUtilisateur = euUtilisateur;
	}


	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre_fournisseur")
	public EuMembreMorale getEuMembreMorale() {
		return this.euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}


	//bi-directional many-to-one association to EuTaxe
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_taxe")
	public EuTaxe getEuTaxe() {
		return this.euTaxe;
	}

	public void setEuTaxe(EuTaxe euTaxe) {
		this.euTaxe = euTaxe;
	}


	//bi-directional many-to-one association to EuBesoin
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_besoin")
	public EuBesoin getEuBesoin() {
		return this.euBesoin;
	}

	public void setEuBesoin(EuBesoin euBesoin) {
		this.euBesoin = euBesoin;
	}

}
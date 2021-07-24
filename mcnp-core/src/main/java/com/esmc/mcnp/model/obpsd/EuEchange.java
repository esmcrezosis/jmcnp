package com.esmc.mcnp.model.obpsd;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.security.EuUtilisateur;

import java.util.Date;


/**
 * The persistent class for the eu_echange database table.
 *
 */
@Entity
@Table(name="eu_echange")
@NamedQuery(name="EuEchange.findAll", query="SELECT e FROM EuEchange e")
public class EuEchange implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idEchange;
	private double agio;
	private String catEchange;
	private String codeCompteObt;
	private String codeProduit;
	private Integer compenser;
	private Date dateEchange;
	private Date dateReglement;
	private Long idCredit;
	private double montant;
	private double montantEchange;
	private Integer regler;
	private String typeEchange;
	private EuCompte euCompte;
	private EuUtilisateur euUtilisateur;
	private EuMembre euMembre;
	private EuMembreMorale euMembreMorale;

	public EuEchange() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_echange", unique=true, nullable=false)
	public Long getIdEchange() {
		return this.idEchange;
	}

	public void setIdEchange(Long idEchange) {
		this.idEchange = idEchange;
	}


	@Column(nullable=false)
	public double getAgio() {
		return this.agio;
	}

	public void setAgio(double agio) {
		this.agio = agio;
	}


	@Column(name="cat_echange", length=180)
	public String getCatEchange() {
		return this.catEchange;
	}

	public void setCatEchange(String catEchange) {
		this.catEchange = catEchange;
	}


	@Column(name="code_compte_obt", length=180)
	public String getCodeCompteObt() {
		return this.codeCompteObt;
	}

	public void setCodeCompteObt(String codeCompteObt) {
		this.codeCompteObt = codeCompteObt;
	}


	@Column(name="code_produit", length=60)
	public String getCodeProduit() {
		return this.codeProduit;
	}

	public void setCodeProduit(String codeProduit) {
		this.codeProduit = codeProduit;
	}


	@Column(nullable=false)
	public Integer getCompenser() {
		return this.compenser;
	}

	public void setCompenser(Integer compenser) {
		this.compenser = compenser;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_echange")
	public Date getDateEchange() {
		return this.dateEchange;
	}

	public void setDateEchange(Date dateEchange) {
		this.dateEchange = dateEchange;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_reglement")
	public Date getDateReglement() {
		return this.dateReglement;
	}

	public void setDateReglement(Date dateReglement) {
		this.dateReglement = dateReglement;
	}


	@Column(name="id_credit")
	public Long getIdCredit() {
		return this.idCredit;
	}

	public void setIdCredit(Long idCredit) {
		this.idCredit = idCredit;
	}


	@Column(nullable=false)
	public double getMontant() {
		return this.montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}


	@Column(name="montant_echange", nullable=false)
	public double getMontantEchange() {
		return this.montantEchange;
	}

	public void setMontantEchange(double montantEchange) {
		this.montantEchange = montantEchange;
	}


	public Integer getRegler() {
		return this.regler;
	}

	public void setRegler(Integer regler) {
		this.regler = regler;
	}


	@Column(name="type_echange", length=180)
	public String getTypeEchange() {
		return this.typeEchange;
	}

	public void setTypeEchange(String typeEchange) {
		this.typeEchange = typeEchange;
	}


	//bi-directional many-to-one association to EuCompte
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_compte_ech")
	public EuCompte getEuCompte() {
		return this.euCompte;
	}

	public void setEuCompte(EuCompte euCompte) {
		this.euCompte = euCompte;
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


	//bi-directional many-to-one association to EuMembre
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre")
	public EuMembre getEuMembre() {
		return this.euMembre;
	}

	public void setEuMembre(EuMembre euMembre) {
		this.euMembre = euMembre;
	}


	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne
	@JoinColumn(name="code_membre_morale")
	public EuMembreMorale getEuMembreMorale() {
		return this.euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}

}
package com.esmc.mcnp.model.smcipn;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.pc.EuRapprochement;
import com.esmc.mcnp.model.security.EuUtilisateur;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_smcipnp database table.
 *
 */
@Entity
@Table(name="eu_smcipnp")
@NamedQuery(name="EuSmcipnp.findAll", query="SELECT e FROM EuSmcipnp e")
public class EuSmcipnp implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeSmcipnp;
	private String codeActeur;
	private Date dateAlloc;
	private Date dateSmcipnp;
	private String descSmcipnp;
	private Integer domicilier;
	private Integer etatSmcipnp;
	private Date heureSmcipnp;
	private String libSmcipnp;
	private double montantSmcipnp;
	private Integer rembourser;
	private String sourceSmcipnp;
	private Integer transferer;
	private List<EuRapprochement> euRapprochements;
	private EuUtilisateur euUtilisateur;
	private EuMembreMorale euMembreMorale;

	public EuSmcipnp() {
	}


	@Id
	@Column(name="code_smcipnp", unique=true, nullable=false, length=200)
	public String getCodeSmcipnp() {
		return this.codeSmcipnp;
	}

	public void setCodeSmcipnp(String codeSmcipnp) {
		this.codeSmcipnp = codeSmcipnp;
	}


	@Column(name="code_acteur", length=100)
	public String getCodeActeur() {
		return this.codeActeur;
	}

	public void setCodeActeur(String codeActeur) {
		this.codeActeur = codeActeur;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_alloc")
	public Date getDateAlloc() {
		return this.dateAlloc;
	}

	public void setDateAlloc(Date dateAlloc) {
		this.dateAlloc = dateAlloc;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_smcipnp")
	public Date getDateSmcipnp() {
		return this.dateSmcipnp;
	}

	public void setDateSmcipnp(Date dateSmcipnp) {
		this.dateSmcipnp = dateSmcipnp;
	}


	@Column(name="desc_smcipnp", length=150)
	public String getDescSmcipnp() {
		return this.descSmcipnp;
	}

	public void setDescSmcipnp(String descSmcipnp) {
		this.descSmcipnp = descSmcipnp;
	}


	public Integer getDomicilier() {
		return this.domicilier;
	}

	public void setDomicilier(Integer domicilier) {
		this.domicilier = domicilier;
	}


	@Column(name="etat_smcipnp")
	public Integer getEtatSmcipnp() {
		return this.etatSmcipnp;
	}

	public void setEtatSmcipnp(Integer etatSmcipnp) {
		this.etatSmcipnp = etatSmcipnp;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="heure_smcipnp")
	public Date getHeureSmcipnp() {
		return this.heureSmcipnp;
	}

	public void setHeureSmcipnp(Date heureSmcipnp) {
		this.heureSmcipnp = heureSmcipnp;
	}


	@Column(name="lib_smcipnp", length=150)
	public String getLibSmcipnp() {
		return this.libSmcipnp;
	}

	public void setLibSmcipnp(String libSmcipnp) {
		this.libSmcipnp = libSmcipnp;
	}


	@Column(name="montant_smcipnp")
	public double getMontantSmcipnp() {
		return this.montantSmcipnp;
	}

	public void setMontantSmcipnp(double montantSmcipnp) {
		this.montantSmcipnp = montantSmcipnp;
	}


	public Integer getRembourser() {
		return this.rembourser;
	}

	public void setRembourser(Integer rembourser) {
		this.rembourser = rembourser;
	}


	@Column(name="source_smcipnp", length=100)
	public String getSourceSmcipnp() {
		return this.sourceSmcipnp;
	}

	public void setSourceSmcipnp(String sourceSmcipnp) {
		this.sourceSmcipnp = sourceSmcipnp;
	}


	public Integer getTransferer() {
		return this.transferer;
	}

	public void setTransferer(Integer transferer) {
		this.transferer = transferer;
	}


	//bi-directional many-to-one association to EuRapprochement
	@OneToMany(mappedBy="euSmcipnp")
	public List<EuRapprochement> getEuRapprochements() {
		return this.euRapprochements;
	}

	public void setEuRapprochements(List<EuRapprochement> euRapprochements) {
		this.euRapprochements = euRapprochements;
	}

	public EuRapprochement addEuRapprochement(EuRapprochement euRapprochement) {
		getEuRapprochements().add(euRapprochement);
		euRapprochement.setEuSmcipnp(this);

		return euRapprochement;
	}

	public EuRapprochement removeEuRapprochement(EuRapprochement euRapprochement) {
		getEuRapprochements().remove(euRapprochement);
		euRapprochement.setEuSmcipnp(null);

		return euRapprochement;
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
	@JoinColumn(name="code_membre")
	public EuMembreMorale getEuMembreMorale() {
		return this.euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}

}
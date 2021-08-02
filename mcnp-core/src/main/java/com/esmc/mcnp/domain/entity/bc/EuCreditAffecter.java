package com.esmc.mcnp.domain.entity.bc;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;

import java.util.Date;

/**
 * The persistent class for the eu_credit_affecter database table.
 *
 */
@Entity
@Table(name = "eu_credit_affecter")
@NamedQuery(name = "EuCreditAffecter.findAll", query = "SELECT e FROM EuCreditAffecter e")
public class EuCreditAffecter implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idCreditAffecter;
	private String codeMembre;
	private String codeProduit;
	private Date dateAffectation;
	private Integer dureeAffectation;
	private Long idCredit;
	private Long idUtilisateur;
	private double montCreditAffecter;
	private EuMembreMorale euMembreMorale;

	public EuCreditAffecter() {
	}

	@Id
	@Column(name = "id_credit_affecter", unique = true, nullable = false)
	public double getIdCreditAffecter() {
		return this.idCreditAffecter;
	}

	public void setIdCreditAffecter(double idCreditAffecter) {
		this.idCreditAffecter = idCreditAffecter;
	}

	@Column(name = "code_membre", length = 180)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Column(name = "code_produit", length = 60)
	public String getCodeProduit() {
		return this.codeProduit;
	}

	public void setCodeProduit(String codeProduit) {
		this.codeProduit = codeProduit;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_affectation")
	public Date getDateAffectation() {
		return this.dateAffectation;
	}

	public void setDateAffectation(Date dateAffectation) {
		this.dateAffectation = dateAffectation;
	}

	@Column(name = "duree_affectation")
	public Integer getDureeAffectation() {
		return this.dureeAffectation;
	}

	public void setDureeAffectation(Integer dureeAffectation) {
		this.dureeAffectation = dureeAffectation;
	}

	@Column(name = "id_credit")
	public Long getIdCredit() {
		return this.idCredit;
	}

	public void setIdCredit(Long idCredit) {
		this.idCredit = idCredit;
	}

	@Column(name = "id_utilisateur")
	public Long getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	@Column(name = "mont_credit_affecter")
	public double getMontCreditAffecter() {
		return this.montCreditAffecter;
	}

	public void setMontCreditAffecter(double montCreditAffecter) {
		this.montCreditAffecter = montCreditAffecter;
	}

	// bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_membre_dist")
	public EuMembreMorale getEuMembreMorale() {
		return this.euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.domain.entity.pc;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.esmc.mcnp.domain.entity.smcipn.EuSmcipnpwi;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "eu_charge_paye")
@NamedQueries({ @NamedQuery(name = "EuChargePaye.findAll", query = "SELECT e FROM EuChargePaye e"),
		@NamedQuery(name = "EuChargePaye.findByIdChargePaye", query = "SELECT e FROM EuChargePaye e WHERE e.idChargePaye = :idChargePaye"),
		@NamedQuery(name = "EuChargePaye.findByDateCharge", query = "SELECT e FROM EuChargePaye e WHERE e.dateCharge = :dateCharge"),
		@NamedQuery(name = "EuChargePaye.findByLibelleCharge", query = "SELECT e FROM EuChargePaye e WHERE e.libelleCharge = :libelleCharge"),
		@NamedQuery(name = "EuChargePaye.findByMontantCharge", query = "SELECT e FROM EuChargePaye e WHERE e.montantCharge = :montantCharge"),
		@NamedQuery(name = "EuChargePaye.findByTypeDoc", query = "SELECT e FROM EuChargePaye e WHERE e.typeDoc = :typeDoc"),
		@NamedQuery(name = "EuChargePaye.findByNumDoc", query = "SELECT e FROM EuChargePaye e WHERE e.numDoc = :numDoc"),
		@NamedQuery(name = "EuChargePaye.findByCodeMembreCreancier", query = "SELECT e FROM EuChargePaye e WHERE e.codeMembreCreancier = :codeMembreCreancier"),
		@NamedQuery(name = "EuChargePaye.findByCodeMembreDebiteur", query = "SELECT e FROM EuChargePaye e WHERE e.codeMembreDebiteur = :codeMembreDebiteur"),
		@NamedQuery(name = "EuChargePaye.findByOrigineCharge", query = "SELECT e FROM EuChargePaye e WHERE e.origineCharge = :origineCharge") })
public class EuChargePaye implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idChargePaye;
	private Date dateCharge;
	private String libelleCharge;
	private double montantCharge;
	private String typeDoc;
	private String numDoc;
	private String codeMembreCreancier;
	private String codeMembreDebiteur;
	private String origineCharge;
	private EuSmcipnpwi euSmcipnpwi;
	private EuCharge euCharge;

	public EuChargePaye() {
	}

	public EuChargePaye(Long idChargePaye) {
		this.idChargePaye = idChargePaye;
	}

	public EuChargePaye(Long idChargePaye, String libelleCharge, double montantCharge, String typeDoc, String numDoc,
			String codeMembreCreancier, String codeMembreDebiteur, String origineCharge) {
		this.idChargePaye = idChargePaye;
		this.libelleCharge = libelleCharge;
		this.montantCharge = montantCharge;
		this.typeDoc = typeDoc;
		this.numDoc = numDoc;
		this.codeMembreCreancier = codeMembreCreancier;
		this.codeMembreDebiteur = codeMembreDebiteur;
		this.origineCharge = origineCharge;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_charge_paye")
	public Long getIdChargePaye() {
		return idChargePaye;
	}

	public void setIdChargePaye(Long idChargePaye) {
		this.idChargePaye = idChargePaye;
	}

	@Column(name = "date_charge")
	@Temporal(TemporalType.DATE)
	public Date getDateCharge() {
		return dateCharge;
	}

	public void setDateCharge(Date dateCharge) {
		this.dateCharge = dateCharge;
	}

	@Size(min = 1, max = 250)
	@Column(name = "libelle_charge")
	public String getLibelleCharge() {
		return libelleCharge;
	}

	public void setLibelleCharge(String libelleCharge) {
		this.libelleCharge = libelleCharge;
	}

	@NotNull
	@Column(name = "montant_charge")
	public double getMontantCharge() {
		return montantCharge;
	}

	public void setMontantCharge(double montantCharge) {
		this.montantCharge = montantCharge;
	}

	@Size(min = 1, max = 50)
	@Column(name = "type_doc")
	public String getTypeDoc() {
		return typeDoc;
	}

	public void setTypeDoc(String typeDoc) {
		this.typeDoc = typeDoc;
	}

	@Size(max = 15)
	@Column(name = "num_doc")
	public String getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}

	@NotNull
	@Size(min = 1, max = 25)
	@Column(name = "code_membre_creancier")
	public String getCodeMembreCreancier() {
		return codeMembreCreancier;
	}

	public void setCodeMembreCreancier(String codeMembreCreancier) {
		this.codeMembreCreancier = codeMembreCreancier;
	}

	@NotNull
	@Size(min = 1, max = 25)
	@Column(name = "code_membre_debiteur")
	public String getCodeMembreDebiteur() {
		return codeMembreDebiteur;
	}

	public void setCodeMembreDebiteur(String codeMembreDebiteur) {
		this.codeMembreDebiteur = codeMembreDebiteur;
	}

	@NotNull
	@Size(min = 1, max = 25)
	@Column(name = "origine_charge")
	public String getOrigineCharge() {
		return origineCharge;
	}

	public void setOrigineCharge(String origineCharge) {
		this.origineCharge = origineCharge;
	}

	@JoinColumn(name = "code_smcipn", referencedColumnName = "code_smcipn")
	@ManyToOne(optional = false)
	public EuSmcipnpwi getEuSmcipnpwi() {
		return euSmcipnpwi;
	}

	public void setEuSmcipnpwi(EuSmcipnpwi euSmcipnpwi) {
		this.euSmcipnpwi = euSmcipnpwi;
	}

	@JoinColumn(name = "id_charge", referencedColumnName = "id_charge")
	@ManyToOne(optional = false)
	public EuCharge getEuCharge() {
		return euCharge;
	}

	public void setEuCharge(EuCharge euCharge) {
		this.euCharge = euCharge;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idChargePaye != null ? idChargePaye.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof EuChargePaye)) {
			return false;
		}
		EuChargePaye other = (EuChargePaye) object;
		return !((this.idChargePaye == null && other.idChargePaye != null)
				|| (this.idChargePaye != null && !this.idChargePaye.equals(other.idChargePaye)));
	}

	@Override
	public String toString() {
		return "com.esmc.mcnp.model.pc.EuChargePaye[ idChargePaye=" + idChargePaye + " ]";
	}

}

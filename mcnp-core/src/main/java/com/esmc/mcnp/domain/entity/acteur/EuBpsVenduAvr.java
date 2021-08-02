package com.esmc.mcnp.model.acteur;

import com.esmc.mcnp.model.others.EuFormAvr;
import com.esmc.mcnp.model.smcipn.EuFormsBudgetNature;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the eu_bps_vendu_avr database table.
 * 
 */
@Entity
@Table(name="eu_bps_vendu_avr")
@NamedQuery(name="EuBpsVenduAvr.findAll", query="SELECT e FROM EuBpsVenduAvr e")
public class EuBpsVenduAvr implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idBpsVenduAchatVenteReciproque;
	private String nomBpsVendu;
	private List<EuFormAvr> euFormAvrs;
	private List<EuFormsBudgetNature> euFormsBudgetNatures;

	public EuBpsVenduAvr() {
	}


	@Id
	@Column(name="id_bps_vendu_achat_vente_reciproque")
	public int getIdBpsVenduAchatVenteReciproque() {
		return this.idBpsVenduAchatVenteReciproque;
	}

	public void setIdBpsVenduAchatVenteReciproque(int idBpsVenduAchatVenteReciproque) {
		this.idBpsVenduAchatVenteReciproque = idBpsVenduAchatVenteReciproque;
	}


	@Column(name="nom_bps_vendu")
	public String getNomBpsVendu() {
		return this.nomBpsVendu;
	}

	public void setNomBpsVendu(String nomBpsVendu) {
		this.nomBpsVendu = nomBpsVendu;
	}


	//bi-directional many-to-one association to EuFormAvr
	@OneToMany(mappedBy="euBpsVenduAvr")
	public List<EuFormAvr> getEuFormAvrs() {
		return this.euFormAvrs;
	}

	public void setEuFormAvrs(List<EuFormAvr> euFormAvrs) {
		this.euFormAvrs = euFormAvrs;
	}

	public EuFormAvr addEuFormAvr(EuFormAvr euFormAvr) {
		getEuFormAvrs().add(euFormAvr);
		euFormAvr.setEuBpsVenduAvr(this);

		return euFormAvr;
	}

	public EuFormAvr removeEuFormAvr(EuFormAvr euFormAvr) {
		getEuFormAvrs().remove(euFormAvr);
		euFormAvr.setEuBpsVenduAvr(null);

		return euFormAvr;
	}


	//bi-directional many-to-one association to EuFormsBudgetNature
	@OneToMany(mappedBy="euBpsVenduAvr")
	public List<EuFormsBudgetNature> getEuFormsBudgetNatures() {
		return this.euFormsBudgetNatures;
	}

	public void setEuFormsBudgetNatures(List<EuFormsBudgetNature> euFormsBudgetNatures) {
		this.euFormsBudgetNatures = euFormsBudgetNatures;
	}

	public EuFormsBudgetNature addEuFormsBudgetNature(EuFormsBudgetNature euFormsBudgetNature) {
		getEuFormsBudgetNatures().add(euFormsBudgetNature);
		euFormsBudgetNature.setEuBpsVenduAvr(this);

		return euFormsBudgetNature;
	}

	public EuFormsBudgetNature removeEuFormsBudgetNature(EuFormsBudgetNature euFormsBudgetNature) {
		getEuFormsBudgetNatures().remove(euFormsBudgetNature);
		euFormsBudgetNature.setEuBpsVenduAvr(null);

		return euFormsBudgetNature;
	}

}
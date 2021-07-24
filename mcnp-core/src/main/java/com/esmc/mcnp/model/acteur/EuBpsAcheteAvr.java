package com.esmc.mcnp.model.acteur;

import com.esmc.mcnp.model.others.EuFormAvr;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the eu_bps_achete_avr database table.
 * 
 */
@Entity
@Table(name="eu_bps_achete_avr")
@NamedQuery(name="EuBpsAcheteAvr.findAll", query="SELECT e FROM EuBpsAcheteAvr e")
public class EuBpsAcheteAvr implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idBpsAcheteAchatVenteReciproque;
	private String nomBpsAchete;
	private List<EuFormAvr> euFormAvrs;

	public EuBpsAcheteAvr() {
	}


	@Id
	@Column(name="id_bps_achete_achat_vente_reciproque")
	public int getIdBpsAcheteAchatVenteReciproque() {
		return this.idBpsAcheteAchatVenteReciproque;
	}

	public void setIdBpsAcheteAchatVenteReciproque(int idBpsAcheteAchatVenteReciproque) {
		this.idBpsAcheteAchatVenteReciproque = idBpsAcheteAchatVenteReciproque;
	}


	@Column(name="nom_bps_achete")
	public String getNomBpsAchete() {
		return this.nomBpsAchete;
	}

	public void setNomBpsAchete(String nomBpsAchete) {
		this.nomBpsAchete = nomBpsAchete;
	}


	//bi-directional many-to-one association to EuFormAvr
	@OneToMany(mappedBy="euBpsAcheteAvr")
	public List<EuFormAvr> getEuFormAvrs() {
		return this.euFormAvrs;
	}

	public void setEuFormAvrs(List<EuFormAvr> euFormAvrs) {
		this.euFormAvrs = euFormAvrs;
	}

	public EuFormAvr addEuFormAvr(EuFormAvr euFormAvr) {
		getEuFormAvrs().add(euFormAvr);
		euFormAvr.setEuBpsAcheteAvr(this);

		return euFormAvr;
	}

	public EuFormAvr removeEuFormAvr(EuFormAvr euFormAvr) {
		getEuFormAvrs().remove(euFormAvr);
		euFormAvr.setEuBpsAcheteAvr(null);

		return euFormAvr;
	}

}
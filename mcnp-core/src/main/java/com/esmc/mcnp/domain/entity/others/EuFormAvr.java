package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.acteur.EuBpsAcheteAvr;
import com.esmc.mcnp.domain.entity.acteur.EuBpsVenduAvr;


/**
 * The persistent class for the eu_form_avr database table.
 * 
 */
@Entity
@Table(name="eu_form_avr")
@NamedQuery(name="EuFormAvr.findAll", query="SELECT e FROM EuFormAvr e")
public class EuFormAvr implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String codeMembreAvr;
	private byte validationachatetventereciproque;
	private EuBpsAcheteAvr euBpsAcheteAvr;
	private EuBpsVenduAvr euBpsVenduAvr;

	public EuFormAvr() {
	}


	@Id
	@Column(name = "id_form_avr")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Column(name="code_membre_avr")
	public String getCodeMembreAvr() {
		return this.codeMembreAvr;
	}

	public void setCodeMembreAvr(String codeMembreAvr) {
		this.codeMembreAvr = codeMembreAvr;
	}


	public byte getValidationachatetventereciproque() {
		return this.validationachatetventereciproque;
	}

	public void setValidationachatetventereciproque(byte validationachatetventereciproque) {
		this.validationachatetventereciproque = validationachatetventereciproque;
	}


	//bi-directional many-to-one association to EuBpsAcheteAvr
	@ManyToOne
	@JoinColumn(name="id_bps_achete_achat_vente_reciproque")
	public EuBpsAcheteAvr getEuBpsAcheteAvr() {
		return this.euBpsAcheteAvr;
	}

	public void setEuBpsAcheteAvr(EuBpsAcheteAvr euBpsAcheteAvr) {
		this.euBpsAcheteAvr = euBpsAcheteAvr;
	}


	//bi-directional many-to-one association to EuBpsVenduAvr
	@ManyToOne
	@JoinColumn(name="id_bps_vendu_achat_vente_reciproque")
	public EuBpsVenduAvr getEuBpsVenduAvr() {
		return this.euBpsVenduAvr;
	}

	public void setEuBpsVenduAvr(EuBpsVenduAvr euBpsVenduAvr) {
		this.euBpsVenduAvr = euBpsVenduAvr;
	}

}
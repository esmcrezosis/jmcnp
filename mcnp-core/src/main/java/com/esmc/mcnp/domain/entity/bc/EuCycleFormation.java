package com.esmc.mcnp.domain.entity.bc;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuCycleFormation
 *
 */
@Entity
@Table(name = "eu_cycle_formation")
public class EuCycleFormation implements Serializable {

	private Integer id;
	private String codeCycleFormation;
	private int dureeCycleFormation;
	private int dureeAnneeFormation;
	private float tauxCycleFormation;
	private static final long serialVersionUID = 1L;

	public EuCycleFormation() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "code_cycle_formation")
	public String getCodeCycleFormation() {
		return this.codeCycleFormation;
	}

	public void setCodeCycleFormation(String codeCycleFormation) {
		this.codeCycleFormation = codeCycleFormation;
	}

	@Column(name = "duree_cycle_formation")
	public int getDureeCycleFormation() {
		return this.dureeCycleFormation;
	}

	public void setDureeCycleFormation(int dureeCycleFormation) {
		this.dureeCycleFormation = dureeCycleFormation;
	}

	@Column(name = "duree_annee_formation")
	public int getDureeAnneeFormation() {
		return dureeAnneeFormation;
	}

	public void setDureeAnneeFormation(int dureeAnneeFormation) {
		this.dureeAnneeFormation = dureeAnneeFormation;
	}

	@Column(name = "taux_cycle_formation")
	public float getTauxCycleFormation() {
		return this.tauxCycleFormation;
	}

	public void setTauxCycleFormation(float tauxCycleFormation) {
		this.tauxCycleFormation = tauxCycleFormation;
	}

}

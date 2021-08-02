package com.esmc.mcnp.domain.entity.org;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.acteur.EuGac;

/**
 * The persistent class for the eu_division_gac database table.
 * 
 */
@Entity
@Table(name = "eu_division_gac")
@NamedQuery(name = "EuDivisionGac.findAll", query = "SELECT e FROM EuDivisionGac e")
public class EuDivisionGac implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idDivisionGac;
	private String codeMembre;
	private String libelleDivision;
	private String nomDivision;
	private String typeDivision;
	private EuGac euGac;

	public EuDivisionGac() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_division_gac", unique = true, nullable = false)
	public double getIdDivisionGac() {
		return this.idDivisionGac;
	}

	public void setIdDivisionGac(double idDivisionGac) {
		this.idDivisionGac = idDivisionGac;
	}

	@Column(name = "code_membre", length = 16)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Column(name = "libelle_division", length = 200)
	public String getLibelleDivision() {
		return this.libelleDivision;
	}

	public void setLibelleDivision(String libelleDivision) {
		this.libelleDivision = libelleDivision;
	}

	@Column(name = "nom_division", length = 200)
	public String getNomDivision() {
		return this.nomDivision;
	}

	public void setNomDivision(String nomDivision) {
		this.nomDivision = nomDivision;
	}

	@Column(name = "type_division", length = 200)
	public String getTypeDivision() {
		return typeDivision;
	}

	public void setTypeDivision(String typeDivision) {
		this.typeDivision = typeDivision;
	}

	// bi-directional many-to-one association to EuTypeGac
	@ManyToOne
	@JoinColumn(name = "code_gac")
	public EuGac getEuGac() {
		return this.euGac;
	}

	public void setEuGac(EuGac euGac) {
		this.euGac = euGac;
	}

}
package com.esmc.mcnp.domain.entity.odd;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the eu_centrales database table.
 * 
 */
@Entity
@Table(name = "eu_centrales")
@NamedQuery(name = "EuCentrale.findAll", query = "SELECT e FROM EuCentrales e")
public class EuCentrales implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_centrales")
	private Integer idCentrales;
    @Column(name = "date_creation_centrales")
	private LocalDate dateCreationCentrales;
	@Column(name = "reference_centrale")
	private String referenceCentrale;
	@Column(name = "libelle_centrale")
	private String libelleCentrale;
	// bi-directional many-to-one association to EuAgencesOdd
	@ManyToOne
	@JoinColumn(name = "id_agences_odd")
	private EuAgencesOdd euAgencesOdd;
	// bi-directional many-to-one association to EuTypeCentrale
	@ManyToOne
	@JoinColumn(name = "id_type_centrales")
	private EuTypeCentrale euTypeCentrale;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_utilisateur")
	private EuUtilisateur user;
	@ManyToOne
	@JoinColumn(name = "id_centres")
	private EuCentres euCentres;

	public EuCentrales() {
	}

	public Integer getIdCentrales() {
		return this.idCentrales;
	}

	public void setIdCentrales(Integer idCentrales) {
		this.idCentrales = idCentrales;
	}

	public LocalDate getDateCreationCentrales() {
		return this.dateCreationCentrales;
	}

	public void setDateCreationCentrales(LocalDate dateCreationCentrales) {
		this.dateCreationCentrales = dateCreationCentrales;
	}

	public String getReferenceCentrale() {
		return this.referenceCentrale;
	}

	public void setReferenceCentrale(String referenceCentrale) {
		this.referenceCentrale = referenceCentrale;
	}

	public String getLibelleCentrale() {
		return libelleCentrale;
	}

	public void setLibelleCentrale(String libelleCentrale) {
		this.libelleCentrale = libelleCentrale;
	}

	public EuAgencesOdd getEuAgencesOdd() {
		return this.euAgencesOdd;
	}

	public void setEuAgencesOdd(EuAgencesOdd euAgencesOdd) {
		this.euAgencesOdd = euAgencesOdd;
	}

	public EuTypeCentrale getEuTypeCentrale() {
		return this.euTypeCentrale;
	}

	public void setEuTypeCentrale(EuTypeCentrale euTypeCentrale) {
		this.euTypeCentrale = euTypeCentrale;
	}

	public EuUtilisateur getUser() {
		return user;
	}

	public void setUser(EuUtilisateur user) {
		this.user = user;
	}

	public EuCentres getEuCentres() {
		return euCentres;
	}

	public void setEuCentres(EuCentres euCentres) {
		this.euCentres = euCentres;
	}
}
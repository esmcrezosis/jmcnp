package com.esmc.mcnp.model.odd;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_odd database table.
 * 
 */
@Entity
@Table(name="eu_odd")
@NamedQuery(name="EuOdd.findAll", query="SELECT e FROM EuOdd e")
public class EuOdd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_odd")
	private Integer idOdd;
	
	@Column(name = "code_odd")
	private String codeOdd;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_creation")
	private Date dateCreation;

	@Lob
	private String description;

	private String liendirect;

	@Lob
	private String resume;

	private byte statut;

	private String titre;

	private String vignette;

	@JsonIgnore
	//bi-directional many-to-one association to EuAgencesOdd
	@OneToMany(mappedBy="euOdd", fetch = FetchType.LAZY)
	private List<EuAgencesOdd> euAgencesOdds;

	public EuOdd() {
	}

	public Integer getIdOdd() {
		return this.idOdd;
	}

	public void setIdOdd(Integer idOdd) {
		this.idOdd = idOdd;
	}

	public String getCodeOdd() {
		return codeOdd;
	}

	public void setCodeOdd(String codeOdd) {
		this.codeOdd = codeOdd;
	}

	public Date getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLiendirect() {
		return this.liendirect;
	}

	public void setLiendirect(String liendirect) {
		this.liendirect = liendirect;
	}

	public String getResume() {
		return this.resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public byte getStatut() {
		return this.statut;
	}

	public void setStatut(byte statut) {
		this.statut = statut;
	}

	public String getTitre() {
		return this.titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getVignette() {
		return this.vignette;
	}

	public void setVignette(String vignette) {
		this.vignette = vignette;
	}

	public List<EuAgencesOdd> getEuAgencesOdds() {
		return this.euAgencesOdds;
	}

	public void setEuAgencesOdds(List<EuAgencesOdd> euAgencesOdds) {
		this.euAgencesOdds = euAgencesOdds;
	}

	public EuAgencesOdd addEuAgencesOdd(EuAgencesOdd euAgencesOdd) {
		getEuAgencesOdds().add(euAgencesOdd);
		euAgencesOdd.setEuOdd(this);

		return euAgencesOdd;
	}

	public EuAgencesOdd removeEuAgencesOdd(EuAgencesOdd euAgencesOdd) {
		getEuAgencesOdds().remove(euAgencesOdd);
		euAgencesOdd.setEuOdd(null);

		return euAgencesOdd;
	}

}
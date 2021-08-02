package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;
//import java.util.Date;


/**
 * The persistent class for the eu_information_additif database table.
 * 
 */
@Entity
@Table(name="eu_information_additif")
@NamedQuery(name="EuInformationAdditif.findAll", query="SELECT e FROM EuInformationAdditif e")
public class EuInformationAdditif implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idInformationAdditif;
	private String codeMembre;
	private String libelleInformationAdditif;
	private String reference;
	private Integer membreAssoId;
	private Integer etat;

	public EuInformationAdditif() {
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Column(name = "id_information_additif")
	public Long getIdInformationAdditif() {
		return idInformationAdditif;
	}

	public void setIdInformationAdditif(Long idInformationAdditif) {
		this.idInformationAdditif = idInformationAdditif;
	}

	@Column(name = "code_membre")
	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Column(name = "libelle_information_additif")
	public String getLibelleInformationAdditif() {
		return libelleInformationAdditif;
	}

	public void setLibelleInformationAdditif(String libelleInformationAdditif) {
		this.libelleInformationAdditif = libelleInformationAdditif;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Column(name = "membreasso_id")
	public Integer getMembreAssoId() {
		return membreAssoId;
	}

	public void setMembreAssoId(Integer membreAssoId) {
		this.membreAssoId = membreAssoId;
	}

	public Integer getEtat() {
		return etat;
	}

	public void setEtat(Integer etat) {
		this.etat = etat;
	}
	
	
}
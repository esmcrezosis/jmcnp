/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.model.cmfh;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Mawuli
 */
@Entity
@Table(name = "eu_cmfh")
public class EuCmfh implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idCmfh;
	private LocalDate dateCreation;
	private Integer idTypeCandidat;
	private String codeMembre;
	private Integer idPays;
	private Integer idRegion;
	private Integer idPrefeture;
	private Integer idCanton;
	private String codeZoneCreate;
	private Integer rembourser;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cmfh")
	public Long getIdCmfh() {
		return idCmfh;
	}

	public void setIdCmfh(Long idCmfh) {
		this.idCmfh = idCmfh;
	}

    @Column(name = "date_creation")
	public LocalDate getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDate dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Column(name = "id_type_candidat")
    public Integer getIdTypeCandidat() {
        return idTypeCandidat;
    }

    public void setIdTypeCandidat(Integer idTypeCandidat) {
        this.idTypeCandidat = idTypeCandidat;
    }

    @Column(name = "code_membre")
	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

    @Column(name = "id_pays")
	public Integer getIdPays() {
		return idPays;
	}

	public void setIdPays(Integer idPays) {
		this.idPays = idPays;
	}

    @Column(name = "id_region")
	public Integer getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(Integer idRegion) {
		this.idRegion = idRegion;
	}

	@Column(name = "id_prefecture")
    public Integer getIdPrefeture() {
        return idPrefeture;
    }

    public void setIdPrefeture(Integer idPrefeture) {
        this.idPrefeture = idPrefeture;
    }

    @Column(name = "id_canton")
    public Integer getIdCanton() {
        return idCanton;
    }

    public void setIdCanton(Integer idCanton) {
        this.idCanton = idCanton;
    }

    @Column(name = "code_zone_create")
	public String getCodeZoneCreate() {
		return codeZoneCreate;
	}

	public void setCodeZoneCreate(String codeZoneCreate) {
		this.codeZoneCreate = codeZoneCreate;
	}

	public Integer getRembourser() {
		return rembourser;
	}

	public void setRembourser(Integer rembourser) {
		this.rembourser = rembourser;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idCmfh != null ? idCmfh.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof EuCmfh)) {
			return false;
		}
		EuCmfh other = (EuCmfh) object;
		if ((idCmfh == null && other.idCmfh != null) || (idCmfh != null && !idCmfh.equals(other.idCmfh))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.esmc.mcnp.model.cmfh.EuCmfh[ id=" + idCmfh + " ]";
	}

}

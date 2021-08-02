/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.model.pc;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "eu_charge")
@NamedQueries({ @NamedQuery(name = "EuCharge.findAll", query = "SELECT e FROM EuCharge e"),
		@NamedQuery(name = "EuCharge.findByIdCharge", query = "SELECT e FROM EuCharge e WHERE e.idCharge = :idCharge"),
		@NamedQuery(name = "EuCharge.findByCodeCharge", query = "SELECT e FROM EuCharge e WHERE e.codeCharge = :codeCharge"),
		@NamedQuery(name = "EuCharge.findByLibelleCharge", query = "SELECT e FROM EuCharge e WHERE e.libelleCharge = :libelleCharge") })
public class EuCharge implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idCharge;
	private String codeCharge;
	private String libelleCharge;
	private String descCharge;
	private EuTypeCharge euTypeCharge;
	private List<EuChargePaye> euChargePayeList;

	public EuCharge() {
	}

	public EuCharge(Integer idCharge) {
		this.idCharge = idCharge;
	}

	public EuCharge(Integer idCharge, String codeCharge, String libelleCharge) {
		this.idCharge = idCharge;
		this.codeCharge = codeCharge;
		this.libelleCharge = libelleCharge;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_charge")
	public Integer getIdCharge() {
		return idCharge;
	}

	public void setIdCharge(Integer idCharge) {
		this.idCharge = idCharge;
	}

	@NotNull
	@Size(min = 1, max = 10)
	@Column(name = "code_charge")
	public String getCodeCharge() {
		return codeCharge;
	}

	public void setCodeCharge(String codeCharge) {
		this.codeCharge = codeCharge;
	}

	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "libelle_charge")
	public String getLibelleCharge() {
		return libelleCharge;
	}

	public void setLibelleCharge(String libelleCharge) {
		this.libelleCharge = libelleCharge;
	}

	@Lob
	@Size(max = 65535)
	@Column(name = "desc_charge")
	public String getDescCharge() {
		return descCharge;
	}

	public void setDescCharge(String descCharge) {
		this.descCharge = descCharge;
	}

	@JsonIgnore
	@JoinColumn(name = "id_type_charge", referencedColumnName = "id_type_charge")
	@ManyToOne(optional = false)
	public EuTypeCharge getEuTypeCharge() {
		return euTypeCharge;
	}

	public void setEuTypeCharge(EuTypeCharge euTypeCharge) {
		this.euTypeCharge = euTypeCharge;
	}

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "euCharge")
	public List<EuChargePaye> getEuChargePayeList() {
		return euChargePayeList;
	}

	public void setEuChargePayeList(List<EuChargePaye> euChargePayeList) {
		this.euChargePayeList = euChargePayeList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idCharge != null ? idCharge.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof EuCharge)) {
			return false;
		}
		EuCharge other = (EuCharge) object;
		return !((this.idCharge == null && other.idCharge != null)
				|| (this.idCharge != null && !this.idCharge.equals(other.idCharge)));
	}

	@Override
	public String toString() {
		return "com.esmc.mcnp.model.pc.EuCharge[ idCharge=" + idCharge + " ]";
	}

}

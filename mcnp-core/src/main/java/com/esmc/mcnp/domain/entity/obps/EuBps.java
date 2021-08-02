package com.esmc.mcnp.domain.entity.obps;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "eu_bps")
@NamedQuery(name = "EuBps.findAll", query = "SELECT b FROM EuBps b")
public class EuBps implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idBps;
	private String designation;
	private String typeSouscription;
	private Double valeurParametre;
	
	@Id
	@Column(name = "id_bps", unique = true, nullable = false)
	public Integer getIdBps() {
		return idBps;
	}
	public void setIdBps(Integer idBps) {
		this.idBps = idBps;
	}
	
	@Column(name = "designation")
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	@Column(name = "type_souscription")
	public String getTypeSouscription() {
		return typeSouscription;
	}
	public void setTypeSouscription(String typeSouscription) {
		this.typeSouscription = typeSouscription;
	}
	
	@Column(name = "valeur_parametre")
	public Double getValeurParametre() {
		return valeurParametre;
	}
	public void setValeurParametre(Double valeurParametre) {
		this.valeurParametre = valeurParametre;
	}
	
	
	

}

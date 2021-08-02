package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: EuActivation
 *
 */

@Entity
@Table(name="eu_activation")
public class EuActivation implements Serializable {
	
	private Long idActivation;
	private Integer idDepot;
	private Date dateActivation;
	private String codeActivation;
	private String codeMembre;
	private Integer membreassoId;
	private static final long serialVersionUID = 1L;

	public EuActivation() {
		super();
	} 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_activation")
	public Long getIdActivation() {
		return this.idActivation;
	}

	public void setIdActivation(Long idActivation) {
		this.idActivation = idActivation;
	}
	
	@Column(name = "id_depot")
	public Integer getIdDepot() {
		return this.idDepot;
	}

	public void setIdDepot(Integer idDepot) {
		this.idDepot = idDepot;
	}   
	
	@Column(name = "date_activation")
	public Date getDateActivation() {
		return this.dateActivation;
	}

	public void setDateActivation(Date dateActivation) {
		this.dateActivation = dateActivation;
	}   
	
	@Column(name = "code_activation")
	public String getCodeActivation() {
		return this.codeActivation;
	}

	public void setCodeActivation(String codeActivation) {
		this.codeActivation = codeActivation;
	}   
	
	@Column(name = "code_membre")
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}   
	
	@Column(name = "membreasso_id")
	public Integer getMembreassoId() {
		return this.membreassoId;
	}

	public void setMembreassoId(Integer membreassoId) {
		this.membreassoId = membreassoId;
	}
   
}

package com.esmc.mcnp.domain.entity.ksu;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Long;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuKacm
 *
 */
@Entity
@Table(name="eu_kacm")
public class EuKacm implements Serializable {

	
	private Long idKacm;
	private String codeMembre;
	private String typeActivite;
	private Double montKacm;
	private Double montOp;
	private static final long serialVersionUID = 1L;

	public EuKacm() {
		super();
	}
	
	
	@Id
	@Column(name = "id_kacm")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getIdKacm() {
		return this.idKacm;
	}

	public void setIdKacm(Long idKacm) {
		this.idKacm = idKacm;
	}
	
	@Column(name = "code_membre")
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}
	
	@Column(name = "type_activite")
	public String getTypeActivite() {
		return this.typeActivite;
	}

	public void setTypeActivite(String typeActivite) {
		this.typeActivite = typeActivite;
	}
	
	@Column(name = "mont_kacm")
	public Double getMontKacm() {
		return this.montKacm;
	}

	public void setMontKacm(Double montKacm) {
		this.montKacm = montKacm;
	}
	
	@Column(name = "mont_op")
	public Double getMontOp() {
		return this.montOp;
	}

	public void setMontOp(Double montOp) {
		this.montOp = montOp;
	}
   
}

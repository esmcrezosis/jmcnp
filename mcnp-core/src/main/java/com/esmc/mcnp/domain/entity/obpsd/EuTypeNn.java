package com.esmc.mcnp.domain.entity.obpsd;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.ba.EuNn;

import java.util.List;


/**
 * The persistent class for the eu_type_nn database table.
 * 
 */
@Entity
@Table(name="eu_type_nn")
@NamedQuery(name="EuTypeNn.findAll", query="SELECT e FROM EuTypeNn e")
public class EuTypeNn implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeTypeNn;
	private String descTypeNn;
	private String libTypeNn;
	private List<EuNn> euNns;
	private List<EuTransfertNn> euTransfertNns;

	public EuTypeNn() {
	}


	@Id
	@Column(name="code_type_nn", unique=true, nullable=false, length=50)
	public String getCodeTypeNn() {
		return this.codeTypeNn;
	}

	public void setCodeTypeNn(String codeTypeNn) {
		this.codeTypeNn = codeTypeNn;
	}


	@Column(name="desc_type_nn", length=255)
	public String getDescTypeNn() {
		return this.descTypeNn;
	}

	public void setDescTypeNn(String descTypeNn) {
		this.descTypeNn = descTypeNn;
	}


	@Column(name="lib_type_nn", length=255)
	public String getLibTypeNn() {
		return this.libTypeNn;
	}

	public void setLibTypeNn(String libTypeNn) {
		this.libTypeNn = libTypeNn;
	}


	//bi-directional many-to-one association to EuNn
	@OneToMany(mappedBy="euTypeNn")
	public List<EuNn> getEuNns() {
		return this.euNns;
	}

	public void setEuNns(List<EuNn> euNns) {
		this.euNns = euNns;
	}

	public EuNn addEuNn(EuNn euNn) {
		getEuNns().add(euNn);
		euNn.setEuTypeNn(this);

		return euNn;
	}

	public EuNn removeEuNn(EuNn euNn) {
		getEuNns().remove(euNn);
		euNn.setEuTypeNn(null);

		return euNn;
	}


	//bi-directional many-to-one association to EuTransfertNn
	@OneToMany(mappedBy="euTypeNn")
	public List<EuTransfertNn> getEuTransfertNns() {
		return this.euTransfertNns;
	}

	public void setEuTransfertNns(List<EuTransfertNn> euTransfertNns) {
		this.euTransfertNns = euTransfertNns;
	}

	public EuTransfertNn addEuTransfertNn(EuTransfertNn euTransfertNn) {
		getEuTransfertNns().add(euTransfertNn);
		euTransfertNn.setEuTypeNn(this);

		return euTransfertNn;
	}

	public EuTransfertNn removeEuTransfertNn(EuTransfertNn euTransfertNn) {
		getEuTransfertNns().remove(euTransfertNn);
		euTransfertNn.setEuTypeNn(null);

		return euTransfertNn;
	}

}
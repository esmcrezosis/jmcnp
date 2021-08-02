package com.esmc.mcnp.domain.entity.cmfh;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.cmfh.EuMf;

import java.util.List;


/**
 * The persistent class for the eu_type_mf database table.
 * 
 */
@Entity
@Table(name="eu_type_mf")
@NamedQuery(name="EuTypeMf.findAll", query="SELECT e FROM EuTypeMf e")
public class EuTypeMf implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeTypeMf;
	private String libTypeMf;
	private List<EuMf> euMfs;

	public EuTypeMf() {
	}


	@Id
	@Column(name="code_type_mf", unique=true, nullable=false, length=20)
	public String getCodeTypeMf() {
		return this.codeTypeMf;
	}

	public void setCodeTypeMf(String codeTypeMf) {
		this.codeTypeMf = codeTypeMf;
	}


	@Column(name="lib_type_mf", length=100)
	public String getLibTypeMf() {
		return this.libTypeMf;
	}

	public void setLibTypeMf(String libTypeMf) {
		this.libTypeMf = libTypeMf;
	}


	//bi-directional many-to-one association to EuMf
	@OneToMany(mappedBy="euTypeMf")
	public List<EuMf> getEuMfs() {
		return this.euMfs;
	}

	public void setEuMfs(List<EuMf> euMfs) {
		this.euMfs = euMfs;
	}

	public EuMf addEuMf(EuMf euMf) {
		getEuMfs().add(euMf);
		euMf.setEuTypeMf(this);

		return euMf;
	}

	public EuMf removeEuMf(EuMf euMf) {
		getEuMfs().remove(euMf);
		euMf.setEuTypeMf(null);

		return euMf;
	}

}
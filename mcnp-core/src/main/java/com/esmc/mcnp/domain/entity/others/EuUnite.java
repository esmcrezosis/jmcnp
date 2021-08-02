package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.cmfh.EuMfUnite;

import java.util.List;


/**
 * The persistent class for the eu_unite database table.
 * 
 */
@Entity
@Table(name="eu_unite")
@NamedQuery(name="EuUnite.findAll", query="SELECT e FROM EuUnite e")
public class EuUnite implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeUnite;
	private String desUnite;
	private String libUnite;
	private List<EuMfUnite> euMfUnites;

	public EuUnite() {
	}


	@Id
	@Column(name="code_unite", unique=true, nullable=false, length=20)
	public String getCodeUnite() {
		return this.codeUnite;
	}

	public void setCodeUnite(String codeUnite) {
		this.codeUnite = codeUnite;
	}


	@Column(name="des_unite", length=200)
	public String getDesUnite() {
		return this.desUnite;
	}

	public void setDesUnite(String desUnite) {
		this.desUnite = desUnite;
	}


	@Column(name="lib_unite", length=200)
	public String getLibUnite() {
		return this.libUnite;
	}

	public void setLibUnite(String libUnite) {
		this.libUnite = libUnite;
	}


	//bi-directional many-to-one association to EuMfUnite
	@OneToMany(mappedBy="euUnite")
	public List<EuMfUnite> getEuMfUnites() {
		return this.euMfUnites;
	}

	public void setEuMfUnites(List<EuMfUnite> euMfUnites) {
		this.euMfUnites = euMfUnites;
	}

	public EuMfUnite addEuMfUnite(EuMfUnite euMfUnite) {
		getEuMfUnites().add(euMfUnite);
		euMfUnite.setEuUnite(this);

		return euMfUnite;
	}

	public EuMfUnite removeEuMfUnite(EuMfUnite euMfUnite) {
		getEuMfUnites().remove(euMfUnite);
		euMfUnite.setEuUnite(null);

		return euMfUnite;
	}

}
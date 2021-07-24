package com.esmc.mcnp.model.acteur;

import com.esmc.mcnp.model.acteur.EuGac;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the eu_type_gac database table.
 *
 */
@Entity
@Table(name = "eu_type_gac")
@NamedQuery(name = "EuTypeGac.findAll", query = "SELECT e FROM EuTypeGac e")
public class EuTypeGac implements Serializable {

	private static final long serialVersionUID = 1L;
	private String codeTypeGac;
	private String nomTypeGac;
	private Integer ordreTypeGac;
	private List<EuGac> euGacs;

	public EuTypeGac() {
	}

	@Id
	@Column(name = "code_type_gac", unique = true, nullable = false, length = 100)
	public String getCodeTypeGac() {
		return this.codeTypeGac;
	}

	public void setCodeTypeGac(String codeTypeGac) {
		this.codeTypeGac = codeTypeGac;
	}

	@Column(name = "nom_type_gac", length = 200)
	public String getNomTypeGac() {
		return this.nomTypeGac;
	}

	public void setNomTypeGac(String nomTypeGac) {
		this.nomTypeGac = nomTypeGac;
	}

	@Column(name = "ordre_type_gac")
	public Integer getOrdreTypeGac() {
		return this.ordreTypeGac;
	}

	public void setOrdreTypeGac(Integer ordreTypeGac) {
		this.ordreTypeGac = ordreTypeGac;
	}

	// bi-directional many-to-one association to EuGac
	@OneToMany(mappedBy = "euTypeGac")
	public List<EuGac> getEuGacs() {
		return this.euGacs;
	}

	public void setEuGacs(List<EuGac> euGacs) {
		this.euGacs = euGacs;
	}

	public EuGac addEuGac(EuGac euGac) {
		getEuGacs().add(euGac);
		euGac.setEuTypeGac(this);

		return euGac;
	}

	public EuGac removeEuGac(EuGac euGac) {
		getEuGacs().remove(euGac);
		euGac.setEuTypeGac(null);

		return euGac;
	}

}

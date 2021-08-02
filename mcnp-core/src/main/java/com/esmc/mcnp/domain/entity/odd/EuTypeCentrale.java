package com.esmc.mcnp.domain.entity.odd;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the eu_type_centrales database table.
 * 
 */
@Entity
@Table(name="eu_type_centrales")
@NamedQuery(name="EuTypeCentrale.findAll", query="SELECT e FROM EuTypeCentrale e")
public class EuTypeCentrale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_type_centrales")
	private Integer idTypeCentrales;

	@Column(name="libelle_type_centrales")
	private String libelleTypeCentrales;

	//bi-directional many-to-one association to EuCentrale
	@JsonIgnore
	@OneToMany(mappedBy="euTypeCentrale")
	private List<EuCentrales> euCentrales;

	public EuTypeCentrale() {
	}

	public Integer getIdTypeCentrales() {
		return this.idTypeCentrales;
	}

	public void setIdTypeCentrales(Integer idTypeCentrales) {
		this.idTypeCentrales = idTypeCentrales;
	}

	public String getLibelleTypeCentrales() {
		return this.libelleTypeCentrales;
	}

	public void setLibelleTypeCentrales(String libelleTypeCentrales) {
		this.libelleTypeCentrales = libelleTypeCentrales;
	}

	public List<EuCentrales> getEuCentrales() {
		return this.euCentrales;
	}

	public void setEuCentrales(List<EuCentrales> euCentrales) {
		this.euCentrales = euCentrales;
	}

	public EuCentrales addEuCentrale(EuCentrales euCentrale) {
		getEuCentrales().add(euCentrale);
		euCentrale.setEuTypeCentrale(this);

		return euCentrale;
	}

	public EuCentrales removeEuCentrale(EuCentrales euCentrale) {
		getEuCentrales().remove(euCentrale);
		euCentrale.setEuTypeCentrale(null);

		return euCentrale;
	}

}
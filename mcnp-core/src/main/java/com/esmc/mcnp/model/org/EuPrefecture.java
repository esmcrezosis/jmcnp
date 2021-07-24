package com.esmc.mcnp.model.org;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * The persistent class for the eu_prefecture database table.
 * 
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name="eu_prefecture")
@NamedQuery(name="EuPrefecture.findAll", query="SELECT e FROM EuPrefecture e")
public class EuPrefecture implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_prefecture")
	private int idPrefecture;
	@Column(name="nom_prefecture")
	private String nomPrefecture;
	@Column(name="id_region")
	private int idRegion;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_region", insertable = false, updatable = false)
	private EuRegion euRegion;
	@JsonIgnore
	@OneToMany(mappedBy="euPrefecture")
	private List<EuCanton> euCantons;

	public EuCanton addEuCanton(EuCanton euCanton) {
		getEuCantons().add(euCanton);
		euCanton.setEuPrefecture(this);

		return euCanton;
	}

	public EuCanton removeEuCanton(EuCanton euCanton) {
		getEuCantons().remove(euCanton);
		euCanton.setEuPrefecture(null);

		return euCanton;
	}

}
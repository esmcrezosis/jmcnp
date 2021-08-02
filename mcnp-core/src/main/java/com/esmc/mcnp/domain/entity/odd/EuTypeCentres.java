package com.esmc.mcnp.model.odd;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "eu_type_centres")
public class EuTypeCentres {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_type_centres")
	private Integer idTypeCentres;
	@Column(name = "libelle_type_centres")
	private String libelleTypeCentres;
	@Column(name = "parent_type_centres")
	private Integer parentTypeCentres;
}

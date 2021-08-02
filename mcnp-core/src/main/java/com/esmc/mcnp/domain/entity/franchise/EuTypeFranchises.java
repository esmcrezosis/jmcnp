package com.esmc.mcnp.domain.entity.franchise;

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
@Table(name = "eu_type_franchises")
public class EuTypeFranchises {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_type_franchises")
	private Integer idTypeFranchises;
	@Column(name = "libelle_type_franchises")
	private String libelleTypeFranchises;
	@Column(name = "montant_franchise")
	private Double montantFranchise;
}

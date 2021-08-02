package com.esmc.mcnp.domain.entity.smcipn;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "eu_categorie_budget")
@Data
public class EuCategorieBudget implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "code_categorie_budget", unique = true)
	private String codeCategorie;
	@Column(name = "nom_categorie_budget")
	private String nomCategorie;

}

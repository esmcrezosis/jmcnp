package com.esmc.mcnp.model.smcipn;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.esmc.mcnp.model.security.EuUtilisateur;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "eu_type_budget")
@Data
@Accessors(chain = true)
public class EuTypeBudget implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "code_type_budget", unique = true)
	private String codeTypeBudget;
	@Column(name = "libelle_budget")
	private String libelleBudget;
	@Column(name = "type_budget")
	private String typeDuree;
	@Column(name = "duree_budget")
	private Integer dureeBudget;
	//@Enumerated(EnumType.STRING)
	@Column(name = "nature_budget")
	private String natureBudget;
	@Column(name = "date_soumission")
	private LocalDate dateSoumission;
	@Column(name = "date_validation")
	private LocalDate dateValidation;
	@Column(name = "date_debut")
	private LocalDate dateDebut;
	@Column(name = "date_fin")
	private LocalDate dateFin;
	private String statut;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_utilisateur")
	private EuUtilisateur user;
	@JsonIgnore
	@OneToMany(mappedBy = "typeBudget")
	private List<EuBudget> budgets;
	
}

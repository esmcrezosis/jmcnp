package com.esmc.mcnp.model.smcipn;

import java.io.Serializable;

import javax.persistence.*;

import com.esmc.mcnp.model.security.EuUtilisateur;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@Entity
@Table(name = "eu_detail_budget")
public class EuDetailBudget implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "id_budget")
	private EuBudget budget;
	@Column(name = "libelle_detail_budget")
	private String libelleDetailBudget;
	@Column(name = "montant_initial")
	private Double montantInitial;
	private boolean valider;
	@Column(name = "montant_budget")
	private Double montantBudget;
	@Column(name = "statut_detail_budget")
	private String statutDetailBudget;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_utilisateur")
	private EuUtilisateur user;
}

package com.esmc.mcnp.model.smcipn;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import com.esmc.mcnp.model.enums.NiveauValidation;
import com.esmc.mcnp.model.odd.EuAgencesOdd;
import com.esmc.mcnp.model.odd.EuCentrales;
import com.esmc.mcnp.model.odd.EuCentres;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "eu_budget")
@Data
@Accessors(chain = true)
public class EuBudget implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "code_budget", unique = true)
	private String codeBudget;
	@Column(name = "nom_budget")
	private String nomBudget;
	@Column(name = "date_budget")
	private LocalDate dateBudget;
	@Column(name = "montant_demande")
	private Double montantDemande;
	@Column(name = "montant_valide")
	private Double montantValide;
	@Column(name = "montant_budget")
	private Double montantBudget;
	@Column(name = "montant_utilise")
	private Double montantUtilise;
	@Column(name = "solde_budget")
	private Double soldeBudget;
	@Column(name = "statut_budget")
	private String statutBudget;
	@Enumerated(EnumType.STRING)
	@Column(name = "niveau_validation")
	private NiveauValidation niveauValidation;
	private boolean valider;
	@OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EuDetailBudget> detailBudgets = Lists.newArrayList();
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_utilisateur")
	private EuUtilisateur user;
	@ManyToOne
	@JoinColumn(name = "id_categorie_budget")
	private EuCategorieBudget categorieBudget;
	@ManyToOne
	@JoinColumn(name = "id_tpye_budget")
	private EuTypeBudget typeBudget;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_centres")
	private EuCentres centre;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_centrales")
	private EuCentrales centrale;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_agences_odd")
	private EuAgencesOdd agencesOdd;

	public EuBudget addDetailBudget(EuDetailBudget detailBudget) {
		detailBudgets.add(detailBudget);
		detailBudget.setBudget(this);
		return this;
	}

	public EuBudget removeDetailBudget(EuDetailBudget detailBudget) {
		detailBudgets.remove(detailBudget);
		detailBudget.setBudget(null);
		return this;
	}
}

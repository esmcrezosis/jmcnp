package com.esmc.mcnp.model.smcipn;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.acteur.EuGac;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.google.common.collect.Lists;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


/**
 * The persistent class for the eu_smcipn database table.
 *
 */
@Data
@Entity
@Table(name="eu_smcipn")
@DynamicUpdate
@DynamicInsert
@NamedQuery(name="EuSmcipn.findAll", query="SELECT e FROM EuSmcipn e")
public class EuSmcipn implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "code_smcipn", unique = true)
	private String codeSmcipn;
	@Column(name = "date_demande")
	private LocalDate dateDemande;
	@Column(name = "montant_smcipn")
	private Double montantSmcipn;
	@Column(name = "montant_investissement")
	private double montantInvestissement;
	@Column(name = "montant_salaire")
    private double montantSalaire;
	@Column(name = "date_allocation")
	private LocalDate dateAllocation;
	@ManyToOne
	@JoinColumn(name = "code_compte")
	private EuCompte compte;
	@ManyToOne
	@JoinColumn(name = "id_type_smcipn")
	private EuTypeSmcipn typeSmcipn;
	@ManyToOne
	@JoinColumn(name = "id_budget")
	private EuBudget budget;
	@ManyToOne
	@JoinColumn(name = "code_membre_morale")
	private EuMembreMorale membreMorale;
	@ManyToOne
	@JoinColumn(name = "code_membre")
	private EuMembre membre;
	@ManyToOne
	@JoinColumn(name = "code_gac")
	private EuGac gac;
	@ManyToOne
	@JoinColumn(name = "id_utilisateur")
	private EuUtilisateur euUtilisateur;
	@OneToMany(mappedBy = "smcipn")
	private List<EuServir> euServirs = Lists.newArrayList();
	@OneToMany(mappedBy = "smcipn")
    private List<EuUtiliser> euUtilisers = Lists.newArrayList();
    
    public EuServir addEuServir(EuServir euServir) {
        getEuServirs().add(euServir);
        euServir.setSmcipn(this);
        return euServir;
    }

    public EuServir removeEuServir(EuServir euServir) {
        getEuServirs().remove(euServir);
        euServir.setSmcipn(null);
        return euServir;
    }
    
    public EuUtiliser addEuUtiliser(EuUtiliser euUtiliser) {
        getEuUtilisers().add(euUtiliser);
        euUtiliser.setSmcipn(this);
        return euUtiliser;
    }

    public EuUtiliser removeEuUtiliser(EuUtiliser euUtiliser) {
        getEuUtilisers().remove(euUtiliser);
        euUtiliser.setSmcipn(null);
        return euUtiliser;
    }

}
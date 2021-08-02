package com.esmc.mcnp.domain.entity.smcipn;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.odd.EuAgencesOdd;
import com.esmc.mcnp.domain.entity.odd.EuCentrales;
import com.esmc.mcnp.domain.entity.odd.EuCentres;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

import lombok.Data;

@Data
@Entity
@Table(name = "eu_demande_smcipn")
public class EuDemandeSmcipn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "code_demande")
	private String codeDemande;
	@Column(name = "date_demande")
	private LocalDate dateDemande;
	private String motif;
	@Column(name = "montant_demande")
	private Double montantDemande;
	private boolean valider;
	@ManyToOne
	@JoinColumn(name = "id_centrales")
	private EuCentrales centrale;
	@ManyToOne
	@JoinColumn(name = "id_agences_odd")
	private EuAgencesOdd agenceOdd;
	@ManyToOne
	@JoinColumn(name = "id_centres")
	private EuCentres centre;
	@ManyToOne
	@JoinColumn(name = "code_membre_morale")
	private EuMembreMorale membreMorale;
	@ManyToOne
	@JoinColumn(name = "id_utilisateur")
	private EuUtilisateur user;
}

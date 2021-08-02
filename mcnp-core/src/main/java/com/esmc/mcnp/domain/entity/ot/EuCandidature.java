package com.esmc.mcnp.domain.entity.ot;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "eu_candidature")
@Data
@Accessors(chain = true)
@DynamicInsert
@DynamicUpdate
public class EuCandidature implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_candidature")
	private Integer idCandidature;
	@Column(name = "candidature_key")
	private String candidatureKey;
	private String competences;
	@Column(name = "dead_selection")
	private Integer deadSelection;
	@Column(name = "annee_experience")
	private String anneeExperience;
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "date_candidature")
	private LocalDate dateCandidature;
	@Column(name = "nbre_jour_expiration")
	private Integer nbreJourExpiration;
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "date_expiration")
	private LocalDate dateExpiration;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_utilisateur")
	private EuUtilisateur user;
	@JsonIgnore
	@OneToMany(mappedBy = "candidature")
	private List<EuCandidaturePoste> postes = new ArrayList<>();
}

package com.esmc.mcnp.domain.entity.ksu;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.esmc.mcnp.domain.enums.LivraisonEnum;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "eu_livraison_ksu")
@Data
@Accessors(chain = true)
public class EuLivraisonKsu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JsonFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "date_demande")
	private LocalDate dateDemande;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_utilisateur")
	private EuUtilisateur user;
	private Integer nombre;
	@JsonFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "date_livraison")
	private LocalDate dateLivraison;
	@JsonFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "date_reception")
	private LocalDateTime dateReception;
	@Enumerated(EnumType.STRING)
	private LivraisonEnum status;
	@Column(name = "code_suivi", unique = true)
	private String codeSuivi;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "eu_demande_carte", joinColumns = @JoinColumn(name = "id_livraison_ksu", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_carte", referencedColumnName = "id_carte"))
	private List<EuCarte> cartes = Lists.newArrayList();

	public EuCarte addCarte(EuCarte euCarte) {
		getCartes().add(euCarte);
		return euCarte;
	}

	public EuCarte removeCarte(EuCarte euCarte) {
		getCartes().remove(euCarte);
		return euCarte;
	}
}

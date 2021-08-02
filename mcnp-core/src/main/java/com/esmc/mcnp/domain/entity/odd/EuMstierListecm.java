package com.esmc.mcnp.domain.entity.odd;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.esmc.mcnp.domain.entity.cm.EuReligion;
import com.esmc.mcnp.domain.entity.oi.EuCaps;
import com.esmc.mcnp.domain.entity.org.EuAgence;
import com.esmc.mcnp.domain.entity.org.EuCanton;
import com.esmc.mcnp.domain.entity.org.EuPays;
import com.esmc.mcnp.domain.entity.org.EuZone;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "eu_mstiers_listecm")
public class EuMstierListecm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_mstiers_listecm")
    private Long id;
	@Column(name = "code_fiche_odd", unique = true)
	private String codeFicheOdd;
	@Column(name = "code_membre_apporteur")
	private String codeMembreApporteur;
	@Column(name = "code_membre_beneficiaire")
	private String codeMembreBeneficiaire;
	@Column(name = "nom_membre")
	private String nomMembre;
	@Column(name = "prenom_membre")
	private String prenomMembre;
	@JsonIgnore
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "date_nais_membre")
	private LocalDate dateNaisMembre;
	@Column(name = "lieu_nais_membre")
	private String lieuNaisMembre;
	@Column(name = "pere_membre")
	private String pereMembre;
	@Column(name = "mere_membre")
	private String mereMembre;
	@Column(name = "nbr_enf_membre")
	private Integer nbrenfMembre;
	@Column(name = "portable_membre")
	private String portableMembre;
	@Column(name = "bp_membre")
	private String bpMembre;
	private String codesecret;
	@Column(name = "email_membre")
	private String emailMembre;
	private String formation;
	@Column(name = "quartier_membre")
    private String quartierMembre;
	@Column(name = "sexe_membre")
    private String sexeMembre;
	@Column(name = "sitfam_membre")
    private String sitfamMembre;
	@Column(name = "ville_membre")
    private String villeMembre;
	@Column(name = "profession_membre")
	private String professionMembre;
	@Column(name = "tel_membre")
    private String telMembre;
    private Integer statut;
    private Integer doublon;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(name = "date_listecm")
    private LocalDateTime dateListecm;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_religion_membre")
    private EuReligion religion;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_caps")
    private EuCaps caps;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_odd")
    private EuOdd odd;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_agence")
    private EuAgence agence;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agence_odd")
    private EuAgencesOdd agenceOdd;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_canton")
    private EuCanton canton;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pays")
    private EuPays pays;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_zone")
    private EuZone zone;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur")
    private EuUtilisateur user;
}

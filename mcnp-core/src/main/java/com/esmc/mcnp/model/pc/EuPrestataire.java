package com.esmc.mcnp.model.pc;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuPrestataire
 *
 */
@Entity
@Table(name = "eu_prestataire")
public class EuPrestataire implements Serializable {

	private Long id;
	private String codeMembre;
	private String codeMembrePrestataire;
	private String nomMembrePrestataire;
	private Date dateAjout;
	private String modePaiement;
	private String typeCompte;
	private String numeroCompte;
	private Boolean marge;
	private Integer nombreOpi;
	private Double montantPrestation;
	private Long idUtilisateur;
	private static final long serialVersionUID = 1L;

	public EuPrestataire() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "code_membre")
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Column(name = "code_membre_prestataire")
	public String getCodeMembrePrestataire() {
		return this.codeMembrePrestataire;
	}

	public void setCodeMembrePrestataire(String codeMembrePrestataire) {
		this.codeMembrePrestataire = codeMembrePrestataire;
	}

	@Column(name = "nom_membre_prestataire")
	public String getNomMembrePrestataire() {
		return nomMembrePrestataire;
	}

	public void setNomMembrePrestataire(String nomMembrePrestataire) {
		this.nomMembrePrestataire = nomMembrePrestataire;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_ajout")
	public Date getDateAjout() {
		return this.dateAjout;
	}

	public void setDateAjout(Date dateAjout) {
		this.dateAjout = dateAjout;
	}

	@Column(name = "mode_paiement")
	public String getModePaiement() {
		return modePaiement;
	}

	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}

	@Column(name = "type_paiement")
	public String getTypeCompte() {
		return typeCompte;
	}

	public void setTypeCompte(String typeCompte) {
		this.typeCompte = typeCompte;
	}

	@Column(name = "numero_compte")
	public String getNumeroCompte() {
		return numeroCompte;
	}

	public void setNumeroCompte(String numeroCompte) {
		this.numeroCompte = numeroCompte;
	}

	public Boolean getMarge() {
		return marge;
	}

	public void setMarge(Boolean marge) {
		this.marge = marge;
	}

	@Column(name = "nombre_opi")
	public Integer getNombreOpi() {
		return nombreOpi;
	}

	public void setNombreOpi(Integer nombreOpi) {
		this.nombreOpi = nombreOpi;
	}

	@Column(name = "montant_prestation")
	public Double getMontantPrestation() {
		return montantPrestation;
	}

	public void setMontantPrestation(Double montantPrestation) {
		this.montantPrestation = montantPrestation;
	}

	@Column(name = "id_utilisateur")
	public Long getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

}

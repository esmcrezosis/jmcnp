package com.esmc.mcnp.model.op;

import com.esmc.mcnp.model.others.EuProposition;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the eu_appel_offre database table.
 *
 */
@Entity
@Table(name = "eu_appel_offre")
@NamedQuery(name = "EuAppelOffre.findAll", query = "SELECT e FROM EuAppelOffre e")
public class EuAppelOffre implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idAppelOffre;
	private String codeMembreMorale;
	private Date dateCreation;
	private String descripAppelOffre;
	private Integer dureeProjet;
	private Long idDemande;
	private Long idUtilisateur;
	private String membreMoraleExecutante;
	private String nomAppelOffre;
	private String numeroOffre;
	private Integer publier;
	private String typeAppelOffre;
	private String typeSmcipn;
	private Integer idCanton;
	private List<EuProposition> euPropositions;

	public EuAppelOffre() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_appel_offre", unique = true, nullable = false)
	public Long getIdAppelOffre() {
		return this.idAppelOffre;
	}

	public void setIdAppelOffre(Long idAppelOffre) {
		this.idAppelOffre = idAppelOffre;
	}

	@Column(name = "code_membre_morale", length = 100)
	public String getCodeMembreMorale() {
		return this.codeMembreMorale;
	}

	public void setCodeMembreMorale(String codeMembreMorale) {
		this.codeMembreMorale = codeMembreMorale;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_creation")
	public Date getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Column(name = "descrip_appel_offre", length = 255)
	public String getDescripAppelOffre() {
		return this.descripAppelOffre;
	}

	public void setDescripAppelOffre(String descripAppelOffre) {
		this.descripAppelOffre = descripAppelOffre;
	}

	@Column(name = "duree_projet")
	public Integer getDureeProjet() {
		return this.dureeProjet;
	}

	public void setDureeProjet(Integer dureeProjet) {
		this.dureeProjet = dureeProjet;
	}

	@Column(name = "id_demande")
	public Long getIdDemande() {
		return this.idDemande;
	}

	public void setIdDemande(Long idDemande) {
		this.idDemande = idDemande;
	}

	@Column(name = "id_utilisateur")
	public Long getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	@Column(name = "membre_morale_executante", length = 100)
	public String getMembreMoraleExecutante() {
		return this.membreMoraleExecutante;
	}

	public void setMembreMoraleExecutante(String membreMoraleExecutante) {
		this.membreMoraleExecutante = membreMoraleExecutante;
	}

	@Column(name = "nom_appel_offre", length = 255)
	public String getNomAppelOffre() {
		return this.nomAppelOffre;
	}

	public void setNomAppelOffre(String nomAppelOffre) {
		this.nomAppelOffre = nomAppelOffre;
	}

	@Column(name = "numero_offre", length = 150)
	public String getNumeroOffre() {
		return this.numeroOffre;
	}

	public void setNumeroOffre(String numeroOffre) {
		this.numeroOffre = numeroOffre;
	}

	public Integer getPublier() {
		return this.publier;
	}

	public void setPublier(Integer publier) {
		this.publier = publier;
	}

	@Column(name = "type_appel_offre", length = 40)
	public String getTypeAppelOffre() {
		return this.typeAppelOffre;
	}

	public void setTypeAppelOffre(String typeAppelOffre) {
		this.typeAppelOffre = typeAppelOffre;
	}

	@Column(name = "type_smcipn")
	public String getTypeSmcipn() {
		return typeSmcipn;
	}

	public void setTypeSmcipn(String typeSmcipn) {
		this.typeSmcipn = typeSmcipn;
	}

	@Column(name = "id_canton")
	public Integer getIdCanton() {
		return idCanton;
	}

	public void setIdCanton(Integer idCanton) {
		this.idCanton = idCanton;
	}

	// bi-directional many-to-one association to EuProposition
	@OneToMany(mappedBy = "euAppelOffre", fetch = FetchType.LAZY)
	public List<EuProposition> getEuPropositions() {
		return this.euPropositions;
	}

	public void setEuPropositions(List<EuProposition> euPropositions) {
		this.euPropositions = euPropositions;
	}

	public EuProposition addEuProposition(EuProposition euProposition) {
		getEuPropositions().add(euProposition);
		euProposition.setEuAppelOffre(this);

		return euProposition;
	}

	public EuProposition removeEuProposition(EuProposition euProposition) {
		getEuPropositions().remove(euProposition);
		euProposition.setEuAppelOffre(null);

		return euProposition;
	}

}
package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.bc.EuDomiciliation;
import com.esmc.mcnp.domain.entity.op.EuAppelNn;
import com.esmc.mcnp.domain.entity.op.EuAppelOffre;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_proposition database table.
 *
 */
@Entity
@Table(name="eu_proposition")
@NamedQuery(name="EuProposition.findAll", query="SELECT e FROM EuProposition e")
public class EuProposition implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idProposition;
	private String autreBudget;
	private Integer choixProposition;
	private String codeMembreMorale;
	private Date dateCreation;
	private Integer disponible;
	private Long idUtilisateur;
	private double montantProposition;
	private double montantSalaire;
	private Integer preselection;
	private List<EuAppelNn> euAppelNns;
	private List<EuDetailProposition> euDetailPropositions;
	private List<EuDomiciliation> euDomiciliations;
	private List<EuMembreProposition> euMembrePropositions;
	private EuAppelOffre euAppelOffre;

	public EuProposition() {
	}


	@Id
	@Column(name="id_proposition", unique=true, nullable=false)
	public Long getIdProposition() {
		return this.idProposition;
	}

	public void setIdProposition(Long idProposition) {
		this.idProposition = idProposition;
	}


	@Column(name="autre_budget", length=50)
	public String getAutreBudget() {
		return this.autreBudget;
	}

	public void setAutreBudget(String autreBudget) {
		this.autreBudget = autreBudget;
	}


	@Column(name="choix_proposition")
	public Integer getChoixProposition() {
		return this.choixProposition;
	}

	public void setChoixProposition(Integer choixProposition) {
		this.choixProposition = choixProposition;
	}


	@Column(name="code_membre_morale", length=25)
	public String getCodeMembreMorale() {
		return this.codeMembreMorale;
	}

	public void setCodeMembreMorale(String codeMembreMorale) {
		this.codeMembreMorale = codeMembreMorale;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_creation")
	public Date getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	public Integer getDisponible() {
		return this.disponible;
	}

	public void setDisponible(Integer disponible) {
		this.disponible = disponible;
	}


	@Column(name="id_utilisateur")
	public Long getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	@Column(name="montant_proposition")
	public double getMontantProposition() {
		return this.montantProposition;
	}

	public void setMontantProposition(double montantProposition) {
		this.montantProposition = montantProposition;
	}


	@Column(name="montant_salaire")
	public double getMontantSalaire() {
		return this.montantSalaire;
	}

	public void setMontantSalaire(double montantSalaire) {
		this.montantSalaire = montantSalaire;
	}


	public Integer getPreselection() {
		return this.preselection;
	}

	public void setPreselection(Integer preselection) {
		this.preselection = preselection;
	}


	//bi-directional many-to-one association to EuAppelNn
	@OneToMany(mappedBy="euProposition")
	public List<EuAppelNn> getEuAppelNns() {
		return this.euAppelNns;
	}

	public void setEuAppelNns(List<EuAppelNn> euAppelNns) {
		this.euAppelNns = euAppelNns;
	}

	public EuAppelNn addEuAppelNn(EuAppelNn euAppelNn) {
		getEuAppelNns().add(euAppelNn);
		euAppelNn.setEuProposition(this);

		return euAppelNn;
	}

	public EuAppelNn removeEuAppelNn(EuAppelNn euAppelNn) {
		getEuAppelNns().remove(euAppelNn);
		euAppelNn.setEuProposition(null);

		return euAppelNn;
	}


	//bi-directional many-to-one association to EuDetailProposition
	@OneToMany(mappedBy="euProposition")
	public List<EuDetailProposition> getEuDetailPropositions() {
		return this.euDetailPropositions;
	}

	public void setEuDetailPropositions(List<EuDetailProposition> euDetailPropositions) {
		this.euDetailPropositions = euDetailPropositions;
	}

	public EuDetailProposition addEuDetailProposition(EuDetailProposition euDetailProposition) {
		getEuDetailPropositions().add(euDetailProposition);
		euDetailProposition.setEuProposition(this);

		return euDetailProposition;
	}

	public EuDetailProposition removeEuDetailProposition(EuDetailProposition euDetailProposition) {
		getEuDetailPropositions().remove(euDetailProposition);
		euDetailProposition.setEuProposition(null);

		return euDetailProposition;
	}


	//bi-directional many-to-one association to EuDomiciliation
	@OneToMany(mappedBy="euProposition")
	public List<EuDomiciliation> getEuDomiciliations() {
		return this.euDomiciliations;
	}

	public void setEuDomiciliations(List<EuDomiciliation> euDomiciliations) {
		this.euDomiciliations = euDomiciliations;
	}

	public EuDomiciliation addEuDomiciliation(EuDomiciliation euDomiciliation) {
		getEuDomiciliations().add(euDomiciliation);
		euDomiciliation.setEuProposition(this);

		return euDomiciliation;
	}

	public EuDomiciliation removeEuDomiciliation(EuDomiciliation euDomiciliation) {
		getEuDomiciliations().remove(euDomiciliation);
		euDomiciliation.setEuProposition(null);

		return euDomiciliation;
	}


	//bi-directional many-to-one association to EuMembreProposition
	@OneToMany(mappedBy="euProposition")
	public List<EuMembreProposition> getEuMembrePropositions() {
		return this.euMembrePropositions;
	}

	public void setEuMembrePropositions(List<EuMembreProposition> euMembrePropositions) {
		this.euMembrePropositions = euMembrePropositions;
	}

	public EuMembreProposition addEuMembreProposition(EuMembreProposition euMembreProposition) {
		getEuMembrePropositions().add(euMembreProposition);
		euMembreProposition.setEuProposition(this);

		return euMembreProposition;
	}

	public EuMembreProposition removeEuMembreProposition(EuMembreProposition euMembreProposition) {
		getEuMembrePropositions().remove(euMembreProposition);
		euMembreProposition.setEuProposition(null);

		return euMembreProposition;
	}


	//bi-directional many-to-one association to EuAppelOffre
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_appel_offre")
	public EuAppelOffre getEuAppelOffre() {
		return this.euAppelOffre;
	}

	public void setEuAppelOffre(EuAppelOffre euAppelOffre) {
		this.euAppelOffre = euAppelOffre;
	}

}
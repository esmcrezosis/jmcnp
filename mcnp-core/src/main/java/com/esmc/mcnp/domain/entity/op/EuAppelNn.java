package com.esmc.mcnp.model.op;

import com.esmc.mcnp.model.others.EuProposition;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_appel_nn database table.
 *
 */
@Entity
@Table(name="eu_appel_nn")
@NamedQuery(name="EuAppelNn.findAll", query="SELECT e FROM EuAppelNn e")
public class EuAppelNn implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idAppelNn;
	private Integer allouer;
	private String codeCompte;
	private String codeMembreMorale;
	private double cumulNn;
	private Date dateAppel;
	private Date dateFin;
	private String designationAppel;
	private Integer disponible;
	private Long idUtilisateur;
	private double montantNn;
	private EuProposition euProposition;
	private List<EuDetailAppelNn> euDetailAppelNns;

	public EuAppelNn() {
	}


	@Id
	@Column(name="id_appel_nn", unique=true, nullable=false)
	public Long getIdAppelNn() {
		return this.idAppelNn;
	}

	public void setIdAppelNn(Long idAppelNn) {
		this.idAppelNn = idAppelNn;
	}


	public Integer getAllouer() {
		return this.allouer;
	}

	public void setAllouer(Integer allouer) {
		this.allouer = allouer;
	}


	@Column(name="code_compte", length=180)
	public String getCodeCompte() {
		return this.codeCompte;
	}

	public void setCodeCompte(String codeCompte) {
		this.codeCompte = codeCompte;
	}


	@Column(name="code_membre_morale", length=100)
	public String getCodeMembreMorale() {
		return this.codeMembreMorale;
	}

	public void setCodeMembreMorale(String codeMembreMorale) {
		this.codeMembreMorale = codeMembreMorale;
	}


	@Column(name="cumul_nn")
	public double getCumulNn() {
		return this.cumulNn;
	}

	public void setCumulNn(double cumulNn) {
		this.cumulNn = cumulNn;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_appel")
	public Date getDateAppel() {
		return this.dateAppel;
	}

	public void setDateAppel(Date dateAppel) {
		this.dateAppel = dateAppel;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_fin")
	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}


	@Column(name="designation_appel", length=100)
	public String getDesignationAppel() {
		return this.designationAppel;
	}

	public void setDesignationAppel(String designationAppel) {
		this.designationAppel = designationAppel;
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


	@Column(name="montant_nn")
	public double getMontantNn() {
		return this.montantNn;
	}

	public void setMontantNn(double montantNn) {
		this.montantNn = montantNn;
	}


	//bi-directional many-to-one association to EuProposition
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_proposition")
	public EuProposition getEuProposition() {
		return this.euProposition;
	}

	public void setEuProposition(EuProposition euProposition) {
		this.euProposition = euProposition;
	}


	//bi-directional many-to-one association to EuDetailAppelNn
	@OneToMany(mappedBy="euAppelNn")
	public List<EuDetailAppelNn> getEuDetailAppelNns() {
		return this.euDetailAppelNns;
	}

	public void setEuDetailAppelNns(List<EuDetailAppelNn> euDetailAppelNns) {
		this.euDetailAppelNns = euDetailAppelNns;
	}

	public EuDetailAppelNn addEuDetailAppelNn(EuDetailAppelNn euDetailAppelNn) {
		getEuDetailAppelNns().add(euDetailAppelNn);
		euDetailAppelNn.setEuAppelNn(this);

		return euDetailAppelNn;
	}

	public EuDetailAppelNn removeEuDetailAppelNn(EuDetailAppelNn euDetailAppelNn) {
		getEuDetailAppelNns().remove(euDetailAppelNn);
		euDetailAppelNn.setEuAppelNn(null);

		return euDetailAppelNn;
	}

}
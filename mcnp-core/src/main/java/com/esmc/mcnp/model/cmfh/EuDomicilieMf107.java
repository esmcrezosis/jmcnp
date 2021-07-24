package com.esmc.mcnp.model.cmfh;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.security.EuUtilisateur;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_domicilie_mf107 database table.
 * 
 */
@Entity
@Table(name="eu_domicilie_mf107")
@NamedQuery(name="EuDomicilieMf107.findAll", query="SELECT e FROM EuDomicilieMf107 e")
public class EuDomicilieMf107 implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idDom;
	private Date dateDom;
	private double etatDomiciliation;
	private Date heureDom;
	private double mtDomiciliation;
	private double mtDomicilie;
	private List<EuDetailDomicilieMf107> euDetailDomicilieMf107s;
	private EuUtilisateur euUtilisateur;
	private EuMembre euMembre;
	private EuMembreMorale euMembreMorale;

	public EuDomicilieMf107() {
	}


	@Id
	@Column(name="id_dom", unique=true, nullable=false)
	public double getIdDom() {
		return this.idDom;
	}

	public void setIdDom(double idDom) {
		this.idDom = idDom;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_dom")
	public Date getDateDom() {
		return this.dateDom;
	}

	public void setDateDom(Date dateDom) {
		this.dateDom = dateDom;
	}


	@Column(name="etat_domiciliation")
	public double getEtatDomiciliation() {
		return this.etatDomiciliation;
	}

	public void setEtatDomiciliation(double etatDomiciliation) {
		this.etatDomiciliation = etatDomiciliation;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="heure_dom")
	public Date getHeureDom() {
		return this.heureDom;
	}

	public void setHeureDom(Date heureDom) {
		this.heureDom = heureDom;
	}


	@Column(name="mt_domiciliation")
	public double getMtDomiciliation() {
		return this.mtDomiciliation;
	}

	public void setMtDomiciliation(double mtDomiciliation) {
		this.mtDomiciliation = mtDomiciliation;
	}


	@Column(name="mt_domicilie")
	public double getMtDomicilie() {
		return this.mtDomicilie;
	}

	public void setMtDomicilie(double mtDomicilie) {
		this.mtDomicilie = mtDomicilie;
	}


	//bi-directional many-to-one association to EuDetailDomicilieMf107
	@OneToMany(mappedBy="euDomicilieMf107")
	public List<EuDetailDomicilieMf107> getEuDetailDomicilieMf107s() {
		return this.euDetailDomicilieMf107s;
	}

	public void setEuDetailDomicilieMf107s(List<EuDetailDomicilieMf107> euDetailDomicilieMf107s) {
		this.euDetailDomicilieMf107s = euDetailDomicilieMf107s;
	}

	public EuDetailDomicilieMf107 addEuDetailDomicilieMf107(EuDetailDomicilieMf107 euDetailDomicilieMf107) {
		getEuDetailDomicilieMf107s().add(euDetailDomicilieMf107);
		euDetailDomicilieMf107.setEuDomicilieMf107(this);

		return euDetailDomicilieMf107;
	}

	public EuDetailDomicilieMf107 removeEuDetailDomicilieMf107(EuDetailDomicilieMf107 euDetailDomicilieMf107) {
		getEuDetailDomicilieMf107s().remove(euDetailDomicilieMf107);
		euDetailDomicilieMf107.setEuDomicilieMf107(null);

		return euDetailDomicilieMf107;
	}


	//bi-directional many-to-one association to EuUtilisateur
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_utilisateur")
	public EuUtilisateur getEuUtilisateur() {
		return this.euUtilisateur;
	}

	public void setEuUtilisateur(EuUtilisateur euUtilisateur) {
		this.euUtilisateur = euUtilisateur;
	}


	//bi-directional many-to-one association to EuMembre
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre")
	public EuMembre getEuMembre() {
		return this.euMembre;
	}

	public void setEuMembre(EuMembre euMembre) {
		this.euMembre = euMembre;
	}


	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre_morale")
	public EuMembreMorale getEuMembreMorale() {
		return this.euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}

}
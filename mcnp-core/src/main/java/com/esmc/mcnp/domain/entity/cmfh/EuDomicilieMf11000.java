package com.esmc.mcnp.domain.entity.cmfh;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_domicilie_mf11000 database table.
 * 
 */
@Entity
@Table(name="eu_domicilie_mf11000")
@NamedQuery(name="EuDomicilieMf11000.findAll", query="SELECT e FROM EuDomicilieMf11000 e")
public class EuDomicilieMf11000 implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idDomi;
	private Date dateDomi;
	private String etatDomi;
	private Date heureDomi;
	private double mtDomiciliation;
	private double mtDomicilie;
	private List<EuDetailDomicilieMf11000> euDetailDomicilieMf11000s;
	private EuMembreMorale euMembreMorale;
	private EuUtilisateur euUtilisateur;
	private EuMembre euMembre;

	public EuDomicilieMf11000() {
	}


	@Id
	@Column(name="id_domi", unique=true, nullable=false)
	public double getIdDomi() {
		return this.idDomi;
	}

	public void setIdDomi(double idDomi) {
		this.idDomi = idDomi;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_domi")
	public Date getDateDomi() {
		return this.dateDomi;
	}

	public void setDateDomi(Date dateDomi) {
		this.dateDomi = dateDomi;
	}


	@Column(name="etat_domi", length=4)
	public String getEtatDomi() {
		return this.etatDomi;
	}

	public void setEtatDomi(String etatDomi) {
		this.etatDomi = etatDomi;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="heure_domi")
	public Date getHeureDomi() {
		return this.heureDomi;
	}

	public void setHeureDomi(Date heureDomi) {
		this.heureDomi = heureDomi;
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


	//bi-directional many-to-one association to EuDetailDomicilieMf11000
	@OneToMany(mappedBy="euDomicilieMf11000")
	public List<EuDetailDomicilieMf11000> getEuDetailDomicilieMf11000s() {
		return this.euDetailDomicilieMf11000s;
	}

	public void setEuDetailDomicilieMf11000s(List<EuDetailDomicilieMf11000> euDetailDomicilieMf11000s) {
		this.euDetailDomicilieMf11000s = euDetailDomicilieMf11000s;
	}

	public EuDetailDomicilieMf11000 addEuDetailDomicilieMf11000(EuDetailDomicilieMf11000 euDetailDomicilieMf11000) {
		getEuDetailDomicilieMf11000s().add(euDetailDomicilieMf11000);
		euDetailDomicilieMf11000.setEuDomicilieMf11000(this);

		return euDetailDomicilieMf11000;
	}

	public EuDetailDomicilieMf11000 removeEuDetailDomicilieMf11000(EuDetailDomicilieMf11000 euDetailDomicilieMf11000) {
		getEuDetailDomicilieMf11000s().remove(euDetailDomicilieMf11000);
		euDetailDomicilieMf11000.setEuDomicilieMf11000(null);

		return euDetailDomicilieMf11000;
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

}
package com.esmc.mcnp.domain.entity.cmfh;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_detail_mf11000 database table.
 * 
 */
@Entity
@Table(name="eu_detail_mf11000")
@NamedQuery(name="EuDetailMf11000.findAll", query="SELECT e FROM EuDetailMf11000 e")
public class EuDetailMf11000 implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idMf11000;
	private String cel;
	private String codeMembre;
	private String creditcode;
	private Date dateMf11000;
	private double montApport;
	private double pourcentage;
	private double proprietaire;
	private List<EuDetailDomicilieMf11000> euDetailDomicilieMf11000s;
	private EuMembreFondateur11000 euMembreFondateur11000;
	private EuUtilisateur euUtilisateur;

	public EuDetailMf11000() {
	}


	@Id
	@Column(name="id_mf11000", unique=true, nullable=false)
	public Long getIdMf11000() {
		return this.idMf11000;
	}

	public void setIdMf11000(Long idMf11000) {
		this.idMf11000 = idMf11000;
	}


	@Column(length=60)
	public String getCel() {
		return this.cel;
	}

	public void setCel(String cel) {
		this.cel = cel;
	}


	@Column(name="code_membre", length=100)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Column(length=80)
	public String getCreditcode() {
		return this.creditcode;
	}

	public void setCreditcode(String creditcode) {
		this.creditcode = creditcode;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_mf11000")
	public Date getDateMf11000() {
		return this.dateMf11000;
	}

	public void setDateMf11000(Date dateMf11000) {
		this.dateMf11000 = dateMf11000;
	}


	@Column(name="mont_apport")
	public double getMontApport() {
		return this.montApport;
	}

	public void setMontApport(double montApport) {
		this.montApport = montApport;
	}


	public double getPourcentage() {
		return this.pourcentage;
	}

	public void setPourcentage(double pourcentage) {
		this.pourcentage = pourcentage;
	}


	public double getProprietaire() {
		return this.proprietaire;
	}

	public void setProprietaire(double proprietaire) {
		this.proprietaire = proprietaire;
	}


	//bi-directional many-to-one association to EuDetailDomicilieMf11000
	@OneToMany(mappedBy="euDetailMf11000")
	public List<EuDetailDomicilieMf11000> getEuDetailDomicilieMf11000s() {
		return this.euDetailDomicilieMf11000s;
	}

	public void setEuDetailDomicilieMf11000s(List<EuDetailDomicilieMf11000> euDetailDomicilieMf11000s) {
		this.euDetailDomicilieMf11000s = euDetailDomicilieMf11000s;
	}

	public EuDetailDomicilieMf11000 addEuDetailDomicilieMf11000(EuDetailDomicilieMf11000 euDetailDomicilieMf11000) {
		getEuDetailDomicilieMf11000s().add(euDetailDomicilieMf11000);
		euDetailDomicilieMf11000.setEuDetailMf11000(this);

		return euDetailDomicilieMf11000;
	}

	public EuDetailDomicilieMf11000 removeEuDetailDomicilieMf11000(EuDetailDomicilieMf11000 euDetailDomicilieMf11000) {
		getEuDetailDomicilieMf11000s().remove(euDetailDomicilieMf11000);
		euDetailDomicilieMf11000.setEuDetailMf11000(null);

		return euDetailDomicilieMf11000;
	}


	//bi-directional many-to-one association to EuMembreFondateur11000
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="num_bon")
	public EuMembreFondateur11000 getEuMembreFondateur11000() {
		return this.euMembreFondateur11000;
	}

	public void setEuMembreFondateur11000(EuMembreFondateur11000 euMembreFondateur11000) {
		this.euMembreFondateur11000 = euMembreFondateur11000;
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

}
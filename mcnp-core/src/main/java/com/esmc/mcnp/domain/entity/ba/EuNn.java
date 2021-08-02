package com.esmc.mcnp.domain.entity.ba;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.obpsd.EuTypeNn;

import java.util.Date;


/**
 * The persistent class for the eu_nn database table.
 *
 */
@Entity
@Table(name="eu_nn")
@NamedQuery(name="EuNn.findAll", query="SELECT e FROM EuNn e")
public class EuNn implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idNn;
	private Date dateEmission;
	private Long idUtilisateur;
	private double montantEmis;
	private double montantRemb;
	private double soldeNn;
	private String typeEmission;
	private EuTypeNn euTypeNn;
	private EuMembreMorale euMembreMorale;

	public EuNn() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_nn", unique=true, nullable=false)
	public Long getIdNn() {
		return this.idNn;
	}

	public void setIdNn(Long idNn) {
		this.idNn = idNn;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_emission")
	public Date getDateEmission() {
		return this.dateEmission;
	}

	public void setDateEmission(Date dateEmission) {
		this.dateEmission = dateEmission;
	}


	@Column(name="id_utilisateur")
	public Long getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	@Column(name="montant_emis")
	public double getMontantEmis() {
		return this.montantEmis;
	}

	public void setMontantEmis(double montantEmis) {
		this.montantEmis = montantEmis;
	}


	@Column(name="montant_remb")
	public double getMontantRemb() {
		return this.montantRemb;
	}

	public void setMontantRemb(double montantRemb) {
		this.montantRemb = montantRemb;
	}


	@Column(name="solde_nn")
	public double getSoldeNn() {
		return this.soldeNn;
	}

	public void setSoldeNn(double soldeNn) {
		this.soldeNn = soldeNn;
	}


	@Column(name="type_emission", length=80)
	public String getTypeEmission() {
		return this.typeEmission;
	}

	public void setTypeEmission(String typeEmission) {
		this.typeEmission = typeEmission;
	}


	//bi-directional many-to-one association to EuTypeNn
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_type_nn")
	public EuTypeNn getEuTypeNn() {
		return this.euTypeNn;
	}

	public void setEuTypeNn(EuTypeNn euTypeNn) {
		this.euTypeNn = euTypeNn;
	}


	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="emetteur_nn")
	public EuMembreMorale getEuMembreMorale() {
		return this.euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}

}
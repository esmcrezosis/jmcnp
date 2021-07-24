package com.esmc.mcnp.model.op;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_projet database table.
 * 
 */
@Entity
@Table(name="eu_projet")
@NamedQuery(name="EuProjet.findAll", query="SELECT e FROM EuProjet e")
public class EuProjet implements Serializable {
	private static final long serialVersionUID = 1L;
	private int projetId;
	private String codeZone;
	private int idCanton;
	private int idPays;
	private int idPrefecture;
	private int idRegion;
	private String projetCentrale;
	private String projetCodeMembre;
	private Date projetDate;
	private String projetDescription;
	private String projetLibelle;
	private double projetMontant;
	private double projetMontantFinal;
	private String projetObservation;
	private String projetStockage;
	private String projetType;
	private int projetUtilisateur;
	private Boolean publier;

	public EuProjet() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="projet_id")
	public int getProjetId() {
		return this.projetId;
	}

	public void setProjetId(int projetId) {
		this.projetId = projetId;
	}


	@Column(name="code_zone")
	public String getCodeZone() {
		return this.codeZone;
	}

	public void setCodeZone(String codeZone) {
		this.codeZone = codeZone;
	}


	@Column(name="id_canton")
	public int getIdCanton() {
		return this.idCanton;
	}

	public void setIdCanton(int idCanton) {
		this.idCanton = idCanton;
	}


	@Column(name="id_pays")
	public int getIdPays() {
		return this.idPays;
	}

	public void setIdPays(int idPays) {
		this.idPays = idPays;
	}


	@Column(name="id_prefecture")
	public int getIdPrefecture() {
		return this.idPrefecture;
	}

	public void setIdPrefecture(int idPrefecture) {
		this.idPrefecture = idPrefecture;
	}


	@Column(name="id_region")
	public int getIdRegion() {
		return this.idRegion;
	}

	public void setIdRegion(int idRegion) {
		this.idRegion = idRegion;
	}


	@Column(name="projet_centrale")
	public String getProjetCentrale() {
		return this.projetCentrale;
	}

	public void setProjetCentrale(String projetCentrale) {
		this.projetCentrale = projetCentrale;
	}


	@Column(name="projet_code_membre")
	public String getProjetCodeMembre() {
		return this.projetCodeMembre;
	}

	public void setProjetCodeMembre(String projetCodeMembre) {
		this.projetCodeMembre = projetCodeMembre;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="projet_date")
	public Date getProjetDate() {
		return this.projetDate;
	}

	public void setProjetDate(Date projetDate) {
		this.projetDate = projetDate;
	}


	@Lob
	@Column(name="projet_description")
	public String getProjetDescription() {
		return this.projetDescription;
	}

	public void setProjetDescription(String projetDescription) {
		this.projetDescription = projetDescription;
	}


	@Column(name="projet_libelle")
	public String getProjetLibelle() {
		return this.projetLibelle;
	}

	public void setProjetLibelle(String projetLibelle) {
		this.projetLibelle = projetLibelle;
	}


	@Column(name="projet_montant")
	public double getProjetMontant() {
		return this.projetMontant;
	}

	public void setProjetMontant(double projetMontant) {
		this.projetMontant = projetMontant;
	}


	@Column(name="projet_montant_final")
	public double getProjetMontantFinal() {
		return this.projetMontantFinal;
	}

	public void setProjetMontantFinal(double projetMontantFinal) {
		this.projetMontantFinal = projetMontantFinal;
	}


	@Lob
	@Column(name="projet_observation")
	public String getProjetObservation() {
		return this.projetObservation;
	}

	public void setProjetObservation(String projetObservation) {
		this.projetObservation = projetObservation;
	}


	@Column(name="projet_stockage")
	public String getProjetStockage() {
		return this.projetStockage;
	}

	public void setProjetStockage(String projetStockage) {
		this.projetStockage = projetStockage;
	}


	@Column(name="projet_type")
	public String getProjetType() {
		return this.projetType;
	}

	public void setProjetType(String projetType) {
		this.projetType = projetType;
	}


	@Column(name="projet_utilisateur")
	public int getProjetUtilisateur() {
		return this.projetUtilisateur;
	}

	public void setProjetUtilisateur(int projetUtilisateur) {
		this.projetUtilisateur = projetUtilisateur;
	}


	public Boolean getPublier() {
		return this.publier;
	}

	public void setPublier(Boolean publier) {
		this.publier = publier;
	}

}
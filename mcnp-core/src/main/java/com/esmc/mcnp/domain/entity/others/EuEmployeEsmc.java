package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_employe_esmc database table.
 * 
 */
@Entity
@Table(name="eu_employe_esmc")
@NamedQuery(name="EuEmployeEsmc.findAll", query="SELECT e FROM EuEmployeEsmc e")
public class EuEmployeEsmc implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idEmploye;
	private double absJustif;
	private double absNonjustif;
	private String adresse;
	private double anciennete;
	private String boitePostale;
	private String codeEmploye;
	private double conges;
	private String dateCreation;
	private String dateEmbauche;
	private String dateNaissance;
	private String email;
	private double idCategorie;
	private double idDepartement;
	private double idEmploi;
	private double idTypecontrat;
	private String lieuNaissance;
	private String matricule;
	private String modePaiement;
	private String nationalite;
	private String nomEmploye;
	private String numCnssEmploye;
	private String numCnssEmployeur;
	private String numCpte;
	private String numIdEsmc;
	private double persCharge;
	private String prenomEmploye;
	private double primeAnciennete;
	private double primeAssiduiteRendement;
	private double primeResponsabilite;
	private double rappSalaire;
	private double salaireBase;
	private String sexe;
	private String sitFam;
	private double sursalaire;
	private String telephone;
	private String txAbatforfait;
	private String txAbatfrsprof;
	private String txIrpp;
	private String txParpatrcnss;
	private String txParsalcnss;
	private String txRetperscharge;
	private String txTcs;

	public EuEmployeEsmc() {
	}


	@Id
	@Column(name="id_employe", unique=true, nullable=false)
	public double getIdEmploye() {
		return this.idEmploye;
	}

	public void setIdEmploye(double idEmploye) {
		this.idEmploye = idEmploye;
	}


	@Column(name="abs_justif")
	public double getAbsJustif() {
		return this.absJustif;
	}

	public void setAbsJustif(double absJustif) {
		this.absJustif = absJustif;
	}


	@Column(name="abs_nonjustif")
	public double getAbsNonjustif() {
		return this.absNonjustif;
	}

	public void setAbsNonjustif(double absNonjustif) {
		this.absNonjustif = absNonjustif;
	}


	@Column(length=250)
	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	public double getAnciennete() {
		return this.anciennete;
	}

	public void setAnciennete(double anciennete) {
		this.anciennete = anciennete;
	}


	@Column(name="boite_postale", length=200)
	public String getBoitePostale() {
		return this.boitePostale;
	}

	public void setBoitePostale(String boitePostale) {
		this.boitePostale = boitePostale;
	}


	@Column(name="code_employe", length=80)
	public String getCodeEmploye() {
		return this.codeEmploye;
	}

	public void setCodeEmploye(String codeEmploye) {
		this.codeEmploye = codeEmploye;
	}


	public double getConges() {
		return this.conges;
	}

	public void setConges(double conges) {
		this.conges = conges;
	}


	@Column(name="date_creation", length=80)
	public String getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}


	@Column(name="date_embauche", length=80)
	public String getDateEmbauche() {
		return this.dateEmbauche;
	}

	public void setDateEmbauche(String dateEmbauche) {
		this.dateEmbauche = dateEmbauche;
	}


	@Column(name="date_naissance", length=80)
	public String getDateNaissance() {
		return this.dateNaissance;
	}

	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}


	@Column(length=200)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Column(name="id_categorie")
	public double getIdCategorie() {
		return this.idCategorie;
	}

	public void setIdCategorie(double idCategorie) {
		this.idCategorie = idCategorie;
	}


	@Column(name="id_departement")
	public double getIdDepartement() {
		return this.idDepartement;
	}

	public void setIdDepartement(double idDepartement) {
		this.idDepartement = idDepartement;
	}


	@Column(name="id_emploi")
	public double getIdEmploi() {
		return this.idEmploi;
	}

	public void setIdEmploi(double idEmploi) {
		this.idEmploi = idEmploi;
	}


	@Column(name="id_typecontrat")
	public double getIdTypecontrat() {
		return this.idTypecontrat;
	}

	public void setIdTypecontrat(double idTypecontrat) {
		this.idTypecontrat = idTypecontrat;
	}


	@Column(name="lieu_naissance", length=200)
	public String getLieuNaissance() {
		return this.lieuNaissance;
	}

	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}


	@Column(length=60)
	public String getMatricule() {
		return this.matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}


	@Column(name="mode_paiement", length=80)
	public String getModePaiement() {
		return this.modePaiement;
	}

	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}


	@Column(length=200)
	public String getNationalite() {
		return this.nationalite;
	}

	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}


	@Column(name="nom_employe", length=150)
	public String getNomEmploye() {
		return this.nomEmploye;
	}

	public void setNomEmploye(String nomEmploye) {
		this.nomEmploye = nomEmploye;
	}


	@Column(name="num_cnss_employe", length=200)
	public String getNumCnssEmploye() {
		return this.numCnssEmploye;
	}

	public void setNumCnssEmploye(String numCnssEmploye) {
		this.numCnssEmploye = numCnssEmploye;
	}


	@Column(name="num_cnss_employeur", length=200)
	public String getNumCnssEmployeur() {
		return this.numCnssEmployeur;
	}

	public void setNumCnssEmployeur(String numCnssEmployeur) {
		this.numCnssEmployeur = numCnssEmployeur;
	}


	@Column(name="num_cpte", length=200)
	public String getNumCpte() {
		return this.numCpte;
	}

	public void setNumCpte(String numCpte) {
		this.numCpte = numCpte;
	}


	@Column(name="num_id_esmc", length=180)
	public String getNumIdEsmc() {
		return this.numIdEsmc;
	}

	public void setNumIdEsmc(String numIdEsmc) {
		this.numIdEsmc = numIdEsmc;
	}


	@Column(name="pers_charge")
	public double getPersCharge() {
		return this.persCharge;
	}

	public void setPersCharge(double persCharge) {
		this.persCharge = persCharge;
	}


	@Column(name="prenom_employe", length=250)
	public String getPrenomEmploye() {
		return this.prenomEmploye;
	}

	public void setPrenomEmploye(String prenomEmploye) {
		this.prenomEmploye = prenomEmploye;
	}


	@Column(name="prime_anciennete")
	public double getPrimeAnciennete() {
		return this.primeAnciennete;
	}

	public void setPrimeAnciennete(double primeAnciennete) {
		this.primeAnciennete = primeAnciennete;
	}


	@Column(name="prime_assiduite_rendement")
	public double getPrimeAssiduiteRendement() {
		return this.primeAssiduiteRendement;
	}

	public void setPrimeAssiduiteRendement(double primeAssiduiteRendement) {
		this.primeAssiduiteRendement = primeAssiduiteRendement;
	}


	@Column(name="prime_responsabilite")
	public double getPrimeResponsabilite() {
		return this.primeResponsabilite;
	}

	public void setPrimeResponsabilite(double primeResponsabilite) {
		this.primeResponsabilite = primeResponsabilite;
	}


	@Column(name="rapp_salaire")
	public double getRappSalaire() {
		return this.rappSalaire;
	}

	public void setRappSalaire(double rappSalaire) {
		this.rappSalaire = rappSalaire;
	}


	@Column(name="salaire_base")
	public double getSalaireBase() {
		return this.salaireBase;
	}

	public void setSalaireBase(double salaireBase) {
		this.salaireBase = salaireBase;
	}


	@Column(length=4)
	public String getSexe() {
		return this.sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}


	@Column(name="sit_fam", length=80)
	public String getSitFam() {
		return this.sitFam;
	}

	public void setSitFam(String sitFam) {
		this.sitFam = sitFam;
	}


	public double getSursalaire() {
		return this.sursalaire;
	}

	public void setSursalaire(double sursalaire) {
		this.sursalaire = sursalaire;
	}


	@Column(length=80)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	@Column(name="tx_abatforfait", length=40)
	public String getTxAbatforfait() {
		return this.txAbatforfait;
	}

	public void setTxAbatforfait(String txAbatforfait) {
		this.txAbatforfait = txAbatforfait;
	}


	@Column(name="tx_abatfrsprof", length=40)
	public String getTxAbatfrsprof() {
		return this.txAbatfrsprof;
	}

	public void setTxAbatfrsprof(String txAbatfrsprof) {
		this.txAbatfrsprof = txAbatfrsprof;
	}


	@Column(name="tx_irpp", length=40)
	public String getTxIrpp() {
		return this.txIrpp;
	}

	public void setTxIrpp(String txIrpp) {
		this.txIrpp = txIrpp;
	}


	@Column(name="tx_parpatrcnss", length=40)
	public String getTxParpatrcnss() {
		return this.txParpatrcnss;
	}

	public void setTxParpatrcnss(String txParpatrcnss) {
		this.txParpatrcnss = txParpatrcnss;
	}


	@Column(name="tx_parsalcnss", length=40)
	public String getTxParsalcnss() {
		return this.txParsalcnss;
	}

	public void setTxParsalcnss(String txParsalcnss) {
		this.txParsalcnss = txParsalcnss;
	}


	@Column(name="tx_retperscharge", length=40)
	public String getTxRetperscharge() {
		return this.txRetperscharge;
	}

	public void setTxRetperscharge(String txRetperscharge) {
		this.txRetperscharge = txRetperscharge;
	}


	@Column(name="tx_tcs", length=40)
	public String getTxTcs() {
		return this.txTcs;
	}

	public void setTxTcs(String txTcs) {
		this.txTcs = txTcs;
	}

}
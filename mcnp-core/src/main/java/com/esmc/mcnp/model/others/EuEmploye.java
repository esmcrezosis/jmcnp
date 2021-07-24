package com.esmc.mcnp.model.others;

import com.esmc.mcnp.model.cm.EuMembre;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_employe database table.
 * 
 */
@Entity
@Table(name="eu_employe")
@NamedQuery(name="EuEmploye.findAll", query="SELECT e FROM EuEmploye e")
public class EuEmploye implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idEmploye;
	private double cnss;
	private String codeMembreEmployeur;
	private Date dateDeclaration;
	private double idUtilisateur;
	private double montSalaire;
	private EuMembre euMembre;
	private List<EuMouvement> euMouvements;

	public EuEmploye() {
	}


	@Id
	@Column(name="id_employe", unique=true, nullable=false)
	public double getIdEmploye() {
		return this.idEmploye;
	}

	public void setIdEmploye(double idEmploye) {
		this.idEmploye = idEmploye;
	}


	public double getCnss() {
		return this.cnss;
	}

	public void setCnss(double cnss) {
		this.cnss = cnss;
	}


	@Column(name="code_membre_employeur", length=100)
	public String getCodeMembreEmployeur() {
		return this.codeMembreEmployeur;
	}

	public void setCodeMembreEmployeur(String codeMembreEmployeur) {
		this.codeMembreEmployeur = codeMembreEmployeur;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_declaration")
	public Date getDateDeclaration() {
		return this.dateDeclaration;
	}

	public void setDateDeclaration(Date dateDeclaration) {
		this.dateDeclaration = dateDeclaration;
	}


	@Column(name="id_utilisateur")
	public double getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(double idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	@Column(name="mont_salaire")
	public double getMontSalaire() {
		return this.montSalaire;
	}

	public void setMontSalaire(double montSalaire) {
		this.montSalaire = montSalaire;
	}


	//bi-directional many-to-one association to EuMembre
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre_employe")
	public EuMembre getEuMembre() {
		return this.euMembre;
	}

	public void setEuMembre(EuMembre euMembre) {
		this.euMembre = euMembre;
	}


	//bi-directional many-to-one association to EuMouvement
	@OneToMany(mappedBy="euEmploye")
	public List<EuMouvement> getEuMouvements() {
		return this.euMouvements;
	}

	public void setEuMouvements(List<EuMouvement> euMouvements) {
		this.euMouvements = euMouvements;
	}

	public EuMouvement addEuMouvement(EuMouvement euMouvement) {
		getEuMouvements().add(euMouvement);
		euMouvement.setEuEmploye(this);

		return euMouvement;
	}

	public EuMouvement removeEuMouvement(EuMouvement euMouvement) {
		getEuMouvements().remove(euMouvement);
		euMouvement.setEuEmploye(null);

		return euMouvement;
	}

}
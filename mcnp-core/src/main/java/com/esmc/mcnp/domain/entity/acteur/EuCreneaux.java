package com.esmc.mcnp.domain.entity.acteur;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_creneaux database table.
 * 
 */
@Entity
@Table(name="eu_creneaux")
@NamedQuery(name="EuCreneaux.findAll", query="SELECT e FROM EuCreneaux e")
public class EuCreneaux implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeCreneau;
	private String codeGacFiliere;
	private Date dateCreation;
	private String groupe;
	private String nomCreneau;
	private List<EuActeursCreneaux> euActeursCreneauxs;
	private EuMembreMorale euMembreMorale;
	private EuTypeCreneau euTypeCreneau;
	private EuUtilisateur euUtilisateur;
	private EuMembre euMembre;

	public EuCreneaux() {
	}


	@Id
	@Column(name="code_creneau", unique=true, nullable=false, length=100)
	public String getCodeCreneau() {
		return this.codeCreneau;
	}

	public void setCodeCreneau(String codeCreneau) {
		this.codeCreneau = codeCreneau;
	}


	@Column(name="code_gac_filiere", length=100)
	public String getCodeGacFiliere() {
		return this.codeGacFiliere;
	}

	public void setCodeGacFiliere(String codeGacFiliere) {
		this.codeGacFiliere = codeGacFiliere;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_creation")
	public Date getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	@Column(length=100)
	public String getGroupe() {
		return this.groupe;
	}

	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}


	@Column(name="nom_creneau", length=255)
	public String getNomCreneau() {
		return this.nomCreneau;
	}

	public void setNomCreneau(String nomCreneau) {
		this.nomCreneau = nomCreneau;
	}


	//bi-directional many-to-one association to EuActeursCreneaux
	@OneToMany(mappedBy="euCreneaux")
	public List<EuActeursCreneaux> getEuActeursCreneauxs() {
		return this.euActeursCreneauxs;
	}

	public void setEuActeursCreneauxs(List<EuActeursCreneaux> euActeursCreneauxs) {
		this.euActeursCreneauxs = euActeursCreneauxs;
	}

	public EuActeursCreneaux addEuActeursCreneaux(EuActeursCreneaux euActeursCreneaux) {
		getEuActeursCreneauxs().add(euActeursCreneaux);
		euActeursCreneaux.setEuCreneaux(this);

		return euActeursCreneaux;
	}

	public EuActeursCreneaux removeEuActeursCreneaux(EuActeursCreneaux euActeursCreneaux) {
		getEuActeursCreneauxs().remove(euActeursCreneaux);
		euActeursCreneaux.setEuCreneaux(null);

		return euActeursCreneaux;
	}


	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre")
	public EuMembreMorale getEuMembreMorale() {
		return this.euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}


	//bi-directional many-to-one association to EuTypeCreneau
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_type_creneau")
	public EuTypeCreneau getEuTypeCreneau() {
		return this.euTypeCreneau;
	}

	public void setEuTypeCreneau(EuTypeCreneau euTypeCreneau) {
		this.euTypeCreneau = euTypeCreneau;
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
	@JoinColumn(name="code_membre_gestionnaire")
	public EuMembre getEuMembre() {
		return this.euMembre;
	}

	public void setEuMembre(EuMembre euMembre) {
		this.euMembre = euMembre;
	}

}
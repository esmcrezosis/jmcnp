package com.esmc.mcnp.domain.entity.smcipn;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuTransfertNr
 *
 */
@Entity
@Table(name = "eu_transfert_nr")
public class EuTransfertNr implements Serializable {

	private Long idTransfertNr;
	private Date dateTransfert;
	private String libelleTransfert;
	private String numAppelOffre;
	private String numDoc;
	private String acteurSource;
	private String acteurDest;
	private double montTransfert;
	private String codeMembreBenef;
	private String codeMembreTransfert;
	private String typeNr;
	private Long idUtilisateur;
	private static final long serialVersionUID = 1L;

	public EuTransfertNr() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_transfert_nr")
	public Long getIdTransfertNr() {
		return this.idTransfertNr;
	}

	public void setIdTransfertNr(Long idTransfertNr) {
		this.idTransfertNr = idTransfertNr;
	}

	@Column(name = "date_transfert")
	public Date getDateTransfert() {
		return this.dateTransfert;
	}

	public void setDateTransfert(Date dateTransfert) {
		this.dateTransfert = dateTransfert;
	}

	@Column(name = "mont_transfert")
	public double getMontTransfert() {
		return this.montTransfert;
	}

	public void setMontTransfert(double montTransfert) {
		this.montTransfert = montTransfert;
	}

	@Column(name = "code_membre_benef")
	public String getCodeMembreBenef() {
		return this.codeMembreBenef;
	}

	public void setCodeMembreBenef(String codeMembreBenef) {
		this.codeMembreBenef = codeMembreBenef;
	}

	@Column(name = "code_membre_transfert")
	public String getCodeMembreTransfert() {
		return this.codeMembreTransfert;
	}

	public void setCodeMembreTransfert(String codeMembreTransfert) {
		this.codeMembreTransfert = codeMembreTransfert;
	}

	@Column(name = "type_nr")
	public String getTypeNr() {
		return this.typeNr;
	}

	public void setTypeNr(String typeNr) {
		this.typeNr = typeNr;
	}

	@Column(name = "id_utilisateur")
	public Long getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	@Column(name = "libelle_transfert")
	public String getLibelleTransfert() {
		return libelleTransfert;
	}

	public void setLibelleTransfert(String libelleTransfert) {
		this.libelleTransfert = libelleTransfert;
	}

	@Column(name = "num_appel_offre")
	public String getNumAppelOffre() {
		return numAppelOffre;
	}

	public void setNumAppelOffre(String numAppelOffre) {
		this.numAppelOffre = numAppelOffre;
	}

	@Column(name = "num_doc")
	public String getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}

	@Column(name = "acteur_source")
	public String getActeurSource() {
		return acteurSource;
	}

	public void setActeurSource(String acteurSource) {
		this.acteurSource = acteurSource;
	}

	@Column(name = "acteur_dest")
	public String getActeurDest() {
		return acteurDest;
	}

	public void setActeurDest(String acteurDest) {
		this.acteurDest = acteurDest;
	}

}

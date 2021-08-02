package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_demande database table.
 * 
 */
@Entity
@Table(name="eu_demande")
@NamedQuery(name="EuDemande.findAll", query="SELECT e FROM EuDemande e")
public class EuDemande implements Serializable {
	private static final long serialVersionUID = 1L;
	private long idDemande;
	private String codeMembreMorale;
	private Date dateDemande;
	private String descriptionDemande;
	private double livrer;
	private String objetDemande;
	private double publier;

	public EuDemande() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_demande", unique=true, nullable=false)
	public long getIdDemande() {
		return this.idDemande;
	}

	public void setIdDemande(long idDemande) {
		this.idDemande = idDemande;
	}


	@Column(name="code_membre_morale", length=25)
	public String getCodeMembreMorale() {
		return this.codeMembreMorale;
	}

	public void setCodeMembreMorale(String codeMembreMorale) {
		this.codeMembreMorale = codeMembreMorale;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_demande")
	public Date getDateDemande() {
		return this.dateDemande;
	}

	public void setDateDemande(Date dateDemande) {
		this.dateDemande = dateDemande;
	}


	@Column(name="description_demande", length=255)
	public String getDescriptionDemande() {
		return this.descriptionDemande;
	}

	public void setDescriptionDemande(String descriptionDemande) {
		this.descriptionDemande = descriptionDemande;
	}


	public double getLivrer() {
		return this.livrer;
	}

	public void setLivrer(double livrer) {
		this.livrer = livrer;
	}


	@Column(name="objet_demande", length=255)
	public String getObjetDemande() {
		return this.objetDemande;
	}

	public void setObjetDemande(String objetDemande) {
		this.objetDemande = objetDemande;
	}


	public double getPublier() {
		return this.publier;
	}

	public void setPublier(double publier) {
		this.publier = publier;
	}

}
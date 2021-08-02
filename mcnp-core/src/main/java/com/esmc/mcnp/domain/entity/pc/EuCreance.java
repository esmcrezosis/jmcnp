package com.esmc.mcnp.domain.entity.pc;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Entity implementation class for Entity: EuCreances
 *
 */
@Entity
@Table(name = "eu_creances")
public class EuCreance implements Serializable {

	private Long idCreance;
	private Date dateCreance;
	private String codeMembreDeb;
	private String codeMembreCred;
	private Double montCreance;
	private Date dateReglement;
	private String modeReglement;
	private String descriptionCreance;
	private String etatCreance;
	private boolean payer;
	private Long idUtilisateur;
	private static final long serialVersionUID = 1L;

	public EuCreance() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_creance")
	public Long getIdCreance() {
		return this.idCreance;
	}

	public void setIdCreance(Long idCreance) {
		this.idCreance = idCreance;
	}


	@DateTimeFormat(pattern ="dd/mm/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "date_creance")
	public Date getDateCreance() {
		return this.dateCreance;
	}

	public void setDateCreance(Date dateCreance) {
		this.dateCreance = dateCreance;
	}

	@Column(name = "code_membre_deb")
	public String getCodeMembreDeb() {
		return this.codeMembreDeb;
	}

	public void setCodeMembreDeb(String codeMembreDeb) {
		this.codeMembreDeb = codeMembreDeb;
	}

	@Column(name = "code_membre_cred")
	public String getCodeMembreCred() {
		return this.codeMembreCred;
	}

	public void setCodeMembreCred(String codeMembreCred) {
		this.codeMembreCred = codeMembreCred;
	}

	@Column(name = "montant_creance")
	public Double getMontCreance() {
		return this.montCreance;
	}

	public void setMontCreance(Double montCreance) {
		this.montCreance = montCreance;
	}

	@DateTimeFormat(pattern ="dd/mm/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "date_reglement")
	public Date getDateReglement() {
		return this.dateReglement;
	}

	public void setDateReglement(Date dateReglement) {
		this.dateReglement = dateReglement;
	}

	@Column(name = "mode_reglement")
	public String getModeReglement() {
		return this.modeReglement;
	}

	public void setModeReglement(String modeReglement) {
		this.modeReglement = modeReglement;
	}

	@Column(name = "description_creance")
	public String getDescriptionCreance() {
		return this.descriptionCreance;
	}

	public void setDescriptionCreance(String descriptionCreance) {
		this.descriptionCreance = descriptionCreance;
	}

	@Column(name = "etat_creance")
	public String getEtatCreance() {
		return this.etatCreance;
	}

	public void setEtatCreance(String etatCreance) {
		this.etatCreance = etatCreance;
	}

	@Column(name = "payer")
	public boolean isPayer() {
		return payer;
	}

	public void setPayer(boolean payer) {
		this.payer = payer;
	}

	@Column(name = "id_utilisateur")
	public Long getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

}

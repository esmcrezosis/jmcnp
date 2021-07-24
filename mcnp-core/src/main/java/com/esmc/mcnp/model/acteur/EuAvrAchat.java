package com.esmc.mcnp.model.acteur;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuAvrAchat
 *
 */
@Entity
@Table(name = "eu_avr_achat")
public class EuAvrAchat implements Serializable {

	private Integer id;
	private String codeMembreAcheteur;
	private String referenceAvr;
	private Date dateAchat;
	private String modePaiement;
	private Double montantTotalAvr;
	private static final long serialVersionUID = 1L;

	public EuAvrAchat() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "code_membre_acheteur")
	public String getCodeMembreAcheteur() {
		return this.codeMembreAcheteur;
	}

	public void setCodeMembreAcheteur(String codeMembreAcheteur) {
		this.codeMembreAcheteur = codeMembreAcheteur;
	}

	@Column(name = "reference_avr")
	public String getReferenceAvr() {
		return this.referenceAvr;
	}

	public void setReferenceAvr(String referenceAvr) {
		this.referenceAvr = referenceAvr;
	}

	@Column(name = "date_achat")
	public Date getDateAchat() {
		return this.dateAchat;
	}

	public void setDateAchat(Date dateAchat) {
		this.dateAchat = dateAchat;
	}

	@Column(name = "mode_paiement")
	public String getModePaiement() {
		return this.modePaiement;
	}

	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}

	@Column(name = "montant_total_avr_produit_achat")
	public Double getMontantTotalAvr() {
		return this.montantTotalAvr;
	}

	public void setMontantTotalAvr(Double montantTotalAvr) {
		this.montantTotalAvr = montantTotalAvr;
	}

}

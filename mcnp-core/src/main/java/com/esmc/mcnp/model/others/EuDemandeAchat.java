package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the eu_demande_achat database table.
 * 
 */
@Entity
@Table(name = "eu_demande_achat")
@NamedQuery(name = "EuDemandeAchat.findAll", query = "SELECT e FROM EuDemandeAchat e")
public class EuDemandeAchat implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idDemandeAchat;
	private String codeMembre;
	private Date dateDemande;
	private String libelleDemandeAchat;
	private Integer livrer;
	private double montantDemandeAchat;
	private String referenceDemandeAchat;
	private Integer rejet;
	private Integer validerDown;
	private Integer validerUp;
	private List<EuDetailDemandeAchat> euDetailDemandeAchats;

	public EuDemandeAchat() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_demande_achat")
	public Long getIdDemandeAchat() {
		return this.idDemandeAchat;
	}

	public void setIdDemandeAchat(Long idDemandeAchat) {
		this.idDemandeAchat = idDemandeAchat;
	}

	@Column(name = "code_membre")
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_demande")
	public Date getDateDemande() {
		return this.dateDemande;
	}

	public void setDateDemande(Date dateDemande) {
		this.dateDemande = dateDemande;
	}

	@Column(name = "libelle_demande_achat")
	public String getLibelleDemandeAchat() {
		return this.libelleDemandeAchat;
	}

	public void setLibelleDemandeAchat(String libelleDemandeAchat) {
		this.libelleDemandeAchat = libelleDemandeAchat;
	}

	public Integer getLivrer() {
		return this.livrer;
	}

	public void setLivrer(Integer livrer) {
		this.livrer = livrer;
	}

	@Column(name = "montant_demande_achat")
	public double getMontantDemandeAchat() {
		return this.montantDemandeAchat;
	}

	public void setMontantDemandeAchat(double montantDemandeAchat) {
		this.montantDemandeAchat = montantDemandeAchat;
	}

	@Column(name = "reference_demande_achat")
	public String getReferenceDemandeAchat() {
		return this.referenceDemandeAchat;
	}

	public void setReferenceDemandeAchat(String referenceDemandeAchat) {
		this.referenceDemandeAchat = referenceDemandeAchat;
	}

	public Integer getRejet() {
		return this.rejet;
	}

	public void setRejet(Integer rejet) {
		this.rejet = rejet;
	}

	@Column(name = "valider_down")
	public Integer getValiderDown() {
		return this.validerDown;
	}

	public void setValiderDown(Integer validerDown) {
		this.validerDown = validerDown;
	}

	@Column(name = "valider_up")
	public Integer getValiderUp() {
		return this.validerUp;
	}

	public void setValiderUp(Integer validerUp) {
		this.validerUp = validerUp;
	}

	// bi-directional many-to-one association to EuDetailDemandeAchat
	@OneToMany(mappedBy = "euDemandeAchat")
	public List<EuDetailDemandeAchat> getEuDetailDemandeAchats() {
		return this.euDetailDemandeAchats;
	}

	public void setEuDetailDemandeAchats(List<EuDetailDemandeAchat> euDetailDemandeAchats) {
		this.euDetailDemandeAchats = euDetailDemandeAchats;
	}

	public EuDetailDemandeAchat addEuDetailDemandeAchat(EuDetailDemandeAchat euDetailDemandeAchat) {
		getEuDetailDemandeAchats().add(euDetailDemandeAchat);
		euDetailDemandeAchat.setEuDemandeAchat(this);

		return euDetailDemandeAchat;
	}

	public EuDetailDemandeAchat removeEuDetailDemandeAchat(EuDetailDemandeAchat euDetailDemandeAchat) {
		getEuDetailDemandeAchats().remove(euDetailDemandeAchat);
		euDetailDemandeAchat.setEuDemandeAchat(null);

		return euDetailDemandeAchat;
	}

}
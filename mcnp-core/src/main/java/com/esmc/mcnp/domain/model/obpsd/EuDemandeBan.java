package com.esmc.mcnp.model.obpsd;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuDemandeBan
 *
 */
@Entity
@Table(name = "eu_demande_ban")
public class EuDemandeBan implements Serializable {

	private Integer id;
	private String codeMembre;
	private String codeTegc;
	private LocalDateTime dateDemande;
	private BigDecimal montant;
	private boolean valider;
	private boolean allouer;
	private BigDecimal montAlloue;
	private LocalDateTime dateAllocation;
	private String modePaiement;
	private static final long serialVersionUID = 1L;

	public EuDemandeBan() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "code_membre")
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Column(name = "code_tegc")
	public String getCodeTegc() {
		return this.codeTegc;
	}

	public void setCodeTegc(String codeTegc) {
		this.codeTegc = codeTegc;
	}

	@Column(name = "date_demande")
	public LocalDateTime getDateDemande() {
		return this.dateDemande;
	}

	public void setDateDemande(LocalDateTime dateDemande) {
		this.dateDemande = dateDemande;
	}

	public BigDecimal getMontant() {
		return this.montant;
	}

	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}

	public boolean getValider() {
		return this.valider;
	}

	public void setValider(boolean valider) {
		this.valider = valider;
	}

	public boolean getAllouer() {
		return this.allouer;
	}

	public void setAllouer(boolean allouer) {
		this.allouer = allouer;
	}

	@Column(name = "montant_alloue")
	public BigDecimal getMontAlloue() {
		return this.montAlloue;
	}

	public void setMontAlloue(BigDecimal montAlloue) {
		this.montAlloue = montAlloue;
	}

	@Column(name = "date_allocation")
	public LocalDateTime getDateAllocation() {
		return this.dateAllocation;
	}

	public void setDateAllocation(LocalDateTime dateAllocation) {
		this.dateAllocation = dateAllocation;
	}
	
	@Column(name = "mode_paiement")
	public String getModePaiement() {
		return modePaiement;
	}

	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}

}

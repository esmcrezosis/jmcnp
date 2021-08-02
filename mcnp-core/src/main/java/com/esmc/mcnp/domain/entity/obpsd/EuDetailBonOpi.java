package com.esmc.mcnp.domain.entity.obpsd;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.esmc.mcnp.domain.entity.bc.EuBon;

/**
 * Entity implementation class for Entity: EuDetailBonOpi
 *
 */
@Entity
@Table(name = "eu_detail_bon_opi")
public class EuDetailBonOpi implements Serializable {

	private Integer idDetailBonOpi;
	private String codeMembreEmetteur;
	private String codeMembrePbf;
	private String modeReg;
	private String typeEsc;
	private Integer nbre;
	private Double montant;
	private EuBon euBon;
	private Long idTpagcp;
	private String codeBanque;
	private String codeTegc;
	private Integer nbreTranche;
	private String typeOpi;
	private double prk;
	private double montTranche1;
	private int diferre;
	private Date dateDebut;
	private String typeGcp;
	private String referencePaiement;
	private String modePaiement;
	private Integer marge;
	private Integer ttc;
	private float tauxTva;
	private static final long serialVersionUID = 1L;

	public EuDetailBonOpi() {
	}

	@Id
	@Column(name = "id_detail_bon_opi")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getIdDetailBonOpi() {
		return this.idDetailBonOpi;
	}

	public void setIdDetailBonOpi(Integer idDetailBonOpi) {
		this.idDetailBonOpi = idDetailBonOpi;
	}

	@Column(name = "code_membre_emetteur")
	public String getCodeMembreEmetteur() {
		return this.codeMembreEmetteur;
	}

	public void setCodeMembreEmetteur(String codeMembreEmetteur) {
		this.codeMembreEmetteur = codeMembreEmetteur;
	}

	@Column(name = "code_membre_pbf")
	public String getCodeMembrePbf() {
		return this.codeMembrePbf;
	}

	public void setCodeMembrePbf(String codeMembrePbf) {
		this.codeMembrePbf = codeMembrePbf;
	}

	@Column(name = "mode_reg")
	public String getModeReg() {
		return this.modeReg;
	}

	public void setModeReg(String modeReg) {
		this.modeReg = modeReg;
	}

	@Column(name = "type_esc")
	public String getTypeEsc() {
		return this.typeEsc;
	}

	public void setTypeEsc(String typeEsc) {
		this.typeEsc = typeEsc;
	}

	@Column(name = "nbre")
	public Integer getNbre() {
		return this.nbre;
	}

	public void setNbre(Integer nbre) {
		this.nbre = nbre;
	}

	@Column(name = "montant")
	public Double getMontant() {
		return this.montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	@OneToOne
	@JoinColumn(name = "bon_id")
	public EuBon getEuBon() {
		return this.euBon;
	}

	public void setEuBon(EuBon euBon) {
		this.euBon = euBon;
	}

	@Column(name = "id_tpagcp")
	public Long getIdTpagcp() {
		return idTpagcp;
	}

	public void setIdTpagcp(Long idTpagcp) {
		this.idTpagcp = idTpagcp;
	}

	@Column(name = "code_banque")
	public String getCodeBanque() {
		return codeBanque;
	}

	public void setCodeBanque(String codeBanque) {
		this.codeBanque = codeBanque;
	}

	@Column(name = "code_tegc")
	public String getCodeTegc() {
		return codeTegc;
	}

	public void setCodeTegc(String codeTegc) {
		this.codeTegc = codeTegc;
	}

	@Column(name = "nbre_tranche")
	public Integer getNbreTranche() {
		return nbreTranche;
	}

	public void setNbreTranche(Integer nbreTranche) {
		this.nbreTranche = nbreTranche;
	}

	@Column(name = "type_opi")
	public String getTypeOpi() {
		return typeOpi;
	}

	public void setTypeOpi(String typeOpi) {
		this.typeOpi = typeOpi;
	}

	@Column(name = "mont_tranche1")
	public double getMontTranche1() {
		return montTranche1;
	}

	public void setMontTranche1(double montTranche1) {
		this.montTranche1 = montTranche1;
	}

	@Column(name = "diferre")
	public int getDiferre() {
		return diferre;
	}

	public void setDiferre(int diferre) {
		this.diferre = diferre;
	}

	@Column(name = "date_debut")
	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	@Column(name = "prk")
	public double getPrk() {
		return prk;
	}

	public void setPrk(double prk) {
		this.prk = prk;
	}

	@Column(name = "type_gcp")
	public String getTypeGcp() {
		return typeGcp;
	}

	public void setTypeGcp(String typeGcp) {
		this.typeGcp = typeGcp;
	}

	@Column(name = "reference_paiement")
	public String getRefrencePaiement() {
		return referencePaiement;
	}

	public void setRefrencePaiement(String refrencePaiement) {
		this.referencePaiement = refrencePaiement;
	}

	@Column(name = "mode_paiement")
	public String getModePaiement() {
		return modePaiement;
	}

	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}

	@Column(name = "marge")
	public Integer getMarge() {
		return marge;
	}

	public void setMarge(Integer marge) {
		this.marge = marge;
	}

	@Column(name = "ttc")
	public Integer getTtc() {
		return ttc;
	}

	public void setTtc(Integer ttc) {
		this.ttc = ttc;
	}

	@Column(name = "taux_tva")
	public float getTauxTva() {
		return tauxTva;
	}

	public void setTauxTva(float tauxTva) {
		this.tauxTva = tauxTva;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDetailBonOpi == null) ? 0 : idDetailBonOpi.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		EuDetailBonOpi other = (EuDetailBonOpi) obj;
		if (idDetailBonOpi == null) {
			if (other.idDetailBonOpi != null) {
				return false;
			}
		} else if (!idDetailBonOpi.equals(other.idDetailBonOpi)) {
			return false;
		}
		return true;
	}

}

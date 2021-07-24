package com.esmc.mcnp.model.obpsd;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the eu_tpagcp database table.
 *
 */
@Entity
@Table(name = "eu_tpagcp")
@NamedQuery(name = "EuTpagcp.findAll", query = "SELECT e FROM EuTpagcp e")
public class EuTpagcp implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idTpagcp;
	private String codeMembre;
	private LocalDate dateDeb;
	private LocalDate dateDebTranche;
	private LocalDate dateFin;
	private LocalDate dateFinTranche;
	private double montGcp;
	private Double montGcpMaj;
	private double montTranche;
	private double montEchu;
	private double montEchange;
	private double montEscompte;
	private double solde;
	private Integer ntf;
	private Integer resteNtf;
	private Integer periode;
	private String modeReglement;
	private String typeRessource;
	private String typeBl;
	private String numeroBl;
	private double escomptable;
	private Integer reinjecter;
	private Integer nbreInjection;
	private EuMembre membre;
	private EuMembreMorale membreMorale;
	private EuCompte euCompte;

	public EuTpagcp() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tpagcp", unique = true, nullable = false)
	public Long getIdTpagcp() {
		return this.idTpagcp;
	}

	public void setIdTpagcp(Long idTpagcp) {
		this.idTpagcp = idTpagcp;
	}

	@Column(name = "code_membre", length = 100)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Column(name = "date_deb")
	public LocalDate getDateDeb() {
		return this.dateDeb;
	}

	public void setDateDeb(LocalDate dateDeb) {
		this.dateDeb = dateDeb;
	}

	@Column(name = "date_deb_tranche")
	public LocalDate getDateDebTranche() {
		return this.dateDebTranche;
	}

	public void setDateDebTranche(LocalDate dateDebTranche) {
		this.dateDebTranche = dateDebTranche;
	}

	@Column(name = "date_fin")
	public LocalDate getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	@Column(name = "date_fin_tranche")
	public LocalDate getDateFinTranche() {
		return this.dateFinTranche;
	}

	public void setDateFinTranche(LocalDate dateFinTranche) {
		this.dateFinTranche = dateFinTranche;
	}

	@Column(nullable = false)
	public double getEscomptable() {
		return this.escomptable;
	}

	public void setEscomptable(double escomptable) {
		this.escomptable = escomptable;
	}

	@Column(name = "mont_echange")
	public double getMontEchange() {
		return this.montEchange;
	}

	public void setMontEchange(double montEchange) {
		this.montEchange = montEchange;
	}

	@Column(name = "mont_echu")
	public double getMontEchu() {
		return this.montEchu;
	}

	public void setMontEchu(double montEchu) {
		this.montEchu = montEchu;
	}

	@Column(name = "mont_escompte")
	public double getMontEscompte() {
		return this.montEscompte;
	}

	public void setMontEscompte(double montEscompte) {
		this.montEscompte = montEscompte;
	}

	@Column(name = "mont_gcp", nullable = false)
	public double getMontGcp() {
		return this.montGcp;
	}

	public void setMontGcp(double montGcp) {
		this.montGcp = montGcp;
	}

	@Column(name = "mont_tranche")
	public double getMontTranche() {
		return this.montTranche;
	}

	public void setMontTranche(double montTranche) {
		this.montTranche = montTranche;
	}

	public Integer getNtf() {
		return this.ntf;
	}

	public void setNtf(Integer ntf) {
		this.ntf = ntf;
	}

	public Integer getPeriode() {
		return this.periode;
	}

	public void setPeriode(Integer periode) {
		this.periode = periode;
	}

	@Column(name = "reste_ntf")
	public Integer getResteNtf() {
		return this.resteNtf;
	}

	public void setResteNtf(Integer resteNtf) {
		this.resteNtf = resteNtf;
	}

	public double getSolde() {
		return this.solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}

	@Column(name = "mode_reglement")
	public String getModeReglement() {
		return modeReglement;
	}

	public void setModeReglement(String modeReglement) {
		this.modeReglement = modeReglement;
	}

	@Column(name = "type_ressource")
	public String getTypeRessource() {
		return typeRessource;
	}

	public void setTypeRessource(String typeRessource) {
		this.typeRessource = typeRessource;
	}

	@Column(name = "type_bl")
	public String getTypeBl() {
		return typeBl;
	}

	public void setTypeBl(String typeBl) {
		this.typeBl = typeBl;
	}

	@Column(name = "numero_bl")
	public String getNumeroBl() {
		return numeroBl;
	}

	public void setNumeroBl(String numeroBl) {
		this.numeroBl = numeroBl;
	}

	@Column(name = "mont_gcp_maj")
	public Double getMontGcpMaj() {
		return montGcpMaj;
	}

	public void setMontGcpMaj(Double montGcpMaj) {
		this.montGcpMaj = montGcpMaj;
	}

	@Column(name = "reinjecter")
	public Integer getReinjecter() {
		return reinjecter;
	}

	public void setReinjecter(Integer reinjecter) {
		this.reinjecter = reinjecter;
	}

	@Column(name = "nbre_injection")
	public Integer getNbreInjection() {
		return nbreInjection;
	}

	public void setNbreInjection(Integer nbreInjection) {
		this.nbreInjection = nbreInjection;
	}

	@JsonIgnore
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "code_membre", insertable = false, updatable = false)
	public EuMembre getMembre() {
		return membre;
	}

	public void setMembre(EuMembre membre) {
		this.membre = membre;
	}

	@JsonIgnore
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "code_membre", insertable = false, updatable = false)
	public EuMembreMorale getMembreMorale() {
		return membreMorale;
	}

	public void setMembreMorale(EuMembreMorale membreMorale) {
		this.membreMorale = membreMorale;
	}

	// bi-directional many-to-one association to EuCompte
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_compte", nullable = false)
	public EuCompte getEuCompte() {
		return this.euCompte;
	}

	public void setEuCompte(EuCompte euCompte) {
		this.euCompte = euCompte;
	}

}

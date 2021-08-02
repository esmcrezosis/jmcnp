package com.esmc.mcnp.domain.entity.obps;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_ancien_tpagcp database table.
 * 
 */
@Entity
@Table(name="eu_ancien_tpagcp")
@NamedQuery(name="EuAncienTpagcp.findAll", query="SELECT e FROM EuAncienTpagcp e")
public class EuAncienTpagcp implements Serializable {
	private static final long serialVersionUID = 1L;
	private String idTpagcp;
	private String codeCompte;
	private String codeMembre;
	private String codeTegc;
	private Date dateDeb;
	private Date dateDebTranche;
	private Date dateFin;
	private Date dateFinTranche;
	private double montEchange;
	private double montEchu;
	private double montEscompte;
	private double montGcp;
	private double montTranche;
	private int ntf;
	private int periode;
	private int resteNtf;
	private double solde;

	public EuAncienTpagcp() {
	}


	@Id
	@Column(name="id_tpagcp")
	public String getIdTpagcp() {
		return this.idTpagcp;
	}

	public void setIdTpagcp(String idTpagcp) {
		this.idTpagcp = idTpagcp;
	}


	@Column(name="code_compte")
	public String getCodeCompte() {
		return this.codeCompte;
	}

	public void setCodeCompte(String codeCompte) {
		this.codeCompte = codeCompte;
	}


	@Column(name="code_membre")
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Column(name="code_tegc")
	public String getCodeTegc() {
		return this.codeTegc;
	}

	public void setCodeTegc(String codeTegc) {
		this.codeTegc = codeTegc;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_deb")
	public Date getDateDeb() {
		return this.dateDeb;
	}

	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_deb_tranche")
	public Date getDateDebTranche() {
		return this.dateDebTranche;
	}

	public void setDateDebTranche(Date dateDebTranche) {
		this.dateDebTranche = dateDebTranche;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_fin")
	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_fin_tranche")
	public Date getDateFinTranche() {
		return this.dateFinTranche;
	}

	public void setDateFinTranche(Date dateFinTranche) {
		this.dateFinTranche = dateFinTranche;
	}


	@Column(name="mont_echange")
	public double getMontEchange() {
		return this.montEchange;
	}

	public void setMontEchange(double montEchange) {
		this.montEchange = montEchange;
	}


	@Column(name="mont_echu")
	public double getMontEchu() {
		return this.montEchu;
	}

	public void setMontEchu(double montEchu) {
		this.montEchu = montEchu;
	}


	@Column(name="mont_escompte")
	public double getMontEscompte() {
		return this.montEscompte;
	}

	public void setMontEscompte(double montEscompte) {
		this.montEscompte = montEscompte;
	}


	@Column(name="mont_gcp")
	public double getMontGcp() {
		return this.montGcp;
	}

	public void setMontGcp(double montGcp) {
		this.montGcp = montGcp;
	}


	@Column(name="mont_tranche")
	public double getMontTranche() {
		return this.montTranche;
	}

	public void setMontTranche(double montTranche) {
		this.montTranche = montTranche;
	}


	public int getNtf() {
		return this.ntf;
	}

	public void setNtf(int ntf) {
		this.ntf = ntf;
	}


	public int getPeriode() {
		return this.periode;
	}

	public void setPeriode(int periode) {
		this.periode = periode;
	}


	@Column(name="reste_ntf")
	public int getResteNtf() {
		return this.resteNtf;
	}

	public void setResteNtf(int resteNtf) {
		this.resteNtf = resteNtf;
	}


	public double getSolde() {
		return this.solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}

}
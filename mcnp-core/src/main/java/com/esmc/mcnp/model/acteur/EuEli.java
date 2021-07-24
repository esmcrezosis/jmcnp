package com.esmc.mcnp.model.acteur;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the eu_eli database table.
 * 
 */
@Entity
@Table(name = "eu_eli")
@NamedQuery(name = "EuEli.findAll", query = "SELECT e FROM EuEli e")
public class EuEli implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idEli;
	private Integer idTdr;
	private Integer propose;
	private boolean bai;
	private boolean ban;
	private String codeMembre;
	private Date dateEli;
	private String libelleEli;
	private String codeTegc;
	private Double montantBai;
	private Double montantBan;
	private Double montantOpi;
	private Double montantEli;
	private Double montantVente;
	private String numeroEli;
	private boolean opi;
	private boolean payer;
	private Integer valider;
	private Integer rejeter;
	private Integer idCanton;

	public EuEli() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_eli")
	public Integer getIdEli() {
		return this.idEli;
	}

	public void setIdEli(Integer idEli) {
		this.idEli = idEli;
	}

	public boolean getBai() {
		return this.bai;
	}

	public void setBai(boolean bai) {
		this.bai = bai;
	}

	public boolean getBan() {
		return this.ban;
	}

	public void setBan(boolean ban) {
		this.ban = ban;
	}

	@Column(name = "code_membre")
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_eli")
	public Date getDateEli() {
		return this.dateEli;
	}

	public void setDateEli(Date dateEli) {
		this.dateEli = dateEli;
	}

	@Column(name = "libelle_eli")
	public String getLibelleEli() {
		return this.libelleEli;
	}

	public void setLibelleEli(String libelleEli) {
		this.libelleEli = libelleEli;
	}

	@Column(name = "montant_bai")
	public Double getMontantBai() {
		return this.montantBai;
	}

	public void setMontantBai(Double montantBai) {
		this.montantBai = montantBai;
	}

	@Column(name = "montant_ban")
	public Double getMontantBan() {
		return this.montantBan;
	}

	public void setMontantBan(Double montantBan) {
		this.montantBan = montantBan;
	}

	@Column(name = "montant_eli")
	public Double getMontantEli() {
		return this.montantEli;
	}

	public void setMontantEli(Double montantEli) {
		this.montantEli = montantEli;
	}

	@Column(name = "montant_opi")
	public Double getMontantOpi() {
		return this.montantOpi;
	}

	public void setMontantOpi(Double montantOpi) {
		this.montantOpi = montantOpi;
	}

	@Column(name = "numero_eli")
	public String getNumeroEli() {
		return this.numeroEli;
	}

	public void setNumeroEli(String numeroEli) {
		this.numeroEli = numeroEli;
	}

	public boolean getOpi() {
		return this.opi;
	}

	public void setOpi(boolean opi) {
		this.opi = opi;
	}

	public boolean getPayer() {
		return this.payer;
	}

	public void setPayer(boolean payer) {
		this.payer = payer;
	}

	public Integer getValider() {
		return this.valider;
	}

	public void setValider(Integer valider) {
		this.valider = valider;
	}

	@Column(name = "code_tegc")
	public String getCodeTegc() {
		return codeTegc;
	}

	public void setCodeTegc(String codeTegc) {
		this.codeTegc = codeTegc;
	}

	@Column(name = "rejeter")
	public Integer getRejeter() {
		return rejeter;
	}

	public void setRejeter(Integer rejeter) {
		this.rejeter = rejeter;
	}

	@Column(name = "id_canton")
	public Integer getIdCanton() {
		return idCanton;
	}

	public void setIdCanton(Integer idCanton) {
		this.idCanton = idCanton;
	}

	@Column(name = "id_tdr")
	public Integer getIdTdr() {
		return idTdr;
	}

	public void setIdTdr(Integer idTdr) {
		this.idTdr = idTdr;
	}

	@Column(name = "propose")
	public Integer getPropose() {
		return propose;
	}

	public void setPropose(Integer propose) {
		this.propose = propose;
	}

	@Column(name = "montant_vente")
	public Double getMontantVente() {
		return montantVente;
	}

	public void setMontantVente(Double montantVente) {
		this.montantVente = montantVente;
	}

}
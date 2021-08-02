package com.esmc.mcnp.domain.entity.acteur;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuCodeActivation
 *
 */
@Entity
@Table(name = "eu_code_activation")
public class EuCodeActivation implements Serializable {

	private Long idCodeActivation;
	private Long souscriptionId;
	private String codeFl;
	private String codeFs;
	private String codeFkps;
	private String codeMembre;
	private Date dateGenerer;
	private String origineCode;
	private Integer membreassoId;
	private  Double montantSouscrit;
	private static final long serialVersionUID = 1L;

	public EuCodeActivation() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_code_activation")
	public Long getIdCodeActivation() {
		return this.idCodeActivation;
	}

	public void setIdCodeActivation(Long idCodeActivation) {
		this.idCodeActivation = idCodeActivation;
	}

	@Column(name = "souscription_id")
	public Long getSouscriptionId() {
		return this.souscriptionId;
	}

	public void setSouscriptionId(Long souscriptionId) {
		this.souscriptionId = souscriptionId;
	}

	@Column(name = "code_fl")
	public String getCodeFl() {
		return this.codeFl;
	}

	public void setCodeFl(String codeFl) {
		this.codeFl = codeFl;
	}

	@Column(name = "code_fs")
	public String getCodeFs() {
		return this.codeFs;
	}

	public void setCodeFs(String codeFs) {
		this.codeFs = codeFs;
	}

	@Column(name = "code_fkps")
	public String getCodeFkps() {
		return this.codeFkps;
	}

	public void setCodeFkps(String codeFkps) {
		this.codeFkps = codeFkps;
	}

	@Column(name = "code_membre")
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Column(name = "date_generer")
	public Date getDateGenerer() {
		return this.dateGenerer;
	}

	public void setDateGenerer(Date dateGenerer) {
		this.dateGenerer = dateGenerer;
	}

	@Column(name = "origine_code")
	public String getOrigineCode() {
		return this.origineCode;
	}

	public void setOrigineCode(String origineCode) {
		this.origineCode = origineCode;
	}

	@Column(name = "membreasso_id")
	public Integer getMembreassoId() {
		return this.membreassoId;
	}

	public void setMembreassoId(Integer membreassoId) {
		this.membreassoId = membreassoId;
	}

	@Column(name = "montant_souscrit")
	public Double getMontantSouscrit() {
		return montantSouscrit;
	}

	public void setMontantSouscrit(Double montantSouscrit) {
		this.montantSouscrit = montantSouscrit;
	}

}

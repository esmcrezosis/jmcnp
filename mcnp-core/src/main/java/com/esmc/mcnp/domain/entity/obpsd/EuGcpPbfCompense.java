package com.esmc.mcnp.domain.entity.obpsd;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the eu_gcp_pbf_compense database table.
 *
 */
@Entity
@Table(name = "eu_gcp_pbf_compense")
@NamedQuery(name = "EuGcpPbfCompense.findAll", query = "SELECT e FROM EuGcpPbfCompense e")
public class EuGcpPbfCompense implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idGcpCompense;
	private String codeCompte;
	private String codeFgfn;
	private Long idCompens;
	private Long idDetailFgfn;
	private Long idDetailGcppbf;
	private double montFgfnSortie;
	private double montGcpEntree;
	private double soldeCompens;
	private String typeCapaFgfn;
	private String typeCapaGcp;

	public EuGcpPbfCompense() {
	}

	@Id
	@Column(name = "id_gcp_compense", unique = true, nullable = false)
	public Long getIdGcpCompense() {
		return this.idGcpCompense;
	}

	public void setIdGcpCompense(Long idGcpCompense) {
		this.idGcpCompense = idGcpCompense;
	}

	@Column(name = "code_compte", length = 180)
	public String getCodeCompte() {
		return this.codeCompte;
	}

	public void setCodeCompte(String codeCompte) {
		this.codeCompte = codeCompte;
	}

	@Column(name = "code_fgfn", length = 120)
	public String getCodeFgfn() {
		return this.codeFgfn;
	}

	public void setCodeFgfn(String codeFgfn) {
		this.codeFgfn = codeFgfn;
	}

	@Column(name = "id_compens")
	public Long getIdCompens() {
		return this.idCompens;
	}

	public void setIdCompens(Long idCompens) {
		this.idCompens = idCompens;
	}

	@Column(name = "id_detail_fgfn")
	public Long getIdDetailFgfn() {
		return this.idDetailFgfn;
	}

	public void setIdDetailFgfn(Long idDetailFgfn) {
		this.idDetailFgfn = idDetailFgfn;
	}

	@Column(name = "id_detail_gcppbf")
	public Long getIdDetailGcppbf() {
		return this.idDetailGcppbf;
	}

	public void setIdDetailGcppbf(Long idDetailGcppbf) {
		this.idDetailGcppbf = idDetailGcppbf;
	}

	@Column(name = "mont_fgfn_sortie")
	public double getMontFgfnSortie() {
		return this.montFgfnSortie;
	}

	public void setMontFgfnSortie(double montFgfnSortie) {
		this.montFgfnSortie = montFgfnSortie;
	}

	@Column(name = "mont_gcp_entree")
	public double getMontGcpEntree() {
		return this.montGcpEntree;
	}

	public void setMontGcpEntree(double montGcpEntree) {
		this.montGcpEntree = montGcpEntree;
	}

	@Column(name = "solde_compens")
	public double getSoldeCompens() {
		return this.soldeCompens;
	}

	public void setSoldeCompens(double soldeCompens) {
		this.soldeCompens = soldeCompens;
	}

	@Column(name = "type_capa_fgfn", length = 60)
	public String getTypeCapaFgfn() {
		return this.typeCapaFgfn;
	}

	public void setTypeCapaFgfn(String typeCapaFgfn) {
		this.typeCapaFgfn = typeCapaFgfn;
	}

	@Column(name = "type_capa_gcp", length = 60)
	public String getTypeCapaGcp() {
		return this.typeCapaGcp;
	}

	public void setTypeCapaGcp(String typeCapaGcp) {
		this.typeCapaGcp = typeCapaGcp;
	}

}
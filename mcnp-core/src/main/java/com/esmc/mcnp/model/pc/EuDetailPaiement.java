package com.esmc.mcnp.model.pc;

import com.esmc.mcnp.model.pc.EuPaiement;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_detail_paiement database table.
 * 
 */
@Entity
@Table(name="eu_detail_paiement")
@NamedQuery(name="EuDetailPaiement.findAll", query="SELECT e FROM EuDetailPaiement e")
public class EuDetailPaiement implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idDetailPaiement;
	private Integer bonNeutreApproId;
	private EuPaiement euPaiement;
	private Integer idPointage;
	private Double montantPaiement;
	private Integer souscriptionId;
	private String table;
	private Integer idTable;

	public EuDetailPaiement() {
	}


	@Id
	@Column(name="id_detail_paiement")
	public int getIdDetailPaiement() {
		return this.idDetailPaiement;
	}

	public void setIdDetailPaiement(int idDetailPaiement) {
		this.idDetailPaiement = idDetailPaiement;
	}


	@Column(name="bon_neutre_appro_id")
	public Integer getBonNeutreApproId() {
		return this.bonNeutreApproId;
	}

	public void setBonNeutreApproId(Integer bonNeutreApproId) {
		this.bonNeutreApproId = bonNeutreApproId;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_paiement")
	public EuPaiement getEuPaiement() {
		return this.euPaiement;
	}

	public void setEuPaiement(EuPaiement euPaiement) {
		this.euPaiement = euPaiement;
	}


	@Column(name="id_pointage")
	public Integer getIdPointage() {
		return this.idPointage;
	}

	public void setIdPointage(Integer idPointage) {
		this.idPointage = idPointage;
	}


	@Column(name="montant_paiement")
	public Double getMontantPaiement() {
		return this.montantPaiement;
	}

	public void setMontantPaiement(Double montantPaiement) {
		this.montantPaiement = montantPaiement;
	}


	@Column(name="souscription_id")
	public Integer getSouscriptionId() {
		return this.souscriptionId;
	}

	public void setSouscriptionId(Integer souscriptionId) {
		this.souscriptionId = souscriptionId;
	}

	@Column(name = "table")
	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	@Column(name = "id_table")
	public Integer getIdTable() {
		return idTable;
	}

	public void setIdTable(Integer idTable) {
		this.idTable = idTable;
	}
}
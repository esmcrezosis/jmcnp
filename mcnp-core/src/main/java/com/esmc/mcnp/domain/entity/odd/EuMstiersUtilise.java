package com.esmc.mcnp.model.odd;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_mstiers_utilise database table.
 * 
 */
@Entity
@Table(name="eu_mstiers_utilise")
@NamedQuery(name="EuMstiersUtilise.findAll", query="SELECT e FROM EuMstiersUtilise e")
public class EuMstiersUtilise implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idMstiersUtilise;
	private String codeCaps;
	private Date dateMstiersUtilise;
	private int idMstiers;
	private double montantUtilise;
	private String codeMembre;

	public EuMstiersUtilise() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_mstiers_utilise")
	public int getIdMstiersUtilise() {
		return this.idMstiersUtilise;
	}

	public void setIdMstiersUtilise(int idMstiersUtilise) {
		this.idMstiersUtilise = idMstiersUtilise;
	}


	@Column(name="code_caps")
	public String getCodeCaps() {
		return this.codeCaps;
	}

	public void setCodeCaps(String codeCaps) {
		this.codeCaps = codeCaps;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_mstiers_utilise")
	public Date getDateMstiersUtilise() {
		return this.dateMstiersUtilise;
	}

	public void setDateMstiersUtilise(Date dateMstiersUtilise) {
		this.dateMstiersUtilise = dateMstiersUtilise;
	}


	@Column(name="id_mstiers")
	public int getIdMstiers() {
		return this.idMstiers;
	}

	public void setIdMstiers(int idMstiers) {
		this.idMstiers = idMstiers;
	}


	@Column(name="montant_utilise")
	public double getMontantUtilise() {
		return this.montantUtilise;
	}

	public void setMontantUtilise(double montantUtilise) {
		this.montantUtilise = montantUtilise;
	}

    @Column(name = "code_membre_beneficiaire")
	public String getCodeMembre() {
		return codeMembre;
	}


	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

}
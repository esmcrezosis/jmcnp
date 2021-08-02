package com.esmc.mcnp.domain.entity.pc;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_releveescompte database table.
 * 
 */
@Entity
@Table(name="eu_releveescompte")
@NamedQuery(name="EuReleveescompte.findAll", query="SELECT e FROM EuReleveescompte e")
public class EuReleveescompte implements Serializable {
	private static final long serialVersionUID = 1L;
	private int releveescompteId;
	private byte publier;
	private Date releveescompteDate;
	private int releveescompteEscompte;
	private int releveescompteMontant;
	private String releveescompteProduit;
	private int releveescompteReleve;

	public EuReleveescompte() {
	}


	@Id
	@Column(name="releveescompte_id")
	public int getReleveescompteId() {
		return this.releveescompteId;
	}

	public void setReleveescompteId(int releveescompteId) {
		this.releveescompteId = releveescompteId;
	}


	public byte getPublier() {
		return this.publier;
	}

	public void setPublier(byte publier) {
		this.publier = publier;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="releveescompte_date")
	public Date getReleveescompteDate() {
		return this.releveescompteDate;
	}

	public void setReleveescompteDate(Date releveescompteDate) {
		this.releveescompteDate = releveescompteDate;
	}


	@Column(name="releveescompte_escompte")
	public int getReleveescompteEscompte() {
		return this.releveescompteEscompte;
	}

	public void setReleveescompteEscompte(int releveescompteEscompte) {
		this.releveescompteEscompte = releveescompteEscompte;
	}


	@Column(name="releveescompte_montant")
	public int getReleveescompteMontant() {
		return this.releveescompteMontant;
	}

	public void setReleveescompteMontant(int releveescompteMontant) {
		this.releveescompteMontant = releveescompteMontant;
	}


	@Column(name="releveescompte_produit")
	public String getReleveescompteProduit() {
		return this.releveescompteProduit;
	}

	public void setReleveescompteProduit(String releveescompteProduit) {
		this.releveescompteProduit = releveescompteProduit;
	}


	@Column(name="releveescompte_releve")
	public int getReleveescompteReleve() {
		return this.releveescompteReleve;
	}

	public void setReleveescompteReleve(int releveescompteReleve) {
		this.releveescompteReleve = releveescompteReleve;
	}

}
package com.esmc.mcnp.model.obps;

import com.esmc.mcnp.model.obpsd.EuTraite;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="eu_commande_nr_opi")
public class EuCommandeNrOpi implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idCommandeNrOpi;
	private EuTraite euTraite;
	private EuCommandeNr euCommandeNr;
	
	
	@Id
	@Column(name = "id_commande_nr_opi", unique = true, nullable = false)
	public int getIdCommandeNrOpi() {
		return idCommandeNrOpi;
	}
	public void setIdCommandeNrOpi(int idCommandeNrOpi) {
		this.idCommandeNrOpi = idCommandeNrOpi;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="traite_id")
	public EuTraite getEuTraite() {
		return euTraite;
	}
	public void setEuTraite(EuTraite euTraite) {
		this.euTraite = euTraite;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_commande_nr")
	public EuCommandeNr getEuCommandeNr() {
		return euCommandeNr;
	}
	public void setEuCommandeNr(EuCommandeNr euCommandeNr) {
		this.euCommandeNr = euCommandeNr;
	}
	
	
}

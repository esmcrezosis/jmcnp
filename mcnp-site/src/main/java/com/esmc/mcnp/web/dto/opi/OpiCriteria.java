package com.esmc.mcnp.web.dto.opi;

import java.io.Serializable;
import java.util.Date;

public class OpiCriteria  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codeMembre;
	private Date dateDebut;
	private Date dateFin;
	private Integer duree;

	public OpiCriteria() {
		// TODO Auto-generated constructor stub
	}

	public OpiCriteria(Date dateDebut, Date dateFin) {
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}

	public OpiCriteria(Date dateDebut, Date dateFin, Integer duree) {
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.duree = duree;
	}

	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Integer getDuree() {
		return duree;
	}

	public void setDuree(Integer duree) {
		this.duree = duree;
	}

}

package com.esmc.mcnp.domain.dto.desendettement;

import java.io.Serializable;
import java.util.Date;

public class OrdrePayement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idOrdrePayement;
	private Date dateOrdrePayement;
	private Long idCreance;
	private String codeMembreCred;
	private String codeMembreDeb;
	private String codeMembreBenef;
	private Long idDette;

	public OrdrePayement() {
		
	}

	public OrdrePayement(Long idOrdrePayement, Date dateOrdrePayement, Long idCreance, String codeMembreCred,
			String codeMembreDeb, String codeMembreBenef, Long idDette) {
		this.idOrdrePayement = idOrdrePayement;
		this.dateOrdrePayement = dateOrdrePayement;
		this.idCreance = idCreance;
		this.codeMembreCred = codeMembreCred;
		this.codeMembreDeb = codeMembreDeb;
		this.codeMembreBenef = codeMembreBenef;
		this.idDette = idDette;
	}

	public Long getIdOrdrePayement() {
		return idOrdrePayement;
	}

	public void setIdOrdrePayement(Long idOrdrePayement) {
		this.idOrdrePayement = idOrdrePayement;
	}

	public Date getDateOrdrePayement() {
		return dateOrdrePayement;
	}

	public void setDateOrdrePayement(Date dateOrdrePayement) {
		this.dateOrdrePayement = dateOrdrePayement;
	}

	public Long getIdCreance() {
		return idCreance;
	}

	public void setIdCreance(Long idCreance) {
		this.idCreance = idCreance;
	}

	public String getCodeMembreCred() {
		return codeMembreCred;
	}

	public void setCodeMembreCred(String codeMembreCred) {
		this.codeMembreCred = codeMembreCred;
	}

	public String getCodeMembreDeb() {
		return codeMembreDeb;
	}

	public void setCodeMembreDeb(String codeMembreDeb) {
		this.codeMembreDeb = codeMembreDeb;
	}

	public String getCodeMembreBenef() {
		return codeMembreBenef;
	}

	public void setCodeMembreBenef(String codeMembreBenef) {
		this.codeMembreBenef = codeMembreBenef;
	}

	public Long getIdDette() {
		return idDette;
	}

	public void setIdDette(Long idDette) {
		this.idDette = idDette;
	}

}

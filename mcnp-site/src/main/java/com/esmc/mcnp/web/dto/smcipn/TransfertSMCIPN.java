package com.esmc.mcnp.web.dto.smcipn;

import java.io.Serializable;
import java.util.Date;

import com.esmc.mcnp.model.smcipn.EuTransfertNr;

public class TransfertSMCIPN implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String numDoc;
	private Date dateTransfert;
	private String libelleTransfert;
	private String numAppelOffre;
	private double montTransfert;
	private String codeMembreBenef;
	private String codeMembreTransfert;
	private String typeNr;
	private Long idUtilisateur;

	public TransfertSMCIPN() {
	}

	public String getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}

	public Date getDateTransfert() {
		return dateTransfert;
	}

	public void setDateTransfert(Date dateTransfert) {
		this.dateTransfert = dateTransfert;
	}

	public String getLibelleTransfert() {
		return libelleTransfert;
	}

	public void setLibelleTransfert(String libelleTransfert) {
		this.libelleTransfert = libelleTransfert;
	}

	public String getNumAppelOffre() {
		return numAppelOffre;
	}

	public void setNumAppelOffre(String numAppelOffre) {
		this.numAppelOffre = numAppelOffre;
	}

	public double getMontTransfert() {
		return montTransfert;
	}

	public void setMontTransfert(double montTransfert) {
		this.montTransfert = montTransfert;
	}

	public String getCodeMembreBenef() {
		return codeMembreBenef;
	}

	public void setCodeMembreBenef(String codeMembreBenef) {
		this.codeMembreBenef = codeMembreBenef;
	}

	public String getCodeMembreTransfert() {
		return codeMembreTransfert;
	}

	public void setCodeMembreTransfert(String codeMembreTransfert) {
		this.codeMembreTransfert = codeMembreTransfert;
	}

	public String getTypeNr() {
		return typeNr;
	}

	public void setTypeNr(String typeNr) {
		this.typeNr = typeNr;
	}

	public Long getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public EuTransfertNr toTrNr() {
		EuTransfertNr tr = new EuTransfertNr();
		tr.setCodeMembreBenef(this.codeMembreBenef);
		tr.setCodeMembreTransfert(this.codeMembreTransfert);
		tr.setIdUtilisateur(this.idUtilisateur);
		tr.setLibelleTransfert(this.libelleTransfert);
		tr.setMontTransfert(this.montTransfert);
		tr.setNumAppelOffre(this.numAppelOffre);
		tr.setNumDoc(this.numDoc);
		tr.setTypeNr(this.typeNr);
		return tr;
	}

}

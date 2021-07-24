package com.esmc.mcnp.web.dto.op;

import java.io.Serializable;

import com.esmc.mcnp.model.op.EuOffreurProjet;

public class OffreurProjet implements Serializable {

	/**	
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String codeMembre;
	private String raisonSociale;
	private String produit;
	private String description;
	private String canton;
	private Integer operationnel;

	public OffreurProjet() {
	}

	public OffreurProjet(Integer id, String codeMembre, String raisonSociale, String produit, String description,
			String canton, Integer operationnel) {
		super();
		this.id = id;
		this.codeMembre = codeMembre;
		this.raisonSociale = raisonSociale;
		this.produit = produit;
		this.description = description;
		this.canton = canton;
		this.operationnel = operationnel;
	}

	public OffreurProjet(EuOffreurProjet offreurProjet) {
		this.id = offreurProjet.getId();
		this.codeMembre = offreurProjet.getCodeMembre();
		this.description = offreurProjet.getDescriptionProjet();
		this.operationnel = offreurProjet.getOperationnel();
		this.canton = offreurProjet.getCanton();
		this.produit = offreurProjet.getProduit();
		this.raisonSociale = offreurProjet.getRaisonSociale();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	public String getRaisonSociale() {
		return raisonSociale;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}

	public String getProduit() {
		return produit;
	}

	public void setProduit(String produit) {
		this.produit = produit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCanton() {
		return canton;
	}

	public void setCanton(String canton) {
		this.canton = canton;
	}

	public Integer getOperationnel() {
		return operationnel;
	}

	public void setOperationnel(Integer operationnel) {
		this.operationnel = operationnel;
	}

}

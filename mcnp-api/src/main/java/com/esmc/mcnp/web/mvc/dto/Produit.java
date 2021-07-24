package com.esmc.mcnp.web.mvc.dto;

public class Produit {

	private Long idProduit;
	private String nomProduit;

	public Produit() {
	}

	public Produit(Long idProduit, String nomProduit) {
		this.idProduit = idProduit;
		this.nomProduit = nomProduit;
	}

	public Long getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(Long idProduit) {
		this.idProduit = idProduit;
	}

	public String getNomProduit() {
		return nomProduit;
	}

	public void setNomProduit(String nomProduit) {
		this.nomProduit = nomProduit;
	}

}

package com.esmc.mcnp.web.dto.obps;

public class TegcDTO {

	public TegcDTO() {
	}

	public String codeTegc;
	private String nomTegc;
	private String nomProduit;

	/**
	 * @return the codeTegc
	 */
	public String getCodeTegc() {
		return codeTegc;
	}

	/**
	 * @param codeTegc
	 *            the codeTegc to set
	 */
	public void setCodeTegc(String codeTegc) {
		this.codeTegc = codeTegc;
	}

	/**
	 * @return the nomTegc
	 */
	public String getNomTegc() {
		return nomTegc;
	}

	/**
	 * @param nomTegc
	 *            the nomTegc to set
	 */
	public void setNomTegc(String nomTegc) {
		this.nomTegc = nomTegc;
	}

	/**
	 * @return the nomProduit
	 */
	public String getNomProduit() {
		return nomProduit;
	}

	/**
	 * @param nomProduit
	 *            the nomProduit to set
	 */
	public void setNomProduit(String nomProduit) {
		this.nomProduit = nomProduit;
	}

}

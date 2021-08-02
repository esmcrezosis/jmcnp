package com.esmc.mcnp.domain.dto.obps;

import com.esmc.mcnp.domain.entity.obps.EuTegc;

public class TegcView {

	public String codeTegc;
	private String nomTegc;
	private String nomProduit;

	public TegcView() {
	}

	public TegcView(String codeTegc, String nomTegc, String nomProduit) {
		this.codeTegc = codeTegc;
		this.nomTegc = nomTegc;
		this.nomProduit = nomProduit;
	}

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

	public static TegcView fromEuTegc(EuTegc tegc) {
		return new TegcView(tegc.getCodeTegc(), tegc.getNomTegc(), tegc.getNomProduit());
	}

}

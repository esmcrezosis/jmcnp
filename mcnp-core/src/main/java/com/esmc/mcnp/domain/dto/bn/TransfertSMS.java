package com.esmc.mcnp.domain.dto.bn;

import java.io.Serializable;

public class TransfertSMS implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
    private String codeCompteVendeur;
    private String typeTransfert;
    private String typeNn;
    private String numeroAcheteur;
    private double montant;
    private String typeReglement;
    private String numero_doc;
    private String codeCompteAcheteur;
    private String typeRecharge;

    public TransfertSMS(String codeCompteVendeur, String typeTransfert, String typeNn, String numeroAcheteur,
			double montant, String typeReglement, String numero_doc, String codeCompteAcheteur, String typeRecharge) {
    	this.codeCompteVendeur = codeCompteVendeur;
		this.typeTransfert = typeTransfert;
		this.typeNn = typeNn;
		this.numeroAcheteur = numeroAcheteur;
		this.montant = montant;
		this.typeReglement = typeReglement;
		this.numero_doc = numero_doc;
		this.codeCompteAcheteur = codeCompteAcheteur;
		this.typeRecharge = typeRecharge;
	}

    public TransfertSMS() {
    }


	public String getCodeCompteVendeur() {
        return codeCompteVendeur;
    }

    public void setCodeCompteVendeur(String codeCompteVendeur) {
        this.codeCompteVendeur = codeCompteVendeur;
    }

    public String getTypeTransfert() {
        return typeTransfert;
    }

    public void setTypeTransfert(String typeTransfert) {
        this.typeTransfert = typeTransfert;
    }

    public String getTypeNn() {
        return typeNn;
    }

    public void setTypeNn(String typeNn) {
        this.typeNn = typeNn;
    }

    public String getNumeroAcheteur() {
        return numeroAcheteur;
    }

    public void setNumeroAcheteur(String numeroAcheteur) {
        this.numeroAcheteur = numeroAcheteur;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getTypeReglement() {
        return typeReglement;
    }

    public void setTypeReglement(String typeReglement) {
        this.typeReglement = typeReglement;
    }

    public String getNumero_doc() {
        return numero_doc;
    }

    public void setNumero_doc(String numero_doc) {
        this.numero_doc = numero_doc;
    }

    public String getCodeCompteAcheteur() {
        return codeCompteAcheteur;
    }

    public void setCodeCompteAcheteur(String codeCompteAcheteur) {
        this.codeCompteAcheteur = codeCompteAcheteur;
    }

    public String getTypeRecharge() {
        return typeRecharge;
    }

    public void setTypeRecharge(String typeRecharge) {
        this.typeRecharge = typeRecharge;
    }

}

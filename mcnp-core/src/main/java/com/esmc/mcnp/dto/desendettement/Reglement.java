/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dto.desendettement;

import java.io.Serializable;
import java.util.List;

import com.esmc.mcnp.dto.obps.ArticleVendu;



/**
 *
 * @author TOSHIBA
 */
public class Reglement implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private Long idUtilisateur;
    private String compteVendeur;
    private String compteAcheteur;
    private double montantAchat;
    private double montantLivraison;
    private double montantPrelever;
    private String codetypeCredit;
    private String codeProduit;
    private String numBon;
    private String typeBon;
    private String typeR;
    private int terminal;
    private String numAppelOffre;
    private String typeTerminal;
    private String NumFacture;
    private String NumTelephone;
    private String NumCompteur;
    private int domicilier;
    private transient List<ArticleVendu> ListArticlesVendus;

   
    
    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
    
    public String getCompteVendeur() {
        return compteVendeur;
    }

    public void setCompteVendeur(String compteVendeur) {
        this.compteVendeur = compteVendeur;
    }

    public String getCompteAcheteur() {
        return compteAcheteur;
    }

    public void setCompteAcheteur(String compteAcheteur) {
        this.compteAcheteur = compteAcheteur;
    }

    public double getMontantAchat() {
        return montantAchat;
    }

    public void setMontantAchat(double montantAchat) {
        this.montantAchat = montantAchat;
    }

    public double getMontantLivraison() {
		return montantLivraison;
	}

	public void setMontantLivraison(double montantLivraison) {
		this.montantLivraison = montantLivraison;
	}

	public double getMontantPrelever() {
		return montantPrelever;
	}

	public void setMontantPrelever(double montantPrelever) {
		this.montantPrelever = montantPrelever;
	}

	public String getCodetypeCredit() {
        return codetypeCredit;
    }

    public void setCodetypeCredit(String codetypeCredit) {
        this.codetypeCredit = codetypeCredit;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

    public String getNumBon() {
		return numBon;
	}

	public void setNumBon(String numBon) {
		this.numBon = numBon;
	}

	public String getTypeBon() {
		return typeBon;
	}

	public void setTypeBon(String typeBon) {
		this.typeBon = typeBon;
	}

	public String getTypeR() {
        return typeR;
    }

    public void setTypeR(String typeR) {
        this.typeR = typeR;
    }

    public int getTerminal() {
        return terminal;
    }

    public void setTerminal(int terminal) {
        this.terminal = terminal;
    }

    public List<ArticleVendu> getListArticlesVendus() {
        return ListArticlesVendus;
    }

    public void setListArticlesVendus(List<ArticleVendu> ListArticlesVendus) {
        this.ListArticlesVendus = ListArticlesVendus;
    }

    public String getNumAppelOffre() {
        return numAppelOffre;
    }

    public void setNumAppelOffre(String numAppelOffre) {
        this.numAppelOffre = numAppelOffre;
    }

    public String getTypeTerminal() {
        return typeTerminal;
    }

    public void setTypeTerminal(String typeTerminal) {
        this.typeTerminal = typeTerminal;
    }

    public String getNumFacture() {
        return NumFacture;
    }

    public void setNumFacture(String NumFacture) {
        this.NumFacture = NumFacture;
    }

    public String getNumTelephone() {
        return NumTelephone;
    }

    public void setNumTelephone(String NumTelephone) {
        this.NumTelephone = NumTelephone;
    }

    public String getNumCompteur() {
        return NumCompteur;
    }

    public void setNumCompteur(String NumCompteur) {
        this.NumCompteur = NumCompteur;
    }

    public int getDomicilier() {
        return domicilier;
    }

    public void setDomicilier(int domicilier) {
        this.domicilier = domicilier;
    }

   
    
    
    
}

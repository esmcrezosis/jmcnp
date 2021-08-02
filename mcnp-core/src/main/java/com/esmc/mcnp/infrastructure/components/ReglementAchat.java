/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.components;

import java.util.List;

import com.esmc.mcnp.domain.dto.obps.ArticleVendu;

/**
 *
 * @author Administrateur
 */
public interface ReglementAchat {
	
     public String saveReglementSimpleParBon(String codeAchat,Integer Domiciliation, String codeTegc, String compteVendeur, String CodeBon, String typeR, String typeProduit, String codeTypeCredit, String nomProduit, Long idUtilisateur,
            List<ArticleVendu> ListArticlesVendu);
     
     public String saveReglementFactureParBon(String codeTegc, String compteVendeur, String CodeBon, String typeR, String typeProduit, String codeTypeCredit, 
    		 String nomProduit, String numeroFacture, String periode, Long idUtilisateur);
     
     public String saveReglementFactureParBonAuGcsc(String codeTegc, String compteVendeur, String CodeBon, String typeR,	String typeProduit, String codeTypeCredit,  String nomProduit, String referenceFacture, String periode,	Long idUtilisateur);
 
     public String saveReglementBySmcipn(String compteVendeur, Double montantSouscription, String CodeBon, String codeTegc, String typeR, String codeTypeCredit, Long idUtilisateur, List<ArticleVendu> ListArticlesVendu);
     
     public String saveReglementParBonAuGcs(String codeTegc, String compteVendeur, String CodeBon, String typeR, String typeProduit, String codeTypeCredit,  String nomProduit, Long idUtilisateur,
             List<ArticleVendu> ListArticlesVendu);
     
    
}

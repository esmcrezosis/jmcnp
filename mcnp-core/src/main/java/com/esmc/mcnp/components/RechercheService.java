package com.esmc.mcnp.components;


import java.util.List;

import com.esmc.mcnp.model.acteur.EuActeur;
import com.esmc.mcnp.model.op.EuAppelOffre;
import com.esmc.mcnp.model.obps.EuArticleStockes;
import com.esmc.mcnp.model.ba.EuCapaDeclaration;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.cm.EuCompteCreditCapa;
import com.esmc.mcnp.model.cm.EuCompteCreditTs;
import com.esmc.mcnp.model.others.EuCours;
import com.esmc.mcnp.model.bc.EuCreditCodebarre;
import com.esmc.mcnp.model.bc.EuDetailDomicilie;
import com.esmc.mcnp.model.acteur.EuFiliere;
import com.esmc.mcnp.model.others.EuFrais;
import com.esmc.mcnp.model.smcipn.EuGcsc;
import com.esmc.mcnp.model.acteur.EuMaison;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.others.EuProposition;
import com.esmc.mcnp.model.cm.EuRepresentation;
import com.esmc.mcnp.model.obps.EuTegc;
import com.esmc.mcnp.model.bc.EuTypeCredit;
import com.esmc.mcnp.model.security.EuUtilisateur;

public interface RechercheService {

	public EuMembre findMembreByCompte(String codeCompte);

	public EuMembre findMembreById(String id);

	public EuMembre findMembreByNomPrenom(String nom, String prenom);

	public EuMembreMorale findMembreMoraleByCompte(String codeCompte);

	public EuMembreMorale findMembreMoraleById(String id);

	public List<EuMembreMorale> findMembreRepresentant(String codeMembreRep);

	public List<EuMembreMorale> findMembreMoraleByNom(String nom, String prenom);

	public EuCompte findCompteById(String id);

	public List<EuCompte> findCompteByMembre(String codeMembre);

	public EuCompte findCompteByNumCarte(String numeroCarte);

	public EuCompte findbyMembreAndCodeCat(String code_membre, String code_cat);

	public List<EuRepresentation> fetchAllByMembreMoral(String codeMembre);

	public EuMembreMorale findByRaisonSociale(String raison_sociale);

	public List<EuCours> findAllCours();

	public List<EuTypeCredit> FindAllTypeCredit();

	public EuTypeCredit FindEuTypeCredit(String codetypecredit);

	public EuCreditCodebarre findCodebarreByCode(String codebarre);

	public Long findMaxIdfromOperation();

	public Long findMaxIdfromEuSmc();

	public Long findMaxIdfromEuSms();

	public Long findMaxIdfromEuGcp();

	public Long findMaxIdfromCreditConsommer();

//	public Long findMaxIdfromOzekimessageOut();

	// public List<EuMembre> findAllEmpreintes();
	public EuMaison getEuMAisonByCodeMembre(String codemembre);

	public EuActeur getEuActeurByCodeMembre(String codemembre);

	public EuTegc getEuTegc(String codeTegc);

	public EuCompteCreditCapa finfEuCompteCreditCapa(Long idcredit);

	public EuCapaDeclaration getEuCapaDeclaration(String reference);

	public EuCapaDeclaration getEuCapaDeclarationByCodebarre(String codebarre);

	public Long findMaxIdfromDeclaration();

	public EuGcsc findEuGcsc(String codemembre);

	public List<EuCompteCredit> FindAllCredit(String codecompte);

	public List<EuCompteCreditTs> FindAllCreditTs(String codecompte);

	// public double getSommeOperationsByImmatBenef(String typecredit, String
	// codemembre);
	public EuCompteCredit FindCompteCredit(Long idCredit);

	public EuUtilisateur FindUtilisateurByCodeMembre(String codemembre);

	public EuFiliere FindEuFiliereById(Long Id);

	public EuAppelOffre FindAppelOffresByNum(String numeroOffre);

	public EuActeur FindSurveillance();

	public EuProposition FindPropositionByNumero(String numeroOffre);

	public EuFrais FindFraisbyProposition(Long IdProposition);

	public List<EuArticleStockes> FindAllArticlesEnStock(String codemembre);

	public Boolean VerifierLecteur(String numSerie);

	public List<String> FindListDesProduitsCapa(String codeCompte);

	public List<String> FindListDesProduitsComptesTpn(String codeCompte);

	public Double findMontSmsByCodeSms(String codeSMS);
        
    public List<EuDetailDomicilie> FindDetailDomicilie(String codeMembre);
    
}

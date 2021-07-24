
package com.esmc.mcnp.web.controller.obps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.esmc.mcnp.components.CreditUtility;
import com.esmc.mcnp.components.EchangeService;
import com.esmc.mcnp.components.EchangeUtility;
import com.esmc.mcnp.components.QuotaUtility;
import com.esmc.mcnp.components.ReglementAchat;
import com.esmc.mcnp.components.SmsComponent;
//import com.esmc.mcnp.components.SouscriptionBon;
import com.esmc.mcnp.components.TransfertUtility;
import com.esmc.mcnp.config.annotation.WebController;
import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dto.bc.CalculBonInfo;
import com.esmc.mcnp.dto.obps.ArticleStocke;
import com.esmc.mcnp.dto.obps.ArticleVendu;
import com.esmc.mcnp.model.obps.EuCommande;
import com.esmc.mcnp.model.obps.EuCommandeNr;
import com.esmc.mcnp.model.obps.EuDetailCommande;
import com.esmc.mcnp.model.obps.EuDetailCommandeAdditif;
//import com.esmc.mcnp.model.EuDetailCommandeNr;
import com.esmc.mcnp.model.acteur.EuEli;
//import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.bc.EuPrk;
import com.esmc.mcnp.model.bc.EuTypeCredit;
import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.obps.EuArticleStockes;
import com.esmc.mcnp.model.obps.EuArticleStockesAdditif;
import com.esmc.mcnp.model.obps.EuArticleStockesCategorie;
import com.esmc.mcnp.model.obps.EuTegc;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.repositories.others.EuTegcRepository;
import com.esmc.mcnp.repositories.obps.EuArticleStockesCategorieRepository;
import com.esmc.mcnp.repositories.obps.EuCommandeRepository;
import com.esmc.mcnp.services.acteurs.EuEliService;
import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.cm.EuMembreService;
import com.esmc.mcnp.services.common.EuTypeCreditService;
import com.esmc.mcnp.services.obps.EuArticleStockeService;
import com.esmc.mcnp.services.obps.EuArticleStockesAdditifService;
import com.esmc.mcnp.services.obps.EuCommandeNrOpiService;
import com.esmc.mcnp.services.obps.EuCommandeNrService;
import com.esmc.mcnp.services.obps.EuDetailCommandeAdditifService;
import com.esmc.mcnp.services.obps.EuDetailCommandeService;
import com.esmc.mcnp.services.obps.EuGcpService;
import com.esmc.mcnp.services.obps.EuInformationAdditifService;
//import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.obps.EuPrkService;
import com.esmc.mcnp.services.obps.EuTegcService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;


@WebController
public class BoutiqueReapproController extends BaseController {
	@Inject
	private SmsComponent smsComponent;
	@Inject
	private TransfertUtility transfertUtility;
	@Inject
	private ReglementAchat reglementAchat;
	@Inject
	private QuotaUtility quotaUtility;
	
	/*@Inject
	private CreditUtility creditUtility;
	@Inject
	private SouscriptionBon souscriptionBon;*/
	@Inject
	private EchangeUtility echangeUtility;


	//private @Autowired EuMembreMoraleService moraleService;
	private @Autowired EuPrkService prkService;
	private @Autowired EuTegcRepository tegcRepo;
	private @Autowired EchangeService echangeService;
	private @Autowired EuTegcService tegcService;
	private @Autowired EuGcpService gcpService;
	private @Autowired EuTypeCreditService typeCreditService;
	//private @Autowired EuDetailCommandeNrService detailCommandeNrService;
	private @Autowired EuCommandeNrService commandeNrService;
	private @Autowired EuCommandeNrOpiService commandeNrOpiService;
	private @Autowired EuArticleStockesCategorieRepository euArticleStockesCategorieRepo;
	private @Autowired EuArticleStockeService euArticleStockeService;
	private @Autowired EuEliService eliService;
	private @Autowired EuMembreMoraleService moraleService;
	private @Autowired EuMembreService membreService;
	private @Autowired EuInformationAdditifService euInfoAdditifService;
	private @Autowired EuDetailCommandeAdditifService euDetComAdditifService;
	private @Autowired EuCommandeRepository euCommandeRepo;
	private @Autowired EuArticleStockesAdditifService euArtStockesAddService;
	private @Autowired EuDetailCommandeService detailCommandeService;
	
	
	
	@RequestMapping(value = "/boutiquereappro", method = RequestMethod.GET)
	public String boutiqueReappro() {
		return "distributeur/boutique_reappro";
	}

	@RequestMapping(value = "/signerEliReappro", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Integer retrouverFranchiseEli() {
		Integer signerELi = 0;
		String codeTegc;
		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		if(StringUtils.isNotBlank(codeTegc)){
		//retrouver EuELi
			List<EuEli> listEuEli = eliService.findByCodeTegc(codeTegc);
			if(!listEuEli.isEmpty()){
				signerELi = 1;
				}
		}
		return signerELi;
	}
	
	//retrouver nom ou raison sociale
		@RequestMapping(value ="raisonAcheteurBoutik/{codeMembreAcheteur}", method = RequestMethod.GET)
		public @ResponseBody List<String> findRaisonSocialeAcheteur(@PathVariable("codeMembreAcheteur") String codeMembreAcheteur) {
			String nomAcheteur = "";
			String telephoneAcheteur = "";
			List<String> listInfos = Lists.newArrayList();
			if(StringUtils.isNotBlank(codeMembreAcheteur)){
					EuMembreMorale membremorale= moraleService.findById(codeMembreAcheteur);
					if(membremorale!=null){
						if(membremorale.getDesactiver()==0){
							nomAcheteur = membremorale.getRaisonSociale(); 
							telephoneAcheteur = membremorale.getPortableMembre();
							System.out.println("nomAcheteur= "+nomAcheteur);
							System.out.println("telephoneAcheteur= "+telephoneAcheteur);
						}
					}
			}
			listInfos.add(nomAcheteur);
			listInfos.add(telephoneAcheteur);
			return listInfos;
		}
	
	
	
	
	
	@ModelAttribute("codeMembreVendeur")
	public String retrouverCodeMembreVendeur() {
		String codeMembre="";

		EuUtilisateur utilisateur = getCurrentUser();
		String codeTegc = utilisateur.getCodeTegc();
		if(StringUtils.isNotBlank(codeTegc)){
			EuTegc euTegc = tegcService.getById(codeTegc);
			if (Objects.nonNull(euTegc)) {
				codeMembre = euTegc.getEuMembreMorale().getCodeMembreMorale();	
			}
		}
		return codeMembre;
		
	}
	//recuperation par categories
	@RequestMapping(value ="ReferenceArticleByCategorierea/{idArticleStockesCategorie}", method = RequestMethod.GET)
	public @ResponseBody List<String> findListRefenceParCategorierea(@PathVariable("idArticleStockesCategorie") Integer idArticleStockesCategorie) {
			List<String> listReference = Lists.newArrayList();
			listReference = euArticleStockeService.findReferenceByCategorie(idArticleStockesCategorie);
			return listReference;
	}
	
	//retrouver les categories
		@RequestMapping(value ="recuperationCategorierea/", method = RequestMethod.GET)
		public @ResponseBody List<EuArticleStockesCategorie> findListCategorieReaParTe() {
		List<EuArticleStockesCategorie> listCategorie = Lists.newArrayList();
			
		EuUtilisateur utilisateur = getCurrentUser();
		String codeTegc = utilisateur.getCodeTegc();
			if(StringUtils.isNotBlank(codeTegc)){
				listCategorie = euArticleStockesCategorieRepo.findCategorieByCodeTegc(codeTegc);
			}
		return listCategorie;
		}
	
		/*//recuperation par reference
		@RequestMapping(value ="infosArticleByReferencerea/{reference}", method = RequestMethod.GET)
		public @ResponseBody ArticleStocke findInfosParReferencerea(@PathVariable("reference") String reference) {
		EuArticleStockes euArticleStockes = euArticleStockeService.findArticleStockesByReference(reference);
		ArticleStocke articleStockes = new ArticleStocke();
			if(euArticleStockes!=null){
			articleStockes.setCategorie(euArticleStockes.getCategorie());
			articleStockes.setCodeBarre(euArticleStockes.getCodeBarre());
			articleStockes.setDesignation(euArticleStockes.getDesignation());
			articleStockes.setPrix(euArticleStockes.getPrix());
			articleStockes.setQuantite(euArticleStockes.getQteSolde());
			articleStockes.setType(euArticleStockes.getType());
			
			}
		return articleStockes;
		}*/
		
		//recuperation par reference
				@RequestMapping(value ="infosArticleByReferencerea/{reference}", method = RequestMethod.GET)
				public @ResponseBody ArticleStocke findInfosParReferencerea(@PathVariable("reference") String reference) {
					ArticleStocke articleStockes=null;
					//controller le TE
					String codeTegc;
					EuUtilisateur utilisateur = getCurrentUser();
					codeTegc = utilisateur.getCodeTegc();
					EuTegc euTegc = tegcService.getById(codeTegc);
					EuArticleStockes euArticleStockes = euArticleStockeService.findArticleStockesByReference(reference);
								
					if(euTegc!=null && euTegc.getTypeTegc().equalsIgnoreCase("INTERIM")){
						articleStockes = new ArticleStocke();
						if(euArticleStockes!=null){
							
							articleStockes.setCategorie(euArticleStockes.getCategorie());
							articleStockes.setReference(euArticleStockes.getReference());
							articleStockes.setDesignation(euArticleStockes.getDesignation());
							articleStockes.setPrix(euArticleStockes.getPrix());
							articleStockes.setQuantite(euArticleStockes.getQteSolde());
							articleStockes.setType(euArticleStockes.getType());
						}
						
					}else if(euTegc!=null && euTegc.getTypeTegc().equalsIgnoreCase("DISTRIBUTEUR")){
						articleStockes = new ArticleStocke();
						if(euArticleStockes!=null && euArticleStockes.getCategorie().equals(codeTegc)){
							
							articleStockes.setCategorie(euArticleStockes.getCategorie());
							articleStockes.setReference(euArticleStockes.getReference());
							articleStockes.setDesignation(euArticleStockes.getDesignation());
							articleStockes.setPrix(euArticleStockes.getPrix());
							articleStockes.setQuantite(euArticleStockes.getQteSolde());
							articleStockes.setType(euArticleStockes.getType());
						}
						
					}
					
					return articleStockes;
				
				}
						
		
		//recuperation des designations par tegc
		@ModelAttribute("infosDesignationByCodeTegcrea")
			public @ResponseBody List<ArticleStocke> findListDesignationByCodeTegc() {
				List<ArticleStocke> listArticles = Lists.newArrayList();
				List<EuArticleStockes> ListArticleStockes  = Lists.newArrayList();
				String codeTegc;
				EuUtilisateur utilisateur = getCurrentUser();
				codeTegc = utilisateur.getCodeTegc();
				if(StringUtils.isNotBlank(codeTegc)){
					ListArticleStockes = euArticleStockeService.findDesignationByCodeTegc(codeTegc);
				}
				if(!ListArticleStockes.isEmpty()){
					for(EuArticleStockes euArticleStocke :ListArticleStockes){
						ArticleStocke articleStocke = new ArticleStocke();
						articleStocke.setCodeBarre(euArticleStocke.getCodeBarre());
						articleStocke.setDesignation(euArticleStocke.getDesignation());
						articleStocke.setCategorie(euArticleStocke.getCategorie());
						articleStocke.setReference(euArticleStocke.getReference());
						articleStocke.setPrix(euArticleStocke.getPrix());
						articleStocke.setType(euArticleStocke.getType());
						articleStocke.setQuantite(euArticleStocke.getQteSolde());
						listArticles.add(articleStocke);
					}
				}
			
				return listArticles;	
			}
		
		
		
		
		//recuperation par tegc
		@ModelAttribute("infosReferenceByCodeTegcRea")
		public @ResponseBody List<String> findListRefenceByCodeTegcRea() {
				List<String> listReference = Lists.newArrayList();
				String codeTegc;
				EuUtilisateur utilisateur = getCurrentUser();
				codeTegc = utilisateur.getCodeTegc();
				System.out.println("codeTegc = "+codeTegc);
				if(StringUtils.isNotBlank(codeTegc)){
				listReference = euArticleStockeService.findReferenceByCodeTegc(codeTegc);
				System.out.println("listReference.size = "+listReference.size());
				
				}
				return listReference;
			}
	/*@ModelAttribute("reapproprks")
	public List<EuPrk> retrouverPrk() {
		String codeTegc;
		List<EuPrk> listPrk = Lists.newArrayList();
		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		if(StringUtils.isNotBlank(codeTegc)){
			listPrk = ListUtils.emptyIfNull(prkService.findValByTegc(codeTegc));
		}
		return listPrk;

	}*/

	@RequestMapping(value = "findprkrea/{codeTypeCredit}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Double retrouverPrkByCodeTypeCredit(@PathVariable("codeTypeCredit") String codeTypeCredit) {
		Double prk =0d;
		if(StringUtils.isNotBlank(codeTypeCredit)){
			EuTypeCredit euTypeCredit = typeCreditService.findById(codeTypeCredit);
			prk = euTypeCredit.getPrk();
		}
		return prk;	
	}

	@ModelAttribute("codeCreditReappro")
	public List<EuTypeCredit> retrouverCodeTypeCredit() {
		String codeTegc;
		List<EuPrk> listPrk = Lists.newArrayList();
		List<EuTypeCredit> listTypeCredit = Lists.newArrayList();
		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		if(StringUtils.isNotBlank(codeTegc)){
			listPrk = ListUtils.emptyIfNull(prkService.findValByTegc(codeTegc));
			listPrk.forEach((euPrk) -> {
				listTypeCredit.add(euPrk.getEuTypeCredit());
			});

		}
		return listTypeCredit;

	}

	/*@ModelAttribute("listtegcs")
	public List<EuTegc> retrouverTegc(@RequestParam("codeMembreAcheteur") String codeMembreAcheteur) {
		List<EuTegc> listTegc = new ArrayList<>();
		if(StringUtils.isNotBlank(codeMembreAcheteur)){ 
			listTegc = tegcService.findByCodeMembre(codeMembreAcheteur);
			System.out.println("listTegc.size = " + listTegc.size());
		}
		return listTegc;
	}*/

	@ModelAttribute("raisontegc")
	public String retrouverRaisonTegc() {
		 String nomTegc = "";
		 String codeTegc;

		 EuUtilisateur utilisateur = getCurrentUser();
		 codeTegc = utilisateur.getCodeTegc();
		 if(StringUtils.isNotBlank(codeTegc)){
			 EuTegc euTegc = tegcService.getById(codeTegc);
			 if (euTegc != null && StringUtils.isNotBlank(euTegc.getNomTegc()) && !euTegc.getTypeTegc().equals("PRESTATAIRE")) {
				 nomTegc = euTegc.getNomTegc().toUpperCase();
				 
			 }
		 }
		 return nomTegc;
	}


	@RequestMapping(value = "listtegc/{codeMembreAcheteur}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<EuTegc> retrouverListTegc(@PathVariable("codeMembreAcheteur") String codeMembreAcheteur) {
		List<EuTegc> listTegc = Lists.newArrayList();
		if(StringUtils.isNotBlank(codeMembreAcheteur)){ 
			listTegc = tegcService.findByCodeMembre(codeMembreAcheteur);
			System.out.println("listTegc.size = " + listTegc.size());
		}
		return listTegc;
	}


	@RequestMapping(value = "reapprordinaires", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Integer retrouverTypeMarchandise() {
		Integer type1 = 0;
		String codeTegc;
		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		if(StringUtils.isNotBlank(codeTegc)){
			EuTegc euTegc = tegcService.getById(codeTegc);
			if (euTegc != null) {
				if (euTegc.getOrdinaire() == 1 && euTegc.getSpecial() == 1) {
					type1 = 1;
				}
				if (euTegc.getOrdinaire() == 1 && euTegc.getSpecial() == 0) {
					type1 = 2;
				}
				if (euTegc.getOrdinaire() == 0 && euTegc.getSpecial() == 1) {
					type1 = 3;
				}
				if (euTegc.getOrdinaire() == 0 && euTegc.getSpecial() == 0) {
					type1 = 4;
				}
			}
		}
		return type1;
	}

	// sendSms
	@RequestMapping(value = "/sendSmsCodeBon", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String> sendSmsForVerification(HttpServletRequest request){
		//String smsBody;
		//String codeBonconso;
		Double montantBonCommande = 0.0;
		double montantGcp; 
		List<String> listCode = new ArrayList<>();
		Double prk =5.6;
		Boolean verifyQuota = false;
		
		
		String codeMembreAcheteur = (String) request.getParameter("codeMembreAcheteur");
		Double montantAchat = Double.valueOf(request.getParameter("montantAchat"));
		String codeTegc = (String) request.getParameter("codeTegc");
		String typeRea = (String) request.getParameter("typeRea");
		String codeTypeCredit = (String) request.getParameter("codeTypeCredit");
	
		
		if (StringUtils.isNotBlank(codeMembreAcheteur)&&StringUtils.isNotBlank(codeTypeCredit) && StringUtils.isNotBlank(typeRea)
				&& StringUtils.isNotBlank(codeTegc) && !Double.isNaN(montantAchat) && !Double.isNaN(prk)) {
			
			    montantBonCommande = (double) Math.floor(montantAchat);
			    System.out.println("montantBonCommande= "+montantBonCommande);
			    System.out.println("codeTegcAcheteur= "+codeTegc);
			   //retrouver le montant du gcp et comparer avec le montant Achat
				montantGcp = gcpService.getSoldeByTegcAndType(codeTegc, "DIST");
				
				
				/*}else{
					montantGcp = tegcService.getSoldeByMembreAndTe(codeMembreAcheteur, codeTegc);
				}*/

				if(montantGcp >= montantBonCommande){
					//verifions le quota
					verifyQuota = quotaUtility.verifyQuota(codeTypeCredit, codeMembreAcheteur, montantBonCommande, "nr");
					
				    if(verifyQuota){

					// generer un code unique
					//String codeEnvoi = ServerUtil.GenererUniqueCode().substring(0,5).toUpperCase();
					//codeBonconso = ServerUtil.GenererUniqueCode().substring(0,5).toUpperCase();

					//envoi de sms
					/*smsBody = "ESMC: Le code " + codeBonconso+ " de votre Bon de Consommation de: "
							+ montantBonCommande*/ ;

					listCode.add("OK");
					return listCode;
					/*boolean result = smsComponent.createAndSendCode(codeEnvoi, codeMembreAcheteur, smsBody);
					if(result){
					return listCode;	
						}*/
					}
				  listCode.add("KO1");//Vous avez depasse le quota
				  return listCode;
				}
				listCode.add("KO2");//le montant des GCP est insuffisant
				return listCode;

			}
		listCode.add("KO3");//revoir la saisie
		return listCode;
	}


	@RequestMapping(value = "/creerBonCommande", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Double creerBonCommande(HttpServletRequest request){
		String reponse = "KO";
		String typeR ="rea";
		boolean echange=false;
		Double montantGcp=0d;
		EuBon bonCommande = null;
	//	EuMembreMorale euMembreMorale = null;
		String compteVendeur;
		Integer subvention = 0;
		double prk=0d;
		String typeProduit="";
		String nomProduit = "";
		String codeMembreVendeur;
		
		//reglement = "codeTegc="+$codeTegc+"&codeTypeCredit="+$codeTypeCredit+"&codeEnvoi="+$codeEnvoi+"&codeBonConso="+$codeBonConso+"&codeMembreAcheteur="+$codeMembreAcheteur+"&montantAchat="+$montantAchatHT+"&listArticlesVendus="+$jsonlist+"&_csrf=" + $csrf + "";
		
		String codeMembreAcheteur = (String) request.getParameter("codeMembreAcheteur");
		Double montantBonCommande = Double.valueOf(request.getParameter("montantAchat"));
		String codeTegc = (String) request.getParameter("codeTegc");
		//String codeBonConso = (String) request.getParameter("codeBonConso");
		//String codeEnvoi = (String) request.getParameter("codeEnvoi");
		String codeTypeCredit = (String) request.getParameter("codeTypeCredit");
		List<ArticleVendu> ListArticlesVendus = mapArticle(request);
		String adresseLivraison = (String) request.getParameter("adresseLivraison");
		Integer modeLivraison = Integer.valueOf(request.getParameter("modeLivraison"));
		Double fraisLivraison = Double.valueOf(request.getParameter("fraisLivraison"));
		String referenceAdditive = (String) request.getParameter("referenceAdditive");
		String telephoneAcheteur = (String) request.getParameter("telephoneAcheteur");
		

		// retrouver le vendeur connecte
		EuUtilisateur utilisateur = BaseController.getCurrentUser();
		
		//String codeMembreVendeur = utilisateur.getCodeMembre();
		
		if (StringUtils.isNotBlank(codeMembreAcheteur) && !Double.isNaN(montantBonCommande) 
				&& StringUtils.isNotBlank(codeTypeCredit) && StringUtils.isNotBlank(codeTegc)) {

			
			// retrouver le tegc du membre morale vendeur
			String codeTegcVendeur = utilisateur.getCodeTegc();
			EuTegc euTegc = tegcRepo.findOne(codeTegcVendeur);
			if(Objects.nonNull(euTegc)){
				// retrouver le vendeur
				codeMembreVendeur = euTegc.getEuMembreMorale().getCodeMembreMorale();
				// retrouver le compte gcp du vendeur
				compteVendeur = "NB-TPAGCP-" + codeMembreVendeur;
				
				nomProduit = euTegc.getNomProduit();
				subvention = euTegc.getSubvention();
				
				EuTypeCredit euTypeCredit = typeCreditService.findById(codeTypeCredit);
								
				if(euTypeCredit!=null){
					typeProduit = euTypeCredit.getTypeProduit();
					prk = euTypeCredit.getPrk();
				}
				
				if(modeLivraison ==1){
					//ajout de articleVendu
				    ArticleVendu articleLivraison = new ArticleVendu();
		            articleLivraison.setCodeMembreAcheteur(codeMembreAcheteur);
		           	articleLivraison.setCodeTegc(euTegc.getCodeTegc());
		            articleLivraison.setCodeBarre("FLIV");
		            articleLivraison.setDesignation("Frais de livraison");
		            articleLivraison.setReference("FLIV");
		            articleLivraison.setQuantite(1);
		            articleLivraison.setPrix(fraisLivraison);
		            ListArticlesVendus.add(articleLivraison);
					}
				
				
			//if(smsComponent.verifyCodeSms(codeEnvoi, codeBonConso, codeMembreAcheteur)){

				//retrouver le montant du gcp et comparer avec le montant Achat
				//if(codeTegc.equals("")){
				//	montantGcp = gcpService.getSoldeByTegcAndType(codeMembreAcheteur, "DIST");
				/*}else{
					montantGcp = tegcService.getSoldeByMembreAndTe(codeMembreAcheteur, codeTegc);
				}*/
				montantGcp = gcpService.getSoldeByTegcAndType(codeTegc, "DIST");
				
				if(montantGcp >= montantBonCommande){

					//echange de gcp en Inr
					echange = echangeService.echangeGCP(codeMembreAcheteur, codeTegc,"GCP", prk, montantBonCommande, typeProduit);
					if(echange){
						CalculBonInfo calculBonInfo = new CalculBonInfo();
						calculBonInfo.setCatBon("nr");
						calculBonInfo.setTypeRecurrent("");
						calculBonInfo.setTypeProduit(typeProduit);
						calculBonInfo.setPrk(prk);
						calculBonInfo.setDuree(0 );

						bonCommande = transfertUtility.tansfertBC(codeMembreAcheteur, "I", calculBonInfo, prk,
								montantBonCommande);

						if (bonCommande != null) {
							
							if(subvention==1){
								//payement au gcsc
								reponse = reglementAchat.saveReglementParBonAuGcs(codeTegcVendeur, compteVendeur,
										bonCommande.getBonNumero(), typeR, typeProduit, codeTypeCredit, nomProduit, utilisateur.getIdUtilisateur(),
										ListArticlesVendus);
							}else{
								reponse = reglementAchat.saveReglementSimpleParBon("", 0, codeTegcVendeur, compteVendeur,
										bonCommande.getBonNumero(), typeR, typeProduit, codeTypeCredit, nomProduit, utilisateur.getIdUtilisateur(),
										ListArticlesVendus);
							}
						  
							if(StringUtils.isNotBlank(reponse) && reponse.equals("OK")){
								//mise a jour de smsconnexion
							
							//smsComponent.miseAjourSmsConnexion(codeEnvoi);
							//verification de la reference additive
							/*if(StringUtils.isNotBlank(referenceAdditive)){
							EuInformationAdditif euInformationAdditif =euInfoAdditifService.retrouverInformationAd(codeMembreAcheteur, referenceAdditive); 
							
							}*/
							//creation d'un code de livraison
						    String codeLivraison = ServerUtil.GenererUniqueCode().substring(0,5).toUpperCase();
							//creation d'une ligne de commande et de ses détails
							EuCommande commandeNew = new EuCommande();
							if(modeLivraison==1){
								commandeNew.setAdresseLivraison(adresseLivraison);
								commandeNew.setFraisLivraison(fraisLivraison);
																
							}else{
								commandeNew.setAdresseLivraison(" ");
								commandeNew.setFraisLivraison(0d);
							}
							commandeNew.setLivrer(0);
							commandeNew.setDateLivraison(new Date());
							commandeNew.setCodeLivraison(codeLivraison);
							commandeNew.setExecuter(1);
							commandeNew.setCodeMembreAcheteur(codeMembreAcheteur);
							commandeNew.setDateCommande(new Date());
							EuTegc euTegcVendeur = tegcService.getById(utilisateur.getCodeTegc());
							if (Objects.nonNull(euTegcVendeur)) {
								commandeNew.setCodeMembreVendeur(euTegcVendeur.getEuMembreMorale().getCodeMembreMorale());
							}
							
							//le tegc du vendeur (a changer apres)
							commandeNew.setCodeMembreLivreur(utilisateur.getCodeTegc());
							commandeNew.setCodeZone(utilisateur.getCodeZone());
							
							EuMembreMorale membreMoral= moraleService.findById(codeMembreAcheteur);
							
							if (Objects.nonNull(membreMoral.getEuPay())) {
								commandeNew.setIdPays(membreMoral.getEuPay().getIdPays());
							}
							//commandeNew.setIdPrefecture(membreMoral.getp);
							//commandeNew.setIdRegion(utilisateur.getr);
							commandeNew.setQuartierAcheteur(membreMoral.getQuartierMembre());
							commandeNew.setVilleAcheteur(membreMoral.getVilleMembre());	
							
							
							commandeNew.setModeLivraison(modeLivraison);
							commandeNew.setTelAcheteur(telephoneAcheteur);
							
							commandeNew.setMontantCommande(montantBonCommande);
							commandeNew.setTypeBon("BL");
							commandeNew.setTypeRecurrent(typeR);
							commandeNew.setEuUtilisateur(utilisateur);
							//commandeNew.set
							euCommandeRepo.saveAndFlush(commandeNew);
							//Long idCommandeNew = commandeNew.getCodeCommande();
							
							//enlever les frais de sms
							//ListArticlesVendus = ListArticlesVendus.stream().filter(articleVendu ->!articleVendu.getCodeBarre().equals("FSMS")).collect(Collectors.toList());
					     	
							
							
							for (ArticleVendu articleVendu : ListArticlesVendus){
								EuDetailCommande detailCommande = new EuDetailCommande();
								detailCommande.setCodeBarre(articleVendu.getCodeBarre());
								detailCommande.setEuCommande(commandeNew);
								detailCommande.setLivrer(0);
								detailCommande.setPrixUnitaire(articleVendu.getPrix());
								detailCommande.setDesignation(articleVendu.getDesignation());
								detailCommande.setQte(articleVendu.getQuantite());
								detailCommande.setReference(articleVendu.getReference());
								detailCommande.setCommander(1);
								detailCommandeService.add(detailCommande);
								
								//AJOUT dans eu_detail_commande_additif
								if(StringUtils.isNotBlank(referenceAdditive)){
								EuDetailCommandeAdditif eudetComAdd = new EuDetailCommandeAdditif();
									
								EuTegc euTegcMoral = tegcService.getById(articleVendu.getCodeTegc());
								if(euTegcMoral!=null){
									EuMembreMorale moraleVendeur = euTegcMoral.getEuMembreMorale();
									System.out.println("ref ad= "+referenceAdditive);
									if(Objects.nonNull(moraleVendeur)){
									EuArticleStockesAdditif euArticleStockesAdditif = euArtStockesAddService.findArticleStockAdditifByMembreAndReference(moraleVendeur.getCodeMembreMorale(), articleVendu.getReference());
									if(Objects.nonNull(euArticleStockesAdditif)){											
										eudetComAdd.setEuArticleStockesAdditif(euArticleStockesAdditif);
										eudetComAdd.setEuDetailCommande(detailCommande);
										eudetComAdd.setReferenceAdditif(referenceAdditive);
										euDetComAdditifService.add(eudetComAdd);
										}
									  }
									}
								}
								
								//Mise a jour des articles stockes
																	
								EuArticleStockes articleStocke = euArticleStockeService.findArticleStockesByReference(articleVendu.getReference());
								if(articleStocke!=null){
								articleStocke.setQteVendu(articleStocke.getQteVendu()+articleVendu.getQuantite());
								articleStocke.setQteSolde(articleStocke.getQteSolde()-articleVendu.getQuantite());
								euArticleStockeService.update(articleStocke);
								}
							}
							//envoi de sms de livraison
							//if(modeLivraison==1){
								String smsBody = "Merci de presenter le code :"+codeLivraison+" a la livraison de votre commande N° "+commandeNew.getCodeCommande();
								smsComponent.sendNotifications(codeMembreAcheteur, smsBody);
								
							/*}else{
								String smsBody = "Votre commande : "+commandeNew.getCodeCommande()+" a été bien exécutée";
								smsComponent.sendNotifications(codeMembreAcheteur, smsBody);
								
							 }	*/				
							
							return Double.valueOf(0);
							}

						}
						//return reponse="KO1";//Echec de creation du bon de commande
						return Double.valueOf(1);
					}
					//return reponse="KO1";//Echec de l'operation d 'échange
					return Double.valueOf(2);
				}
				//return reponse="KO1";//montant des GCP insuffisant
				return Double.valueOf(3);
			  //}
		    }	
	     
			
				//return reponse="KO4";//le code du bon de commande est invalide
			return Double.valueOf(4);
		}
		//return reponse="KO5";//revoir la saisie
		return Double.valueOf(5);
	}

	// Recuperation de la liste des articles vendus
	public List<ArticleVendu> mapArticle(HttpServletRequest request) {

		List<ArticleVendu> listArticles = Lists.newArrayList();
		ArticleVendu[] array;

		try {
			ObjectMapper jsonMapper = new ObjectMapper();
			array = jsonMapper.readValue(request.getParameter("listArticlesVendus"), ArticleVendu[].class);
			listArticles.addAll(Arrays.asList(array));
			return listArticles;
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return null;

	}

	@RequestMapping(value = "/searchCommandeAndSendSms", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String> searchCommandeAndSendSms(HttpServletRequest request) {
		String smsBody;
		String codeBonconso;
		List<String> listCode = new ArrayList<>();

		String codeMembreAcheteur = (String) request.getParameter("codeMembreAcheteur");
		String codeCommande = (String) request.getParameter("codeCommande");
		String numeroTelephone = (String) request.getParameter("numeroTelephone");
		

		if (StringUtils.isNotBlank(codeMembreAcheteur) && StringUtils.isNotBlank(codeCommande) && StringUtils.isNotBlank(numeroTelephone)) {

			//recherche de la commande 
			EuCommandeNr commande = commandeNrService.findCommandeNrByCodeCommande(codeCommande);

			if(commande!=null){
				if(commande.getCodeMembre().equals(codeMembreAcheteur)){

					// generer un code unique
					String codeEnvoi = ServerUtil.GenererUniqueCode().substring(0,5).toUpperCase();
					codeBonconso = ServerUtil.GenererUniqueCode().substring(0,5).toUpperCase();

					smsBody = "ESMC: Votre code " + codeBonconso + " du Bon de Commande de: "+ Math.floor(commande.getMontant()) ;
					listCode.add(codeEnvoi);
					boolean result = smsComponent.createAndSendCode(codeEnvoi, codeMembreAcheteur, smsBody);
					System.out.println("result= "+result);
					if(result){
						return listCode;	
					}
					listCode.add("KO0");
					return listCode;
				} 
				listCode.add("KO1");
				return listCode;//le code membre n est pas valide pour cette commande
			}
			listCode.add("KO2");
			return listCode;//La commande n est pas valide

		}
		listCode.add("KO3");
		return listCode;//Revoir la saisie des données

	}


	/*@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/executerCommande", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Double executerCommande(HttpServletRequest request){
		String reponse = "KO";
		boolean echange;
		EuBon bonCommande = null;
		//EuMembreMorale euMembreMorale = null;
		String compteVendeur;
		double montantBonOpi;
		String typeR = "nr";
		Integer subvention;
		String typeProduit = "";
		Double prk =0d;
		String codeMembreVendeur ="";
		String nomProduit ="";
		//String typeBC;
		
		String codeMembreAcheteur = (String) request.getParameter("codeMembreAcheteur");
		String codeCommande = (String) request.getParameter("codeCommande");
		String codeEnvoi = (String) request.getParameter("codeEnvoi");
		String codeBonConso = (String) request.getParameter("codeBonConso");
		String codeTypeCredit = (String) request.getParameter("codeTypeCredit");
		String codeTegc = (String) request.getParameter("codeTegc");

		// retrouver le vendeur connecte
		EuUtilisateur utilisateur = BaseController.getCurrentUser();
		
		if (StringUtils.isNotBlank(codeMembreAcheteur) && StringUtils.isNotBlank(codeCommande)
				&& StringUtils.isNotBlank(codeBonConso) && StringUtils.isNotBlank(codeEnvoi)
				&& StringUtils.isNotBlank(codeTypeCredit)&& StringUtils.isNotBlank(codeTegc)) {

			// retrouver le tegc du membre morale vendeur
			String codeTegcVendeur = utilisateur.getCodeTegc();
			EuTegc euTegc = tegcRepo.findOne(codeTegcVendeur);
			if(Objects.nonNull(euTegc)){
				
				// retrouver le vendeur
				codeMembreVendeur = euTegc.getEuMembreMorale().getCodeMembreMorale();
				// retrouver le compte gcp du vendeur
				compteVendeur = "NB-TPAGCP-" + codeMembreVendeur;
				// retrouver le tegc du membre morale vendeur
				nomProduit = euTegc.getNomProduit();
				subvention = euTegc.getSubvention();	
			
			EuTypeCredit euTypeCredit = typeCreditService.findById(codeTypeCredit);
			
			if(euTypeCredit!=null){
				typeProduit = euTypeCredit.getTypeProduit();
				prk = euTypeCredit.getPrk();
			}

			// recherche du montant a utiliser dans le bon neutre
			if(smsComponent.verifyCodeSms(codeEnvoi, codeBonConso, codeMembreAcheteur)){

				//recherche de la commande 

				EuCommandeNr commande = commandeNrService.findCommandeNrByCodeCommande(codeCommande);

				if(commande!=null){

					montantBonOpi = commande.getMontant();

					//recherche de la liste des articles vendus
					List<EuDetailCommandeNr> listDetailCommande = new ArrayList<>();
					List<ArticleVendu> ListArticlesVendus = new ArrayList<ArticleVendu>();

					listDetailCommande  = detailCommandeNrService.findDetailsCommandeByCodeCommande(codeCommande);

					if(!listDetailCommande.isEmpty()){

						listDetailCommande.forEach(detailCommande -> {
							ListArticlesVendus.add(new ArticleVendu("", commande.getCodeMembre(), detailCommande.getReference(), detailCommande.getDesignation(), detailCommande.getQuantite(), detailCommande.getPrixUnitaire()));
						});
					}

					List<EuTraite> ListTraites = commandeNrOpiService.findTraiteByCodeCommande(codeCommande);

					if(!ListTraites.isEmpty()){
						echange = echangeUtility.echangeOpiCommandeNr(codeMembreAcheteur, ListTraites, montantBonOpi);
					
						if(echange){

							CalculBonInfo calculBonInfo = new CalculBonInfo();
							calculBonInfo.setCatBon("nr");
							calculBonInfo.setTypeRecurrent("");
							calculBonInfo.setTypeProduit(typeProduit);
							calculBonInfo.setPrk(prk);
							calculBonInfo.setDuree(0 );

							bonCommande = transfertUtility.tansfertBC(codeMembreAcheteur, "I", calculBonInfo, prk,
									montantBonOpi);


							if (bonCommande != null) {
									//rechercher si le vendeur est beneficiaire d'une subvention
									if(subvention == 1){
										//payement au gcsc

										reponse = reglementAchat.saveReglementParBonAuGcs(codeTegcVendeur, compteVendeur,
												bonCommande.getBonNumero(), typeR, typeProduit, codeTypeCredit, nomProduit, utilisateur.getIdUtilisateur(),
												ListArticlesVendus);
									}else{
										//payement au tegc
										reponse = reglementAchat.saveReglementSimpleParBon("", 0, codeTegcVendeur, compteVendeur,
												bonCommande.getBonNumero(), typeR, typeProduit, codeTypeCredit, nomProduit, utilisateur.getIdUtilisateur(),
												ListArticlesVendus);
									}
									System.out.println("reponse reglement simple : " + reponse);
									//mise a jour de smsconnexion
									smsComponent.miseAjourSmsConnexion(codeEnvoi);

									//mise a jour de la commande
									commande.setCodeMembreFournisseur(codeMembreVendeur);
									commande.setStatut(2);
									commande.setDateLivraison(new Date());
									commandeNrService.update(commande);
									return Double.valueOf(0);
									
									
								}
								return Double.valueOf(1); //echec de creation du bc
						}
						return Double.valueOf(3);//echec de l'echange 
					}
				}
				return Double.valueOf(4);//commande non trouvée ou déja exécutée
			}
			return Double.valueOf(5); //le code du bon invalide
		}
	}
		return Double.valueOf(6); //revoir la saisie
	}*/

}




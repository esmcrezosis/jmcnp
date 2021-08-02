/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.web.controller.obps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
//import java.util.stream.Collectors;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dao.repository.obps.EuArticleStockesCategorieRepository;
import com.esmc.mcnp.dao.repository.obps.EuCommandeRepository;
import com.esmc.mcnp.dao.repository.others.EuTegcRepository;
import com.esmc.mcnp.domain.dto.bc.CalculBonInfo;
import com.esmc.mcnp.domain.dto.obps.ArticleStocke;
import com.esmc.mcnp.domain.dto.obps.ArticleStockeCategorie;
import com.esmc.mcnp.domain.dto.obps.ArticleVendu;
import com.esmc.mcnp.domain.entity.acteur.EuEli;
import com.esmc.mcnp.domain.entity.ba.EuCapa;
import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.bc.EuPrk;
import com.esmc.mcnp.domain.entity.bc.EuTypeCredit;
import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.cm.EuTelephone;
import com.esmc.mcnp.domain.entity.obps.EuArticleStockes;
import com.esmc.mcnp.domain.entity.obps.EuArticleStockesAdditif;
import com.esmc.mcnp.domain.entity.obps.EuArticleStockesCategorie;
import com.esmc.mcnp.domain.entity.obps.EuBps;
import com.esmc.mcnp.domain.entity.obps.EuCommande;
import com.esmc.mcnp.domain.entity.obps.EuDetailCommande;
import com.esmc.mcnp.domain.entity.obps.EuDetailCommandeAdditif;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutre;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreAppro;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreApproDetail;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreApproDetailPK;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreDetail;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreUtilise;
import com.esmc.mcnp.domain.entity.org.EuCanton;
import com.esmc.mcnp.domain.entity.others.EuAchatInterim;
import com.esmc.mcnp.domain.entity.others.EuInformationAdditif;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.infrastructure.components.BonAchatComponent;
import com.esmc.mcnp.infrastructure.components.CreditUtility;
import com.esmc.mcnp.infrastructure.components.EchangeService;
import com.esmc.mcnp.infrastructure.components.QuotaUtility;
import com.esmc.mcnp.infrastructure.components.ReglementAchat;
import com.esmc.mcnp.infrastructure.components.SmsComponent;
import com.esmc.mcnp.infrastructure.components.SouscriptionBon;
import com.esmc.mcnp.infrastructure.components.TransfertUtility;
import com.esmc.mcnp.commons.exception.business.CompteNonIntegreException;
import com.esmc.mcnp.commons.exception.business.CompteNonTrouveException;
import com.esmc.mcnp.commons.exception.business.SoldeInsuffisantException;
import com.esmc.mcnp.infrastructure.services.acteurs.EuDetailEliService;
import com.esmc.mcnp.infrastructure.services.acteurs.EuEliService;
import com.esmc.mcnp.infrastructure.services.ba.EuBonNeutreApproDetailService;
import com.esmc.mcnp.infrastructure.services.ba.EuBonNeutreApproService;
import com.esmc.mcnp.infrastructure.services.ba.EuBonNeutreDetailService;
import com.esmc.mcnp.infrastructure.services.ba.EuBonNeutreService;
import com.esmc.mcnp.infrastructure.services.ba.EuBonNeutreUtiliseService;
import com.esmc.mcnp.infrastructure.services.ba.EuCapaService;
import com.esmc.mcnp.infrastructure.services.cm.EuCompteCreditService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreService;
import com.esmc.mcnp.infrastructure.services.common.EuTelephoneService;
import com.esmc.mcnp.infrastructure.services.common.EuTypeCreditService;
import com.esmc.mcnp.infrastructure.services.obps.EuAchatInterimService;
import com.esmc.mcnp.infrastructure.services.obps.EuArticleStockeService;
import com.esmc.mcnp.infrastructure.services.obps.EuArticleStockesAdditifService;
import com.esmc.mcnp.infrastructure.services.obps.EuBpsService;
import com.esmc.mcnp.infrastructure.services.obps.EuDetailCommandeAdditifService;
import com.esmc.mcnp.infrastructure.services.obps.EuDetailCommandeService;
import com.esmc.mcnp.infrastructure.services.obps.EuGcpService;
import com.esmc.mcnp.infrastructure.services.obps.EuInformationAdditifService;
import com.esmc.mcnp.infrastructure.services.obps.EuPrkService;
import com.esmc.mcnp.infrastructure.services.obps.EuTegcService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

/**
 *
 * @author Administrateur
 */
@Controller
public class BoutiqueController extends BaseController {

	private final SouscriptionBon souscriptionBon;
	private final TransfertUtility transfertUtility;
	private final QuotaUtility quotaUtility;
	private final CreditUtility creditUtility;
	private final ReglementAchat reglementAchat;
	private final SmsComponent smsComponent;
	private final EuMembreMoraleService moraleService;
	private final EuMembreService membreService;
	private final EuPrkService prkService;
	private final EuTypeCreditService typeCreditService;
	private final EuBonNeutreService bonNeutreService;
	private final EuBonNeutreDetailService bonNeutreDetailService;
	private final EuBonNeutreApproDetailService bonNeutreApproDetailService;
	private final EuBonNeutreUtiliseService bonNeutreutiliserService;
	private final EuBonNeutreApproService bonNeutreApproService;
	private final EuTegcService tegcService;
	private final EuCapaService capaService;
	private final EuCompteCreditService compteCreditService;
	private final EuBpsService bpsService;
	private final EuTelephoneService telephoneService;
	private final EuTegcRepository tegcRepo;
	private final EuGcpService gcpService;
	private final EchangeService echangeService;
	private final EuArticleStockeService euArticleStockeService;
	private final BonAchatComponent bonAchatComponent;
	private final EuDetailCommandeService detailCommandeService;
	private final EuCommandeRepository euCommandeRepo;
	private final EuArticleStockesCategorieRepository euArticleStockesCategorieRepo;
	private final EuInformationAdditifService euInfoAdditifService;
	private final EuDetailCommandeAdditifService euDetComAdditifService;
	private final EuArticleStockesAdditifService euArtStockesAddService;
	private final EuAchatInterimService euAchatInterimService;
	private final EuEliService eliService;
	private final EuDetailEliService detailEliService;

	public BoutiqueController(SouscriptionBon souscriptionBon, TransfertUtility transfertUtility,
			QuotaUtility quotaUtility, CreditUtility creditUtility, ReglementAchat reglementAchat,
			SmsComponent smsComponent, EuMembreMoraleService moraleService, EuMembreService membreService,
			EuPrkService prkService, EuTypeCreditService typeCreditService, EuBonNeutreService bonNeutreService,
			EuBonNeutreDetailService bonNeutreDetailService, EuBonNeutreApproDetailService bonNeutreApproDetailService,
			EuBonNeutreUtiliseService bonNeutreutiliserService, EuBonNeutreApproService bonNeutreApproService,
			EuTegcService tegcService, EuCapaService capaService, EuCompteCreditService compteCreditService,
			EuBpsService bpsService, EuTelephoneService telephoneService, EuTegcRepository tegcRepo,
			EuGcpService gcpService, EchangeService echangeService, EuArticleStockeService euArticleStockeService,
			EuDetailCommandeService detailCommandeService, EuCommandeRepository euCommandeRepo,
			EuArticleStockesCategorieRepository euArticleStockesCategorieRepo,
			EuInformationAdditifService euInfoAdditifService, EuDetailCommandeAdditifService euDetComAdditifService,
			EuArticleStockesAdditifService euArtStockesAddService, EuAchatInterimService euAchatInterimService,
			EuEliService eliService, EuDetailEliService detailEliService, BonAchatComponent bonAchatComponent) {
		this.souscriptionBon = souscriptionBon;
		this.transfertUtility = transfertUtility;
		this.quotaUtility = quotaUtility;
		this.creditUtility = creditUtility;
		this.reglementAchat = reglementAchat;
		this.smsComponent = smsComponent;
		this.moraleService = moraleService;
		this.membreService = membreService;
		this.prkService = prkService;
		this.typeCreditService = typeCreditService;
		this.bonNeutreService = bonNeutreService;
		this.bonNeutreDetailService = bonNeutreDetailService;
		this.bonNeutreApproDetailService = bonNeutreApproDetailService;
		this.bonNeutreutiliserService = bonNeutreutiliserService;
		this.bonNeutreApproService = bonNeutreApproService;
		this.tegcService = tegcService;
		this.capaService = capaService;
		this.compteCreditService = compteCreditService;
		this.bpsService = bpsService;
		this.telephoneService = telephoneService;
		this.tegcRepo = tegcRepo;
		this.gcpService = gcpService;
		this.echangeService = echangeService;
		this.euArticleStockeService = euArticleStockeService;
		this.bonAchatComponent = bonAchatComponent;
		this.detailCommandeService = detailCommandeService;
		this.euCommandeRepo = euCommandeRepo;
		this.euArticleStockesCategorieRepo = euArticleStockesCategorieRepo;
		this.euInfoAdditifService = euInfoAdditifService;
		this.euDetComAdditifService = euDetComAdditifService;
		this.euArtStockesAddService = euArtStockesAddService;
		this.euAchatInterimService = euAchatInterimService;
		this.eliService = eliService;
		this.detailEliService = detailEliService;
	}

	@RequestMapping(value = "/boutique", method = RequestMethod.GET)
	public String boutique() {
		return "distributeur/boutique";
	}

	// recuperation par tegc
	@ModelAttribute("infosReferenceByCodeTegc")
	public @ResponseBody List<String> findListRefenceByCodeTegc() {
		List<String> listReference = Lists.newArrayList();
		String codeTegc;
		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		if (StringUtils.isNotBlank(codeTegc)) {
			listReference = euArticleStockeService.findReferenceByCodeTegc(codeTegc);
		}
		return listReference;
	}

	// recuperation des designations par tegc
	@ModelAttribute("infosDesignationByCodeTegc")
	public @ResponseBody List<ArticleStocke> findListDesignationByCodeTegc() {
		List<ArticleStocke> listArticles = Lists.newArrayList();
		List<EuArticleStockes> ListArticleStockes = Lists.newArrayList();
		String codeTegc;
		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		if (StringUtils.isNotBlank(codeTegc)) {
			ListArticleStockes = euArticleStockeService.findDesignationByCodeTegc(codeTegc);
		}
		if (!ListArticleStockes.isEmpty()) {
			for (EuArticleStockes euArticleStocke : ListArticleStockes) {
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

	@RequestMapping(value = "/signerEli", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Integer retrouverFranchiseEli() {
		Integer signerELi = 0;
		String codeTegc;
		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		if (StringUtils.isNotBlank(codeTegc)) {
			// retrouver EuELi
			List<EuEli> listEuEli = eliService.findByCodeTegc(codeTegc);
			if (!listEuEli.isEmpty()) {
				signerELi = 1;
			}
		}
		return signerELi;
	}

	/*
	 * public Double RetrouverFraisSms(){ Double fraisSms =
	 * parametreService.getParametre("FSMS", "Frais de SMS"); return fraisSms; }
	 */

	@ModelAttribute("codeMembreVendeur")
	public String retrouverCodeMembreVendeur() {
		String codeMembre = "";

		EuUtilisateur utilisateur = getCurrentUser();
		String codeTegc = utilisateur.getCodeTegc();
		if (StringUtils.isNotBlank(codeTegc)) {
			EuTegc euTegc = tegcService.getById(codeTegc);
			if (Objects.nonNull(euTegc)) {
				codeMembre = euTegc.getEuMembreMorale().getCodeMembreMorale();
			}
		}
		return codeMembre;

	}

	@RequestMapping(value = "findCodeMembreInterimAchat/{codeAchat}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String findCodeMembreInterimAchat(@PathVariable("codeAchat") String codeAchat) {
		String codeMembreAcheteur = "";
		if (StringUtils.isNotBlank(codeAchat)) {
			EuAchatInterim euAchatInterim = euAchatInterimService.findAchatInterimByCodeAchat(codeAchat);
			codeMembreAcheteur = euAchatInterim.getCodeMembre();
		}
		return codeMembreAcheteur;
	}

	// recuperation infos additives du membre par reference
	@RequestMapping(value = "infosAdditiveByMembreAndReference/{codeMembre}/{reference}", method = RequestMethod.GET)
	public @ResponseBody List<String> findInfosAdditiveByMembreAndReference(
			@PathVariable("codeMembre") String codeMembre, @PathVariable("reference") String reference) {
		List<String> listInfosAdditives = Lists.newArrayList();
		List<EuInformationAdditif> listInfos = euInfoAdditifService.retrouverInformationAd(codeMembre, reference);
		if (!listInfos.isEmpty()) {
			listInfosAdditives = listInfos.stream().map(infoAdditive -> infoAdditive.getLibelleInformationAdditif())
					.collect(Collectors.toList());
		}
		return listInfosAdditives;
	}

	// retrouver les categories
	@RequestMapping(value = "recuperationCategorie/", method = RequestMethod.GET)
	public @ResponseBody List<ArticleStockeCategorie> findListCategorieParTe() {
		List<ArticleStockeCategorie> listCategorie = Lists.newArrayList();
		List<EuArticleStockesCategorie> listArticleStockesCategorie = Lists.newArrayList();
		EuUtilisateur utilisateur = getCurrentUser();
		String codeTegc = utilisateur.getCodeTegc();
		if (StringUtils.isNotBlank(codeTegc)) {
			listArticleStockesCategorie = euArticleStockesCategorieRepo.findCategorieByCodeTegc(codeTegc);
			if (!listArticleStockesCategorie.isEmpty()) {
				for (EuArticleStockesCategorie euArticlestocke : listArticleStockesCategorie) {
					ArticleStockeCategorie articleCategorie = new ArticleStockeCategorie();
					articleCategorie.setIdCategorie(euArticlestocke.getIdArticleStockesCategorie());
					articleCategorie.setNomCategorie(euArticlestocke.getNomArticleStockesCategorie());
					listCategorie.add(articleCategorie);
				}
			}

		}
		return listCategorie;
	}

	/*
	 * //retrouver les categories
	 * 
	 * @RequestMapping(value
	 * ="verificationInformationAdditif/{codeMembreAcheteur}/{reference}", method =
	 * RequestMethod.GET) public @ResponseBody String
	 * findVerificationInfoAdditive(@PathVariable("codeMembreAcheteur")String
	 * codeMembreAcheteur, @PathVariable("reference")String reference) {
	 * if(StringUtils.isNotBlank(codeMembreAcheteur)&&StringUtils.isNotBlank(
	 * reference)){
	 * 
	 * 
	 * } return listCategorie; }
	 */

	// recuperation par categories
	@RequestMapping(value = "ReferenceArticleByCategorie/{idArticleStockesCategorie}", method = RequestMethod.GET)
	public @ResponseBody List<String> findListRefenceParCategorie(
			@PathVariable("idArticleStockesCategorie") Integer idArticleStockesCategorie) {
		List<String> listReference = Lists.newArrayList();
		/*
		 * List<EuArticleStockes> listArticleStockes =
		 * euArticleStockeService.findArticleStockesByCategorie(
		 * idArticleStockesCategorie); if(!listArticleStockes.isEmpty()){ listReference
		 * = listArticleStockes.stream().map(articleStocke ->
		 * articleStocke.getReference()).collect(Collectors.toList()); }
		 */
		listReference = euArticleStockeService.findReferenceByCategorie(idArticleStockesCategorie);
		return listReference;
	}

	// recuperation par reference
	@RequestMapping(value = "infosArticleByReference/{reference}", method = RequestMethod.GET)
	public @ResponseBody ArticleStocke findInfosParReference(@PathVariable("reference") String reference) {
		ArticleStocke articleStockes = null;
		// controller le TE
		String codeTegc;
		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		EuTegc euTegc = tegcService.getById(codeTegc);
		EuArticleStockes euArticleStockes = euArticleStockeService.findArticleStockesByReference(reference);

		if (euTegc != null && euTegc.getTypeTegc().equalsIgnoreCase("INTERIM")) {
			articleStockes = new ArticleStocke();
			if (euArticleStockes != null) {

				articleStockes.setCategorie(euArticleStockes.getCategorie());
				articleStockes.setReference(euArticleStockes.getReference());
				articleStockes.setDesignation(euArticleStockes.getDesignation());
				articleStockes.setPrix(euArticleStockes.getPrix());
				articleStockes.setQuantite(euArticleStockes.getQteSolde());
				articleStockes.setType(euArticleStockes.getType());
			}

		} else if (euTegc != null && euTegc.getTypeTegc().equalsIgnoreCase("DISTRIBUTEUR")) {
			articleStockes = new ArticleStocke();
			if (euArticleStockes != null && euArticleStockes.getCategorie().equals(codeTegc)) {

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

	// retrouver liste des numéros de téléphone
	@RequestMapping(value = "listeTelephoneBoutik/{codeMembreAcheteur}", method = RequestMethod.GET)
	public @ResponseBody List<EuTelephone> findListTelephone(
			@PathVariable("codeMembreAcheteur") String codeMembreAcheteur) {
		List<EuTelephone> listTelephone = Lists.newArrayList();
		if (StringUtils.isNotBlank(codeMembreAcheteur)) {
			listTelephone = telephoneService.findByMembre(codeMembreAcheteur);
		}
		return listTelephone;
	}

	// retrouver nom ou raison sociale
	@RequestMapping(value = "nomAcheteurBoutik/{codeMembreAcheteur}", method = RequestMethod.GET)
	public @ResponseBody List<String> findNomAcheteurBoutik(
			@PathVariable("codeMembreAcheteur") String codeMembreAcheteur) {
		String nomAcheteur = "";
		String telephoneAcheteur = "";
		List<String> listInfos = Lists.newArrayList();
		if (StringUtils.isNotBlank(codeMembreAcheteur)) {
			if (codeMembreAcheteur.endsWith("P")) {
				EuMembre membrePhysique = membreService.findById(codeMembreAcheteur);
				if (membrePhysique.getDesactiver() == 0) {
					nomAcheteur = membrePhysique.getNomMembre() + " " + membrePhysique.getPrenomMembre();
					telephoneAcheteur = membrePhysique.getPortableMembre();

				}
			} else {
				EuMembreMorale membremorale = moraleService.findById(codeMembreAcheteur);
				if (membremorale.getDesactiver() == 0) {
					nomAcheteur = membremorale.getRaisonSociale();
					telephoneAcheteur = membremorale.getPortableMembre();
				}
			}
			listInfos.add(nomAcheteur);
			listInfos.add(telephoneAcheteur);
		}
		return listInfos;
	}

	// retrouver un produit par son code barre
	@RequestMapping(value = "infoarticle/{codebarre}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArticleStocke findInfoArticleByCodebarre(@PathVariable("codebarre") String codebarre) {
		ArticleStocke articleStocke = new ArticleStocke();
		if (StringUtils.isNotBlank(codebarre)) {
			EuArticleStockes euArticleStocke = euArticleStockeService.findArticleStockeByCodebarre(codebarre);
			articleStocke.setCodeBarre(euArticleStocke.getCodeBarre());
			articleStocke.setDesignation(euArticleStocke.getDesignation());
			articleStocke.setCategorie(euArticleStocke.getCategorie());
			articleStocke.setReference(euArticleStocke.getReference());
			articleStocke.setPrix(euArticleStocke.getPrix());
			articleStocke.setType(euArticleStocke.getType());
			articleStocke.setQuantite(euArticleStocke.getQteSolde());
		}
		return articleStocke;
	}

	@ModelAttribute("raisonvente")
	public String retrouverRaisonTegc() {
		String nomTegc = "";
		String codeTegc;

		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		if (StringUtils.isNotBlank(codeTegc)) {
			EuTegc euTegc = tegcService.getById(codeTegc);
			if (euTegc != null && StringUtils.isNotBlank(euTegc.getNomTegc())
					&& !euTegc.getTypeTegc().equals("PRESTATAIRE")) {
				nomTegc = euTegc.getNomTegc().toUpperCase();

			}
		}
		return nomTegc;
	}

	@ModelAttribute("prks")
	public List<EuPrk> retrouverPrk() {
		String codeTegc;
		List<EuPrk> listPrk = Lists.newArrayList();
		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		if (StringUtils.isNotBlank(codeTegc)) {
			listPrk = ListUtils.emptyIfNull(prkService.findValByTegc(codeTegc));
		}
		return listPrk;

	}

	@RequestMapping(value = "findprk/{codeTypeCredit}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Double retrouverPrkByCodeTypeCredit(@PathVariable("codeTypeCredit") String codeTypeCredit) {
		Double prk = 0d;
		if (StringUtils.isNotBlank(codeTypeCredit)) {
			EuTypeCredit euTypeCredit = typeCreditService.findById(codeTypeCredit);
			prk = euTypeCredit.getPrk();
		}
		return prk;
	}

	@ModelAttribute("codeCredit")
	public List<EuTypeCredit> retrouverCodeTypeCredit() {
		String codeTegc;
		List<EuPrk> listPrk = Lists.newArrayList();
		List<EuTypeCredit> listTypeCredit = Lists.newArrayList();
		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		if (StringUtils.isNotBlank(codeTegc)) {
			listPrk = ListUtils.emptyIfNull(prkService.findValByTegc(codeTegc));
			listPrk.forEach((euPrk) -> {
				listTypeCredit.add(euPrk.getEuTypeCredit());
			});

		}
		return listTypeCredit;

	}

	@ModelAttribute("bps")
	public List<EuBps> retrouverBps() {
		List<EuBps> listBps = ListUtils.emptyIfNull(bpsService.findAllBps());
		return listBps;

	}

	@ModelAttribute("recurrents")
	public List<String> retrouverRecurrent() {

		String codeTegc;
		List<String> listValeurs = new ArrayList<>();
		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		if (StringUtils.isNotBlank(codeTegc)) {
			EuTegc euTegc = tegcService.getById(codeTegc);

			if (euTegc != null) {
				if (euTegc.getRecurrentIllimite() == 1) {
					listValeurs.add("illimité");
				}
				if (euTegc.getRecurrentLimite() == 1 && euTegc.getPeriode1() == 1) {
					listValeurs.add("limité 11.2");
				}
				if (euTegc.getRecurrentLimite() == 1 && euTegc.getPeriode2() == 1) {
					listValeurs.add("limité 22.4");
				}
				/*
				 * if (euTegc.getRecurrentLimite() == 1 && euTegc.getPeriode3() == 1) {
				 * listValeurs.add("limité 1"); }
				 */
			}
		}
		return listValeurs;

	}

	@RequestMapping(value = "/ordinaires", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Integer retrouverTypeMarchandise() {
		Integer type = 0;
		String codeTegc;
		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		if (StringUtils.isNotBlank(codeTegc)) {
			EuTegc euTegc = tegcService.getById(codeTegc);
			if (euTegc != null) {
				if (euTegc.getOrdinaire() == 1 && euTegc.getSpecial() == 1) {
					if (euTegc.getRecurrentLimite() == 1 || euTegc.getRecurrentIllimite() == 1) {
						// il y a recurrent
						type = 0;
					} else {
						// il n y a pas de recurrent
						type = 10;
					}

				} else if (euTegc.getOrdinaire() == 1 && euTegc.getSpecial() == 0) {
					if (euTegc.getRecurrentLimite() == 1 || euTegc.getRecurrentIllimite() == 1) {
						// il y a recurrent
						type = 1;
					} else {
						// il n y a pas de recurrent
						type = 11;
					}

				} else if (euTegc.getOrdinaire() == 0 && euTegc.getSpecial() == 1) {
					if (euTegc.getRecurrentLimite() == 1 || euTegc.getRecurrentIllimite() == 1) {
						// il y a recurrent
						type = 2;
					} else {
						// il n y a pas de recurrent
						type = 12;
					}
				} else if (euTegc.getOrdinaire() == 0 && euTegc.getSpecial() == 0) {
					type = 3;
				}
			}
		}
		return type;
	}

	@RequestMapping(value = "listte/{codeMembreAcheteur}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<EuTegc> retrouverListTegc(@PathVariable("codeMembreAcheteur") String codeMembreAcheteur) {
		List<EuTegc> listTegc = Lists.newArrayList();
		if (StringUtils.isNotBlank(codeMembreAcheteur)) {
			listTegc = tegcService.findByCodeMembre(codeMembreAcheteur);

		}
		return listTegc;
	}

	/*
	 * @RequestMapping(value = "listtypeba/{codeMembreAcheteur}", method =
	 * RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	 * public @ResponseBody List<String>
	 * retrouverListTypeBa(@PathVariable("codeMembreAcheteur") String
	 * codeMembreAcheteur) { List<String> listTypeBa = new ArrayList<>();
	 * if(StringUtils.isNotBlank(codeMembreAcheteur)){ listTypeBa =
	 * compteCreditService.findCodeProduitByCodeMembre(codeMembreAcheteur);
	 * System.out.println("listTypeBa.size = " + listTypeBa.size()); } return
	 * listTypeBa; }
	 */

	// calculMontantBonNeutre
	@RequestMapping(value = "/findmontantandsendsms", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String> findMontantAndSendSms(HttpServletRequest request) {
		Double montantBonNeutre = 0.0;
		/*
		 * String smsBody; String codeBonconso;
		 */
		EuBonNeutre bonNeutre;
		Double montantBonConsommation = 0.0;
		String typeRecurrent = "";
		Double duree = 0d;
		Boolean verifyQuota = false;
		String typeProduit = "";
		List<String> listCode = new ArrayList<>();

		String codeMembreAcheteur = (String) request.getParameter("codeMembreAcheteur");
		String typeR = (String) request.getParameter("typeR");
		Double montantAchat = Double.valueOf(request.getParameter("montantAchat"));
		String typeMontantBon = (String) request.getParameter("typeMontantBon");
		String perioder = (String) request.getParameter("perioder");

		Integer bps = Integer.valueOf(request.getParameter("bps"));
		Integer frequence = Integer.valueOf(request.getParameter("frequence"));
		Double prk = Double.valueOf(request.getParameter("prk"));
		String codeTypeCredit = (String) request.getParameter("codeTypeCredit");
		// Integer modeLivraison =
		// Integer.valueOf(request.getParameter("modeLivraison"));

		if (StringUtils.isNotBlank(codeMembreAcheteur)/* && StringUtils.isNotBlank(typeR) */
				&& StringUtils.isNotBlank(typeMontantBon) && !Double.isNaN(montantAchat)
				&& StringUtils.isNotBlank(codeTypeCredit)) {
			typeR = "nr";
			typeProduit = typeCreditService.findById(codeTypeCredit).getTypeProduit();

			if (perioder.equals("illimité")) {
				duree = 0d;
				typeRecurrent = "illimite";
			} else if ((perioder.equals("limité 11.2"))) {
				duree = 11.2;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 22.4")) {
				duree = 22.4;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 1")) {
				duree = 1d;
				typeRecurrent = "limite";
			}
			// Double fraisSms=RetrouverFraisSms();

			if (typeMontantBon.equals("mbc")) {

				montantBonConsommation = montantAchat;
				// calculer le montant du bon neutre

				montantBonNeutre = creditUtility.calculMsbc(
						new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk, frequence, bps),
						montantBonConsommation);

				// recuperation du bon neutre correspondant
				bonNeutre = bonNeutreService.findByMembre(codeMembreAcheteur);
				if (bonNeutre != null) {
					if (bonNeutre.getBonNeutreMontantSolde() >= (montantBonNeutre)) {

						// verifions le quota
						verifyQuota = quotaUtility.verifyQuota(codeTypeCredit, codeMembreAcheteur,
								montantBonConsommation, typeR);

						if (verifyQuota) {
							listCode.add(String.valueOf(Math.floor(montantBonNeutre)));
							return listCode;
							// generer un code unique
							/*
							 * String codeEnvoi =
							 * ServerUtil.GenererUniqueCode().substring(0,5).toUpperCase(); codeBonconso =
							 * ServerUtil.GenererUniqueCode().substring(0,5).toUpperCase(); if
							 * (typeR.equals("r")) { smsBody = "ESMC: Votre code " + codeBonconso+
							 * " du BCr de: " + Math.floor(montantBonConsommation) + " du BAn: " +
							 * Math.floor(montantBonNeutre+fraisSms) + ""; } else { smsBody =
							 * "ESMC: Votre code " + codeBonconso + " du BCnr de: " +
							 * Math.floor(montantBonConsommation) + " du BAn: " +
							 * Math.floor(montantBonNeutre+fraisSms) + ""; }
							 * 
							 * boolean result = smsComponent.createAndSendCode(codeEnvoi,
							 * codeMembreAcheteur, smsBody); if(result){ listCode.add(codeEnvoi);
							 */

							/* } */

						}
						listCode.add("KO0");// Vous avez depasse le quota
						return listCode;
					}
					listCode.add("KO1");// montant bon neutre insuffisant
					return listCode;
				}
				listCode.add("KO2");// pas de bon neutre
				return listCode;

			}
		}
		listCode.add("KO3");// revoir la saisie
		return listCode;

	}

	// envoi de sms pour les autres type de bons BA, OPI , BC, BL
	@RequestMapping(value = "/findmontantandsendsmsbons", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String> findMontantAndSendSmsBons(HttpServletRequest request) {
		String smsBody;
		String codeBonconso;
		String typeRecurrent = "";
		Double duree = 0d;
		String typeBC;
		Double montantBonAchat = 0d;
		List<String> listCode = new ArrayList<>();
		Boolean verifyQuota = false;
		String typeProduit = "";

		String codeMembreAcheteur = (String) request.getParameter("codeMembreAcheteur");
		String typeR = (String) request.getParameter("typeR");
		Double montantAchat = Double.valueOf(request.getParameter("montantAchat"));
		String typeMontantBon = (String) request.getParameter("typeMontantBon");
		String perioder = (String) request.getParameter("perioder");
		Double prk = Double.valueOf(request.getParameter("prk"));
		Integer bps = Integer.valueOf(request.getParameter("bps"));
		Integer frequence = Integer.valueOf(request.getParameter("frequence"));
		String codeTypeCredit = (String) request.getParameter("codeTypeCredit");

		if (StringUtils.isNotBlank(codeMembreAcheteur) /* && StringUtils.isNotBlank(typeR) */
				&& StringUtils.isNotBlank(typeMontantBon) && !Double.isNaN(montantAchat)
				&& StringUtils.isNotBlank(codeTypeCredit)) {

			if (typeMontantBon.equals("mba")) {
				typeR = "nr";
			} else if (typeMontantBon.equals("mbonc")) {
				typeR = "r";
			}

			if (codeMembreAcheteur.endsWith("P")) {
				typeBC = "RPG";
			} else {
				typeBC = "I";
			}

			if (perioder.equals("illimité")) {
				duree = 0d;
				typeRecurrent = "illimite";
			} else if ((perioder.equals("limité 11.2"))) {
				duree = 11.2;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 22.4")) {
				duree = 22.4;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 1")) {
				duree = 1d;
				typeRecurrent = "limite";
			}
			// Double fraisSms=RetrouverFraisSms();

			typeProduit = typeCreditService.findById(codeTypeCredit).getTypeProduit();

			verifyQuota = quotaUtility.verifyQuota(codeTypeCredit, codeMembreAcheteur, montantAchat, typeR);

			if (verifyQuota) {
				// generer un code unique
				String codeEnvoi = ServerUtil.GenererUniqueCode().substring(0, 5).toUpperCase();
				codeBonconso = ServerUtil.GenererUniqueCode().substring(0, 5).toUpperCase();

				// par bon d'achat
				if (typeMontantBon.equals("mba")) {
					montantBonAchat = creditUtility.calculMsbc(
							new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk, frequence, bps),
							montantAchat);
					// retrouver le montant du BA dans euCapa

					List<EuCapa> capas = Lists.newArrayList();
					capas = ListUtils.emptyIfNull(
							capaService.findbyMembreAndProduitAndOrigine(codeMembreAcheteur, typeBC, "BAI"));

					if (!capas.isEmpty()) {
						Double soldeCapa = capas.stream().map(c -> c.getMontantSolde()).reduce(Double::sum).get();
						if (soldeCapa >= Math.floor(montantBonAchat)) {
							// verifions le quota
							/*
							 * verifyQuota = quotaUtility.verifyQuota(codeTypeCredit, codeMembreAcheteur,
							 * montantBonAchat, typeR);
							 * 
							 * if(verifyQuota){
							 */
							if (typeR.equals("r")) {
								smsBody = "ESMC: Votre code " + codeBonconso + " du BCr de: " + Math.floor(montantAchat)
										+ " du BA: " + Math.floor(montantBonAchat) + "";
							} else {
								smsBody = "ESMC: Votre code " + codeBonconso + " du BCnr de: "
										+ Math.floor(montantAchat) + " du BA: " + Math.floor(montantBonAchat) + "";
							}

							listCode.add(codeEnvoi);
							listCode.add(String.valueOf(Math.floor(montantBonAchat)));
							boolean result = smsComponent.createAndSendCode(codeEnvoi, codeMembreAcheteur, smsBody);

							if (result) {
								listCode.add("OK");
								listCode.add(String.valueOf(Math.floor(montantBonAchat)));
								return listCode;
							}
							listCode.add("KO0");
							return listCode;

							/*
							 * } listCode.add("KO0");//quota return listCode;
							 */
						}
						listCode.add("KO1");
						return listCode;// le montant du bon d'achat est insuffisant
					}
					listCode.add("KO2");
					return listCode;
					// il n ya pas de bon d'achat
					// par les OPI
					/*
					 * }else if (typeMontantBon.equals("opi")) { //calcul du montant de OPI a
					 * utiliser montantBonOpi= creditUtility.calculMsbc(new CalculBonInfo(typeR,
					 * typeRecurrent, typeProduit, duree, prk, frequence, bps), montantAchat);
					 * //calcul du montant d'opi disponibles List<EuTpagcp> ListTpagcp = null;
					 * 
					 * ListTpagcp = tpagcpService.findByMembreAndModeReg(codeMembreAcheteur, "OPI");
					 * 
					 * if(!ListTpagcp.isEmpty()){ montantDesOpiDisponibles =
					 * ListTpagcp.stream().mapToDouble(EuTpagcp ::getSolde).sum();
					 * System.out.println("montantDesOpiDisponibles= " + montantDesOpiDisponibles);
					 * 
					 * if(montantDesOpiDisponibles >= Math.floor(montantBonOpi)){
					 * 
					 * smsBody = "ESMC: Votre code " + codeBonconso.substring(0,5).toUpperCase() +
					 * " du BCnr de: " + Math.floor(montantAchat) +" de OPI de: "+
					 * Math.floor(montantBonOpi); String indicatif = "228";
					 * listCode.add(codeEnvoi.substring(0,5).toUpperCase());
					 * listCode.add(String.valueOf(Math.floor(montantBonOpi))); boolean result =
					 * smsComponent.createAndSendCode(codeEnvoi, indicatif, portableAcheteur,
					 * codeMembreAcheteur, smsBody); System.out.println("result= "+result); return
					 * listCode; } listCode.add("KO1"); return listCode;//le montant des opi
					 * disponible est insuffisant } listCode.add("KO2"); return listCode;//Il n y a
					 * pas d'OPI disponibles
					 * 
					 */} else if (typeMontantBon.equals("mbonc")) {
					List<EuCompteCredit> credits = null;
					String codeCompteAcheteur = "NB-TPAGC" + typeBC + "-" + codeMembreAcheteur;

					if (typeR.equalsIgnoreCase("r")) {

						credits = ListUtils.emptyIfNull(
								compteCreditService.findbyCompteAndProduit(codeCompteAcheteur, typeBC + typeR));
					} else if (typeR.equalsIgnoreCase("nr")) {
						credits = ListUtils.emptyIfNull(compteCreditService
								.fetchByCompteAndProduitAndPrk(codeCompteAcheteur, typeBC + typeR, prk, typeProduit));
					}
					if (!credits.isEmpty()) {
						double soldeCredit = credits.stream().mapToDouble(EuCompteCredit::getMontantCredit).sum();
						if (soldeCredit >= Math.floor(montantAchat)) {
							/*
							 * //verifions le quota verifyQuota = quotaUtility.verifyQuota(codeTypeCredit,
							 * codeMembreAcheteur, montantBonAchat, typeR);
							 * 
							 * if(verifyQuota){
							 */
							if (typeR.equals("r")) {
								smsBody = "ESMC: Votre code " + codeBonconso + " du BCr de: "
										+ Math.floor(montantAchat);
							} else {
								smsBody = "ESMC: Votre code " + codeBonconso + " du BCnr de: "
										+ Math.floor(montantAchat);
							}

							listCode.add(codeEnvoi);
							boolean result = smsComponent.createAndSendCode(codeEnvoi, codeMembreAcheteur, smsBody);
							if (result) {
								listCode.add("OK");
								return listCode;
							}
							listCode.add("KO0");
							return listCode;
						}
						listCode.add("KO1");
						return listCode;// le montant du bon de consommation est insuffisant
					}
					listCode.add("KO2");
					return listCode;// Il n y a pas de bons disponibles

				} else if (typeMontantBon.equals("mbl")) {
					String codeTegc = (String) request.getParameter("codeTegc");

					if (StringUtils.isNotBlank(codeTegc)) {
						Double montantGcp = tegcService.getSoldeByMembreAndTe(codeMembreAcheteur, codeTegc);

						if (montantGcp >= montantAchat) {
							/*
							 * //verifions le quota verifyQuota = quotaUtility.verifyQuota(codeTypeCredit,
							 * codeMembreAcheteur, montantBonAchat, typeR);
							 * 
							 * if(verifyQuota){
							 */
							if (typeR.equals("r")) {
								smsBody = "ESMC: Votre code " + codeBonconso + " du BCr issu de BL de: "
										+ Math.floor(montantAchat);
							} else {
								smsBody = "ESMC: Votre code " + codeBonconso + " du BCnr issu de BL de: "
										+ Math.floor(montantAchat);
							}

							listCode.add(codeEnvoi);
							boolean result = smsComponent.createAndSendCode(codeEnvoi, codeMembreAcheteur, smsBody);
							if (result) {
								listCode.add("OK");
								return listCode;
							}
							listCode.add("KO0");
							return listCode;

						}
						listCode.add("KO1");
						return listCode;// MONTANT DE BL INSUFFISANT

					}
					listCode.add("KO2");
					return listCode;// PAS DE TE VALIDE
				}
			}
			listCode.add("KO02");
			return listCode;// QUOTA DEPASSE

		}
		listCode.add("KO3");
		return listCode;// revoir la saisie des données
	}

	/*
	 * // envoi de sms pour bons cumul
	 * 
	 * @RequestMapping(value = "/findmontantandsendsmsCumul", method =
	 * RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	 * public @ResponseBody List<String>
	 * findMontantAndSendSmsCumul(HttpServletRequest request) { String
	 * portableAcheteur; String smsBody; String codeBonconso; Double montantBonOpi =
	 * 0d; Double montantDesOpiDisponibles = 0d; String typeRecurrent ="" ; Double
	 * duree=0d; String typeBC; Double montantAchatTotal=0d; List<String> listCode =
	 * new ArrayList<>(); Double montantBonConso1 =0d; Double montantBonConso2 =0d;
	 * 
	 * String codeMembreAcheteur = (String)
	 * request.getParameter("codeMembreAcheteur"); String typeR = (String)
	 * request.getParameter("typeR"); Double montantOpi =
	 * Double.valueOf(request.getParameter("montantOpi")); Double montantBan =
	 * Double.valueOf(request.getParameter("montantBan")); Double montantBa =
	 * Double.valueOf(request.getParameter("montantBa")); Double montantBc =
	 * Double.valueOf(request.getParameter("montantBc")); String typeMontantBon =
	 * (String) request.getParameter("typeMontantBon"); String perioder = (String)
	 * request.getParameter("perioder"); String typeProduit = (String)
	 * request.getParameter("typeProduit");// PO Double prk =
	 * Double.valueOf(request.getParameter("prk"));
	 * 
	 * System.out.println("codeMembreAcheteur= " + codeMembreAcheteur);
	 * System.out.println("typeR= " + typeR); System.out.println("montantOpi= " +
	 * montantOpi); System.out.println("montantBan= " + montantBan);
	 * System.out.println("montantBa= " + montantBa);
	 * System.out.println("montantBc= " + montantBc);
	 * System.out.println("typeMontantBon= " + typeMontantBon);
	 * System.out.println("perioder= " + perioder); System.out.println("prk= " +
	 * prk);
	 * 
	 * if (StringUtils.isNotBlank(codeMembreAcheteur) &&
	 * StringUtils.isNotBlank(typeR) && StringUtils.isNotBlank(typeMontantBon) &&
	 * !Double.isNaN(montantBa)&& !Double.isNaN(montantOpi)&&
	 * !Double.isNaN(montantBan) && StringUtils.isNotBlank(typeProduit)) {
	 * 
	 * if (codeMembreAcheteur.endsWith("P")) { typeBC = "RPG"; portableAcheteur =
	 * membreService.findById(codeMembreAcheteur).getPortableMembre(); } else {
	 * typeBC = "I"; portableAcheteur =
	 * moraleService.findById(codeMembreAcheteur).getPortableMembre(); }
	 * 
	 * if (perioder.equals("illimité")) { duree = 0d; typeRecurrent = "illimite"; }
	 * else if ((perioder.equals("limité 11.2"))) { duree = 11.2; typeRecurrent =
	 * "limite"; } else if (perioder.equals("limité 22.4")) { duree = 22.4;
	 * typeRecurrent = "limite"; } // generer un code unique String codeEnvoi =
	 * ServerUtil.GenererUniqueCode(); codeBonconso =
	 * ServerUtil.GenererUniqueCode();
	 * 
	 * 
	 * if(montantOpi>0){
	 * 
	 * //calcul du montant d'opi disponibles List<EuTpagcp> ListTpagcp = null;
	 * 
	 * ListTpagcp = tpagcpService.findByMembreAndModeReg(codeMembreAcheteur, "OPI");
	 * 
	 * if(!ListTpagcp.isEmpty()){ montantDesOpiDisponibles =
	 * ListTpagcp.stream().mapToDouble(EuTpagcp ::getSolde).sum();
	 * System.out.println("montantDesOpiDisponibles= " + montantDesOpiDisponibles);
	 * 
	 * if(montantDesOpiDisponibles >= Math.floor(montantOpi)){ montantAchatTotal =+
	 * montantBonOpi; } } } if(montantBan>0){ EuBonNeutre bonNeutre =
	 * bonNeutreService.findByMembre(codeMembreAcheteur); if (bonNeutre != null) {
	 * if (bonNeutre.getBonNeutreMontantSolde() >= montantBan) { montantAchatTotal
	 * =+ montantBan; } } } if(montantBa>0){ List<EuCapa> capas =
	 * Lists.newArrayList(); capas =
	 * ListUtils.emptyIfNull(capaService.findbyMembreAndProduitAndOrigine(
	 * codeMembreAcheteur, typeBC, ""));
	 * System.out.println("capas size= "+capas.size()); if (!capas.isEmpty()) {
	 * Double soldeCapa = capas.stream().map(c ->
	 * c.getMontantSolde()).reduce(Double::sum).get(); if (soldeCapa >=
	 * Math.floor(montantBa)){ montantAchatTotal =+ montantBa; } } }
	 * if(montantAchatTotal > 0){ // calculer le montant du bon de consommation
	 * montantBonConso1 = creditUtility.calculBonConso(typeR, typeRecurrent,
	 * typeProduit, prk, duree, montantAchatTotal);
	 * System.out.println("montantBonConsommation= " +
	 * Math.floor(montantBonConso1)); } if(montantBc>0){ List<EuCompteCredit>
	 * credits = null; String codeCompteAcheteur = "NB-TPAGC" + typeBC + "-" +
	 * codeMembreAcheteur;
	 * 
	 * if (typeR.equalsIgnoreCase("r")) { credits =
	 * ListUtils.emptyIfNull(compteCreditService.
	 * findByTypeRecurrentAndTypeProduitAndDuree(codeCompteAcheteur, typeBC+typeR,
	 * typeRecurrent, typeProduit, duree)); } else if(typeR.equalsIgnoreCase("nr")){
	 * System.out.println("codeCompteAcheteur = " + codeCompteAcheteur +
	 * " codeProduit= " + typeBC+typeR + " prk = " + prk); credits = ListUtils
	 * .emptyIfNull(compteCreditService.fetchByCompteAndProduitAndPrk(
	 * codeCompteAcheteur, typeBC+typeR, prk, typeProduit)); }
	 * System.out.println(" Credits size :" + credits.size()); if
	 * (!credits.isEmpty()) { double soldeCredit =
	 * credits.stream().mapToDouble(EuCompteCredit::getMontantCredit).sum();
	 * System.out.println("Somme Credit :" + soldeCredit); if(soldeCredit >=
	 * Math.floor(montantBc)){ montantBonConso2 = Math.floor(montantBonConso1)+
	 * montantBc;
	 * 
	 * } } }
	 * 
	 * if(montantBonConso2>0){
	 * 
	 * if (typeR.equals("r")) { smsBody = "ESMC: Votre code " +
	 * codeBonconso.substring(0,5).toUpperCase() + " du BCr de: " +
	 * Math.floor(montantBonConso2); } else { smsBody = "ESMC: Votre code " +
	 * codeBonconso.substring(0,5).toUpperCase() + " du BCnr de: " +
	 * Math.floor(montantBonConso2); }
	 * 
	 * String indicatif = "228";
	 * listCode.add(codeEnvoi.substring(0,5).toUpperCase());
	 * listCode.add(String.valueOf(Math.floor(montantBonConso2)));
	 * 
	 * boolean result = smsComponent.createAndSendCode(codeEnvoi, indicatif,
	 * portableAcheteur, codeMembreAcheteur, smsBody);
	 * System.out.println("result= "+result); return listCode; }
	 * listCode.add("KO1"); return listCode;//Les ressources sont indisponibles }
	 * listCode.add("KO2"); return listCode;//Saisie erronee
	 * 
	 * }
	 */

	// envoi de sms pour bons cumul
	@RequestMapping(value = "/findmontantandsendsmsCumul", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String> findMontantAndSendSmsCumul(HttpServletRequest request) {
		String smsBody;
		String codeBonconso;
		String typeRecurrent = "";
		Double duree = 0d;
		String typeBC;
		List<String> listCode = new ArrayList<>();
		Double soldeCredit = 0d;
		Double soldeCapa = 0d;
		Double montantSoldeBonNeutre = 0d;
		Double montantBaRestAChercher = 0d;
		EuBonNeutre bonNeutre;
		// Boolean verifyQuota =false;
		String typeProduit = "";

		String codeMembreAcheteur = (String) request.getParameter("codeMembreAcheteur");
		String typeR = (String) request.getParameter("typeR");
		Double montantAchat = Double.valueOf(request.getParameter("montantAchat"));
		String typeMontantBon = (String) request.getParameter("typeMontantBon");
		String perioder = (String) request.getParameter("perioder");
		Double prk = Double.valueOf(request.getParameter("prk"));
		Integer bps = Integer.valueOf(request.getParameter("bps"));
		Integer frequence = Integer.valueOf(request.getParameter("frequence"));
		String codeTypeCredit = (String) request.getParameter("codeTypeCredit");
		// String numeroTelephone = (String)request.getParameter("numeroTelephone");

		if (StringUtils.isNotBlank(codeMembreAcheteur)
				/* && StringUtils.isNotBlank(typeR) */ && StringUtils.isNotBlank(typeMontantBon)
				&& !Double.isNaN(montantAchat) && StringUtils.isNotBlank(codeTypeCredit)) {

			if (codeMembreAcheteur.endsWith("P")) {
				typeBC = "RPG";

			} else {
				typeBC = "I";
			}

			if (perioder.equals("illimité")) {
				duree = 0d;
				typeRecurrent = "illimite";
			} else if ((perioder.equals("limité 11.2"))) {
				duree = 11.2;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 22.4")) {
				duree = 22.4;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 1")) {
				duree = 1d;
				typeRecurrent = "limite";
			}

			typeProduit = typeCreditService.findById(codeTypeCredit).getTypeProduit();

			// generer un code unique
			String codeEnvoi = ServerUtil.GenererUniqueCode().substring(0, 5).toUpperCase();
			codeBonconso = ServerUtil.GenererUniqueCode().substring(0, 5).toUpperCase();

			// le montant Achat est un montant de bon de consommation
			if (montantAchat > 0) {

				// chercher dans les BC
				List<EuCompteCredit> credits = null;
				String codeCompteAcheteur = "NB-TPAGC" + typeBC + "-" + codeMembreAcheteur;

				if (typeR.equalsIgnoreCase("r")) {
					credits = ListUtils.emptyIfNull(
							compteCreditService.findbyCompteAndProduit(codeCompteAcheteur, typeBC + typeR));
				} else if (typeR.equalsIgnoreCase("nr")) {
					credits = ListUtils.emptyIfNull(compteCreditService
							.fetchByCompteAndProduitAndPrk(codeCompteAcheteur, typeBC + typeR, prk, typeProduit));
				} else {

				}

				if (!credits.isEmpty()) {
					soldeCredit = credits.stream().mapToDouble(EuCompteCredit::getMontantCredit).sum();

				}
				if (soldeCredit >= Math.floor(montantAchat)) {

					if (typeR.equals("r")) {
						smsBody = "ESMC: Votre code " + codeBonconso + " du BCr de: " + Math.floor(montantAchat);
					} else {
						smsBody = "ESMC: Votre code " + codeBonconso + " du BCnr de: " + Math.floor(montantAchat);
					}

					listCode.add(codeEnvoi);
					boolean result = smsComponent.createAndSendCode(codeEnvoi, codeMembreAcheteur, smsBody);
					if (result) {
						listCode.add("OK");
						return listCode;
					}
					listCode.add("KO0");
					return listCode;

				} else {

					Double montantRestantAchercher = montantAchat - soldeCredit;

					// chercher ce montant dans les BA internes
					Double montantBonAchat = creditUtility.calculMsbc(
							new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk, frequence, bps),
							montantRestantAchercher);

					List<EuCapa> capas = Lists.newArrayList();
					capas = ListUtils
							.emptyIfNull(capaService.findbyMembreAndProduitAndOrigine(codeMembreAcheteur, typeBC, ""));
					System.out.println("capas size= " + capas.size());
					if (!capas.isEmpty()) {
						soldeCapa = capas.stream().map(c -> c.getMontantSolde()).reduce(Double::sum).get();
					}
					if (soldeCapa >= Math.floor(montantBonAchat)) {

						if (typeR.equals("r")) {
							smsBody = "ESMC: Votre code " + codeBonconso + " du BCr de: " + Math.floor(montantAchat);
						} else {
							smsBody = "ESMC: Votre code " + codeBonconso + " du BCnr de: " + Math.floor(montantAchat);
						}

						listCode.add(codeEnvoi);
						listCode.add(String.valueOf(Math.floor(montantBonAchat)));
						boolean result = smsComponent.createAndSendCode(codeEnvoi, codeMembreAcheteur, smsBody);
						if (result) {
							listCode.add("OK");
							return listCode;
						}
						listCode.add("KO0");
						return listCode;

					} else {

						montantBaRestAChercher = montantBonAchat - soldeCapa;

						// chercher dans le Bon d'achat neutre
						bonNeutre = bonNeutreService.findByMembre(codeMembreAcheteur);
						if (bonNeutre != null) {
							montantSoldeBonNeutre = bonNeutre.getBonNeutreMontantSolde();
						}
						if (montantSoldeBonNeutre >= montantBaRestAChercher) {

							if (typeR.equals("r")) {
								smsBody = "ESMC: Votre code " + codeBonconso + " du BCr de: "
										+ Math.floor(montantAchat);
							} else {
								smsBody = "ESMC: Votre code " + codeBonconso + " du BCnr de: "
										+ Math.floor(montantAchat);
							}
							listCode.add(codeEnvoi);
							listCode.add(String.valueOf(Math.floor(montantBaRestAChercher)));
							boolean result = smsComponent.createAndSendCode(codeEnvoi, codeMembreAcheteur, smsBody);
							System.out.println("result= " + result);
							if (result) {
								listCode.add("OK");
								return listCode;
							}
							listCode.add("KO0");
							return listCode;
						}
					}

				}
			}

		}
		listCode.add("KO2");
		return listCode;// Revoir la saisie
	}

	// calculMontantBonNeutre
	@RequestMapping(value = "/findmontantandsendsmsforappro", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String> findMontantAndSendSmsForApproBan(HttpServletRequest request) {
		Double montantBonNeutre = 0.0;
		String smsBody;
		String codeBonconso;
		EuBonNeutre bonNeutre;
		Double montantBonConsommation = 0.0;
		String typeRecurrent = "";
		Double duree = 0d;
		List<String> listCode = new ArrayList<>();
		Boolean verifyQuota = false;
		String typeProduit = "";
		String codeMembreVendeur = "";

		String codeMembreAcheteur = (String) request.getParameter("codeMembreAcheteur");
		String typeR = (String) request.getParameter("typeR");
		Double montantAchat = Double.valueOf(request.getParameter("montantAchat"));
		String typeMontantBon = (String) request.getParameter("typeMontantBon");
		String perioder = (String) request.getParameter("perioder");
		Integer bps = Integer.valueOf(request.getParameter("bps"));
		Integer frequence = Integer.valueOf(request.getParameter("frequence"));
		Double prk = Double.valueOf(request.getParameter("prk"));
		String codeTypeCredit = (String) request.getParameter("codeTypeCredit");
		// String numeroTelephone = (String)request.getParameter("numeroTelephone");

		// retrouver le vendeur connecte
		EuUtilisateur utilisateur = BaseController.getCurrentUser();

		if (StringUtils.isNotBlank(codeMembreAcheteur) && StringUtils.isNotBlank(typeR)
				&& StringUtils.isNotBlank(typeMontantBon) && !Double.isNaN(montantAchat)
				&& StringUtils.isNotBlank(codeTypeCredit)) {

			// retrouver le tegc du membre morale vendeur
			String codeTegc = utilisateur.getCodeTegc();
			EuTegc euTegc = tegcService.getById(codeTegc);
			if (Objects.nonNull(euTegc)) {
				codeMembreVendeur = euTegc.getEuMembreMorale().getCodeMembreMorale();
			}

			if (perioder.equals("illimité")) {
				duree = 0d;
				typeRecurrent = "illimite";
			} else if ((perioder.equals("limité 11.2"))) {
				duree = 11.2;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 22.4")) {
				duree = 22.4;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 1")) {
				duree = 1d;
				typeRecurrent = "limite";
			}
			typeProduit = typeCreditService.findById(codeTypeCredit).getTypeProduit();

			montantBonConsommation = montantAchat;
			// calculer le montant du bon neutre
			montantBonNeutre = creditUtility.calculMsbc(
					new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk, frequence, bps),
					montantBonConsommation);
			System.out.println("montantBonNeutre mbc= " + Math.floor(montantBonNeutre));

			// recuperation du bon neutre du vendeur
			bonNeutre = bonNeutreService.findByMembre(codeMembreVendeur);
			if (bonNeutre != null) {
				if (bonNeutre.getBonNeutreMontantSolde() >= Math.floor(montantBonNeutre)) {
					// verifions le quota
					verifyQuota = quotaUtility.verifyQuota(codeTypeCredit, codeMembreAcheteur, montantBonConsommation,
							typeR);
					if (verifyQuota) {
						// generer un code unique
						String codeEnvoi = ServerUtil.GenererUniqueCode().substring(0, 5).toUpperCase();
						codeBonconso = ServerUtil.GenererUniqueCode().substring(0, 5).toUpperCase();
						if (typeR.equals("r")) {
							smsBody = "ESMC: Votre code " + codeBonconso + " du BCr de: "
									+ Math.floor(montantBonConsommation) + " du BAn: " + Math.floor(montantBonNeutre)
									+ "";
						} else {
							smsBody = "ESMC: Votre code " + codeBonconso + " du BCnr de: "
									+ Math.floor(montantBonConsommation) + " du BAn: " + Math.floor(montantBonNeutre)
									+ "";
						}
						listCode.add(codeEnvoi);
						listCode.add(String.valueOf(Math.floor(montantBonNeutre)));
						boolean result = smsComponent.createAndSendCode(codeEnvoi, codeMembreAcheteur, smsBody);

						if (result) {
							listCode.add("OK");
							return listCode;
						}
					}
					listCode.add("KO02");// Quota est depassé
					return listCode;

				}
				listCode.add("KO1");// montant bon neutre insuffisant
				return listCode;
			}
			listCode.add("KO2");// pas de bon neutre
			return listCode;
		}
		listCode.add("KO3");// revoir la saisie
		return listCode;

	}

	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/creationBonConsoFactureByBan", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Double CreerBonConsommationParBan(HttpServletRequest request) throws CompteNonIntegreException,
			SoldeInsuffisantException, DataAccessException, NullPointerException, CompteNonTrouveException {
		String reponse = "KO";
		boolean result = false;
		int boncreer = 0;
		EuBon bonConsommation = null;
		EuBon bonAchat = null;
		EuBonNeutre bonNeutre = null;
		EuMembreMorale euMembreMorale = null;
		String typeBC = null;
		double montantBonNeutre;
		String compteVendeur;
		double montantBonConso;
		String typeRecurrent = "";
		double duree = 0;
		Integer subvention = 0;
		String typeProduit = "";
		String nomProduit = "";

		String codeMembreAcheteur = (String) request.getParameter("codeMembreAcheteur");
		String typeR = (String) request.getParameter("typeR");
		Double montantAchat = Double.valueOf(request.getParameter("montantAchat"));
		// String codeBonConso = (String) request.getParameter("codeBonConso");
		String typeMontantBon = (String) request.getParameter("typeMontantBon");
		String perioder = (String) request.getParameter("perioder");
		// String codeEnvoi = (String) request.getParameter("codeEnvoi");
		Double prk = Double.valueOf(request.getParameter("prk"));
		Integer bps = Integer.valueOf(request.getParameter("bps"));
		Integer frequence = Integer.valueOf(request.getParameter("frequence"));
		String codeTypeCredit = (String) request.getParameter("codeTypeCredit");
		String adresseLivraison = (String) request.getParameter("adresseLivraison");
		Integer modeLivraison = Integer.valueOf(request.getParameter("modeLivraison"));
		String telephoneAcheteur = (String) request.getParameter("telephoneAcheteur");
		Double fraisLivraison = Double.valueOf(request.getParameter("fraisLivraison"));
		List<ArticleVendu> ListArticlesVendus = mapArticle(request);
		Integer domiciliation = Integer.valueOf(request.getParameter("domiciliation"));
		String referenceAdditive = (String) request.getParameter("referenceAdditive");

		// retrouver le vendeur connecte
		EuUtilisateur utilisateur = BaseController.getCurrentUser();

		if (StringUtils.isNotBlank(codeMembreAcheteur) && StringUtils.isNotBlank(typeR)
				&& StringUtils.isNotBlank(typeMontantBon)
				/*
				 * && StringUtils.isNotBlank(codeBonConso) && StringUtils.isNotBlank(codeEnvoi)
				 */ && !Double.isNaN(montantAchat) && StringUtils.isNotBlank(codeTypeCredit)) {

			if (codeMembreAcheteur.endsWith("P")) {
				typeBC = "RPG";

			} else {
				typeBC = "I";
			}
			if (perioder.equals("illimité")) {
				duree = 0;
				typeRecurrent = "illimite";
			} else if ((perioder.equals("limité 11.2"))) {
				duree = 11.2;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 22.4")) {
				duree = 22.4;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 1")) {
				duree = 1d;
				typeRecurrent = "limite";
			}
			typeProduit = typeCreditService.findById(codeTypeCredit).getTypeProduit();

			// retrouver le tegc du membre morale vendeur
			String codeTegc = utilisateur.getCodeTegc();
			EuTegc euTegc = tegcService.getById(codeTegc);
			if (!euTegc.getTypeTegc().equalsIgnoreCase("INTERIM")) {
				String codeMembreVendeur = euTegc.getEuMembreMorale().getCodeMembreMorale();
				nomProduit = euTegc.getNomProduit();
				subvention = euTegc.getSubvention();
				// retrouver le vendeur
				euMembreMorale = moraleService.findById(codeMembreVendeur);
				// retrouver le compte gcp du vendeur
				compteVendeur = "NB-TPAGCP-" + euMembreMorale.getCodeMembreMorale();

			} else {
				compteVendeur = " ";

			}

			if (modeLivraison == 1) {
				// ajout de articleVendu
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

			// Double fraisSms = RetrouverFraisSms();

			// recherche du montant a utiliser dans le bon neutre
			if (typeMontantBon.equals("mbc")) {
				montantBonConso = montantAchat;
				montantBonNeutre = creditUtility.calculMsbc(
						new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk, frequence, bps),
						montantBonConso);

			} else {
				montantBonNeutre = montantAchat;
				montantBonConso = creditUtility.calculBonConso(
						new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk, frequence, bps),
						montantBonNeutre);

			}

			// recuperation du bon neutre correspondant
			bonNeutre = bonNeutreService.findByMembre(codeMembreAcheteur);

			// if(smsComponent.verifyCodeSms(codeEnvoi, codeBonConso, codeMembreAcheteur)){

			// Creation du bon d'Achat
			String typeBa = "RPG";
			if (codeMembreAcheteur.endsWith("M")) {
				typeBa = "I";
			}
			result = bonAchatComponent.souscrireBonAchat(codeMembreAcheteur, bonNeutre.getBonNeutreCode(), typeBa,
					Math.floor(montantBonNeutre));

			if (result) {
				bonAchat = transfertUtility.transfertBA(codeMembreAcheteur, "BAN", "", Math.floor(montantBonNeutre));

				CalculBonInfo calculBonInfo = new CalculBonInfo();
				calculBonInfo.setCatBon(typeR);
				calculBonInfo.setTypeRecurrent(typeRecurrent);
				calculBonInfo.setTypeProduit(typeProduit);
				calculBonInfo.setTypeCredit(codeTypeCredit);
				calculBonInfo.setPrk(prk);
				calculBonInfo.setDuree(duree);
				calculBonInfo.setBps(bps);
				calculBonInfo.setFrequenceCumul(frequence);

				// String codeMembre, CalculBonInfo bonInfo, String codeBa,
				// double montant
				if (bonAchat != null) {
					boncreer = souscriptionBon.souscrireBonConso(codeMembreAcheteur, calculBonInfo,
							bonAchat.getBonNumero(), bonAchat.getBonMontant());

					if (boncreer == 0) {
						Double montantBonConsommation = creditUtility.calculBonConso(
								new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk, frequence, bps),
								Math.floor(bonAchat.getBonMontant()));
						bonConsommation = transfertUtility.tansfertBC(codeMembreAcheteur, typeBC, calculBonInfo, prk,
								Math.floor(montantBonConsommation));

						if (bonConsommation != null) {
							// rechercher si le vendeur est beneficiaire d'une subvention
							if (subvention == 1) {
								// payement au gcsc
								reponse = reglementAchat.saveReglementParBonAuGcs(codeTegc, compteVendeur,
										bonConsommation.getBonNumero(), typeR, typeProduit, codeTypeCredit, nomProduit,
										utilisateur.getIdUtilisateur(), ListArticlesVendus);
							} else {
								// payement au tegc
								reponse = reglementAchat.saveReglementSimpleParBon("", domiciliation, codeTegc,
										compteVendeur, bonConsommation.getBonNumero(), typeR, typeProduit,
										codeTypeCredit, nomProduit, utilisateur.getIdUtilisateur(), ListArticlesVendus);
							}

							if (StringUtils.isNotBlank(reponse) && reponse.equals("OK")) {
								// mise a jour de smsconnexion

								// smsComponent.miseAjourSmsConnexion(codeEnvoi);
								// verification de la reference additive
								/*
								 * if(StringUtils.isNotBlank(referenceAdditive)){ EuInformationAdditif
								 * euInformationAdditif
								 * =euInfoAdditifService.retrouverInformationAd(codeMembreAcheteur,
								 * referenceAdditive);
								 * 
								 * }
								 */
								// creation d'un code de livraison
								String codeLivraison = ServerUtil.GenererUniqueCode().substring(0, 5).toUpperCase();
								// creation d'une ligne de commande et de ses détails
								EuCommande commandeNew = new EuCommande();
								if (modeLivraison == 1) {
									commandeNew.setAdresseLivraison(adresseLivraison);
									commandeNew.setFraisLivraison(fraisLivraison);

								} else {
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
									commandeNew.setCodeMembreVendeur(
											euTegcVendeur.getEuMembreMorale().getCodeMembreMorale());
								}

								// le tegc du vendeur (a changer apres)
								commandeNew.setCodeMembreLivreur(utilisateur.getCodeTegc());
								commandeNew.setCodeZone(utilisateur.getCodeZone());
								if (codeMembreAcheteur.endsWith("P")) {
									EuMembre membre = membreService.findById(codeMembreAcheteur);
									if (membre != null) {
										if (Objects.nonNull(membre.getEuPay())) {
											commandeNew.setIdPays(membre.getEuPay().getIdPays());
										} // commandeNew.setIdPrefecture(membre.get);
											// commandeNew.setIdRegion(utilisateur.getr);
										commandeNew.setQuartierAcheteur(membre.getQuartierMembre());
										commandeNew.setVilleAcheteur(membre.getVilleMembre());
									}
								} else {
									EuMembreMorale membreMoral = moraleService.findById(codeMembreAcheteur);

									if (Objects.nonNull(membreMoral.getEuPay())) {
										commandeNew.setIdPays(membreMoral.getEuPay().getIdPays());
									}
									// commandeNew.setIdPrefecture(membreMoral.getp);
									// commandeNew.setIdRegion(utilisateur.getr);
									commandeNew.setQuartierAcheteur(membreMoral.getQuartierMembre());
									commandeNew.setVilleAcheteur(membreMoral.getVilleMembre());
								}

								commandeNew.setModeLivraison(modeLivraison);
								commandeNew.setTelAcheteur(telephoneAcheteur);

								commandeNew.setMontantCommande(montantBonConso);
								commandeNew.setTypeBon("BAN");
								commandeNew.setTypeRecurrent(typeR);
								commandeNew.setEuUtilisateur(utilisateur);
								// commandeNew.set
								euCommandeRepo.saveAndFlush(commandeNew);
								// Long idCommandeNew = commandeNew.getCodeCommande();

								// enlever les frais de sms
								// ListArticlesVendus = ListArticlesVendus.stream().filter(articleVendu
								// ->!articleVendu.getCodeBarre().equals("FSMS")).collect(Collectors.toList());

								for (ArticleVendu articleVendu : ListArticlesVendus) {
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

									// AJOUT dans eu_detail_commande_additif
									if (StringUtils.isNotBlank(referenceAdditive)) {
										EuDetailCommandeAdditif eudetComAdd = new EuDetailCommandeAdditif();

										EuTegc euTegcMoral = tegcService.getById(articleVendu.getCodeTegc());
										if (euTegcMoral != null) {
											EuMembreMorale moraleVendeur = euTegcMoral.getEuMembreMorale();
											System.out.println("ref ad= " + referenceAdditive);
											if (Objects.nonNull(moraleVendeur)) {
												EuArticleStockesAdditif euArticleStockesAdditif = euArtStockesAddService
														.findArticleStockAdditifByMembreAndReference(
																moraleVendeur.getCodeMembreMorale(),
																articleVendu.getReference());
												if (Objects.nonNull(euArticleStockesAdditif)) {
													eudetComAdd.setEuArticleStockesAdditif(euArticleStockesAdditif);
													eudetComAdd.setEuDetailCommande(detailCommande);
													eudetComAdd.setReferenceAdditif(referenceAdditive);
													euDetComAdditifService.add(eudetComAdd);
												}
											}
										}
									}

									// Mise a jour des articles stockes

									EuArticleStockes articleStocke = euArticleStockeService
											.findArticleStockesByReference(articleVendu.getReference());
									if (articleStocke != null) {

										articleStocke
												.setQteVendu(articleStocke.getQteVendu() + articleVendu.getQuantite());
										articleStocke
												.setQteSolde(articleStocke.getQteSolde() - articleVendu.getQuantite());
										euArticleStockeService.update(articleStocke);
									}
								}
								// envoi de sms de livraison
								// if(modeLivraison==1){
								String smsBody = "Merci de presenter le code :" + codeLivraison
										+ " a la livraison de votre commande N° " + commandeNew.getCodeCommande();
								smsComponent.sendNotifications(codeMembreAcheteur, smsBody);

								/*
								 * }else{ String smsBody =
								 * "Votre commande : "+commandeNew.getCodeCommande()+" a été bien exécutée";
								 * smsComponent.sendNotifications(codeMembreAcheteur, smsBody);
								 * 
								 * }
								 */

								return Double.valueOf(0);
							}
						}
						// Echec de creation d'un bon de consommation
						return Double.valueOf(1);
					}
					// Echec de souscription au bon de consommation
					return Double.valueOf(2);
				}
			}
			// Echec de creation du bon d achat
			return Double.valueOf(3);
		}
		// return reponse = "KO5";// Revoir la saise des données
		return Double.valueOf(5);
	}

	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/creationBonConsoByBa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Double CreerBonConsommationParBa(HttpServletRequest request) throws CompteNonIntegreException,
			SoldeInsuffisantException, DataAccessException, NullPointerException, CompteNonTrouveException {
		String reponse = "KO";
		int boncreer = 0;
		EuBon bonConsommation = null;
		EuBon bonAchat = null;
		EuMembreMorale euMembreMorale = null;
		String typeBC = null;
		String compteVendeur;
		String typeRecurrent = "";
		Double duree = 0d;
		Integer subvention = 0;
		Double montantBonAchat = 0d;
		String typeR = "nr";
		String typeProduit = "";
		String nomProduit = "";

		String codeMembreAcheteur = (String) request.getParameter("codeMembreAcheteur");
		Double montantAchat = Double.valueOf(request.getParameter("montantAchat"));
		String codeBonConso = (String) request.getParameter("codeBonConso");
		String typeMontantBon = (String) request.getParameter("typeMontantBon");
		String perioder = (String) request.getParameter("perioder");
		Double prk = Double.valueOf(request.getParameter("prk"));
		String codeEnvoi = (String) request.getParameter("codeEnvoi");
		Integer bps = Integer.valueOf(request.getParameter("bps"));
		Integer frequence = Integer.valueOf(request.getParameter("frequence"));
		String codeTypeCredit = (String) request.getParameter("codeTypeCredit");
		String adresseLivraison = (String) request.getParameter("adresseLivraison");
		Integer modeLivraison = Integer.valueOf(request.getParameter("modeLivraison"));
		String telephoneAcheteur = (String) request.getParameter("telephoneAcheteur");
		Double fraisLivraison = Double.valueOf(request.getParameter("fraisLivraison"));
		Integer domiciliation = Integer.valueOf(request.getParameter("domiciliation"));
		String referenceAdditive = (String) request.getParameter("referenceAdditive");
		List<ArticleVendu> ListArticlesVendus = mapArticle(request);

		// retrouver le vendeur connecte
		EuUtilisateur utilisateur = BaseController.getCurrentUser();

		if (StringUtils.isNotBlank(codeMembreAcheteur) && StringUtils.isNotBlank(typeR)
				&& StringUtils.isNotBlank(typeMontantBon) && StringUtils.isNotBlank(codeBonConso)
				&& !Double.isNaN(montantAchat) && StringUtils.isNotBlank(codeTypeCredit)) {

			if (codeMembreAcheteur.endsWith("P")) {
				typeBC = "RPG";
			} else {
				typeBC = "I";
			}
			if (perioder.equals("illimité")) {
				duree = 0d;
				typeRecurrent = "illimite";
			} else if ((perioder.equals("limité 11.2"))) {
				duree = 11.2;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 22.4")) {
				duree = 22.4;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 1")) {
				duree = 1d;
				typeRecurrent = "limite";
			}

			typeProduit = typeCreditService.findById(codeTypeCredit).getTypeProduit();
			/*
			 * // retrouver le vendeur euMembreMorale =
			 * moraleService.findById(codeMembreVendeur); // retrouver le compte gcp du
			 * vendeur compteVendeur = "NB-TPAGCP-" + euMembreMorale.getCodeMembreMorale();
			 */
			// retrouver le tegc du me mbre morale vendeur
			String codeTegc = utilisateur.getCodeTegc();
			EuTegc euTegc = tegcService.getById(codeTegc);

			if (euTegc != null && !euTegc.getTypeTegc().equalsIgnoreCase("INTERIM")) {
				String codeMembreVendeur = euTegc.getEuMembreMorale().getCodeMembreMorale();
				nomProduit = euTegc.getNomProduit();
				subvention = euTegc.getSubvention();
				// retrouver le vendeur
				euMembreMorale = moraleService.findById(codeMembreVendeur);
				// retrouver le compte gcp du vendeur
				compteVendeur = "NB-TPAGCP-" + euMembreMorale.getCodeMembreMorale();

			} else {
				compteVendeur = " ";

			}
			if (modeLivraison == 1) {
				// ajout de articleVendu
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
			// Double fraisSms = RetrouverFraisSms();

			if (smsComponent.verifyCodeSms(codeEnvoi, codeBonConso, codeMembreAcheteur)) {
				montantBonAchat = creditUtility.calculMsbc(
						new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk, frequence, bps), montantAchat);
				bonAchat = transfertUtility.transfertBA(codeMembreAcheteur, "BAN", "", Math.floor(montantBonAchat));

				CalculBonInfo calculBonInfo = new CalculBonInfo();
				calculBonInfo.setCatBon(typeR);
				calculBonInfo.setTypeRecurrent(typeRecurrent);
				calculBonInfo.setTypeProduit(typeProduit);
				calculBonInfo.setPrk(prk);
				calculBonInfo.setDuree(duree);
				if (bonAchat != null) {
					boncreer = souscriptionBon.souscrireBonConso(codeMembreAcheteur, calculBonInfo,
							bonAchat.getBonNumero(), bonAchat.getBonMontant());

					if (boncreer == 0) {

						Double montantBonConsommation = creditUtility.calculBonConso(
								new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk, frequence, bps),
								Math.floor(bonAchat.getBonMontant()));

						bonConsommation = transfertUtility.tansfertBC(codeMembreAcheteur, typeBC, calculBonInfo, prk,
								Math.floor(montantBonConsommation));
						if (bonConsommation != null) {
							// rechercher si le vendeur est beneficiaire d'une subvention
							if (subvention == 1) {
								// payement au gcsc
								reponse = reglementAchat.saveReglementParBonAuGcs(codeTegc, compteVendeur,
										bonConsommation.getBonNumero(), "", typeProduit, codeTypeCredit, nomProduit,
										utilisateur.getIdUtilisateur(), ListArticlesVendus);
							} else {
								// payement au tegc
								reponse = reglementAchat.saveReglementSimpleParBon("", domiciliation, codeTegc,
										compteVendeur, bonConsommation.getBonNumero(), "", typeProduit, codeTypeCredit,
										nomProduit, utilisateur.getIdUtilisateur(), ListArticlesVendus);
							}
							if (StringUtils.isNotBlank(reponse) && reponse.startsWith("OK")) {
								// return reponse;
								// mise a jour de smsconnexion
								smsComponent.miseAjourSmsConnexion(codeEnvoi);
								// verification de la reference additive
								/*
								 * if(StringUtils.isNotBlank(referenceAdditive)){ EuInformationAdditif
								 * euInformationAdditif
								 * =euInfoAdditifService.retrouverInformationAd(codeMembreAcheteur,
								 * referenceAdditive);
								 * 
								 * }
								 */
								// creation d'un code de livraison
								String codeLivraison = ServerUtil.GenererUniqueCode().substring(0, 5).toUpperCase();
								// creation d'une ligne de commande et de ses détails
								EuCommande commandeNew = new EuCommande();
								if (modeLivraison == 1) {
									commandeNew.setLivrer(0);
									commandeNew.setAdresseLivraison(adresseLivraison);
									commandeNew.setFraisLivraison(fraisLivraison);
									commandeNew.setDateLivraison(new Date());
									commandeNew.setCodeLivraison(codeLivraison);

								} else {
									commandeNew.setLivrer(1);
									commandeNew.setAdresseLivraison(" ");
									commandeNew.setFraisLivraison(0d);
									commandeNew.setDateLivraison(null);
									commandeNew.setCodeLivraison("");
								}
								commandeNew.setExecuter(1);
								commandeNew.setCodeMembreAcheteur(codeMembreAcheteur);
								commandeNew.setDateCommande(new Date());
								EuTegc euTegcVendeur = tegcService.getById(utilisateur.getCodeTegc());
								if (Objects.nonNull(euTegcVendeur)) {
									commandeNew.setCodeMembreVendeur(
											euTegcVendeur.getEuMembreMorale().getCodeMembreMorale());
								}

								// le tegc du vendeur (a changer apres)
								commandeNew.setCodeMembreLivreur(utilisateur.getCodeTegc());
								commandeNew.setCodeZone(utilisateur.getCodeZone());
								if (codeMembreAcheteur.endsWith("P")) {
									EuMembre membre = membreService.findById(codeMembreAcheteur);
									if (membre != null) {
										if (Objects.nonNull(membre.getEuPay())) {
											commandeNew.setIdPays(membre.getEuPay().getIdPays());
										} // commandeNew.setIdPrefecture(membre.get);
											// commandeNew.setIdRegion(utilisateur.getr);
										commandeNew.setQuartierAcheteur(membre.getQuartierMembre());
										commandeNew.setVilleAcheteur(membre.getVilleMembre());
									}
								} else {
									EuMembreMorale membreMoral = moraleService.findById(codeMembreAcheteur);

									if (Objects.nonNull(membreMoral.getEuPay())) {
										commandeNew.setIdPays(membreMoral.getEuPay().getIdPays());
									}
									// commandeNew.setIdPrefecture(membreMoral.getp);
									// commandeNew.setIdRegion(utilisateur.getr);
									commandeNew.setQuartierAcheteur(membreMoral.getQuartierMembre());
									commandeNew.setVilleAcheteur(membreMoral.getVilleMembre());
								}

								commandeNew.setModeLivraison(modeLivraison);
								commandeNew.setTelAcheteur(telephoneAcheteur);

								commandeNew.setMontantCommande(montantAchat);
								commandeNew.setTypeBon("BA");
								commandeNew.setTypeRecurrent(typeR);
								commandeNew.setEuUtilisateur(utilisateur);
								// commandeNew.set
								euCommandeRepo.saveAndFlush(commandeNew);
								// Long idCommandeNew = commandeNew.getCodeCommande();

								// enlever les frais de sms
								// ListArticlesVendus = ListArticlesVendus.stream().filter(articleVendu
								// ->!articleVendu.getCodeBarre().equals("FSMS")).collect(Collectors.toList());

								for (ArticleVendu articleVendu : ListArticlesVendus) {
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

									// AJOUT dans eu_detail_commande_additif
									if (StringUtils.isNotBlank(referenceAdditive)) {
										EuDetailCommandeAdditif eudetComAdd = new EuDetailCommandeAdditif();

										EuTegc euTegcMoral = tegcService.getById(articleVendu.getCodeTegc());
										if (euTegcMoral != null) {
											EuMembreMorale moraleVendeur = euTegcMoral.getEuMembreMorale();
											System.out.println("ref ad= " + referenceAdditive);
											if (Objects.nonNull(moraleVendeur)) {
												EuArticleStockesAdditif euArticleStockesAdditif = euArtStockesAddService
														.findArticleStockAdditifByMembreAndReference(
																moraleVendeur.getCodeMembreMorale(),
																articleVendu.getReference());
												if (Objects.nonNull(euArticleStockesAdditif)) {
													eudetComAdd.setEuArticleStockesAdditif(euArticleStockesAdditif);
													eudetComAdd.setEuDetailCommande(detailCommande);
													eudetComAdd.setReferenceAdditif(referenceAdditive);
													euDetComAdditifService.add(eudetComAdd);
												}
											}
										}
									}

									// Mise a jour des articles stockes

									EuArticleStockes articleStocke = euArticleStockeService
											.findArticleStockesByReference(articleVendu.getReference());
									if (articleStocke != null) {
										articleStocke
												.setQteVendu(articleStocke.getQteVendu() + articleVendu.getQuantite());
										articleStocke
												.setQteSolde(articleStocke.getQteSolde() - articleVendu.getQuantite());
										euArticleStockeService.update(articleStocke);

									}
								}
								// envoi de sms de livraison
								if (modeLivraison == 1) {
									String smsBody = "Merci de presenter le code :" + codeLivraison
											+ " a la livraison de votre commande N° " + commandeNew.getCodeCommande();
									smsComponent.sendNotifications(codeMembreAcheteur, smsBody);
								} else {
									String smsBody = "Votre commande : " + commandeNew.getCodeCommande()
											+ " a été bien exécutée";
									smsComponent.sendNotifications(codeMembreAcheteur, smsBody);
								}
								return Double.valueOf(0);
							}
						}
						// maj compte
						return Double.valueOf(1); // echec de creation du bon
					}
					// maj compte
					return Double.valueOf(2);//
				}
			}
			return Double.valueOf(3);// code du bon de conso est invalide
		}
		return Double.valueOf(4);// revoir la saisie

	}

	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/creationBonConsoByBc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Double CreerBonConsommationParBc(HttpServletRequest request) throws CompteNonIntegreException,
			SoldeInsuffisantException, DataAccessException, NullPointerException, CompteNonTrouveException {
		String reponse = "KO";
		EuBon bonConsommation = null;
		EuMembreMorale euMembreMorale = null;
		String typeBC = null;
		String compteVendeur;
		String typeRecurrent = "";
		Double duree = 0d;
		Integer subvention = 0;
		String typeProduit = "";
		String nomProduit = "";

		String codeMembreAcheteur = (String) request.getParameter("codeMembreAcheteur");
		Double montantAchat = Double.valueOf(request.getParameter("montantAchat"));
		String codeBonConso = (String) request.getParameter("codeBonConso");
		String typeR = (String) request.getParameter("typeR");
		String perioder = (String) request.getParameter("perioder");
		Double prk = Double.valueOf(request.getParameter("prk"));
		String codeEnvoi = (String) request.getParameter("codeEnvoi");
		Integer bps = Integer.valueOf(request.getParameter("bps"));
		Integer frequence = Integer.valueOf(request.getParameter("frequence"));
		String codeTypeCredit = (String) request.getParameter("codeTypeCredit");
		String adresseLivraison = (String) request.getParameter("adresseLivraison");
		Integer modeLivraison = Integer.valueOf(request.getParameter("modeLivraison"));
		String telephoneAcheteur = (String) request.getParameter("telephoneAcheteur");
		Double fraisLivraison = Double.valueOf(request.getParameter("fraisLivraison"));
		Integer domiciliation = Integer.valueOf(request.getParameter("domiciliation"));
		String referenceAdditive = (String) request.getParameter("referenceAdditive");
		List<ArticleVendu> ListArticlesVendus = mapArticle(request);

		// retrouver le vendeur connecte
		EuUtilisateur utilisateur = BaseController.getCurrentUser();

		if (StringUtils.isNotBlank(codeMembreAcheteur)
				/* && StringUtils.isNotBlank(typeR) */ && StringUtils.isNotBlank(codeBonConso)
				&& !Double.isNaN(montantAchat) && StringUtils.isNotBlank(codeTypeCredit)) {

			typeR = "r";
			if (codeMembreAcheteur.endsWith("P")) {
				typeBC = "RPG";
			} else {
				typeBC = "I";
			}
			if (perioder.equals("illimité")) {
				duree = 0d;
				typeRecurrent = "illimite";
			} else if ((perioder.equals("limité 11.2"))) {
				duree = 11.2;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 22.4")) {
				duree = 22.4;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 1")) {
				duree = 1d;
				typeRecurrent = "limite";
			}

			typeProduit = typeCreditService.findById(codeTypeCredit).getTypeProduit();

			/*
			 * // retrouver le vendeur euMembreMorale =
			 * moraleService.findById(codeMembreVendeur); // retrouver le compte gcp du
			 * vendeur compteVendeur = "NB-TPAGCP-" + euMembreMorale.getCodeMembreMorale();
			 */

			// retrouver le tegc du me mbre morale vendeur
			String codeTegc = utilisateur.getCodeTegc();
			EuTegc euTegc = tegcService.getById(codeTegc);
			if (euTegc != null && !euTegc.getTypeTegc().equalsIgnoreCase("INTERIM")) {
				String codeMembreVendeur = euTegc.getEuMembreMorale().getCodeMembreMorale();
				nomProduit = euTegc.getNomProduit();
				subvention = euTegc.getSubvention();
				// retrouver le vendeur
				euMembreMorale = moraleService.findById(codeMembreVendeur);
				// retrouver le compte gcp du vendeur
				compteVendeur = "NB-TPAGCP-" + euMembreMorale.getCodeMembreMorale();

			} else {
				compteVendeur = " ";

			}

			if (modeLivraison == 1) {
				// ajout de articleVendu
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

			// Double fraisSms = RetrouverFraisSms();

			CalculBonInfo calculBonInfo = new CalculBonInfo();
			calculBonInfo.setCatBon(typeR);
			calculBonInfo.setTypeRecurrent(typeRecurrent);
			calculBonInfo.setTypeProduit(typeProduit);
			calculBonInfo.setPrk(prk);
			calculBonInfo.setDuree(duree);
			calculBonInfo.setBps(bps);
			calculBonInfo.setFrequenceCumul(frequence);

			Double montantBonConsommation = (double) Math.floor(montantAchat);
			bonConsommation = transfertUtility.tansfertBC(codeMembreAcheteur, typeBC, calculBonInfo, prk,
					montantBonConsommation);

			if (bonConsommation != null) {
				// rechercher si le vendeur est beneficiaire d'une subvention
				if (subvention == 1) {
					// payement au gcsc

					reponse = reglementAchat.saveReglementParBonAuGcs(codeTegc, compteVendeur,
							bonConsommation.getBonNumero(), "", typeProduit, codeTypeCredit, nomProduit,
							utilisateur.getIdUtilisateur(), ListArticlesVendus);
				} else {
					// payement au tegc
					reponse = reglementAchat.saveReglementSimpleParBon("", domiciliation, codeTegc, compteVendeur,
							bonConsommation.getBonNumero(), "", typeProduit, codeTypeCredit, nomProduit,
							utilisateur.getIdUtilisateur(), ListArticlesVendus);
				}
				if (StringUtils.isNotBlank(reponse) && reponse.startsWith("OK")) {
					// mise a jour de smsconnexion
					smsComponent.miseAjourSmsConnexion(codeEnvoi);

					// verification de la reference additive
					/*
					 * if(StringUtils.isNotBlank(referenceAdditive)){ EuInformationAdditif
					 * euInformationAdditif
					 * =euInfoAdditifService.retrouverInformationAd(codeMembreAcheteur,
					 * referenceAdditive);
					 * 
					 * }
					 */
					// creation d'un code de livraison
					String codeLivraison = ServerUtil.GenererUniqueCode().substring(0, 5).toUpperCase();
					// creation d'une ligne de commande et de ses détails
					EuCommande commandeNew = new EuCommande();
					if (modeLivraison == 1) {
						commandeNew.setLivrer(0);
						commandeNew.setAdresseLivraison(adresseLivraison);
						commandeNew.setFraisLivraison(fraisLivraison);
						commandeNew.setDateLivraison(new Date());
						commandeNew.setCodeLivraison(codeLivraison);

					} else {
						commandeNew.setLivrer(1);
						commandeNew.setAdresseLivraison(" ");
						commandeNew.setFraisLivraison(0d);
						commandeNew.setDateLivraison(null);
						commandeNew.setCodeLivraison("");
					}
					commandeNew.setExecuter(1);
					commandeNew.setCodeMembreAcheteur(codeMembreAcheteur);
					commandeNew.setDateCommande(new Date());
					EuTegc euTegcVendeur = tegcService.getById(utilisateur.getCodeTegc());
					if (Objects.nonNull(euTegcVendeur)) {
						commandeNew.setCodeMembreVendeur(euTegcVendeur.getEuMembreMorale().getCodeMembreMorale());
					}

					// le tegc du vendeur (a changer apres)
					commandeNew.setCodeMembreLivreur(utilisateur.getCodeTegc());
					commandeNew.setCodeZone(utilisateur.getCodeZone());
					if (codeMembreAcheteur.endsWith("P")) {
						EuMembre membre = membreService.findById(codeMembreAcheteur);
						if (membre != null) {
							if (Objects.nonNull(membre.getEuPay())) {
								commandeNew.setIdPays(membre.getEuPay().getIdPays());
							} // commandeNew.setIdPrefecture(membre.get);
								// commandeNew.setIdRegion(utilisateur.getr);
							commandeNew.setQuartierAcheteur(membre.getQuartierMembre());
							commandeNew.setVilleAcheteur(membre.getVilleMembre());
						}
					} else {
						EuMembreMorale membreMoral = moraleService.findById(codeMembreAcheteur);

						if (Objects.nonNull(membreMoral.getEuPay())) {
							commandeNew.setIdPays(membreMoral.getEuPay().getIdPays());
						}
						// commandeNew.setIdPrefecture(membreMoral.getp);
						// commandeNew.setIdRegion(utilisateur.getr);
						commandeNew.setQuartierAcheteur(membreMoral.getQuartierMembre());
						commandeNew.setVilleAcheteur(membreMoral.getVilleMembre());
					}

					commandeNew.setModeLivraison(modeLivraison);
					commandeNew.setTelAcheteur(telephoneAcheteur);

					commandeNew.setMontantCommande(montantAchat);
					commandeNew.setTypeBon("BC");
					commandeNew.setTypeRecurrent(typeR);
					commandeNew.setEuUtilisateur(utilisateur);
					// commandeNew.set
					euCommandeRepo.saveAndFlush(commandeNew);
					// Long idCommandeNew = commandeNew.getCodeCommande();

					// enlever les frais de sms
					// ListArticlesVendus = ListArticlesVendus.stream().filter(articleVendu
					// ->!articleVendu.getCodeBarre().equals("FSMS")).collect(Collectors.toList());

					for (ArticleVendu articleVendu : ListArticlesVendus) {
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

						// AJOUT dans eu_detail_commande_additif
						if (StringUtils.isNotBlank(referenceAdditive)) {
							EuDetailCommandeAdditif eudetComAdd = new EuDetailCommandeAdditif();

							EuTegc euTegcMoral = tegcService.getById(articleVendu.getCodeTegc());
							if (euTegcMoral != null) {
								EuMembreMorale moraleVendeur = euTegcMoral.getEuMembreMorale();
								System.out.println("ref ad= " + referenceAdditive);
								if (Objects.nonNull(moraleVendeur)) {
									EuArticleStockesAdditif euArticleStockesAdditif = euArtStockesAddService
											.findArticleStockAdditifByMembreAndReference(
													moraleVendeur.getCodeMembreMorale(), articleVendu.getReference());
									if (Objects.nonNull(euArticleStockesAdditif)) {
										eudetComAdd.setEuArticleStockesAdditif(euArticleStockesAdditif);
										eudetComAdd.setEuDetailCommande(detailCommande);
										eudetComAdd.setReferenceAdditif(referenceAdditive);
										euDetComAdditifService.add(eudetComAdd);
									}
								}
							}
						}

						// Mise a jour des articles stockes

						EuArticleStockes articleStocke = euArticleStockeService
								.findArticleStockesByReference(articleVendu.getReference());
						if (articleStocke != null) {
							articleStocke.setQteVendu(articleStocke.getQteVendu() + articleVendu.getQuantite());
							articleStocke.setQteSolde(articleStocke.getQteSolde() - articleVendu.getQuantite());
							euArticleStockeService.update(articleStocke);
						}
					}
					// envoi de sms de livraison
					if (modeLivraison == 1) {
						String smsBody = "Merci de presenter le code :" + codeLivraison
								+ " a la livraison de votre commande N° " + commandeNew.getCodeCommande();
						smsComponent.sendNotifications(codeMembreAcheteur, smsBody);

					} else {
						String smsBody = "Votre commande : " + commandeNew.getCodeCommande() + " a été bien exécutée";
						smsComponent.sendNotifications(codeMembreAcheteur, smsBody);

					}
					return Double.valueOf(0);
				}
			}

			return Double.valueOf(1); // echec de creation du bon
		}
		return Double.valueOf(3);// revoir la saisie

	}

	/*
	 * @Transactional(rollbackFor = Exception.class, readOnly = false, propagation =
	 * Propagation.REQUIRED)
	 * 
	 * @RequestMapping(value = "/creationBonConsoByOpi", method =
	 * RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	 * public @ResponseBody Double CreerBonConsommationParOpi(HttpServletRequest
	 * request) throws CompteNonIntegreException, SoldeInsuffisantException,
	 * DataAccessException, NullPointerException, CompteNonTrouveException { String
	 * reponse = "KO"; int boncreer = 0; EuBon bonConsommation = null; EuBon
	 * bonAchat = null; EuMembreMorale euMembreMorale = null; String typeBC = null;
	 * String compteVendeur; String typeRecurrent = ""; Double duree = 0d; Integer
	 * subvention = 0; Double montantBonOpi = 0d; Double
	 * montantDesOpiDisponibles=0d; boolean echange; String typeR = "nr";
	 * 
	 * String codeMembreAcheteur = (String)
	 * request.getParameter("codeMembreAcheteur"); Double montantAchat =
	 * Double.valueOf(request.getParameter("montantAchat")); String codeBonConso =
	 * (String) request.getParameter("codeBonConso"); String typeMontantBon =
	 * (String) request.getParameter("typeMontantBon"); String typeProduit =
	 * (String) request.getParameter("typeProduit");// PO // PS Double prk =
	 * Double.valueOf(request.getParameter("prk")); String codeEnvoi = (String)
	 * request.getParameter("codeEnvoi"); long bps =
	 * Long.valueOf(request.getParameter("bps")); int frequence =
	 * Integer.valueOf(request.getParameter("frequence")); List<ArticleVendu>
	 * ListArticlesVendus = mapArticle(request);
	 * 
	 * // retrouver le vendeur connecte EuUtilisateur utilisateur =
	 * BaseController.getCurrentUser(); String codeMembreVendeur =
	 * utilisateur.getCodeMembre(); System.out.println("codeMembreVendeur= " +
	 * codeMembreVendeur);
	 * 
	 * if (StringUtils.isNotBlank(codeMembreAcheteur) &&
	 * StringUtils.isNotBlank(codeMembreVendeur) &&
	 * StringUtils.isNotBlank(typeMontantBon) && StringUtils.isNotBlank(codeEnvoi)
	 * && StringUtils.isNotBlank(codeBonConso) && !Double.isNaN(montantAchat) &&
	 * StringUtils.isNotBlank(typeProduit)) {
	 * 
	 * if (codeMembreAcheteur.endsWith("P")) { typeBC = "RPG"; } else { typeBC =
	 * "I"; }
	 * 
	 * // retrouver le vendeur euMembreMorale =
	 * moraleService.findById(codeMembreVendeur);
	 * 
	 * // retrouver le compte gcp du vendeur
	 * 
	 * compteVendeur = "NB-TPAGCP-" + euMembreMorale.getCodeMembreMorale(); //
	 * retrouver le tegc du me mbre morale vendeur String codeTegc =
	 * utilisateur.getCodeTegc(); EuTegc euTegc = tegcService.findById(codeTegc);
	 * String nomProduit = euTegc.getNomProduit(); subvention =
	 * euTegc.getSubvention();
	 * 
	 * if(smsComponent.verifyCodeSms(codeEnvoi, codeBonConso, codeMembreAcheteur)){
	 * //calcul du montant de OPI a utiliser montantBonOpi=
	 * creditUtility.calculMsbc(new CalculBonInfo(typeR, typeRecurrent, typeProduit,
	 * duree, prk, frequence, bps), montantAchat);
	 * 
	 * //calcul du montant d'opi disponibles List<EuTpagcp> ListTpagcp = null;
	 * List<Long> ListIdTpagcp = new ArrayList<>(); ListTpagcp =
	 * tpagcpService.findByMembreAndModeReg(codeMembreAcheteur, "OPI");
	 * 
	 * if(!ListTpagcp.isEmpty()){ montantDesOpiDisponibles =
	 * Math.floor(ListTpagcp.stream().mapToDouble(EuTpagcp ::getSolde).sum());
	 * System.out.println("montantDesOpiDisponibles= " + montantDesOpiDisponibles);
	 * 
	 * ListTpagcp.forEach((euTpagcp) -> { ListIdTpagcp.add(euTpagcp.getIdTpagcp());
	 * }); System.out.println("ListTpagcp= " + ListTpagcp.size()); }
	 * if(montantDesOpiDisponibles >= Math.floor(montantBonOpi)){ //echange opi en
	 * bon d'achat (CAPA) echange = echangeUtility.echangeOpi(codeMembreAcheteur,
	 * ListIdTpagcp, montantBonOpi); System.out.println("echange= "+echange);
	 * 
	 * if(echange){ //creation bon d'achat et bon de consommation
	 * 
	 * bonAchat = transfertUtility.transfertBA(codeMembreAcheteur, "NN", "",
	 * Math.floor(montantBonOpi)); System.out.println("bonAchat= " +
	 * bonAchat.getBonNumero()); System.out.println("bonAchat.getBonmontant= " +
	 * bonAchat.getBonMontant());
	 * 
	 * CalculBonInfo calculBonInfo = new CalculBonInfo();
	 * calculBonInfo.setCatBon(typeR); calculBonInfo.setTypeRecurrent("");
	 * calculBonInfo.setTypeProduit(typeProduit); calculBonInfo.setPrk(prk);
	 * calculBonInfo.setDuree(0);
	 * 
	 * boncreer = souscriptionBon.souscrireBonConso(codeMembreAcheteur,
	 * calculBonInfo, bonAchat.getBonNumero(), bonAchat.getBonMontant());
	 * 
	 * System.out.println("boncreer= " + boncreer);
	 * 
	 * if (boncreer == 0) { Double montantBonConsommation =
	 * creditUtility.calculBonConso(new CalculBonInfo(typeR, typeRecurrent,
	 * typeProduit, duree, prk, frequence, bps),
	 * Math.floor(bonAchat.getBonMontant()));
	 * System.out.println("montantBonConsommation = "+montantBonConsommation);
	 * 
	 * bonConsommation = transfertUtility.tansfertBC(codeMembreAcheteur, typeBC,
	 * calculBonInfo, prk, Math.floor(montantBonConsommation));
	 * 
	 * if (bonConsommation != null) { //rechercher si le vendeur est beneficiaire
	 * d'une subvention if(subvention == 1){ //payement au gcsc
	 * 
	 * reponse = reglementAchat.saveReglementParBonAuGcs(compteVendeur,
	 * bonConsommation.getBonNumero(), typeR, typeProduit, nomProduit,
	 * utilisateur.getIdUtilisateur(), ListArticlesVendus); }else{ //payement au
	 * tegc reponse = reglementAchat.saveReglementSimpleParBon(compteVendeur,
	 * bonConsommation.getBonNumero(), typeR, typeProduit, nomProduit,
	 * utilisateur.getIdUtilisateur(), ListArticlesVendus); }
	 * System.out.println("reponse reglement simple : " + reponse); //mise a jour de
	 * smsconnexion smsComponent.miseAjourSmsConnexion(codeEnvoi); return
	 * Double.valueOf(0); } return Double.valueOf(1); //echec de creation du bc }
	 * return Double.valueOf(2);//echec de souscription au bc } return
	 * Double.valueOf(3);//echec de l'echange } return Double.valueOf(4);//montant
	 * des opi disponibles est insuffisant } return Double.valueOf(5);//le code du
	 * bon invalide } return Double.valueOf(6); //revoir la saisie }
	 */

	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/creationBonConsoByDefaut", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Double CreerBonConsommationParDefaut(HttpServletRequest request)
			throws CompteNonIntegreException, SoldeInsuffisantException, DataAccessException, NullPointerException,
			CompteNonTrouveException {
		String reponse = "KO";
		int boncreer = 0;
		EuBon bonConsommation = null;
		EuBon bonAchat = null;
		EuBonNeutre bonNeutre = null;
		Double montantSoldeBonNeutre = 0d;
		EuMembreMorale euMembreMorale = null;
		String typeBC = null;
		String compteVendeur;
		String typeRecurrent = "";
		Double duree = 0d;
		Integer subvention = 0;
		Double soldeCredit = 0d;
		Double soldeCapa = 0d;
		String typeR = "nr";
		String typeProduit = "";
		String nomProduit = "";

		String codeMembreAcheteur = (String) request.getParameter("codeMembreAcheteur");
		Double montantAchat = Double.valueOf(request.getParameter("montantAchat"));
		String codeBonConso = (String) request.getParameter("codeBonConso");
		String typeMontantBon = (String) request.getParameter("typeMontantBon");
		Double prk = Double.valueOf(request.getParameter("prk"));
		String codeEnvoi = (String) request.getParameter("codeEnvoi");
		Integer bps = Integer.valueOf(request.getParameter("bps"));
		Integer frequence = Integer.valueOf(request.getParameter("frequence"));
		String codeTypeCredit = (String) request.getParameter("codeTypeCredit");
		String adresseLivraison = (String) request.getParameter("adresseLivraison");
		Integer modeLivraison = Integer.valueOf(request.getParameter("modeLivraison"));
		String telephoneAcheteur = (String) request.getParameter("telephoneAcheteur");
		Double fraisLivraison = Double.valueOf(request.getParameter("fraisLivraison"));
		Integer domiciliation = Integer.valueOf(request.getParameter("domiciliation"));
		String referenceAdditive = (String) request.getParameter("referenceAdditive");
		List<ArticleVendu> ListArticlesVendus = mapArticle(request);

		// retrouver le vendeur connecte
		EuUtilisateur utilisateur = BaseController.getCurrentUser();

		if (StringUtils.isNotBlank(codeMembreAcheteur) && StringUtils.isNotBlank(typeMontantBon)
				&& StringUtils.isNotBlank(codeEnvoi) && StringUtils.isNotBlank(codeBonConso)
				&& !Double.isNaN(montantAchat) && StringUtils.isNotBlank(codeTypeCredit)) {

			if (codeMembreAcheteur.endsWith("P")) {
				typeBC = "RPG";
			} else {
				typeBC = "I";
			}

			typeProduit = typeCreditService.findById(codeTypeCredit).getTypeProduit();

			/*
			 * // retrouver le vendeur euMembreMorale =
			 * moraleService.findById(codeMembreVendeur); // retrouver le compte gcp du
			 * vendeur compteVendeur = "NB-TPAGCP-" + euMembreMorale.getCodeMembreMorale();
			 */
			// retrouver le tegc du me mbre morale vendeur
			String codeTegc = utilisateur.getCodeTegc();
			EuTegc euTegc = tegcService.getById(codeTegc);

			if (euTegc != null && !euTegc.getTypeTegc().equalsIgnoreCase("INTERIM")) {
				String codeMembreVendeur = euTegc.getEuMembreMorale().getCodeMembreMorale();
				nomProduit = euTegc.getNomProduit();
				subvention = euTegc.getSubvention();
				// retrouver le vendeur
				euMembreMorale = moraleService.findById(codeMembreVendeur);
				// retrouver le compte gcp du vendeur
				compteVendeur = "NB-TPAGCP-" + euMembreMorale.getCodeMembreMorale();

			} else {
				compteVendeur = " ";

			}
			if (modeLivraison == 1) {
				// ajout de articleVendu
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
			// Double fraisSms = RetrouverFraisSms();

			if (smsComponent.verifyCodeSms(codeEnvoi, codeBonConso, codeMembreAcheteur)) {

				CalculBonInfo calculBonInfo = new CalculBonInfo();
				calculBonInfo.setCatBon(typeR);
				calculBonInfo.setTypeRecurrent(typeRecurrent);
				calculBonInfo.setTypeProduit(typeProduit);
				calculBonInfo.setPrk(prk);
				calculBonInfo.setDuree(duree);

				String typeBa = "RPG";
				if (codeMembreAcheteur.endsWith("M")) {
					typeBa = "I";
				}

				// commencons avec les BC
				List<EuCompteCredit> credits = null;
				String codeCompteAcheteur = "NB-TPAGC" + typeBC + "-" + codeMembreAcheteur;

				if (typeR.equalsIgnoreCase("r")) {
					credits = ListUtils.emptyIfNull(
							compteCreditService.findbyCompteAndProduit(codeCompteAcheteur, typeBC + typeR));
				} else if (typeR.equalsIgnoreCase("nr")) {
					credits = ListUtils.emptyIfNull(compteCreditService
							.fetchByCompteAndProduitAndPrk(codeCompteAcheteur, typeBC + typeR, prk, typeProduit));
				}

				if (!credits.isEmpty()) {
					soldeCredit = credits.stream().mapToDouble(EuCompteCredit::getMontantCredit).sum();

				}
				if (soldeCredit >= Math.floor(montantAchat)) {

					Double montantBonConsommation = (double) Math.floor(montantAchat);

					bonConsommation = transfertUtility.tansfertBC(codeMembreAcheteur, typeBC, calculBonInfo, prk,
							montantBonConsommation);
				} else {

					bonConsommation = transfertUtility.tansfertBC(codeMembreAcheteur, typeBC, calculBonInfo, prk,
							soldeCredit);

					montantAchat = montantAchat - soldeCredit;

					// allons dans les BA internes
					Double montantBonAchat = creditUtility.calculMsbc(
							new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk, frequence, bps),
							montantAchat);

					// retrouver le montant du BA dans euCapa

					List<EuCapa> capas = Lists.newArrayList();
					capas = ListUtils
							.emptyIfNull(capaService.findbyMembreAndProduitAndOrigine(codeMembreAcheteur, typeBC, ""));
					if (!capas.isEmpty()) {
						soldeCapa = capas.stream().map(c -> c.getMontantSolde()).reduce(Double::sum).get();
					}

					if (soldeCapa >= Math.floor(montantBonAchat)) {

						bonAchat = transfertUtility.transfertBA(codeMembreAcheteur, "", "",
								Math.floor(montantBonAchat));
						if (bonAchat != null) {
							boncreer = souscriptionBon.souscrireBonConso(codeMembreAcheteur, calculBonInfo,
									bonAchat.getBonNumero(), bonAchat.getBonMontant());

						}
					} else {
						bonAchat = transfertUtility.transfertBA(codeMembreAcheteur, "", "", Math.floor(soldeCapa));

						if (bonAchat != null) {
							boncreer = souscriptionBon.souscrireBonConso(codeMembreAcheteur, calculBonInfo,
									bonAchat.getBonNumero(), bonAchat.getBonMontant());

						}

						montantBonAchat = montantBonAchat - soldeCapa;

						// allons au bon d 'achat neutre

						bonNeutre = bonNeutreService.findByMembre(codeMembreAcheteur);
						if (bonNeutre != null) {
							montantSoldeBonNeutre = bonNeutre.getBonNeutreMontantSolde();
						}
						if (montantSoldeBonNeutre >= montantBonAchat) {

							Boolean result = bonAchatComponent.souscrireBonAchat(codeMembreAcheteur,
									bonNeutre.getBonNeutreCode(), typeBa, Math.floor(montantBonAchat));

							if (result) {
								bonAchat = transfertUtility.transfertBA(codeMembreAcheteur, "BAN", "",
										Math.floor(montantBonAchat));
								// String codeMembre, CalculBonInfo bonInfo, String codeBa,
								// double montant
								if (bonAchat != null) {
									boncreer = souscriptionBon.souscrireBonConso(codeMembreAcheteur, calculBonInfo,
											bonAchat.getBonNumero(), bonAchat.getBonMontant());
								}
							}
						} else {

							Boolean result = bonAchatComponent.souscrireBonAchat(codeMembreAcheteur,
									bonNeutre.getBonNeutreCode(), typeBa, Math.floor(montantSoldeBonNeutre));

							if (result) {
								bonAchat = transfertUtility.transfertBA(codeMembreAcheteur, "BAN", "",
										Math.floor(montantSoldeBonNeutre));
								// String codeMembre, CalculBonInfo bonInfo, String codeBa,
								// double montant
								if (bonAchat != null) {
									boncreer = souscriptionBon.souscrireBonConso(codeMembreAcheteur, calculBonInfo,
											bonAchat.getBonNumero(), bonAchat.getBonMontant());
								}

							}
							montantBonAchat = montantBonAchat - montantSoldeBonNeutre;

						}
					}
				}
				// CalculBonInfo(String catBon, String typeRecurrent, String typeProduit, double
				// duree, double prk)
				if (boncreer == 0) {
					Double montantBonConsommation = creditUtility.calculBonConso(
							new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk, frequence, bps),
							Math.floor(bonAchat.getBonMontant()));

					bonConsommation = transfertUtility.tansfertBC(codeMembreAcheteur, typeBC, calculBonInfo, prk,
							Math.floor(montantBonConsommation));

					if (bonConsommation != null) {
						// rechercher si le vendeur est beneficiaire d'une subvention
						if (subvention == 1) {
							// payement au gcsc

							reponse = reglementAchat.saveReglementParBonAuGcs(codeTegc, compteVendeur,
									bonConsommation.getBonNumero(), typeR, typeProduit, codeTypeCredit, nomProduit,
									utilisateur.getIdUtilisateur(), ListArticlesVendus);
						} else {
							// payement au tegc
							reponse = reglementAchat.saveReglementSimpleParBon("", domiciliation, codeTegc,
									compteVendeur, bonConsommation.getBonNumero(), typeR, typeProduit, codeTypeCredit,
									nomProduit, utilisateur.getIdUtilisateur(), ListArticlesVendus);
						}
						if (StringUtils.isNotBlank(reponse) && reponse.startsWith("BL")) {

							// mise a jour de smsconnexion
							smsComponent.miseAjourSmsConnexion(codeEnvoi);
							// verification de la reference additive
							/*
							 * if(StringUtils.isNotBlank(referenceAdditive)){ EuInformationAdditif
							 * euInformationAdditif
							 * =euInfoAdditifService.retrouverInformationAd(codeMembreAcheteur,
							 * referenceAdditive);
							 * 
							 * }
							 */
							// creation d'un code de livraison
							String codeLivraison = ServerUtil.GenererUniqueCode().substring(0, 5).toUpperCase();
							// creation d'une ligne de commande et de ses détails
							EuCommande commandeNew = new EuCommande();
							if (modeLivraison == 1) {
								commandeNew.setLivrer(0);
								commandeNew.setAdresseLivraison(adresseLivraison);
								commandeNew.setFraisLivraison(fraisLivraison);
								commandeNew.setDateLivraison(new Date());
								commandeNew.setCodeLivraison(codeLivraison);

							} else {
								commandeNew.setLivrer(1);
								commandeNew.setAdresseLivraison(" ");
								commandeNew.setFraisLivraison(0d);
								commandeNew.setDateLivraison(null);
								commandeNew.setCodeLivraison("");
							}
							commandeNew.setExecuter(1);
							commandeNew.setCodeMembreAcheteur(codeMembreAcheteur);
							commandeNew.setDateCommande(new Date());
							EuTegc euTegcVendeur = tegcService.getById(utilisateur.getCodeTegc());
							if (Objects.nonNull(euTegcVendeur)) {
								commandeNew
										.setCodeMembreVendeur(euTegcVendeur.getEuMembreMorale().getCodeMembreMorale());
							}

							// le tegc du vendeur (a changer apres)
							commandeNew.setCodeMembreLivreur(utilisateur.getCodeTegc());
							commandeNew.setCodeZone(utilisateur.getCodeZone());
							if (codeMembreAcheteur.endsWith("P")) {
								EuMembre membre = membreService.findById(codeMembreAcheteur);
								if (membre != null) {
									if (Objects.nonNull(membre.getEuPay())) {
										commandeNew.setIdPays(membre.getEuPay().getIdPays());
									} // commandeNew.setIdPrefecture(membre.get);
										// commandeNew.setIdRegion(utilisateur.getr);
									commandeNew.setQuartierAcheteur(membre.getQuartierMembre());
									commandeNew.setVilleAcheteur(membre.getVilleMembre());
								}
							} else {
								EuMembreMorale membreMoral = moraleService.findById(codeMembreAcheteur);

								if (Objects.nonNull(membreMoral.getEuPay())) {
									commandeNew.setIdPays(membreMoral.getEuPay().getIdPays());
								}
								// commandeNew.setIdPrefecture(membreMoral.getp);
								// commandeNew.setIdRegion(utilisateur.getr);
								commandeNew.setQuartierAcheteur(membreMoral.getQuartierMembre());
								commandeNew.setVilleAcheteur(membreMoral.getVilleMembre());
							}

							commandeNew.setModeLivraison(modeLivraison);
							commandeNew.setTelAcheteur(telephoneAcheteur);

							commandeNew.setMontantCommande(montantAchat);
							commandeNew.setTypeBon("BA");
							commandeNew.setTypeRecurrent(typeR);
							commandeNew.setEuUtilisateur(utilisateur);
							// commandeNew.set
							euCommandeRepo.saveAndFlush(commandeNew);
							// Long idCommandeNew = commandeNew.getCodeCommande();

							// enlever les frais de sms
							// ListArticlesVendus = ListArticlesVendus.stream().filter(articleVendu
							// ->!articleVendu.getCodeBarre().equals("FSMS")).collect(Collectors.toList());

							for (ArticleVendu articleVendu : ListArticlesVendus) {
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

								// AJOUT dans eu_detail_commande_additif
								if (StringUtils.isNotBlank(referenceAdditive)) {
									EuDetailCommandeAdditif eudetComAdd = new EuDetailCommandeAdditif();

									EuTegc euTegcMoral = tegcService.getById(articleVendu.getCodeTegc());
									if (euTegcMoral != null) {
										EuMembreMorale moraleVendeur = euTegcMoral.getEuMembreMorale();
										System.out.println("ref ad= " + referenceAdditive);
										if (Objects.nonNull(moraleVendeur)) {
											EuArticleStockesAdditif euArticleStockesAdditif = euArtStockesAddService
													.findArticleStockAdditifByMembreAndReference(
															moraleVendeur.getCodeMembreMorale(),
															articleVendu.getReference());
											if (Objects.nonNull(euArticleStockesAdditif)) {
												eudetComAdd.setEuArticleStockesAdditif(euArticleStockesAdditif);
												eudetComAdd.setEuDetailCommande(detailCommande);
												eudetComAdd.setReferenceAdditif(referenceAdditive);
												euDetComAdditifService.add(eudetComAdd);
											}
										}
									}
								}

								// Mise a jour des articles stockes

								EuArticleStockes articleStocke = euArticleStockeService
										.findArticleStockesByReference(articleVendu.getReference());
								if (articleStocke != null) {
									articleStocke.setQteVendu(articleStocke.getQteVendu() + articleVendu.getQuantite());
									articleStocke.setQteSolde(articleStocke.getQteSolde() - articleVendu.getQuantite());
									euArticleStockeService.update(articleStocke);
								}
							}
							// envoi de sms de livraison
							if (modeLivraison == 1) {
								String smsBody = "Merci de presenter le code :" + codeLivraison
										+ " a la livraison de votre commande N° " + commandeNew.getCodeCommande();
								smsComponent.sendNotifications(codeMembreAcheteur, smsBody);
							} else {
								String smsBody = "Votre commande : " + commandeNew.getCodeCommande()
										+ " a été bien exécutée";
								smsComponent.sendNotifications(codeMembreAcheteur, smsBody);
							}
							return Double.valueOf(0);
						}
					}
				}
				return Double.valueOf(1); // echec de creation du bc
			}
			return Double.valueOf(2); // Le code du bon de consommation est invalide
		}
		return Double.valueOf(3); // revoir la saisie
	}

	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/creationBonConsoFactureByBanAppro", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Double CreerBonConsommationParBanAppro(HttpServletRequest request)
			throws CompteNonIntegreException, SoldeInsuffisantException, DataAccessException, NullPointerException,
			CompteNonTrouveException {
		String reponse = "KO";
		boolean result = false;
		int boncreer = 0;
		EuBon bonConsommation = null;
		EuBon bonAchat = null;
		EuBonNeutre bonNeutreAcheteur = null;
		EuMembreMorale euMembreMorale = null;
		String typeBC = null;
		double montantBonNeutre;
		String compteVendeur;
		double montantBonConso;
		String typeRecurrent = "";
		double duree = 0;
		int subvention = 0;
		String typeProduit = "";
		String nomProduit = "";

		String codeMembreAcheteur = (String) request.getParameter("codeMembreAcheteur");
		String typeR = (String) request.getParameter("typeR");
		Double montantAchat = Double.valueOf(request.getParameter("montantAchat"));
		String codeBonConso = (String) request.getParameter("codeBonConso");
		String typeMontantBon = (String) request.getParameter("typeMontantBon");
		String perioder = (String) request.getParameter("perioder");
		String codeEnvoi = (String) request.getParameter("codeEnvoi");
		Double prk = Double.valueOf(request.getParameter("prk"));
		Integer bps = Integer.valueOf(request.getParameter("bps"));
		Integer frequence = Integer.valueOf(request.getParameter("frequence"));
		String codeTypeCredit = (String) request.getParameter("codeTypeCredit");
		String adresseLivraison = (String) request.getParameter("adresseLivraison");
		Integer modeLivraison = Integer.valueOf(request.getParameter("modeLivraison"));
		String telephoneAcheteur = (String) request.getParameter("telephoneAcheteur");
		Double fraisLivraison = Double.valueOf(request.getParameter("fraisLivraison"));
		Integer domiciliation = Integer.valueOf(request.getParameter("domiciliation"));
		String referenceAdditive = (String) request.getParameter("referenceAdditive");
		List<ArticleVendu> ListArticlesVendus = mapArticle(request);

		// retrouver le vendeur connecte
		EuUtilisateur utilisateur = BaseController.getCurrentUser();
		String codeMembreVendeur = utilisateur.getCodeMembre();

		if (StringUtils.isNotBlank(codeMembreAcheteur) && StringUtils.isNotBlank(codeMembreVendeur)
				&& StringUtils.isNotBlank(typeR) && StringUtils.isNotBlank(typeMontantBon)
				&& StringUtils.isNotBlank(codeBonConso) && !Double.isNaN(montantAchat)
				&& StringUtils.isNotBlank(codeTypeCredit) && StringUtils.isNotBlank(codeEnvoi)) {

			if (codeMembreAcheteur.endsWith("P")) {
				typeBC = "RPG";

			} else {
				typeBC = "I";

			}
			if (perioder.equals("illimité")) {
				duree = 0;
				typeRecurrent = "illimite";
			} else if ((perioder.equals("limité 11.2"))) {
				duree = 11.2;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 22.4")) {
				duree = 22.4;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 1")) {
				duree = 1d;
				typeRecurrent = "limite";
			}
			typeProduit = typeCreditService.findById(codeTypeCredit).getTypeProduit();

			/*
			 * // retrouver le vendeur euMembreMorale =
			 * moraleService.findById(codeMembreVendeur); // retrouver le compte gcp du
			 * vendeur compteVendeur = "NB-TPAGCP-" + euMembreMorale.getCodeMembreMorale();
			 */
			// retrouver le tegc du me mbre morale vendeur
			String codeTegc = utilisateur.getCodeTegc();
			EuTegc euTegc = tegcService.getById(codeTegc);

			if (euTegc != null && !euTegc.getTypeTegc().equalsIgnoreCase("INTERIM")) {
				codeMembreVendeur = euTegc.getEuMembreMorale().getCodeMembreMorale();
				nomProduit = euTegc.getNomProduit();
				subvention = euTegc.getSubvention();
				// retrouver le vendeur
				euMembreMorale = moraleService.findById(codeMembreVendeur);
				// retrouver le compte gcp du vendeur
				compteVendeur = "NB-TPAGCP-" + euMembreMorale.getCodeMembreMorale();

			} else {
				compteVendeur = " ";
			}
			if (modeLivraison == 1) {
				// ajout de articleVendu
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

			// Double fraisSms = RetrouverFraisSms();

			// recherche du montant a utiliser dans le bon neutre

			montantBonConso = montantAchat;
			montantBonNeutre = creditUtility.calculMsbc(
					new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk, frequence, bps), montantBonConso);

			// recuperation du bon neutre correspondant
			bonNeutreAcheteur = approBAn(codeMembreVendeur, codeMembreAcheteur, montantBonNeutre);
			if (bonNeutreAcheteur != null) {

				if (smsComponent.verifyCodeSms(codeEnvoi, codeBonConso, codeMembreAcheteur)) {

					// Creation du bon d'Achat
					String typeBa = "RPG";
					if (codeMembreAcheteur.endsWith("M")) {
						typeBa = "I";
					}
					result = bonAchatComponent.souscrireBonAchat(codeMembreAcheteur, bonNeutreAcheteur.getBonNeutreCode(),
							typeBa, Math.floor(montantBonNeutre));

					if (result) {
						bonAchat = transfertUtility.transfertBA(codeMembreAcheteur, "BAN", "",
								Math.floor(montantBonNeutre));
						CalculBonInfo calculBonInfo = new CalculBonInfo();
						calculBonInfo.setCatBon(typeR);
						calculBonInfo.setTypeRecurrent(typeRecurrent);
						calculBonInfo.setTypeProduit(typeProduit);
						calculBonInfo.setPrk(prk);
						calculBonInfo.setDuree(duree);
						calculBonInfo.setBps(bps);
						calculBonInfo.setFrequenceCumul(frequence);

						// String codeMembre, CalculBonInfo bonInfo, String codeBa,
						// double montant
						if (bonAchat != null) {
							boncreer = souscriptionBon.souscrireBonConso(codeMembreAcheteur, calculBonInfo,
									bonAchat.getBonNumero(), bonAchat.getBonMontant());

							if (boncreer == 0) {
								Double montantBonConsommation = creditUtility.calculBonConso(new CalculBonInfo(typeR,
										typeRecurrent, typeProduit, duree, prk, frequence, bps),
										Math.floor(bonAchat.getBonMontant()));
								System.out.println("montantBonConsommation = " + Math.floor(montantBonConsommation));

								bonConsommation = transfertUtility.tansfertBC(codeMembreAcheteur, typeBC, calculBonInfo,
										prk, Math.floor(montantBonConsommation));

								if (bonConsommation != null) {
									// rechercher si le vendeur est beneficiaire d'une subvention
									if (subvention == 1) {
										// payement au gcsc
										reponse = reglementAchat.saveReglementParBonAuGcs(codeTegc, compteVendeur,
												bonConsommation.getBonNumero(), typeR, typeProduit, codeTypeCredit,
												nomProduit, utilisateur.getIdUtilisateur(), ListArticlesVendus);
									} else {
										// payement au tegc
										reponse = reglementAchat.saveReglementSimpleParBon("", domiciliation, codeTegc,
												compteVendeur, bonConsommation.getBonNumero(), typeR, typeProduit,
												codeTypeCredit, nomProduit, utilisateur.getIdUtilisateur(),
												ListArticlesVendus);
									}

									if (StringUtils.isNotBlank(reponse) && reponse.startsWith("OK")) {
										// mise a jour de smsconnexion
										smsComponent.miseAjourSmsConnexion(codeEnvoi);
										// verification de la reference additive
										/*
										 * if(StringUtils.isNotBlank(referenceAdditive)){ EuInformationAdditif
										 * euInformationAdditif
										 * =euInfoAdditifService.retrouverInformationAd(codeMembreAcheteur,
										 * referenceAdditive);
										 * 
										 * }
										 */
										// creation d'un code de livraison
										String codeLivraison = ServerUtil.GenererUniqueCode().substring(0, 5)
												.toUpperCase();
										// creation d'une ligne de commande et de ses détails
										EuCommande commandeNew = new EuCommande();
										if (modeLivraison == 1) {
											commandeNew.setLivrer(0);
											commandeNew.setAdresseLivraison(adresseLivraison);
											commandeNew.setFraisLivraison(fraisLivraison);
											commandeNew.setDateLivraison(new Date());
											commandeNew.setCodeLivraison(codeLivraison);

										} else {
											commandeNew.setLivrer(1);
											commandeNew.setAdresseLivraison(" ");
											commandeNew.setFraisLivraison(0d);
											commandeNew.setDateLivraison(null);
											commandeNew.setCodeLivraison("");
										}
										commandeNew.setExecuter(1);
										commandeNew.setCodeMembreAcheteur(codeMembreAcheteur);
										commandeNew.setDateCommande(new Date());
										EuTegc euTegcVendeur = tegcService.getById(utilisateur.getCodeTegc());
										if (Objects.nonNull(euTegcVendeur)) {
											commandeNew.setCodeMembreVendeur(
													euTegcVendeur.getEuMembreMorale().getCodeMembreMorale());
										}

										// le tegc du vendeur (a changer apres)
										commandeNew.setCodeMembreLivreur(utilisateur.getCodeTegc());
										commandeNew.setCodeZone(utilisateur.getCodeZone());
										if (codeMembreAcheteur.endsWith("P")) {
											EuMembre membre = membreService.findById(codeMembreAcheteur);
											if (membre != null) {
												if (Objects.nonNull(membre.getEuPay())) {
													commandeNew.setIdPays(membre.getEuPay().getIdPays());
												} // commandeNew.setIdPrefecture(membre.get);
													// commandeNew.setIdRegion(utilisateur.getr);
												commandeNew.setQuartierAcheteur(membre.getQuartierMembre());
												commandeNew.setVilleAcheteur(membre.getVilleMembre());
											}
										} else {
											EuMembreMorale membreMoral = moraleService.findById(codeMembreAcheteur);

											if (Objects.nonNull(membreMoral.getEuPay())) {
												commandeNew.setIdPays(membreMoral.getEuPay().getIdPays());
											}
											// commandeNew.setIdPrefecture(membreMoral.getp);
											// commandeNew.setIdRegion(utilisateur.getr);
											commandeNew.setQuartierAcheteur(membreMoral.getQuartierMembre());
											commandeNew.setVilleAcheteur(membreMoral.getVilleMembre());
										}

										commandeNew.setModeLivraison(modeLivraison);
										commandeNew.setTelAcheteur(telephoneAcheteur);

										commandeNew.setMontantCommande(montantBonConso);
										commandeNew.setTypeBon("BAN");
										commandeNew.setTypeRecurrent(typeR);
										commandeNew.setEuUtilisateur(utilisateur);
										// commandeNew.set
										euCommandeRepo.saveAndFlush(commandeNew);
										// Long idCommandeNew = commandeNew.getCodeCommande();

										// enlever les frais de sms
										// ListArticlesVendus = ListArticlesVendus.stream().filter(articleVendu
										// ->!articleVendu.getCodeBarre().equals("FSMS")).collect(Collectors.toList());

										for (ArticleVendu articleVendu : ListArticlesVendus) {
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

											// AJOUT dans eu_detail_commande_additif
											if (StringUtils.isNotBlank(referenceAdditive)) {
												EuDetailCommandeAdditif eudetComAdd = new EuDetailCommandeAdditif();

												EuTegc euTegcMoral = tegcService.getById(articleVendu.getCodeTegc());
												if (euTegcMoral != null) {
													EuMembreMorale moraleVendeur = euTegcMoral.getEuMembreMorale();
													System.out.println("ref ad= " + referenceAdditive);
													if (Objects.nonNull(moraleVendeur)) {
														EuArticleStockesAdditif euArticleStockesAdditif = euArtStockesAddService
																.findArticleStockAdditifByMembreAndReference(
																		moraleVendeur.getCodeMembreMorale(),
																		articleVendu.getReference());
														if (Objects.nonNull(euArticleStockesAdditif)) {
															eudetComAdd.setEuArticleStockesAdditif(
																	euArticleStockesAdditif);
															eudetComAdd.setEuDetailCommande(detailCommande);
															eudetComAdd.setReferenceAdditif(referenceAdditive);
															euDetComAdditifService.add(eudetComAdd);
														}
													}
												}
											}

											// Mise a jour des articles stockes

											EuArticleStockes articleStocke = euArticleStockeService
													.findArticleStockesByReference(articleVendu.getReference());
											if (articleStocke != null) {
												articleStocke.setQteVendu(
														articleStocke.getQteVendu() + articleVendu.getQuantite());
												articleStocke.setQteSolde(
														articleStocke.getQteSolde() - articleVendu.getQuantite());
												euArticleStockeService.update(articleStocke);
											}
										}
										// envoi de sms de livraison
										if (modeLivraison == 1) {
											String smsBody = "Merci de presenter le code :" + codeLivraison
													+ " a la livraison de votre commande N° "
													+ commandeNew.getCodeCommande();
											smsComponent.sendNotifications(codeMembreAcheteur, smsBody);
										} else {
											String smsBody = "Votre commande : " + commandeNew.getCodeCommande()
													+ " a été bien exécutée";
											smsComponent.sendNotifications(codeMembreAcheteur, smsBody);

										}
										return Double.valueOf(0);
									}
								}
								// Echec de creation du bon de consommation
								return Double.valueOf(1);
							}
							// Echec de souscription au bon de consommation
							return Double.valueOf(2);
						}
					}
					// Echec de souscription au bon d achat
					return Double.valueOf(3);
				}
				// Le code du bon de consommation est invalide
				return Double.valueOf(4);
			}

			// Erreur dans la creation du bon neutre acheteur
			return Double.valueOf(5);
		}
		// return reponse = "KO5";// Revoir la saise des données
		return Double.valueOf(6);
	}

	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/creationBonConsoByBl", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Double CreerBonConsommationParBl(HttpServletRequest request) throws CompteNonIntegreException,
			SoldeInsuffisantException, DataAccessException, NullPointerException, CompteNonTrouveException {
		String reponse = "KO";
		EuBon bonConsommation = null;
		String compteVendeur = "";
		Integer subvention = 0;
		String typeProduit = "";
		Double montantGcp = 0d;
		boolean echange = false;
		String typeBC = "";
		String nomProduit = "";

		String codeMembreAcheteur = (String) request.getParameter("codeMembreAcheteur");
		Double montantAchat = Double.valueOf(request.getParameter("montantAchat"));
		String codeBonConso = (String) request.getParameter("codeBonConso");
		String typeR = (String) request.getParameter("typeR");
		Double prk = Double.valueOf(request.getParameter("prk"));
		String codeEnvoi = (String) request.getParameter("codeEnvoi");
		String codeTypeCredit = (String) request.getParameter("codeTypeCredit");
		String codeTegcAcheteur = (String) request.getParameter("codeTegc");
		Integer domiciliation = Integer.valueOf(request.getParameter("domiciliation"));
		List<ArticleVendu> ListArticlesVendus = mapArticle(request);

		// retrouver le vendeur connecte
		EuUtilisateur utilisateur = BaseController.getCurrentUser();

		if (StringUtils.isNotBlank(codeMembreAcheteur) && StringUtils.isNotBlank(typeR)
				&& StringUtils.isNotBlank(codeBonConso) && !Double.isNaN(montantAchat)
				&& StringUtils.isNotBlank(codeTypeCredit) && StringUtils.isNotBlank(codeTegcAcheteur)) {

			if (codeMembreAcheteur.endsWith("P")) {
				typeBC = "RPG";

			} else {
				typeBC = "I";

			}
			// retrouver le vendeur
			// euMembreMorale = moraleService.findById(codeMembreVendeur);

			// retrouver le compte gcp du vendeur
			// retrouver le tegc du membre morale vendeur
			String codeTegcVendeur = utilisateur.getCodeTegc();
			EuTegc euTegc = tegcRepo.findOne(codeTegcVendeur);
			if (Objects.nonNull(euTegc)) {
				compteVendeur = "NB-TPAGCP-" + euTegc.getEuMembreMorale().getCodeMembreMorale();
				nomProduit = euTegc.getNomProduit();
				subvention = euTegc.getSubvention();
			}
			EuTypeCredit euTypeCredit = typeCreditService.findById(codeTypeCredit);

			if (euTypeCredit != null) {
				typeProduit = euTypeCredit.getTypeProduit();
				prk = euTypeCredit.getPrk();
			}

			if (smsComponent.verifyCodeSms(codeEnvoi, codeBonConso, codeMembreAcheteur)) {
				// retrouver le montant du gcp et comparer avec le montant Achat
				if (codeTegcAcheteur.equals("")) {
					montantGcp = gcpService.getSoldeByMembre(codeMembreAcheteur);

				} else {
					montantGcp = tegcService.getSoldeByMembreAndTe(codeMembreAcheteur, codeTegcAcheteur);
				}
				if (montantGcp >= montantAchat) {
					// echange de gcp en Inr
					echange = echangeService.echangeGCP(codeMembreAcheteur, codeTegcAcheteur, "GCP", prk, montantAchat,
							typeProduit);
					if (echange) {
						CalculBonInfo calculBonInfo = new CalculBonInfo();
						calculBonInfo.setCatBon("nr");
						calculBonInfo.setTypeRecurrent("");
						calculBonInfo.setTypeProduit(typeProduit);
						calculBonInfo.setPrk(prk);
						calculBonInfo.setDuree(0);

						bonConsommation = transfertUtility.tansfertBC(codeMembreAcheteur, typeBC, calculBonInfo, prk,
								montantAchat);

						if (bonConsommation != null) {
							if (subvention == 1) {
								// payement au gcsc
								reponse = reglementAchat.saveReglementParBonAuGcs(codeTegcVendeur, compteVendeur,
										bonConsommation.getBonNumero(), typeR, typeProduit, codeTypeCredit, nomProduit,
										utilisateur.getIdUtilisateur(), ListArticlesVendus);
							} else {
								reponse = reglementAchat.saveReglementSimpleParBon("", domiciliation, codeTegcVendeur,
										compteVendeur, bonConsommation.getBonNumero(), typeR, typeProduit,
										codeTypeCredit, nomProduit, utilisateur.getIdUtilisateur(), ListArticlesVendus);
							}
							if (StringUtils.isNotBlank(reponse) && reponse.startsWith("BL")) {
								// mise a jour de smsconnexion
								smsComponent.miseAjourSmsConnexion(codeEnvoi);
								return Double.valueOf(0);
							}
						}
						return Double.valueOf(1); // echec de creation du bon de consommation
					}
				}
				return Double.valueOf(2);// montant de BL insuffisant
			}
			return Double.valueOf(3);// Code de confirmation invalide
		}
		return Double.valueOf(4);// revoir la saisie

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

	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public EuBonNeutre approBAn(String codeMembreVendeur, String codeMembreAcheteur, Double montantBonNeutre) {

		String portableAcheteur = "";
		String emailAcheteur = "";
		String nomAcheteur = "";
		String prenomAcheteur = "";
		EuCanton cantonAcheteur = null;
		String raisonAcheteur = "";

		if (codeMembreAcheteur.endsWith("P")) {
			EuMembre membreAcheteur = membreService.findById(codeMembreAcheteur);
			portableAcheteur = membreAcheteur.getPortableMembre();
			emailAcheteur = membreAcheteur.getEmailMembre();
			nomAcheteur = membreAcheteur.getNomMembre();
			prenomAcheteur = membreAcheteur.getPrenomMembre();
			cantonAcheteur = membreAcheteur.getEuCanton();

		} else {

			EuMembreMorale moraleAcheteur = moraleService.findById(codeMembreAcheteur);
			portableAcheteur = moraleAcheteur.getPortableMembre();
			emailAcheteur = moraleAcheteur.getEmailMembre();
			raisonAcheteur = moraleAcheteur.getRaisonSociale();
			cantonAcheteur = moraleAcheteur.getEuCanton();

		}

		EuBonNeutre bonNeutreVendeur = bonNeutreService.findByMembre(codeMembreVendeur);

		EuBonNeutre bonNeutreAcheteur = bonNeutreService.findByMembre(codeMembreAcheteur);

		if (bonNeutreVendeur != null && bonNeutreVendeur.getBonNeutreMontantSolde() >= montantBonNeutre) {
			List<EuBonNeutreDetail> listBonNeutreDetailVendeurs = bonNeutreDetailService
					.findByMembre(codeMembreVendeur);

			// l'acheteur en a deja donc mettre a jour avec les details correspondants
			if (bonNeutreAcheteur != null) {

				bonNeutreAcheteur.setBonNeutreMontant(bonNeutreAcheteur.getBonNeutreMontant() + montantBonNeutre);
				bonNeutreAcheteur
						.setBonNeutreMontantSolde(bonNeutreAcheteur.getBonNeutreMontantSolde() + montantBonNeutre);

				// bonNeutreRepo.saveAndFlush(bonNeutreAcheteur);
				bonNeutreService.create(bonNeutreAcheteur);

				// mise a jour bon Neutre du vendeur
				bonNeutreVendeur.setBonNeutreMontant(bonNeutreVendeur.getBonNeutreMontant() - montantBonNeutre);
				bonNeutreVendeur
						.setBonNeutreMontantSolde(bonNeutreVendeur.getBonNeutreMontantSolde() - montantBonNeutre);
				bonNeutreVendeur
						.setBonNeutreMontantUtilise(bonNeutreVendeur.getBonNeutreMontantUtilise() + montantBonNeutre);
				bonNeutreService.create(bonNeutreVendeur);

				// bon neutre appro acheteur vendeur
				EuBonNeutreAppro bonNeutreAppro = new EuBonNeutreAppro();
				int bonNeutreApproId = bonNeutreApproService.getLastInsertedId() + 1;
				bonNeutreAppro.setBonNeutreApproId(bonNeutreApproId);
				bonNeutreAppro.setBonNeutreApproApporteur(codeMembreVendeur);
				bonNeutreAppro.setBonNeutreApproBeneficiaire(codeMembreAcheteur);
				bonNeutreAppro.setBonNeutreApproDate(new Date());
				bonNeutreAppro.setBonNeutreApproMontant(montantBonNeutre);
				bonNeutreApproService.add(bonNeutreAppro);

				// acheteur mise a jour bon neutre detail acheteur
				List<EuBonNeutreDetail> listbonNeutreDetail = bonNeutreDetailService.findByMembre(codeMembreAcheteur);
				if (!listbonNeutreDetail.isEmpty()) {
					bonNeutreDetailService.updateBon(listbonNeutreDetail, montantBonNeutre);
				}
				// mise a jour eu bon neutre detail vendeur
				if (!listBonNeutreDetailVendeurs.isEmpty()) {
					bonNeutreDetailService.updateBon(listBonNeutreDetailVendeurs, montantBonNeutre);

					double mont = montantBonNeutre;
					int compteur = 0;
					while (mont > 0 && compteur < listBonNeutreDetailVendeurs.size()) {
						EuBonNeutreDetail bonDetail = listBonNeutreDetailVendeurs.get(compteur);
						if (bonDetail.getBonNeutreDetailMontantSolde() < mont) {
							mont -= bonDetail.getBonNeutreDetailMontantSolde();
							// bon appro detail
							EuBonNeutreApproDetail bonNeutreApproDetail = new EuBonNeutreApproDetail();
							bonNeutreApproDetail.setId(new EuBonNeutreApproDetailPK(bonDetail.getBonNeutreDetailId(),
									bonNeutreAppro.getBonNeutreApproId()));
							bonNeutreApproDetail.setBonNeutreApproDetailBanque(bonDetail.getBonNeutreDetailBanque());
							bonNeutreApproDetail.setBonNeutreApproDetailDate(new Date());
							bonNeutreApproDetail
									.setBonNeutreApproDetailMontant(bonDetail.getBonNeutreDetailMontantSolde());
							bonNeutreApproDetail.setBonNeutreApproDetailMontUtilise(Double.valueOf("0"));
							bonNeutreApproDetail
									.setBonNeutreApproDetailSolde(bonDetail.getBonNeutreDetailMontantSolde());
							bonNeutreApproDetailService.add(bonNeutreApproDetail);
							compteur++;
						} else {
							EuBonNeutreApproDetail bonNeutreApproDetail = new EuBonNeutreApproDetail();
							bonNeutreApproDetail.setId(new EuBonNeutreApproDetailPK(bonDetail.getBonNeutreDetailId(),
									bonNeutreAppro.getBonNeutreApproId()));
							bonNeutreApproDetail.setBonNeutreApproDetailBanque(bonDetail.getBonNeutreDetailBanque());
							bonNeutreApproDetail.setBonNeutreApproDetailDate(new Date());
							bonNeutreApproDetail.setBonNeutreApproDetailMontant(mont);
							bonNeutreApproDetail.setBonNeutreApproDetailMontUtilise(Double.valueOf("0"));
							bonNeutreApproDetail.setBonNeutreApproDetailSolde(mont);
							bonNeutreApproDetailService.add(bonNeutreApproDetail);
							mont = 0;
						}
					}

				}

				// bon Neutre Utiliser
				EuBonNeutreUtilise bonNeutreUtilise = new EuBonNeutreUtilise();
				int bonNeutreUtiliseId = bonNeutreutiliserService.getLastInsertedId();
				bonNeutreUtilise.setBonNeutreUtiliseId(bonNeutreUtiliseId + 1);
				bonNeutreUtilise.setBonNeutreUtiliseDate(new Date());
				bonNeutreUtilise.setBonNeutreUtiliseMontant(montantBonNeutre);
				if (codeMembreAcheteur.endsWith("P")) {
					bonNeutreUtilise.setBonNeutreUtiliseType("PP");
					bonNeutreUtilise.setBonNeutreUtiliseLibelle("Approvisionnement de BAn de PP");
				} else {
					bonNeutreUtilise.setBonNeutreUtiliseType("PM");
					bonNeutreUtilise.setBonNeutreUtiliseLibelle("Approvisionnement de BAn de PM");
				}
				bonNeutreUtilise.setEuBonNeutre(bonNeutreVendeur);
				// bonNeutreutiliserRepo.saveAndFlush(bonNeutreUtilise);
				bonNeutreutiliserService.create(bonNeutreUtilise);

			} else {
				String codeGenerer = ServerUtil.GenererUniqueCodeForBan();

				// l'acheteur n a pas de bon neutre donc lui en creer un avec les détails
				bonNeutreAcheteur = new EuBonNeutre();
				int bonNeutreId = bonNeutreService.getLastInsertedId() + 1;

				bonNeutreAcheteur.setBonNeutreId(bonNeutreId);
				bonNeutreAcheteur.setBonNeutreCode(codeGenerer);
				bonNeutreAcheteur.setBonNeutreCodeMembre(codeMembreAcheteur);
				bonNeutreAcheteur.setBonNeutreDate(new Date());
				bonNeutreAcheteur.setBonNeutreEmail(emailAcheteur);
				bonNeutreAcheteur.setBonNeutreMobile(portableAcheteur);
				bonNeutreAcheteur.setBonNeutreMontant(montantBonNeutre);
				bonNeutreAcheteur.setBonNeutreMontantSolde(montantBonNeutre);
				bonNeutreAcheteur.setBonNeutreMontantUtilise(0d);
				bonNeutreAcheteur.setBonNeutreNom(nomAcheteur);
				bonNeutreAcheteur.setBonNeutrePrenom(prenomAcheteur);
				bonNeutreAcheteur.setBonNeutreRaison(raisonAcheteur);
				bonNeutreAcheteur.setBonNeutreType("BAn");
				// bonNeutreRepo.saveAndFlush(bonNeutreAcheteur);
				bonNeutreService.create(bonNeutreAcheteur);

				// mise a jour bon Neutre du vendeur
				bonNeutreVendeur.setBonNeutreMontant(bonNeutreVendeur.getBonNeutreMontant() - montantBonNeutre);
				;
				bonNeutreVendeur
						.setBonNeutreMontantSolde(bonNeutreVendeur.getBonNeutreMontantSolde() - montantBonNeutre);
				bonNeutreVendeur
						.setBonNeutreMontantUtilise(bonNeutreVendeur.getBonNeutreMontantUtilise() + montantBonNeutre);
				// bonNeutreRepo.saveAndFlush(bonNeutreVendeur);
				bonNeutreService.create(bonNeutreVendeur);

				// bon neutre appro acheteur vendeur
				EuBonNeutreAppro bonNeutreAppro = new EuBonNeutreAppro();
				int bonNeutreApproId = bonNeutreApproService.getLastInsertedId() + 1;
				bonNeutreAppro.setBonNeutreApproId(bonNeutreApproId);
				bonNeutreAppro.setBonNeutreApproApporteur(codeMembreVendeur);
				bonNeutreAppro.setBonNeutreApproBeneficiaire(codeMembreAcheteur);
				bonNeutreAppro.setBonNeutreApproDate(new Date());
				bonNeutreAppro.setBonNeutreApproMontant(montantBonNeutre);
				// bonNeutreApproRepo.saveAndFlush(bonNeutreAppro);
				bonNeutreApproService.add(bonNeutreAppro);

				// acheteur creer nouveau bon neutre detail
				EuBonNeutreDetail bonNeutreDetail = new EuBonNeutreDetail();
				int bonNeutreDetailId = bonNeutreDetailService.getLastInsertedId() + 1;
				bonNeutreDetail.setBonNeutreDetailId(bonNeutreDetailId);
				bonNeutreDetail.setBonNeutreApproId(bonNeutreAppro.getBonNeutreApproId());
				bonNeutreDetail.setBonNeutreDetailBanque(null);
				bonNeutreDetail.setBonNeutreDetailCode(bonNeutreAcheteur.getBonNeutreCode());
				bonNeutreDetail.setBonNeutreDetailDate(new Date());
				bonNeutreDetail.setBonNeutreDetailDateNumero(null);
				bonNeutreDetail.setBonNeutreDetailMontant(montantBonNeutre);
				bonNeutreDetail.setBonNeutreDetailMontantSolde(montantBonNeutre);
				bonNeutreDetail.setBonNeutreDetailMontantUtilise(0);
				bonNeutreDetail.setBonNeutreDetailNumero(null);
				bonNeutreDetail.setBonNeutreDetailVignette(null);
				bonNeutreDetail.setEuBonNeutre(bonNeutreAcheteur);
				if (cantonAcheteur != null) {
					bonNeutreDetail.setIdCanton(cantonAcheteur.getIdCanton());
				}

				// bonNeutreDetailRepo.saveAndFlush(bonNeutreDetail);
				bonNeutreDetailService.create(bonNeutreDetail);

				// mise a jour eu bon neutre detail vendeur
				if (!listBonNeutreDetailVendeurs.isEmpty()) {

					bonNeutreDetailService.updateBon(listBonNeutreDetailVendeurs, montantBonNeutre);
					double mont = montantBonNeutre;
					int compteur = 0;
					while (mont > 0 && compteur < listBonNeutreDetailVendeurs.size()) {
						EuBonNeutreDetail bonDetail = listBonNeutreDetailVendeurs.get(compteur);
						if (bonDetail.getBonNeutreDetailMontantSolde() < mont) {
							mont -= bonDetail.getBonNeutreDetailMontantSolde();
							// bon appro detail
							EuBonNeutreApproDetail bonNeutreApproDetail = new EuBonNeutreApproDetail();
							bonNeutreApproDetail.setId(new EuBonNeutreApproDetailPK(bonDetail.getBonNeutreDetailId(),
									bonNeutreAppro.getBonNeutreApproId()));
							bonNeutreApproDetail.setBonNeutreApproDetailBanque(bonDetail.getBonNeutreDetailBanque());
							bonNeutreApproDetail.setBonNeutreApproDetailDate(new Date());
							bonNeutreApproDetail
									.setBonNeutreApproDetailMontant(bonDetail.getBonNeutreDetailMontantSolde());
							bonNeutreApproDetail.setBonNeutreApproDetailMontUtilise(Double.valueOf("0"));
							bonNeutreApproDetail
									.setBonNeutreApproDetailSolde(bonDetail.getBonNeutreDetailMontantSolde());
							// bonNeutreApproDetailRepo.saveAndFlush(bonNeutreApproDetail);
							bonNeutreApproDetailService.add(bonNeutreApproDetail);
							compteur++;
						} else {
							EuBonNeutreApproDetail bonNeutreApproDetail = new EuBonNeutreApproDetail();
							bonNeutreApproDetail.setId(new EuBonNeutreApproDetailPK(bonDetail.getBonNeutreDetailId(),
									bonNeutreAppro.getBonNeutreApproId()));
							bonNeutreApproDetail.setBonNeutreApproDetailBanque(bonDetail.getBonNeutreDetailBanque());
							bonNeutreApproDetail.setBonNeutreApproDetailDate(new Date());
							bonNeutreApproDetail.setBonNeutreApproDetailMontant(mont);
							bonNeutreApproDetail.setBonNeutreApproDetailMontUtilise(Double.valueOf("0"));
							bonNeutreApproDetail.setBonNeutreApproDetailSolde(mont);
							// bonNeutreApproDetailRepo.saveAndFlush(bonNeutreApproDetail);
							bonNeutreApproDetailService.add(bonNeutreApproDetail);
							mont = 0;
						}
					}
				}

				// bon Neutre Utiliser
				EuBonNeutreUtilise bonNeutreUtilise = new EuBonNeutreUtilise();
				int bonNeutreUtiliseId = bonNeutreutiliserService.getLastInsertedId() + 1;
				bonNeutreUtilise.setBonNeutreUtiliseId(bonNeutreUtiliseId);
				bonNeutreUtilise.setBonNeutreUtiliseDate(new Date());
				bonNeutreUtilise.setBonNeutreUtiliseMontant(montantBonNeutre);
				if (codeMembreAcheteur.endsWith("P")) {
					bonNeutreUtilise.setBonNeutreUtiliseType("PP");
					bonNeutreUtilise.setBonNeutreUtiliseLibelle("Approvisionnement de BAn de PP");
				} else {
					bonNeutreUtilise.setBonNeutreUtiliseType("PM");
					bonNeutreUtilise.setBonNeutreUtiliseLibelle("Approvisionnement de BAn de PM");
				}
				bonNeutreUtilise.setEuBonNeutre(bonNeutreVendeur);
				// bonNeutreutiliserRepo.saveAndFlush(bonNeutreUtilise);
				bonNeutreutiliserService.create(bonNeutreUtilise);

			}
			return bonNeutreAcheteur;
		}
		return null;

	}

//codes pour l 'interim Acheteur

	// calculMontantBonNeutre
	@RequestMapping(value = "/findmontantandsendsmsInterim", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String> findMontantAndSendSmsInterim(HttpServletRequest request) {
		Double montantBonNeutre = 0.0;
		String smsBody;
		String codeBonconso;
		Double montantBonConsommation = 0.0;
		String typeRecurrent = "";
		Double duree = 0d;
		Boolean verifyQuota = false;
		String typeProduit = "";
		List<String> listCode = new ArrayList<>();

		String codeMembreAcheteur = (String) request.getParameter("codeMembreAcheteur");
		String typeR = (String) request.getParameter("typeR");
		Double montantAchat = Double.valueOf(request.getParameter("montantAchat"));
		String typeMontantBon = (String) request.getParameter("typeMontantBon");
		String perioder = (String) request.getParameter("perioder");

		Integer bps = Integer.valueOf(request.getParameter("bps"));
		Integer frequence = Integer.valueOf(request.getParameter("frequence"));
		Double prk = Double.valueOf(request.getParameter("prk"));
		String codeTypeCredit = (String) request.getParameter("codeTypeCredit");
		String codeAchat = (String) request.getParameter("codeAchat");

		if (StringUtils.isNotBlank(codeMembreAcheteur) && StringUtils.isNotBlank(typeR)
				&& StringUtils.isNotBlank(typeMontantBon) && !Double.isNaN(montantAchat)
				&& StringUtils.isNotBlank(codeTypeCredit) && StringUtils.isNotBlank(codeAchat)) {

			typeProduit = typeCreditService.findById(codeTypeCredit).getTypeProduit();

			if (perioder.equals("illimité")) {
				duree = 0d;
				typeRecurrent = "illimite";
			} else if ((perioder.equals("limité 11.2"))) {
				duree = 11.2;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 22.4")) {
				duree = 22.4;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 1")) {
				duree = 1d;
				typeRecurrent = "limite";
			}
			// Double fraisSms=RetrouverFraisSms();

			montantBonConsommation = montantAchat;
			// calculer le montant du bon neutre
			montantBonNeutre = creditUtility.calculMsbc(
					new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk, frequence, bps),
					montantBonConsommation);

			EuAchatInterim euAchatInterim = euAchatInterimService.findAchatInterimByCodeAchat(codeAchat);

			if (Objects.nonNull(euAchatInterim)) {
				if (euAchatInterim.getMontantAchat() >= (montantBonNeutre)) {

					// verifions le quota
					verifyQuota = quotaUtility.verifyQuota(codeTypeCredit, codeMembreAcheteur, montantBonConsommation,
							typeR);
					System.out.println("verifyquota= " + verifyQuota);

					if (verifyQuota) {
						// generer un code unique
						String codeEnvoi = ServerUtil.GenererUniqueCode().substring(0, 5).toUpperCase();
						codeBonconso = ServerUtil.GenererUniqueCode().substring(0, 5).toUpperCase();
						if (typeR.equals("r")) {
							smsBody = "ESMC: Votre code " + codeBonconso + " du BCr de: "
									+ Math.floor(montantBonConsommation) + " du BAn: " + Math.floor(montantBonNeutre)
									+ "";
						} else {
							smsBody = "ESMC: Votre code " + codeBonconso + " du BCnr de: "
									+ Math.floor(montantBonConsommation) + " du BAn: " + Math.floor(montantBonNeutre)
									+ "";
						}

						boolean result = smsComponent.createAndSendCode(codeEnvoi, codeMembreAcheteur, smsBody);
						System.out.println("result= " + result);
						if (result) {
							listCode.add(codeEnvoi);
							listCode.add(String.valueOf(Math.floor(montantBonNeutre)));
							return listCode;
						}
						listCode.add("KO0");
						return listCode;
					}
					listCode.add("KO1");// Vous avez depasse le quota
					return listCode;
				}
				listCode.add("KO2");// montant bon neutre insuffisant
				return listCode;
			}
			listCode.add("KO3");// Aucun enregistrement trouvé
			return listCode;
		}

		listCode.add("KO4");// revoir la saisie
		return listCode;
	}

	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/creationBonConsoByInterim", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Double CreerBonConsommationParInterim(HttpServletRequest request)
			throws CompteNonIntegreException, SoldeInsuffisantException, DataAccessException, NullPointerException,
			CompteNonTrouveException {
		String reponse = "KO";
		boolean result = false;
		int boncreer = 0;
		EuBon bonConsommation = null;
		EuBon bonAchat = null;
		EuBonNeutre bonNeutre = null;
		EuMembreMorale euMembreMorale = null;
		String typeBC = null;
		double montantBonNeutre;
		String compteVendeur;
		double montantBonConso;
		String typeRecurrent = "";
		double duree = 0;
		Integer subvention = 0;
		String typeProduit = "";
		String nomProduit = "";

		String codeMembreAcheteur = (String) request.getParameter("codeMembreAcheteur");
		String typeR = (String) request.getParameter("typeR");
		Double montantAchat = Double.valueOf(request.getParameter("montantAchat"));
		String codeBonConso = (String) request.getParameter("codeBonConso");
		String typeMontantBon = (String) request.getParameter("typeMontantBon");
		String perioder = (String) request.getParameter("perioder");
		String codeEnvoi = (String) request.getParameter("codeEnvoi");
		Double prk = Double.valueOf(request.getParameter("prk"));
		Integer bps = Integer.valueOf(request.getParameter("bps"));
		Integer frequence = Integer.valueOf(request.getParameter("frequence"));
		String codeTypeCredit = (String) request.getParameter("codeTypeCredit");
		String adresseLivraison = (String) request.getParameter("adresseLivraison");
		Integer modeLivraison = Integer.valueOf(request.getParameter("modeLivraison"));
		String telephoneAcheteur = (String) request.getParameter("telephoneAcheteur");
		Double fraisLivraison = Double.valueOf(request.getParameter("fraisLivraison"));
		List<ArticleVendu> ListArticlesVendus = mapArticle(request);
		Integer domiciliation = Integer.valueOf(request.getParameter("domiciliation"));
		String referenceAdditive = (String) request.getParameter("referenceAdditive");
		String codeAchat = (String) request.getParameter("codeAchat");

		// retrouver le vendeur connecte
		EuUtilisateur utilisateur = BaseController.getCurrentUser();

		if (StringUtils.isNotBlank(codeMembreAcheteur) && StringUtils.isNotBlank(typeR)
				&& StringUtils.isNotBlank(typeMontantBon) && StringUtils.isNotBlank(codeBonConso)
				&& !Double.isNaN(montantAchat) && StringUtils.isNotBlank(codeTypeCredit)
				&& StringUtils.isNotBlank(codeEnvoi) && StringUtils.isNotBlank(codeAchat)) {

			if (codeMembreAcheteur.endsWith("P")) {
				typeBC = "RPG";
			} else {
				typeBC = "I";
			}
			if (perioder.equals("illimité")) {
				duree = 0;
				typeRecurrent = "illimite";
			} else if ((perioder.equals("limité 11.2"))) {
				duree = 11.2;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 22.4")) {
				duree = 22.4;
				typeRecurrent = "limite";
			} else if (perioder.equals("limité 1")) {
				duree = 1d;
				typeRecurrent = "limite";
			}
			typeProduit = typeCreditService.findById(codeTypeCredit).getTypeProduit();

			// retrouver le tegc du membre morale vendeur
			String codeTegc = utilisateur.getCodeTegc();
			EuTegc euTegc = tegcService.getById(codeTegc);
			if (!euTegc.getTypeTegc().equalsIgnoreCase("INTERIM")) {
				String codeMembreVendeur = euTegc.getEuMembreMorale().getCodeMembreMorale();
				nomProduit = euTegc.getNomProduit();
				subvention = euTegc.getSubvention();
				// retrouver le vendeur
				euMembreMorale = moraleService.findById(codeMembreVendeur);
				// retrouver le compte gcp du vendeur
				compteVendeur = "NB-TPAGCP-" + euMembreMorale.getCodeMembreMorale();

			} else {
				compteVendeur = " ";
			}

			if (modeLivraison == 1) {
				// ajout de articleVendu
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
			// Double fraisSms = RetrouverFraisSms();

			// recherche du montant a utiliser dans le bon neutre

			montantBonConso = montantAchat;
			montantBonNeutre = creditUtility.calculMsbc(
					new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk, frequence, bps), montantBonConso);
			System.out.println("montantBonConso mbc= " + montantBonConso);

			// recuperation du bon neutre correspondant
			bonNeutre = bonNeutreService.findByMembre(codeMembreAcheteur);

			if (smsComponent.verifyCodeSms(codeEnvoi, codeBonConso, codeMembreAcheteur)) {

				// Creation du bon d'Achat
				String typeBa = "RPG";
				if (codeMembreAcheteur.endsWith("M")) {
					typeBa = "I";
				}
				result = bonAchatComponent.souscrireBonAchat(codeMembreAcheteur, bonNeutre.getBonNeutreCode(), typeBa,
						Math.floor(montantBonNeutre));

				if (result) {
					bonAchat = transfertUtility.transfertBA(codeMembreAcheteur, "BAN", "",
							Math.floor(montantBonNeutre));

					CalculBonInfo calculBonInfo = new CalculBonInfo();
					calculBonInfo.setCatBon(typeR);
					calculBonInfo.setTypeRecurrent(typeRecurrent);
					calculBonInfo.setTypeProduit(typeProduit);
					calculBonInfo.setTypeCredit(codeTypeCredit);
					calculBonInfo.setPrk(prk);
					calculBonInfo.setDuree(duree);
					calculBonInfo.setBps(bps);
					calculBonInfo.setFrequenceCumul(frequence);

					// String codeMembre, CalculBonInfo bonInfo, String codeBa,
					// double montant
					if (bonAchat != null) {
						boncreer = souscriptionBon.souscrireBonConso(codeMembreAcheteur, calculBonInfo,
								bonAchat.getBonNumero(), bonAchat.getBonMontant());

						if (boncreer == 0) {
							Double montantBonConsommation = creditUtility.calculBonConso(
									new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk, frequence, bps),
									Math.floor(bonAchat.getBonMontant()));
							System.out.println("montantBonConsommation = " + montantBonConsommation);
							bonConsommation = transfertUtility.tansfertBC(codeMembreAcheteur, typeBC, calculBonInfo,
									prk, Math.floor(montantBonConsommation));

							if (bonConsommation != null) {
								// rechercher si le vendeur est beneficiaire d'une subvention
								if (subvention == 1) {
									// payement au gcsc
									reponse = reglementAchat.saveReglementParBonAuGcs(codeTegc, compteVendeur,
											bonConsommation.getBonNumero(), typeR, typeProduit, codeTypeCredit,
											nomProduit, utilisateur.getIdUtilisateur(), ListArticlesVendus);
								} else {
									// payement au tegc
									reponse = reglementAchat.saveReglementSimpleParBon(codeAchat, domiciliation,
											codeTegc, compteVendeur, bonConsommation.getBonNumero(), typeR, typeProduit,
											codeTypeCredit, nomProduit, utilisateur.getIdUtilisateur(),
											ListArticlesVendus);
								}

								if (StringUtils.isNotBlank(reponse) && reponse.equals("OK")) {
									// mise a jour de smsconnexion

									smsComponent.miseAjourSmsConnexion(codeEnvoi);
									// verification de la reference additive
									/*
									 * if(StringUtils.isNotBlank(referenceAdditive)){ EuInformationAdditif
									 * euInformationAdditif
									 * =euInfoAdditifService.retrouverInformationAd(codeMembreAcheteur,
									 * referenceAdditive);
									 * 
									 * }
									 */
									// creation d'un code de livraison
									String codeLivraison = ServerUtil.GenererUniqueCode().substring(0, 5).toUpperCase();
									// creation d'une ligne de commande et de ses détails
									EuCommande commandeNew = new EuCommande();
									if (modeLivraison == 1) {
										commandeNew.setLivrer(0);
										commandeNew.setAdresseLivraison(adresseLivraison);
										commandeNew.setFraisLivraison(fraisLivraison);
										commandeNew.setDateLivraison(new Date());
										commandeNew.setCodeLivraison(codeLivraison);

									} else {
										commandeNew.setLivrer(1);
										commandeNew.setAdresseLivraison(" ");
										commandeNew.setFraisLivraison(0d);
										commandeNew.setDateLivraison(null);
										commandeNew.setCodeLivraison("");
									}
									commandeNew.setExecuter(1);
									commandeNew.setCodeMembreAcheteur(codeMembreAcheteur);
									commandeNew.setDateCommande(new Date());
									EuTegc euTegcVendeur = tegcService.getById(utilisateur.getCodeTegc());
									if (Objects.nonNull(euTegcVendeur)) {
										commandeNew.setCodeMembreVendeur(
												euTegcVendeur.getEuMembreMorale().getCodeMembreMorale());
									}

									// le tegc du vendeur (a changer apres)
									commandeNew.setCodeMembreLivreur(utilisateur.getCodeTegc());
									commandeNew.setCodeZone(utilisateur.getCodeZone());
									if (codeMembreAcheteur.endsWith("P")) {
										EuMembre membre = membreService.findById(codeMembreAcheteur);
										if (membre != null) {
											if (Objects.nonNull(membre.getEuPay())) {
												commandeNew.setIdPays(membre.getEuPay().getIdPays());
											} // commandeNew.setIdPrefecture(membre.get);
												// commandeNew.setIdRegion(utilisateur.getr);
											commandeNew.setQuartierAcheteur(membre.getQuartierMembre());
											commandeNew.setVilleAcheteur(membre.getVilleMembre());
										}
									} else {
										EuMembreMorale membreMoral = moraleService.findById(codeMembreAcheteur);

										if (Objects.nonNull(membreMoral.getEuPay())) {
											commandeNew.setIdPays(membreMoral.getEuPay().getIdPays());
										}
										// commandeNew.setIdPrefecture(membreMoral.getp);
										// commandeNew.setIdRegion(utilisateur.getr);
										commandeNew.setQuartierAcheteur(membreMoral.getQuartierMembre());
										commandeNew.setVilleAcheteur(membreMoral.getVilleMembre());
									}

									commandeNew.setModeLivraison(modeLivraison);
									commandeNew.setTelAcheteur(telephoneAcheteur);

									commandeNew.setMontantCommande(montantBonConso);
									commandeNew.setTypeBon("BAN");
									commandeNew.setTypeRecurrent(typeR);
									commandeNew.setEuUtilisateur(utilisateur);
									// commandeNew.set
									euCommandeRepo.saveAndFlush(commandeNew);
									// Long idCommandeNew = commandeNew.getCodeCommande();

									// enlever les frais de sms
									// ListArticlesVendus = ListArticlesVendus.stream().filter(articleVendu
									// ->!articleVendu.getCodeBarre().equals("FSMS")).collect(Collectors.toList());

									for (ArticleVendu articleVendu : ListArticlesVendus) {
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

										// AJOUT dans eu_detail_commande_additif
										if (StringUtils.isNotBlank(referenceAdditive)) {
											EuDetailCommandeAdditif eudetComAdd = new EuDetailCommandeAdditif();

											EuTegc euTegcMoral = tegcService.getById(articleVendu.getCodeTegc());
											if (euTegcMoral != null) {
												EuMembreMorale moraleVendeur = euTegcMoral.getEuMembreMorale();
												System.out.println("ref ad= " + referenceAdditive);
												if (Objects.nonNull(moraleVendeur)) {
													EuArticleStockesAdditif euArticleStockesAdditif = euArtStockesAddService
															.findArticleStockAdditifByMembreAndReference(
																	moraleVendeur.getCodeMembreMorale(),
																	articleVendu.getReference());
													if (Objects.nonNull(euArticleStockesAdditif)) {
														eudetComAdd.setEuArticleStockesAdditif(euArticleStockesAdditif);
														eudetComAdd.setEuDetailCommande(detailCommande);
														eudetComAdd.setReferenceAdditif(referenceAdditive);
														euDetComAdditifService.add(eudetComAdd);
													}
												}
											}
										}

										// Mise a jour des articles stockes

										EuArticleStockes articleStocke = euArticleStockeService
												.findArticleStockesByReference(articleVendu.getReference());
										if (articleStocke != null) {
											articleStocke.setQteVendu(
													articleStocke.getQteVendu() + articleVendu.getQuantite());
											articleStocke.setQteSolde(
													articleStocke.getQteSolde() - articleVendu.getQuantite());
											euArticleStockeService.update(articleStocke);
										}
									}
									// envoi de sms de livraison
									if (modeLivraison == 1) {
										String smsBody = "Merci de presenter le code :" + codeLivraison
												+ " a la livraison de votre commande N° "
												+ commandeNew.getCodeCommande();
										smsComponent.sendNotifications(codeMembreAcheteur, smsBody);

									} else {
										String smsBody = "Votre commande : " + commandeNew.getCodeCommande()
												+ " a été bien exécutée";
										smsComponent.sendNotifications(codeMembreAcheteur, smsBody);
									}

									return Double.valueOf(0);
								}
							}
							// Echec de creation d'un bon de consommation
							return Double.valueOf(1);
						}
						// Echec de souscription au bon de consommation
						return Double.valueOf(2);
					}
				}
				// Echec de creation du bon d achat
				return Double.valueOf(3);
			}
			// Le code du bon de consommation est invalide
			return Double.valueOf(4);
		} // return reponse = "KO5";// Revoir la saise des données
		return Double.valueOf(5);
	}

}

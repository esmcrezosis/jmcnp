package com.esmc.mcnp.web.controller.smcipn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.domain.dto.obps.ArticleVendu;
import com.esmc.mcnp.domain.entity.bc.EuPrk;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.infrastructure.components.ReglementAchat;
import com.esmc.mcnp.infrastructure.components.SmcipnComponent;
import com.esmc.mcnp.infrastructure.components.SmsComponent;
import com.esmc.mcnp.infrastructure.components.TransfertUtility;
import com.esmc.mcnp.infrastructure.services.cm.EuCompteService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.infrastructure.services.obps.EuPrkService;
import com.esmc.mcnp.infrastructure.services.obps.EuTegcService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

@Controller
public class SmcipnDemandeurController extends BaseController {
	@Inject
	private TransfertUtility transfertUtility;
	@Inject
	private SmcipnComponent smcipnComponent;
	@Inject
	private ReglementAchat reglementAchat;
	@Inject
	private SmsComponent smsComponent;

	private @Autowired EuMembreMoraleService moraleService;
	private @Autowired EuPrkService prkService;
	private @Autowired EuTegcService tegcService;
	private @Autowired EuCompteService compteService;
	private @Autowired ObjectMapper jsonMapper;

	@RequestMapping(value = "/smcipndemandeur", method = RequestMethod.GET)
	public String SmcipnDemandeur() {
		return "distributeur/smcipn_demandeur";
	}

	@ModelAttribute("prkssmc")
	public List<EuPrk> retrouverPrk() {
		String codeTegc;
		List<EuPrk> listPrk = null;
		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		if (StringUtils.isNotBlank(codeTegc)) {
			listPrk = prkService.findValByTegc(codeTegc);
		}
		return listPrk;

	}

	@ModelAttribute("raisontegcsmc")
	public String retrouverRaisonTegc() {
		String nomTegc = "";
		String codeTegc;

		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		if (StringUtils.isNotBlank(codeTegc)) {
			EuTegc euTegc = tegcService.getById(codeTegc);
			if (euTegc != null) {
				nomTegc = euTegc.getNomTegc();
				System.out.println("nomTegc = " + nomTegc);
			}
		}
		return nomTegc.toUpperCase();
	}

	@RequestMapping(value = "tegcsmcipn/{codeMembreDemandeur}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EuTegc retrouverTesmcipn(@PathVariable("codeMembreDemandeur") String codeMembreDemandeur) {

		if (StringUtils.isNotBlank(codeMembreDemandeur)) {
			EuTegc euTegc = tegcService.findTegcByCodeMembreAndNomTegc(codeMembreDemandeur, "TE SMCIPN");
			if (euTegc != null) {
				System.out.println("euTegc.codeTegc = " + euTegc.getCodeTegc());
				return euTegc;
			}

		}
		return null;
	}

	@RequestMapping(value = "smcipnordinaires", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Integer retrouverTypeMarchandise() {
		Integer type3 = 0;
		String codeTegc;
		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		if (StringUtils.isNotBlank(codeTegc)) {
			EuTegc euTegc = tegcService.getById(codeTegc);
			if (euTegc != null) {
				if (euTegc.getOrdinaire() == 1 && euTegc.getSpecial() == 1) {
					type3 = 1;
				}
				if (euTegc.getOrdinaire() == 1 && euTegc.getSpecial() == 0) {
					type3 = 2;
				}
				if (euTegc.getOrdinaire() == 0 && euTegc.getSpecial() == 1) {
					type3 = 3;
				}
				if (euTegc.getOrdinaire() == 0 && euTegc.getSpecial() == 0) {
					type3 = 4;
				}
			}
		}
		return type3;
	}

	// sendSms
	@RequestMapping(value = "/sendSmsCodeBonSmcipn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String> sendSmsForVerification(HttpServletRequest request) {
		String smsBody;
		String codeBonconso;
		double soldeCompte;
		List<String> listCode = new ArrayList<>();

		String codeMembreDemandeur = (String) request.getParameter("codeMembreDemandeur");
		Double montantDemandeSmcipn = Double.valueOf(request.getParameter("montantDemandeSmcipn"));

		System.out.println("codeMembreDemandeur= " + codeMembreDemandeur);
		System.out.println("montantDemandeSmcipn= " + montantDemandeSmcipn);

		if (StringUtils.isNotBlank(codeMembreDemandeur) && !Double.isNaN(montantDemandeSmcipn)) {
			montantDemandeSmcipn = (double) Math.round(montantDemandeSmcipn);

			// retrouver le montant du gcp et comparer avec le montant Achat
			String compteDemandeur = "NR-TI-" + codeMembreDemandeur;
			soldeCompte = compteService.getById(compteDemandeur).getSolde();
			System.out.println("soldeCompte= " + soldeCompte);

			if (soldeCompte >= montantDemandeSmcipn) {
				// generer un code unique
				String codeEnvoi = ServerUtil.GenererUniqueCode();
				codeBonconso = ServerUtil.GenererUniqueCode();
				System.out.println("codeBonconso= " + codeBonconso);

				// envoi de sms
				smsBody = "ESMC: Le code " + codeBonconso.toUpperCase() + " de votre Bon de Consommation de: "
						+ montantDemandeSmcipn;

				listCode.add(codeEnvoi.substring(0, 5).toUpperCase());
				boolean result = smsComponent.createAndSendCode(codeEnvoi, codeMembreDemandeur, smsBody);
				System.out.println("result= " + result);
				return listCode;
			}
			listCode.add("KO1");// Le solde du compte est insuffisant
			return listCode;
		}
		listCode.add("KO2");// Revoir la saisie
		return listCode;
	}

	// calculMontantBonNeutre

	/*@RequestMapping(value = "/reglementSmcipn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Double reglerDemandeSmcipn(HttpServletRequest request) throws CompteNonIntegreException,
			SoldeInsuffisantException, DataAccessException, NullPointerException, CompteNonTrouveException {

		EuMembreMorale euMembreMorale = null;
		EuBon bonConso = null;
		String compteVendeur;
		boolean echanger = false;
		String typeR = "nr";

		// reglement =
		// "designation="+$designation+"&reference="+$reference+"&typeProduit="+$typeProduit+"&codeMembreDemandeur="+$codeMembreDemandeur+"&montantDemandeSmcipn="+$montantDemandeSmcipn+"&_csrf="
		// + $csrf + "";
		String designation = (String) request.getParameter("designation");
		String typeProduit = (String) request.getParameter("typeProduit");
		String codeBonConso = (String) request.getParameter("codeBonConso");
		String codeEnvoi = (String) request.getParameter("codeEnvoi");
		String codeMembreDemandeur = (String) request.getParameter("codeMembreDemandeur");
		String reference = (String) request.getParameter("reference");
		String numAppelOffre = (String) request.getParameter("numAppelOffre");
		Double montantDemandeSmcipn = Double.valueOf(request.getParameter("montantDemandeSmcipn"));
		Double prk = Double.valueOf(request.getParameter("prk"));
		List<ArticleVendu> ListArticlesVendus = mapArticle(request);
		System.out.println("numAppelOffre= " + numAppelOffre);
		System.out.println("typeProduit= " + typeProduit);
		System.out.println("codeMembreDemandeur= " + codeMembreDemandeur);
		System.out.println("designation= " + designation);
		System.out.println("reference= " + reference);
		System.out.println("montantDemandeSmcipn= " + montantDemandeSmcipn);
		System.out.println("prk= " + prk);
		System.out.println("codeEnvoi= " + codeEnvoi);

		// retrouver le vendeur connecte
		EuUtilisateur utilisateur = BaseController.getCurrentUser();
		String codeMembreVendeur = utilisateur.getCodeMembre();
		System.out.println("codeMembreVendeur= " + codeMembreVendeur);

		if (StringUtils.isNotBlank(codeMembreDemandeur) && StringUtils.isNotBlank(numAppelOffre)
				&& StringUtils.isNotBlank(codeBonConso) && StringUtils.isNotBlank(designation)
				&& StringUtils.isNotBlank(typeProduit) && !Double.isNaN(montantDemandeSmcipn) && !Double.isNaN(prk)) {

			// retrouver le vendeur
			euMembreMorale = moraleService.findById(codeMembreVendeur);

			// retrouver le compte gcp du vendeur

			compteVendeur = "NB-TPAGCP-" + euMembreMorale.getCodeMembreMorale();

			if (smsComponent.verifyCodeSms(codeEnvoi, codeBonConso, codeMembreDemandeur)) {
				// echange du rouge en bleu Inr

				echanger = smcipnComponent.echangeNRNB(codeMembreDemandeur, numAppelOffre, "PO", montantDemandeSmcipn,
						prk);

				if (echanger) {
					CalculBonInfo calculBonInfo = new CalculBonInfo();
					calculBonInfo.setCatBon("nr");
					calculBonInfo.setTypeRecurrent("");
					calculBonInfo.setTypeProduit(typeProduit);
					calculBonInfo.setPrk(0);
					calculBonInfo.setDuree(0);

					bonConso = transfertUtility.tansfertBC(codeMembreDemandeur, "TI", calculBonInfo, prk,
							montantDemandeSmcipn);

					if (bonConso != null) {
						String reponse = reglementAchat.saveReglementDemandeurSmcipn(compteVendeur,
								bonConso.getBonNumero(), typeR, numAppelOffre, utilisateur.getIdUtilisateur(),
								ListArticlesVendus);

						System.out.println("reponse reglement simple : " + reponse);

						// mise a jour de smsconnexion
						smsComponent.miseAjourSmsConnexion(codeEnvoi);
						return Double.valueOf(0);
					}
					// return reponse="KO1";//
					return Double.valueOf(1);
				}
				// return reponse="KO2";//Echec de l'opération d'echange des
				return Double.valueOf(2);
			}
			// return reponse="KO3";//le code du bon est invalide
			return Double.valueOf(3);
		}
		// return reponse="KO4";//revoir la saisie
		return Double.valueOf(4);
	}*/

	// Recuperation de la liste des articles vendus
	public List<ArticleVendu> mapArticle(HttpServletRequest request) {

		List<ArticleVendu> listArticles = Lists.newArrayList();
		ArticleVendu[] array;

		try {
			System.out.println(
					" request.getParameter(listArticlesVendus) = " + request.getParameter("listArticlesVendus"));
			array = jsonMapper.readValue(request.getParameter("listArticlesVendus"), ArticleVendu[].class);
			System.out.println("array.length= " + array.length);
			listArticles.addAll(Arrays.asList(array));
			System.out.println("listArticles.size= " + listArticles.size());
			return listArticles;
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return null;

	}

}

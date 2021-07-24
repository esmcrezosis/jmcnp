package com.esmc.mcnp.web.controller.obps;

import java.util.ArrayList;
import java.util.List;

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

import com.esmc.mcnp.components.BonAchatComponent;
import com.esmc.mcnp.components.CreditUtility;
import com.esmc.mcnp.components.EchangeUtility;
import com.esmc.mcnp.components.QuotaUtility;
import com.esmc.mcnp.components.ReglementAchat;
import com.esmc.mcnp.components.SmsComponent;
import com.esmc.mcnp.components.SouscriptionBon;
import com.esmc.mcnp.components.TransfertUtility;
import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dto.bc.CalculBonInfo;
import com.esmc.mcnp.exception.CompteNonIntegreException;
import com.esmc.mcnp.exception.CompteNonTrouveException;
import com.esmc.mcnp.exception.SoldeInsuffisantException;
import com.esmc.mcnp.model.ba.EuCapa;
import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.bc.EuPrk;
import com.esmc.mcnp.model.bc.EuTypeCredit;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.cm.EuMembre;
//import com.esmc.mcnp.model.acteur.EuMaison;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.cm.EuTelephone;
import com.esmc.mcnp.model.obps.EuTegc;
import com.esmc.mcnp.model.obpsd.EuBonNeutre;
import com.esmc.mcnp.model.obpsd.EuTpagcp;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.services.ba.EuBonNeutreService;
import com.esmc.mcnp.services.ba.EuCapaService;
import com.esmc.mcnp.services.cm.EuCompteCreditService;
//import com.esmc.mcnp.services.org.EuMaisonService;
import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.cm.EuMembreService;
import com.esmc.mcnp.services.common.EuTelephoneService;
import com.esmc.mcnp.services.common.EuTypeCreditService;
import com.esmc.mcnp.services.obps.EuPrkService;
//import com.esmc.mcnp.services.EuRevendeurService;
import com.esmc.mcnp.services.obps.EuTegcService;
import com.esmc.mcnp.services.obpsd.EuTpagcpService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.google.common.collect.Lists;

@Controller
public class BoutiqueInterimController extends BaseController {

	private final SouscriptionBon souscriptionBon;
	private final BonAchatComponent bonAchatComponent;
	private final EchangeUtility echangeUtility;
	private final TransfertUtility transfertUtility;
	private final CreditUtility creditUtility;
	private final ReglementAchat reglementAchat;
	private final SmsComponent smsComponent;
	private final QuotaUtility quotaUtility;
	private final EuMembreMoraleService moraleService;
	private final EuMembreService membreService;
	private final EuPrkService prkService;
	private final EuBonNeutreService bonNeutreService;
//	private  EuMaisonService maisonService;
	private final EuCompteCreditService compteCreditService;
	private final EuTegcService tegcService;
	private final EuCapaService capaService;
	private final EuTpagcpService tpagcpService;
	private final EuTypeCreditService typeCreditService;
	private final EuTelephoneService telephoneService;

	public BoutiqueInterimController(SouscriptionBon souscriptionBon, BonAchatComponent bonAchatComponent,
			EchangeUtility echangeUtility, TransfertUtility transfertUtility, CreditUtility creditUtility,
			ReglementAchat reglementAchat, SmsComponent smsComponent, QuotaUtility quotaUtility,
			EuMembreMoraleService moraleService, EuMembreService membreService, EuPrkService prkService,
			EuBonNeutreService bonNeutreService, EuCompteCreditService compteCreditService, EuTegcService tegcService,
			EuCapaService capaService, EuTpagcpService tpagcpService, EuTypeCreditService typeCreditService,
			EuTelephoneService telephoneService) {
		this.souscriptionBon = souscriptionBon;
		this.bonAchatComponent = bonAchatComponent;
		this.echangeUtility = echangeUtility;
		this.transfertUtility = transfertUtility;
		this.creditUtility = creditUtility;
		this.reglementAchat = reglementAchat;
		this.smsComponent = smsComponent;
		this.quotaUtility = quotaUtility;
		this.moraleService = moraleService;
		this.membreService = membreService;
		this.prkService = prkService;
		this.bonNeutreService = bonNeutreService;
		this.compteCreditService = compteCreditService;
		this.tegcService = tegcService;
		this.capaService = capaService;
		this.tpagcpService = tpagcpService;
		this.typeCreditService = typeCreditService;
		this.telephoneService = telephoneService;
	}

	@RequestMapping(value = "/interimfacture", method = RequestMethod.GET)
	public String boutiqueInterim() {
		return "distributeur/boutique_interim";
	}

	// retrouver liste des numéros de téléphone
	@RequestMapping(value = "listeTelephoneBoutikFacture/{codeMembreAcheteur}", method = RequestMethod.GET)
	public @ResponseBody List<EuTelephone> findListTelephone(
			@PathVariable("codeMembreAcheteur") String codeMembreAcheteur) {
		List<EuTelephone> listTelephone = Lists.newArrayList();
		if (StringUtils.isNotBlank(codeMembreAcheteur)) {
			listTelephone = telephoneService.findByMembre(codeMembreAcheteur);
			System.out.println("listTelephone.size = " + listTelephone.size());
		}
		return listTelephone;
	}

	// retrouver liste des numéros de téléphone
	@RequestMapping(value = "nomAcheteurBoutikFacture/{codeMembreAcheteur}", method = RequestMethod.GET)
	public @ResponseBody String findNomAcheteurBoutik(@PathVariable("codeMembreAcheteur") String codeMembreAcheteur) {
		String nomAcheteur = "";
		if (StringUtils.isNotBlank(codeMembreAcheteur)) {
			if (codeMembreAcheteur.endsWith("P")) {
				EuMembre membrePhysique = membreService.findById(codeMembreAcheteur);
				nomAcheteur = membrePhysique.getNomMembre() + " " + membrePhysique.getPrenomMembre();
				System.out.println("nomAcheteur = " + nomAcheteur);
			} else {
				EuMembreMorale membremorale = moraleService.findById(codeMembreAcheteur);
				nomAcheteur = membremorale.getRaisonSociale();
				System.out.println("nomAcheteur = " + nomAcheteur);
			}

		}
		return nomAcheteur;
	}

	@RequestMapping(value = "listtegcAcheteur/{codeMembreAcheteur}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<EuTegc> retrouverListTegc(@PathVariable("codeMembreAcheteur") String codeMembreAcheteur) {
		List<EuTegc> listTegc = Lists.newArrayList();
		if (StringUtils.isNotBlank(codeMembreAcheteur)) {
			if (codeMembreAcheteur.endsWith("M")) {
				listTegc = tegcService.findByCodeMembre(codeMembreAcheteur);

			} else {
				listTegc = tegcService.findByCodeMembrePhysique(codeMembreAcheteur);

			}
		}
		return listTegc;
	}

	@RequestMapping(value = "findprkf/{codeTypeCredit}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Double retrouverPrkByCodeTypeCredit(@PathVariable("codeTypeCredit") String codeTypeCredit) {
		Double prk = 0d;
		if (StringUtils.isNotBlank(codeTypeCredit)) {
			EuTypeCredit euTypeCredit = typeCreditService.findById(codeTypeCredit);
			prk = euTypeCredit.getPrk();
		}
		return prk;
	}

	@ModelAttribute("codeCreditf")
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

	@ModelAttribute("raisoninterim")
	public String retrouverRaisonInterim() {
		String nomTegc = "";
		String codeTegc;

		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		if (StringUtils.isNotBlank(codeTegc)) {
			EuTegc euTegc = tegcService.getById(codeTegc);
			if (euTegc != null && StringUtils.isNotBlank(euTegc.getNomTegc())
					&& !euTegc.getTypeTegc().equals("PRESTATAIRE")) {
				nomTegc = euTegc.getNomTegc().toUpperCase();
				System.out.println("nomTegc = " + nomTegc);
			}
		}
		return nomTegc;
	}

	/*
	 * @ModelAttribute("produitinterim") public String retrouverNomProduitInterim()
	 * { String nomProduit = null; String codeTegc;
	 * 
	 * EuUtilisateur utilisateur = getCurrentUser(); codeTegc =
	 * utilisateur.getCodeTegc(); if(StringUtils.isNotBlank(codeTegc)){ EuTegc
	 * euTegc = tegcService.findById(codeTegc); if (euTegc != null &&
	 * StringUtils.isNotBlank(euTegc.getNomProduit())) { nomProduit =
	 * euTegc.getNomProduit(); System.out.println("nomProduit = " + nomProduit); } }
	 * return nomProduit; }
	 */

	@ModelAttribute("recurrentsint")
	public List<String> retrouverRecurrent() {
		String codeTegc;
		List<String> listValeurs = Lists.newArrayList();
		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		if (StringUtils.isNotBlank(codeTegc)) {
			EuTegc euTegc = tegcService.getById(codeTegc);
			if (euTegc != null) {
				listValeurs.add("Aucun");
				if (euTegc.getRecurrentIllimite() == 1) {
					listValeurs.add("illimité");
				}
				if (euTegc.getRecurrentLimite() == 1 && euTegc.getPeriode1() == 1) {
					listValeurs.add("limité 11.2");
				}
				if (euTegc.getRecurrentLimite() == 1 && euTegc.getPeriode2() == 1) {
					listValeurs.add("limité 22.4");
				}
			}
		}
		return listValeurs;

	}

	@RequestMapping(value = "/facturesordinaires", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Integer retrouverTypeMarchandise() {
		Integer type2 = 0;
		String codeTegc;
		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		if (StringUtils.isNotBlank(codeTegc)) {
			EuTegc euTegc = tegcService.getById(codeTegc);
			if (euTegc != null) {
				if (euTegc.getOrdinaire() == 1 && euTegc.getSpecial() == 1) {
					if (euTegc.getRecurrentLimite() == 1 || euTegc.getRecurrentIllimite() == 1) {
						// il y a recurrent
						type2 = 0;
					} else {
						// il n y a pas de recurrent
						type2 = 10;
					}

				}
				if (euTegc.getOrdinaire() == 1 && euTegc.getSpecial() == 0) {
					if (euTegc.getRecurrentLimite() == 1 || euTegc.getRecurrentIllimite() == 1) {
						// il y a recurrent
						type2 = 1;
					} else {
						// il n y a pas de recurrent
						type2 = 11;
					}

				}
				if (euTegc.getOrdinaire() == 0 && euTegc.getSpecial() == 1) {
					if (euTegc.getRecurrentLimite() == 1 || euTegc.getRecurrentIllimite() == 1) {
						// il y a recurrent
						type2 = 2;
					} else {
						// il n y a pas de recurrent
						type2 = 12;
					}
				}
				if (euTegc.getOrdinaire() == 0 && euTegc.getSpecial() == 0) {
					type2 = 3;
				}
			}
		}
		return type2;
	}

	// calculMontantBonNeutre
	@RequestMapping(value = "/findmontantandsendsmsinterim", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String> findMontantAndSendSms(HttpServletRequest request) {
		Double montantBonNeutre = 0.0;
		String smsBody;
		String codeBonconso;
		EuBonNeutre bonNeutre;
		String typeBC;
		Double montantBonConsommation = 0.0;
		// EuMaison maison;
		Boolean value = true;
		String typeRecurrent = "";
		double duree = 0;
		List<String> listCode = new ArrayList<>();
		Double soldeCredit = 0d;
		Double montantDesOpiDisponibles = 0d;
		Double montantBaRestAChercher = 0d;
		Double montantSoldeBonNeutre = 0d;
		Double soldeCapa = 0d;
		String typeProduit = "";
		Boolean verifyQuota = false;

		String codeMembreAcheteur = (String) request.getParameter("codeMembreAcheteur");
		String typeR = (String) request.getParameter("typeR");
		Double montantAchat = Double.valueOf(request.getParameter("montantAchat"));
		String typeMontantBon = (String) request.getParameter("typeMontantBon");
		String perioder = (String) request.getParameter("perioder");
		// String police = (String) request.getParameter("police");
		Double prk = Double.valueOf(request.getParameter("prk"));
		String codeTypeCredit = (String) request.getParameter("codeTypeCredit");

		// EuUtilisateur utilisateur = getCurrentUser();

		if (StringUtils.isNotBlank(codeMembreAcheteur) && StringUtils.isNotBlank(typeR)
				&& StringUtils.isNotBlank(typeMontantBon) && StringUtils.isNotBlank(codeTypeCredit)
				&& !Double.isNaN(montantAchat)) {

			if (codeMembreAcheteur.endsWith("P")) {
				typeBC = "RPG";
			} else {
				typeBC = "I";

			}
			typeProduit = typeCreditService.findById(codeTypeCredit).getTypeProduit();

			// retrouver le tegc du membre morale vendeur
			/*
			 * String codeTegc = utilisateur.getCodeTegc(); EuTegc euTegc =
			 * tegcService.findById(codeTegc); if (euTegc != null) { //nomProduit =
			 * euTegc.getNomProduit(); }
			 */

			/*
			 * switch (nomProduit) {
			 * 
			 * case "ELECTRICITE": maison =
			 * maisonService.findMaisonByCodeMembre(codeMembreAcheteur); if (maison != null)
			 * { if (maison.getNumCompteurElectricite().equals(police)) { value = true; } }
			 * break; case "EAU": maison =
			 * maisonService.findMaisonByCodeMembre(codeMembreAcheteur); if (maison != null)
			 * { if (maison.getNumCompteurEau().equals(police)) { value = true; } } break;
			 * case "TELEPHONE": maison =
			 * maisonService.findMaisonByCodeMembre(codeMembreAcheteur); if (maison != null)
			 * { if (maison.getNumLigneTelephone().equals(police)) { value = true; } }
			 * break; default: value = true; }
			 */

			// verifions le quota
			verifyQuota = quotaUtility.verifyQuota(codeTypeCredit, codeMembreAcheteur, montantAchat, typeR);

			if (verifyQuota) {

				if (value) {

					if (perioder.equals("illimité")) {
						duree = 0;
						typeRecurrent = "illimite";
					} else if ((perioder.equals("limité 11.2"))) {
						duree = 11.2;
						typeRecurrent = "limite";
					} else if (perioder.equals("limité 22.4")) {
						duree = 22.4;
						typeRecurrent = "limite";
					} else if (perioder.equals("Aucun")) {
						duree = 0;
						typeRecurrent = "";
					}
					// generer un code unique
					String codeEnvoi = ServerUtil.GenererUniqueCode().substring(0, 5).toUpperCase();
					codeBonconso = ServerUtil.GenererUniqueCode().substring(0, 5).toUpperCase();

					if (typeMontantBon.equals("mbc")) {

						montantBonConsommation = montantAchat;

						// calculer le montant du bon neutre
						montantBonNeutre = creditUtility.calculMsbc(
								new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk),
								montantBonConsommation);

						// recuperation du bon neutre correspondant
						bonNeutre = bonNeutreService.findByMembre(codeMembreAcheteur);
						if (bonNeutre != null) {
							if (bonNeutre.getBonNeutreMontantSolde() >= Math.floor(montantBonNeutre)) {

								if (typeR.equals("r")) {
									smsBody = "ESMC: Votre code " + codeBonconso + " du BCr de: "
											+ Math.floor(montantBonConsommation) + " du BAn: "
											+ Math.floor(montantBonNeutre) + "";
								} else {
									smsBody = "ESMC: Votre code " + codeBonconso + " du BCnr de: "
											+ Math.floor(montantBonConsommation) + " du BAn: "
											+ Math.floor(montantBonNeutre) + "";
								}
								listCode.add(codeEnvoi);
								listCode.add(String.valueOf(Math.floor(montantBonNeutre)));
								boolean result = smsComponent.createAndSendCode(codeEnvoi, codeMembreAcheteur, smsBody);

								if (result) {
									return listCode;
								}

							}
							listCode.add("KO1");// montant bon neutre insuffisant
							return listCode;
						}
						listCode.add("KO2");// Il n ya pas de bon neutre
						return listCode;

					} else if (typeMontantBon.equals("mdf")) {
						// chercher dans les BC
						List<EuCompteCredit> credits = null;
						String codeCompteAcheteur = "NB-TPAGC" + typeBC + "-" + codeMembreAcheteur;

						if (typeR.equalsIgnoreCase("r")) {
							credits = ListUtils.emptyIfNull(
									compteCreditService.findByTypeRecurrentAndTypeProduitAndDuree(codeCompteAcheteur,
											typeBC + typeR, typeRecurrent, typeProduit, duree));
						} else if (typeR.equalsIgnoreCase("nr")) {
							credits = ListUtils.emptyIfNull(compteCreditService.fetchByCompteAndProduitAndPrk(
									codeCompteAcheteur, typeBC + typeR, prk, typeProduit));
						}

						if (!credits.isEmpty()) {
							soldeCredit = credits.stream().mapToDouble(EuCompteCredit::getMontantCredit).sum();

						}
						if (soldeCredit >= Math.floor(montantAchat)) {

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
								return listCode;
							}

						} else {

							Double montantRestantAchercher = montantAchat - soldeCredit;

							// chercher ce montant dans les BA internes
							Double montantBonAchat = creditUtility.calculMsbc(
									new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk),
									montantRestantAchercher);

							List<EuCapa> capas = Lists.newArrayList();
							capas = ListUtils.emptyIfNull(
									capaService.findbyMembreAndProduitAndOrigine(codeMembreAcheteur, typeBC, " "));

							if (!capas.isEmpty()) {
								soldeCapa = capas.stream().map(c -> c.getMontantSolde()).reduce(Double::sum).get();
							}
							if (soldeCapa >= Math.floor(montantBonAchat)) {

								if (typeR.equals("r")) {
									smsBody = "ESMC: Votre code " + codeBonconso + " du BCr de: "
											+ Math.floor(montantAchat);
								} else {
									smsBody = "ESMC: Votre code " + codeBonconso + " du BCnr de: "
											+ Math.floor(montantAchat);
								}

								listCode.add(codeEnvoi);
								listCode.add(String.valueOf(Math.floor(montantBonAchat)));
								boolean result = smsComponent.createAndSendCode(codeEnvoi, codeMembreAcheteur, smsBody);

								if (result) {
									return listCode;
								}

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
									boolean result = smsComponent.createAndSendCode(codeEnvoi, codeMembreAcheteur,
											smsBody);

									if (result) {
										return listCode;
									}

								} else {
									montantBaRestAChercher = montantBonAchat - montantSoldeBonNeutre;

									// chercher dans les OPI
									// calcul du montant d'opi disponibles
									List<EuTpagcp> ListTpagcp = null;

									ListTpagcp = tpagcpService.findByMembreAndModeReg(codeMembreAcheteur, "OPI");

									if (!ListTpagcp.isEmpty()) {
										montantDesOpiDisponibles = ListTpagcp.stream().mapToDouble(EuTpagcp::getSolde)
												.sum();
									}

									if (montantDesOpiDisponibles >= Math.floor(montantBaRestAChercher)) {
										if (typeR.equals("r")) {
											smsBody = "ESMC: Votre code " + codeBonconso + " du BCr de: "
													+ Math.floor(montantAchat);
										} else {
											smsBody = "ESMC: Votre code " + codeBonconso + " du BCnr de: "
													+ Math.floor(montantAchat);
										}

										listCode.add(codeEnvoi);
										listCode.add(String.valueOf(Math.floor(montantBaRestAChercher)));
										boolean result = smsComponent.createAndSendCode(codeEnvoi, codeMembreAcheteur,
												smsBody);
										if (result) {
											return listCode;
										}
									} else {
										listCode.add("KO3");
										return listCode;// Les ressources sont indisponibles

									}
								}
							}

						}

					} else if (typeMontantBon.equals("mba")) {

						Double montantBonAchat = creditUtility.calculMsbc(
								new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk), montantAchat);

						// retrouver le montant du BA dans euCapa
						List<EuCapa> capas = Lists.newArrayList();
						capas = ListUtils.emptyIfNull(
								capaService.findbyMembreAndProduitAndOrigine(codeMembreAcheteur, typeBC, "BAI"));
						if (!capas.isEmpty()) {
							soldeCapa = capas.stream().map(c -> c.getMontantSolde()).reduce(Double::sum).get();
							if (soldeCapa >= Math.floor(montantBonAchat)) {

								if (typeR.equals("r")) {
									smsBody = "ESMC: Votre code " + codeBonconso.substring(0, 5).toUpperCase()
											+ " du BCr de: " + Math.floor(montantAchat) + " du BA: "
											+ Math.floor(montantBonAchat) + "";
								} else {
									smsBody = "ESMC: Votre code " + codeBonconso.substring(0, 5).toUpperCase()
											+ " du BCnr de: " + Math.floor(montantAchat) + " du BA: "
											+ Math.floor(montantBonAchat) + "";
								}

								listCode.add(codeEnvoi.substring(0, 5).toUpperCase());
								listCode.add(String.valueOf(Math.floor(montantBonAchat)));
								boolean result = smsComponent.createAndSendCode(codeEnvoi, codeMembreAcheteur, smsBody);
								if (result) {
									return listCode;
								}

							}
							listCode.add("KO6");
							return listCode;// le montant du bon d'achat est insuffisant
						}
						listCode.add("KO7");
						return listCode;// il n ya pas de bon d'achat
						// par les OPI

					} else if (typeMontantBon.equals("mbonc")) {
						List<EuCompteCredit> credits = null;
						String codeCompteAcheteur = "NB-TPAGC" + typeBC + "-" + codeMembreAcheteur;

						if (typeR.equalsIgnoreCase("r")) {
							credits = ListUtils.emptyIfNull(
									compteCreditService.findByTypeRecurrentAndTypeProduitAndDuree(codeCompteAcheteur,
											typeBC + typeR, typeRecurrent, typeProduit, duree));

						} else if (typeR.equalsIgnoreCase("nr")) {

							credits = ListUtils.emptyIfNull(compteCreditService.fetchByCompteAndProduitAndPrk(
									codeCompteAcheteur, typeBC + typeR, prk, typeProduit));

						}

						if (!credits.isEmpty()) {
							soldeCredit = credits.stream().mapToDouble(EuCompteCredit::getMontantCredit).sum();
							// soldeCredit =
							// credits.stream().mapToDouble(EuCompteCredit::getMontantCredit).sum();
							if (soldeCredit >= Math.floor(montantAchat)) {

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
									return listCode;
								}

							}
							listCode.add("KO8");
							return listCode;// le montant du bon de consommation est insuffisant
						}
						listCode.add("KO9");
						return listCode;// Il n y a pas de bons disponibles

					}
				}

				listCode.add("KO10");// Les donnees de la maison ne sont pas valides
				return listCode;
			}
			listCode.add("KO0");// quota depasse
			return listCode;
		}
		listCode.add("KO11");// revoir la saisie des donnees
		return listCode;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/creationBonConsoFactureByBanInterim", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Double CreerBonConsommationParBan(HttpServletRequest request) throws CompteNonIntegreException,
			SoldeInsuffisantException, DataAccessException, NullPointerException, CompteNonTrouveException {
		String reponse = "KO";
		boolean result = false;
		boolean echange = false;
		int boncreer = 0;
		EuBon bonConsommation = null;
		EuBon bonAchat = null;
		EuBonNeutre bonNeutre = null;
		EuMembreMorale euMembreMorale = null;
		String typeBC = null;
		double montantBonNeutre;
		String compteVendeur;
		String typeRecurrent = "";
		Double duree = 0d;
		String nomProduit = "";
		String typeProduit = "";
		Integer subvention = 0;
		Double soldeCredit = 0d;
		Double montantDesOpiDisponibles = 0d;
		Double montantSoldeBonNeutre = 0d;
		Double soldeCapa = 0d;

		String codeMembreAcheteur = (String) request.getParameter("codeMembreAcheteur");
		String typeR = (String) request.getParameter("typeR");
		Double montantAchat = Double.valueOf(request.getParameter("montantAchat"));
		String typeMontantBon = (String) request.getParameter("typeMontantBon");
		String perioder = (String) request.getParameter("perioder");
		String periodeFacture = (String) request.getParameter("periodeFacture");
		// String police = (String) request.getParameter("police ");
		String referenceFacture = (String) request.getParameter("referenceFacture");
		String codeBonConso = (String) request.getParameter("codeBonConso");
		String codeEnvoi = (String) request.getParameter("codeEnvoi");
		Double prk = Double.valueOf(request.getParameter("prk"));
		String codeTypeCredit = (String) request.getParameter("codeTypeCredit");

		// retrouver le vendeur connecte
		EuUtilisateur utilisateur = BaseController.getCurrentUser();
		String codeMembreVendeur = utilisateur.getCodeMembre();
		System.out.println("codeMembreVendeur= " + codeMembreVendeur);

		if (StringUtils.isNotBlank(codeMembreAcheteur) && StringUtils.isNotBlank(codeMembreVendeur)
				&& StringUtils.isNotBlank(typeR) && !Double.isNaN(montantAchat)
				&& StringUtils.isNotBlank(typeMontantBon) && StringUtils.isNotBlank(codeBonConso)
				&& StringUtils.isNotBlank(codeEnvoi)) {

			if (codeMembreAcheteur.endsWith("P")) {

				typeBC = "RPG";

			} else {
				typeBC = "I";
			}

			// retrouver le tegc du membre morale vendeur
			String codeTegc = utilisateur.getCodeTegc();
			EuTegc euTegc = tegcService.getById(codeTegc);
			if (euTegc != null) {
				nomProduit = euTegc.getNomProduit();
				subvention = euTegc.getSubvention();
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
			}

			typeProduit = typeCreditService.findById(codeTypeCredit).getTypeProduit();

			// retrouver le vendeur
			euMembreMorale = moraleService.findById(codeMembreVendeur);

			// retrouver le compte gcp du vendeur

			compteVendeur = "NB-TPAGCP-" + euMembreMorale.getCodeMembreMorale();

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

				if (typeMontantBon.equals("mdf")) {
					// commencons avec les BC

					// List<EuCompteCredit> credits = null;
					String codeCompteAcheteur = "NB-TPAGC" + typeBC + "-" + codeMembreAcheteur;

					/*
					 * if (typeR.equalsIgnoreCase("r")) { credits =
					 * ListUtils.emptyIfNull(compteCreditService.
					 * findByTypeRecurrentAndTypeProduitAndDuree(codeCompteAcheteur, typeBC+typeR,
					 * typeRecurrent, typeProduit, duree)); } else if(typeR.equalsIgnoreCase("nr")){
					 * 
					 * credits = ListUtils
					 * .emptyIfNull(compteCreditService.fetchByCompteAndProduitAndPrk(
					 * codeCompteAcheteur, typeBC+typeR, prk, typeProduit)); }
					 */

					/*
					 * if (!credits.isEmpty()) { soldeCredit =
					 * credits.stream().mapToDouble(EuCompteCredit::getMontantCredit).sum();
					 * 
					 * }
					 */
					soldeCredit = compteCreditService.getSumCreditByEuCompte(codeCompteAcheteur);

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
								new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk), montantAchat);

						// retrouver le montant du BA dans euCapa

						List<EuCapa> capas = Lists.newArrayList();
						capas = ListUtils.emptyIfNull(
								capaService.findbyMembreAndProduitAndOrigine(codeMembreAcheteur, typeBC, ""));

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

								result = bonAchatComponent.souscrireBonAchat(codeMembreAcheteur,
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

								result = bonAchatComponent.souscrireBonAchat(codeMembreAcheteur,
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
								// allons voir s'il y a des OPI
								List<EuTpagcp> ListTpagcp = null;
								List<Long> ListIdTpagcp = new ArrayList<>();
								ListTpagcp = tpagcpService.findByMembreAndModeReg(codeMembreAcheteur, "OPI");

								if (!ListTpagcp.isEmpty()) {
									montantDesOpiDisponibles = Math
											.floor(ListTpagcp.stream().mapToDouble(EuTpagcp::getSolde).sum());

									ListTpagcp.forEach((euTpagcp) -> {
										ListIdTpagcp.add(euTpagcp.getIdTpagcp());
									});
								}
								if (montantDesOpiDisponibles >= Math.floor(montantBonAchat)) {
									// echange opi en bon d'achat (CAPA)
									echange = echangeUtility.echangeOpi(codeMembreAcheteur, ListIdTpagcp,
											montantBonAchat);

									if (echange) {
										// creation bon d'achat et bon de consommation

										bonAchat = transfertUtility.transfertBA(codeMembreAcheteur, "NN", "",
												Math.floor(montantBonAchat));

										boncreer = souscriptionBon.souscrireBonConso(codeMembreAcheteur, calculBonInfo,
												bonAchat.getBonNumero(), bonAchat.getBonMontant());
									}

								} else {

									// echange opi en bon d'achat (CAPA)
									echange = echangeUtility.echangeOpi(codeMembreAcheteur, ListIdTpagcp,
											montantDesOpiDisponibles);

									if (echange) {
										// creation bon d'achat et bon de consommation

										bonAchat = transfertUtility.transfertBA(codeMembreAcheteur, "NN", "",
												Math.floor(montantDesOpiDisponibles));

										boncreer = souscriptionBon.souscrireBonConso(codeMembreAcheteur, calculBonInfo,
												bonAchat.getBonNumero(), bonAchat.getBonMontant());

									}

								}
							}
						}
					}
					/*
					 * }else if(typeMontantBon.equals("opi")){
					 * 
					 * //calcul du montant de OPI a utiliser montantBonOpi=
					 * creditUtility.calculMsbc(new CalculBonInfo(typeR, typeRecurrent, typeProduit,
					 * duree, prk), montantAchat);
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
					 * Math.floor(montantBonOpi));
					 * 
					 * if(bonAchat!=null){ boncreer =
					 * souscriptionBon.souscrireBonConso(codeMembreAcheteur, calculBonInfo,
					 * bonAchat.getBonNumero(), bonAchat.getBonMontant());
					 * 
					 * System.out.println("boncreer= " + boncreer); } } }
					 */} else if (typeMontantBon.equals("mbc")) {
					// bon neutre

					// calculer le montant du bon neutre
					montantBonNeutre = creditUtility
							.calculMsbc(new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk), montantAchat);
					// recuperation du bon neutre correspondant
					bonNeutre = bonNeutreService.findByMembre(codeMembreAcheteur);
					if (bonNeutre != null) {
						if (bonNeutre.getBonNeutreMontantSolde() >= Math.floor(montantBonNeutre)) {
							result = bonAchatComponent.souscrireBonAchat(codeMembreAcheteur,
									bonNeutre.getBonNeutreCode(), typeBa, Math.floor(montantBonNeutre));

							if (result) {
								bonAchat = transfertUtility.transfertBA(codeMembreAcheteur, "BAN", "",
										Math.floor(montantBonNeutre));

								if (bonAchat != null) {
									boncreer = souscriptionBon.souscrireBonConso(codeMembreAcheteur, calculBonInfo,
											bonAchat.getBonNumero(), bonAchat.getBonMontant());

								}
							}
						}
					}
				} else if (typeMontantBon.equals("mba")) {
					Double montantBonAchat = creditUtility
							.calculMsbc(new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk), montantAchat);
					bonAchat = transfertUtility.transfertBA(codeMembreAcheteur, "BAI", "", Math.floor(montantBonAchat));

					if (bonAchat != null) {
						boncreer = souscriptionBon.souscrireBonConso(codeMembreAcheteur, calculBonInfo,
								bonAchat.getBonNumero(), bonAchat.getBonMontant());

					}
				} else if (typeMontantBon.equals("mbonc")) {
					bonConsommation = transfertUtility.tansfertBC(codeMembreAcheteur, typeBC, calculBonInfo, prk,
							montantAchat);

					if (bonConsommation != null) {

						// rechercher si le vendeur est beneficiaire d'une subvention
						if (subvention == 1) {
							reponse = reglementAchat.saveReglementFactureParBonAuGcsc(codeTegc, compteVendeur,
									bonConsommation.getBonNumero(), typeR, typeProduit, codeTypeCredit, nomProduit,
									referenceFacture, periodeFacture, utilisateur.getIdUtilisateur());
						} else {
							reponse = reglementAchat.saveReglementFactureParBon(codeTegc, compteVendeur,
									bonConsommation.getBonNumero(), typeR, typeProduit, codeTypeCredit, nomProduit,
									referenceFacture, periodeFacture, utilisateur.getIdUtilisateur());
						}
						if (StringUtils.isNotBlank(reponse) && reponse.startsWith("BL")) {
							// mise a jour de smsconnexion
							smsComponent.miseAjourSmsConnexion(codeEnvoi);
							return Double.valueOf(0);
						}

					}
				}
				if (boncreer == 0) {
					Double montantBonConsommation = creditUtility.calculBonConso(
							new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk),
							Math.floor(bonAchat.getBonMontant()));

					bonConsommation = transfertUtility.tansfertBC(codeMembreAcheteur, typeBC, calculBonInfo, prk,
							Math.floor(montantBonConsommation));

					if (bonConsommation != null) {

						// rechercher si le vendeur est beneficiaire d'une subvention
						if (subvention == 1) {
							reponse = reglementAchat.saveReglementFactureParBonAuGcsc(codeTegc, compteVendeur,
									bonConsommation.getBonNumero(), typeR, typeProduit, codeTypeCredit, nomProduit,
									referenceFacture, periodeFacture, utilisateur.getIdUtilisateur());
						} else {
							reponse = reglementAchat.saveReglementFactureParBon(codeTegc, compteVendeur,
									bonConsommation.getBonNumero(), typeR, typeProduit, codeTypeCredit, nomProduit,
									referenceFacture, periodeFacture, utilisateur.getIdUtilisateur());
						}
						if (StringUtils.isNotBlank(reponse) && reponse.startsWith("BL")) {
							// mise a jour de smsconnexion
							smsComponent.miseAjourSmsConnexion(codeEnvoi);
							return Double.valueOf(0);
						}
					}
				}
				return Double.valueOf(1); // echec de creation du bc
			}
			return Double.valueOf(2); // Le code du bon est invalide
		}
		return Double.valueOf(3); // Revoir la saisie

	}

}

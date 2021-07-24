package com.esmc.mcnp.web.controller.oksu;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esmc.mcnp.components.BonAchatComponent;
import com.esmc.mcnp.components.CreditUtility;
import com.esmc.mcnp.components.OPIComponent;
import com.esmc.mcnp.components.ReglementAchat;
import com.esmc.mcnp.components.SmcipnComponent;
import com.esmc.mcnp.components.SmsComponent;
import com.esmc.mcnp.components.SouscriptionBon;
import com.esmc.mcnp.components.TransfertUtility;
import com.esmc.mcnp.exception.CompteNonIntegreException;
import com.esmc.mcnp.exception.CompteNonTrouveException;
import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.exception.SoldeInsuffisantException;
import com.esmc.mcnp.dto.bc.CalculBonInfo;
import com.esmc.mcnp.dto.obps.ArticleVendu;
import com.esmc.mcnp.model.ba.EuCapa;
import com.esmc.mcnp.model.cm.EuTelephone;
import com.esmc.mcnp.model.bc.EuTypeCredit;
import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.cm.EuCompteBancaire;
import com.esmc.mcnp.model.obps.EuTegc;
import com.esmc.mcnp.model.obpsd.EuBanque;
import com.esmc.mcnp.model.obpsd.EuBonNeutre;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.services.acteurs.EuBanqueService;
import com.esmc.mcnp.services.ba.EuBonNeutreService;
import com.esmc.mcnp.services.ba.EuCapaService;
import com.esmc.mcnp.services.cm.EuCompteBancaireService;
import com.esmc.mcnp.services.common.EuTelephoneService;
import com.esmc.mcnp.services.common.EuTypeCreditService;
import com.esmc.mcnp.services.obps.EuTegcService;
import com.esmc.mcnp.services.oksu.EuRevendeurService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.google.common.collect.Lists;

@Controller
public class RevendeurPersoController extends BaseController {
	private OPIComponent opiCompo;
	private SouscriptionBon souscriptionBon;
	private final BonAchatComponent bonAchatComponent;
	private TransfertUtility transfertUtility;
	/*
	 * @Inject private EchangeUtility echangeUtility;
	 */
	private CreditUtility creditUtility;
	private ReglementAchat reglementAchat;
	private SmsComponent smsComponent;
	private SmcipnComponent smcipnComponent;
	private EuTypeCreditService typeCreditService;
	private EuBonNeutreService bonNeutreService;
	private EuTegcService tegcService;
	private EuRevendeurService revendeurService;
	private EuBanqueService banqueService;
	private EuTelephoneService telephoneService;
	private EuCompteBancaireService compteBancaireService;
	private EuCapaService capaService;

	public RevendeurPersoController(OPIComponent opiCompo, SouscriptionBon souscriptionBon,
			TransfertUtility transfertUtility, CreditUtility creditUtility, ReglementAchat reglementAchat,
			SmsComponent smsComponent, SmcipnComponent smcipnComponent, EuTypeCreditService typeCreditService,
			EuBonNeutreService bonNeutreService, EuTegcService tegcService, EuRevendeurService revendeurService,
			EuBanqueService banqueService, EuTelephoneService telephoneService,
			EuCompteBancaireService compteBancaireService, EuCapaService capaService,
			BonAchatComponent bonAchatComponent) {
		this.bonAchatComponent = bonAchatComponent;
		this.opiCompo = opiCompo;
		this.souscriptionBon = souscriptionBon;
		this.transfertUtility = transfertUtility;
		this.creditUtility = creditUtility;
		this.reglementAchat = reglementAchat;
		this.smsComponent = smsComponent;
		this.smcipnComponent = smcipnComponent;
		this.typeCreditService = typeCreditService;
		this.bonNeutreService = bonNeutreService;
		this.tegcService = tegcService;
		this.revendeurService = revendeurService;
		this.banqueService = banqueService;
		this.telephoneService = telephoneService;
		this.compteBancaireService = compteBancaireService;
		this.capaService = capaService;
	}

	@RequestMapping(value = "/revendeurperso", method = RequestMethod.GET)
	public String revendeurPerso() {
		return "distributeur/souscription_perso";
	}

	/*
	 * @ModelAttribute("revproduit") public List<String> retrouverProduit() {
	 * List<String> listProduitRevendeur =
	 * revendeurService.getListeProduitsRevendeur(); return listProduitRevendeur; }
	 */

	@RequestMapping(value = "/revproduit", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String> retrouverProduit() {
		List<String> listProduitRevendeur = revendeurService.getListeProduitsRevendeur();
		return listProduitRevendeur;
	}

	@RequestMapping(value = "/listeTeSousPerso", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<EuTegc> retrouverListTegc() {
		List<EuTegc> listTegc = Lists.newArrayList();
		EuUtilisateur utilisateur = getCurrentUser();
		String codeMembreRevendeur = utilisateur.getCodeMembre();
		if (StringUtils.isNotBlank(codeMembreRevendeur)) {
			if (codeMembreRevendeur.endsWith("M")) {
				listTegc = tegcService.findByCodeMembre(codeMembreRevendeur);
				System.out.println("listTegc.size = " + listTegc.size());
			} else {
				listTegc = tegcService.findByCodeMembrePhysique(codeMembreRevendeur);
				System.out.println("listTegc.size = " + listTegc.size());
			}
		}
		return listTegc;
	}

	// retrouver liste des numéros de téléphone

	@ModelAttribute("listeTelephone")
	public List<EuTelephone> findListTelephone() {
		EuUtilisateur utilisateur = getCurrentUser();
		String codeMembreRevendeur = utilisateur.getCodeMembre();
		List<EuTelephone> listTelephone = Lists.newArrayList();
		listTelephone = telephoneService.findByMembre(codeMembreRevendeur);
		System.out.println("listTelephone.size = " + listTelephone.size());
		return listTelephone;
	}

	// retrouver liste moyens de paiement
	@ModelAttribute("listePayement")
	public List<EuBanque> findListModePayement() {
		List<EuBanque> listModePayement = Lists.newArrayList();
		listModePayement = banqueService.list();
		System.out.println("listModePayement.size = " + listModePayement.size());
		return listModePayement;
	}

	// retrouver liste comptes bancaires
	@ModelAttribute("listeCompteBancaire")
	public List<EuCompteBancaire> findListCompteBancaire() {
		EuUtilisateur utilisateur = getCurrentUser();
		String codeMembreRevendeur = utilisateur.getCodeMembre();
		List<EuCompteBancaire> listCompteBancaire = Lists.newArrayList();
		listCompteBancaire = compteBancaireService.findListCompteBancaire(codeMembreRevendeur);
		System.out.println("listCompteBancaire.size = " + listCompteBancaire.size());
		return listCompteBancaire;
	}

	/*
	 * @ModelAttribute("revPerso") public String retrouverPersonneSouscription() {
	 * String nomTegc = ""; String codeTegc;
	 * 
	 * EuUtilisateur utilisateur = BaseController.getCurrentUser(); codeTegc =
	 * utilisateur.getCodeTegc(); if(StringUtils.isNotBlank(codeTegc)){ EuTegc
	 * euTegc = tegcService.findById(codeTegc); if (euTegc != null &&
	 * StringUtils.isNotBlank(euTegc.getNomTegc())) { nomTegc =
	 * euTegc.getNomTegc().toUpperCase(); System.out.println("nomTegc = " +
	 * nomTegc); } } return nomTegc; }
	 */

	// verifier bon neutre et envoyer sms
	@RequestMapping(value = "/sendSmsRevendeurPerso", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String> findMontantAndSendSms(HttpServletRequest request) {
		String codeMembreRevendeur;
		String smsBody;
		String codeBonconso;
		EuBonNeutre bonNeutre;
		List<String> listCode = new ArrayList<>();

		// rechercheMontant =
		// "montantAchat="+$montantAchatHT+"&prk="+$prk+"&typeMarchandise="+$typeMarchandise+"&codeMembreRevendeur="+$codeMembreRevendeur+"&_csrf="
		// + $csrf + "";
		Double montantBon = Double.valueOf(request.getParameter("montantBon"));
		String numeroTelephone = (String) (request.getParameter("numeroTelephone"));
		String typeBon = (String) (request.getParameter("typeBon"));
		System.out.println("montantBon= " + montantBon);
		System.out.println("numeroTelephone= " + numeroTelephone);
		System.out.println("typeBon= " + typeBon);

		if (StringUtils.isNotBlank(numeroTelephone) && StringUtils.isNotBlank(typeBon) && !Double.isNaN(montantBon)) {

			EuUtilisateur utilisateur = BaseController.getCurrentUser();
			codeMembreRevendeur = utilisateur.getCodeMembre();

			if (typeBon.equals("BAN")) {
				// recuperation du bon neutre correspondant
				bonNeutre = bonNeutreService.findByMembre(codeMembreRevendeur);
				if (bonNeutre != null) {
					if (bonNeutre.getBonNeutreMontantSolde() >= montantBon) {
						// generer un code unique
						String codeEnvoi = ServerUtil.GenererUniqueCode();
						codeBonconso = ServerUtil.GenererUniqueCode();

						smsBody = "ESMC: Le code " + codeBonconso.substring(0, 5).toUpperCase()
								+ " de votre operation Acheteur Revendeur du montant de BAn: " + Math.floor(montantBon);

						boolean result = smsComponent.sendCodeSms(codeMembreRevendeur, smsBody, codeEnvoi,
								numeroTelephone);
						System.out.println("result= " + result);
						if (result) {
							listCode.add(codeEnvoi.substring(0, 5).toUpperCase());
							return listCode;
						}
						listCode.add("KO0");
						return listCode;
					}
					listCode.add("KO1");// montant bon neutre insuffisant
					return listCode;
				}
				listCode.add("KO2");// pas de bon neutre
				return listCode;
			} else {
				List<EuCapa> capas = Lists.newArrayList();
				capas = ListUtils
						.emptyIfNull(capaService.findbyMembreAndProduitAndOrigine(codeMembreRevendeur, " ", " "));
				System.out.println("capas size= " + capas.size());
				if (!capas.isEmpty()) {
					Double soldeCapa = capas.stream().map(c -> c.getMontantSolde()).reduce(Double::sum).get();
					if (soldeCapa >= Math.floor(montantBon)) {
						// generer un code unique
						String codeEnvoi = ServerUtil.GenererUniqueCode().substring(0, 5).toUpperCase();
						codeBonconso = ServerUtil.GenererUniqueCode().substring(0, 5).toUpperCase();

						smsBody = "ESMC: Le code " + codeBonconso
								+ " de votre operation Acheteur Revendeur du montant de BA interne: "
								+ Math.floor(montantBon);

						boolean result = smsComponent.sendCodeSms(codeMembreRevendeur, smsBody, codeEnvoi,
								numeroTelephone);
						System.out.println("result= " + result);
						if (result) {
							listCode.add(codeEnvoi);
							return listCode;
						}
						listCode.add("KO0");
						return listCode;
					}
					listCode.add("KO1");// montant BAI insuffisant
					return listCode;
				}
			}
		}
		listCode.add("KO3");// revoir la saisie
		return listCode;

	}

	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/executerOperationRevendeur", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Double executerOperationRevendeurParBan(HttpServletRequest request)
			throws CompteNonIntegreException, SoldeInsuffisantException, DataAccessException, NullPointerException,
			CompteNonTrouveException {
		boolean result = false;
		boolean echanger = false;
		int boncreer = 0;
		EuBon bonConsommation = null;
		EuBon bonConso = null;
		EuBon bonAchat = null;
		EuBonNeutre bonNeutre = null;
		String typeBC = null;
		String compteVendeur;
		String typeRecurrent = "";
		Double duree = 0d;
		Double montantBonConsommation;
		String typeR = "nr";
		String codeMembreAcheteur = "";
		String codeMembreVendeur = "";
		String numeroBL;
		String numAppelOffre = "";
		String nomProduit = "";
		String typeProduit = "";
		String codeTypeCredit = "CNPGRE";
		Double prk = 0d;
		String codeMembreInterim = "";
		String codeTegcInterim = "";

		// reglement =
		// "designationProduit="+$designationProduit+"&periodeReutilisation="+$periodeReutilisation+"&reutiliser="+$reutiliser+"&numeroCompteBancaire="+$numeroCompteBancaire+"&codeTegc="+$codeTegc+"&numeroTelephone="+$numeroTelephone+"&codeEnvoi="+$codeEnvoi+"&codeBonConso="+$codeBonConso+"&montantBonNeutre="+$montantBonNeutre+"&_csrf="
		// + $csrf + "";

		Integer periodeReutilisation = Integer.valueOf(request.getParameter("periodeReutilisation"));
		Integer reutiliser = Integer.valueOf(request.getParameter("reutiliser"));
		Double montantBon = Double.valueOf(request.getParameter("montantBon"));
		String codeBonConso = (String) request.getParameter("codeBonConso");
		String codeEnvoi = (String) request.getParameter("codeEnvoi");
		String codeTegcRevendeur = (String) request.getParameter("codeTegc");
		String designationProduit = (String) request.getParameter("designationProduit");
		String referencePayement = (String) request.getParameter("referencePayement");
		String modePayement = (String) request.getParameter("modePayement");
		String typeBon = (String) request.getParameter("typeBon");
		List<ArticleVendu> ListArticlesVendus = Lists.newArrayList();

		System.out.println("codeBonConso= " + codeBonConso);
		System.out.println("codeEnvoi= " + codeEnvoi);
		System.out.println("codeTegc= " + codeTegcRevendeur);
		System.out.println("designationProduit= " + designationProduit);
		System.out.println("periodeReutilisation= " + periodeReutilisation);
		System.out.println("reutiliser= " + reutiliser);
		System.out.println("modePayement= " + modePayement);
		System.out.println("referencePayement= " + referencePayement);
		System.out.println("typeBon= " + typeBon);
		// retrouver le tegc du membre morale vendeur du TE REVENDEUR
		EuTegc euTegc = tegcService.findTegcByCodeMembreAndNomTegc("0010010010010000003M", "TE ACHETEUR-REVENDEUR");
		if (euTegc != null) {
			codeMembreInterim = euTegc.getEuMembreMorale().getCodeMembreMorale();
			codeTegcInterim = euTegc.getCodeTegc();
			nomProduit = euTegc.getNomProduit();
		}
		// le revendeur connecté
		EuUtilisateur utilisateur = BaseController.getCurrentUser();
		String codeMembreRevendeur = utilisateur.getCodeMembre();

		if (StringUtils.isNotBlank(codeMembreRevendeur) && StringUtils.isNotBlank(codeMembreInterim)
				&& StringUtils.isNotBlank(typeR) && StringUtils.isNotBlank(codeBonConso) && !Double.isNaN(montantBon)
				&& StringUtils.isNotBlank(codeEnvoi) && StringUtils.isNotBlank(codeTegcRevendeur)
				&& StringUtils.isNotBlank(typeBon)) {

			EuTypeCredit euTypeCredit = typeCreditService.findById(codeTypeCredit);
			if (euTypeCredit != null) {
				typeProduit = euTypeCredit.getTypeProduit();
				prk = euTypeCredit.getPrk();
			}
			// 1ere operation
			codeMembreAcheteur = codeMembreRevendeur;
			codeMembreVendeur = codeMembreInterim;

			if (smsComponent.verifyCodeSms(codeEnvoi, codeBonConso, codeMembreAcheteur)) {

				if (codeMembreAcheteur.endsWith("P")) {
					typeBC = "RPG";
				} else {
					typeBC = "I";
				}

				// retrouver le compte gcp du vendeur
				compteVendeur = "NB-TPAGCP-" + codeMembreVendeur;

				// recuperation du bon neutre correspondant
				bonNeutre = bonNeutreService.findByMembre(codeMembreAcheteur);

				// Creation du bon d'Achat
				String typeBa = "RPG";
				if (codeMembreAcheteur.endsWith("M")) {
					typeBa = "I";
				}
				if (typeBon.equals("BAN")) {
					// recuperation du bon neutre correspondant
					bonNeutre = bonNeutreService.findByMembre(codeMembreAcheteur);

					result = bonAchatComponent.souscrireBonAchat(codeMembreAcheteur, bonNeutre.getBonNeutreCode(),
							typeBa, Math.floor(montantBon));
					System.out.println("result= " + result);
					if (result) {
						bonAchat = transfertUtility.transfertBA(codeMembreAcheteur, "BAN", "", Math.floor(montantBon));
					}
				} else if (typeBon.equals("BAI")) {
					bonAchat = transfertUtility.transfertBA(codeMembreAcheteur, "", "", Math.floor(montantBon));
					System.out.println("bonAchat= " + bonAchat.getBonNumero());
				}

				CalculBonInfo calculBonInfo = new CalculBonInfo();
				calculBonInfo.setCatBon(typeR);
				calculBonInfo.setTypeRecurrent(typeRecurrent);
				calculBonInfo.setTypeProduit(typeProduit);
				calculBonInfo.setPrk(prk);
				calculBonInfo.setDuree(duree);

				if (bonAchat != null) {
					boncreer = souscriptionBon.souscrireBonConso(codeMembreAcheteur, calculBonInfo,
							bonAchat.getBonNumero(), bonAchat.getBonMontant());

					System.out.println("boncreer= " + boncreer);

					if (boncreer == 0) {
						montantBonConsommation = creditUtility.calculBonConso(
								new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk),
								Math.floor(bonAchat.getBonMontant()));
						System.out.println("montantBonConsommation floor = " + Math.floor(montantBonConsommation));
						// System.out.println("montantBonConsommation round =
						// "+Math.round(montantBonConsommation));

						bonConsommation = transfertUtility.tansfertBC(codeMembreAcheteur, typeBC, calculBonInfo, prk,
								Math.floor(montantBonConsommation));

						if (bonConsommation != null) {

							// faire la liste d'articles vendus
							ListArticlesVendus.add(new ArticleVendu("", codeMembreAcheteur, designationProduit,
									designationProduit, 1, Math.floor(montantBonConsommation)));

							// payement au tegc de l'interim et renvoie de son
							// numero de bon de livraison
							numeroBL = reglementAchat.saveReglementSimpleParBon("", 0, codeTegcInterim, compteVendeur,
									bonConsommation.getBonNumero(), typeR, typeProduit, codeTypeCredit, nomProduit,
									utilisateur.getIdUtilisateur(), ListArticlesVendus);

							System.out.println("reponse reglement 1 : " + numeroBL);
							ListArticlesVendus.clear();
							// fin de la 1ere operation

							// debut de la 2eme partie

							if (StringUtils.isNotBlank(numeroBL) && numeroBL.startsWith("BL")) {

								codeMembreAcheteur = codeMembreInterim;
								codeMembreVendeur = codeMembreRevendeur;

								if (codeMembreAcheteur.endsWith("P")) {
									typeBC = "RPG";
								} else {
									typeBC = "I";
								}

								// retrouver le compte gcp du vendeur

								compteVendeur = "NB-TPAGCP-" + codeMembreVendeur;
								System.out.println("compte vendeur = " + compteVendeur);

								System.out.println("codeTegcRevendeur = " + codeTegcRevendeur);
								// Declenchement de la SMCIPN avec le BL
								System.out.println(
										"montantBonConsommation avant smcipn= " + Math.floor(montantBonConsommation));
								numAppelOffre = smcipnComponent.doSmcipnInterm(codeMembreAcheteur, codeTegcInterim,
										codeMembreVendeur, numeroBL, utilisateur.getIdUtilisateur(),
										Math.floor(montantBonConsommation));

								System.out.println("numAppelOffre= " + numAppelOffre);

								// echange du rouge en bleu Inr
								echanger = smcipnComponent.echangeNRNB(codeMembreAcheteur, numAppelOffre,
										calculBonInfo.getTypeProduit(), Math.floor(montantBonConsommation), prk);
								System.out.println("echange NRNB= " + echanger);
								if (echanger) {
									calculBonInfo = new CalculBonInfo();
									calculBonInfo.setCatBon("nr");
									calculBonInfo.setTypeRecurrent("");
									calculBonInfo.setTypeProduit(typeProduit);
									calculBonInfo.setPrk(0);
									calculBonInfo.setDuree(0);

									bonConso = transfertUtility.tansfertBC(codeMembreAcheteur, "TI", calculBonInfo, prk,
											Math.floor(montantBonConsommation));

									if (bonConso != null) {
										// faire la liste d'articles vendus
										ListArticlesVendus
												.add(new ArticleVendu("", codeMembreAcheteur, designationProduit,
														designationProduit, 1, Math.floor(montantBonConsommation)));

										System.out.println("codeTegcRevendeur=" + codeTegcRevendeur);
										String reponse2 = reglementAchat.saveReglementBySmcipn(compteVendeur,
												montantBon, bonConso.getBonNumero(), codeTegcRevendeur, typeR,
												codeTypeCredit, utilisateur.getIdUtilisateur(), ListArticlesVendus);
										System.out.println("reponse reglement smcipn : " + reponse2);
										if (reponse2.equals("OK")) {
											// retrouver la banque par defaut
											/*
											 * EuBanque banqueDefaut = banqueService.findByDefaut();
											 * if(banqueDefaut!=null){
											 */
											// emission d'opi
											/*
											 * String typeGcp, String codeMembre, String codeTegc, String modePaiement,
											 * double montant, Integer nbre, Integer serie, String typeOpi, double
											 * montTranche1, int differe,Date dateDebut, String numeroCompte, Integer
											 * reinjecter, Integer periodicite)
											 */
											// EuBon bonOpi =
											// opiCompo.emetreOpiBonCommande(codeMembreVendeur,codeTegcRevendeur,
											// banqueDefaut.getCodeBanque(),
											// Math.floor(montantBonConsommation),
											// 12, 1, "R", 0d, 0, new Date());

											EuBon bonOpi = opiCompo.emetreOpiBonCommande("SMCIPN", codeMembreVendeur,
													codeTegcRevendeur, modePayement, Math.floor(montantBonConsommation),
													12, 1, "R", 0d, 0, new Date(), referencePayement, reutiliser,
													periodeReutilisation, 1, 0, 0);

											if (bonOpi != null) {
												boolean creerOpi = opiCompo.creerOpi(bonOpi.getBonNumero());
												if (creerOpi) {
													// mise a jour de
													// smsconnexion
													smsComponent.miseAjourSmsConnexion(codeEnvoi);
													return Double.valueOf(0);

												}
												return Double.valueOf(9);// echec
																			// creation
																			// des
																			// opi

											}
										}
										// }

									}
								}
							}
							// Echec de la 1ere vente
							return Double.valueOf(1);
						}
						// Echec de souscription au bon de consommation
						return Double.valueOf(2);
					}
					// Echec de creation du bon de consommation
					return Double.valueOf(3);
				}
				// Echec de souscription au bon d achat
				return Double.valueOf(5);

			}
			// Le code du bon de consommation est invalide
			return Double.valueOf(7);
		} // return reponse = "KO5";// Revoir la saise des données
		return Double.valueOf(8);
	}

}
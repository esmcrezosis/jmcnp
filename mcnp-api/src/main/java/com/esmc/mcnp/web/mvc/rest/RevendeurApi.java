package com.esmc.mcnp.web.mvc.rest;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.esmc.mcnp.domain.dto.bc.CalculBonInfo;
import com.esmc.mcnp.domain.dto.obps.ArticleVendu;
import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.bc.EuTypeCredit;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.domain.entity.obpsd.EuBanque;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutre;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.infrastructure.components.BonAchatComponent;
import com.esmc.mcnp.infrastructure.components.CreditUtility;
import com.esmc.mcnp.infrastructure.components.OPIComponent;
import com.esmc.mcnp.infrastructure.components.ReglementAchat;
import com.esmc.mcnp.infrastructure.components.SmcipnComponent;
import com.esmc.mcnp.infrastructure.components.SouscriptionBon;
import com.esmc.mcnp.infrastructure.components.TransfertUtility;
import com.esmc.mcnp.commons.exception.business.CompteNonIntegreException;
import com.esmc.mcnp.commons.exception.business.CompteNonTrouveException;
import com.esmc.mcnp.commons.exception.business.SoldeInsuffisantException;
import com.esmc.mcnp.infrastructure.services.acteurs.EuBanqueService;
import com.esmc.mcnp.infrastructure.services.ba.EuBonNeutreService;
import com.esmc.mcnp.infrastructure.services.cm.EuCompteService;
import com.esmc.mcnp.infrastructure.services.common.EuTypeCreditService;
import com.esmc.mcnp.infrastructure.services.obps.EuTegcService;
import com.esmc.mcnp.infrastructure.services.oksu.EuRevendeurService;
import com.esmc.mcnp.web.mvc.dto.Reponse;
import com.esmc.mcnp.web.mvc.dto.Result;
import com.esmc.mcnp.web.mvc.dto.Revendeur;
import com.esmc.mcnp.web.mvc.utils.BaseRestController;
import com.google.common.collect.Lists;

@RestController
@RequestMapping(value = "/revendeur")
public class RevendeurApi extends BaseRestController {
	private OPIComponent opiCompo;
	private SouscriptionBon souscriptionBon;
	private TransfertUtility transfertUtility;
	private CreditUtility creditUtility;
	private ReglementAchat reglementAchat;
	private SmcipnComponent smcipnComponent;
	private EuTypeCreditService typeCreditService;
	private EuBonNeutreService bonNeutreService;
	private EuTegcService tegcService;
	private EuRevendeurService revendeurService;
	private EuBanqueService banqueService;
	private EuCompteService compteService;
	private BonAchatComponent bonAchatComponent;

	private static final String CODE_MEMBRE_PC = "0000000000000000019M";
	private static final String COMPTE_PC = "NR-TI-0000000000000000019M";

	public RevendeurApi(OPIComponent opiCompo, SouscriptionBon souscriptionBon, TransfertUtility transfertUtility,
			CreditUtility creditUtility, ReglementAchat reglementAchat, SmcipnComponent smcipnComponent,
			EuCompteService compteService, EuTypeCreditService typeCreditService, EuBonNeutreService bonNeutreService,
			EuTegcService tegcService, EuRevendeurService revendeurService, EuBanqueService banqueService,
			BonAchatComponent bonAchatComponent) {
		this.opiCompo = opiCompo;
		this.souscriptionBon = souscriptionBon;
		this.transfertUtility = transfertUtility;
		this.creditUtility = creditUtility;
		this.reglementAchat = reglementAchat;
		this.smcipnComponent = smcipnComponent;
		this.typeCreditService = typeCreditService;
		this.bonNeutreService = bonNeutreService;
		this.tegcService = tegcService;
		this.revendeurService = revendeurService;
		this.banqueService = banqueService;
		this.compteService = compteService;
		this.bonAchatComponent = bonAchatComponent;
	}

	// retrouver liste moyens de paiement
	@RequestMapping(value = "/listePayement", method = RequestMethod.GET)
	public ResponseEntity<Iterable<EuBanque>> findListModePayement() {
		return new ResponseEntity<>(banqueService.list(), HttpStatus.OK);
	}

	@RequestMapping(value = "/revproduit", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Reponse>> retrouverProduit() {
		List<String> rvs = revendeurService.getListeProduitsRevendeur();
		List<Reponse> reponses = Lists.newArrayList();
		if (!rvs.isEmpty()) {
			rvs.forEach(r -> {
				reponses.add(new Reponse(r));
			});
		}
		return new ResponseEntity<>(reponses, HttpStatus.OK);
	}

	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/souscrire", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Result> executerOperationRevendeurParBan(@RequestBody Revendeur revendeur)
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
		String codeMembreVendeur = "";
		String numeroBL;
		String numAppelOffre = "";
		String nomProduit = "";
		String typeProduit = "";
		String codeTypeCredit = "CNPGRE";
		Double prk = 0d;
		String codeMembreInterim = "";
		String codeTegcInterim = "";

		String codeMembreAcheteur = revendeur.getCodeMembreRevendeur();
		Integer periodeReutilisation = revendeur.getNbreInjection();
		Integer reutiliser = revendeur.getReinjecter();
		Double montantBon = revendeur.getMontantBon();
		String codeTegcRevendeur = revendeur.getCodeTegc();
		String designationProduit = revendeur.getDesignationProduit();
		String referencePayement = revendeur.getReferencePayement();
		String modePayement = revendeur.getModePaiement();
		String typeBon = revendeur.getTypeBon();
		// String codeEnvoi = revendeur.getCodeEnvoi();
		// String codeConfirmation = revendeur.getCodeConfirmation();
		List<ArticleVendu> ListArticlesVendus = Lists.newArrayList();

		try {
			EuTypeCredit euTypeCredit = typeCreditService.findById(codeTypeCredit);
			if (euTypeCredit != null) {
				typeProduit = euTypeCredit.getTypeProduit();
				prk = euTypeCredit.getPrk();
			}
			montantBonConsommation = creditUtility.calculBonConso(
					new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk),
					Math.floor(revendeur.getMontantBon()));
			EuCompte compteDeb = compteService.getById(COMPTE_PC);
			if (compteDeb.getSolde() >= montantBonConsommation) {
				EuUtilisateur user = getCurrentUser();
				// retrouver le tegc du membre morale vendeur du TE REVENDEUR
				EuTegc euTegc = tegcService.findTegcByCodeMembreAndNomTegc("0010010010010000003M",
						"TE ACHETEUR-REVENDEUR");
				if (euTegc != null) {
					codeMembreInterim = euTegc.getEuMembreMorale().getCodeMembreMorale();
					codeTegcInterim = euTegc.getCodeTegc();
					nomProduit = euTegc.getNomProduit();
				} else {
					return new ResponseEntity<>(new Result(0, "TE Non trouvé"), HttpStatus.INTERNAL_SERVER_ERROR);
				}

				if (StringUtils.isNotBlank(codeMembreInterim) && StringUtils.isNotBlank(codeMembreAcheteur)
						&& StringUtils.isNotBlank(typeR) && StringUtils.isNotBlank(typeBon) && !Double.isNaN(montantBon)
						&& StringUtils.isNotBlank(codeTegcRevendeur) /*
																		 * && StringUtils.isNotBlank(codeEnvoi) &&
																		 * StringUtils.isNotBlank(codeConfirmation)
																		 */) {

					// if (smsComponent.verifyCodeSms(codeEnvoi, codeConfirmation,
					// codeMembreAcheteur)) {

					String codeMembreRevendeur = null;
					EuTegc tegc = tegcService.findByCodeTegc(codeTegcRevendeur);
					if (Objects.nonNull(tegc)) {
						if (Objects.nonNull(tegc.getEuMembre())) {
							codeMembreRevendeur = tegc.getEuMembre().getCodeMembre();
						} else {
							codeMembreRevendeur = tegc.getEuMembreMorale().getCodeMembreMorale();
						}
					} else {
						return new ResponseEntity<>(new Result(0, "TE Acheteur Non retrouvé"),
								HttpStatus.INTERNAL_SERVER_ERROR);
					}

					// 1ere operation
					codeMembreAcheteur = codeMembreRevendeur;
					codeMembreVendeur = codeMembreInterim;

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
							bonAchat = transfertUtility.transfertBA(codeMembreAcheteur, "BAN", "",
									Math.floor(montantBon));
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

						if (boncreer == 0) {

							bonConsommation = transfertUtility.tansfertBC(codeMembreAcheteur, typeBC, calculBonInfo,
									prk, Math.floor(montantBonConsommation));

							if (bonConsommation != null) {

								// faire la liste d'articles vendus
								ListArticlesVendus.add(new ArticleVendu(codeTegcInterim, "", codeMembreAcheteur,
										designationProduit, designationProduit, 1, Math.floor(montantBonConsommation)));

								// payement au tegc de l'interim et renvoie de
								// son numero de bon de livraison
								numeroBL = reglementAchat.saveReglementSimpleParBon("", 0, codeTegcInterim,
										compteVendeur, bonConsommation.getBonNumero(), typeR, typeProduit,
										codeTypeCredit, nomProduit, user.getIdUtilisateur(), ListArticlesVendus);

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

									// echange du rouge en bleu Inr
									echanger = smcipnComponent.echangeSmcipn(CODE_MEMBRE_PC, "TPN", "TI",
											Math.floor(montantBonConsommation), 8.0);

									if (echanger) {
										calculBonInfo = new CalculBonInfo();
										calculBonInfo.setCatBon("nr");
										calculBonInfo.setTypeRecurrent("");
										calculBonInfo.setTypeProduit("PS");
										calculBonInfo.setPrk(0);
										calculBonInfo.setDuree(0);

										bonConso = transfertUtility.tansfertBC(CODE_MEMBRE_PC, "TI", calculBonInfo, 8.0,
												Math.floor(montantBonConsommation));

										if (bonConso != null) {
											// faire la liste d'articles vendus
											ListArticlesVendus.add(new ArticleVendu(codeTegcRevendeur, "",
													codeMembreAcheteur, designationProduit, designationProduit, 1,
													Math.floor(montantBonConsommation)));

											String reponse2 = reglementAchat.saveReglementBySmcipn(compteVendeur,
													montantBon, bonConso.getBonNumero(), codeTegcRevendeur, typeR,
													codeTypeCredit, user.getIdUtilisateur(), ListArticlesVendus);
											if (reponse2.equals("OK")) {
												EuBon bonOpi = opiCompo.emetreOpiBonCommande("SMCIPN",
														codeMembreVendeur, codeTegcRevendeur, modePayement,
														Math.floor(montantBonConsommation), 12, 1, "R", 0d, 0,
														new Date(), referencePayement, reutiliser, periodeReutilisation,
														1, 0, 0);

												if (Objects.nonNull(bonOpi)) {
													if (opiCompo.creerOpi(bonOpi.getBonNumero())) {
														return new ResponseEntity<>(
																new Result(1,
																		"L'opération a été effectuée avec succès"),
																HttpStatus.CREATED);
													}
												}
											}

										}
									}
								}
							}
						}
					}

					/*
					 * } return new ResponseEntity<>(new Result(0,
					 * "Echec de l'opération : Code de confirmation invalide!!!"),
					 * HttpStatus.INTERNAL_SERVER_ERROR);
					 */
				}
				return new ResponseEntity<>(
						new Result(0, "Echec de l'opération : Veuillez vérifier les infos envoyées!!!"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				return new ResponseEntity<>(new Result(0, "Solde de PC Insuffisant pour effectuer cette opération"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			getLog().error("Erreur d'éxécution : ", e);
			return new ResponseEntity<>(new Result(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}

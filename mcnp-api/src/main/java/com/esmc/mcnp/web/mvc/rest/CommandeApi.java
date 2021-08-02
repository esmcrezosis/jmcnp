package com.esmc.mcnp.web.mvc.rest;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.ListUtils;
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
import com.esmc.mcnp.domain.entity.ba.EuCapa;
import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.obps.EuCommande;
import com.esmc.mcnp.domain.entity.obps.EuDetailCommande;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutre;
import com.esmc.mcnp.infrastructure.components.BonAchatComponent;
import com.esmc.mcnp.infrastructure.components.CreditUtility;
import com.esmc.mcnp.infrastructure.components.EchangeService;
import com.esmc.mcnp.infrastructure.components.QuotaUtility;
import com.esmc.mcnp.infrastructure.components.ReglementAchat;
import com.esmc.mcnp.infrastructure.components.SouscriptionBon;
import com.esmc.mcnp.infrastructure.components.TransfertUtility;
import com.esmc.mcnp.commons.exception.business.CompteNonIntegreException;
import com.esmc.mcnp.commons.exception.business.CompteNonTrouveException;
import com.esmc.mcnp.commons.exception.business.SoldeInsuffisantException;
import com.esmc.mcnp.infrastructure.services.ba.EuBonNeutreService;
import com.esmc.mcnp.infrastructure.services.ba.EuCapaService;
import com.esmc.mcnp.infrastructure.services.obps.EuCommandeService;
import com.esmc.mcnp.infrastructure.services.obps.EuDetailCommandeService;
import com.esmc.mcnp.infrastructure.services.obps.EuPrkService;
import com.esmc.mcnp.infrastructure.services.obps.EuTegcService;
import com.esmc.mcnp.web.mvc.dto.Commande;
import com.esmc.mcnp.web.mvc.dto.Result;
import com.google.common.collect.Lists;

@RestController
@RequestMapping(value = "/boutiqueEnLigne")
public class CommandeApi {
	
	private final SouscriptionBon souscriptionBon;
	private final BonAchatComponent bonAchatComponent;
	private final TransfertUtility transfertUtility;
	private final CreditUtility creditUtility;
	private final ReglementAchat reglementAchat;
	private final QuotaUtility quotaUtility;
	private final EuPrkService prkService;
	private final EuBonNeutreService bonNeutreService;
	private final EuTegcService tegcService;
	private final EuCommandeService commandeService;
	private final EuDetailCommandeService detaiCommandeService;
	private final EuCapaService capaService;
	private final EchangeService echangeService;

	public CommandeApi(SouscriptionBon souscriptionBon, TransfertUtility transfertUtility, CreditUtility creditUtility,
			ReglementAchat reglementAchat, QuotaUtility quotaUtility, EuPrkService prkService,
			EuBonNeutreService bonNeutreService, EuTegcService tegcService, EuCommandeService commandeService,
			EuDetailCommandeService detaiCommandeService, EuCapaService capaService, EchangeService echangeService, BonAchatComponent bonAchatComponent) {
		this.souscriptionBon = souscriptionBon;
		this.bonAchatComponent = bonAchatComponent;
		this.transfertUtility = transfertUtility;
		this.creditUtility = creditUtility;
		this.reglementAchat = reglementAchat;
		this.quotaUtility = quotaUtility;
		this.prkService = prkService;
		this.bonNeutreService = bonNeutreService;
		this.tegcService = tegcService;
		this.commandeService = commandeService;
		this.detaiCommandeService = detaiCommandeService;
		this.capaService = capaService;
		this.echangeService = echangeService;
	}

	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/executerVenteBoutikEnLigne", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Result> executerVenteParDefaut(@RequestBody Commande commande)
			throws CompteNonIntegreException, SoldeInsuffisantException, DataAccessException, NullPointerException,
			CompteNonTrouveException {
		boolean echange = false;
		String codeMembreAcheteur;
		String codeMembreVendeur;
		String typeR;
		String perioder;
		Integer bps;
		Integer frequence;
		String typeProduit = "PS";
		Double prk = 0d;
		Boolean result = false;
		CalculBonInfo calculBonInfo = new CalculBonInfo();
		/*
		 * Double soldeCredit = 0d; Double soldeCapa = 0d;
		 */
		EuBon bonConsommation = null;
		EuBon bonAchat = null;
		EuBonNeutre bonNeutre = null;
		Integer boncreer = 0;
		String reponse = "KO";
		String typeRecurrent = "";
		Double duree = 0d;
		Integer subvention = 0;
		String nomProduit = "";
		String typeBC = null;
		String compteVendeur;
		String codeTypeCredit;
		String codeConfirmation = commande.getCodeConfirmation();
		Long codeCommande = commande.getCodeCommande();
		String codeTegcVendeur = commande.getCodeTegcVendeur();
		String typeBon = commande.getTypeBon();
		Long idUtilisateur = commande.getIdUtilisateur();
		Double montantAchat = commande.getMontantAchat();
		List<ArticleVendu> ListArticlesVendus = Lists.newArrayList();
		Boolean verifyQuota = false;

		if (StringUtils.isNotBlank(codeTegcVendeur) && StringUtils.isNotBlank(codeConfirmation)
				&& Objects.nonNull(codeCommande) && StringUtils.isNotBlank(typeBon)) {

			// trouver la commande
			EuCommande commandeEnCours = commandeService.findById(codeCommande);
			if (commandeEnCours != null) {
				// retrouver le tegc du membre morale vendeur

				EuTegc euTegc = tegcService.getById(codeTegcVendeur);
				if (euTegc == null) {
					return new ResponseEntity<>(new Result(0, "TE Non trouvé"), HttpStatus.INTERNAL_SERVER_ERROR);
				}

				// verifier le code de confirmation de la commande
				if (commandeEnCours.getCodeConfirmation().equals(codeConfirmation)) {
					// verifions le quota

					codeMembreAcheteur = commandeEnCours.getCodeMembreAcheteur();
					codeMembreVendeur = commandeEnCours.getCodeMembreVendeur();
					typeR = commandeEnCours.getTypeRecurrent();
					perioder = commandeEnCours.getPeriodeRecurrent();
					bps = commandeEnCours.getBps();
					frequence = commandeEnCours.getFrequence();

					// retrouver la liste des articles

					List<EuDetailCommande> ListDetailsCommande = ListUtils
							.emptyIfNull(detaiCommandeService.findDetailsCommandeByCodeCommande(codeCommande));

					if (!ListDetailsCommande.isEmpty()) {
						ListDetailsCommande.forEach((euDetailCommande) -> {
							ArticleVendu articleVendu = new ArticleVendu();
							articleVendu.setCodeBarre(euDetailCommande.getCodeBarre());
							articleVendu.setCodeMembreAcheteur(codeMembreAcheteur);
							articleVendu.setCodeTegc(codeTegcVendeur);
							articleVendu.setDesignation(euDetailCommande.getDesignation());
							articleVendu.setPrix(euDetailCommande.getPrixUnitaire());
							articleVendu.setQuantite(euDetailCommande.getQte());
							articleVendu.setReference(euDetailCommande.getReference());
							ListArticlesVendus.add(articleVendu);
						});
					} else {
						return new ResponseEntity<>(new Result(0, "Pas de liste d'article pour cette commande"),
								HttpStatus.INTERNAL_SERVER_ERROR);
					}
					// retrouver le compte gcp du vendeur
					compteVendeur = "NB-TPAGCP-" + codeMembreVendeur;
					if (codeMembreAcheteur.endsWith("P")) {
						typeBC = "RPG";
					} else {
						typeBC = "I";
					}

					if (euTegc != null) {
						nomProduit = euTegc.getNomProduit();
						subvention = euTegc.getSubvention();
					}
					if (perioder.equals("illimite")) {
						duree = 0d;
						typeRecurrent = "illimite";
					} else if ((perioder.equals("limite 11.2"))) {
						duree = 11.2;
						typeRecurrent = "limite";
					} else if (perioder.equals("limite 22.4")) {
						duree = 22.4;
						typeRecurrent = "limite";
					} else if (perioder.equals("limite 1")) {
						duree = 1d;
						typeRecurrent = "limite";
					}
					String typeBa = "RPG";
					if (codeMembreAcheteur.endsWith("M")) {
						typeBa = "I";
					}
					if (typeR.equals("nr")) {
						prk = prkService.findPrkByTegc(codeTegcVendeur).getValeur();
						codeTypeCredit = prkService.findPrkByTegc(codeTegcVendeur).getEuTypeCredit()
								.getCodeTypeCredit();
						calculBonInfo.setCatBon(typeR);
						calculBonInfo.setTypeRecurrent(typeRecurrent);
						calculBonInfo.setTypeProduit(typeProduit);
						calculBonInfo.setPrk(prk);
						calculBonInfo.setDuree(duree);

					} else {
						codeTypeCredit = prkService.findPrkByTegc(codeTegcVendeur).getEuTypeCredit()
								.getCodeTypeCredit();
						calculBonInfo.setCatBon(typeR);
						calculBonInfo.setTypeRecurrent(typeRecurrent);
						calculBonInfo.setTypeProduit(typeProduit);
						calculBonInfo.setPrk(0d);
						calculBonInfo.setDuree(duree);
					}

					if (typeBon.equals("BAN")) {
						bonNeutre = bonNeutreService.findByMembre(codeMembreAcheteur);

						if (bonNeutre != null) {
							if (bonNeutre.getBonNeutreMontantSolde() >= montantAchat) {

								result = bonAchatComponent.souscrireBonAchat(codeMembreAcheteur,
										bonNeutre.getBonNeutreCode(), typeBa, montantAchat);
								if (result) {
									bonAchat = transfertUtility.transfertBA(codeMembreAcheteur, "BAN", "",
											montantAchat);
									if (bonAchat != null) {
										boncreer = souscriptionBon.souscrireBonConso(codeMembreAcheteur, calculBonInfo,
												bonAchat.getBonNumero(), bonAchat.getBonMontant());
										if (boncreer == 0) {
											Double montantBonConsommation = creditUtility
													.calculBonConso(
															new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree,
																	prk, frequence, bps),
															Math.floor(bonAchat.getBonMontant()));

											verifyQuota = quotaUtility.verifyQuota(codeTypeCredit, codeMembreAcheteur,
													montantBonConsommation, typeR);

											if (verifyQuota) {
												bonConsommation = transfertUtility.tansfertBC(codeMembreAcheteur,
														typeBC, calculBonInfo, prk, Math.floor(montantBonConsommation));

												if (bonConsommation != null) {
													// rechercher si le vendeur est beneficiaire d'une subvention
													if (subvention == 1) {
														// payement au gcsc

														reponse = reglementAchat.saveReglementParBonAuGcs(
																codeTegcVendeur, compteVendeur,
																bonConsommation.getBonNumero(), typeR, typeProduit,
																codeTypeCredit, nomProduit, idUtilisateur,
																ListArticlesVendus);
													} else {
														// payement au tegc
														reponse = reglementAchat.saveReglementSimpleParBon("", 0,
																codeTegcVendeur, compteVendeur,
																bonConsommation.getBonNumero(), typeR, typeProduit,
																codeTypeCredit, nomProduit, idUtilisateur,
																ListArticlesVendus);
													}

													if (StringUtils.isNotBlank(reponse) && reponse.startsWith("BL")) {
														return new ResponseEntity<>(
																new Result(1,
																		"L'opération a été effectuée avec succès"),
																HttpStatus.CREATED);
													}
													return new ResponseEntity<>(new Result(0, "Echec de l'opération"),
															HttpStatus.INTERNAL_SERVER_ERROR);

												}
											}
											return new ResponseEntity<>(
													new Result(0,
															"Echec de l'opération : Le quota autorisé est dépassé"),
													HttpStatus.INTERNAL_SERVER_ERROR);

										}

									}
									return new ResponseEntity<>(
											new Result(0, "Echec de l'opération : Impossible de creer le BA"),
											HttpStatus.INTERNAL_SERVER_ERROR);
								}

							}
							return new ResponseEntity<>(
									new Result(0, "Echec de l'opération : montant du BAn est insuffisant"),
									HttpStatus.INTERNAL_SERVER_ERROR);

						}
						return new ResponseEntity<>(new Result(0, "Echec de l'opération : BAn introuvable"),
								HttpStatus.INTERNAL_SERVER_ERROR);

					} else if (typeBon.equals("BA")) {

						// Double montantBonAchat = creditUtility.calculMsbc(new CalculBonInfo(typeR,
						// typeRecurrent, typeProduit, duree, prk, frequence, bps), montantAchat);

						// retrouver le montant du BA dans euCapa
						List<EuCapa> capas = Lists.newArrayList();
						capas = ListUtils.emptyIfNull(
								capaService.findbyMembreAndProduitAndOrigine(codeMembreAcheteur, " ", " "));

						if (!capas.isEmpty()) {
							Double soldeCapa = capas.stream().map(c -> c.getMontantSolde()).reduce(Double::sum).get();
							if (soldeCapa >= Math.floor(montantAchat)) {

								bonAchat = transfertUtility.transfertBA(codeMembreAcheteur, "", "",
										Math.floor(montantAchat));
								System.out.println("bonAchat= " + bonAchat.getBonNumero());

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

										Double montantBonConsommation = creditUtility.calculBonConso(
												new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk,
														frequence, bps),
												Math.floor(bonAchat.getBonMontant()));
										System.out.println(
												"montantBonConsommation = " + Math.floor(montantBonConsommation));

										bonConsommation = transfertUtility.tansfertBC(codeMembreAcheteur, typeBC,
												calculBonInfo, prk, Math.floor(montantBonConsommation));

										if (bonConsommation != null) {
											// rechercher si le vendeur est beneficiaire d'une subvention
											if (subvention == 1) {
												// payement au gcsc
												reponse = reglementAchat.saveReglementParBonAuGcs(codeTegcVendeur,
														compteVendeur, bonConsommation.getBonNumero(), "", typeProduit,
														codeTypeCredit, nomProduit, idUtilisateur, ListArticlesVendus);
											} else {
												// payement au tegc
												reponse = reglementAchat.saveReglementSimpleParBon("", 0,
														codeTegcVendeur, compteVendeur, bonConsommation.getBonNumero(),
														"", typeProduit, codeTypeCredit, nomProduit, idUtilisateur,
														ListArticlesVendus);
											}

											if (StringUtils.isNotBlank(reponse) && reponse.startsWith("BL")) {
												return new ResponseEntity<>(
														new Result(1, "L'opération a été effectuée avec succès"),
														HttpStatus.CREATED);
											}
											return new ResponseEntity<>(new Result(0, "Echec de l'opération"),
													HttpStatus.INTERNAL_SERVER_ERROR);

										}

									}
								}
							}
						}

					} else if (typeBon.equals("BC")) {

					} else if (typeBon.equals("BL")) {
						// retrouver le montant du gcp et comparer avec le montant Achat

						String codeTegcAcheteur = commande.getCodeTegcAcheteur();
						if (StringUtils.isBlank(codeTegcAcheteur)) {
							return new ResponseEntity<>(new Result(0, "L acheteur n a pas de TE"),
									HttpStatus.INTERNAL_SERVER_ERROR);
						} else {

							Double montantGcp = tegcService.getSoldeByMembreAndTe(codeMembreAcheteur, codeTegcAcheteur);
							Double montantBonCommande = creditUtility.calculBonConso(
									new CalculBonInfo(typeR, typeRecurrent, typeProduit, duree, prk, frequence, bps),
									Math.floor(montantAchat));

							if (montantGcp >= montantBonCommande) {

								verifyQuota = quotaUtility.verifyQuota(codeTypeCredit, codeMembreAcheteur,
										montantBonCommande, typeR);

								if (verifyQuota) {

									// echange de gcp en Inr ou RPGnr
									echange = echangeService.echangeGCP(codeMembreAcheteur, codeTegcAcheteur, "GCP",
											prk, montantBonCommande, typeProduit);
									System.out.println("echange= " + echange);
									if (echange) {
										bonConsommation = transfertUtility.tansfertBC(codeMembreAcheteur, typeBC,
												calculBonInfo, prk, montantBonCommande);

										if (bonConsommation != null) {

											if (subvention == 1) {
												// payement au gcsc
												reponse = reglementAchat.saveReglementParBonAuGcs(codeTegcVendeur,
														compteVendeur, bonConsommation.getBonNumero(), typeR,
														typeProduit, codeTypeCredit, nomProduit, idUtilisateur,
														ListArticlesVendus);
											} else {
												reponse = reglementAchat.saveReglementSimpleParBon("", 0,
														codeTegcVendeur, compteVendeur, bonConsommation.getBonNumero(),
														typeR, typeProduit, codeTypeCredit, nomProduit, idUtilisateur,
														ListArticlesVendus);
											}
											if (StringUtils.isNotBlank(reponse) && reponse.startsWith("BL")) {
												return new ResponseEntity<>(
														new Result(1, "L'opération a été effectuée avec succès"),
														HttpStatus.CREATED);
											}
											return new ResponseEntity<>(new Result(0, "Echec de l'opération"),
													HttpStatus.INTERNAL_SERVER_ERROR);

										}
									}

								}
								return new ResponseEntity<>(new Result(0, "Echec de l'opération: le quota est depassé"),
										HttpStatus.INTERNAL_SERVER_ERROR);
							}
							return new ResponseEntity<>(
									new Result(0, "Echec de l'opération: le montant des BL est insuffisant"),
									HttpStatus.INTERNAL_SERVER_ERROR);
						}
					}

				} // confirmation erronée
				return new ResponseEntity<>(new Result(0, "Echec de l'opération : Code de confirmation erroné!!"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			// commande nulle
			return new ResponseEntity<>(new Result(0, "Echec de l'opération : Commande non trouvée!!"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} // Données envoyées incorrectes
		return new ResponseEntity<>(new Result(0, "Echec de l'opération : Revoir la saisie des données"),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

}

package com.esmc.mcnp.components.auto;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.domain.dto.bc.CalculBonInfo;
import com.esmc.mcnp.domain.entity.acteur.EuContratLivraisonIrrevocable;
import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.domain.entity.pc.EuCharge;
import com.esmc.mcnp.domain.entity.pc.EuChargePaye;
import com.esmc.mcnp.domain.entity.pc.EuDemandePaiement;
import com.esmc.mcnp.domain.entity.pc.EuDetailPaiement;
import com.esmc.mcnp.domain.entity.pc.EuPaiement;
import com.esmc.mcnp.domain.entity.smcipn.EuSmcipnpwi;
import com.esmc.mcnp.infrastructure.components.Payement;
import com.esmc.mcnp.infrastructure.components.SmcipnComponent;
import com.esmc.mcnp.infrastructure.components.TransfertUtility;
import com.esmc.mcnp.infrastructure.services.acteurs.EuContratLivraisonIrrevocableService;
import com.esmc.mcnp.infrastructure.services.acteurs.EuDetailContratLivraisonIrrevocableService;
import com.esmc.mcnp.infrastructure.services.obps.EuTegcService;
import com.esmc.mcnp.infrastructure.services.pc.EuChargePayeService;
import com.esmc.mcnp.infrastructure.services.pc.EuChargeService;
import com.esmc.mcnp.infrastructure.services.pc.EuDemandePaiementService;
import com.esmc.mcnp.infrastructure.services.pc.EuDetailPaiementService;
import com.esmc.mcnp.infrastructure.services.pc.EuPaiementService;
import com.esmc.mcnp.infrastructure.services.smcipn.EuSmcipnpwiService;
import com.google.common.collect.Lists;

@Service
@Transactional
public class CommissionService {
	private @Autowired SmcipnComponent smcipnComp;
	private @Autowired EuDemandePaiementService demPaieService;
	private @Autowired TransfertUtility transfertUtility;
	private @Autowired Payement payement;
	private @Autowired EuTegcService tegcService;
	private @Autowired EuContratLivraisonIrrevocableService contratLivraisonIrrevocableService;
	private @Autowired EuDetailContratLivraisonIrrevocableService detailContratService;
	private @Autowired EuDetailPaiementService detailPaiementService;
	private @Autowired EuPaiementService paiementService;
	private @Autowired EuChargeService chargeService;
	private @Autowired EuChargePayeService chargePayeService;
	private @Autowired EuSmcipnpwiService smcipnService;
	// private @Autowired EuCompteBancaireService compteBancaireService;

	private final Logger logger = LogManager.getLogger(CommissionService.class);

	@Async
	@Scheduled(cron = "0 0 0 * * ?")
	// @Scheduled(fixedRate = 56000000)
	@Transactional(propagation = Propagation.REQUIRED)
	public void payerCommission() {
		List<EuDemandePaiement> demandes = demPaieService.findDemandeNonPayer(0, 300);
		if (!demandes.isEmpty()) {
			for (EuDemandePaiement d : demandes) {
				try {
					String typeProduit = "PO";
					String codeMembreSmc = "0010010010010000212M";
					List<EuPaiement> paiements = Lists.newArrayList();
					EuContratLivraisonIrrevocable contrat = null;
					List<EuDetailPaiement> detPaiements = detailPaiementService
							.findByIdDemandePaiement(d.getIdDemandePaiement());
					if (!detPaiements.isEmpty()) {
						EuDetailPaiement detPaiement = detPaiements.get(0);
						double somme = 0;
						paiements = paiementService.findPaiementByDemande(d.getIdDemandePaiement());
						if (d.getTypeDemande().equals("AVPC")) {
							contrat = contratLivraisonIrrevocableService.findById(detPaiement.getIdTable());
							somme = detailContratService.getSumMontant(detPaiement.getIdTable());
						} else {
							if (!paiements.isEmpty()) {
								somme = paiementService.getSommeByDemande(d.getIdDemandePaiement());
							}
						}
						if ((Objects.nonNull(contrat) || !paiements.isEmpty())
								&& d.getMontantDemandePaiement() >= somme) {

							// Verification du TE du demandeur
							List<EuTegc> tegcs = Collections.emptyList();
							if (codeMembreSmc.endsWith("P")) {
								tegcs = tegcService.findByCodeMembrePhysique(codeMembreSmc);
							} else {
								tegcs = tegcService.findByCodeMembre(codeMembreSmc);
							}
							if (!tegcs.isEmpty()) {
								logger.debug("Paiement de la demande N° " + d.getIdDemandePaiement());
								EuTegc tegc = tegcs.get(0);
								boolean all_paye = true;
								for (EuPaiement p : paiements) {
									try {
										logger.debug("Paiement N° " + p.getIdPaiement());
										List<EuTegc> tes = tegcService.findTebyMembre(p.getCodeMembreEmploye());
										if (!tes.isEmpty()) {
											EuTegc te_com = tes.get(0);
											String numAppelOffre = smcipnComp.doSmcipnComm(codeMembreSmc,
													p.getCodeMembreEmploye(), tegc.getCodeTegc(), d.getTypeDemande(),
													p.getMontantPaiement());
											if (StringUtils.isNotBlank(numAppelOffre)) {
												if (smcipnComp.echangeNRNB(codeMembreSmc, numAppelOffre, typeProduit,
														p.getMontantPaiement(), 8.0)) {
													EuBon bl = null;
													if (Objects.nonNull(contrat)) {
														bl = payement.createBonLivraison(contrat.getCodeMembre(),
																codeMembreSmc, d.getTypeDemande(), "",
																contrat.getIdContrat(), p.getMontantPaiement());
													} else {
														bl = payement.createBonLivraison(p.getCodeMembreEmploye(),
																codeMembreSmc, "COM", d.getTypeDemande(), null,
																p.getMontantPaiement());
													}
													if (Objects.nonNull(bl)) {
														EuCharge tcharge = chargeService.findByCode("CE");
														EuSmcipnpwi smcipn = smcipnService
																.findByNumeroAppel(numAppelOffre);
														EuChargePaye chargePaye = new EuChargePaye();
														chargePaye.setEuSmcipnpwi(smcipn);
														chargePaye.setEuCharge(tcharge);
														chargePaye.setNumDoc(String.valueOf(p.getIdPaiement()));
														chargePaye.setTypeDoc(d.getNumeroDemandePaiement());
														chargePaye.setCodeMembreDebiteur(codeMembreSmc);
														chargePaye.setCodeMembreCreancier(p.getCodeMembreEmploye());
														chargePaye.setDateCharge(new Date());
														chargePaye.setLibelleCharge(
																"Paiement de Commission de la demande N° "
																		+ d.getIdDemandePaiement());
														chargePaye.setMontantCharge(p.getMontantPaiement());
														chargePaye.setOrigineCharge("ESMC");
														chargePayeService.add(chargePaye);

														CalculBonInfo bonInfo = new CalculBonInfo();
														bonInfo.setCatBon("nr");
														bonInfo.setTypeProduit(typeProduit);
														EuBon bon = transfertUtility.tansfertBC(codeMembreSmc, "TI",
																bonInfo, 8.0, p.getMontantPaiement());
														if (Objects.nonNull(bon)) {
															String typeOp = "";
															if (Objects.nonNull(contrat)) {
																typeOp = "AVPC";
															} else {
																if (d.getTypeDemande().equalsIgnoreCase("BAn")
																		|| d.getTypeDemande()
																				.equalsIgnoreCase("Activation")) {
																	typeOp = "COM";
																} else {
																	typeOp = "PS";
																}
															}
															payement.makeTransaction(p.getCodeMembreEmploye(),
																	codeMembreSmc, "TI", te_com, bon, bl, typeOp,
																	p.getMontantPaiement());
															payement.creerMarge(new Date(), p.getMontantPaiement());

															p.setPayer(1);
															paiementService.update(p);
														} else {
															throw new RuntimeException(
																	"Erreur d'execution - Commission Service : Le transfert du BC de ce membre "
																			+ p.getCodeMembreEmploye()
																			+ " n'a pas pu effectuer");
														}
													} else {
														throw new RuntimeException(
																"Erreur d'execution - Commission Service : Le BL de de membre "
																		+ p.getCodeMembreEmploye()
																		+ " n'a pas pu être créé");
													}
												} else {
													throw new RuntimeException(
															"Erreur d'execution - Commission Service : L'echange de NR du membre "
																	+ codeMembreSmc + " n'a pas réussie");
												}
											} else {
												throw new RuntimeException(
														"Erreur d'execution - Commission Service : Le SMCIPN de ce membre "
																+ codeMembreSmc + " n'a pas pu être octroyé");
											}
										} else {
											all_paye = false;
											logger.error("Commission Service === Erreur d'execution du Paiement N°"
													+ p.getIdPaiement() + " de la demande N°" + d.getIdDemandePaiement()
													+ " : Le membre N° " + p.getCodeMembreEmploye()
													+ " n'a pas de TE !!!");
										}
										logger.debug("End Paiement N° " + p.getIdPaiement());
									} catch (Exception e) {
										all_paye = false;
										logger.error(
												"Erreur d'execution - Commission Service : Le paiementdu BC de ce membre "
														+ p.getCodeMembreEmploye() + " n'a pas pu effectuer ",
												e);
										// throw e;
									}
								}
								if (all_paye) {
									d.setPayer(1);
									demPaieService.update(d);
								}
							} else {
								throw new RuntimeException("Erreur d'execution - Commission Service : Ce membre "
										+ codeMembreSmc + " n'a pas de TE ou son solde est nul");
							}
						} else {
							throw new RuntimeException(
									"Erreur d'execution - Commission Service : Ce membre " + d.getCodeMembreEmployeur()
											+ " n'a pas de contrat ou son compte n'est pas valide");
						}
					} else {
						throw new RuntimeException("Erreur d'execution - Commission Service : Ce membre "
								+ d.getCodeMembreEmployeur() + " n'a pas de details de paiements");
					}
				} catch (Exception e) {
					logger.error("Erreur d'execution - Commission Service", e);
				}
				logger.debug("End du Paiement dela demande N° " + d.getIdDemandePaiement());
			}
		}

	}

}

package com.esmc.mcnp.components;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.exception.CompteNonIntegreException;
import com.esmc.mcnp.exception.CompteNonTrouveException;
import com.esmc.mcnp.core.utils.DateUtility;
import com.esmc.mcnp.core.utils.NextDayWeekendAdjuster;
import com.esmc.mcnp.exception.SoldeInsuffisantException;
import com.esmc.mcnp.model.acteur.EuActeur;
import com.esmc.mcnp.model.bc.EuCnp;
import com.esmc.mcnp.model.bc.EuCnpEntree;
import com.esmc.mcnp.model.bc.EuCreditEchange;
import com.esmc.mcnp.model.obpsd.EuDetailBonOpi;
import com.esmc.mcnp.model.acteur.EuDetailContratLivraisonIrrevocable;
import com.esmc.mcnp.model.acteur.EuDetailEli;
import com.esmc.mcnp.model.obpsd.EuEchange;
import com.esmc.mcnp.model.acteur.EuEli;
import com.esmc.mcnp.model.ba.EuNn;
import com.esmc.mcnp.model.others.EuOperation;
import com.esmc.mcnp.model.smcipn.EuSmc;
import com.esmc.mcnp.model.bc.EuBon;
/*import com.esmc.mcnp.model.pbf.EuBonNeutreUtilise;
import com.esmc.mcnp.model.acteur.EuCodeActivation;*/
import com.esmc.mcnp.model.cm.EuCategorieCompte;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteCreditTs;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.obps.EuArticleStockes;
import com.esmc.mcnp.model.obps.EuArticleStockesCategorie;
import com.esmc.mcnp.model.obps.EuArticlesVendu;
import com.esmc.mcnp.model.obps.EuCreditConsommer;
import com.esmc.mcnp.model.obps.EuGcp;
import com.esmc.mcnp.model.obps.EuTegc;
import com.esmc.mcnp.model.obpsd.EuBanque;
import com.esmc.mcnp.model.obpsd.EuDetailGcpPbf;
import com.esmc.mcnp.model.obpsd.EuGcpPbf;
import com.esmc.mcnp.model.obpsd.EuTpagcp;
import com.esmc.mcnp.model.obpsd.EuTraite;
import com.esmc.mcnp.model.obpsd.EuTypeNn;
import com.esmc.mcnp.repositories.obps.EuArticlesVenduRepository;
import com.esmc.mcnp.services.acteurs.EuActeurService;
import com.esmc.mcnp.services.acteurs.EuBanqueService;
import com.esmc.mcnp.services.acteurs.EuDetailContratLivraisonIrrevocableService;
import com.esmc.mcnp.services.acteurs.EuDetailEliService;
import com.esmc.mcnp.services.bc.EuBonService;
import com.esmc.mcnp.services.bc.EuCnpEntreeService;
import com.esmc.mcnp.services.bc.EuCnpService;
import com.esmc.mcnp.services.bc.EuCreditConsommerService;
import com.esmc.mcnp.services.bc.EuCreditEchangeService;
import com.esmc.mcnp.services.bc.EuDetailBonOpiService;
import com.esmc.mcnp.services.cm.EuCompteCreditTsService;
import com.esmc.mcnp.services.cm.EuCompteService;
import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.cm.EuMembreService;
import com.esmc.mcnp.services.common.EuOperationService;
import com.esmc.mcnp.services.obps.EuArticleStockeCategorieService;
import com.esmc.mcnp.services.obps.EuArticleStockeService;
import com.esmc.mcnp.services.obps.EuGcpService;
import com.esmc.mcnp.services.obps.EuTegcService;
import com.esmc.mcnp.services.obpsd.EuDetailGcpPbfService;
import com.esmc.mcnp.services.obpsd.EuEchangeService;
import com.esmc.mcnp.services.obpsd.EuGcpPbfService;
import com.esmc.mcnp.services.obpsd.EuNnService;
import com.esmc.mcnp.services.obpsd.EuTpagcpService;
import com.esmc.mcnp.services.obpsd.EuTraiteService;
import com.esmc.mcnp.services.setting.EuParametresService;
import com.esmc.mcnp.services.smcipn.EuSmcService;
import com.google.common.collect.Lists;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class Payement {

	private @Autowired EuCompteService compteService;
	private @Autowired EuCompteCreditTsService ccTsService;
	private @Autowired EuGcpPbfService gcpPbfService;
	private @Autowired EuDetailGcpPbfService detGcpPbfservice;
	private @Autowired EuEchangeService echangeService;
	private @Autowired EuMembreService membreService;
	private @Autowired EuMembreMoraleService moraleService;
	private @Autowired EuCreditEchangeService creditEchService;
	private @Autowired EuTegcService tegcService;
	private @Autowired EuCnpService cnpService;
	private @Autowired EuSmcService smcService;
	private @Autowired EuOperationService opService;
	private @Autowired EuCreditConsommerService ccConsService;
	private @Autowired EuCnpEntreeService cnpEntService;
	private @Autowired EuActeurService acteurService;
	private @Autowired EuParametresService paramService;
	private @Autowired EuBonService bonService;
	private @Autowired EuGcpService gcpService;
	private @Autowired EuNnService nnService;
	private @Autowired EuArticlesVenduRepository artVendRepo;
	private @Autowired EuDetailContratLivraisonIrrevocableService detailContratLivraisonIrrevocableService;
	private @Autowired EuDetailEliService detailEliService;
	private @Autowired EuArticleStockeService articleService;
	private @Autowired EuArticleStockeCategorieService articleCatService;
	/*
	 * private @Autowired EuCodeActivationService codeActivationService;
	 * private @Autowired EuBonNeutreUtiliseService bonNeutreUtiliseService;
	 */
	private @Autowired EuBanqueService bankService;
	private @Autowired EuDetailBonOpiService detBonOpiService;
	private @Autowired EuTraiteService traiteService;
	private @Autowired EuTpagcpService tpaService;

	private final Logger log = LogManager.getLogger(Payement.class.getName());

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public EuBon createBonLivraison(String codeMembreVendeur, String codeMembreAcheteur, String typePaiement,
			String typeCommission, Integer idContrat, double montant) {
		Date date = new Date();
		Optional<EuBon> opbon = bonService.emettreBon("BL", codeMembreVendeur, montant);
		if (opbon.isPresent()) {
			// Articles vendus concernant l acheteur
			if (typePaiement.equals("AVPC")) {
				List<EuDetailContratLivraisonIrrevocable> euDetailContratLivraisonIrrevocables = detailContratLivraisonIrrevocableService
						.findByContrat(idContrat);
				if (!euDetailContratLivraisonIrrevocables.isEmpty()) {
					euDetailContratLivraisonIrrevocables.forEach(d -> {
						/*
						 * Long idArticleVendu = artVendRepo.getLastEuArticleVenduInsertedId(); if
						 * (idArticleVendu == null) { idArticleVendu = 1L; } else { idArticleVendu++; }
						 */
						EuArticlesVendu article = new EuArticlesVendu();
						// article.setIdArticleVendu(idArticleVendu);
						article.setCodeBarre("");
						article.setCodeMembreAcheteur(codeMembreAcheteur);
						article.setDateVente(date);
						article.setDesignation(d.getLibelleProduit());
						article.setReference("AVPC");
						article.setQuantite(d.getQuantite());
						article.setPrixUnitaire(d.getPrixUnitaire());
						article.setCodeMembreVendeur(codeMembreVendeur);
						article.setEuBon(opbon.get());
						artVendRepo.save(article);
					});
				}
			} else if (typePaiement.equalsIgnoreCase("COM")) {
				EuArticlesVendu article = new EuArticlesVendu();
				/*
				 * Long idArticleVendu = artVendRepo.getLastEuArticleVenduInsertedId(); if
				 * (idArticleVendu == null) { idArticleVendu = 1L; } else { idArticleVendu++; }
				 * article.setIdArticleVendu(idArticleVendu);
				 */
				article.setCodeBarre("");
				article.setCodeMembreAcheteur(codeMembreAcheteur);
				article.setDateVente(date);
				if (typeCommission.equalsIgnoreCase("Activation")) {
					article.setDesignation("Prestation de Services : AC");
					article.setReference("PS-ACT");
				} else {
					article.setDesignation("Prestation de Services : Appro BAn");
					article.setReference("PS-APPRO");
				}
				article.setQuantite(1);
				article.setPrixUnitaire(montant);
				article.setCodeMembreVendeur(codeMembreVendeur);
				article.setEuBon(opbon.get());
				artVendRepo.save(article);
			} else {
				EuArticlesVendu article = new EuArticlesVendu();
				/*
				 * Long idArticleVendu = artVendRepo.getLastEuArticleVenduInsertedId(); if
				 * (idArticleVendu == null) { idArticleVendu = 1L; } else { idArticleVendu++; }
				 * article.setIdArticleVendu(idArticleVendu);
				 */
				article.setCodeBarre("");
				article.setCodeMembreAcheteur(codeMembreAcheteur);
				article.setDateVente(date);
				switch (typePaiement) {
				case "PS":
					article.setDesignation("Prestation de Services : Pointage");
					article.setReference("PS-PTG");
					break;
				case "BNP":
					article.setDesignation("Prestation de Services : BNP");
					article.setReference("PS-BNP");
					break;
				case "AR":
					article.setDesignation("Acheteur-Revendeur");
					article.setReference("PS-AR");
					break;
				case "ELI":
					article.setDesignation("Engagement de Livraison Irrévocable");
					article.setReference("ELI");
					break;
				default:
					article.setDesignation("Prestation de Services");
					article.setReference("PS-" + typePaiement);
					break;
				}
				article.setQuantite(1);
				article.setPrixUnitaire(montant);
				article.setCodeMembreVendeur(codeMembreVendeur);
				article.setEuBon(opbon.get());
				artVendRepo.save(article);
			}
			return opbon.get();
		}
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public EuBon createBonLivraison(String codeMembreVendeur, String codeMembreAcheteur, String typePaiement,
			double montant) {
		Date date = new Date();
		Optional<EuBon> opbon = bonService.emettreBon("BL", codeMembreVendeur, montant);
		if (opbon.isPresent()) {
			// Articles vendus concernant l acheteur
			/*
			 * Long idArticleVendu = artVendRepo.getLastEuArticleVenduInsertedId(); if
			 * (idArticleVendu == null) { idArticleVendu = 1L; } else { idArticleVendu++; }
			 */
			EuArticlesVendu article = new EuArticlesVendu();
			// article.setIdArticleVendu(idArticleVendu);
			article.setCodeBarre("");
			article.setCodeMembreAcheteur(codeMembreAcheteur);
			article.setDateVente(date);
			if (typePaiement.equals("VPC")) {
				article.setDesignation("Vente de la production commune");
				article.setReference(typePaiement);
			} else if (typePaiement.equals("PS")) {
				article.setDesignation("Prestation de Services");
				article.setReference("F" + typePaiement);
			} else {
				article.setDesignation("Achat de la production commune");
				article.setReference(typePaiement);
			}
			article.setQuantite(1);
			article.setPrixUnitaire(montant);
			article.setCodeMembreVendeur(codeMembreVendeur);
			article.setEuBon(opbon.get());
			artVendRepo.save(article);
			return opbon.get();
		}
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean creerMarge(Date date, double montant) {
		EuActeur acteur_surv = acteurService.findActeurByTypeAndActivite("gac_surveillance", "SOURCE");
		if (Objects.nonNull(acteur_surv)) {
			double txMarge = paramService.getParametre("NNMARGE", "valeur");
			EuCompte compteSurv = compteService.getById("NN-TMARGE-" + acteur_surv.getCodeMembre());
			if (txMarge > 0 && Objects.nonNull(compteSurv)) {
				double marge = montant * txMarge / 100;
				if (marge > 0) {

					EuMembreMorale m_surv = moraleService.findById(acteur_surv.getCodeMembre());
					// creation et utilisation de la source NN
					/*
					 * Long id = nnService.getLastInsertId(); if (id == null) { id = 0L; }
					 */
					EuTypeNn typeNn = new EuTypeNn();
					typeNn.setCodeTypeNn("NNMARGE");
					EuNn nn = new EuNn();
					nn.setDateEmission(new Date());
					// nn.setIdNn(id + 1);
					nn.setEuMembreMorale(m_surv);
					nn.setEuTypeNn(typeNn);
					nn.setMontantEmis(marge);
					nn.setMontantRemb(marge);
					nn.setSoldeNn(0.0);
					nn.setTypeEmission("Auto");
					nn.setIdUtilisateur(null);
					nnService.add(nn);

					// mise à jour du compte surveillance
					compteSurv.setSolde(compteSurv.getSolde() + marge);
					compteService.update(compteSurv);

					// euoperation
					/*
					 * Long idOp = opService.getLastOperation(); if (idOp == null) { idOp = 1L; }
					 * else { idOp++; }
					 */
					EuOperation op = new EuOperation();
					// op.setIdOperation(idOp);
					op.setDateOp(date);
					op.setHeureOp(date);
					op.setMontantOp(marge);
					op.setEuMembreMorale(m_surv);
					op.setEuUtilisateur(null);
					op.setCodeProduit("NN");
					op.setLibOp("CREATION DE FRAIS EXPLOITATION");
					op.setEuCategorieCompte(compteSurv.getEuCategorieCompte());
					op.setTypeOp("BL");
					opService.add(op);
				}
			}
		}
		return false;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void makeTransaction(String codeMembreTe, String codeMembrePayeur, String codeCat, EuTegc te, EuBon bc,
			EuBon bl, String typeOp, double montant) {
		if (Objects.isNull(te) || StringUtils.isBlank(codeMembreTe) || StringUtils.isBlank(codeMembrePayeur)
				|| Objects.isNull(bc) || Objects.isNull(bl)) {
			throw new IllegalArgumentException("Des informations manquent pour effectuer cette transaction!!!");
		}
		List<EuCompteCreditTs> cctss = ccTsService.findByEuBon_BonNumero(bc.getBonNumero());
		if (cctss.size() > 0) {
			String codeProduit = "RPGr";
			String codeCompteTe = "NB-TPAGCP-" + codeMembreTe;
			String codeComptePayeur = "NB-TSRPG-" + codeMembrePayeur;
			if ("TBC".equals(codeCat)) {
				codeComptePayeur = "NB-TSBC-" + codeMembrePayeur;
				codeProduit = "BCi";
			} else {
				if (codeMembrePayeur.endsWith("M")) {
					codeProduit = "Ir";
					if (codeCat.equals("TPAGCI")) {
						codeComptePayeur = "NB-TSGCI" + codeMembrePayeur;
					} else {
						codeComptePayeur = "NB-TSI-" + codeMembrePayeur;
					}
				}
			}
			EuCompte compteTe = compteService.getById(codeCompteTe);
			if (Objects.isNull(compteTe)) {
				compteTe = compteService.createOrUpdate("NB", "TPAGCP", codeMembreTe);
			}
			EuCompte comptePayeur = compteService.getById(codeComptePayeur);
			if (Objects.nonNull(compteTe) && Objects.nonNull(comptePayeur)) {
				Date date = new Date();

				EuCategorieCompte cat = new EuCategorieCompte(codeCat);
				// Long idOp = opService.getLastOperation() + 1;
				EuOperation op = new EuOperation();
				op.setEuCategorieCompte(cat);
				op.setCodeProduit(codeProduit);
				op.setDateOp(date);
				op.setHeureOp(date);
				if (StringUtils.isNotBlank(typeOp)) {
					op.setTypeOp(typeOp);
					if ("BNP".equalsIgnoreCase(typeOp)) {
						op.setLibOp("Opération de Payement de la PaR + PaNu");
					} else if ("COM".equalsIgnoreCase(typeOp)) {
						op.setLibOp("Opération de Payement de commission");
					} else if ("PS".equals(typeOp) || "DIST".equals(typeOp)) {
						op.setLibOp("Opération de Payement Prestation de Service");
					} else if ("ROPI".equals(typeOp)) {
						op.setLibOp("Reinjection OPI");
					} else if ("ELI".equalsIgnoreCase(typeOp)) {
						op.setLibOp("Opération d'Engagement de Livraison Irrévocable");
					} else {
						op.setLibOp("Opération de Payement Production commune");
					}
				} else {
					op.setTypeOp("PC");
					op.setLibOp("Paiement de la Production commune");
				}
				op.setMontantOp(montant);
				if (codeMembrePayeur.endsWith("P")) {
					op.setEuMembre(membreService.findById(codeComptePayeur));
					op.setEuMembreMorale(null);
				} else {
					op.setEuMembre(null);
					op.setEuMembreMorale(moraleService.findById(codeComptePayeur));
				}
				// op.setIdOperation(idOp);
				op.setEuUtilisateur(null);
				opService.add(op);

				te.setMontant(te.getMontant() + montant);
				te.setSoldeTegc(te.getSoldeTegc() + montant);
				tegcService.update(te);

				String typesmc = "CNCSnr";
				int c = 0;
				double mont_deduire = montant;
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
				while (mont_deduire > 0 && c < cctss.size()) {
					EuCompteCreditTs ccts = cctss.get(c);
					if (mont_deduire <= ccts.getMontant().doubleValue()) {

						EuSmc smc = new EuSmc();
						smc.setDateSmc(new Date());
						smc.setMontant(mont_deduire);
						smc.setMontantSolde(mont_deduire);
						smc.setEntree(0.0);
						smc.setSolde(0.0);
						smc.setSortie(0.0);
						smc.setOrigineSmc(0);
						smc.setSourceCredit(ccts.getCompteSource());
						smc.setTypeSmc(typesmc);
						smc.setEuCompteCredit(ccts.getEuCompteCredit());
						smcService.add(smc);

						// enregistrememnt euGCP vendeur

						EuGcp gcp = new EuGcp();
						gcp.setEuTegc(te);
						gcp.setCodeMembre(codeMembreTe);
						gcp.setDateConso(date);
						gcp.setReste(mont_deduire);
						gcp.setMontGcp(mont_deduire);
						gcp.setMontPreleve(0);
						gcp.setEuCompteCredit(ccts.getEuCompteCredit());
						gcp.setSource(ccts.getCompteSource());
						gcp.setEuCategorieCompte(compteTe.getEuCategorieCompte());
						gcp.setEuBon(bl);
						if ("BNP".equalsIgnoreCase(typeOp)) {
							gcp.setTypeGcp("BNP");
						} else if ("COM".equalsIgnoreCase(typeOp)) {
							gcp.setTypeGcp("COM");
						} else if ("PS".equals(typeOp) || "DIST".equals(typeOp)) {
							gcp.setTypeGcp("DIST");
						} else if ("ROPI".equals(typeOp)) {
							gcp.setTypeGcp("ROPI");
						} else if ("ELI".equalsIgnoreCase(typeOp)) {
							gcp.setTypeGcp(typeOp);
						} else {
							gcp.setTypeGcp("SMCIPN");
						}
						gcp = gcpService.create(gcp);

						// enregistrement des creditsconsommer avec les
						// credits
						EuCreditConsommer ccCons = new EuCreditConsommer();
						ccCons.setCodeCompte(codeComptePayeur);
						ccCons.setCodeMembre(codeMembrePayeur);
						ccCons.setCodeMembreMorale(null);
						ccCons.setDateConsommation(date);
						ccCons.setHeureConsommation(date);
						ccCons.setMontConsommation(mont_deduire);
						ccCons.setEuOperation(op);
						ccCons.setCodeMembreDist(codeMembreTe);
						ccCons.setEuCompteCredit(ccts.getEuCompteCredit());
						ccCons.setEuProduit(ccts.getEuCompteCredit().getEuProduit());
						ccCons.setEuBon(bc);
						ccConsService.add(ccCons);

						// annulation du CNP
						EuCnp cnp = cnpService.findBySourceCredit(ccts.getEuCompteCredit().getIdCredit(),
								ccts.getCompteSource());
						if (cnp != null) {
							cnp.setMontCredit(cnp.getMontCredit() + mont_deduire);
							cnp.setSoldeCnp(cnp.getSoldeCnp() - mont_deduire);
							cnpService.update(cnp);
						} else {
							throw new IllegalStateException(
									"Paiement Component : Certains crédits n'ont pas d'origine au CNP : "
											+ ccts.getEuCompteCredit().getIdCredit());
						}

						// ajout dans Eu_cnp_entree
						EuCnpEntree cnpEnt = new EuCnpEntree();
						cnpEnt.setDateEntree(new Date());
						cnpEnt.setEuCnp(cnp);
						cnpEnt.setMontCnpEntree(mont_deduire);
						cnpEnt.setTypeCnpEntree("GCP");
						cnpEntService.add(cnpEnt);

						// création de la source GCP AU CNP
						EuCnp new_cnp = new EuCnp();
						new_cnp.setDateCnp(new Date());
						new_cnp.setEuCapa(null);
						new_cnp.setEuDomiciliation(null);
						new_cnp.setTypeCnp("RPGnr");
						new_cnp.setSourceCredit(codeMembreTe + formatter.format(date));
						new_cnp.setOrigineCnp("FGRPGnr");
						new_cnp.setMontDebit(mont_deduire);
						new_cnp.setMontCredit(0);
						new_cnp.setSoldeCnp(mont_deduire);
						new_cnp.setTransfertGcp(0);
						new_cnp.setEuGcp(gcp);
						new_cnp.setEuCompteCredit(null);
						cnpService.add(new_cnp);
						// Mise à jour des eucomptecredit

						ccts.setMontant(ccts.getMontant() - mont_deduire);
						ccts = ccTsService.update(ccts);

						mont_deduire = 0;
					} else {

						EuSmc smc = new EuSmc();
						smc.setDateSmc(date);
						smc.setMontant(ccts.getMontant());
						smc.setMontantSolde(ccts.getMontant());
						smc.setEntree(0.0);
						smc.setSolde(0.0);
						smc.setSortie(0.0);
						smc.setOrigineSmc(0);
						smc.setSourceCredit(ccts.getEuCompteCredit().getSource());
						smc.setTypeSmc(typesmc);
						smc.setEuCompteCredit(ccts.getEuCompteCredit());
						smcService.add(smc);

						EuGcp gcp = new EuGcp();
						gcp.setEuTegc(te);
						gcp.setCodeMembre(codeMembreTe);
						gcp.setDateConso(new Date());
						gcp.setMontGcp(ccts.getMontant());
						gcp.setMontPreleve(0);
						gcp.setReste(ccts.getMontant());
						gcp.setEuCompteCredit(ccts.getEuCompteCredit());
						gcp.setSource(ccts.getEuCompteCredit().getSource());
						gcp.setEuCategorieCompte(compteTe.getEuCategorieCompte());
						gcp.setEuBon(bl);
						if ("BNP".equalsIgnoreCase(typeOp)) {
							gcp.setTypeGcp("BNP");
						} else if ("COM".equalsIgnoreCase(typeOp)) {
							gcp.setTypeGcp("COM");
						} else if ("PS".equals(typeOp) || "DIST".equals(typeOp)) {
							gcp.setTypeGcp("DIST");
						} else if ("ROPI".equals(typeOp)) {
							gcp.setTypeGcp("ROPI");
						} else {
							gcp.setTypeGcp("SMCIPN");
						}
						gcp = gcpService.create(gcp);

						// enregistrement des creditsconsommer avec les
						// credits
						EuCreditConsommer cCons = new EuCreditConsommer();
						cCons.setCodeCompte(codeComptePayeur);
						cCons.setCodeMembre(codeMembrePayeur);
						cCons.setCodeMembreMorale(null);
						cCons.setDateConsommation(date);
						cCons.setHeureConsommation(date);
						cCons.setMontConsommation(ccts.getMontant());
						cCons.setEuOperation(op);
						cCons.setCodeMembreDist(codeMembreTe);
						cCons.setEuCompteCredit(ccts.getEuCompteCredit());
						cCons.setEuProduit(ccts.getEuCompteCredit().getEuProduit());
						cCons.setEuBon(bc);
						ccConsService.add(cCons);

						// mont_deduire = mont_deduire - ccts.getMontant();

						// annulation du CNP
						EuCnp cnp = cnpService.findBySourceCredit(ccts.getEuCompteCredit().getIdCredit(),
								ccts.getCompteSource());
						if (cnp != null) {
							cnp.setMontCredit(cnp.getMontCredit() + ccts.getMontant());
							cnp.setSoldeCnp(cnp.getSoldeCnp() - ccts.getMontant());
							cnpService.update(cnp);
						} else {
							throw new IllegalStateException("Certains crédits n'ont pas d'origine au CNP : "
									+ ccts.getEuCompteCredit().getIdCredit());
						}

						// création de la source GCP AU CNP
						EuCnp new_cnp = new EuCnp();
						new_cnp.setDateCnp(new Date());
						new_cnp.setEuCapa(null);
						new_cnp.setEuDomiciliation(null);
						new_cnp.setTypeCnp("RPGnr");
						new_cnp.setSourceCredit(codeMembreTe + formatter.format(date));
						new_cnp.setOrigineCnp("FGRPGnr");
						new_cnp.setMontDebit(ccts.getMontant());
						new_cnp.setMontCredit(0);
						new_cnp.setSoldeCnp(ccts.getMontant());
						new_cnp.setTransfertGcp(0);
						new_cnp.setEuGcp(gcp);
						new_cnp.setEuCompteCredit(null);
						cnpService.add(new_cnp);

						// ajout dans Eu_cnp_entree
						EuCnpEntree cnpEnt = new EuCnpEntree();
						cnpEnt.setDateEntree(new Date());
						cnpEnt.setEuCnp(cnp);
						cnpEnt.setMontCnpEntree(ccts.getMontant());
						cnpEnt.setTypeCnpEntree("GCP");
						cnpEntService.add(cnpEnt);

						// mise a jour comptecreditTs
						mont_deduire = mont_deduire - ccts.getMontant();

						ccts.setMontant(0.0);
						ccTsService.update(ccts);
						c = c + 1;

					}
				}

				// mise à jour des comptes vendeur
				compteTe.setSolde(compteTe.getSolde() + montant);
				compteService.update(compteTe);

				// mise à jour du comptes ACHETEUR
				comptePayeur.setSolde(comptePayeur.getSolde() - montant);
				comptePayeur = compteService.update(comptePayeur);

				// mise a jour du Bon de consommation
				bc.setBonCodeMembreDistributeur(codeMembreTe);
				bc.setBonDateExpression(date);
				bc.setBonExprimer(1);
				bonService.update(bc);

				// mise a jour du Bon de consommation
				bl.setBonDateExpression(date);
				bl.setBonExprimer(1);
				bonService.update(bl);

			} else {
				if (Objects.isNull(compteTe)) {
					throw new IllegalArgumentException("Le compte GCp du " + codeMembreTe + " n'est pas disponible!!!");
				} else {
					throw new IllegalArgumentException(
							"Le compte GCp du " + codeMembrePayeur + " n'est pas disponible!!!");
				}
			}
		} else {
			throw new IllegalArgumentException(
					"Ce membre de credits sur son compte de transaction pour effectuer cette opérations!!!");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void emetreOpi(String typeOpi, String codeMembre, String codeTegc, String typeGcp, String modePaiement,
			String referencePaiement, int nbre, double montant) {
		String codeCompte = "NB-TPAGCP-" + codeMembre;
		EuCompte compte = compteService.getById(codeCompte);
		EuTegc tegc = tegcService.getById(codeTegc);
		if (Objects.nonNull(compte) && Objects.nonNull(tegc)) {
			double solde = gcpService.getSoldeByMembre(codeMembre);
			if (Double.compare(compte.getSolde(), solde) == 0) {
				if (compte.getSolde() >= montant) {
					Double soldeTegc;
					Double soldeGcp;
					soldeTegc = tegcService.getSoldeByMembreAndTe(codeMembre, codeTegc);
					soldeGcp = gcpService.getSoldeByTegc(codeTegc);
					if (soldeGcp.equals(soldeTegc)) {
						Optional<EuBon> opbon = bonService.emettreBon("BLG", codeMembre, montant);
						if (opbon.isPresent()) {
							EuBon bon = opbon.get();
							EuBanque defalutBk = bankService.findByDefaut();
							Integer serie = nbre;
							if (tegc.getTypeTegc().equalsIgnoreCase("externe")
									|| tegc.getTypeTegc().equalsIgnoreCase("specifique")) {
								nbre = 1;
								serie = nbre;
							} else {
								if (StringUtils.isNotBlank(typeGcp) && typeGcp.equals("CR")) {
								} else {
									nbre = paramService.getParam("prc", "nr");
								}
								serie = paramService.getParam("OPI", "serie");
							}

							EuDetailBonOpi detBonOpi = new EuDetailBonOpi();
							detBonOpi.setCodeMembreEmetteur(codeMembre);
							if (StringUtils.isNotBlank(defalutBk.getCodeMembreMorale())) {
								detBonOpi.setCodeMembrePbf(defalutBk.getCodeMembreMorale());
							}
							detBonOpi.setEuBon(opbon.get());
							detBonOpi.setNbre(nbre);
							detBonOpi.setModeReg("ENC");
							detBonOpi.setTypeEsc(null);
							detBonOpi.setMontant(montant);
							detBonOpi.setIdTpagcp(0L);
							detBonOpi.setCodeBanque(defalutBk.getCodeBanque());
							detBonOpi.setModePaiement(modePaiement);
							detBonOpi.setRefrencePaiement(referencePaiement);
							if (StringUtils.isNotBlank(codeTegc)) {
								detBonOpi.setCodeTegc(codeTegc);
							} else {
								detBonOpi.setCodeTegc(null);
							}
							detBonOpi.setNbreTranche(serie);
							detBonOpi.setTypeOpi(typeOpi);
							detBonOpi.setPrk(0);
							detBonOpi.setMontTranche1(0);
							detBonOpi.setDiferre(0);
							detBonOpi.setDateDebut(null);
							detBonOpi.setTypeGcp(typeGcp);
							detBonOpi = detBonOpiService.add(detBonOpi);

							Date date = new Date();
							Long idTpagcp = tpaService.getLastInsertedId();
							if (idTpagcp == null) {
								idTpagcp = 1L;
							} else {
								idTpagcp += 1;
							}
							int periode = paramService.getParam("periode", "valeur");
							int activer = paramService.getParam("OPI", "datedebut30");
							LocalDate dateDeb = LocalDate.now();
							LocalDate dateFinTranche = dateDeb;
							if (activer > 0) {
								dateFinTranche = dateDeb.with(new NextDayWeekendAdjuster(periode));
							}

							Double prk = paramService.getParametre("OPI", "prk");
							Double pck = paramService.getParametre("pck", "nr");
							Double txEsc = paramService.getParametre("taux", "escompte");
							double mont_opi = 0;
							if (tegc.getTypeTegc().equalsIgnoreCase("externe")
									|| tegc.getTypeTegc().equalsIgnoreCase("specifique")
									|| typeGcp.equalsIgnoreCase("ELI")) {
								mont_opi = montant;
							} else {
								mont_opi = ((montant * prk) / (pck * (1 + txEsc / 100)));
							}

							LocalDate ldatefin = dateDeb.with(new NextDayWeekendAdjuster(periode * nbre));
							EuTpagcp tpagcp = new EuTpagcp();
							tpagcp.setIdTpagcp(idTpagcp);
							tpagcp.setCodeMembre(codeMembre);
							tpagcp.setDateDeb(dateDeb);
							tpagcp.setDateDebTranche(dateDeb);
							tpagcp.setDateFin(ldatefin);
							tpagcp.setDateFinTranche(dateFinTranche);
							tpagcp.setEscomptable(3);
							tpagcp.setEuCompte(compte);
							tpagcp.setModeReglement("OPI");
							tpagcp.setMontEchange(0);
							tpagcp.setMontEchu(0);
							tpagcp.setMontEscompte(0);
							tpagcp.setMontGcp(montant);
							tpagcp.setPeriode(periode);
							tpagcp.setNtf(nbre);
							tpagcp.setResteNtf(nbre);
							tpagcp.setSolde(montant);
							if ("ELI".equalsIgnoreCase(typeGcp)) {
								tpagcp.setTypeRessource(typeGcp);
							} else {
								tpagcp.setTypeRessource("GCp");
							}
							tpagcp.setNumeroBl(bon.getBonNumero());
							if (codeMembre.endsWith("P")) {
								if (typeOpi.equalsIgnoreCase("P")) {
									tpagcp.setTypeBl("PP Prestataire");
								} else {
									tpagcp.setTypeBl("PP Acheteur-Revendeur");
								}
							} else {
								if (tegc.getTypeTegc().equalsIgnoreCase("interim")) {
									tpagcp.setTypeBl("Centrale");
								} else {
									if (detBonOpi.getTypeOpi().equalsIgnoreCase("R")) {
										tpagcp.setTypeBl("PM Acheteur-Revendeur");
									} else {
										tpagcp.setTypeBl("PM");
									}
								}
							}
							tpagcp.setMontGcpMaj(Math.floor(mont_opi));

							tpagcp.setMontTranche(Math.floor(mont_opi / detBonOpi.getNbre()));
							tpagcp.setSolde(Math.floor(mont_opi));
							tpagcp.setReinjecter(0);
							tpagcp.setNbreInjection(0);
							tpagcp = tpaService.add(tpagcp);

							detBonOpi.setIdTpagcp(tpagcp.getIdTpagcp());
							detBonOpi.setPrk(prk);
							detBonOpi = detBonOpiService.update(detBonOpi);

							double mont_blg = montant;
							if (StringUtils.isBlank(typeGcp)) {
								gcpService.preleverGcp("OPI", compte, tegc, tpagcp.getIdTpagcp(), bon, codeMembre,
										mont_blg);
							} else {
								gcpService.preleverGcp(typeGcp, "OPI", compte, tegc, tpagcp.getIdTpagcp(), bon,
										codeMembre, mont_blg);
							}

							EuTraite traite;
							Long id_traite = traiteService.getLastInsertedId();
							Integer traiter = traiteService.getTraiterByTpagcp(detBonOpi.getIdTpagcp());
							if (traiter == null) {
								traiter = 0;
							}
							if (id_traite == null) {
								id_traite = 0L;
							}
							Date datedeb = date;
							if (detBonOpi.getDateDebut() != null) {
								datedeb = detBonOpi.getDateDebut();
							} else {
								if (detBonOpi.getDiferre() > 0) {
									datedeb = DateUtility.asUtilDate(DateUtility.asLocalDate(date)
											.with(new NextDayWeekendAdjuster(detBonOpi.getDiferre() * 30)));
								}
							}
							int nbreDisponible = detBonOpi.getNbre() / detBonOpi.getNbreTranche();
							Date datefin = datedeb;
							if (activer > 0) {
								datefin = DateUtility.asUtilDate(
										DateUtility.asLocalDate(datedeb).with(new NextDayWeekendAdjuster(30)));
							}
							for (int i = 1; i <= detBonOpi.getNbre(); i++) {
								traite = new EuTraite();
								traite.setTraiteId(id_traite + i);
								traite.setTraiteCodeBanque(detBonOpi.getCodeBanque());
								traite.setTraiteTegcp(detBonOpi.getIdTpagcp());
								traite.setEuBon(bon);
								traite.setTraitePayer(0);
								traite.setTraiteAvantVte(0);
								traite.setTraiter(traiter + i);
								if (i == 1) {
									if (detBonOpi.getMontTranche1() > 0) {
										traite.setTraiteMontant(detBonOpi.getMontTranche1());
									} else {
										traite.setTraiteMontant(tpagcp.getMontTranche());
									}
									traite.setTraiteDateDebut(date);
									traite.setTraiteDateFin(datefin);
								} else {
									datefin = DateUtility.asUtilDate(
											DateUtility.asLocalDate(datedeb).with(new NextDayWeekendAdjuster()));
									traite.setTraiteDateDebut(datedeb);
									traite.setTraiteDateFin(datefin);
									traite.setTraiteMontant(tpagcp.getMontTranche());
								}
								if (i <= nbreDisponible) {
									traite.setTraiteDisponible(1);
								} else {
									traite.setTraiteDisponible(0);
								}
								traite.setTraiteImprimer(0);
								traite.setTraiteEscompteNature(0);
								traite.setTraiteAvantVte(0);
								traite.setModePaiement(detBonOpi.getModePaiement());
								traite.setReferencePaiement(detBonOpi.getRefrencePaiement());
								traiteService.add(traite);

								datedeb = datefin;
							}
							bon.setBonExprimer(1);
							bon.setBonDateExpression(date);
							bonService.update(bon);
						} else {

						}
					} else {
						throw new CompteNonIntegreException(
								"Le solde de ce TE est insuffisant ou le compte est incohérent!");
					}
				} else {
					throw new SoldeInsuffisantException();
				}
			} else {
				throw new CompteNonIntegreException("Le solde du compte " + compte.getCodeCompte() + " qui est "
						+ compte.getSolde() + " est différent de la somme des GCp qui est de " + solde);
			}
		} else {
			throw new CompteNonTrouveException();
		}
	}

	public boolean stockArticle(EuEli eli) {
		List<EuDetailEli> detElis = detailEliService.findByEliAndStatut(eli.getIdEli(), 1);
		if (detElis.size() > 0) {
			try {

				Long id = articleCatService.findLastInsertedRow();
				if (id == null) {
					id = 1L;
				} else {
					id++;
				}
				EuArticleStockesCategorie categorie = new EuArticleStockesCategorie();
				categorie.setCodeMembreMorale(eli.getCodeMembre());
				categorie.setCodeTegc(eli.getCodeTegc());
				categorie.setNomArticleStockesCategorie("Autres");
				categorie.setIdArticleStockesCategorie(id);
				categorie.setEtat(1);
				articleCatService.add(categorie);

				EuMembreMorale morale = moraleService.findById(eli.getCodeMembre());
				detElis.forEach(d -> {
					Long idArt = articleService.findMaxId();
					EuArticleStockes article = new EuArticleStockes();
					article.setCategorie(eli.getCodeTegc());
					article.setArticleStockesCategorie(categorie.getIdArticleStockesCategorie().intValue());
					article.setDesignation(d.getLibelleProduit());
					article.setEuEli(eli);
					article.setCodeBarre(null);
					article.setDateEnregistrement(new Date());
					article.setEuMembreMorale(morale);
					article.setPrix(d.getPrixVente());
					article.setPublier(1);
					article.setType(d.getTypeBps());
					article.setQteStock(d.getQteVente().intValue());
					article.setQteSolde(d.getQteVente().intValue());
					article.setQteVendu(0);
					article.setPrixEli(d.getPrixUnitaire());
					article.setReference(StringUtils.capitalize(d.getTypeBps() + idArt++));
					articleService.add(article);
				});
				return true;
			} catch (Exception e) {
				log.error("Echec de création de l'article", e);
				return false;
			}
		}
		return false;
	}

	public boolean payerNn(String codeMembrePbf, String codeMembre, String codeBon, String typeNn, double montant) {
		String codeCompte = "NN-TS" + typeNn + "-" + codeMembre;
		String code_compte_gcp = "NB-TPAGCP-" + codeMembrePbf;
		EuCompte compte = compteService.getById(codeCompte);
		EuCompte compte_gcp = compteService.getById(code_compte_gcp);
		if (Objects.nonNull(compte) && Objects.nonNull(compte_gcp)) {
			EuMembre membre = compte.getEuMembre();
			if (!Objects.nonNull(membre)) {
				membre = membreService.findById(codeMembre);
			}
			EuMembreMorale morale = compte_gcp.getEuMembreMorale();
			if (!Objects.nonNull(morale)) {
				morale = moraleService.findById(codeMembrePbf);
			}
			List<EuCompteCreditTs> cctss = Lists.newArrayList();
			if (compte.getSolde() >= montant) {
				if (StringUtils.isBlank(codeBon)) {
					cctss = ccTsService.findByCompteAndProduit(codeCompte, typeNn + "%");
				} else {
					cctss = ccTsService.findByEuBon_BonNumero(codeBon);
				}
				if (cctss.size() > 0) {
					double somme = cctss.stream().mapToDouble(EuCompteCreditTs::getMontant).sum();
					if (somme >= montant) {
						try {
							Date date = new Date();
							Long id_echange = echangeService.findLastEchangeInsertedId();
							if (id_echange == null) {
								id_echange = 0L;
							}
							EuEchange ech = new EuEchange();
							ech.setIdEchange(id_echange + 1);
							ech.setCatEchange(typeNn);
							ech.setTypeEchange("NN/NN");
							ech.setMontantEchange(montant);
							ech.setMontant(montant);
							ech.setCompenser(0);
							ech.setRegler(1);
							ech.setEuCompte(compte);
							ech.setCodeCompteObt(code_compte_gcp);
							ech.setDateEchange(date);
							ech.setDateReglement(date);
							ech.setEuMembreMorale(morale);
							ech.setEuMembre(membre);
							ech.setCodeProduit(typeNn);
							ech.setAgio(0);
							echangeService.add(ech);

							String code_gcp_pbf = "PBF-" + typeNn + "-" + codeMembrePbf;
							EuGcpPbf gcp_pbf = gcpPbfService.findById(code_gcp_pbf);
							if (Objects.nonNull(gcp_pbf)) {
								gcp_pbf.setMontGcp(gcp_pbf.getMontGcp() + montant);
								gcp_pbf.setMontGcpReel(gcp_pbf.getMontGcpReel() + montant);
								gcp_pbf.setSoldeGcpReel(gcp_pbf.getSoldeGcpReel() + montant);
								gcp_pbf.setSoldeGcp(gcp_pbf.getSoldeGcp() + montant);
							} else {
								gcp_pbf = new EuGcpPbf();
								gcp_pbf.setCodeGcpPbf(code_gcp_pbf);
								gcp_pbf.setCodeMembre(code_compte_gcp);
								gcp_pbf.setEuCompte(compte_gcp);
								gcp_pbf.setAgioConsomme(0);
								gcp_pbf.setGcpCompense(0);
								gcp_pbf.setMontGcp(montant);
								gcp_pbf.setMontAgio(0);
								gcp_pbf.setTypeCapa("FG" + typeNn);
								gcp_pbf.setMontGcpReel(montant);
								gcp_pbf.setSoldeAgio(0);
								gcp_pbf.setSoldeGcpReel(montant);
								gcp_pbf.setSoldeGcp(montant);
							}
							gcpPbfService.add(gcp_pbf);

							double mont_a_deduire = montant;
							int compteur = 0;
							while (mont_a_deduire > 0 && compteur < cctss.size()) {
								EuCompteCreditTs ccts = cctss.get(compteur);
								if (ccts.getMontant() < mont_a_deduire) {
									EuDetailGcpPbf detGcpPbf = new EuDetailGcpPbf();
									Long idGcppbf = detGcpPbfservice.getLastInsertedId();
									if (idGcppbf != null) {
										idGcppbf++;
									} else {
										idGcppbf = 1L;
									}
									detGcpPbf.setAgio(0);
									detGcpPbf.setEuGcpPbf(gcp_pbf);
									detGcpPbf.setIdCredit(ccts.getEuCompteCredit().getIdCredit());
									detGcpPbf.setMontGcpPbf(ccts.getMontant());
									detGcpPbf.setMontPreleve(0);
									detGcpPbf.setTypeCapa("FG" + typeNn);
									detGcpPbf.setSourceCredit(ccts.getEuCompteCredit().getSource());
									detGcpPbf.setIdGcpPbf(idGcppbf);
									detGcpPbf.setSoldeGcpPbf(ccts.getMontant());
									detGcpPbf.setIdEchange(id_echange);
									detGcpPbf.setIdEscompte(null);
									detGcpPbfservice.add(detGcpPbf);

									Long idCreditEchange = creditEchService.getLastInsertedId();
									if (idCreditEchange != null) {
										idCreditEchange++;
									} else {
										idCreditEchange = 1L;
									}
									EuCreditEchange credEch = new EuCreditEchange();
									credEch.setIdCreditEchange(idCreditEchange);
									credEch.setIdCredit(ccts.getEuCompteCredit().getIdCredit());
									credEch.setSourceCredit(ccts.getEuCompteCredit().getSource());
									credEch.setMontEchange(ccts.getMontant());
									credEch.setIdEchange(id_echange);
									creditEchService.add(credEch);

									mont_a_deduire -= ccts.getMontant();
									ccts.setMontant(0d);
									ccTsService.update(ccts);

									compteur++;
								} else {
									EuDetailGcpPbf detGcpPbf = new EuDetailGcpPbf();
									Long idGcppbf = detGcpPbfservice.getLastInsertedId();
									if (idGcppbf != null) {
										idGcppbf++;
									} else {
										idGcppbf = 1L;
									}
									detGcpPbf.setAgio(0);
									detGcpPbf.setEuGcpPbf(gcp_pbf);
									detGcpPbf.setIdCredit(ccts.getEuCompteCredit().getIdCredit());
									detGcpPbf.setMontGcpPbf(mont_a_deduire);
									detGcpPbf.setMontPreleve(0);
									detGcpPbf.setTypeCapa("FG" + typeNn);
									detGcpPbf.setSourceCredit(ccts.getEuCompteCredit().getSource());
									detGcpPbf.setIdGcpPbf(idGcppbf);
									detGcpPbf.setSoldeGcpPbf(mont_a_deduire);
									detGcpPbf.setIdEchange(id_echange);
									detGcpPbf.setIdEscompte(null);
									detGcpPbfservice.add(detGcpPbf);

									Long idCreditEchange = creditEchService.getLastInsertedId();
									if (idCreditEchange != null) {
										idCreditEchange++;
									} else {
										idCreditEchange = 1L;
									}
									EuCreditEchange credEch = new EuCreditEchange();
									credEch.setIdCreditEchange(idCreditEchange);
									credEch.setIdCredit(ccts.getEuCompteCredit().getIdCredit());
									credEch.setSourceCredit(ccts.getEuCompteCredit().getSource());
									credEch.setMontEchange(mont_a_deduire);
									credEch.setIdEchange(id_echange);
									creditEchService.add(credEch);

									ccts.setMontant(ccts.getMontant() - mont_a_deduire);
									ccTsService.update(ccts);

									mont_a_deduire = 0;
								}
							}

							compte.setSolde(compte.getSolde() - montant);
							compteService.update(compte);

							compte_gcp.setSolde(compte_gcp.getSolde() + montant);
							compteService.update(compte_gcp);
							return true;
						} catch (Exception e) {
							return false;
						}
					}
				}
			}
		}
		return false;
	}
}

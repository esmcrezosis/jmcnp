package com.esmc.mcnp.components;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dto.bc.CalculBonInfo;
import com.esmc.mcnp.exception.CompteNonIntegreException;
import com.esmc.mcnp.exception.CompteNonTrouveException;
import com.esmc.mcnp.exception.SoldeInsuffisantException;
import com.esmc.mcnp.model.ba.EuCapa;
import com.esmc.mcnp.model.ba.EuCapaTs;
import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.cm.EuCategorieCompte;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.cm.EuCompteCreditTs;
import com.esmc.mcnp.model.cm.EuTypeCompte;
import com.esmc.mcnp.model.others.EuOperation;
import com.esmc.mcnp.services.ba.EuCapaService;
import com.esmc.mcnp.services.ba.EuCapaTsService;
import com.esmc.mcnp.services.bc.EuBonService;
import com.esmc.mcnp.services.cm.EuCompteCreditService;
import com.esmc.mcnp.services.cm.EuCompteCreditTsService;
import com.esmc.mcnp.services.cm.EuCompteService;
import com.esmc.mcnp.services.common.EuOperationService;
import com.google.common.collect.Lists;

@Service
public class TransfertUtility {

	private Logger logger = LogManager.getLogger(TransfertUtility.class);
	private final EuCapaService capaService;
	private final EuCompteService compteService;
	private final EuCapaTsService capatsService;
	private final EuCompteCreditService compteCreditService;
	private final EuCompteCreditTsService compteCreditTsService;
	private final EuOperationService operationService;
	private final EuBonService bonService;
	private final BonAchatComponent bonAchatComponent;

	public TransfertUtility(EuCapaService capaService, EuCompteService compteService,
			EuCapaTsService capatsService, EuCompteCreditService compteCreditService,
			EuCompteCreditTsService compteCreditTsService, EuOperationService operationService, EuBonService bonService,
			BonAchatComponent bonAchatComponent) {
		this.capaService = capaService;
		this.compteService = compteService;
		this.capatsService = capatsService;
		this.compteCreditService = compteCreditService;
		this.compteCreditTsService = compteCreditTsService;
		this.operationService = operationService;
		this.bonService = bonService;
		this.bonAchatComponent = bonAchatComponent;
	}

	@Transactional(rollbackFor = Exception.class)
	public EuBon transfertBA(String codeMembre, String typeBA, String catBon, Double montant)
			throws CompteNonIntegreException, CompteNonTrouveException, SoldeInsuffisantException, DataAccessException {
		if (!StringUtils.isNotBlank(codeMembre) && StringUtils.isBlank(typeBA) && montant < 0) {
			return null;
		}
		List<EuCapa> capas = Lists.newArrayList();
		if (StringUtils.isNotBlank(catBon)) {
			capas = ListUtils.emptyIfNull(capaService.findbyMembreAndProduitAndOrigine(codeMembre, catBon, typeBA));
		} else {
			capas = ListUtils.emptyIfNull(capaService.findByMembreAndOrigine(codeMembre, typeBA));
		}
		if (!capas.isEmpty()) {
			Double soldeCapa = capas.stream().map(c -> c.getMontantSolde()).reduce(Double::sum).get();
			if (soldeCapa >= montant) {
				String codecompte = "NN-CAPA-" + codeMembre;
				String codeCompteTransfert = "NN-TSCAPA-" + codeMembre;
				EuCompte compte = compteService.findCompteById(codecompte);
				EuCompte comptets = compteService.findCompteById(codeCompteTransfert);
				if (Objects.nonNull(compte) && compte.getSolde() >= soldeCapa.doubleValue()) {
					EuCategorieCompte catCompte = new EuCategorieCompte(ServerUtil.getTypeCompte(codecompte));
					Long idOp = operationService.getLastOperation() + 1;

					Date date = new Date();
					EuOperation op = new EuOperation();
					op.setEuCategorieCompte(catCompte);
					op.setCodeProduit("CAPA");
					op.setDateOp(date);
					op.setHeureOp(date);
					op.setLibOp("Transfert Bon d'Achat");
					op.setTypeOp("TBA");
					op.setMontantOp(montant);
					if (ServerUtil.isPhysique(codeMembre)) {
						op.setEuMembre(compte.getEuMembre());
					} else {
						op.setEuMembreMorale(compte.getEuMembreMorale());
					}
					op.setIdOperation(idOp);
					op.setEuUtilisateur(null);
					operationService.add(op);

					Optional<EuBon> opBon = bonService.emettreBon("BA", codeMembre, montant);
					if (opBon.isPresent()) {
						EuBon bon = opBon.get();
						double mont_a_deduire = montant;
						int compteur = 0;
						while (mont_a_deduire > 0 && compteur < capas.size()) {
							EuCapa capa = capas.get(compteur);
							if (capa.getMontantSolde() > mont_a_deduire) {
								EuCapaTs capats = new EuCapaTs();
								capats.setMontant(mont_a_deduire);
								capats.setMontantUtiliser(0d);
								capats.setMontantSolde(mont_a_deduire);
								capats.setDateCapaTs(date);
								capats.setEuCapa(capa);
								capats.setEuCompte(comptets);
								capats.setEuBon(bon);
								capatsService.add(capats);

								capa.setMontantUtiliser(capa.getMontantUtiliser() + mont_a_deduire);
								capa.setMontantSolde(capa.getMontantSolde() - mont_a_deduire);
								capaService.update(capa);

								mont_a_deduire = 0;
							} else {
								EuCapaTs capats = new EuCapaTs();
								capats.setMontant(capa.getMontantSolde());
								capats.setMontantUtiliser(0d);
								capats.setMontantSolde(capa.getMontantSolde());
								capats.setDateCapaTs(date);
								capats.setEuCapa(capa);
								capats.setEuCompte(comptets);
								capats.setEuBon(bon);
								capatsService.add(capats);

								mont_a_deduire -= capa.getMontantSolde();
								capa.setMontantUtiliser(capa.getMontantUtiliser() + capa.getMontantSolde());
								capa.setMontantSolde(0);
								capaService.update(capa);

								compteur++;
							}
						}

						compte.setSolde(compte.getSolde() - montant);
						compteService.update(compte);

						if (Objects.nonNull(comptets)) {
							comptets.setSolde(comptets.getSolde() + montant);
							compteService.update(comptets);
						} else {
							comptets = new EuCompte();
							comptets.setSolde(montant);
							comptets.setCodeCompte(codeCompteTransfert);
							comptets.setDateAlloc(new Date());
							if (ServerUtil.isPhysique(codeMembre)) {
								comptets.setEuMembre(compte.getEuMembre());
							} else {
								comptets.setEuMembreMorale(compte.getEuMembreMorale());
							}
							comptets.setDesactiver("0");
							comptets.setLibCompte("Compte de Transaction");
							comptets.setEuTypeCompte(new EuTypeCompte("NN"));
							comptets.setEuCategorieCompte(new EuCategorieCompte("TSCAPA"));
							comptets = compteService.create(comptets);
						}
						return bon;
					}
				} else {
					throw new CompteNonIntegreException(
							"Transfert BA : Compte Incoherent; le solde du compte " + compte.getCodeCompte() + " de "
									+ compte.getSolde() + " n'est équivalent à la somme des détails de " + soldeCapa);
				}
			} else {
				throw new SoldeInsuffisantException("Transfert BA : Le solde de votre compte  de " + soldeCapa
						+ " est insuffisant pour effectuer une transaction de " + montant);
			}
		}
		return null;
	}

	@Transactional(rollbackFor = Exception.class)
	public EuBon transfertBA(String codeMembre, String typeBon, String typeOp, String typeBA, String catBon,
			Double montant)
			throws CompteNonIntegreException, CompteNonTrouveException, SoldeInsuffisantException, DataAccessException {
		if (!StringUtils.isNotBlank(codeMembre) && StringUtils.isBlank(typeBon) && montant < 0) {
			return null;
		}
		List<EuCapa> capas = Lists.newArrayList();
		if (StringUtils.isNotBlank(typeBA) && StringUtils.isNotBlank(catBon)) {
			capas = ListUtils.emptyIfNull(capaService.findbyMembreAndProduitAndOrigine(codeMembre, catBon, typeBA));
		} else if (StringUtils.isNotBlank(typeBA)) {
			capas = ListUtils.emptyIfNull(capaService.findByMembreAndOrigine(codeMembre, typeBA));
		} else {
			if ("r".equals(typeBon)) {
				List<String> origines = Arrays.asList("BAN", "BAI");
				double montCapa = capaService.findSumCapaByMembreAndOrigine(codeMembre, origines);
				if (montCapa < montant) {
					String typeBonAchat = "";
					if (codeMembre.endsWith("M")) {
						typeBonAchat = "I";
					} else {
						typeBonAchat = "RPG";
					}
					if (!bonAchatComponent.souscrireBonAchat(codeMembre, "", typeBonAchat, montant - montCapa)) {
						return null;
					}
				}
				capas = ListUtils.emptyIfNull(capaService.findByMembreAndOrigine(codeMembre, origines));
			} else {
				if ("revendeur".equalsIgnoreCase(typeOp)) {
					List<String> origines = Arrays.asList("BAN", "BAI");
					double montCapa = capaService.findSumCapaByMembreAndOrigine(codeMembre, origines);
					if (montCapa < montant) {
						String typeBonAchat = "";
						if (codeMembre.endsWith("M")) {
							typeBonAchat = "I";
						} else {
							typeBonAchat = "RPG";
						}
						if (!bonAchatComponent.souscrireBonAchat(codeMembre, "", typeBonAchat, montant - montCapa)) {
							return null;
						}
					}
					capas = ListUtils.emptyIfNull(capaService.findByMembreAndOrigine(codeMembre, origines));
				} else {
					List<String> origines = Arrays.asList("BAN", "BAI");
					capas.addAll(capaService.findByMembreAndOrigine(codeMembre, "NN"));
					capas.addAll(capaService.findByMembreAndOrigine(codeMembre, origines));
				}
			}
		}
		if (!capas.isEmpty()) {
			Double soldeCapa = capas.stream().map(c -> c.getMontantSolde()).reduce(Double::sum).get();
			if (soldeCapa >= montant) {
				String codecompte = "NN-CAPA-" + codeMembre;
				String codeCompteTransfert = "NN-TSCAPA-" + codeMembre;
				EuCompte compte = compteService.findCompteById(codecompte);
				EuCompte comptets = compteService.findCompteById(codeCompteTransfert);
				if (Objects.nonNull(compte) && compte.getSolde() >= soldeCapa) {
					EuCategorieCompte catCompte = new EuCategorieCompte(ServerUtil.getTypeCompte(codecompte));
					Long idOp = operationService.getLastOperation() + 1;

					Date date = new Date();
					EuOperation op = new EuOperation();
					op.setEuCategorieCompte(catCompte);
					op.setCodeProduit("CAPA");
					op.setDateOp(date);
					op.setHeureOp(date);
					op.setLibOp("Transfert Bon d'Achat");
					op.setTypeOp("TBA");
					op.setMontantOp(montant);
					if (ServerUtil.isPhysique(codeMembre)) {
						op.setEuMembre(compte.getEuMembre());
					} else {
						op.setEuMembreMorale(compte.getEuMembreMorale());
					}
					op.setIdOperation(idOp);
					op.setEuUtilisateur(null);
					operationService.add(op);

					Optional<EuBon> opBon = bonService.emettreBon("BA", codeMembre, montant);
					if (opBon.isPresent()) {
						EuBon bon = opBon.get();
						double mont_a_deduire = montant;
						int compteur = 0;
						while (mont_a_deduire > 0 && compteur < capas.size()) {
							EuCapa capa = capas.get(compteur);
							if (capa.getMontantSolde() > mont_a_deduire) {
								EuCapaTs capats = new EuCapaTs();
								capats.setMontant(mont_a_deduire);
								capats.setMontantUtiliser(0d);
								capats.setMontantSolde(mont_a_deduire);
								capats.setDateCapaTs(date);
								capats.setEuCapa(capa);
								capats.setEuCompte(comptets);
								capats.setEuBon(bon);
								capatsService.add(capats);

								capa.setMontantUtiliser(capa.getMontantUtiliser() + mont_a_deduire);
								capa.setMontantSolde(capa.getMontantSolde() - mont_a_deduire);
								capaService.update(capa);

								mont_a_deduire = 0;
							} else {
								EuCapaTs capats = new EuCapaTs();
								capats.setMontant(capa.getMontantSolde());
								capats.setMontantUtiliser(0d);
								capats.setMontantSolde(capa.getMontantSolde());
								capats.setDateCapaTs(date);
								capats.setEuCapa(capa);
								capats.setEuCompte(comptets);
								capats.setEuBon(bon);
								capatsService.add(capats);

								mont_a_deduire -= capa.getMontantSolde();
								capa.setMontantUtiliser(capa.getMontantUtiliser() + capa.getMontantSolde());
								capa.setMontantSolde(0);
								capaService.update(capa);

								compteur++;
							}
						}

						compte.setSolde(compte.getSolde() - montant);
						compteService.update(compte);

						if (Objects.nonNull(comptets)) {
							comptets.setSolde(comptets.getSolde() + montant);
							compteService.update(comptets);
						} else {
							comptets = new EuCompte();
							comptets.setSolde(montant);
							comptets.setCodeCompte(codeCompteTransfert);
							comptets.setDateAlloc(new Date());
							if (ServerUtil.isPhysique(codeMembre)) {
								comptets.setEuMembre(compte.getEuMembre());
							} else {
								comptets.setEuMembreMorale(compte.getEuMembreMorale());
							}
							comptets.setDesactiver("0");
							comptets.setLibCompte("Compte de Transaction");
							comptets.setEuTypeCompte(new EuTypeCompte("NN"));
							comptets.setEuCategorieCompte(new EuCategorieCompte("TSCAPA"));
							comptets = compteService.create(comptets);
						}
						return bon;
					}
				} else {
					throw new CompteNonIntegreException(
							"Transfert BA : Compte Incoherent; le solde du compte " + compte.getCodeCompte() + " de "
									+ compte.getSolde() + " n'est équivalent à la somme des détails de " + soldeCapa);
				}
			} else {
				throw new SoldeInsuffisantException("Transfert BA : Le solde de votre compte  de " + soldeCapa
						+ " est insuffisant pour effectuer une transaction de " + montant);
			}
		}
		return null;
	}

	@Transactional(rollbackFor = Exception.class)
	public EuBon tansfertBC(String codeMembre, String typeBC, CalculBonInfo bonInfo, Double prk, Double montant)
			throws CompteNonIntegreException, CompteNonTrouveException, SoldeInsuffisantException, DataAccessException {

		if (!StringUtils.isNotBlank(codeMembre) && !StringUtils.isNotBlank(typeBC)
				&& !StringUtils.isNotBlank(bonInfo.getCatBon())) {
			throw new IllegalArgumentException(
					"Transfert BC : Vous devez fournir des infos nécéssaires au bon de cette opération(code membre, type de BC ...)");
		}
		if (montant <= 0) {
			throw new IllegalArgumentException("Transfert BC : Le montant du transfert ne doit pas être nul");
		}

		if (bonInfo.getCatBon().equals("nr") && (prk == null || prk <= 0)) {
			throw new IllegalArgumentException("Transfert BC : Vous devez fournir la PRK du Non Récurrent choisi");
		}

		String codeProduit = StringUtils.EMPTY;
		String codeComptets = StringUtils.EMPTY;
		String codeCompte = StringUtils.EMPTY;
		String codeCat = StringUtils.EMPTY;
		if (typeBC.equalsIgnoreCase("RPG") || typeBC.equalsIgnoreCase("I")) {
			codeComptets = "NB-TSRPG-" + codeMembre;
			codeCompte = "NB-TPAGC" + typeBC + "-" + codeMembre;
			codeProduit = typeBC + bonInfo.getCatBon();
			if (typeBC.equalsIgnoreCase("RPG")) {
				codeCat = "TS" + typeBC;
			} else {
				codeCat = "TSGC" + typeBC;
			}
		} else if (typeBC.equalsIgnoreCase("TI")) {
			if (codeMembre.endsWith("M")) {
				codeComptets = "NB-TSI-" + codeMembre;
				codeCompte = "NB-TI-" + codeMembre;
				codeProduit = "Inr";
				codeCat = "TSI";
			} else {
				codeComptets = "NB-TSRPG-" + codeMembre;
				codeCompte = "NB-TPAGCRPG" + "-" + codeMembre;
				codeProduit = "RPGnr";
				codeCat = "TSRPG";
			}
		} else if("BCi".equals(typeBC)) {
			codeCompte = "NB-TBC-" + codeMembre;
			codeComptets = "NB-TSBC" + "-" + codeMembre;
			codeProduit = typeBC;
			codeCat = "TSBC";
		} else {
			codeCompte = "NB-TS-" + typeBC + codeMembre;
			codeComptets = "NB-TS" + typeBC + "-" + codeMembre;
			codeProduit = typeBC + bonInfo.getCatBon();
			codeCat = "TS" + typeBC;
		}

		List<EuCompteCredit> credits = null;
		if (bonInfo.getCatBon().equalsIgnoreCase("nr")) {
			credits = ListUtils.emptyIfNull(compteCreditService.fetchByCompteAndProduitAndPrk(codeCompte, codeProduit,
					prk, bonInfo.getTypeProduit()));
		} else {
			credits = ListUtils.emptyIfNull(compteCreditService.findbyCompteAndProduit(codeCompte,
					codeProduit));
			/*credits = ListUtils.emptyIfNull(compteCreditService.findByTypeRecurrentAndTypeProduitAndDuree(codeCompte,
					codeProduit, bonInfo.getTypeRecurrent(), bonInfo.getTypeProduit(), bonInfo.getDuree()));*/
		}
		
		if (!credits.isEmpty()) {
			double soldeCredit = credits.stream().mapToDouble(EuCompteCredit::getMontantCredit).sum();
			if (soldeCredit >= montant) {
				EuCompte compte = compteService.findCompteById(codeCompte);
				EuCompte comptets = compteService.findCompteById(codeComptets);
				if (Objects.nonNull(compte) && compte.getSolde() >= soldeCredit) {
					try {
						EuCategorieCompte catCompte = new EuCategorieCompte(ServerUtil.getTypeCompte(codeCompte));
						Long idOp = operationService.getLastOperation() + 1;

						Date date = new Date();
						EuOperation op = new EuOperation();
						op.setEuCategorieCompte(catCompte);
						op.setCodeProduit(codeProduit);
						op.setDateOp(date);
						op.setHeureOp(date);
						op.setLibOp("Transfert Bon de Consommation");
						op.setTypeOp("TBC");
						op.setMontantOp(montant);
						if (ServerUtil.isPhysique(codeMembre)) {
							op.setEuMembre(compte.getEuMembre());
						} else {
							op.setEuMembreMorale(compte.getEuMembreMorale());
						}
						op.setIdOperation(idOp);
						op.setEuUtilisateur(null);
						operationService.add(op);

						Optional<EuBon> opBon = bonService.emettreBon("BC" + bonInfo.getCatBon(), codeMembre, montant);
						if (opBon.isPresent()) {
							EuBon bon = opBon.get();

							if (Objects.nonNull(comptets)) {
								comptets.setSolde(comptets.getSolde() + montant);
								comptets = compteService.update(comptets);
							} else {
								comptets = new EuCompte();
								comptets.setSolde(montant);
								comptets.setCodeCompte(codeComptets);
								comptets.setDateAlloc(date);
								if (ServerUtil.isPhysique(codeMembre)) {
									comptets.setEuMembre(compte.getEuMembre());
								} else {
									comptets.setEuMembreMorale(compte.getEuMembreMorale());
								}
								comptets.setDesactiver("0");
								comptets.setLibCompte("Compte de Transaction");
								comptets.setEuTypeCompte(new EuTypeCompte("NB"));
								comptets.setEuCategorieCompte(new EuCategorieCompte(codeCat));
								comptets = compteService.create(comptets);
							}

							double mont_a_deduire = montant;
							int compteur = 0;
							while (mont_a_deduire > 0) {
								EuCompteCredit credit = credits.get(compteur);
								if (credit.getMontantCredit() > mont_a_deduire) {
									EuCompteCreditTs creditts = new EuCompteCreditTs();
									creditts.setMontant(mont_a_deduire);
									creditts.setEuCompteCredit(credit);
									creditts.setCompteSource(credit.getSource());
									creditts.setDateCreditTs(date);
									creditts.setEuCompte(comptets);
									creditts.setEuBon(bon);
									creditts = compteCreditTsService.add(creditts);

									credit.setMontantCredit(credit.getMontantCredit() - mont_a_deduire);
									credit = compteCreditService.update(credit);

									mont_a_deduire = 0;
								} else {
									EuCompteCreditTs creditts = new EuCompteCreditTs();
									creditts.setMontant(credit.getMontantCredit());
									creditts.setEuCompteCredit(credit);
									creditts.setCompteSource(credit.getSource());
									creditts.setDateCreditTs(date);
									creditts.setEuCompte(comptets);
									creditts.setEuBon(bon);
									creditts = compteCreditTsService.add(creditts);

									mont_a_deduire -= credit.getMontantCredit();
									credit.setMontantCredit(0.0);
									credit = compteCreditService.update(credit);

									compteur++;
								}
							}

							compte.setSolde(compte.getSolde() - montant);
							compteService.update(compte);

							return bon;
						} else {
							throw new IllegalStateException(
									"Transfert BC : Echec de la création du bon de consommation");
						}
					} catch (Exception e) {
						logger.error("Transfert BC : Exception occured : ", e);
						if (e instanceof RuntimeException) {
							throw e;
						} else {
							throw new RuntimeException(e);
						}
					}
				} else {
					if (!Objects.nonNull(compte)) {
						throw new CompteNonTrouveException(
								"Transfert BC : Le compte N° " + codeCompte + " n'existe pas");
					} else {
						throw new CompteNonIntegreException("Transfert BC : Le solde " + compte.getSolde()
								+ " du compte N° " + compte.getCodeCompte()
								+ "n' est pas équivalent à la somme des détails BCs de " + soldeCredit);
					}
				}
			} else {
				throw new SoldeInsuffisantException("Transfert BC : Le solde du compte N° " + codeCompte + " : "
						+ soldeCredit + "  != montant demande :" + montant
						+ " est insuffisant pour effectuer cette opération!");
			}
		} else {
			throw new CompteNonTrouveException("Transfert BC : Les crédits correspondant à ce compte n'existent pas!");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public EuBon transfertNn(String codeMembre, String typeNn, double montant) {
		if (StringUtils.isNotBlank(codeMembre) && StringUtils.isNotBlank(typeNn)) {
			String codeCompte = "";
			String codeProduit = "";
			switch (typeNn) {
			case "CNCS":
				codeProduit = "CNCSnr";
				codeCompte = "NN-T" + typeNn + "-" + codeMembre;
				break;
			case "PaR":
				codeProduit = "PaR";
				codeCompte = "NN-T" + typeNn + "-" + codeMembre;
				break;
			default:
				codeProduit = "GCP";
				codeCompte = "NN-TPA" + typeNn + "-" + codeMembre;
				break;
			}
			String codeComptets = "NN-TS" + typeNn + "-" + codeMembre;
			EuCompte compte = compteService.findCompteById(codeCompte);
			EuCompte comptets = compteService.findCompteById(codeComptets);
			if (Objects.nonNull(compte)) {
				List<EuCompteCredit> ccs = compteCreditService.findbyCompteAndProduit(codeCompte, codeProduit);
				double som = ccs.stream().mapToDouble(EuCompteCredit::getMontantCredit).sum();
				if (compte.getSolde() >= montant && som >= montant) {
					try {
						EuCategorieCompte catCompte = new EuCategorieCompte(ServerUtil.getTypeCompte(codeCompte));
						Long idOp = operationService.getLastOperation() + 1;

						Date date = new Date();
						EuOperation op = new EuOperation();
						op.setEuCategorieCompte(catCompte);
						op.setCodeProduit(codeProduit);
						op.setDateOp(date);
						op.setHeureOp(date);
						op.setLibOp("Transfert Bon de Consommation");
						op.setTypeOp("TBC");
						op.setMontantOp(montant);
						if (ServerUtil.isPhysique(codeMembre)) {
							op.setEuMembre(compte.getEuMembre());
						} else {
							op.setEuMembreMorale(compte.getEuMembreMorale());
						}
						op.setIdOperation(idOp);
						op.setEuUtilisateur(null);
						operationService.add(op);

						Optional<EuBon> opBon = bonService.emettreBon("BA", codeMembre, montant);
						if (opBon.isPresent()) {
							EuBon bon = opBon.get();
							double mont_a_deduire = montant;
							int compteur = 0;
							while (mont_a_deduire > 0) {
								EuCompteCredit credit = ccs.get(compteur);
								if (credit.getMontantCredit() > mont_a_deduire) {
									EuCompteCreditTs creditts = new EuCompteCreditTs();
									creditts.setMontant(mont_a_deduire);
									creditts.setEuCompteCredit(credit);
									creditts.setDateCreditTs(date);
									creditts.setEuCompte(comptets);
									creditts.setEuBon(bon);
									compteCreditTsService.add(creditts);

									credit.setMontantCredit(credit.getMontantCredit() - mont_a_deduire);
									compteCreditService.update(credit);

									mont_a_deduire = 0;
								} else {
									EuCompteCreditTs creditts = new EuCompteCreditTs();
									creditts.setMontant(credit.getMontantCredit());
									creditts.setEuCompteCredit(credit);
									creditts.setDateCreditTs(date);
									creditts.setEuCompte(comptets);
									creditts.setEuBon(bon);
									compteCreditTsService.add(creditts);

									mont_a_deduire -= credit.getMontantCredit();
									credit.setMontantCredit(0.0);
									compteCreditService.update(credit);

									compteur++;
								}
							}

							compte.setSolde(compte.getSolde() - montant);
							compteService.update(compte);

							if (Objects.nonNull(comptets)) {
								comptets.setSolde(comptets.getSolde() + montant);
								compteService.update(comptets);
							} else {
								comptets = new EuCompte();
								comptets.setSolde(montant);
								comptets.setCodeCompte(codeComptets);
								comptets.setDateAlloc(date);
								if (ServerUtil.isPhysique(codeMembre)) {
									comptets.setEuMembre(compte.getEuMembre());
								} else {
									comptets.setEuMembreMorale(compte.getEuMembreMorale());
								}
								comptets.setDesactiver("0");
								comptets.setLibCompte("Compte de Transaction");
								comptets.setEuTypeCompte(new EuTypeCompte("NN"));
								comptets.setEuCategorieCompte(new EuCategorieCompte("TS" + typeNn));
								compteService.create(comptets);
							}
							return bon;
						}

					} catch (Exception e) {
						logger.error("Erreur d'exécution du transfert NN ", e);
						return null;
					}
				}
			}
		}
		return null;
	}

	@Transactional(rollbackFor = Exception.class)
	public EuBon transfertNr(String codeMembre, String typeNr, double montant) {
		if (StringUtils.isNotBlank(codeMembre) && StringUtils.isNotBlank(typeNr)) {
			String codeCompte = "";
			String codeProduit = "";
			String lib_op;
			switch (typeNr) {
			case "CNCS":
				if (codeMembre.endsWith("P")) {
					codeProduit = "CNCSnr";
					codeCompte = "NR-T" + typeNr + "-" + codeMembre;
					lib_op = "Transfert de salaire";
				} else {
					codeProduit = "CNCSnr";
					codeCompte = "NR-TPN" + "-" + codeMembre;
					lib_op = "Transfert de salaire";
				}
				break;
			default:
				codeProduit = "Inr";
				codeCompte = "NR-T" + typeNr + "-" + codeMembre;
				lib_op = "Transfert d'investissement de subvention";
				break;
			}
			String codeComptets = "NR-TS" + typeNr + "-" + codeMembre;
			if (typeNr.equals("CNCS") && codeMembre.endsWith("M")) {
				codeComptets = "NR-TSPN-" + codeMembre;
			}

			EuCompte compte = compteService.findCompteById(codeCompte);
			EuCompte comptets = compteService.findCompteById(codeComptets);
			if (Objects.nonNull(compte)) {
				List<EuCompteCredit> ccs = compteCreditService.findbyCompteAndProduit(codeCompte, codeProduit);
				double som = ccs.stream().mapToDouble(EuCompteCredit::getMontantCredit).sum();
				if (compte.getSolde() >= montant && som >= montant) {
					try {
						EuCategorieCompte catCompte = new EuCategorieCompte(ServerUtil.getTypeCompte(codeCompte));
						Long idOp = operationService.getLastOperation() + 1;

						Date date = new Date();
						EuOperation op = new EuOperation();
						op.setEuCategorieCompte(catCompte);
						op.setCodeProduit(codeProduit);
						op.setDateOp(date);
						op.setHeureOp(date);
						op.setLibOp(lib_op);
						op.setTypeOp("TNR");
						op.setMontantOp(montant);
						if (ServerUtil.isPhysique(codeMembre)) {
							op.setEuMembre(compte.getEuMembre());
						} else {
							op.setEuMembreMorale(compte.getEuMembreMorale());
						}
						op.setIdOperation(idOp);
						op.setEuUtilisateur(null);
						operationService.add(op);

						Optional<EuBon> opBon = bonService.emettreBon("BS", codeMembre, montant);
						if (opBon.isPresent()) {
							EuBon bon = opBon.get();
							double mont_a_deduire = montant;
							int compteur = 0;
							while (mont_a_deduire > 0) {
								EuCompteCredit credit = ccs.get(compteur);
								if (credit.getMontantCredit() > mont_a_deduire) {
									EuCompteCreditTs creditts = new EuCompteCreditTs();
									creditts.setMontant(mont_a_deduire);
									creditts.setEuCompteCredit(credit);
									creditts.setDateCreditTs(date);
									creditts.setEuCompte(comptets);
									creditts.setEuBon(bon);
									compteCreditTsService.add(creditts);

									credit.setMontantCredit(credit.getMontantCredit() - mont_a_deduire);
									compteCreditService.update(credit);

									mont_a_deduire = 0;
								} else {
									EuCompteCreditTs creditts = new EuCompteCreditTs();
									creditts.setMontant(credit.getMontantCredit());
									creditts.setEuCompteCredit(credit);
									creditts.setDateCreditTs(date);
									creditts.setEuCompte(comptets);
									creditts.setEuBon(bon);
									compteCreditTsService.add(creditts);

									mont_a_deduire -= credit.getMontantCredit();
									credit.setMontantCredit(0.0);
									compteCreditService.update(credit);

									compteur++;
								}
							}

							compte.setSolde(compte.getSolde() - montant);
							compteService.update(compte);

							if (Objects.nonNull(comptets)) {
								comptets.setSolde(comptets.getSolde() + montant);
								compteService.update(comptets);
							} else {
								comptets = new EuCompte();
								comptets.setSolde(montant);
								comptets.setCodeCompte(codeComptets);
								comptets.setDateAlloc(date);
								if (ServerUtil.isPhysique(codeMembre)) {
									comptets.setEuMembre(compte.getEuMembre());
								} else {
									comptets.setEuMembreMorale(compte.getEuMembreMorale());
								}
								comptets.setDesactiver("0");
								comptets.setLibCompte("Compte de Transaction");
								comptets.setEuTypeCompte(new EuTypeCompte("NR"));
								comptets.setEuCategorieCompte(new EuCategorieCompte("TS" + typeNr));
								compteService.create(comptets);
							}
							System.out.println("Apres -> Solde du compte : " + compte.getSolde()
									+ "<-> Solde du compte TS : " + comptets.getSolde());
							return bon;
						}

					} catch (Exception e) {
						logger.error("Une erreur est survenue pendant le transfert du NR ", e);
						throw e;
					}
				}
			}
		}
		return null;
	}

}

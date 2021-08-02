/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.components;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.exception.CompteNonIntegreException;
import com.esmc.mcnp.exception.CompteNonTrouveException;
import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dto.bc.Apa;
import com.esmc.mcnp.dto.bc.CalculBonInfo;
import com.esmc.mcnp.exception.SoldeInsuffisantException;
import com.esmc.mcnp.model.acteur.EuActeur;
import com.esmc.mcnp.model.op.EuAppelNn;
import com.esmc.mcnp.model.op.EuAppelOffre;
import com.esmc.mcnp.model.oi.EuBnpSqmax;
import com.esmc.mcnp.model.ba.EuCapa;
import com.esmc.mcnp.model.ba.EuCapaTs;
import com.esmc.mcnp.model.oi.EuCaps;
import com.esmc.mcnp.model.bc.EuDetailDomicilie;
import com.esmc.mcnp.model.bc.EuDetailDomiciliePK;
import com.esmc.mcnp.model.bc.EuDomiciliation;
import com.esmc.mcnp.model.smcipn.EuFn;
import com.esmc.mcnp.model.others.EuFrais;
import com.esmc.mcnp.model.smcipn.EuGcsc;
import com.esmc.mcnp.model.mprg.EuKrr;
import com.esmc.mcnp.model.others.EuOperation;
import com.esmc.mcnp.model.bc.EuProduit;
import com.esmc.mcnp.model.others.EuProposition;
import com.esmc.mcnp.model.smcipn.EuSmc;
import com.esmc.mcnp.model.cm.EuCategorieCompte;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.cm.EuCompteCreditCapa;
import com.esmc.mcnp.model.cm.EuCompteCreditCapaPK;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.cm.EuTypeCompte;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.model.smcipn.EuServir;
import com.esmc.mcnp.model.smcipn.EuSmcipnpwi;
import com.esmc.mcnp.model.smcipn.EuUtiliser;
import com.esmc.mcnp.repositories.others.EuAppelNnRepository;
import com.esmc.mcnp.repositories.others.EuDetailDomiciliationRepository;
import com.esmc.mcnp.repositories.others.EuDomiciliationRepository;
import com.esmc.mcnp.repositories.acteurs.EuActeurRepository;
import com.esmc.mcnp.repositories.ba.EuCapaRepository;
import com.esmc.mcnp.repositories.ba.EuCapaTsRepository;
import com.esmc.mcnp.repositories.cm.EuCompteCreditCapaRepository;
import com.esmc.mcnp.repositories.cm.EuCompteCreditRepository;
import com.esmc.mcnp.repositories.cm.EuCompteRepository;
import com.esmc.mcnp.repositories.cm.EuMembreMoraleRepository;
import com.esmc.mcnp.repositories.cm.EuMembreRepository;
import com.esmc.mcnp.repositories.common.EuFraisRepository;
import com.esmc.mcnp.repositories.common.EuOperationRepository;
import com.esmc.mcnp.repositories.common.EuSqmaxRepository;
import com.esmc.mcnp.repositories.mprg.EuKrrRepository;
import com.esmc.mcnp.repositories.obps.EuGcscRepository;
import com.esmc.mcnp.repositories.oi.EuCapsRepository;
import com.esmc.mcnp.repositories.smcipn.EuAppelOffreRepository;
import com.esmc.mcnp.repositories.smcipn.EuFnRepository;
import com.esmc.mcnp.repositories.smcipn.EuPropositionRepository;
import com.esmc.mcnp.repositories.smcipn.EuServirRepository;
import com.esmc.mcnp.repositories.smcipn.EuSmcRepository;
import com.esmc.mcnp.repositories.smcipn.EuSmcipnwiRepository;
import com.esmc.mcnp.repositories.smcipn.EuUtiliserRepository;
import com.esmc.mcnp.services.setting.EuParametresService;

/**
 * Classe effectuant les CAPA
 *
 * @author Mawuli AKLASSOU
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ApaServiceImpl implements ApaService {

	private @Autowired EuParametresService paramService;
	private @Autowired EuPropositionRepository proposService;
	private @Autowired CreditComponent creditService;
	private @Autowired EuGcscRepository gcscRepo;
	private @Autowired EuCompteRepository compteRepo;
	private @Autowired EuCompteCreditRepository ccreditRepo;
	private @Autowired EuSqmaxRepository sqmaxRepo;
	private @Autowired EuSmcipnwiRepository smcipnRepo;
	private @Autowired EuDomiciliationRepository domiRepo;
	private @Autowired EuCapsRepository capsRepo;
	private @Autowired EuFnRepository fnRepo;
	private @Autowired EuOperationRepository opRepo;
	private @Autowired EuCapaRepository capaRepo;
	private @Autowired EuSmcRepository smcRepo;
	private @Autowired EuActeurRepository acteurRepo;
	private @Autowired EuCompteCreditCapaRepository ccCapaRepo;
	private @Autowired EuMembreRepository membreRepo;
	private @Autowired EuAppelNnRepository appelRepo;
	private @Autowired EuMembreMoraleRepository moralRepo;
	private @Autowired EuFraisRepository fraisRepo;
	private @Autowired EuServirRepository servirRepo;
	private @Autowired EuUtiliserRepository utiliserRepo;
	private @Autowired EuAppelOffreRepository offreRepo;
	private @Autowired EuDetailDomiciliationRepository detDomiRepo;
	private @Autowired EuCapaTsRepository capaTsRepo;
	private @Autowired EuKrrRepository krrRepo;
	private @Autowired CreditUtility creditUtility;

	/**
	 * Methode pour le mecanisme APA RPG recurrent et non recurrent
	 *
	 * @param apa
	 *            : Object contenant des informations sur l'operation APA
	 * @return String: chaîne de caractères
	 */
	@Override
	public boolean doApaRPG(Apa apa) {
		double credit = 0;
		double creditSqmax = 0;
		double capa = 0;
		double sqmax = 0;
		double quota = 0;
		double mont_capa_eff = 0;
		double credit_fs = 0;
		double panu_fs = 0;
		int periode = paramService.getParam("periode", "valeur");
		EuMembre membre = null;
		EuCompte compte, compteNn = new EuCompte();
		if (StringUtils.isNotBlank(apa.getCodeMembre()) && apa.getCodeMembre().endsWith("P")
				&& StringUtils.isNotBlank(apa.getTypeBon()) && apa.getTypeBon().equals("RPG")) {
			try {

				if (StringUtils.isBlank(apa.getCodeBa())) {
					throw new CompteNonTrouveException("Vous devez fournir le code du Bon BAn");
				}
				if (StringUtils.isBlank(apa.getCatBon())) {
					return false;
				}
				String codeProduit = apa.getTypeBon() + apa.getCatBon();

				String codeCompteRPG = "NB-TPAGCRPG-" + apa.getCodeMembre();
				compte = compteRepo.findCompteById(codeCompteRPG);
				if (!Objects.nonNull(compte)) {
					throw new CompteNonTrouveException(
							"Le Numéro du Compte Acheteur " + codeCompteRPG + " est incorrect");
				}
				// verification de mode d'achat du CAPA
				membre = compte.getEuMembre();
				if (!Objects.nonNull(membre)) {
					membre = membreRepo.findOne(apa.getCodeMembre());
				}

				System.out.println("Verification du compte NN:");
				capa = apa.getMontApa();
				String codeCompteNn = "";
				codeCompteNn = "NN-TSCAPA-" + apa.getCodeMembre();
				compteNn = compteRepo.findOne(codeCompteNn);
				if (!Objects.nonNull(compteNn)) {
					throw new CompteNonTrouveException("Le compte " + codeCompteNn + " n'existe pas.");
				}

				double soldeTs = 0;
				List<EuCapaTs> capats = capaTsRepo.findByEuBon_BonNumero(apa.getCodeBa());
				soldeTs = capats.stream().mapToDouble(EuCapaTs::getMontantSolde).sum();
				if (compteNn.getSolde() < soldeTs) {
					throw new SoldeInsuffisantException();
				}

				if ((compteNn.getSolde() < capa) || soldeTs < capa) {
					throw new SoldeInsuffisantException(
							"Le Solde du compte NN Bon d'achat de souscription est Insuffisant");
				}

				double mont_op = capa;
				EuCaps caps = new EuCaps();
				boolean enroller = false;
				boolean caps_lie = false;

				/**
				 * Vérification du type de crédit (récurrent ou non récurrent) et calcul du
				 * montant du crédit
				 */
				if (apa.getCatBon().equals("r")) {
					System.out.println("CAPA Récurrent:");
					codeProduit = "RPGr";
					double reste_capa_eff = 0;

					quota = paramService.getParametre("quota", "RPGr");
					List<EuCompteCredit> ccef = ListUtils
							.emptyIfNull(ccreditRepo.findbyCompteAndProduit(codeCompteRPG, codeProduit));
					if (!ccef.isEmpty()) {
						mont_capa_eff = ccef.stream().filter(c -> c.getNbreRenouvel() != 0)
								.mapToDouble(EuCompteCredit::getMontantPlace).sum();
					}

					if (mont_capa_eff > 0) {
						if (quota > mont_capa_eff) {
							reste_capa_eff = quota - mont_capa_eff;
							if (capa > reste_capa_eff) {
								sqmax = capa - reste_capa_eff;
								capa = reste_capa_eff;
								credit = Math
										.floor(creditUtility.calculBonConso(
												new CalculBonInfo(apa.getCatBon(), apa.getTypeRecurrent(),
														apa.getTypeProduit(), apa.getPrk(), apa.getDureeInvest()),
												capa));
								creditSqmax = Math
										.floor(creditUtility.calculBonConso(
												new CalculBonInfo(apa.getCatBon(), apa.getTypeRecurrent(),
														apa.getTypeProduit(), apa.getPrk(), apa.getDureeInvest()),
												sqmax) / 4);
							} else {
								credit = Math
										.floor(creditUtility.calculBonConso(
												new CalculBonInfo(apa.getCatBon(), apa.getTypeRecurrent(),
														apa.getTypeProduit(), apa.getPrk(), apa.getDureeInvest()),
												capa));
							}
						} else {
							sqmax = capa;
							capa = 0;
							creditSqmax = Math
									.floor(creditUtility
											.calculBonConso(
													new CalculBonInfo(apa.getCatBon(), apa.getTypeRecurrent(),
															apa.getTypeProduit(), apa.getPrk(), apa.getDureeInvest()),
													sqmax)
											/ 4);
						}
					} else {
						credit = Math.floor(
								creditUtility.calculBonConso(new CalculBonInfo(apa.getCatBon(), apa.getTypeRecurrent(),
										apa.getTypeProduit(), apa.getPrk(), apa.getDureeInvest()), capa));
					}
				} else {
					codeProduit = "RPGnr";
					credit = Math.floor(creditUtility.calculBonConso(new CalculBonInfo(apa.getCatBon(),
							apa.getTypeRecurrent(), apa.getTypeProduit(), apa.getPrk(), apa.getDureeInvest()), capa));
				}

				if (credit > 0) {
					double mont_bc = creditUtility.calculateCapaStandard(capa);
					double mont_bcp = 0;
					double tbcp = 0;
					if (apa.getCatBon().equals("r")) {
						tbcp = paramService.getParametre("TBCP", "valeur");
						mont_bcp = mont_bc * tbcp / 100;
						mont_bc = credit;
						if (StringUtils.isNotBlank(membre.getAutoEnroler())
								&& membre.getAutoEnroler().equalsIgnoreCase("N")) {
							enroller = true;
							caps = capsRepo.findByEuMembre2_CodeMembre(apa.getCodeMembre());
							if (Objects.nonNull(caps)) {
								if (Objects.nonNull(caps.getEuCompteCredit())) {
									caps_lie = true;
								} else {
									credit_fs = paramService.getParametre("PCAPS", "valeur");
									mont_bc = mont_bc - credit_fs;
									System.out.println(
											credit + ":" + mont_bc + ":" + mont_bcp + ":" + credit_fs + ":" + panu_fs);
								}
							}
						}
					}

					EuCategorieCompte catCompte = new EuCategorieCompte();
					catCompte.setCodeCat("TPAGCRPG");
					Long idOp = opRepo.getLastOperationInsertedId() + 1;

					Date date = new Date();
					EuOperation op = new EuOperation();
					op.setEuCategorieCompte(catCompte);
					op.setCodeProduit(codeProduit);
					op.setDateOp(date);
					op.setHeureOp(date);
					op.setLibOp("CAPA RPG");
					op.setTypeOp("APA");
					op.setMontantOp(mont_op);
					op.setEuMembre(membre);
					op.setIdOperation(idOp);
					EuUtilisateur user = new EuUtilisateur();
					if (apa.getIdUtilisateur() != null) {
						user.setIdUtilisateur(apa.getIdUtilisateur());
						op.setEuUtilisateur(user);
					}
					opRepo.save(op);
					System.out.println("OK OP");

					compte.setSolde(compte.getSolde() + mont_bc);
					compteRepo.save(compte);

					EuProduit produit = new EuProduit();
					produit.setCodeProduit(codeProduit);
					String c_source = null;
					c_source = compteNn.getCodeCompte();
					EuCompteCredit cc = creditService.createCredit(compte, produit, capa, mont_bc, false, null, op,
							apa.getPrk(), c_source, apa);
					
					if (Objects.nonNull(cc)) {
						System.out.println("Jusqu'ici tout va bien 1");
						creditService.updateListCapaTs(capats, cc, capa, date, apa.getCatBon());

						compteNn.setSolde(compteNn.getSolde() - capa);
						compteRepo.save(compteNn);

						if (enroller && !caps_lie) {
							System.out.println("Debut BNP CAPS");
							caps.setIndexer(1);
							caps.setEuCompteCredit(cc);
							caps.setPeriode(1);
							caps.setPanu(0);
							caps.setMontFs(caps.getMontFs() + credit_fs);
							caps.setMontPanuFs(0);
							capsRepo.saveAndFlush(caps);

							if (Objects.nonNull(caps.getEuMembre1())) {
								EuCompte compte_fs = creditService.saveCompte(caps.getEuMembre1(), "TFS", "NB",
										credit_fs);
								produit = new EuProduit();
								produit.setCodeProduit("FS");
								creditService.createCredit(compte_fs, produit, caps.getMontCaps(), credit_fs, true,
										caps.getCodeCaps(), op, 1, String.valueOf(cc.getIdCredit()), apa);

							} else {
								EuCompte compte_fs = creditService.saveCompte(caps.getEuMembreMorale(), "TFS", "NB",
										credit_fs);
								produit = new EuProduit();
								produit.setCodeProduit("FS");
								creditService.createCredit(compte_fs, produit, caps.getMontCaps(), credit_fs, true,
										caps.getCodeCaps(), op, 1, String.valueOf(cc.getIdCredit()), apa);

							}
						}

						EuKrr krr = new EuKrr();
						krr.setTypeKrr("krrBCRI");
						krr.setDateDemande(date);
						krr.setDateEchue(null);
						krr.setDateRenouveller(DateUtils.addDays(date, periode));
						krr.setEuMembre(membre);
						krr.setEuMembreMorale(null);
						krr.setEuProduit(produit);
						krr.setIdCredit(cc.getIdCredit());
						krr.setMontCapa(capa);
						krr.setMontKrr(mont_bcp * paramService.getParam("periode", "RBNP"));
						krr.setMontReconst(mont_bcp);
						krrRepo.save(krr);

					} else {
						throw new RuntimeException("Echec de la création du Bon");
					}

					if (codeProduit.equalsIgnoreCase("RPGr") && creditSqmax > 0) {

						compte.setSolde(compte.getSolde() + creditSqmax);
						compteRepo.save(compte);

						Long idSqmax = 1L;
						if (sqmaxRepo.count() > 0) {
							idSqmax += sqmaxRepo.findLastSQMaxIdInserted();
						}

						EuProduit produit_sqmax = new EuProduit();
						produit_sqmax.setCodeProduit(codeProduit);
						EuCompteCredit cc_sqmax = creditService.createCredit(compte, produit_sqmax, sqmax, creditSqmax,
								true, idSqmax.toString(), op, apa.getPrk(), "SQMAXUI", apa);
						if (cc_sqmax != null && credit <= 0) {
							System.out.println("Jusqu'ici tout va bien 1");
							creditService.updateListCapaTs(capats, cc, capa, date, apa.getCatBon());

							compteNn.setSolde(compteNn.getSolde() - capa);
							compteRepo.save(compteNn);
							System.out.println("Jusqu'ici tout va bien NN");
						} else {
							throw new RuntimeException("Echec de l'exécution");
						}

						EuBnpSqmax bnp_sqmax = new EuBnpSqmax();
						bnp_sqmax.setIdSqmax(idSqmax);
						bnp_sqmax.setEuMembre(membre);
						bnp_sqmax.setMontant(sqmax);
						bnp_sqmax.setEuCategorieCompte(compte.getEuCategorieCompte());
						bnp_sqmax.setIdCredit(cc.getIdCredit());
						sqmaxRepo.save(bnp_sqmax);

						if (compteNn.getCodeCompte().startsWith("NN-CAPA-")) {
							List<EuCapa> capas = capaRepo.findByEuCompte_CodeCompte(compteNn.getCodeCompte());
							creditService.updateListCapa(capas, cc, sqmax, date, apa.getCatBon());
						}

						compteNn.setSolde(compteNn.getSolde() - sqmax);
						compteRepo.save(compteNn);
						System.out.println("Jusqu'ici tout va bien SQMAX");
					}
					return true;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				throw e;
			}
		}
		return false;
	}

	/**
	 * Méthode le mécanisme de CAPA Ir et Inr
	 *
	 * @param apa
	 *            Objet APA fournissant les infos sur l'opération à effectuer
	 * @return String chaîne de caractères exprimant si l'opération a été effectuée
	 */
	@Override
	public boolean doApaI(Apa apa) {
		String codeProduit;
		double credit = 0;
		double capa = apa.getMontApa();
		double prk = 1;
		EuMembreMorale membre = new EuMembreMorale();
		EuCompte compte, compteNn = new EuCompte();

		if (apa.getTypeBon().equals("I")) {
			if (apa.getCodeMembre().endsWith("M")) {
				String codeCat = "TPAGCI";
				codeProduit = apa.getTypeBon() + apa.getCatBon();
				String codeCompteNn = "NN-TSCAPA-" + apa.getCodeMembre();
				double soldeCapaTs = 0;
				List<EuCapaTs> capats = capaTsRepo.findByEuBon_BonNumero(apa.getCodeBa());
				soldeCapaTs = capats.stream().mapToDouble(EuCapaTs::getMontantSolde).sum();

				compteNn = compteRepo.findOne(codeCompteNn);
				if (!Objects.nonNull(compteNn)) {
					throw new CompteNonTrouveException();
				}
				if (soldeCapaTs > compteNn.getSolde()) {
					throw new CompteNonIntegreException();
				}
				if (compteNn.getSolde() < capa) {
					throw new SoldeInsuffisantException();
				}

				String codeCompteI = "NB-" + codeCat + "-" + apa.getCodeMembre();
				System.out.println(codeCat + " :" + codeCompteI);
				compte = compteRepo.findOne(codeCompteI);
				if (compte == null) {
					throw new CompteNonTrouveException();
				}

				// verification de mode d'achat du CAPA
				membre = compte.getEuMembreMorale();
				if (membre == null) {
					membre = moralRepo.findOne(apa.getCodeMembre());
				}

				// Verification du type de credit (recurrent ou non
				// recurrent) et calcul du montant de crdit
				if (apa.getCatBon().equals("r")) {
					System.out.println("Credit récutrrent :");
					codeProduit = "Ir";
					credit = Math.floor(creditUtility.calculBonConso(new CalculBonInfo(apa.getCatBon(),
							apa.getTypeRecurrent(), apa.getTypeProduit(), apa.getPrk(), apa.getDureeInvest()), capa));
					System.out.println("Montant :" + credit);
				} else {
					System.out.println("Credit Non récutrrent :");
					codeProduit = apa.getTypeBon() + apa.getCatBon();
					credit = Math.floor(creditUtility.calculBonConso(new CalculBonInfo(apa.getCatBon(),
							apa.getTypeRecurrent(), apa.getTypeProduit(), apa.getPrk(), apa.getDureeInvest()), capa));
					System.out.println("Montant :" + credit);
				}

				EuCategorieCompte catCompte = new EuCategorieCompte(codeCat);
				long idOp = opRepo.getLastOperationInsertedId() + 1;
				Date date = new Date();
				EuOperation op = new EuOperation();
				op.setEuCategorieCompte(catCompte);
				op.setCodeProduit(codeProduit);
				op.setDateOp(date);
				op.setLibOp("CAPA I");
				op.setTypeOp("APA");
				op.setMontantOp(capa);
				op.setEuMembreMorale(membre);
				op.setIdOperation(idOp);
				EuUtilisateur user = new EuUtilisateur();
				if (apa.getIdUtilisateur() != null) {
					user.setIdUtilisateur(apa.getIdUtilisateur());
					op.setEuUtilisateur(user);
				}
				op.setHeureOp(date);
				opRepo.save(op);
				String c_source = null;
				c_source = compteNn.getCodeCompte();

				compte.setSolde(compte.getSolde() + credit);
				compteRepo.save(compte);

				EuProduit produit = new EuProduit();
				produit.setCodeProduit(codeProduit);
				EuCompteCredit cc = creditService.createCredit(compte, produit, capa, credit, false, null, op, prk,
						c_source, apa);

				if (codeCat.equals("TSCAPA")) {
					creditService.updateListCapaTs(capats, cc, capa, date, apa.getCatBon());
				}
				compteNn.setSolde(compteNn.getSolde() - apa.getMontApa());
				compteRepo.save(compteNn);
				System.out.println("Jusqu'ici tout va bien NN");
				return true;
			} else {
				throw new IllegalArgumentException("Ce membre n'est pas autorisé à effectuer cette opération.");
			}
		} else {
			throw new IllegalArgumentException("Vous vous êtes tromper d'operation.");
		}
	}

	/**
	 * Methode pour le mecanisme APA RPG non recurrent PRE
	 *
	 * @param apa
	 *            Object contenant des informations sur l'operation APA
	 * @return String chaîne de caractères
	 */
	@Override
	public boolean doApaRPGPre(Apa apa) {
		String codeProduit;
		if (apa != null) {
			double pck = paramService.getParametre("pck", "nr");
			double prk = apa.getPrk();
			double capa = apa.getMontApa();
			double credit = capa / pck;
			EuMembre membre = new EuMembre();
			EuCompte compte, compteNn = new EuCompte();
			if (apa.getTypeBon().equals("RPG")) {
				if (apa.getCodeMembre().endsWith("P")) {
					String codeCompteRPG = "NB-TPAGCRPG-" + apa.getCodeMembre();
					compte = compteRepo.findOne(codeCompteRPG);
					if (compte == null) {
						throw new CompteNonTrouveException();
					}

					codeProduit = "RPGnrPRE";
					// verification de mode d'achat du CAPA
					membre = compte.getEuMembre();
					if (membre == null) {
						membre = membreRepo.findOne(apa.getCodeMembre());
					}
					capa = apa.getMontApa();
					String codeCompteNn = "NN-TSCAPA-" + apa.getCodeMembre();
					compteNn = compteRepo.findOne(codeCompteNn);
					if (compteNn != null) {
						if (compteNn.getSolde() < capa) {
							throw new SoldeInsuffisantException();
						}
					} else {
						throw new CompteNonTrouveException();
					}

					List<EuCapaTs> capats = capaTsRepo.findByEuBon_BonNumero(apa.getCodeBa());
					double somme_nn = capats.stream().mapToDouble(EuCapaTs::getMontantSolde).sum();
					if (somme_nn < capa) {
						throw new SoldeInsuffisantException();
					}

					EuCategorieCompte catCompte = new EuCategorieCompte();
					catCompte.setCodeCat("TPAGCRPG");
					double mont_op = capa;
					long idOp = opRepo.getLastOperationInsertedId() + 1;
					Date date = new Date();
					EuOperation op = new EuOperation();
					op.setEuCategorieCompte(catCompte);
					op.setCodeProduit(codeProduit);
					op.setDateOp(date);
					op.setLibOp("CAPA RPG");
					op.setTypeOp("APA");
					op.setMontantOp(mont_op);
					op.setEuMembre(membre);
					op.setIdOperation(idOp);
					EuUtilisateur user = new EuUtilisateur();
					if (apa.getIdUtilisateur() != null) {
						user.setIdUtilisateur(apa.getIdUtilisateur());
						op.setEuUtilisateur(user);
					}
					op.setHeureOp(date);
					opRepo.save(op);

					if (credit > 0) {

						compte.setSolde(compte.getSolde() + credit);
						compteRepo.save(compte);

						EuProduit produit = new EuProduit();
						produit.setCodeProduit(codeProduit);
						String c_source = null;
						c_source = compteNn.getCodeCompte();
						EuCompteCredit cc = creditService.createCredit(compte, produit, capa, credit, false, null, op,
								prk, c_source, apa);
						System.out.println("OK credit");
						if (cc != null) {
							System.out.println("Jusqu'ici tout va bien 1");
							creditService.updateListCapaTs(capats, cc, capa, date, apa.getCatBon());

							compteNn.setSolde(compteNn.getSolde() - capa);
							compteRepo.save(compteNn);
							System.out.println("Jusqu'ici tout va bien NN");
						} else {
							throw new RuntimeException("Echec de la création du Bon");
						}
					}
					return true;
				} else {
					throw new IllegalArgumentException("Ce membre n'est pas autorisé à effectuer cette opération.");
				}
			} else {
				throw new IllegalArgumentException("Vous n'avez pas fourni le compte adequat");
			}
		}
		return false;
	}

	/**
	 * Méthode du mécanisme de CAPA InrPRE
	 *
	 * @param apa
	 *            Objet APA fournissant les infos sur l'opération à effectuer
	 * @return String chaîne de caractères exprimant si l'opération a été effectuée
	 */
	@Override
	public String doApaInrPre(Apa apa) {
		String rep = "Echec de l'opération";
		String codeProduit = "InrPRE";
		double credit = 0;
		double capa = 0;
		double mont_invest = apa.getMontInvest();
		double mont_nn_col = 0;
		double prk = 1;
		int duree = apa.getDureeInvest();
		double pck = paramService.getParametre("pck", "nr");

		EuMembreMorale membre = null;
		EuCompte compte, compteNN = new EuCompte();
		if (apa.getTypeBon().equals("I") && !apa.getNumeroAppelOffre().isEmpty()) {
			if (!apa.getCodeMembre().isEmpty() && apa.getCodeMembre().endsWith("M")) {
				try {
					String codeCompte = "NB-TPAGCI-" + apa.getCodeMembre();
					compte = compteRepo.findOne(codeCompte);
					if (compte == null) {
						return "Compte acheteur Introuvable";
					}

					// verification de mode d'achat du CAPA
					membre = compte.getEuMembreMorale();
					if (membre == null) {
						membre = moralRepo.findOne(apa.getCodeMembre());
					}
					EuActeur acteur_a = acteurRepo.findByCodeMembre(apa.getCodeMembre());
					if (acteur_a == null) {
						return "Cet acteur n'existe pas";
					} else if (!acteur_a.getTypeActeur().equalsIgnoreCase("gac_surveillance")) {
						return "Cet acteur n'est pas la GAC Surveillance";
					}
					EuAppelOffre offre = offreRepo.findAppelOffresByNumero(apa.getNumeroAppelOffre());
					if (offre == null || !offre.getTypeAppelOffre().equalsIgnoreCase("inrpre")) {
						return "Cette offre n'existe pas ou n'est pas une offre INRPRE";
					}
					EuAppelNn appelNn = appelRepo.findAppelNnByNumeroOffre(apa.getNumeroAppelOffre());
					EuFrais frais = null;
					if (appelNn == null) {
						return "Cet appel d'offre n'existe pas!";
					} else {
						System.out.println(appelNn.getCodeMembreMorale());
						if (appelNn.getAllouer() == 1) {
							return "Cet appel d'offre est déjà alloué!";
						}
						frais = fraisRepo.findByIdProposition(appelNn.getEuProposition().getIdProposition());
						if (frais != null && frais.getDisponible() == 1) {
							mont_invest = frais.getMontProjet();
						} else {
							return "Le projet validé n'existe pas ou n'est pas encore payé!";
						}
						mont_nn_col = appelNn.getMontantNn();
						capa = mont_nn_col;
						duree = appelNn.getEuProposition().getEuAppelOffre().getDureeProjet();
						System.out.println(
								"CAPA NN :" + capa + ", Montant Investissement: " + mont_invest + ", Durée :" + duree);
					}
					String codeCompteNN = null;

					codeCompteNN = "NN-TSCAPA-" + apa.getCodeMembre();
					compteNN = compteRepo.findOne(codeCompteNN);
					if (compteNN != null) {
						if (compteNN.getSolde() < capa) {
							return "Solde du compte NN CAPA Insuffisant";
						}
					} else {
						return "Ce compte n'existe pas!";
					}

					credit = Math.floor(capa * prk / pck);

					System.out.println("OK, jusqu'ici tout va");

					EuCategorieCompte codeCat = new EuCategorieCompte();
					codeCat.setCodeCat("TPAGCI");

					long idOp = opRepo.getLastOperationInsertedId() + 1;
					EuUtilisateur user = new EuUtilisateur();
					Date date = new Date();
					EuOperation op = new EuOperation();
					op.setEuCategorieCompte(codeCat);
					op.setCodeProduit(codeProduit);
					op.setDateOp(date);
					op.setLibOp("CAPA Inr PRE");
					op.setTypeOp("APA");
					op.setIdOperation(idOp);
					op.setEuMembreMorale(membre);
					if (apa.getIdUtilisateur() != null) {
						user.setIdUtilisateur(apa.getIdUtilisateur());
						op.setEuUtilisateur(user);
					}
					op.setMontantOp(mont_nn_col);
					op.setHeureOp(date);
					opRepo.save(op);

					System.out.println("OK, jusqu'à OP tout va");

					String c_source = null;
					c_source = compteNN.getCodeCompte();

					EuProduit produit = null;
					produit = new EuProduit();
					produit.setCodeProduit(codeProduit);
					EuCompteCredit ccredit = creditService.createCreditPre(compte, produit, capa, credit, false, null,
							op, duree, c_source, apa);

					SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
					if (credit > 0) {
						if (ccredit != null) {
							System.out.println("ID Credit : " + ccredit.getIdCredit());
							String newCodeSmcipn = null;
							String codeSmcipn = smcipnRepo.findLastByCodeMembreAndTypeSmcipn(apa.getCodeMembre(),
									"SMCIPNWI");
							if (codeSmcipn == null) {
								newCodeSmcipn = "P" + apa.getCodeMembre() + "0001";
							} else {
								String code = codeSmcipn.substring(codeSmcipn.length() - 4);
								int numsmc = Integer.valueOf(code);
								numsmc++;
								newCodeSmcipn = "P" + apa.getCodeMembre()
										+ StringUtils.leftPad(String.valueOf(numsmc), 4, "0");
							}

							double mont_i = frais.getMontantProposition();
							double mont_smc = mont_invest - mont_i;

							EuSmcipnpwi smcipn = new EuSmcipnpwi();
							smcipn.setCodeSmcipn(newCodeSmcipn);
							smcipn.setCodeMembre(apa.getCodeMembre());
							smcipn.setDateSmcipn(date);
							smcipn.setTypeSmcipn("SMCIPNWI");
							smcipn.setEtatAllocInvestis(1);
							smcipn.setRembourser(0);
							smcipn.setEtatAllocSalaire(1);
							smcipn.setSalaireAlloue(0);
							smcipn.setInvestisAlloue(0);
							smcipn.setMontSalaire(mont_smc);
							smcipn.setMontInvestis(mont_i);
							if (apa.getIdUtilisateur() != null) {
								smcipn.setIdUtilisateur(apa.getIdUtilisateur());
							} else {
								smcipn.setIdUtilisateur(null);
							}
							smcipn.setNumeroAppel(apa.getNumeroAppelOffre());
							smcipn.setTypeNr("nrPRE");
							smcipnRepo.save(smcipn);

							System.out.println("Yaa tout va bien :" + newCodeSmcipn + " credit : " + credit);

							EuDomiciliation domi = new EuDomiciliation();
							domi.setCodeDomicilier("PRE" + dateFormatter.format(date));
							domi.setAccorder("O");
							domi.setCatRessource("nrPRE");
							domi.setDateDomiciliation(date);
							Date date_fin = ServerUtil.ajouterMois(date, duree);
							if (date_fin != null) {
								System.out.println(date_fin);
								domi.setDateEchue(date_fin);
							}
							domi.setDomicilier("O");
							domi.setDureeRenouvellement(duree);
							domi.setResteDuree(duree - 1);
							domi.setEuMembreMorale(membre);
							domi.setCodeMembreAssureur(membre.getCodeMembreMorale());
							domi.setMontantDomicilier(credit);
							domi.setMontantSubvent(mont_invest);
							domi.setTypeDomiciliation("SMCIPNPWI");
							domi.setEuProposition(appelNn.getEuProposition());
							domi.setEuSmcipnpwi(smcipn);
							if (apa.getIdUtilisateur() != null) {
								domi.setEuUtilisateur(user);
							} else {
								domi.setEuUtilisateur(null);
							}
							domiRepo.save(domi);

							EuDetailDomiciliePK pk = new EuDetailDomiciliePK();
							pk.setCodeDomicilier(domi.getCodeDomicilier());
							pk.setIdCredit(ccredit.getIdCredit());

							EuDetailDomicilie detDomi = new EuDetailDomicilie();
							detDomi.setId(pk);
							detDomi.setMontantCredit(ccredit.getMontantCredit());
							detDomi.setEuMembre(null);
							detDomi.setEuMembreMorale(membre);
							detDomi.setUtiliser(1);
							detDomi.setDureeRenouvellement(duree);
							detDomi.setResteDuree(duree - 1);
							detDomiRepo.save(detDomi);

							System.out.println("Yaa tout va bien au domiciliation:");
							long idGcsc = 0;
							if (gcscRepo.count() > 0) {
								idGcsc = gcscRepo.findLastGcscInsertedId() + 1;
							} else {
								idGcsc = 1;
							}

							EuGcsc sc = new EuGcsc();
							sc.setCodeDomicilier(domi.getCodeDomicilier());
							sc.setCredit(credit);
							sc.setDebit(0);
							sc.setSolde(credit);
							sc.setCodeMembre(membre.getCodeMembreMorale());
							sc.setIdGcsc(idGcsc);
							sc.setEuSmcipnpwi(smcipn);
							gcscRepo.save(sc);

							System.out.println("Yaa tout va bien au GCSC");

							EuTypeCompte typeCompte = new EuTypeCompte();
							typeCompte.setCodeTypeCompte("NR");

							System.out.println("Allocation <-> Montant investissement :" + mont_i
									+ " <-> Montant salaire :" + mont_smc);

							String codeCompteTe = "NR-TESMCIPNWI-" + apa.getCodeMembre();
							EuCompte compteTe = compteRepo.findOne(codeCompteTe);
							if (compteTe == null) {
								EuCategorieCompte cat = new EuCategorieCompte();
								cat.setCodeCat("TESMCIPNWI");

								compteTe = new EuCompte();
								compteTe.setCodeCompte(codeCompteTe);
								System.out.println("Debut TSCI");
								compteTe.setDateAlloc(date);
								compteTe.setDesactiver("N");
								compteTe.setEuCategorieCompte(cat);
								compteTe.setEuMembreMorale(membre);
								compteTe.setSolde(mont_invest);
								compteTe.setEuTypeCompte(typeCompte);
								compteTe.setLibCompte("TESMCIPNWI");
								compteTe.setEuMembre(null);
								compteTe.setCardprinteddate(null);
								compteTe.setCardprintediddate(0);
								compteTe.setMifarecard(null);
								compteTe.setNumeroCarte(null);
								compteRepo.save(compteTe);
							} else {
								compteTe.setSolde(compteTe.getSolde() + mont_invest);
								compteRepo.save(compteTe);
							}

							EuCapa eucapa = new EuCapa();
							eucapa.setCodeCapa("CAPAInrPRE" + dateFormatter.format(date));
							eucapa.setDateCapa(date);
							eucapa.setCodeMembre(apa.getCodeMembre());
							eucapa.setCodeProduit("InrPRE");
							eucapa.setTypeCapa("InrPRE");
							eucapa.setOrigineCapa("NN");
							eucapa.setMontantCapa(capa);
							eucapa.setMontantUtiliser(capa);
							eucapa.setMontantSolde(0);
							eucapa.setEuCompte(ccredit.getEuCompte());
							eucapa.setEtatCapa("Actif");
							eucapa.setIdOperation(ccredit.getEuOperation().getIdOperation());
							eucapa.setHeureCapa(date);
							capaRepo.save(eucapa);

							EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
							EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
							ccCapaPK.setCodeCapa(eucapa.getCodeCapa());
							ccCapaPK.setIdCredit(ccredit.getIdCredit());
							ccCapa.setId(ccCapaPK);
							ccCapa.setCodeProduit(ccredit.getEuProduit().getCodeProduit());
							ccCapa.setMontant(eucapa.getMontantUtiliser());
							ccCapaRepo.save(ccCapa);

							long idFn = fnRepo.getLastFnInsertedId() + 1;
							EuFn fn = new EuFn();
							fn.setIdFn(idFn);
							fn.setTypeFn("Inr");
							fn.setDateFn(date);
							fn.setEuCapa(eucapa);
							fn.setMontant(capa);
							fn.setSortie(mont_i);
							fn.setEntree(0);
							fn.setSolde(mont_i);
							fn.setMtSolde(capa - mont_i);
							fn.setCodeDomicilier(domi.getCodeDomicilier());
							fn.setCodeSmcipn(smcipn.getCodeSmcipn());
							fn.setOrigineFn(1);
							fnRepo.save(fn);

							Long idServir = servirRepo.getLastEuServirInsertedId();
							if (idServir != null) {
								idServir = idServir + 1;
							} else {
								idServir = 1L;
							}

							EuServir servir = new EuServir();
							servir.setIdServir(idServir);
							servir.setDateCreation(date);
							servir.setMontantAllouer(mont_i);
							servir.setEuSmcipnpwi(smcipn);
							servir.setEuFn(fn);
							servirRepo.save(servir);

							System.out.println("Yaa tout va bien au FN");

							EuSmc smc = new EuSmc();
							long idsmc = smcRepo.getLastEuSmcInsertedId() + 1;
							smc.setIdSmc(idsmc);
							smc.setDateSmc(date);
							smc.setEuCompteCredit(ccredit);
							smc.setMontant(mont_invest);
							smc.setSortie(mont_smc);
							smc.setSolde(mont_smc);
							smc.setEntree(0);
							smc.setCodeCapa(eucapa.getCodeCapa());
							smc.setCodeSmcipn(codeSmcipn);
							smc.setCodeDomicilier(domi.getCodeDomicilier());
							smc.setTypeSmc("CNCSr");
							smc.setOrigineSmc(1);
							smc.setSourceCredit(ccredit.getSource());
							smc.setMontantSolde(mont_invest - mont_smc);
							smcRepo.save(smc);

							Long idUtiliser = utiliserRepo.findLastUtiliserInsertedId();
							if (idUtiliser != null) {
								idUtiliser++;
							} else {
								idUtiliser = 1L;
							}
							EuUtiliser utiliser = new EuUtiliser();
							utiliser.setIdUtiliser(idUtiliser);
							utiliser.setEuSmc(smc);
							utiliser.setMontantAllouer(mont_smc);
							utiliser.setEuSmcipnpwi(smcipn);
							utiliser.setIdCredit(ccredit.getIdCredit());
							utiliser.setDateCreation(date);
							utiliserRepo.save(utiliser);

							appelNn.setAllouer(1);
							appelRepo.save(appelNn);

							compteNN.setSolde(compteNN.getSolde() - mont_nn_col);
							compteRepo.save(compteNN);
						} else {
							return "Le compte du credit ne peut être nul!";
						}
					} else {
						return "Le montant du credit ne peut être nul!";
					}
					return "L'opération a été effectuée avec succés!";
				} catch (Exception e) {
					return "Erreur d'execution : " + e.getMessage();
				}
			}
		} else {
			return "Veuillez renseigner le numero d'appel d'offre;\n Verifier votre carte!";
		}

		return rep;
	}

	/**
	 * Méthode de CAPA nrPRE (InrPRE Kit technopole et Assurance)
	 *
	 * @param apa
	 *            Objet APA fournissant les infos sur l'opération à effectuer
	 * @return String chaîne de caractères exprimant si l'opération a été effectuée
	 */
	@Override
	public String doApaPreKit(Apa apa) {
		String rep = "Echec de l'opération!";
		if (apa.getTypeBon().equalsIgnoreCase("INRPREKIT") || apa.getTypeBon().equalsIgnoreCase("INRPREASS")) {
			if (apa.getNumeroAppelOffre().isEmpty()) {
				return "Le numéro d'appel d'offre est obligatoire";
			}

			String codeMembre = apa.getCodeMembre();
			EuAppelOffre offre = offreRepo.findAppelOffresByNumero(apa.getNumeroAppelOffre());
			if (offre != null && !offre.getTypeAppelOffre().equals("inrpre")) {
				String codeMembreBenef = offre.getCodeMembreMorale();
				EuMembreMorale benef = moralRepo.findOne(codeMembreBenef);
				EuProposition propos = proposService.findPropositionByNumeroAppelOffre(offre.getIdAppelOffre());
				String codecompteNn = "NN-TSCAPA-" + codeMembre;
				EuCompte compteNn = compteRepo.findOne(codecompteNn);
				if (compteNn != null && compteNn.getSolde() >= apa.getMontApa()) {
					Double soldefn = fnRepo.findSumByOrigineFn();
					if (soldefn >= apa.getMontApa()) {
						Date date = new Date();
						String codeCompteTi = "NR-TI-" + codeMembre;

						Long idOp = opRepo.getLastOperationInsertedId();
						if (idOp == null) {
							idOp = 0L;
						}
						EuCategorieCompte codeCat = new EuCategorieCompte();
						codeCat.setCodeCat("TI");

						EuOperation op = new EuOperation();
						op.setEuCategorieCompte(codeCat);
						op.setCodeProduit("InrPREKIT");
						op.setDateOp(date);
						op.setLibOp("CAPA Inr PRE KIT");
						op.setTypeOp("APA");
						op.setIdOperation(idOp + 1);
						if (apa.getIdUtilisateur() != null) {
							EuUtilisateur user = new EuUtilisateur();
							user.setIdUtilisateur(apa.getIdUtilisateur());
							op.setEuUtilisateur(user);
						}
						op.setHeureOp(date);
						opRepo.save(op);

						EuCompte compteti = compteRepo.findOne(codeCompteTi);
						if (compteti != null) {
							compteti.setSolde(compteti.getSolde() + apa.getMontApa());
							compteRepo.save(compteti);
						} else {
							compteti = new EuCompte();
							compteti.setSolde(apa.getMontApa());
							compteti.setCodeCompte(codeCompteTi);
							compteti.setDateAlloc(date);
							compteti.setEuMembreMorale(compteNn.getEuMembreMorale());
							compteti.setDesactiver("1");
							compteti.setLibCompte("Investissement de subvention");
							EuTypeCompte typecompte = new EuTypeCompte();
							typecompte.setCodeTypeCompte("NR");
							compteti.setEuTypeCompte(typecompte);
							EuCategorieCompte catcompte = new EuCategorieCompte();
							catcompte.setCodeCat("TI");
							catcompte.setCodeTypeCompte("NR");
							compteti.setEuCategorieCompte(catcompte);
							compteRepo.save(compteti);
						}

						EuSmcipnpwi smcipn = smcipnRepo.findByNumeroAppel(apa.getNumeroAppelOffre());
						if (smcipn == null) {
							String newCodeSmcipn = null;
							String codeSmcipn = smcipnRepo.findLastByCodeMembreAndTypeSmcipn(codeMembre, "SMCIPNWI");
							if (codeSmcipn == null) {
								newCodeSmcipn = "P" + codeMembre + "0001";
							} else {
								String code = codeSmcipn.substring(codeSmcipn.length() - 4);
								int numsmc = Integer.valueOf(code);
								numsmc++;
								newCodeSmcipn = "P" + codeMembre + StringUtils.leftPad(String.valueOf(numsmc), 4, "0");
							}
							smcipn = new EuSmcipnpwi();
							smcipn.setCodeSmcipn(newCodeSmcipn);
							smcipn.setCodeMembre(codeMembre);
							smcipn.setDateSmcipn(date);
							smcipn.setTypeSmcipn("SMCIPNWI");
							smcipn.setEtatAllocInvestis(1);
							smcipn.setRembourser(0);
							smcipn.setEtatAllocSalaire(0);
							smcipn.setSalaireAlloue(0);
							smcipn.setMontSalaire(0);
							smcipn.setMontInvestis(apa.getMontApa());
							if (apa.getIdUtilisateur() != null) {
								smcipn.setIdUtilisateur(apa.getIdUtilisateur());
							} else {
								smcipn.setIdUtilisateur(null);
							}
							smcipn.setNumeroAppel(apa.getNumeroAppelOffre());
							smcipn.setTypeNr("nrPRE");
							smcipnRepo.saveAndFlush(smcipn);
						} else {
							smcipn.setEtatAllocInvestis(1);
							smcipn.setMontInvestis(apa.getMontApa());
							smcipn.setInvestisAlloue(0.0);
							smcipnRepo.saveAndFlush(smcipn);
						}

						SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
						EuProduit produit = new EuProduit();
						produit.setCodeProduit(apa.getTypeBon() + apa.getCatBon());

						Long idCredit = ccreditRepo.getLastCreditInsertedId();
						if (idCredit == null) {
							idCredit = 1L;
						} else {
							idCredit = idCredit + 1;
						}
						EuCompteCredit cc = new EuCompteCredit();
						cc.setIdCredit(idCredit);
						cc.setCodeMembre(codeMembre);
						cc.setMontantPlace(apa.getMontApa());
						cc.setMontantCredit(apa.getMontApa());
						cc.setEuOperation(op);
						cc.setEuProduit(produit);
						cc.setCodeTypeCredit(apa.getTypeProduit());
						cc.setSource(codeMembre + dateFormatter.format(date));
						cc.setCompteSource(smcipn.getCodeSmcipn());
						cc.setDateOctroi(date);
						cc.setDatedeb(date);
						Date datefin = ServerUtil.ajouterJours(date, 30);
						cc.setDatefin(datefin);
						cc.setRenouveller("O");
						cc.setBnp(0);
						cc.setKrr("N");
						cc.setEuCompte(compteti);
						cc.setDomicilier(0);
						cc.setNbreRenouvel(0);
						ccreditRepo.save(cc);

						List<EuCapaTs> capas = capaTsRepo.findCapaTsByCompte(compteNn.getCodeCompte());
						if (capas != null) {
							double mont_capa = apa.getMontApa();
							for (EuCapaTs c : capas) {
								if (mont_capa > c.getMontantSolde()) {
									EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
									EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
									ccCapaPK.setCodeCapa(c.getEuCapa().getCodeCapa());
									ccCapaPK.setIdCredit(cc.getIdCredit());
									ccCapa.setId(ccCapaPK);
									ccCapa.setCodeProduit("Inr");
									ccCapa.setMontant(c.getMontantSolde());
									ccCapaRepo.save(ccCapa);

									mont_capa -= c.getMontantSolde();

									c.setMontantUtiliser(c.getMontantUtiliser() + c.getMontantSolde());
									c.setMontantSolde(0.0);
									capaTsRepo.save(c);

								} else {
									EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
									EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
									ccCapaPK.setCodeCapa(c.getEuCapa().getCodeCapa());
									ccCapaPK.setIdCredit(cc.getIdCredit());
									ccCapa.setId(ccCapaPK);
									ccCapa.setCodeProduit("Inr");
									ccCapa.setMontant(mont_capa);
									ccCapaRepo.save(ccCapa);

									c.setMontantUtiliser(c.getMontantUtiliser() + mont_capa);
									c.setMontantSolde(c.getMontantSolde() - mont_capa);
									capaTsRepo.save(c);
									mont_capa = 0;
								}
								if (mont_capa == 0) {
									break;
								}
							}

							double mont_fn = apa.getMontApa();
							List<EuFn> fns = fnRepo.findByOrigineFn();
							if (fns != null) {
								System.out.println("Nbre FNs :" + fns.size());
								int i = 0;
								while (mont_fn > 0) {
									EuFn fn = fns.get(i);
									if (fn.getMtSolde() >= mont_fn) {
										System.out.println("CAS 1");
										fn.setSortie(fn.getSortie() + mont_fn);
										fn.setSolde(fn.getSolde() + mont_fn);
										fn.setMtSolde(fn.getMtSolde() - mont_fn);
										fnRepo.save(fn);

										System.out.println("Code EuServir - FN :" + fn.getIdFn() + "; SMCIPN:"
												+ smcipn.getCodeSmcipn());

										Long idServir = servirRepo.getLastEuServirInsertedId();
										if (idServir != null) {
											idServir = idServir + 1;
										} else {
											idServir = 1L;
										}
										EuServir servir = new EuServir();
										servir.setIdServir(idServir);
										servir.setDateCreation(date);
										servir.setEuFn(fn);
										servir.setEuSmcipnpwi(smcipn);
										servir.setMontantAllouer(mont_fn);
										servirRepo.saveAndFlush(servir);

										mont_fn = 0;
									} else {
										System.out.println("CAS 2");
										mont_fn -= fn.getMtSolde();

										Long idServir = servirRepo.getLastEuServirInsertedId();
										if (idServir != null) {
											idServir = idServir + 1;
										} else {
											idServir = 1L;
										}
										System.out.println("Code EuServir - FN :" + idServir);

										EuServir servir = new EuServir();
										servir.setIdServir(idServir);
										servir.setDateCreation(date);
										servir.setEuFn(fn);
										servir.setEuSmcipnpwi(smcipn);
										servir.setMontantAllouer(fn.getMtSolde());
										servirRepo.saveAndFlush(servir);

										fn.setSortie(fn.getSortie() + fn.getMtSolde());
										fn.setSolde(fn.getSolde() + fn.getMtSolde());
										fn.setMtSolde(0);
										fnRepo.save(fn);

										i++;
									}
								}
							}

							EuDomiciliation domi = domiRepo.findDomiByCodeSmcipn(codeMembreBenef,
									smcipn.getCodeSmcipn());
							if (domi != null) {
								domi.setMontantSubvent(domi.getMontantSubvent() + apa.getMontApa());
							} else {
								domi = new EuDomiciliation();
								domi.setCodeDomicilier("PRE" + dateFormatter.format(date));
								domi.setAccorder("O");
								domi.setCatRessource("nrPRE");
								domi.setDateDomiciliation(date);
								domi.setDateEchue(null);
								domi.setDomicilier("O");
								domi.setDureeRenouvellement(0);
								domi.setResteDuree(0);
								domi.setEuMembreMorale(benef);
								domi.setCodeMembreAssureur(benef.getCodeMembreMorale());
								domi.setMontantDomicilier(0);
								domi.setMontantSubvent(apa.getMontApa());
								domi.setTypeDomiciliation("SMCIPNWI");
								domi.setEuProposition(propos);
								domi.setEuSmcipnpwi(smcipn);
								domi.setEuUtilisateur(null);
							}
							domiRepo.save(domi);

							EuGcsc sc = gcscRepo.findBySmcipnAndBenef(smcipn.getCodeSmcipn(), codeMembreBenef);
							if (sc == null) {
								long idGcsc = 0;
								if (gcscRepo.count() > 0) {
									idGcsc = gcscRepo.findLastGcscInsertedId() + 1;
								} else {
									idGcsc = 1;
								}
								sc = new EuGcsc();
								sc.setCodeDomicilier(domi.getCodeDomicilier());
								sc.setCredit(0);
								sc.setDebit(0);
								sc.setSolde(0);
								sc.setCodeMembre(benef.getCodeMembreMorale());
								sc.setIdGcsc(idGcsc);
								sc.setEuSmcipnpwi(smcipn);
								gcscRepo.save(sc);
							}

							compteNn.setSolde(compteNn.getSolde() - apa.getMontApa());
							compteRepo.save(compteNn);
							return "L'operation a été effectuée avec succés";
						}
					}
				} else {
					return "Le numéro d'appel d'offre indiquée n'existe pas \nou n'est pas conforme pour cette opération";
				}
			}
		}
		return rep;
	}

	/**
	 * Méthode du mécanisme de Achat du Pouvoir d'Achat CNCSnrPRE
	 *
	 * @param apa
	 *            Objet APA fournissant les infos sur l'opération à effectuer
	 * @return String chaîne de caractères exprimant si l'opération a été effectuée
	 */
	@Override
	public String doApaCncsnrPre(Apa apa) {
		String rep = "Echec de l'opération!";
		if (apa.getTypeBon().equalsIgnoreCase("CNCSNRPREKIT") || apa.getTypeBon().equalsIgnoreCase("CNCSNRPREASS")) {
			if (apa.getNumeroAppelOffre().isEmpty()) {
				return "Le numéro d'appel d'offre est obligatoire";
			}
			if (apa.getCodeMembre().endsWith("M")) {
				String codeMembre = apa.getCodeMembre();
				EuAppelOffre offre = offreRepo.findAppelOffresByNumero(apa.getNumeroAppelOffre());
				if (offre != null && !offre.getTypeAppelOffre().equalsIgnoreCase("inrpre")) {
					String codeMembreBenef = offre.getCodeMembreMorale();
					EuMembreMorale benef = moralRepo.findOne(codeMembreBenef);
					EuProposition propos = proposService.findPropositionByNumeroAppelOffre(offre.getIdAppelOffre());
					EuActeur acteur_apa = acteurRepo.findByCodeMembre(codeMembre);
					if (acteur_apa != null && acteur_apa.getTypeActeur().equalsIgnoreCase("gac_surveillance")) {
						String codecompteNn = "NN-TSCAPA-" + codeMembre;
						EuCompte compteNn = compteRepo.findOne(codecompteNn);
						if (compteNn != null && compteNn.getSolde() >= apa.getMontApa()) {
							Double solde_smc = smcRepo.findSumByOrigineSmc();
							if (solde_smc >= apa.getMontApa()) {
								Date date = new Date();
								String codeCompteTi = "NR-TPN-" + codeMembre;

								Long idOp = opRepo.getLastOperationInsertedId();
								if (idOp == null) {
									idOp = 1L;
								} else {
									idOp += 1;
								}

								EuCategorieCompte codeCat = new EuCategorieCompte();
								codeCat.setCodeCat("TPN");

								EuOperation op = new EuOperation();
								op.setEuCategorieCompte(codeCat);
								op.setCodeProduit("CNCSnrPRE");
								op.setDateOp(date);
								op.setLibOp("CAPA CNCSnr PRE KIT");
								op.setTypeOp("APA");
								op.setIdOperation(idOp);
								if (apa.getIdUtilisateur() != null) {
									EuUtilisateur user = new EuUtilisateur();
									user.setIdUtilisateur(apa.getIdUtilisateur());
									op.setEuUtilisateur(user);
								}
								op.setHeureOp(date);
								opRepo.save(op);

								EuCompte compteti = compteRepo.findOne(codeCompteTi);
								if (compteti != null) {
									compteti.setSolde(compteti.getSolde() + apa.getMontApa());
									compteRepo.save(compteti);
								} else {
									compteti = new EuCompte();
									compteti.setSolde(apa.getMontApa());
									compteti.setCodeCompte(codeCompteTi);
									compteti.setDateAlloc(date);
									compteti.setEuMembreMorale(compteNn.getEuMembreMorale());
									compteti.setDesactiver("1");
									compteti.setLibCompte("TPN");
									EuTypeCompte typecompte = new EuTypeCompte();
									typecompte.setCodeTypeCompte("NR");
									compteti.setEuTypeCompte(typecompte);
									EuCategorieCompte catcompte = new EuCategorieCompte();
									catcompte.setCodeCat("TPN");
									catcompte.setCodeTypeCompte("NR");
									compteti.setEuCategorieCompte(catcompte);
									compteRepo.save(compteti);
								}

								EuSmcipnpwi smcipn = smcipnRepo.findByNumeroAppel(apa.getNumeroAppelOffre());
								if (smcipn == null) {
									String newCodeSmcipn = null;
									String codeSmcipn = smcipnRepo.findLastByCodeMembreAndTypeSmcipn(codeMembre,
											"SMCIPNWI");
									if (codeSmcipn == null) {
										newCodeSmcipn = "P" + codeMembre + "0001";
									} else {
										String code = codeSmcipn.substring(codeSmcipn.length() - 4);
										int numsmc = Integer.valueOf(code);
										numsmc++;
										newCodeSmcipn = "P" + codeMembre
												+ StringUtils.leftPad(String.valueOf(numsmc), 4, "0");
									}
									smcipn = new EuSmcipnpwi();
									smcipn.setCodeSmcipn(newCodeSmcipn);
									smcipn.setCodeMembre(codeMembre);
									smcipn.setDateSmcipn(date);
									smcipn.setTypeSmcipn("SMCIPNWI");
									smcipn.setEtatAllocInvestis(0);
									smcipn.setRembourser(0);
									smcipn.setEtatAllocSalaire(1);
									smcipn.setSalaireAlloue(0.0);
									smcipn.setInvestisAlloue(0.0);
									smcipn.setMontSalaire(apa.getMontApa());
									smcipn.setMontInvestis(0.0);
									if (apa.getIdUtilisateur() != null) {
										smcipn.setIdUtilisateur(apa.getIdUtilisateur());
									} else {
										smcipn.setIdUtilisateur(null);
									}
									smcipn.setNumeroAppel(apa.getNumeroAppelOffre());
									smcipn.setTypeNr("nrPRE");
									smcipnRepo.save(smcipn);
								} else {
									smcipn.setEtatAllocSalaire(1);
									smcipn.setSalaireAlloue(0);
									smcipn.setMontSalaire(apa.getMontApa());
									smcipnRepo.save(smcipn);
								}

								SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
								EuProduit produit = new EuProduit();
								produit.setCodeProduit(apa.getTypeBon() + apa.getCatBon());
								EuCompteCredit cc = new EuCompteCredit();
								Long idCredit = ccreditRepo.getLastCreditInsertedId() + 1;
								cc.setIdCredit(idCredit);
								cc.setCodeMembre(codeMembre);
								cc.setMontantPlace(apa.getMontApa());
								cc.setMontantCredit(apa.getMontApa());
								cc.setEuOperation(op);
								cc.setEuProduit(produit);
								cc.setCodeTypeCredit(apa.getTypeProduit());
								cc.setSource(codeMembre + dateFormatter.format(date));
								cc.setCompteSource(smcipn.getCodeSmcipn());
								cc.setDateOctroi(date);
								cc.setDatedeb(date);
								Date datefin = ServerUtil.ajouterJours(date, 30);
								cc.setDatefin(datefin);
								cc.setRenouveller("N");
								cc.setBnp(0);
								cc.setKrr("N");
								cc.setEuCompte(compteti);
								cc.setDomicilier(0);
								cc.setNbreRenouvel(0);
								ccreditRepo.save(cc);

								List<EuCapaTs> capas = capaTsRepo.findCapaTsByCompte(compteNn.getCodeCompte());
								if (capas != null) {
									double mont_capa = apa.getMontApa();
									for (EuCapaTs c : capas) {
										if (mont_capa > c.getMontantSolde()) {
											EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
											EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
											ccCapaPK.setCodeCapa(c.getEuCapa().getCodeCapa());
											ccCapaPK.setIdCredit(cc.getIdCredit());
											ccCapa.setId(ccCapaPK);
											ccCapa.setCodeProduit("Inr");
											ccCapa.setMontant(c.getMontantSolde());
											ccCapaRepo.save(ccCapa);

											mont_capa -= c.getMontantSolde();

											c.setMontantUtiliser(c.getMontantUtiliser() + c.getMontantSolde());
											c.setMontantSolde(0.0);
											capaTsRepo.save(c);

										} else {
											EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
											EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
											ccCapaPK.setCodeCapa(c.getEuCapa().getCodeCapa());
											ccCapaPK.setIdCredit(cc.getIdCredit());
											ccCapa.setId(ccCapaPK);
											ccCapa.setCodeProduit("Inr");
											ccCapa.setMontant(mont_capa);
											ccCapaRepo.save(ccCapa);

											c.setMontantUtiliser(c.getMontantUtiliser() + mont_capa);
											c.setMontantSolde(c.getMontantSolde() - mont_capa);
											capaTsRepo.save(c);
											mont_capa = 0;
										}
										if (mont_capa == 0) {
											break;
										}
									}
								}
								List<EuSmc> smcs = smcRepo.findByOrigineSmc();
								if (smcs != null) {
									double mont_smc = apa.getMontApa();
									int i = 0;
									while (mont_smc > 0) {
										EuSmc smc = smcs.get(i);
										if (smc.getMontantSolde() > mont_smc) {
											smc.setSortie(smc.getSortie() + mont_smc);
											smc.setSolde(smc.getSolde() + mont_smc);
											smc.setMontantSolde(smc.getMontantSolde() - mont_smc);
											smcRepo.save(smc);

											Long idUtiliser = utiliserRepo.findLastUtiliserInsertedId();
											if (idUtiliser != null) {
												idUtiliser++;
											} else {
												idUtiliser = 1L;
											}
											EuUtiliser utiliser = new EuUtiliser();
											utiliser.setIdUtiliser(idUtiliser);
											utiliser.setEuSmc(smc);
											utiliser.setMontantAllouer(mont_smc);
											utiliser.setEuSmcipnpwi(smcipn);
											utiliser.setIdCredit(idCredit);
											utiliser.setDateCreation(date);
											utiliserRepo.save(utiliser);

											mont_smc = 0;
										} else {
											mont_smc -= smc.getMontantSolde();

											Long idUtiliser = utiliserRepo.findLastUtiliserInsertedId();
											if (idUtiliser != null) {
												idUtiliser++;
											} else {
												idUtiliser = 1L;
											}
											EuUtiliser utiliser = new EuUtiliser();
											utiliser.setIdUtiliser(idUtiliser);
											utiliser.setEuSmc(smc);
											utiliser.setMontantAllouer(smc.getMontantSolde());
											utiliser.setEuSmcipnpwi(null);
											utiliser.setIdCredit(idCredit);
											utiliser.setDateCreation(date);
											utiliserRepo.save(utiliser);

											smc.setSortie(smc.getSortie() + smc.getMontantSolde());
											smc.setSolde(smc.getSolde() + smc.getMontantSolde());
											smc.setMontantSolde(0);
											smcRepo.save(smc);
											i++;
										}
									}
								}

								EuDomiciliation domi = domiRepo.findDomiByCodeSmcipn(codeMembreBenef,
										smcipn.getCodeSmcipn());
								if (domi != null) {
									domi.setMontantSubvent(domi.getMontantSubvent() + apa.getMontApa());
								} else {
									domi = new EuDomiciliation();
									domi.setCodeDomicilier("PRE" + dateFormatter.format(date));
									domi.setAccorder("O");
									domi.setCatRessource("nrPRE");
									domi.setDateDomiciliation(date);
									domi.setDateEchue(null);
									domi.setDomicilier("O");
									domi.setDureeRenouvellement(0);
									domi.setResteDuree(0);
									domi.setEuMembreMorale(benef);
									domi.setCodeMembreAssureur(benef.getCodeMembreMorale());
									domi.setMontantDomicilier(0);
									domi.setMontantSubvent(apa.getMontApa());
									domi.setTypeDomiciliation("SMCIPNWI");
									domi.setEuProposition(propos);
									domi.setEuSmcipnpwi(smcipn);
									domi.setEuUtilisateur(null);
								}
								domiRepo.save(domi);

								EuGcsc sc = gcscRepo.findBySmcipnAndBenef(smcipn.getCodeSmcipn(), codeMembreBenef);
								if (sc == null) {
									long idGcsc = 0;
									if (gcscRepo.count() > 0) {
										idGcsc = gcscRepo.findLastGcscInsertedId() + 1;
									} else {
										idGcsc = 1;
									}
									sc = new EuGcsc();
									sc.setCodeDomicilier(domi.getCodeDomicilier());
									sc.setCredit(0);
									sc.setDebit(0);
									sc.setSolde(0);
									sc.setCodeMembre(benef.getCodeMembreMorale());
									sc.setIdGcsc(idGcsc);
									sc.setEuSmcipnpwi(smcipn);
									gcscRepo.save(sc);
								}

								compteNn.setSolde(compteNn.getSolde() - apa.getMontApa());
								compteRepo.save(compteNn);
								return "L'operation a été effectuée avec succès";
							}
						} else {
							return "Le solde du compte NN est insuffisant";
						}
					} else {
						return "Cet acteur n'est pas un GAC surveillance ou n'existe pas";
					}
				} else {
					return "Le numéro d'appel d'offre indiquée n'existe pas \nou n'est pas conforme pour cette opération";
				}
			} else {
				return "Ce compte n'est pas autorisé  faire cette opération!";
			}
		}
		return rep;
	}

	/**
	 * Méthode du mécanisme de CAPA CNCSnr
	 *
	 * @param apa
	 *            Objet APA fournissant les infos sur l'opération à effectuer
	 * @return String chaîne de caractères exprimant si l'opération a été effectuée
	 */
	@Override
	public String doApaCncs(Apa apa) {
		String response = "Echec de l'opération";
		String codeMembre = apa.getCodeMembre();
		EuCompte compteNn = null;
		String codeCompteNN = "";
		if (apa.getModeApa().equals("BAn")) {

		} else {
			codeCompteNN = "NN-TSCAPA-" + codeMembre;
		}
		compteNn = compteRepo.findOne(codeCompteNN);
		if (Objects.nonNull(compteNn)) {
			if (compteNn.getSolde() < apa.getMontApa()) {
				return "Le solde du compte " + codeCompteNN + " est insuffisant pour effectuer cette opération";
			}
		} else {
			return "Ce compte NN " + codeCompteNN + " n'existe pas";
		}
		double solde_smc = smcRepo.findSumByOrigineSmc();
		if (solde_smc >= apa.getMontApa()) {
			Date date = new Date();
			String codeCompteTi = "NR-TPN-" + codeMembre;

			EuUtilisateur user = null;
			Long idOp = opRepo.getLastOperationInsertedId() + 1;

			EuCategorieCompte codeCateg = new EuCategorieCompte();
			codeCateg.setCodeCat("TPN");

			EuOperation op = new EuOperation();
			op.setEuCategorieCompte(codeCateg);
			op.setCodeProduit("CNCSnr");
			op.setDateOp(date);
			op.setLibOp("CAPA CNCSnr");
			op.setTypeOp("APA");
			op.setIdOperation(idOp);
			if (apa.getIdUtilisateur() != null) {
				user = new EuUtilisateur();
				user.setIdUtilisateur(apa.getIdUtilisateur());
				op.setEuUtilisateur(user);
			}
			op.setHeureOp(date);
			opRepo.save(op);

			EuCompte compteti = compteRepo.findOne(codeCompteTi);
			if (compteti != null) {
				compteti.setSolde(compteti.getSolde() + apa.getMontApa());
				compteRepo.save(compteti);
			} else {
				compteti = new EuCompte();
				compteti.setSolde(apa.getMontApa());
				compteti.setCodeCompte(codeCompteTi);
				compteti.setDateAlloc(date);
				compteti.setEuMembreMorale(compteNn.getEuMembreMorale());
				compteti.setDesactiver("0");
				compteti.setLibCompte("TPN");
				EuTypeCompte typecompte = new EuTypeCompte();
				typecompte.setCodeTypeCompte("NR");
				compteti.setEuTypeCompte(typecompte);
				EuCategorieCompte catcompte = new EuCategorieCompte();
				catcompte.setCodeCat("TPN");
				catcompte.setCodeTypeCompte("NR");
				compteti.setEuCategorieCompte(catcompte);
				compteRepo.save(compteti);
			}

			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
			Long idCredit = ccreditRepo.getLastCreditInsertedId();
			if (idCredit == null) {
				idCredit = 0L;
			}
			EuProduit produit = new EuProduit();
			produit.setCodeProduit("CNCSnr");
			EuCompteCredit cc = new EuCompteCredit();
			cc.setIdCredit(idCredit + 1);
			cc.setCodeMembre(codeMembre);
			cc.setMontantPlace(apa.getMontApa());
			cc.setMontantCredit(apa.getMontApa());
			cc.setEuOperation(op);
			cc.setEuProduit(produit);
			cc.setCodeTypeCredit(apa.getTypeProduit());
			cc.setSource(codeMembre + dateFormatter.format(date));
			cc.setCompteSource(compteNn.getCodeCompte());
			cc.setDateOctroi(date);
			cc.setDatedeb(date);
			Date datefin = ServerUtil.ajouterJours(date, 30);
			cc.setDatefin(datefin);
			cc.setRenouveller("N");
			cc.setBnp(0);
			cc.setKrr("N");
			cc.setDomicilier(0);
			cc.setEuCompte(compteti);
			cc.setNbreRenouvel(0);
			ccreditRepo.save(cc);

			List<EuCapa> capas = capaRepo.findByEuCompte_CodeCompte(compteNn.getCodeCompte());
			if (capas != null) {
				double mont_capa = apa.getMontApa();
				for (EuCapa c : capas) {
					if (mont_capa > c.getMontantSolde()) {
						EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
						EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
						ccCapaPK.setCodeCapa(c.getCodeCapa());
						ccCapaPK.setIdCredit(cc.getIdCredit());
						ccCapa.setId(ccCapaPK);
						ccCapa.setCodeProduit("Inr");
						ccCapa.setMontant(c.getMontantSolde());
						ccCapaRepo.save(ccCapa);

						mont_capa -= c.getMontantSolde();

						c.setMontantUtiliser(c.getMontantUtiliser() + c.getMontantSolde());
						c.setMontantSolde(0);
						capaRepo.save(c);

					} else {
						EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
						EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
						ccCapaPK.setCodeCapa(c.getCodeCapa());
						ccCapaPK.setIdCredit(cc.getIdCredit());
						ccCapa.setId(ccCapaPK);
						ccCapa.setCodeProduit("Inr");
						ccCapa.setMontant(mont_capa);
						ccCapaRepo.save(ccCapa);

						c.setMontantUtiliser(c.getMontantUtiliser() + mont_capa);
						c.setMontantSolde(c.getMontantSolde() - mont_capa);
						capaRepo.save(c);
						mont_capa = 0;
					}
					if (mont_capa == 0) {
						break;
					}
				}
			}
			List<EuSmc> smcs = smcRepo.findByOrigineSmc();
			if (smcs != null) {
				double mont_smc = apa.getMontApa();
				int i = 0;
				while (mont_smc > 0) {
					EuSmc smc = smcs.get(i);
					if (smc.getMontantSolde() > mont_smc) {
						smc.setSortie(smc.getSortie() + mont_smc);
						smc.setSolde(smc.getSolde() + mont_smc);
						smc.setMontantSolde(smc.getMontantSolde() - mont_smc);
						smcRepo.save(smc);

						Long idUtiliser = utiliserRepo.findLastUtiliserInsertedId();
						if (idUtiliser != null) {
							idUtiliser++;
						} else {
							idUtiliser = 1L;
						}
						EuUtiliser utiliser = new EuUtiliser();
						utiliser.setIdUtiliser(idUtiliser);
						utiliser.setEuSmc(smc);
						utiliser.setMontantAllouer(mont_smc);
						utiliser.setEuSmcipnpwi(null);
						utiliser.setIdCredit(idCredit);
						utiliser.setDateCreation(date);
						utiliserRepo.save(utiliser);

						mont_smc = 0;
					} else {
						mont_smc -= smc.getMontantSolde();

						Long idUtiliser = utiliserRepo.findLastUtiliserInsertedId();
						if (idUtiliser != null) {
							idUtiliser++;
						} else {
							idUtiliser = 1L;
						}
						EuUtiliser utiliser = new EuUtiliser();
						utiliser.setIdUtiliser(idUtiliser);
						utiliser.setEuSmc(smc);
						utiliser.setMontantAllouer(smc.getMontantSolde());
						utiliser.setEuSmcipnpwi(null);
						utiliser.setIdCredit(idCredit);
						utiliser.setDateCreation(date);
						utiliserRepo.save(utiliser);

						smc.setSortie(smc.getSortie() + smc.getMontantSolde());
						smc.setSolde(smc.getSolde() + smc.getMontantSolde());
						smc.setMontantSolde(0);
						smcRepo.save(smc);

						i++;
					}
				}
			}
			compteNn.setSolde(compteNn.getSolde() - apa.getMontApa());
			compteRepo.save(compteNn);
			response = "L'operation a été effectuée avec succès";
		} else {
			response = "Manque de disponibilité pour effectuer cette opération!";
		}
		return response;
	}

	@Override
	public String doApaPreKitRes(Apa apa) {
		String rep = "Echec de l'opération!";
		if (apa.getTypeBon().equalsIgnoreCase("INRPREKIT")) {
			if (apa.getCodeMembre().endsWith("M")) {
				String codeMembre = apa.getCodeMembre();
				EuAppelOffre offre = offreRepo.findAppelOffresByNumero(apa.getNumeroAppelOffre());
				if (offre != null && !offre.getTypeAppelOffre().equals("inrpre")) {
					String codeMembreBenef = offre.getCodeMembreMorale();
					EuMembreMorale benef = moralRepo.findOne(codeMembreBenef);
					String codecompteNn = "NN-TSCAPA-" + codeMembre;
					EuCompte compteNn = compteRepo.findOne(codecompteNn);
					if (compteNn != null && compteNn.getSolde() >= apa.getMontApa()) {
						Double soldefn = fnRepo.findSumByOrigineFn();
						if (soldefn >= apa.getMontApa()) {
							Date date = new Date();
							String codeCompteTi = "NR-TI-" + codeMembre;

							Long idOp = opRepo.getLastOperationInsertedId();
							if (idOp == null) {
								idOp = 0L;
							}

							EuCategorieCompte codeCateg = new EuCategorieCompte();
							codeCateg.setCodeCat("TPN");

							EuOperation op = new EuOperation();
							op.setEuCategorieCompte(codeCateg);
							op.setCodeProduit("InrPREKIT");
							op.setDateOp(date);
							op.setLibOp("CAPA Inr PRE KIT");
							op.setTypeOp("APA");
							op.setIdOperation(idOp + 1);
							if (apa.getIdUtilisateur() != null) {
								EuUtilisateur user = new EuUtilisateur();
								user.setIdUtilisateur(apa.getIdUtilisateur());
								op.setEuUtilisateur(user);
							}
							op.setHeureOp(date);
							opRepo.save(op);

							EuCompte compteti = compteRepo.findOne(codeCompteTi);
							if (compteti != null) {
								compteti.setSolde(compteti.getSolde() + apa.getMontApa());
								compteRepo.save(compteti);
							} else {
								compteti = new EuCompte();
								compteti.setSolde(apa.getMontApa());
								compteti.setCodeCompte(codeCompteTi);
								compteti.setDateAlloc(date);
								compteti.setEuMembreMorale(compteNn.getEuMembreMorale());
								compteti.setDesactiver("1");
								compteti.setLibCompte("Investissement de subvention");
								EuTypeCompte typecompte = new EuTypeCompte();
								typecompte.setCodeTypeCompte("NR");
								compteti.setEuTypeCompte(typecompte);
								EuCategorieCompte catcompte = new EuCategorieCompte();
								catcompte.setCodeCat("TI");
								catcompte.setCodeTypeCompte("NR");
								compteti.setEuCategorieCompte(catcompte);
								compteRepo.save(compteti);
							}

							EuSmcipnpwi smcipn = smcipnRepo.findByNumeroAppel(apa.getNumeroAppelOffre());
							if (smcipn == null) {
								String newCodeSmcipn = null;
								String codeSmcipn = smcipnRepo.findLastByCodeMembreAndTypeSmcipn(codeMembre,
										"SMCIPNWI");
								if (codeSmcipn == null) {
									newCodeSmcipn = "P" + codeMembre + "0001";
								} else {
									String code = codeSmcipn.substring(codeSmcipn.length() - 4);
									int numsmc = Integer.valueOf(code);
									numsmc++;
									newCodeSmcipn = "P" + codeMembre
											+ StringUtils.leftPad(String.valueOf(numsmc), 4, "0");
								}
								smcipn = new EuSmcipnpwi();
								smcipn.setCodeSmcipn(newCodeSmcipn);
								smcipn.setCodeMembre(codeMembre);
								smcipn.setDateSmcipn(date);
								smcipn.setTypeSmcipn("SMCIPNWI");
								smcipn.setEtatAllocInvestis(1);
								smcipn.setRembourser(0);
								smcipn.setEtatAllocSalaire(0);
								smcipn.setSalaireAlloue(0.0);
								smcipn.setMontSalaire(0.0);
								smcipn.setMontInvestis(apa.getMontApa());
								if (apa.getIdUtilisateur() != null) {
									smcipn.setIdUtilisateur(apa.getIdUtilisateur());
								} else {
									smcipn.setIdUtilisateur(null);
								}
								smcipn.setNumeroAppel(apa.getNumeroAppelOffre());
								smcipn.setTypeNr("nrPRE");
								smcipnRepo.saveAndFlush(smcipn);
							} else {
								smcipn.setEtatAllocInvestis(1);
								smcipn.setMontInvestis(apa.getMontApa());
								smcipn.setInvestisAlloue(0.0);
								smcipnRepo.saveAndFlush(smcipn);
							}

							SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
							EuProduit produit = new EuProduit();
							produit.setCodeProduit(apa.getTypeBon() + apa.getCatBon());

							Long idCredit = ccreditRepo.getLastCreditInsertedId();
							if (idCredit == null) {
								idCredit = 1L;
							} else {
								idCredit = idCredit + 1;
							}
							EuCompteCredit cc = new EuCompteCredit();
							cc.setIdCredit(idCredit);
							cc.setCodeMembre(codeMembre);
							cc.setMontantPlace(apa.getMontApa());
							cc.setMontantCredit(apa.getMontApa());
							cc.setEuOperation(op);
							cc.setEuProduit(produit);
							cc.setCodeTypeCredit(apa.getTypeProduit());
							cc.setSource(codeMembre + dateFormatter.format(date));
							cc.setCompteSource(smcipn.getCodeSmcipn());
							cc.setDateOctroi(date);
							cc.setDatedeb(date);
							Date datefin = ServerUtil.ajouterJours(date, 30);
							cc.setDatefin(datefin);
							cc.setRenouveller("O");
							cc.setBnp(0);
							cc.setKrr("N");
							cc.setEuCompte(compteti);
							cc.setDomicilier(0);
							cc.setNbreRenouvel(0);
							ccreditRepo.save(cc);

							List<EuCapaTs> capas = capaTsRepo.findCapaTsByCompte(compteNn.getCodeCompte());
							if (capas != null) {
								double mont_capa = apa.getMontApa();
								for (EuCapaTs c : capas) {
									if (mont_capa > c.getMontantSolde()) {
										EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
										EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
										ccCapaPK.setCodeCapa(c.getEuCapa().getCodeCapa());
										ccCapaPK.setIdCredit(cc.getIdCredit());
										ccCapa.setId(ccCapaPK);
										ccCapa.setCodeProduit("Inr");
										ccCapa.setMontant(c.getMontantSolde());
										ccCapaRepo.save(ccCapa);

										mont_capa -= c.getMontantSolde();

										c.setMontantUtiliser(c.getMontantUtiliser() + c.getMontantSolde());
										c.setMontantSolde(0.0);
										capaTsRepo.save(c);

									} else {
										EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
										EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
										ccCapaPK.setCodeCapa(c.getEuCapa().getCodeCapa());
										ccCapaPK.setIdCredit(cc.getIdCredit());
										ccCapa.setId(ccCapaPK);
										ccCapa.setCodeProduit("Inr");
										ccCapa.setMontant(mont_capa);
										ccCapaRepo.save(ccCapa);

										c.setMontantUtiliser(c.getMontantUtiliser() + mont_capa);
										c.setMontantSolde(c.getMontantSolde() - mont_capa);
										capaTsRepo.save(c);
										mont_capa = 0;
									}
									if (mont_capa == 0) {
										break;
									}
								}

								double mont_fn = apa.getMontApa();
								List<EuFn> fns = fnRepo.findByOrigineFn();
								if (fns != null) {
									System.out.println("Nbre FNs :" + fns.size());
									int i = 0;
									while (mont_fn > 0) {
										EuFn fn = fns.get(i);
										if (fn.getMtSolde() >= mont_fn) {
											System.out.println("CAS 1");
											fn.setSortie(fn.getSortie() + mont_fn);
											fn.setSolde(fn.getSolde() + mont_fn);
											fn.setMtSolde(fn.getMtSolde() - mont_fn);
											fnRepo.save(fn);

											System.out.println("Code EuServir - FN :" + fn.getIdFn() + "; SMCIPN:"
													+ smcipn.getCodeSmcipn());

											Long idServir = servirRepo.getLastEuServirInsertedId();
											if (idServir != null) {
												idServir = idServir + 1;
											} else {
												idServir = 1L;
											}
											EuServir servir = new EuServir();
											servir.setIdServir(idServir);
											servir.setDateCreation(date);
											servir.setEuFn(fn);
											servir.setEuSmcipnpwi(smcipn);
											servir.setMontantAllouer(mont_fn);
											servirRepo.saveAndFlush(servir);

											mont_fn = 0;
										} else {
											System.out.println("CAS 2");
											mont_fn -= fn.getMtSolde();

											Long idServir = servirRepo.getLastEuServirInsertedId();
											if (idServir != null) {
												idServir = idServir + 1;
											} else {
												idServir = 1L;
											}
											System.out.println("Code EuServir - FN :" + idServir);

											EuServir servir = new EuServir();
											servir.setIdServir(idServir);
											servir.setDateCreation(date);
											servir.setEuFn(fn);
											servir.setEuSmcipnpwi(smcipn);
											servir.setMontantAllouer(fn.getMtSolde());
											servirRepo.saveAndFlush(servir);

											fn.setSortie(fn.getSortie() + fn.getMtSolde());
											fn.setSolde(fn.getSolde() + fn.getMtSolde());
											fn.setMtSolde(0);
											fnRepo.save(fn);

											i++;
										}
									}
								}

								EuDomiciliation domi = domiRepo.findDomiByCodeSmcipn(codeMembreBenef,
										smcipn.getCodeSmcipn());
								if (domi != null) {
									domi.setMontantSubvent(domi.getMontantSubvent() + apa.getMontApa());
								} else {
									domi = new EuDomiciliation();
									domi.setCodeDomicilier("PRE" + dateFormatter.format(date));
									domi.setAccorder("O");
									domi.setCatRessource("nrPRE");
									domi.setDateDomiciliation(date);
									domi.setDateEchue(null);
									domi.setDomicilier("O");
									domi.setDureeRenouvellement(0);
									domi.setResteDuree(0);
									domi.setEuMembreMorale(benef);
									domi.setCodeMembreAssureur(benef.getCodeMembreMorale());
									domi.setMontantDomicilier(0);
									domi.setMontantSubvent(apa.getMontApa());
									domi.setTypeDomiciliation("SMCIPNWI");
									domi.setEuProposition(null);
									domi.setEuSmcipnpwi(smcipn);
									domi.setEuUtilisateur(null);
								}
								domiRepo.save(domi);

								EuGcsc sc = gcscRepo.findBySmcipnAndBenef(smcipn.getCodeSmcipn(), codeMembreBenef);
								if (sc == null) {
									long idGcsc = 0;
									if (gcscRepo.count() > 0) {
										idGcsc = gcscRepo.findLastGcscInsertedId() + 1;
									} else {
										idGcsc = 1;
									}
									sc = new EuGcsc();
									sc.setCodeDomicilier(domi.getCodeDomicilier());
									sc.setCredit(0);
									sc.setDebit(0);
									sc.setSolde(0);
									sc.setCodeMembre(benef.getCodeMembreMorale());
									sc.setIdGcsc(idGcsc);
									sc.setEuSmcipnpwi(smcipn);
									gcscRepo.save(sc);
								}

								compteNn.setSolde(compteNn.getSolde() - apa.getMontApa());
								compteRepo.save(compteNn);
								return "L'operation a été effectuée avec succès";
							}
						}
					} else {
						return "Le numéro d'appel d'offre indiquée n'existe pas \nou n'est pas conforme pour cette opération";
					}
				}
			}
		}
		return rep;
	}

	@Override
	public String doApaCncsnrPreRes(Apa apa) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}

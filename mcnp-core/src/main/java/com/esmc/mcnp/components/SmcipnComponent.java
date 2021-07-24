package com.esmc.mcnp.components;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.exception.CompteNonIntegreException;
import com.esmc.mcnp.exception.CompteNonTrouveException;
import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.exception.SoldeInsuffisantException;
import com.esmc.mcnp.model.acteur.EuActeur;
import com.esmc.mcnp.model.op.EuAppelOffre;
import com.esmc.mcnp.model.ba.EuCapa;
import com.esmc.mcnp.model.bc.EuCnp;
import com.esmc.mcnp.model.obpsd.EuEchange;
import com.esmc.mcnp.model.smcipn.EuFn;
import com.esmc.mcnp.model.smcipn.EuGcsc;
import com.esmc.mcnp.model.ba.EuNn;
import com.esmc.mcnp.model.others.EuOperation;
import com.esmc.mcnp.model.bc.EuProduit;
import com.esmc.mcnp.model.smcipn.EuSmc;
import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.cm.EuCategorieCompte;
/*import com.esmc.mcnp.model.acteur.EuCodeActivation;
import com.esmc.mcnp.model.pbf.EuBonNeutreUtilise;
import com.esmc.mcnp.model.pc.EuDemandePaiement;
import com.esmc.mcnp.model.others.EuActivation;*/
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.cm.EuCompteGeneral;
import com.esmc.mcnp.model.cm.EuCompteGeneralPK;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.cm.EuTypeCompte;
import com.esmc.mcnp.model.obps.EuTegc;
import com.esmc.mcnp.model.obpsd.EuTypeNn;
import com.esmc.mcnp.model.obpsd.EuUtiliserNn;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.model.smcipn.EuServir;
import com.esmc.mcnp.model.smcipn.EuSmcipnpwi;
import com.esmc.mcnp.model.smcipn.EuTransfertNr;
import com.esmc.mcnp.model.smcipn.EuUtiliser;
import com.esmc.mcnp.services.acteurs.EuActeurService;
import com.esmc.mcnp.services.ba.EuCapaService;
import com.esmc.mcnp.services.bc.EuBonService;
import com.esmc.mcnp.services.bc.EuCnpService;
import com.esmc.mcnp.services.cm.EuCompteCreditService;
import com.esmc.mcnp.services.cm.EuCompteGeneralService;
import com.esmc.mcnp.services.cm.EuCompteService;
import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.cm.EuMembreService;
import com.esmc.mcnp.services.common.EuOperationService;
import com.esmc.mcnp.services.obps.EuGcscService;
import com.esmc.mcnp.services.obps.EuTegcService;
import com.esmc.mcnp.services.obpsd.EuEchangeService;
import com.esmc.mcnp.services.obpsd.EuNnService;
import com.esmc.mcnp.services.obpsd.EuUtiliserNnService;
import com.esmc.mcnp.services.smcipn.EuAppelOffreService;
import com.esmc.mcnp.services.smcipn.EuFnService;
import com.esmc.mcnp.services.smcipn.EuServirService;
import com.esmc.mcnp.services.smcipn.EuSmcService;
import com.esmc.mcnp.services.smcipn.EuSmcipnpwiService;
import com.esmc.mcnp.services.smcipn.EuTransfertNrService;
import com.esmc.mcnp.services.smcipn.EuUtiliserService;

@Component
public class SmcipnComponent {

	private @Autowired EuCompteService compteService;
	private @Autowired EuCompteCreditService ccService;
	private @Autowired EuFnService fnService;
	private @Autowired EuSmcipnpwiService smcipnService;
	private @Autowired EuOperationService opService;
	private @Autowired CreditComponent creditService;
	private @Autowired EuGcscService gcscService;
	private @Autowired EuAppelOffreService offreService;
	private @Autowired EuMembreService membreService;
	private @Autowired EuMembreMoraleService moraleService;
	private @Autowired EuEchangeService echService;
	private @Autowired EuNnService nnService;
	private @Autowired EuCompteGeneralService compteGeneService;
	private @Autowired EuCnpService cnpService;
	private @Autowired EuServirService servirService;
	private @Autowired EuTransfertNrService transnrService;
	private @Autowired EuActeurService acteurService;
	private @Autowired EuSmcService smcService;
	private @Autowired EuUtiliserService utiliserService;
	private @Autowired EuTegcService tegcService;
	private @Autowired EuBonService bonService;
	private @Autowired EuUtiliserNnService utiliserNNService;
	private @Autowired EuCapaService capaService;

	/**
	 * Logger.
	 */
	private final Logger log = LogManager.getLogger(SmcipnComponent.class.getName());

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean echangeNRNB(String codeMembre, String numAppelOffre, String typeProduit, double montant,
			double prk) {
		EuMembre membre = null;
		EuMembreMorale moral = null;
		EuGcsc gcsc = null;
		EuAppelOffre offre = null;
		String codeProduit = StringUtils.EMPTY;
		Date date = new Date();
		String codeCategorie = "";
		String codeCompte = "";
		try {
			if (montant > 0) {
				if (codeMembre.endsWith("M")) {
					codeCompte = "NR-TI-" + codeMembre;
					codeCategorie = "TI";
				} else {
					codeCompte = "NR-TCNCS-" + codeMembre;
					codeCategorie = "TCNCS";
				}

				EuCompte compteOrigine = compteService.getById(codeCompte);
				String codeCompteDest = StringUtils.EMPTY;
				if (codeCategorie.equals("TI")) {
					codeCompteDest = "NB-TI-" + codeMembre;
					codeProduit = "Inr";
					EuSmcipnpwi smcipn = smcipnService.findByNumeroAppel(numAppelOffre);
					if (Objects.nonNull(smcipn)) {
						// Récupération du GCsc et del'appel d'offre
						gcsc = gcscService.findByEuSmcipn_CodeSmcipn(smcipn.getCodeSmcipn());
						offre = offreService.findAppelOffresByNumero(smcipn.getNumeroAppel());
						if (Objects.isNull(gcsc) || offre.getTypeAppelOffre().equals("inrpre")) {
							throw new IllegalStateException("Le TEGCSC de remboursement du SMCIPN n'est pas défini!");
						}
					}
				} else {
					codeCompteDest = "NB-TPAGCRPG-" + codeMembre;
					codeProduit = "RPGnr";
				}

				if (Objects.nonNull(compteOrigine) && StringUtils.isNotBlank(codeCompteDest)) {
					double solde = compteOrigine.getSolde();
					if (solde >= montant) {
						if (codeMembre.endsWith("M")) {
							moral = moraleService.findById(codeMembre);
						} else {
							membre = membreService.findById(codeMembre);
						}
						Double mont_credits = ccService.getSumCreditByEuCompte(codeCompte);
						if (mont_credits != null && mont_credits == solde) {
							/*
							 * Long idOp = opService.getLastOperation(); if (idOp == null) { idOp = 1L; }
							 */
							EuOperation op = new EuOperation();
							op.setCodeProduit(codeProduit);
							op.setDateOp(date);
							op.setMontantOp(montant);
							op.setTypeOp("ECH");
							if (Objects.isNull(moral)) {
								op.setLibOp("Echange du CNCS en NR");
							} else {
								op.setLibOp("Echange du TI en NR");
							}
							op.setHeureOp(date);
							op.setEuUtilisateur(null);
							// op.setIdOperation(idOp);
							if (codeMembre.endsWith("M")) {
								op.setEuMembreMorale(moral);
							} else {
								op.setEuMembre(membre);
							}
							opService.add(op);

							// Mise à jour ou création du compte de desstination
							// du crédit échangé
							EuCompte compteDest = compteService.getById(codeCompteDest);
							EuTypeCompte typeCompte = new EuTypeCompte("NB");
							EuCategorieCompte cat = new EuCategorieCompte(codeCategorie);
							if (Objects.nonNull(compteDest)) {
								compteDest.setSolde(compteDest.getSolde() + montant);
								compteDest = compteService.update(compteDest);
							} else {
								compteDest = new EuCompte();
								compteDest.setCodeCompte(codeCompteDest);
								compteDest.setDateAlloc(date);
								compteDest.setDesactiver("N");
								compteDest.setEuCategorieCompte(cat);
								if (codeMembre.endsWith("M")) {
									compteDest.setEuMembreMorale(compteOrigine.getEuMembreMorale());
									compteDest.setEuMembre(null);
								} else {
									compteDest.setEuMembre(compteOrigine.getEuMembre());
									compteDest.setEuMembreMorale(null);
								}
								compteDest.setSolde(montant);
								compteDest.setEuTypeCompte(typeCompte);
								compteDest.setLibCompte("NB " + codeCategorie);
								compteDest.setCardprinteddate(null);
								compteDest.setCardprintediddate(0);
								compteDest.setMifarecard(null);
								compteDest.setNumeroCarte(null);
								compteDest = compteService.create(compteDest);
							}

							// Création de l'échange effectué
							/*
							 * Long id_echange = echService.findLastEchangeInsertedId(); if (id_echange ==
							 * null) { id_echange = 0L; }
							 */
							EuEchange ech = new EuEchange();
							// ech.setIdEchange(id_echange + 1);
							if (codeProduit.equals("Inr")) {
								ech.setCatEchange("I");
							} else {
								ech.setCatEchange("CNCS");
							}
							ech.setTypeEchange("NR/NB");
							ech.setMontantEchange(montant);
							ech.setMontant(montant);
							ech.setCompenser(1);
							ech.setRegler(1);
							ech.setEuCompte(compteOrigine);
							ech.setCodeCompteObt(codeCompteDest);
							ech.setDateEchange(date);
							ech.setDateReglement(date);
							if (codeMembre.endsWith("M")) {
								ech.setEuMembreMorale(compteOrigine.getEuMembreMorale());
							} else {
								ech.setEuMembre(compteOrigine.getEuMembre());
							}
							ech.setCodeProduit(codeProduit);
							ech.setAgio(0);
							echService.add(ech);

							// Création et utilisation du NN pour l'échange
							/*
							 * Long id = nnService.getLastInsertId(); if (id == null) { id = 0L; }
							 */
							EuTypeNn typeNn = new EuTypeNn();
							typeNn.setCodeTypeNn(codeProduit);
							EuNn eunn = new EuNn();
							eunn.setDateEmission(date);
							// eunn.setIdNn(id + 1);
							eunn.setEuMembreMorale(null);
							eunn.setEuTypeNn(typeNn);
							eunn.setMontantEmis(ech.getMontant());
							eunn.setMontantRemb(ech.getMontant());
							eunn.setSoldeNn(0.0);
							eunn.setTypeEmission("Echange");
							nnService.add(eunn);

							// Mise à jour du compte général des NN émis
							EuCompteGeneralPK cgPK = new EuCompteGeneralPK();
							cgPK.setCodeCompte("FGS" + codeProduit);
							cgPK.setCodeTypeCompte("NN");
							cgPK.setService("E");
							EuCompteGeneral cg = compteGeneService.findById(cgPK);
							if (cg != null) {
								cg.setSolde(cg.getSolde() + ech.getMontant());
								compteGeneService.update(cg);
							} else {
								cg = new EuCompteGeneral();
								cg.setId(cgPK);
								cg.setIntitule("FG Source " + codeProduit);
								cg.setSolde(ech.getMontant());
								compteGeneService.add(cg);
							}

							// Enregistrement du compte crédit
							SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
							EuProduit produit = new EuProduit();
							produit.setCodeProduit(codeProduit);

							EuCompteCredit cc = new EuCompteCredit();
							// Long idCredit = ccService.getLastCreditInsertedId() + 1;
							// cc.setIdCredit(idCredit);
							cc.setCodeMembre(codeMembre);
							cc.setMontantPlace(montant);
							cc.setMontantCredit(montant);
							cc.setEuOperation(op);
							cc.setEuProduit(produit);
							cc.setCodeTypeCredit("CNPG");
							cc.setSource(codeMembre + formatter.format(date));
							cc.setCompteSource(compteOrigine.getCodeCompte());
							cc.setDateOctroi(date);
							cc.setDatedeb(date);
							cc.setDatefin(ServerUtil.ajouterJours(date, 30));
							cc.setRenouveller("N");
							cc.setNbreRenouvel(-1);
							cc.setBnp(0);
							cc.setKrr("N");
							cc.setEuCompte(compteDest);
							cc.setDomicilier(0);
							cc.setPrk(prk);
							cc.setAffecter(0);
							cc.setCodeBnp(null);
							cc.setTypeProduit(typeProduit);
							ccService.create(cc);

							// Enregistrement du CNP
							// long idCnp = cnpService.getLastCnpInsertedId() + 1;
							EuCnp cnp = new EuCnp();
							cnp.setDateCnp(date);
							cnp.setEuCapa(null);
							cnp.setEuDomiciliation(null);
							cnp.setTypeCnp(produit.getCodeProduit());
							cnp.setSourceCredit(cc.getSource());
							cnp.setOrigineCnp("FG" + produit.getCodeProduit());
							cnp.setMontDebit(montant);
							cnp.setMontCredit(0);
							cnp.setSoldeCnp(montant);
							cnp.setTransfertGcp(0);
							cnp.setEuCompteCredit(cc);
							// cnp.setIdCnp(idCnp);
							cnpService.add(cnp);

							// Enregistrement du FN
							// long idFn = fnService.getLastFnInsertedId() + 1;
							EuFn fn = new EuFn();
							// fn.setIdFn(idFn);
							fn.setTypeFn("Inr");
							fn.setDateFn(date);
							fn.setEuCapa(null);
							fn.setMontant(montant);
							fn.setSortie(0);
							fn.setEntree(0);
							fn.setSolde(0);
							fn.setOrigineFn(1);
							fn.setMtSolde(montant);
							fnService.add(fn);

							// Mise à jour du SMC
							List<EuCompteCredit> credits = ccService.findByCodeCompte(codeCompte);
							// creditService.updateListSmc(credits, compteDest, op, montant);
							if ("TCNCS".equals(codeCategorie)) {
								creditService.updateListSmc(credits, compteDest, op, montant);
							}
							// Mise à jour du FN
							if ("TI".equals(codeCategorie)) {
								creditService.updateListFn(credits, compteDest, op, montant);
							}

							// Mise à jour du compte échangé
							compteOrigine.setSolde(compteOrigine.getSolde() - montant);
							compteService.update(compteOrigine);

							return true;
						} else {
							log.error("Echec de l'echange du NR/NB",
									new CompteNonIntegreException("Le solde du compte " + compteOrigine.getCodeCompte()
											+ " qui est " + NumberFormat.getInstance().format(compteOrigine.getSolde())
											+ " est différent de la somme des crédits qui est "
											+ NumberFormat.getInstance().format(mont_credits)));
							return false;
						}
					} else {
						log.error("Echec de l'echange du NR/NB",
								new SoldeInsuffisantException("Le solde du compte " + compteOrigine.getCodeCompte()
										+ " qui est " + NumberFormat.getInstance().format(compteOrigine.getSolde())
										+ " est insuffisant pour effectuer cette opération de "
										+ NumberFormat.getInstance().format(montant)));
						return false;
					}
				} else {
					log.error("Echec de l'echange NR/NB", new CompteNonIntegreException(
							"Ce compte " + compteOrigine.getCodeCompte() + " n'existe pas ou est incorrect!"));
					return false;
				}
			} else {
				log.error("Echec de l'echange NR/NB",
						new IllegalArgumentException("Veuillez fournir le montant de l'opération!"));
				return false;
			}
		} catch (Exception e) {
			log.error("Echec de l'echange du NR/NB", e);
			return false;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean echangeNRNB(String codeMembre, String numAppelOffre, String typeRessource, String typeProduit,
			double montant, double prk) {
		EuMembre membre = null;
		EuMembreMorale moral = null;
		EuGcsc gcsc = null;
		EuAppelOffre offre = null;
		String codeProduit = StringUtils.EMPTY;
		Date date = new Date();
		String codeCategorie = "";
		String codeCompte = "";
		try {
			if (montant > 0) {
				if (codeMembre.endsWith("M")) {
					if ("TI".equals(typeRessource)) {
						codeCompte = "NR-TI-" + codeMembre;
						codeCategorie = "TI";
					} else {
						codeCompte = "NR-TPN-" + codeMembre;
						codeCategorie = "TPN";
					}
				} else {
					codeCompte = "NR-TCNCS-" + codeMembre;
					codeCategorie = "TCNCS";
				}

				EuCompte compteOrigine = compteService.getById(codeCompte);
				String codeCompteDest = StringUtils.EMPTY;
				if (codeCategorie.equals("TI") || codeCategorie.equals("TPN")) {
					codeCompteDest = "NB-TI-" + codeMembre;
					codeProduit = "Inr";
					EuSmcipnpwi smcipn = smcipnService.findByNumeroAppel(numAppelOffre);
					if (Objects.nonNull(smcipn)) {
						// Récupération du GCsc et del'appel d'offre
						gcsc = gcscService.findByEuSmcipn_CodeSmcipn(smcipn.getCodeSmcipn());
						offre = offreService.findAppelOffresByNumero(smcipn.getNumeroAppel());
						if (Objects.isNull(gcsc) || offre.getTypeAppelOffre().equals("inrpre")) {
							throw new IllegalStateException("Le TEGCSC de remboursement du SMCIPN n'est pas défini!");
						}
					}
				} else {
					codeCompteDest = "NB-TPAGCRPG-" + codeMembre;
					codeProduit = "RPGnr";
				}

				if (Objects.nonNull(compteOrigine) && StringUtils.isNotBlank(codeCompteDest)) {
					double solde = compteOrigine.getSolde();
					if (solde >= montant) {
						if (codeMembre.endsWith("M")) {
							moral = moraleService.findById(codeMembre);
						} else {
							membre = membreService.findById(codeMembre);
						}
						Double mont_credits = ccService.getSumCreditByEuCompte(codeCompte);
						if (mont_credits != null && mont_credits == solde) {
							/*
							 * Long idOp = opService.getLastOperation(); if (idOp == null) { idOp = 1L; }
							 */
							EuOperation op = new EuOperation();
							op.setCodeProduit(codeProduit);
							op.setDateOp(date);
							op.setMontantOp(montant);
							op.setTypeOp("ECH");
							if (Objects.isNull(moral)) {
								op.setLibOp("Echange du CNCS en NR");
							} else {
								op.setLibOp("Echange du TI en NR");
							}
							op.setHeureOp(date);
							op.setEuUtilisateur(null);
							// op.setIdOperation(idOp);
							if (codeMembre.endsWith("M")) {
								op.setEuMembreMorale(moral);
							} else {
								op.setEuMembre(membre);
							}
							opService.add(op);

							// Mise à jour ou création du compte de desstination
							// du crédit échangé
							EuCompte compteDest = compteService.getById(codeCompteDest);
							EuTypeCompte typeCompte = new EuTypeCompte("NB");
							EuCategorieCompte cat = new EuCategorieCompte(codeCategorie);
							if (Objects.nonNull(compteDest)) {
								compteDest.setSolde(compteDest.getSolde() + montant);
								compteDest = compteService.update(compteDest);
							} else {
								compteDest = new EuCompte();
								compteDest.setCodeCompte(codeCompteDest);
								compteDest.setDateAlloc(date);
								compteDest.setDesactiver("N");
								compteDest.setEuCategorieCompte(cat);
								if (codeMembre.endsWith("M")) {
									compteDest.setEuMembreMorale(compteOrigine.getEuMembreMorale());
									compteDest.setEuMembre(null);
								} else {
									compteDest.setEuMembre(compteOrigine.getEuMembre());
									compteDest.setEuMembreMorale(null);
								}
								compteDest.setSolde(montant);
								compteDest.setEuTypeCompte(typeCompte);
								compteDest.setLibCompte("NB " + codeCategorie);
								compteDest.setCardprinteddate(null);
								compteDest.setCardprintediddate(0);
								compteDest.setMifarecard(null);
								compteDest.setNumeroCarte(null);
								compteDest = compteService.create(compteDest);
							}

							// Création de l'échange effectué
							/*
							 * Long id_echange = echService.findLastEchangeInsertedId(); if (id_echange ==
							 * null) { id_echange = 0L; }
							 */
							EuEchange ech = new EuEchange();
							// ech.setIdEchange(id_echange + 1);
							if (codeProduit.equals("Inr")) {
								ech.setCatEchange("I");
							} else {
								ech.setCatEchange("CNCS");
							}
							ech.setTypeEchange("NR/NB");
							ech.setMontantEchange(montant);
							ech.setMontant(montant);
							ech.setCompenser(1);
							ech.setRegler(1);
							ech.setEuCompte(compteOrigine);
							ech.setCodeCompteObt(codeCompteDest);
							ech.setDateEchange(date);
							ech.setDateReglement(date);
							if (codeMembre.endsWith("M")) {
								ech.setEuMembreMorale(compteOrigine.getEuMembreMorale());
							} else {
								ech.setEuMembre(compteOrigine.getEuMembre());
							}
							ech.setCodeProduit(codeProduit);
							ech.setAgio(0);
							echService.add(ech);

							// Création et utilisation du NN pour l'échange
							/*
							 * Long id = nnService.getLastInsertId(); if (id == null) { id = 0L; }
							 */
							EuTypeNn typeNn = new EuTypeNn();
							typeNn.setCodeTypeNn(codeProduit);
							EuNn eunn = new EuNn();
							eunn.setDateEmission(date);
							// eunn.setIdNn(id + 1);
							eunn.setEuMembreMorale(null);
							eunn.setEuTypeNn(typeNn);
							eunn.setMontantEmis(ech.getMontant());
							eunn.setMontantRemb(ech.getMontant());
							eunn.setSoldeNn(0.0);
							eunn.setTypeEmission("Echange");
							nnService.add(eunn);

							// Mise à jour du compte général des NN émis
							EuCompteGeneralPK cgPK = new EuCompteGeneralPK();
							cgPK.setCodeCompte("FGS" + codeProduit);
							cgPK.setCodeTypeCompte("NN");
							cgPK.setService("E");
							EuCompteGeneral cg = compteGeneService.findById(cgPK);
							if (cg != null) {
								cg.setSolde(cg.getSolde() + ech.getMontant());
								compteGeneService.update(cg);
							} else {
								cg = new EuCompteGeneral();
								cg.setId(cgPK);
								cg.setIntitule("FG Source " + codeProduit);
								cg.setSolde(ech.getMontant());
								compteGeneService.add(cg);
							}

							// Enregistrement du compte crédit
							SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
							EuProduit produit = new EuProduit();
							produit.setCodeProduit(codeProduit);

							EuCompteCredit cc = new EuCompteCredit();
							// Long idCredit = ccService.getLastCreditInsertedId() + 1;
							// cc.setIdCredit(idCredit);
							cc.setCodeMembre(codeMembre);
							cc.setMontantPlace(montant);
							cc.setMontantCredit(montant);
							cc.setEuOperation(op);
							cc.setEuProduit(produit);
							cc.setCodeTypeCredit("CNPG");
							cc.setSource(codeMembre + formatter.format(date));
							cc.setCompteSource(compteOrigine.getCodeCompte());
							cc.setDateOctroi(date);
							cc.setDatedeb(date);
							cc.setDatefin(ServerUtil.ajouterJours(date, 30));
							cc.setRenouveller("N");
							cc.setNbreRenouvel(-1);
							cc.setBnp(0);
							cc.setKrr("N");
							cc.setEuCompte(compteDest);
							cc.setDomicilier(0);
							cc.setPrk(prk);
							cc.setAffecter(0);
							cc.setCodeBnp(null);
							cc.setTypeProduit(typeProduit);
							ccService.create(cc);

							// Enregistrement du CNP
							// long idCnp = cnpService.getLastCnpInsertedId() + 1;
							EuCnp cnp = new EuCnp();
							cnp.setDateCnp(date);
							cnp.setEuCapa(null);
							cnp.setEuDomiciliation(null);
							cnp.setTypeCnp(produit.getCodeProduit());
							cnp.setSourceCredit(cc.getSource());
							cnp.setOrigineCnp("FG" + produit.getCodeProduit());
							cnp.setMontDebit(montant);
							cnp.setMontCredit(0);
							cnp.setSoldeCnp(montant);
							cnp.setTransfertGcp(0);
							cnp.setEuCompteCredit(cc);
							// cnp.setIdCnp(idCnp);
							cnpService.add(cnp);

							// Enregistrement du FN
							// long idFn = fnService.getLastFnInsertedId() + 1;
							EuFn fn = new EuFn();
							// fn.setIdFn(idFn);
							fn.setTypeFn("Inr");
							fn.setDateFn(date);
							fn.setEuCapa(null);
							fn.setMontant(montant);
							fn.setSortie(0);
							fn.setEntree(0);
							fn.setSolde(0);
							fn.setOrigineFn(1);
							fn.setMtSolde(montant);
							fnService.add(fn);

							// Mise à jour du SMC
							List<EuCompteCredit> credits = ccService.findByCodeCompte(codeCompte);
							// creditService.updateListSmc(credits, compteDest, op, montant);
							if ("TCNCS".equals(codeCategorie) || "TPN".equals(codeCategorie)) {
								creditService.updateListSmc(credits, compteDest, op, montant);
							}
							// Mise à jour du FN
							if ("TI".equals(codeCategorie)) {
								creditService.updateListFn(credits, compteDest, op, montant);
							}

							// Mise à jour du compte échangé
							compteOrigine.setSolde(compteOrigine.getSolde() - montant);
							compteService.update(compteOrigine);

							return true;
						} else {
							log.error("Echec de l'echange du NR/NB",
									new CompteNonIntegreException("Le solde du compte " + compteOrigine.getCodeCompte()
											+ " qui est " + NumberFormat.getInstance().format(compteOrigine.getSolde())
											+ " est différent de la somme des crédits qui est "
											+ NumberFormat.getInstance().format(mont_credits)));
							return false;
						}
					} else {
						log.error("Echec de l'echange du NR/NB",
								new SoldeInsuffisantException("Le solde du compte " + compteOrigine.getCodeCompte()
										+ " qui est " + NumberFormat.getInstance().format(compteOrigine.getSolde())
										+ " est insuffisant pour effectuer cette opération de "
										+ NumberFormat.getInstance().format(montant)));
						return false;
					}
				} else {
					log.error("Echec de l'echange NR/NB", new CompteNonIntegreException(
							"Ce compte " + compteOrigine.getCodeCompte() + " n'existe pas ou est incorrect!"));
					return false;
				}
			} else {
				log.error("Echec de l'echange NR/NB",
						new IllegalArgumentException("Veuillez fournir le montant de l'opération!"));
				return false;
			}
		} catch (Exception e) {
			log.error("Echec de l'echange du NR/NB", e);
			return false;
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean echangeSmcipn(String codeMembre, String typeRessource, String typeProduit,
			double montant, double prk) {
		EuMembre membre = null;
		EuMembreMorale moral = null;
		String codeProduit = StringUtils.EMPTY;
		Date date = new Date();
		String codeCategorie = "";
		String codeCompte = "";
		try {
			if (montant > 0) {
				if (codeMembre.endsWith("M")) {
					if ("TI".equals(typeRessource)) {
						codeCompte = "NR-TI-" + codeMembre;
						codeCategorie = "TI";
					} else {
						codeCompte = "NR-TPN-" + codeMembre;
						codeCategorie = "TPN";
					}
				} else {
					codeCompte = "NR-TCNCS-" + codeMembre;
					codeCategorie = "TCNCS";
				}

				EuCompte compteOrigine = compteService.getById(codeCompte);
				String codeCompteDest = StringUtils.EMPTY;
				if (codeCategorie.equals("TI") || codeCategorie.equals("TPN")) {
					codeCompteDest = "NB-TI-" + codeMembre;
					codeProduit = "Inr";
				} else {
					codeCompteDest = "NB-TPAGCRPG-" + codeMembre;
					codeProduit = "RPGnr";
				}

				if (Objects.nonNull(compteOrigine) && StringUtils.isNotBlank(codeCompteDest)) {
					double solde = compteOrigine.getSolde();
					if (solde >= montant) {
						if (codeMembre.endsWith("M")) {
							moral = moraleService.findById(codeMembre);
						} else {
							membre = membreService.findById(codeMembre);
						}
						Double mont_credits = ccService.getSumCreditByEuCompte(codeCompte);
						if (mont_credits != null && mont_credits == solde) {
							/*
							 * Long idOp = opService.getLastOperation(); if (idOp == null) { idOp = 1L; }
							 */
							EuOperation op = new EuOperation();
							op.setCodeProduit(codeProduit);
							op.setDateOp(date);
							op.setMontantOp(montant);
							op.setTypeOp("ECH");
							if (Objects.isNull(moral)) {
								op.setLibOp("Echange du CNCS en NR");
							} else {
								op.setLibOp("Echange du TI en NR");
							}
							op.setHeureOp(date);
							op.setEuUtilisateur(null);
							// op.setIdOperation(idOp);
							if (codeMembre.endsWith("M")) {
								op.setEuMembreMorale(moral);
							} else {
								op.setEuMembre(membre);
							}
							opService.add(op);

							// Mise à jour ou création du compte de desstination
							// du crédit échangé
							EuCompte compteDest = compteService.getById(codeCompteDest);
							EuTypeCompte typeCompte = new EuTypeCompte("NB");
							EuCategorieCompte cat = new EuCategorieCompte(codeCategorie);
							if (Objects.nonNull(compteDest)) {
								compteDest.setSolde(compteDest.getSolde() + montant);
								compteDest = compteService.update(compteDest);
							} else {
								compteDest = new EuCompte();
								compteDest.setCodeCompte(codeCompteDest);
								compteDest.setDateAlloc(date);
								compteDest.setDesactiver("N");
								compteDest.setEuCategorieCompte(cat);
								if (codeMembre.endsWith("M")) {
									compteDest.setEuMembreMorale(compteOrigine.getEuMembreMorale());
									compteDest.setEuMembre(null);
								} else {
									compteDest.setEuMembre(compteOrigine.getEuMembre());
									compteDest.setEuMembreMorale(null);
								}
								compteDest.setSolde(montant);
								compteDest.setEuTypeCompte(typeCompte);
								compteDest.setLibCompte("NB " + codeCategorie);
								compteDest.setCardprinteddate(null);
								compteDest.setCardprintediddate(0);
								compteDest.setMifarecard(null);
								compteDest.setNumeroCarte(null);
								compteDest = compteService.create(compteDest);
							}

							// Création de l'échange effectué
							/*
							 * Long id_echange = echService.findLastEchangeInsertedId(); if (id_echange ==
							 * null) { id_echange = 0L; }
							 */
							EuEchange ech = new EuEchange();
							// ech.setIdEchange(id_echange + 1);
							if (codeProduit.equals("Inr")) {
								ech.setCatEchange("I");
							} else {
								ech.setCatEchange("CNCS");
							}
							ech.setTypeEchange("NR/NB");
							ech.setMontantEchange(montant);
							ech.setMontant(montant);
							ech.setCompenser(1);
							ech.setRegler(1);
							ech.setEuCompte(compteOrigine);
							ech.setCodeCompteObt(codeCompteDest);
							ech.setDateEchange(date);
							ech.setDateReglement(date);
							if (codeMembre.endsWith("M")) {
								ech.setEuMembreMorale(compteOrigine.getEuMembreMorale());
							} else {
								ech.setEuMembre(compteOrigine.getEuMembre());
							}
							ech.setCodeProduit(codeProduit);
							ech.setAgio(0);
							echService.add(ech);

							// Création et utilisation du NN pour l'échange
							/*
							 * Long id = nnService.getLastInsertId(); if (id == null) { id = 0L; }
							 */
							EuTypeNn typeNn = new EuTypeNn();
							typeNn.setCodeTypeNn(codeProduit);
							EuNn eunn = new EuNn();
							eunn.setDateEmission(date);
							// eunn.setIdNn(id + 1);
							eunn.setEuMembreMorale(null);
							eunn.setEuTypeNn(typeNn);
							eunn.setMontantEmis(ech.getMontant());
							eunn.setMontantRemb(ech.getMontant());
							eunn.setSoldeNn(0.0);
							eunn.setTypeEmission("Echange");
							nnService.add(eunn);

							// Mise à jour du compte général des NN émis
							EuCompteGeneralPK cgPK = new EuCompteGeneralPK();
							cgPK.setCodeCompte("FGS" + codeProduit);
							cgPK.setCodeTypeCompte("NN");
							cgPK.setService("E");
							EuCompteGeneral cg = compteGeneService.findById(cgPK);
							if (cg != null) {
								cg.setSolde(cg.getSolde() + ech.getMontant());
								compteGeneService.update(cg);
							} else {
								cg = new EuCompteGeneral();
								cg.setId(cgPK);
								cg.setIntitule("FG Source " + codeProduit);
								cg.setSolde(ech.getMontant());
								compteGeneService.add(cg);
							}

							// Enregistrement du compte crédit
							SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
							EuProduit produit = new EuProduit();
							produit.setCodeProduit(codeProduit);

							EuCompteCredit cc = new EuCompteCredit();
							// Long idCredit = ccService.getLastCreditInsertedId() + 1;
							// cc.setIdCredit(idCredit);
							cc.setCodeMembre(codeMembre);
							cc.setMontantPlace(montant);
							cc.setMontantCredit(montant);
							cc.setEuOperation(op);
							cc.setEuProduit(produit);
							cc.setCodeTypeCredit("CNPG");
							cc.setSource(codeMembre + formatter.format(date));
							cc.setCompteSource(compteOrigine.getCodeCompte());
							cc.setDateOctroi(date);
							cc.setDatedeb(date);
							cc.setDatefin(ServerUtil.ajouterJours(date, 30));
							cc.setRenouveller("N");
							cc.setNbreRenouvel(-1);
							cc.setBnp(0);
							cc.setKrr("N");
							cc.setEuCompte(compteDest);
							cc.setDomicilier(0);
							cc.setPrk(prk);
							cc.setAffecter(0);
							cc.setCodeBnp(null);
							cc.setTypeProduit(typeProduit);
							ccService.create(cc);

							// Enregistrement du CNP
							// long idCnp = cnpService.getLastCnpInsertedId() + 1;
							EuCnp cnp = new EuCnp();
							cnp.setDateCnp(date);
							cnp.setEuCapa(null);
							cnp.setEuDomiciliation(null);
							cnp.setTypeCnp(produit.getCodeProduit());
							cnp.setSourceCredit(cc.getSource());
							cnp.setOrigineCnp("FG" + produit.getCodeProduit());
							cnp.setMontDebit(montant);
							cnp.setMontCredit(0);
							cnp.setSoldeCnp(montant);
							cnp.setTransfertGcp(0);
							cnp.setEuCompteCredit(cc);
							// cnp.setIdCnp(idCnp);
							cnpService.add(cnp);

							// Enregistrement du FN
							// long idFn = fnService.getLastFnInsertedId() + 1;
							EuFn fn = new EuFn();
							// fn.setIdFn(idFn);
							fn.setTypeFn("Inr");
							fn.setDateFn(date);
							fn.setEuCapa(null);
							fn.setMontant(montant);
							fn.setSortie(0);
							fn.setEntree(0);
							fn.setSolde(0);
							fn.setOrigineFn(1);
							fn.setMtSolde(montant);
							fnService.add(fn);

							// Mise à jour du SMC
							List<EuCompteCredit> credits = ccService.findByCodeCompte(codeCompte);
							// creditService.updateListSmc(credits, compteDest, op, montant);
							if ("TCNCS".equals(codeCategorie) || "TPN".equals(codeCategorie)) {
								creditService.updateListSmc(credits, compteDest, op, montant);
							}
							// Mise à jour du FN
							if ("TI".equals(codeCategorie)) {
								creditService.updateListFn(credits, compteDest, op, montant);
							}

							// Mise à jour du compte échangé
							compteOrigine.setSolde(compteOrigine.getSolde() - montant);
							compteService.update(compteOrigine);

							return true;
						} else {
							log.error("Echec de l'echange du NR/NB",
									new CompteNonIntegreException("Le solde du compte " + compteOrigine.getCodeCompte()
											+ " qui est " + NumberFormat.getInstance().format(compteOrigine.getSolde())
											+ " est différent de la somme des crédits qui est "
											+ NumberFormat.getInstance().format(mont_credits)));
							return false;
						}
					} else {
						log.error("Echec de l'echange du NR/NB",
								new SoldeInsuffisantException("Le solde du compte " + compteOrigine.getCodeCompte()
										+ " qui est " + NumberFormat.getInstance().format(compteOrigine.getSolde())
										+ " est insuffisant pour effectuer cette opération de "
										+ NumberFormat.getInstance().format(montant)));
						return false;
					}
				} else {
					log.error("Echec de l'echange NR/NB", new CompteNonIntegreException(
							"Ce compte " + compteOrigine.getCodeCompte() + " n'existe pas ou est incorrect!"));
					return false;
				}
			} else {
				log.error("Echec de l'echange NR/NB",
						new IllegalArgumentException("Veuillez fournir le montant de l'opération!"));
				return false;
			}
		} catch (Exception e) {
			log.error("Echec de l'echange du NR/NB", e);
			return false;
		}
	}

	public EuEchange echangeNRNN(String codeMembre, String codeMembreDest, String numAppelOffre, String typeProduit,
			double montant, Long idUtilisateur) {
		System.out.println("Exchange NR to  NN");
		Date date = new Date();
		String codeCategorie = StringUtils.EMPTY;
		String codeCompte = StringUtils.EMPTY;
		String codeProduit = StringUtils.EMPTY;
		if (codeMembre.endsWith("M")) {
			codeCompte = "NR-TI-" + codeMembre;
			codeCategorie = "TI";
		} else {
			codeCompte = "NR-TCNCS-" + codeMembre;
			codeCategorie = "TCNCS";
		}
		if (codeMembreDest.endsWith("P")) {
			codeProduit = "RPG";
		} else {
			codeProduit = "I";
		}
		// Récupération du SMCIPN correspondant au numéro d'appel d'offre
		EuSmcipnpwi smcipn = smcipnService.findByNumeroAppel(numAppelOffre);
		if (smcipn != null) {
			// Récupération du GCsc et del'appel d'offre
			EuGcsc gcsc = gcscService.findByEuSmcipn_CodeSmcipn(smcipn.getCodeSmcipn());
			EuAppelOffre offre = offreService.findAppelOffresByNumero(smcipn.getNumeroAppel());
			if (gcsc == null && offre.getTypeAppelOffre().equals("inrpre")) {
				throw new IllegalStateException("le TEGCSC de remboursement du SMCIPN n'est pas défini!");
			}
			String codeCompteOrig = codeCompte;
			EuCompte compteOrigine = compteService.findCompteById(codeCompteOrig);
			if (compteOrigine != null && compteOrigine.getSolde() >= montant) {
				String codeCompteDest = "NN-CAPA-" + codeMembreDest;
				try {
					List<EuCompteCredit> credit_s = ccService.findByCodeCompte(compteOrigine.getCodeCompte());
					if (credit_s.isEmpty()) {
						throw new CompteNonIntegreException("Il n'y a pas de crédits correspondant à ce compte");
					} else {
						double somme_credit = 0;
						somme_credit = credit_s.stream().mapToDouble((c) -> c.getMontantCredit()).sum();
						if (compteOrigine.getSolde() != somme_credit) {
							throw new CompteNonIntegreException("Il n'y a pas de crédits correspondant à ce compte");
						}
					}
					List<EuCompteCredit> credits = ccService.findByCompteandSource(compteOrigine.getCodeCompte(),
							smcipn.getCodeSmcipn());
					if (credits.isEmpty()) {
						throw new CompteNonIntegreException("Il n'y a pas de crédits correspondant à ce compte");
					} else {
						double somme_credit = 0;
						somme_credit = credits.stream().map((c) -> c.getMontantCredit()).reduce(somme_credit,
								(accumulator, _item) -> accumulator + _item);
						if (montant > somme_credit) {
							throw new CompteNonIntegreException("Le solde de votre compte est insuffisant!!!");
						}
					}

					EuCategorieCompte categorie = new EuCategorieCompte();
					categorie.setCodeCat(codeCategorie);

					EuOperation op = new EuOperation();
					op.setEuCategorieCompte(categorie);
					op.setCodeProduit(codeProduit);
					op.setDateOp(date);
					op.setLibOp("Echange de " + codeCategorie);
					op.setTypeOp("E");
					op.setMontantOp(montant);
					if (codeMembre.endsWith("P")) {
						op.setEuMembre(compteOrigine.getEuMembre());
					} else {
						op.setEuMembreMorale(compteOrigine.getEuMembreMorale());
					}
					// op.setIdOperation(idOp + 1);
					EuUtilisateur user = new EuUtilisateur();
					if (idUtilisateur != null) {
						user.setIdUtilisateur(idUtilisateur);
						op.setEuUtilisateur(user);
					}
					op.setHeureOp(date);
					op = opService.add(op);

					// Création de l'opération d'échange
					EuEchange ech = new EuEchange();
					// ech.setIdEchange(id_echange + 1);
					ech.setCatEchange(codeProduit);
					ech.setTypeEchange("NR/NN");
					ech.setMontantEchange(montant);
					ech.setMontant(montant);
					ech.setCompenser(0);
					ech.setRegler(0);
					ech.setEuCompte(compteOrigine);
					ech.setCodeCompteObt(codeCompteDest);
					ech.setDateEchange(date);
					ech.setDateReglement(date);
					if (codeMembre.endsWith("M")) {
						ech.setEuMembreMorale(compteOrigine.getEuMembreMorale());
						ech.setEuMembre(null);
					} else {
						ech.setEuMembreMorale(null);
						ech.setEuMembre(compteOrigine.getEuMembre());
					}
					ech.setCodeProduit(codeProduit);
					ech.setAgio(0);
					ech = echService.add(ech);

					String typeNn = "INR";
					String typeCapa = "I";
					if (codeMembre.endsWith("P")) {
						typeNn = "RPGNR";
						typeCapa = "RPG";
					}

					EuNn nn = nnService.EmettreNn(typeNn, montant, date);

					EuUtiliserNn utilNn = new EuUtiliserNn();
					utilNn.setCodeMembreNb(codeMembreDest);
					utilNn.setIdNn(nn.getIdNn());
					utilNn.setCodeMembreNn(codeMembre);
					utilNn.setCodeProduit(typeNn);
					utilNn.setCodeProduitNn("OPI");
					utilNn.setCodeSms(null);
					utilNn.setDateTransfert(date);
					utilNn.setIdOperation(op.getIdOperation());
					utilNn.setIdUtilisateur(null);
					utilNn.setMontTransfert(montant);
					utilNn.setNumBon(null);
					utilNn.setMotif("Echange de SMCIPN en BAi");
					utiliserNNService.add(utilNn);

					EuCompte compteDest = compteService.getById(codeCompteDest);
					if (Objects.nonNull(compteDest)) {
						compteDest.setSolde(compteDest.getSolde() + montant);
						compteService.update(compteDest);
					} else {
						compteDest = new EuCompte();
						EuCategorieCompte cat = new EuCategorieCompte();
						cat.setCodeCat("CAPA");
						EuTypeCompte typeCompte = new EuTypeCompte();
						typeCompte.setCodeTypeCompte("NN");
						compteDest.setCodeCompte(codeCompteDest);
						compteDest.setEuCategorieCompte(cat);
						compteDest.setEuTypeCompte(typeCompte);
						compteDest.setLibCompte(cat.getCodeCat());
						if (codeMembre.endsWith("P")) {
							compteDest.setEuMembre(membreService.findById(codeCompteDest));
						} else {
							compteDest.setEuMembreMorale(moraleService.findById(codeCompteDest));
						}
						compteDest.setDesactiver("N");
						compteDest.setSolde(montant);
						compteDest.setDateAlloc(date);
						compteService.create(compteDest);
					}

					EuCapa capa = new EuCapa();
					capa.setCodeCapa("CAPA" + ServerUtil.formatDate2(date));
					capa.setDateCapa(date);
					capa.setCodeMembre(codeMembreDest);
					capa.setCodeProduit(typeCapa);
					capa.setTypeCapa(typeCapa);
					capa.setOrigineCapa("BAi");
					capa.setMontantCapa(montant);
					capa.setMontantUtiliser(0);
					capa.setMontantSolde(montant);
					capa.setEuCompte(compteDest);
					capa.setEtatCapa("Actif");
					capa.setIdOperation(op.getIdOperation());
					capa.setHeureCapa(date);
					capaService.add(capa);

					// Mise à jour des FNs
					creditService.updateListFns(credits, op, montant);

					// Mise à jour du compte du crédit échangé
					compteOrigine.setSolde(compteOrigine.getSolde() - montant);
					compteService.update(compteOrigine);
					return ech;
				} catch (Exception e) {
					throw e;
				}
			} else {
				throw new CompteNonIntegreException("Le solde de votre compte est insuffisant!!!");
			}
		} else {
			throw new IllegalStateException(
					"Echec de l'opération : Il n'y a pas de SMCIPN associé à cet appel d'offre!!!");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String doSmcipnInterm(String codeInterim, String codeTegc, String codeIntermed, String codeBl, Long idUser,
			double montant) {
		if (StringUtils.isBlank(codeInterim) && StringUtils.isBlank(codeIntermed) && StringUtils.isBlank(codeTegc)
				&& StringUtils.isBlank(codeBl)) {
			throw new IllegalArgumentException(
					"Le code Membre des acteurs, le code Reglement et le TE de l'interim doivent être fournis");
		}
		if (montant <= 0) {
			throw new IllegalArgumentException("Le montant de la subvention doit être supérieur à zéro!");
		}
		EuMembre benef = null;
		EuMembreMorale mbenef = null;
		if (codeInterim.endsWith("M")) {
			mbenef = moraleService.findById(codeInterim);
		} else {
			benef = membreService.findById(codeInterim);
		}
		if (Objects.isNull(mbenef) && Objects.isNull(benef)) {
			throw new CompteNonTrouveException(
					"Le code Membre des acteurs n'existent pas ou n'ont pas de compte marchand!");
		}
		EuTegc tegc = tegcService.getById(codeTegc);
		if (Objects.nonNull(tegc)) {
			EuBon bon = bonService.findByBonCode(codeBl, 1);
			if (Objects.nonNull(bon)) {
				Date date = new Date();
				String codeCompteTi;
				String codeCategorie;
				if (codeInterim.endsWith("M")) {
					codeCompteTi = "NR-TI-" + codeInterim;
					codeCategorie = "TI";
				} else {
					codeCompteTi = "NR-TCNCS-" + codeInterim;
					codeCategorie = "TCNCS";
				}

				Long idOp = opService.getLastOperation();
				if (idOp == null) {
					idOp = 0L;
				}
				EuCategorieCompte codeCat = new EuCategorieCompte();
				codeCat.setCodeCat(codeCategorie);

				EuOperation op = new EuOperation();
				op.setEuCategorieCompte(codeCat);
				op.setCodeProduit("Inr");
				op.setDateOp(date);
				op.setLibOp("Subvention Inr KIT Technopole");
				op.setTypeOp("SMCIPN");
				op.setIdOperation(idOp + 1);
				if (idUser != null) {
					EuUtilisateur user = new EuUtilisateur();
					user.setIdUtilisateur(idUser);
					op.setEuUtilisateur(user);
				}
				op.setHeureOp(date);
				opService.add(op);

				Long idappel = offreService.getLastinsertedId();
				if (idappel == null) {
					idappel = 1L;
				} else {
					idappel++;
				}
				EuAppelOffre offre = new EuAppelOffre();
				offre.setIdAppelOffre(idappel);
				offre.setDateCreation(date);
				offre.setCodeMembreMorale(codeInterim);
				offre.setDescripAppelOffre("SMCIPN Pour Revendeur " + codeIntermed);
				offre.setDureeProjet(0);
				offre.setNomAppelOffre("Rachat de BPS");
				offre.setIdDemande(null);
				offre.setIdUtilisateur(idUser);
				offre.setMembreMoraleExecutante(codeInterim);
				offre.setNumeroOffre("AO" + StringUtils.leftPad(String.valueOf(idappel), 4, '0'));
				offre.setTypeAppelOffre("kit");
				if (codeInterim.endsWith("M")) {
					offre.setTypeSmcipn("smci");
				} else {
					offre.setTypeSmcipn("smcpn");
				}
				offre.setPublier(1);
				offre = offreService.add(offre);

				EuCompte compteti = compteService.getById(codeCompteTi);
				if (Objects.nonNull(compteti)) {
					compteti.setSolde(compteti.getSolde() + montant);
					compteService.update(compteti);
				} else {
					compteti = new EuCompte();
					compteti.setSolde(montant);
					compteti.setCodeCompte(codeCompteTi);
					compteti.setDateAlloc(date);
					if (codeInterim.endsWith("M")) {
						compteti.setEuMembreMorale(mbenef);
					} else {
						compteti.setEuMembre(benef);
					}
					compteti.setDesactiver("0");
					if (codeInterim.endsWith("M")) {
						compteti.setLibCompte("Investissement de subvention");
					} else {
						compteti.setLibCompte("CNCS de subvention");
					}
					EuTypeCompte typecompte = new EuTypeCompte();
					typecompte.setCodeTypeCompte("NR");
					compteti.setEuTypeCompte(typecompte);
					EuCategorieCompte catcompte = new EuCategorieCompte();
					catcompte.setCodeCat(codeCategorie);
					catcompte.setCodeTypeCompte("NR");
					compteti.setEuCategorieCompte(catcompte);
					compteService.create(compteti);
				}

				EuSmcipnpwi smcipn = smcipnService.findByNumeroAppel(offre.getNumeroOffre());
				if (Objects.nonNull(smcipn)) {
					smcipn.setEtatAllocInvestis(1);
					smcipn.setMontInvestis(montant);
					smcipn.setInvestisAlloue(montant);
					smcipnService.update(smcipn);
				} else {
					String newCodeSmcipn;
					String codeSmcipn = smcipnService.findLastByCodeMembreAndTypeSmcipn(codeInterim, "SMCIPNWI");
					if (codeSmcipn == null) {
						newCodeSmcipn = "WI" + codeInterim + "0001";
					} else {
						String code = codeSmcipn.substring(codeSmcipn.length() - 4);
						int numsmc = Integer.valueOf(code);
						numsmc++;
						newCodeSmcipn = "WI" + codeInterim + StringUtils.leftPad(String.valueOf(numsmc), 4, "0");
					}
					smcipn = new EuSmcipnpwi();
					smcipn.setCodeSmcipn(newCodeSmcipn);
					smcipn.setCodeMembre(codeInterim);
					smcipn.setDateSmcipn(date);
					smcipn.setTypeSmcipn("SMCIPNWI");
					smcipn.setEtatAllocInvestis(1);
					smcipn.setRembourser(0);
					smcipn.setEtatAllocSalaire(0);
					smcipn.setSalaireAlloue(0);
					if (codeInterim.endsWith("M")) {
						smcipn.setMontSalaire(0);
						smcipn.setSalaireAlloue(0);
						smcipn.setMontInvestis(montant);
						smcipn.setInvestisAlloue(montant);
					} else {
						smcipn.setMontInvestis(0);
						smcipn.setInvestisAlloue(0);
						smcipn.setMontSalaire(montant);
						smcipn.setSalaireAlloue(montant);
					}
					if (idUser != null) {
						smcipn.setIdUtilisateur(idUser);
					} else {
						smcipn.setIdUtilisateur(null);
					}
					smcipn.setNumeroAppel(offre.getNumeroOffre());
					smcipn.setTypeNr("nr");
					smcipnService.add(smcipn);
				}

				SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
				EuProduit produit = new EuProduit();
				if (codeInterim.endsWith("M")) {
					produit.setCodeProduit("Inr");
				} else {
					produit.setCodeProduit("CNCSnr");
				}

				Long idCredit = ccService.getLastCreditInsertedId();
				if (idCredit == null) {
					idCredit = 1L;
				} else {
					idCredit = idCredit + 1;
				}
				EuCompteCredit cc = new EuCompteCredit();
				cc.setIdCredit(idCredit);
				cc.setCodeMembre(codeInterim);
				cc.setMontantPlace(montant);
				cc.setMontantCredit(montant);
				cc.setEuOperation(op);
				cc.setEuProduit(produit);
				cc.setCodeTypeCredit(null);
				cc.setSource(codeInterim + dateFormatter.format(date));
				cc.setCompteSource(smcipn.getCodeSmcipn());
				cc.setDateOctroi(date);
				cc.setDatedeb(date);
				Date datefin = ServerUtil.ajouterJours(date, 30);
				cc.setDatefin(datefin);
				cc.setRenouveller("N");
				cc.setBnp(0);
				cc.setKrr("N");
				cc.setAffecter(0);
				if (codeInterim.endsWith("M")) {
					cc.setCodeTypeCredit("SMCI");
				} else {
					cc.setCodeTypeCredit("SMCPN");
				}
				cc.setPrk(0d);
				cc.setEuCompte(compteti);
				cc.setDomicilier(0);
				cc.setNbreRenouvel(0);
				cc.setTypeProduit("PO");
				cc.setTypeRecurrent("nr");
				cc = ccService.create(cc);

				if (codeInterim.endsWith("M")) {
					List<EuFn> fns = fnService.findByOrigineFn(1);
					creditService.updateSmcipnFns(fns, date, smcipn, montant);
				} else {
					List<EuSmc> smcs = smcService.findByOrigineSmc();
					creditService.updateSmcipnSmcs(smcs, date, smcipn, idCredit, montant);
				}

				System.out.println("Creation ou Mise à jour du GCSC");
				EuGcsc sc = gcscService.findBySmcipnAndBenef(smcipn.getCodeSmcipn(), codeInterim);
				if (Objects.isNull(sc)) {
					long idGcsc = 0;
					if (gcscService.count() > 0) {
						idGcsc = gcscService.findLastGcscInsertedId() + 1;
					} else {
						idGcsc = 1;
					}
					sc = new EuGcsc();
					sc.setCodeDomicilier(null);
					sc.setCredit(montant);
					sc.setDebit(0);
					sc.setSolde(montant);
					sc.setCodeMembre(codeInterim);
					sc.setIdGcsc(idGcsc);
					sc.setEuSmcipnpwi(smcipn);
					sc.setCodeTegc(codeTegc);
					gcscService.add(sc);
				} else {
					sc.setCredit(sc.getCredit() + montant);
					sc.setSolde(sc.getCredit() - sc.getDebit());
					if (StringUtils.isBlank(sc.getCodeTegc())) {
						sc.setCodeTegc(codeTegc);
					}
					gcscService.update(sc);
				}

				EuActeur det_source = acteurService.findActeurByTypeAndActivite("GAC_DETENTRICE", "SOURCE");
				EuActeur techno = acteurService.findByActeurAndTypeAndActivite(det_source.getCodeActeur(), "TECHNOPOLE",
						"SOURCE");
				EuActeur filiere = acteurService.findByActeurAndTypeAndActivite(det_source.getCodeActeur(), "FILIERE",
						"SOURCE");
				EuActeur acnev = acteurService.findByActeurAndTypeAndActivite(det_source.getCodeActeur(), "ACNEV",
						"SOURCE");

				Long idTr = transnrService.getLastInsertedId();
				if (idTr == null) {
					idTr = 1L;
				} else {
					idTr++;
				}
				EuTransfertNr transfert = new EuTransfertNr();
				transfert.setIdTransfertNr(idTr);
				transfert.setCodeMembreBenef(filiere.getCodeMembre());
				transfert.setCodeMembreTransfert(techno.getCodeMembre());
				transfert.setDateTransfert(date);
				transfert.setMontTransfert(montant);
				if (codeInterim.endsWith("M")) {
					transfert.setTypeNr("SMCI");
				} else {
					transfert.setTypeNr("SMCPN");
				}
				transfert.setIdUtilisateur(idUser);
				transfert.setLibelleTransfert(offre.getNomAppelOffre());
				transfert.setNumAppelOffre(offre.getNumeroOffre());
				transnrService.add(transfert);

				idTr++;
				transfert = new EuTransfertNr();
				transfert.setIdTransfertNr(idTr);
				transfert.setCodeMembreBenef(acnev.getCodeMembre());
				transfert.setCodeMembreTransfert(filiere.getCodeMembre());
				transfert.setDateTransfert(date);
				transfert.setMontTransfert(montant);
				if (codeInterim.endsWith("M")) {
					transfert.setTypeNr("SMCI");
				} else {
					transfert.setTypeNr("SMCPN");
				}
				transfert.setIdUtilisateur(idUser);
				transfert.setLibelleTransfert(offre.getNomAppelOffre());
				transfert.setNumAppelOffre(offre.getNumeroOffre());
				transnrService.add(transfert);

				idTr++;
				transfert = new EuTransfertNr();
				transfert.setIdTransfertNr(idTr + 1);
				transfert.setCodeMembreBenef(codeInterim);
				transfert.setCodeMembreTransfert(acnev.getCodeMembre());
				transfert.setDateTransfert(date);
				transfert.setMontTransfert(montant);
				if (codeInterim.endsWith("M")) {
					transfert.setTypeNr("SMCI");
				} else {
					transfert.setTypeNr("SMCPN");
				}
				transfert.setIdUtilisateur(idUser);
				transfert.setLibelleTransfert(offre.getNomAppelOffre());
				transfert.setNumAppelOffre(offre.getNumeroOffre());
				transnrService.add(transfert);

				tegc.setSubvention(0);
				tegcService.update(tegc);

				return offre.getNumeroOffre();
			} else {
				throw new CompteNonTrouveException("Le bon de livraison spécifié n'est pas disponible!");
			}
		} else {
			throw new CompteNonTrouveException("Cet Interim ne dispose pas d'un TE ou le TE n'est pas bien configure!");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String doSmcipnComm(String codeInterim, String codeMembre, String codeTegc, String typePaiement,
			double montant) {
		if (StringUtils.isBlank(codeMembre) && StringUtils.isBlank(typePaiement)) {
			throw new IllegalArgumentException(
					"Le code Membre des acteurs et le type de paiement doivent être fournis");
		}
		if (montant <= 0) {
			throw new IllegalArgumentException("Le montant de la subvention doit être supérieur à zéro!");
		}
		EuMembre benef = null;
		EuMembreMorale mbenef = null;
		if (codeInterim.endsWith("M")) {
			mbenef = moraleService.findById(codeInterim);
		} else {
			benef = membreService.findById(codeInterim);
		}
		if (Objects.isNull(mbenef) && Objects.isNull(benef)) {
			throw new CompteNonTrouveException(
					"Le code Membre des acteurs n'existent pas ou n'ont pas de compte marchand!");
		}

		EuTegc tegc = tegcService.getById(codeTegc);
		if (Objects.nonNull(tegc) && (tegc.getTypeTegc().equalsIgnoreCase("PRESTATAIRE")
				|| tegc.getTypeTegc().equalsIgnoreCase("DISTRIBUTEUR"))) {
			Date date = new Date();
			String codeCompteTi = StringUtils.EMPTY;
			String codeCategorie = StringUtils.EMPTY;
			if (codeInterim.endsWith("M")) {
				codeCompteTi = "NR-TI-" + codeInterim;
				codeCategorie = "TI";
			} else {
				codeCompteTi = "NR-TCNCS-" + codeInterim;
				codeCategorie = "TCNCS";
			}

			/*
			 * Long idOp = opService.getLastOperation(); if (idOp == null) { idOp = 0L; }
			 */
			EuCategorieCompte codeCat = new EuCategorieCompte();
			codeCat.setCodeCat(codeCategorie);

			EuOperation op = new EuOperation();
			op.setEuCategorieCompte(codeCat);
			op.setCodeProduit("Inr");
			op.setDateOp(date);
			op.setLibOp("Subvention Inr KIT Technopole");
			op.setTypeOp("SMCIPN");
			// op.setIdOperation(idOp + 1);
			op.setEuUtilisateur(null);
			op.setHeureOp(date);
			opService.add(op);

			/*
			 * Long idappel = offreService.getLastinsertedId(); if (idappel == null) {
			 * idappel = 1L; } else { idappel++; }
			 */
			EuAppelOffre offre = new EuAppelOffre();
			// offre.setIdAppelOffre(idappel);
			offre.setDateCreation(date);
			offre.setCodeMembreMorale(codeInterim);
			offre.setDureeProjet(0);
			if (typePaiement.equals("COM")) {
				offre.setNomAppelOffre("Paiement de commission");
				offre.setDescripAppelOffre("SMCIPN Pour payer la commission de l'intégrateur " + codeMembre);
			} else {
				offre.setNomAppelOffre("Achat et Vente Réciproque de BPS");
				offre.setDescripAppelOffre("SMCIPN Pour Achat et Vente de :" + codeMembre);
			}
			offre.setIdDemande(null);
			offre.setIdUtilisateur(null);
			offre.setMembreMoraleExecutante(codeInterim);
			offre.setNumeroOffre("AO" + ServerUtil.GenererUniqueCode());
			offre.setTypeAppelOffre("kit");
			if (codeInterim.endsWith("M")) {
				offre.setTypeSmcipn("smci");
			} else {
				offre.setTypeSmcipn("smcpn");
			}
			offre.setPublier(1);
			offre = offreService.add(offre);

			EuCompte compteti = compteService.getById(codeCompteTi);
			if (Objects.nonNull(compteti)) {
				compteti.setSolde(compteti.getSolde() + montant);
				compteService.update(compteti);
			} else {
				compteti = new EuCompte();
				compteti.setSolde(montant);
				compteti.setCodeCompte(codeCompteTi);
				compteti.setDateAlloc(date);
				if (codeInterim.endsWith("M")) {
					compteti.setEuMembreMorale(mbenef);
				} else {
					compteti.setEuMembre(benef);
				}
				compteti.setDesactiver("0");
				if (codeInterim.endsWith("M")) {
					compteti.setLibCompte("Investissement de subvention");
				} else {
					compteti.setLibCompte("CNCS de subvention");
				}
				EuTypeCompte typecompte = new EuTypeCompte();
				typecompte.setCodeTypeCompte("NR");
				compteti.setEuTypeCompte(typecompte);
				EuCategorieCompte catcompte = new EuCategorieCompte();
				catcompte.setCodeCat(codeCategorie);
				catcompte.setCodeTypeCompte("NR");
				compteti.setEuCategorieCompte(catcompte);
				compteService.create(compteti);
			}

			EuSmcipnpwi smcipn = smcipnService.findByNumeroAppel(offre.getNumeroOffre());
			if (Objects.nonNull(smcipn)) {
				smcipn.setEtatAllocInvestis(1);
				smcipn.setMontInvestis(montant);
				smcipn.setInvestisAlloue(montant);
				smcipnService.update(smcipn);
			} else {
				String newCodeSmcipn = "P" + codeInterim + "-" + ServerUtil.GenerateUnicCode();
				smcipn = new EuSmcipnpwi();
				smcipn.setCodeSmcipn(newCodeSmcipn);
				smcipn.setCodeMembre(codeInterim);
				smcipn.setDateSmcipn(date);
				if (typePaiement.equals("COM")) {
					smcipn.setTypeSmcipn("SMCIPNP");
				} else {
					smcipn.setTypeSmcipn("SMCIPNWI");
				}
				smcipn.setEtatAllocInvestis(1);
				smcipn.setRembourser(0);
				smcipn.setEtatAllocSalaire(0);
				smcipn.setSalaireAlloue(0);
				if (codeInterim.endsWith("M")) {
					smcipn.setMontSalaire(0);
					smcipn.setSalaireAlloue(0);
					smcipn.setMontInvestis(montant);
					smcipn.setInvestisAlloue(montant);
				} else {
					smcipn.setMontInvestis(0);
					smcipn.setInvestisAlloue(0);
					smcipn.setMontSalaire(montant);
					smcipn.setSalaireAlloue(montant);
				}
				smcipn.setIdUtilisateur(null);
				smcipn.setNumeroAppel(offre.getNumeroOffre());
				smcipn.setTypeNr("nr");
				smcipn = smcipnService.add(smcipn);
			}

			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
			EuProduit produit = new EuProduit();
			if (codeInterim.endsWith("M")) {
				produit.setCodeProduit("Inr");
			} else {
				produit.setCodeProduit("CNCSnr");
			}

			/*
			 * Long idCredit = ccService.getLastCreditInsertedId(); if (idCredit == null) {
			 * idCredit = 1L; } else { idCredit = idCredit + 1; }
			 */
			EuCompteCredit cc = new EuCompteCredit();
			// cc.setIdCredit(idCredit);
			cc.setCodeMembre(codeInterim);
			cc.setMontantPlace(montant);
			cc.setMontantCredit(montant);
			cc.setEuOperation(op);
			cc.setEuProduit(produit);
			cc.setCodeTypeCredit(null);
			cc.setSource(codeInterim + dateFormatter.format(date));
			cc.setCompteSource(smcipn.getCodeSmcipn());
			cc.setDateOctroi(date);
			cc.setDatedeb(date);
			Date datefin = ServerUtil.ajouterJours(date, 30);
			cc.setDatefin(datefin);
			cc.setRenouveller("N");
			cc.setBnp(0);
			cc.setKrr("N");
			cc.setAffecter(0);
			if (codeInterim.endsWith("M")) {
				cc.setCodeTypeCredit("SMCI");
			} else {
				cc.setCodeTypeCredit("SMCPN");
			}
			cc.setPrk(0d);
			cc.setEuCompte(compteti);
			cc.setDomicilier(0);
			cc.setNbreRenouvel(0);
			cc.setTypeProduit("PO");
			cc.setTypeRecurrent("nr");
			cc = ccService.create(cc);

			if (codeInterim.endsWith("M")) {
				List<EuFn> fns = fnService.findByOrigineFn(1);
				creditService.updateSmcipnFns(fns, date, smcipn, montant);
			} else {
				List<EuSmc> smcs = smcService.findByOrigineSmc();
				creditService.updateSmcipnSmcs(smcs, date, smcipn, cc.getIdCredit(), montant);
			}

			EuGcsc sc = gcscService.findBySmcipnAndBenef(smcipn.getCodeSmcipn(), codeInterim);
			if (Objects.isNull(sc)) {
				/*
				 * long idGcsc = 0; if (gcscService.count() > 0) { idGcsc =
				 * gcscService.findLastGcscInsertedId() + 1; } else { idGcsc = 1; }
				 */
				sc = new EuGcsc();
				// sc.setIdGcsc(idGcsc);
				sc.setCodeDomicilier(null);
				sc.setCredit(montant);
				sc.setDebit(0);
				sc.setSolde(montant);
				sc.setCodeMembre(codeInterim);
				sc.setEuSmcipnpwi(smcipn);
				sc.setCodeTegc(codeTegc);
				gcscService.add(sc);
			} else {
				sc.setCredit(sc.getCredit() + montant);
				sc.setSolde(sc.getCredit() - sc.getDebit());
				if (StringUtils.isBlank(sc.getCodeTegc())) {
					sc.setCodeTegc(codeTegc);
				}
				gcscService.update(sc);
			}

			EuActeur det_source = acteurService.findActeurByTypeAndActivite("GAC_DETENTRICE", "SOURCE");
			EuActeur techno = acteurService.findByActeurAndTypeAndActivite(det_source.getCodeActeur(), "TECHNOPOLE",
					"SOURCE");
			EuActeur filiere = acteurService.findByActeurAndTypeAndActivite(det_source.getCodeActeur(), "FILIERE",
					"SOURCE");
			EuActeur acnev = acteurService.findByActeurAndTypeAndActivite(det_source.getCodeActeur(), "ACNEV",
					"SOURCE");

			/*
			 * Long idTr = transnrService.getLastInsertedId(); if (idTr == null) { idTr =
			 * 1L; } else { idTr++; }
			 */
			EuTransfertNr transfert = new EuTransfertNr();
			// transfert.setIdTransfertNr(idTr);
			transfert.setCodeMembreBenef(filiere.getCodeMembre());
			transfert.setCodeMembreTransfert(techno.getCodeMembre());
			transfert.setDateTransfert(date);
			transfert.setMontTransfert(montant);
			if (codeInterim.endsWith("M")) {
				transfert.setTypeNr("SMCI");
			} else {
				transfert.setTypeNr("SMCPN");
			}
			transfert.setIdUtilisateur(null);
			transfert.setLibelleTransfert(offre.getNomAppelOffre());
			transfert.setNumAppelOffre(offre.getNumeroOffre());
			transnrService.add(transfert);

			// idTr++;
			transfert = new EuTransfertNr();
			// transfert.setIdTransfertNr(idTr);
			transfert.setCodeMembreBenef(acnev.getCodeMembre());
			transfert.setCodeMembreTransfert(filiere.getCodeMembre());
			transfert.setDateTransfert(date);
			transfert.setMontTransfert(montant);
			if (codeInterim.endsWith("M")) {
				transfert.setTypeNr("SMCI");
			} else {
				transfert.setTypeNr("SMCPN");
			}
			transfert.setIdUtilisateur(null);
			transfert.setLibelleTransfert(offre.getNomAppelOffre());
			transfert.setNumAppelOffre(offre.getNumeroOffre());
			transnrService.add(transfert);

			// idTr++;
			transfert = new EuTransfertNr();
			// transfert.setIdTransfertNr(idTr + 1);
			transfert.setCodeMembreBenef(codeInterim);
			transfert.setCodeMembreTransfert(acnev.getCodeMembre());
			transfert.setDateTransfert(date);
			transfert.setMontTransfert(montant);
			if (codeInterim.endsWith("M")) {
				transfert.setTypeNr("SMCI");
			} else {
				transfert.setTypeNr("SMCPN");
			}
			transfert.setIdUtilisateur(null);
			transfert.setLibelleTransfert(offre.getNomAppelOffre());
			transfert.setNumAppelOffre(offre.getNumeroOffre());
			transnrService.add(transfert);

			tegc.setSubvention(0);
			tegcService.update(tegc);
			return offre.getNumeroOffre();
		} else {
			throw new CompteNonTrouveException(
					"PaiementComponent : Ce TE " + codeTegc + " n'existe pas ou n'est pas bien configure!");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String doSmcipnCharge(String codeMembre, String codeTegc, String libelleCharge, double montant) {
		if (StringUtils.isBlank(codeMembre) && StringUtils.isBlank(libelleCharge)) {
			throw new IllegalArgumentException(
					"Le code Membre des acteurs et le libellé de la Charge doivent être fournis");
		}
		if (montant <= 0) {
			throw new IllegalArgumentException("Le montant de la subvention doit être supérieur à zéro!");
		}
		EuMembre benef = null;
		EuMembreMorale mbenef = null;
		if (codeMembre.endsWith("M")) {
			mbenef = moraleService.findById(codeMembre);
		} else {
			benef = membreService.findById(codeMembre);
		}
		if (Objects.isNull(mbenef) && Objects.isNull(benef)) {
			throw new CompteNonTrouveException(
					"Le code Membre " + codeMembre + " des acteurs n'existent pas ou n'ont pas de compte marchand!");
		}

		EuTegc tegc = tegcService.getById(codeTegc);
		if (Objects.nonNull(tegc)) {
			Date date = new Date();
			String codeCompteTi = StringUtils.EMPTY;
			String codeCategorie = StringUtils.EMPTY;
			if (codeMembre.endsWith("M")) {
				codeCompteTi = "NR-TI-" + codeMembre;
				codeCategorie = "TI";
			} else {
				codeCompteTi = "NR-TCNCS-" + codeMembre;
				codeCategorie = "TCNCS";
			}

			Long idOp = opService.getLastOperation();
			if (idOp == null) {
				idOp = 0L;
			}
			EuCategorieCompte codeCat = new EuCategorieCompte();
			codeCat.setCodeCat(codeCategorie);

			EuOperation op = new EuOperation();
			op.setEuCategorieCompte(codeCat);
			op.setCodeProduit("Inr");
			op.setDateOp(date);
			op.setLibOp(libelleCharge);
			op.setTypeOp("SMCIPN");
			op.setIdOperation(idOp + 1);
			op.setEuUtilisateur(null);
			op.setHeureOp(date);
			opService.add(op);

			/*
			 * Long idappel = offreService.getLastinsertedId(); if (idappel == null) {
			 * idappel = 1L; } else { idappel++; }
			 */
			EuAppelOffre offre = new EuAppelOffre();
			// offre.setIdAppelOffre(idappel);
			offre.setDateCreation(date);
			offre.setCodeMembreMorale(codeMembre);
			offre.setDureeProjet(0);
			offre.setNomAppelOffre(libelleCharge);
			offre.setDescripAppelOffre("SMCIPN Pour payer les charges budgétaires");
			offre.setIdDemande(null);
			offre.setIdUtilisateur(null);
			offre.setMembreMoraleExecutante(codeMembre);
			offre.setNumeroOffre("AO" + ServerUtil.GenererUniqueCode());
			offre.setTypeAppelOffre("kit");
			if (codeMembre.endsWith("M")) {
				offre.setTypeSmcipn("smci");
			} else {
				offre.setTypeSmcipn("smcpn");
			}
			offre.setPublier(1);
			offre = offreService.add(offre);

			EuCompte compteti = compteService.getById(codeCompteTi);
			if (Objects.nonNull(compteti)) {
				compteti.setSolde(compteti.getSolde() + montant);
				compteService.update(compteti);
			} else {
				compteti = new EuCompte();
				compteti.setSolde(montant);
				compteti.setCodeCompte(codeCompteTi);
				compteti.setDateAlloc(date);
				if (codeMembre.endsWith("M")) {
					compteti.setEuMembreMorale(mbenef);
				} else {
					compteti.setEuMembre(benef);
				}
				compteti.setDesactiver("0");
				if (codeMembre.endsWith("M")) {
					compteti.setLibCompte("Investissement de subvention");
				} else {
					compteti.setLibCompte("Salaire de subvention");
				}
				EuTypeCompte typecompte = new EuTypeCompte();
				typecompte.setCodeTypeCompte("NR");
				compteti.setEuTypeCompte(typecompte);
				EuCategorieCompte catcompte = new EuCategorieCompte();
				catcompte.setCodeCat(codeCategorie);
				catcompte.setCodeTypeCompte("NR");
				compteti.setEuCategorieCompte(catcompte);
				compteService.create(compteti);
			}

			EuSmcipnpwi smcipn = smcipnService.findByNumeroAppel(offre.getNumeroOffre());
			if (Objects.nonNull(smcipn)) {
				smcipn.setEtatAllocInvestis(1);
				smcipn.setMontInvestis(montant);
				smcipn.setInvestisAlloue(montant);
				smcipn = smcipnService.update(smcipn);
			} else {
				String newCodeSmcipn = null;
				String codeSmcipn = smcipnService.findLastByCodeMembreAndTypeSmcipn(codeMembre, "SMCIPNWI");
				if (codeSmcipn == null) {
					newCodeSmcipn = "WI" + codeMembre + "0001";
				} else {
					String code = codeSmcipn.substring(codeSmcipn.length() - 4);
					int numsmc = Integer.valueOf(code);
					numsmc++;
					newCodeSmcipn = "WI" + codeMembre + StringUtils.leftPad(String.valueOf(numsmc), 4, "0");
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
				if (codeMembre.endsWith("M")) {
					smcipn.setMontSalaire(0);
					smcipn.setSalaireAlloue(0);
					smcipn.setMontInvestis(montant);
					smcipn.setInvestisAlloue(montant);
				} else {
					smcipn.setMontInvestis(0);
					smcipn.setInvestisAlloue(0);
					smcipn.setMontSalaire(montant);
					smcipn.setSalaireAlloue(montant);
				}
				smcipn.setIdUtilisateur(null);
				smcipn.setNumeroAppel(offre.getNumeroOffre());
				smcipn.setTypeNr("nr");
				smcipn = smcipnService.add(smcipn);
			}

			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
			EuProduit produit = new EuProduit();
			if (codeMembre.endsWith("M")) {
				produit.setCodeProduit("Inr");
			} else {
				produit.setCodeProduit("CNCSnr");
			}

			/*
			 * Long idCredit = ccService.getLastCreditInsertedId(); if (idCredit == null) {
			 * idCredit = 1L; } else { idCredit = idCredit + 1; }
			 */
			EuCompteCredit cc = new EuCompteCredit();
			// cc.setIdCredit(idCredit);
			cc.setCodeMembre(codeMembre);
			cc.setMontantPlace(montant);
			cc.setMontantCredit(montant);
			cc.setEuOperation(op);
			cc.setEuProduit(produit);
			cc.setCodeTypeCredit(null);
			cc.setSource(codeMembre + dateFormatter.format(date));
			cc.setCompteSource(smcipn.getCodeSmcipn());
			cc.setDateOctroi(date);
			cc.setDatedeb(date);
			Date datefin = ServerUtil.ajouterJours(date, 30);
			cc.setDatefin(datefin);
			cc.setRenouveller("N");
			cc.setBnp(0);
			cc.setKrr("N");
			cc.setAffecter(0);
			if (codeMembre.endsWith("M")) {
				cc.setCodeTypeCredit("SMCI");
			} else {
				cc.setCodeTypeCredit("SMCPN");
			}
			cc.setPrk(0d);
			cc.setEuCompte(compteti);
			cc.setDomicilier(0);
			cc.setNbreRenouvel(0);
			cc.setTypeProduit("PO");
			cc.setTypeRecurrent("nr");
			cc = ccService.create(cc);

			if (codeMembre.endsWith("M")) {
				List<EuFn> fns = fnService.findByOrigineFn(1);
				creditService.updateSmcipnFns(fns, date, smcipn, montant);
			} else {
				List<EuSmc> smcs = smcService.findByOrigineSmc();
				creditService.updateSmcipnSmcs(smcs, date, smcipn, cc.getIdCredit(), montant);
			}

			EuGcsc sc = gcscService.findBySmcipnAndBenef(smcipn.getCodeSmcipn(), codeMembre);
			if (Objects.isNull(sc)) {
				/*
				 * long idGcsc = 0; if (gcscService.count() > 0) { idGcsc =
				 * gcscService.findLastGcscInsertedId() + 1; } else { idGcsc = 1; }
				 */
				sc = new EuGcsc();
				sc.setCodeDomicilier(null);
				sc.setCredit(montant);
				sc.setDebit(0);
				sc.setSolde(montant);
				sc.setCodeMembre(codeMembre);
				// sc.setIdGcsc(idGcsc);
				sc.setEuSmcipnpwi(smcipn);
				sc.setCodeTegc(codeTegc);
				gcscService.add(sc);
			} else {
				sc.setCredit(sc.getCredit() + montant);
				sc.setSolde(sc.getCredit() - sc.getDebit());
				if (StringUtils.isBlank(sc.getCodeTegc())) {
					sc.setCodeTegc(codeTegc);
				}
				gcscService.update(sc);
			}

			EuActeur det_source = acteurService.findActeurByTypeAndActivite("GAC_DETENTRICE", "SOURCE");
			EuActeur techno = acteurService.findByActeurAndTypeAndActivite(det_source.getCodeActeur(), "TECHNOPOLE",
					"SOURCE");
			EuActeur filiere = acteurService.findByActeurAndTypeAndActivite(det_source.getCodeActeur(), "FILIERE",
					"SOURCE");
			EuActeur acnev = acteurService.findByActeurAndTypeAndActivite(det_source.getCodeActeur(), "ACNEV",
					"SOURCE");

			/*
			 * Long idTr = transnrService.getLastInsertedId(); if (idTr == null) { idTr =
			 * 1L; } else { idTr++; }
			 */
			EuTransfertNr transfert = new EuTransfertNr();
			// transfert.setIdTransfertNr(idTr);
			transfert.setCodeMembreBenef(filiere.getCodeMembre());
			transfert.setCodeMembreTransfert(techno.getCodeMembre());
			transfert.setDateTransfert(date);
			transfert.setMontTransfert(montant);
			if (codeMembre.endsWith("M")) {
				transfert.setTypeNr("SMCI");
			} else {
				transfert.setTypeNr("SMCPN");
			}
			transfert.setIdUtilisateur(null);
			transfert.setLibelleTransfert(offre.getNomAppelOffre());
			transfert.setNumAppelOffre(offre.getNumeroOffre());
			transnrService.add(transfert);

			// idTr++;
			transfert = new EuTransfertNr();
			// transfert.setIdTransfertNr(idTr);
			transfert.setCodeMembreBenef(acnev.getCodeMembre());
			transfert.setCodeMembreTransfert(filiere.getCodeMembre());
			transfert.setDateTransfert(date);
			transfert.setMontTransfert(montant);
			if (codeMembre.endsWith("M")) {
				transfert.setTypeNr("SMCI");
			} else {
				transfert.setTypeNr("SMCPN");
			}
			transfert.setIdUtilisateur(null);
			transfert.setLibelleTransfert(offre.getNomAppelOffre());
			transfert.setNumAppelOffre(offre.getNumeroOffre());
			transnrService.add(transfert);

			// idTr++;
			transfert = new EuTransfertNr();
			// transfert.setIdTransfertNr(idTr + 1);
			transfert.setCodeMembreBenef(codeMembre);
			transfert.setCodeMembreTransfert(acnev.getCodeMembre());
			transfert.setDateTransfert(date);
			transfert.setMontTransfert(montant);
			if (codeMembre.endsWith("M")) {
				transfert.setTypeNr("SMCI");
			} else {
				transfert.setTypeNr("SMCPN");
			}
			transfert.setIdUtilisateur(null);
			transfert.setLibelleTransfert(offre.getNomAppelOffre());
			transfert.setNumAppelOffre(offre.getNumeroOffre());
			transnrService.add(transfert);

			tegc.setSubvention(0);
			tegcService.update(tegc);
			return offre.getNumeroOffre();
		} else {
			throw new CompteNonTrouveException(
					"Cet Interim " + codeMembre + " ne dispose pas d'un TE ou le TE n'est pas bien configure!");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean doApaPreKit(String codeMembre, String codeMembreBenef, String codeTe, String numAppelOffre,
			String nomAppelOffre, String description, int duree, Long idUser, double montant)
			throws DataAccessException {
		if (numAppelOffre.isEmpty()) {
			return false;
		}
		EuMembre benef = null;
		EuMembreMorale mbenef = null;
		if (codeMembreBenef.endsWith("M")) {
			mbenef = moraleService.findById(codeMembreBenef);
		} else {
			benef = membreService.findById(codeMembreBenef);
		}
		System.out.println("KIT Tecno");
		if (Objects.nonNull(benef) || Objects.nonNull(mbenef)) {
			EuActeur act_benef = acteurService.findByMembreAndTypes(codeMembreBenef,
					Arrays.asList("FILIERE", "TECHNOPOLE", "ACNEV"));
			EuTegc tegc = tegcService.getById(codeTe);
			if (Objects.isNull(tegc) && Objects.isNull(act_benef)) {
				throw new CompteNonTrouveException(
						"Le Tegc n° " + codeTe + " n'est pas trouvé ou ce membre n'est pas un administrateur");
			}
			Date date = new Date();
			String codeCompteTi = "NR-TI-" + codeMembreBenef;

			EuCategorieCompte codeCat = new EuCategorieCompte();
			codeCat.setCodeCat("TI");

			EuOperation op = new EuOperation();
			op.setEuCategorieCompte(codeCat);
			op.setCodeProduit("Inr");
			op.setDateOp(date);
			op.setLibOp("Subvention Inr KIT Technopole");
			op.setTypeOp("SI");
			// op.setIdOperation(idOp + 1);
			if (idUser != null) {
				EuUtilisateur user = new EuUtilisateur();
				user.setIdUtilisateur(idUser);
				op.setEuUtilisateur(user);
			}
			op.setHeureOp(date);
			opService.add(op);

			/*
			 * Long idappel = offreService.getLastinsertedId(); if (idappel == null) {
			 * idappel = 1L; } else { idappel++; }
			 */
			EuAppelOffre offre = new EuAppelOffre();
			// offre.setIdAppelOffre(idappel);
			offre.setDateCreation(date);
			offre.setCodeMembreMorale(codeMembreBenef);
			offre.setDescripAppelOffre(description);
			offre.setDureeProjet(duree);
			offre.setNomAppelOffre(nomAppelOffre);
			offre.setIdDemande(null);
			offre.setIdUtilisateur(idUser);
			offre.setMembreMoraleExecutante(codeMembre);
			offre.setNumeroOffre(numAppelOffre);
			offre.setTypeAppelOffre("kit");
			offre.setTypeSmcipn("smci");
			offre.setPublier(1);
			offreService.add(offre);

			System.out.println("KIT Tecno : Mise à jour compte I");

			EuCompte compteti = compteService.getById(codeCompteTi);
			if (Objects.nonNull(compteti)) {
				compteti.setSolde(compteti.getSolde() + montant);
				compteService.update(compteti);
			} else {
				compteti = new EuCompte();
				compteti.setSolde(montant);
				compteti.setCodeCompte(codeCompteTi);
				compteti.setDateAlloc(date);
				if (codeMembreBenef.endsWith("M")) {
					compteti.setEuMembreMorale(mbenef);
				} else {
					compteti.setEuMembre(benef);
				}
				compteti.setDesactiver("0");
				compteti.setLibCompte("Investissement de subvention");
				EuTypeCompte typecompte = new EuTypeCompte();
				typecompte.setCodeTypeCompte("NR");
				compteti.setEuTypeCompte(typecompte);
				EuCategorieCompte catcompte = new EuCategorieCompte();
				catcompte.setCodeCat("TI");
				catcompte.setCodeTypeCompte("NR");
				compteti.setEuCategorieCompte(catcompte);
				compteService.create(compteti);
			}

			EuSmcipnpwi smcipn = smcipnService.findByNumeroAppel(numAppelOffre);
			if (Objects.nonNull(smcipn)) {
				smcipn.setEtatAllocInvestis(1);
				smcipn.setMontInvestis(montant);
				smcipn.setInvestisAlloue(montant);
				smcipnService.update(smcipn);
			} else {
				String codeSmcipn = smcipnService.findLastByCodeMembreAndTypeSmcipn(codeMembre, "SMCIPNP");
				String newCodeSmcipn = getCodeSmcipn(codeSmcipn, codeMembre);
				smcipn = new EuSmcipnpwi();
				smcipn.setCodeSmcipn(newCodeSmcipn);
				smcipn.setCodeMembre(codeMembre);
				smcipn.setDateSmcipn(date);
				smcipn.setTypeSmcipn("SMCIPNP");
				smcipn.setEtatAllocInvestis(1);
				smcipn.setRembourser(0);
				smcipn.setEtatAllocSalaire(0);
				smcipn.setSalaireAlloue(0);
				smcipn.setMontSalaire(0);
				smcipn.setMontInvestis(montant);
				if (idUser != null) {
					smcipn.setIdUtilisateur(idUser);
				} else {
					smcipn.setIdUtilisateur(null);
				}
				smcipn.setInvestisAlloue(montant);
				smcipn.setNumeroAppel(numAppelOffre);
				smcipn.setTypeNr("nr");
				smcipnService.add(smcipn);
			}

			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
			EuProduit produit = new EuProduit();
			produit.setCodeProduit("Inr");

			EuCompteCredit cc = new EuCompteCredit();
			// cc.setIdCredit(idCredit);
			cc.setCodeMembre(codeMembreBenef);
			cc.setMontantPlace(montant);
			cc.setMontantCredit(montant);
			cc.setEuOperation(op);
			cc.setEuProduit(produit);
			cc.setCodeTypeCredit(null);
			cc.setSource(codeMembreBenef + dateFormatter.format(date));
			cc.setCompteSource(smcipn.getCodeSmcipn());
			cc.setDateOctroi(date);
			cc.setDatedeb(date);
			Date datefin = ServerUtil.ajouterJours(date, 30);
			cc.setDatefin(datefin);
			cc.setRenouveller("N");
			cc.setBnp(0);
			cc.setKrr("N");
			cc.setAffecter(0);
			cc.setCodeTypeCredit("SMCI");
			cc.setPrk(0d);
			cc.setEuCompte(compteti);
			cc.setDomicilier(0);
			cc.setNbreRenouvel(0);
			cc.setTypeProduit("PS");
			cc.setTypeRecurrent("nr");
			ccService.create(cc);

			double mont_fn = montant;
			double solde_fn = fnService.findSumByOrigineFn();
			if (solde_fn >= mont_fn) {
				List<EuFn> fns = fnService.findByOrigineFn();
				creditService.updateSmcipnFns(fns, date, smcipn, mont_fn);
			} else {
				/*
				 * Long idFn = fnService.getLastFnInsertedId(); if (idFn == null) { idFn = 1L; }
				 * else { idFn += 1; }
				 */
				EuFn fn = new EuFn();
				// fn.setIdFn(idFn);
				fn.setTypeFn("Inr");
				fn.setDateFn(date);
				fn.setEuCapa(null);
				fn.setMontant(0);
				fn.setSortie(montant);
				fn.setEntree(0);
				fn.setSolde(0);
				fn.setOrigineFn(1);
				fn.setMtSolde(-montant);
				fnService.add(fn);

				Long idServir = servirService.getLastEuServirInsertedId();
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
				servir.setMontantAllouer(Math.abs(fn.getMtSolde()));
				servirService.add(servir);
			}

			EuGcsc sc = gcscService.findBySmcipnAndBenef(smcipn.getCodeSmcipn(), codeMembreBenef);
			if (Objects.isNull(sc)) {
				sc = new EuGcsc();
				sc.setCodeDomicilier(null);
				sc.setCredit(montant);
				sc.setDebit(0);
				sc.setCodeTegc(tegc.getCodeTegc());
				sc.setDateGcsc(date);
				sc.setSolde(montant);
				sc.setCodeMembre(codeMembreBenef);
				// sc.setIdGcsc(idGcsc);
				sc.setEuSmcipnpwi(smcipn);
				gcscService.add(sc);
			} else {
				sc.setCredit(sc.getCredit() + montant);
				sc.setSolde(sc.getCredit() - sc.getDebit());
				gcscService.update(sc);
			}

			EuTransfertNr transfert = new EuTransfertNr();
			transfert.setCodeMembreBenef(codeMembreBenef);
			transfert.setCodeMembreTransfert(codeMembre);
			transfert.setDateTransfert(date);
			transfert.setMontTransfert(montant);
			transfert.setTypeNr("SMCI");
			transfert.setIdUtilisateur(idUser);
			transfert.setLibelleTransfert(offre.getNomAppelOffre());
			transfert.setNumAppelOffre(offre.getNumeroOffre());
			transnrService.add(transfert);

			tegc.setSubvention(1);
			tegcService.update(tegc);

			return true;
		}
		return false;
	}

	public boolean doApaCncsnrPre(String codeMembre, String codeMembreBenef, String numAppelOffre, String nomAppelOffre,
			String description, int duree, Long idUser, double montant) {
		if (StringUtils.isBlank(numAppelOffre) || StringUtils.isBlank(codeMembre)
				|| StringUtils.isBlank(codeMembreBenef) || montant == 0) {
			throw new IllegalArgumentException("Veuillez completer vos infos");
		}
		EuAppelOffre offre = offreService.findAppelOffresByNumero(numAppelOffre);
		if (offre != null && offre.getTypeAppelOffre().equalsIgnoreCase("inrpre")) {
			throw new IllegalArgumentException("Il faut un appel d'offre de type Kit Technopole");
		}
		EuMembre benef = null;
		EuMembreMorale mbenef = null;
		EuTegc tegc = null;
		if (codeMembreBenef.endsWith("M")) {
			mbenef = moraleService.findById(codeMembreBenef);
			tegc = tegcService.findByCodeMembre(codeMembreBenef).get(0);
		} else {
			benef = membreService.findById(codeMembreBenef);
			tegc = tegcService.findByCodeMembrePhysique(codeMembreBenef).get(0);
		}

		if (Objects.isNull(mbenef) && Objects.isNull(benef)) {
			throw new CompteNonTrouveException("Le compte marchand du bénéficiaire n'est pas valide!");
		}
		Date date = new Date();
		String codeCompteTi = "NR-TPN-" + codeMembreBenef;

		EuCategorieCompte codeCat = new EuCategorieCompte();
		codeCat.setCodeCat("TPN");
		try {
			EuOperation op = new EuOperation();
			op.setEuCategorieCompte(codeCat);
			op.setCodeProduit("CNCSnr");
			op.setDateOp(date);
			op.setLibOp("Subvention CNCSnr KIT Technopole");
			op.setTypeOp("SS");
			// op.setIdOperation(idOp);
			if (idUser != null) {
				EuUtilisateur user = new EuUtilisateur();
				user.setIdUtilisateur(idUser);
				op.setEuUtilisateur(user);
			}
			op.setHeureOp(date);
			opService.add(op);

			/*
			 * Long idappel = offreService.getLastinsertedId(); if (idappel == null) {
			 * idappel = 1L; } else { idappel++; }
			 */
			if (Objects.isNull(offre)) {
				offre = new EuAppelOffre();
				// offre.setIdAppelOffre(idappel);
				offre.setDateCreation(date);
				offre.setCodeMembreMorale(codeMembreBenef);
				offre.setDescripAppelOffre(description);
				offre.setDureeProjet(duree);
				offre.setNomAppelOffre(nomAppelOffre);
				offre.setIdDemande(null);
				offre.setIdUtilisateur(idUser);
				offre.setMembreMoraleExecutante(codeMembre);
				offre.setNumeroOffre(numAppelOffre);
				offre.setTypeAppelOffre("kit");
				offre.setTypeSmcipn("smcpn");
				offre.setPublier(1);
				offreService.add(offre);
			}

			EuCompte compteti = compteService.getById(codeCompteTi);
			if (Objects.nonNull(compteti)) {
				compteti.setSolde(compteti.getSolde() + montant);
				compteService.update(compteti);
			} else {
				compteti = new EuCompte();
				compteti.setSolde(montant);
				compteti.setCodeCompte(codeCompteTi);
				compteti.setDateAlloc(date);
				if (codeMembreBenef.endsWith("M")) {
					compteti.setEuMembreMorale(mbenef);
				} else {
					compteti.setEuMembre(benef);
				}
				compteti.setDesactiver("N");
				compteti.setLibCompte("TPN");
				EuTypeCompte typecompte = new EuTypeCompte();
				typecompte.setCodeTypeCompte("NR");
				compteti.setEuTypeCompte(typecompte);
				EuCategorieCompte catcompte = new EuCategorieCompte();
				catcompte.setCodeCat("TPN");
				catcompte.setCodeTypeCompte("NR");
				compteti.setEuCategorieCompte(catcompte);
				compteService.create(compteti);
			}

			EuSmcipnpwi smcipn = smcipnService.findByNumeroAppel(numAppelOffre);
			if (Objects.isNull(smcipn)) {
				String codeSmcipn = smcipnService.findLastByCodeMembreAndTypeSmcipn(codeMembre, "SMCIPNWI");
				String newCodeSmcipn = getCodeSmcipn(codeSmcipn, codeMembre);
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
				smcipn.setMontSalaire(montant);
				smcipn.setMontInvestis(0.0);
				if (idUser != null) {
					smcipn.setIdUtilisateur(idUser);
				} else {
					smcipn.setIdUtilisateur(null);
				}
				smcipn.setNumeroAppel(numAppelOffre);
				smcipn.setTypeNr("nr");
				smcipnService.add(smcipn);
			} else {
				smcipn.setEtatAllocSalaire(1);
				smcipn.setSalaireAlloue(montant);
				smcipn.setMontSalaire(montant);
				smcipnService.update(smcipn);
			}

			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
			EuProduit produit = new EuProduit();
			produit.setCodeProduit("CNCSnr");
			EuCompteCredit cc = new EuCompteCredit();
			cc.setCodeMembre(codeMembreBenef);
			cc.setMontantPlace(montant);
			cc.setMontantCredit(montant);
			cc.setEuOperation(op);
			cc.setEuProduit(produit);
			cc.setCodeTypeCredit(null);
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
			cc.setTypeProduit("PS");
			cc.setDomicilier(0);
			cc.setNbreRenouvel(0);
			cc = ccService.create(cc);

			Double solde_smc = smcService.findSumByOrigineSmc();
			if (solde_smc >= montant) {
				List<EuSmc> smcs = smcService.findByOrigineSmc();
				creditService.updateSmcipnSmcs(smcs, date, smcipn, cc.getIdCredit(), montant);
			} else {
				EuSmc smc = new EuSmc();
				smc.setCodeCapa(null);
				smc.setCodeDomicilier(null);
				smc.setDateSmc(date);
				smc.setEntree(0);
				smc.setEuCompteCredit(cc);
				// smc.setIdSmc(idSmc++);
				smc.setMontant(montant);
				smc.setMontantSolde(0);
				smc.setOrigineSmc(1);
				smc.setTypeSmc("CNCSnr");
				smc.setSourceCredit(cc.getSource());
				smc.setSortie(montant);
				smc.setSolde(montant);
				smcService.add(smc);

				Long idUtiliser = utiliserService.findLastUtiliserInsertedId();
				if (idUtiliser != null) {
					idUtiliser++;
				} else {
					idUtiliser = 1L;
				}
				EuUtiliser utiliser = new EuUtiliser();
				utiliser.setIdUtiliser(idUtiliser);
				utiliser.setEuSmc(smc);
				utiliser.setMontantAllouer(montant);
				utiliser.setEuSmcipnpwi(smcipn);
				utiliser.setIdCredit(cc.getIdCredit());
				utiliser.setDateCreation(date);
				utiliserService.add(utiliser);
			}

			EuGcsc sc = gcscService.findBySmcipnAndBenef(smcipn.getCodeSmcipn(), codeMembreBenef);
			if (Objects.isNull(sc)) {
				sc = new EuGcsc();
				sc.setCodeDomicilier(null);
				sc.setCredit(0);
				sc.setDebit(montant);
				sc.setSolde(montant);
				sc.setCodeMembre(codeMembreBenef);
				sc.setCodeTegc(tegc.getCodeTegc());
				sc.setDateGcsc(date);
				sc.setEuSmcipnpwi(smcipn);
				gcscService.add(sc);
			} else {
				sc.setCredit(sc.getCredit() + montant);
				sc.setSolde(sc.getCredit() - sc.getDebit());
				gcscService.update(sc);
			}

			EuTransfertNr transfert = new EuTransfertNr();
			transfert.setCodeMembreBenef(codeMembreBenef);
			transfert.setCodeMembreTransfert(codeMembre);
			transfert.setDateTransfert(date);
			transfert.setMontTransfert(montant);
			transfert.setTypeNr("SMCPN");
			transfert.setIdUtilisateur(idUser);
			transfert.setLibelleTransfert(offre.getNomAppelOffre());
			transfert.setNumAppelOffre(offre.getNumeroOffre());
			transnrService.add(transfert);

			return true;
		} catch (Exception e) {
			log.error("SMCIPN Salaire : Erreur", e);
			return false;
		}
	}

	public boolean transfertResNn(String codeMembre, String codeMembreBenef, String libTransfert, String numAppelOffre,
			double montant, long idUser) throws DataAccessException, IllegalArgumentException, NullPointerException {
		String catTransfert = "CAPA";
		String codeCompteDest = "";
		String codeProduit = "";
		EuCompte compteDest = null;
		Date date = new Date();
		System.out.println("Processing Transfert NN");
		EuActeur act_tr = acteurService.findByMembreAndTypes(codeMembre,
				Arrays.asList("TECHNOPOLE", "FILIERE", "ACNEV"));
		EuActeur act_benef = acteurService.findByCodeMembre(codeMembreBenef);
		if (Objects.nonNull(act_tr) && Objects.nonNull(act_benef)) {
			if ((act_benef.getTypeActeur().equalsIgnoreCase("TECHNOPOLE")
					|| act_benef.getTypeActeur().equalsIgnoreCase("FILIERE")
					|| act_benef.getTypeActeur().equalsIgnoreCase("ACNEV"))
					&& (act_benef.getTypeActeur().equalsIgnoreCase("TECHNOPOLE")
							|| act_benef.getTypeActeur().equalsIgnoreCase("FILIERE")
							|| act_benef.getTypeActeur().equalsIgnoreCase("ACNEV"))) {
				throw new IllegalArgumentException(
						"Le transfert de BAi ne peut faire entre un compte d'administration et un compte d'animation");
			}

			System.out.println("Beneficiaire : " + codeMembreBenef);
			System.out.println("  Acteur : " + act_benef.getCodeActeur());
			String codeCompteTransfert = "NN-" + catTransfert + "-" + codeMembre;
			EuCompte compteTransfert = compteService.getById(codeCompteTransfert);
			if (Objects.nonNull(compteTransfert)) {
				if (compteTransfert.getSolde() >= montant) {
					codeCompteDest = "NN-" + catTransfert + "-" + codeMembreBenef;

					compteDest = compteService.getById(codeCompteDest);
					if (Objects.nonNull(compteDest)) {
						compteDest.setSolde(compteDest.getSolde() + montant);
						compteService.update(compteDest);
					} else {
						compteDest = new EuCompte();
						EuCategorieCompte cat = new EuCategorieCompte();
						cat.setCodeCat(catTransfert);
						EuTypeCompte typeCompte = new EuTypeCompte();
						typeCompte.setCodeTypeCompte("NN");
						compteDest.setCodeCompte(codeCompteDest);
						compteDest.setEuCategorieCompte(cat);
						compteDest.setEuTypeCompte(typeCompte);
						compteDest.setLibCompte(cat.getCodeCat());
						if (codeMembreBenef.endsWith("P")) {
							compteDest.setEuMembre(membreService.findById(codeMembreBenef));
						} else {
							compteDest.setEuMembreMorale(moraleService.findById(codeMembreBenef));
						}
						compteDest.setDesactiver("N");
						compteDest.setSolde(montant);
						compteDest.setDateAlloc(date);
						compteService.create(compteDest);
					}

					EuProduit produit = new EuProduit();
					produit.setCodeProduit(codeProduit);

					EuCapa eucapa = new EuCapa();
					eucapa.setCodeCapa("CAPA" + ServerUtil.formatDate2(date));
					eucapa.setDateCapa(date);
					eucapa.setCodeMembre(codeMembreBenef);
					if (codeMembreBenef.endsWith("P")) {
						eucapa.setCodeProduit("RPG");
					} else {
						eucapa.setCodeProduit("I");
					}
					eucapa.setTypeCapa("NN");
					eucapa.setOrigineCapa("BAi");
					eucapa.setMontantCapa(montant);
					eucapa.setMontantUtiliser(0);
					eucapa.setMontantSolde(montant);
					eucapa.setEuCompte(compteDest);
					eucapa.setEtatCapa("Actif");
					eucapa.setIdOperation(null);
					eucapa.setHeureCapa(date);
					capaService.add(eucapa);

					EuTransfertNr transfert = new EuTransfertNr();
					transfert = new EuTransfertNr();
					transfert.setCodeMembreBenef(codeMembreBenef);
					transfert.setCodeMembreTransfert(codeMembre);
					transfert.setDateTransfert(date);
					transfert.setMontTransfert(montant);
					transfert.setTypeNr("BAi");
					transfert.setActeurSource(act_tr.getTypeActeur());
					transfert.setActeurDest(act_benef.getTypeActeur());
					transfert.setLibelleTransfert(libTransfert);
					transfert.setNumAppelOffre(numAppelOffre);
					transfert.setNumDoc(numAppelOffre);
					transfert.setIdUtilisateur(idUser);
					transnrService.add(transfert);

					List<EuCapa> capas = capaService.findByMembreAndOrigine(codeMembre, "BAi");
					if (capas.size() > 0) {
						double mont_deduire = montant;
						int i = 0;
						while (mont_deduire > 0 && i < capas.size()) {
							EuCapa capa = capas.get(i);
							if (capa.getMontantSolde() >= mont_deduire) {
								capa.setMontantUtiliser(capa.getMontantUtiliser() + mont_deduire);
								capa.setMontantSolde(capa.getMontantSolde() - mont_deduire);
								capaService.update(capa);

								mont_deduire = 0;
							} else {
								mont_deduire -= capa.getMontantSolde();
								capa.setMontantUtiliser(capa.getMontantUtiliser() + capa.getMontantSolde());
								capa.setMontantSolde(0);
								capaService.update(capa);
								i++;
							}
						}
					}

					compteTransfert.setSolde(compteTransfert.getSolde() - montant);
					compteService.create(compteTransfert);

					return true;
				} else {
					throw new SoldeInsuffisantException(
							"Le solde de votre compte SMCIPN est insuffisant pour effectuer ce transfert!");
				}
			} else {
				throw new CompteNonTrouveException(
						"Le compte de Transfert SMCIPN du membre " + codeMembre + " n'est pas trouvé");
			}
		} else {
			throw new RuntimeException("Le membre qui fait le transfert n'est pas autorisé à le faire");
		}
	}

	public boolean transfertResNr(String codeMembre, String codeMembreBenef, String libTransfert, String numAppelOffre,
			String typeNr, double montant, long idUser)
			throws DataAccessException, IllegalArgumentException, NullPointerException {
		if (StringUtils.isNotBlank(numAppelOffre)) {
			System.out.println("Enter Transfert");
			String catTransfert = "";
			String codeCompteDest = "";
			String codeProduit = "";
			EuCompte compteDest = null;
			Date date = new Date();

			EuActeur act_tr = acteurService.findByMembreAndTypes(codeMembre,
					Arrays.asList("TECHNOPOLE", "FILIERE", "ACNEV"));
			EuActeur act_benef = acteurService.findByMembreAndTypes(codeMembreBenef,
					Arrays.asList("TECHNOPOLE", "FILIERE", "ACNEV"));
			if (Objects.nonNull(act_benef)) {
				if (act_tr.getTypeActeur().equalsIgnoreCase("TECHNOPOLE") && Objects.nonNull(act_benef)
						&& !act_benef.getTypeActeur().equalsIgnoreCase("FILIERE")) {
					throw new IllegalArgumentException(
							"Le technopole ne peut transférer des ressources qu'à la FILIERE!");
				}

				if (act_tr.getTypeActeur().equalsIgnoreCase("FILIERE")
						&& !act_benef.getTypeActeur().equalsIgnoreCase("TECHNOPOLE")
						&& !act_benef.getTypeActeur().equalsIgnoreCase("ACNEV")) {
					throw new IllegalArgumentException(
							"La FILIERE ne peut transférer des ressources qu'au technopole ou sur son Compte de Payement par SMCIPN!");
				}
			}

			System.out.println("Beginning Transfert");
			EuSmcipnpwi smcipn = smcipnService.findByNumeroAppel(numAppelOffre);
			if (smcipn != null) {
				System.out.println("Enter Search SMCIPN Transfert");
				if (typeNr.equals("I")) {
					codeProduit = "I" + smcipn.getTypeNr();
					catTransfert = "TI";
				} else {
					codeProduit = "CNCS" + smcipn.getTypeNr();
					catTransfert = "TPN";
				}

				String codeCompteTransfert = "NR-" + catTransfert + "-" + codeMembre;
				System.out.println("Verification du compte de transfert :" + codeCompteTransfert);
				EuCompte compteTransfert = compteService.getById(codeCompteTransfert);
				if (Objects.nonNull(compteTransfert)) {
					System.out.println("Verification du compte de transfert : Solde  du compte " + codeMembre + " = "
							+ compteTransfert.getSolde());
					if (compteTransfert.getSolde() >= montant) {
						if (Objects.nonNull(act_benef)) {
							if (act_tr.getCodeMembre().equals(act_benef.getCodeMembre())) {
								codeCompteDest = "NR-TI-" + codeMembreBenef;
							} else {
								codeCompteDest = "NR-" + catTransfert + "-" + codeMembreBenef;
							}
						} else {
							if (typeNr.equals("I")) {
								if (codeMembreBenef.endsWith("P")) {
									throw new IllegalArgumentException(
											"Seul un membre moral peut bénéficier d'un investissement");
								} else {
									codeCompteDest = "NR-TI-" + codeMembreBenef;
								}
							} else {
								if (codeMembreBenef.endsWith("P")) {
									codeCompteDest = "NR-TCNCS-" + codeMembreBenef;
								} else {
									codeCompteDest = "NR-TPN-" + codeMembreBenef;
								}
							}
						}
						System.out.println("Code Compte Benef : " + codeCompteDest);
						compteDest = compteService.getById(codeCompteDest);
						if (Objects.nonNull(compteDest)) {
							compteDest.setSolde(compteDest.getSolde() + montant);
							compteService.update(compteDest);
						} else {
							compteDest = new EuCompte();
							EuCategorieCompte cat = new EuCategorieCompte();
							cat.setCodeCat(catTransfert);
							EuTypeCompte typeCompte = new EuTypeCompte();
							typeCompte.setCodeTypeCompte("NR");
							compteDest.setCodeCompte(codeCompteDest);
							compteDest.setEuCategorieCompte(cat);
							compteDest.setEuTypeCompte(typeCompte);
							compteDest.setLibCompte(cat.getCodeCat());
							if (codeMembreBenef.endsWith("P")) {
								compteDest.setEuMembre(membreService.findById(codeMembreBenef));
							} else {
								compteDest.setEuMembreMorale(moraleService.findById(codeMembreBenef));
							}
							compteDest.setDesactiver("N");
							compteDest.setSolde(montant);
							compteDest.setDateAlloc(date);
							compteService.create(compteDest);
						}

						EuProduit produit = new EuProduit();
						produit.setCodeProduit(codeProduit);
						SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
						EuCompteCredit cc = new EuCompteCredit();
						/*
						 * Long idCredit = ccService.getLastCreditInsertedId(); if (idCredit == null) {
						 * idCredit = 0L; }
						 */
						// cc.setIdCredit(idCredit + 1);
						cc.setCodeMembre(codeMembreBenef);
						cc.setMontantPlace(montant);
						cc.setMontantCredit(montant);
						cc.setEuOperation(null);
						cc.setEuProduit(produit);
						cc.setCodeTypeCredit(null);
						cc.setSource(codeMembreBenef + formatter.format(date));
						cc.setCompteSource(smcipn.getCodeSmcipn());
						cc.setDateOctroi(date);
						cc.setDatedeb(date);
						Date datefin = ServerUtil.ajouterJours(date, 30);
						cc.setDatefin(datefin);
						cc.setRenouveller("N");
						cc.setNbreRenouvel(1);
						cc.setBnp(0);
						cc.setKrr("N");
						cc.setEuCompte(compteDest);
						cc.setDomicilier(0);
						cc.setPrk((double) 1);
						cc.setAffecter(0);
						cc.setCodeBnp(null);
						ccService.create(cc);

						EuTransfertNr transfert = new EuTransfertNr();
						transfert.setCodeMembreBenef(codeMembreBenef);
						transfert.setCodeMembreTransfert(codeMembre);
						transfert.setDateTransfert(date);
						transfert.setMontTransfert(montant);
						if (typeNr.equals("I")) {
							transfert.setTypeNr("SMCI");
						} else {
							transfert.setTypeNr("SMCPN");
						}
						transfert.setLibelleTransfert(libTransfert);
						transfert.setNumAppelOffre(numAppelOffre);
						transfert.setIdUtilisateur(idUser);
						transnrService.add(transfert);

						List<EuCompteCredit> credits = ccService.findByCodeCompte(codeCompteTransfert);
						if (credits.size() > 0) {
							double mont_deduire = montant;
							int i = 0;
							while (mont_deduire > 0 && i < credits.size()) {
								EuCompteCredit cct = credits.get(i);
								if (cct.getMontantCredit() >= mont_deduire) {
									cct.setMontantCredit(cct.getMontantCredit() - mont_deduire);
									ccService.update(cct);

									mont_deduire = 0;
								} else {
									mont_deduire -= cct.getMontantCredit();
									cct.setMontantCredit(0.0);
									ccService.update(cct);
									i++;
								}
							}
						}

						compteTransfert.setSolde(compteTransfert.getSolde() - montant);
						compteService.create(compteTransfert);

						return true;
					} else {
						throw new SoldeInsuffisantException(
								"Le solde de votre compte SMCIPN est insuffisant pour effectuer ce transfert!");
					}
				} else {
					throw new CompteNonTrouveException(
							"Le compte de Transfert SMCIPN du membre " + codeMembre + " n'est pas trouvé");
				}
			} else {
				throw new CompteNonTrouveException("La SMCIPN N° " + numAppelOffre + " n'est pas trouvé");
			}
		} else {
			throw new IllegalArgumentException("Le numéro du SMCIPN doit être fourni!");
		}
	}

	private String getCodeSmcipn(String codeSmcipn, String codeMembre) {
		String newCodeSmcipn = StringUtils.EMPTY;
		if (StringUtils.isBlank(codeSmcipn)) {
			newCodeSmcipn = "P" + codeMembre + "0001";
		} else {
			String code = codeSmcipn.substring(codeSmcipn.length() - 4);
			int numsmc = Integer.valueOf(code);
			numsmc++;
			newCodeSmcipn = "P" + codeMembre + StringUtils.leftPad(String.valueOf(numsmc), 4, "0");
		}
		if (Objects.nonNull(smcipnService.findById(newCodeSmcipn))) {
			getCodeSmcipn(newCodeSmcipn, codeMembre);
		}
		return newCodeSmcipn;
	}
}

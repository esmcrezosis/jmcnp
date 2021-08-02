package com.esmc.mcnp.infrastructure.components;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dao.repository.bc.EuCnpRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteCreditRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteGeneralRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteRepository;
import com.esmc.mcnp.dao.repository.cm.EuMembreMoraleRepository;
import com.esmc.mcnp.dao.repository.cm.EuMembreRepository;
import com.esmc.mcnp.dao.repository.common.EuOperationRepository;
import com.esmc.mcnp.dao.repository.obps.EuGcpPreleverRepository;
import com.esmc.mcnp.dao.repository.obps.EuGcpRepository;
import com.esmc.mcnp.dao.repository.obps.EuGcscRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuEchangeRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuNnRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuTpagcpRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuTraiteRepository;
import com.esmc.mcnp.dao.repository.others.EuTegcRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuAppelOffreRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuFnRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuSmcipnwiRepository;
import com.esmc.mcnp.domain.dto.echange.Echange;
import com.esmc.mcnp.domain.entity.ba.EuNn;
import com.esmc.mcnp.domain.entity.bc.EuCnp;
import com.esmc.mcnp.domain.entity.bc.EuProduit;
import com.esmc.mcnp.domain.entity.cm.EuCategorieCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;
import com.esmc.mcnp.domain.entity.cm.EuCompteGeneral;
import com.esmc.mcnp.domain.entity.cm.EuCompteGeneralPK;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.cm.EuTypeCompte;
import com.esmc.mcnp.domain.entity.obps.EuGcp;
import com.esmc.mcnp.domain.entity.obps.EuGcpPrelever;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.domain.entity.obpsd.EuEchange;
import com.esmc.mcnp.domain.entity.obpsd.EuTpagcp;
import com.esmc.mcnp.domain.entity.obpsd.EuTraite;
import com.esmc.mcnp.domain.entity.obpsd.EuTypeNn;
import com.esmc.mcnp.domain.entity.op.EuAppelOffre;
import com.esmc.mcnp.domain.entity.others.EuOperation;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.domain.entity.smcipn.EuFn;
import com.esmc.mcnp.domain.entity.smcipn.EuGcsc;
import com.esmc.mcnp.domain.entity.smcipn.EuSmcipnpwi;
import com.esmc.mcnp.commons.exception.business.CompteNonIntegreException;
import com.esmc.mcnp.commons.exception.business.CompteNonTrouveException;
import com.esmc.mcnp.commons.exception.business.SoldeInsuffisantException;
import com.google.common.collect.Lists;

@Service
@Transactional
public class EchangeServiceImpl implements EchangeService {

	private @Autowired EuCompteRepository compteRepo;
	private @Autowired EuCompteCreditRepository creditRepo;
	private @Autowired EuEchangeRepository echrepo;
	private @Autowired EuOperationRepository opRepo;
	private @Autowired EuMembreRepository membreRepo;
	private @Autowired EuMembreMoraleRepository moralRepo;
	private @Autowired EuNnRepository nnDao;
	private @Autowired EuCompteGeneralRepository cgDao;
	private @Autowired CreditComponent creditService;
	private @Autowired EuGcscRepository gcscRepo;
	private @Autowired EuCnpRepository cnpRepo;
	private @Autowired EuTegcRepository tegcRepo;
	private @Autowired EuGcpRepository gcpRepo;
	private @Autowired EuFnRepository fnRepo;
	private @Autowired EuSmcipnwiRepository smcipnRepo;
	private @Autowired EuAppelOffreRepository offreRepo;
	private @Autowired EuTpagcpRepository tpacgpRepo;
	private @Autowired EuGcpPreleverRepository gcpPrelRepo;
	private @Autowired EuTraiteRepository traiteRepo;

	SimpleDateFormat formatter = null;

	/**
	 * Echange des Numériques rouges(TI et CNCSr) en Numériques Bleus(Inr et RPGnr)
	 *
	 * @param echange
	 *            Objet du type Ecahnge comportant lesI informations sur le compte
	 *            du crédit à echanger
	 * @return String Chaîne de caractères expliquant si l'opération a été bien
	 *         effectuée
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String echangeNRNB(Echange echange) {
		EuMembre membre = null;
		EuMembreMorale moral = null;
		EuGcsc gcsc = null;
		EuAppelOffre offre = null;
		String codeProduit = StringUtils.EMPTY;
		Date date = new Date();
		String codeMembre = ServerUtil.getCodeMembre(echange.getCodeCompte());
		String codeCategorie = ServerUtil.getTypeCompte(echange.getCodeCompte());
		String codeCompte = "";
		try {
			if (echange.getMontant() > 0) {
				if (codeCategorie.equals("TI") || codeCategorie.equals("TSI")) {
					codeCompte = "NR-TI-" + codeMembre;
				} else {
					codeCompte = echange.getCodeCompte();
				}

				EuCompte compteOrigine = compteRepo.findOne(codeCompte);
				String codeCompteDest = StringUtils.EMPTY;
				if (codeCategorie.equals("TI") || codeCategorie.equals("TSI")) {
					codeCompteDest = "NB-TI-" + codeMembre;
					codeProduit = "Inr";
					EuSmcipnpwi smcipn = smcipnRepo.findByNumeroAppel(echange.getNumAppelOffre());
					if (Objects.nonNull(smcipn)) {
						// Récupération du GCsc et del'appel d'offre
						gcsc = gcscRepo.findByEuSmcipn_CodeSmcipn(smcipn.getCodeSmcipn());
						offre = offreRepo.findAppelOffresByNumero(smcipn.getNumeroAppel());
						if (gcsc == null && offre.getTypeAppelOffre().equals("inrpre")) {
							return "le TEGCSC de remboursement du SMCIPN n'est pas défini!";
						}
					}
				} else {
					codeCompteDest = "NB-TPAGCRPG-" + codeMembre;
					codeProduit = "RPGnr";
				}

				if (Objects.nonNull(compteOrigine) && StringUtils.isNotBlank(codeCompteDest)) {
					double solde = compteOrigine.getSolde();
					if (solde >= echange.getMontant()) {
						if (echange.getCodeCompte().endsWith("M")) {
							moral = moralRepo.findOne(codeMembre);
						} else {
							membre = membreRepo.findOne(codeMembre);
						}
						Double mont_credits = creditRepo.getSumCreditByEuCompte(codeCompte);
						if (mont_credits == solde) {
							Long idOp = opRepo.getLastOperationInsertedId();
							if (idOp == null) {
								idOp = 1L;
							}
							EuOperation op = new EuOperation();
							op.setCodeProduit("CNCSr");
							op.setDateOp(date);
							op.setMontantOp(echange.getMontant());
							op.setTypeOp(echange.getTypeEchange());
							op.setLibOp("Echange du CNCS en NR");
							op.setHeureOp(date);
							op.setEuUtilisateur(null);
							op.setIdOperation(idOp);
							if (echange.getCodeCompte().endsWith("M")) {
								op.setEuMembreMorale(moral);
							} else {
								op.setEuMembre(membre);
							}
							opRepo.save(op);

							// Mise à jour ou création du compte de desstination
							// du crédit échangé
							EuCompte compteDest = compteRepo.findOne(codeCompteDest);
							EuTypeCompte typeCompte = new EuTypeCompte();
							EuCategorieCompte cat = new EuCategorieCompte();
							if (Objects.nonNull(compteDest)) {
								compteDest.setSolde(compteDest.getSolde() + echange.getMontant());
								compteRepo.save(compteDest);
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
								compteDest.setSolde(echange.getMontant());
								compteDest.setEuTypeCompte(typeCompte);
								compteDest.setLibCompte("NB " + codeCategorie);
								compteDest.setCardprinteddate(null);
								compteDest.setCardprintediddate(0);
								compteDest.setMifarecard(null);
								compteDest.setNumeroCarte(null);
								compteRepo.save(compteDest);
							}

							// Création de l'échange effectué

							EuEchange ech = new EuEchange();
							if (codeProduit.equals("Inr")) {
								ech.setCatEchange("I");
							} else {
								ech.setCatEchange("CNCS");
							}
							ech.setTypeEchange(echange.getTypeEchange());
							ech.setMontantEchange(echange.getMontant());
							ech.setMontant(echange.getMontant());
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
							echrepo.save(ech);

							// Création et utilisation du NN pour l'échange

							EuTypeNn typeNn = new EuTypeNn();
							typeNn.setCodeTypeNn(codeProduit);
							EuNn eunn = new EuNn();
							eunn.setDateEmission(date);
							eunn.setEuMembreMorale(null);
							eunn.setEuTypeNn(typeNn);
							eunn.setMontantEmis(ech.getMontant());
							eunn.setMontantRemb(ech.getMontant());
							eunn.setSoldeNn(0.0);
							eunn.setTypeEmission("Echange");
							nnDao.save(eunn);

							// Mise à jour du compte général des NN émis
							EuCompteGeneralPK cgPK = new EuCompteGeneralPK();
							cgPK.setCodeCompte("FGS" + codeProduit);
							cgPK.setCodeTypeCompte("NN");
							cgPK.setService("E");
							EuCompteGeneral cg = cgDao.findOne(cgPK);
							if (cg != null) {
								cg.setSolde(cg.getSolde() + ech.getMontant());
								cgDao.save(cg);
							} else {
								cg = new EuCompteGeneral();
								cg.setId(cgPK);
								cg.setIntitule("FG Source " + codeProduit);
								cg.setSolde(ech.getMontant());
								cgDao.save(cg);
							}

							// Enregistrement du compte crédit
							formatter = new SimpleDateFormat("yyyyMMddHHmmss");
							EuProduit produit = new EuProduit();
							produit.setCodeProduit(codeProduit);

							EuCompteCredit cc = new EuCompteCredit();
							cc.setCodeMembre(codeMembre);
							cc.setMontantPlace(echange.getMontant());
							cc.setMontantCredit(echange.getMontant());
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
							cc.setPrk(5.6);
							cc.setAffecter(0);
							cc.setCodeBnp(null);
							creditRepo.save(cc);

							// Enregistrement du CNP
							EuCnp cnp = new EuCnp();
							cnp.setDateCnp(date);
							cnp.setEuCapa(null);
							cnp.setEuDomiciliation(null);
							cnp.setTypeCnp(produit.getCodeProduit());
							cnp.setSourceCredit(cc.getSource());
							cnp.setOrigineCnp("FG" + produit.getCodeProduit());
							cnp.setMontDebit(echange.getMontant());
							cnp.setMontCredit(0);
							cnp.setSoldeCnp(echange.getMontant());
							cnp.setTransfertGcp(0);
							cnp.setEuCompteCredit(cc);
							cnpRepo.save(cnp);

							// Enregistrement du FN
							EuFn fn = new EuFn();
							fn.setTypeFn("Inr");
							fn.setDateFn(date);
							fn.setEuCapa(null);
							fn.setMontant(echange.getMontant());
							fn.setSortie(0);
							fn.setEntree(0);
							fn.setSolde(0);
							fn.setOrigineFn(1);
							fn.setMtSolde(echange.getMontant());
							fnRepo.save(fn);

							// Mise à jour du SMC
							List<EuCompteCredit> credits = creditRepo.findByEuCompte_CodeCompte(codeCompte);
							creditService.updateListSmc(credits, compteDest, op, echange.getMontant());
							if ("TCNCS".equals(codeCategorie)) {
								creditService.updateListSmc(credits, compteDest, op, echange.getMontant());
							}
							// Mise à jour du FN
							if ("TI".equals(codeCategorie)) {
								creditService.updateListFn(credits, compteDest, op, echange.getMontant());
							}

							// Mise à jour du compte échangé
							compteOrigine.setSolde(compteOrigine.getSolde() - echange.getMontant());
							compteRepo.save(compteOrigine);
							return "L'operation a été effectuée avec succès!";
						} else {
							return "Compte incohérent : \nLe montant du solde est différent de la somme des comptes crédits detaillés";
						}
					} else {
						return "Solde insuffisant pour effectuer cette opération";
					}
				} else {
					return "le compte " + echange.getCodeCompte() + " n'existe pas!";
				}
			} else {
				return "Renseigner le montant à echanger!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Echec de l'opération" + e.getMessage();
		}
	}

	/**
	 * Echange des Numériques rouges(CNCSr) en Numériques Bleus(RPGnr)
	 *
	 * @param echange
	 *            Objet du type Ecahnge comportant lesI informations sur le compte
	 *            du crédit à echanger
	 * @return String Chaîne de caractères expliquant si l'opération a été bien
	 *         effectuée
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String echangeNRCNCS(Echange echange) {
		Date date = new Date();
		String codeCompte = "";
		try {
			if (echange.getMontant() > 0 && StringUtils.isNotBlank(echange.getCodeCompte())) {
				codeCompte = echange.getCodeCompte();
				String codeMembre = ServerUtil.getCodeMembre(echange.getCodeCompte());
				String codeCategorie = ServerUtil.getTypeCompte(echange.getCodeCompte());
				System.out.println("Code Categorie :" + codeCategorie + " -- Code Compte :" + codeCompte);
				if (echange.getCodeCompte().startsWith("NR-TSCNCS-")) {
					codeCompte = "NR-TCNCS-" + codeMembre;
				}
				EuCompte compteOrigine = compteRepo.findOne(codeCompte);
				String codeCompteDest = "NB-TPAGCRPG-" + codeMembre;
				String codeProduit = "RPGnr";

				if (Objects.nonNull(compteOrigine) && StringUtils.isNotBlank(codeCompteDest)) {
					Double solde = compteOrigine.getSolde();
					System.out.println("Solde :" + solde + "<-> Montant Echange :" + echange.getMontant());
					if (solde >= echange.getMontant()) {
						EuMembre membre = membreRepo.findOne(codeMembre);
						Double mont_credits = creditRepo.getSumCreditByEuCompte(codeCompte);
						if (Objects.equals(mont_credits, solde)) {
							Long idOp = opRepo.getLastOperationInsertedId();
							if (idOp == null) {
								idOp = 1L;
							}
							EuOperation op = new EuOperation();
							op.setCodeProduit("CNCSr");
							op.setDateOp(date);
							op.setMontantOp(echange.getMontant());
							op.setTypeOp(echange.getTypeEchange());
							op.setLibOp("Echange du CNCS en NB");
							op.setHeureOp(date);
							op.setEuUtilisateur(null);
							op.setIdOperation(idOp + 1);
							op.setEuMembreMorale(null);
							op.setEuMembre(membre);
							opRepo.save(op);

							// Mise à jour ou création du compte de desstination
							// du crédit échangé
							EuCompte compteDest = compteRepo.findOne(codeCompteDest);
							EuTypeCompte typeCompte = new EuTypeCompte();
							EuCategorieCompte cat = new EuCategorieCompte();
							if (Objects.nonNull(compteDest)) {
								compteDest.setSolde(compteDest.getSolde() + echange.getMontant());
								compteRepo.save(compteDest);
							} else {
								compteDest = new EuCompte();
								compteDest.setCodeCompte(codeCompteDest);
								System.out.println("Debut TSI");
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
								compteDest.setSolde(echange.getMontant());
								compteDest.setEuTypeCompte(typeCompte);
								compteDest.setLibCompte("NB " + codeCategorie);
								compteDest.setCardprinteddate(null);
								compteDest.setCardprintediddate(0);
								compteDest.setMifarecard(null);
								compteDest.setNumeroCarte(null);
								compteRepo.save(compteDest);
							}

							// Création de l'échange effectué
							Long id_echange = echrepo.findLastEchangeInsertedId();
							if (id_echange == null) {
								id_echange = 0L;
							}
							EuEchange ech = new EuEchange();
							ech.setIdEchange(id_echange + 1);
							ech.setCatEchange("CNCS");
							ech.setTypeEchange(echange.getTypeEchange());
							ech.setMontantEchange(echange.getMontant());
							ech.setMontant(echange.getMontant());
							ech.setCompenser(1);
							ech.setRegler(1);
							ech.setEuCompte(compteOrigine);
							ech.setCodeCompteObt(codeCompteDest);
							ech.setDateEchange(date);
							ech.setDateReglement(date);
							ech.setEuMembreMorale(null);
							ech.setEuMembre(compteOrigine.getEuMembre());
							ech.setCodeProduit(codeProduit);
							ech.setAgio(0);
							echrepo.save(ech);

							// Création et utilisation du NN pour l'échange
							Long id = nnDao.getLastInsertId();
							if (id == null) {
								id = 0L;
							}
							EuTypeNn typeNn = new EuTypeNn();
							typeNn.setCodeTypeNn(codeProduit);
							EuNn eunn = new EuNn();
							eunn.setDateEmission(date);
							eunn.setIdNn(id + 1);
							eunn.setEuMembreMorale(null);
							eunn.setEuTypeNn(typeNn);
							eunn.setMontantEmis(ech.getMontant());
							eunn.setMontantRemb(ech.getMontant());
							eunn.setSoldeNn(0.0);
							eunn.setTypeEmission("Echange");
							nnDao.save(eunn);

							// Mise à jour du compte général des NN émis
							System.out.println("OK Emission");
							EuCompteGeneralPK cgPK = new EuCompteGeneralPK();
							cgPK.setCodeCompte("FGS" + codeProduit);
							cgPK.setCodeTypeCompte("NN");
							cgPK.setService("E");
							EuCompteGeneral cg = cgDao.findOne(cgPK);
							if (cg != null) {
								cg.setSolde(cg.getSolde() + ech.getMontant());
								cgDao.save(cg);
							} else {
								cg = new EuCompteGeneral();
								cg.setId(cgPK);
								cg.setIntitule("FG Source " + codeProduit);
								cg.setSolde(ech.getMontant());
								cgDao.save(cg);
							}

							// Enregistrement du compte crédit
							formatter = new SimpleDateFormat("yyyyMMddHHmmss");
							EuProduit produit = new EuProduit();
							produit.setCodeProduit(codeProduit);

							EuCompteCredit cc = new EuCompteCredit();
							Long idCredit = creditRepo.getLastCreditInsertedId() + 1;
							cc.setIdCredit(idCredit);
							cc.setCodeMembre(codeMembre);
							cc.setMontantPlace(echange.getMontant());
							cc.setMontantCredit(echange.getMontant());
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
							cc.setPrk(5.6);
							cc.setAffecter(0);
							cc.setCodeBnp(null);
							creditRepo.save(cc);

							// Enregistrement du CNP
							System.out.println("Jusqu'ici tout va bien 0");
							long idCnp = cnpRepo.getLastCnpInsertedId() + 1;
							EuCnp cnp = new EuCnp();
							cnp.setDateCnp(date);
							cnp.setEuCapa(null);
							cnp.setEuDomiciliation(null);
							cnp.setTypeCnp(produit.getCodeProduit());
							cnp.setSourceCredit(cc.getSource());
							cnp.setOrigineCnp("FG" + produit.getCodeProduit());
							cnp.setMontDebit(echange.getMontant());
							cnp.setMontCredit(0);
							cnp.setSoldeCnp(echange.getMontant());
							cnp.setTransfertGcp(0);
							cnp.setEuCompteCredit(cc);
							cnp.setIdCnp(idCnp);
							cnpRepo.save(cnp);

							// Enregistrement du FN
							long idFn = fnRepo.getLastFnInsertedId() + 1;
							EuFn fn = new EuFn();
							fn.setIdFn(idFn);
							fn.setTypeFn("Inr");
							fn.setDateFn(date);
							fn.setEuCapa(null);
							fn.setMontant(echange.getMontant());
							fn.setSortie(0);
							fn.setEntree(0);
							fn.setSolde(0);
							fn.setOrigineFn(1);
							fn.setMtSolde(echange.getMontant());
							fnRepo.save(fn);

							// Mise à jour du SMC
							List<EuCompteCredit> credits = creditRepo.findByEuCompte_CodeCompte(codeCompte);
							creditService.updateListSmc(credits, compteDest, op, echange.getMontant());
							if ("TCNCS".equals(codeCategorie)) {
								System.out.println("Mise à jour des SMCs");
								creditService.updateListSmc(credits, compteDest, op, echange.getMontant());
							}
							// Mise à jour du FN
							if ("TI".equals(codeCategorie)) {
								System.out.println("Mise à jour des Fns");
								creditService.updateListFn(credits, compteDest, op, echange.getMontant());
							}

							// Mise à jour du compte échangé
							compteOrigine.setSolde(compteOrigine.getSolde() - echange.getMontant());
							compteRepo.save(compteOrigine);
							return "L'operation a été effectuée avec succès!";
						} else {
							return "Compte incohérent : \nLe montant du solde est différent de la somme des comptes crédits detaillés";
						}
					} else {
						return "Solde insuffisant pour effectuer cette opération";
					}
				} else {
					return "le compte " + echange.getCodeCompte() + " n'existe pas!";
				}
			} else {
				return "Renseigner le montant à echanger!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Echec de l'opération" + e.getMessage();
		}
	}

	/**
	 * Echange des GCps en Numériques Bleus(Inr et RPGnr)
	 *
	 * @param codeCompte
	 *            Le code du compte crédit à échangé
	 * @param typeEchange
	 *            Le type d'échange à effectué
	 * @param creditObt
	 *            le type de crédit à obtenir
	 * @param codeCompteRPG
	 *            le compte du compte crédit RPG à obtenir si c'est une personne
	 *            physique qui est le bénéficiaire
	 * @param montant
	 *            Le montant de l'opération
	 * @return String Chaîne de caractères expliquant si l'opération a été bien
	 *         effectuée
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean echangeGCP(String codeMembre, String typeEchange, double montant, String creditObt,
			String codeMembreRpg) {
		Date date = new Date();
		if (StringUtils.isNotBlank(codeMembre)) {
			String codecat = "";
			String codeCompteObt = "";
			String codeProduit = "";
			String codeCompteOrigine = "NB-TPAGCP-" + codeMembre;
			EuCompte compteGcp = compteRepo.findOne(codeCompteOrigine);
			if (compteGcp != null && compteGcp.getSolde() >= montant) {
				if (!creditObt.isEmpty()) {
					codeCompteObt = "NB-TPAGCI-" + codeMembre;
					codeProduit = "Inr";
					codecat = "TPAGCI";
				} else if (creditObt.equals("Inr")) {
					codeCompteObt = "NB-TPAGCI-" + codeMembre;
					codeProduit = "Inr";
					codecat = "TPAGCI";
				} else {
					if (StringUtils.isNotBlank(codeMembreRpg)) {
						codeCompteObt = "NB-TPAGCRPG-" + codeMembreRpg;
						codeProduit = "RPGnr";
						codecat = "TPAGCRPG";
					} else {
						return false;
					}
				}
				String codeTegc = "TEGCP" + compteGcp.getEuMembreMorale().getIdFiliere() + codeMembre;
				System.out.println(codeTegc);
				EuTegc tegc = tegcRepo.findOne(codeTegc);
				if (!Objects.nonNull(tegc)) {
					return false;
				}
				try {
					Long idOp = opRepo.getLastOperationInsertedId();
					if (idOp == null) {
						idOp = 0L;
					}

					// Enregistrement de l'opération
					EuCategorieCompte categorie = new EuCategorieCompte();
					categorie.setCodeCat(codecat);

					EuOperation op = new EuOperation();
					op.setEuCategorieCompte(categorie);
					op.setCodeProduit("GCP");
					op.setDateOp(date);
					op.setLibOp("Echange de GCP");
					op.setTypeOp("E");
					op.setMontantOp(montant);
					op.setEuMembreMorale(compteGcp.getEuMembreMorale());
					op.setIdOperation(idOp + 1);
					op.setEuUtilisateur(null);
					op.setHeureOp(date);
					opRepo.save(op);

					// Mise à jour ou Création du compte crédit obtenu
					EuTypeCompte typeCompte = new EuTypeCompte();
					EuCategorieCompte cat = new EuCategorieCompte();
					EuCompte compteObt = compteRepo.findOne(codeCompteObt);
					if (compteObt != null) {
						compteObt.setSolde(compteObt.getSolde() + montant);
						compteRepo.save(compteObt);
					} else {
						cat.setCodeCat(codecat);
						typeCompte.setCodeTypeCompte("NB");

						compteObt = new EuCompte();
						compteObt.setCodeCompte(codeCompteObt);
						System.out.println("Debut TSCI");
						compteObt.setDateAlloc(date);
						compteObt.setDesactiver("N");
						compteObt.setEuCategorieCompte(cat);
						if (codeMembre.endsWith("M")) {
							compteObt.setEuMembreMorale(compteGcp.getEuMembreMorale());
							compteObt.setEuMembre(null);
						} else {
							EuMembre membre = membreRepo.findOne(codeMembreRpg);
							compteObt.setEuMembre(membre);
							compteObt.setEuMembreMorale(null);
						}
						compteObt.setSolde(montant);
						compteObt.setEuTypeCompte(typeCompte);
						compteObt.setLibCompte("NB " + codecat);
						compteObt.setCardprinteddate(null);
						compteObt.setCardprintediddate(0);
						compteObt.setMifarecard(null);
						compteObt.setNumeroCarte(null);
						compteRepo.save(compteObt);
					}

					// Enregistrement de l'echange proprement dit
					Long id_echange = echrepo.findLastEchangeInsertedId();
					if (id_echange == null) {
						id_echange = 0L;
					}
					EuEchange ech = new EuEchange();
					ech.setIdEchange(id_echange + 1);
					ech.setCatEchange("GCP");
					ech.setTypeEchange("NB/NB");
					ech.setMontantEchange(montant);
					ech.setMontant(montant);
					ech.setCompenser(0);
					ech.setRegler(0);
					ech.setEuCompte(compteObt);
					ech.setCodeCompteObt(codeCompteObt);
					ech.setDateEchange(date);
					ech.setDateReglement(date);
					ech.setEuMembreMorale(compteGcp.getEuMembreMorale());
					ech.setEuMembre(null);
					ech.setCodeProduit(codeProduit);
					ech.setAgio(0);
					echrepo.save(ech);

					// Céation du crédit concerné par l'échange
					formatter = new SimpleDateFormat("yyyyMMddHHmmss");
					EuProduit produit = new EuProduit();
					produit.setCodeProduit(codeProduit);
					EuCompteCredit cc = new EuCompteCredit();
					long idCredit = creditRepo.getLastCreditInsertedId() + 1;
					cc.setIdCredit(idCredit);
					cc.setCodeMembre(codeMembre);
					cc.setMontantPlace(montant);
					cc.setMontantCredit(montant);
					cc.setEuOperation(op);
					cc.setEuProduit(produit);
					if (codeProduit.equals("RPGnr")) {
						cc.setCodeTypeCredit("CNPG");
					} else {
						cc.setCodeTypeCredit("REAPPRO");
					}
					cc.setSource(codeMembre + formatter.format(date));
					cc.setCompteSource(compteGcp.getCodeCompte());
					cc.setDateOctroi(date);
					cc.setDatedeb(date);
					Date datefin = ServerUtil.ajouterJours(date, 30);
					cc.setDatefin(datefin);
					cc.setRenouveller("N");
					cc.setNbreRenouvel(-1);
					cc.setBnp(0);
					cc.setKrr("N");
					cc.setEuCompte(compteObt);
					cc.setDomicilier(0);
					cc.setPrk(0.0);
					cc.setAffecter(0);
					cc.setCodeBnp(null);
					creditRepo.save(cc);

					// Création du CNP correspondant au crédit obtenu
					System.out.println("Jusqu'ici tout va bien 0");
					long idCnp = cnpRepo.getLastCnpInsertedId() + 1;
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
					cnp.setIdCnp(idCnp);
					cnpRepo.save(cnp);

					// Création du FN correspondant au crédit échangé
					Long idFn = fnRepo.getLastFnInsertedId();
					if (idFn == null) {
						idFn = 1L;
					} else {
						idFn += 1;
					}
					EuFn fn = new EuFn();
					fn.setIdFn(idFn);
					fn.setTypeFn("Inr");
					fn.setDateFn(date);
					fn.setEuCapa(null);
					fn.setMontant(montant);
					fn.setSortie(0);
					fn.setEntree(0);
					fn.setSolde(0);
					fn.setMtSolde(montant);
					fn.setOrigineFn(1);
					fnRepo.save(fn);

					// Mise à jour du Compte échangé
					compteGcp.setSolde(compteGcp.getSolde() - montant);
					compteRepo.save(compteGcp);

					// Mise à jour du TEGC du distributeur qui échange
					tegc.setMontantUtilise(tegc.getMontantUtilise() + montant);
					tegc.setSoldeTegc(tegc.getSoldeTegc() - montant);
					tegcRepo.save(tegc);

					System.out.print("Récupération des CNPs");
					// Mise à jour des prelevements des GCp
					List<EuGcp> gcps = gcpRepo.findGcpByCodeMembre(codeMembre);
					if (gcps.size() > 0) {
						System.out.print("Mise à jour des CNPs");
						double mont_gcp = montant;
						int i = 0;
						while (mont_gcp > 0 && i < gcps.size()) {
							EuGcp gcp = gcps.get(i);
							if (gcp.getReste() >= mont_gcp) {
								gcp.setReste(gcp.getReste() - mont_gcp);
								gcp.setMontPreleve(gcp.getMontPreleve() + mont_gcp);
								gcpRepo.save(gcp);

								Long idPrel = gcpPrelRepo.getLastInsertId();
								if (idPrel == null) {
									idPrel = 1L;
								} else {
									idPrel += 1;
								}
								EuGcpPrelever gcpPrel = new EuGcpPrelever();
								gcpPrel.setCodeMembre(gcp.getCodeMembre());
								gcpPrel.setDatePrelevement(date);
								gcpPrel.setEuGcp(gcp);
								gcpPrel.setEuTegc(tegc);
								gcpPrel.setIdCredit(gcp.getEuCompteCredit().getIdCredit());
								gcpPrel.setIdOperation(idOp);
								gcpPrel.setSourceCredit(gcp.getEuCompteCredit().getSource());
								gcpPrel.setMontPrelever(mont_gcp);
								gcpPrel.setMontRapprocher(mont_gcp);
								gcpPrel.setSoldePrelevement(0);
								gcpPrel.setHeurePrelevement(date);
								gcpPrel.setIdTpagcp(null);
								gcpPrel.setRapprocher(1);
								gcpPrel.setIdPrelevement(idPrel);
								gcpPrelRepo.save(gcpPrel);

								EuCnp cnpp = cnpRepo.findByEuGcp_IdGcp(gcp.getIdGcp());
								cnpp.setMontCredit(mont_gcp);
								cnpp.setSoldeCnp(cnpp.getMontDebit() - cnpp.getMontCredit());
								cnpRepo.save(cnpp);

								mont_gcp = 0;
								System.out.println("Cas 1");
							} else {
								mont_gcp -= gcp.getReste();

								Long idPrel = gcpPrelRepo.getLastInsertId();
								if (idPrel == null) {
									idPrel = 1L;
								} else {
									idPrel += 1;
								}
								EuGcpPrelever gcpPrel = new EuGcpPrelever();
								gcpPrel.setCodeMembre(gcp.getCodeMembre());
								gcpPrel.setDatePrelevement(date);
								gcpPrel.setEuGcp(gcp);
								gcpPrel.setEuTegc(tegc);
								gcpPrel.setIdCredit(gcp.getEuCompteCredit().getIdCredit());
								gcpPrel.setIdOperation(idOp);
								gcpPrel.setSourceCredit(gcp.getEuCompteCredit().getSource());
								gcpPrel.setMontPrelever(gcp.getReste());
								gcpPrel.setMontRapprocher(gcp.getReste());
								gcpPrel.setSoldePrelevement(0);
								gcpPrel.setHeurePrelevement(date);
								gcpPrel.setIdTpagcp(null);
								gcpPrel.setRapprocher(1);
								gcpPrel.setIdPrelevement(idPrel);
								gcpPrelRepo.save(gcpPrel);

								EuCnp cnpp = cnpRepo.findByEuGcp_IdGcp(gcp.getIdGcp());
								if (Objects.nonNull(cnpp)) {
									cnpp.setMontCredit(gcp.getReste());
									cnpp.setSoldeCnp(cnpp.getMontDebit() - cnpp.getMontCredit());
									cnpRepo.save(cnpp);
								}

								gcp.setMontPreleve(gcp.getMontPreleve() + gcp.getReste());
								gcp.setReste(0);
								gcpRepo.save(gcp);
								i++;
								System.out.println("Cas 2");
							}
						}
					} else {
						return false;
					}
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}

			} else {
				System.out.println("Solde insuffisant");
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Echange des Numériques rouges(TI) en Numériques Noirs(Inr)
	 *
	 * @param echange
	 *            Objet du type Echange comportant lesinfos de l'échange à ffectuer
	 * @return String Chaîne de caractères expliquant si l'opération a été bien
	 *         effectuée
	 */
	@Override
	public String echangeNRNN(Echange echange) {
		if (echange.getTypeEchange().equals("NR/NN")) {
			String codecat = ServerUtil.getTypeCompte(echange.getCodeCompte());
			if (!codecat.equals("TSI") && !codecat.equals("TSGCI")) {
				return "Cet echange se fait les compte TI et TSGCI!";
			}
			if (codecat.equals("TSI")) {
				codecat = "TI";
			}

			// Récupération du SMCIPN correspondant au numéro d'appel d'offre
			EuSmcipnpwi smcipn = smcipnRepo.findByNumeroAppel(echange.getNumAppelOffre());
			if (smcipn != null) {
				// Récupération du GCsc et del'appel d'offre
				EuGcsc gcsc = gcscRepo.findByEuSmcipn_CodeSmcipn(smcipn.getCodeSmcipn());
				EuAppelOffre offre = offreRepo.findAppelOffresByNumero(smcipn.getNumeroAppel());
				if (gcsc == null && offre.getTypeAppelOffre().equals("inrpre")) {
					return "le TEGCSC de remboursement du SMCIPN n'est pas défini!";
				}
				String codeMembre = ServerUtil.getCodeMembre(echange.getCodeCompte());
				String codeCompteOrig = "NR-" + codecat + "-" + codeMembre;
				EuCompte compteOrigine = compteRepo.findOne(codeCompteOrig);
				Date date = new Date();
				if (compteOrigine != null && compteOrigine.getSolde() >= echange.getMontant()) {
					String codeCompteDest = "NN-" + codecat + "-" + codeMembre;
					List<EuCompteCredit> credit_s = creditRepo.findByEuCompte_CodeCompte(compteOrigine.getCodeCompte());
					if (credit_s.isEmpty()) {
						return "Il n'y a pas de crédits correspondant à ce compte";
					} else {
						double somme_credit = 0;
						somme_credit = credit_s.stream().mapToDouble((c) -> c.getMontantCredit()).sum();
						System.out.println("Somme crédit = " + somme_credit);
						if (compteOrigine.getSolde() != somme_credit) {
							return "Votre compte n'est pas intègre!!!";
						}
					}
					try {
						List<EuCompteCredit> credits = creditRepo.findByCompteandSource(compteOrigine.getCodeCompte(),
								smcipn.getCodeSmcipn());
						if (credits.isEmpty()) {
							return "Il n'y a pas de crédits correspondant à ce compte";
						} else {
							double somme_credit = 0;
							somme_credit = credits.stream().map((c) -> c.getMontantCredit()).reduce(somme_credit,
									(accumulator, _item) -> accumulator + _item);
							if (echange.getMontant() > somme_credit) {
								return "Votre compte est insuffisant!!!";
							}
						}

						// Création ou Mise à jour du compte du crédit à obtenir
						EuCompte compteDest = compteRepo.findOne(codeCompteDest);
						EuTypeCompte typeCompte = new EuTypeCompte();
						EuCategorieCompte cat = new EuCategorieCompte();
						if (compteDest != null) {
							compteDest.setSolde(compteDest.getSolde() + echange.getMontant());
							compteRepo.save(compteDest);
						} else {

							cat.setCodeCat(codecat);
							typeCompte.setCodeTypeCompte("NN");

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
							compteDest.setSolde(echange.getMontant());
							compteDest.setEuTypeCompte(typeCompte);
							compteDest.setLibCompte("NN " + codecat);
							compteDest.setCardprinteddate(null);
							compteDest.setCardprintediddate(0);
							compteDest.setMifarecard(null);
							compteDest.setNumeroCarte(null);
							compteRepo.save(compteDest);
						}

						// Création du NN à utiliser à la source NN
						Long id = nnDao.getLastInsertId();
						if (id == null) {
							id = 0L;
						}
						EuTypeNn typeNn = new EuTypeNn();
						typeNn.setCodeTypeNn(echange.getCreditObtenir() + "NR");
						EuNn eunn = new EuNn();
						eunn.setDateEmission(date);
						eunn.setIdNn(id + 1);
						eunn.setEuMembreMorale(null);
						eunn.setEuTypeNn(typeNn);
						eunn.setMontantEmis(echange.getMontant());
						eunn.setMontantRemb(echange.getMontant());
						eunn.setSoldeNn(0.0);
						eunn.setTypeEmission("Manuelle");
						nnDao.save(eunn);

						// Mise à jour du compte général NN
						System.out.println("OK Emission");
						EuCompteGeneralPK cgPK = new EuCompteGeneralPK();
						cgPK.setCodeCompte("FGS" + echange.getCreditObtenir());
						cgPK.setCodeTypeCompte("NN");
						cgPK.setService("E");
						EuCompteGeneral cg = cgDao.findOne(cgPK);
						if (cg != null) {
							cg.setSolde(cg.getSolde() + echange.getMontant());
							cgDao.save(cg);
						} else {
							cg = new EuCompteGeneral();
							cg.setId(cgPK);
							cg.setIntitule("FG Source " + echange.getCreditObtenir());
							cg.setSolde(echange.getMontant());
							cgDao.save(cg);
						}

						// Création de l'opération d'échange
						Long id_echange = echrepo.findLastEchangeInsertedId();
						if (id_echange == null) {
							id_echange = 0L;
						}
						EuEchange ech = new EuEchange();
						ech.setIdEchange(id_echange + 1);
						ech.setCatEchange(echange.getCodeProduit());
						ech.setTypeEchange(echange.getTypeEchange());
						ech.setMontantEchange(echange.getMontant());
						ech.setMontant(echange.getMontant());
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
						ech.setCodeProduit(echange.getCodeProduit());
						ech.setAgio(0);
						echrepo.save(ech);

						Long idOp = opRepo.getLastOperationInsertedId();
						if (idOp == null) {
							idOp = 0L;
						}

						EuCategorieCompte categorie = new EuCategorieCompte();
						categorie.setCodeCat(codecat);

						EuOperation op = new EuOperation();
						op.setEuCategorieCompte(categorie);
						op.setCodeProduit(echange.getCodeProduit());
						op.setDateOp(date);
						op.setLibOp("Echange de " + codecat);
						op.setTypeOp("E");
						System.out.println("Valeur Echange :" + echange.getMontant());
						op.setMontantOp(echange.getMontant());
						if (codeMembre.endsWith("P")) {
							op.setEuMembre(compteOrigine.getEuMembre());
						} else {
							op.setEuMembreMorale(compteOrigine.getEuMembreMorale());
						}
						op.setIdOperation(idOp + 1);
						EuUtilisateur user = new EuUtilisateur();
						if (echange.getIdUtilisateur() != null) {
							user.setIdUtilisateur(echange.getIdUtilisateur());
							op.setEuUtilisateur(user);
						}
						op.setHeureOp(date);
						opRepo.save(op);

						// Mise à jour des GCsc des crédits échangés
						double mont_credit = echange.getMontant();
						int j = 0;
						while (mont_credit > 0) {
							EuCompteCredit credit = credits.get(j);
							System.out.println("");
							if (credit.getMontantCredit() < mont_credit) {
								mont_credit -= credit.getMontantCredit();
								if (gcsc != null) {
									gcsc.setDebit(gcsc.getDebit() + credit.getMontantCredit());
									gcsc.setSolde(gcsc.getCredit() - gcsc.getDebit());
									gcscRepo.save(gcsc);
								}
								credit.setMontantCredit(0.0);
								creditRepo.save(credit);
								j++;
							} else {
								if (gcsc != null) {
									gcsc.setDebit(gcsc.getDebit() + mont_credit);
									gcsc.setSolde(gcsc.getCredit() - gcsc.getDebit());
									gcscRepo.save(gcsc);
								}
								credit.setMontantCredit(credit.getMontantCredit() - mont_credit);
								creditRepo.save(credit);
								mont_credit = 0;
							}
						}

						// Mise à jour des FNs
						creditService.updateListFn(credits, compteDest, op, echange.getMontant());
						// Mise à jour du compte du crédit échangé
						compteOrigine.setSolde(compteOrigine.getSolde() - echange.getMontant());
						compteRepo.save(compteOrigine);
						return "L'opération a été effectuée avec succès";
					} catch (Exception e) {
						return "Erreur d'exécution : " + e.getMessage();
					}
				} else {
					return "Echec de l'opération: \n Le solde de votre compte est insuffisant pour effectuer cette opération!";
				}
			} else {
				return "Echec de l'opération : Il n'y a pas de SMCIPN associé à cet appel d'offre!";
			}
		} else {
			return "Echec de l'opération!";
		}
	}

	@Override
	public boolean echangeGCP(String codeMembre, String codetegc, String typeEchange, double prk, double montant,
			String typeProduit) throws SoldeInsuffisantException, NullPointerException, CompteNonIntegreException {
		Date date = new Date();
		if (StringUtils.isNotBlank(codeMembre)) {
			String codeCompteOrigine = "NB-TPAGCP-" + codeMembre;
			EuCompte compteGcp = compteRepo.findOne(codeCompteOrigine);
			if (compteGcp != null && compteGcp.getSolde() >= montant) {
				String codeCompteObt = "NB-TPAGCI-" + codeMembre;
				String codeProduit = "Inr";
				String codecat = "TPAGCI";
				if (codeMembre.endsWith("P")) {
					codeCompteObt = "NB-TPAGCRPG-" + codeMembre;
					codeProduit = "RPGnr";
					codecat = "TPAGCRPG";
				}
				List<EuTegc> tegcs = Lists.newArrayList();
				Double soldeTegc = 0.0;
				if (codeMembre.endsWith("M")) {
					if (StringUtils.isNotBlank(codetegc)) {
						tegcs = tegcRepo.fetchByMembreAndTe(codeMembre, codetegc);
						soldeTegc = tegcRepo.getSoldeByMembreandTe(codeMembre, codetegc);
					} else {
						tegcs = tegcRepo.findByCodeMembre(codeMembre);
						soldeTegc = tegcRepo.getSoldeByCodeMembre(codeMembre);
					}
				} else {
					if (StringUtils.isNotBlank(codetegc)) {
						tegcs = tegcRepo.fetchByMembrePysiqueAndTe(codeMembre, codetegc);
						soldeTegc = tegcRepo.getSoldeByMembrePhysiqueandTe(codeMembre, codetegc);
					} else {
						tegcs = tegcRepo.findByCodeMembrePhysique(codeMembre);
						soldeTegc = tegcRepo.getSoldeByCodeMembrePhysique(codeMembre);
					}
				}

				if (soldeTegc >= montant) {
					/*
					 * Long idOp = opRepo.getLastOperationInsertedId(); if (idOp == null) { idOp =
					 * 0L; }
					 */

					// Enregistrement de l'opération
					EuCategorieCompte categorie = new EuCategorieCompte();
					categorie.setCodeCat(codecat);

					EuOperation op = new EuOperation();
					op.setEuCategorieCompte(categorie);
					op.setCodeProduit("GCP");
					op.setDateOp(date);
					op.setLibOp("Echange de GCP");
					op.setTypeOp("E");
					op.setMontantOp(montant);
					op.setEuMembreMorale(compteGcp.getEuMembreMorale());
					// op.setIdOperation(idOp + 1);
					op.setEuUtilisateur(null);
					op.setHeureOp(date);
					op = opRepo.save(op);

					// Mise à jour ou Création du compte crédit obtenu
					EuTypeCompte typeCompte = new EuTypeCompte();
					EuCategorieCompte cat = new EuCategorieCompte();
					EuCompte compteObt = compteRepo.findOne(codeCompteObt);
					if (compteObt != null) {
						compteObt.setSolde(compteObt.getSolde() + montant);
						compteRepo.save(compteObt);
					} else {
						cat.setCodeCat(codecat);
						typeCompte.setCodeTypeCompte("NB");

						compteObt = new EuCompte();
						compteObt.setCodeCompte(codeCompteObt);
						compteObt.setDateAlloc(date);
						compteObt.setDesactiver("N");
						compteObt.setEuCategorieCompte(cat);
						compteObt.setEuMembreMorale(compteGcp.getEuMembreMorale());
						compteObt.setEuMembre(null);
						compteObt.setSolde(montant);
						compteObt.setEuTypeCompte(typeCompte);
						compteObt.setLibCompte("NB " + codecat);
						compteObt.setCardprinteddate(null);
						compteObt.setCardprintediddate(0);
						compteObt.setMifarecard(null);
						compteObt.setNumeroCarte(null);
						compteRepo.save(compteObt);
					}

					// Enregistrement de l'echange proprement dit
					/*
					 * Long id_echange = echrepo.findLastEchangeInsertedId(); if (id_echange ==
					 * null) { id_echange = 0L; }
					 */
					EuEchange ech = new EuEchange();
					// ech.setIdEchange(id_echange + 1);
					ech.setCatEchange("GCP");
					ech.setTypeEchange("NB/NB");
					ech.setMontantEchange(montant);
					ech.setMontant(montant);
					ech.setCompenser(0);
					ech.setRegler(0);
					ech.setEuCompte(compteObt);
					ech.setCodeCompteObt(codeCompteObt);
					ech.setDateEchange(date);
					ech.setDateReglement(date);
					if (codeMembre.endsWith("M")) {
						ech.setEuMembreMorale(compteGcp.getEuMembreMorale());
						ech.setEuMembre(null);
					} else {
						ech.setEuMembreMorale(null);
						ech.setEuMembre(compteGcp.getEuMembre());
					}
					ech.setCodeProduit(codeProduit);
					ech.setAgio(0);
					echrepo.save(ech);

					// Céation du crédit concerné par l'échange
					formatter = new SimpleDateFormat("yyyyMMddHHmmss");
					EuProduit produit = new EuProduit();
					produit.setCodeProduit(codeProduit);
					EuCompteCredit cc = new EuCompteCredit();
					// long idCredit = creditRepo.getLastCreditInsertedId() + 1;
					// cc.setIdCredit(idCredit);
					cc.setCodeMembre(codeMembre);
					cc.setMontantPlace(montant);
					cc.setMontantCredit(montant);
					cc.setEuOperation(op);
					cc.setEuProduit(produit);
					if (codeProduit.equals("RPGnr")) {
						cc.setCodeTypeCredit("CNPG");
					} else {
						cc.setCodeTypeCredit("REAPPRO");
					}
					cc.setSource(codeMembre + formatter.format(date));
					cc.setCompteSource(compteGcp.getCodeCompte());
					cc.setDateOctroi(date);
					cc.setDatedeb(date);
					Date datefin = ServerUtil.ajouterJours(date, 30);
					cc.setDatefin(datefin);
					cc.setRenouveller("N");
					cc.setNbreRenouvel(Long.valueOf(Math.round(prk)).intValue());
					cc.setBnp(0);
					cc.setKrr("N");
					cc.setEuCompte(compteObt);
					cc.setDomicilier(0);
					cc.setTypeProduit(typeProduit);
					cc.setPrk(prk);
					cc.setAffecter(0);
					cc.setCodeBnp(null);
					creditRepo.save(cc);

					// Création du CNP correspondant au crédit obtenu
					// long idCnp = cnpRepo.getLastCnpInsertedId() + 1;
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
					cnpRepo.save(cnp);

					/*
					 * // Création du FN correspondant au crédit échangé Long idFn =
					 * fnRepo.getLastFnInsertedId(); if (idFn == null) { idFn = 1L; } else { idFn +=
					 * 1; }
					 */
					EuFn fn = new EuFn();
					// fn.setIdFn(idFn);
					fn.setTypeFn("Inr");
					fn.setDateFn(date);
					fn.setEuCapa(null);
					fn.setMontant(montant);
					fn.setSortie(0);
					fn.setEntree(0);
					fn.setSolde(0);
					fn.setMtSolde(montant);
					fn.setOrigineFn(1);
					fnRepo.save(fn);

					// Mise à jour du Compte échangé
					compteGcp.setSolde(compteGcp.getSolde() - montant);
					compteRepo.save(compteGcp);

					double mont_gcp = montant;
					for (EuTegc tegc : tegcs) {
						System.out.println("Récupération des CNPs");
						// Mise à jour des prelevements des GCp
						List<EuGcp> gcps = gcpRepo.findByTe(tegc.getCodeTegc());
						System.out.println("Mise à jour des CNPs");
						int i = 0;
						while (mont_gcp > 0 && i < gcps.size()) {
							EuGcp gcp = gcps.get(i);
							if (gcp.getReste() >= mont_gcp) {
								gcp.setReste(gcp.getReste() - mont_gcp);
								gcp.setMontPreleve(gcp.getMontPreleve() + mont_gcp);
								gcpRepo.save(gcp);

								Long idPrel = gcpPrelRepo.getLastInsertId();
								if (idPrel == null) {
									idPrel = 1L;
								} else {
									idPrel += 1;
								}
								EuGcpPrelever gcpPrel = new EuGcpPrelever();
								gcpPrel.setCodeMembre(gcp.getCodeMembre());
								gcpPrel.setDatePrelevement(date);
								gcpPrel.setEuGcp(gcp);
								gcpPrel.setEuTegc(tegc);
								gcpPrel.setIdCredit(gcp.getEuCompteCredit().getIdCredit());
								gcpPrel.setIdOperation(op.getIdOperation());
								gcpPrel.setSourceCredit(gcp.getEuCompteCredit().getSource());
								gcpPrel.setMontPrelever(mont_gcp);
								gcpPrel.setMontRapprocher(mont_gcp);
								gcpPrel.setSoldePrelevement(0);
								gcpPrel.setHeurePrelevement(date);
								gcpPrel.setIdTpagcp(null);
								gcpPrel.setRapprocher(1);
								gcpPrel.setIdPrelevement(idPrel);
								gcpPrelRepo.save(gcpPrel);

								EuCnp cnpp = cnpRepo.findByEuGcp_IdGcp(gcp.getIdGcp());
								cnpp.setMontCredit(mont_gcp);
								cnpp.setSoldeCnp(cnpp.getMontDebit() - cnpp.getMontCredit());
								cnpRepo.save(cnpp);

								mont_gcp = 0;
								System.out.println("Cas 1");
							} else {
								mont_gcp -= gcp.getReste();

								Long idPrel = gcpPrelRepo.getLastInsertId();
								if (idPrel == null) {
									idPrel = 1L;
								} else {
									idPrel += 1;
								}
								EuGcpPrelever gcpPrel = new EuGcpPrelever();
								gcpPrel.setCodeMembre(gcp.getCodeMembre());
								gcpPrel.setDatePrelevement(date);
								gcpPrel.setEuGcp(gcp);
								gcpPrel.setEuTegc(tegc);
								gcpPrel.setIdCredit(gcp.getEuCompteCredit().getIdCredit());
								gcpPrel.setIdOperation(op.getIdOperation());
								gcpPrel.setSourceCredit(gcp.getEuCompteCredit().getSource());
								gcpPrel.setMontPrelever(gcp.getReste());
								gcpPrel.setMontRapprocher(gcp.getReste());
								gcpPrel.setSoldePrelevement(0);
								gcpPrel.setHeurePrelevement(date);
								gcpPrel.setIdTpagcp(null);
								gcpPrel.setRapprocher(1);
								gcpPrel.setIdPrelevement(idPrel);
								gcpPrelRepo.save(gcpPrel);

								EuCnp cnpp = cnpRepo.findByEuGcp_IdGcp(gcp.getIdGcp());
								if (Objects.nonNull(cnpp)) {
									cnpp.setMontCredit(gcp.getReste());
									cnpp.setSoldeCnp(cnpp.getMontDebit() - cnpp.getMontCredit());
									cnpRepo.save(cnpp);
								}

								gcp.setMontPreleve(gcp.getMontPreleve() + gcp.getReste());
								gcp.setReste(0);
								gcpRepo.save(gcp);
								i++;
								System.out.println("Cas 2");
							}
						}

						// Mise à jour du TEGC du distributeur qui échange
						tegc.setMontantUtilise(tegc.getMontantUtilise() + montant);
						tegc.setSoldeTegc(tegc.getSoldeTegc() - montant);
						tegcRepo.save(tegc);

						if (mont_gcp == 0) {
							break;
						}
					}
					return true;
				} else {
					throw new SoldeInsuffisantException();
				}

			} else {
				throw new SoldeInsuffisantException();
			}
		} else {
			throw new CompteNonTrouveException();
		}
	}

	@Override
	public boolean echangeGcp(String codeMembre, List<Long> tpagcps, String typeEchange, double prk, double montant,
			String typeProduit) throws SoldeInsuffisantException, NullPointerException, CompteNonIntegreException {
		if (StringUtils.isNotBlank(codeMembre)) {
			List<EuTpagcp> tgcps = Lists.newArrayList();
			if (tpagcps != null && !tpagcps.isEmpty()) {
				tgcps = tpacgpRepo.findbyIds(tpagcps);
			} else {
				tgcps = tpacgpRepo.findByMembreAndModeReg(codeMembre, "OPI");
			}
			if (tgcps.isEmpty()) {
				throw new SoldeInsuffisantException();
			} else {
				Date date = new Date();
				String codeCompteObt = "NB-TPAGCI-" + codeMembre;
				String codeProduit = "Inr";
				String codecat = "TPAGCI";
				if (codeMembre.endsWith("P")) {
					codeCompteObt = "NB-TPAGCRPG-" + codeMembre;
					codeProduit = "RPGnr";
					codecat = "TPAGCRPG";
				}
				double mont_gcp_deduire = montant;
				double mont_deduit = 0;
				EuMembreMorale morale = moralRepo.findByKey(codeMembre);
				for (EuTpagcp tpa : tgcps) {
					List<EuGcpPrelever> gcpPrels = gcpPrelRepo.findGcpPreleverByIdTpagcp(tpa.getIdTpagcp());
					int compteur = 0;
					while (mont_gcp_deduire > 0 && compteur < gcpPrels.size()) {
						EuGcpPrelever gcpPrel = gcpPrels.get(compteur);
						EuCnp cnp = cnpRepo.findByEuGcp_IdGcp(gcpPrel.getEuGcp().getIdGcp());
						if (Objects.nonNull(cnp)) {
							if (gcpPrel.getSoldePrelevement() >= mont_gcp_deduire) {
								cnp.setMontCredit(cnp.getMontCredit() + mont_gcp_deduire);
								cnp.setSoldeCnp(cnp.getMontDebit() - cnp.getMontCredit());
								cnpRepo.save(cnp);

								gcpPrel.setMontRapprocher(gcpPrel.getMontRapprocher() + mont_gcp_deduire);
								gcpPrel.setSoldePrelevement(gcpPrel.getMontPrelever() - gcpPrel.getMontRapprocher());
								gcpPrelRepo.save(gcpPrel);

								mont_deduit += mont_gcp_deduire;
								mont_gcp_deduire = 0;
							} else {
								cnp.setMontCredit(cnp.getMontCredit() + gcpPrel.getSoldePrelevement());
								cnp.setSoldeCnp(cnp.getMontDebit() - cnp.getMontCredit());
								cnpRepo.save(cnp);

								mont_gcp_deduire -= gcpPrel.getSoldePrelevement();
								mont_deduit += gcpPrel.getSoldePrelevement();

								gcpPrel.setMontRapprocher(gcpPrel.getMontRapprocher() + gcpPrel.getSoldePrelevement());
								gcpPrel.setSoldePrelevement(0);
								gcpPrelRepo.save(gcpPrel);

								compteur += 1;
							}
						} else {
							throw new CompteNonIntegreException(
									"Votre compte n'est pas intègre : Il y a des unités qui n'ont pas d'origine");
						}
					}
					System.out.println("Fin Mise à jour des CNP et des GCP Prelevés");

					int nbreTraite = Double.valueOf(Math.ceil(mont_deduit / tpa.getMontTranche())).intValue();
					List<EuTraite> traites = traiteRepo.findByTpagcpAndDispo(tpa.getIdTpagcp(), 0);
					if (traites.size() > 0 && nbreTraite <= traites.size()) {
						System.out.println("Mise à jour des traites");
						for (int j = 0; j < nbreTraite; j++) {
							EuTraite traite = traites.get(j);
							traite.setTraiteEscompteNature(1);
							traiteRepo.save(traite);
						}
						System.out.println("Fin Mise à jour des " + nbreTraite + " traites");
					} else {
						throw new SoldeInsuffisantException("Nombre d'OPI insuffisant pour effectuer cette operation");
					}

					tpa.setMontEscompte(tpa.getMontEscompte() + mont_deduit);
					tpa.setResteNtf(tpa.getResteNtf() - nbreTraite);
					tpa.setSolde(tpa.getSolde() - mont_deduit);
					tpacgpRepo.save(tpa);

					if (mont_gcp_deduire == 0) {
						break;
					}
				}

				Long idOp = opRepo.getLastOperationInsertedId();
				if (idOp == null) {
					idOp = 0L;
				}

				// Enregistrement de l'opération
				EuCategorieCompte categorie = new EuCategorieCompte();
				categorie.setCodeCat(codecat);

				EuOperation op = new EuOperation();
				op.setEuCategorieCompte(categorie);
				op.setCodeProduit("GCP");
				op.setDateOp(date);
				op.setLibOp("Echange de GCP");
				op.setTypeOp("E");
				op.setMontantOp(montant);
				op.setEuMembreMorale(morale);
				op.setIdOperation(idOp + 1);
				op.setEuUtilisateur(null);
				op.setHeureOp(date);
				opRepo.save(op);

				// Mise à jour ou Création du compte crédit obtenu
				EuTypeCompte typeCompte = new EuTypeCompte();
				EuCategorieCompte cat = new EuCategorieCompte();
				EuCompte compteObt = compteRepo.findOne(codeCompteObt);
				if (compteObt != null) {
					compteObt.setSolde(compteObt.getSolde() + montant);
					compteRepo.save(compteObt);
				} else {
					cat.setCodeCat(codecat);
					typeCompte.setCodeTypeCompte("NB");

					compteObt = new EuCompte();
					compteObt.setCodeCompte(codeCompteObt);
					compteObt.setDateAlloc(date);
					compteObt.setDesactiver("N");
					compteObt.setEuCategorieCompte(cat);
					compteObt.setEuMembreMorale(morale);
					compteObt.setEuMembre(null);
					compteObt.setSolde(montant);
					compteObt.setEuTypeCompte(typeCompte);
					compteObt.setLibCompte("NB " + codecat);
					compteObt.setCardprinteddate(null);
					compteObt.setCardprintediddate(0);
					compteObt.setMifarecard(null);
					compteObt.setNumeroCarte(null);
					compteRepo.save(compteObt);
				}

				// Enregistrement de l'echange proprement dit
				Long id_echange = echrepo.findLastEchangeInsertedId();
				if (id_echange == null) {
					id_echange = 0L;
				}
				EuEchange ech = new EuEchange();
				ech.setIdEchange(id_echange + 1);
				ech.setCatEchange("GCP");
				ech.setTypeEchange("NB/NB");
				ech.setMontantEchange(montant);
				ech.setMontant(montant);
				ech.setCompenser(0);
				ech.setRegler(0);
				ech.setEuCompte(compteObt);
				ech.setCodeCompteObt(codeCompteObt);
				ech.setDateEchange(date);
				ech.setDateReglement(date);
				ech.setEuMembreMorale(morale);
				ech.setEuMembre(null);
				ech.setCodeProduit(codeProduit);
				ech.setAgio(0);
				echrepo.save(ech);

				// Céation du crédit concerné par l'échange
				formatter = new SimpleDateFormat("yyyyMMddHHmmss");
				EuProduit produit = new EuProduit();
				produit.setCodeProduit(codeProduit);
				EuCompteCredit cc = new EuCompteCredit();
				long idCredit = creditRepo.getLastCreditInsertedId() + 1;
				cc.setIdCredit(idCredit);
				cc.setCodeMembre(codeMembre);
				cc.setMontantPlace(montant);
				cc.setMontantCredit(montant);
				cc.setEuOperation(op);
				cc.setEuProduit(produit);
				if (codeProduit.equals("RPGnr")) {
					cc.setCodeTypeCredit("CNPG");
				} else {
					cc.setCodeTypeCredit("REAPPRO");
				}
				cc.setSource(codeMembre + formatter.format(date));
				cc.setCompteSource("EGCP-" + id_echange);
				cc.setDateOctroi(date);
				cc.setDatedeb(date);
				Date datefin = ServerUtil.ajouterJours(date, 30);
				cc.setDatefin(datefin);
				cc.setRenouveller("N");
				cc.setNbreRenouvel(Long.valueOf(Math.round(prk)).intValue());
				cc.setBnp(0);
				cc.setKrr("N");
				cc.setEuCompte(compteObt);
				cc.setDomicilier(0);
				cc.setPrk(prk);
				cc.setAffecter(0);
				cc.setTypeProduit(typeProduit);
				cc.setCodeBnp(null);
				creditRepo.save(cc);

				// Création du CNP correspondant au crédit obtenu
				System.out.println("Jusqu'ici tout va bien 0");
				long idCnp = cnpRepo.getLastCnpInsertedId() + 1;
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
				cnp.setIdCnp(idCnp);
				cnpRepo.save(cnp);

				// Création du FN correspondant au crédit échangé
				Long idFn = fnRepo.getLastFnInsertedId();
				if (idFn == null) {
					idFn = 1L;
				} else {
					idFn += 1;
				}
				EuFn fn = new EuFn();
				fn.setIdFn(idFn);
				fn.setTypeFn("Inr");
				fn.setDateFn(date);
				fn.setEuCapa(null);
				fn.setMontant(montant);
				fn.setSortie(0);
				fn.setEntree(0);
				fn.setSolde(0);
				fn.setMtSolde(montant);
				fn.setOrigineFn(1);
				fnRepo.save(fn);
			}
		} else {
			throw new CompteNonTrouveException("Veuillez fournir le code du membre qui demande cette opération!");
		}
		return false;
	}

	@Override
	public boolean echangeNRNB(String codeMembre, String numeroAppelOffre, double montant) {
		// TODO Auto-generated method stub
		return false;
	}

}

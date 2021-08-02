/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.components;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.core.utils.SMSUtil;
import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dao.repository.acteurs.EuActeurRepository;
import com.esmc.mcnp.dao.repository.ba.EuCapaRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteRepository;
import com.esmc.mcnp.dao.repository.cmfh.EuCmfhRepository;
import com.esmc.mcnp.dao.repository.common.EuSmsRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuDetailFgfnRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuDetailSmsmoneyRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuDetailVentesmsRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuFgfnRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuNnRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuTransfertNnRepository;
import com.esmc.mcnp.dao.repository.others.EuGacRepository;
import com.esmc.mcnp.dao.repository.others.EuSmsmoneyRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuAppelOffreRepository;
import com.esmc.mcnp.domain.dto.bn.TransfertSMS;
import com.esmc.mcnp.domain.dto.smcipn.Transfert;
import com.esmc.mcnp.domain.entity.acteur.EuActeur;
import com.esmc.mcnp.domain.entity.acteur.EuGac;
import com.esmc.mcnp.domain.entity.ba.EuCapa;
import com.esmc.mcnp.domain.entity.ba.EuDetailSmsmoney;
import com.esmc.mcnp.domain.entity.ba.EuDetailVentesms;
import com.esmc.mcnp.domain.entity.ba.EuNn;
import com.esmc.mcnp.domain.entity.cm.EuCategorieCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.cm.EuTypeCompte;
import com.esmc.mcnp.domain.entity.obpsd.EuDetailFgfn;
import com.esmc.mcnp.domain.entity.obpsd.EuFgfn;
import com.esmc.mcnp.domain.entity.obpsd.EuSmsmoney;
import com.esmc.mcnp.domain.entity.obpsd.EuTransfertNn;
import com.esmc.mcnp.domain.entity.obpsd.EuTypeNn;
import com.esmc.mcnp.domain.entity.op.EuAppelOffre;
import com.esmc.mcnp.domain.entity.sms.EuSms;
import com.esmc.mcnp.commons.exception.business.CompteNonIntegreException;
import com.esmc.mcnp.commons.exception.business.CompteNonTrouveException;
import com.esmc.mcnp.commons.exception.business.SoldeInsuffisantException;
import com.esmc.mcnp.infrastructure.services.obpsd.EuNnService;
import com.esmc.mcnp.infrastructure.services.setting.EuParametresService;

/**
 *
 * @author mawuli
 */
@Service
@Transactional
public class TransfertServiceImpl implements TransfertService {

	private @Autowired EuParametresService paramService;
	private @Autowired EuCompteRepository compteDao;
	private @Autowired EuSmsmoneyRepository smsMoneyDao;
	private @Autowired EuNnRepository nnDao;
	private @Autowired EuDetailSmsmoneyRepository detSmsmoneyDao;
	private @Autowired EuActeurRepository acteurDao;
	private @Autowired EuFgfnRepository fgfnDao;
	private @Autowired EuDetailFgfnRepository detFgfnRepo;
	private @Autowired EuTransfertNnRepository transfertDao;
	private @Autowired EuGacRepository gacDao;
	private @Autowired EuDetailVentesmsRepository detVteSmsDao;
	private @Autowired EuSmsRepository smsDao;
	private @Autowired EuNnService nnService;
	private @Autowired EuCapaRepository capaRepo;
	private @Autowired EuAppelOffreRepository appelRepo;
	private @Autowired EuCmfhRepository cmfhRepo;

	SimpleDateFormat formatter;

	/**
	 * Methode de transfert en gros des uintés NN
	 *
	 * @param transfert
	 * @return chaines de caractères expliquant si l'opération a été bien effectuée
	 *         ou non
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String doTransfertGros(TransfertSMS transfert) {

		if (transfert.getCodeCompteVendeur() == null || transfert.getCodeCompteVendeur().isEmpty()) {
			return "Echec de l'opération : Précisez le Numéro de compte de transfert!";
		}
		if (transfert.getCodeCompteAcheteur() == null || transfert.getCodeCompteAcheteur().isEmpty()) {
			return "Echec de l'opération : Précisez le numéro du compte de réception";
		}
		if (transfert.getTypeNn() == null || transfert.getTypeNn().isEmpty()) {
			return "Echec de l'opération : Précisez le type d'unité NN à transférer!";
		}
		if (transfert.getTypeRecharge() == null || transfert.getTypeRecharge().isEmpty()) {
			return "Echec de l'opération : Précisez le mode de transfert : SMS ou Recharge directe!";
		}
		if (!ServerUtil.getTypeNumerique(transfert.getCodeCompteVendeur()).equals("NN")
				|| !ServerUtil.getTypeCompte(transfert.getCodeCompteVendeur()).equals("TR")) {
			return "Compte de transfert Invalide : On a besoin d'un compte de transfert!";
		}
		if (!ServerUtil.getTypeNumerique(transfert.getCodeCompteAcheteur()).equals("NN")
				|| !ServerUtil.getTypeCompte(transfert.getCodeCompteAcheteur()).equals("TR")) {
			return "Compte de l'acheteur Invalide : On a besoin d'un compte de transfert!";
		}

		String codeMembre = ServerUtil.getCodeMembre(transfert.getCodeCompteVendeur());
		String codeMembre_a = ServerUtil.getCodeMembre(transfert.getCodeCompteAcheteur());
		if (codeMembre.equalsIgnoreCase(codeMembre_a)) {
			return "Vous ne pouvez pas vendre à vous-même!";
		}

		EuActeur acteur_v = acteurDao.findByCodeMembre(codeMembre);
		EuActeur acteur_a = acteurDao.findByCodeMembre(codeMembre_a);

		if (acteur_v == null || acteur_a == null) {
			return "Le Vendeur ou l'acheteur n'est pas mis sur chaîne";
		}

		if (!acteur_v.getTypeActeur().equals("gac_detentrice") && !acteur_v.getTypeActeur().equals("gac_surveillance")
				&& !acteur_v.getTypeActeur().equals("gac_executante") && !acteur_v.getTypeActeur().equals("ACNEV")
				&& !acteur_v.getTypeActeur().equals("PBF")) {
			return "Seul les GAC Détentrice et Surveillance peuvent faire des transferts en gros!";
		}

		if (acteur_v.getTypeActeur().equals("gac_detentrice")
				&& ((acteur_a.getTypeActeur().equals("gac_detentrice") && acteur_a.getCodeActivite().equals("SOURCE"))
						&& !acteur_a.getTypeActeur().equals("gac_surveillance")
						&& !acteur_a.getTypeActeur().equals("gac_executante"))) {
			return "Operation Impossible : La detentrice ne peut vendre qu'aux surveillances!";
		}
		if (acteur_v.getTypeActeur().equals("gac_surveillance") && !acteur_a.getTypeActeur().equals("gac_executante")) {
			return "Operation Impossible : La Surveillance ne peut vendre qu'a l'exécutante!!";
		}

		if (acteur_v.getTypeActeur().equalsIgnoreCase("gac_executante")
				&& acteur_v.getCodeActivite().equalsIgnoreCase("SOURCE")) {
			if (!acteur_a.getTypeActeur().equalsIgnoreCase("ACNEV")
					&& !acteur_a.getCodeActivite().equalsIgnoreCase("SOURCE")) {
				return "Operation Impossible : L'executante Source ne peut transferer les unités qu'a l'ACNEV Source!!";
			}
		}

		if (acteur_v.getTypeActeur().equalsIgnoreCase("gac_executante")
				&& acteur_v.getCodeActivite().equalsIgnoreCase("detaillant")) {
			if (!acteur_a.getTypeActeur().equalsIgnoreCase("ACNEV")
					&& !acteur_a.getCodeActivite().equalsIgnoreCase("detaillant")) {
				return "Operation Impossible : L'executante Source ne peut transferer les unités qu'a l'ACNEV Source!!";
			}
		}

		if (acteur_v.getTypeActeur().equals("ACNEV") && acteur_v.getCodeActivite().equals("SOURCE")) {
			if (!acteur_a.getTypeActeur().equalsIgnoreCase("detentrice")
					&& !acteur_a.getCodeActivite().equalsIgnoreCase("detaillant")) {
				return "Le PBF ACNEV SOURCE ne peut vendre qu'aux Détentrices";
			}
		}

		if (acteur_v.getTypeActeur().equals("ACNEV") && acteur_v.getCodeActivite().equalsIgnoreCase("detaillant")) {
			if (!acteur_a.getTypeActeur().equalsIgnoreCase("PBF") && (!acteur_a.getTypeActeur().equals("grossiste")
					|| !acteur_a.getTypeActeur().equals("semi-grossiste")
					|| !acteur_a.getTypeActeur().equals("detaillant"))) {
				return "Le ACNEV PBF ne peut vendre qu'aux grossistes de SMS";
			}
		}

		if (acteur_v.getTypeActeur().equals("PBF") && (!acteur_v.getCodeActivite().equals("grossiste")
				&& !acteur_v.getCodeActivite().equals("semi-grossiste"))) {
			return "Seul les PBF grossiste et semi-grossiste peuvent faire un transfert en gros";
		}

		if (acteur_v.getTypeActeur().equals("PBF") && acteur_v.getCodeActivite().equalsIgnoreCase("grossiste")) {
			if (!acteur_a.getTypeActeur().equalsIgnoreCase("PBF") && (!acteur_a.getTypeActeur().equals("semi-grossiste")
					|| !acteur_a.getTypeActeur().equals("detaillant"))) {
				return "Le PBF grossiste ne peut vendre qu'aux semi-grossistes et détaillants de SMS";
			}
		}

		if (acteur_v.getTypeActeur().equals("PBF") && acteur_v.getCodeActivite().equalsIgnoreCase("semi-grossiste")) {
			if (!acteur_a.getTypeActeur().equalsIgnoreCase("PBF") && !acteur_a.getTypeActeur().equals("detaillant")) {
				return "Le PBF Semi-grossiste ne peut vendre qu'aux détaillants de SMS";
			}
		}

		EuCompte compte = compteDao.findOne(transfert.getCodeCompteVendeur());
		if (compte == null) {
			return "Compte de transfert Introuvable";
		}
		EuCompte dest_compte = compteDao.findOne(transfert.getCodeCompteAcheteur());
		if (dest_compte == null) {
			return "Compte Destination Introuvable";
		}
		Double soldenn = transfertDao.FindSoldeNnByCompte(compte.getCodeCompte());
		if (compte.getSolde() == soldenn) {
			Double soldeTypenn = transfertDao.FindSoldeNnByTypeNn(compte.getCodeCompte(), transfert.getTypeNn());
			if (soldeTypenn != null) {
				if (soldeTypenn < transfert.getMontant()) {
					return "Le solde du compte NN " + transfert.getTypeNn() + " de transfert est insuffisant";
				}
			} else {
				return "Vous n'avez de transfert NN de type " + transfert.getTypeNn();
			}
			if (compte.getSolde() < transfert.getMontant()) {
				return "Le solde du compte de transfert est insuffisant";
			}

			Date date = new Date();
			try {
				Double sv_mont = transfert.getMontant();
				List<EuTransfertNn> transferts = transfertDao.findByTypeNnAndCompte(transfert.getCodeCompteVendeur(),
						transfert.getTypeNn());
				if (transferts != null && transferts.size() > 0) {
					for (EuTransfertNn euTransfertNn : transferts) {
						if (euTransfertNn.getSoldeTransfert() >= sv_mont) {
							euTransfertNn.setMontVendu(euTransfertNn.getMontVendu() + sv_mont);
							euTransfertNn.setSoldeTransfert(euTransfertNn.getSoldeTransfert() - sv_mont);
							transfertDao.save(euTransfertNn);
							break;
						} else {
							euTransfertNn
									.setMontVendu(euTransfertNn.getMontVendu() + euTransfertNn.getSoldeTransfert());
							euTransfertNn.setSoldeTransfert(0);
							transfertDao.save(euTransfertNn);
						}
					}
				} else {
					return "Vous n'avez pas de transfert de type :" + transfert.getTypeNn();
				}
				System.out.println("Vendeur -> " + acteur_v.getCodeMembre() + ":" + acteur_v.getTypeActeur() + ":"
						+ acteur_v.getCodeActivite());
				System.out.println("Acheteur -> " + acteur_a.getCodeMembre() + ":" + acteur_a.getTypeActeur() + ":"
						+ acteur_a.getCodeActivite());
				if (acteur_a.getTypeActeur().equals("gac_surveillance")
						|| acteur_a.getTypeActeur().equals("gac_executante")
						|| (acteur_a.getTypeActeur().equals("ACNEV") && acteur_a.getCodeActivite().equals("SOURCE"))
						|| (acteur_a.getTypeActeur().equals("ACNEV") && acteur_a.getCodeActivite().equals("detaillant"))
						|| (acteur_a.getTypeActeur().equals("gac_detentrice")
								&& acteur_a.getCodeActivite().equals("detaillant"))
						|| (acteur_a.getTypeActeur().equals("PBF") && acteur_a.getCodeActivite().equals("grossiste"))
						|| (acteur_a.getTypeActeur().equals("PBF")
								&& acteur_a.getCodeActivite().equals("semi-grossiste"))) {
					EuTypeNn euTypeNn = new EuTypeNn();
					Long idtranfert = transfertDao.getLastTransfertInsertId();
					if (idtranfert == null) {
						idtranfert = 0L;
					}
					euTypeNn.setCodeTypeNn(transfert.getTypeNn());
					EuTransfertNn transfertNn = new EuTransfertNn();
					transfertNn.setIdTransfertNn(idtranfert + 1);
					transfertNn.setEuCompteDist(dest_compte);
					transfertNn.setEuCompteTransfert(compte);
					transfertNn.setDateTransfert(date);
					transfertNn.setMontTransfert(transfert.getMontant());
					transfertNn.setMontVendu(0);
					transfertNn.setSoldeTransfert(transfert.getMontant());
					transfertNn.setEuTypeNn(euTypeNn);
					transfertNn.setTypeTransfert(transfert.getTypeTransfert());
					transfertNn.setTypeReglement(transfert.getTypeReglement());
					if (transfert.getTypeReglement().equals("Credit")) {
						transfertNn.setMontRegle(0);
						transfertNn.setRestantDu(transfert.getMontant());
					} else {
						transfertNn.setMontRegle(transfert.getMontant());
						transfertNn.setRestantDu(0);
					}
					transfertNn.setCodeDocument(transfert.getNumero_doc());
					transfertDao.save(transfertNn);
				} else {
					Long idDetailSmsMoney = detSmsmoneyDao.getLastDetSmsmooneyInsertedId();
					if (idDetailSmsMoney == null) {
						idDetailSmsMoney = 0L;
					}
					EuDetailSmsmoney detSmsMoney = new EuDetailSmsmoney();
					detSmsMoney.setIdDetailSmsmoney(idDetailSmsMoney + 1);
					detSmsMoney.setCodeMembre(dest_compte.getEuMembreMorale().getCodeMembreMorale());
					detSmsMoney.setEuMembreMorale(compte.getEuMembreMorale());
					detSmsMoney.setOrigineSms("TR");
					detSmsMoney.setCreditcode("");
					detSmsMoney.setDateAllocation(date);
					detSmsMoney.setMontSms(transfert.getMontant());
					if (transfert.getTypeReglement().equals("Credit")) {
						detSmsMoney.setMontRegle(0);
					} else {
						detSmsMoney.setMontRegle(transfert.getMontant());
					}
					detSmsMoney.setIdUtilisateur(0L);
					detSmsMoney.setTypeSms(transfert.getTypeNn());
					detSmsMoney.setMontVendu(0);
					detSmsMoney.setSoldeSms(transfert.getMontant());
					detSmsmoneyDao.save(detSmsMoney);
				}

				compte.setSolde(compte.getSolde() - transfert.getMontant());
				compteDao.save(compte);

				if (transfert.getTypeRecharge().equals("Direct")) {
					dest_compte.setSolde(dest_compte.getSolde() + transfert.getMontant());
					compteDao.save(dest_compte);
				} else {
					Long smsMoneyId = smsMoneyDao.getLastInsertId();
					if (smsMoneyId == null) {
						smsMoneyId = 0L;
					}
					System.out.println("Ajout du transfert");
					EuSmsmoney smsMoney = new EuSmsmoney();
					smsMoney.setNeng(smsMoneyId + 1);
					smsMoney.setCreditamount(transfert.getMontant());
					smsMoney.setSentto(transfert.getNumeroAcheteur());
					smsMoney.setFromaccount(transfert.getCodeCompteVendeur());
					smsMoney.setMotif(transfert.getTypeNn());
					smsMoney.setDatetime(ServerUtil.formatDate(date));
					smsMoney.setCreditcode(ServerUtil.GenererUniqueCode());
					smsMoneyDao.save(smsMoney);
				}

				EuSms sms = new EuSms();
				Long liastId = smsDao.getLastSmsInserted();
				if (liastId == null) {
					liastId = 0L;
				}
				sms.setNeng(liastId + 1);
				sms.setDateenvoi(date.toString());
				sms.setEtat(0);
				sms.setRecipient(transfert.getNumeroAcheteur());
				sms.setSmsbody("Vous venez d'effectuer un transfert " + transfert.getTypeNn() + " de "
						+ transfert.getMontant());
				smsDao.save(sms);
				SMSUtil.insertSms(transfert.getNumeroAcheteur(), "Vous venez d'effectuer un transfert "
						+ transfert.getTypeNn() + " de " + transfert.getMontant());
				return "Opération effectuée avec succès!";
			} catch (Exception e) {
				System.out.println("Erreur d'éxécution: " + e.getMessage());
				return "Erreur d'éxécution: " + e.getMessage();
			}
		} else {
			return "Votre compte est incohérent.\n Veuillez s'adresser l'adminitrateur pour vérification!";
		}
	}

	/**
	 * Methode de tansfert de NN pour les transferts en détails des PBF Détaillants
	 *
	 * @param transfert
	 *            Infos sur le transfert à effectuer; objet venant du client
	 * @return String chaines de caractères expliquant l'éxécution de l'opération
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String doTransfertDetail(TransfertSMS transfert) {
		String response = "echec";
		String categorie_v = ServerUtil.getTypeCompte(transfert.getCodeCompteVendeur());
		String type_nv = ServerUtil.getTypeNumerique(transfert.getCodeCompteVendeur());
		String codeMembre_v = ServerUtil.getCodeMembre(transfert.getCodeCompteVendeur());
		if (transfert.getTypeTransfert().equals("DET")) {
			// verification du compte de transfert
			if (type_nv.equalsIgnoreCase("NN") && categorie_v.equals("TR")) {
				if (codeMembre_v.endsWith("P") && (!transfert.getTypeNn().equalsIgnoreCase("FS")
						&& !transfert.getTypeNn().equalsIgnoreCase("FL")
						&& !transfert.getTypeNn().equalsIgnoreCase("FCPS")
						&& !transfert.getTypeNn().equalsIgnoreCase("CAPS"))) {
					return "Les membres physiques ne peuvent vendre que des unités d'enrollement:\n FS, FL, FCPS ou CAPS!";
				}

				EuCompte compteMarge = null;
				EuMembreMorale surveillance = null;
				String codeCompteMarge = null;
				EuActeur acteur_v = null;
				EuGac gac_surv = null;
				if (transfert.getCodeCompteVendeur().endsWith("M")) {
					acteur_v = acteurDao.findByCodeMembre(codeMembre_v);
					if (acteur_v != null) {
						if (!acteur_v.getTypeActeur().equalsIgnoreCase("PBF")
								/**
								 * && !acteur_v.getTypeActeur().equalsIgnoreCase(" CMFH")
								 */
								&& !acteur_v.getTypeActeur().equalsIgnoreCase("DSMS")) {
							return "Seuls les PBF et les cybers Marchés peuvent vendre en détails!";
						}
						if (acteur_v.getTypeActeur().equalsIgnoreCase("PBF")
								&& !acteur_v.getCodeActivite().equalsIgnoreCase("detaillant")) {
							return "Seuls les PBFS detaillants peuvent vendre en détails";
						}
						gac_surv = gacDao.findOne(acteur_v.getEuGac().getCodeGac());
						if (gac_surv != null
								&& gac_surv.getEuTypeGac().getCodeTypeGac().equalsIgnoreCase("gac_surveillance")) {
							codeCompteMarge = "NN-TMARGE-" + gac_surv.getEuMembreMorale().getCodeMembreMorale();
							compteMarge = compteDao.findOne(codeCompteMarge);
							surveillance = gac_surv.getEuMembreMorale();
						}
					} else {
						return "Cet acteur n'existe pas";
					}
				}

				double mont_transfert = transfert.getMontant();
				if (transfert.getTypeNn().startsWith("MF")) {
					if (transfert.getTypeNn().startsWith("MF1") && !(mont_transfert % 70000 == 0)) {
						return "Le transfert MF11000 et MF107 doit être un multiple de 70000";
					}
					double quotaMF = paramService.getParametre("quotaMF", "valeur");
					if (mont_transfert / 70000 > quotaMF) {
						return "Vous ne pouvez vendre MF depassant " + quotaMF * 70000;
					}
				}
				if (transfert.getTypeNn().equals("CAPS") && !(mont_transfert % 70000 == 0)) {
					return "Le transfert FS doit être un multiple de 70000";
				}
				if (transfert.getTypeNn().equals("FS") && !(mont_transfert % 50000 == 0)) {
					return "Le transfert FS doit être un multiple de 50000";
				}
				if (transfert.getTypeNn().equals("FL") && !(mont_transfert % 10000 == 0)) {
					return "Le transfert FL doit être un multiple de 10000";
				}
				if (transfert.getTypeNn().equals("FCPS") && !(mont_transfert % 10000 == 0)) {
					return "Le transfert FCPS doit être un multiple de 10000";
				}

				EuCompte compteTransfert = compteDao.findOne(transfert.getCodeCompteVendeur());
				if (compteTransfert != null) {

					Double soldeByTypeNn = 0.0;
					soldeByTypeNn = detSmsmoneyDao.findSumByTypeNnAndMembre(
							compteTransfert.getEuMembreMorale().getCodeMembreMorale(), transfert.getTypeNn());
					if (soldeByTypeNn >= transfert.getMontant()) {
						Double soldeDetSms;
						List<EuDetailSmsmoney> detSmsmoneys;
						Long lastDetVteId;
						try {
							soldeDetSms = detSmsmoneyDao
									.findSumByMembre(ServerUtil.getCodeMembre(compteTransfert.getCodeCompte()));
							System.out.println("Récupération des détails SMSMoney :" + soldeDetSms);
							detSmsmoneys = detSmsmoneyDao.findByTypeNnAndMembre(
									ServerUtil.getCodeMembre(transfert.getCodeCompteVendeur()), transfert.getTypeNn());
							if (detSmsmoneys == null || detSmsmoneys.isEmpty()) {
								return "Vous n'avez pas de transfert de type " + transfert.getTypeNn();
							}
						} catch (Exception e) {
							System.out.println(e.getMessage());
							return "Vous n'avez pas de transferts.\n Veuillez vérifier votre compte de transfert";
						}
						String codeSMS = StringUtils.EMPTY;
						if (soldeDetSms != null && soldeDetSms == compteTransfert.getSolde()) {
							Date date = new Date();
							try {

								codeSMS = ServerUtil.GenererUniqueCode();
								double tx_nnmarge = paramService.getParametre("NNMARGE", "valeur");
								double mont_marge = transfert.getMontant() * tx_nnmarge / 100;

								long smsMoneyId = smsMoneyDao.getLastInsertId();
								compteTransfert.setSolde(compteTransfert.getSolde() - transfert.getMontant());
								compteDao.save(compteTransfert);

								EuSmsmoney smsMoney = new EuSmsmoney();
								smsMoney.setNeng(smsMoneyId + 1);
								smsMoney.setCreditamount(transfert.getMontant());
								smsMoney.setSentto(transfert.getNumeroAcheteur());
								smsMoney.setFromaccount(transfert.getCodeCompteVendeur());
								smsMoney.setMotif(transfert.getTypeNn());
								smsMoney.setDatetime(ServerUtil.formatDate(date));
								smsMoney.setCurrencycode("XOF");
								smsMoney.setCreditcode(codeSMS);
								smsMoney.setIddatetimeconsumed(0);
								smsMoneyDao.save(smsMoney);

								int i = 0;
								while (mont_transfert > 0) {
									lastDetVteId = detVteSmsDao.getLastDetVenteSmsmoneyInsertedId();
									if (lastDetVteId == null) {
										lastDetVteId = 0L;
									}
									EuDetailSmsmoney detSmsMoney = detSmsmoneys.get(i);
									if (detSmsMoney.getSoldeSms() >= mont_transfert) {
										lastDetVteId += 1;
										EuDetailVentesms detVentesms = new EuDetailVentesms();
										detVentesms.setIdDetailVtsms(lastDetVteId);
										detVentesms.setEuDetailSmsmoney(detSmsMoney);
										detVentesms.setEuMembreMorale(compteTransfert.getEuMembreMorale());
										detVentesms.setCreditcode(smsMoney.getCreditcode());
										detVentesms.setMontVente(mont_transfert);
										detVentesms.setCodeProduit(transfert.getTypeNn());
										detVentesms.setTypeTransfert(transfert.getTypeNn());
										detVentesms.setDateVente(date);
										detVteSmsDao.save(detVentesms);

										detSmsMoney.setMontVendu(detSmsMoney.getMontVendu() + mont_transfert);
										detSmsMoney.setSoldeSms(detSmsMoney.getSoldeSms() - mont_transfert);
										detSmsmoneyDao.save(detSmsMoney);

										mont_transfert = 0;
									} else {
										lastDetVteId += 1;
										mont_transfert = mont_transfert - detSmsMoney.getSoldeSms();
										EuDetailVentesms detVentesms = new EuDetailVentesms();
										detVentesms.setIdDetailVtsms(lastDetVteId);
										detVentesms.setEuDetailSmsmoney(detSmsMoney);
										detVentesms.setEuMembreMorale(compteTransfert.getEuMembreMorale());
										detVentesms.setCreditcode(smsMoney.getCreditcode());
										detVentesms.setMontVente(detSmsMoney.getSoldeSms());
										detVentesms.setTypeTransfert(transfert.getTypeNn());
										detVentesms.setDateVente(date);
										detVentesms.setCodeProduit(transfert.getTypeNn());
										detVteSmsDao.save(detVentesms);

										detSmsMoney
												.setMontVendu(detSmsMoney.getMontVendu() + detSmsMoney.getSoldeSms());
										detSmsMoney.setSoldeSms(0);
										detSmsmoneyDao.save(detSmsMoney);
										i++;
									}
								}

								if (!transfert.getTypeNn().equals("FS") && !transfert.getTypeNn().equals("FL")
										&& !transfert.getTypeNn().equals("FCPS")
										&& !transfert.getTypeNn().equals("CAPS")) {
									System.out.println("Determination du type FGFN");
									String type_fgfn = "FGFNMES";
									System.out.println("Mise à jour FGFN");
									String codeFgfn = type_fgfn + "-"
											+ compteTransfert.getEuMembreMorale().getCodeMembreMorale();
									EuFgfn fgfn = fgfnDao.findOne(codeFgfn);
									if (fgfn != null) {
										fgfn.setMontantFgfn(fgfn.getMontantFgfn() + transfert.getMontant());
										fgfn.setSoldeFgfn(fgfn.getSoldeFgfn() + transfert.getMontant());
										fgfnDao.save(fgfn);
									} else {
										fgfn = new EuFgfn();
										fgfn.setTypeFgfn(type_fgfn);
										fgfn.setCodeFgfn(codeFgfn);
										fgfn.setEuMembreMorale(compteTransfert.getEuMembreMorale());
										fgfn.setMontantFgfn(transfert.getMontant());
										fgfn.setMontantUtilise(0.0);
										fgfn.setSoldeFgfn(transfert.getMontant());
										fgfnDao.saveAndFlush(fgfn);
									}

									Long id_detFgfn = detFgfnRepo.findLastDetFgfnInsertedId();
									if (id_detFgfn == null) {
										id_detFgfn = 0L;
									} else {
										id_detFgfn++;
									}
									EuDetailFgfn det_fgfn = new EuDetailFgfn();
									det_fgfn.setCodeMembrePbf(
											compteTransfert.getEuMembreMorale().getCodeMembreMorale());
									det_fgfn.setDateFgfn(date);
									det_fgfn.setEuFgfn(fgfn);
									det_fgfn.setMontFgfn(transfert.getMontant());
									det_fgfn.setIdFgfn(id_detFgfn);
									det_fgfn.setMontPreleve(0);
									det_fgfn.setCreditcode(codeSMS);
									det_fgfn.setSoldeFgfn(transfert.getMontant());
									det_fgfn.setOrigineFgfn("TR");
									det_fgfn.setTypeFgfn(type_fgfn);
									detFgfnRepo.save(det_fgfn);
								}

								nnService.EmettreNn("NNMARGE", mont_marge, date);
								System.out.println("Allocation de la marge");
								if (compteMarge != null) {
									System.out.println("Mise à jour de la marge");
									compteMarge.setSolde(compteMarge.getSolde() + mont_marge);
									compteDao.save(compteMarge);
								} else {
									System.out.println("Creation du compte de la marge");
									EuCategorieCompte cat = new EuCategorieCompte();
									cat.setCodeCat("TMARGE");
									EuTypeCompte typeCompte = new EuTypeCompte();
									typeCompte.setCodeTypeCompte("NN");
									compteMarge = new EuCompte();
									compteMarge.setCodeCompte(codeCompteMarge);
									compteMarge.setEuCategorieCompte(cat);
									compteMarge.setEuTypeCompte(typeCompte);
									compteMarge.setEuMembre(null);
									compteMarge.setEuMembreMorale(surveillance);
									compteMarge.setDesactiver("N");
									compteMarge.setSolde(mont_marge);
									compteMarge.setDateAlloc(date);
									compteMarge.setCardprinteddate(null);
									compteMarge.setCardprintediddate(0);
									compteMarge.setLibCompte("NN Marge");
									compteDao.save(compteMarge);
								}
								System.out.println("Allocation de la marge : OK");

								EuSms sms = new EuSms();
								Long liastId = smsDao.getLastSmsInserted();
								if (liastId == null) {
									liastId = 0L;
								}
								sms.setNeng(liastId + 1);
								sms.setDateenvoi(date.toString());
								sms.setEtat(0);
								sms.setRecipient(transfert.getNumeroAcheteur());
								sms.setSmsbody("Vous venez d'effectuer un transfert " + transfert.getTypeNn() + " de "
										+ transfert.getMontant() + " Code :" + codeSMS);
								smsDao.save(sms);
								// SMSUtil.insertSms(transfert.getNumeroAcheteur(),
								// "Vous venez d'effectuer un transfert " +
								// transfert.getTypeNn() + " de " +
								// transfert.getMontant() + " Code :" +
								// codeSMS);
							} catch (Exception e) {
								return "Erreur d'exécution : Sauvegarde du transfert non éffectuée :\n"
										+ e.getLocalizedMessage();
							}
							SMSUtil.insertSms(transfert.getNumeroAcheteur(), "Vous venez d'effectuer un transfert "
									+ transfert.getTypeNn() + " de " + transfert.getMontant() + " Code :" + codeSMS);
							response = "L'operation a été effectuée avec succès!";
						} else {
							System.out.println("Solde du compte :" + compteTransfert.getSolde()
									+ ", Somme des détails :" + soldeDetSms);
							response = "Votre compte de transfert n'est pas correct.\n Veuillez contacter l'ACNEV de contrôle";
						}
					} else {
						response = "Le solde de votre compte de transfert est insuffisant\n pour effectuer cette opéraation";
					}
				} else {
					response = "Le compte de transfert n'existe pas!!!";
				}
			} else {
				response = "Le compte du transfert n'est pas valide!!!";
			}
		}
		return response;
	}

	@Override
	public String doTransfertSource(TransfertSMS transfert) {
		EuCompte dest_compte;
		String rep = "Echec de l'opération";
		if (transfert.getTypeNn() == null || transfert.getTypeNn().isEmpty()) {
			return "Echec de l'opération : Précisez le type d'unité NN à transférer!";
		}
		if (transfert.getTypeRecharge() == null || transfert.getTypeRecharge().isEmpty()) {
			return "Echec de l'opération : Précisez le mode de transfert : SMS ou Recharge directe!";
		}
		if (transfert.getCodeCompteVendeur() == null || transfert.getCodeCompteVendeur().isEmpty()) {
			if (transfert.getCodeCompteAcheteur() != null && !transfert.getCodeCompteAcheteur().isEmpty()
					&& transfert.getCodeCompteAcheteur().endsWith("M")) {
				if (!ServerUtil.getTypeNumerique(transfert.getCodeCompteAcheteur()).equals("NN")
						|| !ServerUtil.getTypeCompte(transfert.getCodeCompteAcheteur()).equals("TR")) {
					return "Compte de l'acheteur Invalide : On a besoin d'un compte de transfert!";
				}
			} else {
				return "Compte de l'acheteur Invalide : Compte Membre moral requis!";
			}

			dest_compte = compteDao.findOne(transfert.getCodeCompteAcheteur());
			if (dest_compte == null) {
				return "Compte Destination Introuvable";
			}

			EuActeur acteur_a = acteurDao.findByCodeMembre(dest_compte.getEuMembreMorale().getCodeMembreMorale());
			if (!acteur_a.getTypeActeur().equals("gac_detentrice")) {
				return "Seule la GAC Détentrice peut acheter en gros!";
			}
			Date date = new Date();
			try {
				double soldenn = nnDao.getSumNnByTypeNn(transfert.getTypeNn().trim());
				if (soldenn < transfert.getMontant()) {
					rep = "Solde Insuffisant pour effectuer ce transfert";
				} else {
					List<EuNn> nNs = nnDao.findSourceNns(transfert.getTypeNn().trim());
					if (nNs != null && !nNs.isEmpty()) {
						if (transfert.getTypeRecharge().equals("Direct")) {
							dest_compte.setSolde(dest_compte.getSolde() + transfert.getMontant());
							compteDao.save(dest_compte);
						} else {
							long smsMoneyId = smsMoneyDao.getLastInsertId();
							System.out.println("Ajout du transfert");
							EuSmsmoney smsMoney = new EuSmsmoney();
							smsMoney.setNeng(smsMoneyId + 1);
							smsMoney.setCreditamount(transfert.getMontant());
							smsMoney.setSentto(transfert.getNumeroAcheteur());
							smsMoney.setFromaccount(transfert.getCodeCompteVendeur());
							smsMoney.setMotif(transfert.getTypeNn());
							smsMoney.setDatetime(ServerUtil.formatDate(date));
							smsMoney.setCreditcode(ServerUtil.GenererUniqueCode());
							smsMoneyDao.save(smsMoney);
						}
						System.out.println("Nombre NNs :" + nNs.size());
						double sv_mont = transfert.getMontant();
						for (EuNn source_nn : nNs) {
							if (source_nn.getSoldeNn() >= sv_mont) {
								source_nn.setMontantRemb(source_nn.getMontantRemb() + sv_mont);
								source_nn.setSoldeNn(source_nn.getSoldeNn() - sv_mont);
								sv_mont = 0;
								System.out.println("Cas 1:");
							} else {
								source_nn.setMontantRemb(source_nn.getMontantRemb() + source_nn.getSoldeNn());
								sv_mont = sv_mont - source_nn.getSoldeNn();
								source_nn.setSoldeNn(0.0);
								System.out.println("Cas 2:");
							}
							nnDao.save(source_nn);

							long idtransfert = 0;
							if (transfertDao.count() > 0) {
								idtransfert = transfertDao.getLastTransfertInsertId();
							}
							EuTypeNn euTypeNn = new EuTypeNn();
							euTypeNn.setCodeTypeNn(transfert.getTypeNn());
							EuTransfertNn transfertNn = new EuTransfertNn();
							transfertNn.setIdTransfertNn(idtransfert + 1);
							transfertNn.setEuCompteDist(dest_compte);
							transfertNn.setEuCompteTransfert(null);
							transfertNn.setDateTransfert(date);
							transfertNn.setMontTransfert(transfert.getMontant());
							transfertNn.setMontVendu(0);
							transfertNn.setSoldeTransfert(transfert.getMontant());
							transfertNn.setEuTypeNn(euTypeNn);
							transfertNn.setTypeTransfert(transfert.getTypeTransfert());
							transfertNn.setTypeReglement(transfert.getTypeReglement());
							transfertNn.setCodeDocument(transfert.getNumero_doc());
							if (transfert.getTypeReglement().equals("Credit")) {
								transfertNn.setMontRegle(0);
								transfertNn.setRestantDu(transfert.getMontant());
							} else {
								transfertNn.setMontRegle(transfert.getMontant());
								transfertNn.setRestantDu(0);
							}
							transfertDao.save(transfertNn);

							EuSms sms = new EuSms();
							long liastId = smsDao.getLastSmsInserted();
							sms.setNeng(liastId + 1);
							sms.setDateenvoi(date.toString());
							sms.setEtat(0);
							sms.setRecipient(transfert.getNumeroAcheteur());
							sms.setSmsbody("Vous venez d'effectuer un transfert " + transfert.getTypeNn() + " de "
									+ transfert.getMontant());
							smsDao.save(sms);
							if (sv_mont == 0) {
								break;
							}
						}
					} else {
						return "Vous n'avez pas emis de transfert " + transfert.getTypeNn();
					}
					rep = "L'operation a été effectuée avec succès!";
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				rep = "Erreur d'exécution :" + e.getMessage();
			}
		}
		return rep;
	}

	@Override
	public String doTransfertPre(Transfert transfert) {
		String rep = "Echec de l'opération, veuillez rééssayez!!";
		Date date = new Date();
		if (transfert != null && transfert.getMontTransfert() > 0) {
			if (transfert.getCodeCompteTransfert() == null || transfert.getCodeCompteTransfert().isEmpty()) {
				return "Il faut renseigner le compte de transfert";
			}
			if (transfert.getCodeCompteDest() == null || transfert.getCodeCompteDest().isEmpty()) {
				return "Il faut renseigner le compte de reception";
			}
			if (transfert.getTypeTransfert() != null || transfert.getTypeTransfert().isEmpty()) {
				return "Il faut renseigner le type de transfert!";
			}
			if (transfert.getNumeroAppel() == null || transfert.getNumeroAppel().isEmpty()) {
				rep = "IL faut rensiegner le Numéro d'appel d'offre";
			} else {
				EuAppelOffre offre = appelRepo.findAppelOffresByNumero(transfert.getNumeroAppel());
				if (offre != null) {
					if (offre.getTypeAppelOffre() != null && !offre.getTypeAppelOffre().equals("inrpre")) {
						String codeCatNn = ServerUtil.getTypeCompte(transfert.getCodeCompteDest());
						String codeCatTr = ServerUtil.getTypeCompte(transfert.getCodeCompteTransfert());
						if (codeCatNn.equals("TSCAPA") && codeCatTr.equals("TR")) {
							String codeCompteNn = "NN-CAPA-" + ServerUtil.getCodeMembre(transfert.getCodeCompteDest());
							EuCompte compteTr = compteDao.findOne(transfert.getCodeCompteTransfert());
							String codeMembre_dest = ServerUtil.getCodeMembre(transfert.getCodeCompteDest());
							// String codeMembre_tr =
							// ServerUtil.getCodeMembre(transfert.getCodeCompteTransfert());
							if (compteTr != null) {
								Double soldeTypenn = transfertDao.FindSoldeNnByTypeNn(compteTr.getCodeCompte(),
										transfert.getTypeTransfert().toUpperCase());
								if (soldeTypenn != null && compteTr.getSolde() >= transfert.getMontTransfert()
										&& soldeTypenn >= transfert.getMontTransfert()) {
									EuCompte comptenn = compteDao.findOne(codeCompteNn);
									if (comptenn != null) {
										List<EuTransfertNn> transferts = transfertDao.findByTypeNnAndCompte(
												transfert.getCodeCompteTransfert(),
												transfert.getTypeTransfert().toUpperCase());
										if (transferts != null && transferts.size() > 0) {
											comptenn.setSolde(comptenn.getSolde() + transfert.getMontTransfert());
											compteDao.save(comptenn);

											compteTr.setSolde(compteTr.getSolde() - transfert.getMontTransfert());
											compteDao.save(compteTr);

											formatter = new SimpleDateFormat("yyyyMMddHHmmss");
											EuCapa eucapa = new EuCapa();
											String type = "";
											if (transfert.getTypeTransfert().startsWith("RPG")) {
												type = "RGP";
											} else if (transfert.getTypeTransfert().startsWith("I")) {
												type = "I";
											} else {
												type = "CNCS";
											}
											eucapa.setCodeCapa("CAPA" + type + formatter.format(date));
											eucapa.setDateCapa(date);
											eucapa.setCodeMembre(codeMembre_dest);
											eucapa.setCodeProduit(transfert.getTypeTransfert());
											eucapa.setTypeCapa(type);
											eucapa.setOrigineCapa("TR");
											eucapa.setMontantCapa(transfert.getMontTransfert());
											eucapa.setMontantUtiliser(0);
											eucapa.setMontantSolde(transfert.getMontTransfert());
											eucapa.setEuCompte(comptenn);
											eucapa.setEtatCapa("Actif");
											eucapa.setIdOperation(null);
											eucapa.setHeureCapa(date);
											capaRepo.save(eucapa);

											double sv_mont = transfert.getMontTransfert();
											for (EuTransfertNn euTransfertNn : transferts) {
												if (euTransfertNn.getSoldeTransfert() >= sv_mont) {
													euTransfertNn.setMontVendu(euTransfertNn.getMontVendu() + sv_mont);
													euTransfertNn.setSoldeTransfert(
															euTransfertNn.getSoldeTransfert() - sv_mont);
													transfertDao.save(euTransfertNn);
													sv_mont = 0;
													break;
												} else {
													sv_mont -= euTransfertNn.getSoldeTransfert();
													euTransfertNn.setMontVendu(euTransfertNn.getMontVendu()
															+ euTransfertNn.getSoldeTransfert());
													euTransfertNn.setSoldeTransfert(0);
													transfertDao.save(euTransfertNn);
												}
											}
											rep = "L'operation a été effectuée avec succès";
										} else {
											rep = "Vous n'avez pas de transfert de type :"
													+ transfert.getTypeTransfert();
										}
									} else {
										rep = "Ce compte NN n'existe pas";
									}
								} else {
									rep = "Le solde du compte de transfert est insuffisant";
								}
							} else {
								rep = "Ce compte de transfert n'existe pas";
							}
						} else {
							rep = "Il faut un compte de transfert et un compte NN CAPA";
						}
					} else {
						rep = "Le type de l'offre d'appel n'est pas renseigné ou l'offre n'est pas un offre InrPRE";
					}
				} else {
					rep = "L'offre d'appel n'existe pas";
				}
			}
		} else {
		}
		return rep;
	}

	@Override
	public String doTransfertPreRes(Transfert transfert) {
		String rep = "Echec de l'opération, veuillez rééssayez!!";
		Date date = new Date();
		if (transfert != null) {
			String codeMembreBenef = ServerUtil.getCodeMembre(transfert.getCodeCompteDest());
			String codeMembreTr = ServerUtil.getCodeMembre(transfert.getCodeCompteTransfert());
			EuActeur acteur_tr = acteurDao.findByCodeMembre(codeMembreTr);
			if (acteur_tr == null || (acteur_tr.getTypeActeur().equalsIgnoreCase("gac_detentrice"))) {
				return "Seul la GAC détentrice peut effectuer cette opération!!";
			}
			
			if (!transfert.getCodeCompteTransfert().isEmpty() && !transfert.getCodeCompteDest().isEmpty()) {
				String codeCatNn = ServerUtil.getTypeCompte(codeMembreBenef);
				String codeCatTr = ServerUtil.getTypeCompte(codeMembreTr);
				if (codeCatNn.equals("TSCAPA") && codeCatTr.equals("TR")) {
					String codeCompteNn = "NN-CAPA-" + ServerUtil.getCodeMembre(codeMembreBenef);
					EuCompte compteTr = compteDao.findOne(transfert.getCodeCompteTransfert());
					if (compteTr != null) {
						Double soldeTypenn = transfertDao.FindSoldeNnByTypeNn(compteTr.getCodeCompte(),
								transfert.getTypeTransfert().toUpperCase());
						if (soldeTypenn != null && compteTr.getSolde() >= transfert.getMontTransfert()
								&& soldeTypenn >= transfert.getMontTransfert()) {
							EuCompte comptenn = compteDao.findOne(codeCompteNn);
							if (comptenn != null) {
								List<EuTransfertNn> transferts = transfertDao.findByTypeNnAndCompte(
										transfert.getCodeCompteTransfert(), transfert.getTypeTransfert().toUpperCase());
								if (transferts != null && transferts.size() > 0) {
									comptenn.setSolde(comptenn.getSolde() + transfert.getMontTransfert());
									compteDao.save(comptenn);

									compteTr.setSolde(compteTr.getSolde() - transfert.getMontTransfert());
									compteDao.save(compteTr);

									formatter = new SimpleDateFormat("yyyyMMddHHmmss");
									EuCapa eucapa = new EuCapa();
									String type = "";
									if (transfert.getTypeTransfert().startsWith("RPG")) {
										type = "RGP";
									} else if (transfert.getTypeTransfert().startsWith("I")) {
										type = "I";
									} else {
										type = "CNCS";
									}
									eucapa.setCodeCapa("CAPA" + type + formatter.format(date));
									eucapa.setDateCapa(date);
									eucapa.setCodeMembre(codeMembreBenef);
									eucapa.setCodeProduit(transfert.getTypeTransfert());
									eucapa.setTypeCapa(type);
									eucapa.setOrigineCapa("TR");
									eucapa.setMontantCapa(transfert.getMontTransfert());
									eucapa.setMontantUtiliser(0);
									eucapa.setMontantSolde(transfert.getMontTransfert());
									eucapa.setEuCompte(comptenn);
									eucapa.setEtatCapa("Actif");
									eucapa.setIdOperation(null);
									eucapa.setHeureCapa(date);
									capaRepo.save(eucapa);

									double sv_mont = transfert.getMontTransfert();
									for (EuTransfertNn euTransfertNn : transferts) {
										if (euTransfertNn.getSoldeTransfert() >= sv_mont) {
											euTransfertNn.setMontVendu(euTransfertNn.getMontVendu() + sv_mont);
											euTransfertNn
													.setSoldeTransfert(euTransfertNn.getSoldeTransfert() - sv_mont);
											transfertDao.save(euTransfertNn);
											sv_mont = 0;
											break;
										} else {
											sv_mont -= euTransfertNn.getSoldeTransfert();
											euTransfertNn.setMontVendu(
													euTransfertNn.getMontVendu() + euTransfertNn.getSoldeTransfert());
											euTransfertNn.setSoldeTransfert(0);
											transfertDao.save(euTransfertNn);
										}
									}
									rep = "L'operation a été effectuée avec succès";
								} else {
									rep = "Vous n'avez pas de transfert de type :" + transfert.getTypeTransfert();
								}
							} else {
								rep = "Ce compte NN n'existe pas";
							}
						} else {
							rep = "Le solde du compte de transfert est insuffisant";
						}
					} else {
						rep = "Ce compte de transfert n'existe pas";
					}
				} else {
					rep = "Il faut un compte de transfert et un compte NN CAPA";
				}
			} else {
				rep = "Renseignez le Compte TR et le Compte NN";
			}
		} else {
		}
		return rep;
	}

	/**
	 * Methode de tansfert de NN pour les transferts des unités CMFH des PBF
	 * Détaillants
	 *
	 * @param transfert
	 *            Infos sur le transfert à effectuer; objet venant du client
	 * @return String chaines de caractères expliquant l'éxécution de l'opération
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String doTransfertCmfh(TransfertSMS transfert) {
		String response = "echec de l'opération";
		String categorie_v = ServerUtil.getTypeCompte(transfert.getCodeCompteVendeur());
		String type_nv = ServerUtil.getTypeNumerique(transfert.getCodeCompteVendeur());
		String codeMembre_v = ServerUtil.getCodeMembre(transfert.getCodeCompteVendeur());
		String codeMembre_a = ServerUtil.getCodeMembre(transfert.getCodeCompteAcheteur());
		// verification du compte de transfert
		if (type_nv.equalsIgnoreCase("NN") && categorie_v.equals("TR")) {
			if (!transfert.getTypeNn().equalsIgnoreCase("FS") && !transfert.getTypeNn().equalsIgnoreCase("FL")
					&& !transfert.getTypeNn().equalsIgnoreCase("FCPS")
					&& !transfert.getTypeNn().equalsIgnoreCase("CAPS")) {
				return "Seules les unités FS, FL, FCPS ou CAPS peuvent être vendues aux CMFH!";
			}

			EuCompte compteMarge = null;
			EuMembreMorale surveillance = null;
			String codeCompteMarge = null;
			EuActeur acteur_v = null;
			EuGac gac_surv = null;

			if (!Objects.nonNull(cmfhRepo.findByCodeMembre(codeMembre_a))) {
				return "Ce CMFH  n° " + codeMembre_a + " n'existe";
			} else {
				if (!transfert.getTypeNn().equals("FS") && !transfert.getTypeNn().equals("FL")
						&& !transfert.getTypeNn().equals("FCPS") && !transfert.getTypeNn().equals("MF11000")
						&& !transfert.getTypeNn().equals("MF107")) {
					return "Veuillez choisir un type numérique pour ls CMFH";
				}
				acteur_v = acteurDao.findByCodeMembre(codeMembre_v);
				if (acteur_v != null) {
					if (!acteur_v.getTypeActeur().equalsIgnoreCase("PBF")
							&& !acteur_v.getTypeActeur().equalsIgnoreCase("DSMS")) {
						return "Seuls les PBF et les cybers Marchés peuvent vendre en détails!";
					}
					if (acteur_v.getTypeActeur().equalsIgnoreCase("PBF")
							&& !acteur_v.getCodeActivite().equalsIgnoreCase("detaillant")) {
						return "Seuls les PBFS detaillants peuvent vendre en détails";
					}
					gac_surv = gacDao.findOne(acteur_v.getEuGac().getCodeGac());
					if (gac_surv != null
							&& gac_surv.getEuTypeGac().getCodeTypeGac().equalsIgnoreCase("gac_surveillance")) {
						codeCompteMarge = "NN-TMARGE-" + gac_surv.getEuMembreMorale().getCodeMembreMorale();
						compteMarge = compteDao.findOne(codeCompteMarge);
						surveillance = gac_surv.getEuMembreMorale();
					}
				} else {
					return "Cet acteur n'existe pas";
				}

				double mont_transfert = transfert.getMontant();
				if (transfert.getTypeNn().startsWith("MF")) {
					if (transfert.getTypeNn().startsWith("MF1") && !(mont_transfert % 70000 == 0)) {
						return "Le transfert MF11000 et MF107 doit être un multiple de 70000";
					}
					double quotaMF = paramService.getParametre("quotaMF", "valeur");
					if (mont_transfert / 70000 > quotaMF) {
						return "Vous ne pouvez vendre MF depassant " + quotaMF * 70000;
					}
				}
				if (transfert.getTypeNn().equals("FS") && !(mont_transfert % 50000 == 0)) {
					return "Le transfert FS doit être un multiple de 50000";
				}
				if (transfert.getTypeNn().equals("FL") && !(mont_transfert % 10000 == 0)) {
					return "Le transfert FL doit être un multiple de 10000";
				}
				if (transfert.getTypeNn().equals("FCPS") && !(mont_transfert % 10000 == 0)) {
					return "Le transfert FCPS doit être un multiple de 10000";
				}

				EuCompte compteTransfert = compteDao.findOne(transfert.getCodeCompteVendeur());
				if (compteTransfert != null) {

					Double soldeByTypeNn = 0.0;
					soldeByTypeNn = detSmsmoneyDao.findSumByTypeNnAndMembre(
							compteTransfert.getEuMembreMorale().getCodeMembreMorale(), transfert.getTypeNn());
					if (soldeByTypeNn >= transfert.getMontant()) {
						Double soldeDetSms;
						List<EuDetailSmsmoney> detSmsmoneys;
						Long lastDetVteId;
						try {
							soldeDetSms = detSmsmoneyDao
									.findSumByMembre(ServerUtil.getCodeMembre(compteTransfert.getCodeCompte()));
							System.out.println("Récupération des détails SMSMoney :" + soldeDetSms);
							detSmsmoneys = detSmsmoneyDao.findByTypeNnAndMembre(
									ServerUtil.getCodeMembre(transfert.getCodeCompteVendeur()), transfert.getTypeNn());
							if (detSmsmoneys == null || detSmsmoneys.isEmpty()) {
								return "Vous n'avez pas de transfert de type " + transfert.getTypeNn();
							}
						} catch (Exception e) {
							return "Vous n'avez pas de transferts.\n Veuillez vérifier votre compte de transfert";
						}

						if (soldeDetSms != null && soldeDetSms == compteTransfert.getSolde()) {
							Date date = new Date();
							try {
								double tx_nnmarge = paramService.getParametre("NNMARGE", "valeur");
								double mont_marge = transfert.getMontant() * tx_nnmarge / 100;

								compteTransfert.setSolde(compteTransfert.getSolde() - transfert.getMontant());
								compteDao.save(compteTransfert);

								int i = 0;
								while (mont_transfert > 0) {
									lastDetVteId = detVteSmsDao.getLastDetVenteSmsmoneyInsertedId();
									if (lastDetVteId == null) {
										lastDetVteId = 0L;
									}
									EuDetailSmsmoney detSmsMoney = detSmsmoneys.get(i);
									if (detSmsMoney.getSoldeSms() >= mont_transfert) {
										lastDetVteId += 1;
										EuDetailVentesms detVentesms = new EuDetailVentesms();
										detVentesms.setIdDetailVtsms(lastDetVteId);
										detVentesms.setEuDetailSmsmoney(detSmsMoney);
										detVentesms.setEuMembreMorale(compteTransfert.getEuMembreMorale());
										detVentesms.setCreditcode("CMFH-" + codeMembre_a);
										detVentesms.setMontVente(mont_transfert);
										detVentesms.setCodeProduit(transfert.getTypeNn());
										detVentesms.setTypeTransfert(transfert.getTypeNn());
										detVentesms.setDateVente(date);
										detVteSmsDao.save(detVentesms);

										detSmsMoney.setMontVendu(detSmsMoney.getMontVendu() + mont_transfert);
										detSmsMoney.setSoldeSms(detSmsMoney.getSoldeSms() - mont_transfert);
										detSmsmoneyDao.save(detSmsMoney);

										mont_transfert = 0;
									} else {
										lastDetVteId += 1;
										mont_transfert = mont_transfert - detSmsMoney.getSoldeSms();
										EuDetailVentesms detVentesms = new EuDetailVentesms();
										detVentesms.setIdDetailVtsms(lastDetVteId);
										detVentesms.setEuDetailSmsmoney(detSmsMoney);
										detVentesms.setEuMembreMorale(compteTransfert.getEuMembreMorale());
										detVentesms.setCreditcode("CMFH-" + codeMembre_a);
										detVentesms.setMontVente(detSmsMoney.getSoldeSms());
										detVentesms.setTypeTransfert(transfert.getTypeNn());
										detVentesms.setDateVente(date);
										detVentesms.setCodeProduit(transfert.getTypeNn());
										detVteSmsDao.save(detVentesms);

										detSmsMoney
												.setMontVendu(detSmsMoney.getMontVendu() + detSmsMoney.getSoldeSms());
										detSmsMoney.setSoldeSms(0);
										detSmsmoneyDao.save(detSmsMoney);
										i++;
									}
								}

								nnService.EmettreNn("NNMARGE", mont_marge, date);
								if (compteMarge != null) {
									System.out.println("Mise à jour de la marge");
									compteMarge.setSolde(compteMarge.getSolde() + mont_marge);
									compteDao.save(compteMarge);
								} else {
									EuCategorieCompte cat = new EuCategorieCompte();
									cat.setCodeCat("TMARGE");
									EuTypeCompte typeCompte = new EuTypeCompte();
									typeCompte.setCodeTypeCompte("NN");
									compteMarge = new EuCompte();
									compteMarge.setCodeCompte(codeCompteMarge);
									compteMarge.setEuCategorieCompte(cat);
									compteMarge.setEuTypeCompte(typeCompte);
									compteMarge.setEuMembre(null);
									compteMarge.setEuMembreMorale(surveillance);
									compteMarge.setDesactiver("N");
									compteMarge.setSolde(mont_marge);
									compteMarge.setDateAlloc(date);
									compteMarge.setCardprinteddate(null);
									compteMarge.setCardprintediddate(0);
									compteMarge.setLibCompte("NN Marge");
									compteDao.save(compteMarge);
								}

								String codeCompte_a = "NN-TRE-" + codeMembre_a;
								EuCompte compte_a = compteDao.findCompteById(codeCompte_a);
								if (Objects.nonNull(compte_a)) {
									compte_a.setSolde(compte_a.getSolde() + transfert.getMontant());
								} else {
									EuMembre membre_a = new EuMembre();
									membre_a.setCodeMembre(codeMembre_a);
									EuCategorieCompte catCompte = new EuCategorieCompte();
									catCompte.setCodeCat("TRE");
									EuTypeCompte typeCompte = new EuTypeCompte();
									typeCompte.setCodeTypeCompte("NN");

									compte_a = new EuCompte();
									compte_a.setCodeCompte(codeCompte_a);
									compte_a.setEuMembre(membre_a);
									compte_a.setEuCategorieCompte(catCompte);
									compte_a.setEuTypeCompte(typeCompte);
									compte_a.setCardprinteddate(null);
									compte_a.setCardprintediddate(0);
									compte_a.setDateAlloc(new Date());
									compte_a.setDesactiver("N");
									compte_a.setEuMembreMorale(null);
									compte_a.setLibCompte("Compte de transfert d'exploitation");
									compte_a.setMifarecard(null);
									compte_a.setNumeroCarte(null);
									compte_a.setSolde(transfert.getMontant());
								}
								compteDao.save(compte_a);

								EuSms sms = new EuSms();
								Long liastId = smsDao.getLastSmsInserted();
								if (liastId == null) {
									liastId = 0L;
								}
								sms.setNeng(liastId + 1);
								sms.setDateenvoi(date.toString());
								sms.setEtat(0);
								sms.setRecipient(transfert.getNumeroAcheteur());
								sms.setSmsbody("Vous venez d'effectuer un transfert " + transfert.getTypeNn() + " de "
										+ transfert.getMontant());
								smsDao.save(sms);
							} catch (Exception e) {
								return "Erreur d'exécution : Sauvegarde du transfert non éffectuée :\n"
										+ e.getLocalizedMessage();
							}
							SMSUtil.insertSms(transfert.getNumeroAcheteur(), "Vous venez d'effectuer un transfert "
									+ transfert.getTypeNn() + " de " + transfert.getMontant());
							response = "OK";
						} else {
							response = "Votre compte de transfert n'est pas correct.\n Veuillez contacter l'ACNEV de contrôle";
						}
					} else {
						response = "Le solde de votre compte de transfert est insuffisant\n pour effectuer cette op�raation";
					}
				} else {
					response = "Le compte de transfert n'existe pas!!!";
				}
			}
		} else {
			response = "Le compte du transfert n'est pas valide!!!";
		}
		return response;
	}

	@Override
	public String doTransfertDetail(String type, double montant)
			throws DataAccessException, CompteNonIntegreException, CompteNonTrouveException, SoldeInsuffisantException {

		String codeMembreSource = "0000000000000000001M";
		String codeCompteTR = "NN-TR-" + codeMembreSource;
		if (type.equals("CAPA")) {
			codeMembreSource = "0000000000000000002M";
			codeCompteTR = "NN-TR-" + codeMembreSource;
		}

		double mont_transfert = montant;

		EuCompte compteTransfert = compteDao.findOne(codeCompteTR);
		if (Objects.nonNull(compteTransfert)) {
			Double soldeByTypeNn = 0.0;
			soldeByTypeNn = detSmsmoneyDao.findSumByTypeNnAndMembre(codeMembreSource, type);
			if (soldeByTypeNn >= montant) {
				Double soldeDetSms;
				List<EuDetailSmsmoney> detSmsmoneys;
				Long lastDetVteId;
				soldeDetSms = detSmsmoneyDao.findSumByMembre(ServerUtil.getCodeMembre(compteTransfert.getCodeCompte()));

				detSmsmoneys = detSmsmoneyDao.findByTypeNnAndMembre(codeMembreSource, type);
				if (detSmsmoneys == null || detSmsmoneys.isEmpty()) {
					return null;
				}
				String codeSMS = StringUtils.EMPTY;
				if (soldeDetSms != null && soldeDetSms == compteTransfert.getSolde()) {
					Date date = new Date();
					codeSMS = ServerUtil.GenererUniqueCode();

					long smsMoneyId = smsMoneyDao.getLastInsertId();
					compteTransfert.setSolde(compteTransfert.getSolde() - montant);
					compteDao.save(compteTransfert);

					EuSmsmoney smsMoney = new EuSmsmoney();
					smsMoney.setNeng(smsMoneyId + 1);
					smsMoney.setCreditamount(montant);
					smsMoney.setSentto(StringUtils.EMPTY);
					smsMoney.setFromaccount(codeCompteTR);
					smsMoney.setMotif(type);
					smsMoney.setDatetime(ServerUtil.formatDate(date));
					smsMoney.setCurrencycode("XOF");
					smsMoney.setCreditcode(codeSMS);
					smsMoney.setIddatetimeconsumed(0);
					smsMoneyDao.save(smsMoney);

					int i = 0;
					while (mont_transfert > 0) {
						lastDetVteId = detVteSmsDao.getLastDetVenteSmsmoneyInsertedId();
						if (lastDetVteId == null) {
							lastDetVteId = 0L;
						}
						EuDetailSmsmoney detSmsMoney = detSmsmoneys.get(i);
						if (detSmsMoney.getSoldeSms() >= mont_transfert) {
							lastDetVteId += 1;
							EuDetailVentesms detVentesms = new EuDetailVentesms();
							detVentesms.setIdDetailVtsms(lastDetVteId);
							detVentesms.setEuDetailSmsmoney(detSmsMoney);
							detVentesms.setEuMembreMorale(compteTransfert.getEuMembreMorale());
							detVentesms.setCreditcode(smsMoney.getCreditcode());
							detVentesms.setMontVente(mont_transfert);
							detVentesms.setCodeProduit(type);
							detVentesms.setTypeTransfert(type);
							detVentesms.setDateVente(date);
							detVteSmsDao.save(detVentesms);

							detSmsMoney.setMontVendu(detSmsMoney.getMontVendu() + mont_transfert);
							detSmsMoney.setSoldeSms(detSmsMoney.getSoldeSms() - mont_transfert);
							detSmsmoneyDao.save(detSmsMoney);

							mont_transfert = 0;
						} else {
							lastDetVteId += 1;
							mont_transfert = mont_transfert - detSmsMoney.getSoldeSms();
							EuDetailVentesms detVentesms = new EuDetailVentesms();
							detVentesms.setIdDetailVtsms(lastDetVteId);
							detVentesms.setEuDetailSmsmoney(detSmsMoney);
							detVentesms.setEuMembreMorale(compteTransfert.getEuMembreMorale());
							detVentesms.setCreditcode(smsMoney.getCreditcode());
							detVentesms.setMontVente(detSmsMoney.getSoldeSms());
							detVentesms.setTypeTransfert(type);
							detVentesms.setDateVente(date);
							detVentesms.setCodeProduit(type);
							detVteSmsDao.save(detVentesms);

							detSmsMoney.setMontVendu(detSmsMoney.getMontVendu() + detSmsMoney.getSoldeSms());
							detSmsMoney.setSoldeSms(0);
							detSmsmoneyDao.save(detSmsMoney);
							i++;
						}
					}

					EuSms sms = new EuSms();
					Long liastId = smsDao.getLastSmsInserted();
					if (liastId == null) {
						liastId = 0L;
					}
					sms.setNeng(liastId + 1);
					sms.setDateenvoi(date.toString());
					sms.setEtat(0);
					sms.setRecipient(StringUtils.EMPTY);
					sms.setSmsbody(
							"Vous venez d'effectuer un transfert " + type + " de " + montant + " Code :" + codeSMS);
					smsDao.save(sms);

					return codeSMS;
				} else {
					throw new CompteNonIntegreException("compte incoherent, le solde du compte de transfert = "
							+ compteTransfert.getSolde() + " est différent de la somme des détails : " + soldeDetSms);
				}
			} else {
				throw new SoldeInsuffisantException(
						"Le solde du compte de transfert " + compteTransfert.getCodeCompte() + " est insuffisant");
			}
		} else {
			throw new CompteNonTrouveException(
					"Ce compte de transfert " + codeCompteTR + " est introuvable");
		}
	}

}

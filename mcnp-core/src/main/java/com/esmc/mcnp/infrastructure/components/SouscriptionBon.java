package com.esmc.mcnp.components;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dto.bc.CalculBonInfo;
import com.esmc.mcnp.dto.other.Bnp;
import com.esmc.mcnp.exception.CompteNonIntegreException;
import com.esmc.mcnp.exception.CompteNonTrouveException;
import com.esmc.mcnp.exception.SoldeInsuffisantException;
import com.esmc.mcnp.model.ba.EuCapa;
import com.esmc.mcnp.model.ba.EuCapaTs;
import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.bc.EuCycleFormation;
import com.esmc.mcnp.model.bc.EuProduit;
import com.esmc.mcnp.model.cm.EuCategorieCompte;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.cm.EuTypeCompte;
import com.esmc.mcnp.model.obpsd.EuBonNeutre;
import com.esmc.mcnp.model.obpsd.EuSmsmoney;
import com.esmc.mcnp.model.odd.EuMstier;
import com.esmc.mcnp.model.others.EuOperation;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.services.ba.EuBonNeutreDetailService;
import com.esmc.mcnp.services.ba.EuBonNeutreService;
import com.esmc.mcnp.services.ba.EuCapaService;
import com.esmc.mcnp.services.ba.EuCapaTsService;
import com.esmc.mcnp.services.bc.EuBonService;
import com.esmc.mcnp.services.cm.EuCompteCreditService;
import com.esmc.mcnp.services.cm.EuCompteService;
import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.cm.EuMembreService;
import com.esmc.mcnp.services.common.EuCycleFormationService;
import com.esmc.mcnp.services.common.EuOperationService;
import com.esmc.mcnp.services.obpsd.EuSmsmoneyService;
import com.esmc.mcnp.services.oi.EuMstiersService;

@Component("sousciptionBon")
@Transactional
public class SouscriptionBon {

	private final EuCompteService compteService;
	private final EuSmsmoneyService smsmoneyService;
	private final EuBonNeutreService bonNeutreService;
	private final TransfertService transfertService;
	private final EuCapaService capaService;
	private final EuCapaTsService capaTsService;
	private final BonConsoComponent bcComponent;
	private final TransfertUtility transfertUtility;
	private final BnpComponent bnpService;
	private final EuBonService bonService;
	private final EuMembreService membreService;
	private final EuBonNeutreDetailService bnDetService;
	private final EuCycleFormationService cycleFormService;
	private final CreditComponent creditService;
	private final EuCompteCreditService ccService;
	private final EuOperationService opService;
	private final EuMembreMoraleService moraleService;
	private final EuMstiersService mstierService;

	public SouscriptionBon(EuCompteService compteService, EuSmsmoneyService smsmoneyService,
			EuBonNeutreService bonNeutreService, TransfertService transfertService, EuCapaService capaService,
			EuCapaTsService capaTsService, BonConsoComponent bcComponent, TransfertUtility transfertUtility,
			BnpComponent bnpService, EuBonService bonService, EuMembreService membreService,
			EuBonNeutreDetailService bnDetService, EuCycleFormationService cycleFormService,
			CreditComponent creditService, EuCompteCreditService ccService, EuOperationService opService,
			EuMembreMoraleService moraleService, EuMstiersService mstierService) {
		this.compteService = compteService;
		this.smsmoneyService = smsmoneyService;
		this.bonNeutreService = bonNeutreService;
		this.transfertService = transfertService;
		this.capaService = capaService;
		this.capaTsService = capaTsService;
		this.bcComponent = bcComponent;
		this.transfertUtility = transfertUtility;
		this.bnpService = bnpService;
		this.bonService = bonService;
		this.membreService = membreService;
		this.bnDetService = bnDetService;
		this.cycleFormService = cycleFormService;
		this.creditService = creditService;
		this.ccService = ccService;
		this.opService = opService;
		this.moraleService = moraleService;
		this.mstierService = mstierService;
	}

	public int souscrireBonSalaire(String codeMembre, CalculBonInfo bonInfo, String codeBa, double montant,
			EuUtilisateur user) throws CompteNonIntegreException, CompteNonTrouveException, SoldeInsuffisantException,
			DataAccessException, NullPointerException {
		String typeBon = "CNCS";
		String codeCompte = "NR-TPN-" + codeMembre;
		String codeCompteNn = "NN-TSCAPA-" + codeMembre;
		List<EuCapaTs> capatss = capaTsService.findByEuBon_BonNumero(codeBa);
		EuCompte compte = compteService.getById(codeCompte);
		EuCompte compteNn = compteService.getById(codeCompteNn);
		boolean result = bcComponent.souscrireCncs(codeMembre, compte, compteNn, codeBa, capatss, typeBon, bonInfo,
				montant, user);
		if (result) {
			return 0;
		} else {
			return 1;
		}
	}

	public int souscrireBonConso(String codeMembre, CalculBonInfo bonInfo, String codeBa, double montant)
			throws CompteNonIntegreException, CompteNonTrouveException, SoldeInsuffisantException, DataAccessException,
			NullPointerException {
		List<EuCapaTs> capatss = capaTsService.findByEuBon_BonNumero(codeBa);
		String typeBon = "RPG";
		if (codeMembre.endsWith("M")) {
			typeBon = "I";
		}
		String codeCompte = "NB-TPAGC" + typeBon + "-" + codeMembre;
		String codeCompteNn = "NN-TSCAPA-" + codeMembre;
		EuCompte compte = compteService.getById(codeCompte);
		EuCompte compteNn = compteService.getById(codeCompteNn);
		if (codeMembre.endsWith("M")) {
			boolean result = bcComponent.souscrireI(codeMembre, compte, compteNn, codeBa, capatss, typeBon, bonInfo,
					montant);
			if (result) {
				return 0;
			} else {
				return 1;
			}
		} else {
			boolean result = bcComponent.souscrireRpg(codeMembre, compte, compteNn, codeBa, typeBon, bonInfo, montant);
			if (result) {
				return 0;
			} else {
				return 1;
			}
		}
	}

	public int souscrirePourTiers(Bnp bnp) {
		if (StringUtils.isNotBlank(bnp.getCodeMembreApp()) && StringUtils.isNotBlank(bnp.getCodeMembreBenef())
				&& StringUtils.isNotBlank(bnp.getTypeBnp()) && bnp.getMontBnp() > 0) {
			String codeCompteCapa = "NN-CAPA-" + bnp.getCodeMembreBenef();
			Date date = new Date();
			EuBonNeutre bonNeutre;
			if (StringUtils.isNotBlank(bnp.getCodeBnp())) {
				bonNeutre = bonNeutreService.findByCode(bnp.getCodeBnp());
			} else {
				bonNeutre = bonNeutreService.findByMembre(bnp.getCodeMembreApp());
			}
			if (Objects.nonNull(bonNeutre) && bonNeutre.getBonNeutreMontantSolde() >= bnp.getMontBnp()) {
				String codeSMS = transfertService.doTransfertDetail("CAPA", bnp.getMontBnp());
				if (StringUtils.isNotBlank(codeSMS)) {
					EuSmsmoney smsmoney = smsmoneyService.findByCreditcode(codeSMS);
					if (Objects.nonNull(smsmoney)) {
						bonNeutreService.updateBonNeutre(bonNeutre,
								"Souscription pour Tiers N° " + bnp.getCodeMembreBenef(), "BA", smsmoney,
								bnp.getMontBnp());
						EuCompte comptecapa = compteService.getById(codeCompteCapa);
						comptecapa.setSolde(comptecapa.getSolde() + bnp.getMontBnp());
						compteService.update(comptecapa);

						EuCapa eucapa = new EuCapa();
						eucapa.setCodeCapa("CAPA" + ServerUtil.formatDate2(date));
						eucapa.setDateCapa(date);
						eucapa.setCodeMembre(bnp.getCodeMembreBenef());
						eucapa.setCodeProduit("RPG");
						eucapa.setTypeCapa("RPG");
						eucapa.setOrigineCapa("BAN");
						eucapa.setMontantCapa(bnp.getMontBnp());
						eucapa.setMontantUtiliser(0);
						eucapa.setMontantSolde(bnp.getMontBnp());
						eucapa.setEuCompte(comptecapa);
						eucapa.setEtatCapa("Actif");
						eucapa.setIdOperation(null);
						eucapa.setHeureCapa(date);
						capaService.add(eucapa);

						smsmoney.setDestaccount(codeCompteCapa);
						smsmoney.setDatetimeconsumed(ServerUtil.formatDate(date));
						smsmoney.setIddatetimeconsumed((int) date.getTime());
						smsmoneyService.update(smsmoney);

						EuBon bon = transfertUtility.transfertBA(bnp.getCodeMembreBenef(), "BAN", "RPG",
								bnp.getMontBnp());
						if (Objects.nonNull(bon)) {
							bnp.setModeApa("RPGr");
							CalculBonInfo bonInfo = new CalculBonInfo("r", bnp.getTypeRecurrent(), "RPGr", 22.4, 1);
							bonInfo.setTypeCredit(bnp.getTypeProduit());
							List<EuCapaTs> capatss = capaTsService.findByEuBon_BonNumero(bon.getBonNumero());
							if (bnpService.doBnp(bnp, bonInfo, capatss, bon)) {
								bon.setBonDateExpression(date);
								bon.setBonExprimer(1);
								bonService.update(bon);
							}
						}
					}
				}
				return 0;
			}
		}
		return -1;
	}

	public int souscrirePourTiers(String codeMembreBenef, String codeMembreApp, String typeBnp, double montant) {
		if (StringUtils.isNotBlank(codeMembreBenef) && StringUtils.isNotBlank(typeBnp) && montant > 0) {
			String codeCompteCapa = "NN-CAPA-" + codeMembreBenef;
			Date date = new Date();
			List<EuMstier> mstiers;
			Double solde;
			if (StringUtils.isNotBlank(codeMembreApp)) {
				solde = mstierService.getSumByMembreAndStatut(codeMembreApp, "AvecListe");
				mstiers = mstierService.findByMembreAndStatut(codeMembreApp, "AvecListe");
			} else {
				solde = mstierService.getSumByStatut("SansListe");
				mstiers = mstierService.findByStatut("SansListe");
			}

			if (solde >= montant && mstiers.size() > 0) {
				String codeSMS = transfertService.doTransfertDetail("CAPA", montant);
				if (StringUtils.isNotBlank(codeSMS)) {
					EuSmsmoney smsmoney = smsmoneyService.findByCreditcode(codeSMS);
					if (Objects.nonNull(smsmoney)) {

						EuCompte comptecapa = compteService.getById(codeCompteCapa);
						comptecapa.setSolde(comptecapa.getSolde() + montant);
						compteService.update(comptecapa);

						EuCapa eucapa = new EuCapa();
						eucapa.setCodeCapa("CAPA" + ServerUtil.formatDate2(date));
						eucapa.setDateCapa(date);
						eucapa.setCodeMembre(codeMembreBenef);
						eucapa.setCodeProduit("RPG");
						eucapa.setTypeCapa("RPG");
						eucapa.setOrigineCapa("BAN");
						eucapa.setMontantCapa(montant);
						eucapa.setMontantUtiliser(0);
						eucapa.setMontantSolde(montant);
						eucapa.setEuCompte(comptecapa);
						eucapa.setEtatCapa("Actif");
						eucapa.setIdOperation(null);
						eucapa.setHeureCapa(date);
						capaService.add(eucapa);

						smsmoney.setDestaccount(codeCompteCapa);
						smsmoney.setDatetimeconsumed(ServerUtil.formatDate(date));
						smsmoney.setIddatetimeconsumed((int) date.getTime());
						smsmoneyService.update(smsmoney);

						EuBon bon = transfertUtility.transfertBA(codeMembreBenef, "BAN", "RPG", montant);
						if (Objects.nonNull(bon)) {
							CalculBonInfo bonInfo = new CalculBonInfo("r", "illimite", "RPGr", 22.4, 1);
							bonInfo.setTypeCredit("PS");
							Bnp bnp = new Bnp();
							bnp.setCodeBnp("");
							bnp.setTypeBnp(typeBnp);
							bnp.setCodeMembreApp(codeMembreApp);
							bnp.setCodeMembreBenef(codeMembreBenef);
							bnp.setMontBnp(montant);
							bnp.setModeApa("RPGr");
							List<EuCapaTs> capatss = capaTsService.findByEuBon_BonNumero(bon.getBonNumero());
							if (bnpService.doBnp(bnp, bonInfo, mstiers, capatss, bon)) {
								bon.setBonDateExpression(date);
								bon.setBonExprimer(1);
								bonService.update(bon);
							}
						}
					}
				}
				return 0;
			}
		}
		return -1;
	}

	@Transactional
	public void souscrireFormation(String codeMembre, String catBon, String typeProduit, String typeRecurrent,
			String cycle, double montFormation, String numBon)
			throws NullPointerException, CompteNonTrouveException, IllegalArgumentException {
		EuMembreMorale morale = null;
		EuMembre membre = null;
		if (codeMembre.endsWith("P")) {
			membre = membreService.findById(codeMembre);
			if (Objects.isNull(membre)) {
				throw new CompteNonTrouveException("Ce membre N° " + codeMembre + " n'existe pas");
			}
		} else {
			morale = moraleService.findById(codeMembre);
			if (Objects.isNull(morale)) {
				throw new CompteNonTrouveException("Ce membre N° " + codeMembre + " n'existe pas");
			}
		}

		String typeCapa = "RPG";
		String codeProduit = "RPGr";
		String codeCat = "TPAGCRPG";
		if (codeMembre.endsWith("M")) {
			typeCapa = "I";
			codeProduit = "Ir";
			codeCat = "TPAGCI";
		}

		String codeCompte = "NB-" + codeCat + "-" + codeMembre;
		if (!ccService.verifierSolde(codeCompte)) {
			throw new CompteNonIntegreException("Ce compte n'est pas correct. Veuillez contacter ESMC!");
		}

		EuCycleFormation cycleForm = cycleFormService.findByCodeformation(cycle);
		if (Objects.nonNull(cycleForm)) {
			double montBonConso = Math.floor(montFormation / cycleForm.getDureeAnneeFormation());
			double montSouscription = Math.floor(montBonConso * cycleForm.getTauxCycleFormation());
			EuBonNeutre bn;
			if (StringUtils.isNotBlank(numBon)) {
				bn = bonNeutreService.findByCode(numBon);
			} else {
				bn = bonNeutreService.findByMembre(codeMembre);
			}

			if (Objects.isNull(bn) || bn.getBonNeutreMontantSolde() < montSouscription) {
				throw new SoldeInsuffisantException(
						"Ce membre ne dispose pas de Bon de Neutre  : " + bn.getBonNeutreMontantSolde()
								+ " suffisant pour effectuer cette opération de :" + montSouscription);
			}

			double soldeDet = bnDetService.getSoldeByBonNeutreId(bn.getBonNeutreId());
			if (!bn.getBonNeutreMontantSolde().equals(soldeDet)) {
				throw new CompteNonIntegreException("Ce compte de Bon Neutre est incohérent => Somme details : "
						+ soldeDet + " solde Bon Neutre : " + bn.getBonNeutreMontantSolde());
			}

			String codeSMS = transfertService.doTransfertDetail("CAPA", montSouscription);
			if (StringUtils.isNotBlank(codeSMS)) {
				EuSmsmoney smsmoney = smsmoneyService.findByCreditcode(codeSMS);
				if (Objects.nonNull(smsmoney)) {
					bonNeutreService.updateBonNeutre(bn, "Souscription pour Soi Formation " + codeMembre, "BA",
							smsmoney, montSouscription);

					String codeCompteCapa = "NN-CAPA-" + codeMembre;
					Date date = new Date();
					EuCompte comptecapa = compteService.getById(codeCompteCapa);
					if (Objects.isNull(comptecapa)) {
						comptecapa = new EuCompte();
						EuCategorieCompte cat = new EuCategorieCompte();
						cat.setCodeCat(codeCat);
						EuTypeCompte typeCompte = new EuTypeCompte();
						typeCompte.setCodeTypeCompte("NN");
						comptecapa.setCodeCompte(codeCompteCapa);
						comptecapa.setEuCategorieCompte(cat);
						comptecapa.setEuTypeCompte(typeCompte);
						if (Objects.nonNull(morale)) {
							comptecapa.setEuMembreMorale(morale);
							comptecapa.setEuMembre(null);
						} else {
							comptecapa.setEuMembre(membre);
							comptecapa.setEuMembreMorale(null);
						}
						comptecapa.setLibCompte("Compte CAPA");
						comptecapa.setDesactiver("N");
						comptecapa.setSolde(montSouscription);
						comptecapa.setDateAlloc(date);
						comptecapa.setCardprinteddate(null);
						comptecapa.setCardprintediddate(0);
						comptecapa.setMifarecard(null);
						comptecapa.setNumeroCarte(null);
						compteService.update(comptecapa);
					} else {
						comptecapa.setSolde(comptecapa.getSolde() + montSouscription);
						compteService.update(comptecapa);
					}

					EuCapa eucapa = new EuCapa();
					eucapa.setCodeCapa("CAPA" + ServerUtil.formatDate2(date));
					eucapa.setDateCapa(date);
					eucapa.setCodeMembre(codeMembre);
					eucapa.setCodeProduit(codeProduit);
					eucapa.setTypeCapa(typeCapa);
					eucapa.setOrigineCapa("BAN");
					eucapa.setMontantCapa(montSouscription);
					eucapa.setMontantUtiliser(0);
					eucapa.setMontantSolde(montSouscription);
					eucapa.setEuCompte(comptecapa);
					eucapa.setEtatCapa("Actif");
					eucapa.setIdOperation(null);
					eucapa.setHeureCapa(date);
					capaService.add(eucapa);

					smsmoney.setDestaccount(codeCompteCapa);
					smsmoney.setDatetimeconsumed(ServerUtil.formatDate(date));
					smsmoney.setIddatetimeconsumed((int) date.getTime());
					smsmoneyService.update(smsmoney);

					EuBon bon = transfertUtility.transfertBA(codeMembre, "BAN", codeProduit, montSouscription);
					if (Objects.nonNull(bon)) {

						List<EuCapaTs> capatss = capaTsService.findByEuBon_BonNumero(bon.getBonNumero());
						if (capatss.size() == 0) {
							throw new IllegalStateException("Erreur de création Bon d'achat Formation");
						}

						String codeCompteNn = "NN-TSCAPA-" + codeMembre;
						EuCompte compteNn = compteService.getById(codeCompteNn);
						EuCompte compte = compteService.getById(codeCompte);

						EuCategorieCompte catCompte = new EuCategorieCompte();
						catCompte.setCodeCat(codeCat);
						Long idOp = opService.getLastOperation() + 1;

						EuOperation op = new EuOperation();
						op.setEuCategorieCompte(catCompte);
						op.setCodeProduit(codeProduit);
						op.setDateOp(date);
						op.setHeureOp(date);
						op.setLibOp("CAPA " + typeCapa);
						op.setTypeOp("APA");
						op.setMontantOp(montSouscription);
						op.setEuMembre(membre);
						op.setIdOperation(idOp);
						opService.add(op);

						compte.setSolde(compte.getSolde() + montBonConso);
						compteService.update(compte);

						EuProduit produit = new EuProduit();
						produit.setCodeProduit(codeProduit);
						String c_source = null;
						c_source = compteNn.getCodeCompte();
						CalculBonInfo bonInfo = new CalculBonInfo(catBon, typeRecurrent, typeProduit,
								cycleForm.getDureeAnneeFormation(), 1);
						EuCompteCredit cc = creditService.createCredit(compte, produit, montSouscription, montBonConso,
								false, null, op, c_source, membre.getCodeMembre(), bonInfo);

						creditService.updateListCapaTs(capatss, cc, montSouscription, date, bonInfo.getCatBon());

						compteNn.setSolde(compteNn.getSolde() - montSouscription);
						compteService.update(compteNn);

						bon.setBonDateExpression(date);
						bon.setBonExprimer(1);
						bonService.update(bon);
					}
				}
			}
		} else {
			throw new IllegalArgumentException(
					"Ce cycle de formation n'est pas disponible. Veuillez contacter les admins.");
		}
	}
}

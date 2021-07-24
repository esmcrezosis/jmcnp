/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.components;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dto.bc.CalculBonInfo;
import com.esmc.mcnp.dto.other.Bnp;
import com.esmc.mcnp.model.ba.EuCapaTs;
import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.bc.EuProduit;
import com.esmc.mcnp.model.cm.EuCategorieCompte;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.mprg.EuKrr;
import com.esmc.mcnp.model.obps.EuTegc;
import com.esmc.mcnp.model.odd.EuMstier;
import com.esmc.mcnp.model.odd.EuMstiersUtilise;
import com.esmc.mcnp.model.oi.EuBnp;
import com.esmc.mcnp.model.oi.EuCaps;
import com.esmc.mcnp.model.oi.EuTypeBnp;
import com.esmc.mcnp.model.others.EuOperation;
import com.esmc.mcnp.model.smcipn.EuGcsc;
import com.esmc.mcnp.repositories.cm.EuCompteRepository;
import com.esmc.mcnp.repositories.cm.EuMembreMoraleRepository;
import com.esmc.mcnp.repositories.cm.EuMembreRepository;
import com.esmc.mcnp.repositories.common.EuOperationRepository;
import com.esmc.mcnp.repositories.mprg.EuKrrRepository;
import com.esmc.mcnp.repositories.obps.EuGcscRepository;
import com.esmc.mcnp.repositories.odd.EuMstierRepository;
import com.esmc.mcnp.repositories.odd.EuMstiersUtiliseRepository;
import com.esmc.mcnp.repositories.oi.EuBnpRepository;
import com.esmc.mcnp.repositories.oi.EuCapsRepository;
import com.esmc.mcnp.repositories.oi.EuTypeBnpRepository;
import com.esmc.mcnp.services.bc.EuBonService;
import com.esmc.mcnp.services.obps.EuTegcService;
import com.esmc.mcnp.services.setting.EuParametresService;

/**
 * @author USER
 */
@Component
@Transactional
public class BnpComponent {

	private  EuMembreRepository membreRepo;
	private  EuMembreMoraleRepository moralRepo;
	private  EuCapsRepository capsRepo;
	private  EuParametresService paramService;
	private  EuCompteRepository compteRepo;
	private  EuBnpRepository bnpRepo;
	private  EuTypeBnpRepository bnpTypeRepo;
	private  CreditComponent creditService;
	private  EuOperationRepository opRepo;
	private  EuKrrRepository krrRepo;
	private  CreditUtility creditUtility;
	private  SmcipnComponent smcipnCompo;
	private  EuTegcService tegcService;
	private  TransfertUtility transfertUtility;
	private  Payement payement;
	private  EuGcscRepository gcscRepo;
	private  EuBonService bonService;
	private  EuMstierRepository mstierRepo;
	private  EuMstiersUtiliseRepository mstierUtilRepo;

	public BnpComponent(EuMembreRepository membreRepo, EuMembreMoraleRepository moralRepo, EuCapsRepository capsRepo,
			EuParametresService paramService, EuCompteRepository compteRepo, EuBnpRepository bnpRepo,
			EuTypeBnpRepository bnpTypeRepo, CreditComponent creditService, EuOperationRepository opRepo,
			EuKrrRepository krrRepo, CreditUtility creditUtility, SmcipnComponent smcipnCompo,
			EuTegcService tegcService, TransfertUtility transfertUtility, Payement payement, EuGcscRepository gcscRepo,
			EuBonService bonService, EuMstierRepository mstierRepo, EuMstiersUtiliseRepository mstierUtilRepo) {
		this.membreRepo = membreRepo;
		this.moralRepo = moralRepo;
		this.capsRepo = capsRepo;
		this.paramService = paramService;
		this.compteRepo = compteRepo;
		this.bnpRepo = bnpRepo;
		this.bnpTypeRepo = bnpTypeRepo;
		this.creditService = creditService;
		this.opRepo = opRepo;
		this.krrRepo = krrRepo;
		this.creditUtility = creditUtility;
		this.smcipnCompo = smcipnCompo;
		this.tegcService = tegcService;
		this.transfertUtility = transfertUtility;
		this.payement = payement;
		this.gcscRepo = gcscRepo;
		this.bonService = bonService;
		this.mstierRepo = mstierRepo;
		this.mstierUtilRepo = mstierUtilRepo;
	}
	
	public boolean isCreditAttached(String codeMembre) {
		boolean response = false;
		if (StringUtils.isNotBlank(codeMembre)) {
			EuCaps caps = capsRepo.findByEuMembre2_CodeMembre(codeMembre);
			if (Objects.nonNull(caps)) {
				response = (caps.getIndexer() == 1);
			}
		}
		return response;
	}

	public void indexCaps(EuCaps caps, EuCompteCredit credit) {

	}

	public boolean doBnp(Bnp bnp, CalculBonInfo info, List<EuCapaTs> capatss, EuBon bon) {
		if (Objects.nonNull(bnp) && Objects.nonNull(info)) {
			if (bnp.getCodeMembreBenef().endsWith("M")) {
				throw new IllegalArgumentException("Ce membre ne peut bénéficier d'une souscription du BC pour tiers");
			}
			if (StringUtils.isNotBlank(bnp.getTypeBnp()) && StringUtils.isNotBlank(bnp.getCodeMembreApp())
					&& StringUtils.isNotBlank(bnp.getCodeMembreBenef()) && bnp.getMontBnp() > 0) {
				Date date = new Date();
				EuTypeBnp tbnp = bnpTypeRepo.findOne(bnp.getTypeBnp());
				EuMembre membre = membreRepo.findOne(bnp.getCodeMembreBenef());
				EuMembre apporteur = null;
				EuMembreMorale mapporteur = null;
				EuCaps caps = null;
				if (bnp.getCodeMembreApp().endsWith("M")) {
					mapporteur = moralRepo.findOne(bnp.getCodeMembreApp());
				} else {
					apporteur = membreRepo.findOne(bnp.getCodeMembreApp());
				}
				double pck = paramService.getParametre("pck", "r");
				double prk = paramService.getParametre("prk", "r");
				double pbnp = paramService.getParam("periode", "RBNP");
				double tbcp = paramService.getParametre("TBCP", "valeur");
				double tpanu = paramService.getParametre("TPANU", "valeur");
				double bcn = bnp.getMontBnp() * prk / pck;
				double par = (bcn * tbcp) / 100;
				double credit = 0;
				double fs = 0;
				double panu = 0;
				if (membre.getAutoEnroler().equalsIgnoreCase("O")) {
					credit = Math.round(
							creditUtility.calculBcTiers(info.getTypeCredit(), bnp.getTypeBnp(), bnp.getMontBnp()));
				} else {
					caps = capsRepo.findByCodeMembreBenef(bnp.getCodeMembreBenef());
					if (Objects.nonNull(caps) && caps.getIndexer() != 1) {
						fs = Math.round(caps.getMontCaps() / pbnp);
					}
					credit = Math.round(
							creditUtility.calculBcTiers(info.getTypeCredit(), bnp.getTypeBnp(), bnp.getMontBnp()));
				}
				double krrCredit = Math.round((credit * tbcp) / 100);
				if (!bnp.getTypeBnp().equalsIgnoreCase("CAIPC")) {
					panu = Math.round((bcn * tpanu) / 100);
				}
				double msbcr = Math.round((credit + krrCredit) * pck / prk);

				EuCategorieCompte cat = new EuCategorieCompte("TPAGCRPG");
				Long idOp = opRepo.getLastOperationInsertedId() + 1;
				EuOperation op = new EuOperation();
				op.setEuCategorieCompte(cat);
				op.setCodeProduit("RPGr");
				op.setDateOp(date);
				op.setHeureOp(date);
				op.setLibOp("Opération de " + bnp.getTypeBnp());
				op.setTypeOp("BNP");
				op.setMontantOp(bnp.getMontBnp());
				op.setEuMembre(membre);
				op.setEuMembreMorale(mapporteur);
				op.setIdOperation(idOp);
				op.setEuUtilisateur(null);
				opRepo.save(op);

				EuBnp eubnp = new EuBnp();
				eubnp.setCodeBnp(bnp.getTypeBnp() + ServerUtil.formatDate2(date));
				eubnp.setCodeMembreApp(bnp.getCodeMembreApp());
				eubnp.setCodeMembreBenef(bnp.getCodeMembreBenef());
				eubnp.setConus(credit);
				eubnp.setEuOperation(op);
				eubnp.setEuTypeBnp(tbnp);
				eubnp.setMontantBnp(bnp.getMontBnp());
				eubnp.setMontConus(credit);
				eubnp.setMontCredit(bcn);
				eubnp.setPanu(panu);
				eubnp.setMontPanu(panu);
				eubnp.setMontPar(par);
				eubnp.setNatureBnp("BNP");
				eubnp = bnpRepo.save(eubnp);

				EuCompte compteb = creditService.saveCompte(apporteur, "TPAGCRPG", "NB", credit);
				EuProduit produit = new EuProduit("RPGr");
				EuCompteCredit cc = creditService.createCredit(compteb, produit, msbcr, credit, true,
						eubnp.getCodeBnp(), op, eubnp.getCodeBnp(), bnp.getCodeMembreBenef(), info);

				EuCompte compte_fs = null;
				if (fs > 0) {
					if (Objects.nonNull(caps.getEuMembre1())) {
						compte_fs = creditService.saveCompte(caps.getEuMembre1(), "TFS", "NB", fs);
					} else {
						compte_fs = creditService.saveCompte(caps.getEuMembreMorale(), "TFS", "NB", fs);
					}
					EuCompteCredit ccfs = creditService.createCredit(compte_fs, new EuProduit("FS"), fs * pck, fs, true,
							eubnp.getCodeBnp(), op, cc.getIdCredit().toString(), bnp.getCodeMembreApp(), info);
					creditService.updateListCapaTs(capatss, ccfs, fs * pck, date, "r", bon);
				}
				double mont_smcipn = (par + panu) * info.getDuree();
				EuTegc tegc = tegcService.findByCodeMembre(bnp.getCodeMembreApp()).get(0);
				EuBon bl = payement.createBonLivraison(bnp.getCodeMembreApp(), "0010010010010000003M", "BNP", "", null,
						mont_smcipn);
				if (Objects.nonNull(bl)) {

					bl.setBonDateExpression(date);
					bl.setBonExprimer(1);
					bonService.update(bl);

					String numeroAppel = smcipnCompo.doSmcipnInterm(bnp.getCodeMembreBenef(), tegc.getCodeTegc(),
							bnp.getCodeMembreApp(), bl.getBonNumero(), null, mont_smcipn);
					if (StringUtils.isNotBlank(numeroAppel)) {
						smcipnCompo.echangeNRNB(bnp.getCodeMembreBenef(), numeroAppel, bnp.getTypeProduit(),
								mont_smcipn, prk);
						CalculBonInfo bonInfo = new CalculBonInfo("nr", null, bnp.getTypeProduit(), 22.4, prk);
						EuBon bonBc = transfertUtility.tansfertBC(bnp.getCodeMembreBenef(), "RPG", bonInfo, prk,
								mont_smcipn);
						payement.makeTransaction(bnp.getCodeMembreApp(), bnp.getCodeMembreBenef(), "TPAGCRPG", tegc,
								bonBc, bl, "BNP", mont_smcipn);
						payement.creerMarge(date, mont_smcipn);
						EuGcsc gcsc = gcscRepo.findByNumeroAppelOffre(numeroAppel);
						if (Objects.nonNull(gcsc)) {
							eubnp.setIdGcsc(gcsc.getIdGcsc().intValue());
							bnpRepo.save(eubnp);
						} else {
							throw new RuntimeException("Le GCsc n'est pas bien enregistré");
						}
					} else {
						throw new RuntimeException("L'operation de SMCIPN n'est aps bien terminée!");
					}
				}
				return true;
			} else {
				throw new IllegalArgumentException("Certaines infos manquent pour effectuer cette opération");
			}
		}
		return false;
	}

	public boolean doBnp(Bnp bnp, CalculBonInfo info, List<EuMstier> mstiers, List<EuCapaTs> capatss, EuBon bon) {
		if (Objects.nonNull(bnp) && Objects.nonNull(info)) {
			if (bnp.getCodeMembreBenef().endsWith("M")) {
				throw new IllegalArgumentException("Ce membre ne peut bénéficier d'une souscription du BC pour tiers");
			}
			if (StringUtils.isNotBlank(bnp.getTypeBnp()) && StringUtils.isNotBlank(bnp.getCodeMembreApp())
					&& StringUtils.isNotBlank(bnp.getCodeMembreBenef()) && bnp.getMontBnp() > 0) {
				Date date = new Date();
				EuTypeBnp tbnp = bnpTypeRepo.findOne(bnp.getTypeBnp());
				EuMembre membre = membreRepo.findOne(bnp.getCodeMembreBenef());
				EuMembre apporteur = null;
				EuMembreMorale mapporteur = null;
				EuCaps caps = null;
				if (bnp.getCodeMembreApp().endsWith("M")) {
					mapporteur = moralRepo.findOne(bnp.getCodeMembreApp());
				} else {
					apporteur = membreRepo.findOne(bnp.getCodeMembreApp());
				}
				double pck = paramService.getParametre("pck", "r");
				double prk = paramService.getParametre("prk", "r");
				double pbnp = paramService.getParam("periode", "RBNP");
				double tbcp = paramService.getParametre("TBCP", "valeur");
				double tpanu = paramService.getParametre("TPANU", "valeur");
				double bcn = bnp.getMontBnp() * prk / pck;
				double par = (bcn * tbcp) / 100;
				double credit = 0;
				double fs = 0;
				double panu = 0;
				if (membre.getAutoEnroler().equalsIgnoreCase("O")) {
					credit = Math.round(
							creditUtility.calculBcTiers(info.getTypeCredit(), bnp.getTypeBnp(), bnp.getMontBnp()));
				} else {
					caps = capsRepo.findByCodeMembreBenef(bnp.getCodeMembreBenef());
					if (Objects.nonNull(caps) && caps.getIndexer() != 1) {
						fs = Math.round(caps.getMontCaps() / pbnp);
					}
					credit = Math.round(
							creditUtility.calculBcTiers(info.getTypeCredit(), bnp.getTypeBnp(), bnp.getMontBnp()));
				}
				double krrCredit = Math.round((credit * tbcp) / 100);
				if (!bnp.getTypeBnp().equalsIgnoreCase("CAIPC")) {
					panu = Math.round((bcn * tpanu) / 100);
				}
				double msbcr = Math.round((credit + krrCredit) * pck / prk);

				EuCategorieCompte cat = new EuCategorieCompte("TPAGCRPG");
				Long idOp = opRepo.getLastOperationInsertedId() + 1;
				EuOperation op = new EuOperation();
				op.setEuCategorieCompte(cat);
				op.setCodeProduit("RPGr");
				op.setDateOp(date);
				op.setHeureOp(date);
				op.setLibOp("Opération de " + bnp.getTypeBnp());
				op.setTypeOp("BNP");
				op.setMontantOp(bnp.getMontBnp());
				op.setEuMembre(membre);
				op.setEuMembreMorale(mapporteur);
				op.setIdOperation(idOp);
				op.setEuUtilisateur(null);
				opRepo.save(op);

				EuBnp eubnp = new EuBnp();
				eubnp.setCodeBnp(bnp.getTypeBnp() + ServerUtil.formatDate2(date));
				eubnp.setCodeMembreApp(bnp.getCodeMembreApp());
				eubnp.setCodeMembreBenef(bnp.getCodeMembreBenef());
				eubnp.setConus(credit);
				eubnp.setEuOperation(op);
				eubnp.setEuTypeBnp(tbnp);
				eubnp.setMontantBnp(bnp.getMontBnp());
				eubnp.setMontConus(credit);
				eubnp.setMontCredit(bcn);
				eubnp.setPanu(panu);
				eubnp.setMontPanu(panu);
				eubnp.setMontPar(par);
				eubnp.setNatureBnp("BNP");
				eubnp = bnpRepo.save(eubnp);

				EuCompte compteb = creditService.saveCompte(apporteur, "TPAGCRPG", "NB", credit);
				EuProduit produit = new EuProduit("RPGr");
				EuCompteCredit cc = creditService.createCredit(compteb, produit, msbcr, credit, true,
						eubnp.getCodeBnp(), op, eubnp.getCodeBnp(), bnp.getCodeMembreBenef(), info);

				EuCompte compte_fs = null;
				if (fs > 0) {
					if (Objects.nonNull(caps.getEuMembre1())) {
						compte_fs = creditService.saveCompte(caps.getEuMembre1(), "TFS", "NB", fs);
					} else {
						compte_fs = creditService.saveCompte(caps.getEuMembreMorale(), "TFS", "NB", fs);
					}
					EuCompteCredit ccfs = creditService.createCredit(compte_fs, new EuProduit("FS"), fs * pck, fs, true,
							eubnp.getCodeBnp(), op, cc.getIdCredit().toString(), bnp.getCodeMembreApp(), info);
					creditService.updateListCapaTs(capatss, ccfs, fs * pck, date, "r", bon);
				}
				int i = 0;
				double mont_deduire = bnp.getMontBnp();
				while (i < mstiers.size() && mont_deduire > 0) {
					EuMstier mstier = mstiers.get(i);
					if (mstier.getMontantRestant() < mont_deduire) {

						mont_deduire -= mstier.getMontantRestant();

						Integer id = mstierUtilRepo.getLastInsertId();
						if (id == null) {
							id = 1;
						}
						EuMstiersUtilise mstierUtiil = new EuMstiersUtilise();
						mstierUtiil.setIdMstiers(id);
						mstierUtiil.setCodeCaps(eubnp.getCodeBnp());
						mstierUtiil.setDateMstiersUtilise(date);
						mstierUtiil.setIdMstiers(mstier.getIdMstiers());
						mstierUtiil.setMontantUtilise(mstier.getMontantRestant());
						mstierUtilRepo.save(mstierUtiil);

						mstier.setMontantUtilise(mstier.getMontantUtilise() + mstier.getMontantRestant());
						mstier.setMontantRestant(0);
						mstierRepo.save(mstier);

						i++;
					} else {

						Integer id = mstierUtilRepo.getLastInsertId();
						if (id == null) {
							id = 1;
						}
						EuMstiersUtilise mstierUtiil = new EuMstiersUtilise();
						mstierUtiil.setIdMstiers(id);
						mstierUtiil.setCodeCaps(eubnp.getCodeBnp());
						mstierUtiil.setDateMstiersUtilise(date);
						mstierUtiil.setIdMstiers(mstier.getIdMstiers());
						mstierUtiil.setMontantUtilise(mont_deduire);
						mstierUtilRepo.save(mstierUtiil);

						mstier.setMontantUtilise(mstier.getMontantUtilise() + mont_deduire);
						mstier.setMontantRestant(mstier.getMontantRestant() - mont_deduire);
						mstierRepo.save(mstier);
						mont_deduire = 0;
					}
				}
				return true;
			} else {
				throw new IllegalArgumentException("Certaines infos manquent pour effectuer cette opération");
			}
		}
		return false;
	}

	public boolean doBnpPerenne(Bnp bnp) {
		if (Objects.nonNull(bnp)) {
			if (StringUtils.isNotBlank(bnp.getCodeMembreApp()) && StringUtils.isNotBlank(bnp.getCodeMembreBenef())
					&& StringUtils.isNotBlank(bnp.getTypeBnp()) && bnp.getMontBnp() > 0) {
				if ((bnp.getTypeBnp().equals("CAPU") || bnp.getTypeBnp().equals("CMIT")
						|| bnp.getTypeBnp().equals("CAIPC")) && bnp.getCodeMembreBenef().endsWith("M")) {
					return false;
				}

				EuMembreMorale m_apport = null;
				EuMembre p_apport = null;
				EuMembre p_benef = null;
				Date date = new Date();
				if (bnp.getCodeMembreApp().endsWith("M")) {
					m_apport = moralRepo.findByKey(bnp.getCodeMembreApp());
				} else {
					p_apport = membreRepo.findOne(bnp.getCodeMembreApp());
				}
				p_benef = membreRepo.findOne(bnp.getCodeMembreBenef());
				if (Objects.nonNull(p_benef) && (Objects.nonNull(p_apport) || Objects.nonNull(m_apport))) {
					/**
					 * Calcul du bon de consommation et sa repartition
					 */
					EuTypeBnp tbnp = bnpTypeRepo.findOne(bnp.getTypeBnp());
					if (Objects.nonNull(tbnp)) {
						double pck = paramService.getParametre("pck", "r");
						double prk = paramService.getParametre("prk", "r");
						double cnp = bnp.getMontBnp() * prk / pck;
						double conus = cnp * tbnp.getTxConus() / 100;
						double par = cnp * tbnp.getTxPar() / 100;
						double fs = 0;
						double panu = 0;
						EuCaps caps = null;
						if (tbnp.getCodeTypeBnp().equals("CAPU") || tbnp.getCodeTypeBnp().equals("CMIT")) {
							panu = cnp * tbnp.getTxPanu() / 100;
						}
						if (StringUtils.isNotBlank(p_benef.getAutoEnroler()) && p_benef.getAutoEnroler().equals("N")) {
							caps = capsRepo.findByCodeMembreBenef(bnp.getCodeMembreBenef());
							if (Objects.nonNull(caps) && caps.getIndexer() != 1) {
								fs = cnp * tbnp.getTxFs() / 100;
							}
						}

						EuCategorieCompte cat = new EuCategorieCompte("TPAGCRPG");
						Long idOp = opRepo.getLastOperationInsertedId() + 1;
						EuOperation op = new EuOperation();
						op.setEuCategorieCompte(cat);
						op.setCodeProduit("RPGr");
						op.setDateOp(date);
						op.setHeureOp(date);
						op.setLibOp("Opération de " + bnp.getTypeBnp());
						op.setTypeOp("BNP");
						op.setMontantOp(bnp.getMontBnp());
						op.setEuMembre(p_benef);
						op.setIdOperation(idOp);
						op.setEuUtilisateur(null);
						opRepo.save(op);

						EuBnp eubnp = new EuBnp();
						eubnp.setCodeBnp(bnp.getTypeBnp() + ServerUtil.formatDate2(date));
						eubnp.setCodeMembreApp(bnp.getCodeMembreApp());
						eubnp.setCodeMembreBenef(bnp.getCodeMembreBenef());
						eubnp.setConus(conus);
						eubnp.setEuOperation(op);
						eubnp.setEuTypeBnp(tbnp);
						eubnp.setMontantBnp(bnp.getMontBnp());
						eubnp.setMontConus(eubnp.getMontConus() + conus);
						eubnp.setMontCredit(cnp);
						eubnp.setMontPanu(eubnp.getMontPanu() + panu);
						eubnp.setMontPar(eubnp.getMontPar() + par);
						eubnp.setNatureBnp("BNPP");
						bnpRepo.save(eubnp);

						EuCompte compte_panu = null;
						EuCompte compte_par = null;
						EuCompte compte_fs = null;
						EuCompte compteb = compteRepo.findCompteById("NB-TPAGCRPG-" + p_benef.getCodeMembre());
						if (Objects.nonNull(compteb)) {
							compteb.setSolde(compteb.getSolde() + conus);
							compteRepo.save(compteb);
						}

						EuProduit produit = new EuProduit("RPGr");
						EuCompteCredit cc = creditService.createCredit(compteb, produit, bnp.getMontBnp(), conus, true,
								eubnp.getCodeBnp(), op, bnp.getCodeBnp(), bnp.getCodeMembreBenef(),
								new CalculBonInfo(prk, -1));
						creditService.createCapaCredit(cc, bnp.getMontBnp(), date, "r");

						if (bnp.getNatureBnp().equals("BNPP")) {
							EuKrr krr = new EuKrr();
							krr.setDateDemande(date);
							krr.setDateEchue(DateUtils.addDays(date, (int) Math.ceil(22.4 * 30)));
							krr.setDateRenouveller(DateUtils.addDays(date, 30));
							if (bnp.getCodeMembreApp().equals("M")) {
								krr.setEuMembreMorale(m_apport);
							} else {
								krr.setEuMembre(p_apport);
							}
							krr.setEuProduit(produit);
							krr.setIdCredit(cc.getIdCredit());
							krr.setMontCapa(bnp.getMontBnp());
							krr.setMontKrr(bnp.getMontBnp());
							krr.setMontReconst(par);
							krr.setTypeKrr("krrBNPP");
							krr.setPayer("N");
							krr.setReconstituer("N");
							krrRepo.save(krr);

						} else {
							if (Objects.nonNull(p_apport)) {
								compte_par = creditService.saveCompte(p_apport, "TPaR", "NB", par);
							} else {
								compte_par = creditService.saveCompte(m_apport, "TPaR", "NB", par);
							}
							creditService.createCredit(compte_par, new EuProduit("PaR"), bnp.getMontBnp(), par, true,
									eubnp.getCodeBnp(), op, cc.getIdCredit().toString(), bnp.getCodeMembreApp(),
									new CalculBonInfo(prk, 0));
						}
						if (tbnp.getCodeTypeBnp().equals("CAPU") || tbnp.getCodeTypeBnp().equals("CMIT")) {
							if (Objects.nonNull(p_apport)) {
								compte_panu = creditService.saveCompte(p_apport, "TPaNu", "NB", panu);
							} else {
								compte_panu = creditService.saveCompte(m_apport, "TPaNu", "NB", panu);
							}
							creditService.createCredit(compte_panu, new EuProduit("PaNu"), bnp.getMontBnp(), panu, true,
									eubnp.getCodeBnp(), op, cc.getIdCredit().toString(), bnp.getCodeMembreApp(),
									new CalculBonInfo(prk, 0));
						}
						if (fs > 0) {
							if (Objects.nonNull(caps.getEuMembre1())) {
								compte_fs = creditService.saveCompte(caps.getEuMembre1(), "TFS", "NB", fs);
							} else {
								compte_fs = creditService.saveCompte(caps.getEuMembreMorale(), "TFS", "NB", fs);
							}
							creditService.createCredit(compte_fs, new EuProduit("FS"), bnp.getMontBnp(), fs, true,
									eubnp.getCodeBnp(), op, cc.getIdCredit().toString(), bnp.getCodeMembreApp(),
									new CalculBonInfo(prk, 0));
						}
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return false;
	}

}

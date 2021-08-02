package com.esmc.mcnp.infrastructure.components;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dao.repository.acteurs.EuEliRepository;
import com.esmc.mcnp.dao.repository.acteurs.EuTypeIntegrateurRepository;
import com.esmc.mcnp.dao.repository.cm.EuMembreMoraleRepository;
import com.esmc.mcnp.dao.repository.cm.EuMembreRepository;
import com.esmc.mcnp.dao.repository.cmfh.EuCmfhRepository;
import com.esmc.mcnp.dao.repository.cmfh.EuSouscriptionRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuBonNeutreDetailRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuBonNeutreRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuTpagcpRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuTraiteRepository;
import com.esmc.mcnp.dao.repository.org.EuPrefectureRepository;
import com.esmc.mcnp.dao.repository.org.EuRegionRepository;
import com.esmc.mcnp.domain.dto.bc.CalculBonInfo;
import com.esmc.mcnp.domain.dto.ksu.Souscription;
import com.esmc.mcnp.domain.entity.acteur.EuEli;
import com.esmc.mcnp.domain.entity.acteur.EuSouscription;
import com.esmc.mcnp.domain.entity.acteur.EuTypeIntegrateur;
import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.cm.EuCompteBancaire;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.cmfh.EuCmfh;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.domain.entity.obpsd.EuBan;
import com.esmc.mcnp.domain.entity.obpsd.EuBanVendu;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutre;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreDetail;
import com.esmc.mcnp.domain.entity.obpsd.EuTpagcp;
import com.esmc.mcnp.domain.entity.obpsd.EuTraite;
import com.esmc.mcnp.domain.entity.odd.EuMstier;
import com.esmc.mcnp.domain.entity.org.EuPrefecture;
import com.esmc.mcnp.domain.entity.org.EuRegion;
import com.esmc.mcnp.domain.entity.pc.EuCharge;
import com.esmc.mcnp.domain.entity.pc.EuChargePaye;
import com.esmc.mcnp.domain.entity.smcipn.EuSmcipnpwi;
import com.esmc.mcnp.infrastructure.services.bc.EuBonService;
import com.esmc.mcnp.infrastructure.services.cm.EuCompteBancaireService;
import com.esmc.mcnp.infrastructure.services.obps.EuTegcService;
import com.esmc.mcnp.infrastructure.services.oi.EuMstiersService;
import com.esmc.mcnp.infrastructure.services.pc.EuBanService;
import com.esmc.mcnp.infrastructure.services.pc.EuBanVenduService;
import com.esmc.mcnp.infrastructure.services.pc.EuChargePayeService;
import com.esmc.mcnp.infrastructure.services.pc.EuChargeService;
import com.esmc.mcnp.infrastructure.services.setting.EuParametresService;
import com.esmc.mcnp.infrastructure.services.smcipn.EuSmcipnpwiService;

@Component
@Transactional
public class OpiUtility {

	private final TransfertUtility transfertUtility;
	private final SouscriptionBon souscriptionBon;
	private final BonAchatComponent bonAchatComponent;
	private final Payement payement;
	private final EuTegcService tegcService;
	private final SmcipnComponent smcipnComponent;
	private final EuBonService bonService;
	private final CreditUtility creditUtility;
	private final EuParametresService parametresService;
	private final EuMstiersService mstiersService;
	private final EuSouscriptionRepository souscriptionRepository;
	private final EuCmfhRepository cmfhRepository;
	private final EuMembreRepository membreRepository;
	private final EuMembreMoraleRepository moraleRepository;
	private final EuTypeIntegrateurRepository typeIntegrateurRepo;
	private final EuPrefectureRepository prefectureRepo;
	private final EuRegionRepository regionRepo;
	private final EuEliRepository eliRepository;
	private final EuBonNeutreRepository bnNeutreRepo;
	private final EuBonNeutreDetailRepository bnNeutreDetRepo;
	private final EuTraiteRepository traiteRepo;
	private final EuTpagcpRepository tpagcpRepo;
	private final EuCompteBancaireService cbservice;
	private final EuSmcipnpwiService smcipnService;
	private final EuChargeService chargeService;
	private final EuChargePayeService chargePayeService;
	private final EuBanService banService;
	private final EuBanVenduService banVenduService;

	private final Logger log = LogManager.getLogger(OpiUtility.class.getName());

	public OpiUtility(TransfertUtility transfertUtility, SouscriptionBon souscriptionBon, Payement payement,
			EuTegcService tegcService, SmcipnComponent smcipnComponent, EuBonService bonService,
			CreditUtility creditUtility, EuParametresService parametresService, EuMstiersService mstiersService,
			EuSouscriptionRepository souscriptionRepository, EuCmfhRepository cmfhRepository,
			EuMembreRepository membreRepository, EuMembreMoraleRepository moraleRepository,
			EuTypeIntegrateurRepository typeIntegrateurRepo, EuPrefectureRepository prefectureRepo,
			EuRegionRepository regionRepo, EuEliRepository eliRepository, EuBonNeutreRepository bnNeutreRepo,
			EuBonNeutreDetailRepository bnNeutreDetRepo, EuTraiteRepository traiteRepo, EuTpagcpRepository tpagcpRepo,
			EuCompteBancaireService cbservice, EuSmcipnpwiService smcipnService, EuChargeService chargeService,
			EuChargePayeService chargePayeService, EuBanService banService, EuBanVenduService banVenduService, BonAchatComponent bonAchatComponent) {
		this.transfertUtility = transfertUtility;
		this.souscriptionBon = souscriptionBon;
		this.bonAchatComponent = bonAchatComponent;
		this.payement = payement;
		this.tegcService = tegcService;
		this.smcipnComponent = smcipnComponent;
		this.bonService = bonService;
		this.creditUtility = creditUtility;
		this.parametresService = parametresService;
		this.mstiersService = mstiersService;
		this.souscriptionRepository = souscriptionRepository;
		this.cmfhRepository = cmfhRepository;
		this.membreRepository = membreRepository;
		this.moraleRepository = moraleRepository;
		this.typeIntegrateurRepo = typeIntegrateurRepo;
		this.prefectureRepo = prefectureRepo;
		this.regionRepo = regionRepo;
		this.eliRepository = eliRepository;
		this.bnNeutreRepo = bnNeutreRepo;
		this.bnNeutreDetRepo = bnNeutreDetRepo;
		this.traiteRepo = traiteRepo;
		this.tpagcpRepo = tpagcpRepo;
		this.cbservice = cbservice;
		this.smcipnService = smcipnService;
		this.chargeService = chargeService;
		this.chargePayeService = chargePayeService;
		this.banService = banService;
		this.banVenduService = banVenduService;
	}

	public boolean souscrireAR(String codeMembre, String typeNn, String codeBanque, String numeroCompte, int nbre,
			Date date, double montant) {
		try {
			String codeMembreSmc = "0010010010010000003M";
			EuTegc tegc = tegcService.findTegcByCodeMembreAndNomTegc(codeMembreSmc, "TE ACHETEUR-REVENDEUR");
			String typeCapa = "I";
			if (codeMembre.endsWith("P")) {
				typeCapa = "RPG";
			}
			EuBon ba = transfertUtility.transfertBA(codeMembre, typeNn, typeCapa, montant);
			if (Objects.nonNull(ba)) {
				CalculBonInfo bonInfo = new CalculBonInfo();
				bonInfo.setCatBon("nr");
				bonInfo.setTypeProduit("PS");
				bonInfo.setPrk(8);
				bonInfo.setDuree(8);
				double mont_bon = Math.floor(creditUtility.calculBonConso(bonInfo, montant));
				souscriptionBon.souscrireBonConso(codeMembre, bonInfo, ba.getBonNumero(), montant);
				EuBon bc = transfertUtility.tansfertBC(codeMembre, typeCapa, bonInfo, 8.0, mont_bon);
				if (Objects.nonNull(bc)) {
					EuBon bl = payement.createBonLivraison(codeMembreSmc, codeMembre, "VPC", mont_bon);
					if (Objects.nonNull(bl)) {
						payement.makeTransaction(codeMembre, codeMembreSmc, "TI", tegc, bc, bl, "", mont_bon);
						payement.creerMarge(new Date(), mont_bon);

						EuBon bld = payement.createBonLivraison(codeMembre, codeMembreSmc, "APC", mont_bon);
						bld.setBonExprimer(1);
						bld.setBonDateExpression(date);
						bonService.update(bld);

						String numAppelOffre = smcipnComponent.doSmcipnInterm(codeMembreSmc, tegc.getCodeTegc(),
								codeMembre, bld.getBonNumero(), null, mont_bon);
						if (StringUtils.isNotBlank(numAppelOffre)) {
							if (smcipnComponent.echangeNRNB(codeMembreSmc, numAppelOffre, "PO", mont_bon, 8.0)) {
								bonInfo = new CalculBonInfo();
								bonInfo.setCatBon("nr");
								bonInfo.setTypeProduit("PO");
								EuBon bon = transfertUtility.tansfertBC(codeMembreSmc, "TI", bonInfo, 8.0, mont_bon);
								if (Objects.nonNull(bon)) {
									String typeOp = "PC";
									EuTegc te = tegcService.findByCodeMembrePhysique(codeMembre).get(0);
									payement.makeTransaction(codeMembre, codeMembreSmc, "TI", te, bon, bl, typeOp,
											mont_bon);
									payement.creerMarge(new Date(), montant);

									String typeOpi = "P";
									typeOpi = "PM";
									payement.emetreOpi(typeOpi, codeMembre, te.getCodeTegc(), "", codeBanque,
											numeroCompte, nbre, mont_bon);
								}
							}
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean souscrireBCTiers(Souscription souscript, String typeNn, String typeResource, String codeBanque,
			String numeroCompte, Date date, double montant) {
		try {
			Double montCaps = parametresService.getParametre("CAPS", "valeur");
			Double bnpmps = parametresService.getParametre("BNPMPSFS", "valeur");
			Double bnpnmps = parametresService.getParametre("BNPNMPSFS", "valeur");
			String codeMembre = souscript.getCodeMembreIntegrateur();
			double tpanu = 0;
			double mont_smcipn = 0;
			String codeMembreSmcipn = "0010010010010000212M";
			EuMembre membre = null;
			EuMembreMorale morale = null;
			EuPrefecture prefecture = null;
			EuRegion region = null;

			if (codeMembre.endsWith("P")) {
				membre = membreRepository.findByCodeMembre(codeMembre);
				if (Objects.nonNull(membre.getEuCanton())) {
					prefecture = prefectureRepo.findByEuCantons_IdCanton(membre.getEuCanton().getIdCanton());
					region = regionRepo.findByKey(prefecture.getIdRegion());
				}
			} else {
				morale = moraleRepository.findByKey(codeMembre);
			}
			EuTypeIntegrateur typeIntegrateur = typeIntegrateurRepo.findOne(souscript.getTypeIntegrateur());
			if (souscript.getMontSouscription() < typeIntegrateur.getMontantParam()) {
				return false;
			}
			if (souscript.getTypeActivite().equalsIgnoreCase("CM")
					&& souscript.getStatutSouscription().equalsIgnoreCase("AvecListe")) {
				if (souscript.getMontSouscription() % montCaps != 0) {
					return false;
				}
			} else if (souscript.getTypeActivite().equalsIgnoreCase("BC")
					&& souscript.getStatutSouscription().equalsIgnoreCase("AvecListe")) {
				if (souscript.getTypeSouscription().equalsIgnoreCase("CAPU")
						|| souscript.getTypeSouscription().equalsIgnoreCase("CMIT")) {
					if (souscript.getMontSouscription() % bnpmps != 0) {
						return false;
					}
				} else {
					if (souscript.getMontSouscription() % bnpnmps != 0) {
						return false;
					}
				}
			}
			if (souscript.getCodeMembreIntegrateur().endsWith("P")
					&& (typeIntegrateur.getIdTypeIntegrateur() == 21 || typeIntegrateur.getIdTypeIntegrateur() == 24)) {
				return false;
			}
			if ("BAN".equalsIgnoreCase(typeNn)) {
				bonAchatComponent.souscrireBonAchat(codeMembre, "", "", montant);
			}
			EuSouscription souscription = new EuSouscription();
			souscription.setCodeActivite("");
			souscription.setCodeStatut("");
			souscription.setCodeTypeActeur("");
			souscription.setErreur(0);
			souscription.setErreurdescription("");
			souscription.setIdCompetence(0);
			souscription.setIdMetier(0);
			souscription.setIdPostulat(0);
			souscription.setPublier(1);
			souscription.setQuittanceInvalide(0);
			souscription.setSouscriptionAncienMembre(null);
			souscription.setSouscriptionAutonome(null);
			souscription.setSouscriptionBanque(null);
			souscription.setSouscriptionDate(date);
			souscription.setSouscriptionDateNumero(date);
			if (codeMembre.endsWith("P")) {
				souscription.setSouscriptionEmail(membre.getEmailMembre());
				souscription.setSouscriptionNom(membre.getNomMembre());
				souscription.setSouscriptionPrenom(membre.getPrenomMembre());
				if (Objects.nonNull(membre.getEuCanton())) {
					souscription.setIdCanton(membre.getEuCanton().getIdCanton());
				}
			} else {
				souscription.setSouscriptionEmail(morale.getEmailMembre());
				souscription.setSouscriptionRaison(morale.getRaisonSociale());
				if (Objects.nonNull(morale.getEuCanton())) {
					souscription.setIdCanton(morale.getEuCanton().getIdCanton());
				}
			}
			souscription.setSouscriptionFiliere(0);
			souscription.setSouscriptionVille(null);
			souscription.setSouscriptionVignette(null);
			souscription.setSouscriptionLogin(null);
			souscription.setSouscriptionTypeCandidat(0);
			souscription.setSouscriptionPasse(null);
			souscription.setSouscriptionProgramme(null);
			souscription.setSouscriptionQuartier(null);
			souscriptionRepository.save(souscription);

			EuCmfh cmfh = cmfhRepository.findByCodeMembre(codeMembre);
			if (Objects.isNull(cmfh)) {
				cmfh = new EuCmfh();
				cmfh.setCodeMembre(codeMembre);
				cmfh.setDateCreation(LocalDate.now());
				cmfh.setIdTypeCandidat(souscript.getTypeIntegrateur());
				if (Objects.nonNull(membre)) {
					if (Objects.nonNull(membre.getEuCanton())) {
						cmfh.setIdCanton(membre.getEuCanton().getIdCanton());
					}
					if (Objects.nonNull(prefecture)) {
						cmfh.setIdPrefeture(prefecture.getIdPrefecture());
					}
					if (Objects.nonNull(region)) {
						cmfh.setIdRegion(region.getIdRegion());
					}
					if (Objects.nonNull(region.getEuPay())) {
						cmfh.setIdPays(region.getEuPay().getIdPays());
					}
				} else {
					if (Objects.nonNull(morale.getEuCanton())) {
						cmfh.setIdCanton(morale.getEuCanton().getIdCanton());
					}
					if (Objects.nonNull(prefecture)) {
						cmfh.setIdPrefeture(prefecture.getIdPrefecture());
					}
					if (Objects.nonNull(region)) {
						cmfh.setIdRegion(region.getIdRegion());
					}
					if (Objects.nonNull(region.getEuPay())) {
						cmfh.setIdPays(region.getEuPay().getIdPays());
					}
				}
			}

			EuMstier mstier = new EuMstier();
			mstier.setBonNeutreCode("");
			mstier.setCodeMembre(codeMembre);
			mstier.setDateMstiers(date);
			mstier.setIdSouscription(souscription.getSouscriptionId());
			mstier.setMontantRestant(montant);
			mstier.setMontantSouscris(montant);
			mstier.setMontantUtilise(0);
			mstier.setStatutMstiers("SansListe");
			mstier.setTypeSouscription("CAPU");
			mstiersService.add(mstier);

			EuBon ba = transfertUtility.transfertBA(codeMembre, typeNn, typeResource, montant);

			if (codeMembre.endsWith("M")) {
				tpanu = parametresService.getParametre("TPANU", "PM");
				mont_smcipn = Math.floor(montant + (montant * tpanu / 100));
			} else {
				tpanu = parametresService.getParametre("TPANU", "PP");
				mont_smcipn = Math.floor(montant + ((montant * tpanu / 100) * 2));
			}
			EuBon bl = payement.createBonLivraison(codeMembre, codeMembreSmcipn, "BNP", "", null, mont_smcipn);
			bl.setBonDateExpression(date);
			bl.setBonExprimer(1);
			bonService.update(bl);

			EuTegc tegc = null;
			List<EuTegc> tegcs = tegcService.findByCodeMembre(codeMembre);
			if (!tegcs.isEmpty()) {
				tegc = tegcs.get(0);
			}
			if (Objects.isNull(tegc)) {
				tegc = tegcService.creerTe(codeMembre);
			}

			String numeroAppel = smcipnComponent.doSmcipnCharge(codeMembreSmcipn, tegc.getCodeTegc(),
					"SMCIPN de remboursement des souscription pour tiers", mont_smcipn);
			smcipnComponent.echangeNRNB(codeMembreSmcipn, numeroAppel, "PS", mont_smcipn, 8);
			CalculBonInfo bonInfo = new CalculBonInfo("nr", null, "PS", 22.4, 8);
			EuBon bonBc = transfertUtility.tansfertBC(codeMembreSmcipn, "TI", bonInfo, 8.0, mont_smcipn);
			payement.makeTransaction(codeMembre, codeMembreSmcipn, "TI", tegc, bonBc, bl, "BNP", mont_smcipn);
			payement.creerMarge(date, mont_smcipn);

			String typeOpi = "P";
			payement.emetreOpi(typeOpi, codeMembre, tegc.getCodeTegc(), "", codeBanque, numeroCompte, 23, mont_smcipn);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public void reglerELIParSmcipn(String codeMembreSmcipn, String numeroEli, EuTegc tegc, double montant) {
		Date date = new Date();
		Optional<EuEli> euEli = eliRepository.findByNumeroEli(numeroEli);
		if (euEli.isPresent() && (euEli.get().getBai() || euEli.get().getOpi())) {
			EuEli eli = euEli.get();
			String numeroAppel = smcipnComponent.doSmcipnCharge(codeMembreSmcipn, tegc.getCodeTegc(),
					"SMCIPN de remboursement anticipé d'ELI", montant);
			if (StringUtils.isNotBlank(numeroAppel)) {
				EuCharge tcharge = chargeService.findByCode("CE");
				EuSmcipnpwi smcipn = smcipnService.findByNumeroAppel(numeroAppel);
				EuChargePaye chargePaye = new EuChargePaye();
				chargePaye.setEuSmcipnpwi(smcipn);
				chargePaye.setEuCharge(tcharge);
				chargePaye.setTypeDoc("ELI");
				chargePaye.setNumDoc(eli.getNumeroEli());
				chargePaye.setCodeMembreDebiteur(codeMembreSmcipn);
				chargePaye.setCodeMembreCreancier(eli.getCodeMembre());
				chargePaye.setDateCharge(date);
				chargePaye.setLibelleCharge("Paiement anticipé d'ELI");
				chargePaye.setMontantCharge(montant);
				chargePaye.setOrigineCharge("ESMC");
				chargePayeService.add(chargePaye);

				if (eli.getOpi()) {
					double mont_smcipn = eli.getMontantOpi();
					smcipnComponent.echangeNRNB(codeMembreSmcipn, numeroAppel, "PS", mont_smcipn, 8);
					CalculBonInfo bonInfo = new CalculBonInfo("nr", null, "PS", 22.4, 8);
					EuBon bonBc = transfertUtility.tansfertBC(codeMembreSmcipn, "TI", bonInfo, 8.0, mont_smcipn);
					EuBon bl = payement.createBonLivraison(eli.getCodeMembre(), codeMembreSmcipn, "ELI", "", null,
							mont_smcipn);
					if (Objects.nonNull(bl)) {
						bl.setBonDateExpression(date);
						bl.setBonExprimer(1);
						bonService.update(bl);
						payement.makeTransaction(eli.getCodeMembre(), codeMembreSmcipn, "TI", tegc, bonBc, bl, "ELI",
								mont_smcipn);
						payement.creerMarge(date, mont_smcipn);
						List<EuCompteBancaire> cbs = cbservice.findListCompteBancaire(eli.getCodeMembre());
						EuCompteBancaire cb = null;
						if (cbs.size() > 1) {
							cb = cbs.stream().filter(c -> c.getPrincipal() == 1).findFirst().get();
						} else {
							cb = cbs.get(0);
						}
						payement.emetreOpi("D", eli.getCodeMembre(), tegc.getCodeTegc(), "ELI", cb.getCodeBanque(),
								cb.getNumCompteBancaire(), 12, mont_smcipn);
					}
				}

				if (eli.getBai()) {
					smcipnComponent.echangeNRNN(codeMembreSmcipn, eli.getCodeMembre(), numeroAppel, "PS",
							eli.getMontantBai(), null);
				}
			}
		}
	}

	public void souscrireBan(String codeMembre, String mode, String codeRecu, Long numeroTraite) {
		if (StringUtils.isNotBlank(codeMembre) && StringUtils.isNotBlank(mode)
				&& (StringUtils.isNotBlank(codeRecu) || numeroTraite != null)) {
			Date date = new Date();
			EuMembreMorale morale = null;
			EuMembre membre = null;
			if (codeMembre.endsWith("M")) {
				morale = moraleRepository.findByKey(codeMembre);
			} else {
				membre = membreRepository.findByCodeMembre(codeMembre);
			}
			if (Objects.nonNull(membre) || Objects.nonNull(morale)) {
				if ("OPI".equalsIgnoreCase(mode) && numeroTraite != null) {
					System.out.println("Souscription BAn par OPI N° " + numeroTraite);
					EuTraite traite = traiteRepo.findOne(numeroTraite);
					if (Objects.nonNull(traite) && traite.getTraitePayer() == 0
							&& date.compareTo(traite.getTraiteDateFin()) >= 0) {
						EuTpagcp tpagcp = tpagcpRepo.findOne(traite.getTraiteTegcp());
						if (Objects.nonNull(tpagcp)) {
							String codeBan = doBan(date, morale, membre, null, traite.getTraiteId(),
									traite.getTraiteMontant());
							tpagcp.setMontEchange(tpagcp.getMontEchange() + traite.getTraiteMontant());
							tpagcp.setSolde(tpagcp.getSolde() - traite.getTraiteMontant());
							tpagcp.setResteNtf(tpagcp.getResteNtf() - 1);
							tpagcpRepo.save(tpagcp);

							traite.setTraitePayer(1);
							traite.setTraiteImprimer(1);
							traite.setTraiteNumero(codeBan);
							traiteRepo.save(traite);
						}
					}
				} else {
					System.out.println("Souscription BAn par ELI N° " + codeRecu);
					Optional<EuEli> euEli = eliRepository.findByNumeroEli(codeRecu);
					if (euEli.isPresent() && euEli.get().getBan()) {
						double montantBan = euEli.get().getMontantBan();
						if (montantBan > 0) {
							double taux = parametresService.getParametre("BAN", "TxAppro");
							double mont_ban = montantBan + (montantBan * taux) / 100;
							doBan(date, morale, membre, euEli.get().getNumeroEli(), null, mont_ban);
						}
					}
				}
			}
		}

	}

	public String doBan(Date date, EuMembreMorale morale, EuMembre membre, String numeroEli, Long idTraite,
			double montantBan) {
		String result = StringUtils.EMPTY;
		if ((StringUtils.isNotBlank(numeroEli) || idTraite != null)
				&& (Objects.nonNull(membre) || Objects.nonNull(morale)) && montantBan > 0) {
			try {
				String codeBan = ServerUtil.GenererUniqueCodeForBan();
				String codeMembre = StringUtils.EMPTY;
				if (Objects.nonNull(membre)) {
					codeMembre = membre.getCodeMembre();
				} else {
					codeMembre = morale.getCodeMembreMorale();
				}
				EuBan ban = banService.emettreBanBis(codeMembre, montantBan);
				if (Objects.nonNull(ban)) {
					EuBanVendu banVendu = new EuBanVendu();
					banVendu.setCodeMembre(codeMembre);
					banVendu.setDateBanVendu(date);
					banVendu.setEuBan(ban);
					banVendu.setMontVendu(BigDecimal.valueOf(montantBan));
					if (StringUtils.isNotBlank(numeroEli)) {
						banVendu.setNumeroRecu(numeroEli);
					} else {
						banVendu.setNumeroRecu(String.valueOf(idTraite));
					}
					banVendu.setIdUser(null);
					banVenduService.add(banVendu);

					ban.setMontVendu(ban.getMontVendu().add(BigDecimal.valueOf(montantBan)));
					ban.setSolde(ban.getMontEmis().subtract(ban.getMontVendu()));
					banService.update(ban);

					EuBonNeutre bnNeutre = bnNeutreRepo.findByBonNeutreCodeMembre(codeMembre);
					if (Objects.nonNull(bnNeutre)) {
						bnNeutre.setBonNeutreCode(codeBan);
						bnNeutre.setBonNeutreMontant(bnNeutre.getBonNeutreMontant() + montantBan);
						bnNeutre.setBonNeutreMontantSolde(bnNeutre.getBonNeutreMontantSolde() + montantBan);
						bnNeutreRepo.save(bnNeutre);
					} else {
						bnNeutre = new EuBonNeutre();
						bnNeutre.setBonNeutreCode(codeBan);
						bnNeutre.setBonNeutreCodebarre(null);
						bnNeutre.setBonNeutreCodeMembre(codeMembre);
						bnNeutre.setBonNeutreDate(date);
						if (Objects.nonNull(morale)) {
							bnNeutre.setBonNeutreEmail(morale.getEmailMembre());
							bnNeutre.setBonNeutreMobile(morale.getTelMembre());
							bnNeutre.setBonNeutreRaison(morale.getRaisonSociale());
						} else {
							bnNeutre.setBonNeutreNom(membre.getNomMembre());
							bnNeutre.setBonNeutrePrenom(membre.getPrenomMembre());
							bnNeutre.setBonNeutreEmail(membre.getEmailMembre());
							bnNeutre.setBonNeutreMobile(membre.getTelMembre());
						}
						bnNeutre.setBonNeutreMontant(montantBan);
						bnNeutre.setBonNeutreMontantUtilise(0);
						bnNeutre.setBonNeutreMontantSolde(montantBan);
						bnNeutre.setBonNeutreType("BAn");
						bnNeutreRepo.save(bnNeutre);
					}

					EuBonNeutreDetail bnNeutreDetail = new EuBonNeutreDetail();
					bnNeutreDetail.setBonNeutreApproId(null);
					bnNeutreDetail.setBonNeutreDetailCode(codeBan);
					bnNeutreDetail.setBonNeutreDetailDate(date);
					bnNeutreDetail.setBonNeutreDetailMontant(montantBan);
					bnNeutreDetail.setBonNeutreDetailMontantSolde(montantBan);
					bnNeutreDetail.setBonNeutreDetailMontantUtilise(0);
					bnNeutreDetail.setBonNeutreDetailDateNumero(date);
					if (StringUtils.isNotBlank(numeroEli)) {
						bnNeutreDetail.setBonNeutreDetailNumero(numeroEli);
						bnNeutreDetail.setBonNeutreDetailType("ELI");
						bnNeutreDetail.setBonNeutreDetailBanque("ELI");
					} else {
						bnNeutreDetail.setBonNeutreDetailNumero(String.valueOf(idTraite));
						bnNeutreDetail.setBonNeutreDetailType("OPI");
						bnNeutreDetail.setBonNeutreDetailBanque("OPI");
					}
					bnNeutreDetail.setBonNeutreDetailVignette(null);
					bnNeutreDetail.setBonNeutreTiersId(null);
					bnNeutreDetail.setEuBonNeutre(bnNeutre);
					if (Objects.nonNull(morale) && Objects.nonNull(morale.getEuCanton())) {
						bnNeutreDetail.setIdCanton(morale.getEuCanton().getIdCanton());
					} else if (Objects.nonNull(membre) && Objects.nonNull(membre.getEuCanton())) {
						bnNeutreDetail.setIdCanton(membre.getEuCanton().getIdCanton());
					}
					bnNeutreDetRepo.save(bnNeutreDetail);
					result = codeBan;
				}
			} catch (Exception e) {
				log.error("Echec de l'écriture du BAn ", e);
			}
		}
		return result;
	}

	public String doBan(Date date, EuMembreMorale morale, EuMembre membre, String codeMembreBenef, String typeBan,
			String numeroEli, Long idTraite, double montantBan) {
		System.out.println("Doing BAn");
		String result = StringUtils.EMPTY;
		if ((StringUtils.isNotBlank(numeroEli) || idTraite != null)
				&& (Objects.nonNull(membre) || Objects.nonNull(morale)) && montantBan > 0) {
			try {
				String codeBan = ServerUtil.GenererUniqueCodeForBan();
				String codeMembre = StringUtils.EMPTY;
				if (Objects.nonNull(membre)) {
					codeMembre = membre.getCodeMembre();
				} else {
					codeMembre = morale.getCodeMembreMorale();
				}
				EuBan ban = banService.emettreBanBis(codeMembre, montantBan);
				if (Objects.nonNull(ban)) {
					EuMembre membre_benef = null;
					EuMembreMorale morale_benef = null;
					if (codeMembreBenef.endsWith("P")) {
						membre_benef = membreRepository.findByCodeMembre(codeMembreBenef);
					} else {
						morale_benef = moraleRepository.findByKey(codeMembreBenef);
					}
					EuBanVendu banVendu = new EuBanVendu();
					banVendu.setCodeMembre(codeMembreBenef);
					banVendu.setDateBanVendu(date);
					banVendu.setEuBan(ban);
					banVendu.setMontVendu(BigDecimal.valueOf(montantBan));
					if (StringUtils.isNotBlank(numeroEli)) {
						banVendu.setNumeroRecu(numeroEli);
					} else {
						banVendu.setNumeroRecu(String.valueOf(idTraite));
					}
					banVendu.setIdUser(null);
					banVenduService.add(banVendu);

					ban.setMontVendu(ban.getMontVendu().add(BigDecimal.valueOf(montantBan)));
					ban.setSolde(ban.getMontEmis().subtract(ban.getMontVendu()));
					banService.update(ban);

					EuBonNeutre bnNeutre = bnNeutreRepo.findByBonNeutreCodeMembre(codeMembreBenef);
					if (Objects.nonNull(bnNeutre)) {
						bnNeutre.setBonNeutreCode(codeBan);
						bnNeutre.setBonNeutreMontant(bnNeutre.getBonNeutreMontant() + montantBan);
						bnNeutre.setBonNeutreMontantSolde(bnNeutre.getBonNeutreMontantSolde() + montantBan);
						bnNeutreRepo.save(bnNeutre);
					} else {
						bnNeutre = new EuBonNeutre();
						bnNeutre.setBonNeutreCode(codeBan);
						bnNeutre.setBonNeutreCodebarre(null);
						bnNeutre.setBonNeutreCodeMembre(codeMembreBenef);
						bnNeutre.setBonNeutreDate(date);
						if (Objects.nonNull(morale_benef)) {
							bnNeutre.setBonNeutreEmail(morale_benef.getEmailMembre());
							bnNeutre.setBonNeutreMobile(morale_benef.getTelMembre());
							bnNeutre.setBonNeutreRaison(morale_benef.getRaisonSociale());
						} else {
							bnNeutre.setBonNeutreNom(membre_benef.getNomMembre());
							bnNeutre.setBonNeutrePrenom(membre_benef.getPrenomMembre());
							bnNeutre.setBonNeutreEmail(membre_benef.getEmailMembre());
							bnNeutre.setBonNeutreMobile(membre_benef.getTelMembre());
						}
						bnNeutre.setBonNeutreMontant(montantBan);
						bnNeutre.setBonNeutreMontantUtilise(0);
						bnNeutre.setBonNeutreMontantSolde(montantBan);
						bnNeutre.setBonNeutreType("BAn");
						bnNeutreRepo.save(bnNeutre);
					}

					EuBonNeutreDetail bnNeutreDetail = new EuBonNeutreDetail();
					bnNeutreDetail.setBonNeutreApproId(null);
					bnNeutreDetail.setBonNeutreDetailCode(codeBan);
					bnNeutreDetail.setBonNeutreDetailDate(date);
					bnNeutreDetail.setBonNeutreDetailMontant(montantBan);
					bnNeutreDetail.setBonNeutreDetailMontantSolde(montantBan);
					bnNeutreDetail.setBonNeutreDetailMontantUtilise(0);
					bnNeutreDetail.setBonNeutreDetailDateNumero(date);
					if (StringUtils.isNotBlank(numeroEli)) {
						bnNeutreDetail.setBonNeutreDetailNumero(numeroEli);
						bnNeutreDetail.setBonNeutreDetailType(typeBan);
						bnNeutreDetail.setBonNeutreDetailBanque("ELI");
					} else {
						bnNeutreDetail.setBonNeutreDetailNumero(String.valueOf(idTraite));
						bnNeutreDetail.setBonNeutreDetailType("OPI");
						bnNeutreDetail.setBonNeutreDetailBanque("OPI");
					}
					bnNeutreDetail.setBonNeutreDetailVignette(null);
					bnNeutreDetail.setBonNeutreTiersId(null);
					bnNeutreDetail.setEuBonNeutre(bnNeutre);
					if (Objects.nonNull(morale_benef) && Objects.nonNull(morale_benef.getEuCanton())) {
						bnNeutreDetail.setIdCanton(morale_benef.getEuCanton().getIdCanton());
					} else if (Objects.nonNull(membre_benef) && Objects.nonNull(membre_benef.getEuCanton())) {
						bnNeutreDetail.setIdCanton(membre_benef.getEuCanton().getIdCanton());
					}
					bnNeutreDetRepo.save(bnNeutreDetail);
					result = codeBan;
				}
			} catch (Exception e) {
				log.error("Echec de l'écriture du BAn ", e);
			}
		}
		return result;
	}
}

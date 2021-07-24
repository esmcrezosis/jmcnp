package com.esmc.mcnp.components.auto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.components.BnpComponent;
import com.esmc.mcnp.components.CreditComponent;
import com.esmc.mcnp.components.CreditUtility;
import com.esmc.mcnp.components.EchangeUtility;
import com.esmc.mcnp.components.Payement;
import com.esmc.mcnp.components.SmcipnComponent;
import com.esmc.mcnp.components.SouscriptionBon;
import com.esmc.mcnp.components.TransfertUtility;
import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dto.bc.CalculBonInfo;
import com.esmc.mcnp.dto.other.Bnp;
import com.esmc.mcnp.model.ba.EuNn;
import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.bc.EuCnnc;
import com.esmc.mcnp.model.bc.EuCnp;
import com.esmc.mcnp.model.bc.EuDetailDomicilie;
import com.esmc.mcnp.model.bc.EuDomiciliation;
import com.esmc.mcnp.model.bc.EuProduit;
import com.esmc.mcnp.model.bc.EuRenouvellement;
import com.esmc.mcnp.model.cm.EuCategorieCompte;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteBancaire;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.cm.EuCompteCreditCapa;
import com.esmc.mcnp.model.cm.EuCompteCreditTs;
import com.esmc.mcnp.model.cm.EuCompteGeneral;
import com.esmc.mcnp.model.cm.EuCompteGeneralPK;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.cm.EuTypeCompte;
import com.esmc.mcnp.model.config.EuParametresPK;
import com.esmc.mcnp.model.mprg.EuDetailKrr;
import com.esmc.mcnp.model.mprg.EuKrr;
import com.esmc.mcnp.model.obps.EuTegc;
import com.esmc.mcnp.model.obpsd.EuCompensation;
import com.esmc.mcnp.model.obpsd.EuEscompte;
import com.esmc.mcnp.model.obpsd.EuTpagcp;
import com.esmc.mcnp.model.obpsd.EuTraite;
import com.esmc.mcnp.model.obpsd.EuTypeNn;
import com.esmc.mcnp.model.obpsd.EuUtiliserNn;
import com.esmc.mcnp.model.oi.EuBnp;
import com.esmc.mcnp.model.oi.EuBnpCredit;
import com.esmc.mcnp.model.oi.EuCaps;
import com.esmc.mcnp.model.smcipn.EuGcsc;
import com.esmc.mcnp.repositories.bc.EuCnncRepository;
import com.esmc.mcnp.repositories.bc.EuCnpRepository;
import com.esmc.mcnp.repositories.cm.EuCompteCreditRepository;
import com.esmc.mcnp.repositories.cm.EuCompteCreditTsRepository;
import com.esmc.mcnp.repositories.cm.EuCompteGeneralRepository;
import com.esmc.mcnp.repositories.cm.EuCompteRepository;
import com.esmc.mcnp.repositories.cm.EuMembreMoraleRepository;
import com.esmc.mcnp.repositories.cm.EuMembreRepository;
import com.esmc.mcnp.repositories.common.EuParametreRepository;
import com.esmc.mcnp.repositories.mprg.EuDetailKrrRepository;
import com.esmc.mcnp.repositories.mprg.EuKrrRepository;
import com.esmc.mcnp.repositories.obps.EuGcscRepository;
import com.esmc.mcnp.repositories.obpsd.EuEscompteRepository;
import com.esmc.mcnp.repositories.obpsd.EuNnRepository;
import com.esmc.mcnp.repositories.obpsd.EuTpagcpRepository;
import com.esmc.mcnp.repositories.obpsd.EuUtiliserNnRepository;
import com.esmc.mcnp.repositories.oi.EuBnpCreditRepository;
import com.esmc.mcnp.repositories.oi.EuBnpRepository;
import com.esmc.mcnp.repositories.oi.EuCapsRepository;
import com.esmc.mcnp.repositories.others.EuCompensationRepository;
import com.esmc.mcnp.repositories.others.EuDetailDomiciliationRepository;
import com.esmc.mcnp.repositories.others.EuDomiciliationRepository;
import com.esmc.mcnp.repositories.others.RenouvellementRepository;
import com.esmc.mcnp.services.bc.EuBonService;
import com.esmc.mcnp.services.bc.EuCnncService;
import com.esmc.mcnp.services.bc.EuCnpService;
import com.esmc.mcnp.services.cm.EuCompteBancaireService;
import com.esmc.mcnp.services.cm.EuCompteCreditCapaService;
import com.esmc.mcnp.services.cm.EuCompteGeneralService;
import com.esmc.mcnp.services.obps.EuTegcService;
import com.esmc.mcnp.services.obpsd.EuTraiteService;

/**
 * @author Mawuli
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RenouvellementService {

	private final EuCompteCreditRepository creditRepo;
	private final EuCompteCreditTsRepository creditTsRepo;
	private final EuCompteRepository compteRepo;
	private final EuCnpRepository cnpRepo;
	private final EuMembreRepository membreRepo;
	private final EuMembreMoraleRepository moralRepo;
	private final RenouvellementRepository renouvRepo;
	private final EuCompteGeneralRepository cgRepo;
	private final EuCnncRepository cnncRepo;
	private final EuParametreRepository parametreRepo;
	private final EuCapsRepository capsRepo;
	private final EuDetailDomiciliationRepository detailDomRepo;
	private final EuDomiciliationRepository domiRepo;
	private final EuGcscRepository gcscRepo;
	private final EuKrrRepository krrRepo;
	private final EuNnRepository nnRepo;
	private final EuTpagcpRepository tpagcpRepo;
	private final EuEscompteRepository escRepo;
	private final EuCompensationRepository compensRepo;
	private final EuBnpCreditRepository bnpCreditRepo;
	private final EuBnpRepository bnpRepo;
	private final BnpComponent bnpService;
	private final CreditUtility creditUtility;
	private final CreditComponent creditComponent;
	private final EuDetailKrrRepository detKrrRepo;
	private final EuUtiliserNnRepository utiliserNnRepo;
	private final EuCompteGeneralService cgService;
	private final EuCnncService cnncService;
	private final EuCnpService cnpService;
	private final EuTraiteService traiteService;
	private final EuCompteCreditCapaService ccCapaService;
	private final EchangeUtility echangeUtility;
	private final TransfertUtility transfertUtility;
	private final SouscriptionBon souscriptionBon;
	private final Payement payement;
	private final EuTegcService tegcService;
	private final SmcipnComponent smcipnComponent;
	private final EuCompteBancaireService compteBancaireService;
	private final EuBonService bonService;

	private final Logger logger = LogManager.getLogger(RenouvellementService.class.getName());

	public RenouvellementService(EuCompteCreditRepository creditRepo, EuCompteCreditTsRepository creditTsRepo,
			EuCompteRepository compteRepo, EuCnpRepository cnpRepo, EuMembreRepository membreRepo,
			EuMembreMoraleRepository moralRepo, RenouvellementRepository renouvRepo, EuCompteGeneralRepository cgRepo,
			EuCnncRepository cnncRepo, EuParametreRepository parametreRepo, EuCapsRepository capsRepo,
			EuDetailDomiciliationRepository detailDomRepo, EuDomiciliationRepository domiRepo,
			EuGcscRepository gcscRepo, EuKrrRepository krrRepo, EuNnRepository nnRepo, EuTpagcpRepository tpagcpRepo,
			EuEscompteRepository escRepo, EuCompensationRepository compensRepo, EuBnpCreditRepository bnpCreditRepo,
			EuBnpRepository bnpRepo, BnpComponent bnpService, CreditUtility creditUtility,
			EuDetailKrrRepository detKrrRepo, EuUtiliserNnRepository utiliserNnRepo, EuCompteGeneralService cgService,
			EuCnncService cnncService, EuCnpService cnpService, EuTraiteService traiteService,
			EuCompteCreditCapaService ccCapaService, EchangeUtility echangeUtility, TransfertUtility transfertUtility,
			SouscriptionBon souscriptionBon, Payement payement, EuTegcService tegcService,
			SmcipnComponent smcipnComponent, EuCompteBancaireService compteBancaireService, EuBonService bonService, CreditComponent creditComponent) {
		this.creditRepo = creditRepo;
		this.creditTsRepo = creditTsRepo;
		this.compteRepo = compteRepo;
		this.cnpRepo = cnpRepo;
		this.membreRepo = membreRepo;
		this.moralRepo = moralRepo;
		this.renouvRepo = renouvRepo;
		this.cgRepo = cgRepo;
		this.cnncRepo = cnncRepo;
		this.parametreRepo = parametreRepo;
		this.capsRepo = capsRepo;
		this.detailDomRepo = detailDomRepo;
		this.domiRepo = domiRepo;
		this.gcscRepo = gcscRepo;
		this.krrRepo = krrRepo;
		this.nnRepo = nnRepo;
		this.tpagcpRepo = tpagcpRepo;
		this.escRepo = escRepo;
		this.compensRepo = compensRepo;
		this.bnpCreditRepo = bnpCreditRepo;
		this.bnpRepo = bnpRepo;
		this.bnpService = bnpService;
		this.creditUtility = creditUtility;
		this.creditComponent = creditComponent;
		this.detKrrRepo = detKrrRepo;
		this.utiliserNnRepo = utiliserNnRepo;
		this.cgService = cgService;
		this.cnncService = cnncService;
		this.cnpService = cnpService;
		this.traiteService = traiteService;
		this.ccCapaService = ccCapaService;
		this.echangeUtility = echangeUtility;
		this.transfertUtility = transfertUtility;
		this.souscriptionBon = souscriptionBon;
		this.payement = payement;
		this.tegcService = tegcService;
		this.smcipnComponent = smcipnComponent;
		this.compteBancaireService = compteBancaireService;
		this.bonService = bonService;
	}

	@Async
	@Scheduled(cron = "0 0 0 * * ?")
	public void renouvellerBonConsoJour() {
		Date date = new Date();
		List<String> produits = Arrays.asList("RPGr", "Ir");
		List<EuCompteCredit> credits = ListUtils.emptyIfNull(creditRepo.fetchAllCredits("O", 0, produits, 1));
		if (CollectionUtils.isNotEmpty(credits)) {
			System.out.println("Nombre de crédits à renouvller :" + credits.size());
			// Initialisation des variables
			int nbre_credit_nc = 0;
			int nbre_ts = 0;
			int nbre_credit = 0;
			double mont_nconso = 0;
			double mont_credit = 0;
			double mont_ncreditTs = 0;
			double total_mont_credit = 0;
			int periode = 1;

			LocalTime debut = LocalTime.now();
			for (EuCompteCredit cc : credits) {
				EuCompte compte = cc.getEuCompte();
				/**
				 * Récupération des crédits de transactions
				 */
				if (cc.getFrequenceCumul() == 1 || cc.getDatefin().before(date)) {
					List<EuCompteCreditTs> creditTs = creditTsRepo.findByIdCredit(cc.getIdCredit());
					if (CollectionUtils.isNotEmpty(creditTs)) {
						for (EuCompteCreditTs ts : creditTs) {
							EuCompte cpteTs = ts.getEuCompteCredit().getEuCompte();
							cpteTs.setSolde(cpteTs.getSolde() - ts.getMontant());
							compteRepo.save(cpteTs);

							cnncService.saveCnncTs(ts);

							cgService.saveCG("CNNC", "NB", "E", ts.getMontant());

							mont_nconso += ts.getMontant();
							ts.setMontant(0.0);
							creditTsRepo.save(ts);
						}
					}
					if (cc.getMontantCredit() > 0) {
						nbre_credit_nc++;
						mont_nconso += cc.getMontantCredit();

						compte.setSolde(compte.getSolde() - cc.getMontantCredit());
						compteRepo.save(compte);

						cnncService.saveCnnc(cc);

						cgService.saveCG("CNNC", "NB", "E", cc.getMontantCredit());

						cc.setMontantCredit(0.0);
						creditRepo.save(cc);
					}
				}

				CalculBonInfo bonInfo = new CalculBonInfo("r", cc.getTypeRecurrent(), cc.getTypeProduit(), 1, 0,
						cc.getFrequenceCumul(), cc.getIdBps());
				mont_credit = Math.round(creditUtility.calculBonConso(bonInfo, cc.getMontantPlace()));
				if (cc.getDuree() == 1 || cc.getDatefin().before(date)) {
					String source = cc.getCodeMembre() + DateFormatUtils.format(date, "yyyyMMddhhmmss");

					/**
					 * Creation du nouveau CNP et Mise à jour du compte général CNP Sorti
					 */
					EuCompteCreditCapa ccCapa = ccCapaService.findCreditCapaByIdCredit(cc.getIdCredit()).get(0);
					cnpService.createCnp(cc, ccCapa.getEuCapa(), null, source, date, mont_credit);

					cc.setMontantCredit(mont_credit);
					cc.setDatedeb(date);
					cc.setSource(source);
					if (cc.getFrequenceCumul() == 1) {
						cc.setDatefin(ServerUtil.ajouterJours(date, periode));
					} else {
						cc.setDatefin(ServerUtil.ajouterJours(date, cc.getFrequenceCumul()));
					}
					creditRepo.save(cc);
				} else {
					cnpService.updateCnp(cc.getIdCredit(), cc.getSource(), mont_credit);
					cc.setMontantCredit(mont_credit);
					creditRepo.save(cc);
				}

				cgService.saveCG("CNP", "NB", "S", mont_credit);

				compte.setSolde(compte.getSolde() + mont_credit);
				compteRepo.save(compte);

				total_mont_credit += mont_credit;
				nbre_credit += 1;
			}

			Long idRenouvel = renouvRepo.getLastEuRenouvllemmentInsertedId();
			if (idRenouvel == null) {
				idRenouvel = 1L;
			} else {
				idRenouvel += 1;
			}
			EuRenouvellement renouvellement = new EuRenouvellement();
			renouvellement.setIdRenouvellement(idRenouvel);
			renouvellement.setDateRenouvellement(new Date());
			renouvellement.setHeureDeb(debut);
			renouvellement.setTypeRenouvellement("BCJ");
			renouvellement.setHeureFin(LocalTime.now());
			renouvellement.setMontCredit(total_mont_credit);
			renouvellement.setMontNconso(mont_nconso + mont_ncreditTs);
			renouvellement.setNbreNconso(nbre_credit_nc + nbre_ts);
			renouvellement.setNbreCredit(nbre_credit);
			renouvRepo.save(renouvellement);
		}
	}

	@Async
	@Scheduled(cron = "0 0 0 * * ?")
	// @Scheduled(fixedRate = 1800000)
	public void renouvellerCredit() {
		Date now = new Date();
		List<String> produits = Arrays.asList("RPGr", "RPGnrPRE", "Ir", "InrPRE");
		List<Integer> bnps = Arrays.asList(0, 2);
		List<EuCompteCredit> credits = ListUtils.emptyIfNull(creditRepo.fetchAllCredits("O", bnps, produits, now, 1));
		if (!credits.isEmpty()) {
			logger.debug("Nombre de crédits à renouvller :" + credits.size());
			// Initialisation des variables
			EuMembre membre = null;
			int nbre_credit_nc = 0;
			int nbre_ts = 0;
			int nbre_credit = 0;
			double mont_nconso = 0;
			double mont_credit = 0;
			double mont_ncreditTs = 0;
			double total_mont_credit = 0;
			double pck = parametreRepo.findByCodeAndLib("pck", "r");
			double prk = parametreRepo.findByCodeAndLib("prk", "r");
			EuDomiciliation dom = null;
			int periode = (int) Math.floor(parametreRepo.findOne(new EuParametresPK("periode", "valeur")).getMontant());

			EuCompteGeneralPK id = new EuCompteGeneralPK();
			id.setCodeCompte("CNNC");
			id.setCodeTypeCompte("NN");
			id.setService("E");
			EuTypeCompte typeCpte = new EuTypeCompte();
			typeCpte.setCodeTypeCompte("NN");
			LocalTime debut = LocalTime.now();

			for (EuCompteCredit cc : credits) {
				try {
					EuCompte compte = cc.getEuCompte();
					logger.info("ID = : " + cc.getIdCredit() + " Durée : " + cc.getDuree());
					if (!cc.getEuProduit().getCodeProduit().endsWith("PRE")) {
						/**
						 * Récupération des crédits de transactions
						 */
						List<EuCompteCreditTs> creditTs = creditTsRepo.findByIdCredit(cc.getIdCredit());
						if (creditTs.size() > 0) {

							// Annulation des crédits non consommés avant terme sur
							// les comptes de transaction
							for (EuCompteCreditTs ts : creditTs) {
								nbre_ts += 1;
								mont_ncreditTs += ts.getMontant();

								EuCompte cpteTs = ts.getEuCompteCredit().getEuCompte();
								cpteTs.setSolde(cpteTs.getSolde() - ts.getMontant());
								compteRepo.save(cpteTs);

								cnncService.saveCnncTs(ts);

								cgService.saveCG("CNNC", "NB", "E", ts.getMontant());

								ts.setMontant(Double.valueOf("0"));
								creditTsRepo.save(ts);
							}
						}

						/**
						 * Annulation des crédits non consommés avant terme sur les comptes crédits
						 */
						if (cc.getMontantCredit() > 0) {
							nbre_credit_nc += 1;
							mont_nconso += cc.getMontantCredit();

							compte.setSolde(compte.getSolde() - cc.getMontantCredit());
							compteRepo.save(compte);

							cnncService.saveCnnc(cc);

							cgService.saveCG("CNNC", "NB", "E", cc.getMontantCredit());

							cc.setMontantCredit(0.0);
							creditRepo.save(cc);
						}
					}

					CalculBonInfo bonInfo = new CalculBonInfo("r", cc.getTypeRecurrent(), cc.getTypeProduit(),
							cc.getDuree(), prk, cc.getFrequenceCumul(), cc.getIdBps());
					mont_credit = Math.round(creditUtility.calculBonConso(bonInfo, cc.getMontantPlace()));
					double mont_bc = Math.round(cc.getMontantPlace() * prk / pck);
					double mont_bcr = mont_bc - mont_credit;
					String source = cc.getCodeMembre() + DateFormatUtils.format(now, "yyyyMMddhhmmss");
					double s_mont_credit = mont_credit;
					String auto_enroler = "O";
					if (cc.getCodeMembre().endsWith("P")) {
						membre = membreRepo.findOne(cc.getCodeMembre());
						auto_enroler = membre.getAutoEnroler();
					}

					if (cc.getTypeRecurrent().equalsIgnoreCase("limite") && cc.getNbreRenouvel() == 0
							&& cc.getRenouveller().equals("O")) {
						cc.setRenouveller("N");
						creditRepo.save(cc);
					} else {
						if (StringUtils.isBlank(auto_enroler) || auto_enroler.equals("O")) {
							if (cc.getDomicilier() == 1) {
								double reste_credit = 0;
								EuGcsc gcsc = null;
								EuDetailDomicilie detailDom = detailDomRepo.findByIdCreditAndUtiliser(cc.getIdCredit(),
										1);
								dom = detailDom.getEuDomiciliation();
								if (Objects.nonNull(dom.getEuSmcipnpwi())) {
									gcsc = gcscRepo.findByEuSmcipn_CodeSmcipn(dom.getEuSmcipnpwi().getCodeSmcipn());
								}
								if (dom.getResteDuree() == 0) {
									cc.setRenouveller("N");
									creditRepo.save(cc);
								} else {
									if (detailDom.getMontantCredit() < mont_credit) {
										reste_credit = mont_credit - detailDom.getMontantCredit();
									}
									if (dom.getMontantDomicilier() < dom.getMontantSubvent()) {
										double mont_dom_tot = dom.getMontantDomicilier() + detailDom.getMontantCredit();
										if (mont_dom_tot >= dom.getMontantSubvent()) {
											dom.setMontantDomicilier(dom.getMontantSubvent());
											dom.setResteDuree(0);
											dom.setDomicilier("O");
											domiRepo.save(dom);

											if (Objects.nonNull(gcsc)) {
												gcsc.setCredit(gcsc.getCredit()
														+ (dom.getMontantSubvent() - dom.getMontantDomicilier()));
												gcsc.setSolde(gcsc.getSolde()
														+ (dom.getMontantSubvent() - dom.getMontantDomicilier()));
												gcscRepo.save(gcsc);
											}

											reste_credit += (mont_dom_tot - dom.getMontantSubvent());

											cc.setMontantCredit(reste_credit);
											cc.setDatedeb(DateUtils.addDays(cc.getDatedeb(), periode));
											cc.setDatefin(DateUtils.addDays(cc.getDatefin(), periode));
											cc.setSource(source);
											cc.setRenouveller("N");
											creditRepo.save(cc);
										} else {
											dom.setResteDuree(dom.getResteDuree() - 1);
											dom.setMontantDomicilier(mont_dom_tot);
											domiRepo.save(dom);

											if (Objects.nonNull(gcsc)) {
												gcsc.setCredit(gcsc.getCredit() + detailDom.getMontantCredit());
												gcsc.setSolde(gcsc.getSolde() + detailDom.getMontantCredit());
												gcscRepo.save(gcsc);
											}

											cc.setMontantCredit(reste_credit);
											cc.setDatedeb(DateUtils.addDays(cc.getDatedeb(), periode));
											cc.setDatefin(DateUtils.addDays(cc.getDatefin(), periode));
											cc.setSource(source);
											creditRepo.save(cc);
										}
										compte.setSolde(compte.getSolde() + reste_credit);
										compteRepo.save(compte);
									} else {
										cc.setMontantCredit(mont_credit);
										cc.setDatedeb(DateUtils.addDays(cc.getDatedeb(), periode));
										cc.setDatefin(DateUtils.addDays(cc.getDatefin(), periode));
										cc.setSource(source);
										cc.setRenouveller("N");
										creditRepo.save(cc);

										compte.setSolde(compte.getSolde() + mont_credit);
										compteRepo.save(compte);
									}
								}
							} else {
								cc.setMontantCredit(mont_credit);
								cc.setDatedeb(DateUtils.addDays(cc.getDatedeb(), periode));
								cc.setDatefin(DateUtils.addDays(cc.getDatefin(), periode));
								cc.setSource(source);
								creditRepo.save(cc);

								compte.setSolde(compte.getSolde() + mont_credit);
								compteRepo.save(compte);
							}
						} else {
							double fs;
							double periode_fs = parametreRepo.findOne(new EuParametresPK("periode", "bnp"))
									.getMontant();
							EuCaps caps = capsRepo.findByCodeMembreBenef(cc.getCodeMembre());
							if (Objects.nonNull(caps) && caps.getMontCaps() > caps.getMontFs()) {
								fs = Math.floor(caps.getMontCaps() / periode_fs);
								mont_credit = mont_credit - fs;
								caps.setMontFs(caps.getMontFs() + fs);
								if (caps.getMontCaps() == caps.getMontFs()) {
									caps.setReconstFs(1);
									caps.setRembourser("O");
								}
								caps.setPeriode(caps.getPeriode() + 1);
								capsRepo.save(caps);
								String codeCompteFs;
								if (Objects.nonNull(caps.getEuMembre2())) {
									codeCompteFs = "NB-TFS" + caps.getEuMembre2().getCodeMembre();
								} else {
									codeCompteFs = "NB-TFS" + caps.getEuMembreMorale().getCodeMembreMorale();
								}
								EuCompte compteFs = compteRepo.findOne(codeCompteFs);
								if (Objects.nonNull(compteFs)) {
									compteFs.setSolde(compteFs.getSolde() + fs);
									compteRepo.save(compteFs);
								}
								List<EuCompteCredit> ccFss = creditRepo.findByEuCompte_CodeCompte(codeCompteFs);
								if (!ccFss.isEmpty() && ccFss.size() == 1) {
									EuCompteCredit ccFs = ccFss.get(0);
									ccFs.setMontantCredit(ccFs.getMontantCredit() + fs);
									creditRepo.save(ccFs);
								}
							}

							cc.setMontantCredit(mont_credit);
							cc.setDatedeb(DateUtils.addDays(cc.getDatedeb(), periode));
							cc.setDatefin(DateUtils.addDays(cc.getDatefin(), periode));
							cc.setSource(source);
							creditRepo.save(cc);

							compte.setSolde(compte.getSolde() + mont_credit);
							compteRepo.save(compte);
						}

						/**
						 * Mise à jour des PRE et des Ir et Krr RPGr
						 */
						logger.info("Mise à jour des PRE et des Ir");
						if (cc.getTypeRecurrent().equalsIgnoreCase("limite")) {
							cc.setNbreRenouvel(cc.getNbreRenouvel() - 1);
							if (cc.getNbreRenouvel() == 0) {
								cc.setRenouveller("N");
							}
							creditRepo.save(cc);
						} else if (cc.getTypeRecurrent().equals("illimite")) {
							EuKrr krr = krrRepo.findByIdCreditAndTypeKrr(cc.getIdCredit(), "krrBCRI");
							if (Objects.nonNull(krr)) {
								double mont_rec = krr.getMontReconst() + mont_bcr;
								if (cc.getTypeProduit().equals("PS")) {
									mont_rec = Math.round(krr.getMontReconst() + (mont_bcr / 2));
								}
								if (mont_rec >= krr.getMontKrr()) {
									krr.setMontReconst(krr.getMontKrr());
									krr.setDateEchue(now);
									krr.setReconstituer("O");
									krr.setPayer("O");
									krrRepo.save(krr);

									EuDetailKrr detKrr = new EuDetailKrr();
									detKrr.setIdCredit(cc.getIdCredit());
									detKrr.setIdKrr(krr.getIdKrr());
									detKrr.setSourceCredit(cc.getCompteSource());
									detKrr.setMontCredit(mont_bcr);
									detKrrRepo.save(detKrr);

									creditComponent.reconstituerCapaCreditRec(krr);
								} else {
									krr.setMontReconst(mont_rec);
									krr.setDateRenouveller(cc.getDatefin());
									krrRepo.save(krr);

									EuDetailKrr detKrr = new EuDetailKrr();
									detKrr.setIdCredit(cc.getIdCredit());
									detKrr.setIdKrr(krr.getIdKrr());
									detKrr.setSourceCredit(cc.getCompteSource());
									detKrr.setMontCredit(mont_bcr);
									detKrrRepo.save(detKrr);
								}
							} else {
								krr = new EuKrr();
								krr.setDateDemande(now);
								krr.setDateEchue(DateUtils.addDays(now, (int) Math.ceil(cc.getDuree() * 30)));
								krr.setDateRenouveller(DateUtils.addDays(now, 30));
								krr.setEuMembreMorale(null);
								krr.setEuMembre(membre);
								krr.setEuProduit(new EuProduit("RPGr"));
								krr.setIdCredit(cc.getIdCredit());
								krr.setMontCapa(cc.getMontantPlace());
								krr.setMontKrr(mont_bcr * 22.4);
								krr.setMontReconst(mont_bcr);
								krr.setTypeKrr("krrBCRI");
								krr.setPayer("N");
								krr.setReconstituer("N");
								krrRepo.save(krr);

								EuDetailKrr detKrr = new EuDetailKrr();
								detKrr.setIdCredit(cc.getIdCredit());
								detKrr.setIdKrr(krr.getIdKrr());
								detKrr.setSourceCredit(cc.getCompteSource());
								detKrr.setMontCredit(mont_bcr);
								detKrrRepo.save(detKrr);
							}
						}
					}

					/**
					 * Creation du nouveau CNP et Mise à jour du compte général CNP Sorti
					 */
					EuCompteCreditCapa ccCapa = ccCapaService.findCreditCapaByIdCredit(cc.getIdCredit()).get(0);
					cnpService.createCnp(cc, ccCapa.getEuCapa(), dom, source, now, s_mont_credit);

					cgService.saveCG("CNP", "NB", "S", s_mont_credit);

					total_mont_credit += s_mont_credit;
					nbre_credit += 1;
				} catch (Exception e) {
					logger.error("Error de traitement du crédit n° " + cc.getIdCredit(), e);
				}
			}

			Long idRenouvel = renouvRepo.getLastEuRenouvllemmentInsertedId();
			if (idRenouvel == null) {
				idRenouvel = 1L;
			} else {
				idRenouvel += 1;
			}
			EuRenouvellement renouvellement = new EuRenouvellement();
			renouvellement.setIdRenouvellement(idRenouvel);
			renouvellement.setDateRenouvellement(new Date());
			renouvellement.setHeureDeb(debut);
			renouvellement.setHeureFin(LocalTime.now());
			renouvellement.setMontCredit(total_mont_credit);
			renouvellement.setMontNconso(mont_nconso + mont_ncreditTs);
			renouvellement.setNbreNconso(nbre_credit_nc + nbre_ts);
			renouvellement.setNbreCredit(nbre_credit);
			renouvellement.setTypeRenouvellement("BCN");
			renouvRepo.save(renouvellement);
		}
	}

	// @Async
	// @Scheduled(cron = "0 0 0 * * ?")
	public void renouvellerCreditBnp() {
		Date now = new Date();
		List<String> produits = Arrays.asList("RPGr", "Ir");
		List<EuCompteCredit> credits = ListUtils.emptyIfNull(creditRepo.fetchAllCredits("O", 1, produits, 1, now));
		if (!credits.isEmpty()) {
			// Initialisation des variables
			int nbre_credit_nc = 0;
			EuCompteGeneral cg = null;
			int nbre_ts = 0;
			int nbre_credit = 0;
			double mont_nconso = 0;
			double mont_credit = 0;
			double mont_ncreditTs = 0;
			double total_mont_credit = 0;
			double prk = parametreRepo.findOne(new EuParametresPK("prk", "r")).getMontant();
			double pck = parametreRepo.findOne(new EuParametresPK("pck", "r")).getMontant();
			int periode = (int) Math.floor(parametreRepo.findOne(new EuParametresPK("periode", "valeur")).getMontant());
			float pBnp = (float) parametreRepo.findOne(new EuParametresPK("periode", "RBNP")).getMontant();
			LocalTime debut = LocalTime.now();
			for (EuCompteCredit cc : credits) {
				boolean autoEnrller = creditUtility.isAutoEnroller(cc.getCodeMembre());
				EuBnp bnp = bnpRepo.findOne(cc.getCodeBnp());
				EuBnpCredit bnpCredit = bnpCreditRepo.getBnpCreditByCodeBnp(cc.getCodeBnp());
				EuCompte compte = cc.getEuCompte();
				if (Objects.nonNull(bnp) && Objects.nonNull(bnpCredit)) {
					if (!bnp.getEuTypeBnp().getCodeTypeBnp().endsWith("PREKITTEC")
							&& (cc.getDomicilier() == 0 || cc.getDomicilier() == null)) {
						/**
						 * Récupération et annulation des crédits de transactions non consommés
						 */

						EuCompteGeneralPK id = new EuCompteGeneralPK("CNNC", "NN", "E");
						EuTypeCompte typeCpte = new EuTypeCompte("NN");
						List<EuCompteCreditTs> creditTs = creditTsRepo.findByIdCredit(cc.getIdCredit());
						if (!creditTs.isEmpty()) {

							// Annulation des crédits non consommés avant terme
							// sur
							// les comptes de transaction
							for (EuCompteCreditTs ts : creditTs) {
								nbre_ts += 1;
								mont_ncreditTs += ts.getMontant();

								EuCompte cpteTs = ts.getEuCompteCredit().getEuCompte();
								cpteTs.setSolde(cpteTs.getSolde() - ts.getMontant());
								compteRepo.save(cpteTs);

								Long idCnnc = cnncRepo.getLastEuCnncInsertedId();
								if (idCnnc == null) {
									idCnnc = 1L;
								} else {
									idCnnc += 1;
								}
								EuCnnc cnnc = new EuCnnc();
								cnnc.setIdCnnc(idCnnc);
								cnnc.setCodeMembre(ts.getEuCompteCredit().getCodeMembre());
								cnnc.setDatefin(ts.getEuCompteCredit().getDatefin());
								cnnc.setEuCompteCredit(cc);
								cnnc.setLibelle("Credit Non consomme");
								cnnc.setMontCredit(ts.getMontant());
								cnnc.setMontUtilise(0);
								cnnc.setSolde(ts.getMontant());
								cnnc.setSourceCredit(ts.getEuCompteCredit().getSource());
								cnncRepo.save(cnnc);

								cg = cgRepo.findOne(id);
								if (Objects.nonNull(cg)) {
									cg.setSolde(cg.getSolde() + ts.getMontant());
									cgRepo.save(cg);
								} else {
									cg = new EuCompteGeneral();
									cg.setId(id);
									cg.setEuTypeCompte(typeCpte);
									cg.setIntitule("Credit Non consomme");
									cg.setSolde(cg.getSolde() + ts.getMontant());
									cgRepo.save(cg);
								}

								ts.setMontant(Double.valueOf("0"));
								creditTsRepo.save(ts);
							}
						}

						/**
						 * Annulation des crédits non consommés avant terme sur le compte crédit
						 */
						if (cc.getMontantCredit() > 0) {
							nbre_credit_nc += 1;
							mont_nconso += cc.getMontantCredit();

							compte.setSolde(compte.getSolde() - cc.getMontantCredit());
							compteRepo.save(compte);

							Long idCnnc = cnncRepo.getLastEuCnncInsertedId();
							if (idCnnc == null) {
								idCnnc = 1L;
							} else {
								idCnnc += 1;
							}
							EuCnnc cnnc = new EuCnnc();
							cnnc.setIdCnnc(idCnnc);
							cnnc.setCodeMembre(cc.getCodeMembre());
							cnnc.setDatefin(cc.getDatefin());
							cnnc.setEuCompteCredit(cc);
							cnnc.setLibelle("Crédit Non consommé");
							cnnc.setMontCredit(cc.getMontantCredit());
							cnnc.setMontUtilise(0);
							cnnc.setSolde(cc.getMontantCredit());
							cnnc.setSourceCredit(cc.getSource());
							cnncRepo.save(cnnc);

							cg = cgRepo.findOne(id);
							if (Objects.nonNull(cg)) {
								cg.setSolde(cg.getSolde() + cc.getMontantCredit());
								cgRepo.save(cg);
							} else {
								cg = new EuCompteGeneral();
								cg.setId(id);
								cg.setEuTypeCompte(typeCpte);
								cg.setIntitule("Credit Non consomme");
								cg.setSolde(cg.getSolde() + cc.getMontantCredit());
								cgRepo.save(cg);
							}
						}
					} // Fin vidage des comptes non consommés

					/**
					 * Calcul du bon de consommation et sa repartition
					 */
					if ((bnp.getMontantBnp() > bnp.getMontPar()) || bnp.getReconstPar().equals("N")) {
						String source = cc.getCodeMembre() + DateFormatUtils.format(now, "yyyyMMddhhmmss");
						mont_credit = (cc.getMontantCredit() * prk) / pck;
						double fs = 0;
						double panu = 0;
						double par = 0;
						double conus = 0;
						double rcapa = 0;
						if (!bnp.getEuTypeBnp().getCodeTypeBnp().equals("CACB")
								&& !bnp.getEuTypeBnp().getCodeTypeBnp().equals("CSCOE")) {
							par = mont_credit * bnp.getEuTypeBnp().getTxPar() / 100;
							conus = mont_credit * bnp.getEuTypeBnp().getTxConus() / 100;
							if (!autoEnrller) {
								fs = mont_credit * bnp.getEuTypeBnp().getTxFs();
								if (!bnp.getEuTypeBnp().getCodeTypeBnp().equals("CAIPC")) {
									double mont_rest_bnp = bnp.getMontantBnp() - bnp.getMontPar();
									double p_credit = mont_rest_bnp * prk / pck;
									panu = p_credit * bnp.getEuTypeBnp().getTxPanu() / 100;
								}
							} else if (!bnp.getEuTypeBnp().getCodeTypeBnp().equals("CAIPC")) {
								double mont_rest_bnp = bnp.getMontantBnp() - bnp.getMontPar();
								double p_credit = mont_rest_bnp * prk / pck;
								panu = p_credit * (bnp.getEuTypeBnp().getTxPanu() + bnp.getEuTypeBnp().getTxFs()) / 100;
							}
						} else {
							double tbcp = parametreRepo.findOne(new EuParametresPK("TBCP", "valeur")).getMontant();
							par = mont_credit * bnp.getEuTypeBnp().getTxPar() / 100;
							panu = mont_credit * bnp.getEuTypeBnp().getTxPanu() / 100;
							conus = mont_credit * bnp.getEuTypeBnp().getTxConus() / 100;
							rcapa = mont_credit * tbcp / 100;
						}

						EuMembre membreApp = null;
						EuMembreMorale moralApp = null;
						if (bnp.getCodeMembreApp().endsWith("P")) {
							membreApp = membreRepo.findOne(bnp.getCodeMembreApp());
						} else {
							moralApp = moralRepo.findOne(bnp.getCodeMembreApp());
						}

						// Enregistrement de la repartition du BC récurrent en
						// PaR, PaNu et FS
						EuKrr krrBnp = null;
						if (bnp.getNatureBnp().equals("BNP")) {
							/**
							 * Mise à jour de la PaR
							 */
							String codeComptePar = "NB-TPaR-" + bnp.getCodeMembreApp();
							EuCompte parCompte = compteRepo.findOne(codeComptePar);
							List<EuCompteCredit> ccspar = ListUtils.emptyIfNull(
									creditRepo.findByCompteandSource(codeComptePar, String.valueOf(cc.getIdCredit())));
							if (Objects.nonNull(parCompte)) {
								parCompte.setSolde(parCompte.getSolde() + par);
								compteRepo.save(parCompte);
								if (!ccspar.isEmpty()) {
									EuCompteCredit ccpar = ccspar.get(0);
									ccpar.setMontantCredit(ccpar.getMontantCredit() + par);
									creditRepo.save(ccpar);
								}
							}

						} else {
							krrBnp = krrRepo.findByIdCreditAndTypeKrr(cc.getIdCredit(), "krrBNPP");
							if (Objects.nonNull(krrBnp)) {
								if (krrBnp.getMontKrr() > krrBnp.getMontReconst()) {
									double reste = krrBnp.getMontKrr() - krrBnp.getMontReconst();
									if (reste > par) {
										krrBnp.setMontReconst(krrBnp.getMontReconst() + par);
										krrBnp.setDateRenouveller(
												DateUtils.addDays(krrBnp.getDateRenouveller(), periode));
										krrRepo.save(krrBnp);

										EuDetailKrr detKrr = new EuDetailKrr();
										detKrr.setIdCredit(cc.getIdCredit());
										detKrr.setIdKrr(krrBnp.getIdKrr());
										detKrr.setSourceCredit(cc.getCompteSource());
										detKrr.setMontCredit(par);
										detKrrRepo.save(detKrr);
									} else {
										krrBnp.setMontReconst(krrBnp.getMontReconst() + reste);
										krrBnp.setDateRenouveller(
												DateUtils.addDays(krrBnp.getDateRenouveller(), periode));
										krrBnp.setReconstituer("O");
										krrBnp.setPayer("O");
										krrRepo.save(krrBnp);

										EuDetailKrr detKrr = new EuDetailKrr();
										detKrr.setIdCredit(cc.getIdCredit());
										detKrr.setIdKrr(krrBnp.getIdKrr());
										detKrr.setSourceCredit(cc.getCompteSource());
										detKrr.setMontCredit(reste);
										detKrrRepo.save(detKrr);

										EuMembre membre_sel = creditUtility.selectBnpMembre();
										if (Objects.nonNull(membre_sel)) {
											if (Objects.nonNull(membreApp)) {
												bnpService.doBnpPerenne(new Bnp(bnp.getEuTypeBnp().getCodeTypeBnp(),
														"BNPP", bnp.getCodeMembreApp(), membre_sel.getCodeMembre(),
														"NN", creditUtility.reconstituerCapaKrr(krrBnp, membreApp),
														bnp.getCodeBnp()));
											} else {
												bnpService.doBnpPerenne(new Bnp(bnp.getEuTypeBnp().getCodeTypeBnp(),
														"BNPP", bnp.getCodeMembreApp(), membre_sel.getCodeMembre(),
														"NN", creditUtility.reconstituerCapaKrr(krrBnp, moralApp),
														bnp.getCodeBnp()));
											}
										}
									}
								}
							} else {

								krrBnp = new EuKrr();
								krrBnp.setDateDemande(now);
								krrBnp.setDateEchue(
										DateUtils.addDays(krrBnp.getDateDemande(), (int) Math.ceil(22.4 * periode)));
								krrBnp.setDateRenouveller(DateUtils.addDays(krrBnp.getDateDemande(), periode));
								krrBnp.setEuProduit(cc.getEuProduit());
								krrBnp.setIdCredit(cc.getIdCredit());
								krrBnp.setMontCapa(cc.getMontantPlace());
								krrBnp.setMontKrr(bnp.getMontantBnp());
								krrBnp.setMontReconst(par);
								krrBnp.setReconstituer("N");
								if (bnp.getCodeMembreApp().endsWith("P")) {
									krrBnp.setEuMembre(membreApp);
								} else {
									krrBnp.setEuMembreMorale(moralApp);
								}
								krrBnp.setTypeKrr("krrBNPP");
								krrRepo.save(krrBnp);

								EuDetailKrr detKrr = new EuDetailKrr();
								detKrr.setIdCredit(cc.getIdCredit());
								detKrr.setIdKrr(krrBnp.getIdKrr());
								detKrr.setSourceCredit(cc.getCompteSource());
								detKrr.setMontCredit(par);
								detKrrRepo.save(detKrr);
							}
						}

						if (bnp.getEuTypeBnp().getCodeTypeBnp().equals("CACB")
								&& bnp.getEuTypeBnp().getCodeTypeBnp().equals("CSCOE")) {
							krrBnp = krrRepo.findByIdCreditAndTypeKrr(cc.getIdCredit(), "krrKBNP");
							if (Objects.nonNull(krrBnp)) {
								if (krrBnp.getReconstituer().equals("N")
										|| krrBnp.getMontKrr() > krrBnp.getMontReconst()) {
									double reste = krrBnp.getMontKrr() - krrBnp.getMontReconst();
									if (reste > par) {
										krrBnp.setMontReconst(krrBnp.getMontReconst() + par);
										krrBnp.setDateRenouveller(
												DateUtils.addDays(krrBnp.getDateRenouveller(), periode));
										krrRepo.save(krrBnp);

										EuDetailKrr detKrr = new EuDetailKrr();
										detKrr.setIdCredit(cc.getIdCredit());
										detKrr.setIdKrr(krrBnp.getIdKrr());
										detKrr.setSourceCredit(cc.getCompteSource());
										detKrr.setMontCredit(par);
										detKrrRepo.save(detKrr);
									} else {
										krrBnp.setMontReconst(krrBnp.getMontReconst() + reste);
										krrBnp.setDateRenouveller(
												DateUtils.addDays(krrBnp.getDateRenouveller(), periode));
										krrBnp.setReconstituer("O");
										krrBnp.setPayer("O");
										krrRepo.save(krrBnp);

										EuDetailKrr detKrr = new EuDetailKrr();
										detKrr.setIdCredit(cc.getIdCredit());
										detKrr.setIdKrr(krrBnp.getIdKrr());
										detKrr.setSourceCredit(cc.getCompteSource());
										detKrr.setMontCredit(reste);
										detKrrRepo.save(detKrr);

										creditComponent.reconstituerCapaCreditRec(krrBnp);
									}
								}
							} else {
								krrBnp = new EuKrr();
								krrBnp.setDateDemande(now);
								krrBnp.setDateEchue(
										DateUtils.addDays(krrBnp.getDateDemande(), (int) Math.ceil(22.4 * periode)));
								krrBnp.setDateRenouveller(DateUtils.addDays(krrBnp.getDateDemande(), periode));
								krrBnp.setEuProduit(cc.getEuProduit());
								krrBnp.setIdCredit(cc.getIdCredit());
								krrBnp.setMontCapa(cc.getMontantPlace());
								krrBnp.setMontKrr(rcapa * pBnp);
								krrBnp.setMontReconst(rcapa);
								krrBnp.setReconstituer("N");
								if (bnp.getCodeMembreApp().endsWith("P")) {
									krrBnp.setEuMembre(membreApp);
								} else {
									krrBnp.setEuMembreMorale(moralApp);
								}
								krrBnp.setTypeKrr("krrKBNP");
								krrRepo.save(krrBnp);

								EuDetailKrr detKrr = new EuDetailKrr();
								detKrr.setIdCredit(cc.getIdCredit());
								detKrr.setIdKrr(krrBnp.getIdKrr());
								detKrr.setSourceCredit(cc.getCompteSource());
								detKrr.setMontCredit(rcapa);
								detKrrRepo.save(detKrr);
							}
						}

						if (!bnp.getEuTypeBnp().getCodeTypeBnp().equals("CAIPC")) {
							/**
							 * Mise à jour de la PaNu
							 */
							String codeComptePaNu = "NB-TPaNu-" + bnp.getCodeMembreApp();
							EuCompte panuCompte = compteRepo.findOne(codeComptePaNu);
							if (Objects.nonNull(panuCompte)) {
								panuCompte.setSolde(panuCompte.getSolde() + panu);
							} else {
								panuCompte = new EuCompte();
								panuCompte.setCardprinteddate(null);
								panuCompte.setCardprintediddate(0);
								panuCompte.setCodeCompte(codeComptePaNu);
								panuCompte.setDateAlloc(new Date());
								panuCompte.setDesactiver("N");
								panuCompte.setEuCategorieCompte(new EuCategorieCompte("TPaNu"));
								panuCompte.setEuTypeCompte(new EuTypeCompte("NB"));
								if (bnp.getCodeMembreApp().endsWith("P")) {
									panuCompte.setEuMembre(membreApp);
								} else {
									panuCompte.setEuMembreMorale(moralApp);
								}
								panuCompte.setLibCompte("Compte de PaNu");
								panuCompte.setMifarecard(null);
								panuCompte.setNumeroCarte(null);
								panuCompte.setSolde(panu);
							}
							compteRepo.save(panuCompte);
						}
						if (!autoEnrller) {
							/**
							 * Mise à jour du FS
							 */
							String codeCompteFs = "NB-TFS-" + bnp.getCodeMembreApp();
							EuCompte fsCompte = compteRepo.findOne(codeCompteFs);
							if (Objects.nonNull(fsCompte)) {
								fsCompte.setSolde(fsCompte.getSolde() + fs);
							} else {
								fsCompte = new EuCompte();
								fsCompte.setCardprinteddate(null);
								fsCompte.setCardprintediddate(0);
								fsCompte.setCodeCompte(codeCompteFs);
								fsCompte.setDateAlloc(new Date());
								fsCompte.setDesactiver("N");
								fsCompte.setEuCategorieCompte(new EuCategorieCompte("TFS"));
								fsCompte.setEuTypeCompte(new EuTypeCompte("NB"));
								if (bnp.getCodeMembreApp().endsWith("P")) {
									fsCompte.setEuMembre(membreApp);
								} else {
									fsCompte.setEuMembreMorale(moralApp);
								}
								fsCompte.setLibCompte("Compte du FS");
								fsCompte.setMifarecard(null);
								fsCompte.setNumeroCarte(null);
								fsCompte.setSolde(fs);
							}
							compteRepo.save(fsCompte);
						}
						if (cc.getDomicilier() == 1) {
							Double reste_credit = 0.0;
							EuDetailDomicilie detailDom = detailDomRepo.findByIdCreditAndUtiliser(cc.getIdCredit(), 1);
							EuDomiciliation dom = detailDom.getEuDomiciliation();
							if (dom.getResteDuree() == 0) {
								cc.setRenouveller("N");
								creditRepo.save(cc);
							} else {
								if (detailDom.getMontantCredit() < mont_credit) {
									reste_credit = mont_credit - detailDom.getMontantCredit();
								}
								if (dom.getMontantDomicilier() < dom.getMontantSubvent()) {
									double mont_dom = dom.getMontantDomicilier() + detailDom.getMontantCredit();
									if (mont_dom >= dom.getMontantSubvent()) {
										dom.setMontantDomicilier(dom.getMontantSubvent());
										dom.setResteDuree(0);
										dom.setDomicilier("O");
										domiRepo.save(dom);

										cc.setMontantCredit(reste_credit);
										cc.setDatedeb(DateUtils.addDays(cc.getDatedeb(), periode));
										cc.setDatefin(DateUtils.addDays(cc.getDatefin(), periode));
										cc.setSource(source);
										cc.setRenouveller("N");
										creditRepo.save(cc);
									} else {
										dom.setResteDuree(dom.getResteDuree() - 1);
										dom.setMontantDomicilier(mont_dom);
										domiRepo.save(dom);

										cc.setMontantCredit(reste_credit);
										cc.setDatedeb(DateUtils.addDays(cc.getDatedeb(), periode));
										cc.setDatefin(DateUtils.addDays(cc.getDatefin(), periode));
										cc.setSource(source);
										creditRepo.save(cc);
									}
								} else {
									cc.setMontantCredit(reste_credit);
									cc.setDatedeb(DateUtils.addDays(cc.getDatedeb(), periode));
									cc.setDatefin(DateUtils.addDays(cc.getDatefin(), periode));
									cc.setSource(source);
									cc.setRenouveller("N");
									creditRepo.save(cc);
								}
								compte.setSolde(compte.getSolde() + reste_credit);
								compteRepo.save(compte);

								Long idCnp = cnpRepo.getLastCnpInsertedId();
								if (idCnp == null) {
									idCnp = 1L;
								} else {
									idCnp += 1;
								}
								EuCnp cnp = new EuCnp();
								cnp.setIdCnp(idCnp);
								cnp.setDateCnp(now);
								cnp.setEuCapa(null);
								cnp.setEuCompteCredit(cc);
								cnp.setEuDomiciliation(null);
								cnp.setMontCredit(0);
								cnp.setMontDebit(mont_credit);
								cnp.setOrigineCnp("FG" + cc.getEuProduit().getCodeProduit());
								cnp.setSoldeCnp(mont_credit);
								cnp.setSourceCredit(source);
								cnp.setTypeCnp(cc.getEuProduit().getCodeProduit());
								cnp.setTransfertGcp(0);
								cnpRepo.save(cnp);

								cg = cgRepo.findById("CNP", "NB", "S");
								if (Objects.nonNull(cg)) {
									cg.setSolde(cg.getSolde() + mont_credit);
									cgRepo.save(cg);
								} else {
									cg = new EuCompteGeneral();
									EuCompteGeneralPK cgpk = new EuCompteGeneralPK();
									cgpk.setCodeCompte("CNP");
									cgpk.setCodeTypeCompte("NB");
									cgpk.setService("S");
									cg.setId(cgpk);
									cg.setIntitule("Crédit en Nature Pérenne");
									cg.setSolde(mont_credit);
									cgRepo.save(cg);
								}
							}
						} else {
							cc.setMontantCredit(conus);
							cc.setDatedeb(DateUtils.addDays(cc.getDatedeb(), periode));
							cc.setDatefin(DateUtils.addDays(cc.getDatefin(), periode));
							cc.setSource(source);
							creditRepo.save(cc);

							compte.setSolde(compte.getSolde() + conus);
							compteRepo.save(compte);

							Long idCnp = cnpRepo.getLastCnpInsertedId();
							if (idCnp == null) {
								idCnp = 1L;
							} else {
								idCnp += 1;
							}
							EuCnp cnp = new EuCnp();
							cnp.setIdCnp(idCnp);
							cnp.setDateCnp(now);
							cnp.setEuCapa(null);
							cnp.setEuCompteCredit(cc);
							cnp.setEuDomiciliation(null);
							cnp.setMontCredit(0);
							cnp.setMontDebit(conus);
							cnp.setOrigineCnp("FG" + cc.getEuProduit().getCodeProduit());
							cnp.setSoldeCnp(conus);
							cnp.setSourceCredit(source);
							cnp.setTypeCnp(cc.getEuProduit().getCodeProduit());
							cnp.setTransfertGcp(0);
							cnpRepo.save(cnp);

							cg = cgRepo.findById("CNP", "NB", "S");
							if (Objects.nonNull(cg)) {
								cg.setSolde(cg.getSolde() + conus);
								cgRepo.save(cg);
							} else {
								cg = new EuCompteGeneral();
								EuCompteGeneralPK cgpk = new EuCompteGeneralPK();
								cgpk.setCodeCompte("CNP");
								cgpk.setCodeTypeCompte("NB");
								cgpk.setService("S");
								cg.setId(cgpk);
								cg.setIntitule("Crédit en Nature Pérenne");
								cg.setSolde(conus);
								cgRepo.save(cg);
							}
						}
					} else {
						cc.setBnp(2);
						creditRepo.save(cc);
					}
				}

				total_mont_credit += mont_credit;
				nbre_credit += 1;
			} // Fin test de BNP non null

			Long idRenouvel = renouvRepo.getLastEuRenouvllemmentInsertedId();
			if (idRenouvel == null) {
				idRenouvel = 1L;
			} else {
				idRenouvel += 1;
			}
			EuRenouvellement renouvellement = new EuRenouvellement();
			renouvellement.setIdRenouvellement(idRenouvel);
			renouvellement.setDateRenouvellement(new Date());
			renouvellement.setHeureDeb(debut);
			renouvellement.setHeureFin(LocalTime.now());
			renouvellement.setMontCredit(total_mont_credit);
			renouvellement.setMontNconso(mont_nconso + mont_ncreditTs);
			renouvellement.setNbreNconso(nbre_credit_nc + nbre_ts);
			renouvellement.setNbreCredit(nbre_credit);
			renouvRepo.save(renouvellement);
		}

	}

	@Async
	@Scheduled(cron = "0 0 0 * * ?")
	// @Scheduled(fixedRate = 15600000)
	public void traiteEchu() {
		Date date = new Date();
		List<EuTraite> traites = traiteService.findByDateFin(date);
		if (traites.size() > 0) {
			traites.forEach(t -> {
				EuTpagcp tpagcp = tpagcpRepo.findOne(t.getTraiteTegcp());
				if (Objects.nonNull(tpagcp)) {
					if (tpagcp.getReinjecter() != null && tpagcp.getReinjecter() == 1) {
						t.setTraiteDisponible(2);
					} else {
						t.setTraiteDisponible(1);
					}
					tpagcp.setMontEchu(tpagcp.getMontEchu() + t.getTraiteMontant());
					tpagcpRepo.save(tpagcp);
					traiteService.update(t);
				}
			});
		}

		List<EuTpagcp> tpagcps = tpagcpRepo.findAllByReinjecter(1);
		if (!tpagcps.isEmpty()) {
			tpagcps.parallelStream().forEach(t -> {
				Integer nbre = traiteService.getNbreTraiteDisponibleByTpa(t.getIdTpagcp()).intValue();
				if (nbre.equals(t.getNbreInjection())) {
					try {
						double montant = traiteService.getSumTraiteDisponibleByTpa(t.getIdTpagcp());
						List<EuTraite> rtraites = traiteService.findTraiteDisponibleByTpa(t.getIdTpagcp());
						String codeMembreSmc = "0010010010010000003M";
						EuTegc tegc = tegcService.findTegcByCodeMembreAndNomTegc(codeMembreSmc,
								"TE ACHETEUR-REVENDEUR");
						if (Objects.nonNull(tegc)) {
							if (echangeUtility.echangeOpiCommandeNr(t.getCodeMembre(), rtraites, montant)) {
								String typeCapa = "I";
								if (t.getCodeMembre().endsWith("P")) {
									typeCapa = "RPG";
								}
								EuBon ba = transfertUtility.transfertBA(t.getCodeMembre(), "NN", typeCapa, montant);
								if (Objects.nonNull(ba)) {
									CalculBonInfo bonInfo = new CalculBonInfo();
									bonInfo.setCatBon("nr");
									bonInfo.setTypeProduit("PS");
									bonInfo.setPrk(8);
									bonInfo.setDuree(8);
									double mont_bon = Math.floor(creditUtility.calculBonConso(bonInfo, montant));
									souscriptionBon.souscrireBonConso(t.getCodeMembre(), bonInfo, ba.getBonNumero(),
											montant);
									EuBon bc = transfertUtility.tansfertBC(t.getCodeMembre(), typeCapa, bonInfo, 8.0,
											mont_bon);
									if (Objects.nonNull(bc)) {
										EuBon bl = payement.createBonLivraison(codeMembreSmc, t.getCodeMembre(), "VPC",
												mont_bon);
										if (Objects.nonNull(bl)) {
											payement.makeTransaction(t.getCodeMembre(), codeMembreSmc, "TI", tegc, bc,
													bl, "", mont_bon);
											payement.creerMarge(new Date(), mont_bon);

											EuBon bld = payement.createBonLivraison(t.getCodeMembre(), codeMembreSmc,
													"APC", mont_bon);
											bld.setBonExprimer(1);
											bld.setBonDateExpression(date);
											bonService.update(bld);

											String numAppelOffre = smcipnComponent.doSmcipnInterm(codeMembreSmc,
													tegc.getCodeTegc(), t.getCodeMembre(), bld.getBonNumero(), null,
													mont_bon);
											if (StringUtils.isNotBlank(numAppelOffre)) {
												if (smcipnComponent.echangeNRNB(codeMembreSmc, numAppelOffre, "PO",
														mont_bon, 8.0)) {
													bonInfo = new CalculBonInfo();
													bonInfo.setCatBon("nr");
													bonInfo.setTypeProduit("PO");
													EuBon bon = transfertUtility.tansfertBC(codeMembreSmc, "TI",
															bonInfo, 8.0, mont_bon);
													if (Objects.nonNull(bon)) {
														String typeOp = "PC";
														EuTegc te = tegcService
																.findByCodeMembrePhysique(t.getCodeMembre()).get(0);
														payement.makeTransaction(t.getCodeMembre(), codeMembreSmc, "TI",
																te, bon, bl, typeOp, mont_bon);
														payement.creerMarge(new Date(), montant);

														EuCompteBancaire cb = compteBancaireService
																.getCompteBancaire(t.getCodeMembre(), "BOA");
														String typeOpi = "P";
														typeOpi = "PM";
														payement.emetreOpi(typeOpi, t.getCodeMembre(), te.getCodeTegc(),
																"", cb.getCodeBanque(), cb.getNumCompteBancaire(),
																t.getNtf(), mont_bon);

														t.setResteNtf(t.getResteNtf() - nbre);
														t.setMontEchange(t.getMontEchange() + montant);
														tpagcpRepo.save(t);
													}
												}
											}
										}
									}
								}
							}
						}
					} catch (Exception e) {
						logger.error("Echec de la réinjection de traite " + t.getIdTpagcp(), e);
					}
				} else {
					System.out.println("Aucune traite trouvée");
				}
			});
		}

	}

	// @Async
	// @Scheduled(cron = "0 0 0 * * ?")
	public void salaireMature() {
		Date date = new Date();
		List<EuCompteCredit> credits_cncs = creditRepo.findByProduitAndCompte("CNCS%", "NR-TCNCS-%");
		if (credits_cncs.isEmpty()) {
			System.out.println("Pas de crédits salaires matures!");
		} else {
			Integer periode = (int) Math
					.floor(parametreRepo.findOne(new EuParametresPK("periode", "valeur")).getMontant());
			credits_cncs.stream().map((EuCompteCredit cc) -> {
				EuTypeNn type_nn = new EuTypeNn();
				type_nn.setCodeTypeNn(cc.getEuProduit().getCodeProduit());
				EuNn nn = new EuNn();
				nn.setDateEmission(date);
				nn.setEuMembreMorale(null);
				nn.setEuTypeNn(type_nn);
				nn.setIdUtilisateur(null);
				nn.setMontantEmis(cc.getMontantCredit());
				nn.setMontantRemb(cc.getMontantCredit());
				nn.setSoldeNn(0.0);
				nnRepo.save(nn);

				EuUtiliserNn utilNn = new EuUtiliserNn();
				utilNn.setCodeMembreNb(null);
				utilNn.setCodeMembreNn(cc.getCodeMembre());
				utilNn.setCodeProduit(cc.getEuProduit().getCodeProduit());
				utilNn.setCodeProduitNn("CNCS");
				utilNn.setCodeSms(null);
				utilNn.setDateTransfert(new Date());
				utilNn.setIdOperation(null);
				utilNn.setIdUtilisateur(null);
				utilNn.setMontTransfert(cc.getMontantCredit());
				utilNn.setNumBon(null);
				utilNn.setMotif("CNCS Mature");
				utiliserNnRepo.save(utilNn);

				String codeCompte_nnCncs = "NN-TCNCS-" + cc.getCodeMembre();
				EuCompte nn_cncs = compteRepo.findOne(codeCompte_nnCncs);
				if (Objects.nonNull(nn_cncs)) {
					nn_cncs.setSolde(nn_cncs.getSolde() + cc.getMontantCredit());
					compteRepo.save(nn_cncs);
				} else {
					EuMembre membre = new EuMembre();
					membre.setCodeMembre(cc.getCodeMembre());

					EuTypeCompte typeCompte = new EuTypeCompte();
					typeCompte.setCodeTypeCompte("NN");

					EuCategorieCompte catCompte = new EuCategorieCompte();
					catCompte.setCodeCat("TCNCS");

					nn_cncs = new EuCompte();
					nn_cncs.setCardprinteddate(null);
					nn_cncs.setCardprintediddate(null);
					nn_cncs.setCodeCompte(codeCompte_nnCncs);
					nn_cncs.setDateAlloc(date);
					nn_cncs.setDesactiver("N");
					nn_cncs.setEuMembreMorale(null);
					nn_cncs.setEuMembre(membre);
					nn_cncs.setLibCompte("CNCS Numérique noir");
					nn_cncs.setMifarecard(null);
					nn_cncs.setEuTypeCompte(typeCompte);
					nn_cncs.setEuCategorieCompte(catCompte);
					nn_cncs.setSolde(cc.getMontantCredit());
					compteRepo.save(nn_cncs);
				}

				EuCompteCredit nncc = new EuCompteCredit();
				nncc.setAffecter(0);
				nncc.setBnp(0);
				nncc.setCodeBnp(null);
				nncc.setCodeMembre(cc.getCodeMembre());
				nncc.setCodeTypeCredit(cc.getEuProduit().getCodeProduit());
				nncc.setCompteSource("NN");
				nncc.setDatedeb(date);
				nncc.setDatefin(DateUtils.addDays(date, periode));
				nncc.setDateOctroi(date);
				nncc.setDomicilier(0);
				nncc.setEuOperation(null);
				nncc.setEuProduit(cc.getEuProduit());
				nncc.setKrr("N");
				nncc.setMontantCredit(cc.getMontantCredit());
				nncc.setMontantPlace(cc.getMontantPlace());
				nncc.setNbreRenouvel(0);
				nncc.setPrk(0.0);
				nncc.setRenouveller("N");
				nncc.setEuCompte(nn_cncs);
				nncc.setSource(cc.getCodeMembre() + DateFormatUtils.format(date, "yyyyMMddHHmmss"));
				creditRepo.save(nncc);

				EuCompte compte = cc.getEuCompte();
				compte.setSolde(compte.getSolde() - cc.getMontantCredit());
				compteRepo.save(compte);
				cc.setMontantCredit(0.0);
				return cc;
			}).forEach((cc) -> creditRepo.save(cc));
		}
	}

	public void gcpEchu() {
		List<EuTpagcp> gcps = tpagcpRepo.getAll();
		if (!gcps.isEmpty()) {
			LocalDate date = LocalDate.now();
			Date ldate = new Date();
			gcps.stream().forEach((gcp) -> {
				Period p;
				if (gcp.getDateDeb().compareTo(gcp.getDateDebTranche()) == 0) {
					p = Period.between(gcp.getDateDeb(), date);
				} else {
					p = Period.between(gcp.getDateDebTranche(), date);
				}
				if (Objects.nonNull(p)) {
					if (p.getDays() > gcp.getPeriode()) {
						int duree = (int) Math.floor(p.getDays() / gcp.getPeriode());
						if (duree >= 1) {
							if (duree > gcp.getResteNtf()) {
								duree = gcp.getResteNtf();
							}

							double mont_echu = gcp.getMontTranche() * duree;
							EuTypeNn type_nn = new EuTypeNn();
							type_nn.setCodeTypeNn("GCP");

							EuMembreMorale membre = new EuMembreMorale();
							membre.setCodeMembreMorale(gcp.getCodeMembre());

							EuNn nn = new EuNn();
							nn.setDateEmission(ldate);
							nn.setEuMembreMorale(membre);
							nn.setEuTypeNn(type_nn);
							nn.setIdUtilisateur(null);
							nn.setMontantEmis(mont_echu);
							nn.setMontantRemb(mont_echu);
							nn.setSoldeNn(0.0);
							nnRepo.save(nn);

							EuUtiliserNn utilNn = new EuUtiliserNn();
							utilNn.setCodeMembreNb(null);
							utilNn.setCodeMembreNb(gcp.getCodeMembre());
							utilNn.setCodeMembreNn("NN-" + nn.getIdNn());
							utilNn.setCodeProduit("GCP");
							utilNn.setCodeProduitNn("GCP");
							utilNn.setCodeSms(null);
							utilNn.setDateTransfert(new Date());
							utilNn.setIdOperation(null);
							utilNn.setIdUtilisateur(null);
							utilNn.setMontTransfert(mont_echu);
							utilNn.setNumBon(null);
							utilNn.setMotif("GCP Mature");
							utiliserNnRepo.save(utilNn);

							String codeCompte = "NN-TPAGCP-" + gcp.getCodeMembre();
							EuCompte nn_cncs = compteRepo.findOne(codeCompte);
							if (Objects.nonNull(nn_cncs)) {
								nn_cncs.setSolde(nn_cncs.getSolde() + mont_echu);
								compteRepo.save(nn_cncs);
							} else {

								EuTypeCompte typeCompte = new EuTypeCompte();
								typeCompte.setCodeTypeCompte("NN");

								EuCategorieCompte catCompte = new EuCategorieCompte();
								catCompte.setCodeCat("TPAGCP");

								nn_cncs = new EuCompte();
								nn_cncs.setCardprinteddate(null);
								nn_cncs.setCardprintediddate(null);
								nn_cncs.setCodeCompte(codeCompte);
								nn_cncs.setDateAlloc(ldate);
								nn_cncs.setDesactiver("N");
								nn_cncs.setEuMembre(null);
								nn_cncs.setEuMembreMorale(membre);
								nn_cncs.setLibCompte("TPAGCP Num�rique noir");
								nn_cncs.setMifarecard(null);
								nn_cncs.setEuTypeCompte(typeCompte);
								nn_cncs.setEuCategorieCompte(catCompte);
								nn_cncs.setSolde(mont_echu);
								compteRepo.save(nn_cncs);
							}
							gcp.setMontEchu(gcp.getMontEchu() + mont_echu);
							gcp.setSolde(gcp.getSolde() - mont_echu);
							gcp.setResteNtf(gcp.getResteNtf() - duree);
							if (gcp.getDateDeb().compareTo(gcp.getDateDebTranche()) == 0) {
								gcp.setDateDebTranche(gcp.getDateDeb().plusDays((duree * gcp.getPeriode()) + 1));
							} else {
								gcp.setDateDebTranche(gcp.getDateDebTranche().plusDays((duree * gcp.getPeriode()) + 1));
							}
							gcp.setDateFinTranche(gcp.getDateDebTranche().plusDays(gcp.getPeriode()));
							tpagcpRepo.save(gcp);
						}
					}
				}
			});
		}
	}

	public void escompteEchu() {
		int periode = (int) Math.floor(parametreRepo.findOne(new EuParametresPK("periode", "valeur")).getMontant());
		List<EuEscompte> escomptes = escRepo.findAllEscompte();
		if (!escomptes.isEmpty()) {
			LocalDate date = LocalDate.now();
			escomptes.stream().forEach((e) -> {
				int duree = (int) Math.floor(Period.between(e.getDateDebTranche(), date).getDays() / periode);
				if (duree >= 1) {
					double mont_echu = e.getMontTranche() * duree;
					e.setMontEchu(e.getMontEchu() + mont_echu);
					e.setSolde(e.getSolde() - mont_echu);
					e.setResteNtf(e.getResteNtf() - duree);
					e.setDateDebTranche(e.getDateDebTranche().plusDays((duree * periode) + 1));
					e.setDateFinTranche(e.getDateDebTranche().plusDays(periode));
					escRepo.save(e);
				}
			});
		}
	}

	public void compensationEchu() {
		int periode = (int) Math.floor(parametreRepo.findOne(new EuParametresPK("periode", "valeur")).getMontant());
		List<EuCompensation> compensations = compensRepo.findAllCompensation();
		if (!compensations.isEmpty()) {
			LocalDate date = LocalDate.now();
			compensations.stream().forEach((c) -> {
				int duree = (int) Math.floor(Period.between(c.getDateDebTranche(), date).getDays() / periode);
				if (duree >= 1) {
					double mont_echu = c.getMontTranche() * duree;
					c.setSoldeCompensation(c.getSoldeCompensation() - mont_echu);
					c.setMontEchu(c.getMontEchu() + mont_echu);
					c.setResteNtf(c.getResteNtf() - duree);
					c.setDateDebTranche(c.getDateDebTranche().plusDays((periode * duree) + 1));
					c.setDateFinTranche(c.getDateDebTranche().plusDays(periode));
					compensRepo.save(c);
				}
			});
		}
	}
}

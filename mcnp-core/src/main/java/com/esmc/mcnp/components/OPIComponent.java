package com.esmc.mcnp.components;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.exception.CompteNonIntegreException;
import com.esmc.mcnp.exception.CompteNonTrouveException;
import com.esmc.mcnp.core.utils.DateUtility;
import com.esmc.mcnp.core.utils.NextDayWeekendAdjuster;
import com.esmc.mcnp.dto.obps.TpaGcpRequest;
import com.esmc.mcnp.exception.SoldeInsuffisantException;
import com.esmc.mcnp.model.acteur.EuActeur;
import com.esmc.mcnp.model.obpsd.EuApprovisionnement;
import com.esmc.mcnp.model.ba.EuCapaTs;
import com.esmc.mcnp.model.bc.EuCnp;
import com.esmc.mcnp.model.bc.EuCnpEntree;
import com.esmc.mcnp.model.obpsd.EuDetailAppro;
import com.esmc.mcnp.model.obpsd.EuDetailBonOpi;
import com.esmc.mcnp.model.obpsd.EuEchange;
import com.esmc.mcnp.model.obpsd.EuEscompte;
import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteCreditTs;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.obps.EuBonGcpPrelever;
import com.esmc.mcnp.model.obps.EuBonGcpPreleverPK;
import com.esmc.mcnp.model.obps.EuGcp;
import com.esmc.mcnp.model.obps.EuGcpPrelever;
import com.esmc.mcnp.model.obps.EuTegc;
//import com.esmc.mcnp.model.acteur.EuActeursCreneaux;
import com.esmc.mcnp.model.obpsd.EuBanque;
import com.esmc.mcnp.model.obpsd.EuDetailGcpPbf;
import com.esmc.mcnp.model.obpsd.EuGcpPbf;
import com.esmc.mcnp.model.obpsd.EuTpagcp;
import com.esmc.mcnp.model.obpsd.EuTraite;
import com.esmc.mcnp.services.acteurs.EuActeurService;
import com.esmc.mcnp.services.acteurs.EuBanqueService;
import com.esmc.mcnp.services.ba.EuCapaTsService;
import com.esmc.mcnp.services.bc.EuBonGcpPreleverService;
import com.esmc.mcnp.services.bc.EuBonService;
import com.esmc.mcnp.services.bc.EuCnpEntreeService;
import com.esmc.mcnp.services.bc.EuCnpService;
import com.esmc.mcnp.services.bc.EuDetailBonOpiService;
import com.esmc.mcnp.services.cm.EuCompteCreditTsService;
import com.esmc.mcnp.services.cm.EuCompteService;
import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.cm.EuMembreService;
import com.esmc.mcnp.services.obps.EuGcpPreleverService;
import com.esmc.mcnp.services.obps.EuGcpService;
import com.esmc.mcnp.services.obps.EuTegcService;
import com.esmc.mcnp.services.obpsd.EuApprovisionnementService;
import com.esmc.mcnp.services.obpsd.EuDetailApproService;
import com.esmc.mcnp.services.obpsd.EuDetailGcpPbfService;
import com.esmc.mcnp.services.obpsd.EuEchangeService;
import com.esmc.mcnp.services.obpsd.EuEscompteService;
import com.esmc.mcnp.services.obpsd.EuGcpPbfService;
import com.esmc.mcnp.services.obpsd.EuTpagcpService;
import com.esmc.mcnp.services.obpsd.EuTraiteService;
import com.esmc.mcnp.services.setting.EuParametresService;
import com.google.common.collect.Lists;

@Component
@Transactional
public class OPIComponent {

	private EuCompteService compteService;
	private EuGcpPbfService gcpPbfService;
	private EuDetailGcpPbfService detGcpPbfservice;
	private EuEchangeService echangeService;
	private EuEscompteService escompteService;
	private EuMembreMoraleService moraleService;
	private EuMembreService membreService;
	private EuGcpPreleverService gcpPrelService;
	private EuTpagcpService tpagcpService;
	private EuBonService bonService;
	private EuTraiteService traiteService;
	private EuBonGcpPreleverService bonGcpservice;
	private EuParametresService paramService;
	private EuCnpService cnpService;
	private EuCnpEntreeService cnpEntService;
	private EuGcpService gcpService;
	private EuTegcService tegcService;
	private EuBanqueService bankService;
	private EuDetailBonOpiService detBonOpiService;
	private EuActeurService acteurService;
	// private EuActeurCreneauxService actCrenService;
	private EuCapaTsService capaTsService;
	private TransfertUtility transfertUtility;
	private EuCompteCreditTsService cctsService;
	// private EuMontantReduitService montReduitService;
	private EuApprovisionnementService approvisionnementService;
	private EuDetailApproService detailApproService;

	/**
	 * Logger.
	 */
	private final Logger log = LogManager.getLogger(OPIComponent.class.getName());

	@Autowired
	public OPIComponent(EuCompteService compteService, EuGcpPbfService gcpPbfService,
			EuDetailGcpPbfService detGcpPbfservice, EuEchangeService echangeService, EuEscompteService escompteService,
			EuMembreMoraleService moraleService, EuMembreService membreService, EuGcpPreleverService gcpPrelService,
			EuTpagcpService tpagcpService, EuBonService bonService, EuTraiteService traiteService,
			EuBonGcpPreleverService bonGcpservice, EuParametresService paramService, EuCnpService cnpService,
			EuCnpEntreeService cnpEntService, EuGcpService gcpService, EuTegcService tegcService,
			EuBanqueService bankService, EuDetailBonOpiService detBonOpiService, EuActeurService acteurService,
			EuCapaTsService capaTsService, TransfertUtility transfertUtility, EuCompteCreditTsService cctsService,
			EuApprovisionnementService approvisionnementService,
			EuDetailApproService detailApproService/**
													 * , EuMontantReduitService montReduitService
													 */
	) {
		this.compteService = compteService;
		this.gcpPbfService = gcpPbfService;
		this.detGcpPbfservice = detGcpPbfservice;
		this.echangeService = echangeService;
		this.escompteService = escompteService;
		this.moraleService = moraleService;
		this.membreService = membreService;
		this.gcpPrelService = gcpPrelService;
		this.tpagcpService = tpagcpService;
		this.bonService = bonService;
		this.traiteService = traiteService;
		this.bonGcpservice = bonGcpservice;
		this.paramService = paramService;
		this.cnpService = cnpService;
		this.cnpEntService = cnpEntService;
		this.gcpService = gcpService;
		this.tegcService = tegcService;
		this.bankService = bankService;
		this.detBonOpiService = detBonOpiService;
		this.acteurService = acteurService;
		this.capaTsService = capaTsService;
		this.transfertUtility = transfertUtility;
		this.cctsService = cctsService;
		this.approvisionnementService = approvisionnementService;
		this.detailApproService = detailApproService;
		// this.montReduitService = montReduitService;
	}

	public EuBon emettreOpiBa(String codeMembre, String typeRessource, String codeBanque, double montant, Integer nbre,
			Integer serie) throws SoldeInsuffisantException, CompteNonTrouveException, CompteNonIntegreException,
			NullPointerException, DataAccessException {
		String codeCompte = "NN-CAPA-" + codeMembre;
		EuCompte compte = compteService.getById(codeCompte);
		EuBon ba = transfertUtility.transfertBA(codeMembre, "NN", typeRessource, montant);
		if (Objects.nonNull(ba)) {
			List<EuCapaTs> cctss = capaTsService.findByEuBon_BonNumero(ba.getBonNumero());
			if (cctss.size() > 0) {
				Optional<EuBon> opbon = bonService.emettreBon("OPI", codeMembre, montant);
				if (opbon.isPresent()) {
					EuBon bon = opbon.get();
					String codeCompteTs = "NN-TSCAPA-" + codeMembre;
					EuCompte compteTs = compteService.getById(codeCompteTs);
					int compteur = 0;
					double mont = montant;
					while (mont > 0 && compteur < cctss.size()) {
						EuCapaTs capats = cctss.get(compteur);
						if (capats.getMontantSolde() < mont) {
							mont -= capats.getMontantSolde();
							compteur++;
							capats.setMontantUtiliser(capats.getMontantUtiliser() + capats.getMontantSolde());
							capats.setMontantSolde(0.0);
							capaTsService.update(capats);
						} else {
							capats.setMontantUtiliser(capats.getMontantUtiliser() + mont);
							capats.setMontantSolde(capats.getMontantSolde() - mont);
							capaTsService.update(capats);
							mont = 0;
						}
					}

					compteTs.setSolde(compteTs.getSolde() - montant);
					compteService.update(compteTs);

					/*
					 * Long idTpagcp = tpaService.getLastInsertedId(); if (idTpagcp == null) {
					 * idTpagcp = 0L; }
					 */
					int periode = paramService.getParam("periode", "valeur");
					LocalDate dateDeb = LocalDate.now();
					if (nbre == null || nbre == 0) {
						nbre = paramService.getParam("prc", "nr");
					}
					if (serie == null || serie == 0) {
						serie = paramService.getParam("OPI", "serie");
					}
					LocalDate datefin = dateDeb.plusDays(periode * nbre);
					LocalDate dateFinTranche = dateDeb.plusDays(periode);
					EuTpagcp tpagcp = new EuTpagcp();
					// tpagcp.setIdTpagcp(idTpagcp + 1);
					tpagcp.setCodeMembre(codeMembre);
					tpagcp.setDateDeb(dateDeb);
					tpagcp.setDateDebTranche(dateDeb);
					tpagcp.setDateFin(datefin);
					tpagcp.setDateFinTranche(dateFinTranche);
					tpagcp.setEscomptable(3);
					tpagcp.setEuCompte(compte);
					tpagcp.setModeReglement("OPI");
					tpagcp.setMontEchange(0);
					tpagcp.setMontEchu(0);
					tpagcp.setMontEscompte(0);
					tpagcp.setMontGcp(montant);
					tpagcp.setMontTranche(Math.floor(montant / nbre));
					tpagcp.setPeriode(periode);
					tpagcp.setNtf(nbre);
					tpagcp.setResteNtf(nbre);
					tpagcp.setSolde(montant);
					tpagcp.setTypeRessource(typeRessource);
					tpagcp = tpagcpService.add(tpagcp);

					EuBanque bank = bankService.findById(codeBanque);
					EuDetailBonOpi detBonOpi = new EuDetailBonOpi();
					detBonOpi.setCodeMembreEmetteur(codeMembre);
					if (StringUtils.isNotBlank(bank.getCodeMembreMorale())) {
						detBonOpi.setCodeMembrePbf(bank.getCodeMembreMorale());
					}
					detBonOpi.setEuBon(bon);
					detBonOpi.setNbre(nbre);
					detBonOpi.setModeReg("ENC");
					detBonOpi.setTypeEsc(null);
					detBonOpi.setMontant(montant);
					detBonOpi.setNbreTranche(serie);
					detBonOpi.setIdTpagcp(tpagcp.getIdTpagcp());
					detBonOpi.setCodeBanque(codeBanque);
					detBonOpi.setCodeTegc(ba.getBonNumero());
					detBonOpiService.add(detBonOpi);

					ba.setBonDateExpression(new Date());
					ba.setBonExprimer(1);
					bonService.update(ba);

					return bon;
				}
			}
		}
		return null;
	}

	public EuBon emettreOpiCncs(String codeMembre, String typeRessource, String codeBanque, double montant,
			Integer nbre, Integer serie) throws SoldeInsuffisantException, CompteNonTrouveException,
			CompteNonIntegreException, NullPointerException, DataAccessException {
		System.out.println("Emettre OPI Salaire");
		String codeCompte = "NR-T" + typeRessource + "-" + codeMembre;
		if (codeMembre.endsWith("M")) {
			codeCompte = "NR-TPN-" + codeMembre;
		}
		EuCompte compte = compteService.getById(codeCompte);
		EuBon ba = transfertUtility.transfertNr(codeMembre, typeRessource, montant);
		if (Objects.nonNull(ba)) {
			List<EuCompteCreditTs> cctss = cctsService.findByEuBon_BonNumero(ba.getBonNumero());
			if (cctss.size() > 0) {
				Optional<EuBon> opbon = bonService.emettreBon("OPI", codeMembre, montant);
				if (opbon.isPresent()) {
					EuBon bon = opbon.get();
					String codeCompteTs = "NR-TS" + typeRessource + "-" + codeMembre;
					if (codeMembre.endsWith("M")) {
						codeCompteTs = "NR-TSPN-" + codeMembre;
					}
					EuCompte compteTs = compteService.getById(codeCompteTs);
					int compteur = 0;
					double mont = montant;
					while (mont > 0 && compteur < cctss.size()) {
						EuCompteCreditTs capats = cctss.get(compteur);
						if (capats.getMontant() < mont) {
							mont -= capats.getMontant();
							compteur++;
							capats.setMontant(0.0);
							cctsService.update(capats);
						} else {
							capats.setMontant(capats.getMontant() - mont);
							cctsService.update(capats);
							mont = 0;
						}
					}

					compteTs.setSolde(compteTs.getSolde() - montant);
					compteService.update(compteTs);

					if (nbre == null || nbre == 0) {
						nbre = paramService.getParam("prc", "nr");
					}
					if (serie == null || serie == 0) {
						serie = paramService.getParam("OPI", "serie");
					}
					/*
					 * Long idTpagcp = tpaService.getLastInsertedId(); if (idTpagcp == null) {
					 * idTpagcp = 0L; }
					 */
					int periode = paramService.getParam("periode", "valeur");
					LocalDate dateDeb = LocalDate.now();
					LocalDate datefin = dateDeb.plusDays(periode * nbre);
					LocalDate dateFinTranche = dateDeb.plusDays(periode);
					EuTpagcp tpagcp = new EuTpagcp();
					// tpagcp.setIdTpagcp(idTpagcp + 1);
					tpagcp.setCodeMembre(codeMembre);
					tpagcp.setDateDeb(dateDeb);
					tpagcp.setDateDebTranche(dateDeb);
					tpagcp.setDateFin(datefin);
					tpagcp.setDateFinTranche(dateFinTranche);
					tpagcp.setEscomptable(3);
					tpagcp.setEuCompte(compte);
					tpagcp.setModeReglement("OPI");
					tpagcp.setMontEchange(0);
					tpagcp.setMontEchu(0);
					tpagcp.setMontEscompte(0);
					tpagcp.setMontGcp(montant);
					tpagcp.setMontTranche(Math.floor(montant / nbre));
					tpagcp.setPeriode(periode);
					tpagcp.setNtf(nbre);
					tpagcp.setResteNtf(nbre);
					tpagcp.setSolde(montant);
					tpagcp.setTypeRessource(typeRessource);
					tpagcp = tpagcpService.add(tpagcp);

					EuBanque bank = bankService.findById(codeBanque);
					EuDetailBonOpi detBonOpi = new EuDetailBonOpi();
					detBonOpi.setCodeMembreEmetteur(codeMembre);
					if (StringUtils.isNotBlank(bank.getCodeMembreMorale())) {
						detBonOpi.setCodeMembrePbf(bank.getCodeMembreMorale());
					}
					detBonOpi.setEuBon(bon);
					detBonOpi.setNbre(nbre);
					detBonOpi.setModeReg("ENC");
					detBonOpi.setTypeEsc(null);
					detBonOpi.setMontant(montant);
					detBonOpi.setNbreTranche(serie);
					detBonOpi.setIdTpagcp(tpagcp.getIdTpagcp());
					detBonOpi.setCodeBanque(codeBanque);
					detBonOpi.setCodeTegc(ba.getBonNumero());
					detBonOpiService.add(detBonOpi);

					ba.setBonDateExpression(new Date());
					ba.setBonExprimer(1);
					bonService.update(ba);

					return bon;
				}
			}
		}
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public EuBon emetreOpi(String typeGcp, String codeMembre, String codeTegc, String modePaiement,
			String referencePaiement, double montant, Integer nbre, Integer serie, String typeOpi, double montTranche1,
			int differe, Date dateDebut, Integer marge, Integer ttc, float tauxTva)
			throws CompteNonTrouveException, IllegalArgumentException {

		if (StringUtils.isBlank(codeMembre)) {
			throw new IllegalArgumentException("Le code Membre ne peut être nul");
		}
		if (StringUtils.isBlank(referencePaiement) || StringUtils.isBlank(modePaiement)) {
			throw new IllegalArgumentException(
					"Veuillez preciser le mode de paiement et le numero sur lequel les OPI vont être payés");
		}
		String codeCompte = "NB-TPAGCP-" + codeMembre;
		EuCompte compte = compteService.getById(codeCompte);
		if (Objects.nonNull(compte)) {
			Double solde = gcpService.getSoldeByMembre(codeMembre);
			System.out.println("OPI : montant somme GCp = " + solde);
			if (solde.equals(compte.getSolde())) {
				if (compte.getSolde() >= montant) {
					List<EuTegc> tegcs;
					if (StringUtils.isBlank(codeTegc)) {
						tegcs = tegcService.findByCodeMembre(codeMembre);
						if (tegcs.size() == 1) {
							codeTegc = tegcs.get(0).getCodeTegc();
						}
					} else {
						tegcs = tegcService.findByMembreAndTe(codeMembre, codeTegc);
					}

					if (tegcs.size() > 0) {
						Double soldeTegc = 0.0;
						Double soldeGcp = 0.0;
						if (StringUtils.isBlank(codeTegc)) {
							soldeTegc = tegcService.getSoldeByCodeMembre(codeMembre);
							soldeGcp = gcpService.getSoldeByMembre(codeMembre);
						} else {
							soldeTegc = tegcService.getSoldeByMembreAndTe(codeMembre, codeTegc);
							soldeGcp = gcpService.getSoldeByTegc(codeTegc);
						}
						if (soldeGcp.equals(soldeTegc)) {
							Optional<EuBon> opbon = bonService.emettreBon("BLG", codeMembre, montant);
							if (opbon.isPresent()) {
								EuBanque defalutBk = bankService.findByDefaut();
								if (nbre == null || nbre == 0) {
									if (tegcs.get(0).getTypeTegc().equalsIgnoreCase("externe")
											|| tegcs.get(0).getTypeTegc().equalsIgnoreCase("specifique")) {
										nbre = 1;
									} else {
										if (StringUtils.isNotBlank(typeGcp) && typeGcp.equals("CR")) {
										} else {
											nbre = paramService.getParam("prc", "nr");
										}
									}
								}
								if (serie == null || serie == 0) {
									if (tegcs.get(0).getTypeTegc().equalsIgnoreCase("externe")
											|| tegcs.get(0).getTypeTegc().equalsIgnoreCase("specifique")) {
										serie = 1;
									} else {
										serie = paramService.getParam("OPI", "serie");
									}
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
								detBonOpi.setMontTranche1(montTranche1);
								detBonOpi.setDiferre(differe);
								detBonOpi.setDateDebut(dateDebut);
								detBonOpi.setTypeGcp(typeGcp);
								detBonOpi.setMarge(marge);
								detBonOpi.setTtc(ttc);
								detBonOpi.setTauxTva(tauxTva);
								detBonOpiService.add(detBonOpi);
								return opbon.get();
							} else {
								return null;
							}
						} else {
							throw new CompteNonIntegreException("Le solde de " + soldeTegc + " du TE du membre "
									+ codeMembre + " est différent de la somme du détail des GCp " + soldeGcp);
						}
					} else {
						throw new CompteNonTrouveException("Ce membre " + codeMembre + " n'a pas de GCp");
					}
				} else {
					throw new SoldeInsuffisantException("Le solde de " + compte.getSolde() + " du compte GCp du "
							+ codeMembre + " est insuffisant pour effectuer cette operation de " + montant);
				}
			} else {
				throw new CompteNonIntegreException("Le compte GCp du " + codeMembre + " est incohérent : son solde "
						+ compte.getSolde() + " est différent de la somme des détails GCp qui est " + solde);
			}
		} else {
			throw new CompteNonTrouveException();
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public EuBon emetreOpi(String typeGcp, String codeMembre, EuTegc tegc, String modePaiement,
			String referencePaiement, double montant, Integer nbre, Integer serie, String typeOpi, double montTranche1,
			int differe, Date dateDebut, Integer marge, Integer ttc, float tauxTva)
			throws CompteNonTrouveException, IllegalArgumentException {

		if (StringUtils.isBlank(codeMembre)) {
			throw new IllegalArgumentException("Le code Membre ne peut être nul");
		}
		if (StringUtils.isBlank(referencePaiement) || StringUtils.isBlank(modePaiement)) {
			throw new IllegalArgumentException(
					"Veuillez preciser le mode de paiement et le numero sur lequel les OPI vont être payés");
		}
		String codeCompte = "NB-TPAGCP-" + codeMembre;
		EuCompte compte = compteService.getById(codeCompte);
		if (Objects.nonNull(compte)) {
			Double solde = gcpService.getSoldeByMembre(codeMembre);
			if (solde.equals(compte.getSolde())) {
				if (compte.getSolde() >= montant) {
					if (Objects.nonNull(tegc)) {
						Double soldeTegc = 0.0;
						Double soldeGcp = 0.0;
						soldeTegc = tegcService.getSoldeByMembreAndTe(codeMembre, tegc.getCodeTegc());
						soldeGcp = gcpService.getSoldeByTegc(tegc.getCodeTegc());
						if (soldeGcp.equals(soldeTegc)) {
							Optional<EuBon> opbon = bonService.emettreBon("BLG", codeMembre, montant);
							if (opbon.isPresent()) {
								EuBanque defalutBk = bankService.findByDefaut();
								if (nbre == null || nbre == 0) {
									if (tegc.getTypeTegc().equalsIgnoreCase("externe")
											|| tegc.getTypeTegc().equalsIgnoreCase("specifique")) {
										nbre = 1;
									} else {
										if (StringUtils.isNotBlank(typeGcp) && typeGcp.equals("CR")) {
										} else {
											nbre = paramService.getParam("prc", "nr");
										}
									}
								}
								if (serie == null || serie == 0) {
									if (tegc.getTypeTegc().equalsIgnoreCase("externe")
											|| tegc.getTypeTegc().equalsIgnoreCase("specifique")) {
										serie = 1;
									} else {
										serie = paramService.getParam("OPI", "serie");
									}
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
								detBonOpi.setCodeTegc(tegc.getCodeTegc());
								detBonOpi.setNbreTranche(serie);
								detBonOpi.setTypeOpi(typeOpi);
								detBonOpi.setPrk(0);
								detBonOpi.setMontTranche1(montTranche1);
								detBonOpi.setDiferre(differe);
								detBonOpi.setDateDebut(dateDebut);
								detBonOpi.setTypeGcp(typeGcp);
								detBonOpi.setMarge(marge);
								detBonOpi.setTtc(ttc);
								detBonOpi.setTauxTva(tauxTva);
								detBonOpiService.add(detBonOpi);
								return opbon.get();
							} else {
								return null;
							}
						} else {
							throw new CompteNonIntegreException("Le solde de " + soldeTegc + " du TE du membre "
									+ codeMembre + " est différent de la somme du détail des GCp " + soldeGcp);
						}
					} else {
						throw new CompteNonTrouveException("Ce membre " + codeMembre + " n'a pas de GCp");
					}
				} else {
					throw new SoldeInsuffisantException("Le solde de " + compte.getSolde() + " du compte GCp du "
							+ codeMembre + " est insuffisant pour effectuer cette operation de " + montant);
				}
			} else {
				throw new CompteNonIntegreException("Le compte GCp du " + codeMembre + " est incohérent : son solde "
						+ compte.getSolde() + " est différent de la somme des détails GCp qui est " + solde);
			}
		} else {
			throw new CompteNonTrouveException();
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean validerEmissionOpi(String numeroBon) {
		log.debug("Emission des OPI: Recherche du Bon");
		int activer = paramService.getParam("OPI", "datedebut30");
		EuBon bon = bonService.findByBonCode(numeroBon, 0);
		if (Objects.nonNull(bon)) {
			Date date = new Date();
			EuDetailBonOpi detbopi = detBonOpiService.findByEuBon_BonNumero(bon.getBonNumero());
			if (Objects.nonNull(detbopi)) {
				try {
					String codeCompte = "NB-TPAGCP-" + detbopi.getCodeMembreEmetteur();
					EuCompte compte = compteService.getById(codeCompte);
					List<EuTegc> tegcs;
					if (StringUtils.isBlank(detbopi.getCodeTegc())) {
						tegcs = tegcService.findByCodeMembre(detbopi.getCodeMembreEmetteur());
					} else {
						tegcs = tegcService.findByMembreAndTe(detbopi.getCodeMembreEmetteur(), detbopi.getCodeTegc());
					}
					/*
					 * Long idTpagcp = tpaService.getLastInsertedId(); if (idTpagcp == null) {
					 * idTpagcp = 1L; } else { idTpagcp += 1; }
					 */
					int periode = paramService.getParam("periode", "valeur");
					LocalDate dateDeb = LocalDate.now();
					EuTegc te = tegcService.getById(detbopi.getCodeTegc());

					LocalDate dateFinTranche = dateDeb;
					if (activer > 0) {
						dateFinTranche = dateDeb.with(new NextDayWeekendAdjuster(30));
					} else if (detbopi.getDateDebut() != null) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						dateFinTranche = LocalDate.parse(dateFormat.format(detbopi.getDateDebut()));
					} else {
						if (detbopi.getDiferre() > 0) {
							dateFinTranche = dateDeb.with(new NextDayWeekendAdjuster(30 * detbopi.getDiferre()));
						}
					}

					Double prk = paramService.getParametre("OPI", "prk");
					Double pck = paramService.getParametre("pck", "nr");
					Double txEsc = paramService.getParametre("taux", "escompte");
					double mont_opi = 0;
					double mont_tva = 0;
					if (detbopi.getTtc() != null && detbopi.getTtc().intValue() == 1) {
						mont_tva = detbopi.getMontant() * (detbopi.getTauxTva() / (100 + detbopi.getTauxTva()));
					}

					double mont_ht = detbopi.getMontant() - mont_tva;
					if (te.getTypeTegc().equalsIgnoreCase("externe") || te.getTypeTegc().equalsIgnoreCase("specifique")
							|| detbopi.getMarge() == null || detbopi.getMarge() == 0) {
						mont_opi = detbopi.getMontant();
					} else {
						mont_opi = ((mont_ht * prk) / (pck * (1 + txEsc / 100))) + mont_tva;
					}

					LocalDate ldatefin = dateDeb.with(new NextDayWeekendAdjuster(periode * detbopi.getNbre()));
					EuTpagcp tpagcp = new EuTpagcp();
					// tpagcp.setIdTpagcp(idTpagcp);
					tpagcp.setCodeMembre(detbopi.getCodeMembreEmetteur());
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
					tpagcp.setMontGcp(detbopi.getMontant());
					tpagcp.setPeriode(periode);
					tpagcp.setNtf(detbopi.getNbre());
					tpagcp.setResteNtf(detbopi.getNbre());
					tpagcp.setSolde(detbopi.getMontant());
					tpagcp.setTypeRessource("GCp");
					tpagcp.setNumeroBl(bon.getBonNumero());
					if (detbopi.getCodeMembreEmetteur().endsWith("P")) {
						if (detbopi.getTypeOpi().equalsIgnoreCase("P")) {
							tpagcp.setTypeBl("PP Prestataire");
						} else {
							tpagcp.setTypeBl("PP Acheteur-Revendeur");
						}
					} else {
						if (tegcs.get(0).getTypeTegc().equalsIgnoreCase("interim")) {
							tpagcp.setTypeBl("Centrale");
						} else {
							if (detbopi.getTypeOpi().equalsIgnoreCase("R")) {
								tpagcp.setTypeBl("PM Acheteur-Revendeur");
							} else {
								tpagcp.setTypeBl("PM");
							}
						}
					}
					tpagcp.setMontGcpMaj(Math.floor(mont_opi));
					if (detbopi.getMontTranche1() > 0) {
						tpagcp.setMontTranche(Math.floor((mont_opi - detbopi.getMontTranche1()) / detbopi.getNbre()));
					} else {
						tpagcp.setMontTranche(Math.floor(mont_opi / detbopi.getNbre()));
					}
					tpagcp.setSolde(Math.floor(mont_opi));
					tpagcp.setReinjecter(0);
					tpagcp.setNbreInjection(0);
					tpagcp = tpagcpService.add(tpagcp);

					detbopi.setIdTpagcp(tpagcp.getIdTpagcp());
					detbopi.setPrk(prk);
					detbopi = detBonOpiService.update(detbopi);

					double mont_blg = detbopi.getMontant();
					int c = 0;
					while (mont_blg > 0 && c < tegcs.size()) {
						EuTegc tegc = tegcs.get(c);
						if (tegc.getSoldeTegc() >= mont_blg) {
							if (StringUtils.isBlank(detbopi.getTypeGcp())) {
								gcpService.preleverGcp("OPI", compte, tegc, tpagcp.getIdTpagcp(), bon,
										detbopi.getCodeMembreEmetteur(), mont_blg);
							} else {
								gcpService.preleverGcp(detbopi.getTypeGcp(), "OPI", compte, tegc, tpagcp.getIdTpagcp(),
										bon, detbopi.getCodeMembreEmetteur(), mont_blg);
							}
							mont_blg = 0;
						} else {
							mont_blg -= tegc.getSoldeTegc();
							if (StringUtils.isBlank(detbopi.getTypeGcp())) {
								gcpService.preleverGcp("OPI", compte, tegc, tpagcp.getIdTpagcp(), bon,
										detbopi.getCodeMembreEmetteur(), tegc.getSoldeTegc());
							} else {
								gcpService.preleverGcp(detbopi.getTypeGcp(), "OPI", compte, tegc, tpagcp.getIdTpagcp(),
										bon, detbopi.getCodeMembreEmetteur(), tegc.getSoldeTegc());
							}
							c++;
						}
					}

					EuTraite traite;
					// Long id_traite = traiteService.getLastInsertedId();
					Integer traiter = traiteService.getTraiterByTpagcp(detbopi.getIdTpagcp());
					if (traiter == null) {
						traiter = 0;
					}
					/*
					 * if (id_traite == null) { id_traite = 0L; }
					 */
					Date datedeb = new Date();
					if (detbopi.getDateDebut() != null) {
						datedeb = detbopi.getDateDebut();
					} else if (detbopi.getDiferre() > 0) {
						// datedeb = ServerUtil.ajouterJours(date, detbopi.getDiferre() * 30);
						datedeb = DateUtility.asUtilDate(DateUtility.asLocalDate(date)
								.with(new NextDayWeekendAdjuster(detbopi.getDiferre() * 30)));
					}

					int nbreDisponible = detbopi.getNbre() / detbopi.getNbreTranche();
					Date datefin = datedeb;
					if (activer > 0) {
						datefin = DateUtility
								.asUtilDate(DateUtility.asLocalDate(date).with(new NextDayWeekendAdjuster(30)));
					}
					for (int i = 1; i <= detbopi.getNbre(); i++) {
						traite = new EuTraite();
						// traite.setTraiteId(id_traite + i);
						traite.setTraiteCodeBanque(detbopi.getCodeBanque());
						traite.setTraiteTegcp(detbopi.getIdTpagcp());
						traite.setEuBon(bon);
						traite.setTraitePayer(0);
						traite.setTraiteAvantVte(0);
						traite.setTraiter(traiter + i);
						if (i == 1) {
							if (detbopi.getMontTranche1() > 0) {
								traite.setTraiteMontant(detbopi.getMontTranche1());
							} else {
								traite.setTraiteMontant(tpagcp.getMontTranche());
							}
							traite.setTraiteDateDebut(datedeb);
							traite.setTraiteDateFin(datefin);
						} else {
							datefin = DateUtility
									.asUtilDate(DateUtility.asLocalDate(datedeb).with(new NextDayWeekendAdjuster()));
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
						traite.setModePaiement(detbopi.getModePaiement());
						traite.setReferencePaiement(detbopi.getRefrencePaiement());
						traiteService.add(traite);

						datedeb = datefin;
					}
					bon.setBonExprimer(1);
					bon.setBonDateExpression(date);
					bonService.update(bon);
					return true;
				} catch (Exception e) {
					log.error("Echec de l'opération d'émission d'OPI", e);
					return false;
				}
			} else {
				throw new EntityNotFoundException("Le détail du Bon de Commande n'est pas trouvé");
			}
		}
		return false;
	}

	public boolean updateTraiteDateFin(String codeMembre, Date dateDeb, Date dateFin, int duree) {
		if (Objects.nonNull(dateDeb) && Objects.nonNull(dateFin)) {
			List<EuTraite> traites = Collections.emptyList();
			if (StringUtils.isNotBlank(codeMembre)) {
				List<Long> tpas = tpagcpService.findAllByCodeMembre(codeMembre);
				traites = traiteService.findAllTraiteByDateAndTpa(tpas, dateDeb, dateFin);
			} else {
				traites = traiteService.findTraitesNonEchu(dateDeb, dateFin);
			}
			if (!traites.isEmpty()) {
				traites.forEach(t -> {
					Date date = DateUtils.addDays(t.getTraiteDateDebut(), -30);
					LocalDate ldate = DateUtility.asLocalDate(date);
					Date deb = DateUtility.asUtilDate(ldate.with(new NextDayWeekendAdjuster()));
					Date fin = deb;
					if (!DateUtils.isSameDay(t.getTraiteDateDebut(), t.getTraiteDateFin())) {
						LocalDate ldateFin = DateUtility.asLocalDate(fin);
						fin = DateUtility.asUtilDate(ldateFin.with(new NextDayWeekendAdjuster()));
					}
					t.setTraiteDateDebut(deb);
					t.setTraiteDateFin(fin);
					t.setTraiteDisponible(0);
					traiteService.update(t);
				});
				return true;
			}
		}
		return false;
	}

	public boolean updateOpiEcheance(String codeMembre, Date dateDeb, Date dateFin) {
		if (Objects.nonNull(dateDeb) && Objects.nonNull(dateFin)) {
			System.out.println("Date Debut = " + dateDeb);
			System.out.println("Date Fin = " + dateFin);
			LocalDate ldate = LocalDate.of(2018, Month.APRIL, 24);
			LocalDate rdate = LocalDate.of(2018, Month.DECEMBER, 24);
			long p2 = ChronoUnit.DAYS.between(ldate, rdate);
			List<EuTpagcp> tpas = Collections.emptyList();
			if (StringUtils.isNotBlank(codeMembre)) {
				tpas = tpagcpService.findByMembre(codeMembre, 12);
			} else {
				tpas = tpagcpService.findByDateDebAndFin(DateUtility.asLocalDate(dateDeb),
						DateUtility.asLocalDate(dateFin), 12, 10);
			}
			if (!tpas.isEmpty()) {
				for (EuTpagcp tpa : tpas) {
					System.out.println("Membres = " + tpa.getIdTpagcp() + " -- " + tpa.getCodeMembre()
							+ "  - Date Debut = " + tpa.getDateDeb() + " ___ Date fin = " + tpa.getDateFin());
					List<EuTraite> traites = traiteService.findAllTraiteByTpaAndDateFin(tpa.getIdTpagcp(), dateFin);
					if (!traites.isEmpty()) {
						int c = 0;
						Date deb = null;
						while (c < traites.size()) {
							EuTraite t = traites.get(c);
							System.out.println("Date Deb = " + t.getTraiteDateDebut());
							if (Objects.isNull(deb)) {
								LocalDate ldeb = DateUtility.asLocalDate(t.getTraiteDateFin()).plusDays(p2);
								deb = DateUtility.asUtilDate(ldeb);
							}
							Date fin = DateUtility
									.asUtilDate(DateUtility.asLocalDate(deb).with(new NextDayWeekendAdjuster()));
							t.setTraiteDateDebut(deb);
							t.setTraiteDateFin(fin);
							t.setTraiteDisponible(0);
							traiteService.update(t);
							deb = fin;
							c += 1;
						}
					} else {
						System.out.println("Pas de traites à mettre à jour");
					}
				}
				return true;
			}
		}
		return false;
	}

	public boolean updateTraite(Date dateDeb, Date dateFin) {
		if (Objects.nonNull(dateDeb)) {
			List<EuTpagcp> tpas = Lists.newArrayList();
			if (Objects.nonNull(dateFin)) {
				tpas = tpagcpService.findByDateDebBetween(DateUtility.asLocalDate(dateDeb),
						DateUtility.asLocalDate(dateFin));
			} else {
				tpas = tpagcpService.findByDateDebut(DateUtility.asLocalDate(dateDeb));
			}
			if (!tpas.isEmpty()) {
				tpas.forEach(p -> {
					List<EuTraite> traites = traiteService.findByTpagcp(p.getIdTpagcp());
					LocalDate dDeb = p.getDateDeb();
					for (EuTraite t : traites) {
						t.setTraiteDateDebut(DateUtility.asUtilDate(dDeb));
						LocalDate fin = dDeb.with(new NextDayWeekendAdjuster());
						t.setTraiteDateFin(DateUtility.asUtilDate(fin));
						if (t.getTraitePayer() != 1) {
							t.setTraiteDisponible(0);
							t.setTraiteNumero(null);
							t.setTraiteImprimer(0);
						}
						traiteService.update(t);
						dDeb = fin;
					}
				});
				return true;
			}
		}
		return false;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public EuBon emetreOpiBonCommande(String typeGcp, String codeMembre, String codeTegc, String modePaiement,
			double montant, Integer nbre, Integer serie, String typeOpi, double montTranche1, int differe,
			Date dateDebut, String numeroCompte, Integer reinjecter, Integer periodicite, Integer marge, int ttc,
			float tauxTva) throws CompteNonIntegreException, CompteNonTrouveException, SoldeInsuffisantException {
		EuMembreMorale morale = null;
		EuMembre membre = null;
		if (codeMembre.endsWith("M")) {
			morale = moraleService.findById(codeMembre);
		} else {
			membre = membreService.findById(codeMembre);
		}
		if (Objects.isNull(morale) && Objects.isNull(membre)) {
			throw new CompteNonTrouveException();
		}
		int activer = paramService.getParam("OPI", "datedebut30");
		String codeCompte = "NB-TPAGCP-" + codeMembre;
		EuCompte compte = compteService.getById(codeCompte);
		if (Objects.nonNull(compte)) {
			double solde = gcpService.getSoldeByMembre(codeMembre);
			if (solde == compte.getSolde()) {
				if (compte.getSolde() >= montant) {
					List<EuTegc> tegcs = Lists.newArrayList();
					if (StringUtils.isBlank(codeTegc)) {
						tegcs = tegcService.findByCodeMembre(codeMembre);
						if (tegcs.size() == 1) {
							codeTegc = tegcs.get(0).getCodeTegc();
						}
					} else {
						tegcs = tegcService.findByMembreAndTe(codeMembre, codeTegc);
					}

					if (tegcs.size() > 0) {
						Double soldeTegc = 0.0;
						Double soldeGcp = 0.0;
						if (StringUtils.isBlank(codeTegc)) {
							soldeTegc = tegcService.getSoldeByCodeMembre(codeMembre);
							soldeGcp = gcpService.getSoldeByMembre(codeMembre);
						} else {
							soldeTegc = tegcService.getSoldeByMembreAndTe(codeMembre, codeTegc);
							soldeGcp = gcpService.getSoldeByTegc(codeTegc);
						}
						// System.out.println("Solde GCp :" + soldeGcp + "<->
						// Solde TE : " + soldeTegc);
						if (soldeGcp.equals(soldeTegc)) {
							Optional<EuBon> opbon = bonService.emettreBon("BLG", codeMembre, montant);
							if (opbon.isPresent()) {
								EuBon bon = opbon.get();
								/*
								 * Long idTpagcp = tpaService.getLastInsertedId(); if (idTpagcp == null) {
								 * idTpagcp = 1L; } else { idTpagcp += 1; }
								 */
								EuTegc te = tegcs.get(0);
								if (nbre == null || nbre == 0) {
									if (te.getTypeTegc().equalsIgnoreCase("externe")
											|| te.getTypeTegc().equalsIgnoreCase("specifique")) {
										nbre = 1;
									} else {
										if (typeGcp.equals("CR")) {
										} else {
											nbre = paramService.getParam("prc", "nr");
										}
									}
								}
								if (serie == null || serie == 0) {
									if (te.getTypeTegc().equalsIgnoreCase("externe")
											|| te.getTypeTegc().equalsIgnoreCase("specifique")) {
										serie = 1;
									} else {
										serie = paramService.getParam("OPI", "serie");
									}
								}

								int periode = paramService.getParam("periode", "valeur");
								LocalDate dateDeb = LocalDate.now();
								if (dateDebut != null) {
									dateDeb = DateUtility.asLocalDate(dateDebut);
								} else if (differe > 0) {
									dateDeb = dateDeb.with(new NextDayWeekendAdjuster(differe * 30));
								}
								LocalDate dateFinTranche = dateDeb;
								if (activer > 0) {
									dateFinTranche = dateDeb.plusDays(30);
								}

								Double prk = paramService.getParametre("OPI", "prk");
								Double pck = paramService.getParametre("pck", "nr");
								Double txEsc = paramService.getParametre("taux", "escompte");
								double mont_opi = 0;
								double mont_tva = 0;
								if (ttc == 1) {
									mont_tva = montant * (tauxTva / (100 + tauxTva));
								}
								double mont_ht = montant - mont_tva;
								if (te.getTypeTegc().equalsIgnoreCase("externe")
										|| te.getTypeTegc().equalsIgnoreCase("specifique") || marge == null
										|| marge == 0) {
									mont_opi = montant;
								} else {
									mont_opi = ((mont_ht * prk) / (pck * (1 + txEsc / 100))) + mont_tva;
								}

								LocalDate datefin = dateDeb.with(new NextDayWeekendAdjuster(periode * nbre));
								EuTpagcp tpagcp = new EuTpagcp();
								// tpagcp.setIdTpagcp(idTpagcp);
								tpagcp.setCodeMembre(codeMembre);
								tpagcp.setDateDeb(dateDeb);
								tpagcp.setDateDebTranche(dateDeb);
								tpagcp.setDateFin(datefin);
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
								tpagcp.setTypeRessource("GCp");
								tpagcp.setNumeroBl(opbon.get().getBonNumero());
								if (codeMembre.endsWith("P")) {
									if (typeOpi.equalsIgnoreCase("P")) {
										tpagcp.setTypeBl("PP Prestataire");
									} else {
										tpagcp.setTypeBl("PP Acheteur-Revendeur");
									}
								} else {
									if (tegcs.get(0).getTypeTegc().equalsIgnoreCase("interim")) {
										tpagcp.setTypeBl("Centrale");
									} else {
										if (typeOpi.equalsIgnoreCase("R")) {
											tpagcp.setTypeBl("PM Acheteur-Revendeur");
										} else {
											tpagcp.setTypeBl("PM");
										}
									}
								}
								tpagcp.setMontGcpMaj(Math.floor(mont_opi));
								if (montTranche1 > 0) {
									tpagcp.setMontTranche(Math.floor((mont_opi - montTranche1) / nbre));
								} else {
									tpagcp.setMontTranche(Math.floor(mont_opi / nbre));
								}
								tpagcp.setReinjecter(reinjecter);
								tpagcp.setNbreInjection(periodicite);
								tpagcp.setSolde(Math.floor(mont_opi));
								tpagcp = tpagcpService.add(tpagcp);

								double mont_blg = montant;
								int c = 0;
								while (mont_blg > 0 && c < tegcs.size()) {
									EuTegc tegc = tegcs.get(c);
									if (tegc.getSoldeTegc() >= mont_blg) {
										if (StringUtils.isBlank(typeGcp)) {
											gcpService.preleverGcp("OPI", compte, tegc, tpagcp.getIdTpagcp(), bon,
													codeMembre, mont_blg);
										} else {
											gcpService.preleverGcp(typeGcp, "OPI", compte, tegc, tpagcp.getIdTpagcp(),
													bon, codeMembre, mont_blg);
										}
										mont_blg = 0;
									} else {
										mont_blg -= tegc.getSoldeTegc();
										if (StringUtils.isBlank(typeGcp)) {
											gcpService.preleverGcp("OPI", compte, tegc, tpagcp.getIdTpagcp(), bon,
													codeMembre, tegc.getSoldeTegc());
										} else {
											gcpService.preleverGcp(typeGcp, "OPI", compte, tegc, tpagcp.getIdTpagcp(),
													bon, codeMembre, tegc.getSoldeTegc());
										}
										c++;
									}
								}
								EuBanque bank = bankService.findByDefaut();
								EuDetailBonOpi detBonOpi = new EuDetailBonOpi();
								detBonOpi.setCodeMembreEmetteur(codeMembre);
								if (StringUtils.isNotBlank(bank.getCodeMembreMorale())) {
									detBonOpi.setCodeMembrePbf(bank.getCodeMembreMorale());
								}
								detBonOpi.setEuBon(bon);
								detBonOpi.setNbre(nbre);
								detBonOpi.setModeReg("ENC");
								detBonOpi.setTypeEsc(null);
								detBonOpi.setMontant(montant);
								detBonOpi.setIdTpagcp(tpagcp.getIdTpagcp());
								detBonOpi.setCodeBanque(bank.getCodeBanque());
								if (StringUtils.isNotBlank(codeTegc)) {
									detBonOpi.setCodeTegc(codeTegc);
								} else {
									detBonOpi.setCodeTegc(null);
								}
								detBonOpi.setNbreTranche(serie);
								detBonOpi.setTypeOpi(typeOpi);
								detBonOpi.setPrk(prk);
								detBonOpi.setMontTranche1(montTranche1);
								detBonOpi.setDiferre(differe);
								detBonOpi.setDateDebut(dateDebut);
								detBonOpi.setModePaiement(modePaiement);
								detBonOpi.setRefrencePaiement(numeroCompte);
								detBonOpi.setTtc(ttc);
								detBonOpi.setTauxTva(tauxTva);
								detBonOpiService.add(detBonOpi);

								if (StringUtils.isNotBlank(bank.getCodeMembreMorale())) {
									bon.setBonCodeMembreDistributeur(bank.getCodeMembreMorale());
									bonService.update(bon);
								}
								return bon;
							} else {
								return null;
							}
						} else {
							throw new CompteNonIntegreException(
									"Le solde de ce TE est insuffisant ou le compte est incohérent!");
						}
					} else {
						throw new CompteNonTrouveException("Ce compte est incohérent : Il n'y a pas de TE");
					}
				} else {
					throw new SoldeInsuffisantException();
				}
			} else {
				throw new CompteNonIntegreException();
			}
		} else {
			throw new CompteNonTrouveException();
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean creerOpi(String numeroBon) {
		int activer = paramService.getParam("OPI", "datedebut30");
		EuBon bon = bonService.findByBonCode(numeroBon, 0);
		if (Objects.nonNull(bon)) {
			Date date = new Date();
			EuDetailBonOpi detbopi = detBonOpiService.findByEuBon_BonNumero(bon.getBonNumero());
			if (Objects.nonNull(detbopi)) {
				int nbreDisponible = detbopi.getNbre() / detbopi.getNbreTranche();

				Date datedeb = date;
				if (activer > 0) {
					datedeb = DateUtility
							.asUtilDate(DateUtility.asLocalDate(date).with(new NextDayWeekendAdjuster(30)));
				} else if (detbopi.getDateDebut() != null) {
					datedeb = detbopi.getDateDebut();
				} else if (detbopi.getDiferre() > 0) {
					datedeb = DateUtility.asUtilDate(
							DateUtility.asLocalDate(date).with(new NextDayWeekendAdjuster(detbopi.getDiferre() * 30)));
				}

				EuTpagcp tpagcp = tpagcpService.findById(detbopi.getIdTpagcp());
				Integer traiter = traiteService.getTraiterByTpagcp(detbopi.getIdTpagcp());
				if (detbopi.getTypeOpi().equals("R")) {
					double marge = paramService.getParametre("OPI", "MARGE");
					double marge_g = paramService.getParametre("OPI", "MARGE-G");
					double mont_opi_esp = (tpagcp.getMontGcpMaj() / (1 + (marge_g / 100)));
					if (marge > 0) {
						mont_opi_esp += ((mont_opi_esp * marge) / 100);
					}
					double mont_opi_bai = tpagcp.getMontGcpMaj() - mont_opi_esp;
					createTraite(bon, detbopi, datedeb, mont_opi_esp, traiter, nbreDisponible, "esp");
					createTraite(bon, detbopi, datedeb, mont_opi_bai, traiter, nbreDisponible, "bai");
				} else {
					createTraite(bon, detbopi, datedeb, tpagcp.getMontGcpMaj(), traiter, nbreDisponible, "esp");
				}
				bon.setBonExprimer(1);
				bon.setBonDateExpression(date);
				bonService.update(bon);
				return true;
			} else {
				throw new EntityNotFoundException("Le détail du Bon de Commande n'est pas trouvé");
			}
		}
		return false;
	}

	public void createTraite(EuBon bon, EuDetailBonOpi detbopi, Date datedeb, double montant, Integer traiter,
			int nbreDisponible, String typeTraite) {
		EuTraite traite = null;
		Date datefin = datedeb;
		if (traiter == null) {
			traiter = 0;
		}
		for (int c = 1; c <= detbopi.getNbre(); c++) {
			traite = new EuTraite();
			// traite.setTraiteId(id_traite + c);
			traite.setTraiteCodeBanque(detbopi.getCodeBanque());
			traite.setTraiteTegcp(detbopi.getIdTpagcp());
			traite.setEuBon(bon);
			traite.setTraitePayer(0);
			traite.setTraiteAvantVte(0);
			traite.setTraiter(traiter + c);
			if (c == 1) {
				if (detbopi.getMontTranche1() > 0) {
					traite.setTraiteMontant(detbopi.getMontTranche1());
				} else {
					traite.setTraiteMontant(Math.floor(montant / detbopi.getNbre()));
				}
				traite.setTraiteDateDebut(new Date());
				traite.setTraiteDateFin(datefin);
			} else {
				datefin = DateUtility.asUtilDate(DateUtility.asLocalDate(datedeb).with(new NextDayWeekendAdjuster()));
				traite.setTraiteDateDebut(datedeb);
				traite.setTraiteDateFin(datefin);
				traite.setTraiteMontant(Math.floor(montant / detbopi.getNbre()));
			}

			if (c <= nbreDisponible) {
				if (typeTraite.equals("esp")) {
					traite.setTraiteDisponible(1);
				} else {
					traite.setTraiteDisponible(2);
				}
			} else {
				traite.setTraiteDisponible(0);
			}
			traite.setTraiteImprimer(0);
			traite.setTraiteEscompteNature(0);
			traite.setModePaiement(detbopi.getModePaiement());
			traite.setReferencePaiement(detbopi.getRefrencePaiement());
			traite.setBonType(typeTraite);
			traiteService.add(traite);

			datedeb = datefin;
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public EuBon emettreBonCommande(List<Long> idtpagcps, String codeMembre, String typeBon, String codeBanque,
			String modeReg, String typeEsc, int nbre, double montant)
			throws DataAccessException, SoldeInsuffisantException, CompteNonIntegreException {
		EuMembreMorale moral = moraleService.findById(codeMembre);
		if (!Objects.nonNull(moral)) {
			throw new CompteNonTrouveException();
		}
		EuActeur acteur = acteurService.findByCodeMembre(moral.getCodeMembreMorale());
		if (!Objects.nonNull(acteur)) {
			throw new CompteNonTrouveException("Cet acteur n'est pas mise sur chaine.");
		}
		if (!moral.getTypeFournisseur().equals("externe") && !moral.getTypeFournisseur().equals("specifique")
				&& !acteur.getCodeActivite().equals("grossiste")) {
			throw new IllegalArgumentException("Cet acteur n'est pas autorisé à effectuer cette opération.");
		}
		if (idtpagcps.size() > 0) {
			List<EuTpagcp> tpagcps = tpagcpService.findByIds(idtpagcps);
			double totSolde = 0;
			double totEchu = 0;
			for (EuTpagcp t : tpagcps) {
				totSolde += t.getSolde();
				totEchu += t.getMontEchu();
			}
			if (typeBon.equals("OPI")) {
				if (modeReg.equals("ESC")) {
					if (typeEsc.equals("P")) {
						for (EuTpagcp tpagcp : tpagcps) {
							if (nbre > tpagcp.getResteNtf()) {
								throw new SoldeInsuffisantException("Le Nombre restant de votre OPI  "
										+ tpagcp.getResteNtf() + " est insuffisant pour effectuer cette transaction");
							}
						}
					} else if (typeEsc.equals("T") && totSolde < montant) {
						throw new SoldeInsuffisantException(
								"Le solde de votre OPI est insuffisant pour effectuer cette transaction");
					}
				} else {
					if (totEchu < montant) {
						throw new SoldeInsuffisantException(
								"Le solde de votre OPI echu est insuffisant pour effectuer cette transaction");
					}
				}
			} else if (totSolde < montant) {
				throw new SoldeInsuffisantException(
						"Le solde de votre OPI echu est insuffisant pour effectuer cette transaction");
			}
			Optional<EuBon> opbon = bonService.emettreBon(typeBon, codeMembre, montant);
			if (opbon.isPresent()) {
				EuBon bon = opbon.get();
				for (EuTpagcp tpagcp : tpagcps) {

					// Long id_traite = traiteService.getLastInsertedId();
					Integer traiter = traiteService.getTraiterByTpagcp(tpagcp.getIdTpagcp());
					if (traiter == null) {
						traiter = 0;
					}
					/*
					 * if (id_traite == null) { id_traite = 0L; }
					 */
					int t_nbre = 0;
					double t_montant = 0;
					if (modeReg.equals("ESC")) {
						if (typeEsc.equals("T")) {
							t_nbre = tpagcp.getResteNtf();
							t_montant = tpagcp.getSolde();
						} else {
							t_nbre = nbre;
							if (t_nbre > tpagcp.getResteNtf()) {
								t_nbre = tpagcp.getResteNtf();
							}
							t_montant = t_nbre * tpagcp.getMontTranche();
						}
					} else {
						t_montant = tpagcp.getMontEchu();
						t_nbre = Long.valueOf(Math.round(tpagcp.getMontEchu() / tpagcp.getMontTranche())).intValue();
					}

					if (t_nbre > 0 && t_nbre <= tpagcp.getResteNtf()) {
						EuTraite traite = null;
						for (int c = 1; c <= t_nbre; c++) {
							traite = new EuTraite();
							// traite.setTraiteId(id_traite + c);
							traite.setTraiteCodeBanque(codeBanque);
							traite.setTraiteTegcp(tpagcp.getIdTpagcp());
							traite.setEuBon(bon);
							traite.setTraiter(traiter + c);
							traiteService.add(traite);
						}
					}

					List<EuGcpPrelever> gcpPrelevers = gcpPrelService.findByIdTpagcp(tpagcp.getIdTpagcp());

					double mont_deduire = t_montant;
					int compteur = 0;
					while (mont_deduire > 0 && compteur < gcpPrelevers.size()) {
						EuGcpPrelever gcpPreleve = gcpPrelevers.get(compteur);
						if (gcpPreleve.getSoldePrelevement() >= montant) {
							gcpPreleve.setMontRapprocher(gcpPreleve.getMontRapprocher() + mont_deduire);
							gcpPreleve.setSoldePrelevement(gcpPreleve.getSoldePrelevement() - mont_deduire);
							gcpPrelService.update(gcpPreleve);

							EuBonGcpPreleverPK id = new EuBonGcpPreleverPK(bon.getBonId(),
									gcpPreleve.getIdPrelevement());
							EuBonGcpPrelever bongcp = new EuBonGcpPrelever();
							bongcp.setId(id);
							bongcp.setIdTpagcp(tpagcp.getIdTpagcp());
							bongcp.setMontantBon(mont_deduire);
							bonGcpservice.add(bongcp);

							mont_deduire = 0;

						} else {
							mont_deduire -= gcpPreleve.getSoldePrelevement();

							EuBonGcpPreleverPK id = new EuBonGcpPreleverPK(bon.getBonId(),
									gcpPreleve.getIdPrelevement());
							EuBonGcpPrelever bongcp = new EuBonGcpPrelever();
							bongcp.setId(id);
							bongcp.setIdTpagcp(tpagcp.getIdTpagcp());
							bongcp.setMontantBon(mont_deduire);
							bonGcpservice.add(bongcp);

							gcpPreleve.setMontRapprocher(
									gcpPreleve.getMontRapprocher() + gcpPreleve.getSoldePrelevement());
							gcpPreleve.setSoldePrelevement(0);
							gcpPrelService.update(gcpPreleve);

							compteur += 1;
						}
					}
					if (modeReg.equals("ESC")) {
						Integer periode = paramService.getParam("periode", "valeur");
						LocalDate date_deb = tpagcp.getDateDebTranche().plusDays(periode * t_nbre);
						LocalDate date_fin = tpagcp.getDateDebTranche().plusDays(periode);
						tpagcp.setMontEscompte(tpagcp.getMontEscompte() + t_montant);
						tpagcp.setResteNtf(tpagcp.getResteNtf() - t_nbre);
						tpagcp.setSolde(tpagcp.getSolde() - t_montant);
						tpagcp.setDateDebTranche(date_deb);
						tpagcp.setDateFinTranche(date_fin);
					} else {
						tpagcp.setMontEchu(tpagcp.getMontEchu() - t_montant);
					}
					tpagcpService.update(tpagcp);
				}
				// smsc.sendSms("ESMC: Le Numero de votre Bon est " +
				// bon.getBonNumero(), "228", moral.getTelMembre());
				return bon;
			}
		}
		return null;
	}

	@Transactional
	public boolean approvisionnerOpi(String codeMembreVendeur, String codeMembreAcheteur, String modePaiement,
			String numeroCompte, List<TpaGcpRequest> dtraites, double montant) {
		if (StringUtils.isNoneBlank(codeMembreVendeur) && StringUtils.isNotBlank(codeMembreAcheteur)
				&& !dtraites.isEmpty()) {
			List<Long> ids = Lists.newArrayList();
			dtraites.forEach(d -> {
				ids.add(d.getIdTpagcp());
			});
			List<EuTpagcp> tpas = tpagcpService.findByIds(ids);
			if (!tpas.isEmpty()) {
				try {
					dtraites.forEach(t -> {
						EuTpagcp tpagcp = tpagcpService.findById(t.getIdTpagcp());
						List<EuTraite> traites = traiteService.findTraiteDispoByIds(t.getIdTraites());
						if (!traites.isEmpty()) {
							EuApprovisionnement appro = new EuApprovisionnement();
							appro.setCodeMembreApporteur(codeMembreVendeur);
							appro.setCodeMembreBeneficiare(codeMembreAcheteur);
							appro.setDateApprovisionnement(new Date());
							appro.setMontantApprovisionnement(montant);
							appro.setIdCanton(0L);
							appro.setIdTpagcp(t.getIdTpagcp());
							appro.setTypeApprovisionnement("OPI");
							approvisionnementService.add(appro);

							traites.forEach(r -> {
								EuDetailAppro detailAppro = new EuDetailAppro();
								detailAppro.setApprovisionnement(appro);
								detailAppro.setCodeCapa(null);
								detailAppro.setIdCredit(t.getIdTpagcp());
								detailAppro.setIdTraite(r.getTraiteId());
								detailAppro.setCodeCompte(null);
								detailAppro.setMontantDetailAppro(r.getTraiteMontant());
								detailApproService.add(detailAppro);

								r.setIdApprovisionnement(appro.getIdApprovisionement());
								r.setModePaiement(modePaiement);
								r.setReferencePaiement(numeroCompte);
								traiteService.update(r);
							});

							tpagcp.setMontEscompte(tpagcp.getMontEscompte() + (t.getNbre() * tpagcp.getMontTranche()));
							tpagcpService.update(tpagcp);
						}
					});
					return true;
				} catch (Exception e) {
					log.error("Echec de l'opération d'approvisionnement d'OPI", e);
					return false;
				}
			}
		}
		return false;
	}

	public boolean payerGcp(String codeMembrePbf, String modeReg, String typeEsc, Integer nbre, String codeBon,
			double montant) throws DataAccessException, SoldeInsuffisantException, CompteNonIntegreException {
		if (StringUtils.isNotBlank(codeBon) && StringUtils.isNotBlank(codeMembrePbf)) {
			String codeComptePbf = "NB-TPAGCP-" + codeMembrePbf;
			EuBon bon = bonService.findByBonCode(codeBon, 0);
			List<Long> ids = bonGcpservice.findIdTpagcpByBonNumero(codeBon);
			if (Objects.nonNull(bon) && ids.size() > 0) {
				EuCompte comptepbf = compteService.getById(codeComptePbf);
				EuMembreMorale moral = moraleService.findById(bon.getBonCodeMembreEmetteur());
				if (!Objects.nonNull(moral)) {
					return false;
				}
				if (!moral.getTypeFournisseur().equals("externe") && !moral.getTypeFournisseur().equals("specifique")) {
					return false;
				}

				EuCompte compteGcp = compteService.getById("NB-TPAGCP-" + bon.getBonCodeMembreEmetteur());
				List<EuTpagcp> tpagcps = tpagcpService.findByIds(ids);
				if (modeReg.equals("ESC")) {

					LocalDate ldate = LocalDate.now();
					Date date = new Date();
					Integer periode = paramService.getParam("periode", "valeur");
					Double tauxAgio = paramService.getParametre("taux", "agio");
					double agio = Math.floor((montant * tauxAgio) / 100);
					double net = montant - agio;
					Long id_echange = echangeService.findLastEchangeInsertedId();
					if (id_echange == null) {
						id_echange = 0L;
					}
					EuEchange ech = new EuEchange();
					ech.setIdEchange(id_echange + 1);
					ech.setCatEchange("GCP");
					ech.setTypeEchange("NB/NN");
					ech.setMontantEchange(montant);
					ech.setMontant(net);
					ech.setCompenser(0);
					ech.setRegler(1);
					ech.setEuCompte(compteGcp);
					ech.setCodeCompteObt(null);
					ech.setDateEchange(date);
					ech.setDateReglement(date);
					ech.setEuMembreMorale(compteGcp.getEuMembreMorale());
					ech.setEuMembre(null);
					ech.setCodeProduit("GCP");
					ech.setAgio(agio);
					echangeService.add(ech);

					if (typeEsc.equals("P")) {
						for (EuTpagcp tpagcp : tpagcps) {
							List<EuBonGcpPrelever> gcps_prels = bonGcpservice.findByIdTpagcp(tpagcp.getIdTpagcp());
							if (gcps_prels.size() > 0) {

								double mont_escompte = tpagcp.getMontTranche() * nbre;
								double mont_agio = Math.ceil((mont_escompte * tauxAgio) / 100);
								double mont_net = mont_escompte - mont_agio;
								LocalDate date_deb = tpagcp.getDateDebTranche().plusDays(periode * nbre);
								EuEscompte escompte = new EuEscompte();
								Long id_escompte = escompteService.getLastInsertedId();
								if (id_escompte != null) {
									id_escompte++;
								} else {
									id_escompte = 1L;
								}
								escompte.setIdEscompte(id_escompte);
								escompte.setIdEchange(ech.getIdEchange());
								escompte.setCodeMembre(moral.getCodeMembreMorale());
								escompte.setCodeMembreBenef(codeComptePbf);
								escompte.setDateDeb(tpagcp.getDateDebTranche());
								escompte.setDateDebTranche(tpagcp.getDateDebTranche());
								escompte.setDateEscompte(ldate);
								escompte.setDateFin(date_deb);
								escompte.setDateFinTranche(tpagcp.getDateFinTranche());
								escompte.setEuCompte(compteGcp);
								escompte.setMontEchu(0);
								escompte.setMontEchuTransferer(0);
								escompte.setMontTranche(tpagcp.getMontTranche());
								escompte.setMontant(mont_escompte);
								escompte.setNtf(nbre);
								escompte.setPeriode(tpagcp.getPeriode());
								escompte.setResteNtf(nbre);
								escompte.setSolde(mont_escompte);
								escompte.setTxAgioApplique(tauxAgio);
								escompteService.add(escompte);

								String code_gcp_pbf = "GCP-GCP-" + codeMembrePbf;
								EuGcpPbf gcp_pbf = gcpPbfService.findById(code_gcp_pbf);
								if (Objects.nonNull(gcp_pbf)) {
									gcp_pbf.setMontGcp(gcp_pbf.getMontGcp() + mont_escompte);
									gcp_pbf.setMontAgio(gcp_pbf.getMontAgio() + mont_agio);
									gcp_pbf.setMontGcpReel(gcp_pbf.getMontGcpReel() + mont_net);
									gcp_pbf.setSoldeAgio(gcp_pbf.getSoldeAgio() + mont_agio);
									gcp_pbf.setSoldeGcpReel(gcp_pbf.getSoldeGcpReel() + mont_net);
									gcp_pbf.setSoldeGcp(gcp_pbf.getSoldeGcp() + mont_escompte);
									gcpPbfService.update(gcp_pbf);
								} else {
									gcp_pbf = new EuGcpPbf();
									gcp_pbf.setCodeGcpPbf(code_gcp_pbf);
									gcp_pbf.setCodeMembre(codeMembrePbf);
									gcp_pbf.setEuCompte(comptepbf);
									gcp_pbf.setAgioConsomme(0);
									gcp_pbf.setGcpCompense(0);
									gcp_pbf.setMontGcp(mont_escompte);
									gcp_pbf.setMontAgio(mont_agio);
									gcp_pbf.setTypeCapa("FGGCP");
									gcp_pbf.setMontGcpReel(mont_net);
									gcp_pbf.setSoldeAgio(mont_agio);
									gcp_pbf.setSoldeGcpReel(mont_net);
									gcp_pbf.setSoldeGcp(mont_escompte);
									gcpPbfService.add(gcp_pbf);
								}

								double mont_deduire = mont_escompte;
								int i = 0;
								System.out.println("EP: Nbre de GCP prelever :" + gcps_prels.size()
										+ " Montant à déduire = " + mont_deduire);
								while (mont_deduire > 0) {
									EuBonGcpPrelever gcp_p = gcps_prels.get(i);
									if (gcp_p.getMontantBon() < mont_deduire) {
										System.out.println("EP : Cas 1");
										EuGcp eugcp = gcp_p.getEuGcpPrelever().getEuGcp();
										EuCnp cnp = cnpService.findByIdGcp(eugcp.getIdGcp());
										if (Objects.nonNull(cnp)) {

											cnp.setMontCredit(cnp.getMontCredit() + gcp_p.getMontantBon());
											cnp.setSoldeCnp(cnp.getSoldeCnp() - gcp_p.getMontantBon());
											cnpService.update(cnp);

											EuCnpEntree cnp_e = new EuCnpEntree();
											Long idCnpEntree = cnpEntService.getLastInsertedId();
											if (idCnpEntree != null) {
												idCnpEntree += 1;
											} else {
												idCnpEntree = 1L;
											}
											cnp_e.setIdCnpEntree(idCnpEntree);
											cnp_e.setEuCnp(cnp);
											cnp_e.setTypeCnpEntree("GCP");
											cnp_e.setMontCnpEntree(gcp_p.getMontantBon());
											cnp_e.setDateEntree(date);
											cnpEntService.add(cnp_e);

											EuDetailGcpPbf detGcpPbf = new EuDetailGcpPbf();
											Long idGcppbf = detGcpPbfservice.getLastInsertedId();
											if (idGcppbf != null) {
												idGcppbf++;
											} else {
												idGcppbf = 1L;
											}
											detGcpPbf.setAgio(mont_agio);
											detGcpPbf.setEuGcpPbf(gcp_pbf);
											detGcpPbf.setIdCredit(eugcp.getIdGcp());
											detGcpPbf.setMontGcpPbf(gcp_p.getMontantBon());
											detGcpPbf.setMontPreleve(0);
											detGcpPbf.setTypeCapa("FGGCP");
											detGcpPbf.setSourceCredit(eugcp.getSource());
											detGcpPbf.setIdGcpPbf(idGcppbf);
											detGcpPbf.setSoldeGcpPbf(gcp_p.getMontantBon());
											detGcpPbf.setIdEchange(id_echange);
											detGcpPbf.setIdEscompte(id_escompte);
											detGcpPbfservice.add(detGcpPbf);

											mont_deduire -= gcp_p.getMontantBon();

											i++;
										} else {
											throw new CompteNonIntegreException("Votre compte n'est pas cohérent");
										}
									} else {
										System.out.println("EP : Cas 2");
										EuGcp eugcp = gcp_p.getEuGcpPrelever().getEuGcp();
										EuCnp cnp = cnpService.findByIdGcp(eugcp.getIdGcp());
										if (Objects.nonNull(cnp)) {

											cnp.setMontCredit(cnp.getMontCredit() + mont_deduire);
											cnp.setSoldeCnp(cnp.getSoldeCnp() - mont_deduire);
											cnpService.update(cnp);

											EuCnpEntree cnp_e = new EuCnpEntree();
											Long idCnpEntree = cnpEntService.getLastInsertedId();
											if (idCnpEntree != null) {
												idCnpEntree += 1;
											} else {
												idCnpEntree = 1L;
											}
											cnp_e.setIdCnpEntree(idCnpEntree);
											cnp_e.setEuCnp(cnp);
											cnp_e.setTypeCnpEntree("GCP");
											cnp_e.setMontCnpEntree(mont_deduire);
											cnp_e.setDateEntree(date);
											cnpEntService.add(cnp_e);

											EuDetailGcpPbf detGcpPbf = new EuDetailGcpPbf();
											Long idGcppbf = detGcpPbfservice.getLastInsertedId();
											if (idGcppbf != null) {
												idGcppbf++;
											} else {
												idGcppbf = 1L;
											}
											detGcpPbf.setAgio(mont_agio);
											detGcpPbf.setEuGcpPbf(gcp_pbf);
											detGcpPbf.setIdCredit(eugcp.getEuCompteCredit().getIdCredit());
											detGcpPbf.setMontGcpPbf(mont_deduire);
											detGcpPbf.setMontPreleve(0);
											detGcpPbf.setTypeCapa("FGGCP");
											detGcpPbf.setSourceCredit(eugcp.getSource());
											detGcpPbf.setIdGcpPbf(idGcppbf);
											detGcpPbf.setSoldeGcpPbf(mont_deduire);
											detGcpPbf.setIdEchange(id_echange);
											detGcpPbf.setIdEscompte(id_escompte);
											detGcpPbfservice.add(detGcpPbf);

											mont_deduire = 0;
										} else {
											throw new CompteNonIntegreException("Votre compte n'est pas corhérent");
										}
									}
								}
								comptepbf.setSolde(comptepbf.getSolde() + mont_escompte);
								compteService.update(comptepbf);

							} else {
								throw new CompteNonIntegreException("Votre compte n'est pas corhérent");
							}
						}
					} else {
						for (EuTpagcp tpagcp : tpagcps) {
							List<EuBonGcpPrelever> gcps_prels = bonGcpservice.findByIdTpagcp(tpagcp.getIdTpagcp());
							if (gcps_prels.size() > 0) {

								double mont_escompte = tpagcp.getSolde();
								double mont_agio = Math.ceil((mont_escompte * tauxAgio) / 100);
								double mont_net = mont_escompte - mont_agio;
								EuEscompte escompte = new EuEscompte();
								Long id_escompte = escompteService.getLastInsertedId();
								if (id_escompte != null) {
									id_escompte++;
								} else {
									id_escompte = 1L;
								}
								escompte.setIdEscompte(id_escompte);
								escompte.setIdEchange(ech.getIdEchange());
								escompte.setCodeMembre(moral.getCodeMembreMorale());
								escompte.setCodeMembreBenef(codeComptePbf);
								escompte.setDateDeb(tpagcp.getDateDebTranche());
								escompte.setDateDebTranche(tpagcp.getDateDebTranche());
								escompte.setDateEscompte(ldate);
								escompte.setDateFin(tpagcp.getDateFin());
								escompte.setDateFinTranche(tpagcp.getDateFinTranche());
								escompte.setEuCompte(compteGcp);
								escompte.setMontEchu(0);
								escompte.setMontEchuTransferer(0);
								escompte.setMontTranche(tpagcp.getMontTranche());
								escompte.setMontant(mont_escompte);
								escompte.setNtf(tpagcp.getResteNtf());
								escompte.setPeriode(tpagcp.getPeriode());
								escompte.setResteNtf(tpagcp.getResteNtf());
								escompte.setSolde(mont_escompte);
								escompte.setTxAgioApplique(tauxAgio);
								escompteService.add(escompte);

								String code_gcp_pbf = "GCP-GCP-" + codeMembrePbf;
								EuGcpPbf gcp_pbf = gcpPbfService.findById(code_gcp_pbf);
								if (Objects.nonNull(gcp_pbf)) {
									gcp_pbf.setMontGcp(gcp_pbf.getMontGcp() + mont_escompte);
									gcp_pbf.setMontAgio(gcp_pbf.getMontAgio() + mont_agio);
									gcp_pbf.setMontGcpReel(gcp_pbf.getMontGcpReel() + mont_net);
									gcp_pbf.setSoldeAgio(gcp_pbf.getSoldeAgio() + mont_agio);
									gcp_pbf.setSoldeGcpReel(gcp_pbf.getSoldeGcpReel() + mont_net);
									gcp_pbf.setSoldeGcp(gcp_pbf.getSoldeGcp() + mont_escompte);
									gcpPbfService.update(gcp_pbf);
								} else {
									gcp_pbf = new EuGcpPbf();
									gcp_pbf.setCodeGcpPbf(code_gcp_pbf);
									gcp_pbf.setCodeMembre(codeMembrePbf);
									gcp_pbf.setEuCompte(comptepbf);
									gcp_pbf.setAgioConsomme(0);
									gcp_pbf.setGcpCompense(0);
									gcp_pbf.setMontGcp(mont_escompte);
									gcp_pbf.setMontAgio(mont_agio);
									gcp_pbf.setTypeCapa("FGGCP");
									gcp_pbf.setMontGcpReel(mont_net);
									gcp_pbf.setSoldeAgio(mont_agio);
									gcp_pbf.setSoldeGcpReel(mont_net);
									gcp_pbf.setSoldeGcp(mont_escompte);
									gcpPbfService.add(gcp_pbf);
								}

								double mont_deduire = mont_escompte;
								int i = 0;
								while (mont_deduire > 0) {
									EuBonGcpPrelever gcp_p = gcps_prels.get(i);
									if (gcp_p.getMontantBon() < mont_deduire) {
										EuGcp eugcp = gcp_p.getEuGcpPrelever().getEuGcp();
										EuCnp cnp = cnpService.findByIdGcp(eugcp.getIdGcp());
										if (Objects.nonNull(cnp)) {

											cnp.setMontCredit(cnp.getMontCredit() + gcp_p.getMontantBon());
											cnp.setSoldeCnp(cnp.getSoldeCnp() - gcp_p.getMontantBon());
											cnpService.update(cnp);

											EuCnpEntree cnp_e = new EuCnpEntree();
											Long idCnpEntree = cnpEntService.getLastInsertedId();
											if (idCnpEntree != null) {
												idCnpEntree += 1;
											} else {
												idCnpEntree = 1L;
											}
											cnp_e.setIdCnpEntree(idCnpEntree);
											cnp_e.setEuCnp(cnp);
											cnp_e.setTypeCnpEntree("GCP");
											cnp_e.setMontCnpEntree(gcp_p.getMontantBon());
											cnp_e.setDateEntree(date);
											cnpEntService.add(cnp_e);

											EuDetailGcpPbf detGcpPbf = new EuDetailGcpPbf();
											Long idGcppbf = detGcpPbfservice.getLastInsertedId();
											if (idGcppbf != null) {
												idGcppbf++;
											} else {
												idGcppbf = 1L;
											}
											detGcpPbf.setAgio(mont_agio);
											detGcpPbf.setEuGcpPbf(gcp_pbf);
											detGcpPbf.setIdCredit(eugcp.getIdGcp());
											detGcpPbf.setMontGcpPbf(gcp_p.getMontantBon());
											detGcpPbf.setMontPreleve(0);
											detGcpPbf.setTypeCapa("FGGCP");
											detGcpPbf.setSourceCredit(eugcp.getSource());
											detGcpPbf.setIdGcpPbf(idGcppbf);
											detGcpPbf.setSoldeGcpPbf(gcp_p.getMontantBon());
											detGcpPbf.setIdEchange(id_echange);
											detGcpPbf.setIdEscompte(id_escompte);
											detGcpPbfservice.add(detGcpPbf);

											mont_deduire -= gcp_p.getMontantBon();

											i++;
										} else {
											throw new CompteNonIntegreException("Votre compte n'est pas corhérent");
										}
									} else {
										EuGcp eugcp = gcp_p.getEuGcpPrelever().getEuGcp();
										EuCnp cnp = cnpService.findByIdGcp(eugcp.getIdGcp());
										if (Objects.nonNull(cnp)) {

											cnp.setMontCredit(cnp.getMontCredit() + mont_deduire);
											cnp.setSoldeCnp(cnp.getSoldeCnp() - mont_deduire);
											cnpService.update(cnp);

											EuCnpEntree cnp_e = new EuCnpEntree();
											Long idCnpEntree = cnpEntService.getLastInsertedId();
											if (idCnpEntree != null) {
												idCnpEntree += 1;
											} else {
												idCnpEntree = 1L;
											}
											cnp_e.setIdCnpEntree(idCnpEntree);
											cnp_e.setEuCnp(cnp);
											cnp_e.setTypeCnpEntree("GCP");
											cnp_e.setMontCnpEntree(mont_deduire);
											cnp_e.setDateEntree(date);
											cnpEntService.add(cnp_e);

											EuDetailGcpPbf detGcpPbf = new EuDetailGcpPbf();
											Long idGcppbf = detGcpPbfservice.getLastInsertedId();
											if (idGcppbf != null) {
												idGcppbf++;
											} else {
												idGcppbf = 1L;
											}
											detGcpPbf.setAgio(mont_agio);
											detGcpPbf.setEuGcpPbf(gcp_pbf);
											detGcpPbf.setIdCredit(eugcp.getEuCompteCredit().getIdCredit());
											detGcpPbf.setMontGcpPbf(mont_deduire);
											detGcpPbf.setMontPreleve(0);
											detGcpPbf.setTypeCapa("FGGCP");
											detGcpPbf.setSourceCredit(eugcp.getSource());
											detGcpPbf.setIdGcpPbf(idGcppbf);
											detGcpPbf.setSoldeGcpPbf(mont_deduire);
											detGcpPbf.setIdEchange(id_echange);
											detGcpPbf.setIdEscompte(id_escompte);
											detGcpPbfservice.add(detGcpPbf);

											mont_deduire = 0;
										} else {
											throw new CompteNonIntegreException("Votre compte n'est pas corhérent");
										}
									}
								}

								comptepbf.setSolde(comptepbf.getSolde() + mont_escompte);
								compteService.update(comptepbf);

							} else {
								throw new CompteNonIntegreException("Votre compte n'est pas corhérent");
							}
						}
					}
				} else {

					Date date = new Date();
					Long id_echange = echangeService.findLastEchangeInsertedId();
					if (id_echange == null) {
						id_echange = 0L;
					}
					EuEchange ech = new EuEchange();
					ech.setIdEchange(id_echange + 1);
					ech.setCatEchange("GCP");
					ech.setTypeEchange("NB/NN");
					ech.setMontantEchange(montant);
					ech.setMontant(montant);
					ech.setCompenser(0);
					ech.setRegler(1);
					ech.setEuCompte(compteGcp);
					ech.setCodeCompteObt(null);
					ech.setDateEchange(date);
					ech.setDateReglement(date);
					ech.setEuMembreMorale(compteGcp.getEuMembreMorale());
					ech.setEuMembre(null);
					ech.setCodeProduit("GCP");
					ech.setAgio(0);
					echangeService.add(ech);

					for (EuTpagcp tpagcp : tpagcps) {
						List<EuBonGcpPrelever> gcps_prels = bonGcpservice.findByIdTpagcp(tpagcp.getIdTpagcp());
						if (gcps_prels.size() > 0) {

							String code_gcp_pbf = "GCP-GCP-" + codeMembrePbf;
							EuGcpPbf gcp_pbf = gcpPbfService.findById(code_gcp_pbf);
							if (Objects.nonNull(gcp_pbf)) {
								gcp_pbf.setMontGcp(gcp_pbf.getMontGcp() + montant);
								gcp_pbf.setMontGcpReel(gcp_pbf.getMontGcpReel() + montant);
								gcp_pbf.setSoldeGcpReel(gcp_pbf.getSoldeGcpReel() + montant);
								gcp_pbf.setSoldeGcp(gcp_pbf.getSoldeGcp() + montant);
								gcpPbfService.update(gcp_pbf);
							} else {
								gcp_pbf = new EuGcpPbf();
								gcp_pbf.setCodeGcpPbf(code_gcp_pbf);
								gcp_pbf.setCodeMembre(codeMembrePbf);
								gcp_pbf.setEuCompte(comptepbf);
								gcp_pbf.setAgioConsomme(0);
								gcp_pbf.setGcpCompense(0);
								gcp_pbf.setMontGcp(montant);
								gcp_pbf.setMontAgio(0);
								gcp_pbf.setTypeCapa("FGGCP");
								gcp_pbf.setMontGcpReel(montant);
								gcp_pbf.setSoldeAgio(0);
								gcp_pbf.setSoldeGcpReel(montant);
								gcp_pbf.setSoldeGcp(montant);
								gcpPbfService.add(gcp_pbf);
							}

							double mont_deduire = montant;
							int i = 0;
							System.out.println("EP: Nbre de GCP prelever :" + gcps_prels.size()
									+ " Montant à déduire = " + mont_deduire);
							while (mont_deduire > 0) {
								EuBonGcpPrelever gcp_p = gcps_prels.get(i);
								if (gcp_p.getMontantBon() < mont_deduire) {
									System.out.println("EP : Cas 1");
									EuGcp eugcp = gcp_p.getEuGcpPrelever().getEuGcp();
									EuCnp cnp = cnpService.findByIdGcp(eugcp.getIdGcp());
									if (Objects.nonNull(cnp)) {

										cnp.setMontCredit(cnp.getMontCredit() + gcp_p.getMontantBon());
										cnp.setSoldeCnp(cnp.getSoldeCnp() - gcp_p.getMontantBon());
										cnpService.update(cnp);

										EuCnpEntree cnp_e = new EuCnpEntree();
										Long idCnpEntree = cnpEntService.getLastInsertedId();
										if (idCnpEntree != null) {
											idCnpEntree += 1;
										} else {
											idCnpEntree = 1L;
										}
										cnp_e.setIdCnpEntree(idCnpEntree);
										cnp_e.setEuCnp(cnp);
										cnp_e.setTypeCnpEntree("GCP");
										cnp_e.setMontCnpEntree(gcp_p.getMontantBon());
										cnp_e.setDateEntree(date);
										cnpEntService.add(cnp_e);

										EuDetailGcpPbf detGcpPbf = new EuDetailGcpPbf();
										Long idGcppbf = detGcpPbfservice.getLastInsertedId();
										if (idGcppbf != null) {
											idGcppbf++;
										} else {
											idGcppbf = 1L;
										}
										detGcpPbf.setAgio(0);
										detGcpPbf.setEuGcpPbf(gcp_pbf);
										detGcpPbf.setIdCredit(eugcp.getIdGcp());
										detGcpPbf.setMontGcpPbf(gcp_p.getMontantBon());
										detGcpPbf.setMontPreleve(0);
										detGcpPbf.setTypeCapa("FGGCP");
										detGcpPbf.setSourceCredit(eugcp.getSource());
										detGcpPbf.setIdGcpPbf(idGcppbf);
										detGcpPbf.setSoldeGcpPbf(gcp_p.getMontantBon());
										detGcpPbf.setIdEchange(id_echange);
										detGcpPbf.setIdEscompte(null);
										detGcpPbfservice.add(detGcpPbf);

										mont_deduire -= gcp_p.getMontantBon();

										i++;
									} else {
										throw new CompteNonIntegreException("Votre compte n'est pas corhérent");
									}
								} else {
									System.out.println("EP : Cas 2");
									EuGcp eugcp = gcp_p.getEuGcpPrelever().getEuGcp();
									EuCnp cnp = cnpService.findByIdGcp(eugcp.getIdGcp());
									if (Objects.nonNull(cnp)) {

										cnp.setMontCredit(cnp.getMontCredit() + mont_deduire);
										cnp.setSoldeCnp(cnp.getSoldeCnp() - mont_deduire);
										cnpService.update(cnp);

										EuCnpEntree cnp_e = new EuCnpEntree();
										Long idCnpEntree = cnpEntService.getLastInsertedId();
										if (idCnpEntree != null) {
											idCnpEntree += 1;
										} else {
											idCnpEntree = 1L;
										}
										cnp_e.setIdCnpEntree(idCnpEntree);
										cnp_e.setEuCnp(cnp);
										cnp_e.setTypeCnpEntree("GCP");
										cnp_e.setMontCnpEntree(mont_deduire);
										cnp_e.setDateEntree(date);
										cnpEntService.add(cnp_e);

										EuDetailGcpPbf detGcpPbf = new EuDetailGcpPbf();
										Long idGcppbf = detGcpPbfservice.getLastInsertedId();
										if (idGcppbf != null) {
											idGcppbf++;
										} else {
											idGcppbf = 1L;
										}
										detGcpPbf.setAgio(0);
										detGcpPbf.setEuGcpPbf(gcp_pbf);
										detGcpPbf.setIdCredit(eugcp.getEuCompteCredit().getIdCredit());
										detGcpPbf.setMontGcpPbf(mont_deduire);
										detGcpPbf.setMontPreleve(0);
										detGcpPbf.setTypeCapa("FGGCP");
										detGcpPbf.setSourceCredit(eugcp.getSource());
										detGcpPbf.setIdGcpPbf(idGcppbf);
										detGcpPbf.setSoldeGcpPbf(mont_deduire);
										detGcpPbf.setIdEchange(id_echange);
										detGcpPbf.setIdEscompte(null);
										detGcpPbfservice.add(detGcpPbf);

										mont_deduire = 0;
									} else {
										throw new CompteNonIntegreException("Votre compte n'est pas corhérent");
									}
								}
							}

							comptepbf.setSolde(comptepbf.getSolde() + montant);
							compteService.update(comptepbf);

						} else {
							throw new CompteNonIntegreException("Votre compte n'est pas corhérent");
						}
					}
				}
			}
		}
		return false;
	}

}

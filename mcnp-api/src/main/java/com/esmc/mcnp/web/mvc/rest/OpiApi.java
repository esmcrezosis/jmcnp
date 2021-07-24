package com.esmc.mcnp.web.mvc.rest;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.esmc.mcnp.components.EchangeUtility;
//import com.esmc.mcnp.components.CreditUtility;
import com.esmc.mcnp.components.OPIComponent;
import com.esmc.mcnp.components.OpiUtility;
import com.esmc.mcnp.components.Payement;
import com.esmc.mcnp.components.SmcipnComponent;
import com.esmc.mcnp.components.TransfertUtility;
import com.esmc.mcnp.dto.bc.CalculBonInfo;
import com.esmc.mcnp.model.pc.EuCharge;
import com.esmc.mcnp.model.pc.EuChargePaye;
import com.esmc.mcnp.model.others.EuDemandeAchat;
import com.esmc.mcnp.model.acteur.EuEli;
import com.esmc.mcnp.model.smcipn.EuFormsBudgetNature;
import com.esmc.mcnp.model.op.EuProjet;
import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.obps.EuTegc;
import com.esmc.mcnp.model.obpsd.EuTpagcp;
import com.esmc.mcnp.model.obpsd.EuTraite;
import com.esmc.mcnp.model.smcipn.EuSmcipnpwi;
import com.esmc.mcnp.services.acteurs.EuDetailEliService;
import com.esmc.mcnp.services.acteurs.EuEliService;
import com.esmc.mcnp.services.acteurs.EuFormsBudgetNatureService;
import com.esmc.mcnp.services.bc.EuBonService;
import com.esmc.mcnp.services.cm.EuCompteService;
import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.cm.EuMembreService;
import com.esmc.mcnp.services.obps.EuTegcService;
import com.esmc.mcnp.services.obpsd.EuTpagcpService;
import com.esmc.mcnp.services.obpsd.EuTraiteService;
import com.esmc.mcnp.services.op.EuProjetService;
import com.esmc.mcnp.services.pc.EuChargePayeService;
import com.esmc.mcnp.services.pc.EuChargeService;
import com.esmc.mcnp.services.pc.EuDemandeAchatService;
import com.esmc.mcnp.services.setting.EuParametresService;
import com.esmc.mcnp.services.smcipn.EuSmcipnpwiService;
import com.esmc.mcnp.web.mvc.dto.Budget;
import com.esmc.mcnp.web.mvc.dto.Eli;
//import com.esmc.mcnp.services.EuKacmService;
import com.esmc.mcnp.web.mvc.dto.Opi;
import com.esmc.mcnp.web.mvc.dto.Opi_B;
import com.esmc.mcnp.web.mvc.dto.Result;
//import com.esmc.mcnp.web.mvc.utils.BaseRestController;
import com.esmc.mcnp.web.mvc.dto.TransformTraite;
import com.google.common.collect.Lists;

@RestController
@RequestMapping("/souscriptionOpi")
public class OpiApi {

	/**
	 * Logger.
	 */
	private final Logger log = LogManager.getLogger(OpiApi.class.getName());

	private EuTegcService tegcService;
	private Payement payement;
	private SmcipnComponent smcipnCompo;
	private EuBonService bonService;
	private TransfertUtility transfertUtility;
	private EuParametresService parametresService;
	private OPIComponent opiComponent;
	private EuCompteService compteService;
	private EuMembreService membreService;
	private EuMembreMoraleService moraleService;
	private EuEliService eliService;
	private EuDetailEliService detailEliService;
	private OpiUtility opiUtility;
	private EchangeUtility echangeUtility;
	private EuTraiteService traiteService;
	private EuTpagcpService tpagcpService;
	private EuFormsBudgetNatureService budgetNatureService;
	private EuDemandeAchatService demandeAchatService;
	private EuProjetService projetService;
	private EuSmcipnpwiService smcipnService;
	private EuChargeService chargeService;
	private EuChargePayeService chargePayeService;
	// private CreditUtility creditUtility;
	// private EuKacmService kacmService;

	@Autowired
	public OpiApi(EuCompteService compteService, EuTegcService tegcService, Payement payement,
                  SmcipnComponent smcipnCompo, EuBonService bonService, TransfertUtility transfertUtility,
                  EuParametresService parametresService, OPIComponent opiComponent, EuMembreService membreService,
                  EuMembreMoraleService moraleService, OpiUtility opiUtility, EuEliService eliService,
                  EuDetailEliService detailEliService, EchangeUtility echangeUtility, EuTraiteService traiteService,
                  EuTpagcpService tpagcpService, EuFormsBudgetNatureService budgetNatureService,
                  EuDemandeAchatService demandeAchatService, EuProjetService projetService, EuChargeService chargeService,
                  EuChargePayeService chargePayeService, EuSmcipnpwiService smcipnService
                  /**
	 * ,CreditUtility creditUtility, EuKacmService kacmService
	 */
	) {
		this.membreService = membreService;
		this.moraleService = moraleService;
		this.compteService = compteService;
		this.tegcService = tegcService;
		this.payement = payement;
		this.smcipnCompo = smcipnCompo;
		this.bonService = bonService;
		this.transfertUtility = transfertUtility;
		this.parametresService = parametresService;
		this.opiComponent = opiComponent;
		this.eliService = eliService;
		this.detailEliService = detailEliService;
		this.opiUtility = opiUtility;
		this.echangeUtility = echangeUtility;
		this.traiteService = traiteService;
		this.tpagcpService = tpagcpService;
		this.budgetNatureService = budgetNatureService;
		this.demandeAchatService = demandeAchatService;
		this.projetService = projetService;
		this.chargePayeService = chargePayeService;
		this.chargeService = chargeService;
		this.smcipnService = smcipnService;
		// this.creditUtility = creditUtility;
		// this.kacmService = kacmService;
	}

	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = "/eli", method = RequestMethod.POST)
	public ResponseEntity<Result> makeOpiEli(@RequestBody Eli eliDto) {
		if (StringUtils.isNotBlank(eliDto.getNumeroEli())) {
			Optional<EuEli> opeli = eliService.findByNumero(eliDto.getNumeroEli());
			if (opeli.isPresent()) {
				EuEli eli = opeli.get();
				if (eli.getValider() == 4) {
					double montDetEli = detailEliService.getSommeDetailByeliAndStatut(eli.getIdEli(), 1);
					if (montDetEli == eli.getMontantEli()) {
						EuTegc tegc = tegcService.findByCodeMembre(eli.getCodeMembre()).get(0);
						if (eli.getBan()) {
							opiUtility.souscrireBan(eli.getCodeMembre(), "ELI", eli.getNumeroEli(), null);
						}
						if (eli.getBai() || eli.getOpi()) {
							double montSmcipn = 0;
							if (eli.getBai() && eli.getOpi()) {
								montSmcipn = eli.getMontantBai() + eli.getMontantOpi();
							} else if (eli.getBai()) {
								montSmcipn = eli.getMontantBai();
							} else if (eli.getOpi()) {
								montSmcipn = eli.getMontantOpi();
							}
							opiUtility.reglerELIParSmcipn("0010010010010000212M", eliDto.getNumeroEli(), tegc,
									montSmcipn);
						}
						if (tegc.getSubvention() != 2) {
							tegc.setSubvention(2);
							tegcService.update(tegc);
						}
						payement.stockArticle(eli);
						eli.setPayer(true);
						eliService.update(eli);
						return new ResponseEntity<>(new Result(1, "L'opération a ete bien effectuée"), HttpStatus.OK);
					} else {
						return new ResponseEntity<>(new Result(0, "Echec de l'opération : ELI non intègre"),
								HttpStatus.OK);
					}
				} else {
					return new ResponseEntity<>(new Result(0, "Echec de l'opération : Cet ELI n'est pas encore validé"),
							HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>(new Result(0, "Echec de l'opération : ELI non trouvé"), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(new Result(0, "Echec de l'opération: Veuillez bien vérifier votre numéro d'ELI"),
				HttpStatus.OK);
	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	@PostMapping(value = "/budget")
	public ResponseEntity<Result> emissionBudget(@RequestBody Budget budget) {
		if (budget.getIdBudget() != null && budget.getIdTypebudget() != null
				&& StringUtils.isNotBlank(budget.getTypeBudget())) {
			EuFormsBudgetNature budgetNature = budgetNatureService.findById(budget.getIdBudget().intValue());
			if (Objects.nonNull(budgetNature) && budgetNature.isValidBudget() && !budgetNature.isAffecterBudget()) {
				EuProjet projet = null;
				EuDemandeAchat demandeAchat = null;
				String codeMembre = StringUtils.EMPTY;
				if (budget.getTypeBudget().equalsIgnoreCase("OP")) {
					projet = projetService.findById(budgetNature.getReferenceTypeBudget().intValue());
					codeMembre = projet.getProjetCodeMembre();
				} else {
					demandeAchat = demandeAchatService.findById(budgetNature.getReferenceTypeBudget());
					codeMembre = demandeAchat.getCodeMembre();
				}

				if ((Objects.nonNull(projet) && projet.getPublier())
						|| (Objects.nonNull(demandeAchat) && demandeAchat.getValiderUp() == 1)) {
					Date date = new Date();
					String numeroAppel = StringUtils.EMPTY;
					double montant = budgetNature.getMontantBudget();
					EuTegc tegc = null;
					List<EuTegc> tegcs = tegcService.findByCodeMembre(budgetNature.getCodeMembreBudget());
					if (!tegcs.isEmpty()) {
						tegc = tegcs.get(0);
					}
					numeroAppel = smcipnCompo.doSmcipnCharge("0010010010010000212M", tegc.getCodeTegc(),
							"SMCIPN de remboursement des souscription pour tiers", montant);
					if (StringUtils.isNotBlank(numeroAppel)) {
						EuCharge tcharge = chargeService.findByCode("CE");
						EuSmcipnpwi smcipn = smcipnService.findByNumeroAppel(numeroAppel);
						EuChargePaye chargePaye = new EuChargePaye();
						chargePaye.setEuSmcipnpwi(smcipn);
						chargePaye.setEuCharge(tcharge);
						chargePaye.setCodeMembreDebiteur("0010010010010000212M");
						if (Objects.nonNull(projet)) {
							chargePaye.setTypeDoc("OP");
							chargePaye.setNumDoc(String.valueOf(projet.getProjetId()));
							chargePaye.setCodeMembreCreancier(projet.getProjetCodeMembre());
						} else {
							chargePaye.setTypeDoc("DA");
							chargePaye.setNumDoc(String.valueOf(demandeAchat.getIdDemandeAchat()));
							chargePaye.setCodeMembreCreancier(demandeAchat.getCodeMembre());
						}
						chargePaye.setDateCharge(date);
						chargePaye.setLibelleCharge("Paiement anticipé d'ELI");
						chargePaye.setMontantCharge(montant);
						chargePaye.setOrigineCharge("ESMC");
						chargePayeService.add(chargePaye);

						smcipnCompo.echangeNRNN("0010010010010000212M", codeMembre, numeroAppel, "PS", montant, null);
					}
				}
			}
		}
		return new ResponseEntity<>(new Result(0, "Echec de l'opération: Veuillez bien vérifier les infos"),
				HttpStatus.OK);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/transformOpi", method = RequestMethod.POST)
	public ResponseEntity<Result> transformOpi(@RequestBody TransformTraite transTraite) {
		System.out.println("ID Traite " + transTraite.getIdTraite() + " Mode de paiement : " + transTraite.getMode());
		if (Objects.nonNull(transTraite) && transTraite.getIdTraite() != null
				&& StringUtils.isNotBlank(transTraite.getMode())) {
			Date date = new Date();
			EuTraite traite = traiteService.findById(transTraite.getIdTraite());
			if (Objects.nonNull(traite) && traite.getTraiteDisponible() == 1 && traite.getTraitePayer() == 0
					&& StringUtils.isBlank(traite.getTraiteNumero())
					&& date.compareTo(traite.getTraiteDateFin()) >= 0) {
				EuTpagcp tpagcp = tpagcpService.findById(traite.getTraiteTegcp());
				if (transTraite.getMode().equals("BAi")) {
					System.out.println("Reglement par " + transTraite.getMode());
					List<EuTraite> traites = Lists.newArrayList(traite);
					echangeUtility.echangeOpiCommandeNr(tpagcp.getCodeMembre(), traites, traite.getTraiteMontant());
					return new ResponseEntity<>(new Result(1, "L'opération a ete bien effectuée"), HttpStatus.OK);
				} else if (transTraite.getMode().equals("BAn")) {
					System.out.println("Reglement par " + transTraite.getMode());
					opiUtility.souscrireBan(tpagcp.getCodeMembre(), "OPI", null, traite.getTraiteId());
					return new ResponseEntity<>(new Result(1, "L'opération a ete bien effectuée"), HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>(new Result(0,
						"L'opération a échouée : L'OPI à transformer n'est pas encore mature ou est déja entré dans la procédure de reglement bancaire"),
						HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>(
					new Result(0, "Echec de l'opération : Veuillez bien vérifier les infos fournies!!!"),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(
				new Result(0, "Echec de l'opération : Veuillez bien vérifier les infos fournies!!!"), HttpStatus.OK);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/emission", method = RequestMethod.POST)
	public ResponseEntity<Result> makeOpi(@RequestBody Opi opi) {
		if (StringUtils.isNotBlank(opi.getCodeMembre()) && StringUtils.isNotBlank(opi.getModePaiement())
				&& StringUtils.isNotBlank(opi.getReferencePaiement()) && opi.getMontant() > 0
				&& Objects.nonNull(opi.getSouscriptionId())) {
			try {
				Date date = new Date();
				double tpanu = 0;
				double mont_smcipn = 0;
				String codeMembreSmcipn = "0000000000000000019M";
				// double kacm = Math.ceil(creditUtility.calculKacm(opi.getTypeBnp(),
				// opi.getMontant()));
				if (opi.getCodeMembre().endsWith("M")) {
					tpanu = parametresService.getParametre("TPANU", "PM");
					mont_smcipn = Math.floor(opi.getMontant() + (opi.getMontant() * tpanu / 100));
				} else {
					tpanu = parametresService.getParametre("TPANU", "PP");
					mont_smcipn = Math.floor(opi.getMontant() + ((opi.getMontant() * tpanu / 100) * 2));
				}

				EuTegc tegc = null;
				if (StringUtils.isNotBlank(opi.getCodeTegc())) {
					tegc = tegcService.findByCodeTegc(opi.getCodeTegc());
				} else {
					List<EuTegc> tegcs = tegcService.findByCodeMembre(opi.getCodeMembre());
					if (!tegcs.isEmpty()) {
						tegc = tegcs.get(0);
					}
				}
				if (Objects.isNull(tegc) || StringUtils.isBlank(opi.getCodeTegc())) {
					tegc = tegcService.creerTe(opi.getCodeMembre());
				}
				EuBon bl = payement.createBonLivraison(opi.getCodeMembre(), codeMembreSmcipn, "BNP", "", null,
						mont_smcipn);
				if (Objects.nonNull(bl)) {

					bl.setBonDateExpression(date);
					bl.setBonExprimer(1);
					bonService.update(bl);

					smcipnCompo.echangeSmcipn(codeMembreSmcipn, "TPN", "PS", mont_smcipn, 8.0);
					CalculBonInfo bonInfo = new CalculBonInfo("nr", null, "PS", 22.4, 8);
					EuBon bonBc = transfertUtility.tansfertBC(codeMembreSmcipn, "TI", bonInfo, 8.0, mont_smcipn);
					payement.makeTransaction(opi.getCodeMembre(), codeMembreSmcipn, "TI", tegc, bonBc, bl, "BNP",
							mont_smcipn);
					payement.creerMarge(date, mont_smcipn);
					EuBon bon = opiComponent.emetreOpi(null, opi.getCodeMembre(), tegc, opi.getModePaiement(),
							opi.getReferencePaiement(), mont_smcipn, 23, 1, opi.getTypeOpi(), 0, 0, null, 0, 0, 0);
					opiComponent.validerEmissionOpi(bon.getBonNumero());

					EuCharge tcharge = chargeService.findByCode("CF");
					EuChargePaye chargePaye = new EuChargePaye();
					chargePaye.setEuSmcipnpwi(null);
					chargePaye.setEuCharge(tcharge);
					chargePaye.setNumDoc(null);
					chargePaye.setCodeMembreDebiteur(codeMembreSmcipn);
					chargePaye.setCodeMembreCreancier(opi.getCodeMembre());
					chargePaye.setDateCharge(new Date());
					chargePaye.setLibelleCharge("Souscription de BC pour tiers");
					chargePaye.setMontantCharge(mont_smcipn);
					chargePaye.setOrigineCharge("ESMC");
					chargePayeService.add(chargePaye);

					/*
					 * EuKacm kacmp = new EuKacm(); kacmp.setCodeMembre(opi.getCodeMembre());
					 * kacmp.setTypeActivite("SBC"); kacmp.setMontKacm(kacm);
					 * kacmp.setMontOp(opi.getMontant()); kacmService.add(kacmp);
					 */
					return new ResponseEntity<>(new Result(1, "L'opération a ete bien effectuée"), HttpStatus.OK);

				} else {
					return new ResponseEntity<>(
							new Result(0, "Echec de l'opération : La création du bon de livraison a échoué"),
							HttpStatus.OK);
				}
			} catch (Exception e) {
				log.error("Echec de l'opération de souscription " + e.getMessage(), e);
				return new ResponseEntity<>(new Result(0, "Echec de l'opération " + e.getMessage()), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(new Result(0, "Echec de l'opération: Manque d'informations obligatoire"),
				HttpStatus.OK);
	}

	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = "/emettreOpi", method = RequestMethod.POST)
	public ResponseEntity<Result> emettreOpi(@RequestBody Opi_B opi) {
		try {
			if (Objects.isNull(opi) || StringUtils.isBlank(opi.getCodeMembre())
					|| StringUtils.isNotBlank(opi.getCodeTegc()) || StringUtils.isBlank(opi.getModePaiement())
					|| StringUtils.isBlank(opi.getTypeTe()) || StringUtils.isBlank(opi.getReferencePaiement())
					|| Double.isNaN(opi.getMontant())) {
				return new ResponseEntity<>(new Result(0, "Echec de l'opération: Manque d'informations obligatoire"),
						HttpStatus.OK);
			}
			EuMembreMorale morale = null;
			EuMembre membre = null;
			if (opi.getCodeMembre().endsWith("P")) {
				membre = membreService.findById(opi.getCodeMembre());
				if (Objects.isNull(membre) || membre.getDesactiver() == 1) {
					return new ResponseEntity<>(new Result(0,
							"Echec de l'opération: Vous n'êtes pas autorisé à faire cette opération. \\n Veuillez contacter la direction de l'ESMC"),
							HttpStatus.OK);
				}
			} else {
				morale = moraleService.findById(opi.getCodeMembre());
				if (Objects.isNull(morale) || morale.getDesactiver() == 1) {
					return new ResponseEntity<>(new Result(0,
							"Echec de l'opération: Vous n'êtes pas autorisé à faire cette opération. \\n Veuillez contacter la direction de l'ESMC"),
							HttpStatus.OK);
				}
			}
			EuBon bon = opiComponent.emetreOpi(null, opi.getCodeMembre(), opi.getCodeTegc(), opi.getModePaiement(),
					opi.getReferencePaiement(), opi.getMontant(), 12, 12, opi.getTypeTe(), 0, 0, null, 1, 0, 0);
			if (Objects.nonNull(bon) && StringUtils.isNotBlank(bon.getBonNumero())) {
				opiComponent.validerEmissionOpi(bon.getBonNumero());
				return new ResponseEntity<>(new Result(1, "Opération effectuée avec succès"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(
						new Result(0, "Echec de l'opération: La création du Bon de Livraison a échoué"), HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("Echec de l'opération d'emission d'OPI: " + e.getMessage(), e);
			return new ResponseEntity<>(new Result(0,
					"Echec de l'opération: L'erreur suivante est survenue au cours de l'opération : " + e.getMessage()),
					HttpStatus.OK);
		}

	}
}

package com.esmc.mcnp.web.controller.smcipn;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esmc.mcnp.domain.dto.bc.CalculBonInfo;
import com.esmc.mcnp.domain.entity.acteur.EuActeur;
import com.esmc.mcnp.domain.entity.acteur.EuContratLivraisonIrrevocable;
import com.esmc.mcnp.domain.entity.acteur.EuLiasonCompte;
import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.domain.entity.obpsd.EuBanque;
import com.esmc.mcnp.domain.entity.op.EuProjet;
import com.esmc.mcnp.domain.entity.others.EuDemandeAchat;
import com.esmc.mcnp.domain.entity.pc.EuCharge;
import com.esmc.mcnp.domain.entity.pc.EuChargePaye;
import com.esmc.mcnp.domain.entity.pc.EuDemandePaiement;
import com.esmc.mcnp.domain.entity.pc.EuDetailPaiement;
import com.esmc.mcnp.domain.entity.pc.EuPaiement;
import com.esmc.mcnp.domain.entity.pc.EuTypeCharge;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.domain.entity.smcipn.EuFormsBudgetNature;
import com.esmc.mcnp.domain.entity.smcipn.EuSmcipnpwi;
import com.esmc.mcnp.domain.entity.smcipn.EuTransfertNr;
import com.esmc.mcnp.infrastructure.components.OPIComponent;
import com.esmc.mcnp.infrastructure.components.Payement;
import com.esmc.mcnp.infrastructure.components.SmcipnComponent;
import com.esmc.mcnp.infrastructure.components.TransfertUtility;
import com.esmc.mcnp.commons.exception.business.CompteNonIntegreException;
import com.esmc.mcnp.commons.exception.business.CompteNonTrouveException;
import com.esmc.mcnp.commons.exception.business.SoldeInsuffisantException;
import com.esmc.mcnp.infrastructure.services.acteurs.EuActeurService;
import com.esmc.mcnp.infrastructure.services.acteurs.EuBanqueService;
import com.esmc.mcnp.infrastructure.services.acteurs.EuContratLivraisonIrrevocableService;
import com.esmc.mcnp.infrastructure.services.acteurs.EuDetailContratLivraisonIrrevocableService;
import com.esmc.mcnp.infrastructure.services.acteurs.EuFormsBudgetNatureService;
import com.esmc.mcnp.infrastructure.services.acteurs.EuLiasonCompteService;
import com.esmc.mcnp.infrastructure.services.bc.EuBonService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreService;
import com.esmc.mcnp.infrastructure.services.obps.EuTegcService;
import com.esmc.mcnp.infrastructure.services.op.EuProjetService;
import com.esmc.mcnp.infrastructure.services.pc.EuChargePayeService;
import com.esmc.mcnp.infrastructure.services.pc.EuChargeService;
import com.esmc.mcnp.infrastructure.services.pc.EuDemandeAchatService;
import com.esmc.mcnp.infrastructure.services.pc.EuDemandePaiementService;
import com.esmc.mcnp.infrastructure.services.pc.EuDetailPaiementService;
import com.esmc.mcnp.infrastructure.services.pc.EuPaiementService;
import com.esmc.mcnp.infrastructure.services.smcipn.EuAppelOffreService;
import com.esmc.mcnp.infrastructure.services.smcipn.EuSmcipnpwiService;
import com.esmc.mcnp.infrastructure.services.smcipn.EuTransfertNrService;
import com.esmc.mcnp.infrastructure.services.smcipn.EuTypeChargeService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.dto.smcipn.BudgetDTO;
import com.esmc.mcnp.web.dto.smcipn.ChargeDTO;
import com.esmc.mcnp.web.dto.smcipn.Creance;
import com.esmc.mcnp.web.dto.Demande;
import com.esmc.mcnp.web.dto.smcipn.Liaison;
import com.esmc.mcnp.web.dto.util.Result;
import com.esmc.mcnp.web.dto.ot.SalaireDTO;
import com.esmc.mcnp.web.dto.smcipn.Smcipn;
import com.esmc.mcnp.web.dto.smcipn.TransfertSMCIPN;
import com.esmc.mcnp.util.PageWrapper;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/smcipn")
public class ScmcipnController extends BaseController {

	private SmcipnComponent smcipnComp;
	private EuActeurService acteurService;
	private EuAppelOffreService offreService;
	private EuTransfertNrService transfertNrService;
	private EuDemandePaiementService demPaieService;
	private TransfertUtility transfertUtility;
	private Payement payement;
	private EuTegcService tegcService;
	private EuContratLivraisonIrrevocableService contratLivraisonIrrevocableService;
	private EuDetailContratLivraisonIrrevocableService detailContratService;
	private EuDetailPaiementService detailPaiementService;
	private EuPaiementService paiementService;
	private EuBonService bonService;
	private EuTypeChargeService typeChargeService;
	private EuChargeService chargeService;
	private EuChargePayeService chargePayeService;
	private EuMembreService membreService;
	private EuMembreMoraleService moraleService;
	private EuSmcipnpwiService smcipnService;
	private OPIComponent opiComponent;
	private EuBanqueService banqueService;
	private EuFormsBudgetNatureService budgetNatureService;
	private EuDemandeAchatService demandeAchatService;
	private EuProjetService projetService;
	private EuLiasonCompteService liasonService;

	@Autowired
	public ScmcipnController(SmcipnComponent smcipnComp, EuActeurService acteurService,
			EuAppelOffreService offreService, EuTransfertNrService transfertNrService, OPIComponent opiComponent,
			EuDemandePaiementService demPaieService, TransfertUtility transfertUtility, Payement payement,
			EuTegcService tegcService, EuContratLivraisonIrrevocableService contratLivraisonIrrevocableService,
			EuDetailContratLivraisonIrrevocableService detailContratService, EuBonService bonService,
			EuDetailPaiementService detailPaiementService, EuPaiementService paiementService,
			EuBanqueService banqueService, EuTypeChargeService typeChargeService, EuChargeService chargeService,
			EuChargePayeService chargePayeService, EuMembreService membreService, EuMembreMoraleService moraleService,
			EuSmcipnpwiService smcipnService, EuFormsBudgetNatureService budgetNatureService,
			EuDemandeAchatService demandeAchatService, EuProjetService projetService,
			EuLiasonCompteService liasonService) {
		this.smcipnComp = smcipnComp;
		this.acteurService = acteurService;
		this.offreService = offreService;
		this.transfertNrService = transfertNrService;
		this.opiComponent = opiComponent;
		this.demPaieService = demPaieService;
		this.transfertUtility = transfertUtility;
		this.payement = payement;
		this.tegcService = tegcService;
		this.contratLivraisonIrrevocableService = contratLivraisonIrrevocableService;
		this.detailContratService = detailContratService;
		this.detailPaiementService = detailPaiementService;
		this.paiementService = paiementService;
		this.bonService = bonService;
		this.typeChargeService = typeChargeService;
		this.chargeService = chargeService;
		this.chargePayeService = chargePayeService;
		this.membreService = membreService;
		this.moraleService = moraleService;
		this.smcipnService = smcipnService;
		this.banqueService = banqueService;
		this.budgetNatureService = budgetNatureService;
		this.demandeAchatService = demandeAchatService;
		this.projetService = projetService;
		this.liasonService = liasonService;
	}

	@ModelAttribute("role")
	public String getRole() {
		return getCurrentUser().getRole();
	}

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String afficheSmcipn() {
		return "smcipn/index";
	}

	@RequestMapping(value = "/budget", method = RequestMethod.GET)
	public String gotoBudget(@ModelAttribute("budget") BudgetDTO budget) {
		return "smcipn/budget";
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@PostMapping(value = "/budget")
	public String gotoBudget(@ModelAttribute("budget") BudgetDTO budget, Model model, RedirectAttributes rmodel) {
		if (budget.getNumeroBudget() != null && budget.getTypeBudgetId() != null
				&& StringUtils.isNotBlank(budget.getTypeBudget())) {
			EuFormsBudgetNature budgetNature = budgetNatureService.findById(Integer.valueOf(budget.getNumeroBudget()));
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
					numeroAppel = smcipnComp.doSmcipnCharge("0010010010010000212M", tegc.getCodeTegc(),
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

						smcipnComp.echangeNRNN("0010010010010000212M", codeMembre, numeroAppel, "PS", montant, null);
					}
				}
			}
		}
		return "";
	}

	@RequestMapping(value = "/technopole", method = RequestMethod.GET)
	public String gotokitTechnopole(@ModelAttribute Smcipn smcipn) {
		return "smcipn/smci";
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@RequestMapping(value = "/technopole", method = RequestMethod.POST)
	public String dokitTechnopole(@ModelAttribute Smcipn smcipn, Model model, RedirectAttributes rmodel) {
		if (StringUtils.isNotBlank(smcipn.getCodeMembre())) {
			EuActeur act_smc = acteurService.findByMembreAndTypes(getCurrentUser().getCodeMembre(),
					Arrays.asList("GAC_DETENTRICE", "TECHNOPOLE"));
			EuActeur acteur_benef = acteurService.findByCodeMembre(smcipn.getCodeMembre());
			if (Objects.nonNull(act_smc)
					&& (Objects.nonNull(acteur_benef) || smcipn.getCodeMembre().equals("0010010010010000212M"))) {
				try {
					if (StringUtils.isBlank(smcipn.getNumAppelOffre())) {
						Long compteur = offreService.getLastinsertedId();
						if (compteur == null) {
							compteur = 1L;
						} else {
							compteur++;
						}
						smcipn.setNumAppelOffre("AO" + StringUtils.leftPad(String.valueOf(compteur), 4, '0'));
					}
					if (smcipn.getType().equals("I")) {
						if (smcipnComp.doApaPreKit(getCurrentUser().getCodeMembre(), smcipn.getCodeMembre(),
								smcipn.getCodeTe(), smcipn.getNumAppelOffre(), smcipn.getNomAppelOffre(),
								smcipn.getDescription(), smcipn.getDuree(), getCurrentUser().getIdUtilisateur(),
								smcipn.getMontant())) {
							EuCharge tcharge = chargeService.findByCode("CE");
							EuSmcipnpwi smcipn_r = smcipnService.findByNumeroAppel(smcipn.getNumAppelOffre());
							EuChargePaye chargePaye = new EuChargePaye();
							chargePaye.setEuSmcipnpwi(smcipn_r);
							chargePaye.setEuCharge(tcharge);
							chargePaye.setCodeMembreDebiteur(getCurrentUser().getCodeMembre());
							chargePaye.setTypeDoc("BDG");
							chargePaye.setNumDoc(smcipn.getNumDoc());
							chargePaye.setCodeMembreCreancier(smcipn.getCodeMembre());
							chargePaye.setDateCharge(new Date());
							chargePaye.setLibelleCharge(smcipn.getNomAppelOffre());
							chargePaye.setMontantCharge(smcipn.getMontant());
							chargePaye.setOrigineCharge("SOURCE");
							chargePaye.setNumDoc(smcipn.getNumAppelOffre());
							chargePayeService.add(chargePaye);
							rmodel.addFlashAttribute("message",
									"La SMCIPN N° " + smcipn.getNumAppelOffre() + " a été effectuée avec succès.");
							return "redirect:/technopole";
						} else {
							model.addAttribute("message", "Echec de l'opération ");
							return "smcipn/smci";
						}
					} else {
						if (smcipnComp.doApaCncsnrPre(getCurrentUser().getCodeMembre(), smcipn.getCodeMembre(),
								smcipn.getNumAppelOffre(), smcipn.getNomAppelOffre(), smcipn.getDescription(),
								smcipn.getDuree(), getCurrentUser().getIdUtilisateur(), smcipn.getMontant())) {
							EuCharge tcharge = chargeService.findByCode("CE");
							EuSmcipnpwi smcipn_r = smcipnService.findByNumeroAppel(smcipn.getNumAppelOffre());
							EuChargePaye chargePaye = new EuChargePaye();
							chargePaye.setEuSmcipnpwi(smcipn_r);
							chargePaye.setEuCharge(tcharge);
							chargePaye.setCodeMembreDebiteur(getCurrentUser().getCodeMembre());
							chargePaye.setTypeDoc("BDG");
							chargePaye.setNumDoc(smcipn.getNumDoc());
							chargePaye.setCodeMembreCreancier(smcipn.getCodeMembre());
							chargePaye.setDateCharge(new Date());
							chargePaye.setLibelleCharge(smcipn.getNomAppelOffre());
							chargePaye.setMontantCharge(smcipn.getMontant());
							chargePaye.setOrigineCharge("SOURCE");
							chargePaye.setNumDoc(smcipn.getNumAppelOffre());
							chargePayeService.add(chargePaye);
							rmodel.addFlashAttribute("message",
									"La SMCIPN N° " + smcipn.getNumAppelOffre() + " a été effectuée avec succès.");
							return "redirect:/technopole";
						} else {
							model.addAttribute("message", "Echec de l'opération ");
							return "smcipn/smci";
						}
					}
				} catch (DataAccessException e) {
					model.addAttribute("message", "Erreur : " + e.getMessage());
					return "smcipn/smci";
				}
			} else {
				if (act_smc == null) {
					model.addAttribute("message", "Veuillez vérifier le code membre du technopole!");
					return "smcipn/smci";
				}
				if (acteur_benef == null) {
					model.addAttribute("message", "Veuillez vérifier le code membre du  bénéficiaire! ");
					return "smcipn/smci";
				}
			}
		}
		model.addAttribute("message", "Veuillez renseigner tous les champs nécéssaires!:");
		return "smcipn/smci";
	}

	@GetMapping(value = "/liaison")
	public String lierCompte(@ModelAttribute Liaison liaison) {
		liaison.setCodeCompteadmin(getCurrentUser().getCodeMembre());
		return "smcipn/liaison";
	}

	@PostMapping(value = "/liaison")
	public String doLiaison(@ModelAttribute Liaison liaison, Model model, RedirectAttributes rmodel) {
		if (StringUtils.isNotBlank(liaison.getCodeCompteAnim())) {
			try {
				EuLiasonCompte lc = new EuLiasonCompte(getCurrentUser().getCodeMembre(), liaison.getCodeCompteAnim());
				lc.setDateLiaison(LocalDateTime.now());
				liasonService.add(lc);
				rmodel.addFlashAttribute("message", "Liaison du Compte Admin N° " + getCurrentUser().getCodeMembre()
						+ " et du Compte Animateur N° " + liaison.getCodeCompteAnim() + " a été bien effectuée.");
				return "redirect:/liaison";
			} catch (Exception e) {
				getLog().error("Erreur de liaison des comptes : ", e);
				model.addAttribute("message", "Erreur de liaison des comptes : " + e.getMessage());
				return "smcipn/liaison";
			}
		}
		model.addAttribute("message", "Veuillez bien saisir le numéro de compte animateur");
		return "smcipn/liaison";
	}

	// retrouver liste moyens de paiement
	@RequestMapping(value = "/listePayement", method = RequestMethod.GET)
	public ResponseEntity<Iterable<EuBanque>> findListModePayement() {
		return new ResponseEntity<>(banqueService.list(), HttpStatus.OK);
	}

	@ModelAttribute("banks")
	public List<EuBanque> getBanks() {
		return banqueService.list();
	}

	@ModelAttribute("typeChagres")
	public List<EuTypeCharge> loadTypecharge() {
		return typeChargeService.list();
	}

	@ResponseBody
	@RequestMapping(value = "/loadCharge", method = RequestMethod.GET)
	public List<EuCharge> loadChargeByCodeType(@RequestParam("type") String codeType) {
		List<EuCharge> charges = Lists.newArrayList();
		if (StringUtils.isNotBlank(codeType)) {
			charges = chargeService.findByCodeTypecharge(codeType);
		}
		return charges;
	}

	@ResponseBody
	@RequestMapping(value = "/loadChargeId", method = RequestMethod.GET)
	public List<EuCharge> loadChargeByIdType(@RequestParam("type") Integer idType) {
		List<EuCharge> charges = Lists.newArrayList();
		if (idType != null) {
			charges = chargeService.findByIdTypeCharge(idType);
		}
		return charges;
	}

	@RequestMapping(value = "/salaire")
	public String payerSalaire(@ModelAttribute("salaireDto") SalaireDTO salaireDTO, Model model) {
		return "smcipn/salaire";
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@RequestMapping(value = "/payerSalaire", method = RequestMethod.POST)
	public ResponseEntity<Result> payerSalaire(@RequestBody SalaireDTO salaire, BindingResult errors) {
		if (errors.hasErrors()) {
			return new ResponseEntity<>(new Result(0, "Erreur de données"), HttpStatus.OK);
		}
		try {
			if (Objects.nonNull(salaire) && salaire.getCreances().length > 0) {
				if (StringUtils.isNotBlank(salaire.getNumeroAppel())) {
					List<Creance> creances = Arrays.asList(salaire.getCreances());
					double montantCharge = creances.stream().mapToDouble(Creance::getMontantOp).sum();
					if (smcipnComp.echangeNRNB(getCurrentUser().getCodeMembre(), salaire.getNumeroAppel(), "TPN", "PS",
							montantCharge, 8.0)) {
						CalculBonInfo bonInfo = new CalculBonInfo();
						bonInfo.setCatBon("nr");
						bonInfo.setTypeProduit("PS");
						creances.forEach(c -> {
							EuCharge tcharge = chargeService.findByCode(salaire.getCodeCharge());
							EuSmcipnpwi smcipn = smcipnService.findByNumeroAppel(salaire.getNumeroAppel());
							if (Objects.nonNull(tcharge) && Objects.nonNull(smcipn)) {

								EuChargePaye chargePaye = new EuChargePaye();
								chargePaye.setEuSmcipnpwi(smcipn);
								chargePaye.setEuCharge(tcharge);
								chargePaye.setNumDoc(c.getNumDoc());
								chargePaye.setCodeMembreDebiteur(getCurrentUser().getCodeMembre());
								chargePaye.setCodeMembreCreancier(c.getCodeMembreCreancier());
								chargePaye.setDateCharge(new Date());
								chargePaye.setLibelleCharge(salaire.getLibelleCharge());
								chargePaye.setMontantCharge(c.getMontantOp());
								chargePaye.setOrigineCharge("ESMC");
								chargePayeService.add(chargePaye);

								EuBon bon = transfertUtility.tansfertBC(getCurrentUser().getCodeMembre(), "TI", bonInfo,
										8.0, c.getMontantOp());
								if (Objects.nonNull(bon)) {
									EuTegc tegc = tegcService.getById(c.getCodeTegc());
									EuBon bl = payement.createBonLivraison(c.getCodeMembreCreancier(),
											getCurrentUser().getCodeMembre(), "CNCS", null, 0, c.getMontantOp());
									payement.makeTransaction(c.getCodeMembreCreancier(),
											getCurrentUser().getCodeMembre(), "TI", tegc, bon, bl, "PS",
											c.getMontantOp());
									payement.creerMarge(new Date(), c.getMontantOp());

									EuBon bopi = opiComponent.emetreOpi(null, c.getCodeMembreCreancier(),
											c.getCodeTegc(), c.getModePaiement(), c.getReferencePaiement(),
											c.getMontantOp(), c.getNbreOpi(), 1, "P", 0, 0, null, c.getMarge(), 0, 0);
									opiComponent.validerEmissionOpi(bopi.getBonNumero());
								}
							}
						});
						return new ResponseEntity<>(new Result(1, "Opération effectuée avec succès"), HttpStatus.OK);
					} else {
						getLog().error("Echec de l'echange");
						return new ResponseEntity<>(new Result(0, "Echec de l'Opération : Echec de l'echange"),
								HttpStatus.OK);
					}
				} else {
					getLog().error("Echec du SMCIPN");
					return new ResponseEntity<>(new Result(0, "Echec de l'Opération : "), HttpStatus.OK);
				}
			} else {
				getLog().error("Pas de charge à payer");
				return new ResponseEntity<>(new Result(0, "Echec de l'Opération : Pas de charge à payer"),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			getLog().error("Echec de l'opération", e);
			return new ResponseEntity<>(new Result(0, "Echec de l'Opération : " + e.getMessage()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/payerCharge")
	public String payerCharge(@ModelAttribute("chargeDto") ChargeDTO chargeDTO, Model model) {
		model.addAttribute("charges", chargeService.list());
		model.addAttribute("typeCharges", typeChargeService.list());
		return "smcipn/charge";
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@RequestMapping(value = "/payerCharge", method = RequestMethod.POST)
	public ResponseEntity<Result> payerCharge(@RequestBody ChargeDTO charge, BindingResult errors) {
		if (errors.hasErrors()) {
			return new ResponseEntity<>(new Result(0, "Erreur de données"), HttpStatus.OK);
		}
		try {
			if (Objects.nonNull(charge) && charge.getCreances().length > 0) {
				if (StringUtils.isNotBlank(charge.getNumeroAppel())) {
					List<Creance> creances = Arrays.asList(charge.getCreances());
					double montantCharge = creances.stream().mapToDouble(Creance::getMontantOp).sum();
					if (smcipnComp.echangeNRNB(charge.getCodeMembreDebiteur(), charge.getNumeroAppel(), "PS",
							montantCharge, 8.0)) {
						CalculBonInfo bonInfo = new CalculBonInfo();
						bonInfo.setCatBon("nr");
						bonInfo.setTypeProduit("PS");
						creances.forEach(c -> {
							EuCharge tcharge = chargeService.findByCode(charge.getCodeCharge());
							EuSmcipnpwi smcipn = smcipnService.findByNumeroAppel(charge.getNumeroAppel());
							if (Objects.nonNull(tcharge) && Objects.nonNull(smcipn)) {

								EuChargePaye chargePaye = new EuChargePaye();
								chargePaye.setEuSmcipnpwi(smcipn);
								chargePaye.setEuCharge(tcharge);
								chargePaye.setNumDoc(c.getNumDoc());
								chargePaye.setCodeMembreDebiteur(charge.getCodeMembreDebiteur());
								chargePaye.setCodeMembreCreancier(c.getCodeMembreCreancier());
								chargePaye.setDateCharge(new Date());
								chargePaye.setLibelleCharge(charge.getLibelleCharge());
								chargePaye.setMontantCharge(c.getMontantOp());
								chargePaye.setOrigineCharge(charge.getOrigineCharge());
								chargePayeService.add(chargePaye);

								EuBon bon = transfertUtility.tansfertBC(charge.getCodeMembreDebiteur(), "TI", bonInfo,
										8.0, c.getMontantOp());
								if (Objects.nonNull(bon)) {
									EuTegc tegc = tegcService.getById(c.getCodeTegc());
									EuBon bl = payement.createBonLivraison(c.getCodeMembreCreancier(),
											charge.getCodeMembreDebiteur(), charge.getTypeCharge(), null, 0,
											c.getMontantOp());
									payement.makeTransaction(c.getCodeMembreCreancier(), charge.getCodeMembreDebiteur(),
											"TI", tegc, bon, bl, "PS", c.getMontantOp());
									payement.creerMarge(new Date(), c.getMontantOp());

									EuBon bopi = opiComponent.emetreOpi(null, c.getCodeMembreCreancier(),
											c.getCodeTegc(), c.getModePaiement(), c.getReferencePaiement(),
											c.getMontantOp(), c.getNbreOpi(), 1, "P", 0, 0, null, c.getMarge(), 0, 0);
									opiComponent.validerEmissionOpi(bopi.getBonNumero());
								}
							}
						});
						return new ResponseEntity<>(new Result(1, "Opération effectuée avec succès"), HttpStatus.OK);
					} else {
						getLog().error("Echec de l'echange");
						return new ResponseEntity<>(new Result(0, "Echec de l'Opération : Echec de l'echange"),
								HttpStatus.OK);
					}
				} else {
					getLog().error("Echec du SMCIPN");
					return new ResponseEntity<>(new Result(0, "Echec de l'Opération : "), HttpStatus.OK);
				}
			} else {
				getLog().error("Pas de charge à payer");
				return new ResponseEntity<>(new Result(0, "Echec de l'Opération : Pas de charge à payer"),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			getLog().error("Echec de l'opération", e);
			return new ResponseEntity<>(new Result(0, "Echec de l'Opération : " + e.getMessage()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/nomMembre", method = RequestMethod.GET)
	public @ResponseBody String loadNomMembre(@RequestParam("codeMembre") String codeMembre) {
		if (StringUtils.isNotBlank(codeMembre) && StringUtils.length(codeMembre) == 20) {
			if (codeMembre.endsWith("P")) {
				EuMembre membre = membreService.findById(codeMembre);
				if (Objects.nonNull(membre)) {
					return membre.getNomMembre() + " " + membre.getPrenomMembre();
				}
			} else {
				EuMembreMorale morale = moraleService.findById(codeMembre);
				if (Objects.nonNull(morale)) {
					return morale.getRaisonSociale();
				}
			}
		}
		return "";
	}

	@RequestMapping(value = "/payer", method = RequestMethod.GET)
	public String payerCharge(@ModelAttribute("smcipn") Smcipn smcipn) {
		smcipn.setTauxTva(0f);
		return "smcipn/smcipn";
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@RequestMapping(value = "/payer", method = RequestMethod.POST)
	public String doPayercharge(@ModelAttribute("smcipn") Smcipn smcipn, Model model, RedirectAttributes rmodel,
			BindingResult errors) {
		if ((smcipn.getTtc() != null) && (1 == smcipn.getTtc())
				&& (0f == smcipn.getTauxTva() || Objects.isNull(smcipn.getTauxTva()))) {
			model.addAttribute("message", "Si la valeur est TTC, vous devez renseigner le taux de la TVA!");
			return "smcipn/smcipn";
		}
		String codeMembreSmc = smcipn.getCodeMembreSmcipn();
		if (StringUtils.isNotBlank(smcipn.getCodeMembre()) && StringUtils.isNotBlank(smcipn.getCodeTe())
				&& StringUtils.isNotBlank(smcipn.getTypePaiement()) && smcipn.getMontant() > 0) {
			EuTegc tegc = tegcService.getById(smcipn.getCodeTe());
			if (Objects.nonNull(tegc)) {
				try {
					EuBon bl = payement.createBonLivraison(smcipn.getCodeMembre(), codeMembreSmc,
							smcipn.getTypePaiement(), null, 0, smcipn.getMontant());
					if (Objects.nonNull(bl)) {

						bl.setBonExprimer(1);
						bl.setBonDateExpression(new Date());
						bonService.update(bl);

						String numAppelOffre = smcipnComp.doSmcipnInterm(codeMembreSmc, tegc.getCodeTegc(),
								smcipn.getCodeMembre(), bl.getBonNumero(), getCurrentUser().getIdUtilisateur(),
								smcipn.getMontant());
						if (StringUtils.isNotBlank(numAppelOffre)) {
							if (smcipnComp.echangeNRNB(codeMembreSmc, numAppelOffre, "PO", smcipn.getMontant(), 8.0)) {
								CalculBonInfo bonInfo = new CalculBonInfo();
								bonInfo.setCatBon("nr");
								bonInfo.setTypeProduit("PO");

								EuCharge tcharge = chargeService.findByCode(smcipn.getTypePaiement());
								EuSmcipnpwi tsmcipn = smcipnService.findByNumeroAppel(numAppelOffre);

								EuChargePaye chargePaye = new EuChargePaye();
								chargePaye.setEuSmcipnpwi(tsmcipn);
								chargePaye.setEuCharge(tcharge);
								chargePaye.setNumDoc(smcipn.getNumDoc());
								chargePaye.setCodeMembreDebiteur(smcipn.getCodeMembreSmcipn());
								chargePaye.setCodeMembreCreancier(smcipn.getCodeMembre());
								chargePaye.setDateCharge(new Date());
								chargePaye.setLibelleCharge(smcipn.getNomAppelOffre());
								chargePaye.setMontantCharge(smcipn.getMontant());
								chargePaye.setOrigineCharge("ESMC");
								chargePayeService.add(chargePaye);

								EuBon bon = transfertUtility.tansfertBC(codeMembreSmc, "TI", bonInfo, 8.0,
										smcipn.getMontant());
								if (Objects.nonNull(bon)) {
									payement.makeTransaction(smcipn.getCodeMembre(), codeMembreSmc, "TI", tegc, bon, bl,
											smcipn.getTypePaiement(), smcipn.getMontant());
									payement.creerMarge(new Date(), smcipn.getMontant());
									String typeOpi = "";
									if (smcipn.getCodeMembre().endsWith("M")) {
										typeOpi = "M";
									} else {
										typeOpi = "P";
									}

									EuBon bopi = opiComponent.emetreOpi(null, smcipn.getCodeMembre(),
											smcipn.getCodeTe(), smcipn.getModePaiement(), smcipn.getNumeroCompte(),
											smcipn.getMontant(), smcipn.getNbreOpi(), 1, typeOpi, 0, 0, null,
											smcipn.getMarge(), smcipn.getTtc(), smcipn.getTauxTva());
									opiComponent.validerEmissionOpi(bopi.getBonNumero());

								}
							}
						}
					}
					rmodel.addFlashAttribute("message", "L'opération a été effectuée avec succès");
					return "redirect:/payer";
				} catch (IllegalArgumentException | CompteNonTrouveException | NullPointerException e) {
					model.addAttribute("message", "Erreur d'exécution : " + e.getMessage());
					return "smcipn/smcipn";
				}
			} else {
				model.addAttribute("message", "Ce membre n'a pas un Terminal d'Echange!");
				return "smcipn/smcipn";
			}
		} else {
			model.addAttribute("message", "Veuillez fournir les informations marquées comme obligatoires");
			return "smcipn/smcipn";
		}
	}

	@RequestMapping(value = "/payerSmcipn", method = RequestMethod.GET)
	public String payerSmcipn(@ModelAttribute("smcipn") Smcipn smcipn) {
		return "smcipn/smci_com";
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@RequestMapping(value = "/payerSmcipn", method = RequestMethod.POST)
	public String doPayerSmcipn(@ModelAttribute("smcipn") Smcipn smcipn, Model model, RedirectAttributes rmodel,
			BindingResult errors) {
		String codeMembreSmc = smcipn.getCodeMembreSmcipn();
		if (StringUtils.isNotBlank(smcipn.getCodeMembre()) && StringUtils.isNotBlank(smcipn.getCodeTe())
				&& StringUtils.isNotBlank(smcipn.getTypePaiement()) && StringUtils.isNotBlank(smcipn.getNumAppelOffre())
				&& smcipn.getMontant() > 0) {
			EuTegc tegc = tegcService.getById(smcipn.getCodeTe());
			if (Objects.nonNull(tegc)) {
				EuDemandePaiement demande = demPaieService.findByNumeroDemandePaiement(smcipn.getNumAppelOffre());
				if (Objects.nonNull(demande) && (demande.getPayer() == null || demande.getPayer() == 0)) {
					List<EuPaiement> paiements = Lists.newArrayList();
					EuContratLivraisonIrrevocable contrat = null;
					List<EuDetailPaiement> detPaiements = detailPaiementService
							.findByNumeroDemande(demande.getNumeroDemandePaiement());
					if (!detPaiements.isEmpty() && detPaiements.size() >= 1) {
						EuDetailPaiement detPaiement = detPaiements.get(0);
						if (smcipn.getTypePaiement().equals("AVPC")) {
							contrat = contratLivraisonIrrevocableService.findById(detPaiement.getIdTable());
							Double somme = detailContratService.getSumMontant(detPaiement.getIdTable());
							if (!somme.equals(demande.getMontantDemandePaiement())) {
								model.addAttribute("message",
										"Demande non valide: Le montant demandé ne correspondant pas au montant du contrat");
								return "smcipn/smci_com";
							}
						} else {
							paiements = paiementService.findByDemandePaiement(demande.getIdDemandePaiement());
							if (!paiements.isEmpty()) {
								Double montantaPayer = paiementService
										.getSommeByDemande(demande.getIdDemandePaiement());
								if (!montantaPayer.equals(demande.getMontantDemandePaiement())) {
									model.addAttribute("message",
											"Demande non valide: Le montant demandé ne correspondant pas au montant du paiement");
									return "smcipn/smci_com";
								}
							}
						}
						if (Objects.nonNull(contrat) || !paiements.isEmpty()) {
							try {
								EuBon bl = null;
								if (Objects.nonNull(contrat)) {
									bl = payement.createBonLivraison(contrat.getCodeMembre(), codeMembreSmc,
											smcipn.getTypePaiement(), "", contrat.getIdContrat(),
											demande.getMontantDemandePaiement());
								} else {
									bl = payement.createBonLivraison(demande.getCodeMembreEmployeur(), codeMembreSmc,
											smcipn.getTypePaiement(), "", paiements.get(0).getIdPaiement(),
											demande.getMontantDemandePaiement());
								}
								if (Objects.nonNull(bl)) {
									String numAppelOffre = smcipnComp.doSmcipnComm(codeMembreSmc,
											demande.getCodeMembreEmployeur(), tegc.getCodeTegc(),
											smcipn.getTypePaiement(), demande.getMontantDemandePaiement());
									if (StringUtils.isNotBlank(numAppelOffre)) {
										if (smcipnComp.echangeNRNB(codeMembreSmc, numAppelOffre,
												smcipn.getTypeProduit(), demande.getMontantDemandePaiement(), 8.0)) {
											CalculBonInfo bonInfo = new CalculBonInfo();
											bonInfo.setCatBon("nr");
											bonInfo.setTypeProduit(smcipn.getTypeProduit());
											EuBon bon = transfertUtility.tansfertBC(codeMembreSmc, "TI", bonInfo, 8.0,
													demande.getMontantDemandePaiement());
											if (Objects.nonNull(bon)) {
												String typeOp = "";
												if (Objects.nonNull(contrat)) {
													typeOp = "AVPC";
												} else {
													typeOp = "PS";
												}
												payement.makeTransaction(demande.getCodeMembreEmployeur(),
														codeMembreSmc, "TI", tegc, bon, bl, typeOp,
														demande.getMontantDemandePaiement());
												payement.creerMarge(new Date(), demande.getMontantDemandePaiement());

												demande.setPayer(1);
												demPaieService.update(demande);
											}
										}
									}
								}
								rmodel.addFlashAttribute("message", "L'opération a été effectuée avec succès");
								return "redirect:/payerSmcipn";
							} catch (IllegalArgumentException | CompteNonTrouveException | NullPointerException e) {
								model.addAttribute("message", "Erreur d'exécution : " + e.getMessage());
								return "smcipn/smci_com";
							}
						} else {
							model.addAttribute("message",
									"Il n'existe aucun contrat correspondant à cette demande de paiement.");
							return "smcipn/smci_com";
						}
					} else {
						model.addAttribute("message", "Il n'y a pas d'enregistrement de paiement lié à cette demande.");
						return "smcipn/smci_com";
					}

				} else {
					if (Objects.isNull(demande)) {
						model.addAttribute("message",
								"Le numéro que vouz avez fourni ne correspond à aucune demande de paiement.");
					} else {
						model.addAttribute("message", "Cette demande a été dèjà payé!");
					}
					return "smcipn/smci_com";
				}
			} else {
				model.addAttribute("message", "Ce membre n'a pas un Terminal d'Echange!");
				return "smcipn/smci_com";
			}
		} else {
			model.addAttribute("message", "Veuillez fournir les informations marquées comme obligatoires");
			return "smcipn/smci_com";
		}
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@RequestMapping(value = "/payercom", method = RequestMethod.GET)
	public String payerSmcipn(@RequestParam("membre") String codeMembre, @RequestParam("tegc") String codeTegc,
			@RequestParam("paiement") String typePaiement, @RequestParam("comm") String typeComm,
			@RequestParam("activation") String typeActivation, @RequestParam("id") Long id,
			@RequestParam("montant") Double montant) {
		String numAppelOffre;
		EuTegc tegc = null;
		String codeMembreSmc = "0010010010010000003M";
		if (StringUtils.isNotBlank(codeMembre) && StringUtils.isNotBlank(codeTegc)
				&& StringUtils.isNotBlank(typePaiement) && StringUtils.isNotBlank(typeComm) && id != null
				&& montant != null) {
			if (typeComm.equals("activation") && StringUtils.isBlank(typeActivation)) {
				return "KO";
			}
			tegc = tegcService.getById(codeTegc);
			if (Objects.isNull(tegc)) {
				return "KO";
			}
			try {
				EuBon bl = payement.createBonLivraison(codeMembre, codeMembreSmc, typePaiement, typeComm, id.intValue(),
						montant);
				if (Objects.nonNull(bl)) {
					numAppelOffre = smcipnComp.doSmcipnComm(codeMembreSmc, codeMembre, codeTegc, typePaiement, montant);
					if (StringUtils.isNotBlank(numAppelOffre)) {
						if (smcipnComp.echangeNRNB(codeMembreSmc, numAppelOffre, "Inr", montant, 8.0)) {
							CalculBonInfo bonInfo = new CalculBonInfo();
							bonInfo.setCatBon("nr");
							bonInfo.setTypeProduit("PO");
							EuBon bon = transfertUtility.tansfertBC(codeMembreSmc, "TI", bonInfo, 8.0, montant);
							if (Objects.nonNull(bon)) {
								payement.makeTransaction(codeMembre, codeMembreSmc, "TI", tegc, bon, null, "COM",
										montant);
								payement.creerMarge(new Date(), montant);
								return "OK";
							}
						}
					}
				}
			} catch (CompteNonIntegreException | CompteNonTrouveException | SoldeInsuffisantException
					| DataAccessException e) {
				return "KO";
			}
		}
		return "KO";
	}

	@GetMapping(value = "/demande", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public @ResponseBody Demande loadDemande(@RequestParam("numero") String numero,
			@RequestParam("membre") String codeMembre, @RequestParam("type") String typePaiement) {
		EuDemandePaiement demPaie = null;
		if (StringUtils.isNotBlank(numero)) {
			demPaie = demPaieService.findByNumeroDemandePaiement(numero);
		} else {
			if (StringUtils.isNotBlank(codeMembre) && StringUtils.isNotBlank(typePaiement)) {
				String typeCommission = "Activation";
				if (typePaiement.equalsIgnoreCase("BAN")) {
					typeCommission = "BAn";
				}
				List<EuDemandePaiement> demPaies = demPaieService.findByCodeMembrerAndType(codeMembre, typeCommission);
				if (!demPaies.isEmpty()) {
					if (demPaies.size() == 1) {
						demPaie = demPaies.get(0);
					}
				} else {
					return null;
				}
			}
		}
		Demande demande = new Demande();
		demande.setIdDemande(demPaie.getIdDemandePaiement());
		demande.setLibelle(demPaie.getLibelleTypeDemande());
		demande.setTypeDemande(demPaie.getTypeDemande());
		demande.setMontant(demPaie.getMontantDemandePaiement());
		return demande;
	}

	@RequestMapping(value = "/transfertSmcipn")
	public String transfertSmcipn(@ModelAttribute("tr") TransfertSMCIPN tr) {
		return "smcipn/transfert_smcipn";
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@RequestMapping(value = "/transfertSmcipn", method = RequestMethod.POST)
	public String doTransfertSmcipn(@ModelAttribute("tr") TransfertSMCIPN tr, RedirectAttributes model) {
		EuUtilisateur user = getCurrentUser();
		if (StringUtils.isNotBlank(user.getCodeMembre())) {
			EuActeur acteur = acteurService.findByMembreAndTypes(user.getCodeMembre(),
					Arrays.asList("TECHNOPOLE", "FILIERE", "ACNEV"));
			if (Objects.nonNull(acteur) && (acteur.getTypeActeur().equalsIgnoreCase("TECHNOPOLE")
					|| acteur.getTypeActeur().equalsIgnoreCase("FILIERE")
					|| acteur.getTypeActeur().equalsIgnoreCase("ACNEV"))) {
				System.out.println("Enter Transfert Controller");
				try {
					if (smcipnComp.transfertResNr(user.getCodeMembre(), tr.getCodeMembreBenef(),
							tr.getLibelleTransfert(), tr.getNumAppelOffre(), tr.getTypeNr(), tr.getMontTransfert(),
							user.getIdUtilisateur())) {
						model.addFlashAttribute("message", "Le transfert est effectué avec succès!");
						return "redirect:/transfertSmcipn";
					} else {
						model.addAttribute("message", "Veuillez fournir le numéro du SMCIPN à transférer");
						return "smcipn/transfert_smcipn";
					}
				} catch (CompteNonTrouveException | SoldeInsuffisantException | NullPointerException e) {
					model.addAttribute("message", e.getMessage());
					return "smcipn/transfert_smcipn";
				} catch (IllegalArgumentException e) {
					model.addAttribute("message", e.getMessage());
					return "smcipn/transfert_smcipn";
				}
			}
		}
		model.addAttribute("message", "Vous n'êtes pas autorisé avec effectué cette opération");
		return "smcipn/transfert_smcipn";
	}

	@RequestMapping(value = "/transfertBai")
	public String transfertNn(@ModelAttribute("tr") TransfertSMCIPN tr) {
		EuLiasonCompte liaison = liasonService.findByCompteAdmin(getCurrentUser().getCodeMembre());
		tr.setCodeMembreBenef(liaison.getCodeCompteAnim());
		return "smcipn/transfert_bai";
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@RequestMapping(value = "/transfertBai", method = RequestMethod.POST)
	public String doTransfertNn(@ModelAttribute("tr") TransfertSMCIPN tr, RedirectAttributes model) {
		System.out.println("Enter Transfert NN");
		EuUtilisateur user = getCurrentUser();
		if (StringUtils.isNotBlank(user.getCodeMembre())) {
			EuActeur acteur = acteurService.findByMembreAndTypes(user.getCodeMembre(),
					Arrays.asList("TECHNOPOLE", "FILIERE", "ACNEV"));
			if (Objects.nonNull(acteur)) {
				EuLiasonCompte liaison = liasonService.findByCompteAdmin(user.getCodeMembre());
				if (Objects.nonNull(liaison)) {
					try {
						if (Objects.nonNull(smcipnComp.echangeNRNN(user.getCodeMembre(), user.getCodeMembre(),
								tr.getNumAppelOffre(), "PS", tr.getMontTransfert(), user.getIdUtilisateur()))) {
							if (smcipnComp.transfertResNn(user.getCodeMembre(), liaison.getCodeCompteAnim(),
									tr.getLibelleTransfert(), tr.getNumAppelOffre(), tr.getMontTransfert(),
									user.getIdUtilisateur())) {
								model.addFlashAttribute("message", "Le transfert est effectué avec succès!");
								return "redirect:/transfertBai";
							} else {
								tr.setCodeMembreBenef(liaison.getCodeCompteAnim());
								model.addAttribute("message", "Veuillez fournir le numéro du SMCIPN à transférer");
								return "smcipn/transfert_bai";
							}
						} else {
							tr.setCodeMembreBenef(liaison.getCodeCompteAnim());
							getLog().error("L'Echange du SMCIPN ne s'est bien achevé");
							model.addAttribute("message", "L'Echange du SMCIPN ne s'est bien achevé");
							return "smcipn/transfert_bai";
						}
					} catch (CompteNonTrouveException | SoldeInsuffisantException | NullPointerException e) {
						getLog().error("Echec de l'affectation", e);
						model.addAttribute("message", e.getMessage());
						tr.setCodeMembreBenef(liaison.getCodeCompteAnim());
						return "smcipn/transfert_bai";
					} catch (IllegalArgumentException e) {
						getLog().error("Echec de l'affectation", e);
						tr.setCodeMembreBenef(liaison.getCodeCompteAnim());
						model.addAttribute("message", e.getMessage());
						return "smcipn/transfert_bai";
					} catch (Exception e) {
						getLog().error("Echec de l'affectation", e);
						tr.setCodeMembreBenef(liaison.getCodeCompteAnim());
						model.addAttribute("message", e.getMessage());
						return "smcipn/transfert_bai";
					}
				} else {
					tr.setCodeMembreBenef(liaison.getCodeCompteAnim());
					model.addAttribute("message", "Ce compte d'administration n'est pas lié à son compte d'animation");
					return "smcipn/transfert_bai";
				}
			}
		}
		model.addAttribute("message", "Vous n'êtes pas autorisé avec effectué cette opération");
		return "smcipn/transfert_smcipn";
	}

	@RequestMapping(value = "/transferteff")
	public String listTrEffectues(Model model, Pageable pageable) {
		EuUtilisateur user = getCurrentUser();
		Page<EuTransfertNr> transferts = transfertNrService.findbyTransfert(user.getCodeMembre(), pageable);
		PageWrapper<EuTransfertNr> pTransferts = new PageWrapper<>(transferts, "/transferteff");
		model.addAttribute("transferts", pTransferts.getContent());
		model.addAttribute("page", pTransferts);
		return "smcipn/transfert_eff";
	}

	@RequestMapping(value = "/transfertrec")
	public String listTransfertRecus(Model model, Pageable pageable) {
		EuUtilisateur user = getCurrentUser();
		Page<EuTransfertNr> transferts = transfertNrService.findByBenef(user.getCodeMembre(), pageable);
		PageWrapper<EuTransfertNr> pTransferts = new PageWrapper<>(transferts, "/transferteff");
		model.addAttribute("transferts", pTransferts.getContent());
		model.addAttribute("page", pTransferts);
		return "smcipn/transfert_recu";
	}

}

package com.esmc.mcnp.web.controller.pc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esmc.mcnp.dao.repository.pc.EuPrestataireRepository;
import com.esmc.mcnp.domain.dto.bc.CalculBonInfo;
import com.esmc.mcnp.domain.entity.acteur.EuLiasonCompte;
import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.domain.entity.op.EuOffreurProjet;
import com.esmc.mcnp.domain.entity.org.EuDivisionGac;
import com.esmc.mcnp.domain.entity.pc.EuCharge;
import com.esmc.mcnp.domain.entity.pc.EuChargePaye;
import com.esmc.mcnp.domain.entity.pc.EuPrestataire;
import com.esmc.mcnp.domain.entity.smcipn.EuSmcipnpwi;
import com.esmc.mcnp.infrastructure.components.Payement;
import com.esmc.mcnp.infrastructure.components.SmcipnComponent;
import com.esmc.mcnp.infrastructure.components.SouscriptionBon;
import com.esmc.mcnp.infrastructure.components.TransfertUtility;
import com.esmc.mcnp.commons.exception.business.CompteNonTrouveException;
import com.esmc.mcnp.infrastructure.services.acteurs.EuLiaisonUserService;
import com.esmc.mcnp.infrastructure.services.acteurs.EuLiasonCompteService;
import com.esmc.mcnp.infrastructure.services.bc.EuBonService;
import com.esmc.mcnp.infrastructure.services.cm.EuCompteService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreService;
import com.esmc.mcnp.infrastructure.services.obps.EuTegcService;
import com.esmc.mcnp.infrastructure.services.op.EuOffreurProjetService;
import com.esmc.mcnp.infrastructure.services.pc.EuChargePayeService;
import com.esmc.mcnp.infrastructure.services.pc.EuChargeService;
import com.esmc.mcnp.infrastructure.services.smcipn.EuSmcipnpwiService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.dto.smcipn.Creance;
import com.esmc.mcnp.web.dto.op.OffreurProjet;
import com.esmc.mcnp.web.dto.util.Result;
import com.esmc.mcnp.web.dto.ot.SalaireDTO;

@Controller
public class PaiementController extends BaseController {

	private SmcipnComponent smcipnComp;
	private TransfertUtility transfertUtility;
	private Payement payement;
	private EuTegcService tegcService;
	private EuBonService bonService;
	private EuChargeService chargeService;
	private EuChargePayeService chargePayeService;
	private EuMembreService membreService;
	private EuMembreMoraleService moraleService;
	private EuSmcipnpwiService smcipnService;
	private EuCompteService compteService;
	private EuLiaisonUserService liaisonUserService;
	private EuLiasonCompteService liasonCompteService;
	private SouscriptionBon souscriptionBon;
	private EuOffreurProjetService offreurService;
	private EuPrestataireRepository prestataiareRepo;

	@Autowired
	public PaiementController(SmcipnComponent smcipnComp, TransfertUtility transfertUtility, Payement payement,
			EuTegcService tegcService, EuBonService bonService, EuCompteService compteService,
			EuChargeService chargeService, EuChargePayeService chargePayeService, EuMembreService membreService,
			EuMembreMoraleService moraleService, EuSmcipnpwiService smcipnService,
			EuLiaisonUserService liaisonUserService, EuLiasonCompteService liasonCompteService,
			SouscriptionBon souscriptionBon, EuOffreurProjetService offreurService,
			EuPrestataireRepository prestataiareRepo) {
		this.liaisonUserService = liaisonUserService;
		this.smcipnComp = smcipnComp;
		this.transfertUtility = transfertUtility;
		this.payement = payement;
		this.tegcService = tegcService;
		this.bonService = bonService;
		this.chargeService = chargeService;
		this.chargePayeService = chargePayeService;
		this.membreService = membreService;
		this.moraleService = moraleService;
		this.smcipnService = smcipnService;
		this.compteService = compteService;
		this.liasonCompteService = liasonCompteService;
		this.souscriptionBon = souscriptionBon;
		this.offreurService = offreurService;
		this.prestataiareRepo = prestataiareRepo;
	}

	@ModelAttribute("role")
	public String getRole() {
		return getCurrentUser().getRole();
	}

	@GetMapping(value = "/offreurp")
	public String afficheOffreurProjet(@ModelAttribute("offreur") OffreurProjet offreur, Model model,
			@SortDefault("codeMembre") Pageable pageable) {
		Page<EuOffreurProjet> offreurs = null;
		if (StringUtils.isNotBlank(offreur.getCodeMembre())) {
			offreurs = offreurService.findByMembre(offreur.getCodeMembre(), pageable);
		} else if (StringUtils.isNotBlank(offreur.getProduit()) && StringUtils.isNotBlank(offreur.getDescription())) {
			offreurs = offreurService.findByProduitOrDescription(offreur.getProduit(), offreur.getDescription(),
					pageable);
		} else if (StringUtils.isNotBlank(offreur.getProduit())) {
			offreurs = offreurService.findByProduit(offreur.getProduit(), pageable);
		} else if (StringUtils.isNotBlank(offreur.getDescription())) {
			offreurs = offreurService.findByDescription(offreur.getDescription(), pageable);
		} else {
			offreurs = offreurService.findAll(pageable);
		}
		model.addAttribute("page", offreurs);
		return "paiement/offreurProjet";
	}

	@GetMapping(value = "/prestataires")
	public String affichePrestataires(@ModelAttribute("prestataire") EuPrestataire prestataire, Model model,
			@SortDefault("codeMembre") Pageable pageable) {
		Page<EuPrestataire> prestataires = null;
		if (StringUtils.isNotBlank(prestataire.getCodeMembrePrestataire())) {
			prestataires = prestataiareRepo
					.findByCodeMembrePrestataireContaining(prestataire.getCodeMembrePrestataire(), pageable);
		} else if (StringUtils.isNotBlank(prestataire.getNomMembrePrestataire())) {
			prestataires = prestataiareRepo.findByNomMembrePrestataireContaining(prestataire.getNomMembrePrestataire(),
					pageable);
		} else {
			prestataires = prestataiareRepo.findAll(pageable);
		}
		model.addAttribute("page", prestataires);
		return "paiement/prestataires";
	}

	@GetMapping(value = "/addPrestataire")
	public String addPrestataire(@RequestParam(value = "id", required = false) String id, Model model) {
		EuPrestataire prestataire = new EuPrestataire();
		EuDivisionGac divisionGac = liaisonUserService.findByUtilisateur(getCurrentUser().getIdUtilisateur());
		if (Objects.nonNull(divisionGac)) {
			if (divisionGac.getLibelleDivision().equalsIgnoreCase("filiere")) {
				if (StringUtils.isNotBlank(id)) {
					String nom = StringUtils.EMPTY;
					if (id.endsWith("M")) {
						EuMembreMorale morale = moraleService.findById(id);
						nom = morale.getRaisonSociale();
					} else {
						EuMembre membre = membreService.findById(id);
						nom = membre.getNomMembre() + " " + membre.getPrenomMembre();
					}
					prestataire.setCodeMembrePrestataire(id);
					prestataire.setNomMembrePrestataire(nom);
					prestataire.setMarge(false);
					if (divisionGac.getTypeDivision().equalsIgnoreCase("administration")) {
						prestataire.setTypeCompte("SCMI");
					} else {
						prestataire.setTypeCompte("BAI");
					}
				}
				model.addAttribute("prestataire", prestataire);
				return "paiement/prestataire";
			}
		}
		return "paiement/index";
	}

	@Transactional
	@PostMapping(value = "/addPrestataire")
	public String addOffreurProjet(@ModelAttribute("prestataire") EuPrestataire prestataire, RedirectAttributes model) {
		if (Objects.nonNull(prestataire)) {
			EuDivisionGac divisionGac = liaisonUserService.findByUtilisateur(getCurrentUser().getIdUtilisateur());
			if (Objects.nonNull(divisionGac)) {
				EuMembreMorale debiteur = null;
				String codeMembreAdmin = divisionGac.getEuGac().getEuMembreMorale().getCodeMembreMorale();
				if (divisionGac.getTypeDivision().equalsIgnoreCase("administration")) {
					debiteur = moraleService.findById(codeMembreAdmin);
				} else {
					EuLiasonCompte liaison = liasonCompteService.findByCompteAdmin(codeMembreAdmin);
					debiteur = moraleService.findById(liaison.getCodeCompteAnim());
				}
				if (Objects.nonNull(debiteur)) {
					prestataire.setCodeMembre(debiteur.getCodeMembreMorale());
					if (prestataire.getMarge() == null || prestataire.getMarge() == false) {
						prestataire.setMarge(false);
					}
					if (prestataire.getNombreOpi() == null || prestataire.getNombreOpi() == 0) {
						prestataire.setNombreOpi(12);
					}
					prestataiareRepo.save(prestataire);
					model.addFlashAttribute("message", "L'opération a été effectuée avec succès");
					return "redirect:/prestataires";
				}
			}
		}
		model.addAttribute("message", "Echec de l'ajout du prestataire  :" + prestataire.getCodeMembrePrestataire());
		return "paiement/prestataire";
	}

	@GetMapping(value = "/paiement")
	public String afficheSmcipn() {
		return "paiement/index";
	}

	@GetMapping(value = "/psalaire")
	public String payerSalaire(@ModelAttribute("salaireDto") SalaireDTO salaireDTO, Model model) {
		return "paiement/salaire";
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@PostMapping(value = "/psalaire")
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

	@GetMapping(value = "/reglement")
	public String payerCharge(@RequestParam(value = "id", required = false) String id,
			@ModelAttribute("creance") Creance creance, Model model) {
		EuDivisionGac divisionGac = liaisonUserService.findByUtilisateur(getCurrentUser().getIdUtilisateur());
		if (Objects.nonNull(divisionGac)) {
			if (divisionGac.getLibelleDivision().equalsIgnoreCase("filiere")) {
				String codeMembreAdmin = divisionGac.getEuGac().getEuMembreMorale().getCodeMembreMorale();
				if (divisionGac.getTypeDivision().equalsIgnoreCase("administration")) {
					EuMembreMorale debiteur = moraleService.findById(codeMembreAdmin);
					creance.setCodeMembreDebiteur(codeMembreAdmin);
					creance.setNomDebiteur(debiteur.getRaisonSociale());
					creance.setTypeCreance("CF");
					creance.setModePaiement("SCMI");
				} else {
					EuLiasonCompte liaison = liasonCompteService.findByCompteAdmin(codeMembreAdmin);
					EuMembreMorale debiteur = moraleService.findById(liaison.getCodeCompteAnim());
					creance.setCodeMembreDebiteur(liaison.getCodeCompteAnim());
					creance.setNomDebiteur(debiteur.getRaisonSociale());
					creance.setTypeCreance("CF");
					creance.setModePaiement("BAI");
				}
				if (StringUtils.isNotBlank(id)) {
					if (id.endsWith("P")) {
						EuMembre membre = membreService.findById(id);
						if (Objects.nonNull(membre)) {
							creance.setCodeMembreCreancier(membre.getCodeMembre());
							creance.setNomCreancier(membre.getNomMembre() + " " + membre.getPrenomMembre());
							List<EuTegc> tegcs = tegcService.findByCodeMembrePhysique(membre.getCodeMembre());
							if (!tegcs.isEmpty()) {
								creance.setCodeTegc(tegcs.get(0).getCodeTegc());
							}
						}
					} else {
						EuMembreMorale morale = moraleService.findById(id);
						if (Objects.nonNull(morale)) {
							creance.setCodeMembreCreancier(morale.getCodeMembreMorale());
							creance.setNomCreancier(morale.getRaisonSociale());
							List<EuTegc> tegcs = tegcService.findByCodeMembre(morale.getCodeMembreMorale());
							if (!tegcs.isEmpty()) {
								creance.setCodeTegc(tegcs.get(0).getCodeTegc());
							}
						}
					}
				}
				return "paiement/reglement";
			} else {
				model.addAttribute("message", "Pas utilisateur de Tête de Division Filère");
				return "paiement/index";
			}
		} else {
			model.addAttribute("message", "Pas utilisateur de Tête de Division");
			return "paiement/index";
		}
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@PostMapping(value = "/reglement")
	public String doPayercharge(@ModelAttribute("creance") Creance creance, Model model, RedirectAttributes rmodel,
			BindingResult errors) {
		String codeMembreSmc = creance.getCodeMembreDebiteur();
		if (StringUtils.isNotBlank(creance.getCodeMembreCreancier()) && StringUtils.isNotBlank(creance.getCodeTegc())
				&& StringUtils.isNotBlank(creance.getTypeCreance()) && creance.getMontantOp() > 0) {
			EuTegc tegc = tegcService.getById(creance.getCodeTegc());
			if (Objects.nonNull(tegc)) {
				try {
					EuCompte compteDeb = null;
					if (creance.getModePaiement().equals("BAI")) {
						compteDeb = compteService.getById("NN-CAPA-" + codeMembreSmc);
					} else {
						compteDeb = compteService.getById("NR-TI-" + codeMembreSmc);
					}
					if (compteDeb.getSolde() >= creance.getMontantOp()) {
						EuBon bl = payement.createBonLivraison(creance.getCodeMembreCreancier(), codeMembreSmc,
								creance.getTypeCreance(), null, 0, creance.getMontantOp());
						if (Objects.nonNull(bl)) {

							bl.setBonExprimer(1);
							bl.setBonDateExpression(new Date());
							bonService.update(bl);

							if (creance.getModePaiement().equals("BAI")) {
								EuBon ba = transfertUtility.transfertBA(codeMembreSmc, "BAi", "",
										creance.getMontantOp());
								CalculBonInfo bonInfo = new CalculBonInfo();
								bonInfo.setCatBon("nr");
								bonInfo.setTypeCredit("AG");
								bonInfo.setPrk(5.6);
								souscriptionBon.souscrireBonConso(codeMembreSmc, bonInfo, ba.getBonNumero(),
										creance.getMontantOp());
								EuBon bon = transfertUtility.tansfertBC(codeMembreSmc, "I", bonInfo, 5.6,
										creance.getMontantOp());
								if (Objects.nonNull(bon)) {
									payement.makeTransaction(creance.getCodeMembreCreancier(), codeMembreSmc, "TI",
											tegc, bon, bl, creance.getTypeCreance(), creance.getMontantOp());
									payement.creerMarge(new Date(), creance.getMontantOp());

								}
							} else {
								CalculBonInfo bonInfo = new CalculBonInfo();
								bonInfo.setCatBon("nr");
								bonInfo.setTypeProduit("PS");
								if (StringUtils.isNotBlank(creance.getNumeroAppel())) {
									smcipnComp.echangeNRNB(codeMembreSmc, creance.getNumeroAppel(), "TPN", "PS",
											creance.getMontantOp(), 8.0);
								} else {
									smcipnComp.echangeSmcipn(codeMembreSmc, "TPN", "PS", creance.getMontantOp(), 8.0);
								}
								EuBon bon = transfertUtility.tansfertBC(codeMembreSmc, "TI", bonInfo, 8.0,
										creance.getMontantOp());
								if (Objects.nonNull(bon)) {
									payement.makeTransaction(creance.getCodeMembreCreancier(), codeMembreSmc, "TI",
											tegc, bon, bl, "PS", creance.getMontantOp());
									payement.creerMarge(new Date(), creance.getMontantOp());
								}
							}

							EuCharge tcharge = chargeService.findByCode(creance.getTypeCreance());
							EuChargePaye chargePaye = new EuChargePaye();
							if (StringUtils.isNotBlank(creance.getNumeroAppel())) {
								EuSmcipnpwi smcipn = smcipnService.findByNumeroAppel(creance.getNumeroAppel());
								chargePaye.setEuSmcipnpwi(smcipn);
							} else {
								chargePaye.setEuSmcipnpwi(null);
							}
							chargePaye.setEuCharge(tcharge);
							chargePaye.setNumDoc(creance.getNumDoc());
							chargePaye.setCodeMembreDebiteur(creance.getCodeMembreDebiteur());
							chargePaye.setCodeMembreCreancier(creance.getCodeMembreCreancier());
							chargePaye.setDateCharge(new Date());
							chargePaye.setLibelleCharge(creance.getTypeCreance());
							chargePaye.setMontantCharge(creance.getMontantOp());
							chargePaye.setOrigineCharge("ESMC");
							chargePayeService.add(chargePaye);
						}
						rmodel.addFlashAttribute("message", "L'opération a été effectuée avec succès");
						return "redirect:/reglement";
					} else {
						model.addAttribute("message", "Solde Insuffisant!");
						return "paiement/reglement";
					}
				} catch (IllegalArgumentException | CompteNonTrouveException | NullPointerException e) {
					model.addAttribute("message", "Erreur d'exécution : " + e.getMessage());
					return "paiement/reglement";
				}
			} else {
				model.addAttribute("message", "Ce membre n'a pas un Terminal d'Echange!");
				return "paiement/reglement";
			}
		} else {
			model.addAttribute("message", "Veuillez fournir les informations marquées comme obligatoires");
			return "paiement/reglement";
		}
	}

}

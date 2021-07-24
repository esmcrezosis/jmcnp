package com.esmc.mcnp.web.controller.bc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import com.esmc.mcnp.web.dto.bon.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esmc.mcnp.components.BonAchatComponent;
import com.esmc.mcnp.components.BonConsoComponent;
import com.esmc.mcnp.components.CreditUtility;
//import com.esmc.mcnp.components.CreditUtility;
import com.esmc.mcnp.components.SouscriptionBon;
import com.esmc.mcnp.components.TransfertUtility;
import com.esmc.mcnp.dto.bc.CalculBonInfo;
import com.esmc.mcnp.dto.other.TCredit;
import com.esmc.mcnp.model.ba.EuCapaTs;
import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.bc.EuCycleFormation;
import com.esmc.mcnp.model.bc.EuTypeCredit;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.obpsd.EuBonNeutre;
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
import com.esmc.mcnp.services.common.EuTypeCreditService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.google.common.collect.Lists;

@Controller
public class BonController extends BaseController {

	private TransfertUtility transfertUtility;
	private EuTypeCreditService typeCreditService;
	private SouscriptionBon souscriptionBon;
	private BonConsoComponent bonConsoComponent;
	private BonAchatComponent bonAchatComponent;
	private CreditUtility creditUtility;
	private EuCapaService capaService;
	private EuBonService bonService;
	private EuCapaTsService capatsService;
	private EuCompteService compteService;
	private EuCompteCreditService compteCreditService;
	private EuCycleFormationService cycleService;
	private EuBonNeutreService bonNeutreService;
	private final EuMembreService membreService;
	private final EuMembreMoraleService moraleService;

	@Inject
	public BonController(TransfertUtility transfertUtility, EuTypeCreditService typeCreditService,
			SouscriptionBon souscriptionBon, BonConsoComponent bonConsoComponent, CreditUtility creditUtility,
			EuCapaService capaService, EuBonService bonService, EuCapaTsService capatsService,
			EuCompteService compteService, EuCompteCreditService compteCreditService,
			EuCycleFormationService cycleService, EuBonNeutreService bonNeutreService,
			EuBonNeutreDetailService bonNeutreDetailService, EuMembreService membreService,
			EuMembreMoraleService moraleService, BonAchatComponent bonAchatComponent) {
		this.bonAchatComponent = bonAchatComponent;
		this.transfertUtility = transfertUtility;
		this.typeCreditService = typeCreditService;
		this.souscriptionBon = souscriptionBon;
		this.bonConsoComponent = bonConsoComponent;
		this.capaService = capaService;
		this.bonService = bonService;
		this.capatsService = capatsService;
		this.compteService = compteService;
		this.compteCreditService = compteCreditService;
		this.cycleService = cycleService;
		this.bonNeutreService = bonNeutreService;
		this.creditUtility = creditUtility;
		this.membreService = membreService;
		this.moraleService = moraleService;
	}

	@RequestMapping(value = "/bon", method = RequestMethod.GET)
	public String bon() {
		return "bon/bon";
	}

	@ModelAttribute("codeMembre")
	public String membre() {
		return getCurrentUser().getCodeMembre();
	}

	@ModelAttribute("tcredits")
	public List<TCredit> listTcredit() {
		List<TCredit> listTypeCredit = Lists.newArrayList();
		List<EuTypeCredit> tcredits = typeCreditService.fetchWithoutSalaire();
		if (!tcredits.isEmpty()) {
			tcredits.forEach(c -> {
				listTypeCredit.add(new TCredit(c.getCodeTypeCredit(), c.getLibTypeCredit(), c.getCodeCatProduit(),
						c.getTypeProduit()));
			});
		}
		return listTypeCredit;
	}

	@RequestMapping(value = "/typeba", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<String> listTypeBa(@RequestParam("codeMembre") String codeMembre,
			@RequestParam("typeBon") String origine) {
		List<String> listTypeCredit = capaService.findProduitByMembreAndOrigine(codeMembre, origine);
		return listTypeCredit;
	}

	@RequestMapping(value = "/transfertbc", method = RequestMethod.GET)
	public String transfertBonCons(@ModelAttribute("bon") TransfertBC bon,
			@RequestParam(value = "message", required = false) String message, Model model) {
		if (StringUtils.isNotBlank(message)) {
			model.addAttribute(message);
		}
		return "bon/transfert";
	}

	@RequestMapping(value = "/cycle", method = RequestMethod.GET)
	public @ResponseBody EuCycleFormation getCycle(@RequestParam("cycle") String cycle) {
		if (StringUtils.isNotBlank(cycle)) {
			return cycleService.findByCodeformation(cycle);
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/bcformation", method = RequestMethod.GET)
	public String souscrireFormation(@ModelAttribute("formation") BcFormation bcFormation) {
		return "bon/formation";
	}

	@Transactional
	@RequestMapping(value = "/bcformation", method = RequestMethod.POST)
	public String doSouscriptionFormation(@ModelAttribute("formation") BcFormation bcFormation, BindingResult errors,
			Model model, RedirectAttributes rmodel) {
		try {
			souscriptionBon.souscrireFormation(bcFormation.getCodeMembre(), bcFormation.getCatBonConso(),
					bcFormation.getTypeProduit(), bcFormation.getTypeRecurrent(), bcFormation.getCycle(),
					bcFormation.getMontFormation(), bcFormation.getNumBon());
			rmodel.addFlashAttribute("message", "Opération effectuée avec réussite.");
			return "redirect:/formation";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			return "bon/formation";
		}
	}

	@RequestMapping(value = "/transfertbc", method = RequestMethod.POST)
	public String doTransfertBonCons(@ModelAttribute("bon") TransfertBC bon, RedirectAttributes model) {
		if (Objects.nonNull(bon)) {
			CalculBonInfo bonInfo = new CalculBonInfo();
			EuBon bc = transfertUtility.tansfertBC(bon.getCodeMembre(), bon.getType(), bonInfo, bon.getPrk(),
					bon.getMontant());
			if (Objects.nonNull(bc)) {
				model.addFlashAttribute("message", "Le Bon  a été bien créé; Son numéro est :" + bc.getBonNumero());
				return "redirect:/transfertbc";
			} else {
				model.addAttribute("message", "erreur inattendue, Veuillez signaler cette erreur à l'ESMC!");
				return "bon/transfert";
			}
		} else {
			model.addAttribute("message", "Vous devez fournir toutes les informations requises ");
			return "bon/transfert";
		}
	}

	@RequestMapping(value = "/transfertba", method = RequestMethod.GET)
	public String transfertBonAchat(@ModelAttribute("bon") TransfertBA bon,
			@RequestParam(value = "message", required = false) String message, Model model) {
		if (StringUtils.isNotBlank(message)) {
			model.addAttribute("message", message);
		}
		return "bon/transfertBA";
	}

	@RequestMapping(value = "/transfertba", method = RequestMethod.POST)
	public String doTransfertBonAchat(@ModelAttribute("bon") TransfertBA bon, RedirectAttributes model) {
		if (Objects.nonNull(bon)) {
			EuBon ba = transfertUtility.transfertBA(bon.getCodeMembre(), bon.getTypeBon(), bon.getCatBon(),
					bon.getMontant());
			if (Objects.nonNull(ba)) {
				model.addFlashAttribute("message", "Le Bon a été bien créé; Son numéro est :" + ba.getBonNumero());
				return "redirect:/transfertba";
			} else {
				model.addAttribute("message", "erreur inattendue, Veuillez signaler cette erreur à l'ESMC!");
				return "bon/transfertBA";
			}
		} else {
			model.addAttribute("message", "Vous devez fournir toutes les informations requises ");
			return "bon/transfertBA";
		}
	}

	@GetMapping(value = "/activatebcr")
	public String activateBCri(ModelMap model, @RequestParam(name = "codeMembre", required = false) String codeMembre,
			@RequestParam(name = "typeRecurrent", required = false) String typeRecurrent,
			@RequestParam(name = "dateDeb", required = false) String deb,
			@RequestParam(name = "dateFin", required = false) String fin, Pageable pageable) throws ParseException {
		Page<EuCompteCredit> credits = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotBlank(codeMembre) && StringUtils.isNotBlank(typeRecurrent) && StringUtils.isNotBlank(deb)
				&& StringUtils.isNotBlank(fin)) {
			Date dateDeb = dateFormat.parse(deb);
			Date dateFin = dateFormat.parse(fin);
			credits = compteCreditService.findCredits(codeMembre, typeRecurrent, dateDeb, dateFin, pageable);
		} else if (StringUtils.isNotBlank(codeMembre) && StringUtils.isNotBlank(typeRecurrent)) {
			credits = compteCreditService.findByCodeMembreAndTypeRecurrent(codeMembre, typeRecurrent, pageable);
		} else if (StringUtils.isNotBlank(codeMembre)) {
			credits = compteCreditService.findByCodeMembre(codeMembre, pageable);
		} else if (StringUtils.isNotBlank(typeRecurrent)) {
			credits = compteCreditService.findByTypeRecurrent(typeRecurrent, pageable);
		} else if (StringUtils.isNotBlank(deb) && StringUtils.isNotBlank(fin)) {
			Date dateDeb = dateFormat.parse(deb);
			Date dateFin = dateFormat.parse(fin);
			credits = compteCreditService.findByDateOctroiBetween(dateDeb, dateFin, pageable);
		}
		model.addAttribute("credits", credits);
		return "bon/activation";
	}

	@Transactional
	@GetMapping(value = "/activatebcr/{id}")
	public ResponseEntity<String> activate(@PathVariable("id") Long idCredit) {
		if (idCredit != null) {
			EuCompteCredit credit = compteCreditService.getById(idCredit);
			if (Objects.nonNull(credit)) {
				credit.setActiver(true);
				compteCreditService.update(credit);
				return new ResponseEntity<>("Bon Conso N° " + idCredit + " activé", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Echec de l'activation du Bon Conso N° " + idCredit, HttpStatus.OK);
	}

	@GetMapping(value = "/bc")
	public String listBC() {
		return "bon/bc";
	}

	@RequestMapping(value = "/bcr", method = RequestMethod.GET)
	public String bonConsor(@ModelAttribute("bc") BonConso bc, Model model) {
		bc.setCatBonConso("r");
		if (StringUtils.isNotBlank(getCurrentUser().getCodeMembre())) {
			String codeMembre = getCurrentUser().getCodeMembre();
			bc.setCodeMembre(codeMembre);
			if (codeMembre.endsWith("P")) {
				model.addAttribute("nom", membreService.findMembreInfo(codeMembre));
			} else {
				model.addAttribute("nom", moraleService.findMembreInfo(codeMembre));
			}
		}
		model.addAttribute("agr", "OKSU");
		return "bon/bcr";
	}

	@Transactional
	@RequestMapping(value = "/bcr", method = RequestMethod.POST)
	public String doBonConsor(@ModelAttribute("bc") BonConso bc, RedirectAttributes model) {
		List<EuCapaTs> capatss = capatsService.findByEuBon_BonNumero(bc.getCodeBon());
		String typeBon = "RPG";
		if (bc.getCodeMembre().endsWith("M")) {
			typeBon = "I";
		}
		String codeCompte = "NB-TPAGC" + typeBon + "-" + bc.getCodeMembre();
		String codeCompteNn = "NN-CAPA-" + bc.getCodeMembre();
		EuCompte compte = compteService.getById(codeCompte);
		EuCompte compteNn = compteService.getById(codeCompteNn);
		CalculBonInfo bonInfo = new CalculBonInfo();
		bonInfo.setCatBon(bc.getCatBonConso());
		bonInfo.setDuree(bc.getDuree());
		bonInfo.setPrk(bc.getPrk());
		if (StringUtils.isNotBlank(bc.getTypeProduit())) {
			bonInfo.setTypeProduit(bc.getTypeProduit());
		} else {
			bonInfo.setTypeProduit("PS");
		}
		bonInfo.setTypeRecurrent(bc.getTypeRecurrent());
		if (bc.getCodeMembre().endsWith("M")) {
			boolean result = bonConsoComponent.souscrireI(bc.getCodeMembre(), compte, compteNn, bc.getCodeBon(),
					capatss, typeBon, bonInfo, bc.getMontant());
			if (result) {
				model.addFlashAttribute("message", "La souscription au Bon de consommation a été bien effectuée;");
				return "redirect:/bcr";
			} else {
				model.addAttribute("message",
						"La souscription au Bon de consommation a échouée; Veuillez réessayer ou contacter ESMC pour signaler l'erreur");
				return "bon/bcr";
			}
		} else {
			boolean result = bonConsoComponent.souscrireRpg(bc.getCodeMembre(), compte, compteNn, bc.getCodeBon(),
					typeBon, bonInfo, bc.getMontant());
			if (result) {
				model.addFlashAttribute("message", "La souscription au Bon de consommation a été bien effectuée;");
				return "redirect:/bcr";
			} else {
				model.addAttribute("message",
						"La souscription au Bon de consommation a échouée; Veuillez réessayer ou contacter ESMC pour signaler l'erreur");
				return "bon/bcr";
			}
		}
	}

	@RequestMapping(value = "/bcban", method = RequestMethod.GET)
	public String bonConsobanr(@ModelAttribute("bc") BonConso bc, Model model) {
		bc.setCatBonConso("r");
		if (StringUtils.isNotBlank(getCurrentUser().getCodeMembre())) {
			String codeMembre = getCurrentUser().getCodeMembre();
			bc.setCodeMembre(codeMembre);
			if (codeMembre.endsWith("P")) {
				model.addAttribute("nom", membreService.findMembreInfo(codeMembre));
			} else {
				model.addAttribute("nom", moraleService.findMembreInfo(codeMembre));
			}
		}
		model.addAttribute("agr", "OKSU");
		return "bon/bcban";
	}

	@RequestMapping(value = "/bcbannr", method = RequestMethod.GET)
	public String bonConsobannr(@ModelAttribute("bc") BonConso bc, Model model) {
		bc.setCatBonConso("nr");
		bc.setTypeNonRecurrent("prk");
		if (StringUtils.isNotBlank(getCurrentUser().getCodeMembre())) {
			String codeMembre = getCurrentUser().getCodeMembre();
			bc.setCodeMembre(codeMembre);
			if (codeMembre.endsWith("P")) {
				model.addAttribute("nom", membreService.findMembreInfo(codeMembre));
			} else {
				model.addAttribute("nom", moraleService.findMembreInfo(codeMembre));
			}
		}
		model.addAttribute("agr", "OKSU");
		return "bon/bcbannr";
	}

	@RequestMapping(value = "/bcbannrpre", method = RequestMethod.GET)
	public String bonConsobapren(@ModelAttribute("bc") BonConso bc, Model model) {
		bc.setCatBonConso("nr");
		bc.setTypeNonRecurrent("pre");
		if (StringUtils.isNotBlank(getCurrentUser().getCodeMembre())) {
			String codeMembre = getCurrentUser().getCodeMembre();
			bc.setCodeMembre(codeMembre);
			if (codeMembre.endsWith("P")) {
				model.addAttribute("nom", membreService.findMembreInfo(codeMembre));
			} else {
				model.addAttribute("nom", moraleService.findMembreInfo(codeMembre));
			}
		}
		model.addAttribute("agr", "OKSU");
		return "bon/bcbannrpre";
	}

	@Transactional
	@RequestMapping(value = "/bcban", method = RequestMethod.POST)
	public String doBonConsoban(@ModelAttribute("bc") BonConso bc, RedirectAttributes model) {
		System.out.print("Categorie Bon :" + bc.getCodeBon());
		String typeBa = "RPG";
		if (bc.getCodeMembre().endsWith("M")) {
			typeBa = "I";
		}
		if (bonAchatComponent.souscrireBonAchat(bc.getCodeMembre(), bc.getCodeBon(), typeBa, bc.getMontant())) {
			System.out.println("====== Categorie Bon : " + bc.getCodeBon());
			String typeNn = "RPG";
			if (bc.getCodeMembre().endsWith("M")) {
				typeNn = "I";
			}
			EuBon bon = transfertUtility.transfertBA(bc.getCodeMembre(), "BAN", typeNn, bc.getMontant());
			if (Objects.nonNull(bon)) {
				CalculBonInfo bonInfo = new CalculBonInfo();
				bonInfo.setCatBon(bc.getCatBonConso());
				bonInfo.setDuree(bc.getDuree());
				bonInfo.setPrk(bc.getPrk());
				if (StringUtils.isNotBlank(bc.getTypeProduit())) {
					bonInfo.setTypeProduit(bc.getTypeProduit());
				} else {
					bonInfo.setTypeProduit("PS");
				}
				bonInfo.setTypeRecurrent(bc.getTypeRecurrent());
				int result = souscriptionBon.souscrireBonConso(bc.getCodeMembre(), bonInfo, bon.getBonNumero(),
						bon.getBonMontant());
				if (result == 0) {
					model.addFlashAttribute("message", "La souscription  a été bien effectué");
					return "redirect:/bcban";
				} else {
					model.addAttribute("message",
							"La souscription au Bon de Consommationa échouée; Veuillez réessayer ou contacter ESMC pour signaler l'erreur");
					return "bon/bcban";
				}
			}
		}
		return "bon/bcban";
	}

	@RequestMapping(value = "ba", method = RequestMethod.GET)
	public String soucrireBonAchat(Model model) {
		BonAchat ba = new BonAchat();
		ba.setType("BN");
		model.addAttribute("ba", ba);
		return "bon/ba";
	}

	@RequestMapping(value = "calBc", method = RequestMethod.GET)
	public @ResponseBody double calculBonConso(@RequestParam("catBonConso") String catBon,
			@RequestParam("typeRecurrent") String typeRecurrent, @RequestParam("duree") Double duree,
			@RequestParam("montant") double montant) {
		double montResult = 0;
		if (StringUtils.isNotBlank(catBon) && StringUtils.isNotBlank(typeRecurrent) && duree > 0.0) {
			CalculBonInfo bonInfo = new CalculBonInfo();
			bonInfo.setCatBon(catBon);
			bonInfo.setTypeRecurrent(typeRecurrent);
			bonInfo.setDuree(duree);
			bonInfo.setTypeProduit("PS");
			try {
				montResult = creditUtility.calculBonConso(bonInfo, montant);
			} catch (RuntimeException e) {
				getLog().error("Erreur d'execution", e);
			}
		}
		return Math.floor(montResult);
	}

	@RequestMapping(value = "/getbon", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody double getBon(@RequestParam("codeBon") String numeroBon) {
		if (StringUtils.isNotBlank(numeroBon)) {
			EuBon bon = bonService.findByBonCode(numeroBon, 0);
			if (bon != null) {
				return bon.getBonMontant();
			}
		}
		return 0;
	}

	@RequestMapping(value = "/bonNeutre", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody double getBonNeutre(@RequestParam("codeBon") String numeroBon) {
		if (StringUtils.isNotBlank(numeroBon)) {
			EuBonNeutre bon = bonNeutreService.findByCode(numeroBon);
			if (bon != null) {
				return bon.getBonNeutreMontantSolde();
			} else {

			}
		}
		return 0;
	}

	@RequestMapping(value = "ba", method = RequestMethod.POST)
	public String soucrireBonAchat(@ModelAttribute("ba") BonAchat ba, RedirectAttributes model) {
		boolean result = false;
		String codeProduit = "RPG";
		if (ba.getCodeMembre().endsWith("M")) {
			codeProduit = "I";
		}
		if (StringUtils.isNotBlank(ba.getCodeBonNeutre())) {
			result = bonAchatComponent.souscrireBonAchat(ba.getCodeMembre(), ba.getCodeBonNeutre(), codeProduit,
					ba.getMontant());
		} else {
			result = bonAchatComponent.souscrireBonAchat(ba.getCodeMembre(), null, codeProduit, ba.getMontant());
		}
		if (result) {
			model.addFlashAttribute("message", "Le transfert  a été bien effectué");
			return "redirect:/ba";
		} else {
			model.addAttribute("message",
					"La souscription au Bon d'Achat a échoue; Veuillez réessayer ou contacter ESMC pour signaler l'erreur");
			return "bon/ba";
		}
	}
}

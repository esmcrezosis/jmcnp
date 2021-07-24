package com.esmc.mcnp.web.controller.odd;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esmc.mcnp.components.CreditUtility;
import com.esmc.mcnp.components.SouscriptionBon;
import com.esmc.mcnp.dto.other.Bnp;
import com.esmc.mcnp.dto.other.TableauBnp;
import com.esmc.mcnp.model.oi.EuTypeBnp;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.obps.EuTegc;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.cm.EuMembreService;
import com.esmc.mcnp.services.obps.EuTegcService;
import com.esmc.mcnp.services.oi.EuTypeBnpService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.dto.bon.BnpDto;
import com.esmc.mcnp.util.BnpForm;
import com.esmc.mcnp.util.Reponse;
import com.google.common.collect.Lists;

@Controller
public class BnpController extends BaseController {

	private final CreditUtility creditUtility;
	private final EuTypeBnpService bnpTypeRepo;
	private final SouscriptionBon souscrireBon;
	private final EuTegcService tegcService;
	private final EuMembreService membreService;
	private final EuMembreMoraleService membreMoraleService;

	@Inject
	public BnpController(CreditUtility creditUtility, EuTypeBnpService bnpTypeRepo, SouscriptionBon souscrireBon,
			EuTegcService tegcService, EuMembreService membreService, EuMembreMoraleService membreMoraleService) {
		this.creditUtility = creditUtility;
		this.bnpTypeRepo = bnpTypeRepo;
		this.souscrireBon = souscrireBon;
		this.tegcService = tegcService;
		this.membreService = membreService;
		this.membreMoraleService = membreMoraleService;
	}

	@RequestMapping(value = "/bindex")
	public String index() {
		return "bnp/index";
	}

	@RequestMapping(value = "/tbnp", method = RequestMethod.GET)
	public String bnp(@ModelAttribute("bnpForm") BnpForm bnpForm) {
		return "bnp/tableaubnp";
	}

	@ModelAttribute("typeBnps")
	public List<EuTypeBnp> listTypeBnp() {
		return bnpTypeRepo.list();
	}

	@RequestMapping(value = "/tbnp", method = RequestMethod.POST)
	public String doTableauBnp(BnpForm bnpForm, Model model) {
		if (Objects.nonNull(bnpForm)) {
			System.out.println("Web Controller for BNP");
			if (StringUtils.isNotBlank(bnpForm.getTypeBnp()) && bnpForm.getMontantBnp() > 0) {
				System.out.println("Producing the BNP Grid");
				List<TableauBnp> tbs = creditUtility.doTableauBnp(bnpForm.getTypeBnp(), bnpForm.isFs(),
						bnpForm.getMontantBnp());
				System.out.println("Taille :" + tbs.size());
				model.addAttribute("bnps", tbs);
				return "home/tableaubnp";
			}
		}
		return "bnp/tableaubnp";
	}

	@RequestMapping(value = "/bnp", method = RequestMethod.GET)
	public String souscrirePourTiers(@ModelAttribute("bnp") Bnp bnp, Model model) {
		EuUtilisateur user = getCurrentUser();
		if (StringUtils.isNotBlank(user.getCodeMembre())) {
			List<EuTegc> tegcs = tegcService.findByCodeMembre(user.getCodeMembre());
			bnp.setCodeMembreApp(user.getCodeMembre());
			model.addAttribute("tegcs", tegcs);
		} else {
			model.addAttribute("tegcs", Lists.newArrayList());
		}
		return "bnp/bnp";
	}

	@RequestMapping(value = "/sbnp", method = RequestMethod.GET)
	public String souscrirePrTiers(@ModelAttribute("bnp") BnpDto bnp, Model model) {
		EuUtilisateur user = getCurrentUser();
		if (StringUtils.isNotBlank(user.getCodeMembre())) {
			bnp.setCodeMembreApp(user.getCodeMembre());
		}
		return "bnp/sbnp";
	}

	@Transactional
	@RequestMapping(value = "/sbnp", method = RequestMethod.POST)
	public String doSouscrirePrTiers(@ModelAttribute("bnp") BnpDto bnp, Model model, RedirectAttributes rmodel) {
		if (souscrireBon.souscrirePourTiers(bnp.getCodeMembreBenef(), bnp.getCodeMembreApp(), bnp.getTypeBnp(),
				bnp.getMontant()) == 0) {
			rmodel.addFlashAttribute("message", "L'opération a été bien effectuée");
			return "redirect:/sbnp";
		}
		model.addAttribute("message", "Veuillez renseigner les champs correctement");
		return "bnp/sbnp";
	}

	@RequestMapping(value = "/membre", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Reponse> getMembreIdent(@RequestParam("codeMembre") String codeMembre) {
		if (StringUtils.isNotBlank(codeMembre) && 20 == codeMembre.length()) {
			if (codeMembre.endsWith("P")) {
				EuMembre membre = membreService.findById(codeMembre);
				return new ResponseEntity<>(new Reponse(membre.getNomMembre() + " " + membre.getPrenomMembre()),
						HttpStatus.OK);
			} else {
				EuMembreMorale membre = membreMoraleService.findById(codeMembre);
				return new ResponseEntity<>(new Reponse(membre.getRaisonSociale()), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(new Reponse(""), HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(value = "/bnp", method = RequestMethod.POST)
	public String souscrireTiers(@ModelAttribute("bnp") Bnp bnp, Model model, RedirectAttributes rmodel) {
		EuTegc tegc = tegcService.getById(bnp.getCodeTeApp());
		if (Objects.isNull(tegc)) {
			model.addAttribute("message", "Le membre Apporteur n'a pas de TE");
			return "bnp/bnp";
		}
		if (souscrireBon.souscrirePourTiers(bnp) == 0) {
			rmodel.addFlashAttribute("message", "L'opération a été bien effectuée");
			return "redirect:/bnp";
		}
		model.addAttribute("message", "Veuillez renseigner les champs correctement");
		return "bnp/bnp";
	}

	@RequestMapping(value = "/calculBcTiers", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Double calculBcTiers(@RequestParam("typeBnp") String typeBnp,
			@RequestParam("typeProduit") String typeProduit, @RequestParam("montantBnp") Double montBnp) {
		return Math.floor(creditUtility.calculBcTiers(typeProduit, typeBnp, montBnp));
	}

	@RequestMapping(value = "/calculMsbcTiers", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Double calculMsBcTiers(@RequestParam("typeBnp") String typeBnp,
			@RequestParam("typeProduit") String typeProduit, @RequestParam("montBc") Double montBc) {
		return Math.floor(creditUtility.calculMsbcTiers(typeProduit, typeBnp, montBc));
	}

}

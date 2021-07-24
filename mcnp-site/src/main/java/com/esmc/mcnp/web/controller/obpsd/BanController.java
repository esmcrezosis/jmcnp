package com.esmc.mcnp.web.controller.obpsd;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esmc.mcnp.components.AffectationComponent;
import com.esmc.mcnp.components.BonNeutreComponent;
import com.esmc.mcnp.model.acteur.EuActeur;
import com.esmc.mcnp.model.obpsd.EuDemandeBan;
import com.esmc.mcnp.model.org.EuDivisionGac;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.services.acteurs.EuActeurService;
import com.esmc.mcnp.services.acteurs.EuLiaisonUserService;
import com.esmc.mcnp.services.ba.EuDemandeBanService;
import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.cm.EuMembreService;
import com.esmc.mcnp.services.pc.EuBanService;
import com.esmc.mcnp.services.setting.EuParametresService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.dto.bon.Ban;
import com.esmc.mcnp.web.dto.util.Data;

@Controller
public class BanController extends BaseController {

	private EuBanService banService;
	private EuMembreMoraleService moraleService;
	private EuMembreService membreService;
	private EuActeurService acteurService;
	private EuParametresService parametresService;
	private EuDemandeBanService demandeService;
	private AffectationComponent affectationCompo;
	private BonNeutreComponent bonNeutreComponent;
	private EuLiaisonUserService liaisonUserService;

	@Autowired
	public BanController(EuBanService banService, EuMembreMoraleService moraleService, EuActeurService acteurService,
			EuParametresService parametresService, EuDemandeBanService demandeService, EuMembreService membreService,
			AffectationComponent affectationCompo, BonNeutreComponent bonNeutreComponent,
			EuLiaisonUserService liaisonUserService) {
		this.banService = banService;
		this.moraleService = moraleService;
		this.acteurService = acteurService;
		this.parametresService = parametresService;
		this.demandeService = demandeService;
		this.membreService = membreService;
		this.affectationCompo = affectationCompo;
		this.bonNeutreComponent = bonNeutreComponent;
		this.liaisonUserService = liaisonUserService;
	}

	@PreAuthorize("hasAuthority('FDD')")
	@GetMapping(value = "/ban")
	public String ban(@ModelAttribute Ban ban) {
		return "banque/ban";
	}

	@PreAuthorize("hasAuthority('FDD')")
	@PostMapping(value = "/ban")
	public String emettreBan(@RequestParam String codeMembre, @RequestParam double montant, RedirectAttributes model) {
		if (StringUtils.isNotBlank(codeMembre) && montant > 0) {
			EuActeur acteur = acteurService.findByMembreAndType(codeMembre, "PBF");
			if (Objects.nonNull(acteur) && acteur.getCodeActivite().equalsIgnoreCase("GROSSISTE")) {
				double taux = parametresService.getParametre("BAN", "TxAppro");
				double mont_ban = montant + (montant * taux) / 100;
				int res = banService.emettreBan(codeMembre, mont_ban);
				if (res == 1) {
					model.addFlashAttribute("message", "Opération bien effectuée");
					return "redirect:/ban";
				} else {
					model.addAttribute("message", "Echec de l'opération : Une Erreur s'est produite");
					return "banque/ban";
				}
			}
		}
		return null;
	}

	@PreAuthorize("hasAuthority('FDS')")
	@GetMapping(value = "/affectban")
	public String affectBan(@ModelAttribute Ban ban) {
		return "banque/affectationBan";
	}

	@PreAuthorize("hasAuthority('FDS')")
	@PostMapping(value = "/affectban")
	public String affectBan(@ModelAttribute Ban ban, Model md, RedirectAttributes model) {
		EuDivisionGac divisionGac = liaisonUserService.findByUtilisateur(getCurrentUser().getIdUtilisateur());
		if (Objects.nonNull(divisionGac)) {
			if (divisionGac.getLibelleDivision().equalsIgnoreCase("filiere")) {
				if (StringUtils.isNotBlank(ban.getCodeMembre()) && ban.getMontant() > 0) {
					try {
						if ("0010010010010000212M".equals(divisionGac.getCodeMembre())) {
							affectationCompo.affecterBan(divisionGac.getCodeMembre(), ban.getCodeMembre(),
									ban.getType(), ban.getMontant());
						} else {
							bonNeutreComponent.approBAn(divisionGac.getCodeMembre(), ban.getCodeMembre(),
									ban.getMontant());
						}
						model.addFlashAttribute("message", "Opération bien effectuée");
						return "redirect:/affectban";
					} catch (Exception e) {
						getLog().error(e.getStackTrace());
						md.addAttribute("message",
								"Echec de l'opération : Une Erreur s'est produite :" + e.getMessage());
						return "banque/affectationBan";
					}
				} else {
					md.addAttribute("message", "Echec de l'opération : Une Erreur s'est produite");
					return "banque/affectationBan";
				}
			} else {
				md.addAttribute("message", "Vous n'êtes pas autorisé à effectuer cette opération");
				return "banque/affectationBan";
			}
		} else {
			md.addAttribute("message", "Vous n'êtes pas lié à une tête de division");
			return "banque/affectationBan";
		}
	}

	@GetMapping(value = "/raisonMorale")
	public ResponseEntity<Data> getNom(@RequestParam String codeMembre) {
		if (StringUtils.isNotBlank(codeMembre)) {
			EuMembreMorale morale = moraleService.findById(codeMembre);
			if (Objects.nonNull(morale)) {
				return new ResponseEntity<>(new Data(morale.getRaisonSociale()), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/nomMembreAff")
	public ResponseEntity<Data> getNomMembre(@RequestParam String codeMembre) {
		if (StringUtils.isNotBlank(codeMembre)) {
			if (codeMembre.endsWith("M")) {
				EuMembreMorale morale = moraleService.findById(codeMembre);
				if (Objects.nonNull(morale)) {
					return new ResponseEntity<>(new Data(morale.getRaisonSociale()), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(null, HttpStatus.OK);
				}
			} else {
				EuMembre membre = membreService.findById(codeMembre);
				if (Objects.nonNull(membre)) {
					return new ResponseEntity<>(new Data(membre.getNomMembre() + " " + membre.getPrenomMembre()),
							HttpStatus.OK);
				} else {
					return new ResponseEntity<>(null, HttpStatus.OK);
				}

			}
		} else {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/banGcp")
	public String demandeBan(@ModelAttribute Ban ban) {
		return "distributeur/ban";
	}

	@Transactional(rollbackFor = Exception.class)
	@PostMapping(value = "/banGcp")
	public String doDemandeBan(@ModelAttribute("ban") Ban ban, Model model, RedirectAttributes rmodel) {
		if (StringUtils.isNotBlank(ban.getCodeMembre()) && ban.getMontant() > 0) {
			try {
				EuDemandeBan demBan = new EuDemandeBan();
				demBan.setDateDemande(LocalDateTime.now());
				demBan.setCodeMembre(ban.getCodeMembre());
				demBan.setAllouer(false);
				demBan.setCodeTegc(ban.getCodeTegc());
				demBan.setMontAlloue(BigDecimal.ZERO);
				demBan.setValider(false);
				demBan.setModePaiement(ban.getModePaiement());
				demandeService.add(demBan);
				rmodel.addFlashAttribute("message", "Demande effectuée avec succès");
				return "redirect:/banGcp";
			} catch (Exception e) {
				getLog().error("Echec de la demande du BAn contre ... :", e);
				model.addAttribute("message", "Echec de la demande :" + e.getMessage());
				return "distributeur/ban";
			}
		}
		return "distributeur/ban";
	}

	public String listDemandeBan(@RequestParam("dateDamande") Date date,
			@RequestParam("codeMembre") String codeMembre) {
		return "";
	}
}

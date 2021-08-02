package com.esmc.mcnp.web.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esmc.mcnp.domain.entity.cm.EuReligion;
import com.esmc.mcnp.domain.entity.odd.EuMstierListecm;
import com.esmc.mcnp.domain.entity.odd.EuOdd;
import com.esmc.mcnp.domain.entity.org.EuCanton;
import com.esmc.mcnp.domain.entity.org.EuPays;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.infrastructure.services.common.EuReligionService;
import com.esmc.mcnp.infrastructure.services.odd.EuAgenceOddService;
import com.esmc.mcnp.infrastructure.services.odd.EuOddService;
import com.esmc.mcnp.infrastructure.services.oi.EuMstierListecmService;
import com.esmc.mcnp.infrastructure.services.org.EuCantonService;
import com.esmc.mcnp.infrastructure.services.org.EuPaysService;
import com.esmc.mcnp.web.controller.base.BaseController;

@Controller
@RequestMapping(value = "public")
public class FicheOddController extends BaseController {
	
	private final EuOddService oddService;
	private final EuAgenceOddService agenceOddService;
	private final EuMstierListecmService mstierListecmService;
	private final EuCantonService cantonService;
	private final EuReligionService religionService;
	private final EuPaysService paysService;

	public FicheOddController(EuOddService oddService, EuAgenceOddService agenceOddService,
			EuMstierListecmService mstierListecmService, EuCantonService cantonService,
			EuReligionService religionService, EuPaysService paysService) {
		this.oddService = oddService;
		this.agenceOddService = agenceOddService;
		this.mstierListecmService = mstierListecmService;
		this.cantonService = cantonService;
		this.religionService = religionService;
		this.paysService = paysService;
	}

	@GetMapping(value = "fiche")
	public String addFicheOdd(@ModelAttribute("fiche") EuMstierListecm mstierListecm, ModelMap model) {
		mstierListecm.setCanton(new EuCanton());
		mstierListecm.setOdd(new EuOdd());
		mstierListecm.setReligion(new EuReligion());
		List<EuOdd> odds = oddService.listAll();
		List<EuReligion> religions = religionService.listAll();
		List<EuCanton> cantons = cantonService.listAll();
		List<EuPays> pays = paysService.list();
		model.addAttribute("odds", odds);
		model.addAttribute("religions", religions);
		model.addAttribute("cantons", cantons);
		model.addAttribute("pays", pays);
		return "public/fiche";
	}
	
	@Transactional
	@PostMapping(value = "fiche")
	public String saveFicheOdd(@ModelAttribute("fiche") EuMstierListecm mstierListecm, Model model,
			RedirectAttributes rmodel) {
		if (Objects.nonNull(mstierListecm)) {
			try {
				List<EuMstierListecm> listes = mstierListecmService.findDoublonByNom(mstierListecm.getNomMembre(),
						mstierListecm.getPrenomMembre());
				long compte = 0;
				if (listes.size() > 1) {
					compte = listes.stream()
							.filter(l -> (l.getDateNaisMembre().isEqual(mstierListecm.getDateNaisMembre())
									&& l.getLieuNaisMembre().equalsIgnoreCase(mstierListecm.getLieuNaisMembre())))
							.count();
				}
				if (compte <= 1) {
					EuUtilisateur user = getCurrentUser();
					mstierListecm.setUser(user);
					mstierListecm.setDateListecm(LocalDateTime.now());
					mstierListecm.setAgence(user.getEuAgence());
					mstierListecm.setStatut(0);
					if (listes.size() > 1) {
						mstierListecm.setDoublon(1);
					} else {
						mstierListecm.setDoublon(0);
					}
					mstierListecm.setCodesecret(RandomStringUtils.randomAlphanumeric(8));
					if (Objects.nonNull(user.getAgencesOdd())) {
						mstierListecm.setAgenceOdd(user.getAgencesOdd());
					}
					mstierListecmService.create(mstierListecm);
					rmodel.addFlashAttribute("message", "Fiche ODD bien enregistrée");
					return "redirect:/odd/fiche";
				} else {
					model.addAttribute("message", "Cette personne est déjà enregistrée");
					model.addAttribute("fiche", mstierListecm);
					return "odd/fiche";
				}
			} catch (Exception e) {
				model.addAttribute("fiche", mstierListecm);
				model.addAttribute("message", "Echec de l'opération: " + e.getMessage());
				return "odd/fiche";
			}
		} else {
			model.addAttribute("message", "Veuillez renseigner tous les champs obligatoires");
			model.addAttribute("fiche", mstierListecm);
			return "odd/fiche";
		}
	}
}

package com.esmc.mcnp.web.controller.oi;

import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.esmc.mcnp.model.odd.EuMstierListebc;
import com.esmc.mcnp.model.odd.EuMstierListecm;
import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.cm.EuMembreService;
import com.esmc.mcnp.services.odd.EuOddService;
import com.esmc.mcnp.services.oi.EuMstierListebcService;
import com.esmc.mcnp.services.oi.EuMstierListecmService;
import com.esmc.mcnp.util.JqGrid;
import com.esmc.mcnp.util.Reponse;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "oi")
public class OiController extends BaseController {

	private final EuOddService oddService;
	private final EuMstierListecmService mstierListecmService;
	private final EuMstierListebcService mstierListebcService;
	private final EuMembreService membreService;
	private final EuMembreMoraleService moraleService;

	@Autowired
	public OiController(EuOddService oddService, EuMstierListecmService mstierListecmService,
			EuMstierListebcService mstierListebcService, EuMembreMoraleService moraleService,
			EuMembreService membreService) {
		this.oddService = oddService;
		this.mstierListecmService = mstierListecmService;
		this.mstierListebcService = mstierListebcService;
		this.membreService = membreService;
		this.moraleService = moraleService;
	}

	@GetMapping(value = "/iv/index")
	public String indexOiInd() {
		return "oi/index";
	}

	@GetMapping(value = "/ms/index")
	public String indexOiMen() {
		return "oi/index";
	}

	@GetMapping(value = "/iv/tegc")
	public String afficheTegc() {
		return "oi/tegc";
	}

	@GetMapping(value = "/iv/fiche")
	public String addFicheBc(@ModelAttribute("fiche") EuMstierListebc mstierListebc, ModelMap model) {
		mstierListebc.setPeripherique(0);
		mstierListebc.setConnectivite(0);
		mstierListebc.setAssurance(0);
		String codeMembre = getCodeMembreUser();
		String nom = "";
		String prenom = "";
		if (codeMembre.endsWith("P")) {
			String[] result = membreService.getMembreInfo(getCodeMembreUser());
			nom = result[0];
			prenom = result[1];
		} else {
			nom = moraleService.findMembreInfo(codeMembre);
		}
		model.addAttribute("nom", nom);
		model.addAttribute("prenom", prenom);
		model.addAttribute("odds", oddService.listAll());
		return "oi/fichebc";
	}

	@PostMapping(value = "/iv/fiche")
	public ResponseEntity<?> addFicheOddBc(@RequestBody EuMstierListebc mstierListebc, ModelMap model) {
		if (!mstierListebc.getCodeMembreBenef().isBlank() && mstierListebc.getIdOdd() != null) {
			List<EuMstierListebc> results = mstierListebcService.findByBenefAndOdd(mstierListebc.getCodeMembreBenef(),
					mstierListebc.getIdOdd());
			if (results.isEmpty()) {
				String code = RandomStringUtils.randomAlphanumeric(8);
				mstierListebc.setCodeFicheOdd(code);
				mstierListebc.setUtilisateur(getCurrentUser().getIdUtilisateur());
				mstierListebcService.add(mstierListebc);
				return ResponseEntity.ok(new Reponse("Fiche ODD bien enregistrée; Code = " + code));
			} else {
				return ResponseEntity.badRequest().body(new Reponse("Vous êtes sur une fiche ODD pour ce ODD"));
			}
		} else {
			return ResponseEntity.badRequest()
					.body(new Reponse("Veuillez fournir au moins votre Code Membre et l'ODD"));
		}
	}

	@GetMapping(value = "/iv/bcri")
	public String afficheBc(ModelMap model) {
		return "oi/bcri";
	}

	@GetMapping(value = "/iv/listeodd")
	public @ResponseBody JqGrid<EuMstierListecm> listeOdd(
			@RequestParam(value = "codeMembre", required = false) String codeMembre,
			@RequestParam(value = "_search", required = false) Boolean search,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord) {
		Sort sort = null;
		String orderBy = sidx;
		if (orderBy != null && sord != null) {
			if (sord.equals("desc")) {
				sort = Sort.by(Sort.Direction.DESC, orderBy);
			} else {
				sort = Sort.by(Sort.Direction.ASC, orderBy);
			}
		}
		// Constructs page request for current page
		// Note: page number for Spring Data JPA starts with 0, while jqGrid
		// starts with 1
		if (page == null || page == 0) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		PageRequest pageRequest = null;
		if (sort != null) {
			pageRequest = PageRequest.of(page - 1, rows, sort);
		} else {
			pageRequest = PageRequest.of(page - 1, rows);
		}

		JqGrid<EuMstierListecm> response = new JqGrid<>();
		Page<EuMstierListecm> listecm = null;
		if (StringUtils.isNotBlank(codeMembre)) {
			listecm = mstierListecmService.findByMembreBeneficiaire(codeMembre, pageRequest);
		} else {
			listecm = mstierListecmService.listAll(pageRequest);
		}

		if (Objects.nonNull(listecm) && listecm.getTotalElements() > 0) {
			List<EuMstierListecm> creances = Lists.newArrayList(listecm.iterator());
			response.setRows(creances);
			response.setRecords(Long.toString(listecm.getTotalElements()));
			response.setTotal(Integer.toString(listecm.getTotalPages()));
			response.setPage(Integer.toString(listecm.getNumber() + 1));
			return response;
		} else {
			response.setRows(Lists.newArrayList());
			response.setRecords("0");
			response.setTotal("0");
			response.setPage("0");
			return response;
		}
	}
}
package com.esmc.mcnp.web.controller.odd;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esmc.mcnp.model.cm.EuReligion;
import com.esmc.mcnp.model.odd.EuMstierListecm;
import com.esmc.mcnp.model.odd.EuOdd;
import com.esmc.mcnp.model.org.EuCanton;
import com.esmc.mcnp.model.org.EuPays;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.services.common.EuReligionService;
import com.esmc.mcnp.services.odd.EuAgenceOddService;
import com.esmc.mcnp.services.odd.EuOddService;
import com.esmc.mcnp.services.oi.EuMstierListecmService;
import com.esmc.mcnp.services.org.EuCantonService;
import com.esmc.mcnp.services.org.EuPaysService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.model.grid.Grid;
import com.esmc.mcnp.util.JqGrid;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/odd")
public class OddController extends BaseController {

	private EuOddService oddService;
	private EuAgenceOddService agenceOddService;
	private EuMstierListecmService mstierListecmService;
	private EuCantonService cantonService;
	private EuReligionService religionService;
	private EuPaysService paysService;

	@Autowired
	public OddController(EuOddService oddService, EuAgenceOddService agenceOddService,
			EuMstierListecmService mstierListecmService, EuCantonService cantonService,
			EuReligionService religionService, EuPaysService paysService) {
		this.oddService = oddService;
		this.agenceOddService = agenceOddService;
		this.mstierListecmService = mstierListecmService;
		this.cantonService = cantonService;
		this.religionService = religionService;
		this.paysService = paysService;
	}

	@GetMapping(value = "index")
	public String indexodd() {
		return "odd/index";
	}

	@GetMapping(value = "listeGen")
	public String listeGenOdd(ModelMap model) {
		model.addAttribute("odds", oddService.listAll());
		model.addAttribute("agences", agenceOddService.listAll());
		return "odd/liste";
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
		return "odd/fiche";
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

	@GetMapping(value = "listeodd")
	public @ResponseBody JqGrid<EuMstierListecm> listeOdd(
			@RequestParam(value = "idAgencesOdd", required = false) Integer idAgence,
			@RequestParam(value = "idOdd", required = false) Integer idOdd,
			@RequestParam(value = "deb", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate deb,
			@RequestParam(value = "fin", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate fin,
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
		if (idAgence != null && idOdd != null && deb != null && fin != null) {
			listecm = mstierListecmService.findByAgenceAndOddAndDate(idAgence, idOdd, deb.atStartOfDay(),
					fin.atStartOfDay(), pageRequest);
		} else if (idAgence != null && idOdd != null && deb == null && fin == null) {
			listecm = mstierListecmService.findByAgenceAndOdd(idAgence, idOdd, pageRequest);
		} else if (idAgence == null && idOdd == null && deb != null && fin != null) {
			listecm = mstierListecmService.findByDate(deb.atStartOfDay(), fin.atStartOfDay(), pageRequest);
		} else if (idAgence != null && idOdd == null && deb != null && fin != null) {
			listecm = mstierListecmService.findByAgenceOddAndDate(idAgence, deb.atStartOfDay(), fin.atStartOfDay(),
					pageRequest);
		} else if (idAgence == null && idOdd != null && deb != null && fin != null) {
			listecm = mstierListecmService.findByOddAndDate(idOdd, deb.atStartOfDay(), fin.atStartOfDay(), pageRequest);
		} else if (idAgence != null && idOdd == null && deb == null && fin == null) {
			listecm = mstierListecmService.findByAgenceOdd(idAgence, pageRequest);
		} else if (idAgence == null && idOdd != null && deb == null && fin == null) {
			listecm = mstierListecmService.findByOdd(idOdd, pageRequest);
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

	@GetMapping(value = "malisteodd")
	public @ResponseBody JqGrid<EuMstierListecm> listeMstiercm(
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
		if (page == null) {
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

		EuUtilisateur utilisateur = getCurrentUser();
		JqGrid<EuMstierListecm> response = new JqGrid<>();
		if (Objects.nonNull(utilisateur)) {

			Page<EuMstierListecm> listecm = mstierListecmService.findByUser(utilisateur.getIdUtilisateur(),
					pageRequest);
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
		} else {
			response.setRows(Lists.newArrayList());
			response.setRecords("0");
			response.setTotal("0");
			response.setPage("0");
			return response;
		}
	}

	@GetMapping(value = "liste")
	public @ResponseBody Grid<EuMstierListecm> listeOdd(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sort", required = false) String sidx,
			@RequestParam(value = "order", required = false) String sord) {
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
		if (page == null) {
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

		EuUtilisateur utilisateur = getCurrentUser();
		Grid<EuMstierListecm> response = new Grid<>();
		if (Objects.nonNull(utilisateur)) {

			Page<EuMstierListecm> listecm = mstierListecmService.findByUser(utilisateur.getIdUtilisateur(),
					pageRequest);
			if (Objects.nonNull(listecm) && listecm.getTotalElements() > 0) {
				List<EuMstierListecm> creances = Lists.newArrayList(listecm.iterator());
				response.setRows(creances);
				response.setTotal(Integer.toString(listecm.getTotalPages()));
				return response;
			} else {
				response.setRows(Lists.newArrayList());
				response.setTotal("0");
				return response;
			}
		} else {
			response.setRows(Lists.newArrayList());
			response.setTotal("0");
			return response;
		}
	}
}

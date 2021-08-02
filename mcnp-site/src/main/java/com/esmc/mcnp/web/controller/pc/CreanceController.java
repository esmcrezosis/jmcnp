package com.esmc.mcnp.web.controller.pc;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esmc.mcnp.domain.UserSecurity;
import com.esmc.mcnp.domain.dto.desendettement.OrdrePayement;
import com.esmc.mcnp.domain.entity.others.EuObjet;
import com.esmc.mcnp.domain.entity.pc.EuCreance;
import com.esmc.mcnp.domain.entity.pc.EuOrdrePayement;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.infrastructure.services.pc.EuCreanceService;
import com.esmc.mcnp.infrastructure.services.pc.EuObjetService;
import com.esmc.mcnp.infrastructure.services.pc.EuOrdrePayementService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.util.JqGrid;
import com.google.common.collect.Lists;

@Controller
public class CreanceController extends BaseController {

	protected @Autowired EuCreanceService creanceService;
	protected @Autowired EuObjetService objetService;
	protected @Autowired EuOrdrePayementService ordreService;

	@ModelAttribute("creance")
	public EuCreance getCreance() {
		return new EuCreance();
	}

	@RequestMapping(value = "/icreance")
	public String icreance() {
		return "creance/index";
	}

	@RequestMapping(value = "/addCreance", method = RequestMethod.GET)
	public String addCreance() {
		return "creance/add";
	}

	@RequestMapping(value = "/addCreance", method = RequestMethod.POST)
	public String doAddCreance(EuCreance creance) {
		System.out.println("In the Add Controller");
		if (Objects.nonNull(creance)) {
			creanceService.add(creance);
			return "creance/list";
		} else {
			System.out.println("Echec de l'operation");
			return "creance/add";
		}
	}

	@RequestMapping(value = "/ordre")
	public String addOrdre(@ModelAttribute("ordre") OrdrePayement ordre) {
		return "creance/ordre";
	}

	@RequestMapping(value = "/creances")
	public String listCreances() {
		return "creance/list";
	}

	@RequestMapping(value = "/dettes")
	public String listDettes() {
		return "creance/dettes";
	}

	@RequestMapping(value = "/ordres")
	public String listOrdres() {
		return "creance/listOrdre";
	}

	@RequestMapping(value = "/addbps", method = RequestMethod.GET)
	public String addBps(Model model) {
		EuUtilisateur utilisateur = (UserSecurity) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (Objects.nonNull(utilisateur)) {

			EuObjet objet = new EuObjet();
			model.addAttribute("objet", objet);
			List<EuCreance> dettes = creanceService.findByDebiteur(utilisateur.getCodeMembre());
			model.addAttribute("dettes", dettes);
			return "creance/objet";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/addbps", method = RequestMethod.POST)
	public String doAddBps(@ModelAttribute EuObjet objet, RedirectAttributes rmodel, Model model,
			BindingResult errors) {
		StringBuilder message = new StringBuilder();
		if (errors.hasErrors()) {
			errors.getAllErrors().forEach(e -> {
				message.append(e.getDefaultMessage());
				message.append("\n");
			});
			model.addAttribute("message", message);
			model.addAttribute("objet", objet);
			return "creance/objet";
		}

		return "";
	}

	@RequestMapping(value = "/transfertd", method = RequestMethod.POST)
	public String doAddOrdre(OrdrePayement ordre, ModelMap model) {
		if (Objects.nonNull(ordre)) {
			EuCreance creance = creanceService.findById(ordre.getIdCreance());
			if (Objects.nonNull(creance)) {
				EuOrdrePayement ordrePayement = new EuOrdrePayement();
				ordrePayement.setCodeMembreBenef(ordre.getCodeMembreBenef());
				ordrePayement.setCodeMembreCred(ordre.getCodeMembreCred());
				ordrePayement.setCodeMembreDeb(ordre.getCodeMembreDeb());
				ordrePayement.setDateOrdrePayement(ordre.getDateOrdrePayement());
				ordrePayement.setEuCreance(creance);
				ordreService.add(ordrePayement);

				creance.setEtatCreance("Transféré");
				creanceService.update(creance);
			} else {
				model.put("message", "La créance N° " + ordre.getIdCreance() + "n'existe pas");
				return null;
			}
		}
		return "creance/listOrdre";
	}

	@RequestMapping(value = "/listcreance", method = RequestMethod.GET)
	public @ResponseBody JqGrid<EuCreance> loadCreances(
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

		EuUtilisateur utilisateur = (UserSecurity) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		JqGrid<EuCreance> response = new JqGrid<>();
		if (Objects.nonNull(utilisateur)) {

			Page<EuCreance> pcreances = creanceService.findByCrediteur(utilisateur.getCodeMembre(), pageRequest);
			if (Objects.nonNull(pcreances) && pcreances.getTotalElements() > 0) {
				List<EuCreance> creances = Lists.newArrayList(pcreances.iterator());
				response.setRows(creances);
				response.setRecords(Long.toString(pcreances.getTotalElements()));
				response.setTotal(Integer.toString(pcreances.getTotalPages()));
				response.setPage(Integer.toString(pcreances.getNumber() + 1));
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

	@RequestMapping(value = "/listdette", method = RequestMethod.GET)
	public @ResponseBody JqGrid<EuCreance> loadDettes(@RequestParam(value = "_search", required = false) Boolean search,
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

		EuUtilisateur utilisateur = (UserSecurity) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		JqGrid<EuCreance> response = new JqGrid<>();
		if (Objects.nonNull(utilisateur)) {

			Page<EuCreance> pcreances = creanceService.findByDebiteur(utilisateur.getCodeMembre(), pageRequest);
			if (Objects.nonNull(pcreances) && pcreances.getTotalElements() > 0) {
				List<EuCreance> dettes = Lists.newArrayList(pcreances.iterator());
				response.setRows(dettes);
				response.setRecords(Long.toString(pcreances.getTotalElements()));
				response.setTotal(Integer.toString(pcreances.getTotalPages()));
				response.setPage(Integer.toString(pcreances.getNumber() + 1));
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
}

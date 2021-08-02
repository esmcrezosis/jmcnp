package com.esmc.mcnp.web.controller.oksu;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esmc.mcnp.commons.util.StringUtils;
import com.esmc.mcnp.domain.entity.acteur.EuDepotVente;
import com.esmc.mcnp.domain.entity.cmfh.EuDetailMf107;
import com.esmc.mcnp.domain.entity.cmfh.EuDetailMf11000;
import com.esmc.mcnp.domain.entity.cmfh.EuMembreFondateur107;
import com.esmc.mcnp.domain.entity.cmfh.EuMembreFondateur11000;
import com.esmc.mcnp.domain.entity.cmfh.EuRepartitionMf107;
import com.esmc.mcnp.domain.entity.cmfh.EuRepartitionMf11000;
import com.esmc.mcnp.infrastructure.services.cm.EuAncienMembreService;
import com.esmc.mcnp.infrastructure.services.cmfh.EuCmfhService;
import com.esmc.mcnp.infrastructure.services.cmfh.EuDepotVenteService;
import com.esmc.mcnp.infrastructure.services.cmfh.EuDetailMf107Service;
import com.esmc.mcnp.infrastructure.services.cmfh.EuDetailMf11000Service;
import com.esmc.mcnp.infrastructure.services.cmfh.EuMembreFondateur107Service;
import com.esmc.mcnp.infrastructure.services.cmfh.EuMembreFondateur11000Service;
import com.esmc.mcnp.infrastructure.services.cmfh.EuRepartitionMf107Service;
import com.esmc.mcnp.infrastructure.services.cmfh.EuRepartitionMf11000Service;
import com.esmc.mcnp.infrastructure.services.cmfh.EuSouscriptionService;
import com.esmc.mcnp.util.JqGrid;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "cmfh")
public class CmfhController extends BaseController {
	private final EuDepotVenteService depotVteService;
	private final EuCmfhService cmfhService;
	private final EuSouscriptionService souscriptionService;
	private final EuMembreFondateur107Service mf107Service;
	private final EuRepartitionMf107Service repMf107Service;
	private final EuMembreFondateur11000Service mf11000Service;
	private final EuRepartitionMf11000Service repMf11000Service;
	private final EuDetailMf107Service detailMf107Service;
	private final EuDetailMf11000Service detailMf11000Service;
	private final EuAncienMembreService ancMembreService;

	public CmfhController(EuDepotVenteService depotVteService, EuCmfhService cmfhService,
			EuSouscriptionService souscriptionService, EuMembreFondateur107Service mf107Service,
			EuRepartitionMf107Service repMf107Service, EuMembreFondateur11000Service mf11000Service,
			EuRepartitionMf11000Service repMf11000Service, EuDetailMf107Service detailMf107Service,
			EuDetailMf11000Service detailMf11000Service, EuAncienMembreService ancMembreService) {
		this.depotVteService = depotVteService;
		this.cmfhService = cmfhService;
		this.souscriptionService = souscriptionService;
		this.mf107Service = mf107Service;
		this.repMf107Service = repMf107Service;
		this.mf11000Service = mf11000Service;
		this.repMf11000Service = repMf11000Service;
		this.detailMf107Service = detailMf107Service;
		this.detailMf11000Service = detailMf11000Service;
		this.ancMembreService = ancMembreService;
	}

	@GetMapping(value = "mf107")
	public String afficheMf107(Model model) {
		String codeMembre = getCodeMembreUser();
		EuMembreFondateur107 mf107 = mf107Service.findByMembre(codeMembre);
		if (Objects.isNull(mf107)) {
			String ancCodeMembre = ancMembreService.getAncienCodeByMembre(codeMembre);
			mf107 = mf107Service.findByMembre(ancCodeMembre);
		}
		model.addAttribute("mf107", mf107);
		return "oksu/mf107";
	}

	@GetMapping(value = "mf11000")
	public String afficheMf11000(Model model) {
		String codeMembre = getCodeMembreUser();
		EuMembreFondateur11000 mf11000 = mf11000Service.findByMembre(codeMembre);
		if (Objects.isNull(mf11000)) {
			String ancCodeMembre = ancMembreService.getAncienCodeByMembre(codeMembre);
			mf11000 = mf11000Service.findByMembre(ancCodeMembre);
		}
		model.addAttribute("mf11000", mf11000);
		return "oksu/mf11000";
	}

	@GetMapping(value = "liste")
	public @ResponseBody JqGrid<EuDepotVente> listeDepot(
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

		JqGrid<EuDepotVente> response = new JqGrid<>();
		Page<EuDepotVente> depotVentes = null;
		if (StringUtils.isNotBlank(codeMembre)) {
			depotVentes = depotVteService.findByMembre(codeMembre, pageRequest);
		} else {
			depotVentes = depotVteService.listAll(pageRequest);
		}

		if (Objects.nonNull(depotVentes) && depotVentes.getTotalElements() > 0) {
			List<EuDepotVente> ventes = Lists.newArrayList(depotVentes.iterator());
			response.setRows(ventes);
			response.setRecords(Long.toString(depotVentes.getTotalElements()));
			response.setTotal(Integer.toString(depotVentes.getTotalPages()));
			response.setPage(Integer.toString(depotVentes.getNumber() + 1));
			return response;
		} else {
			response.setRows(Lists.newArrayList());
			response.setRecords("0");
			response.setTotal("0");
			response.setPage("0");
			return response;
		}
	}

	@GetMapping(value = "listeMf107")
	public @ResponseBody JqGrid<EuMembreFondateur107> listeMf107(
			@RequestParam(value = "codeMembre", required = false) String codeMembre,
			@RequestParam(value = "idMf107", required = false) String idMf107,
			@RequestParam(value = "nom", required = false) String nom,
			@RequestParam(value = "prenom", required = false) String prenom,
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

		JqGrid<EuMembreFondateur107> response = new JqGrid<>();
		Page<EuMembreFondateur107> mf107s = null;
		if (StringUtils.isNotBlank(idMf107)) {
			mf107s = mf107Service.findByNumident(idMf107, pageRequest);
		} else if (StringUtils.isNotBlank(codeMembre)) {
			mf107s = mf107Service.findByMembre(codeMembre, pageRequest);
		} else if (StringUtils.isNotBlank(nom)) {
			if (StringUtils.isNotBlank(prenom)) {
				mf107s = mf107Service.findByNomAndPrenom(nom, prenom, pageRequest);
			} else {
				mf107s = mf107Service.findByNom(prenom, pageRequest);
			}
		} else {
			mf107s = mf107Service.listAll(pageRequest);
		}

		if (Objects.nonNull(mf107s) && mf107s.getTotalElements() > 0) {
			List<EuMembreFondateur107> ventes = Lists.newArrayList(mf107s.iterator());
			response.setRows(ventes);
			response.setRecords(Long.toString(mf107s.getTotalElements()));
			response.setTotal(Integer.toString(mf107s.getTotalPages()));
			response.setPage(Integer.toString(mf107s.getNumber() + 1));
			return response;
		} else {
			response.setRows(Lists.newArrayList());
			response.setRecords("0");
			response.setTotal("0");
			response.setPage("0");
			return response;
		}
	}

	@GetMapping(value = "listeDetMf107")
	public @ResponseBody JqGrid<EuDetailMf107> listeMf107(
			@RequestParam(value = "numIdent", required = false) String numIdent,
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

		JqGrid<EuDetailMf107> response = new JqGrid<>();
		Page<EuDetailMf107> detailMf107s = null;
		if (StringUtils.isNotBlank(numIdent)) {
			detailMf107s = detailMf107Service.findByNumident(numIdent, pageRequest);
		} else if (StringUtils.isNotBlank(codeMembre)) {
			detailMf107s = detailMf107Service.findByCodeMembre(ancMembreService.getAncienCodeByMembre(codeMembre),
					pageRequest);
		} else {
			detailMf107s = detailMf107Service.listAll(pageRequest);
		}

		if (Objects.nonNull(detailMf107s) && detailMf107s.getTotalElements() > 0) {
			List<EuDetailMf107> ventes = Lists.newArrayList(detailMf107s.iterator());
			response.setRows(ventes);
			response.setRecords(Long.toString(detailMf107s.getTotalElements()));
			response.setTotal(Integer.toString(detailMf107s.getTotalPages()));
			response.setPage(Integer.toString(detailMf107s.getNumber() + 1));
			return response;
		} else {
			response.setRows(Lists.newArrayList());
			response.setRecords("0");
			response.setTotal("0");
			response.setPage("0");
			return response;
		}
	}

	@GetMapping(value = "listeRepMf107")
	public @ResponseBody JqGrid<EuRepartitionMf107> listeRepMf107(
			@RequestParam(value = "idMf107", required = false) Long idMf107,
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

		JqGrid<EuRepartitionMf107> response = new JqGrid<>();
		Page<EuRepartitionMf107> repartitionMf107s = null;
		if (idMf107 != null) {
			repartitionMf107s = repMf107Service.findByIdMf107(idMf107, pageRequest);
		} else if (StringUtils.isNotBlank(codeMembre)) {
			repartitionMf107s = repMf107Service.findByCodeMembre(ancMembreService.getAncienCodeByMembre(codeMembre),
					pageRequest);
		} else {
			repartitionMf107s = repMf107Service.listAll(pageRequest);
		}

		if (Objects.nonNull(repartitionMf107s) && repartitionMf107s.getTotalElements() > 0) {
			List<EuRepartitionMf107> ventes = Lists.newArrayList(repartitionMf107s.iterator());
			response.setRows(ventes);
			response.setRecords(Long.toString(repartitionMf107s.getTotalElements()));
			response.setTotal(Integer.toString(repartitionMf107s.getTotalPages()));
			response.setPage(Integer.toString(repartitionMf107s.getNumber() + 1));
			return response;
		} else {
			response.setRows(Lists.newArrayList());
			response.setRecords("0");
			response.setTotal("0");
			response.setPage("0");
			return response;
		}
	}

	@GetMapping(value = "listeMf11000")
	public @ResponseBody JqGrid<EuMembreFondateur11000> listeMf11000(
			@RequestParam(value = "codeMembre", required = false) String codeMembre,
			@RequestParam(value = "idMf11000", required = false) Long idMf11000,
			@RequestParam(value = "nom", required = false) String nom,
			@RequestParam(value = "prenom", required = false) String prenom,
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

		JqGrid<EuMembreFondateur11000> response = new JqGrid<>();
		Page<EuMembreFondateur11000> mf11000s = null;
		if (idMf11000 != null) {
			mf11000s = mf11000Service.findByNumBon(idMf11000, pageRequest);
		} else if (StringUtils.isNotBlank(codeMembre)) {
			mf11000s = mf11000Service.findByMembre(codeMembre, pageRequest);
		} else if (StringUtils.isNotBlank(nom)) {
			if (StringUtils.isNotBlank(prenom)) {
				mf11000s = mf11000Service.findByNomAndPrenom(nom, prenom, pageRequest);
			} else {
				mf11000s = mf11000Service.findByNom(prenom, pageRequest);
			}
		} else {
			mf11000s = mf11000Service.listAll(pageRequest);
		}

		if (Objects.nonNull(mf11000s) && mf11000s.getTotalElements() > 0) {
			List<EuMembreFondateur11000> ventes = Lists.newArrayList(mf11000s.iterator());
			response.setRows(ventes);
			response.setRecords(Long.toString(mf11000s.getTotalElements()));
			response.setTotal(Integer.toString(mf11000s.getTotalPages()));
			response.setPage(Integer.toString(mf11000s.getNumber() + 1));
			return response;
		} else {
			response.setRows(Lists.newArrayList());
			response.setRecords("0");
			response.setTotal("0");
			response.setPage("0");
			return response;
		}
	}

	@GetMapping(value = "listeDetMf11000")
	public @ResponseBody JqGrid<EuDetailMf11000> listeDetMf11000(
			@RequestParam(value = "codeMembre", required = false) String codeMembre,
			@RequestParam(value = "idMf11000", required = false) Long idMf11000,
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

		JqGrid<EuDetailMf11000> response = new JqGrid<>();
		Page<EuDetailMf11000> detailMf11000s = null;
		if (idMf11000 != null) {
			detailMf11000s = detailMf11000Service.findById(idMf11000, pageRequest);
		} else if (StringUtils.isNotBlank(codeMembre)) {
			detailMf11000s = detailMf11000Service.findByMembre(ancMembreService.getAncienCodeByMembre(codeMembre),
					pageRequest);
		} else {
			detailMf11000s = detailMf11000Service.listAll(pageRequest);
		}

		if (Objects.nonNull(detailMf11000s) && detailMf11000s.getTotalElements() > 0) {
			List<EuDetailMf11000> ventes = Lists.newArrayList(detailMf11000s.iterator());
			response.setRows(ventes);
			response.setRecords(Long.toString(detailMf11000s.getTotalElements()));
			response.setTotal(Integer.toString(detailMf11000s.getTotalPages()));
			response.setPage(Integer.toString(detailMf11000s.getNumber() + 1));
			return response;
		} else {
			response.setRows(Lists.newArrayList());
			response.setRecords("0");
			response.setTotal("0");
			response.setPage("0");
			return response;
		}
	}

	@GetMapping(value = "listeRepMf11000")
	public @ResponseBody JqGrid<EuRepartitionMf11000> listeRepMf11000(
			@RequestParam(value = "codeMembre", required = false) String codeMembre,
			@RequestParam(value = "idMf11000", required = false) Long idMf11000,
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

		JqGrid<EuRepartitionMf11000> response = new JqGrid<>();
		Page<EuRepartitionMf11000> repartitionMf11000s = null;
		if (idMf11000 != null) {
			repartitionMf11000s = repMf11000Service.findByIdMf11000(idMf11000, pageRequest);
		} else if (StringUtils.isNotBlank(codeMembre)) {
			repartitionMf11000s = repMf11000Service.findByCodeMembre(ancMembreService.getAncienCodeByMembre(codeMembre),
					pageRequest);
		} else {
			repartitionMf11000s = repMf11000Service.listAll(pageRequest);
		}

		if (Objects.nonNull(repartitionMf11000s) && repartitionMf11000s.getTotalElements() > 0) {
			List<EuRepartitionMf11000> ventes = Lists.newArrayList(repartitionMf11000s.iterator());
			response.setRows(ventes);
			response.setRecords(Long.toString(repartitionMf11000s.getTotalElements()));
			response.setTotal(Integer.toString(repartitionMf11000s.getTotalPages()));
			response.setPage(Integer.toString(repartitionMf11000s.getNumber() + 1));
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

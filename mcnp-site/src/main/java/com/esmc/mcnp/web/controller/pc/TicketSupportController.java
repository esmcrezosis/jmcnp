package com.esmc.mcnp.web.controller.pc;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esmc.mcnp.core.utils.StringUtils;
import com.esmc.mcnp.dto.desendettement.ReclamationDto;
import com.esmc.mcnp.mapper.pc.ReclamationMapper;
import com.esmc.mcnp.model.obpsd.Place;
import com.esmc.mcnp.model.pc.EuReclamation;
import com.esmc.mcnp.model.pc.TypePassif;
import com.esmc.mcnp.repositories.pc.EuReclamationAttachmentRepository;
import com.esmc.mcnp.services.cm.EuAncienMembreService;
import com.esmc.mcnp.services.cmfh.EuDetailMf107Service;
import com.esmc.mcnp.services.cmfh.EuDetailMf11000Service;
import com.esmc.mcnp.services.cmfh.EuMembreFondateur107Service;
import com.esmc.mcnp.services.cmfh.EuMembreFondateur11000Service;
import com.esmc.mcnp.services.cmfh.EuRepartitionMf107Service;
import com.esmc.mcnp.services.cmfh.EuRepartitionMf11000Service;
import com.esmc.mcnp.services.obpsd.PlaceService;
import com.esmc.mcnp.services.pc.EuCasPassifService;
import com.esmc.mcnp.services.pc.EuReclamationService;
import com.esmc.mcnp.util.JqGrid;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.dto.util.Result;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "reclamation")
public class TicketSupportController extends BaseController {
	private final EuReclamationService reclamationService;
	private final EuReclamationAttachmentRepository attachmentRepository;
	private final EuCasPassifService casPassifService;
	private final ReclamationMapper reclamationMapper;
	private final PlaceService placeService;
    private final EuMembreFondateur107Service mf107Service;
    private final EuRepartitionMf107Service repMf107Service;
    private final EuMembreFondateur11000Service mf11000Service;
    private final EuRepartitionMf11000Service repMf11000Service;
    private final EuDetailMf107Service detailMf107Service;
    private final EuDetailMf11000Service detailMf11000Service;
    private final EuAncienMembreService ancMembreService;

	public TicketSupportController(EuReclamationService reclamationService,
			EuReclamationAttachmentRepository attachmentRepository, EuCasPassifService casPassifService,
			ReclamationMapper reclamationMapper, PlaceService placeService, EuRepartitionMf11000Service repMf11000Service, EuRepartitionMf107Service repMf107Service, EuMembreFondateur11000Service mf11000Service, EuMembreFondateur107Service mf107Service, EuDetailMf11000Service detailMf11000Service, EuDetailMf107Service detailMf107Service, EuAncienMembreService ancMembreService) {
		this.reclamationService = reclamationService;
		this.attachmentRepository = attachmentRepository;
		this.casPassifService = casPassifService;
		this.reclamationMapper = reclamationMapper;
		this.placeService = placeService;
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
        return "desendettement/emf107s";
    }

    @GetMapping(value = "mf11000")
    public String afficheMf11000(Model model) {
        return "desendettement/emf11000s";
    }

	@GetMapping
	public String lister(Model model) {
		model.addAttribute("cas", casPassifService.listAll());
		return "desendettement/reclamations";
	}

	@GetMapping(value = "edit/{id}")
	public String editReclamation(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cas", casPassifService.listAll());
		model.addAttribute("reclamation", reclamationMapper.toDto(reclamationService.getById(id)));
		return "desendettement/reclamation";
	}
	
	@GetMapping(value = "place/{id}")
	public String editPalce(@PathVariable("id") Long id, Model model) {
		model.addAttribute("place", placeService.getById(id));
		return "desendettement/place";
	}

	@PostMapping(value = "edit")
	public ResponseEntity<?> editReclamation(ReclamationDto reclamationDto) {
		reclamationService.update(reclamationMapper.toEntity(reclamationDto));
		return ResponseEntity.ok(new Result(0, "Opération effectuée avec sucès"));
	}

	@GetMapping(value = "sousc")
	public String souscrireRed() {
		return "desendettement/places";
	}

	@GetMapping(value = "liste")
	@ResponseBody
	public JqGrid<ReclamationDto> loadReclamation(
			@RequestParam(value = "codeMembre", required = false) String codeMembre,
			@RequestParam(value = "typePassif", required = false) TypePassif typePassif,
			@RequestParam(value = "cas", required = false) Integer cas,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord) {

		Sort sort = null;
		String orderBy[] = sidx.split(",");
		if (orderBy.length > 0 && sord != null) {
			for (String o : orderBy) {
				if (o.trim().contains(" ")) {
					String[] ord = o.trim().split(" ");
					if (sort == null) {
						if (ord[1].equals("desc")) {
							sort = Sort.by(Sort.Direction.DESC, ord[0]);
						} else {
							sort = Sort.by(Sort.Direction.ASC, ord[0]);
						}
					} else {
						if (ord[1].equals("desc")) {
							sort.and(Sort.by(Sort.Direction.DESC, ord[0]));
						} else {
							sort.and(Sort.by(Sort.Direction.ASC, ord[0]));
						}
					}
				} else {
					if (sord.equals("desc")) {
						sort = Sort.by(Sort.Direction.DESC, o.trim());
					} else {
						sort = Sort.by(Sort.Direction.ASC, o.trim());
					}
				}
			}

		}
		// Constructs page request for current page
		// Note: page number for Spring Data JPA starts with 0, while jqGrid
		// starts with 1
		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 100;
		}
		PageRequest pageRequest = null;
		if (sort != null) {
			pageRequest = PageRequest.of(page - 1, rows, sort);
		} else {
			pageRequest = PageRequest.of(page - 1, rows);
		}

		Page<EuReclamation> capas = null;
		if (StringUtils.isNotBlank(codeMembre)) {
			if (typePassif != null) {
				capas = reclamationService.findByMembreAndTypePassif(codeMembre, typePassif, pageRequest);
			} else if (cas != null) {
				capas = reclamationService.findByMembreAndCasPassif(codeMembre, cas, pageRequest);
			} else {
				capas = reclamationService.findByCodeMembre(codeMembre, pageRequest);
			}
		} else {
			capas = reclamationService.listAll(pageRequest);
		}
		JqGrid<ReclamationDto> response = new JqGrid<>();

		if (Objects.nonNull(capas) && capas.getTotalElements() > 0) {
			response.setRows(reclamationMapper.toDtos(capas.getContent()));
			response.setTotal(Integer.toString(capas.getTotalPages()));
		} else {
			response.setRows(Lists.newArrayList());
			response.setTotal("0");
		}
		return response;
	}

	@GetMapping(value = "places")
	@ResponseBody
	public JqGrid<Place> loadPlace(@RequestParam(value = "codeMembre", required = false) String codeMembre,
			@RequestParam(value = "lib", required = false) String lib,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord) {

		Sort sort = null;
		String orderBy[] = sidx.split(",");
		if (orderBy.length > 0 && sord != null) {
			for (String o : orderBy) {
				if (o.trim().contains(" ")) {
					String[] ord = o.trim().split(" ");
					if (sort == null) {
						if (ord[1].equals("desc")) {
							sort = Sort.by(Sort.Direction.DESC, ord[0]);
						} else {
							sort = Sort.by(Sort.Direction.ASC, ord[0]);
						}
					} else {
						if (ord[1].equals("desc")) {
							sort.and(Sort.by(Sort.Direction.DESC, ord[0]));
						} else {
							sort.and(Sort.by(Sort.Direction.ASC, ord[0]));
						}
					}
				} else {
					if (sord.equals("desc")) {
						sort = Sort.by(Sort.Direction.DESC, o.trim());
					} else {
						sort = Sort.by(Sort.Direction.ASC, o.trim());
					}
				}
			}

		}
		// Constructs page request for current page
		// Note: page number for Spring Data JPA starts with 0, while jqGrid
		// starts with 1
		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 100;
		}
		PageRequest pageRequest = null;
		if (sort != null) {
			pageRequest = PageRequest.of(page - 1, rows, sort);
		} else {
			pageRequest = PageRequest.of(page - 1, rows);
		}

		Page<Place> capas = null;
		if (StringUtils.isNotBlank(codeMembre)) {
			if (StringUtils.isNotBlank(lib)) {
				capas = placeService.findByMembreAndLib(codeMembre, lib, pageRequest);
			} else {
				capas = placeService.findByMembre(codeMembre, pageRequest);
			}
		} else if (StringUtils.isNotBlank(lib)) {
			capas = placeService.findByLib(lib, pageRequest);
		} else {
			capas = placeService.listAll(pageRequest);
		}
		JqGrid<Place> response = new JqGrid<>();

		if (Objects.nonNull(capas) && capas.getTotalElements() > 0) {
			response.setRows(capas.getContent());
			response.setTotal(Integer.toString(capas.getTotalPages()));
		} else {
			response.setRows(Lists.newArrayList());
			response.setTotal("0");
		}
		return response;
	}
}

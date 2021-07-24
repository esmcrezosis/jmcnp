package com.esmc.mcnp.web.controller.obpsd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esmc.mcnp.dto.obps.TegcDto;
import com.esmc.mcnp.dto.pbf.TpagcpDto;
import com.esmc.mcnp.dto.pbf.TraiteDto;
import com.esmc.mcnp.mapper.obps.TeGcMapper;
import com.esmc.mcnp.mapper.pbf.TpagcpMapper;
import com.esmc.mcnp.mapper.pbf.TraiteMapper;
import com.esmc.mcnp.model.obps.EuTegc;
import com.esmc.mcnp.model.obpsd.EuBanque;
import com.esmc.mcnp.model.obpsd.EuDetailFgfn;
import com.esmc.mcnp.model.obpsd.EuFgfn;
import com.esmc.mcnp.model.obpsd.EuTpagcp;
import com.esmc.mcnp.model.obpsd.EuTraite;
import com.esmc.mcnp.repositories.obpsd.EuTraiteRepository;
import com.esmc.mcnp.services.acteurs.EuBanqueService;
import com.esmc.mcnp.services.obps.EuTegcService;
import com.esmc.mcnp.services.obpsd.EuDetailfgfnService;
import com.esmc.mcnp.services.obpsd.EuFgfnService;
import com.esmc.mcnp.services.obpsd.EuTpagcpService;
import com.esmc.mcnp.services.obpsd.EuTraiteService;
import com.esmc.mcnp.util.JqGrid;
import com.esmc.mcnp.util.PageWrapper;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.google.common.collect.Lists;

@Controller
public class FgfnController extends BaseController {

	private EuFgfnService fgfnService;
	private EuBanqueService bankService;
	private EuDetailfgfnService detFgfnService;
	private EuTpagcpService tpagcpService;
	private EuTraiteService traiteService;
	private EuTraiteRepository traiteRepository;
	private TraiteMapper traiteMapper;
	private TpagcpMapper tpagcpMapper;
	private final EuTegcService tegcService;
	private final TeGcMapper tegcMapper;

	@Inject
	public FgfnController(EuFgfnService fgfnService, EuBanqueService bankService, EuDetailfgfnService detFgfnService,
			EuTpagcpService tpagcpService, EuTraiteService traiteService, EuTraiteRepository traiteRepository,
			TraiteMapper traiteMapper, TpagcpMapper tpagcpMapper, EuTegcService tegcService, TeGcMapper tegcMapper) {
		this.fgfnService = fgfnService;
		this.bankService = bankService;
		this.detFgfnService = detFgfnService;
		this.tpagcpService = tpagcpService;
		this.traiteService = traiteService;
		this.traiteRepository = traiteRepository;
		this.traiteMapper = traiteMapper;
		this.tpagcpMapper = tpagcpMapper;
		this.tegcService = tegcService;
		this.tegcMapper = tegcMapper;
	}

	@ModelAttribute("banks")
	public List<EuBanque> getBanks() {
		return bankService.list();
	}

	@RequestMapping(value = "/fgfn", method = RequestMethod.GET)
	public String ifgfn() {
		return "fgfn/index";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listFgfn(Model model, Pageable page) {
		Page<EuFgfn> fgfns = fgfnService.findAll(page);
		PageWrapper<EuFgfn> fgfnsWrapper = new PageWrapper<>(fgfns, "/list");
		model.addAttribute("fgfns", fgfnsWrapper.getContent());
		model.addAttribute("page", fgfnsWrapper);
		return "fgfn/list";
	}

	@RequestMapping(value = "/listDet", method = RequestMethod.GET)
	public String listDetFgfn(Model model, @RequestParam(value = "code", required = false) String codeFgfn,
			Pageable pageable) {
		Page<EuDetailFgfn> fgfns;
		PageWrapper<EuDetailFgfn> fgfnsWrapper = null;
		if (StringUtils.isNotBlank(codeFgfn)) {
			fgfns = detFgfnService.findDetByFgfn(codeFgfn, pageable);
			fgfnsWrapper = new PageWrapper<>(fgfns, "/listDet?code=" + codeFgfn);
		} else {
			fgfns = detFgfnService.findAll(pageable);
			fgfnsWrapper = new PageWrapper<>(fgfns, "/listDet");
		}
		model.addAttribute("detfgfns", fgfnsWrapper.getContent());
		model.addAttribute("page", fgfnsWrapper);
		return "fgfn/listDetails";
	}

	@RequestMapping(value = "/majfgfn", method = RequestMethod.GET)
	public String majFgfn() {
		return "fgfn/maj";
	}

	@RequestMapping(value = "/tabOpiMat", method = RequestMethod.GET)
	public String listopimature() {
		return "fgfn/opimature";
	}

	@GetMapping(value = "/tabOpiEmis")
	public String listopiemis() {
		return "fgfn/opiemis";
	}

	@GetMapping(value = "/tpagcps")
	@ResponseBody
	public JqGrid<TpagcpDto> loadTpagcp(@RequestParam(value = "codeMembre", required = false) String codeMembre,
			@RequestParam(value = "deb", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate deb,
			@RequestParam(value = "fin", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fin,
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

		JqGrid<TpagcpDto> response = new JqGrid<>();
		Page<EuTpagcp> traitePage = null;
		if (StringUtils.isNotBlank(codeMembre)) {
			if (deb != null && fin != null) {
				traitePage = tpagcpService.findByMembreAndDate(codeMembre, deb, fin, pageRequest);
			} else if (deb != null) {
				traitePage = tpagcpService.findByMembreDateSup(codeMembre, deb, pageRequest);
			} else if (fin != null) {
				traitePage = tpagcpService.findByMembreDateInf(codeMembre, fin, pageRequest);
			} else {
				traitePage = tpagcpService.findAll(codeMembre, pageRequest);
			}
		} else if (deb != null && fin != null) {
			traitePage = tpagcpService.findByDateDebBetween(deb, fin, pageRequest);
		} else if (deb != null) {
			traitePage = tpagcpService.findByDateDebutSup(deb, pageRequest);
		} else if (fin != null) {
			traitePage = tpagcpService.findByDateDebInf(fin, pageRequest);
		} else {
			traitePage = tpagcpService.findAll(pageRequest);
		}
		if (Objects.nonNull(traitePage) && traitePage.getTotalElements() > 0) {
			response.setRows(tpagcpMapper.fromEuTpagcpList(traitePage.getContent()));
			response.setTotal(Integer.toString(traitePage.getTotalPages()));
		} else {
			response.setRows(Lists.newArrayList());
			response.setTotal("0");
		}
		return response;
	}

	@RequestMapping(value = "/traite", method = RequestMethod.GET)
	public String detailOpiemis(Model model, @RequestParam(value = "id", required = false) Long id, Pageable page) {
		if (id != null) {
			Page<EuTraite> traites = traiteService.findByTpagcp(id, PageRequest.of(0, 24));
			EuTpagcp tpa = tpagcpService.getById(id);
			PageWrapper<EuTraite> fgfnsWrapper = new PageWrapper<>(traites, "/traite?id=" + id);
			model.addAttribute("tpa", tpa);
			model.addAttribute("traites", fgfnsWrapper.getContent());
			model.addAttribute("page", fgfnsWrapper);
		}
		return "fgfn/traite";
	}

	@RequestMapping(value = "/opis")
	public String listOpis() {
		return "fgfn/opis";
	}

	@GetMapping(value = "listopi")
	@ResponseBody
	public JqGrid<TraiteDto> loadOpi(@RequestParam(value = "codeMembre", required = false) String codeMembre,
			@RequestParam(value = "tpagcp", required = false) Long idTpagcp,
			@RequestParam(value = "deb", required = false) String dateDeb,
			@RequestParam(value = "fin", required = false) String dateFin,
			@RequestParam(value = "echu", required = false) Integer echu,
			@RequestParam(value = "payer", required = false) Integer payer,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date deb = null;
		Date fin = null;
		if (StringUtils.isNotBlank(dateDeb)) {
			try {
				deb = format.parse(dateDeb);
			} catch (ParseException e) {
			}
		}
		if (StringUtils.isNotBlank(dateFin)) {
			try {
				fin = format.parse(dateFin);
			} catch (ParseException e) {
			}
		}
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

		JqGrid<TraiteDto> response = new JqGrid<>();
		Page<EuTraite> traitePage = null;
		if (idTpagcp != null) {
			traitePage = traiteRepository.findByTraiteTegcp(idTpagcp, pageRequest);
		} else if (StringUtils.isNotBlank(codeMembre)) {
			if (echu != null) {
				if (echu == 1) {
					if (payer != null) {
						if (payer == 1) {
							traitePage = traiteRepository.findByTpagcp_CodeMembreAndTraitePayer(codeMembre, payer,
									pageRequest);
						} else {
							traitePage = traiteRepository.findTraiteEchuNonPayeByMembre(codeMembre, new Date(),
									pageRequest);
						}
					}
				} else {
					traitePage = traiteRepository.findTraiteNonEchuByMembre(codeMembre, new Date(), pageRequest);
				}
			} else if (payer != null) {
				traitePage = traiteRepository.findTraitePayerByMembre(codeMembre, payer, pageRequest);
			} else {
				traitePage = traiteRepository.findByTpagcp_CodeMembre(codeMembre, pageRequest);
			}
		} else if (deb != null && fin != null) {
			if (echu != null) {
				if (echu == 1) {
					if (payer != null) {
						if (payer == 1) {
							traitePage = traiteRepository.findByTraitePayerAndTraiteDateDebutBetween(payer, deb, fin,
									pageRequest);
						} else {
							traitePage = traiteRepository.findTraiteEchuNonPayeByDate(deb, fin, new Date(),
									pageRequest);
						}
					} else {
						traitePage = traiteRepository.findTraiteEchuByDate(deb, fin, new Date(), pageRequest);
					}
				} else {
					traitePage = traiteRepository.findTraiteNonEchuByDate(deb, fin, new Date(), pageRequest);
				}
			} else if (payer != null) {
				traitePage = traiteRepository.findByTraitePayerAndTraiteDateDebutBetween(payer, deb, fin, pageRequest);
			} else {
				traitePage = traiteRepository.findByTraiteDateDebutBetween(deb, fin, pageRequest);
			}
		} else if (echu != null) {
			if (echu == 1) {
				if (payer != null) {
					traitePage = traiteRepository.getByTraitePayerAndTraiteDateFinLessThan(payer, new Date(),
							pageRequest);
				} else {
					traitePage = traiteRepository.findByTraiteDateFinLessThanEqual(new Date(), pageRequest);
				}
			} else {
				traitePage = traiteRepository.findByTraiteDateFinGreaterThan(new Date(), pageRequest);
			}
		} else if (payer != null) {
			traitePage = traiteRepository.getByTraitePayer(payer, pageRequest);
		} else {
			traitePage = traiteRepository.findAll(pageRequest);
		}
		if (Objects.nonNull(traitePage) && traitePage.getTotalElements() > 0) {
			response.setRows(traiteMapper.fromEuTraiteList(traitePage.getContent()));
			response.setTotal(Integer.toString(traitePage.getTotalPages()));
		} else {
			response.setRows(Lists.newArrayList());
			response.setTotal("0");
		}
		return response;
	}

	@GetMapping(value = "tegcs")
	@ResponseBody
	public JqGrid<TegcDto> loadTegcs(@RequestParam(value = "codeMembre", required = false) String codeMembre,
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
			rows = 20;
		}
		PageRequest pageRequest = null;
		if (sort != null) {
			pageRequest = PageRequest.of(page - 1, rows, sort);
		} else {
			pageRequest = PageRequest.of(page - 1, rows);
		}

		JqGrid<TegcDto> response = new JqGrid<>();
		Page<EuTegc> tegcs = null;
		if (StringUtils.isNotBlank(codeMembre)) {
			tegcs = tegcService.findByMembre(codeMembre, pageRequest);
		} else {
			tegcs = tegcService.listAll(pageRequest);
		}
		if (Objects.nonNull(tegcs) && tegcs.getTotalElements() > 0) {
			response.setRows(tegcMapper.toViews(tegcs.getContent()));
			response.setTotal(Integer.toString(tegcs.getTotalPages()));
		} else {
			response.setRows(Lists.newArrayList());
			response.setTotal("0");
		}
		return response;
	}

	@RequestMapping(value = "/majfgfn", method = RequestMethod.POST)
	public String domajFgfn() {
		return "fgfn/maj";
	}

	@RequestMapping(value = "/listdetfgfn", method = RequestMethod.GET)
	public String listDetFgfn() {
		return "fgfn/listDetails";
	}

	@RequestMapping(value = "/detfgfn", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<EuDetailFgfn> detFgfn(
			@RequestParam(value = "codeBanque", defaultValue = "") String codeBanque) {
		return detFgfnService.fetchByBanque(codeBanque);
	}

	@RequestMapping(value = "/listdat", method = RequestMethod.GET)
	public String listDat() {
		return "fgfn/dat";
	}
}

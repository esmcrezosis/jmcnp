package com.esmc.mcnp.web.controller.pc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.model.echange.DepotVente;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esmc.mcnp.UserSecurity;
import com.esmc.mcnp.model.acteur.EuDepotVente;
import com.esmc.mcnp.model.bc.EuProduit;
import com.esmc.mcnp.model.cm.EuAncienCompteCredit;
import com.esmc.mcnp.model.cm.EuAncienMembre;
import com.esmc.mcnp.model.cm.EuCategorieCompte;
import com.esmc.mcnp.model.cmfh.EuMembreFondateur107;
import com.esmc.mcnp.model.cmfh.EuMembreFondateur11000;
import com.esmc.mcnp.model.cmfh.EuRepartitionMf107;
import com.esmc.mcnp.model.cmfh.EuRepartitionMf11000;
import com.esmc.mcnp.model.obpsd.Place;
import com.esmc.mcnp.model.pc.EuReleve;
import com.esmc.mcnp.model.pc.EuRelevedetail;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.services.cm.EuAncienCompteCreditService;
import com.esmc.mcnp.services.cm.EuAncienCompteService;
import com.esmc.mcnp.services.cm.EuAncienMembreService;
import com.esmc.mcnp.services.cm.EuCategorieCompteService;
import com.esmc.mcnp.services.cm.EuProduitService;
import com.esmc.mcnp.services.cmfh.EuDepotVenteService;
import com.esmc.mcnp.services.cmfh.EuMembreFondateur107Service;
import com.esmc.mcnp.services.cmfh.EuMembreFondateur11000Service;
import com.esmc.mcnp.services.cmfh.EuRepartitionMf107Service;
import com.esmc.mcnp.services.cmfh.EuRepartitionMf11000Service;
import com.esmc.mcnp.services.obps.EuAncienGcpService;
import com.esmc.mcnp.services.obpsd.PlaceService;
import com.esmc.mcnp.services.old.MoraleService;
import com.esmc.mcnp.services.old.PhysiqueService;
import com.esmc.mcnp.services.pc.EuRecouvrementMcnpService;
import com.esmc.mcnp.services.pc.EuReleveService;
import com.esmc.mcnp.services.pc.EuRelevedetailService;
import com.esmc.mcnp.util.JqGrid;
import com.esmc.mcnp.util.Reponse;
import com.esmc.mcnp.web.dto.smcipn.RecouvrementDto;
import com.esmc.mcnp.web.dto.util.Result;
import com.esmc.mcnp.web.model.desendettement.CalcReleve;
import com.esmc.mcnp.web.model.desendettement.DetailReleve;
import com.esmc.mcnp.web.model.desendettement.Releve;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RecouvrementController extends BaseController {
	private final EuRecouvrementMcnpService recouvMcnpService;
	private final EuAncienMembreService ancMembreService;
	private final PhysiqueService physiqueService;
	private final MoraleService moraleService;
	private final EuCategorieCompteService categorieService;
	private final EuProduitService produitService;
	private final EuAncienCompteCreditService ancCompteCreditService;
	private final EuAncienCompteService ancienCompteService;
	private final EuAncienGcpService ancienGcpService;
	private final EuMembreFondateur11000Service mf11000Service;
	private final EuRepartitionMf11000Service repMf11000Service;
	private final EuRepartitionMf107Service repMf107Service;
	private final EuMembreFondateur107Service mf107Service;
	private final EuDepotVenteService depotVenteService;
	private final EuReleveService releveService;
	private final EuRelevedetailService relevedetailService;
	private final PlaceService placeService;

	@Inject
	public RecouvrementController(EuRecouvrementMcnpService recouvMcnpService, EuAncienMembreService ancMembreService,
			PhysiqueService physiqueService, MoraleService moraleService,
			EuAncienCompteCreditService ancCompteCreditService, EuAncienCompteService ancienCompteService,
			EuAncienGcpService ancienGcpService, EuMembreFondateur11000Service mf11000Service,
			EuRepartitionMf11000Service repMf11000Service, EuRepartitionMf107Service repMf107Service,
			EuMembreFondateur107Service mf107Service, EuReleveService releveService,
			EuRelevedetailService relevedetailService, PlaceService placeService, EuDepotVenteService depotVenteService,
			EuProduitService produitService, EuCategorieCompteService categorieService) {
		this.recouvMcnpService = recouvMcnpService;
		this.ancMembreService = ancMembreService;
		this.physiqueService = physiqueService;
		this.moraleService = moraleService;
		this.categorieService = categorieService;
		this.produitService = produitService;
		this.ancCompteCreditService = ancCompteCreditService;
		this.ancienCompteService = ancienCompteService;
		this.ancienGcpService = ancienGcpService;
		this.mf11000Service = mf11000Service;
		this.repMf11000Service = repMf11000Service;
		this.repMf107Service = repMf107Service;
		this.mf107Service = mf107Service;
		this.depotVenteService = depotVenteService;
		this.releveService = releveService;
		this.relevedetailService = relevedetailService;
		this.placeService = placeService;
	}

	@GetMapping(value = "comptes")
	public String listeCompte(Model model) {
		List<EuCategorieCompte> cats = categorieService.listAll();
		model.addAttribute("cats", cats);
		return "cm/comptes";
	}

	@GetMapping(value = "lcredits")
	public String listeCompteCredit(Model model) {
		List<EuProduit> produits = produitService.listAll();
		model.addAttribute("produits", produits);
		return "cm/compteCredits";
	}

	@GetMapping(value = "/releve")
	public String listeReleve() {
		return "desendettement/releves";
	}

	@RequestMapping(value = "/listerel", method = RequestMethod.GET)
	public @ResponseBody JqGrid<EuReleve> loadReleves(
			@RequestParam(value = "codeMembre", required = false) String codeMembre,
			@RequestParam(value = "ancCodeMembre", required = false) String ancCodeMembre,
			@RequestParam(value = "typeRessource", required = false) String typeRessource,
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
		if (page == null || page == 0) {
			page = 1;
		}
		if (rows == null || rows == 0) {
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
		JqGrid<EuReleve> response = new JqGrid<>();
		Page<EuReleve> preleves = null;
		if (StringUtils.isNotBlank(codeMembre)) {
			if (StringUtils.isNotBlank(typeRessource)) {
				preleves = new PageImpl<EuReleve>(
						Lists.newArrayList(releveService.findByMembreAndType(codeMembre, typeRessource)));
			} else {
				preleves = releveService.findByMembre(codeMembre, pageRequest);
			}
		} else if (StringUtils.isNotBlank(ancCodeMembre)) {
			if (StringUtils.isNotBlank(typeRessource)) {
				preleves = new PageImpl<EuReleve>(
						Lists.newArrayList(releveService.findByReleveMembreAndType(codeMembre, typeRessource)));
			} else {
				preleves = releveService.findByReleveMembre(ancCodeMembre, pageRequest);
			}
		} else if (StringUtils.isNotBlank(typeRessource)) {
			preleves = releveService.findByReleveType(typeRessource, pageRequest);
		} else {
			preleves = releveService.listAll(pageRequest);
		}
		if (Objects.nonNull(preleves) && preleves.getTotalElements() > 0) {
			List<EuReleve> releves = Lists.newArrayList(preleves.iterator());
			response.setRows(releves);
			response.setRecords(Long.toString(preleves.getTotalElements()));
			response.setTotal(Integer.toString(preleves.getTotalPages()));
			response.setPage(Integer.toString(preleves.getNumber() + 1));
		} else {
			response.setRows(Lists.newArrayList());
			response.setRecords("0");
			response.setTotal("0");
			response.setPage("0");
		}
		return response;
	}

	@GetMapping(value = "/releve/edit/{id}")
	public String editReleve(@PathVariable("id") Integer id, Model model) {
		if (id != null) {
			model.addAttribute("releve", releveService.getById(id));
		} else {
			model.addAttribute("releve", new EuReleve());
		}
		return "desendettement/releve";
	}

	@RequestMapping(value = "/listereldet", method = RequestMethod.GET)
	public @ResponseBody JqGrid<EuRelevedetail> loadDetailReleves(
			@RequestParam(value = "idReleve", required = false) Integer idReleve,
			@RequestParam(value = "codeProduit", required = false) String codeProduit,
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
		if (page == null || page == 0) {
			page = 1;
		}
		if (rows == null || rows == 0) {
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
		JqGrid<EuRelevedetail> response = new JqGrid<>();
		Page<EuRelevedetail> preleves = null;
		if (idReleve != null && StringUtils.isNotBlank(codeProduit)) {
			preleves = relevedetailService.findByReleveAndProduit(idReleve, codeProduit, pageRequest);
		} else if (idReleve != null) {
			preleves = relevedetailService.findByReleve(idReleve, pageRequest);
		} else if (StringUtils.isNotBlank(codeProduit)) {
			preleves = relevedetailService.findByProduit(codeProduit, pageRequest);
		}
		if (Objects.nonNull(preleves) && preleves.getTotalElements() > 0) {
			List<EuRelevedetail> releves = Lists.newArrayList(preleves.iterator());
			response.setRows(releves);
			response.setRecords(Long.toString(preleves.getTotalElements()));
			response.setTotal(Integer.toString(preleves.getTotalPages()));
			response.setPage(Integer.toString(preleves.getNumber() + 1));
		} else {
			response.setRows(Lists.newArrayList());
			response.setRecords("0");
			response.setTotal("0");
			response.setPage("0");
		}
		return response;
	}

	@Transactional(rollbackFor = Exception.class)
	@PostMapping(value = "/releve/detail/save")
	public ResponseEntity<?> correctDetailReleve(DetailReleve detailReleve) {
		EuRelevedetail relevedetail = relevedetailService.getById(detailReleve.getRelevedetailId());
		if (relevedetail.getRelevedetailMontant() != detailReleve.getRelevedetailMontant()) {
			EuReleve releve = releveService.getById(detailReleve.getRelevedetailReleve());
			if (StringUtils.isNotBlank(releve.getReleveMembre())) {
				try {
					if (releve.getReleveMembre().length() <= 5) {
						if (detailReleve.getRelevedetailProduit().equalsIgnoreCase("MF11000")) {
							EuRepartitionMf11000 repMf11000 = repMf11000Service
									.getById(Long.valueOf(detailReleve.getRelevedetailCredit()));
							EuMembreFondateur11000 mf11000 = mf11000Service.getById(repMf11000.getIdMf11000());
							mf11000.setSolde(detailReleve.getRelevedetailMontant());
							mf11000Service.update(mf11000);
							List<EuRepartitionMf11000> repMf11000s = repMf11000Service
									.findByIdMf11000(mf11000.getNumBon());
							List<EuRepartitionMf11000> urepMf11000s = new ArrayList<>();
							repMf11000s.forEach(r -> {
								r.setMontRep(detailReleve.getRelevedetailMontant());
								r.setSoldeRep(r.getMontRep() - r.getMontReglt());
								urepMf11000s.add(r);
							});
							repMf11000Service.updateInBatch(urepMf11000s);
						} else if (detailReleve.getRelevedetailProduit().equalsIgnoreCase("MF107")) {
							EuRepartitionMf107 repMf107 = repMf107Service
									.getById(Long.valueOf(detailReleve.getRelevedetailCredit()));
							EuMembreFondateur107 mf107 = mf107Service.getById(String.valueOf(repMf107.getIdMf107()));
							mf107.setSolde(detailReleve.getRelevedetailMontant());
							mf107Service.update(mf107);
							List<EuRepartitionMf107> repMf107s = repMf107Service
									.findByIdMf107(Long.valueOf(mf107.getNumident()));
							List<EuRepartitionMf107> urepMf107s = new ArrayList<>();
							repMf107s.forEach(r -> {
								r.setMontRep(detailReleve.getRelevedetailMontant());
								r.setSoldeRep(r.getMontRep() - r.getMontReglt());
								urepMf107s.add(r);
							});
							repMf107Service.updateInBatch(urepMf107s);
						}
					} else if (releve.getReleveMembre().length() == 13) {
						Place place = placeService.getById((long) detailReleve.getRelevedetailCredit());
						place.setMontant(String.valueOf(detailReleve.getRelevedetailMontant()));
						placeService.update(place);
					} else if (releve.getReleveMembre().length() == 20) {
						EuAncienCompteCredit acc = ancCompteCreditService
								.getById(Long.valueOf(detailReleve.getRelevedetailCredit()));
						Double oldMtPlace = acc.getMontantPlace();
						acc.setMontantPlace(detailReleve.getRelevedetailMontant());
						if (acc.getCodeProduit().endsWith("nr")) {
							var oldBc = oldMtPlace * 8 / 5.6;
							var newBc = acc.getMontantPlace() * 8 / 5.6;
							if (newBc > oldBc) {
								acc.setMontantCredit(acc.getMontantCredit() + (newBc - oldBc));
							}
						}
						ancCompteCreditService.update(acc);
					}
					return ResponseEntity.ok(new Result(0, "Operation bien effectuée"));
				} catch (Exception e) {
					log.error("Erreur :", e);
					return ResponseEntity.ok(new Result(1, "Echec de l'operation : " + e.getMessage()));
				}
			}
		}
		return ResponseEntity.ok(new Result(1, "Echec de l'operation"));
	}

	@Transactional(rollbackFor = Exception.class)
	@PostMapping(value = "/releve/valider")
	public ResponseEntity<?> validerReleve(Releve releve) {
		try {
			EuReleve euReleve = releveService.getById(releve.getIdReleve());
			euReleve.setPublier(1);
			euReleve.setTraiter(true);
			euReleve.setNewCodeMembre(releve.getNewCodeMembre());
			releveService.update(euReleve);
			List<EuRelevedetail> details = relevedetailService.findByReleve(releve.getIdReleve());
			List<EuRelevedetail> udetails = new ArrayList<>();
			if (details.size() > 0) {
				details.forEach(d -> {
					d.setPublier(1);
					udetails.add(d);
				});
				relevedetailService.updateInBatch(udetails);
			}
			return ResponseEntity.ok(new Result(0, "Operation bien effectuée"));
		} catch (Exception e) {
			return ResponseEntity.ok(new Result(1, "Echec de l'operation : " + e.getMessage()));
		}
	}

	@GetMapping(value = "/releve/newCode")
	public ResponseEntity<?> getNewCodeMembre(String oldCodeMembre) {
		if (StringUtils.isNotBlank(oldCodeMembre)) {
			if (oldCodeMembre.length() == 13) {
				if (oldCodeMembre.endsWith("P")) {
					return ResponseEntity.ok(new Reponse(physiqueService.getById(oldCodeMembre).getCodeMembre()));
				} else {
					return ResponseEntity.ok(new Reponse(moraleService.getById(oldCodeMembre).getCodeMembre()));
				}
			} else if (oldCodeMembre.length() == 20) {
				return ResponseEntity.ok(new Reponse(ancMembreService.findById(oldCodeMembre).getCodeMembre()));
			}
		}
		return ResponseEntity.badRequest().body(new Reponse("Cet ancien membre n'a pas encore reactivé son compte"));
	}

	@GetMapping(value = "/releve/montrec")
	public ResponseEntity<?> calcMontRecouvrer(Integer idReleve) {
		if (idReleve != null) {
			return ResponseEntity.ok(releveService.verifierRelever(idReleve));
		}
		return ResponseEntity.badRequest().body(new Reponse("Vous devez fournir l' ID du relevé"));
	}

	@GetMapping(value = "/releve/relrec")
	public ResponseEntity<?> calcRecouvrer(Integer idReleve) {
		if (idReleve != null) {
			EuReleve releve = releveService.getById(idReleve);
			Double montRec = releveService.verifierRelever(releve);
			CalcReleve calcReleve = new CalcReleve();
			calcReleve.setIdReleve(releve.getReleveId()).setCodeMembre(releve.getReleveMembre()).setMontantRec(montRec)
					.setTypeRessource(releve.getReleveType());
			if (releve.getNewCodeMembre() != null) {
				calcReleve.setNewCodeMembre(releve.getNewCodeMembre());
			} else {
				String newCodeMembre = StringUtils.EMPTY;
				if (releve.getReleveMembre().length() == 13) {
					if (releve.getReleveMembre().endsWith("P")) {
						newCodeMembre = physiqueService.getById(releve.getReleveMembre()).getCodeMembre();
					} else {
						newCodeMembre = moraleService.getById(releve.getReleveMembre()).getCodeMembre();
					}
				} else if (releve.getReleveMembre().length() == 20) {
					newCodeMembre = ancMembreService.findById(releve.getReleveMembre()).getCodeMembre();
				}
				calcReleve.setNewCodeMembre(newCodeMembre);
			}
			return ResponseEntity.ok(calcReleve);
		}
		return ResponseEntity.badRequest().body(new Reponse("Vous devez fournir l' ID du relevé"));
	}

	@RequestMapping(value = "/ancNomMembre", method = RequestMethod.GET)
	public ResponseEntity<Reponse> loadNomMembre(@RequestParam("codeMembre") String codeMembre,
			@RequestParam("type") String type, @RequestParam("ressource") String ressource) {
		if (StringUtils.isNotBlank(codeMembre) && StringUtils.isNotBlank(type)) {
			if (StringUtils.isNotBlank(ressource) && ressource.startsWith("MF")) {
				if ("MF107".equalsIgnoreCase(ressource)) {
					EuAncienMembre membre = ancMembreService.findById(codeMembre);
					if (Objects.nonNull(membre)) {
						return new ResponseEntity<>(new Reponse(membre.getNomMembre() + " " + membre.getPrenomMembre()),
								HttpStatus.OK);
					}
				} else {
					EuMembreFondateur11000 mf11000 = mf11000Service.findByMembre(codeMembre);
					if (Objects.nonNull(mf11000)) {
						return new ResponseEntity<>(new Reponse(mf11000.getNom() + " " + mf11000.getPrenom()),
								HttpStatus.OK);
					}
				}
			} else {
				if ("MCNP".equalsIgnoreCase(type)) {
					EuAncienMembre membre = ancMembreService.findById(codeMembre);
					if (Objects.nonNull(membre)) {
						return new ResponseEntity<>(new Reponse(membre.getNomMembre() + " " + membre.getPrenomMembre()),
								HttpStatus.OK);
					}
				} else {

				}
			}
		}
		return new ResponseEntity<>(new Reponse(""), HttpStatus.OK);
	}

	@RequestMapping(value = "/payercmfh", method = RequestMethod.GET)
	public String recouvrerCmfh(@ModelAttribute("recouvrementDto") RecouvrementDto recouvrementDto, Model model) {
		return "desendettement/cmfh";
	}

	@RequestMapping(value = "/payercmfh", method = RequestMethod.POST)
	public ResponseEntity<?> recouvrerCmfh(@RequestBody DepotVente depotVente) {
		if (StringUtils.isNotBlank(depotVente.getCodeMembre())) {
			List<EuDepotVente> depots = depotVenteService.listAllByIds(Arrays.asList(depotVente.getIds()));
			if (!depots.isEmpty()) {
				List<EuDepotVente> deps = depots.stream()
						.filter(d -> d.getCodeMembre().equalsIgnoreCase(depotVente.getCodeMembre()) && !d.getPayer())
						.collect(Collectors.toList());
				if (!deps.isEmpty()) {
					Double montant = deps.stream().mapToDouble(EuDepotVente::getMontDepot).sum();
					TransactionStatus status = getTransactionStatus();
					try {
						if (depotVenteService.recouvrerCmfh(deps, montant)) {
							getTransactionManager().commit(status);
							return ResponseEntity.ok(new Result(0, "Opération bien effectuée"));
						}
					} catch (Exception e) {
						getTransactionManager().rollback(status);
						getLog().error("Echec de l'opération", e);
						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								.body(new Result(1, "Echec de l'operation : " + e.getMessage()));
					}
				} else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new Result(1, "Echec de l'operation : Les lignes selectionnées sont déjà traitées"));
				}
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new Result(1, "Echec de l'operation : Veuillez selectionner les lignes à traiter"));
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Result(1, "Echec de l'operation : Veuillez fournir votre numéro Membre ESMC"));
	}

	@RequestMapping(value = "/recouvrer", method = RequestMethod.GET)
	public String recouvrerMcnp(@ModelAttribute("recouvrementDto") RecouvrementDto recouvrementDto, Model model) {
		return "creance/recouvrement";
	}

	@RequestMapping(value = "/recouvrer", method = RequestMethod.POST)
	public String recouvrerMcnp(@ModelAttribute("recouvrementDto") RecouvrementDto recouvrement, Model model,
			RedirectAttributes rmodel) {
		if (StringUtils.isNotBlank(recouvrement.getNewCodeMembre())
				&& StringUtils.isNotBlank(recouvrement.getTypeRessource())) {
			try {
				var result = recouvMcnpService.recouvrerMcnp(recouvrement.getCodeMembre(),
						recouvrement.getNewCodeMembre(), recouvrement.getTypeRessource());
				rmodel.addFlashAttribute("message", result);
				rmodel.addFlashAttribute("recouvrementDto", recouvrement);
				return "redirect:/recouvrer";
			} catch (Exception e) {
				log.error("", e);
				model.addAttribute("recouvrementDto", recouvrement);
				model.addAttribute("message", "Erreur : " + e.getMessage());
				return "creance/recouvrement";
			}
		}
		model.addAttribute("recouvrementDto", recouvrement);
		model.addAttribute("message", "Echec de l'opération : Les infos fournies sont incomplètes!!!");
		return "creance/recouvrement";
	}

	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = "/rembourser", method = RequestMethod.POST)
	public ResponseEntity<?> rembourserMcnp(RecouvrementDto recouvrement) {
		try {
			if (StringUtils.isNotBlank(recouvrement.getNewCodeMembre())) {
				var result = StringUtils.EMPTY;
				if (recouvrement.getIdReleve() != null) {
					EuReleve releve = releveService.getById(recouvrement.getIdReleve());
					if (StringUtils.isBlank(releve.getNewCodeMembre())) {
						releve.setNewCodeMembre(recouvrement.getNewCodeMembre());
					}
					result = recouvMcnpService.recouvrerMcnp(releve);
				} else if (StringUtils.isNotBlank(recouvrement.getNewCodeMembre())
						&& StringUtils.isNotBlank(recouvrement.getTypeRessource())) {
					result = recouvMcnpService.recouvrerMcnp(recouvrement.getCodeMembre(),
							recouvrement.getNewCodeMembre(), recouvrement.getTypeRessource());
				}
				return ResponseEntity.ok(new Result(0, result));
			} else {
				return ResponseEntity.badRequest()
						.body(new Result(1, "Veuillez fournir votre nouveau numéro de compte ESMC"));
			}
		} catch (Exception e) {
			log.error("Echec de l'operation", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Result(1, "Echec de l'operation :" + e.getMessage()));
		}
	}

	@PostMapping(value = "sm")
	public ResponseEntity<?> loadOrigine(@RequestParam(name = "id") Long id, @RequestParam(name = "type") String type,
			String codeMembre) {
		if ("CNP".equals(type) || "CNCS".equals(type)) {
			if (codeMembre.length() == 20) {
				return ResponseEntity.ok(ancCompteCreditService.getById(id));
			} else {
				return ResponseEntity.ok(placeService.getById(id));
			}
		} else if("GCP".equals(type)) {
			return ResponseEntity.ok(ancienGcpService.findById(id));
		} else if("MF107".equals(type)) {
			return ResponseEntity.ok(repMf107Service.getById(id));
		} else if("MF11000".equals(type)) {
			return ResponseEntity.ok(repMf11000Service.getById(id));
		}
		return null;
	}
}

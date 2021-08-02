package com.esmc.mcnp.web.controller.smcipn;

import com.esmc.mcnp.domain.dto.smcipn.DetailBudget;
import com.esmc.mcnp.domain.enums.StatutBudget;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.domain.entity.smcipn.EuBudget;
import com.esmc.mcnp.domain.entity.smcipn.EuDetailBudget;
import com.esmc.mcnp.domain.entity.smcipn.EuTypeBudget;
import com.esmc.mcnp.domain.mapper.smcipn.BudgetMapper;
import com.esmc.mcnp.domain.mapper.smcipn.DetailBudgetMapper;
import com.esmc.mcnp.commons.exception.business.NotFoundException;
import com.esmc.mcnp.infrastructure.services.smcipn.EuBudgetService;
import com.esmc.mcnp.infrastructure.services.smcipn.EuDetailBudgetService;
import com.esmc.mcnp.infrastructure.services.smcipn.EuTypeBudgetService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.dto.util.Result;
import com.esmc.mcnp.web.model.budget.Budget;
import com.esmc.mcnp.web.model.budget.TypeBudget;
import com.esmc.mcnp.util.ErrorDTO;
import com.esmc.mcnp.util.JqGrid;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping(value = "/budget")
public class Budgetcontroller extends BaseController {

    private EuBudgetService budgetService;
    private EuTypeBudgetService typeBudgetService;
    private EuDetailBudgetService detailBudgetService;
    private DetailBudgetMapper detailBudgetMapper;
    private BudgetMapper budgetMapper;

    @Autowired
    public Budgetcontroller(EuTypeBudgetService typeBudgetService, EuBudgetService budgetService,
                            EuDetailBudgetService detailBudgetService, DetailBudgetMapper detailBudgetMapper,
                            BudgetMapper budgetMapper) {
        this.typeBudgetService = typeBudgetService;
        this.budgetService = budgetService;
        this.detailBudgetService = detailBudgetService;
        this.detailBudgetMapper = detailBudgetMapper;
        this.budgetMapper = budgetMapper;
    }

    @GetMapping(value = "add")
    public String newBudget(@ModelAttribute("budget") EuBudget budget, Model model) {
        List<TypeBudget> types = TypeBudget.toListTypes(typeBudgetService.listAll());
        List<String> statuts = Lists.newArrayList();
        Arrays.asList(StatutBudget.values()).forEach(s -> statuts.add(s.name()));
        model.addAttribute("types", types);
        model.addAttribute("statuts", statuts);
        return "budget/budget";
    }

    @PostMapping(value = "add")
    public String addBudget(@ModelAttribute("budget") Budget budget, Model model) {
        return "";
    }

    @GetMapping(value = "detail/{id}")
    public String gotoDetailBudget(@PathVariable("id") Long id, Model model) {
        List<TypeBudget> types = TypeBudget.toListTypes(typeBudgetService.listAll());
        List<String> statuts = Lists.newArrayList();
        Arrays.asList(StatutBudget.values()).forEach(s -> statuts.add(s.name()));
        Budget budget = new Budget(budgetService.findWithId(id));
        model.addAttribute("types", types);
        model.addAttribute("statuts", statuts);
        model.addAttribute("budget", budget);
        return "budget/detailBudget";
    }

    @GetMapping(value = "validation")
    public String validationBudget(@ModelAttribute("budget") Budget budget, Model model) {
        List<TypeBudget> types = TypeBudget.toListTypes(typeBudgetService.listAll());
        model.addAttribute("types", types);
        return "budget/validation";
    }

    @GetMapping(value = "valider/{id}")
    public String gotoValidationBudget(@PathVariable("id") Long id, Model model) {
        List<TypeBudget> types = TypeBudget.toListTypes(typeBudgetService.listAll());
        List<String> statuts = Lists.newArrayList();
        Arrays.asList(StatutBudget.values()).forEach(s -> statuts.add(s.name()));
        Budget budget = new Budget(budgetService.findWithId(id));
        model.addAttribute("types", types);
        model.addAttribute("statuts", statuts);
        model.addAttribute("budget", budget);
        return "budget/validerBudget";
    }

    @GetMapping(value = "addDetail")
    public ResponseEntity<?> ajoutDetail(DetailBudget detailBudget) {
        try {
            detailBudgetService.create(detailBudgetMapper.toEuDetailBudget(detailBudget));
            return ResponseEntity.ok(null);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(value = "saveDetail")
    public ResponseEntity<?> addDetail(@RequestParam(name = "id") String id,
                                       @RequestParam(name = "budget.codeBudget") String codeBudget,
                                       @RequestParam(name = "libelleDetailBudget") String libelleDetail,
                                       @RequestParam(name = "montantInitial") Double montant) {
        try {
            Optional<Double> somDetailOpt = detailBudgetService.getSumInitByBudget(codeBudget);
            com.esmc.mcnp.domain.dto.smcipn.Budget budget = budgetMapper.fromEuBudget(budgetService.findByCodeBudget(codeBudget));
            if (somDetailOpt.isEmpty() || (somDetailOpt.isPresent() && ((somDetailOpt.get() + montant) <= budget.getMontantDemande()))) {
                if (StringUtils.isNumeric(id)) {
                    DetailBudget detailBudget = detailBudgetMapper.fromEuDetailBudget(detailBudgetService.getById(Long.valueOf(id)));
                    detailBudget.setLibelleDetailBudget(libelleDetail)
                            .setMontantInitial(montant)
                            .setMontantBudget(montant)
                            .setBudget(budget);
                    EuDetailBudget euDetailBudget = detailBudgetMapper.toEuDetailBudget(detailBudget);
                    euDetailBudget.setUser(getCurrentUser());
                    detailBudgetService.update(euDetailBudget);
                } else {
                    DetailBudget detailBudget = new DetailBudget();
                    detailBudget.setLibelleDetailBudget(libelleDetail)
                            .setMontantInitial(montant)
                            .setMontantBudget(montant)
                            .setBudget(budget);
                    EuDetailBudget euDetailBudget = detailBudgetMapper.toEuDetailBudget(detailBudget);
                    euDetailBudget.setUser(getCurrentUser());
                    detailBudgetService.create(euDetailBudget);
                }
                return ResponseEntity.ok().body(new Result(0, "Detail Budget bien enregistre"));
            } else {
                double reste = budget.getMontantDemande() - somDetailOpt.get();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("Le montant defini est depasse, le reste est :" + reste));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO(e.getMessage()));
        }
    }

    @PostMapping(value = "detail")
    public ResponseEntity<?> addDetail(DetailBudget detailBudget) {
        try {
            detailBudgetService.create(detailBudgetMapper.toEuDetailBudget(detailBudget));
            return ResponseEntity.ok(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "details")
    @ResponseBody
    public JqGrid<DetailBudget> loadDetailBudget(
            @RequestParam(value = "codeBudget", required = false) String codeBudget,
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

        EuUtilisateur user = getCurrentUser();
        JqGrid<DetailBudget> response = new JqGrid<>();
        if (Objects.nonNull(user)) {
            Page<EuDetailBudget> euDetailBudgets = detailBudgetService.findByBudgetAndUser(codeBudget, user.getIdUtilisateur(), pageRequest);
            if (Objects.nonNull(euDetailBudgets) && euDetailBudgets.getTotalElements() > 0) {
                List<DetailBudget> detailBudgets = detailBudgetMapper.fromEuDetailBudgetList(Lists.newArrayList(euDetailBudgets.iterator()));
                response.setRows(detailBudgets);
                response.setTotal(Integer.toString(euDetailBudgets.getTotalPages()));
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

    @GetMapping(value = "detailBudgets")
    @ResponseBody
    public JqGrid<DetailBudget> loadDetailBudgetById(
            @RequestParam(value = "id", required = false) Long id,
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
        JqGrid<DetailBudget> response = new JqGrid<>();
        if (Objects.nonNull(utilisateur)) {
            Page<EuDetailBudget> euDetailBudgets = detailBudgetService.findByBudgetAndUser(id, utilisateur.getIdUtilisateur(), pageRequest);
            if (Objects.nonNull(euDetailBudgets) && euDetailBudgets.getTotalElements() > 0) {
                List<DetailBudget> detailBudgets = detailBudgetMapper.fromEuDetailBudgetList(Lists.newArrayList(euDetailBudgets.iterator()));
                response.setRows(detailBudgets);
                response.setTotal(Integer.toString(euDetailBudgets.getTotalPages()));
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

    @GetMapping(value = "detailBudget/{code}")
    public ResponseEntity<?> getDetailsBudget(@PathVariable("code") String codeBudget) {
        if (StringUtils.isNotBlank(codeBudget)) {
            return ResponseEntity.ok(detailBudgetMapper.fromEuDetailBudgetList(detailBudgetService.findByBudget(codeBudget)));
        } else {
            return ResponseEntity.ok(Lists.newArrayList());
        }
    }

    @PostMapping(value = "valider")
    public ResponseEntity<?> validerBudget(Budget budget) {
        try {
            EuBudget euBudget = budget.toEuBudget();
            if (Objects.nonNull(euBudget) && !euBudget.isValider()) {
                List<EuDetailBudget> detailBudgets = detailBudgetService.findByBudget(euBudget.getId());
                if (!detailBudgets.isEmpty()) {
                    euBudget.setMontantValide(detailBudgets.stream().mapToDouble(EuDetailBudget::getMontantBudget).sum());
                } else {
                    if (euBudget.getMontantValide() == 0.0 || euBudget.getMontantValide() == null) {
                        euBudget.setMontantValide(euBudget.getMontantDemande());
                    }
                }
                euBudget.setMontantBudget(0.0);
                euBudget.setMontantUtilise(0.0);
                euBudget.setSoldeBudget(0.0);
                euBudget.setStatutBudget(StatutBudget.VALIDE.name());
                euBudget.setValider(true);
                budgetService.update(euBudget);
                return ResponseEntity.ok().body(new Result(0, "Budget " + euBudget.getCodeBudget() + " est bien valide", euBudget.getMontantBudget()));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("Code Budget " + euBudget.getCodeBudget() + " non trouve ou deja valide"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO(e.getMessage()));
        }
    }

    @PostMapping(value = "affecter")
    public ResponseEntity<?> affecterBudget(String codeBudget) {
        try {
            EuBudget euBudget = budgetService.findByCodeBudget(codeBudget);
            if (Objects.nonNull(euBudget) && euBudget.isValider()) {
                List<EuDetailBudget> detailBudgets = detailBudgetService.findByBudget(euBudget.getId());
                if (!detailBudgets.isEmpty()) {
                    euBudget.setMontantBudget(detailBudgets.stream().mapToDouble(EuDetailBudget::getMontantBudget).sum());
                }
                euBudget.setMontantUtilise(0.0);
                euBudget.setSoldeBudget(euBudget.getMontantBudget());
                euBudget.setStatutBudget(StatutBudget.VALIDE.name());
                euBudget.setValider(true);
                budgetService.update(euBudget);
                return ResponseEntity.ok().body(new Result(0, "Budget " + euBudget.getCodeBudget() + " est bien valide", euBudget.getMontantBudget()));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("Code Budget " + codeBudget + " non trouve ou deja valide"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO(e.getMessage()));
        }
    }

    @PostMapping(value = "validerDet")
    public ResponseEntity<?> validerDetBudget(@RequestParam(name = "id") String id,
                                              @RequestParam(name = "budget.codeBudget") String codeBudget,
                                              @RequestParam(name = "libelleDetailBudget") String libelleDetail,
                                              @RequestParam(name = "montantInitial") Double montant,
                                              @RequestParam(name = "montantBudget") Double montantBudget) {
        try {
            Optional<EuDetailBudget> opDetailBudget = detailBudgetService.fetchById(Long.parseLong(id));
            if (opDetailBudget.isPresent() && !opDetailBudget.get().isValider()) {
                Optional<Double> somDetailOpt = detailBudgetService.getSumByBudget(codeBudget);
                com.esmc.mcnp.domain.dto.smcipn.Budget budget = budgetMapper.fromEuBudget(budgetService.findByCodeBudget(codeBudget));
                if (somDetailOpt.isEmpty() || budget.getMontantBudget() == null || budget.getMontantBudget() == 0.0 ||
                        (somDetailOpt.get() < budget.getMontantBudget())) {
                    if (StringUtils.isNumeric(id)) {
                        DetailBudget detailBudget = detailBudgetMapper.fromEuDetailBudget(detailBudgetService.getById(Long.valueOf(id)));
                        detailBudget.setLibelleDetailBudget(libelleDetail)
                                .setMontantInitial(montant)
                                .setMontantBudget(montantBudget)
                                .setStatutDetailBudget(StatutBudget.VALIDE.name())
                                .setValider(true)
                                .setBudget(budget);
                        EuDetailBudget euDetailBudget = detailBudgetMapper.toEuDetailBudget(detailBudget);
                        euDetailBudget.setUser(getCurrentUser());
                        detailBudgetService.update(euDetailBudget);
                        return ResponseEntity.ok().body(new Result(0, "Detail Budget bien valide"));
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Echec de la validation : Erreur de Cle Primaire"));
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("Echec de la validation : Erreur d'integrite des donnees"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("Echec de la validation: Detail Budget deja valide ou non trouve"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO(e.getMessage()));
        }
    }

    @GetMapping(value = "budgets")
    public ResponseEntity<?> fetchBudget() {
        return ResponseEntity.ok(budgetService.getAll());
    }

    @PostMapping(value = "save")
    public ResponseEntity<?> saveBudget(@ModelAttribute("budget") Budget budget) {
        try {
            EuBudget euBudget = budget.toEuBudget();
            if (euBudget.getId() != null) {
                budgetService.update(euBudget);
            } else {
                EuTypeBudget typeBudget = typeBudgetService.getById(budget.getTypeBudget().getId());
                var codeBudget = typeBudget.getCodeTypeBudget() + "-" + RandomStringUtils.randomNumeric(2);
                euBudget.setCodeBudget(codeBudget);
                euBudget.setMontantBudget(0.0);
                euBudget.setMontantUtilise(0.0);
                euBudget.setSoldeBudget(0.0);
                euBudget.setUser(getCurrentUser());
                if (Objects.nonNull(getCurrentUser().getAgencesOdd())) {
                    euBudget.setAgencesOdd(getCurrentUser().getAgencesOdd());
                } else if (Objects.nonNull(getCurrentUser().getCentre())) {
                    euBudget.setCentre(getCurrentUser().getCentre());
                }
                budgetService.create(euBudget);
            }
            return ResponseEntity.ok().body(new Result(0, "Budget bien cree"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO(ex.getMessage()));
        }

    }

    @GetMapping(value = "{id}")
    public String editBudget(@PathVariable("id") Long id, Model model) {
        if (id != null) {
            Budget budget = new Budget(budgetService.getById(id));
            if (Objects.nonNull(budget)) {
                model.addAttribute("budget", budget);
                return "budget/budget";
            } else {
                new NotFoundException("Le budget N°" + id + "est introuvable");
            }
        }
        return "";
    }

    @GetMapping(value = "list")
    public String listBudget(@ModelAttribute(name = "budget") Budget budget, Model model) {
        List<TypeBudget> types = TypeBudget.toListTypes(typeBudgetService.listAll());
        List<String> statuts = Lists.newArrayList();
        Arrays.asList(StatutBudget.values()).forEach(s -> statuts.add(s.name()));
        model.addAttribute("types", types);
        model.addAttribute("statuts", statuts);
        return "budget/budgets";
    }

    @GetMapping(value = "listBudget")
    @ResponseBody
    public JqGrid<Budget> loadBudget(@RequestParam(value = "page", required = false) Integer page,
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
        JqGrid<Budget> response = new JqGrid<>();
        if (Objects.nonNull(utilisateur)) {

            Page<EuBudget> budgets = budgetService.findByUser(utilisateur.getIdUtilisateur(), pageRequest);
            if (Objects.nonNull(budgets) && budgets.getTotalElements() > 0) {
                List<Budget> creances = Budget.toListBudget(Lists.newArrayList(budgets.iterator()));
                response.setRows(creances);
                response.setTotal(Integer.toString(budgets.getTotalPages()));
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

    @GetMapping(value = "listeBudgets")
    @ResponseBody
    public JqGrid<Budget> loadBudgets(@RequestParam(value = "valider", required = false) Boolean valider,
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

        JqGrid<Budget> response = new JqGrid<>();
        Page<EuBudget> budgetPage = null;
        if (valider == null) {
            budgetPage = budgetService.listAll(pageRequest);
        } else {
            budgetPage = budgetService.findByValider(valider, pageRequest);
        }
        if (Objects.nonNull(budgetPage) && budgetPage.getTotalElements() > 0) {
            List<Budget> creances = Budget.toListBudget(Lists.newArrayList(budgetPage.iterator()));
            response.setRows(creances);
            response.setTotal(Integer.toString(budgetPage.getTotalPages()));
            return response;
        } else {
            response.setRows(Lists.newArrayList());
            response.setTotal("0");
            return response;
        }
    }

    @GetMapping(value = "index")
    public String indexBudget() {
        return "budget/index";
    }

    @GetMapping(value = "type")
    public String afficheTypeBudget() {
        return "budget/typeBudget";
    }

    @GetMapping(value = "typeBudget")
    public String delTypeBudget(@RequestParam("id") Long id) {
        if (id != null) {
            typeBudgetService.removeById(id);
            return "ok";
        }
        return "ko";
    }

    @PostMapping(value = "type")
    public ResponseEntity<?> saveTypeBudget(@ModelAttribute TypeBudget type) {
        try {
            if (type.getId() != null) {
                Optional<EuTypeBudget> optypeBudget = typeBudgetService.fetchById(type.getId());
                if (optypeBudget.isPresent()) {
                    EuTypeBudget typeBudget = typeBudgetService.update(type.toTypeBudget(optypeBudget.get()));
                    return new ResponseEntity<TypeBudget>(new TypeBudget(typeBudget), HttpStatus.CREATED);
                } else {
                    EuTypeBudget typeBudget = type.toTypeBudget();
                    typeBudget.setUser(getCurrentUser());
                    typeBudget = typeBudgetService.create(typeBudget);
                    return new ResponseEntity<TypeBudget>(new TypeBudget(typeBudget), HttpStatus.CREATED);
                }
            } else {
                EuTypeBudget typeBudget = type.toTypeBudget();
                typeBudget.setUser(getCurrentUser());
                typeBudget = typeBudgetService.create(typeBudget);
                return new ResponseEntity<TypeBudget>(new TypeBudget(typeBudget), HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<Result>(new Result(0, "Erreur d'execution: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "types")
    @ResponseBody
    public JqGrid<TypeBudget> loadTypeBudget(
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
        JqGrid<TypeBudget> response = new JqGrid<>();
        if (Objects.nonNull(utilisateur)) {
            Page<EuTypeBudget> listecm = typeBudgetService.findByUser(utilisateur.getIdUtilisateur(), pageRequest);
            if (Objects.nonNull(listecm) && listecm.getTotalElements() > 0) {
                List<TypeBudget> creances = TypeBudget.toListTypes(Lists.newArrayList(listecm.iterator()));
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

    @GetMapping(value = "typeBudget/{id}")
    ResponseEntity<?> fetchTypeBudget(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.ok(typeBudgetService.getById(id));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO(ex.getMessage()));
        }

    }

}

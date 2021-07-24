package com.esmc.mcnp.web.controller.odd;

import com.esmc.mcnp.dto.cm.Membre;
import com.esmc.mcnp.dto.odd.AgencesOdd;
import com.esmc.mcnp.dto.odd.Centrales;
import com.esmc.mcnp.mapper.cm.MembreMapper;
import com.esmc.mcnp.mapper.cm.MembreMoraleMapper;
import com.esmc.mcnp.mapper.odd.AgenceOddMapper;
import com.esmc.mcnp.mapper.security.RolesMapper;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.odd.EuCentrales;
import com.esmc.mcnp.model.odd.EuTypeCentrale;
import com.esmc.mcnp.model.security.EuUserRolesPermission;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.cm.EuMembreService;
import com.esmc.mcnp.services.odd.EuAgenceOddService;
import com.esmc.mcnp.services.odd.EuCentralesService;
import com.esmc.mcnp.services.odd.EuTypeCentraleService;
import com.esmc.mcnp.services.security.EuRolesService;
import com.esmc.mcnp.services.security.EuUserRolesPermissionService;
import com.esmc.mcnp.services.security.EuUtilisateurService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.dto.util.Result;
import com.esmc.mcnp.web.model.grid.Grid;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping(value = "/centrale")
public class CentralesController extends BaseController {

    private final EuCentralesService centralesService;
    private final EuTypeCentraleService typeCentraleService;
    private final EuAgenceOddService agenceOddService;
    private final AgenceOddMapper agenceOddMapper;
    private final EuMembreMoraleService moraleService;
    private final MembreMoraleMapper moraleMapper;
    private final EuUtilisateurService utilisateurService;
    private final EuRolesService roleService;
    private final RolesMapper rolesMapper;
    private final EuUserRolesPermissionService userRolesPermissionService;
    private final EuMembreService membreService;
    private final MembreMapper membreMapper;

    @Autowired
    public CentralesController(EuCentralesService centralesService, EuTypeCentraleService typeCentraleService,
                               EuAgenceOddService agenceOddService, AgenceOddMapper agenceOddMapper,
                               EuMembreMoraleService moraleService, MembreMoraleMapper moraleMapper,
                               EuUtilisateurService utilisateurService, EuRolesService roleService,
                               RolesMapper rolesMapper, EuUserRolesPermissionService userRolesPermissionService,
                               EuMembreService membreService, MembreMapper membreMapper) {
        this.centralesService = centralesService;
        this.typeCentraleService = typeCentraleService;
        this.agenceOddService = agenceOddService;
        this.agenceOddMapper = agenceOddMapper;
        this.moraleService = moraleService;
        this.moraleMapper = moraleMapper;
        this.utilisateurService = utilisateurService;
        this.roleService = roleService;
        this.rolesMapper = rolesMapper;
        this.userRolesPermissionService = userRolesPermissionService;
        this.membreService = membreService;
        this.membreMapper = membreMapper;
    }

    @GetMapping(value = "ajout")
    public String addingCentrale(@ModelAttribute("centrale") Centrales centrales, Model model) {
        model.addAttribute("agences", agenceOddService.getAll());
        model.addAttribute("types", typeCentraleService.listAll());
        return "admin/addCentrale";
    }

    @PreAuthorize("hasAnyAuthority('CA','ADMIN')")
    @PostMapping(value = "ajout")
    public String ajoutCentrale(@ModelAttribute("centrale") Centrales centrale,
                                @RequestParam(name = "login") String login,
                                @RequestParam(name = "password") String password,
                                @RequestParam(name = "passwordConf") String passwordConf, Model model, RedirectAttributes redirect) {
        if (!StringUtils.equals(password, passwordConf)) {
            model.addAttribute("centrale", centrale);
            model.addAttribute("login", login);
            model.addAttribute("password", password);
            model.addAttribute("passwordConf", passwordConf);
            model.addAttribute("agences", agenceOddService.getAll());
            model.addAttribute("message", "Les mots de passes ne sont pas identiques");
            return "admin/addAgenceOdd";
        }
        try {
            EuUtilisateur user = getCurrentUser();
            String refCentrale = "CA-" + RandomStringUtils.randomAlphanumeric(6).toUpperCase();
            Membre membre = new Membre()
                    .setNomMembre("Agent")
                    .setPrenomMembre(centrale.getLibelleCentrale())
                    .setBpMembre(null)
                    .setTelMembre(null)
                    .setPortableMembre(null)
                    .setQuartierMembre(null)
                    .setAutoEnroler("O")
                    .setIdUtilisateur(Objects.requireNonNull(user).getIdUtilisateur())
                    .setEtatMembre("N")
                    .setDesactiver(0)
                    .setHeureIdentification(LocalTime.now())
                    .setDateIdentification(new Date());
            EuMembre euMembre = membreService.createAdmin(membre);

            EuUtilisateur utilisateur = new EuUtilisateur()
                    .setNomUtilisateur("Agent")
                    .setPrenomUtilisateur(centrale.getLibelleCentrale())
                    .setIdUtilisateurParent(user.getIdUtilisateur())
                    .setLogin(login)
                    .setPasswordHash(password)
                    .setPwd(password)
                    .setChPwdFlog(0)
                    .setCodeMembre(euMembre.getCodeMembre())
                    .setConnecte(0)
                    .setUlock(0)
                    .setAgencesOdd(user.getAgencesOdd())
                    .setCentre(user.getCentre());
            utilisateur = utilisateurService.saveUser(utilisateur, utilisateur.getPasswordHash());

            EuUserRolesPermission userRole = new EuUserRolesPermission();
            userRole.setUtilisateur(utilisateur);
            userRole.setResponsabilite(1);
            userRole.setCodeResponsabilite("RCODD");
            userRole.setLibelleResponsabilite("Responsable Centrale");
            userRole.setEuRole(roleService.getByCode("CODD"));
            userRole.setDateAffectationRoles(LocalDateTime.now());
            userRolesPermissionService.create(userRole);

            EuCentrales centrales = centrale.toEuCentrales();
            centrales.setUser(user);
            centrales.setReferenceCentrale(refCentrale);
            centrales.setEuAgencesOdd(user.getAgencesOdd());
            centrales.setDateCreationCentrales(LocalDate.now());
            centralesService.create(centrales);
            return "redirect:/centrale/list";
        } catch (Exception e) {
            model.addAttribute("agences", agenceOddService.getAll());
            model.addAttribute("centrale", centrale);
            model.addAttribute("login", login);
            model.addAttribute("password", password);
            model.addAttribute("passwordConf", passwordConf);
            model.addAttribute("message", e.getMessage());
            getLog().error("Echec de la creation du centre:", e);
            return "admin/addCentrale";
        }
    }

    @GetMapping(value = "list")
    public String listCentrales() {
        return "admin/centrale";
    }

    @GetMapping(value = "centrales")
    @ResponseBody
    public Grid<Centrales> loadCentrales(@RequestParam(value = "page", required = false) Integer page,
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
        Grid<Centrales> response = new Grid<>();
        if (Objects.nonNull(utilisateur)) {

            Page<EuCentrales> centrales = centralesService.listAll(pageRequest);
            if (Objects.nonNull(centrales) && centrales.getTotalElements() > 0) {
                response.setRows(Centrales.toCentralesList(centrales.getContent()));
                response.setTotal(Integer.toString(centrales.getTotalPages()));
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

    @GetMapping(value = "typeCentrale")
    public ResponseEntity<?> loadTypeCentrales() {
        return ResponseEntity.ok(typeCentraleService.listAll());
    }

    @GetMapping(value = "listAgenceOdd")
    public ResponseEntity<?> loadAgenceOdd() {
        return ResponseEntity.ok(agenceOddMapper.fromEuAgencesOddList(agenceOddService.listAll()));
    }


    @GetMapping(value = "add")
    public String newCentre(@ModelAttribute("centrale") Centrales centrale, Model model) {
        List<EuTypeCentrale> typeCentrales = typeCentraleService.listAll();
        List<AgencesOdd> agences = AgencesOdd.toAgencesOddList(agenceOddService.listAll());
        model.addAttribute(typeCentrales);
        model.addAttribute(agences);
        return "admin/centrale";
    }

    @PostMapping(value = "add")
    public ResponseEntity<?> saveCentrale(@ModelAttribute Centrales centrale, Model model) {
        try {
            EuUtilisateur user = getCurrentUser();
            if (centrale.getId() != null) {
                Optional<EuCentrales> optCentrale = centralesService.fetchById(centrale.getId());
                if (optCentrale.isPresent()) {
                    EuCentrales centrales = optCentrale.get();
                    centrales.setLibelleCentrale(centrale.getLibelleCentrale());
                    centrales.setEuTypeCentrale(centrale.getEuTypeCentrale());
                    if (Objects.isNull(centrale.getDateCreationCentrales())) {
                        centrales.setDateCreationCentrales(LocalDate.now());
                    }
                    if (StringUtils.isBlank(centrale.getReferenceCentrale())) {
                        centrales.setReferenceCentrale("C" + RandomStringUtils.randomAlphanumeric(7).toUpperCase());
                    }
                    if (centrale.getAgencesOdd().getId() != null) {
                        centrales.setEuAgencesOdd(agenceOddService.getById(centrale.getAgencesOdd().getId()));
                    }
                    centrales = centralesService.update(centrales);
                    return new ResponseEntity<Centrales>(new Centrales(centrales), HttpStatus.CREATED);
                } else {
                    EuCentrales centrales = centrale.toEuCentrales();
                    centrales.setUser(user);
                    centrales.setReferenceCentrale("C" + RandomStringUtils.randomAlphanumeric(7).toUpperCase());
                    if (centrale.getAgencesOdd().getId() != null) {
                        centrales.setEuAgencesOdd(agenceOddService.getById(centrale.getAgencesOdd().getId()));
                    } else {
                        centrales.setEuAgencesOdd(user.getAgencesOdd());
                    }
                    centrales.setDateCreationCentrales(LocalDate.now());
                    centrales = centralesService.create(centrales);
                    return new ResponseEntity<Centrales>(new Centrales(centrales), HttpStatus.CREATED);
                }
            } else {
                EuCentrales centrales = centrale.toEuCentrales();
                centrales.setUser(user);
                centrales.setReferenceCentrale("C" + RandomStringUtils.randomAlphanumeric(7).toUpperCase());
                if (centrale.getAgencesOdd().getId() != null) {
                    centrales.setEuAgencesOdd(agenceOddService.getById(centrale.getAgencesOdd().getId()));
                } else {
                    centrales.setEuAgencesOdd(user.getAgencesOdd());
                }
                centrales.setDateCreationCentrales(LocalDate.now());
                centrales = centralesService.create(centrales);
                return new ResponseEntity<Centrales>(new Centrales(centrales), HttpStatus.CREATED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Result>(new Result(0, "Erreur d'execution: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "del")
    public String delCentrale(@RequestParam("id") Integer id) {
        if (id != null) {
            centralesService.removeById(id);
            return "ok";
        }
        return "ko";
    }

}

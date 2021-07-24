package com.esmc.mcnp.web.controller.odd;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.esmc.mcnp.dto.cm.MembreMorale;
import com.esmc.mcnp.dto.projections.CantonVO;
import com.esmc.mcnp.mapper.cm.MembreMoraleMapper;
import com.esmc.mcnp.mapper.odd.AgenceOddMapper;
import com.esmc.mcnp.mapper.odd.CentresMapper;
import com.esmc.mcnp.mapper.security.RolesMapper;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.odd.EuCentres;
import com.esmc.mcnp.model.org.EuCanton;
import com.esmc.mcnp.model.security.EuUserRolesPermission;
import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.odd.EuCentresService;
import com.esmc.mcnp.services.org.EuCantonService;
import com.esmc.mcnp.services.security.EuRolesService;
import com.esmc.mcnp.services.security.EuUserRolesPermissionService;
import com.esmc.mcnp.services.security.EuUtilisateurService;
import com.esmc.mcnp.web.model.enums.TypeCentreEnum;
import com.esmc.mcnp.web.model.grid.Grid;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esmc.mcnp.model.odd.EuAgencesOdd;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.services.odd.EuAgenceOddService;
import com.esmc.mcnp.services.odd.EuOddService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.dto.util.Result;
import com.esmc.mcnp.dto.odd.AgencesOdd;
import com.google.common.collect.Lists;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/agence")
public class AgenceOddController extends BaseController {

    private final EuAgenceOddService agenceOddService;
    private final AgenceOddMapper agenceOddMapper;
    private final CentresMapper centresMapper;
    private final EuOddService oddService;
    private final EuCentresService centresService;
    private final EuCantonService cantonService;
    private final EuMembreMoraleService moraleService;
    private final MembreMoraleMapper moraleMapper;
    private final EuUtilisateurService utilisateurService;
    private final EuRolesService roleService;
    private final RolesMapper rolesMapper;
    private final EuUserRolesPermissionService userRolesPermissionService;

    @Autowired
    public AgenceOddController(EuAgenceOddService agenceOddService, EuOddService oddService,
                               EuCentresService centresService, AgenceOddMapper agenceOddMapper,
                               CentresMapper centresMapper, EuCantonService cantonService,
                               EuMembreMoraleService moraleService, MembreMoraleMapper moraleMapper,
                               EuUtilisateurService utilisateurService, EuRolesService roleService,
                               RolesMapper rolesMapper, EuUserRolesPermissionService userRolesPermissionService) {
        this.agenceOddService = agenceOddService;
        this.agenceOddMapper = agenceOddMapper;
        this.oddService = oddService;
        this.centresService = centresService;
        this.centresMapper = centresMapper;
        this.cantonService = cantonService;
        this.moraleService = moraleService;
        this.moraleMapper = moraleMapper;
        this.utilisateurService = utilisateurService;
        this.roleService = roleService;
        this.rolesMapper = rolesMapper;
        this.userRolesPermissionService = userRolesPermissionService;
    }

    @GetMapping(value = "odd")
    public ResponseEntity<?> listeOdds() {
        return ResponseEntity.ok(oddService.fetchAll());
    }

    @GetMapping(value = "canton")
    public ResponseEntity<?> listeCantons() {
        return ResponseEntity.ok(cantonService.getAll());
    }

    @GetMapping(value = "listCentres")
    public ResponseEntity<?> listeCentres() {
        return ResponseEntity.ok(centresService.getAllVO());
    }

    @GetMapping(value = "ajout")
    public String addAgencesOdd(@ModelAttribute("agenceOdd") AgencesOdd agences, Model model) {
        EuUtilisateur user = getCurrentUser();
        assert user != null;
        List<CantonVO> cantons = Lists.newArrayList();
        if (Objects.nonNull(user.getCentre())) {
            if (user.getCentre().getTypeCentre().equalsIgnoreCase(TypeCentreEnum.CANTON.getTypeCentre())) {
                cantons.add(new CantonVO(user.getCentre().getCanton().getIdCanton(), user.getCentre().getCanton().getNomCanton()));
            } else if (user.getCentre().getTypeCentre().equalsIgnoreCase(TypeCentreEnum.PREFECTURE.getTypeCentre())) {
                cantons = cantonService.getAllByPrefecture(user.getCentre().getPrefecture().getIdPrefecture());
            } else if (user.getCentre().getTypeCentre().equalsIgnoreCase(TypeCentreEnum.REGION.getTypeCentre())) {
                cantons = cantonService.getAllByRegion(user.getCentre().getRegion().getIdRegion());
            } else {
                cantons = cantonService.getAllByPays(user.getCentre().getPays().getIdPays());
            }
        }
        model.addAttribute("canton", cantons);
        model.addAttribute("odds", oddService.fetchAll());
        return "admin/addAgenceOdd";
    }

    @PreAuthorize("hasAnyAuthority('CA','ADMIN')")
    @PostMapping(value = "ajout")
    public String ajoutAgencesOdd(@ModelAttribute("agenceOdd") AgencesOdd agence,
                                  @RequestParam(name = "login") String login,
                                  @RequestParam(name = "password") String password,
                                  @RequestParam(name = "passwordConf") String passwordConf, Model model, RedirectAttributes redirect) {
        if (!StringUtils.equals(password, passwordConf)) {
            model.addAttribute("agenceOdd", agence);
            model.addAttribute("login", login);
            model.addAttribute("password", password);
            model.addAttribute("passwordConf", passwordConf);
            model.addAttribute("message", "Les mots de passes ne sont pas identiques");
            return "admin/addAgenceOdd";
        }
        try {
            EuUtilisateur user = getCurrentUser();
            String refCentre = "C-" + RandomStringUtils.randomAlphanumeric(7).toUpperCase();
            MembreMorale membreMorale = new MembreMorale()
                    .setCodeTypeActeur("OSE")
                    .setRaisonSociale(agence.getLibelleAgencesOdd())
                    .setNumRegistreMembre(refCentre)
                    .setBpMembre(agence.getBpAgencesOdd())
                    .setTelMembre(agence.getTelephoneAgencesOdd())
                    .setPortableMembre(agence.getTelephoneAgencesOdd())
                    .setQuartierMembre(agence.getAdresseAgencesOdd())
                    .setIdUtilisateur(Objects.requireNonNull(user).getIdUtilisateur())
                    .setEtatMembre("N").setDesactiver(0)
                    .setHeureIdentification(LocalTime.now())
                    .setDateIdentification(new Date());
            EuMembreMorale morale = moraleService.createAdmin(membreMorale);

            EuUtilisateur utilisateur = new EuUtilisateur()
                    .setNomUtilisateur(agence.getLibelleAgencesOdd())
                    .setIdUtilisateurParent(user.getIdUtilisateur())
                    .setLogin(login)
                    .setPasswordHash(password)
                    .setPwd(password)
                    .setCentre(user.getCentre())
                    .setChPwdFlog(0)
                    .setCodeMembre(morale.getCodeMembreMorale())
                    .setConnecte(0)
                    .setUlock(0)
                    .setAgencesOdd(user.getAgencesOdd())
                    .setCentre(user.getCentre());
            utilisateur = utilisateurService.saveUser(utilisateur, utilisateur.getPasswordHash());

            EuUserRolesPermission userRole = new EuUserRolesPermission();
            userRole.setUtilisateur(utilisateur);
            userRole.setResponsabilite(1);
            userRole.setCodeResponsabilite("AODD");
            userRole.setLibelleResponsabilite("Responsable Agence");
            userRole.setEuRole(roleService.getByCode("AGODD"));
            userRole.setDateAffectationRoles(LocalDateTime.now());
            userRolesPermissionService.create(userRole);

            EuCentres euCentres = centresService.getById(user.getCentre().getIdCentres());
            EuAgencesOdd agenceOdd = agenceOddMapper.toEuAgenceOdd(agence);
            EuCanton canton = cantonService.getById(agence.getCanton().getIdCanton());
            agenceOdd.setDateAgencesOdd(LocalDate.now());
            agenceOdd.setReferenceAgencesOdd("A-" + RandomStringUtils.randomAlphanumeric(7).toUpperCase());
            agenceOdd.setUser(user);
            agenceOdd.setEuCanton(canton);
            agenceOdd.setEuCentre(euCentres);
            agenceOddService.create(agenceOdd);
            return "redirect:/agence/list";
        } catch (Exception e){
            model.addAttribute("agenceOdd", agence);
            model.addAttribute("login", login);
            model.addAttribute("password", password);
            model.addAttribute("passwordConf", passwordConf);
            model.addAttribute("message", e.getMessage());
            getLog().error("Echec de la creation du centre:", e);
            return "admin/addAgenceOdd";
        }
    }

    @GetMapping(value = "list")
    public String newAgencesOdd() {
        return "admin/agenceodd";
    }

    @GetMapping(value = "agences")
    @ResponseBody
    public Grid<AgencesOdd> loadAgences(@RequestParam(value = "page", required = false) Integer page,
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
        Grid<AgencesOdd> response = new Grid<>();
        if (Objects.nonNull(utilisateur)) {

            Page<EuAgencesOdd> agences = agenceOddService.fetchAll(pageRequest);
            if (Objects.nonNull(agences) && agences.getTotalElements() > 0) {
                response.setRows(agenceOddMapper.fromEuAgencesOddList(agences.getContent()));
                response.setTotal(Integer.toString(agences.getTotalPages()));
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

    @PostMapping(value = "add")
    @Transactional
    public ResponseEntity<?> saveAgnceOdd(@ModelAttribute AgencesOdd agencesOdd) {
        try {
            EuUtilisateur user = getCurrentUser();
            if (agencesOdd.getId() != null) {
                EuAgencesOdd agence = agenceOddMapper.toEuAgenceOdd(agencesOdd);
                if (agencesOdd.getCentre().getId() != null) {
                    agence.setEuCentre(centresService.getById(agencesOdd.getCentre().getId()));
                }
                if (agencesOdd.getCanton().getIdCanton() != null) {
                    agence.setEuCanton(cantonService.getById(agencesOdd.getCanton().getIdCanton()));
                }
                if (StringUtils.isBlank(agence.getReferenceAgencesOdd())) {
                    agence.setReferenceAgencesOdd("A-" + RandomStringUtils.randomAlphanumeric(7).toUpperCase());
                }
                agence.setDateAgencesOdd(LocalDate.now());
                agence = agenceOddService.update(agence);
                return new ResponseEntity<AgencesOdd>(agenceOddMapper.fromEuAgenceOdd(agence), HttpStatus.CREATED);
            } else {
                EuCentres euCentres = centresService.getById(agencesOdd.getCentre().getId());
                EuAgencesOdd agence = agenceOddMapper.toEuAgenceOdd(agencesOdd);
                EuCanton canton = cantonService.getById(agencesOdd.getCanton().getIdCanton());
                agence.setDateAgencesOdd(LocalDate.now());
                agence.setReferenceAgencesOdd("A-" + RandomStringUtils.randomAlphanumeric(7).toUpperCase());
                agence.setUser(user);
                agence.setEuCanton(canton);
                agence.setEuCentre(euCentres);
                agence = agenceOddService.create(agence);
                return new ResponseEntity<AgencesOdd>(agenceOddMapper.fromEuAgenceOdd(agence), HttpStatus.CREATED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Result>(new Result(0, "Erreur d'execution: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "del")
    public String delAgenceOdd(@RequestParam("id") Integer id) {
        if (id != null) {
            agenceOddService.removeById(id);
            return "ok";
        }
        return "ko";
    }

}

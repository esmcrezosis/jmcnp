package com.esmc.mcnp.web.controller.odd;

import static com.esmc.mcnp.web.model.enums.TypeCentreEnum.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import com.esmc.mcnp.dto.cm.MembreMorale;
import com.esmc.mcnp.exception.NotFoundException;
import com.esmc.mcnp.mapper.cm.MembreMoraleMapper;
import com.esmc.mcnp.mapper.odd.CentresMapper;
import com.esmc.mcnp.mapper.security.RolesMapper;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.security.EuUserRolesPermission;
import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.security.EuRolesService;
import com.esmc.mcnp.services.security.EuUserRolesPermissionService;
import com.esmc.mcnp.services.security.EuUtilisateurService;
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

import com.esmc.mcnp.model.org.EuPays;
import com.esmc.mcnp.model.org.EuPrefecture;
import com.esmc.mcnp.model.org.EuRegion;
import com.esmc.mcnp.model.org.EuZone;
import com.esmc.mcnp.model.odd.EuCentres;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.services.org.EuCantonService;
import com.esmc.mcnp.services.org.EuPaysService;
import com.esmc.mcnp.services.org.EuPrefectureService;
import com.esmc.mcnp.services.org.EuRegionService;
import com.esmc.mcnp.services.org.EuZoneService;
import com.esmc.mcnp.services.odd.EuCentresService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.dto.util.Result;
import com.esmc.mcnp.web.model.ajax.JsonResult;
import com.esmc.mcnp.web.model.enums.TypeCentreEnum;
import com.esmc.mcnp.web.model.grid.Grid;
import com.esmc.mcnp.dto.odd.Centres;
import com.esmc.mcnp.util.Reponse;
import com.google.common.collect.Lists;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/centre")
public class CentresController extends BaseController {

    private final EuCentresService centresService;
    private final CentresMapper centresMapper;
    private final EuPaysService paysService;
    private final EuRegionService regionservice;
    private final EuPrefectureService prefectureService;
    private final EuCantonService cantonService;
    private final EuZoneService zoneService;
    private final EuMembreMoraleService moraleService;
    private final MembreMoraleMapper moraleMapper;
    private final EuUtilisateurService utilisateurService;
    private final EuRolesService roleService;
    private final RolesMapper rolesMapper;
    private final EuUserRolesPermissionService userRolesPermissionService;

    @Autowired
    public CentresController(EuCentresService centresService, EuPaysService paysService,
                             EuRegionService regionservice, CentresMapper centresMapper,
                             EuPrefectureService prefectureService, EuCantonService cantonService,
                             EuZoneService zoneService, EuMembreMoraleService moraleService, MembreMoraleMapper moraleMapper, EuUtilisateurService utilisateurService, EuRolesService roleService, RolesMapper rolesMapper, EuUserRolesPermissionService userRolesPermissionService) {
        this.centresService = centresService;
        this.centresMapper = centresMapper;
        this.paysService = paysService;
        this.regionservice = regionservice;
        this.prefectureService = prefectureService;
        this.cantonService = cantonService;
        this.zoneService = zoneService;
        this.moraleService = moraleService;
        this.moraleMapper = moraleMapper;
        this.utilisateurService = utilisateurService;
        this.roleService = roleService;
        this.rolesMapper = rolesMapper;
        this.userRolesPermissionService = userRolesPermissionService;
    }

    @GetMapping(value = "ajout")
    public String ajoutCentre(@ModelAttribute("centre") Centres centres, Model model) {
        EuUtilisateur user = getCurrentUser();
        assert user != null;
        TypeCentreEnum typeCentre = TypeCentreEnum.valueOf(user.getCentre().getTypeCentre());
        List<String> typeCentres = Lists.newArrayList();
        Arrays.asList(values()).stream().filter(t -> t.compareTo(typeCentre) < 0).forEach(t -> typeCentres.add(t.name()));
        model.addAttribute("typeCentres", typeCentres);
        return "admin/addCentre";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CA')")
    @Transactional
    @PostMapping(value = "ajout")
    public String ajouterCentre(@ModelAttribute("centre") Centres centre,
                                @RequestParam(name = "typeCentre") String typeCentre,
                                @RequestParam(name = "login") String login,
                                @RequestParam(name = "password") String password,
                                @RequestParam(name = "passwordConf") String passwordConf,
                                @RequestParam("idlocal") Integer id, RedirectAttributes redirect, Model model) {
        if (!StringUtils.equals(password, passwordConf)) {
            model.addAttribute("centre", centre);
            model.addAttribute("typeCentre", typeCentre);
            model.addAttribute("login", login);
            model.addAttribute("password", password);
            model.addAttribute("passwordConf", passwordConf);
            model.addAttribute("idlocal", id);
            model.addAttribute("message", "Les mots de passes ne sont pas identiques");
            return "admin/addcentre";
        }
        try {
            EuUtilisateur user = getCurrentUser();
            String refCentre = "C-" + RandomStringUtils.randomAlphanumeric(7).toUpperCase();
            MembreMorale membreMorale = new MembreMorale()
                    .setCodeTypeActeur("OSE")
                    .setRaisonSociale(centre.getLibelleCentre())
                    .setNumRegistreMembre(refCentre)
                    .setBpMembre(centre.getBpCentre())
                    .setTelMembre(centre.getTelephoneCentre())
                    .setPortableMembre(centre.getTelephoneCentre())
                    .setQuartierMembre(centre.getAdresseCentre())
                    .setIdUtilisateur(Objects.requireNonNull(user).getIdUtilisateur())
                    .setEtatMembre("N").setDesactiver(0)
                    .setHeureIdentification(LocalTime.now())
                    .setDateIdentification(new Date());
            EuMembreMorale morale = moraleService.createAdmin(membreMorale);

            EuUtilisateur utilisateur = new EuUtilisateur()
                    .setNomUtilisateur(centre.getLibelleCentre())
                    .setIdUtilisateurParent(user.getIdUtilisateur())
                    .setLogin(login)
                    .setPasswordHash(password)
                    .setPwd(password)
                    .setCentre(user.getCentre())
                    .setChPwdFlog(0)
                    .setCodeMembre(morale.getCodeMembreMorale())
                    .setConnecte(0)
                    .setUlock(0);
            utilisateur = utilisateurService.saveUser(utilisateur, utilisateur.getPasswordHash());

            EuUserRolesPermission userRole = new EuUserRolesPermission();
            userRole.setUtilisateur(utilisateur);
            userRole.setResponsabilite(1);
            userRole.setCodeResponsabilite("CA");
            userRole.setLibelleResponsabilite("Responsable Centre");
            userRole.setEuRole(roleService.getByCode("CA"));
            userRole.setDateAffectationRoles(LocalDateTime.now());
            userRolesPermissionService.create(userRole);

            EuCentres centres = centre.toEuCentres();
            centres.setDateCentres(LocalDate.now());
            centres.setReferenceCentre(refCentre);
            centres.setUser(user);
            centres.setMembre(morale);
            if (CANTON.name().equals(typeCentre)) {
                centres.setCanton(cantonService.getById(id));
            } else if (REGION.name().equals(typeCentre)) {
                centres.setRegion(regionservice.findById(id));
            } else if (PREFECTURE.name().equals(typeCentre)) {
                centres.setPrefecture(prefectureService.findById(id));
            } else if (PAYS.name().equals(typeCentre)) {
                centres.setPays(paysService.findById(id));
            } else {
                centres.setZone(zoneService.findById(id));
            }
            centres.setCentreParent(user.getCentre());
            if (centre.getSurfaceCentre() == null) {
                centre.setSurfaceCentre(0.0);
            }
            centresService.create(centres);
            redirect.addFlashAttribute("message", "Centre Bien créé");
            return "redirect:/centre/list";
        } catch (Exception e) {
            getLog().error("Echec de la creation du centre:", e);
            model.addAttribute("centre", centre);
            model.addAttribute("typeCentre", typeCentre);
            model.addAttribute("login", login);
            model.addAttribute("password", password);
            model.addAttribute("passwordConf", passwordConf);
            model.addAttribute("idlocal", id);
            model.addAttribute("message", e.getMessage());
            return "admin/addcentre";
        }
    }

    @GetMapping(value = "list")
    public String newCentre(@ModelAttribute("centre") Centres centre, Model model) {
        List<String> typeCentres = Lists.newArrayList();
        Arrays.asList(values()).forEach(t -> typeCentres.add(t.name()));
        model.addAttribute(typeCentres);
        return "admin/centre";
    }

    @GetMapping(value = "typeCentres")
    public ResponseEntity<?> listCentres() {
        List<Reponse> reps = Lists.newArrayList();
        Arrays.asList(values()).forEach(t -> reps.add(new Reponse(t.name())));
        return ResponseEntity.ok(reps);
    }

    @GetMapping(value = "local")
    public ResponseEntity<?> loadLocalite(@RequestParam("typeCentre") String typeCentre) {
        List<JsonResult> results = Lists.newArrayList();
        if (StringUtils.isNotBlank(typeCentre)) {
            TypeCentreEnum val = valueOf(typeCentre);
            switch (val) {
                case PAYS: {
                    List<EuPays> pays = paysService.list();
                    pays.parallelStream().forEach(p -> results.add(new JsonResult(p.getIdPays(), p.getLibellePays())));
                }
                break;
                case REGION: {
                    List<EuRegion> regions = regionservice.list();
                    regions.parallelStream().forEach(r -> results.add(new JsonResult(r.getIdRegion(), r.getNomRegion())));
                }
                break;
                case PREFECTURE: {
                    List<EuPrefecture> prefectures = prefectureService.list();
                    prefectures.forEach(p -> results.add(new JsonResult(p.getIdPrefecture(), p.getNomPrefecture())));
                }
                break;
                case CANTON: {
                    cantonService.getAll().parallelStream().forEach(c -> results.add(new JsonResult(c.getIdCanton(), c.getNomCanton())));
                }
                break;
                case MONDE:
                    break;
                case ZONE: {
                    List<EuZone> zones = zoneService.list();
                    zones.parallelStream().forEach(z -> results.add(new JsonResult(Integer.valueOf(z.getCodeZone()), z.getNomZone())));
                }
                break;
                default:
                    break;
            }
        }
        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "load")
    @ResponseBody
    public Grid<Centres> loadCentre(@RequestParam(value = "page", required = false) Integer page,
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
        Grid<Centres> response = new Grid<>();
        if (Objects.nonNull(utilisateur)) {
            Page<EuCentres> centres = centresService.findByUser(utilisateur.getIdUtilisateur(), pageRequest);
            if (centres.getTotalElements() > 0) {
                response.setRows(centresMapper.fromEuCentresList(centres.getContent()));
                response.setTotal(Integer.toString(centres.getTotalPages()));
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
    public ResponseEntity<?> saveCentre(@ModelAttribute Centres centre,
                                        @RequestParam(name = "typeCentre") String typeCentre, @RequestParam("idlocal") Integer id) {
        try {
            EuUtilisateur user = getCurrentUser();
            if (centre.getId() != null) {
                Optional<EuCentres> optCentre = centresService.fetchById(centre.getId());
                if (optCentre.isPresent()) {
                    EuCentres centres = centre.toEuCentres(optCentre.get());
                    if (CANTON.name().equals(typeCentre)) {
                        centres.setCanton(cantonService.getById(id));
                    } else if (REGION.name().equals(typeCentre)) {
                        centres.setRegion(regionservice.findById(id));
                    } else if (PREFECTURE.name().equals(typeCentre)) {
                        centres.setPrefecture(prefectureService.findById(id));
                    } else if (PAYS.name().equals(typeCentre)) {
                        centres.setPays(paysService.findById(id));
                    } else {
                        centres.setZone(zoneService.findById(id));
                    }
                    centres.setDateCentres(LocalDate.now());
                    if (StringUtils.isBlank(centres.getReferenceCentre())) {
                        centres.setReferenceCentre("C-" + RandomStringUtils.randomAlphanumeric(7).toUpperCase());
                    }
                    EuCentres agence = centresService.update(centres);
                    return new ResponseEntity<EuCentres>(agence, HttpStatus.CREATED);
                } else {
                    throw new NotFoundException("Centre N° " + centre.getId() + " non trouvé");
                }
            } else {
                EuCentres centres = centre.toEuCentres();
                centres.setDateCentres(LocalDate.now());
                centres.setReferenceCentre("C-" + RandomStringUtils.randomAlphanumeric(7).toUpperCase());
                centres.setUser(user);
                if (CANTON.name().equals(typeCentre)) {
                    centres.setCanton(cantonService.getById(id));
                } else if (REGION.name().equals(typeCentre)) {
                    centres.setRegion(regionservice.findById(id));
                } else if (PREFECTURE.name().equals(typeCentre)) {
                    centres.setPrefecture(prefectureService.findById(id));
                } else if (PAYS.name().equals(typeCentre)) {
                    centres.setPays(paysService.findById(id));
                } else {
                    centres.setZone(zoneService.findById(id));
                }
                centres = centresService.create(centres);
                return new ResponseEntity<Centres>(new Centres(centres), HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<Result>(new Result(0, "Erreur d'execution: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "del")
    public String delCentre(@RequestParam("id") Integer id) {
        if (id != null) {
            centresService.removeById(id);
            return "ok";
        }
        return "ko";
    }

}

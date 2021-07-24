package com.esmc.mcnp.web.controller.odd;

import com.esmc.mcnp.dto.security.User;
import com.esmc.mcnp.mapper.odd.AgenceOddMapper;
import com.esmc.mcnp.mapper.odd.CentresMapper;
import com.esmc.mcnp.mapper.security.GroupeRolesMapper;
import com.esmc.mcnp.mapper.security.RolesMapper;
import com.esmc.mcnp.mapper.security.UserMapper;
import com.esmc.mcnp.model.security.EuRoles;
import com.esmc.mcnp.model.security.EuUserRolesPermission;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.services.odd.EuAgenceOddService;
import com.esmc.mcnp.services.odd.EuCentresService;
import com.esmc.mcnp.services.security.*;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.util.JqGrid;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/agent")
public class AgentController extends BaseController {
    private final EuUtilisateurService utilisateurService;
    private final UserMapper userMapper;
    private final EuRolesService rolesService;
    private final RolesMapper rolesMapper;
    private final EuUserRolesPermissionService userRolesPermissionService;
    private final EuGroupeRolesService groupeRolesService;
    private final GroupeRolesMapper groupeRolesMapper;
    private final EuUserGroupService userGroupService;
    private final EuAgenceOddService agenceOddService;
    private final AgenceOddMapper agenceOddMapper;
    private final EuCentresService centresService;
    private final CentresMapper centresMapper;

    public AgentController(EuUtilisateurService utilisateurService, UserMapper userMapper,
                           EuRolesService rolesService, RolesMapper rolesMapper,
                           EuUserRolesPermissionService userRolesPermissionService, EuGroupeRolesService groupeRolesService, GroupeRolesMapper groupeRolesMapper, EuUserGroupService userGroupService, EuAgenceOddService agenceOddService, AgenceOddMapper agenceOddMapper, EuCentresService centresService, CentresMapper centresMapper) {
        this.utilisateurService = utilisateurService;
        this.userMapper = userMapper;
        this.rolesService = rolesService;
        this.rolesMapper = rolesMapper;
        this.userRolesPermissionService = userRolesPermissionService;
        this.groupeRolesService = groupeRolesService;
        this.groupeRolesMapper = groupeRolesMapper;
        this.userGroupService = userGroupService;
        this.agenceOddService = agenceOddService;
        this.agenceOddMapper = agenceOddMapper;
        this.centresService = centresService;
        this.centresMapper = centresMapper;
    }

    @GetMapping(value = "agents")
    public String listAgents() {
        return "admin/agents";
    }

    @RequestMapping(value = "/list")
    public @ResponseBody
    JqGrid<EuUtilisateur> listerAgents(@RequestParam(value = "_search", required = false) Boolean search,
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
            rows = 20;
        }
        PageRequest pageRequest = null;
        if (sort != null) {
            pageRequest = PageRequest.of(page - 1, rows, sort);
        } else {
            pageRequest = PageRequest.of(page - 1, rows);
        }
        EuUtilisateur utilisateur = getCurrentUser();
        JqGrid<EuUtilisateur> response = new JqGrid<>();
        if (Objects.nonNull(utilisateur)) {
            Page<EuUtilisateur> euUtilisateurs = utilisateurService.loadAllByParent(utilisateur.getIdUtilisateur(), pageRequest);
            if (euUtilisateurs.getTotalElements() > 0) {
                List<EuUtilisateur> utilisateurs = euUtilisateurs.getContent();
                response.setRows(utilisateurs);
                response.setRecords(Long.toString(euUtilisateurs.getTotalElements()));
                response.setTotal(Integer.toString(euUtilisateurs.getTotalPages()));
                response.setPage(Integer.toString(euUtilisateurs.getNumber() + 1));
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

    @GetMapping(value = "add")
    public String addingAgent(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("ugroupes", userGroupService.getAll());
        model.addAttribute("groupes", groupeRolesService.getAll());
        model.addAttribute("roles", rolesService.getAll());
        return "admin/agent";
    }

    @PostMapping(value = "add")
    @Transactional
    public String addAgent(@ModelAttribute(name = "user") User user,
                                 @RequestParam(name = "pwdConfirm") String pwdConfirm,
                                 @RequestParam(required = false) Integer role,
                                 @RequestParam(required = false) String groupe,
                                 @RequestParam(required = false) Integer responsable,
                                 @RequestParam(required = false) String codeResponsabilite,
                                 @RequestParam(required = false) String nomResponsabilite,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirect) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            result.getAllErrors().forEach(e -> builder.append(e.getDefaultMessage() + "\n"));
            model.addAttribute("message", builder.toString());
            return "/error";
        }
        if (pwdConfirm.isBlank() || !user.getPwd().equals(pwdConfirm)) {
            model.addAttribute("ugroupes", userGroupService.getAll());
            model.addAttribute("groupes", groupeRolesService.getAll());
            model.addAttribute("roles", rolesService.getAll());
            model.addAttribute("groupe", groupe);
            model.addAttribute("role", role);
            model.addAttribute("message", "Les mots de passe entrés ne correspondent pas");
            return "admin/agent";
        }
        if (role == null) {
            model.addAttribute("ugroupes", userGroupService.getAll());
            model.addAttribute("groupes", groupeRolesService.getAll());
            model.addAttribute("roles", rolesService.getAll());
            model.addAttribute("groupe", groupe);
            model.addAttribute("role", role);
            model.addAttribute("message", "Veuillez choisir le rôle de l'utilisateur");
            return "admin/agent";
        }
        try {
            var roles = new EuRoles();
            var currentuser = getCurrentUser();
            EuUtilisateur utilisateur = userMapper.toEuUtilisateur(user);
            if (StringUtils.isNotBlank(groupe)) {
                utilisateur.setEuUserGroup(userGroupService.getById(groupe));
            }
            if (currentuser.getAgencesOdd() != null) {
                utilisateur.setAgencesOdd(agenceOddService.getById(user.getAgencesOdd().getIdAgencesOdd()));
            } else {
                utilisateur.setAgencesOdd(null);
            }
            if (currentuser.getCentre() != null) {
                utilisateur.setCentre(centresService.getById(user.getCentre().getIdCentres()));
            } else {
                utilisateur.setCentre(null);
            }
            user.setIdUtilisateurParent(currentuser.getIdUtilisateur());
            utilisateur = utilisateurService.saveUser(utilisateur, utilisateur.getPwd());
            EuUserRolesPermission userRole = new EuUserRolesPermission();
            userRole.setUtilisateur(utilisateur);
            if (responsable == null) {
                userRole.setResponsabilite(0);
            } else {
                userRole.setResponsabilite(responsable);
            }
            userRole.setCodeResponsabilite(codeResponsabilite);
            userRole.setLibelleResponsabilite(nomResponsabilite);
            userRole.setEuRole(rolesService.getById(role));
            userRole.setDateAffectationRoles(LocalDateTime.now());
            userRolesPermissionService.create(userRole);
            return "redirect:/agent/agents";
        } catch (RuntimeException e) {
            //e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            model.addAttribute("ugroupes", userGroupService.getAll());
            model.addAttribute("groupes", groupeRolesService.getAll());
            model.addAttribute("groupe", groupe);
            model.addAttribute("role", role);
            return "admin/agent";
        }
    }
}

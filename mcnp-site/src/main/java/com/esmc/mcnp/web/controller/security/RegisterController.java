package com.esmc.mcnp.web.controller.security;

import com.esmc.mcnp.dto.projections.AgencesOddVO;
import com.esmc.mcnp.dto.projections.CentreVO;
import com.esmc.mcnp.dto.projections.RolesVO;
import com.esmc.mcnp.dto.security.GroupeRoles;
import com.esmc.mcnp.dto.security.User;
import com.esmc.mcnp.dto.security.UserGroupDTO;
import com.esmc.mcnp.mapper.odd.AgenceOddMapper;
import com.esmc.mcnp.mapper.odd.CentresMapper;
import com.esmc.mcnp.mapper.security.GroupeRolesMapper;
import com.esmc.mcnp.mapper.security.RolesMapper;
import com.esmc.mcnp.mapper.security.UserGroupMapper;
import com.esmc.mcnp.mapper.security.UserMapper;
import com.esmc.mcnp.model.security.EuRoles;
import com.esmc.mcnp.model.security.EuUserRolesPermission;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.services.odd.EuAgenceOddService;
import com.esmc.mcnp.services.odd.EuCentresService;
import com.esmc.mcnp.services.security.*;
import com.esmc.mcnp.web.controller.base.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/admin")
public class RegisterController extends BaseController {

    private final EuUtilisateurService utilisateurService;
    private final UserMapper userMapper;
    private final EuRolesService roleService;
    private final RolesMapper rolesMapper;
    private final EuUserRolesPermissionService userRolesPermissionService;
    private final EuGroupeRolesService groupeRolesService;
    private final GroupeRolesMapper groupeRolesMapper;
    private final EuUserGroupService userGroupService;
    private final UserGroupMapper userGroupMapper;
    private final EuAgenceOddService agenceOddService;
    private final AgenceOddMapper agenceOddMapper;
    private final EuCentresService centresService;
    private final CentresMapper centresMapper;
    private List<GroupeRoles> groupeRoles;
    private List<UserGroupDTO> userGroupDTOS;
    private List<AgencesOddVO> agencesOdds;
    private List<CentreVO> centresList;
    private List<RolesVO> rolesList;

    @Autowired
    public RegisterController(EuUtilisateurService utilisateurService,
                              EuRolesService roleService,
                              EuUserRolesPermissionService userRolesPermissionService,
                              EuGroupeRolesService groupeRolesService,
                              GroupeRolesMapper groupeRolesMapper, UserMapper userMapper,
                              EuUserGroupService userGroupService, UserGroupMapper userGroupMapper,
                              RolesMapper rolesMapper, EuAgenceOddService agenceOddService,
                              AgenceOddMapper agenceOddMapper, EuCentresService centresService,
                              CentresMapper centresMapper) {
        this.utilisateurService = utilisateurService;
        this.roleService = roleService;
        this.userRolesPermissionService = userRolesPermissionService;
        this.groupeRolesService = groupeRolesService;
        this.groupeRolesMapper = groupeRolesMapper;
        this.userMapper = userMapper;
        this.userGroupService = userGroupService;
        this.userGroupMapper = userGroupMapper;
        this.rolesMapper = rolesMapper;
        this.agenceOddService = agenceOddService;
        this.agenceOddMapper = agenceOddMapper;
        this.centresService = centresService;
        this.centresMapper = centresMapper;
    }

    @GetMapping(value = "register")
    public String register(@ModelAttribute("user") User user, Model model) {
        centresList = centresService.getAllVO();
        agencesOdds = agenceOddService.getAll();
        groupeRoles = groupeRolesMapper.fromEuGroupeRolesList(groupeRolesService.listAll());
        userGroupDTOS = userGroupMapper.fromEuUserGroupList(userGroupService.listAll());
        rolesList = roleService.getAll();
        model.addAttribute("ugroupes", userGroupDTOS);
        model.addAttribute("groupes", groupeRoles);
        model.addAttribute("agencesOdds", agencesOdds);
        model.addAttribute("centres", centresList);
        model.addAttribute("roles", rolesList);
        return "signup/register";
    }

    @GetMapping(value = "rolesUser")
    public ResponseEntity<?> loadRoles(@RequestParam(name = "id", required = false) Integer id) {
        if (id != null) {
            return ResponseEntity.ok(rolesMapper.fromEuRolesList(roleService.findByGroupeRole(id)));
        } else {
            return ResponseEntity.ok(rolesMapper.fromEuRolesList(roleService.listAll()));
        }
    }

    @GetMapping(value = "agenceOdd")
    public ResponseEntity<?> loadAgenceOdd(@RequestParam(name = "id", required = false) Integer id) {
        if (id != null) {
            return ResponseEntity.ok(agenceOddMapper.fromEuAgencesOddList(agenceOddService.findByCentres(id)));
        } else {
            return ResponseEntity.ok(agenceOddMapper.fromEuAgencesOddList(agenceOddService.listAll()));
        }
    }

    @PostMapping(value = "register")
    @Transactional
    public String addUtilisateur(@ModelAttribute(name = "user") User user,
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
            centresList = centresService.getAllVO();
            agencesOdds = agenceOddService.getAll();
            groupeRoles = groupeRolesMapper.fromEuGroupeRolesList(groupeRolesService.listAll());
            userGroupDTOS = userGroupMapper.fromEuUserGroupList(userGroupService.listAll());
            rolesList = roleService.getAll();
            model.addAttribute("ugroupes", userGroupDTOS);
            model.addAttribute("groupes", groupeRoles);
            model.addAttribute("agencesOdds", agencesOdds);
            model.addAttribute("centres", centresList);
            model.addAttribute("roles", rolesList);
            model.addAttribute("message", "Les mots de passe entrés ne correspondent pas");
            return "signup/register";
        }
        if (role == null) {
            centresList = centresService.getAllVO();
            agencesOdds = agenceOddService.getAll();
            groupeRoles = groupeRolesMapper.fromEuGroupeRolesList(groupeRolesService.listAll());
            userGroupDTOS = userGroupMapper.fromEuUserGroupList(userGroupService.listAll());
            rolesList = roleService.getAll();
            model.addAttribute("ugroupes", userGroupDTOS);
            model.addAttribute("groupes", groupeRoles);
            model.addAttribute("agencesOdds", agencesOdds);
            model.addAttribute("centres", centresList);
            model.addAttribute("roles", rolesList);
            model.addAttribute("message", "Veuillez choisir le rôle de l'utilisateur");
            return "signup/register";
        }
        try {
            var roles = new EuRoles();
            EuUtilisateur utilisateur = userMapper.toEuUtilisateur(user);
            if (StringUtils.isNotBlank(groupe)) {
                utilisateur.setEuUserGroup(userGroupService.getById(groupe));
            }
            if (user.getAgencesOdd().getIdAgencesOdd() != null) {
                utilisateur.setAgencesOdd(agenceOddService.getById(user.getAgencesOdd().getIdAgencesOdd()));
            } else {
                utilisateur.setAgencesOdd(null);
            }
            if (user.getCentre().getIdCentres() != null) {
                utilisateur.setCentre(centresService.getById(user.getCentre().getIdCentres()));
            } else {
                utilisateur.setCentre(null);
            }
            user.setIdUtilisateurParent(Objects.requireNonNull(getCurrentUser()).getIdUtilisateur());
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
            userRole.setEuRole(roleService.getById(role));
            userRole.setDateAffectationRoles(LocalDateTime.now());
            userRolesPermissionService.create(userRole);
            return "redirect:/admin/utilisateur";
        } catch (RuntimeException e) {
            //e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            model.addAttribute("ugroupes", userGroupDTOS);
            model.addAttribute("groupes", groupeRoles);
            model.addAttribute("groupe", groupe);
            model.addAttribute("role", role);
            return "signup/register";
        }
    }

}

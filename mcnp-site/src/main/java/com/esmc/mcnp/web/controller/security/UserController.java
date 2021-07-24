package com.esmc.mcnp.web.controller.security;

import com.esmc.mcnp.UserSecurity;
import com.esmc.mcnp.dto.security.GroupeRoles;
import com.esmc.mcnp.dto.security.User;
import com.esmc.mcnp.dto.vue.RoleDTO;
import com.esmc.mcnp.mapper.security.GroupeRolesMapper;
import com.esmc.mcnp.mapper.security.RolesMapper;
import com.esmc.mcnp.mapper.security.UserGroupMapper;
import com.esmc.mcnp.mapper.security.UserMapper;
import com.esmc.mcnp.model.security.EuGroupeRoles;
import com.esmc.mcnp.model.security.EuRoles;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.services.odd.EuAgenceOddService;
import com.esmc.mcnp.services.odd.EuCentresService;
import com.esmc.mcnp.services.security.*;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.dto.util.Result;
import com.esmc.mcnp.util.JqGrid;
import com.esmc.mcnp.util.SecurityUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/admin")
public class UserController extends BaseController {

    private final EuUtilisateurService utilisateurService;
    private final UserMapper userMapper;
    private final EuRolesService rolesService;
    private RolesMapper rolesMapper;
    private final EuGroupeRolesService groupeRolesService;
    private final GroupeRolesMapper groupeRolesMapper;
    private final EuCentresService centresService;
    private final EuAgenceOddService agenceOddService;
    private final EuUserGroupService userGroupService;
    private final UserGroupMapper userGroupMapper;
    private final EuUserRolesPermissionService userRolesPermissionService;

    @Autowired
    public UserController(EuUtilisateurService utilisateurService,
                          UserMapper userMapper, EuRolesService rolesService,
                          RolesMapper rolesMapper, EuGroupeRolesService groupeRolesService,
                          GroupeRolesMapper groupeRolesMapper, EuCentresService centresService,
                          EuAgenceOddService agenceOddService, EuUserGroupService userGroupService,
                          UserGroupMapper userGroupMapper, EuUserRolesPermissionService userRolesPermissionService) {
        this.utilisateurService = utilisateurService;
        this.rolesService = rolesService;
        this.userMapper = userMapper;
        this.rolesMapper = rolesMapper;
        this.groupeRolesService = groupeRolesService;
        this.groupeRolesMapper = groupeRolesMapper;
        this.centresService = centresService;
        this.agenceOddService = agenceOddService;
        this.userGroupService = userGroupService;
        this.userGroupMapper = userGroupMapper;
        this.userRolesPermissionService = userRolesPermissionService;
    }

    @GetMapping(value = "index")
    public String index() {
        return "admin/index";
    }

    @GetMapping(value = "utilisateur")
    public String listUtilisateurs(Model model) {
        model.addAttribute("ugroupes", userGroupMapper.fromEuUserGroupList(userGroupService.listAll()));
        model.addAttribute("agencesOdds", agenceOddService.getAll());
        model.addAttribute("centres", centresService.getAllVO());
        model.addAttribute("roles", rolesService.getAll());
        return "signup/utilisateur";
    }

    @GetMapping(value = "groupes")
    public String listGroupeRoles() {
        return "signup/grouperoles";
    }

    @GetMapping(value = "findUser/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        try {
            if (id != null) {
                EuUtilisateur utilisateur = utilisateurService.getOne(id);
                User user = userMapper.fromEuUtilisateur(utilisateur);
                List<EuRoles> rolesList = userRolesPermissionService.findByUser(utilisateur.getIdUtilisateur());
                if (!rolesList.isEmpty()) {
                    user.setRoles(RoleDTO.toRoles(rolesList));
                }
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.badRequest().body(new Result(1, "L'utilisateur est introuvable"));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result(1, "Echec de la recherche:" + e.getMessage()));
        }
    }

    @RequestMapping(value = "/ulist")
    public @ResponseBody
    JqGrid<User> listutilisateurs(@RequestParam(value = "_search", required = false) Boolean search,
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
        JqGrid<User> response = new JqGrid<>();
        if (Objects.nonNull(utilisateur)) {
            Page<EuUtilisateur> euUtilisateurs = null;
            if (SecurityUtil.isUserInRole(utilisateur.getPermissions(), "ADMIN")) {
                euUtilisateurs = utilisateurService.getAll(pageRequest);
            } else {
                euUtilisateurs = utilisateurService.findByParent(utilisateur.getIdUtilisateur(), pageRequest);
            }
            if (euUtilisateurs.getTotalElements() > 0) {
                response.setRows(userMapper.fromUtilisateurs(euUtilisateurs.getContent()));
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

    @RequestMapping(value = "groupeRoles")
    public @ResponseBody
    JqGrid<GroupeRoles> listGroupes(@RequestParam(value = "_search", required = false) Boolean search,
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

        EuUtilisateur utilisateur = (UserSecurity) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        JqGrid<GroupeRoles> response = new JqGrid<>();
        if (Objects.nonNull(utilisateur)) {

            Page<EuGroupeRoles> euGroupeRoles = groupeRolesService.listAll(pageRequest);
            if (euGroupeRoles.getTotalElements() > 0) {
                List<GroupeRoles> groupeRoles = groupeRolesMapper.fromEuGroupeRolesList(Lists.newArrayList(euGroupeRoles.iterator()));
                response.setRows(groupeRoles);
                response.setRecords(Long.toString(euGroupeRoles.getTotalElements()));
                response.setTotal(Integer.toString(euGroupeRoles.getTotalPages()));
                response.setPage(Integer.toString(euGroupeRoles.getNumber() + 1));
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

    @Transactional
    @PostMapping(value = "groupeRoles")
    public ResponseEntity<?> addGroupeRole(@RequestParam(required = false) Integer id, @RequestParam String libelleGroupeRoles) {
        if (StringUtils.isNotBlank(libelleGroupeRoles)) {
            if (id == 0) id = null;
            EuGroupeRoles groupe = groupeRolesMapper.toEuGroupeRoles(new GroupeRoles(id, libelleGroupeRoles));
            try {
                return ResponseEntity.ok(groupeRolesMapper.fromEuGroupeRoles(groupeRolesService.create(groupe)));
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Veuillez envoyer des données corectes");
        }
    }

    @PostMapping(value = "editUser")
    public ResponseEntity<?> editUser(User user) {
        try {
            return ResponseEntity.ok(new Result(0, "Mise à jour bien effectué"));
        } catch (RuntimeException e) {
            getLog().error("Echec de la mise àjour :", e);
            return ResponseEntity.ok(new Result(0, "Echec de la Mise à jour :" + e.getMessage()));
        }
    }

}

package com.esmc.mcnp.web.controller.security;

import com.esmc.mcnp.UserSecurity;
import com.esmc.mcnp.dto.security.GroupeRoles;
import com.esmc.mcnp.dto.security.Permission;
import com.esmc.mcnp.dto.security.Roles;
import com.esmc.mcnp.mapper.security.GroupeRolesMapper;
import com.esmc.mcnp.mapper.security.PermissionMapper;
import com.esmc.mcnp.mapper.security.RolesMapper;
import com.esmc.mcnp.model.security.*;
import com.esmc.mcnp.services.security.EuGroupeRolesService;
import com.esmc.mcnp.services.security.EuPermissionService;
import com.esmc.mcnp.services.security.EuRolePermissionService;
import com.esmc.mcnp.services.security.EuRolesService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.dto.util.Result;
import com.esmc.mcnp.util.JqGrid;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping(value = "/admin")
public class RoleController extends BaseController {

    private final EuRolesService rolesService;
    private final RolesMapper rolesMapper;
    private final EuPermissionService permissionService;
    private final PermissionMapper permissionMapper;
    private final EuGroupeRolesService groupeRolesService;
    private final GroupeRolesMapper groupeRolesMapper;
    private EuRolePermissionService rolePermissionService;

    private List<Permission> permissions;
    private List<GroupeRoles> groupeRoles;
    private List<Roles> roles;

    @Autowired
    public RoleController(EuRolesService rolesService,
                          RolesMapper rolesMapper,
                          EuPermissionService permissionService,
                          PermissionMapper permissionMapper,
                          EuGroupeRolesService groupeRolesService,
                          GroupeRolesMapper groupeRolesMapper,
                          EuRolePermissionService rolePermissionService) {
        this.rolesService = rolesService;
        this.rolesMapper = rolesMapper;
        this.permissionService = permissionService;
        this.permissionMapper = permissionMapper;
        this.groupeRolesService = groupeRolesService;
        this.groupeRolesMapper = groupeRolesMapper;
        this.rolePermissionService = rolePermissionService;
    }

    @GetMapping(value = "role")
    public String addRole(@ModelAttribute("role") Roles role, Model model) throws ExecutionException, InterruptedException {
        CompletableFuture<List<EuGroupeRoles>> cpGroupes = groupeRolesService.loadAllAsync();
        CompletableFuture<List<EuRoles>> cpRoles = rolesService.loadAllAsync();
        CompletableFuture<List<EuPermission>> cpPermissions = permissionService.loadAllAsync();
        CompletableFuture.allOf(cpGroupes, cpPermissions, cpRoles).join();
        permissions = permissionMapper.fromEuPermissionList(cpPermissions.get());
        groupeRoles = groupeRolesMapper.fromEuGroupeRolesList(cpGroupes.get());
        roles = rolesMapper.fromEuRolesList(cpRoles.get());
        model.addAttribute("roles", roles);
        model.addAttribute("permissions", permissions);
        model.addAttribute("groupeRoles", groupeRoles);
        return "signup/role";
    }

    @Transactional
    @PostMapping(value = "/role")
    public String saveRole(@ModelAttribute("role") Roles role,
                           @RequestParam(name = "permissions", required = false) Integer[] permissions,
                           Model model, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            result.getAllErrors().forEach(e -> builder.append(e.getDefaultMessage() + "\n"));
            model.addAttribute("message", builder.toString());
            model.addAttribute("groupeRoles", groupeRoles);
            model.addAttribute("permissions", permissions);
            model.addAttribute("roles", roles);
            return "signup/role";
        }
        try {
            System.out.println("Entering try");
            EuGroupeRoles groupeRoles = groupeRolesService.getById(role.getGroupeRoles().getId());
            EuRoles roles = rolesMapper.toEuroles(role);
            if (role.getRoleParent().getId() != null) {
                roles.setParent(rolesService.getById(role.getRoleParent().getId()));
            } else {
                roles.setParent(null);
            }
            roles.setDateRoles(LocalDate.now());
            AtomicReference<EuRolePermission> rolePermission = new AtomicReference<>();
            if (permissions != null && permissions.length > 0) {
                List<EuPermission> euPermissions = permissionService.listAllByIds(Arrays.asList(permissions));
                euPermissions.forEach(p -> {
                    rolePermission.set(new EuRolePermission());
                    rolePermission.get().setPermission(p);
                    roles.addRolePermission(rolePermission.get());
                });
            }
            roles.setGroupeRoles(groupeRoles);
            rolesService.create(roles);
            return "redirect:/admin/roles";
        } catch (RuntimeException e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            model.addAttribute("groupeRoles", groupeRoles);
            model.addAttribute("permissions", permissions);
            model.addAttribute("roles", roles);
            return "signup/role";
        }
    }

    @PostMapping(value = "edit")
    public ResponseEntity<?> editRole(Roles role) {
        try {
            if (Objects.nonNull(role) && role.getId() != null) {
                EuRoles euRole = rolesMapper.toEuroles(role);
                if (role.getRoleParent().getId() != null) {
                    euRole.setParent(rolesService.getById(role.getRoleParent().getId()));
                }
                if (role.getGroupeRoles().getId() != null) {
                    euRole.setGroupeRoles(groupeRolesService.getById(role.getGroupeRoles().getId()));
                }
                euRole.setDateRoles(LocalDate.now());
                rolesService.update(euRole);
                return ResponseEntity.ok().body(new Result(0, "Role bien mis à jour"));
            } else {
                return ResponseEntity.ok().body(new Result(0, "Echec de la mise à jour du Role"));
            }
        } catch (Exception e) {
            getLog().error("Echec de la mise à jour du Role", e);
            return ResponseEntity.ok().body(new Result(0, "Echec de la mise à jour du Role"));
        }
    }

    @RequestMapping(value = "roles")
    public String listeRoles(Model model) {
        model.addAttribute("roles", rolesService.getAll());
        model.addAttribute("groupeRoles", groupeRolesService.getAll());
        return "signup/roles";
    }

    @RequestMapping(value = "/listroles")
    public @ResponseBody
    JqGrid<Roles> listroles(@RequestParam(value = "_search", required = false) Boolean search,
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
        JqGrid<Roles> response = new JqGrid<>();
        if (Objects.nonNull(utilisateur)) {

            Page<EuRoles> rolesPage = rolesService.listAll(pageRequest);
            if (rolesPage.getTotalElements() > 0) {
                List<Roles> rolesList = rolesMapper.fromEuRolesList(Lists.newArrayList(rolesPage.iterator()));
                response.setRows(rolesList);
                response.setRecords(Long.toString(rolesPage.getTotalElements()));
                response.setTotal(Integer.toString(rolesPage.getTotalPages()));
                response.setPage(Integer.toString(rolesPage.getNumber() + 1));
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

}

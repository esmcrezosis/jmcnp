package com.esmc.mcnp.services.security;

import com.esmc.mcnp.model.security.EuRoles;
import com.esmc.mcnp.model.security.EuUserRolesPermission;
import com.esmc.mcnp.services.base.CrudService;

import java.util.List;

public interface EuUserRolesPermissionService extends CrudService<EuUserRolesPermission, Integer> {
    List<EuRoles> findByUser(Long idUser);
}

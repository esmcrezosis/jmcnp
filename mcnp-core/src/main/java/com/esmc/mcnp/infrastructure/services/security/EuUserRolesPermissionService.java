package com.esmc.mcnp.infrastructure.services.security;

import com.esmc.mcnp.domain.entity.security.EuRoles;
import com.esmc.mcnp.domain.entity.security.EuUserRolesPermission;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

import java.util.List;

public interface EuUserRolesPermissionService extends CrudService<EuUserRolesPermission, Integer> {
    List<EuRoles> findByUser(Long idUser);
}

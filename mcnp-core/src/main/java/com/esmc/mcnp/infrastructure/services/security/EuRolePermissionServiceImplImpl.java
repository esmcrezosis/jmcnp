package com.esmc.mcnp.infrastructure.services.security;

import com.esmc.mcnp.dao.repository.security.EuRolePermissionRepository;
import com.esmc.mcnp.domain.entity.security.EuRolePermission;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class EuRolePermissionServiceImplImpl extends CrudServiceImpl<EuRolePermission, Integer> implements EuRolePermissionService {

    private EuRolePermissionRepository rolePermissionRepository;

    protected EuRolePermissionServiceImplImpl(EuRolePermissionRepository rolePermissionRepository) {
        super(rolePermissionRepository);
    }
}

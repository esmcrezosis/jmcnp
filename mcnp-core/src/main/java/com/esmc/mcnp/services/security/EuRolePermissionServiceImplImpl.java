package com.esmc.mcnp.services.security;

import com.esmc.mcnp.model.security.EuRolePermission;
import com.esmc.mcnp.repositories.security.EuRolePermissionRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EuRolePermissionServiceImplImpl extends CrudServiceImpl<EuRolePermission, Integer> implements EuRolePermissionService {

    private EuRolePermissionRepository rolePermissionRepository;

    protected EuRolePermissionServiceImplImpl(EuRolePermissionRepository rolePermissionRepository) {
        super(rolePermissionRepository);
    }
}

package com.esmc.mcnp.infrastructure.services.security;

import java.util.List;

import com.esmc.mcnp.domain.entity.security.EuRole;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuRoleService extends BaseService<EuRole, Integer> {

	public EuRole saveRole(EuRole role);

	public List<EuRole> findAll();
}

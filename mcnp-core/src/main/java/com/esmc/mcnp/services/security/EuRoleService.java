package com.esmc.mcnp.services.security;

import java.util.List;

import com.esmc.mcnp.model.security.EuRole;
import com.esmc.mcnp.services.base.BaseService;

public interface EuRoleService extends BaseService<EuRole, Integer> {

	public EuRole saveRole(EuRole role);

	public List<EuRole> findAll();
}

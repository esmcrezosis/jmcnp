package com.esmc.mcnp.infrastructure.services.security;

import com.esmc.mcnp.dao.repository.security.EuUserRolesPermissionRepository;
import com.esmc.mcnp.domain.entity.security.EuRoles;
import com.esmc.mcnp.domain.entity.security.EuUserRolesPermission;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EuUserRolesPermissionServiceImplImpl extends CrudServiceImpl<EuUserRolesPermission, Integer>
		implements EuUserRolesPermissionService {

	private EuUserRolesPermissionRepository userRolesPermissionRepository;
	
	protected EuUserRolesPermissionServiceImplImpl(EuUserRolesPermissionRepository userRolesPermissionRepository) {
		super(userRolesPermissionRepository);
		this.userRolesPermissionRepository = userRolesPermissionRepository;
	}

	@Override
	public List<EuRoles> findByUser(Long idUser) {
		return userRolesPermissionRepository.findByUser(idUser);
	}
}

package com.esmc.mcnp.services.security;

import com.esmc.mcnp.model.security.EuRoles;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.security.EuUserRolesPermission;
import com.esmc.mcnp.repositories.security.EuUserRolesPermissionRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

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

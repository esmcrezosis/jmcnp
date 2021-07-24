package com.esmc.mcnp.services.security;

import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.security.EuPermission;
import com.esmc.mcnp.repositories.security.EuPermissionRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class EuPermissionServiceImplImpl extends CrudServiceImpl<EuPermission, Integer> implements EuPermissionService {
  
	protected EuPermissionServiceImplImpl(EuPermissionRepository permissionRepository) {
		super(permissionRepository);
		this.permissionRepository = permissionRepository;
	}

	private EuPermissionRepository permissionRepository;


	@Override
	public CompletableFuture<List<EuPermission>> loadAllAsync() {
		return permissionRepository.loadAllAsync();
	}
}

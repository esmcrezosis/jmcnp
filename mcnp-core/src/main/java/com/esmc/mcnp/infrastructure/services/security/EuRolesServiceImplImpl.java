package com.esmc.mcnp.services.security;

import com.esmc.mcnp.dto.projections.RolesVO;
import com.esmc.mcnp.model.security.EuRoles;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.repositories.security.EuRolesRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class EuRolesServiceImplImpl extends CrudServiceImpl<EuRoles, Integer> implements EuRolesService {

	private EuRolesRepository rolesRepository;

	protected EuRolesServiceImplImpl(EuRolesRepository rolesRepository) {
		super(rolesRepository);
		this.rolesRepository = rolesRepository;
	}

	@Override
	public List<EuRoles> findByGroupeRole(Integer idGroupeRole) {
		return rolesRepository.findByGroupeRoles_IdGroupeRoles(idGroupeRole);
	}

	@Override
	public CompletableFuture<List<EuRoles>> loadAllAsync() {
		return rolesRepository.loadAllAsync();
	}

	@Override
	public EuRoles getByCode(String codeRole) {
		return rolesRepository.getByCodeRoles(codeRole);
	}

	@Override
	public List<RolesVO> getAll() {
		return rolesRepository.getAllBy();
	}
}

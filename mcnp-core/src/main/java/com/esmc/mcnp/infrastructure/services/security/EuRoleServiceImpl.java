package com.esmc.mcnp.services.security;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.security.EuRole;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.security.RoleRepository;

@Service("euRoleService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuRoleServiceImpl extends BaseServiceImpl<EuRole, Integer> implements EuRoleService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired RoleRepository roleRepo;

	@Override
	@Transactional(readOnly = false)
	public EuRole saveRole(@NotNull EuRole role) {
		try {
			return roleRepo.save(role);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<EuRole> findAll() {
		return roleRepo.findAll();
	}

	@Override
	protected BaseRepository<EuRole, Integer> getRepository() {
		return roleRepo;
	}

}

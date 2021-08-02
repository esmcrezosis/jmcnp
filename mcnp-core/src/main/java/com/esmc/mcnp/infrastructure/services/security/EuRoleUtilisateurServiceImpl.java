package com.esmc.mcnp.infrastructure.services.security;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.security.UserRoleRepository;
import com.esmc.mcnp.domain.entity.security.EuUser;
import com.esmc.mcnp.domain.entity.security.EuUserRole;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euRoleUtilisateurService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuRoleUtilisateurServiceImpl extends BaseServiceImpl<EuUserRole, Long> implements EuRoleUtilisateurService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired UserRoleRepository userRoleRepo;

	@Override
	@Transactional(readOnly = false)
	public EuUserRole saveRoleUtilisateur(@NotNull EuUserRole userRole) {
		try {
			return userRoleRepo.save(userRole);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removeRoleById(Long id) {
		try {
			userRoleRepo.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removeRoleByUtilisateur(EuUser user) {
		return false;
	}

	@Override
	public List<EuUserRole> findAll() {
		return userRoleRepo.findAll();
	}

	@Override
	public List<EuUserRole> findByUtilisateurId(Long id) {
		return null;
	}

	@Override
	public List<EuUserRole> findByUtilisateur(EuUser user) {
		return null;
	}

	@Override
	protected BaseRepository<EuUserRole, Long> getRepository() {
		return userRoleRepo;
	}

}

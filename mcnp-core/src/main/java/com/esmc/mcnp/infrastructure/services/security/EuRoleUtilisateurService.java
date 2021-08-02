package com.esmc.mcnp.infrastructure.services.security;

import java.util.List;

import com.esmc.mcnp.domain.entity.security.EuUser;
import com.esmc.mcnp.domain.entity.security.EuUserRole;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuRoleUtilisateurService extends BaseService<EuUserRole, Long> {
	public EuUserRole saveRoleUtilisateur(EuUserRole userRole);

	public boolean removeRoleById(Long id);

	public boolean removeRoleByUtilisateur(EuUser user);

	public List<EuUserRole> findAll();

	public List<EuUserRole> findByUtilisateurId(Long id);

	public List<EuUserRole> findByUtilisateur(EuUser user);
}

package com.esmc.mcnp.services.security;

import java.util.List;

import com.esmc.mcnp.model.security.EuUser;
import com.esmc.mcnp.model.security.EuUserRole;
import com.esmc.mcnp.services.base.BaseService;

public interface EuRoleUtilisateurService extends BaseService<EuUserRole, Long> {
	public EuUserRole saveRoleUtilisateur(EuUserRole userRole);

	public boolean removeRoleById(Long id);

	public boolean removeRoleByUtilisateur(EuUser user);

	public List<EuUserRole> findAll();

	public List<EuUserRole> findByUtilisateurId(Long id);

	public List<EuUserRole> findByUtilisateur(EuUser user);
}

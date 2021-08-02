package com.esmc.mcnp.infrastructure.services.cmfh;

import com.esmc.mcnp.domain.entity.acteur.EuMembreasso;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuMembreassoService extends BaseService<EuMembreasso, Long> {
	EuMembreasso findByLogin(String login);
	
}

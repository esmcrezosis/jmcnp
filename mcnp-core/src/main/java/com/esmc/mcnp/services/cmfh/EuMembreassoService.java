package com.esmc.mcnp.services.cmfh;

import com.esmc.mcnp.model.acteur.EuMembreasso;
import com.esmc.mcnp.services.base.BaseService;

public interface EuMembreassoService extends BaseService<EuMembreasso, Long> {
	EuMembreasso findByLogin(String login);
	
}

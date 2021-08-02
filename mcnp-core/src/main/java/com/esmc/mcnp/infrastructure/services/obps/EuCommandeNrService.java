package com.esmc.mcnp.infrastructure.services.obps;

import com.esmc.mcnp.domain.entity.obps.EuCommandeNr;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuCommandeNrService extends BaseService<EuCommandeNr, Long> {
	
	public EuCommandeNr findCommandeNrByCodeCommande(String codeCommande);
	
}

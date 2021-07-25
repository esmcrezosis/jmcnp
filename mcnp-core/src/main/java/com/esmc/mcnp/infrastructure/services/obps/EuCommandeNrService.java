package com.esmc.mcnp.services.obps;

import com.esmc.mcnp.model.obps.EuCommandeNr;
import com.esmc.mcnp.services.base.BaseService;

public interface EuCommandeNrService extends BaseService<EuCommandeNr, Long> {
	
	public EuCommandeNr findCommandeNrByCodeCommande(String codeCommande);
	
}

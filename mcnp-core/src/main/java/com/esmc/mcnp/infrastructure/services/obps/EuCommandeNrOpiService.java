package com.esmc.mcnp.infrastructure.services.obps;

import java.util.List;

import com.esmc.mcnp.domain.entity.obps.EuCommandeNrOpi;
import com.esmc.mcnp.domain.entity.obpsd.EuTraite;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuCommandeNrOpiService extends BaseService<EuCommandeNrOpi, Integer> {
	
	public List<EuTraite> findTraiteByCodeCommande(String codeCommande);
}

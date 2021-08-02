package com.esmc.mcnp.infrastructure.services.obps;

import java.util.List;

import com.esmc.mcnp.domain.entity.obps.EuDetailCommande;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuDetailCommandeService extends BaseService<EuDetailCommande, Long> {

	public List<EuDetailCommande> findDetailsCommandeByCodeCommande(Long codeCommande);
	
}

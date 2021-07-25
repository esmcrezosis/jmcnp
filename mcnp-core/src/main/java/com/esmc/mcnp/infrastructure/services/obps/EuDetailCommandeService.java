package com.esmc.mcnp.services.obps;

import java.util.List;

import com.esmc.mcnp.model.obps.EuDetailCommande;
import com.esmc.mcnp.services.base.BaseService;

public interface EuDetailCommandeService extends BaseService<EuDetailCommande, Long> {

	public List<EuDetailCommande> findDetailsCommandeByCodeCommande(Long codeCommande);
	
}

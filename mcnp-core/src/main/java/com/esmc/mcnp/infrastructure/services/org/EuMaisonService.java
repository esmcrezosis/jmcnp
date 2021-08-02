package com.esmc.mcnp.infrastructure.services.org;

import com.esmc.mcnp.domain.entity.acteur.EuMaison;
import com.esmc.mcnp.infrastructure.services.base.BaseService;


public interface EuMaisonService extends BaseService<EuMaison, Long> {

	public EuMaison findMaisonByCodeMembre(String codeMembre);
	
}

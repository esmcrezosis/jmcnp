package com.esmc.mcnp.services.org;

import com.esmc.mcnp.model.acteur.EuMaison;
import com.esmc.mcnp.services.base.BaseService;


public interface EuMaisonService extends BaseService<EuMaison, Long> {

	public EuMaison findMaisonByCodeMembre(String codeMembre);
	
}

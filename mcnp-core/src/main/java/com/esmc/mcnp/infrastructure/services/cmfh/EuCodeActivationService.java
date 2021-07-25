package com.esmc.mcnp.services.cmfh;

import com.esmc.mcnp.model.acteur.EuCodeActivation;
import com.esmc.mcnp.services.base.BaseService;

public interface EuCodeActivationService extends BaseService<EuCodeActivation, Long> {
	EuCodeActivation findByMembre(String codeMembre);
}

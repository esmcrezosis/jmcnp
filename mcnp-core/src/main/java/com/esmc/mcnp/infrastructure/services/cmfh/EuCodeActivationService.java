package com.esmc.mcnp.infrastructure.services.cmfh;

import com.esmc.mcnp.domain.entity.acteur.EuCodeActivation;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuCodeActivationService extends BaseService<EuCodeActivation, Long> {
	EuCodeActivation findByMembre(String codeMembre);
}

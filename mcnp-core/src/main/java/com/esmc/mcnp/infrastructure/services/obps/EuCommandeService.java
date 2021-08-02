package com.esmc.mcnp.infrastructure.services.obps;

import com.esmc.mcnp.domain.entity.obps.EuCommande;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuCommandeService extends BaseService<EuCommande, Long> {
	
    EuCommande findByCodeConfirmation(String codeConfirmation);
}

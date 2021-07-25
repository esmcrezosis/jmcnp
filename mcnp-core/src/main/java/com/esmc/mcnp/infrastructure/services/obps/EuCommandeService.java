package com.esmc.mcnp.services.obps;

import com.esmc.mcnp.model.obps.EuCommande;
import com.esmc.mcnp.services.base.BaseService;

public interface EuCommandeService extends BaseService<EuCommande, Long> {
	
    EuCommande findByCodeConfirmation(String codeConfirmation);
}

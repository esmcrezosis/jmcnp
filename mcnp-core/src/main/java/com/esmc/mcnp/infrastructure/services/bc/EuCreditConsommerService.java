package com.esmc.mcnp.infrastructure.services.bc;

import com.esmc.mcnp.domain.entity.obps.EuCreditConsommer;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuCreditConsommerService extends BaseService<EuCreditConsommer, Long> {
	
    public Long getLastEuConsommationInsertedId();
    
    public Double getSommeDejaConsommerParTypeCreditAndMembre(String codeTypeCredit, String codeMembre);
}

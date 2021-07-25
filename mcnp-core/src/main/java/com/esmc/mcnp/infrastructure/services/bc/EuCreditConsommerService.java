package com.esmc.mcnp.services.bc;

import com.esmc.mcnp.model.obps.EuCreditConsommer;
import com.esmc.mcnp.services.base.BaseService;

public interface EuCreditConsommerService extends BaseService<EuCreditConsommer, Long> {
	
    public Long getLastEuConsommationInsertedId();
    
    public Double getSommeDejaConsommerParTypeCreditAndMembre(String codeTypeCredit, String codeMembre);
}

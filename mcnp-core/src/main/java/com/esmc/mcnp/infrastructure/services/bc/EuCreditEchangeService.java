package com.esmc.mcnp.services.bc;

import com.esmc.mcnp.model.bc.EuCreditEchange;
import com.esmc.mcnp.services.base.BaseService;

public interface EuCreditEchangeService extends BaseService<EuCreditEchange, Long> {
	public Long getLastInsertedId();
}

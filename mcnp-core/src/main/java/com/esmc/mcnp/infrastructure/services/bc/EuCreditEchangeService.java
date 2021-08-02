package com.esmc.mcnp.infrastructure.services.bc;

import com.esmc.mcnp.domain.entity.bc.EuCreditEchange;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuCreditEchangeService extends BaseService<EuCreditEchange, Long> {
	public Long getLastInsertedId();
}

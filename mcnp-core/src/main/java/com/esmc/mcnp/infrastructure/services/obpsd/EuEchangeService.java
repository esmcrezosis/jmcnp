package com.esmc.mcnp.infrastructure.services.obpsd;

import com.esmc.mcnp.domain.entity.obpsd.EuEchange;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuEchangeService extends BaseService<EuEchange, Long> {
	public Long findLastEchangeInsertedId();
}

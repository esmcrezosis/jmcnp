package com.esmc.mcnp.services.obpsd;

import com.esmc.mcnp.model.obpsd.EuEchange;
import com.esmc.mcnp.services.base.BaseService;

public interface EuEchangeService extends BaseService<EuEchange, Long> {
	public Long findLastEchangeInsertedId();
}

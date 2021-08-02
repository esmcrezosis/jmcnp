package com.esmc.mcnp.infrastructure.services.bc;

import com.esmc.mcnp.domain.entity.bc.EuCnpEntree;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuCnpEntreeService extends BaseService<EuCnpEntree, Long> {
	public Long getLastInsertedId();
}

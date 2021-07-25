package com.esmc.mcnp.services.bc;

import com.esmc.mcnp.model.bc.EuCnpEntree;
import com.esmc.mcnp.services.base.BaseService;

public interface EuCnpEntreeService extends BaseService<EuCnpEntree, Long> {
	public Long getLastInsertedId();
}

package com.esmc.mcnp.infrastructure.services.ba;

import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreAppro;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuBonNeutreApproService extends BaseService<EuBonNeutreAppro, Integer> {
	public int getLastInsertedId();
}

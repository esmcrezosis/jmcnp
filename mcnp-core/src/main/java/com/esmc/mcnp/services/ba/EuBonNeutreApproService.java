package com.esmc.mcnp.services.ba;

import com.esmc.mcnp.model.obpsd.EuBonNeutreAppro;
import com.esmc.mcnp.services.base.BaseService;

public interface EuBonNeutreApproService extends BaseService<EuBonNeutreAppro, Integer> {
	public int getLastInsertedId();
}

package com.esmc.mcnp.services.common;

import com.esmc.mcnp.model.bc.EuCycleFormation;
import com.esmc.mcnp.services.base.BaseService;

public interface EuCycleFormationService extends BaseService<EuCycleFormation, Integer> {

	public EuCycleFormation findByCodeformation(String cycle);
}

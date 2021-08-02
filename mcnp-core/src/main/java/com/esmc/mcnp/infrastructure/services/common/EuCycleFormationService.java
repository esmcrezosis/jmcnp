package com.esmc.mcnp.infrastructure.services.common;

import com.esmc.mcnp.domain.entity.bc.EuCycleFormation;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuCycleFormationService extends BaseService<EuCycleFormation, Integer> {

	public EuCycleFormation findByCodeformation(String cycle);
}

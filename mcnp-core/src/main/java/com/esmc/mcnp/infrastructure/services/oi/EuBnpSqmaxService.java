package com.esmc.mcnp.infrastructure.services.oi;

import com.esmc.mcnp.domain.entity.oi.EuBnpSqmax;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuBnpSqmaxService extends BaseService<EuBnpSqmax, Long> {
	public Long findLastSQMaxIdInserted();
}

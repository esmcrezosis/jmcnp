package com.esmc.mcnp.services.oi;

import com.esmc.mcnp.model.oi.EuBnpSqmax;
import com.esmc.mcnp.services.base.BaseService;

public interface EuBnpSqmaxService extends BaseService<EuBnpSqmax, Long> {
	public Long findLastSQMaxIdInserted();
}

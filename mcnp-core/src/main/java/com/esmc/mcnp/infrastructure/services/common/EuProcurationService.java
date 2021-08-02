package com.esmc.mcnp.infrastructure.services.common;

import com.esmc.mcnp.domain.entity.others.EuProcuration;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuProcurationService extends BaseService<EuProcuration, Integer> {
	public EuProcuration findProcurationByCodeMembre(String codeMembre);

}

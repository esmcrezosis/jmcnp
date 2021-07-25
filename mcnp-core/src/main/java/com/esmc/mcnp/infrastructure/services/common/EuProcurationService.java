package com.esmc.mcnp.services.common;

import com.esmc.mcnp.model.others.EuProcuration;
import com.esmc.mcnp.services.base.BaseService;

public interface EuProcurationService extends BaseService<EuProcuration, Integer> {
	public EuProcuration findProcurationByCodeMembre(String codeMembre);

}

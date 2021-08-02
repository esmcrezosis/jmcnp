package com.esmc.mcnp.infrastructure.services.common;

import com.esmc.mcnp.domain.entity.sms.EuSmsSent;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuSmsSentService extends BaseService<EuSmsSent, Long>{

	public Long findMaxInsertedIdSmsSent();
	
}

package com.esmc.mcnp.services.common;

import com.esmc.mcnp.model.sms.EuSmsSent;
import com.esmc.mcnp.services.base.BaseService;

public interface EuSmsSentService extends BaseService<EuSmsSent, Long>{

	public Long findMaxInsertedIdSmsSent();
	
}

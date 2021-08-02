package com.esmc.mcnp.infrastructure.services.common;

import com.esmc.mcnp.domain.entity.sms.EuSms;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuSmsService extends BaseService<EuSms, Long> {

	public Long getLastSmsInserted();
	
}

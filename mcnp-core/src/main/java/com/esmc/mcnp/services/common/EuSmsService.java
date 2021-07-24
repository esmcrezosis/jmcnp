package com.esmc.mcnp.services.common;

import com.esmc.mcnp.model.sms.EuSms;
import com.esmc.mcnp.services.base.BaseService;

public interface EuSmsService extends BaseService<EuSms, Long> {

	public Long getLastSmsInserted();
	
}

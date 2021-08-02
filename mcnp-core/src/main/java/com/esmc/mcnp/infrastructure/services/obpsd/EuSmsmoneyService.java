package com.esmc.mcnp.infrastructure.services.obpsd;

import com.esmc.mcnp.domain.entity.obpsd.EuSmsmoney;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuSmsmoneyService extends BaseService<EuSmsmoney, Long> {
	public double findCreditAmountByCreditcode(String creditCode);

	public EuSmsmoney findByCreditcode(String creditCode);
}

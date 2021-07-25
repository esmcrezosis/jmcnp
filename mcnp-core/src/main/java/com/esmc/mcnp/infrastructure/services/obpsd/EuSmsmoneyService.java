package com.esmc.mcnp.services.obpsd;

import com.esmc.mcnp.model.obpsd.EuSmsmoney;
import com.esmc.mcnp.services.base.BaseService;

public interface EuSmsmoneyService extends BaseService<EuSmsmoney, Long> {
	public double findCreditAmountByCreditcode(String creditCode);

	public EuSmsmoney findByCreditcode(String creditCode);
}

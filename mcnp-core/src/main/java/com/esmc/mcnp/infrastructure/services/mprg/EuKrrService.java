package com.esmc.mcnp.services.mprg;

import com.esmc.mcnp.model.mprg.EuKrr;
import com.esmc.mcnp.services.base.BaseService;

public interface EuKrrService extends BaseService<EuKrr, Long> {
	public EuKrr findByIdCreditAndTypeKrr(long idCredit, String typeKrr);
}

package com.esmc.mcnp.infrastructure.services.mprg;

import com.esmc.mcnp.domain.entity.mprg.EuKrr;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuKrrService extends BaseService<EuKrr, Long> {
	public EuKrr findByIdCreditAndTypeKrr(long idCredit, String typeKrr);
}

package com.esmc.mcnp.infrastructure.services.smcipn;

import java.util.List;

import com.esmc.mcnp.domain.entity.smcipn.EuSmc;
import com.esmc.mcnp.domain.entity.smcipn.EuUtiliser;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuUtiliserService extends BaseService<EuUtiliser, Long> {
	public Long findLastUtiliserInsertedId();

	public List<EuSmc> findByEuSmcipnpwi_CodeSmcipn(String codeSmcipn);

	public List<EuUtiliser> findByIdCredit(Long idCredit);
}

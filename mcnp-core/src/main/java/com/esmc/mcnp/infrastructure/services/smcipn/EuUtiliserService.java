package com.esmc.mcnp.services.smcipn;

import java.util.List;

import com.esmc.mcnp.model.smcipn.EuSmc;
import com.esmc.mcnp.model.smcipn.EuUtiliser;
import com.esmc.mcnp.services.base.BaseService;

public interface EuUtiliserService extends BaseService<EuUtiliser, Long> {
	public Long findLastUtiliserInsertedId();

	public List<EuSmc> findByEuSmcipnpwi_CodeSmcipn(String codeSmcipn);

	public List<EuUtiliser> findByIdCredit(Long idCredit);
}

package com.esmc.mcnp.infrastructure.services.smcipn;

import java.util.List;

import com.esmc.mcnp.domain.entity.smcipn.EuSmc;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuSmcService extends BaseService<EuSmc, Long> {
	public Long getLastEuSmcInsertedId();

	public List<EuSmc> findByOrigineSmc();

	public Double findSumByOrigineSmc();

	public List<EuSmc> findSmcByCodeSmcipn(String codeSmcipn);

}

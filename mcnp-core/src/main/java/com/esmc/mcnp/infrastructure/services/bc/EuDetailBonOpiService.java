package com.esmc.mcnp.infrastructure.services.bc;

import com.esmc.mcnp.domain.entity.obpsd.EuDetailBonOpi;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuDetailBonOpiService extends BaseService<EuDetailBonOpi, Integer> {
	public Integer getLastInsertedId();

	public EuDetailBonOpi findByEuBon_BonNumero(String numeroBon);
	
	public EuDetailBonOpi findByTpagcp(Long idTpagcp);
}

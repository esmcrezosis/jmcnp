package com.esmc.mcnp.services.bc;

import com.esmc.mcnp.model.obpsd.EuDetailBonOpi;
import com.esmc.mcnp.services.base.BaseService;

public interface EuDetailBonOpiService extends BaseService<EuDetailBonOpi, Integer> {
	public Integer getLastInsertedId();

	public EuDetailBonOpi findByEuBon_BonNumero(String numeroBon);
	
	public EuDetailBonOpi findByTpagcp(Long idTpagcp);
}

package com.esmc.mcnp.infrastructure.services.acteurs;

import com.esmc.mcnp.domain.entity.obpsd.EuBanque;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuBanqueService extends BaseService<EuBanque, String> {

    public EuBanque findByMembre(String codeMembre);

    public EuBanque findByDefaut();
}

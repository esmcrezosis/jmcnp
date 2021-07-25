package com.esmc.mcnp.services.acteurs;

import com.esmc.mcnp.model.obpsd.EuBanque;
import com.esmc.mcnp.services.base.BaseService;

public interface EuBanqueService extends BaseService<EuBanque, String> {

    public EuBanque findByMembre(String codeMembre);

    public EuBanque findByDefaut();
}

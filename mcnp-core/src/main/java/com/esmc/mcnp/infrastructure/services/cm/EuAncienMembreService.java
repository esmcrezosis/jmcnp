package com.esmc.mcnp.infrastructure.services.cm;

import com.esmc.mcnp.domain.entity.cm.EuAncienMembre;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuAncienMembreService extends BaseService<EuAncienMembre, String> {
    EuAncienMembre findByMembre(String codeMembre);

    String getAncienCodeByMembre(String codeMembre);
}

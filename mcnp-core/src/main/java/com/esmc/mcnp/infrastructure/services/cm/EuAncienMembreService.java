package com.esmc.mcnp.services.cm;

import com.esmc.mcnp.model.cm.EuAncienMembre;
import com.esmc.mcnp.services.base.BaseService;

public interface EuAncienMembreService extends BaseService<EuAncienMembre, String> {
    EuAncienMembre findByMembre(String codeMembre);

    String getAncienCodeByMembre(String codeMembre);
}

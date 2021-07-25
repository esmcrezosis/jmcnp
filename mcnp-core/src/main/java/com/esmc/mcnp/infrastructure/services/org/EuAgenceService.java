package com.esmc.mcnp.services.org;

import com.esmc.mcnp.model.org.EuAgence;
import com.esmc.mcnp.services.base.CrudService;

public interface EuAgenceService extends CrudService<EuAgence, String> {
    EuAgence findByTypeGac(String typeGac);
}

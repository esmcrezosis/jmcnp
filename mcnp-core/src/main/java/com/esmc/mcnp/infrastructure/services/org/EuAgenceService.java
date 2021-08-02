package com.esmc.mcnp.infrastructure.services.org;

import com.esmc.mcnp.domain.entity.org.EuAgence;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

public interface EuAgenceService extends CrudService<EuAgence, String> {
    EuAgence findByTypeGac(String typeGac);
}

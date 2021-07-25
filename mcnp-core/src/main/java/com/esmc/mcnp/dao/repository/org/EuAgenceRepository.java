package com.esmc.mcnp.repositories.org;

import com.esmc.mcnp.model.org.EuAgence;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuAgenceRepository extends BaseRepository<EuAgence, String> {
    EuAgence findByTypeGac(String typeGac);
}

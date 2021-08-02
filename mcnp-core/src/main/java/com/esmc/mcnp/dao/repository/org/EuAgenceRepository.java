package com.esmc.mcnp.dao.repository.org;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.org.EuAgence;

public interface EuAgenceRepository extends BaseRepository<EuAgence, String> {
    EuAgence findByTypeGac(String typeGac);
}

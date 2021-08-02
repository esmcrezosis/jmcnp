package com.esmc.mcnp.dao.repository.old;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.old.Morale;

public interface MoraleRepository extends BaseRepository<Morale, String> {
    Morale findByCodeMembre(String codeMembre);
}

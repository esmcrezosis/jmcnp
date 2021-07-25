package com.esmc.mcnp.repositories.old;

import com.esmc.mcnp.model.old.Morale;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface MoraleRepository extends BaseRepository<Morale, String> {
    Morale findByCodeMembre(String codeMembre);
}

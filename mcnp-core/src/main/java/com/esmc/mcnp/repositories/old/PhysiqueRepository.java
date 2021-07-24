package com.esmc.mcnp.repositories.old;

import com.esmc.mcnp.model.old.Physique;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface PhysiqueRepository extends BaseRepository<Physique, String> {
    Physique findByCodeMembre(String codeMembre);
}

package com.esmc.mcnp.dao.repository.old;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.old.Physique;

public interface PhysiqueRepository extends BaseRepository<Physique, String> {
    Physique findByCodeMembre(String codeMembre);
}

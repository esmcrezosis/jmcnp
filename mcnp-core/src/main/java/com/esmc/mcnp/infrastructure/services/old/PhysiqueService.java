package com.esmc.mcnp.infrastructure.services.old;

import com.esmc.mcnp.domain.entity.old.Physique;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

public interface PhysiqueService extends CrudService<Physique, String> {
    Physique findByCodeMembre(String codeMembre);
}

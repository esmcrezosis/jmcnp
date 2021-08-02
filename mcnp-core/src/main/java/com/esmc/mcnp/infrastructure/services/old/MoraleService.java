package com.esmc.mcnp.infrastructure.services.old;

import com.esmc.mcnp.domain.entity.old.Morale;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

public interface MoraleService extends CrudService<Morale, String> {
    Morale findByCodeMembre(String codeMembre);
}

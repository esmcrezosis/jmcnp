package com.esmc.mcnp.services.old;

import com.esmc.mcnp.model.old.Morale;
import com.esmc.mcnp.services.base.CrudService;

public interface MoraleService extends CrudService<Morale, String> {
    Morale findByCodeMembre(String codeMembre);
}

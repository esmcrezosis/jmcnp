package com.esmc.mcnp.services.old;

import com.esmc.mcnp.model.old.Physique;
import com.esmc.mcnp.services.base.CrudService;

public interface PhysiqueService extends CrudService<Physique, String> {
    Physique findByCodeMembre(String codeMembre);
}

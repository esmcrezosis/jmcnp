package com.esmc.mcnp.services.pc;

import com.esmc.mcnp.model.pc.EuCasPassif;
import com.esmc.mcnp.services.base.CrudService;

import java.util.List;

public interface EuCasPassifService extends CrudService<EuCasPassif, Integer> {
    List<EuCasPassif> findByEntreprise(String entreprise);
}

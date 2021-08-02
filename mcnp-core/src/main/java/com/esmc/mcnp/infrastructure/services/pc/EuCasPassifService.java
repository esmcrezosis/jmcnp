package com.esmc.mcnp.infrastructure.services.pc;

import com.esmc.mcnp.domain.entity.pc.EuCasPassif;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

import java.util.List;

public interface EuCasPassifService extends CrudService<EuCasPassif, Integer> {
    List<EuCasPassif> findByEntreprise(String entreprise);
}

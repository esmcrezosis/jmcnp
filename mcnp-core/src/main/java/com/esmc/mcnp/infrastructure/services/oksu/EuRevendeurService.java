package com.esmc.mcnp.infrastructure.services.oksu;

import com.esmc.mcnp.domain.entity.oksu.EuRevendeur;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

import java.util.List;

public interface EuRevendeurService extends BaseService<EuRevendeur, Long> {

    public List<String> getListeProduitsRevendeur();
}

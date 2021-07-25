package com.esmc.mcnp.services.oksu;

import com.esmc.mcnp.model.oksu.EuRevendeur;
import com.esmc.mcnp.services.base.BaseService;

import java.util.List;

public interface EuRevendeurService extends BaseService<EuRevendeur, Long> {

    public List<String> getListeProduitsRevendeur();
}

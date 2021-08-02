package com.esmc.mcnp.infrastructure.services.common;

import java.util.List;

import com.esmc.mcnp.domain.entity.cm.EuTelephone;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuTelephoneService extends BaseService<EuTelephone, Integer> {

	List<EuTelephone> findByMembre(String codeMembre);

	List<EuTelephone> findByMembreAndCompagnie(String codeMembre, String compagnie);
}

package com.esmc.mcnp.services.common;

import java.util.List;

import com.esmc.mcnp.model.cm.EuTelephone;
import com.esmc.mcnp.services.base.BaseService;

public interface EuTelephoneService extends BaseService<EuTelephone, Integer> {

	List<EuTelephone> findByMembre(String codeMembre);

	List<EuTelephone> findByMembreAndCompagnie(String codeMembre, String compagnie);
}

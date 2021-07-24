package com.esmc.mcnp.services.obps;

import java.util.List;

import com.esmc.mcnp.model.obps.EuAncienGcp;
import com.esmc.mcnp.services.base.BaseService;

public interface EuAncienGcpService extends BaseService<EuAncienGcp, Long> {
	List<EuAncienGcp> findByMembre(String codeMembre);

	Double getSoldeGcpByMembre(String codeMembre);
}

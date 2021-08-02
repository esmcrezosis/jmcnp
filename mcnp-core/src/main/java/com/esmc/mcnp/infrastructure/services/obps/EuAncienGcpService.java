package com.esmc.mcnp.infrastructure.services.obps;

import java.util.List;

import com.esmc.mcnp.domain.entity.obps.EuAncienGcp;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuAncienGcpService extends BaseService<EuAncienGcp, Long> {
	List<EuAncienGcp> findByMembre(String codeMembre);

	Double getSoldeGcpByMembre(String codeMembre);
}

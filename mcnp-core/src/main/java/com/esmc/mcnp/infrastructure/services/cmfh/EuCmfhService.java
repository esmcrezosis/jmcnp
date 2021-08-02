package com.esmc.mcnp.infrastructure.services.cmfh;

import java.util.List;

import com.esmc.mcnp.domain.entity.cmfh.EuCmfh;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuCmfhService extends BaseService<EuCmfh, Long> {
	
	EuCmfh findByMembre(String codeMembre);

	List<EuCmfh> findAllByMembre(String codeMembre);

	List<EuCmfh> findByRembourser(Integer rembourser);

	EuCmfh findByMembreAndRembourser(String codeMembre, Integer rembourser);
}

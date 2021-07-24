package com.esmc.mcnp.services.cmfh;

import java.util.List;

import com.esmc.mcnp.model.cmfh.EuCmfh;
import com.esmc.mcnp.services.base.BaseService;

public interface EuCmfhService extends BaseService<EuCmfh, Long> {
	
	EuCmfh findByMembre(String codeMembre);

	List<EuCmfh> findAllByMembre(String codeMembre);

	List<EuCmfh> findByRembourser(Integer rembourser);

	EuCmfh findByMembreAndRembourser(String codeMembre, Integer rembourser);
}

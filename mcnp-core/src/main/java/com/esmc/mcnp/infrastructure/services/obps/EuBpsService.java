package com.esmc.mcnp.infrastructure.services.obps;

import java.util.List;

import com.esmc.mcnp.domain.entity.obps.EuBps;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuBpsService extends BaseService<EuBps, Integer> {

	
	public List<EuBps> findAllBps();
	
	public EuBps findByDesignationAndTypeSouscription(String designation, String typesouscription);

	public EuBps findByDesignation(String designation);
}

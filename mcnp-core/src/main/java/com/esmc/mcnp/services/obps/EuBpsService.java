package com.esmc.mcnp.services.obps;

import java.util.List;

import com.esmc.mcnp.model.obps.EuBps;
import com.esmc.mcnp.services.base.BaseService;

public interface EuBpsService extends BaseService<EuBps, Integer> {

	
	public List<EuBps> findAllBps();
	
	public EuBps findByDesignationAndTypeSouscription(String designation, String typesouscription);

	public EuBps findByDesignation(String designation);
}

package com.esmc.mcnp.services.org;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.org.EuZone;
import com.esmc.mcnp.repositories.org.EuZoneRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Service
public class EuZoneServiceImpl extends BaseServiceImpl<EuZone, Integer> implements EuZoneService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EuZoneRepository zoneRepository;

	@Override
	protected BaseRepository<EuZone, Integer> getRepository() {
		return zoneRepository;
	}

}

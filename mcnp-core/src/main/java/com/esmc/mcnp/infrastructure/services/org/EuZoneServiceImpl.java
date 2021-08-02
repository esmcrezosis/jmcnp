package com.esmc.mcnp.infrastructure.services.org;

import org.springframework.stereotype.Service;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.org.EuZoneRepository;
import com.esmc.mcnp.domain.entity.org.EuZone;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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

package com.esmc.mcnp.infrastructure.services.org;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.org.EuRegionRepository;
import com.esmc.mcnp.domain.entity.org.EuRegion;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euRegionService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuRegionServiceImpl extends BaseServiceImpl<EuRegion, Integer> implements EuRegionService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuRegionRepository regionRepo;

	@Override
	protected BaseRepository<EuRegion, Integer> getRepository() {
		return regionRepo;
	}

}

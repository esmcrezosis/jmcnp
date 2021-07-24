package com.esmc.mcnp.services.org;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.org.EuRegion;
import com.esmc.mcnp.repositories.org.EuRegionRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

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

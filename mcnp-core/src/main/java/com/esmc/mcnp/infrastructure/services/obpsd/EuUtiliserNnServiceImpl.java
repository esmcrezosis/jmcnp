package com.esmc.mcnp.infrastructure.services.obpsd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuUtiliserNnRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuUtiliserNn;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euUtiliserNnService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuUtiliserNnServiceImpl extends BaseServiceImpl<EuUtiliserNn, Long> implements EuUtiliserNnService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuUtiliserNnRepository utiliserNnRepo;

	@Override
	protected BaseRepository<EuUtiliserNn, Long> getRepository() {
		return utiliserNnRepo;
	}

}

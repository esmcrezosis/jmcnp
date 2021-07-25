package com.esmc.mcnp.services.obpsd;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obpsd.EuUtiliserNn;
import com.esmc.mcnp.repositories.obpsd.EuUtiliserNnRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

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

package com.esmc.mcnp.infrastructure.services.obps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.others.EuTypePrkRepository;
import com.esmc.mcnp.domain.entity.bc.EuTypePrk;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euTypePrkService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuTypePrkServiceImpl extends BaseServiceImpl<EuTypePrk, Integer> implements EuTypePrkService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuTypePrkRepository typePrkRepo;
	@Override
	protected BaseRepository<EuTypePrk, Integer> getRepository() {
		return typePrkRepo;
	}

}

package com.esmc.mcnp.services.obps;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.bc.EuTypePrk;
import com.esmc.mcnp.repositories.others.EuTypePrkRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

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

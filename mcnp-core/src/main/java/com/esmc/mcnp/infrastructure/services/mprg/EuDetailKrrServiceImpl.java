package com.esmc.mcnp.services.mprg;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.mprg.EuDetailKrr;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.mprg.EuDetailKrrRepository;

@Service("euDetailKrr")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuDetailKrrServiceImpl extends BaseServiceImpl<EuDetailKrr, Long> implements EuDetailKrrService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuDetailKrrRepository detKrrRepo;

	@Override
	protected BaseRepository<EuDetailKrr, Long> getRepository() {
		return detKrrRepo;
	}

}

package com.esmc.mcnp.infrastructure.services.mprg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.mprg.EuDetailKrrRepository;
import com.esmc.mcnp.domain.entity.mprg.EuDetailKrr;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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

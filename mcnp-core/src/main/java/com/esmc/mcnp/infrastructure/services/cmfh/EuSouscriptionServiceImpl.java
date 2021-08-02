package com.esmc.mcnp.infrastructure.services.cmfh;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.cmfh.EuSouscriptionRepository;
import com.esmc.mcnp.domain.entity.acteur.EuSouscription;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euSouscriptionService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuSouscriptionServiceImpl extends BaseServiceImpl<EuSouscription, Long> implements EuSouscriptionService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EuSouscriptionRepository souscriptionRepo;

	@Override
	protected BaseRepository<EuSouscription, Long> getRepository() {
		return souscriptionRepo;
	}

}

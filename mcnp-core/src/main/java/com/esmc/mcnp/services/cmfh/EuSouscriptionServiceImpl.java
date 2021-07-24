package com.esmc.mcnp.services.cmfh;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.acteur.EuSouscription;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.cmfh.EuSouscriptionRepository;

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

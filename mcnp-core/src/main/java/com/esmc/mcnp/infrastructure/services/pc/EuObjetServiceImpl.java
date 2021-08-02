package com.esmc.mcnp.infrastructure.services.pc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obps.EuObjetRepository;
import com.esmc.mcnp.domain.entity.others.EuObjet;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euObjetService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class EuObjetServiceImpl extends BaseServiceImpl<EuObjet, Integer> implements EuObjetService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuObjetRepository objetRepo;

	@Override
	protected BaseRepository<EuObjet, Integer> getRepository() {
		return objetRepo;
	}

}

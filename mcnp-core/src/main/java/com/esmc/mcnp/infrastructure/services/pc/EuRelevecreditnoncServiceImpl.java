package com.esmc.mcnp.infrastructure.services.pc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.pc.EuRelevecreditnoncRepository;
import com.esmc.mcnp.domain.entity.pc.EuRelevecreditnonc;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euRelevecreditnoncService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuRelevecreditnoncServiceImpl extends BaseServiceImpl<EuRelevecreditnonc, Integer>
		implements EuRelevecreditnoncService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuRelevecreditnoncRepository relcreditnoncRepo;

	@Override
	protected BaseRepository<EuRelevecreditnonc, Integer> getRepository() {
		return relcreditnoncRepo;
	}

}

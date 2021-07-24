package com.esmc.mcnp.services.pc;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.pc.EuRelevecreditnonc;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.pc.EuRelevecreditnoncRepository;

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

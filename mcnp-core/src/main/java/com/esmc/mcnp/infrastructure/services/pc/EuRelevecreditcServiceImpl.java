package com.esmc.mcnp.infrastructure.services.pc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.pc.EuRelevecreditcRepository;
import com.esmc.mcnp.domain.entity.pc.EuRelevecreditc;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euRelevecredictService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuRelevecreditcServiceImpl extends BaseServiceImpl<EuRelevecreditc, Integer>
		implements EuRelevecreditcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuRelevecreditcRepository relcreditcRepo;
	@Override
	protected BaseRepository<EuRelevecreditc, Integer> getRepository() {
		return relcreditcRepo;
	}

}

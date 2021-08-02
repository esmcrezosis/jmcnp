package com.esmc.mcnp.infrastructure.services.bc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.bc.EuCreditEchangeRepository;
import com.esmc.mcnp.domain.entity.bc.EuCreditEchange;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euCreditEchangeService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuCreditEchangeServiceImpl extends BaseServiceImpl<EuCreditEchange, Long>
		implements EuCreditEchangeService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuCreditEchangeRepository creditEchRepo;
	@Override
	public Long getLastInsertedId() {
		return creditEchRepo.getLastInsertedId();
	}

	@Override
	protected BaseRepository<EuCreditEchange, Long> getRepository() {
		return creditEchRepo;
	}

}

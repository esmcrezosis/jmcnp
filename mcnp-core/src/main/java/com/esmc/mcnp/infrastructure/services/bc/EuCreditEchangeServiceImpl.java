package com.esmc.mcnp.services.bc;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.bc.EuCreditEchange;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.bc.EuCreditEchangeRepository;

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

package com.esmc.mcnp.services.obpsd;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obpsd.EuEchange;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obpsd.EuEchangeRepository;

@Service("euEchangeService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuEchangeServiceImpl extends BaseServiceImpl<EuEchange, Long> implements EuEchangeService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuEchangeRepository echangeRepo;

	@Override
	protected BaseRepository<EuEchange, Long> getRepository() {
		return echangeRepo;
	}

	@Override
	public Long findLastEchangeInsertedId() {
		return echangeRepo.findLastEchangeInsertedId();
	}

}

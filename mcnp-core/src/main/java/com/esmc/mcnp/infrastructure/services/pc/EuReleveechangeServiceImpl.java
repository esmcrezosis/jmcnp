package com.esmc.mcnp.infrastructure.services.pc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.pc.EuReleveechangeRepository;
import com.esmc.mcnp.domain.entity.pc.EuReleveechange;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euReleveechangeService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuReleveechangeServiceImpl extends BaseServiceImpl<EuReleveechange, Integer>
		implements EuReleveechangeService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuReleveechangeRepository relechRepo;

	@Override
	protected BaseRepository<EuReleveechange, Integer> getRepository() {
		return relechRepo;
	}

}

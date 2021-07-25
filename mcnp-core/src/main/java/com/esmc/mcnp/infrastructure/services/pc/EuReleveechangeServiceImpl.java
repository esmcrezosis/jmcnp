package com.esmc.mcnp.services.pc;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.pc.EuReleveechange;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.pc.EuReleveechangeRepository;

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

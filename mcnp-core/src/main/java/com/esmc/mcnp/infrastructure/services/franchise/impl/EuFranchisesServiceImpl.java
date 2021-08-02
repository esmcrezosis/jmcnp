package com.esmc.mcnp.infrastructure.services.franchise.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.franchise.EuFranchisesRepository;
import com.esmc.mcnp.domain.entity.franchise.EuFranchises;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;
import com.esmc.mcnp.infrastructure.services.franchise.EuFranchisesService;

@Service
@Transactional
public class EuFranchisesServiceImpl extends BaseServiceImpl<EuFranchises, Integer> implements EuFranchisesService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EuFranchisesRepository repository;

	@Override
	protected BaseRepository<EuFranchises, Integer> getRepository() {
		return repository;
	}

}

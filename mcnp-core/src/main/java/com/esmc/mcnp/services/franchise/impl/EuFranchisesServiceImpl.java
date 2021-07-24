package com.esmc.mcnp.services.franchise.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.franchise.EuFranchises;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.franchise.EuFranchisesRepository;
import com.esmc.mcnp.services.base.BaseServiceImpl;
import com.esmc.mcnp.services.franchise.EuFranchisesService;

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

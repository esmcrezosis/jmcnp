package com.esmc.mcnp.infrastructure.services.franchise.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.franchise.EuActivationActeurRepository;
import com.esmc.mcnp.domain.entity.franchise.EuActivationActeur;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;
import com.esmc.mcnp.infrastructure.services.franchise.EuActivationActeurService;

@Service
@Transactional
public class EuActivationActeurServiceImpl extends BaseServiceImpl<EuActivationActeur, Integer>
		implements EuActivationActeurService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EuActivationActeurRepository repository;

	@Override
	protected BaseRepository<EuActivationActeur, Integer> getRepository() {
		return repository;
	}

}

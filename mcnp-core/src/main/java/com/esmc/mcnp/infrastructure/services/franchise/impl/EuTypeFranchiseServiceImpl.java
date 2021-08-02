package com.esmc.mcnp.infrastructure.services.franchise.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.franchise.EuTypeFranchisesRepository;
import com.esmc.mcnp.domain.entity.franchise.EuTypeFranchises;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;
import com.esmc.mcnp.infrastructure.services.franchise.EuTypeFranchiseService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuTypeFranchiseServiceImpl extends CrudServiceImpl<EuTypeFranchises, Integer>
		implements EuTypeFranchiseService {

	private EuTypeFranchisesRepository typeFranchiseRepo;

	public EuTypeFranchiseServiceImpl(EuTypeFranchisesRepository typeFranchiseRepo) {
		super(typeFranchiseRepo);
		this.typeFranchiseRepo = typeFranchiseRepo;
	}

}

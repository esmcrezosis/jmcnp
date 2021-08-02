package com.esmc.mcnp.infrastructure.services.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.bc.EuCycleFormationRepository;
import com.esmc.mcnp.domain.entity.bc.EuCycleFormation;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euCycleFormationService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuCycleFormationServiceImpl extends BaseServiceImpl<EuCycleFormation, Integer>
		implements EuCycleFormationService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuCycleFormationRepository cycleFormationRepo;

	@Override
	protected BaseRepository<EuCycleFormation, Integer> getRepository() {
		return cycleFormationRepo;
	}

	@Override
	public EuCycleFormation findByCodeformation(String cycle) {
		return cycleFormationRepo.findByCodeCycleFormation(cycle);
	}

}

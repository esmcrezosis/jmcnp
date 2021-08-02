package com.esmc.mcnp.dao.repository.bc;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.bc.EuCycleFormation;

public interface EuCycleFormationRepository extends BaseRepository<EuCycleFormation, Integer> {

	public EuCycleFormation findByCodeCycleFormation(String cycle);
}

package com.esmc.mcnp.repositories.bc;

import com.esmc.mcnp.model.bc.EuCycleFormation;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuCycleFormationRepository extends BaseRepository<EuCycleFormation, Integer> {

	public EuCycleFormation findByCodeCycleFormation(String cycle);
}

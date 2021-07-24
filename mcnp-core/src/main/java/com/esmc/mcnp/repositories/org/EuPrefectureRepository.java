package com.esmc.mcnp.repositories.org;

import com.esmc.mcnp.model.org.EuPrefecture;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuPrefectureRepository extends BaseRepository<EuPrefecture, Integer> {
	
	EuPrefecture findByEuCantons_IdCanton(Integer idCanton);
}

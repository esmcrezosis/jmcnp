package com.esmc.mcnp.dao.repository.org;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.org.EuPrefecture;

public interface EuPrefectureRepository extends BaseRepository<EuPrefecture, Integer> {
	
	EuPrefecture findByEuCantons_IdCanton(Integer idCanton);
}

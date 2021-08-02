package com.esmc.mcnp.dao.repository.obpsd;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreAppro;

public interface EuBonNeutreApproRepository extends BaseRepository<EuBonNeutreAppro, Integer> {
	@Query("select max(b.bonNeutreApproId) from EuBonNeutreAppro b")
	public int getLastInsertedId();
}

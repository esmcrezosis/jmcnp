package com.esmc.mcnp.repositories.obpsd;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.model.obpsd.EuBonNeutreAppro;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuBonNeutreApproRepository extends BaseRepository<EuBonNeutreAppro, Integer> {
	@Query("select max(b.bonNeutreApproId) from EuBonNeutreAppro b")
	public int getLastInsertedId();
}

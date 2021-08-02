package com.esmc.mcnp.dao.repository.others;

import java.util.List;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.pc.EuSalaireAffecter;

public interface EuSalaireAffecterRepository extends BaseRepository<EuSalaireAffecter, Long> {

	public List<EuSalaireAffecter> findByIdCredit(Long idCredit);
}

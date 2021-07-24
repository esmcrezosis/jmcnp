package com.esmc.mcnp.repositories.others;

import java.util.List;

import com.esmc.mcnp.model.pc.EuSalaireAffecter;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuSalaireAffecterRepository extends BaseRepository<EuSalaireAffecter, Long> {

	public List<EuSalaireAffecter> findByIdCredit(Long idCredit);
}

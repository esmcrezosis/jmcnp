package com.esmc.mcnp.dao.repository.ba;

import java.time.LocalDateTime;
import java.util.List;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuDemandeBan;

public interface EuDemandeBanRepository extends BaseRepository<EuDemandeBan, Integer> {
	List<EuDemandeBan> findByCodeMembreAndValider(String codeMembre, boolean valider);

	List<EuDemandeBan> findByCodeMembre(String codeMembre);
	
	List<EuDemandeBan> findByDateDemande(LocalDateTime date);
	
	
}

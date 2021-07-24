package com.esmc.mcnp.repositories.ba;

import java.time.LocalDateTime;
import java.util.List;

import com.esmc.mcnp.model.obpsd.EuDemandeBan;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuDemandeBanRepository extends BaseRepository<EuDemandeBan, Integer> {
	List<EuDemandeBan> findByCodeMembreAndValider(String codeMembre, boolean valider);

	List<EuDemandeBan> findByCodeMembre(String codeMembre);
	
	List<EuDemandeBan> findByDateDemande(LocalDateTime date);
	
	
}

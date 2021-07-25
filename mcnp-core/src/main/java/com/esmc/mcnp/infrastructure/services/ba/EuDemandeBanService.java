package com.esmc.mcnp.services.ba;

import java.time.LocalDateTime;
import java.util.List;

import com.esmc.mcnp.model.obpsd.EuDemandeBan;
import com.esmc.mcnp.services.base.BaseService;

public interface EuDemandeBanService extends BaseService<EuDemandeBan, Integer> {
	List<EuDemandeBan> findByMembreAndValider(String codeMembre, boolean valider);

	List<EuDemandeBan> findByMembre(String codeMembre);

	List<EuDemandeBan> findByDateDemande(LocalDateTime date);
}

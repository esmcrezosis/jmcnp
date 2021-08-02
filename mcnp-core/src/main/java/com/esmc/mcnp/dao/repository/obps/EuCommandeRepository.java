package com.esmc.mcnp.dao.repository.obps;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obps.EuCommande;

public interface EuCommandeRepository extends BaseRepository<EuCommande, Long> {
	EuCommande findBycodeConfirmation(String codeConfirmation);
}

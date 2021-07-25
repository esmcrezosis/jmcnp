package com.esmc.mcnp.repositories.obps;

import com.esmc.mcnp.model.obps.EuCommande;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuCommandeRepository extends BaseRepository<EuCommande, Long> {
	EuCommande findBycodeConfirmation(String codeConfirmation);
}

package com.esmc.mcnp.repositories.cmfh;

import com.esmc.mcnp.model.acteur.EuCodeActivation;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuCodeActivationRepository extends BaseRepository<EuCodeActivation, Long> {
	EuCodeActivation findByCodeMembre(String codeMembre);
}

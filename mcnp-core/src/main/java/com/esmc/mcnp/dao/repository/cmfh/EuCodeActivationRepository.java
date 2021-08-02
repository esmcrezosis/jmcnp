package com.esmc.mcnp.dao.repository.cmfh;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.acteur.EuCodeActivation;

public interface EuCodeActivationRepository extends BaseRepository<EuCodeActivation, Long> {
	EuCodeActivation findByCodeMembre(String codeMembre);
}

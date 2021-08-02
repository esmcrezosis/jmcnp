package com.esmc.mcnp.infrastructure.services.obpsd;

import java.util.Date;
import java.util.Optional;

import com.esmc.mcnp.domain.entity.obpsd.EuRelevebancaire;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuRelevebancaireService extends BaseService<EuRelevebancaire, Integer> {
	boolean isReleveBancaireExist(Date date, String bank);

	Optional<EuRelevebancaire> findByDateAndBanque(Date date, String codeBanque);

	Optional<EuRelevebancaire> insertRelevebancaire(Date date, String codeBanque, Long idUtilisateur);

}

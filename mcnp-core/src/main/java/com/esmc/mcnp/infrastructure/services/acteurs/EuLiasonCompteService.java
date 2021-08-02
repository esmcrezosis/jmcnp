package com.esmc.mcnp.infrastructure.services.acteurs;

import com.esmc.mcnp.domain.entity.acteur.EuLiasonCompte;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuLiasonCompteService extends BaseService<EuLiasonCompte, Integer> {
	EuLiasonCompte findByCompteAdmin(String codeCompte);

	EuLiasonCompte findByCompteAnim(String codeCompte);

}

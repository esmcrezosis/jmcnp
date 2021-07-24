package com.esmc.mcnp.services.acteurs;

import com.esmc.mcnp.model.acteur.EuLiasonCompte;
import com.esmc.mcnp.services.base.BaseService;

public interface EuLiasonCompteService extends BaseService<EuLiasonCompte, Integer> {
	EuLiasonCompte findByCompteAdmin(String codeCompte);

	EuLiasonCompte findByCompteAnim(String codeCompte);

}

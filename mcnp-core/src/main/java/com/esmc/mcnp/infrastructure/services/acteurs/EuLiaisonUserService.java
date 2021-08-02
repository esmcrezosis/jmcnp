package com.esmc.mcnp.infrastructure.services.acteurs;

import com.esmc.mcnp.domain.entity.acteur.EuLiaisonUser;
import com.esmc.mcnp.domain.entity.org.EuDivisionGac;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuLiaisonUserService extends BaseService<EuLiaisonUser, Integer> {
	EuDivisionGac findByUtilisateur(Long idUser);
}

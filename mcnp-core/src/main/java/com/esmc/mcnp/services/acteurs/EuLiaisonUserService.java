package com.esmc.mcnp.services.acteurs;

import com.esmc.mcnp.model.org.EuDivisionGac;
import com.esmc.mcnp.model.acteur.EuLiaisonUser;
import com.esmc.mcnp.services.base.BaseService;

public interface EuLiaisonUserService extends BaseService<EuLiaisonUser, Integer> {
	EuDivisionGac findByUtilisateur(Long idUser);
}

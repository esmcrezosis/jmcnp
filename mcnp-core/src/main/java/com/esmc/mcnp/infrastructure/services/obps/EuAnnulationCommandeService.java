package com.esmc.mcnp.infrastructure.services.obps;

import java.util.List;

import com.esmc.mcnp.domain.entity.obps.EuAnnulationCommande;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuAnnulationCommandeService extends BaseService<EuAnnulationCommande, Integer> {
	public List<EuAnnulationCommande> findByComande(Integer codeCommande);
}
